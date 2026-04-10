import request from '../utils/request'

export function polishArticleContent(content) {
  return request({
    url: '/ai/polish',
    method: 'post',
    data: { content }
  })
}
