import axios from 'axios'
import { ElMessage } from 'element-plus'

// 创建 axios 实例
const service = axios.create({
  baseURL: '/api', // 配合 vite.config.js 中的 proxy
  timeout: 60000 // 将超时时间从 5000ms 延长到 60000ms 以支持大模型慢回复
})

// 请求拦截器
service.interceptors.request.use(
  config => {
    // 如果有 token 自动携带在请求头
    const token = localStorage.getItem('token')
    if (token) {
      config.headers['Authorization'] = token
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(
  response => {
    const res = response.data
    // 这里的 code 200 是我们在后端 Result 类中定义的
    if (res.code !== 200) {
      ElMessage.error(res.msg || '系统错误')
      return Promise.reject(new Error(res.msg || 'Error'))
    } else {
      return res
    }
  },
  error => {
    console.log('err' + error)
    if (error.response && error.response.status === 401) {
      ElMessage.error(error.response.data.msg || '登录已过期，请重新登录')
      localStorage.removeItem('token')
      localStorage.removeItem('user')
      // 跳转到登录页
      setTimeout(() => {
        window.location.href = '/login'
      }, 500)
    } else {
      ElMessage.error(error.message)
    }
    return Promise.reject(error)
  }
)

export default service
