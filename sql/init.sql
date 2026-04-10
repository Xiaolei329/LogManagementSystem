/*
 Navicat Premium Dump SQL

 Source Server         : test
 Source Server Type    : MySQL
 Source Server Version : 90300 (9.3.0)
 Source Host           : localhost:3306
 Source Schema         : log_management_system

 Target Server Type    : MySQL
 Target Server Version : 90300 (9.3.0)
 File Encoding         : 65001

 Date: 10/04/2026 18:15:49
*/

-- 创建数据库
CREATE DATABASE IF NOT EXISTS log_management_system DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for s_city
-- ----------------------------
DROP TABLE IF EXISTS `s_city`;
CREATE TABLE `s_city`  (
  `city_id` int NOT NULL AUTO_INCREMENT COMMENT '城市ID',
  `city_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '城市名称',
  `zip_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮编',
  `province_id` int NOT NULL COMMENT '所属省份ID',
  `date_created` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `date_updated` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`city_id`) USING BTREE,
  INDEX `idx_province_id`(`province_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '城市表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of s_city
-- ----------------------------
INSERT INTO `s_city` VALUES (1, '洛阳市', NULL, 1, '2025-12-22 21:09:09', '2025-12-22 21:09:09');
INSERT INTO `s_city` VALUES (2, '郑州市', NULL, 1, '2025-12-22 21:09:09', '2025-12-22 21:09:09');
INSERT INTO `s_city` VALUES (3, '朝阳区', NULL, 2, '2025-12-22 21:09:09', '2025-12-22 21:09:09');
INSERT INTO `s_city` VALUES (4, '新乡市', NULL, 1, '2025-12-23 22:56:08', '2025-12-23 22:56:23');

-- ----------------------------
-- Table structure for s_district
-- ----------------------------
DROP TABLE IF EXISTS `s_district`;
CREATE TABLE `s_district`  (
  `district_id` int NOT NULL AUTO_INCREMENT COMMENT '地区ID',
  `district_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '地区名称',
  `city_id` int NOT NULL COMMENT '所属城市ID',
  `date_created` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `date_updated` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`district_id`) USING BTREE,
  INDEX `idx_city_id`(`city_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '地区表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of s_district
-- ----------------------------
INSERT INTO `s_district` VALUES (1, '洛龙区', 1, '2025-12-22 21:09:09', '2025-12-22 21:09:09');
INSERT INTO `s_district` VALUES (2, '金水区', 2, '2025-12-22 21:09:09', '2025-12-23 23:13:25');
INSERT INTO `s_district` VALUES (3, '西工区', 1, '2025-12-23 20:35:48', '2025-12-23 23:13:27');
INSERT INTO `s_district` VALUES (4, '老城区', 1, '2025-12-23 20:36:01', '2025-12-23 23:13:28');
INSERT INTO `s_district` VALUES (5, '牧野区', 4, '2025-12-23 22:56:41', '2025-12-23 23:13:30');
INSERT INTO `s_district` VALUES (6, '卫滨区', 4, '2025-12-23 22:57:03', '2025-12-23 23:24:54');

-- ----------------------------
-- Table structure for s_province
-- ----------------------------
DROP TABLE IF EXISTS `s_province`;
CREATE TABLE `s_province`  (
  `province_id` int NOT NULL AUTO_INCREMENT COMMENT '省份ID',
  `province_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '省份名称',
  `date_created` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `date_updated` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`province_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '省份表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of s_province
-- ----------------------------
INSERT INTO `s_province` VALUES (1, '河南省', '2025-12-22 21:09:09', '2025-12-22 21:09:09');
INSERT INTO `s_province` VALUES (2, '北京市', '2025-12-22 21:09:09', '2025-12-22 21:09:09');

-- ----------------------------
-- Table structure for sys_comment
-- ----------------------------
DROP TABLE IF EXISTS `sys_comment`;
CREATE TABLE `sys_comment`  (
  `comment_id` int NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `article_id` int NOT NULL COMMENT '日志ID',
  `user_id` int NOT NULL COMMENT '评论用户ID',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论内容',
  `parent_id` int NULL DEFAULT 0 COMMENT '父评论ID (用于回复)',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
  PRIMARY KEY (`comment_id`) USING BTREE,
  INDEX `idx_article_id`(`article_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '评论表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_comment
-- ----------------------------
INSERT INTO `sys_comment` VALUES (9, 12, 10, '校友！！！！', 0, '2025-12-23 23:08:45');
INSERT INTO `sys_comment` VALUES (10, 12, 10, '你好', 0, '2025-12-23 23:08:48');
INSERT INTO `sys_comment` VALUES (11, 14, 1, '测试AI Agent功能是个好主意，期待看到更多自动化测试的成果！', 0, '2026-04-09 17:18:07');
INSERT INTO `sys_comment` VALUES (12, 16, 11, '测试进展扎实，问题定位清晰，期待Agent在你们手中越来越聪明！', 0, '2026-04-09 17:33:58');
INSERT INTO `sys_comment` VALUES (13, 17, 11, 'AI Agent测试任务圆满完成，专业高效！', 0, '2026-04-09 17:48:49');
INSERT INTO `sys_comment` VALUES (14, 18, 11, '测试任务圆满完成，专业高效！期待系统顺利上线！', 0, '2026-04-09 17:51:08');
INSERT INTO `sys_comment` VALUES (15, 18, 12, '今日系统测试任务已完成', 0, '2026-04-09 17:51:16');
INSERT INTO `sys_comment` VALUES (16, 19, 11, 'Redis集成顺利，技术实力再上新台阶！', 0, '2026-04-09 18:18:56');
INSERT INTO `sys_comment` VALUES (17, 20, 11, '系统更新与任务优化都完成得很棒，继续保持高效与细致！', 0, '2026-04-09 19:17:01');
INSERT INTO `sys_comment` VALUES (18, 21, 11, '测试清单制定得如此系统全面，看得出你为保障质量打下了坚实基础，非常专业！', 0, '2026-04-10 15:33:13');
INSERT INTO `sys_comment` VALUES (19, 22, 11, '测试覆盖全面，代码质量有保障，为项目稳定性打下坚实基础！', 0, '2026-04-10 18:09:27');

-- ----------------------------
-- Table structure for sys_log_article
-- ----------------------------
DROP TABLE IF EXISTS `sys_log_article`;
CREATE TABLE `sys_log_article`  (
  `article_id` int NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标题',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '内容',
  `user_id` int NOT NULL COMMENT '发布用户ID',
  `read_count` int NULL DEFAULT 0 COMMENT '阅读量',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`article_id`) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '日志文章表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log_article
-- ----------------------------
INSERT INTO `sys_log_article` VALUES (1, '你好', '今天是2025年12月22日', 1, 6, '2025-12-22 21:18:38', '2026-04-09 18:18:22');
INSERT INTO `sys_log_article` VALUES (9, 'llllyk521dt', 'lyk   dt      ', 1, 3, '2025-12-23 23:04:21', '2026-04-09 18:18:18');
INSERT INTO `sys_log_article` VALUES (10, 'Hello World!', '第一个程序', 1, 4, '2025-12-23 23:04:48', '2026-04-09 18:17:58');
INSERT INTO `sys_log_article` VALUES (11, '梁玉昆', 'lllllllllllllllllllyk', 1, 2, '2025-12-23 23:05:00', '2025-12-23 23:07:55');
INSERT INTO `sys_log_article` VALUES (12, '河南科技大学', 'HAUST', 10, 10, '2025-12-23 23:08:29', '2026-04-09 18:16:54');
INSERT INTO `sys_log_article` VALUES (13, '完成AI Agent模块的添加。', '今日任务：已完成AI Agent模块的添加。', 1, 5, '2026-04-09 17:13:12', '2026-04-09 18:18:14');
INSERT INTO `sys_log_article` VALUES (14, '测试AI Agent功能', '测试AI Agent功能，帮我完成本次日志。', 1, 9, '2026-04-09 17:18:04', '2026-04-10 17:57:41');
INSERT INTO `sys_log_article` VALUES (15, '第二次测试AI Agent功能', '今日任务：第二次测试AI Agent功能，帮我完成任务清单', 1, 4, '2026-04-09 17:28:52', '2026-04-09 18:18:06');
INSERT INTO `sys_log_article` VALUES (16, '第三次测试AI Agent功能', '【工作日志】第三次AI Agent功能测试\n\n今日完成对AI Agent核心功能的第三次迭代测试，重点验证了多轮对话理解、任务规划与自主执行模块的稳定性。测试覆盖了5个预设任务场景，包括信息查询、流程自动化与简单决策支持。本次测试中，Agent在任务拆解和上下文保持方面表现良好，但在复杂条件分支处理时出现一次逻辑延迟，已记录日志并标记为待优化项。\n\n下一步计划：\n1. 针对延迟问题排查底层推理逻辑；\n2. 增加边界用例测试；\n3. 准备集成环境部署验证。\n\n测试数据已归档，代码更新至GitHub对应分支。', 1, 9, '2026-04-09 17:33:56', '2026-04-09 18:18:04');
INSERT INTO `sys_log_article` VALUES (17, '今日测试AI Agent任务已完成', '今日AI Agent测试任务已全部完成。', 12, 2, '2026-04-09 17:48:47', '2026-04-09 17:48:51');
INSERT INTO `sys_log_article` VALUES (18, '今日系统测试任务已完成', '今日系统测试任务已全部完成。', 12, 5, '2026-04-09 17:51:06', '2026-04-10 17:57:26');
INSERT INTO `sys_log_article` VALUES (19, 'Redis模块添加成功', '今日任务：成功完成Redis模块的集成与部署。', 1, 2, '2026-04-09 18:18:54', '2026-04-09 18:20:49');
INSERT INTO `sys_log_article` VALUES (20, '完成系统更新', '已完成系统更新，并对今日任务内容进行了润色与优化。当前系统时间为：2023年10月27日 15:30。', 1, 3, '2026-04-09 19:16:33', '2026-04-09 19:17:09');
INSERT INTO `sys_log_article` VALUES (21, '2026.4.10今日第一次任务', '**2026年4月10日 工作日志**\n\n**任务一：系统功能测试**\n\n为保障测试工作的系统性与完整性，特制定以下功能测试清单，请参照执行：\n\n1.  **核心功能验证**\n    - 用户登录/注册流程\n    - 主要业务操作流程（如：数据提交、查询、修改、删除）\n    - 系统关键计算与逻辑判断\n\n2.  **界面与交互测试**\n    - 各页面布局与元素显示\n    - 导航、按钮、链接等交互响应\n    - 表单验证与错误提示信息\n\n3.  **数据与性能测试**\n    - 数据输入、存储与输出的准确性\n    - 典型操作场景下的响应速度\n    - 多用户并发操作稳定性（如适用）\n\n4.  **兼容性测试**\n    - 主流浏览器（Chrome, Firefox, Safari, Edge）兼容性\n    - 移动端设备适配情况（如适用）\n\n5.  **安全与权限测试**\n    - 用户权限控制（不同角色功能隔离）\n    - 敏感数据保护与传输安全\n    - 会话管理（如超时退出）\n\n**备注：** 测试过程中请详细记录测试步骤、实际结果、发现的问题及严重程度，并附上必要的截图或日志。', 1, 7, '2026-04-10 15:33:11', '2026-04-10 18:08:06');
INSERT INTO `sys_log_article` VALUES (22, '完成单元测试', '**今日任务清单：**\n\n1. **完成单元测试**\n   - 针对当前模块的核心功能编写并执行单元测试用例。\n   - 确保测试覆盖率达到预设标准（如分支覆盖、语句覆盖）。\n   - 修复测试过程中发现的代码缺陷或逻辑错误。\n   - 整理并提交测试报告，记录测试结果与问题跟踪。\n\n2. **代码审查与优化**\n   - 检查已编写代码是否符合项目编码规范。\n   - 对复杂或低效代码段进行重构，提升可读性与性能。\n   - 验证边界条件与异常处理机制的完整性。\n\n3. **文档更新**\n   - 根据代码变更同步更新相关技术文档或接口说明。\n   - 补充单元测试部分的说明与使用示例。\n\n4. **明日计划准备**\n   - 初步评估下一阶段开发任务，并列出可能的技术难点。\n   - 确保测试环境与开发环境配置一致，为后续工作做好准备。', 13, 3, '2026-04-10 18:09:25', '2026-04-10 18:13:29');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` int NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `real_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `district_id` int NULL DEFAULT NULL COMMENT '地区ID',
  `avatar_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像路径',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `qq` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'QQ',
  `is_admin` tinyint(1) NULL DEFAULT 0 COMMENT '是否管理员 0:否 1:是',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '状态 0:未审核 1:正常 2:禁用',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '123456', '系统管理员', NULL, NULL, 'https://xiaolei-log-system-oss-2026.oss-cn-beijing.aliyuncs.com/avatars/38ba6f74-7479-4343-8065-3e6933119d14.jpg', '16600000000', '16600000000@163.com', '3291864055', 1, 1, '2025-12-22 21:09:08', '2026-04-10 16:49:02');
INSERT INTO `sys_user` VALUES (11, 'aiagent', '123456', '小助手', NULL, NULL, NULL, '00000000000', '00000000000@163.com', '000000000', 0, 1, '2026-04-09 17:24:50', '2026-04-09 17:46:07');
INSERT INTO `sys_user` VALUES (13, 'xiaolei', '123456', '小雷', NULL, 1, 'https://xiaolei-log-system-oss-2026.oss-cn-beijing.aliyuncs.com/avatars/bfc780ae-496e-4a86-9164-6d200028b526.jpg', '166000000182', '166000000182@gmail.com', '000000000', 0, 1, '2026-04-10 18:06:47', '2026-04-10 18:11:11');

SET FOREIGN_KEY_CHECKS = 1;
