import axios from 'axios'
import { ElMessage } from 'element-plus'

// 创建 axios 实例
const service = axios.create({
  baseURL: '/api', // 配合 vite.config.js 中的 proxy
  timeout: 5000
})

// 请求拦截器
service.interceptors.request.use(
  config => {
    // 如果有 token 可以在这里添加
    // const token = localStorage.getItem('token')
    // if (token) {
    //   config.headers['Authorization'] = token
    // }
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
    ElMessage.error(error.message)
    return Promise.reject(error)
  }
)

export default service
