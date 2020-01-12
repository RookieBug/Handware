/*
 Navicat Premium Data Transfer

 Source Server         : TulLing
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : 47.103.22.58:3306
 Source Schema         : tulling

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 12/01/2020 19:48:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for register_info
-- ----------------------------
DROP TABLE IF EXISTS `register_info`;
CREATE TABLE `register_info`  (
  `card_id` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '卡号',
  `employer_id` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '员工号',
  `register_time` datetime(0) NOT NULL COMMENT '签到时间',
  `desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `state` tinyint(1) NOT NULL COMMENT '状态：未迟到：0，迟到：1',
  PRIMARY KEY (`card_id`, `register_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
