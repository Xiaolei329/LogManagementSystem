import request from '../utils/request'

export function getProvinces() {
  return request({
    url: '/region/provinces',
    method: 'get'
  })
}

export function getCities(provinceId) {
  return request({
    url: `/region/cities/${provinceId}`,
    method: 'get'
  })
}

export function getDistricts(cityId) {
  return request({
    url: `/region/districts/${cityId}`,
    method: 'get'
  })
}
