# Log Management System (企业级智能日志管理平台)

<p align="center">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.2.1-brightgreen?style=for-the-badge&logo=springboot" alt="Spring Boot" />
  <img src="https://img.shields.io/badge/Vue.js-3.0-4FC08D?style=for-the-badge&logo=vuedotjs&logoColor=white" alt="Vue 3" />
  <img src="https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL" />
  <img src="https://img.shields.io/badge/Redis-6.0-DC382D?style=for-the-badge&logo=redis&logoColor=white" alt="Redis" />
  <img src="https://img.shields.io/badge/Aliyun_OSS-SDK-FF6A00?style=for-the-badge&logo=alibabacloud" alt="Aliyun OSS" />
  <img src="https://img.shields.io/badge/DeepSeek-AI-1031c2?style=for-the-badge" alt="DeepSeek AI" />
</p>

> 一个基于 **Spring Boot 3 + Vue 3** 体系构建的前后端分离智能日志管理系统。融入了 **AI 智能赋能、全双工通信、分布式缓存、云端对象存储与接口安全风控**等进阶架构体系，提供高可用、易扩展的现代化企业协作与数据治理解决方案。

---

## 📖 目录

- [✨ 核心功能特性](#-核心功能特性)
- [🧱 技术堆栈说明](#-技术堆栈说明)
- [📁 核心目录结构](#-核心目录结构)
- [🚀 本地开发与部署](#-本地开发与部署)
- [📄 开源协议 (License)](#-开源协议-license)

---

## ✨ 核心功能特性

### 1. 基础架构与风控验证
- **动态风控拦截**：引入 `Hutool-Captcha` 生成安全图形验证码，配合 `Redis` 赋予 120 秒 TTL 生命周期，实现防重放、防暴力遍历。
- **无状态安全鉴权**：利用 `JJWT` 发放访问令牌，结合拦截器完成全域接口权限拦截，保障多端会话安全。

### 2. 智能化日志协作 (核心引擎)
- **大模型 AI 深度集成**：无缝对接大模型 RESTful API。支持利用 `@Async` 非阻塞任务机制，由 Agent 完成日志专业润色，及高并发下的 AI 小助手自动评论互动。
- **多维度条件检索**：依托 `PageHelper` 深分页组件，实现极低开销条件级联合搜索及分页响应。

### 3. 热度排行与流量分发
- **算法级热搜呈现**：不再依赖数据库扫表排序，而是通过 Redis `ZSet` 落库阅读点击事件，实现毫秒级响应的实时**日志热度排行榜**。
- **分布式字典管控**：对于全局共享的“省/市/区”级联数据架构，通过 `@Cacheable` 配置进行常驻 Redis 内存读取，避免 IO 挤压。

### 4. 实时社交与多媒体控制
- **全双工 P2P 通信流**：打破 HTTP 轮询壁垒。整合 `Spring WebSockets` 与前端会话池，精准实现评论与系统级消息的**点对点实时推流下发**。
- **云端原生图床存储**：打通 `Aliyun OSS SDK` 抛弃本地媒体流服务，用户资源全部由云端对象池派发 CDN 公网地址重载交互。

---

## 🧱 技术堆栈说明

### 服务端基座 (Backend)
| 组件/框架 | 说明 | 版本/基底 |
| --- | --- | --- |
| **基础框架** | Spring Boot | 3.2.1 (基于 JDK 17) |
| **持久层生态** | MyBatis | 搭配 PageHelper 标准化分页处理器 |
| **数据库** | MySQL | 8.0+ |
| **高速缓存** | Redis | 提供字典树 Cache 及 ZSet 高频榜单计算 |
| **全双工协议** | Spring WebSockets | 长链接通道建立与路由 |
| **安全与工具** | JJWT / Hutool / Aliyun SDK | JWT 鉴权 / 图形验证与工具集 / 云存储网关 |

### 展示控制层 (Frontend)
| 技术/规范 | 说明 |
| --- | --- |
| **运行及包管理** | Vite 极速构建链 + Node.js 18 环境 |
| **响应与状态管理** | Vue 3 (Composition API) + Pinia 全域状态管理 |
| **组件化 UI UI** | Element Plus 数据呈现体系 |
| **数据通信** | Axios 结合请求拦截中心完成 Header 封装与 401 接管 |

---

## 📁 核心目录结构

由于采取了彻底的前后端分离标准，项目根目录由基础环境挂载及两级核心子工程构成：

```text
LogManagementSystem/
├── sql/
│   └── init.sql                 # 【核心】MySQL 结构初始化及基础数据装配脚本
├── src/                         # 【后端核心工程区】
│   ├── main/
│   │   ├── java/com/itheima/logmanagementsystem/
│   │   │   ├── config/          # CORS、WebSocket、拦截器等核心调度配置
│   │   │   ├── controller/      # API 出入边界控制中心
│   │   │   ├── entity/          # 基于 RDBMS 倒推映射的数据实体组
│   │   │   ├── interceptor/     # JWT 无状态通行权解析中间件
│   │   │   ├── mapper/          # MyBatis 底层读写 DAO 层
│   │   │   ├── service/         # 服务承载与 AI/WebSocket 高频逻辑包装组件
│   │   │   └── utils/           # JWT, Aliyun OSS等基建工具集
│   │   └── resources/
│   │       ├── mapper/          # 复杂动态 SQL XML 实现块
│   │       └── application.yml  # 【配置源】MySQL、Redis、OSS参数、AI参数池
├── vue-frontend/                # 【前端视图渲染工程】
│   ├── src/
│   │   ├── api/                 # Axios 请求接口隔离封装区
│   │   ├── assets/              # 本地静态多媒体及基础样式集
│   │   ├── router/              # Vue Router 导航与白名单隔离门禁
│   │   ├── stores/              # Pinia 持久化 / 动态状态管理
│   │   ├── utils/               # Axios 全局拦截及公共调用器
│   │   └── views/               # Element-Plus 组装的大型组合式应用页面
│   ├── vite.config.js           # 代理路由与本地服务开发口配置 (Port: 3000)
│   └── package.json             # 前端工程化依赖索引库
└── pom.xml                      # 后端 Maven 依赖全集索引 
```

---

## 🚀 本地开发与部署

为了顺利运行该平台，请确保宿主机已安装 **JDK 17**, **Maven**, **MySQL 8.0+**, **Node.js 18+**, 且后台有一台正在提供服务的 **Redis 守护进程 (Service)**。

### 1. 结构与数据库注入
- 利用外部数据库工具 (如 Navicat 或 DataGrip) 创建对应的数据表或逻辑空间。
- 执行存储池中的 `sql/init.sql` 覆盖并建立应用表结构。

### 2. 环境参数映射
请访问后端核心配置文件 `src/main/resources/application.yml` 进行变量适配替换：
```yaml
spring:
  datasource:
    # 填入你本地的 MySQL 名与密码
    url: jdbc:mysql://localhost:3306/...
    password: yourmysqlpassword

  redis:
    host: localhost
    port: 6379

# 对象存储配置 (如需体验用户真实文件上云)：
alioss:
  endpoint: <Your OSS Endpoint>
  bucket-name: <Your Bucket Name>
  access-key-id: <Your Access Key>
  access-key-secret: <Your Access Secret>
  
# 人工智能代理配置：
deepseek:
  api-key: <为了体验完整的 AI 辅助能力，请额外填入你个人的 API Key>
```

### 3. 全局启动指令

#### (1) 服务端点燃 (Backend)
定位至工程根目录下 (`LogManagementSystem/`) 并在终端拉起：
```bash
# 执行 Maven 清理并直接发起 Spring Boot 生命循环
mvnw.cmd spring-boot:run
```
> 服务将在底层抛挂 `8080` 端口开启监听。

#### (2) 前端服务点燃 (Frontend)
将终端切换至子工程目录，开始安装视图依赖并触发代理热更新：
```bash
cd vue-frontend
npm install
npm run dev
```

成功拉起后，系统将映射在：
👉 **`http://localhost:3000`**

### 4. 模拟测试账户
在非外部安全环境（本地）的初始化状态中，系统预置了后台唯一的访客凭证：
- 通行账号：`admin`
- 通行密钥：`123456`

---

## 📄 开源协议 (License)

本项目严格采用技术界标准的开源理念设计，仅用于学习、交流与个人作品展示。
