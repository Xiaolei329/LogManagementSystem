<template>
  <div class="login-container">
    <el-card class="login-card">
      <template #header>
        <div class="card-header">
          <h2>日志管理系统</h2>
        </div>
      </template>
      <el-form :model="form" :rules="rules" ref="loginFormRef" label-width="0px">
        <el-form-item prop="username">
          <el-input v-model="form.username" placeholder="用户名" prefix-icon="User" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" type="password" placeholder="密码" prefix-icon="Lock" show-password @keyup.enter="handleLogin" />
        </el-form-item>
        <el-form-item prop="captchaCode">
          <div style="display: flex; gap: 10px; width: 100%;">
            <el-input v-model="form.captchaCode" placeholder="请输入验证码" @keyup.enter="handleLogin" style="flex: 1;" />
            <img v-if="captchaImg" :src="captchaImg" @click="fetchCaptcha" style="height: 32px; cursor: pointer; border-radius: 4px;" title="点击刷新" />
          </div>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" class="w-100" @click="handleLogin" :loading="loading">登录</el-button>
        </el-form-item>
        <div class="links">
          <router-link to="/register">没有账号？立即注册</router-link>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { login, getCaptcha } from '../api/user'
import { ElMessage } from 'element-plus'

const router = useRouter()
const loginFormRef = ref(null)
const loading = ref(false)
const captchaImg = ref('')

const form = reactive({
  username: '',
  password: '',
  captchaCode: '',
  captchaId: ''
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  captchaCode: [{ required: true, message: '请输入验证码', trigger: 'blur' }]
}

const fetchCaptcha = async () => {
  try {
    const res = await getCaptcha()
    if (res.data) {
      form.captchaId = res.data.captchaId
      captchaImg.value = res.data.captchaImg
    }
  } catch (error) {
    ElMessage.error('无法加载验证码')
  }
}

onMounted(() => {
  fetchCaptcha()
})

const handleLogin = async () => {
  if (!loginFormRef.value) return
  await loginFormRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        const res = await login(form)
        if (!res.data || !res.data.token) {
          ElMessage.error(res.message || '登录失败，请重试')
          fetchCaptcha() 
          return
        }
        // 提取 token 和 user
        localStorage.setItem('user', JSON.stringify(res.data.user))
        localStorage.setItem('token', res.data.token)
        ElMessage.success('登录成功')
        router.push('/')
      } catch (error) {
        fetchCaptcha() 
      } finally {
        loading.value = false
      }
    }
  })
}
</script>

<style scoped>
.login-container {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-image: url('../assets/login_bg.png');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
}
.login-card {
  width: 400px;
  border-radius: 16px;
  background: rgba(255, 255, 255, 0.75);
  backdrop-filter: blur(20px);
  -webkit-backdrop-filter: blur(20px);
  border: 1px solid rgba(255, 255, 255, 0.3);
  box-shadow: 0 8px 32px 0 rgba(0, 0, 0, 0.1);
}
.card-header {
  text-align: center;
  color: #303133;
}
.w-100 {
  width: 100%;
}
.links {
  text-align: right;
  font-size: 14px;
}
.links a {
  color: #409eff;
  text-decoration: none;
}
</style>
