# Log Management System

> 一个基于 **Spring Boot + Vue 3** 的前后端分离日志管理系统，面向企业场景下的日志内容发布、评论互动、用户管理与区域信息维护。

该项目以“工程化、可扩展、可展示”为目标构建，适合作为 Java 全栈方向的项目作品，用于求职作品集、技术面试讲解或课程实践演示。

---

## ✨ 项目亮点

- **前后端分离架构**：后端 RESTful API + 前端 SPA，职责清晰，便于维护与迭代。
- **完整业务闭环**：覆盖用户注册/登录、日志发布与维护、评论管理、后台用户审核等核心流程。
- **企业常见技术栈**：Spring Boot、MyBatis、Redis、Vue 3、Pinia、Element Plus。
- **可落地的本地部署能力**：提供数据库初始化脚本与可运行配置，启动成本低。

---

## 🧱 技术栈

### Backend

- Java 17
- Spring Boot 3.2.1
- MyBatis
- MySQL
- Redis
- PageHelper（分页）
- Maven

### Frontend

- Vue 3
- Vite
- Vue Router
- Pinia
- Element Plus
- Axios
- Sass

---

## 🏗️ 系统架构（简述）

```text
Vue 3 + Element Plus (3000)
          |
          | /api (Vite Proxy)
          v
Spring Boot REST API (8080)
      |             |
      v             v
    MySQL         Redis
```

---

## 📦 功能模块

### 用户模块

- 用户注册、登录
- 用户资料查询/更新
- 用户列表管理、状态审核

### 日志模块

- 日志分页查询
- 日志详情查看
- 日志发布、编辑、删除
- 我的日志管理

### 评论模块

- 按日志查询评论
- 发表评论
- 删除评论

### 区域模块

- 省/市/区三级联动数据查询

---

## 🚀 快速开始

### 1. 环境准备

- JDK 17+
- Maven 3.8+
- MySQL 8+
- Redis 6+
- Node.js 18+（建议）

### 2. 初始化数据库

执行以下 SQL 脚本：

- `sql/init.sql`

并确认后端配置（`src/main/resources/application.yml`）中的数据库连接信息与本地环境一致。

### 3. 启动后端

在仓库根目录执行：

```bash
bash mvnw spring-boot:run
```

后端默认地址：

- `http://localhost:8080`

### 4. 启动前端

```bash
cd vue-frontend
npm install
npm run dev
```

前端默认地址：

- `http://localhost:3000`

---

## 🔐 默认账号（本地初始化数据）

- 账号：`admin`
- 密码：`123456`

> 说明：该账号用于本地演示与联调。生产环境请务必替换默认密码并完善权限与安全策略。

---

## 📁 项目结构

```text
LogManagementSystem/
├── src/                         # Spring Boot 后端源码
│   ├── main/java/com/itheima/logmanagementsystem
│   │   ├── controller/
│   │   ├── service/
│   │   ├── mapper/
│   │   └── entity/
│   └── main/resources/
│       ├── application.yml
│       └── mapper/*.xml
├── vue-frontend/                # Vue 3 前端工程
│   ├── src/
│   │   ├── api/
│   │   ├── router/
│   │   ├── stores/
│   │   └── views/
├── sql/init.sql                 # 数据库初始化脚本
└── pom.xml                      # Maven 依赖管理
```

---

## ✅ 测试与构建

### 后端测试

```bash
bash mvnw test
```

### 前端构建

```bash
cd vue-frontend
npm run build
```

---

## 📚 项目价值（面试表达建议）

该项目可用于体现以下能力：

- 具备完整的 **前后端分离项目交付能力**
- 熟悉 Java Web 主流技术栈与常见工程实践
- 能独立完成从数据建模、接口开发到前端联调的闭环落地
- 具备一定的系统化思维（模块化设计、配置化部署、可维护结构）

---

## 📄 License

本项目仅用于学习、交流与个人作品展示。
