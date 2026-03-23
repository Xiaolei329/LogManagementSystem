<template>
  <div class="user-manage-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <h2>用户管理</h2>
        </div>
      </template>
      <el-table :data="userList" style="width: 100%" v-loading="loading">
        <el-table-column prop="username" label="用户名" />
        <el-table-column prop="realName" label="真实姓名" />
        <el-table-column prop="createTime" label="注册时间">
          <template #default="scope">
            {{ formatTime(scope.row.createTime) }}
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.status)">
              {{ getStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button
              v-if="scope.row.status === 0"
              size="small"
              type="success"
              @click="handleAudit(scope.row.userId, 1)">
              通过
            </el-button>
            <el-button
              v-if="scope.row.status === 1"
              size="small"
              type="warning"
              @click="handleAudit(scope.row.userId, 2)">
              禁用
            </el-button>
            <el-button
              v-if="scope.row.status === 2"
              size="small"
              type="success"
              @click="handleAudit(scope.row.userId, 1)">
              解禁
            </el-button>
            <el-button
              size="small"
              type="danger"
              @click="handleDelete(scope.row.userId)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import request from '../utils/request' // 简单起见直接用 request，规范应封装 api/user.js
import { ElMessage, ElMessageBox } from 'element-plus'

const userList = ref([])
const loading = ref(false)

const fetchUsers = async () => {
  loading.value = true
  try {
    const res = await request.get('/user/list')
    userList.value = res.data
  } finally {
    loading.value = false
  }
}

const handleAudit = async (id, status) => {
  try {
    await request.put(`/user/audit/${id}/${status}`)
    ElMessage.success('操作成功')
    fetchUsers()
  } catch (error) {}
}

const handleDelete = (id) => {
  ElMessageBox.confirm('确定要删除该用户吗？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await request.delete(`/user/${id}`)
    ElMessage.success('删除成功')
    fetchUsers()
  })
}

const getStatusType = (status) => {
  const map = { 0: 'info', 1: 'success', 2: 'danger' }
  return map[status] || 'info'
}

const getStatusText = (status) => {
  const map = { 0: '待审核', 1: '正常', 2: '已禁用' }
  return map[status] || '未知'
}

const formatTime = (time) => {
  if (!time) return ''
  return time.replace('T', ' ')
}

onMounted(() => {
  fetchUsers()
})
</script>

<style scoped>
.user-manage-container {
  max-width: 1000px;
  margin: 0 auto;
}
</style>
