/*
 Navicat Premium Data Transfer

 Source Server         : black-hole
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : ifarmshop.com:3306
 Source Schema         : black-hole

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 21/09/2020 15:12:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for grade_score
-- ----------------------------
DROP TABLE IF EXISTS `grade_score`;
CREATE TABLE `grade_score` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `rule_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '规则id',
  `score` varchar(255) NOT NULL DEFAULT '' COMMENT '得分',
  `rank` int(11) NOT NULL DEFAULT '99999' COMMENT '排名',
  `city_id` bigint(20) DEFAULT NULL COMMENT '城市id',
  `data_version` int(11) NOT NULL DEFAULT '0' COMMENT '计算数据版本',
  `is_active` tinyint(4) NOT NULL DEFAULT '1' COMMENT '软删,1:有效,0:删除',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `drc_check_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '仅供DRC数据校验使用',
  `ezone_shard_info` bigint(20) DEFAULT NULL COMMENT 'shard info',
  PRIMARY KEY (`id`),
  KEY `ix_rule_id` (`rule_id`),
  KEY `ix_team_id_rule_id` (`team_id`,`rule_id`),
  KEY `ix_drc_check_time` (`drc_check_time`),
  KEY `ix_created_at` (`created_at`),
  KEY `ix_updated_at` (`updated_at`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='得分表';

SET FOREIGN_KEY_CHECKS = 1;
