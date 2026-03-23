-- 创建数据库
CREATE DATABASE IF NOT EXISTS log_management_system DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE log_management_system;

-- 1. 省份表 (s_province)
CREATE TABLE IF NOT EXISTS `s_province` (
  `province_id` INT AUTO_INCREMENT COMMENT '省份ID',
  `province_name` VARCHAR(50) NOT NULL COMMENT '省份名称',
  `date_created` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `date_updated` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`province_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='省份表';

-- 2. 城市表 (s_city)
CREATE TABLE IF NOT EXISTS `s_city` (
  `city_id` INT AUTO_INCREMENT COMMENT '城市ID',
  `city_name` VARCHAR(50) NOT NULL COMMENT '城市名称',
  `zip_code` VARCHAR(50) COMMENT '邮编',
  `province_id` INT NOT NULL COMMENT '所属省份ID',
  `date_created` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `date_updated` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`city_id`),
  INDEX `idx_province_id` (`province_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='城市表';

-- 3. 地区表 (s_district)
CREATE TABLE IF NOT EXISTS `s_district` (
  `district_id` INT AUTO_INCREMENT COMMENT '地区ID',
  `district_name` VARCHAR(50) NOT NULL COMMENT '地区名称',
  `city_id` INT NOT NULL COMMENT '所属城市ID',
  `date_created` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `date_updated` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`district_id`),
  INDEX `idx_city_id` (`city_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='地区表';

-- 4. 用户表 (sys_user) - 对应文档 logsystem
CREATE TABLE IF NOT EXISTS `sys_user` (
  `user_id` INT AUTO_INCREMENT COMMENT '用户ID',
  `username` VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
  `password` VARCHAR(100) NOT NULL COMMENT '密码',
  `real_name` VARCHAR(50) COMMENT '真实姓名',
  `birthday` DATE COMMENT '生日',
  `district_id` INT COMMENT '地区ID',
  `avatar_url` VARCHAR(500) COMMENT '头像路径',
  `phone` VARCHAR(20) COMMENT '电话',
  `email` VARCHAR(100) COMMENT '邮箱',
  `qq` VARCHAR(20) COMMENT 'QQ',
  `is_admin` TINYINT(1) DEFAULT 0 COMMENT '是否管理员 0:否 1:是',
  `status` TINYINT(1) DEFAULT 0 COMMENT '状态 0:未审核 1:正常 2:禁用',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 5. 日志表 (sys_log_article) - 对应文档 news
CREATE TABLE IF NOT EXISTS `sys_log_article` (
  `article_id` INT AUTO_INCREMENT COMMENT '日志ID',
  `title` VARCHAR(100) NOT NULL COMMENT '标题',
  `content` LONGTEXT COMMENT '内容',
  `user_id` INT NOT NULL COMMENT '发布用户ID',
  `read_count` INT DEFAULT 0 COMMENT '阅读量',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`article_id`),
  INDEX `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='日志文章表';

-- 6. 评论表 (sys_comment) - 对应文档 comment
CREATE TABLE IF NOT EXISTS `sys_comment` (
  `comment_id` INT AUTO_INCREMENT COMMENT '评论ID',
  `article_id` INT NOT NULL COMMENT '日志ID',
  `user_id` INT NOT NULL COMMENT '评论用户ID',
  `content` TEXT NOT NULL COMMENT '评论内容',
  `parent_id` INT DEFAULT 0 COMMENT '父评论ID (用于回复)',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
  PRIMARY KEY (`comment_id`),
  INDEX `idx_article_id` (`article_id`),
  INDEX `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评论表';

-- 初始化数据
-- 插入管理员
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `is_admin`, `status`) VALUES ('admin', '123456', '系统管理员', 1, 1);

-- 插入示例省份
INSERT INTO `s_province` (`province_name`) VALUES ('河南省'), ('北京市');
-- 插入示例城市
INSERT INTO `s_city` (`city_name`, `province_id`) VALUES ('洛阳市', 1), ('郑州市', 1), ('朝阳区', 2);
-- 插入示例地区
INSERT INTO `s_district` (`district_name`, `city_id`) VALUES ('洛龙区', 1), ('涧西区', 1), ('金水区', 2);
