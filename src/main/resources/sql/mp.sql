/*
Navicat MySQL Data Transfer

Source Server         : localhost-3306
Source Server Version : 50726
Source Host           : localhost:3306
Source Database       : mp

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-07-25 15:50:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(30) DEFAULT NULL COMMENT '姓名',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `manager_id` bigint(20) DEFAULT NULL COMMENT '直属上级id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `manager_fk` (`manager_id`),
  CONSTRAINT `manager_fk` FOREIGN KEY (`manager_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1087982257332887553', '大boss', '40', 'boss@baomidou.com', null, '2019-01-11 14:20:20');
INSERT INTO `user` VALUES ('1088248166370832385', '王天风', '25', 'wtf@baomidou.com', '1087982257332887553', '2019-02-05 11:12:22');
INSERT INTO `user` VALUES ('1088250446457389058', '李艺伟', '28', 'lyw@baomidou.com', '1088248166370832385', '2019-02-14 08:31:16');
INSERT INTO `user` VALUES ('1094590409767661570', '张雨琪', '31', 'zjq@baomidou.com', '1088248166370832385', '2019-01-14 09:15:15');
INSERT INTO `user` VALUES ('1094592041087729666', '刘红雨', '32', 'lhm@baomidou.com', '1088248166370832385', '2019-01-14 09:48:16');
