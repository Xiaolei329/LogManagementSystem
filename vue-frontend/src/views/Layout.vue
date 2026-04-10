<template>
  <el-container class="layout-container">
    <el-header class="header">
      <div class="logo">日志管理系统</div>
      <div class="nav">
        <el-menu mode="horizontal" :default-active="activePath" router background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">
          <el-menu-item index="/home">首页</el-menu-item>
          <el-menu-item index="/publish">发布日志</el-menu-item>
          <el-menu-item index="/profile">个人中心</el-menu-item>
          <el-menu-item v-if="user.isAdmin === 1" index="/user-manage">用户管理</el-menu-item>
        </el-menu>
      </div>
      <div class="user-info">
        <el-dropdown @command="handleCommand">
          <span class="el-dropdown-link">
            {{ user.realName || user.username }}
            <el-icon class="el-icon--right"><arrow-down /></el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </el-header>
    <el-main class="main">
      <router-view />
    </el-main>
  </el-container>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElNotification } from 'element-plus'

const router = useRouter()
const route = useRoute()

let user = {}
try {
  const userStr = localStorage.getItem('user')
  user = userStr && userStr !== 'undefined' ? JSON.parse(userStr) : {}
} catch (e) {
  console.error("Failed to parse user from localStorage", e)
}

const activePath = computed(() => route.path)

let ws = null

const initWebSocket = () => {
  if (!user || (!user.userId && !user.token)) return
  
  const wsUrl = `ws://localhost:8080/ws/${user.userId}`
  ws = new WebSocket(wsUrl)
  
  ws.onmessage = (e) => {
    ElNotification({
      title: '系统通知',
      message: e.data,
      type: 'success',
      duration: 6000
    })
  }

  ws.onerror = () => {
    console.error("WebSocket 连接遇到问题")
  }
}

onMounted(() => {
  initWebSocket()
})

onUnmounted(() => {
  if (ws) {
    ws.close()
  }
})

const handleCommand = (command) => {
  if (command === 'logout') {
    localStorage.removeItem('user')
    router.push('/login')
  }
}
</script>

<style scoped>
.layout-container {
  min-height: 100vh;
}
.header {
  background-color: #545c64;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  color: white;
}
.logo {
  font-size: 20px;
  font-weight: bold;
}
.nav {
  flex: 1;
  margin-left: 40px;
}
.user-info {
  cursor: pointer;
}
.el-dropdown-link {
  color: white;
  display: flex;
  align-items: center;
}
.main {
  background-color: #f0f2f5;
  padding: 20px;
}
</style>
