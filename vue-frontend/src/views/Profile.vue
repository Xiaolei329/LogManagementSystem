<template>
  <div class="profile-container">
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card class="user-card">
          <div class="avatar-wrapper">
            <el-upload
              class="avatar-uploader"
              action="/api/upload"
              :headers="uploadHeaders"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload"
              name="image"
            >
              <el-avatar v-if="userInfo.avatarUrl" :size="100" :src="userInfo.avatarUrl" />
              <el-avatar v-else :size="100" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />
              <div class="upload-tip">点击更换头像</div>
            </el-upload>
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

let user = {}
try {
  const userStr = localStorage.getItem('user')
  user = userStr && userStr !== 'undefined' ? JSON.parse(userStr) : {}
} catch (e) {
  console.error("Failed to parse user from localStorage", e)
}

const uploadHeaders = { Authorization: localStorage.getItem('token') }

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

const handleAvatarSuccess = async (res) => {
  if (res.code === 200) {
    userInfo.value.avatarUrl = res.data;

    await updateUserInfo({ userId: user.userId, avatarUrl: res.data });
    
    const cachedUser = JSON.parse(localStorage.getItem('user') || '{}');
    cachedUser.avatarUrl = res.data;
    localStorage.setItem('user', JSON.stringify(cachedUser));
    
    ElMessage.success('头像更换成功！');
  } else {
    ElMessage.error(res.message || '上传失败');
  }
}

const beforeAvatarUpload = (file) => {
  const isImage = file.type.startsWith('image/');
  const isLt2M = file.size / 1024 / 1024 < 2;

  if (!isImage) {
    ElMessage.error('头像只能是图片格式!');
  }
  if (!isLt2M) {
    ElMessage.error('头像图片大小不能超过 2MB!');
  }
  return isImage && isLt2M;
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
  position: relative;
  display: inline-block;
  cursor: pointer;
}
.upload-tip {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  background: rgba(0,0,0,0.5);
  color: #fff;
  font-size: 12px;
  line-height: 24px;
  border-bottom-left-radius: 50px;
  border-bottom-right-radius: 50px;
  opacity: 0;
  transition: opacity 0.3s;
}
.avatar-wrapper:hover .upload-tip {
  opacity: 1;
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
