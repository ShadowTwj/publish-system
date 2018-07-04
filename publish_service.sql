/*
 Navicat Premium Data Transfer

 Source Server         : aliyun-publish-system
 Source Server Type    : MySQL
 Source Server Version : 50716
 Source Host           : 47.94.134.165
 Source Database       : publish_service

 Target Server Type    : MySQL
 Target Server Version : 50716
 File Encoding         : utf-8

 Date: 07/04/2018 15:52:34 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `environment`
-- ----------------------------
DROP TABLE IF EXISTS `environment`;
CREATE TABLE `environment` (
  `id` int(15) NOT NULL AUTO_INCREMENT,
  `unique_name` varchar(255) NOT NULL,
  `ip` varchar(255) NOT NULL,
  `user_name` varchar(64) NOT NULL,
  `password` varchar(64) NOT NULL,
  `status` int(4) DEFAULT NULL,
  `remark` text,
  `create_time` datetime DEFAULT NULL,
  `create_user` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_name` (`unique_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `project`
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `id` int(15) NOT NULL AUTO_INCREMENT,
  `unique_name` varchar(255) NOT NULL,
  `git` varchar(255) DEFAULT NULL,
  `module` varchar(255) DEFAULT NULL,
  `manager` varchar(255) DEFAULT NULL,
  `remark` text,
  `status` int(4) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(255) DEFAULT NULL,
  `time_restrict` int(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE,
  UNIQUE KEY `name` (`unique_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `publish_conf`
-- ----------------------------
DROP TABLE IF EXISTS `publish_conf`;
CREATE TABLE `publish_conf` (
  `id` int(15) NOT NULL AUTO_INCREMENT,
  `project_id` int(15) NOT NULL,
  `environment_id` int(15) DEFAULT NULL,
  `environment_unique_name` varchar(255) DEFAULT NULL,
  `tomcat_context_path` text,
  `replicas` int(10) DEFAULT NULL,
  `ports` varchar(255) DEFAULT NULL,
  `remark` text,
  `create_user` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_user` varchar(255) DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `publish_history`
-- ----------------------------
DROP TABLE IF EXISTS `publish_history`;
CREATE TABLE `publish_history` (
  `id` int(15) NOT NULL AUTO_INCREMENT,
  `publish_conf_id` int(15) NOT NULL,
  `project_id` int(15) NOT NULL,
  `project_name` varchar(255) DEFAULT NULL,
  `environment_id` int(15) NOT NULL,
  `environment_name` varchar(255) DEFAULT NULL,
  `branch` varchar(255) NOT NULL,
  `remark` text,
  `status` int(4) NOT NULL,
  `cost_time` bigint(64) DEFAULT NULL,
  `create_user` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_user` varchar(255) DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `publish_log`
-- ----------------------------
DROP TABLE IF EXISTS `publish_log`;
CREATE TABLE `publish_log` (
  `id` int(15) NOT NULL AUTO_INCREMENT,
  `project_id` int(15) NOT NULL,
  `publish_conf_id` int(15) NOT NULL,
  `publish_history_id` int(15) NOT NULL,
  `step_name` varchar(255) DEFAULT NULL,
  `step_order` int(15) DEFAULT NULL,
  `step_log` longtext,
  `remark` text,
  `status` int(4) DEFAULT NULL,
  `create_user` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_user` varchar(255) DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=116 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `recent_publish`
-- ----------------------------
DROP TABLE IF EXISTS `recent_publish`;
CREATE TABLE `recent_publish` (
  `id` int(15) NOT NULL AUTO_INCREMENT,
  `project_id` int(15) NOT NULL,
  `unique_name` varchar(255) NOT NULL,
  `user_id` int(15) NOT NULL,
  `account` varchar(255) NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `project_id_user_id_idx` (`project_id`,`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(64) NOT NULL,
  `password` varchar(64) NOT NULL,
  `nickname` varchar(32) DEFAULT NULL,
  `token` varchar(64) DEFAULT NULL,
  `creater` varchar(32) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_account_unique` (`account`) USING BTREE,
  KEY `user_id_foreign` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
