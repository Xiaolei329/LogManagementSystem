<template>
  <div class="detail-container" v-loading="loading">
    <el-card v-if="article">
      <div class="article-header">
        <h1>{{ article.title }}</h1>
        <div class="meta">
          <span>作者：{{ article.authorName }}</span>
          <span class="ml-3">发布时间：{{ formatTime(article.createTime) }}</span>
          <span class="ml-3">阅读：{{ article.readCount }}</span>
        </div>
      </div>
      <el-divider />
      <div class="article-content">
        {{ article.content }}
      </div>
    </el-card>

    <el-card class="comment-section">
      <template #header>
        <div class="card-header">
          <h3>评论区</h3>
        </div>
      </template>

      <div class="comment-input">
        <el-input
          v-model="newComment"
          type="textarea"
          :rows="3"
          placeholder="写下你的评论..."
        />
        <div class="btn-wrapper">
          <el-button type="primary" size="small" @click="submitComment" :disabled="!newComment.trim()">发表评论</el-button>
        </div>
      </div>

      <div class="comment-list">
        <div v-for="item in comments" :key="item.commentId" class="comment-item">
          <div class="comment-user">
            <span class="username">{{ item.userName }}</span>
            <span class="time">{{ formatTime(item.createTime) }}</span>
          </div>
          <div class="comment-content">{{ item.content }}</div>
          <el-divider />
        </div>
        <el-empty v-if="comments.length === 0" description="暂无评论，快来抢沙发吧" />
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getArticleDetail } from '../api/article'
import { getComments, addComment } from '../api/comment' 
import { ElMessage } from 'element-plus'
import request from '../utils/request' 

const route = useRoute()
const articleId = route.params.id
const loading = ref(false)
const article = ref(null)
const comments = ref([])
const newComment = ref('')

let user = {}
try {
  const userStr = localStorage.getItem('user')
  user = userStr && userStr !== 'undefined' ? JSON.parse(userStr) : {}
} catch (e) {}

const fetchDetail = async () => {
  loading.value = true
  try {
    const res = await getArticleDetail(articleId)
    article.value = res.data
  } finally {
    loading.value = false
  }
}

const fetchComments = async () => {
  const res = await request.get(`/comment/list/${articleId}`)
  comments.value = res.data
}

const submitComment = async () => {
  if (!newComment.value.trim()) return
  try {
    await request.post('/comment/add', {
      articleId: parseInt(articleId),
      userId: user.userId,
      content: newComment.value,
      parentId: 0
    })
    ElMessage.success('评论成功')
    newComment.value = ''
    fetchComments()
  } catch (error) {}
}

const formatTime = (time) => {
  if (!time) return ''
  return time.replace('T', ' ')
}

onMounted(() => {
  fetchDetail()
  fetchComments()
})
</script>

<style scoped>
.detail-container {
  max-width: 800px;
  margin: 40px auto;
  padding: 0 20px;
}
.article-header {
  text-align: center;
  margin-bottom: 30px;
}
h1 {
  font-size: 28px;
  font-weight: 700;
  letter-spacing: -0.5px;
  margin-bottom: 12px;
  color: var(--el-text-color-primary);
}
.meta {
  color: var(--el-text-color-secondary);
  font-size: 14px;
}
.ml-3 {
  margin-left: 24px;
}
.article-content {
  font-size: 16px;
  line-height: 1.8;
  padding: 30px 0;
  white-space: pre-wrap;
  color: var(--el-text-color-primary);
}
.comment-section {
  margin-top: 40px;
  border-radius: 16px;
}
.card-header h3 {
  margin: 0;
  font-size: 18px;
  font-weight: 600;
}
.comment-input {
  margin-bottom: 30px;
}
:deep(.el-textarea__inner) {
  border-radius: 12px;
  padding: 12px;
  background-color: #f5f5f7;
  border: none;
  box-shadow: none;
}
:deep(.el-textarea__inner:focus) {
  background-color: #fff;
  box-shadow: 0 0 0 2px var(--el-color-primary);
}
.btn-wrapper {
  text-align: right;
  margin-top: 16px;
}
.comment-item {
  padding: 16px 0;
}
.comment-user {
  display: flex;
  justify-content: space-between;
  font-size: 13px;
  color: var(--el-text-color-secondary);
  margin-bottom: 8px;
}
.username {
  color: var(--el-text-color-primary);
  font-weight: 600;
}
.comment-content {
  font-size: 15px;
  color: var(--el-text-color-primary);
  line-height: 1.5;
}
</style>
