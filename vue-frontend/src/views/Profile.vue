<template>
  <div class="profile-container">
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card class="user-card">
          <div class="avatar-wrapper">
            <el-avatar :size="100" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />
          </div>
          <div class="user-info">
            <h2>{{ userInfo.realName }}</h2>
            <p>用户名：{{ userInfo.username }}</p>
            <p>电话：{{ userInfo.phone || '未填写' }}</p>
            <p>邮箱：{{ userInfo.email || '未填写' }}</p>
            <el-button type="primary" size="small" @click="editDialogVisible = true">修改资料</el-button>
          </div>
        </el-card>
      </el-col>
      <el-col :span="16">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>我的日志</span>
            </div>
          </template>
          <el-table :data="myArticles" style="width: 100%">
            <el-table-column prop="title" label="标题" />
            <el-table-column prop="createTime" label="发布时间" width="180">
              <template #default="scope">
                {{ formatTime(scope.row.createTime) }}
              </template>
            </el-table-column>
            <el-table-column prop="readCount" label="阅读" width="80" />
            <el-table-column label="操作" width="150">
              <template #default="scope">
                <el-button size="small" @click="$router.push(`/article/${scope.row.articleId}`)">查看</el-button>
                <el-button size="small" type="danger" @click="handleDelete(scope.row.articleId)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <div class="pagination">
            <el-pagination
              background
              layout="prev, pager, next"
              :total="total"
              :page-size="pageSize"
              @current-change="handlePageChange"
            />
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 修改资料弹窗 -->
    <el-dialog v-model="editDialogVisible" title="修改资料" width="500px">
      <el-form :model="editForm" label-width="80px">
        <el-form-item label="真实姓名">
          <el-input v-model="editForm.realName" />
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="editForm.phone" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="editForm.email" />
        </el-form-item>
        <el-form-item label="QQ">
          <el-input v-model="editForm.qq" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="editDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleUpdate">确认</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getUserInfo, updateUserInfo } from '../api/user'
import { getMyArticles, deleteArticle } from '../api/article'
import { ElMessage, ElMessageBox } from 'element-plus'

const user = JSON.parse(localStorage.getItem('user') || '{}')
const userInfo = ref({})
const myArticles = ref([])
const total = ref(0)
const pageNum = ref(1)
const pageSize = ref(10)
const editDialogVisible = ref(false)
const editForm = reactive({
  userId: user.userId,
  realName: '',
  phone: '',
  email: '',
  qq: ''
})

const fetchUserInfo = async () => {
  const res = await getUserInfo(user.userId)
  userInfo.value = res.data
  Object.assign(editForm, res.data)
}

const fetchMyArticles = async () => {
  const res = await getMyArticles({
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    userId: user.userId
  })
  myArticles.value = res.data.list
  total.value = res.data.total
}

const handlePageChange = (val) => {
  pageNum.value = val
  fetchMyArticles()
}

const handleUpdate = async () => {
  try {
    await updateUserInfo(editForm)
    ElMessage.success('修改成功')
    editDialogVisible.value = false
    fetchUserInfo()
  } catch (error) {}
}

const handleDelete = (id) => {
  ElMessageBox.confirm('确定要删除这篇日志吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await deleteArticle(id)
    ElMessage.success('删除成功')
    fetchMyArticles()
  })
}

const formatTime = (time) => {
  if (!time) return ''
  return time.replace('T', ' ')
}

onMounted(() => {
  fetchUserInfo()
  fetchMyArticles()
})
</script>

<style scoped>
.profile-container {
  max-width: 1000px;
  margin: 0 auto;
}
.user-card {
  text-align: center;
}
.avatar-wrapper {
  margin-bottom: 20px;
}
.user-info p {
  color: #606266;
  margin: 10px 0;
}
.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}
</style>
