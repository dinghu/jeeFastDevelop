/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50711
Source Host           : localhost:3306
Source Database       : xjj

Target Server Type    : MYSQL
Target Server Version : 50711
File Encoding         : 65001

Date: 2019-05-27 15:53:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_sec_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_sec_menu`;
CREATE TABLE `t_sec_menu` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TITLE` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(2000) DEFAULT NULL,
  `PARENT_ID` bigint(20) DEFAULT NULL,
  `PRIVILEGE_CODE` varchar(200) DEFAULT NULL,
  `URL` varchar(200) DEFAULT NULL,
  `order_sn` int(11) DEFAULT NULL,
  `ICON` varchar(100) DEFAULT NULL,
  `status` varchar(45) NOT NULL,
  `code` varchar(16) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `unique_code` (`code`),
  KEY `FK_psv09uu72jp7gwbch9sgm0pm6` (`PARENT_ID`),
  CONSTRAINT `FK_psv09uu72jp7gwbch9sgm0pm6` FOREIGN KEY (`PARENT_ID`) REFERENCES `t_sec_menu` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=169 DEFAULT CHARSET=utf8 COMMENT='菜单';

-- ----------------------------
-- Records of t_sec_menu
-- ----------------------------
INSERT INTO `t_sec_menu` VALUES ('153', '权限管理', '权限管理', null, '', null, '2', 'users', 'valid', '02');
INSERT INTO `t_sec_menu` VALUES ('155', '系统管理', '系统管理', null, '', null, '1', 'desktop', 'valid', '01');
INSERT INTO `t_sec_menu` VALUES ('156', '字典管理', '字典管理', '155', 'sys_dict', '/sys/dict/index', '1', null, 'valid', '0101');
INSERT INTO `t_sec_menu` VALUES ('157', '代码生成', '代码生成', '155', 'sys_code', '/sys/code/index', '2', null, 'valid', '0102');
INSERT INTO `t_sec_menu` VALUES ('158', '管理员管理', '管理员管理', '153', 'sec_manager', '/sec/manager/index', null, null, 'valid', '0202');
INSERT INTO `t_sec_menu` VALUES ('159', '用户管理', '用户管理', '153', 'sec_user', '/sec/user/index', null, null, 'valid', '0203');
INSERT INTO `t_sec_menu` VALUES ('161', '菜单管理', '菜单管理', '153', 'sec_menu', '/sec/menu/index', null, null, 'valid', '0204');
INSERT INTO `t_sec_menu` VALUES ('162', '角色管理', '菜单管理', '153', 'sec_role', '/sec/role/index', null, null, 'valid', '0201');
INSERT INTO `t_sec_menu` VALUES ('163', '文件管理', '文件管理', '155', 'sys_xfile', '/sys/xfile/index', null, null, 'valid', '0103');

-- ----------------------------
-- Table structure for t_sec_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sec_role`;
CREATE TABLE `t_sec_role` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TITLE` varchar(200) DEFAULT NULL COMMENT '名称',
  `CODE` varchar(200) DEFAULT NULL COMMENT '编码',
  `DESCRIPTION` varchar(2000) DEFAULT NULL COMMENT '描述',
  `status` varchar(20) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `uni_code` (`CODE`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Records of t_sec_role
-- ----------------------------
INSERT INTO `t_sec_role` VALUES ('4', '管理员', 'admin', '管理员', 'valid');
INSERT INTO `t_sec_role` VALUES ('5', '录入员', 'lulu', '录入员', 'valid');
INSERT INTO `t_sec_role` VALUES ('6', '教师', 'teacher', '教师', 'valid');

-- ----------------------------
-- Table structure for t_sec_role_privilege
-- ----------------------------
DROP TABLE IF EXISTS `t_sec_role_privilege`;
CREATE TABLE `t_sec_role_privilege` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ROLE_ID` bigint(20) DEFAULT NULL,
  `PRIVILEGE_TITLE` varchar(200) DEFAULT NULL,
  `PRIVILEGE_CODE` varchar(100) DEFAULT NULL,
  `FUNCTION_LIST` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_37slprxb3e2ygav598e3yeawt` (`ROLE_ID`),
  CONSTRAINT `FK_37slprxb3e2ygav598e3yeawt` FOREIGN KEY (`ROLE_ID`) REFERENCES `t_sec_role` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sec_role_privilege
-- ----------------------------
INSERT INTO `t_sec_role_privilege` VALUES ('14', '4', '代码生成管理', 'sys_code', 'generate');
INSERT INTO `t_sec_role_privilege` VALUES ('15', '4', '菜单管理', 'sec_menu', 'edit|delete|create|list');
INSERT INTO `t_sec_role_privilege` VALUES ('20', '4', '字典管理', 'sys_dict', 'edit|delete|create|list');
INSERT INTO `t_sec_role_privilege` VALUES ('26', '4', '管理员管理', 'sec_manager', 'delete|create|list|edit');
INSERT INTO `t_sec_role_privilege` VALUES ('27', '4', '角色管理', 'sec_role', 'edit|delete|create|list');
INSERT INTO `t_sec_role_privilege` VALUES ('28', '4', '用户管理', 'sec_user', 'edit|delete|create|list');
INSERT INTO `t_sec_role_privilege` VALUES ('29', '5', '菜单管理', 'sec_menu', 'edit|delete|create|list');
INSERT INTO `t_sec_role_privilege` VALUES ('30', '6', '角色管理', 'sec_role', 'edit|delete|create|list');
INSERT INTO `t_sec_role_privilege` VALUES ('31', '6', '文件管理', 'sys_xfile', 'edit|delete|create|list');
INSERT INTO `t_sec_role_privilege` VALUES ('32', '6', '用户管理', 'sec_user', 'edit|deletelist');

-- ----------------------------
-- Table structure for t_sec_user
-- ----------------------------
DROP TABLE IF EXISTS `t_sec_user`;
CREATE TABLE `t_sec_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `login_name` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `user_name` varchar(45) NOT NULL,
  `user_type` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `mobile` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `status` varchar(20) NOT NULL,
  `birthday` datetime DEFAULT NULL,
  `province` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_login_name` (`login_name`) USING BTREE,
  UNIQUE KEY `unique_email` (`email`),
  UNIQUE KEY `unique_mobile` (`mobile`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8 COMMENT='用户';

-- ----------------------------
-- Records of t_sec_user
-- ----------------------------
INSERT INTO `t_sec_user` VALUES ('52', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '系统管理员', 'admin', '443409972@qq.com', '18080064362', '', '2018-04-19 13:30:57', 'valid', '2019-05-27 00:00:00', null);
INSERT INTO `t_sec_user` VALUES ('54', 'zhanghejie', 'e10adc3949ba59abbe56e057f20f883e', '张合杰', 'user', '', '13366442927', null, '2018-04-27 16:16:38', 'valid', '2013-03-07 00:00:00', null);
INSERT INTO `t_sec_user` VALUES ('58', 'tutor', 'e10adc3949ba59abbe56e057f20f883e', '教师', 'admin', 'jlsdzhj@126.com', '13366442928', null, '2018-05-03 17:40:45', 'valid', '2018-05-03 00:00:00', null);
INSERT INTO `t_sec_user` VALUES ('74', 'xjjv92s2018-05-07 14:42:55', 'e10adc3949ba59abbe56e057f20f883e', '张三1', 'user', 'zhangsan@126.com', '13966442927', null, '2018-05-07 14:42:55', 'valid', null, null);
INSERT INTO `t_sec_user` VALUES ('75', 'xjj7w7R2018-05-07 14:42:55', 'e10adc3949ba59abbe56e057f20f883e', '张三2', 'user', 'zhangsan@127.com', null, null, '2018-05-07 14:42:55', 'valid', null, null);
INSERT INTO `t_sec_user` VALUES ('76', 'test3', 'e10adc3949ba59abbe56e057f20f883e', '张三3', 'user', 'zhangsan@128.com', null, null, '2018-05-07 14:42:55', 'valid', null, null);
INSERT INTO `t_sec_user` VALUES ('77', 'test4', 'e10adc3949ba59abbe56e057f20f883e', '张三4', 'user', 'zhangsan@129.com', null, null, '2018-05-07 14:42:55', 'valid', null, null);
INSERT INTO `t_sec_user` VALUES ('78', 'test5', 'e10adc3949ba59abbe56e057f20f883e', '张三5', 'user', 'zhangsan@130.com', null, null, '2018-05-07 14:42:55', 'valid', null, null);
INSERT INTO `t_sec_user` VALUES ('79', 'test6', 'e10adc3949ba59abbe56e057f20f883e', '张三6', 'user', 'zhangsan@131.com', null, null, '2018-05-07 14:42:55', 'valid', null, null);
INSERT INTO `t_sec_user` VALUES ('80', 'test7', 'e10adc3949ba59abbe56e057f20f883e', '张三7', 'user', 'zhangsan@132.com', null, null, '2018-05-07 14:42:55', 'valid', null, null);
INSERT INTO `t_sec_user` VALUES ('81', 'test8', 'e10adc3949ba59abbe56e057f20f883e', '张三8', 'user', 'zhangsan@133.com', null, null, '2018-05-07 14:42:55', 'valid', null, null);
INSERT INTO `t_sec_user` VALUES ('82', 'test9', 'e10adc3949ba59abbe56e057f20f883e', '张三9', 'user', 'zhangsan@134.com', null, null, '2018-05-07 14:42:55', 'valid', null, null);
INSERT INTO `t_sec_user` VALUES ('83', 'test10', 'e10adc3949ba59abbe56e057f20f883e', '张三10', 'user', 'zhangsan@135.com', null, null, '2018-05-07 14:42:55', 'valid', null, null);
INSERT INTO `t_sec_user` VALUES ('84', 'test11', 'e10adc3949ba59abbe56e057f20f883e', '张三11', 'user', 'zhangsan@136.com', null, null, '2018-05-07 14:42:55', 'valid', null, null);
INSERT INTO `t_sec_user` VALUES ('85', 'test12', 'e10adc3949ba59abbe56e057f20f883e', '张三12', 'user', 'zhangsan@137.com', null, null, '2018-05-07 14:42:55', 'valid', null, null);
INSERT INTO `t_sec_user` VALUES ('86', 'test13', 'e10adc3949ba59abbe56e057f20f883e', '张三13', 'user', 'zhangsan@138.com', null, null, '2018-05-07 14:42:55', 'valid', null, null);
INSERT INTO `t_sec_user` VALUES ('87', 'bbt', 'e10adc3949ba59abbe56e057f20f883e', 'bbt', 'user', 'bbt', 'bbt', null, '2018-05-08 14:29:20', 'valid', '2018-05-08 00:00:00', null);
INSERT INTO `t_sec_user` VALUES ('88', 'shandongren', 'e10adc3949ba59abbe56e057f20f883e', 'shandongren', 'user', 'shandongren', 'shandongren', null, '2018-05-08 14:50:41', 'valid', '2018-05-08 00:00:00', 'henan');

-- ----------------------------
-- Table structure for t_sec_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sec_user_role`;
CREATE TABLE `t_sec_user_role` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `USER_ID` bigint(20) NOT NULL,
  `ROLE_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='用户角色';

-- ----------------------------
-- Records of t_sec_user_role
-- ----------------------------
INSERT INTO `t_sec_user_role` VALUES ('1', '58', '6');
INSERT INTO `t_sec_user_role` VALUES ('2', '58', '4');

-- ----------------------------
-- Table structure for t_sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_dict`;
CREATE TABLE `t_sys_dict` (
  `id` int(8) unsigned NOT NULL AUTO_INCREMENT,
  `group_code` varchar(16) NOT NULL,
  `name` varchar(45) NOT NULL,
  `code` varchar(45) NOT NULL,
  `status` varchar(45) NOT NULL,
  `detail` varchar(512) DEFAULT NULL,
  `sn` int(8) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='字典';

-- ----------------------------
-- Records of t_sys_dict
-- ----------------------------
INSERT INTO `t_sys_dict` VALUES ('5', 'gender', '男', 'man', 'valid', null, '0');
INSERT INTO `t_sys_dict` VALUES ('6', 'gender', '女', 'woman', 'valid', null, null);
INSERT INTO `t_sys_dict` VALUES ('7', 'province', '山东', 'shandong', 'valid', null, '1');
INSERT INTO `t_sys_dict` VALUES ('8', 'province', '河南', 'henan', 'valid', null, '2');
INSERT INTO `t_sys_dict` VALUES ('9', 'province', '河北', 'hebei', 'valid', null, '3');

-- ----------------------------
-- Table structure for t_sys_xfile
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_xfile`;
CREATE TABLE `t_sys_xfile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `file_realname` varchar(200) NOT NULL,
  `file_path` varchar(200) NOT NULL,
  `file_title` varchar(200) NOT NULL,
  `url` varchar(200) DEFAULT NULL,
  `file_size` bigint(16) DEFAULT NULL,
  `user_id` bigint(16) DEFAULT NULL,
  `extension_name` varchar(16) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `is_deleted` char(8) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_xfile
-- ----------------------------
