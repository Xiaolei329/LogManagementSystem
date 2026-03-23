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
import { getComments, addComment } from '../api/comment' // 假设你会在 api/comment.js 中创建这些方法
import { ElMessage } from 'element-plus'
import request from '../utils/request' // 临时直接用 request

const route = useRoute()
const articleId = route.params.id
const loading = ref(false)
const article = ref(null)
const comments = ref([])
const newComment = ref('')
const user = JSON.parse(localStorage.getItem('user') || '{}')

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
  margin: 0 auto;
}
.article-header {
  text-align: center;
}
.meta {
  color: #909399;
  font-size: 13px;
  margin-top: 10px;
}
.ml-3 {
  margin-left: 20px;
}
.article-content {
  font-size: 16px;
  line-height: 1.8;
  padding: 20px 0;
  white-space: pre-wrap;
}
.comment-section {
  margin-top: 20px;
}
.btn-wrapper {
  text-align: right;
  margin-top: 10px;
}
.comment-item {
  padding: 10px 0;
}
.comment-user {
  display: flex;
  justify-content: space-between;
  font-size: 12px;
  color: #909399;
  margin-bottom: 5px;
}
.username {
  color: #303133;
  font-weight: bold;
}
.comment-content {
  font-size: 14px;
  color: #606266;
}
</style>
