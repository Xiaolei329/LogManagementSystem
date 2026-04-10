<template>
  <router-view />
</template>

<script setup>
</script>

<style>
:root {
  --el-color-primary: #0071e3;
  --el-color-success: #34c759;
  --el-color-warning: #ff9f0a;
  --el-color-danger: #ff3b30;
  --el-border-radius-base: 14px;
  --el-border-radius-small: 8px;
  --el-border-radius-round: 20px;
  --el-box-shadow-light: 0 4px 20px rgba(0, 0, 0, 0.04);
  --el-box-shadow: 0 8px 30px rgba(0, 0, 0, 0.06);
  --el-bg-color-page: #f5f5f7;
  --el-text-color-primary: #1d1d1f;
  --el-text-color-regular: #515154;
  --el-text-color-secondary: #86868b;
  --el-font-weight-primary: 500;
  --el-border-color-light: #d2d2d7;
}
html, body {
  margin: 0;
  padding: 0;
  height: 100%;
  background-color: var(--el-bg-color-page);
  font-family: -apple-system, BlinkMacSystemFont, "SF Pro Text", "Helvetica Neue", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
}
#app {
  height: 100%;
}
.el-card {
  border: none !important;
  background: rgba(255, 255, 255, 0.8) !important;
  backdrop-filter: saturate(180%) blur(20px);
  -webkit-backdrop-filter: saturate(180%) blur(20px);
}
.el-button {
  font-weight: 500;
  letter-spacing: 0.3px;
}
</style>
