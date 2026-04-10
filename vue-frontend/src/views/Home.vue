<template>
  <div class="home-container layout-flex">
    <div class="main-content">
      <div class="search-bar">
        <el-input v-model="searchTitle" placeholder="搜索日志标题" style="width: 300px" clearable @clear="fetchData">
          <template #append>
            <el-button icon="Search" @click="fetchData" />
          </template>
        </el-input>
      </div>

      <div class="article-list" v-loading="loading">
        <el-card v-for="item in articleList" :key="item.articleId" class="article-item" shadow="hover" @click="goDetail(item.articleId)">
          <div class="article-header">
            <h3 class="title">{{ item.title }}</h3>
            <span class="time">{{ formatTime(item.createTime) }}</span>
          </div>
          <div class="article-meta">
            <span><el-icon><User /></el-icon> {{ item.authorName }}</span>
            <span class="ml-2"><el-icon><View /></el-icon> {{ item.readCount }}</span>
          </div>
          <div class="article-preview">
            {{ item.content.substring(0, 100) }}...
          </div>
        </el-card>
        <el-empty v-if="articleList.length === 0" description="暂无日志" />
      </div>

      <div class="pagination">
        <el-pagination
          background
          layout="prev, pager, next"
          :total="total"
          :page-size="pageSize"
          @current-change="handlePageChange"
        />
      </div>
    </div>

    <div class="sidebar">
      <el-card class="hot-card" v-loading="hotLoading">
        <template #header>
          <div class="card-header hot-header">
            <h3>🔥 实时热度榜单</h3>
          </div>
        </template>
        <div class="hot-list">
          <div v-for="(item, index) in hotList" :key="item.articleId" class="hot-item" @click="goDetail(item.articleId)">
            <span :class="['rank-num', index < 3 ? 'top3' : '']">{{ index + 1 }}</span>
            <span class="hot-title">{{ item.title }}</span>
          </div>
          <el-empty v-if="hotList.length === 0" description="暂无榜单数据" />
        </div>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getArticleList, getHotArticles } from '../api/article'

const router = useRouter()
const loading = ref(false)
const articleList = ref([])
const total = ref(0)
const pageNum = ref(1)
const pageSize = ref(10)
const searchTitle = ref('')

const hotLoading = ref(false)
const hotList = ref([])

const fetchHotData = async () => {
  hotLoading.value = true
  try {
    const res = await getHotArticles()
    hotList.value = res.data || []
  } finally {
    hotLoading.value = false
  }
}

const fetchData = async () => {
  loading.value = true
  try {
    const res = await getArticleList({
      pageNum: pageNum.value,
      pageSize: pageSize.value,
      title: searchTitle.value
    })
    articleList.value = res.data.list
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const handlePageChange = (val) => {
  pageNum.value = val
  fetchData()
}

const goDetail = (id) => {
  router.push(`/article/${id}`)
}

const formatTime = (time) => {
  if (!time) return ''
  return time.replace('T', ' ')
}

onMounted(() => {
  fetchData()
  fetchHotData()
})
</script>

<style scoped>
.home-container {
  max-width: 1100px;
  margin: 40px auto;
  padding: 0 20px;
}
.layout-flex {
  display: flex;
  gap: 30px;
  align-items: flex-start;
}
.main-content {
  flex: 1;
  min-width: 0;
}
.sidebar {
  width: 320px;
  flex-shrink: 0;
}
.hot-card {
  border-radius: 16px;
  position: sticky;
  top: 40px;
}
.hot-header h3 {
  margin: 0;
  font-size: 18px;
  font-weight: 600;
  color: var(--el-color-danger);
}
.hot-item {
  display: flex;
  align-items: center;
  padding: 12px 0;
  cursor: pointer;
  transition: all 0.2s;
  border-bottom: 1px solid var(--el-border-color-light);
}
.hot-item:last-child {
  border-bottom: none;
}
.hot-item:hover .hot-title {
  color: var(--el-color-primary);
}
.rank-num {
  font-size: 16px;
  font-weight: bold;
  color: #909399;
  width: 24px;
}
.rank-num.top3 {
  color: var(--el-color-danger);
  font-size: 18px;
}
.hot-title {
  font-size: 14px;
  color: var(--el-text-color-primary);
  flex: 1;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.search-bar {
  margin-bottom: 30px;
  text-align: right;
}
:deep(.el-input__wrapper) {
  border-radius: 20px;
  padding: 0 16px;
  box-shadow: 0 0 0 1px var(--el-border-color-light) inset;
  background-color: rgba(255,255,255,0.8);
}
.article-item {
  margin-bottom: 24px;
  cursor: pointer;
  border-radius: 16px;
  transition: all 0.4s cubic-bezier(0.2, 0.8, 0.2, 1);
}
.article-item:hover {
  transform: translateY(-4px);
  box-shadow: var(--el-box-shadow);
}
.article-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
}
.title {
  margin: 0;
  font-size: 20px;
  font-weight: 600;
  color: var(--el-text-color-primary);
  letter-spacing: -0.5px;
}
.time {
  font-size: 13px;
  color: var(--el-text-color-secondary);
}
.article-meta {
  font-size: 13px;
  color: var(--el-text-color-regular);
  margin-bottom: 16px;
  display: flex;
  align-items: center;
}
.ml-2 {
  margin-left: 20px;
}
.article-preview {
  color: var(--el-text-color-regular);
  font-size: 15px;
  line-height: 1.6;
}
.pagination {
  margin-top: 40px;
  display: flex;
  justify-content: center;
}
</style>
