<template>
  <div class="register-container">
    <el-card class="register-card">
      <template #header>
        <div class="card-header">
          <h2>用户注册</h2>
        </div>
      </template>
      <el-form :model="form" :rules="rules" ref="registerFormRef" label-width="80px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" type="password" show-password placeholder="请输入密码" />
        </el-form-item>
        <el-form-item label="真实姓名" prop="realName">
          <el-input v-model="form.realName" placeholder="请输入真实姓名" />
        </el-form-item>
        <el-form-item label="省份">
          <el-select
            v-model="selectedProvince"
            placeholder="请选择省份"
            @change="handleProvinceChange"
            style="width: 100%">
            <el-option v-for="item in provinces" :key="item.provinceId" :label="item.provinceName" :value="item.provinceId" />
          </el-select>
        </el-form-item>
        <el-form-item label="城市" v-if="cities.length > 0">
          <el-select
            v-model="selectedCity"
            placeholder="请选择城市"
            @change="handleCityChange"
            style="width: 100%">
            <el-option v-for="item in cities" :key="item.cityId" :label="item.cityName" :value="item.cityId" />
          </el-select>
        </el-form-item>
        <el-form-item label="区县" v-if="districts.length > 0">
          <el-select
            v-model="form.districtId"
            placeholder="请选择区县"
            style="width: 100%">
            <el-option v-for="item in districts" :key="item.districtId" :label="item.districtName" :value="item.districtId" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" class="w-100" @click="handleRegister" :loading="loading">注册</el-button>
        </el-form-item>
        <div class="links">
          <router-link to="/login">已有账号？去登录</router-link>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { register } from '../api/user'
import { getProvinces, getCities, getDistricts } from '../api/common'
import { ElMessage } from 'element-plus'

const router = useRouter()
const registerFormRef = ref(null)
const loading = ref(false)

const form = reactive({
  username: '',
  password: '',
  realName: '',
  districtId: null
})

const selectedProvince = ref(null)
const selectedCity = ref(null)
const provinces = ref([])
const cities = ref([])
const districts = ref([])

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  realName: [{ required: true, message: '请输入真实姓名', trigger: 'blur' }]
}

onMounted(async () => {
  try {
    const res = await getProvinces()
    provinces.value = res.data
  } catch (e) {
    console.error("获取省份失败", e)
  }
})

const handleProvinceChange = async (val) => {
  selectedCity.value = null
  form.districtId = null
  cities.value = []
  districts.value = []
  try {
    const res = await getCities(val)
    cities.value = res.data
  } catch (e) {
    console.error("获取城市失败", e)
  }
}

const handleCityChange = async (val) => {
  form.districtId = null
  districts.value = []
  try {
    const res = await getDistricts(val)
    districts.value = res.data
  } catch (e) {
    console.error("获取地区失败", e)
  }
}

const handleRegister = async () => {
  if (!registerFormRef.value) return
  await registerFormRef.value.validate(async (valid) => {
    if (valid) {
      if (!form.districtId) {
        ElMessage.warning('请选择完整的所在地')
        return
      }
      loading.value = true
      try {
        await register(form)
        ElMessage.success('注册成功，请等待管理员审核')
        router.push('/login')
      } catch (error) {
      } finally {
        loading.value = false
      }
    }
  })
}
</script>

<style scoped>
.register-container {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}
.register-card {
  width: 500px;
  border-radius: 8px;
}
.card-header {
  text-align: center;
}
.w-100 {
  width: 100%;
}
.links {
  text-align: right;
  margin-top: 10px;
}
.links a {
  color: #409eff;
  text-decoration: none;
}
</style>
