<template>
  <div class="home-container">
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
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getArticleList } from '../api/article'

const router = useRouter()
const loading = ref(false)
const articleList = ref([])
const total = ref(0)
const pageNum = ref(1)
const pageSize = ref(10)
const searchTitle = ref('')

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
})
</script>

<style scoped>
.home-container {
  max-width: 1000px;
  margin: 0 auto;
}
.search-bar {
  margin-bottom: 20px;
  text-align: right;
}
.article-item {
  margin-bottom: 15px;
  cursor: pointer;
  transition: all 0.3s;
}
.article-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}
.title {
  margin: 0;
  font-size: 18px;
  color: #303133;
}
.time {
  font-size: 12px;
  color: #909399;
}
.article-meta {
  font-size: 13px;
  color: #606266;
  margin-bottom: 10px;
  display: flex;
  align-items: center;
}
.ml-2 {
  margin-left: 15px;
}
.article-preview {
  color: #606266;
  font-size: 14px;
  line-height: 1.6;
}
.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}
</style>
