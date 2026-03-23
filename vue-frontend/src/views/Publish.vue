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
          <el-button type="primary" @click="handleSubmit" :loading="loading">发布</el-button>
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
import { ElMessage } from 'element-plus'

const router = useRouter()
const formRef = ref(null)
const loading = ref(false)
const user = JSON.parse(localStorage.getItem('user') || '{}')

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
</script>

<style scoped>
.publish-container {
  max-width: 800px;
  margin: 0 auto;
}
</style>
