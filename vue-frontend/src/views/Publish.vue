<template>
  <div class="publish-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <h2>发布新日志</h2>
        </div>
      </template>
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入日志标题" />
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <el-input
            v-model="form.content"
            type="textarea"
            :rows="15"
            placeholder="在此输入日志内容..."
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSubmit" :loading="loading" round>发布</el-button>
          <el-button color="#f5f5f7" @click="handlePolish" :loading="aiLoading" style="color: #1d1d1f;" round>✨ 一键极简润色</el-button>
          <el-button @click="$router.back()">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { publishArticle } from '../api/article'
import { polishArticleContent } from '../api/ai'
import { ElMessage } from 'element-plus'

const router = useRouter()
const formRef = ref(null)
const loading = ref(false)
const aiLoading = ref(false)

let user = {}
try {
  const userStr = localStorage.getItem('user')
  user = userStr && userStr !== 'undefined' ? JSON.parse(userStr) : {}
} catch (e) {}

const form = reactive({
  title: '',
  content: '',
  userId: user.userId
})

const rules = {
  title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
  content: [{ required: true, message: '请输入内容', trigger: 'blur' }]
}

const handleSubmit = async () => {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        await publishArticle(form)
        ElMessage.success('发布成功')
        router.push('/home')
      } finally {
        loading.value = false
      }
    }
  })
}

const handlePolish = async () => {
  if (!form.content.trim()) {
    ElMessage.warning('请先输入需要润色的日志内容')
    return
  }
  aiLoading.value = true
  try {
    const res = await polishArticleContent(form.content)
    if(res.code === 200) { 
      form.content = res.data
      ElMessage.success('润色成功！')
    } else {
      ElMessage.error(res.msg || '润色失败')
    }
  } catch (error) {
    console.error(error)
  } finally {
    aiLoading.value = false
  }
}
</script>

<style scoped>
.publish-container {
  max-width: 800px;
  margin: 40px auto;
  padding: 0 20px;
}
.card-header h2 {
  margin: 0;
  font-size: 24px;
  font-weight: 600;
  letter-spacing: -0.5px;
}
:deep(.el-input__wrapper),
:deep(.el-textarea__inner) {
  border-radius: 12px;
  background-color: #f5f5f7;
  border: none;
  box-shadow: none;
  padding: 12px 16px;
}
:deep(.el-input__wrapper.is-focus),
:deep(.el-textarea__inner:focus) {
  background-color: #fff;
  box-shadow: 0 0 0 2px var(--el-color-primary);
}
</style>
