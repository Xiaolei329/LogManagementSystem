import request from '../utils/request'

export function getArticleList(params) {
  return request({
    url: '/article/list',
    method: 'get',
    params
  })
}

export function getArticleDetail(id) {
  return request({
    url: `/article/${id}`,
    method: 'get'
  })
}

export function publishArticle(data) {
  return request({
    url: '/article/publish',
    method: 'post',
    data
  })
}

export function getMyArticles(params) {
  return request({
    url: '/article/my',
    method: 'get',
    params
  })
}

export function deleteArticle(id) {
  return request({
    url: `/article/${id}`,
    method: 'delete'
  })
}
