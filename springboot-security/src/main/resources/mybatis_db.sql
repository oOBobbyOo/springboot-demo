-- ==========================================
-- 1. 创建数据库
-- ==========================================
-- 使用 utf8mb4 支持 Emoji 表情，排序规则使用 utf8mb4_general_ci (或 utf8mb4_0900_ai_ci)
CREATE DATABASE IF NOT EXISTS `mybatis_db`
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_general_ci;

USE `mybatis_db`;

-- ==========================================
-- 2. 创建用户表 (sys_user)
-- ==========================================
DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
    `id` bigint(20) NOT NULL COMMENT '主键ID (雪花算法)',
    `username` varchar(50) NOT NULL COMMENT '用户名 (登录账号)',
    `password` varchar(100) NOT NULL COMMENT '密码 (BCrypt加密)',
    `nickname` varchar(50) DEFAULT NULL COMMENT '用户昵称',
    `email` varchar(100) DEFAULT NULL COMMENT '电子邮箱',
    `phone` varchar(20) DEFAULT NULL COMMENT '手机号码',
    `avatar` varchar(255) DEFAULT NULL COMMENT '头像URL',
    `gender` tinyint(1) DEFAULT '0' COMMENT '性别 (0:未知, 1:男, 2:女)',
    `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '账号状态 (0:禁用, 1:正常)',
    `login_ip` varchar(50) DEFAULT NULL COMMENT '最后登录IP',
    `login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
    `remark` varchar(500) DEFAULT NULL COMMENT '备注',

    -- 审计字段 (通常由 MyBatis-Plus / MPJ 等 ORM 框架自动填充)
    `create_by` bigint(20) DEFAULT NULL COMMENT '创建人ID (自动填充)',
    `update_by` bigint(20) DEFAULT NULL COMMENT '修改人ID (自动填充)',

    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除标识 (0:未删除, 1:已删除)',

    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`) USING BTREE COMMENT '用户名唯一索引',
    UNIQUE KEY `uk_phone` (`phone`) USING BTREE COMMENT '手机号码唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统用户表';
