# Log Management System

> 一个基于 **Spring Boot + Vue 3** 的前后端分离日志管理系统，面向企业场景下的日志内容发布、评论互动、用户管理与区域信息维护。

该项目以“工程化、可扩展、可展示”为目标构建，适合作为 Java 全栈方向的项目作品，用于课程实践演示与个人技术能力展示。

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
# macOS / Linux
./mvnw spring-boot:run

# Windows (PowerShell / CMD)
mvnw.cmd spring-boot:run
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
# macOS / Linux
./mvnw test

# Windows (PowerShell / CMD)
mvnw.cmd test
```

### 前端构建

```bash
cd vue-frontend
npm run build
```

---

## 📚 项目实践总结

### 我负责什么

- 完成后端核心业务接口设计与实现（用户、日志、评论、区域等模块）
- 负责前后端联调与接口规范统一，保障核心业务流程可用
- 完成本地化部署配置与初始化脚本整理，降低项目启动门槛

### 项目难点

- 业务模块较多，接口与数据模型之间的关联关系需要保持一致性
- 前后端并行开发过程中，需要持续处理字段、状态与分页参数的对齐问题
- 兼顾功能完整性与代码可维护性，避免后续迭代成本过高

### 优化点

- 通过模块化分层（controller/service/mapper）提升职责清晰度
- 使用 Redis 支撑高频数据访问场景，降低数据库压力
- 结合统一分页方案（PageHelper）改善列表查询体验与性能稳定性

### 可量化成果

- 覆盖完整业务闭环：注册登录、日志发布、评论互动、后台管理
- 前后端分离架构落地，支持独立开发、联调与部署
- 提供一键初始化脚本与跨平台启动命令，提升项目复现效率

---

## 📄 License

本项目仅用于学习、交流与个人作品展示。
