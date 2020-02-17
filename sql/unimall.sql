/*
Navicat MySQL Data Transfer

Source Server         : ubuntu
Source Server Version : 50729
Source Host           : 192.168.8.229:3306
Source Database       : unimall

Target Server Type    : MYSQL
Target Server Version : 50729
File Encoding         : 65001

Date: 2020-02-17 21:49:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for unimall_address
-- ----------------------------
DROP TABLE IF EXISTS `unimall_address`;
CREATE TABLE `unimall_address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `province` varchar(60) NOT NULL,
  `city` varchar(60) NOT NULL,
  `county` varchar(60) NOT NULL,
  `address` varchar(240) NOT NULL,
  `default_address` int(11) NOT NULL DEFAULT '0',
  `user_id` bigint(20) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `consignee` varchar(100) NOT NULL,
  `gmt_create` datetime NOT NULL,
  `gmt_update` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of unimall_address
-- ----------------------------
INSERT INTO `unimall_address` VALUES ('1', '重庆', '重庆市', '南岸区', '有点小问题', '1', '16', '18584669549', '魏朝正', '2019-07-27 17:35:14', '2020-01-08 11:09:00');
INSERT INTO `unimall_address` VALUES ('2', '重庆', '重庆市', '南岸区', '香溪路2号', '1', '21', '18584669549', '魏朝正', '2019-08-04 11:21:39', '2019-08-04 11:21:39');
INSERT INTO `unimall_address` VALUES ('4', '河北省', '秦皇岛市', '北戴河区', '啊的吧', '1', '37', '15155955512', '5555', '2019-08-09 19:29:08', '2019-08-09 19:29:08');
INSERT INTO `unimall_address` VALUES ('5', '山西省', '长治市', '屯留县', '低调低调', '1', '36', '18874850464', '小涵涵', '2019-08-09 19:33:24', '2019-08-09 19:33:24');
INSERT INTO `unimall_address` VALUES ('6', '北京', '北京市', '东城区', '111', '0', '72', '15923232323', '111', '2019-08-10 11:48:09', '2019-08-10 17:16:57');
INSERT INTO `unimall_address` VALUES ('7', '内蒙古自治区', '包头市', '青山区', '的', '1', '72', '13625252525', '呃呃呃', '2019-08-10 17:17:19', '2019-08-10 17:17:19');
INSERT INTO `unimall_address` VALUES ('8', '浙江省', '嘉兴市', '嘉善县', '在的地方广告', '1', '96', '18825545528', '改改改改', '2019-08-10 23:14:20', '2019-08-10 23:14:20');
INSERT INTO `unimall_address` VALUES ('9', '内蒙古自治区', '包头市', '昆都仑区', '哈哈哈', '1', '106', '13073746895', '啊', '2019-08-11 11:03:58', '2019-08-11 11:03:58');
INSERT INTO `unimall_address` VALUES ('10', '山东省', '青岛市', '城阳区', '瑞阳路78号', '1', '104', '18661482845', '刘家金', '2019-08-11 14:21:46', '2019-08-11 14:21:46');
INSERT INTO `unimall_address` VALUES ('11', '河北省', '唐山市', '丰南区', '赶时间婚纱12318', '1', '141', '13100695218', '张三丰', '2019-08-12 13:09:37', '2019-08-12 13:09:42');
INSERT INTO `unimall_address` VALUES ('12', '山东省', '青岛市', '城阳区', '瑞阳路18', '1', '175', '18661485845', '刘家金', '2019-08-13 13:21:42', '2019-08-13 13:21:42');
INSERT INTO `unimall_address` VALUES ('13', '北京', '北京市', '崇文区', '对对对', '1', '66', '13676122744', '啊啊', '2019-08-14 09:41:04', '2019-08-14 09:41:04');
INSERT INTO `unimall_address` VALUES ('14', '江西省', '南昌市', '青山湖区', '中大青山湖东园6栋1单元3402', '1', '209', '17779152309', '张运康', '2019-08-16 13:18:10', '2019-08-16 13:18:10');
INSERT INTO `unimall_address` VALUES ('15', '江苏省', '苏州市', '昆山市', '张浦镇', '1', '260', '18021619628', '刘旭', '2019-08-22 09:31:52', '2019-08-22 09:31:52');
INSERT INTO `unimall_address` VALUES ('16', '浙江省', '温州市', '洞头县', '骨灰盒', '1', '265', '13856232563', '黑胡椒', '2019-08-22 13:55:59', '2019-08-22 13:55:59');
INSERT INTO `unimall_address` VALUES ('17', '重庆', '重庆市', '北碚区', '弄', '1', '22', '18883165287', '匡柏权', '2019-08-23 16:59:43', '2019-08-23 17:01:22');
INSERT INTO `unimall_address` VALUES ('18', '北京', '北京市', '西城区', '都城大厦1515', '1', '285', '13810819020', '李', '2019-08-25 07:46:49', '2019-08-25 07:46:49');
INSERT INTO `unimall_address` VALUES ('19', '山东省', '青岛市', '市北区', '测试', '1', '314', '17878787878', '玫瑰', '2019-08-29 16:27:44', '2019-08-29 16:27:44');
INSERT INTO `unimall_address` VALUES ('20', '河北省', '唐山市', '路北区', '44400', '1', '287', '18125555555', '刚刚', '2019-08-30 15:33:57', '2019-09-07 12:58:19');
INSERT INTO `unimall_address` VALUES ('21', '浙江省', '杭州市', '萧山区', '测试用', '1', '355', '18758186347', '测试', '2019-09-04 14:12:31', '2019-09-04 14:12:36');
INSERT INTO `unimall_address` VALUES ('22', '浙江省', '杭州市', '下城区', '102', '1', '367', '13576830382', '曾先生', '2019-09-07 10:09:00', '2019-09-07 10:09:00');
INSERT INTO `unimall_address` VALUES ('23', '内蒙古自治区', '鄂尔多斯市', '准格尔旗', '才', '0', '287', '18125546817', '111', '2019-09-07 12:36:20', '2019-09-07 12:36:20');
INSERT INTO `unimall_address` VALUES ('24', '北京', '北京市', '东城区', '啊啊啊', '1', '381', '13555556666', '啊啊啊', '2019-09-09 16:47:38', '2019-09-09 16:47:38');
INSERT INTO `unimall_address` VALUES ('25', '北京', '北京市', '东城区', '？我也不知道', '1', '423', '15968845617', '张三', '2019-09-15 10:46:19', '2019-09-15 10:46:19');

-- ----------------------------
-- Table structure for unimall_admin
-- ----------------------------
DROP TABLE IF EXISTS `unimall_admin`;
CREATE TABLE `unimall_admin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `realname` varchar(255) DEFAULT NULL,
  `avatar_url` varchar(255) DEFAULT NULL,
  `role_ids` varchar(255) DEFAULT NULL,
  `status` int(11) NOT NULL COMMENT '0.冻结 1.激活',
  `last_login_ip` varchar(255) DEFAULT NULL,
  `gmt_last_login` datetime NOT NULL,
  `gmt_update` datetime NOT NULL,
  `gmt_create` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`(30)) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of unimall_admin
-- ----------------------------
INSERT INTO `unimall_admin` VALUES ('1', 'admin', 'e7492a74a978b0b549941b3c85af9390', '18584669549', '魏朝正', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/ef1c5af8d1c34582a5bcda0d48ef9437.png', '[1]', '1', '127.0.0.1', '2019-04-08 22:12:04', '2019-04-17 12:23:21', '2019-04-08 22:12:12');
INSERT INTO `unimall_admin` VALUES ('22', 'kbq', '082d14061524bee4b01d8d5a61a699fb', '1234', '456123', null, '[10]', '1', '0.0.0.0', '1997-01-20 00:00:00', '2019-07-19 18:34:38', '2019-07-19 18:34:19');
INSERT INTO `unimall_admin` VALUES ('23', 'guest', '0c34aec774ad3e9d7df467301f58c52a', '17132358876', '游客', null, '[12]', '1', '0.0.0.0', '1997-01-20 00:00:00', '2019-08-11 13:20:55', '2019-08-11 12:34:29');

-- ----------------------------
-- Table structure for unimall_advertisement
-- ----------------------------
DROP TABLE IF EXISTS `unimall_advertisement`;
CREATE TABLE `unimall_advertisement` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ad_type` int(11) NOT NULL,
  `title` varchar(100) DEFAULT NULL,
  `url` varchar(100) NOT NULL,
  `img_url` varchar(100) NOT NULL,
  `status` int(11) NOT NULL,
  `color` varchar(30) DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_update` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of unimall_advertisement
-- ----------------------------
INSERT INTO `unimall_advertisement` VALUES ('32', '4', '环球狗粮', '/pages/product/list?tid=1036520', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/80090b06a6bf4e5e9d8a1c620c30f482.png', '1', 'rgb(163,148,208)', '2019-08-03 23:51:02', '2019-08-21 12:35:52');
INSERT INTO `unimall_advertisement` VALUES ('33', '4', '买好东西', '/pages/product/list?tid=1036527', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/5e41ecdd8ad94a779da794678edd6dda.png', '1', 'rgb(122,194,182)', '2019-08-03 23:52:12', '2019-08-21 12:30:48');
INSERT INTO `unimall_advertisement` VALUES ('34', '4', '团购标签', '/pages/groupshop/list', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/c553f844bda84cdc94675f4e5577009b.png', '1', 'rgb(214,142,158)', '2019-08-03 23:53:21', '2019-08-21 12:30:56');
INSERT INTO `unimall_advertisement` VALUES ('35', '4', '狗狗头像', '/pages/product/list?tid=1036529', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/9f6ccac8ad8741a6b580201a75c32bf5.png', '1', 'rgb(121,178,213)', '2019-08-03 23:53:43', '2019-08-21 12:31:01');
INSERT INTO `unimall_advertisement` VALUES ('36', '4', '哈哈哈哈', '/pages/product/list?tid=1036527', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/549e9b927fb449bfa6531041f21687f0.png', '1', 'rgb(214,156,128)', '2019-08-03 23:54:05', '2019-08-21 12:31:04');
INSERT INTO `unimall_advertisement` VALUES ('38', '2', '狗狗分类精选', '/pages/product/list?tid=1036525', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/767258cfa0e9407382e01f3fd702c9fe.jpg', '1', 'rgb(237,216,111)', '2019-08-03 23:55:43', '2019-08-21 12:31:10');
INSERT INTO `unimall_advertisement` VALUES ('39', '2', '猫猫分类精选', '/pages/product/list?tid=1036527', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/d290570d32874c87b9ef3ce949ea8c35.jpg', '1', 'rgb(221,214,211)', '2019-08-03 23:56:00', '2019-08-21 12:36:13');
INSERT INTO `unimall_advertisement` VALUES ('42', '1', '轮播1', '/pages/product/list?tid=1036520', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/055b8278d1b842a4bec6f386705244c2.jpg', '1', 'rgb(196,180,162)', '2019-08-21 12:32:04', '2019-08-21 12:32:04');
INSERT INTO `unimall_advertisement` VALUES ('43', '1', '轮播2', '/pages/product/detail?id=1236773', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/253829df1e2447269fcbcd2fae4d045c.jpg', '1', 'rgb(152,198,212)', '2019-08-21 12:32:29', '2019-08-21 12:32:29');
INSERT INTO `unimall_advertisement` VALUES ('44', '1', '轮播3', '/pages/product/list?tid=1036520', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/bb69cece2c8d4a5495c07da664c72d4c.jpg', '1', 'rgb(223,223,222)', '2019-08-21 12:37:06', '2019-08-21 12:37:06');

-- ----------------------------
-- Table structure for unimall_appraise
-- ----------------------------
DROP TABLE IF EXISTS `unimall_appraise`;
CREATE TABLE `unimall_appraise` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `spu_id` bigint(20) DEFAULT NULL,
  `sku_id` bigint(20) DEFAULT NULL,
  `order_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `content` varchar(1000) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_update` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of unimall_appraise
-- ----------------------------

-- ----------------------------
-- Table structure for unimall_cart
-- ----------------------------
DROP TABLE IF EXISTS `unimall_cart`;
CREATE TABLE `unimall_cart` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sku_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `num` int(11) NOT NULL,
  `gmt_update` datetime NOT NULL,
  `gmt_create` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of unimall_cart
-- ----------------------------

-- ----------------------------
-- Table structure for unimall_category
-- ----------------------------
DROP TABLE IF EXISTS `unimall_category`;
CREATE TABLE `unimall_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `parent_id` bigint(20) NOT NULL,
  `icon_url` varchar(255) DEFAULT NULL,
  `pic_url` varchar(255) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `gmt_update` datetime NOT NULL,
  `gmt_create` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1036544 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of unimall_category
-- ----------------------------
INSERT INTO `unimall_category` VALUES ('1036517', '狗狗', '0', null, null, '0', '2019-08-21 12:22:23', '2019-08-21 12:22:23');
INSERT INTO `unimall_category` VALUES ('1036518', '猫猫', '0', null, null, '0', '2019-08-21 12:22:28', '2019-08-21 12:22:28');
INSERT INTO `unimall_category` VALUES ('1036519', '狗粮', '1036517', null, null, '1', '2019-08-21 12:22:40', '2019-08-21 12:22:40');
INSERT INTO `unimall_category` VALUES ('1036520', '进口狗粮', '1036519', null, 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/3e29a454ba344311af4dc56b3f3d3451.jpg', '2', '2019-08-21 12:23:07', '2019-08-21 12:23:07');
INSERT INTO `unimall_category` VALUES ('1036521', '国产狗粮', '1036519', null, 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/f93cfc43446a4f708da7adfe735a0b89.jpg', '2', '2019-08-21 12:23:20', '2019-08-21 12:23:20');
INSERT INTO `unimall_category` VALUES ('1036522', '处方狗粮', '1036519', null, 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eb7a841fc8714d75a0a19e747fdd201b.jpg', '2', '2019-08-21 12:23:34', '2019-08-21 12:23:34');
INSERT INTO `unimall_category` VALUES ('1036523', '零食', '1036517', null, null, '1', '2019-08-21 12:24:24', '2019-08-21 12:24:24');
INSERT INTO `unimall_category` VALUES ('1036524', '肉类零食', '1036523', null, 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/b80c780e31714dc38bfc2255f7c5d491.jpg', '2', '2019-08-21 12:24:41', '2019-08-21 12:24:41');
INSERT INTO `unimall_category` VALUES ('1036525', '磨牙零食', '1036523', null, 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/df12703bcdde413a9fd68387134c3dfc.jpg', '2', '2019-08-21 12:25:11', '2019-08-21 12:25:11');
INSERT INTO `unimall_category` VALUES ('1036526', '猫粮', '1036518', null, null, '1', '2019-08-21 12:26:00', '2019-08-21 12:26:00');
INSERT INTO `unimall_category` VALUES ('1036527', '进口猫粮', '1036526', null, 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/bb042c1d7ef14bc69f7d63a7e448f602.jpg', '2', '2019-08-21 12:26:21', '2019-08-21 12:26:21');
INSERT INTO `unimall_category` VALUES ('1036528', '国产猫粮', '1036526', null, 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/cfbb9e4490f6406ea7a90c0d03cb8053.jpg', '2', '2019-08-21 12:26:37', '2019-08-21 12:26:37');
INSERT INTO `unimall_category` VALUES ('1036529', '处方猫粮', '1036526', null, 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/646f718f4c2241aebfbbdcb9fcb97332.jpg', '2', '2019-08-21 12:26:59', '2019-08-21 12:26:59');
INSERT INTO `unimall_category` VALUES ('1036530', '清洁', '0', null, null, '0', '2019-08-21 12:27:04', '2019-08-21 12:27:04');
INSERT INTO `unimall_category` VALUES ('1036531', '猫猫', '1036530', null, null, '1', '2019-08-21 12:27:12', '2019-08-21 12:27:12');
INSERT INTO `unimall_category` VALUES ('1036532', '厕所', '1036531', null, 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/76247590454c435291e5033628dbc71a.jpg', '2', '2019-08-21 12:27:26', '2019-08-21 12:27:26');
INSERT INTO `unimall_category` VALUES ('1036533', '猫砂', '1036531', null, 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/024a2f840bb44188aa4c9659ecdee19e.jpg', '2', '2019-08-21 12:27:37', '2019-08-21 12:27:37');
INSERT INTO `unimall_category` VALUES ('1036534', '授权', '0', null, null, '0', '2019-08-21 12:33:18', '2019-08-21 12:33:18');
INSERT INTO `unimall_category` VALUES ('1036535', '商业授权', '1036534', null, null, '1', '2019-08-21 12:33:30', '2019-08-21 12:33:30');
INSERT INTO `unimall_category` VALUES ('1036536', '永久授权', '1036535', null, 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/b68e51d469524632b03a248408b4b6c1.jpeg', '2', '2019-08-21 12:34:13', '2019-08-21 12:34:13');
INSERT INTO `unimall_category` VALUES ('1036537', '111', '0', null, null, '0', '2019-09-05 16:00:01', '2019-09-05 16:00:01');
INSERT INTO `unimall_category` VALUES ('1036538', '狗粮狗粮狗粮狗粮狗粮', '1036517', null, null, '1', '2019-08-21 12:22:40', '2019-08-21 12:22:40');
INSERT INTO `unimall_category` VALUES ('1036539', '狗粮', '1036517', null, null, '1', '2019-08-21 12:22:40', '2019-08-21 12:22:40');
INSERT INTO `unimall_category` VALUES ('1036540', '狗粮', '1036517', null, null, '1', '2019-08-21 12:22:40', '2019-08-21 12:22:40');
INSERT INTO `unimall_category` VALUES ('1036541', '狗粮', '1036517', null, null, '1', '2019-08-21 12:22:40', '2019-08-21 12:22:40');
INSERT INTO `unimall_category` VALUES ('1036542', '狗粮', '1036517', null, null, '1', '2019-08-21 12:22:40', '2019-08-21 12:22:40');
INSERT INTO `unimall_category` VALUES ('1036543', '狗粮', '1036517', null, null, '1', '2019-08-21 12:22:40', '2019-08-21 12:22:40');

-- ----------------------------
-- Table structure for unimall_collect
-- ----------------------------
DROP TABLE IF EXISTS `unimall_collect`;
CREATE TABLE `unimall_collect` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `spu_id` bigint(20) DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_update` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=116 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of unimall_collect
-- ----------------------------
INSERT INTO `unimall_collect` VALUES ('1', '2', '22', '2019-07-05 04:15:07', '2019-07-05 04:15:10');
INSERT INTO `unimall_collect` VALUES ('2', '2', '23', '2019-07-05 04:15:07', '2019-07-05 04:15:10');
INSERT INTO `unimall_collect` VALUES ('3', '2', '24', '2019-07-05 04:15:07', '2019-07-05 04:15:10');
INSERT INTO `unimall_collect` VALUES ('4', '2', '25', '2019-07-05 04:15:07', '2019-07-05 04:15:10');
INSERT INTO `unimall_collect` VALUES ('5', '2', '26', '2019-07-05 04:15:07', '2019-07-05 04:15:10');
INSERT INTO `unimall_collect` VALUES ('6', '2', '27', '2019-07-05 04:15:07', '2019-07-05 04:15:10');
INSERT INTO `unimall_collect` VALUES ('7', '2', '28', '2019-07-05 04:15:07', '2019-07-05 04:15:10');
INSERT INTO `unimall_collect` VALUES ('8', '2', '29', '2019-07-05 04:15:07', '2019-07-05 04:15:10');
INSERT INTO `unimall_collect` VALUES ('9', '2', '30', '2019-07-05 04:15:07', '2019-07-05 04:15:10');
INSERT INTO `unimall_collect` VALUES ('10', '2', '31', '2019-07-05 04:15:07', '2019-07-05 04:15:10');
INSERT INTO `unimall_collect` VALUES ('11', '2', '32', '2019-07-05 04:15:07', '2019-07-05 04:15:10');
INSERT INTO `unimall_collect` VALUES ('12', '2', '33', '2019-07-05 04:15:07', '2019-07-05 04:15:10');
INSERT INTO `unimall_collect` VALUES ('13', '2', '34', '2019-07-05 04:15:07', '2019-07-05 04:15:10');
INSERT INTO `unimall_collect` VALUES ('14', '2', '35', '2019-07-05 04:15:07', '2019-07-05 04:15:10');
INSERT INTO `unimall_collect` VALUES ('15', '2', '36', '2019-07-05 04:15:07', '2019-07-05 04:15:10');
INSERT INTO `unimall_collect` VALUES ('16', '2', '37', '2019-07-05 04:15:07', '2019-07-05 04:15:10');
INSERT INTO `unimall_collect` VALUES ('17', '2', '38', '2019-07-05 04:15:07', '2019-07-05 04:15:10');
INSERT INTO `unimall_collect` VALUES ('18', '2', '39', '2019-07-05 04:15:07', '2019-07-05 04:15:10');
INSERT INTO `unimall_collect` VALUES ('19', '2', '40', '2019-07-05 04:15:07', '2019-07-05 04:15:10');
INSERT INTO `unimall_collect` VALUES ('20', '2', '41', '2019-07-05 04:15:07', '2019-07-05 04:15:10');
INSERT INTO `unimall_collect` VALUES ('21', '2', '42', '2019-07-05 04:15:07', '2019-07-05 04:15:10');
INSERT INTO `unimall_collect` VALUES ('22', '2', '43', '2019-07-05 04:15:07', '2019-07-05 04:15:10');
INSERT INTO `unimall_collect` VALUES ('23', '2', '44', '2019-07-05 04:15:07', '2019-07-05 04:15:10');
INSERT INTO `unimall_collect` VALUES ('24', '2', '45', '2019-07-05 04:15:07', '2019-07-05 04:15:10');
INSERT INTO `unimall_collect` VALUES ('25', '2', '46', '2019-07-05 04:15:07', '2019-07-05 04:15:10');
INSERT INTO `unimall_collect` VALUES ('26', '2', '47', '2019-07-05 04:15:07', '2019-07-05 04:15:10');
INSERT INTO `unimall_collect` VALUES ('31', '3', '43', '2019-07-08 18:37:22', '2019-07-08 18:37:22');
INSERT INTO `unimall_collect` VALUES ('32', '3', '28', '2019-07-08 21:40:47', '2019-07-08 21:40:47');
INSERT INTO `unimall_collect` VALUES ('33', '14', '28', '2019-07-14 15:28:28', '2019-07-14 15:28:28');
INSERT INTO `unimall_collect` VALUES ('43', '22', '1236771', '2019-08-04 01:47:38', '2019-08-04 01:47:38');
INSERT INTO `unimall_collect` VALUES ('44', '21', '1236769', '2019-08-04 20:16:24', '2019-08-04 20:16:24');
INSERT INTO `unimall_collect` VALUES ('45', '22', '1236773', '2019-08-06 22:23:33', '2019-08-06 22:23:33');
INSERT INTO `unimall_collect` VALUES ('46', '30', '1236768', '2019-08-09 18:26:33', '2019-08-09 18:26:33');
INSERT INTO `unimall_collect` VALUES ('48', '46', '1236775', '2019-08-09 22:51:07', '2019-08-09 22:51:07');
INSERT INTO `unimall_collect` VALUES ('49', '64', '1236771', '2019-08-10 09:48:34', '2019-08-10 09:48:34');
INSERT INTO `unimall_collect` VALUES ('55', '72', '1236768', '2019-08-10 14:45:26', '2019-08-10 14:45:26');
INSERT INTO `unimall_collect` VALUES ('56', '101', '1236768', '2019-08-11 09:22:07', '2019-08-11 09:22:07');
INSERT INTO `unimall_collect` VALUES ('57', '106', '1236774', '2019-08-11 11:02:01', '2019-08-11 11:02:01');
INSERT INTO `unimall_collect` VALUES ('58', '122', '1236777', '2019-08-11 23:52:28', '2019-08-11 23:52:28');
INSERT INTO `unimall_collect` VALUES ('60', '152', '1236772', '2019-08-12 17:50:25', '2019-08-12 17:50:25');
INSERT INTO `unimall_collect` VALUES ('62', '156', '1236775', '2019-08-12 18:52:17', '2019-08-12 18:52:17');
INSERT INTO `unimall_collect` VALUES ('63', '179', '1236768', '2019-08-13 14:29:00', '2019-08-13 14:29:00');
INSERT INTO `unimall_collect` VALUES ('64', '190', '1236774', '2019-08-14 14:36:48', '2019-08-14 14:36:48');
INSERT INTO `unimall_collect` VALUES ('66', '194', '1236773', '2019-08-14 18:32:16', '2019-08-14 18:32:16');
INSERT INTO `unimall_collect` VALUES ('67', '240', '1236774', '2019-08-18 16:13:18', '2019-08-18 16:13:18');
INSERT INTO `unimall_collect` VALUES ('69', '84', '1236774', '2019-08-20 11:24:39', '2019-08-20 11:24:39');
INSERT INTO `unimall_collect` VALUES ('70', '267', '1236773', '2019-08-22 16:45:57', '2019-08-22 16:45:57');
INSERT INTO `unimall_collect` VALUES ('72', '105', '1236773', '2019-08-25 09:50:25', '2019-08-25 09:50:25');
INSERT INTO `unimall_collect` VALUES ('74', '305', '1236769', '2019-08-28 21:38:27', '2019-08-28 21:38:27');
INSERT INTO `unimall_collect` VALUES ('75', '305', '1236774', '2019-08-28 21:44:24', '2019-08-28 21:44:24');
INSERT INTO `unimall_collect` VALUES ('77', '314', '1236771', '2019-08-29 16:28:10', '2019-08-29 16:28:10');
INSERT INTO `unimall_collect` VALUES ('78', '321', '1236770', '2019-08-31 16:07:06', '2019-08-31 16:07:06');
INSERT INTO `unimall_collect` VALUES ('79', '259', '1236773', '2019-09-02 02:12:43', '2019-09-02 02:12:43');
INSERT INTO `unimall_collect` VALUES ('80', '340', '1236772', '2019-09-02 11:11:08', '2019-09-02 11:11:08');
INSERT INTO `unimall_collect` VALUES ('82', '351', '1236777', '2019-09-03 15:01:32', '2019-09-03 15:01:32');
INSERT INTO `unimall_collect` VALUES ('88', '367', '1236768', '2019-09-07 09:39:04', '2019-09-07 09:39:04');
INSERT INTO `unimall_collect` VALUES ('99', '381', '1236771', '2019-09-09 16:46:26', '2019-09-09 16:46:26');
INSERT INTO `unimall_collect` VALUES ('100', '387', '1236769', '2019-09-10 10:24:07', '2019-09-10 10:24:07');
INSERT INTO `unimall_collect` VALUES ('101', '388', '1236768', '2019-09-10 10:41:25', '2019-09-10 10:41:25');
INSERT INTO `unimall_collect` VALUES ('102', '21', '1236777', '2019-09-12 01:26:31', '2019-09-12 01:26:31');
INSERT INTO `unimall_collect` VALUES ('108', '405', '1236773', '2019-09-12 10:19:48', '2019-09-12 10:19:48');
INSERT INTO `unimall_collect` VALUES ('109', '415', '1236774', '2019-09-13 15:04:33', '2019-09-13 15:04:33');
INSERT INTO `unimall_collect` VALUES ('110', '65', '1236773', '2019-09-14 10:39:09', '2019-09-14 10:39:09');
INSERT INTO `unimall_collect` VALUES ('112', '420', '1236768', '2019-09-14 17:32:35', '2019-09-14 17:32:35');
INSERT INTO `unimall_collect` VALUES ('113', '421', '1236773', '2019-09-14 20:12:36', '2019-09-14 20:12:36');
INSERT INTO `unimall_collect` VALUES ('115', '447', '1236772', '2019-09-18 15:53:34', '2019-09-18 15:53:34');

-- ----------------------------
-- Table structure for unimall_config
-- ----------------------------
DROP TABLE IF EXISTS `unimall_config`;
CREATE TABLE `unimall_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `key_word` varchar(60) DEFAULT NULL,
  `value_worth` varchar(100) DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_update` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of unimall_config
-- ----------------------------
INSERT INTO `unimall_config` VALUES ('1', 'title', '5', '2019-07-20 11:23:37', '2019-07-20 11:23:37');
INSERT INTO `unimall_config` VALUES ('2', 'logoUrl', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/3a20237b18c44356891890f6e2260c03.jpg', '2019-07-20 11:23:37', '2019-07-20 11:23:37');
INSERT INTO `unimall_config` VALUES ('3', 'description', '6', '2019-07-20 11:23:37', '2019-07-20 11:23:37');
INSERT INTO `unimall_config` VALUES ('4', 'address', '7', '2019-07-20 11:23:37', '2019-07-20 11:23:37');
INSERT INTO `unimall_config` VALUES ('5', 'showType', '2', '2019-07-20 11:23:37', '2019-07-20 11:23:37');

-- ----------------------------
-- Table structure for unimall_coupon
-- ----------------------------
DROP TABLE IF EXISTS `unimall_coupon`;
CREATE TABLE `unimall_coupon` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `type` int(11) NOT NULL DEFAULT '1' COMMENT '使用类型，如满减',
  `description` varchar(255) DEFAULT NULL,
  `total` int(11) NOT NULL,
  `surplus` int(11) NOT NULL COMMENT '会员类型0:非会员1:会员2:全部',
  `limit` int(11) NOT NULL,
  `discount` int(11) NOT NULL COMMENT '减少金额',
  `min` int(11) NOT NULL COMMENT '最低消费金额',
  `status` int(11) NOT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  `days` int(11) DEFAULT NULL COMMENT '过期天数',
  `gmt_start` datetime DEFAULT NULL COMMENT '领取开始时间',
  `gmt_end` datetime DEFAULT NULL COMMENT '领取/使用结束时间',
  `gmt_update` datetime NOT NULL,
  `gmt_create` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of unimall_coupon
-- ----------------------------
INSERT INTO `unimall_coupon` VALUES ('2', '测试非会员是否可见', '1', '测试用', '10', '10', '1', '100', '10000', '1', null, '10', null, null, '2019-10-22 11:40:59', '2019-10-22 11:40:59');
INSERT INTO `unimall_coupon` VALUES ('3', '测试普通用户优惠券', '1', '测试用', '1000', '999', '1', '100', '100', '1', null, '10', null, null, '2019-10-23 13:04:29', '2019-10-23 13:04:29');
INSERT INTO `unimall_coupon` VALUES ('7', '小米鼠标满减券', '1', '满2-1', '3', '2', '1', '100', '200', '1', '3', '1', null, null, '2019-10-23 16:14:16', '2019-10-23 16:14:16');
INSERT INTO `unimall_coupon` VALUES ('8', '132', '1', '00', '10', '8', '2', '100', '200', '1', null, null, '2019-10-23 02:00:00', '2019-10-24 16:23:42', '2019-10-23 16:23:51', '2019-10-23 16:23:51');
INSERT INTO `unimall_coupon` VALUES ('9', '测试', '1', null, '1000', '997', '10', '100', '100', '1', null, '1', null, null, '2019-10-23 16:58:48', '2019-10-23 16:58:48');
INSERT INTO `unimall_coupon` VALUES ('10', '测试单一类目满减券', '1', null, '100', '99', '10', '100', '100', '1', '3', '10', null, null, '2019-10-23 17:16:48', '2019-10-23 17:16:48');
INSERT INTO `unimall_coupon` VALUES ('11', '我来看看', '1', null, '123', '123', '12', '1200', '1300', '1', null, '12', null, null, '2019-11-26 12:35:00', '2019-11-26 12:35:00');
INSERT INTO `unimall_coupon` VALUES ('12', '111', '1', '1', '10', '10', '1', '200', '300', '1', '1036537', '1', null, null, '2019-11-26 12:36:09', '2019-11-26 12:36:09');
INSERT INTO `unimall_coupon` VALUES ('13', '22', '1', '3', '3', '3', '3', '300', '300', '1', null, '3', null, null, '2019-11-26 12:36:26', '2019-11-26 12:36:26');
INSERT INTO `unimall_coupon` VALUES ('14', '3', '1', '3', '30', '30', '3', '3000', '3000', '1', '1036534', '3', null, null, '2019-11-26 12:36:42', '2019-11-26 12:36:42');
INSERT INTO `unimall_coupon` VALUES ('15', '4', '1', '4', '4', '4', '4', '400', '400', '1', null, '4', null, null, '2019-11-26 12:36:50', '2019-11-26 12:36:50');

-- ----------------------------
-- Table structure for unimall_footprint
-- ----------------------------
DROP TABLE IF EXISTS `unimall_footprint`;
CREATE TABLE `unimall_footprint` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `spu_id` bigint(20) NOT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_update` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=560 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of unimall_footprint
-- ----------------------------
INSERT INTO `unimall_footprint` VALUES ('1', '2', '28', '2019-05-08 01:46:24', '2019-05-08 01:46:27');
INSERT INTO `unimall_footprint` VALUES ('5', '3', '42', '2019-07-08 17:02:10', '2019-07-08 18:14:47');
INSERT INTO `unimall_footprint` VALUES ('6', '3', '44', '2019-07-08 17:22:01', '2019-07-08 17:22:01');
INSERT INTO `unimall_footprint` VALUES ('8', '3', '40', '2019-07-08 17:22:18', '2019-07-08 17:22:24');
INSERT INTO `unimall_footprint` VALUES ('9', '3', '36', '2019-07-08 17:37:21', '2019-07-08 17:37:21');
INSERT INTO `unimall_footprint` VALUES ('10', '3', '28', '2019-07-08 18:05:34', '2019-07-11 16:05:45');
INSERT INTO `unimall_footprint` VALUES ('11', '3', '43', '2019-07-08 18:37:21', '2019-07-08 18:37:21');
INSERT INTO `unimall_footprint` VALUES ('12', '3', '45', '2019-07-08 21:31:32', '2019-07-08 21:31:32');
INSERT INTO `unimall_footprint` VALUES ('13', '14', '28', '2019-07-13 11:23:02', '2019-07-14 16:02:13');
INSERT INTO `unimall_footprint` VALUES ('14', '14', '22', '2019-07-13 14:59:58', '2019-07-14 16:03:29');
INSERT INTO `unimall_footprint` VALUES ('15', '14', '35', '2019-07-14 16:03:19', '2019-07-14 16:03:19');
INSERT INTO `unimall_footprint` VALUES ('16', '14', '24', '2019-07-14 16:03:35', '2019-07-14 16:03:35');
INSERT INTO `unimall_footprint` VALUES ('17', '15', '28', '2019-07-17 17:35:29', '2019-07-17 19:03:48');
INSERT INTO `unimall_footprint` VALUES ('18', '16', '28', '2019-07-17 18:10:33', '2019-07-21 19:05:22');
INSERT INTO `unimall_footprint` VALUES ('19', '17', '28', '2019-07-17 19:04:46', '2019-07-18 11:15:18');
INSERT INTO `unimall_footprint` VALUES ('20', '18', '28', '2019-07-18 12:17:25', '2019-07-18 15:00:30');
INSERT INTO `unimall_footprint` VALUES ('21', '1', '28', '2019-07-18 15:42:35', '2019-07-18 17:46:53');
INSERT INTO `unimall_footprint` VALUES ('22', '16', '29', '2019-07-19 18:34:24', '2019-07-19 18:34:24');
INSERT INTO `unimall_footprint` VALUES ('23', '19', '28', '2019-07-21 18:02:14', '2019-07-21 18:02:14');
INSERT INTO `unimall_footprint` VALUES ('24', '16', '1234194', '2019-07-23 11:47:11', '2019-07-27 15:47:10');
INSERT INTO `unimall_footprint` VALUES ('25', '16', '1234192', '2019-07-23 11:47:17', '2019-07-27 15:47:05');
INSERT INTO `unimall_footprint` VALUES ('26', '16', '1234195', '2019-07-23 11:47:23', '2019-07-23 11:47:23');
INSERT INTO `unimall_footprint` VALUES ('27', '16', '1234197', '2019-07-23 11:47:28', '2019-07-23 11:47:28');
INSERT INTO `unimall_footprint` VALUES ('28', '16', '1234176', '2019-07-23 11:47:34', '2019-07-27 16:33:40');
INSERT INTO `unimall_footprint` VALUES ('29', '16', '1234177', '2019-07-23 11:47:37', '2019-07-27 16:33:42');
INSERT INTO `unimall_footprint` VALUES ('30', '16', '1234179', '2019-07-23 11:49:05', '2019-07-23 19:02:45');
INSERT INTO `unimall_footprint` VALUES ('31', '16', '1234181', '2019-07-27 15:45:15', '2019-07-27 15:45:15');
INSERT INTO `unimall_footprint` VALUES ('32', '21', '1236768', '2019-08-01 23:11:43', '2019-11-25 15:38:01');
INSERT INTO `unimall_footprint` VALUES ('33', '21', '1236769', '2019-08-01 23:31:36', '2019-11-22 17:07:24');
INSERT INTO `unimall_footprint` VALUES ('34', '22', '1236768', '2019-08-02 00:19:31', '2019-08-23 18:05:06');
INSERT INTO `unimall_footprint` VALUES ('35', '22', '1236773', '2019-08-02 00:20:05', '2019-08-23 18:05:00');
INSERT INTO `unimall_footprint` VALUES ('36', '22', '1236769', '2019-08-02 00:21:19', '2019-08-23 18:05:03');
INSERT INTO `unimall_footprint` VALUES ('37', '22', '1236775', '2019-08-04 01:23:49', '2019-08-23 18:04:30');
INSERT INTO `unimall_footprint` VALUES ('38', '22', '1236770', '2019-08-04 01:25:25', '2019-08-06 22:23:42');
INSERT INTO `unimall_footprint` VALUES ('39', '21', '1236770', '2019-08-04 01:28:28', '2019-11-22 16:23:08');
INSERT INTO `unimall_footprint` VALUES ('40', '21', '1236775', '2019-08-04 01:28:39', '2019-12-30 11:20:20');
INSERT INTO `unimall_footprint` VALUES ('41', '22', '1236771', '2019-08-04 01:31:15', '2019-08-23 18:05:14');
INSERT INTO `unimall_footprint` VALUES ('42', '22', '1236774', '2019-08-04 01:39:17', '2019-08-23 18:04:56');
INSERT INTO `unimall_footprint` VALUES ('43', '22', '1236772', '2019-08-04 01:49:52', '2019-08-23 18:04:52');
INSERT INTO `unimall_footprint` VALUES ('44', '21', '1236773', '2019-08-04 11:29:49', '2019-11-22 16:19:37');
INSERT INTO `unimall_footprint` VALUES ('45', '1', '1236770', '2019-08-04 13:57:22', '2019-08-04 13:57:22');
INSERT INTO `unimall_footprint` VALUES ('46', '21', '1236771', '2019-08-05 11:57:29', '2019-11-25 16:04:32');
INSERT INTO `unimall_footprint` VALUES ('47', '21', '1236774', '2019-08-05 12:09:23', '2019-11-25 16:04:26');
INSERT INTO `unimall_footprint` VALUES ('48', '21', '1236772', '2019-08-05 17:55:14', '2019-11-06 15:50:45');
INSERT INTO `unimall_footprint` VALUES ('49', '21', '1236776', '2019-08-05 17:55:22', '2020-02-15 20:16:53');
INSERT INTO `unimall_footprint` VALUES ('50', '23', '1236768', '2019-08-08 19:26:27', '2019-08-08 19:26:34');
INSERT INTO `unimall_footprint` VALUES ('51', '23', '1236775', '2019-08-08 19:26:44', '2019-08-08 19:26:44');
INSERT INTO `unimall_footprint` VALUES ('52', '24', '1236769', '2019-08-09 17:38:41', '2019-08-09 17:38:41');
INSERT INTO `unimall_footprint` VALUES ('53', '27', '1236771', '2019-08-09 17:45:49', '2019-08-09 17:45:49');
INSERT INTO `unimall_footprint` VALUES ('54', '27', '1236772', '2019-08-09 17:46:07', '2019-08-09 17:46:07');
INSERT INTO `unimall_footprint` VALUES ('55', '30', '1236768', '2019-08-09 18:26:31', '2019-08-09 18:27:00');
INSERT INTO `unimall_footprint` VALUES ('56', '30', '1236773', '2019-08-09 18:27:08', '2019-08-09 18:27:08');
INSERT INTO `unimall_footprint` VALUES ('57', '30', '1236774', '2019-08-09 18:27:11', '2019-08-09 18:27:11');
INSERT INTO `unimall_footprint` VALUES ('58', '30', '1236770', '2019-08-09 18:27:15', '2019-08-09 18:27:15');
INSERT INTO `unimall_footprint` VALUES ('59', '36', '1236774', '2019-08-09 19:23:20', '2019-08-09 19:23:20');
INSERT INTO `unimall_footprint` VALUES ('60', '37', '1236769', '2019-08-09 19:29:26', '2019-08-09 19:29:26');
INSERT INTO `unimall_footprint` VALUES ('61', '36', '1236769', '2019-08-09 19:32:36', '2019-08-09 19:32:36');
INSERT INTO `unimall_footprint` VALUES ('62', '36', '1236770', '2019-08-09 19:34:07', '2019-08-09 19:34:16');
INSERT INTO `unimall_footprint` VALUES ('63', '38', '1236768', '2019-08-09 20:14:42', '2019-08-09 20:14:42');
INSERT INTO `unimall_footprint` VALUES ('64', '41', '1236769', '2019-08-09 21:17:08', '2019-08-09 21:17:08');
INSERT INTO `unimall_footprint` VALUES ('65', '41', '1236768', '2019-08-09 21:17:13', '2019-08-09 21:17:13');
INSERT INTO `unimall_footprint` VALUES ('66', '43', '1236775', '2019-08-09 21:25:34', '2019-08-09 21:25:34');
INSERT INTO `unimall_footprint` VALUES ('67', '44', '1236769', '2019-08-09 21:31:16', '2019-08-09 21:31:16');
INSERT INTO `unimall_footprint` VALUES ('68', '44', '1236768', '2019-08-09 21:33:27', '2019-08-09 21:33:36');
INSERT INTO `unimall_footprint` VALUES ('69', '44', '1236772', '2019-08-09 21:33:54', '2019-08-09 21:33:54');
INSERT INTO `unimall_footprint` VALUES ('70', '45', '1236768', '2019-08-09 21:54:01', '2019-08-09 21:54:01');
INSERT INTO `unimall_footprint` VALUES ('71', '46', '1236769', '2019-08-09 22:12:02', '2019-08-09 22:12:02');
INSERT INTO `unimall_footprint` VALUES ('72', '46', '1236771', '2019-08-09 22:12:25', '2019-08-09 22:12:25');
INSERT INTO `unimall_footprint` VALUES ('73', '46', '1236774', '2019-08-09 22:12:54', '2019-08-09 22:20:26');
INSERT INTO `unimall_footprint` VALUES ('74', '46', '1236770', '2019-08-09 22:20:30', '2019-08-09 22:20:30');
INSERT INTO `unimall_footprint` VALUES ('75', '46', '1236772', '2019-08-09 22:21:07', '2019-08-09 22:21:07');
INSERT INTO `unimall_footprint` VALUES ('76', '47', '1236774', '2019-08-09 22:27:00', '2019-08-09 22:27:00');
INSERT INTO `unimall_footprint` VALUES ('77', '47', '1236775', '2019-08-09 22:27:29', '2019-08-09 22:27:29');
INSERT INTO `unimall_footprint` VALUES ('78', '46', '1236775', '2019-08-09 22:50:58', '2019-08-09 22:50:58');
INSERT INTO `unimall_footprint` VALUES ('79', '48', '1236769', '2019-08-09 23:05:52', '2019-08-09 23:05:52');
INSERT INTO `unimall_footprint` VALUES ('80', '49', '1236768', '2019-08-09 23:16:10', '2019-08-09 23:16:55');
INSERT INTO `unimall_footprint` VALUES ('81', '50', '1236775', '2019-08-09 23:24:11', '2019-08-09 23:24:11');
INSERT INTO `unimall_footprint` VALUES ('82', '52', '1236775', '2019-08-09 23:39:46', '2019-08-09 23:39:46');
INSERT INTO `unimall_footprint` VALUES ('83', '53', '1236775', '2019-08-09 23:45:00', '2019-08-10 21:54:41');
INSERT INTO `unimall_footprint` VALUES ('84', '53', '1236774', '2019-08-09 23:46:44', '2019-08-09 23:46:44');
INSERT INTO `unimall_footprint` VALUES ('85', '53', '1236770', '2019-08-09 23:46:54', '2019-08-10 21:54:08');
INSERT INTO `unimall_footprint` VALUES ('86', '53', '1236768', '2019-08-09 23:47:04', '2019-08-09 23:48:42');
INSERT INTO `unimall_footprint` VALUES ('87', '55', '1236768', '2019-08-10 00:58:20', '2019-08-10 01:27:45');
INSERT INTO `unimall_footprint` VALUES ('88', '55', '1236773', '2019-08-10 00:58:47', '2019-08-10 00:58:47');
INSERT INTO `unimall_footprint` VALUES ('89', '55', '1236774', '2019-08-10 00:58:50', '2019-08-10 00:58:50');
INSERT INTO `unimall_footprint` VALUES ('90', '55', '1236775', '2019-08-10 01:00:34', '2019-08-10 01:00:34');
INSERT INTO `unimall_footprint` VALUES ('91', '56', '1236771', '2019-08-10 05:49:50', '2019-08-10 05:49:50');
INSERT INTO `unimall_footprint` VALUES ('92', '58', '1236772', '2019-08-10 06:25:33', '2019-08-10 06:25:33');
INSERT INTO `unimall_footprint` VALUES ('93', '59', '1236770', '2019-08-10 08:56:40', '2019-08-10 08:57:03');
INSERT INTO `unimall_footprint` VALUES ('94', '59', '1236768', '2019-08-10 08:56:48', '2019-08-10 08:56:48');
INSERT INTO `unimall_footprint` VALUES ('95', '60', '1236768', '2019-08-10 09:15:00', '2019-08-10 09:15:00');
INSERT INTO `unimall_footprint` VALUES ('96', '62', '1236772', '2019-08-10 09:24:57', '2019-08-10 09:24:57');
INSERT INTO `unimall_footprint` VALUES ('97', '64', '1236771', '2019-08-10 09:48:08', '2019-08-10 09:48:08');
INSERT INTO `unimall_footprint` VALUES ('98', '64', '1236775', '2019-08-10 09:49:33', '2019-08-10 09:49:33');
INSERT INTO `unimall_footprint` VALUES ('99', '64', '1236772', '2019-08-10 09:50:45', '2019-08-10 09:50:45');
INSERT INTO `unimall_footprint` VALUES ('100', '64', '1236773', '2019-08-10 09:51:03', '2019-08-10 09:51:03');
INSERT INTO `unimall_footprint` VALUES ('101', '64', '1236768', '2019-08-10 09:51:21', '2019-08-10 09:51:21');
INSERT INTO `unimall_footprint` VALUES ('102', '65', '1236773', '2019-08-10 10:28:37', '2019-09-14 10:38:54');
INSERT INTO `unimall_footprint` VALUES ('103', '65', '1236774', '2019-08-10 10:28:41', '2019-08-10 10:28:41');
INSERT INTO `unimall_footprint` VALUES ('104', '65', '1236770', '2019-08-10 10:28:56', '2019-08-10 10:28:56');
INSERT INTO `unimall_footprint` VALUES ('105', '67', '1236772', '2019-08-10 10:52:45', '2019-08-10 10:52:45');
INSERT INTO `unimall_footprint` VALUES ('106', '68', '1236768', '2019-08-10 10:55:42', '2019-08-10 10:55:42');
INSERT INTO `unimall_footprint` VALUES ('107', '68', '1236769', '2019-08-10 10:55:53', '2019-08-10 10:55:53');
INSERT INTO `unimall_footprint` VALUES ('108', '68', '1236770', '2019-08-10 10:56:06', '2019-08-10 10:56:06');
INSERT INTO `unimall_footprint` VALUES ('109', '71', '1236775', '2019-08-10 11:13:52', '2019-08-10 13:31:12');
INSERT INTO `unimall_footprint` VALUES ('110', '72', '1236772', '2019-08-10 11:48:23', '2019-08-10 14:46:07');
INSERT INTO `unimall_footprint` VALUES ('111', '72', '1236773', '2019-08-10 11:49:09', '2019-08-10 11:57:29');
INSERT INTO `unimall_footprint` VALUES ('112', '75', '1236768', '2019-08-10 13:20:22', '2019-08-10 13:20:22');
INSERT INTO `unimall_footprint` VALUES ('113', '75', '1236775', '2019-08-10 13:20:25', '2019-09-04 14:46:00');
INSERT INTO `unimall_footprint` VALUES ('114', '78', '1236775', '2019-08-10 14:26:08', '2019-08-10 14:26:08');
INSERT INTO `unimall_footprint` VALUES ('115', '79', '1236768', '2019-08-10 14:29:36', '2019-08-10 14:29:36');
INSERT INTO `unimall_footprint` VALUES ('116', '80', '1236775', '2019-08-10 14:32:34', '2019-08-10 14:32:34');
INSERT INTO `unimall_footprint` VALUES ('117', '72', '1236768', '2019-08-10 14:44:47', '2019-08-12 02:26:55');
INSERT INTO `unimall_footprint` VALUES ('118', '72', '1236770', '2019-08-10 14:46:17', '2019-08-10 14:46:17');
INSERT INTO `unimall_footprint` VALUES ('119', '72', '1236775', '2019-08-10 14:47:35', '2019-08-10 14:47:35');
INSERT INTO `unimall_footprint` VALUES ('120', '81', '1236768', '2019-08-10 14:55:14', '2019-08-10 14:55:14');
INSERT INTO `unimall_footprint` VALUES ('121', '83', '1236775', '2019-08-10 16:00:33', '2019-08-10 16:00:33');
INSERT INTO `unimall_footprint` VALUES ('122', '87', '1236768', '2019-08-10 17:04:34', '2019-08-17 16:54:10');
INSERT INTO `unimall_footprint` VALUES ('123', '37', '1236773', '2019-08-10 19:26:17', '2019-08-10 19:26:17');
INSERT INTO `unimall_footprint` VALUES ('124', '90', '1236770', '2019-08-10 20:12:06', '2019-08-10 20:12:06');
INSERT INTO `unimall_footprint` VALUES ('125', '90', '1236768', '2019-08-10 20:12:08', '2019-08-10 20:12:08');
INSERT INTO `unimall_footprint` VALUES ('126', '90', '1236769', '2019-08-10 20:12:50', '2019-08-10 20:12:50');
INSERT INTO `unimall_footprint` VALUES ('127', '94', '1236773', '2019-08-10 22:39:09', '2019-08-10 22:39:09');
INSERT INTO `unimall_footprint` VALUES ('128', '95', '1236770', '2019-08-10 23:06:42', '2019-08-10 23:06:42');
INSERT INTO `unimall_footprint` VALUES ('129', '96', '1236773', '2019-08-10 23:14:51', '2019-08-10 23:14:51');
INSERT INTO `unimall_footprint` VALUES ('130', '97', '1236774', '2019-08-11 00:34:36', '2019-08-11 00:34:36');
INSERT INTO `unimall_footprint` VALUES ('131', '98', '1236769', '2019-08-11 02:58:15', '2019-08-11 02:58:15');
INSERT INTO `unimall_footprint` VALUES ('132', '101', '1236775', '2019-08-11 09:22:26', '2019-08-11 09:22:26');
INSERT INTO `unimall_footprint` VALUES ('133', '102', '1236770', '2019-08-11 09:49:55', '2019-08-11 09:50:57');
INSERT INTO `unimall_footprint` VALUES ('134', '103', '1236775', '2019-08-11 09:53:25', '2019-08-11 09:53:25');
INSERT INTO `unimall_footprint` VALUES ('135', '103', '1236774', '2019-08-11 09:53:37', '2019-08-11 09:53:37');
INSERT INTO `unimall_footprint` VALUES ('136', '105', '1236768', '2019-08-11 10:41:29', '2019-09-05 08:16:57');
INSERT INTO `unimall_footprint` VALUES ('137', '105', '1236770', '2019-08-11 10:42:02', '2019-08-24 23:38:37');
INSERT INTO `unimall_footprint` VALUES ('138', '105', '1236775', '2019-08-11 10:42:20', '2019-08-24 22:21:36');
INSERT INTO `unimall_footprint` VALUES ('139', '106', '1236774', '2019-08-11 11:01:55', '2019-08-20 22:10:54');
INSERT INTO `unimall_footprint` VALUES ('140', '107', '1236772', '2019-08-11 11:03:51', '2019-08-11 11:03:51');
INSERT INTO `unimall_footprint` VALUES ('141', '107', '1236774', '2019-08-11 11:03:55', '2019-08-11 11:05:02');
INSERT INTO `unimall_footprint` VALUES ('142', '107', '1236775', '2019-08-11 11:03:58', '2019-08-11 11:03:58');
INSERT INTO `unimall_footprint` VALUES ('143', '107', '1236768', '2019-08-11 11:04:04', '2019-08-11 11:04:04');
INSERT INTO `unimall_footprint` VALUES ('144', '107', '1236769', '2019-08-11 11:04:07', '2019-08-11 11:04:07');
INSERT INTO `unimall_footprint` VALUES ('145', '107', '1236770', '2019-08-11 11:04:13', '2019-08-11 11:07:15');
INSERT INTO `unimall_footprint` VALUES ('146', '108', '1236769', '2019-08-11 11:18:14', '2019-08-11 11:18:50');
INSERT INTO `unimall_footprint` VALUES ('147', '108', '1236770', '2019-08-11 11:18:32', '2019-08-11 11:18:32');
INSERT INTO `unimall_footprint` VALUES ('148', '109', '1236768', '2019-08-11 12:31:21', '2019-08-11 12:31:21');
INSERT INTO `unimall_footprint` VALUES ('149', '110', '1236768', '2019-08-11 12:38:11', '2019-08-11 12:38:11');
INSERT INTO `unimall_footprint` VALUES ('150', '21', '1236777', '2019-08-11 13:17:23', '2019-11-25 16:00:16');
INSERT INTO `unimall_footprint` VALUES ('151', '113', '1236777', '2019-08-11 14:45:33', '2019-08-11 14:45:33');
INSERT INTO `unimall_footprint` VALUES ('152', '113', '1236768', '2019-08-11 14:46:17', '2019-08-11 14:46:46');
INSERT INTO `unimall_footprint` VALUES ('153', '113', '1236775', '2019-08-11 14:46:57', '2019-08-11 14:46:57');
INSERT INTO `unimall_footprint` VALUES ('154', '115', '1236774', '2019-08-11 15:31:35', '2019-08-11 16:02:14');
INSERT INTO `unimall_footprint` VALUES ('155', '116', '1236774', '2019-08-11 15:33:50', '2019-08-11 15:33:50');
INSERT INTO `unimall_footprint` VALUES ('156', '117', '1236769', '2019-08-11 17:01:21', '2019-08-11 17:01:21');
INSERT INTO `unimall_footprint` VALUES ('157', '118', '1236768', '2019-08-11 17:10:54', '2019-09-03 14:08:03');
INSERT INTO `unimall_footprint` VALUES ('158', '118', '1236774', '2019-08-11 17:11:43', '2019-08-11 17:11:43');
INSERT INTO `unimall_footprint` VALUES ('159', '119', '1236768', '2019-08-11 17:48:07', '2019-08-11 17:48:07');
INSERT INTO `unimall_footprint` VALUES ('160', '120', '1236774', '2019-08-11 21:55:17', '2019-08-11 21:55:17');
INSERT INTO `unimall_footprint` VALUES ('161', '121', '1236777', '2019-08-11 22:45:20', '2019-08-11 22:45:39');
INSERT INTO `unimall_footprint` VALUES ('162', '122', '1236777', '2019-08-11 23:52:05', '2019-08-11 23:52:05');
INSERT INTO `unimall_footprint` VALUES ('163', '124', '1236777', '2019-08-12 00:44:34', '2019-08-12 00:44:34');
INSERT INTO `unimall_footprint` VALUES ('164', '125', '1236772', '2019-08-12 01:09:01', '2019-08-12 01:09:01');
INSERT INTO `unimall_footprint` VALUES ('165', '72', '1236777', '2019-08-12 02:45:19', '2019-08-12 02:45:19');
INSERT INTO `unimall_footprint` VALUES ('166', '127', '1236771', '2019-08-12 08:12:35', '2019-08-12 08:12:35');
INSERT INTO `unimall_footprint` VALUES ('167', '127', '1236768', '2019-08-12 08:14:38', '2019-08-12 08:14:38');
INSERT INTO `unimall_footprint` VALUES ('168', '129', '1236769', '2019-08-12 09:09:04', '2019-08-12 09:09:04');
INSERT INTO `unimall_footprint` VALUES ('169', '130', '1236772', '2019-08-12 09:13:34', '2019-08-12 09:13:34');
INSERT INTO `unimall_footprint` VALUES ('170', '130', '1236769', '2019-08-12 09:13:50', '2019-08-12 09:13:50');
INSERT INTO `unimall_footprint` VALUES ('171', '131', '1236768', '2019-08-12 09:15:16', '2019-08-12 09:15:16');
INSERT INTO `unimall_footprint` VALUES ('172', '132', '1236777', '2019-08-12 09:28:50', '2019-08-12 09:29:05');
INSERT INTO `unimall_footprint` VALUES ('173', '135', '1236777', '2019-08-12 09:43:56', '2019-08-12 09:44:13');
INSERT INTO `unimall_footprint` VALUES ('174', '134', '1236771', '2019-08-12 09:44:17', '2019-08-12 09:44:17');
INSERT INTO `unimall_footprint` VALUES ('175', '137', '1236769', '2019-08-12 10:14:44', '2019-08-12 10:14:44');
INSERT INTO `unimall_footprint` VALUES ('176', '137', '1236771', '2019-08-12 10:14:56', '2019-08-12 10:14:56');
INSERT INTO `unimall_footprint` VALUES ('177', '138', '1236774', '2019-08-12 10:25:02', '2019-08-12 10:25:02');
INSERT INTO `unimall_footprint` VALUES ('178', '141', '1236775', '2019-08-12 13:10:33', '2019-08-12 13:10:33');
INSERT INTO `unimall_footprint` VALUES ('179', '141', '1236776', '2019-08-12 13:11:51', '2019-08-12 13:11:51');
INSERT INTO `unimall_footprint` VALUES ('180', '142', '1236773', '2019-08-12 13:57:46', '2019-08-12 13:58:12');
INSERT INTO `unimall_footprint` VALUES ('181', '142', '1236770', '2019-08-12 13:58:31', '2019-08-12 13:58:31');
INSERT INTO `unimall_footprint` VALUES ('182', '144', '1236773', '2019-08-12 14:26:27', '2019-08-12 14:26:27');
INSERT INTO `unimall_footprint` VALUES ('183', '144', '1236768', '2019-08-12 14:26:58', '2019-08-12 14:26:58');
INSERT INTO `unimall_footprint` VALUES ('184', '147', '1236774', '2019-08-12 15:26:32', '2019-08-12 15:26:32');
INSERT INTO `unimall_footprint` VALUES ('185', '147', '1236775', '2019-08-12 15:26:37', '2019-08-12 15:26:37');
INSERT INTO `unimall_footprint` VALUES ('186', '148', '1236768', '2019-08-12 15:41:57', '2019-08-12 15:41:57');
INSERT INTO `unimall_footprint` VALUES ('187', '149', '1236769', '2019-08-12 15:45:10', '2019-08-12 15:45:10');
INSERT INTO `unimall_footprint` VALUES ('188', '149', '1236775', '2019-08-12 15:45:22', '2019-08-12 15:45:22');
INSERT INTO `unimall_footprint` VALUES ('189', '149', '1236773', '2019-08-12 15:45:30', '2019-08-12 15:45:30');
INSERT INTO `unimall_footprint` VALUES ('190', '149', '1236771', '2019-08-12 15:45:37', '2019-08-12 15:45:37');
INSERT INTO `unimall_footprint` VALUES ('191', '150', '1236768', '2019-08-12 16:51:31', '2019-08-12 16:51:31');
INSERT INTO `unimall_footprint` VALUES ('192', '150', '1236775', '2019-08-12 17:36:05', '2019-08-12 17:36:05');
INSERT INTO `unimall_footprint` VALUES ('193', '152', '1236772', '2019-08-12 17:48:24', '2019-08-12 17:50:22');
INSERT INTO `unimall_footprint` VALUES ('194', '155', '1236777', '2019-08-12 18:44:28', '2019-08-12 18:44:28');
INSERT INTO `unimall_footprint` VALUES ('195', '155', '1236772', '2019-08-12 18:44:41', '2019-08-12 18:44:41');
INSERT INTO `unimall_footprint` VALUES ('196', '155', '1236768', '2019-08-12 18:45:04', '2019-08-12 18:45:04');
INSERT INTO `unimall_footprint` VALUES ('197', '156', '1236774', '2019-08-12 18:52:21', '2019-08-12 18:52:21');
INSERT INTO `unimall_footprint` VALUES ('198', '160', '1236770', '2019-08-12 20:34:15', '2019-08-12 20:34:15');
INSERT INTO `unimall_footprint` VALUES ('199', '161', '1236774', '2019-08-12 20:45:05', '2019-08-12 20:45:05');
INSERT INTO `unimall_footprint` VALUES ('200', '162', '1236768', '2019-08-13 08:43:20', '2019-08-13 08:44:22');
INSERT INTO `unimall_footprint` VALUES ('201', '166', '1236770', '2019-08-13 09:24:32', '2019-08-13 09:24:32');
INSERT INTO `unimall_footprint` VALUES ('202', '169', '1236768', '2019-08-13 10:55:09', '2019-08-13 10:55:09');
INSERT INTO `unimall_footprint` VALUES ('203', '170', '1236777', '2019-08-13 10:58:58', '2019-08-13 10:58:58');
INSERT INTO `unimall_footprint` VALUES ('204', '170', '1236775', '2019-08-13 11:06:40', '2019-08-13 11:06:40');
INSERT INTO `unimall_footprint` VALUES ('205', '171', '1236775', '2019-08-13 11:36:57', '2019-08-13 11:43:12');
INSERT INTO `unimall_footprint` VALUES ('206', '171', '1236769', '2019-08-13 11:37:52', '2019-08-14 16:21:42');
INSERT INTO `unimall_footprint` VALUES ('207', '173', '1236775', '2019-08-13 12:03:38', '2019-08-13 12:03:38');
INSERT INTO `unimall_footprint` VALUES ('208', '174', '1236769', '2019-08-13 13:13:02', '2019-08-13 13:13:02');
INSERT INTO `unimall_footprint` VALUES ('209', '177', '1236772', '2019-08-13 14:02:12', '2019-08-13 14:02:12');
INSERT INTO `unimall_footprint` VALUES ('210', '178', '1236775', '2019-08-13 14:20:35', '2019-08-13 14:20:35');
INSERT INTO `unimall_footprint` VALUES ('211', '179', '1236768', '2019-08-13 14:28:55', '2019-08-13 14:29:05');
INSERT INTO `unimall_footprint` VALUES ('212', '180', '1236775', '2019-08-13 15:09:37', '2019-08-13 15:09:37');
INSERT INTO `unimall_footprint` VALUES ('213', '180', '1236769', '2019-08-13 15:10:34', '2019-08-13 15:10:34');
INSERT INTO `unimall_footprint` VALUES ('214', '181', '1236775', '2019-08-13 15:31:24', '2019-08-13 15:32:32');
INSERT INTO `unimall_footprint` VALUES ('215', '183', '1236777', '2019-08-13 17:36:02', '2019-08-13 17:36:02');
INSERT INTO `unimall_footprint` VALUES ('216', '185', '1236771', '2019-08-13 18:16:08', '2019-08-13 18:16:08');
INSERT INTO `unimall_footprint` VALUES ('217', '185', '1236773', '2019-08-13 18:17:03', '2019-08-13 18:23:05');
INSERT INTO `unimall_footprint` VALUES ('218', '187', '1236773', '2019-08-14 11:25:48', '2019-08-14 11:25:48');
INSERT INTO `unimall_footprint` VALUES ('219', '188', '1236777', '2019-08-14 11:35:49', '2019-08-14 11:35:49');
INSERT INTO `unimall_footprint` VALUES ('220', '190', '1236769', '2019-08-14 14:35:47', '2019-08-14 14:35:47');
INSERT INTO `unimall_footprint` VALUES ('221', '190', '1236775', '2019-08-14 14:36:02', '2019-08-14 14:36:02');
INSERT INTO `unimall_footprint` VALUES ('222', '190', '1236774', '2019-08-14 14:36:46', '2019-08-14 14:36:46');
INSERT INTO `unimall_footprint` VALUES ('223', '191', '1236768', '2019-08-14 14:52:33', '2019-08-14 14:53:21');
INSERT INTO `unimall_footprint` VALUES ('224', '191', '1236772', '2019-08-14 14:53:07', '2019-08-14 14:53:13');
INSERT INTO `unimall_footprint` VALUES ('225', '192', '1236774', '2019-08-14 15:58:24', '2019-08-14 15:58:24');
INSERT INTO `unimall_footprint` VALUES ('226', '171', '1236768', '2019-08-14 16:21:04', '2019-08-14 16:21:49');
INSERT INTO `unimall_footprint` VALUES ('227', '194', '1236775', '2019-08-14 18:31:54', '2019-08-14 18:31:54');
INSERT INTO `unimall_footprint` VALUES ('228', '194', '1236773', '2019-08-14 18:32:10', '2019-08-14 18:32:15');
INSERT INTO `unimall_footprint` VALUES ('229', '43', '1236777', '2019-08-14 22:56:33', '2019-08-14 22:56:33');
INSERT INTO `unimall_footprint` VALUES ('230', '43', '1236769', '2019-08-14 23:09:46', '2019-08-14 23:09:51');
INSERT INTO `unimall_footprint` VALUES ('231', '43', '1236768', '2019-08-14 23:09:58', '2019-08-14 23:09:58');
INSERT INTO `unimall_footprint` VALUES ('232', '195', '1236772', '2019-08-15 04:19:22', '2019-08-30 23:40:05');
INSERT INTO `unimall_footprint` VALUES ('233', '196', '1236768', '2019-08-15 16:48:36', '2019-08-15 16:48:36');
INSERT INTO `unimall_footprint` VALUES ('234', '197', '1236770', '2019-08-15 17:05:49', '2019-08-15 17:52:05');
INSERT INTO `unimall_footprint` VALUES ('235', '199', '1236768', '2019-08-15 20:13:05', '2019-08-15 20:13:05');
INSERT INTO `unimall_footprint` VALUES ('236', '199', '1236769', '2019-08-15 20:13:11', '2019-08-15 20:13:11');
INSERT INTO `unimall_footprint` VALUES ('237', '200', '1236773', '2019-08-15 20:42:14', '2019-08-15 20:42:14');
INSERT INTO `unimall_footprint` VALUES ('238', '202', '1236774', '2019-08-15 23:24:17', '2019-08-15 23:24:17');
INSERT INTO `unimall_footprint` VALUES ('239', '203', '1236777', '2019-08-16 08:19:53', '2019-08-21 14:32:36');
INSERT INTO `unimall_footprint` VALUES ('240', '203', '1236769', '2019-08-16 08:21:17', '2019-08-16 08:21:17');
INSERT INTO `unimall_footprint` VALUES ('241', '203', '1236775', '2019-08-16 08:29:41', '2019-08-16 08:29:41');
INSERT INTO `unimall_footprint` VALUES ('242', '208', '1236777', '2019-08-16 13:15:13', '2019-08-16 13:15:13');
INSERT INTO `unimall_footprint` VALUES ('243', '211', '1236768', '2019-08-16 13:39:58', '2019-08-16 13:39:58');
INSERT INTO `unimall_footprint` VALUES ('244', '213', '1236777', '2019-08-16 14:06:19', '2019-08-16 14:06:19');
INSERT INTO `unimall_footprint` VALUES ('245', '216', '1236773', '2019-08-16 16:36:11', '2019-08-16 16:36:11');
INSERT INTO `unimall_footprint` VALUES ('246', '218', '1236773', '2019-08-16 17:15:34', '2019-08-16 17:15:34');
INSERT INTO `unimall_footprint` VALUES ('247', '218', '1236772', '2019-08-16 17:15:38', '2019-08-16 17:15:48');
INSERT INTO `unimall_footprint` VALUES ('248', '220', '1236772', '2019-08-16 18:06:41', '2019-08-16 18:06:41');
INSERT INTO `unimall_footprint` VALUES ('249', '221', '1236777', '2019-08-16 19:23:52', '2019-08-16 19:23:52');
INSERT INTO `unimall_footprint` VALUES ('250', '221', '1236768', '2019-08-16 19:24:00', '2019-08-16 19:24:00');
INSERT INTO `unimall_footprint` VALUES ('251', '222', '1236768', '2019-08-16 20:49:16', '2019-08-16 20:51:07');
INSERT INTO `unimall_footprint` VALUES ('252', '222', '1236777', '2019-08-16 20:49:41', '2019-08-16 20:49:41');
INSERT INTO `unimall_footprint` VALUES ('253', '222', '1236771', '2019-08-16 20:50:48', '2019-08-16 20:51:34');
INSERT INTO `unimall_footprint` VALUES ('254', '222', '1236772', '2019-08-16 20:50:55', '2019-08-16 20:51:23');
INSERT INTO `unimall_footprint` VALUES ('255', '222', '1236775', '2019-08-16 20:51:02', '2019-08-16 20:51:02');
INSERT INTO `unimall_footprint` VALUES ('256', '222', '1236769', '2019-08-16 20:51:10', '2019-08-16 20:51:10');
INSERT INTO `unimall_footprint` VALUES ('257', '223', '1236770', '2019-08-17 11:57:21', '2019-08-17 11:57:21');
INSERT INTO `unimall_footprint` VALUES ('258', '223', '1236775', '2019-08-17 12:03:59', '2019-08-17 12:04:07');
INSERT INTO `unimall_footprint` VALUES ('259', '224', '1236768', '2019-08-17 12:04:29', '2019-08-17 12:04:29');
INSERT INTO `unimall_footprint` VALUES ('260', '224', '1236777', '2019-08-17 12:05:44', '2019-08-17 12:05:44');
INSERT INTO `unimall_footprint` VALUES ('261', '225', '1236777', '2019-08-17 12:16:30', '2019-08-17 12:16:30');
INSERT INTO `unimall_footprint` VALUES ('262', '226', '1236768', '2019-08-17 12:33:01', '2019-08-17 20:12:29');
INSERT INTO `unimall_footprint` VALUES ('263', '226', '1236770', '2019-08-17 12:33:09', '2019-08-17 13:12:45');
INSERT INTO `unimall_footprint` VALUES ('264', '226', '1236769', '2019-08-17 12:38:10', '2019-08-17 19:43:24');
INSERT INTO `unimall_footprint` VALUES ('265', '226', '1236772', '2019-08-17 13:12:10', '2019-08-17 19:43:40');
INSERT INTO `unimall_footprint` VALUES ('266', '226', '1236773', '2019-08-17 13:12:54', '2019-08-17 19:40:32');
INSERT INTO `unimall_footprint` VALUES ('267', '227', '1236777', '2019-08-17 13:26:37', '2019-08-17 13:26:46');
INSERT INTO `unimall_footprint` VALUES ('268', '228', '1236769', '2019-08-17 13:46:09', '2019-08-17 13:47:06');
INSERT INTO `unimall_footprint` VALUES ('269', '226', '1236774', '2019-08-17 14:13:26', '2019-08-17 20:12:51');
INSERT INTO `unimall_footprint` VALUES ('270', '229', '1236769', '2019-08-17 14:36:13', '2019-08-17 14:36:13');
INSERT INTO `unimall_footprint` VALUES ('271', '231', '1236768', '2019-08-17 15:52:18', '2019-08-19 11:55:53');
INSERT INTO `unimall_footprint` VALUES ('272', '87', '1236770', '2019-08-17 16:54:30', '2019-08-17 16:54:30');
INSERT INTO `unimall_footprint` VALUES ('273', '87', '1236775', '2019-08-17 16:55:11', '2019-08-17 16:55:11');
INSERT INTO `unimall_footprint` VALUES ('274', '234', '1236769', '2019-08-17 17:50:27', '2019-08-17 17:50:27');
INSERT INTO `unimall_footprint` VALUES ('275', '234', '1236770', '2019-08-17 17:50:41', '2019-08-17 17:51:11');
INSERT INTO `unimall_footprint` VALUES ('276', '226', '1236775', '2019-08-17 19:41:16', '2019-08-17 19:41:16');
INSERT INTO `unimall_footprint` VALUES ('277', '237', '1236768', '2019-08-18 08:33:17', '2019-08-18 08:33:17');
INSERT INTO `unimall_footprint` VALUES ('278', '237', '1236777', '2019-08-18 08:33:55', '2019-08-18 11:26:59');
INSERT INTO `unimall_footprint` VALUES ('279', '237', '1236769', '2019-08-18 11:27:31', '2019-08-18 11:27:31');
INSERT INTO `unimall_footprint` VALUES ('280', '240', '1236768', '2019-08-18 16:12:27', '2019-08-18 16:12:27');
INSERT INTO `unimall_footprint` VALUES ('281', '240', '1236774', '2019-08-18 16:13:13', '2019-08-18 16:14:09');
INSERT INTO `unimall_footprint` VALUES ('282', '240', '1236775', '2019-08-18 16:13:54', '2019-08-18 16:13:54');
INSERT INTO `unimall_footprint` VALUES ('283', '84', '1236773', '2019-08-19 08:22:42', '2019-08-20 11:24:08');
INSERT INTO `unimall_footprint` VALUES ('284', '84', '1236775', '2019-08-19 08:22:44', '2019-08-19 08:22:44');
INSERT INTO `unimall_footprint` VALUES ('285', '241', '1236768', '2019-08-19 10:09:07', '2019-08-19 10:36:24');
INSERT INTO `unimall_footprint` VALUES ('286', '241', '1236777', '2019-08-19 11:34:23', '2019-08-19 11:35:21');
INSERT INTO `unimall_footprint` VALUES ('287', '131', '1236769', '2019-08-19 22:16:49', '2019-08-19 22:16:49');
INSERT INTO `unimall_footprint` VALUES ('288', '131', '1236771', '2019-08-19 22:17:15', '2019-08-19 22:17:15');
INSERT INTO `unimall_footprint` VALUES ('289', '248', '1236768', '2019-08-19 23:15:40', '2019-08-19 23:15:40');
INSERT INTO `unimall_footprint` VALUES ('290', '62', '1236777', '2019-08-19 23:48:35', '2019-08-19 23:48:35');
INSERT INTO `unimall_footprint` VALUES ('291', '62', '1236768', '2019-08-19 23:49:14', '2019-08-19 23:49:14');
INSERT INTO `unimall_footprint` VALUES ('292', '62', '1236773', '2019-08-19 23:49:26', '2019-08-19 23:49:26');
INSERT INTO `unimall_footprint` VALUES ('293', '62', '1236775', '2019-08-19 23:50:09', '2019-08-19 23:50:09');
INSERT INTO `unimall_footprint` VALUES ('294', '214', '1236768', '2019-08-20 11:07:36', '2019-08-20 11:07:36');
INSERT INTO `unimall_footprint` VALUES ('295', '84', '1236774', '2019-08-20 11:24:25', '2019-08-20 11:24:37');
INSERT INTO `unimall_footprint` VALUES ('296', '256', '1236777', '2019-08-21 14:00:50', '2019-08-21 14:00:50');
INSERT INTO `unimall_footprint` VALUES ('297', '203', '1236772', '2019-08-21 14:33:57', '2019-08-21 14:33:57');
INSERT INTO `unimall_footprint` VALUES ('298', '257', '1236770', '2019-08-21 17:04:47', '2019-08-21 17:04:47');
INSERT INTO `unimall_footprint` VALUES ('299', '128', '1236771', '2019-08-21 17:39:13', '2019-08-21 17:39:13');
INSERT INTO `unimall_footprint` VALUES ('300', '259', '1236777', '2019-08-22 07:16:01', '2019-09-09 06:18:51');
INSERT INTO `unimall_footprint` VALUES ('301', '260', '1236775', '2019-08-22 09:19:57', '2019-08-25 14:19:03');
INSERT INTO `unimall_footprint` VALUES ('302', '261', '1236768', '2019-08-22 10:31:12', '2019-08-22 10:31:12');
INSERT INTO `unimall_footprint` VALUES ('303', '229', '1236768', '2019-08-22 10:49:52', '2019-08-22 10:49:58');
INSERT INTO `unimall_footprint` VALUES ('304', '264', '1236777', '2019-08-22 13:44:04', '2019-08-23 16:22:44');
INSERT INTO `unimall_footprint` VALUES ('305', '264', '1236771', '2019-08-22 13:45:44', '2019-08-22 13:46:07');
INSERT INTO `unimall_footprint` VALUES ('306', '265', '1236774', '2019-08-22 13:55:20', '2019-08-22 13:55:20');
INSERT INTO `unimall_footprint` VALUES ('307', '265', '1236777', '2019-08-22 13:57:07', '2019-08-22 13:57:07');
INSERT INTO `unimall_footprint` VALUES ('308', '267', '1236777', '2019-08-22 16:43:56', '2019-08-22 17:05:24');
INSERT INTO `unimall_footprint` VALUES ('309', '267', '1236773', '2019-08-22 16:45:22', '2019-08-22 16:49:42');
INSERT INTO `unimall_footprint` VALUES ('310', '267', '1236775', '2019-08-22 16:48:18', '2019-08-22 16:48:18');
INSERT INTO `unimall_footprint` VALUES ('311', '272', '1236769', '2019-08-23 05:17:11', '2019-08-23 05:17:11');
INSERT INTO `unimall_footprint` VALUES ('312', '259', '1236774', '2019-08-23 06:07:03', '2019-08-23 06:07:03');
INSERT INTO `unimall_footprint` VALUES ('313', '259', '1236775', '2019-08-23 06:07:15', '2019-08-29 01:27:16');
INSERT INTO `unimall_footprint` VALUES ('314', '259', '1236768', '2019-08-23 06:07:28', '2019-09-09 06:17:47');
INSERT INTO `unimall_footprint` VALUES ('315', '273', '1236777', '2019-08-23 11:21:58', '2019-08-23 11:41:03');
INSERT INTO `unimall_footprint` VALUES ('316', '273', '1236768', '2019-08-23 11:28:20', '2019-08-23 11:28:20');
INSERT INTO `unimall_footprint` VALUES ('317', '275', '1236768', '2019-08-23 13:13:00', '2019-08-23 13:13:00');
INSERT INTO `unimall_footprint` VALUES ('318', '275', '1236773', '2019-08-23 13:20:05', '2019-08-23 13:20:05');
INSERT INTO `unimall_footprint` VALUES ('319', '275', '1236777', '2019-08-23 13:20:32', '2019-08-23 13:20:32');
INSERT INTO `unimall_footprint` VALUES ('320', '278', '1236777', '2019-08-23 15:44:05', '2019-08-23 15:44:05');
INSERT INTO `unimall_footprint` VALUES ('321', '264', '1236768', '2019-08-23 16:48:37', '2019-08-23 16:48:37');
INSERT INTO `unimall_footprint` VALUES ('322', '127', '1236774', '2019-08-24 08:35:32', '2019-08-24 08:35:32');
INSERT INTO `unimall_footprint` VALUES ('323', '127', '1236777', '2019-08-24 08:35:40', '2019-08-24 08:35:40');
INSERT INTO `unimall_footprint` VALUES ('324', '127', '1236775', '2019-08-24 08:35:53', '2019-08-24 08:35:53');
INSERT INTO `unimall_footprint` VALUES ('325', '280', '1236774', '2019-08-24 13:07:40', '2019-08-24 13:07:40');
INSERT INTO `unimall_footprint` VALUES ('326', '274', '1236777', '2019-08-24 14:15:03', '2019-08-24 14:15:03');
INSERT INTO `unimall_footprint` VALUES ('327', '281', '1236772', '2019-08-24 16:36:54', '2019-08-24 16:36:54');
INSERT INTO `unimall_footprint` VALUES ('328', '105', '1236771', '2019-08-24 21:56:12', '2019-08-25 09:50:15');
INSERT INTO `unimall_footprint` VALUES ('329', '105', '1236774', '2019-08-24 22:21:30', '2019-08-25 09:51:00');
INSERT INTO `unimall_footprint` VALUES ('330', '105', '1236777', '2019-08-24 23:34:48', '2019-09-05 08:24:08');
INSERT INTO `unimall_footprint` VALUES ('331', '105', '1236773', '2019-08-24 23:38:43', '2019-08-25 09:52:05');
INSERT INTO `unimall_footprint` VALUES ('332', '284', '1236777', '2019-08-25 02:22:05', '2019-08-25 02:22:05');
INSERT INTO `unimall_footprint` VALUES ('333', '285', '1236771', '2019-08-25 07:45:40', '2019-08-31 08:40:53');
INSERT INTO `unimall_footprint` VALUES ('334', '285', '1236768', '2019-08-25 07:48:40', '2019-08-25 07:48:40');
INSERT INTO `unimall_footprint` VALUES ('335', '105', '1236772', '2019-08-25 09:51:03', '2019-08-25 09:51:03');
INSERT INTO `unimall_footprint` VALUES ('336', '260', '1236768', '2019-08-25 14:18:16', '2019-08-25 14:19:53');
INSERT INTO `unimall_footprint` VALUES ('337', '260', '1236771', '2019-08-25 14:18:33', '2019-08-25 14:18:44');
INSERT INTO `unimall_footprint` VALUES ('338', '260', '1236777', '2019-08-25 14:20:08', '2019-08-25 14:20:08');
INSERT INTO `unimall_footprint` VALUES ('339', '287', '1236768', '2019-08-26 10:37:35', '2019-09-07 23:48:37');
INSERT INTO `unimall_footprint` VALUES ('340', '287', '1236772', '2019-08-26 10:38:14', '2019-09-07 23:48:52');
INSERT INTO `unimall_footprint` VALUES ('341', '287', '1236773', '2019-08-26 10:39:23', '2019-09-04 19:50:13');
INSERT INTO `unimall_footprint` VALUES ('342', '287', '1236777', '2019-08-26 10:39:31', '2019-09-07 12:57:55');
INSERT INTO `unimall_footprint` VALUES ('343', '289', '1236769', '2019-08-26 14:07:10', '2019-08-26 15:05:58');
INSERT INTO `unimall_footprint` VALUES ('344', '289', '1236775', '2019-08-26 14:08:32', '2019-08-26 14:09:56');
INSERT INTO `unimall_footprint` VALUES ('345', '289', '1236773', '2019-08-26 14:09:06', '2019-08-26 14:09:06');
INSERT INTO `unimall_footprint` VALUES ('346', '289', '1236774', '2019-08-26 14:09:12', '2019-08-26 14:10:00');
INSERT INTO `unimall_footprint` VALUES ('347', '289', '1236772', '2019-08-26 14:10:05', '2019-08-26 14:10:32');
INSERT INTO `unimall_footprint` VALUES ('348', '291', '1236773', '2019-08-26 15:26:41', '2019-08-26 15:26:41');
INSERT INTO `unimall_footprint` VALUES ('349', '293', '1236768', '2019-08-26 17:05:48', '2019-08-26 17:05:48');
INSERT INTO `unimall_footprint` VALUES ('350', '293', '1236769', '2019-08-26 17:05:56', '2019-08-26 17:05:56');
INSERT INTO `unimall_footprint` VALUES ('351', '275', '1236769', '2019-08-26 20:48:56', '2019-08-26 20:48:56');
INSERT INTO `unimall_footprint` VALUES ('352', '295', '1236777', '2019-08-26 23:15:20', '2019-08-26 23:15:20');
INSERT INTO `unimall_footprint` VALUES ('353', '295', '1236775', '2019-08-26 23:16:03', '2019-08-26 23:16:03');
INSERT INTO `unimall_footprint` VALUES ('354', '296', '1236774', '2019-08-27 13:49:49', '2019-08-27 13:49:49');
INSERT INTO `unimall_footprint` VALUES ('355', '297', '1236777', '2019-08-27 16:32:16', '2019-08-27 16:32:16');
INSERT INTO `unimall_footprint` VALUES ('356', '298', '1236769', '2019-08-27 17:32:08', '2019-08-27 17:32:47');
INSERT INTO `unimall_footprint` VALUES ('357', '300', '1236773', '2019-08-27 18:24:26', '2019-08-27 18:24:26');
INSERT INTO `unimall_footprint` VALUES ('358', '300', '1236777', '2019-08-27 18:25:33', '2019-08-27 18:25:33');
INSERT INTO `unimall_footprint` VALUES ('359', '303', '1236777', '2019-08-28 16:16:34', '2019-08-28 16:16:34');
INSERT INTO `unimall_footprint` VALUES ('360', '305', '1236774', '2019-08-28 21:36:56', '2019-09-01 11:08:29');
INSERT INTO `unimall_footprint` VALUES ('361', '305', '1236771', '2019-08-28 21:37:06', '2019-08-28 21:37:06');
INSERT INTO `unimall_footprint` VALUES ('362', '305', '1236773', '2019-08-28 21:37:30', '2019-08-28 21:46:33');
INSERT INTO `unimall_footprint` VALUES ('363', '305', '1236768', '2019-08-28 21:38:08', '2019-08-28 21:40:54');
INSERT INTO `unimall_footprint` VALUES ('364', '305', '1236769', '2019-08-28 21:38:18', '2019-08-28 21:38:18');
INSERT INTO `unimall_footprint` VALUES ('365', '305', '1236775', '2019-08-28 21:39:26', '2019-09-01 11:08:32');
INSERT INTO `unimall_footprint` VALUES ('366', '305', '1236772', '2019-08-28 21:39:33', '2019-08-28 21:47:09');
INSERT INTO `unimall_footprint` VALUES ('367', '307', '1236770', '2019-08-28 23:46:29', '2019-08-28 23:46:29');
INSERT INTO `unimall_footprint` VALUES ('368', '259', '1236771', '2019-08-29 01:25:47', '2019-08-29 01:25:47');
INSERT INTO `unimall_footprint` VALUES ('369', '308', '1236775', '2019-08-29 01:44:52', '2019-08-29 01:44:52');
INSERT INTO `unimall_footprint` VALUES ('370', '309', '1236776', '2019-08-29 09:16:42', '2019-08-29 09:17:56');
INSERT INTO `unimall_footprint` VALUES ('371', '308', '1236773', '2019-08-29 09:44:04', '2019-08-29 09:44:04');
INSERT INTO `unimall_footprint` VALUES ('372', '309', '1236771', '2019-08-29 10:01:08', '2019-08-29 10:01:08');
INSERT INTO `unimall_footprint` VALUES ('373', '311', '1236776', '2019-08-29 10:19:31', '2019-08-29 17:19:15');
INSERT INTO `unimall_footprint` VALUES ('374', '313', '1236777', '2019-08-29 11:44:38', '2019-08-29 11:44:38');
INSERT INTO `unimall_footprint` VALUES ('375', '277', '1236774', '2019-08-29 13:24:26', '2019-08-29 13:24:26');
INSERT INTO `unimall_footprint` VALUES ('376', '303', '1236773', '2019-08-29 13:54:10', '2019-08-29 13:54:10');
INSERT INTO `unimall_footprint` VALUES ('377', '314', '1236771', '2019-08-29 16:26:02', '2019-08-29 16:26:02');
INSERT INTO `unimall_footprint` VALUES ('378', '70', '1236770', '2019-08-29 16:44:36', '2019-08-29 16:44:36');
INSERT INTO `unimall_footprint` VALUES ('379', '70', '1236773', '2019-08-29 16:45:14', '2019-08-29 16:45:19');
INSERT INTO `unimall_footprint` VALUES ('380', '311', '1236773', '2019-08-29 18:09:18', '2019-08-29 18:09:21');
INSERT INTO `unimall_footprint` VALUES ('381', '318', '1236768', '2019-08-30 15:14:37', '2019-08-30 15:14:37');
INSERT INTO `unimall_footprint` VALUES ('382', '319', '1236770', '2019-08-30 15:45:56', '2019-08-30 15:45:56');
INSERT INTO `unimall_footprint` VALUES ('383', '319', '1236773', '2019-08-30 15:46:02', '2019-08-30 15:46:10');
INSERT INTO `unimall_footprint` VALUES ('384', '319', '1236777', '2019-08-30 15:46:33', '2019-08-30 15:46:33');
INSERT INTO `unimall_footprint` VALUES ('385', '319', '1236771', '2019-08-30 15:46:39', '2019-08-30 15:46:39');
INSERT INTO `unimall_footprint` VALUES ('386', '319', '1236775', '2019-08-30 15:46:43', '2019-08-30 15:46:43');
INSERT INTO `unimall_footprint` VALUES ('387', '321', '1236777', '2019-08-30 22:19:49', '2019-08-30 22:19:49');
INSERT INTO `unimall_footprint` VALUES ('388', '321', '1236770', '2019-08-30 22:20:00', '2019-08-31 16:07:01');
INSERT INTO `unimall_footprint` VALUES ('389', '321', '1236771', '2019-08-30 22:20:13', '2019-08-30 22:20:13');
INSERT INTO `unimall_footprint` VALUES ('390', '321', '1236774', '2019-08-30 22:33:24', '2019-08-30 22:33:46');
INSERT INTO `unimall_footprint` VALUES ('391', '321', '1236775', '2019-08-30 22:33:38', '2019-08-30 22:33:38');
INSERT INTO `unimall_footprint` VALUES ('392', '323', '1236773', '2019-08-30 22:49:49', '2019-09-03 08:48:50');
INSERT INTO `unimall_footprint` VALUES ('393', '323', '1236771', '2019-08-30 22:51:18', '2019-08-30 22:51:18');
INSERT INTO `unimall_footprint` VALUES ('394', '323', '1236772', '2019-08-30 22:53:13', '2019-08-30 22:53:13');
INSERT INTO `unimall_footprint` VALUES ('395', '324', '1236772', '2019-08-30 23:36:32', '2019-08-30 23:36:32');
INSERT INTO `unimall_footprint` VALUES ('396', '195', '1236774', '2019-08-30 23:39:07', '2019-08-30 23:39:17');
INSERT INTO `unimall_footprint` VALUES ('397', '195', '1236775', '2019-08-30 23:39:22', '2019-08-30 23:42:45');
INSERT INTO `unimall_footprint` VALUES ('398', '195', '1236777', '2019-08-30 23:39:26', '2019-08-30 23:39:29');
INSERT INTO `unimall_footprint` VALUES ('399', '195', '1236768', '2019-08-30 23:40:02', '2019-08-30 23:41:39');
INSERT INTO `unimall_footprint` VALUES ('400', '195', '1236770', '2019-08-30 23:40:03', '2019-08-30 23:40:03');
INSERT INTO `unimall_footprint` VALUES ('401', '195', '1236776', '2019-08-30 23:40:13', '2019-08-30 23:40:23');
INSERT INTO `unimall_footprint` VALUES ('402', '195', '1236773', '2019-08-30 23:41:30', '2019-08-30 23:41:30');
INSERT INTO `unimall_footprint` VALUES ('403', '325', '1236772', '2019-08-31 08:40:54', '2019-08-31 08:40:54');
INSERT INTO `unimall_footprint` VALUES ('404', '285', '1236772', '2019-08-31 09:26:20', '2019-08-31 09:27:34');
INSERT INTO `unimall_footprint` VALUES ('405', '327', '1236777', '2019-08-31 10:03:41', '2019-08-31 10:03:41');
INSERT INTO `unimall_footprint` VALUES ('406', '329', '1236777', '2019-08-31 14:58:25', '2019-08-31 14:58:25');
INSERT INTO `unimall_footprint` VALUES ('407', '321', '1236769', '2019-08-31 16:07:20', '2019-08-31 16:07:20');
INSERT INTO `unimall_footprint` VALUES ('408', '321', '1236768', '2019-08-31 16:08:17', '2019-08-31 16:08:17');
INSERT INTO `unimall_footprint` VALUES ('409', '330', '1236768', '2019-08-31 16:55:46', '2019-08-31 16:55:46');
INSERT INTO `unimall_footprint` VALUES ('410', '330', '1236777', '2019-08-31 16:55:49', '2019-08-31 16:55:49');
INSERT INTO `unimall_footprint` VALUES ('411', '331', '1236777', '2019-08-31 19:01:07', '2019-08-31 19:01:07');
INSERT INTO `unimall_footprint` VALUES ('412', '133', '1236769', '2019-09-01 08:38:30', '2019-09-01 08:38:30');
INSERT INTO `unimall_footprint` VALUES ('413', '334', '1236769', '2019-09-01 14:19:25', '2019-09-01 14:19:25');
INSERT INTO `unimall_footprint` VALUES ('414', '287', '1236774', '2019-09-01 18:01:36', '2019-09-07 13:01:53');
INSERT INTO `unimall_footprint` VALUES ('415', '287', '1236770', '2019-09-01 18:02:55', '2019-09-04 19:47:20');
INSERT INTO `unimall_footprint` VALUES ('416', '259', '1236773', '2019-09-02 02:12:35', '2019-09-06 12:45:13');
INSERT INTO `unimall_footprint` VALUES ('417', '336', '1236772', '2019-09-02 07:22:16', '2019-09-02 07:22:16');
INSERT INTO `unimall_footprint` VALUES ('418', '338', '1236768', '2019-09-02 10:29:35', '2019-09-02 12:36:57');
INSERT INTO `unimall_footprint` VALUES ('419', '339', '1236771', '2019-09-02 10:31:18', '2019-09-02 10:31:18');
INSERT INTO `unimall_footprint` VALUES ('420', '340', '1236768', '2019-09-02 10:44:29', '2019-09-02 10:44:29');
INSERT INTO `unimall_footprint` VALUES ('421', '340', '1236773', '2019-09-02 10:48:19', '2019-09-02 10:48:19');
INSERT INTO `unimall_footprint` VALUES ('422', '340', '1236772', '2019-09-02 11:10:53', '2019-09-02 11:10:53');
INSERT INTO `unimall_footprint` VALUES ('423', '342', '1236777', '2019-09-02 13:13:28', '2019-09-02 13:13:28');
INSERT INTO `unimall_footprint` VALUES ('424', '343', '1236769', '2019-09-02 14:05:26', '2019-09-02 14:05:26');
INSERT INTO `unimall_footprint` VALUES ('425', '346', '1236777', '2019-09-03 11:02:28', '2019-09-03 11:02:28');
INSERT INTO `unimall_footprint` VALUES ('426', '347', '1236772', '2019-09-03 11:12:42', '2019-09-03 11:12:42');
INSERT INTO `unimall_footprint` VALUES ('427', '118', '1236769', '2019-09-03 14:08:07', '2019-09-03 14:08:07');
INSERT INTO `unimall_footprint` VALUES ('428', '348', '1236771', '2019-09-03 14:15:36', '2019-09-03 14:15:36');
INSERT INTO `unimall_footprint` VALUES ('429', '351', '1236772', '2019-09-03 15:01:16', '2019-09-03 15:01:16');
INSERT INTO `unimall_footprint` VALUES ('430', '351', '1236777', '2019-09-03 15:01:27', '2019-09-03 15:01:31');
INSERT INTO `unimall_footprint` VALUES ('431', '351', '1236768', '2019-09-03 15:01:40', '2019-09-03 15:01:40');
INSERT INTO `unimall_footprint` VALUES ('432', '352', '1236777', '2019-09-03 16:57:05', '2019-09-03 16:57:05');
INSERT INTO `unimall_footprint` VALUES ('433', '156', '1236773', '2019-09-03 17:33:52', '2019-09-03 17:33:52');
INSERT INTO `unimall_footprint` VALUES ('434', '156', '1236768', '2019-09-03 17:34:37', '2019-09-03 17:34:37');
INSERT INTO `unimall_footprint` VALUES ('435', '355', '1236772', '2019-09-04 14:11:53', '2019-09-04 14:11:53');
INSERT INTO `unimall_footprint` VALUES ('436', '75', '1236777', '2019-09-04 14:42:59', '2019-09-04 14:42:59');
INSERT INTO `unimall_footprint` VALUES ('437', '355', '1236773', '2019-09-04 18:38:08', '2019-09-17 16:30:31');
INSERT INTO `unimall_footprint` VALUES ('438', '355', '1236776', '2019-09-04 18:38:14', '2019-09-04 18:44:13');
INSERT INTO `unimall_footprint` VALUES ('439', '302', '1236768', '2019-09-05 14:36:38', '2019-09-05 14:36:38');
INSERT INTO `unimall_footprint` VALUES ('440', '302', '1236777', '2019-09-05 14:36:40', '2019-09-05 14:43:16');
INSERT INTO `unimall_footprint` VALUES ('441', '355', '1236777', '2019-09-05 14:39:38', '2019-09-17 16:30:06');
INSERT INTO `unimall_footprint` VALUES ('442', '359', '1236773', '2019-09-05 15:41:23', '2019-09-05 15:41:37');
INSERT INTO `unimall_footprint` VALUES ('443', '360', '1236768', '2019-09-05 17:38:44', '2019-09-05 17:38:44');
INSERT INTO `unimall_footprint` VALUES ('444', '215', '1236769', '2019-09-05 22:57:32', '2019-09-05 22:57:32');
INSERT INTO `unimall_footprint` VALUES ('445', '215', '1236776', '2019-09-05 22:59:30', '2019-09-05 22:59:30');
INSERT INTO `unimall_footprint` VALUES ('446', '362', '1236777', '2019-09-05 22:59:31', '2019-09-05 22:59:31');
INSERT INTO `unimall_footprint` VALUES ('447', '363', '1236773', '2019-09-06 09:47:03', '2019-09-06 09:47:03');
INSERT INTO `unimall_footprint` VALUES ('448', '363', '1236770', '2019-09-06 09:47:11', '2019-09-06 09:47:11');
INSERT INTO `unimall_footprint` VALUES ('449', '363', '1236769', '2019-09-06 09:47:16', '2019-09-06 09:47:16');
INSERT INTO `unimall_footprint` VALUES ('450', '363', '1236775', '2019-09-06 09:48:37', '2019-09-06 09:48:37');
INSERT INTO `unimall_footprint` VALUES ('451', '363', '1236768', '2019-09-06 09:48:53', '2019-09-06 09:48:53');
INSERT INTO `unimall_footprint` VALUES ('452', '366', '1236775', '2019-09-06 11:08:02', '2019-09-06 11:08:02');
INSERT INTO `unimall_footprint` VALUES ('453', '367', '1236773', '2019-09-06 11:52:38', '2019-09-18 20:44:39');
INSERT INTO `unimall_footprint` VALUES ('454', '367', '1236769', '2019-09-06 11:54:38', '2019-09-18 20:43:48');
INSERT INTO `unimall_footprint` VALUES ('455', '367', '1236777', '2019-09-06 11:54:49', '2019-09-08 15:27:24');
INSERT INTO `unimall_footprint` VALUES ('456', '367', '1236768', '2019-09-06 11:54:54', '2019-09-18 21:06:58');
INSERT INTO `unimall_footprint` VALUES ('457', '367', '1236771', '2019-09-06 11:55:02', '2019-09-18 07:52:12');
INSERT INTO `unimall_footprint` VALUES ('458', '367', '1236775', '2019-09-06 12:11:54', '2019-09-18 20:44:15');
INSERT INTO `unimall_footprint` VALUES ('459', '367', '1236774', '2019-09-06 12:11:58', '2019-09-16 22:32:08');
INSERT INTO `unimall_footprint` VALUES ('460', '355', '1236775', '2019-09-06 14:14:30', '2019-09-11 16:34:32');
INSERT INTO `unimall_footprint` VALUES ('461', '355', '1236768', '2019-09-06 14:28:41', '2019-09-17 16:30:10');
INSERT INTO `unimall_footprint` VALUES ('462', '367', '1236770', '2019-09-07 10:18:34', '2019-09-18 16:25:07');
INSERT INTO `unimall_footprint` VALUES ('463', '375', '1236777', '2019-09-07 16:12:04', '2019-09-07 16:12:04');
INSERT INTO `unimall_footprint` VALUES ('464', '209', '1236777', '2019-09-07 17:21:41', '2019-09-07 18:12:30');
INSERT INTO `unimall_footprint` VALUES ('465', '376', '1236768', '2019-09-07 21:35:07', '2019-09-07 21:35:07');
INSERT INTO `unimall_footprint` VALUES ('466', '376', '1236769', '2019-09-07 21:35:32', '2019-09-07 21:35:32');
INSERT INTO `unimall_footprint` VALUES ('467', '287', '1236769', '2019-09-07 23:48:48', '2019-09-07 23:48:48');
INSERT INTO `unimall_footprint` VALUES ('468', '378', '1236772', '2019-09-08 10:12:25', '2019-09-08 10:12:25');
INSERT INTO `unimall_footprint` VALUES ('469', '378', '1236769', '2019-09-08 11:20:57', '2019-09-08 11:21:11');
INSERT INTO `unimall_footprint` VALUES ('470', '378', '1236768', '2019-09-08 11:21:44', '2019-09-08 11:21:44');
INSERT INTO `unimall_footprint` VALUES ('471', '378', '1236771', '2019-09-08 11:22:05', '2019-09-08 11:22:05');
INSERT INTO `unimall_footprint` VALUES ('472', '317', '1236777', '2019-09-08 15:43:22', '2019-09-08 15:43:22');
INSERT INTO `unimall_footprint` VALUES ('473', '317', '1236775', '2019-09-08 22:16:16', '2019-09-08 22:16:16');
INSERT INTO `unimall_footprint` VALUES ('474', '288', '1236771', '2019-09-09 08:45:12', '2019-09-09 08:45:12');
INSERT INTO `unimall_footprint` VALUES ('475', '288', '1236775', '2019-09-09 09:07:40', '2019-09-09 09:07:40');
INSERT INTO `unimall_footprint` VALUES ('476', '288', '1236773', '2019-09-09 09:10:19', '2019-09-09 09:10:19');
INSERT INTO `unimall_footprint` VALUES ('477', '381', '1236771', '2019-09-09 16:46:19', '2019-09-09 16:46:45');
INSERT INTO `unimall_footprint` VALUES ('478', '381', '1236773', '2019-09-09 16:48:09', '2019-09-09 16:48:09');
INSERT INTO `unimall_footprint` VALUES ('479', '382', '1236772', '2019-09-09 17:58:25', '2019-09-09 17:58:25');
INSERT INTO `unimall_footprint` VALUES ('480', '383', '1236771', '2019-09-09 18:44:30', '2019-09-09 18:44:30');
INSERT INTO `unimall_footprint` VALUES ('481', '383', '1236777', '2019-09-09 18:48:22', '2019-09-09 18:49:01');
INSERT INTO `unimall_footprint` VALUES ('482', '384', '1236773', '2019-09-09 19:03:18', '2019-09-09 19:03:18');
INSERT INTO `unimall_footprint` VALUES ('483', '386', '1236777', '2019-09-10 08:45:41', '2019-09-10 08:45:41');
INSERT INTO `unimall_footprint` VALUES ('484', '387', '1236769', '2019-09-10 10:24:04', '2019-09-10 10:24:04');
INSERT INTO `unimall_footprint` VALUES ('485', '388', '1236768', '2019-09-10 10:40:57', '2019-09-10 10:41:49');
INSERT INTO `unimall_footprint` VALUES ('486', '389', '1236777', '2019-09-10 10:50:00', '2019-09-10 10:50:00');
INSERT INTO `unimall_footprint` VALUES ('487', '387', '1236773', '2019-09-10 10:54:05', '2019-09-10 10:54:05');
INSERT INTO `unimall_footprint` VALUES ('488', '387', '1236777', '2019-09-10 10:54:29', '2019-09-10 10:54:29');
INSERT INTO `unimall_footprint` VALUES ('489', '387', '1236768', '2019-09-10 10:54:46', '2019-09-10 10:54:46');
INSERT INTO `unimall_footprint` VALUES ('490', '387', '1236771', '2019-09-10 10:54:53', '2019-09-10 10:54:53');
INSERT INTO `unimall_footprint` VALUES ('491', '392', '1236772', '2019-09-10 15:29:35', '2019-09-10 15:29:35');
INSERT INTO `unimall_footprint` VALUES ('492', '393', '1236771', '2019-09-10 16:34:10', '2019-09-10 16:34:10');
INSERT INTO `unimall_footprint` VALUES ('493', '394', '1236768', '2019-09-10 16:38:46', '2019-09-10 16:39:04');
INSERT INTO `unimall_footprint` VALUES ('494', '394', '1236777', '2019-09-10 16:39:14', '2019-09-10 16:39:52');
INSERT INTO `unimall_footprint` VALUES ('495', '395', '1236773', '2019-09-10 17:10:51', '2019-09-10 17:11:15');
INSERT INTO `unimall_footprint` VALUES ('496', '397', '1236777', '2019-09-10 18:04:31', '2019-09-10 18:04:31');
INSERT INTO `unimall_footprint` VALUES ('497', '393', '1236777', '2019-09-10 20:59:03', '2019-09-10 20:59:03');
INSERT INTO `unimall_footprint` VALUES ('498', '398', '1236768', '2019-09-11 02:18:22', '2019-09-11 02:18:22');
INSERT INTO `unimall_footprint` VALUES ('499', '398', '1236773', '2019-09-11 02:18:36', '2019-09-11 03:02:14');
INSERT INTO `unimall_footprint` VALUES ('500', '398', '1236769', '2019-09-11 03:01:25', '2019-09-11 03:01:25');
INSERT INTO `unimall_footprint` VALUES ('501', '400', '1236769', '2019-09-11 11:46:29', '2019-09-11 11:46:29');
INSERT INTO `unimall_footprint` VALUES ('502', '400', '1236771', '2019-09-11 11:46:58', '2019-09-11 11:46:58');
INSERT INTO `unimall_footprint` VALUES ('503', '400', '1236777', '2019-09-11 11:47:49', '2019-09-11 11:47:49');
INSERT INTO `unimall_footprint` VALUES ('504', '400', '1236768', '2019-09-11 11:47:58', '2019-09-11 11:47:58');
INSERT INTO `unimall_footprint` VALUES ('505', '401', '1236777', '2019-09-11 11:56:47', '2019-09-11 11:56:47');
INSERT INTO `unimall_footprint` VALUES ('506', '401', '1236773', '2019-09-11 11:56:54', '2019-09-11 11:57:36');
INSERT INTO `unimall_footprint` VALUES ('507', '401', '1236775', '2019-09-11 11:57:19', '2019-09-11 11:57:19');
INSERT INTO `unimall_footprint` VALUES ('508', '402', '1236775', '2019-09-11 15:49:52', '2019-09-11 15:49:52');
INSERT INTO `unimall_footprint` VALUES ('509', '404', '1236771', '2019-09-11 19:44:42', '2019-09-11 19:44:42');
INSERT INTO `unimall_footprint` VALUES ('510', '405', '1236773', '2019-09-12 10:19:46', '2019-09-12 10:20:09');
INSERT INTO `unimall_footprint` VALUES ('511', '405', '1236775', '2019-09-12 10:20:03', '2019-09-12 10:20:03');
INSERT INTO `unimall_footprint` VALUES ('512', '405', '1236774', '2019-09-12 10:20:26', '2019-09-12 10:20:26');
INSERT INTO `unimall_footprint` VALUES ('513', '408', '1236768', '2019-09-12 13:47:05', '2019-09-12 13:47:05');
INSERT INTO `unimall_footprint` VALUES ('514', '409', '1236769', '2019-09-12 16:51:19', '2019-09-12 16:51:19');
INSERT INTO `unimall_footprint` VALUES ('515', '409', '1236772', '2019-09-12 16:51:26', '2019-09-12 16:51:26');
INSERT INTO `unimall_footprint` VALUES ('516', '409', '1236777', '2019-09-12 16:51:42', '2019-09-12 16:51:42');
INSERT INTO `unimall_footprint` VALUES ('517', '411', '1236768', '2019-09-12 19:43:25', '2019-09-12 19:43:25');
INSERT INTO `unimall_footprint` VALUES ('518', '414', '1236773', '2019-09-13 12:29:29', '2019-09-13 12:29:53');
INSERT INTO `unimall_footprint` VALUES ('519', '414', '1236771', '2019-09-13 12:30:02', '2019-09-13 12:30:02');
INSERT INTO `unimall_footprint` VALUES ('520', '414', '1236769', '2019-09-13 12:30:06', '2019-09-13 12:30:06');
INSERT INTO `unimall_footprint` VALUES ('521', '414', '1236768', '2019-09-13 12:30:20', '2019-09-13 12:30:20');
INSERT INTO `unimall_footprint` VALUES ('522', '415', '1236772', '2019-09-13 15:04:21', '2019-09-13 15:04:21');
INSERT INTO `unimall_footprint` VALUES ('523', '415', '1236774', '2019-09-13 15:04:30', '2019-09-13 15:04:30');
INSERT INTO `unimall_footprint` VALUES ('524', '416', '1236773', '2019-09-13 21:02:19', '2019-09-13 21:02:54');
INSERT INTO `unimall_footprint` VALUES ('525', '416', '1236771', '2019-09-13 21:02:47', '2019-09-13 21:02:47');
INSERT INTO `unimall_footprint` VALUES ('526', '418', '1236777', '2019-09-14 09:01:39', '2019-09-14 09:01:39');
INSERT INTO `unimall_footprint` VALUES ('527', '421', '1236773', '2019-09-14 20:12:01', '2019-09-14 20:12:01');
INSERT INTO `unimall_footprint` VALUES ('528', '421', '1236775', '2019-09-14 20:13:01', '2019-09-14 20:13:01');
INSERT INTO `unimall_footprint` VALUES ('529', '413', '1236775', '2019-09-14 21:00:35', '2019-09-14 21:00:35');
INSERT INTO `unimall_footprint` VALUES ('530', '423', '1236768', '2019-09-15 10:44:09', '2019-09-15 10:44:09');
INSERT INTO `unimall_footprint` VALUES ('531', '423', '1236774', '2019-09-15 10:44:56', '2019-09-15 10:44:56');
INSERT INTO `unimall_footprint` VALUES ('532', '423', '1236777', '2019-09-15 11:09:12', '2019-09-15 11:09:12');
INSERT INTO `unimall_footprint` VALUES ('533', '425', '1236777', '2019-09-15 14:23:14', '2019-09-15 14:23:14');
INSERT INTO `unimall_footprint` VALUES ('534', '427', '1236773', '2019-09-16 09:18:55', '2019-09-16 09:18:55');
INSERT INTO `unimall_footprint` VALUES ('535', '428', '1236777', '2019-09-16 10:20:17', '2019-09-16 10:20:17');
INSERT INTO `unimall_footprint` VALUES ('536', '429', '1236768', '2019-09-16 14:40:25', '2019-09-16 14:40:25');
INSERT INTO `unimall_footprint` VALUES ('537', '138', '1236772', '2019-09-16 15:40:57', '2019-09-16 15:56:52');
INSERT INTO `unimall_footprint` VALUES ('538', '431', '1236777', '2019-09-16 17:26:46', '2019-09-16 17:26:46');
INSERT INTO `unimall_footprint` VALUES ('539', '367', '1236772', '2019-09-16 19:39:55', '2019-09-18 08:11:49');
INSERT INTO `unimall_footprint` VALUES ('540', '432', '1236777', '2019-09-17 10:39:04', '2019-09-17 10:39:04');
INSERT INTO `unimall_footprint` VALUES ('541', '434', '1236773', '2019-09-17 16:03:50', '2019-09-17 16:03:50');
INSERT INTO `unimall_footprint` VALUES ('542', '355', '1236771', '2019-09-17 16:30:16', '2019-09-17 16:30:16');
INSERT INTO `unimall_footprint` VALUES ('543', '436', '1236777', '2019-09-17 16:57:30', '2019-09-17 16:57:30');
INSERT INTO `unimall_footprint` VALUES ('544', '437', '1236773', '2019-09-17 17:50:43', '2019-09-17 17:50:43');
INSERT INTO `unimall_footprint` VALUES ('545', '438', '1236771', '2019-09-17 18:45:07', '2019-09-17 18:45:07');
INSERT INTO `unimall_footprint` VALUES ('546', '438', '1236773', '2019-09-17 18:45:14', '2019-09-17 18:45:14');
INSERT INTO `unimall_footprint` VALUES ('547', '439', '1236773', '2019-09-18 11:12:16', '2019-09-18 11:12:16');
INSERT INTO `unimall_footprint` VALUES ('548', '441', '1236772', '2019-09-18 13:53:48', '2019-09-18 17:52:51');
INSERT INTO `unimall_footprint` VALUES ('549', '442', '1236770', '2019-09-18 13:57:56', '2019-09-18 13:57:56');
INSERT INTO `unimall_footprint` VALUES ('550', '443', '1236777', '2019-09-18 14:26:10', '2019-09-18 14:26:10');
INSERT INTO `unimall_footprint` VALUES ('551', '447', '1236772', '2019-09-18 15:53:30', '2019-09-18 15:53:30');
INSERT INTO `unimall_footprint` VALUES ('552', '449', '1236768', '2019-09-18 16:10:17', '2019-09-18 16:10:17');
INSERT INTO `unimall_footprint` VALUES ('553', '449', '1236773', '2019-09-18 16:10:58', '2019-09-18 16:15:41');
INSERT INTO `unimall_footprint` VALUES ('554', '449', '1236775', '2019-09-18 16:15:54', '2019-09-18 16:15:54');
INSERT INTO `unimall_footprint` VALUES ('555', '449', '1236777', '2019-09-18 16:33:50', '2019-09-18 16:36:45');
INSERT INTO `unimall_footprint` VALUES ('556', '367', '1236776', '2019-09-18 20:44:19', '2019-09-18 20:44:19');
INSERT INTO `unimall_footprint` VALUES ('557', '452', '1236768', '2019-09-18 21:21:04', '2019-09-18 21:21:04');
INSERT INTO `unimall_footprint` VALUES ('558', '21', '1236779', '2019-11-25 16:04:29', '2019-11-25 16:04:29');
INSERT INTO `unimall_footprint` VALUES ('559', '0', '1236782', '2020-02-14 16:37:02', '2020-02-14 16:37:49');

-- ----------------------------
-- Table structure for unimall_freight_template
-- ----------------------------
DROP TABLE IF EXISTS `unimall_freight_template`;
CREATE TABLE `unimall_freight_template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `template_name` varchar(100) NOT NULL,
  `spu_location` varchar(200) DEFAULT NULL,
  `delivery_deadline` int(11) NOT NULL,
  `default_free_price` int(11) NOT NULL DEFAULT '-1',
  `default_first_num` int(11) DEFAULT '1',
  `default_first_money` int(11) DEFAULT '0',
  `default_continue_num` int(11) DEFAULT '1',
  `default_continue_money` int(11) DEFAULT '0',
  `gmt_create` datetime DEFAULT NULL,
  `gmt_update` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of unimall_freight_template
-- ----------------------------
INSERT INTO `unimall_freight_template` VALUES ('1', '全国不包邮', '1', '13', '200', '13', '21000', '12', '100', null, '2019-08-23 18:04:26');
INSERT INTO `unimall_freight_template` VALUES ('13', '全国包邮2', '重庆', '4', '0', '1', '0', '1', '0', null, '2019-08-21 12:52:49');
INSERT INTO `unimall_freight_template` VALUES ('31', 'xxx', null, '1', '1300', '1', '100', '1', '100', '2019-08-23 15:15:56', '2019-08-23 15:15:56');

-- ----------------------------
-- Table structure for unimall_freight_template_carriage
-- ----------------------------
DROP TABLE IF EXISTS `unimall_freight_template_carriage`;
CREATE TABLE `unimall_freight_template_carriage` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `template_id` bigint(20) NOT NULL,
  `designated_area` varchar(1000) NOT NULL,
  `free_price` int(11) DEFAULT '-1',
  `first_num` int(11) DEFAULT '1',
  `first_money` int(11) DEFAULT '0',
  `continue_num` int(11) DEFAULT '1',
  `continue_money` int(11) DEFAULT '0',
  `gmt_create` datetime DEFAULT NULL,
  `gmt_update` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of unimall_freight_template_carriage
-- ----------------------------

-- ----------------------------
-- Table structure for unimall_group_shop
-- ----------------------------
DROP TABLE IF EXISTS `unimall_group_shop`;
CREATE TABLE `unimall_group_shop` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `spu_id` bigint(20) NOT NULL,
  `min_price` int(11) NOT NULL,
  `max_price` int(11) NOT NULL,
  `gmt_start` datetime NOT NULL COMMENT '团购开始时间',
  `gmt_end` datetime NOT NULL COMMENT '团购结束时间',
  `minimum_number` int(11) NOT NULL DEFAULT '1' COMMENT '团购基础人数',
  `already_buy_number` int(11) NOT NULL DEFAULT '0' COMMENT '团购已经购买人数',
  `automatic_refund` int(11) NOT NULL DEFAULT '1' COMMENT '团购结束时购买人数未达到基础人数,是否自动退款',
  `status` int(11) NOT NULL COMMENT '判断团购商品是否在活动期间',
  `gmt_create` datetime NOT NULL,
  `gmt_update` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unimall_group_shop_spu_spu_id_uindex` (`spu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of unimall_group_shop
-- ----------------------------
INSERT INTO `unimall_group_shop` VALUES ('25', '1236790', '10', '1', '2019-11-26 00:00:00', '2022-11-26 00:00:00', '1', '0', '1', '1', '2019-11-25 23:47:42', '2019-11-26 00:00:02');
INSERT INTO `unimall_group_shop` VALUES ('26', '1236768', '7200', '16800', '2019-11-29 00:00:00', '2019-11-30 00:00:00', '2', '0', '1', '0', '2019-11-26 00:01:50', '2019-11-26 00:01:50');

-- ----------------------------
-- Table structure for unimall_group_shop_sku
-- ----------------------------
DROP TABLE IF EXISTS `unimall_group_shop_sku`;
CREATE TABLE `unimall_group_shop_sku` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sku_id` bigint(20) NOT NULL,
  `group_shop_id` bigint(20) NOT NULL,
  `sku_group_shop_price` int(11) NOT NULL,
  `gmt_create` datetime NOT NULL,
  `gmt_update` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unimall_group_shop_sku_sku_id_uindex` (`sku_id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of unimall_group_shop_sku
-- ----------------------------
INSERT INTO `unimall_group_shop_sku` VALUES ('49', '2799', '25', '10', '2019-11-26 00:00:02', '2019-11-26 00:00:02');
INSERT INTO `unimall_group_shop_sku` VALUES ('52', '2798', '20', '100', '2019-11-26 00:01:07', '2019-11-26 00:01:07');
INSERT INTO `unimall_group_shop_sku` VALUES ('53', '2774', '26', '7200', '2019-11-26 00:01:50', '2019-11-26 00:01:50');
INSERT INTO `unimall_group_shop_sku` VALUES ('54', '2773', '26', '16800', '2019-11-26 00:01:50', '2019-11-26 00:01:50');

-- ----------------------------
-- Table structure for unimall_img
-- ----------------------------
DROP TABLE IF EXISTS `unimall_img`;
CREATE TABLE `unimall_img` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `biz_type` int(11) NOT NULL,
  `biz_id` bigint(20) NOT NULL,
  `url` varchar(255) NOT NULL,
  `gmt_update` datetime NOT NULL,
  `gmt_create` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3046 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of unimall_img
-- ----------------------------
INSERT INTO `unimall_img` VALUES ('200', '2', '2', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/2e801f1184bf45bc819791f0a7d2f573.png', '2019-04-25 19:15:55', '2019-04-25 19:15:55');
INSERT INTO `unimall_img` VALUES ('201', '2', '1', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/2e801f1184bf45bc819791f0a7d2f573.png', '2019-04-25 19:15:55', '2019-04-25 19:15:55');
INSERT INTO `unimall_img` VALUES ('202', '2', '3', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/2e801f1184bf45bc819791f0a7d2f573.png', '2019-04-25 19:15:55', '2019-04-25 19:15:55');
INSERT INTO `unimall_img` VALUES ('203', '2', '4', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/2e801f1184bf45bc819791f0a7d2f573.png', '2019-04-25 19:15:55', '2019-04-25 19:15:55');
INSERT INTO `unimall_img` VALUES ('204', '2', '5', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/ef1c5af8d1c34582a5bcda0d48ef9437.png', '2019-07-07 00:30:37', '2019-07-07 00:30:37');
INSERT INTO `unimall_img` VALUES ('205', '2', '6', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/ef1c5af8d1c34582a5bcda0d48ef9437.png', '2019-07-07 00:32:02', '2019-07-07 00:32:02');
INSERT INTO `unimall_img` VALUES ('206', '2', '7', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/imgs/3FzdQarcKQr5kkt.png', '2019-07-11 17:49:25', '2019-07-11 17:49:25');
INSERT INTO `unimall_img` VALUES ('208', '2', '9', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/imgs/eMSD6B5D5BEH8zH.jpg', '2019-07-14 09:58:45', '2019-07-14 09:58:45');
INSERT INTO `unimall_img` VALUES ('209', '2', '9', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/imgs/yrmCrRE5FR8XWMH.jpg', '2019-07-14 09:58:45', '2019-07-14 09:58:45');
INSERT INTO `unimall_img` VALUES ('3014', '1', '1236769', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/55c59f82af4e4aefb9f78ce2484b9f36.png', '2019-08-21 12:53:05', '2019-08-21 12:53:05');
INSERT INTO `unimall_img` VALUES ('3015', '1', '1236769', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/8ffee2727cbe4c07ab95cf25ca1946bd.png', '2019-08-21 12:53:05', '2019-08-21 12:53:05');
INSERT INTO `unimall_img` VALUES ('3016', '1', '1236770', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2b86ba9cfc0c40fc82f7741c06270f36.jpg', '2019-08-21 12:53:11', '2019-08-21 12:53:11');
INSERT INTO `unimall_img` VALUES ('3017', '1', '1236770', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a705b7cc22f14d80b5107b9f8b9f7713.jpg', '2019-08-21 12:53:11', '2019-08-21 12:53:11');
INSERT INTO `unimall_img` VALUES ('3018', '1', '1236771', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/86338c9e576342baa0d079bc1caef9cc.jpg', '2019-08-21 12:53:17', '2019-08-21 12:53:17');
INSERT INTO `unimall_img` VALUES ('3019', '1', '1236771', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/3f1e780b9f0c4cde948af3dcfbfe7ece.jpg', '2019-08-21 12:53:17', '2019-08-21 12:53:17');
INSERT INTO `unimall_img` VALUES ('3020', '1', '1236772', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/e48b4f8c1c824e1a9b9f349be0275648.jpg', '2019-08-21 12:53:23', '2019-08-21 12:53:23');
INSERT INTO `unimall_img` VALUES ('3021', '1', '1236772', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/1546107f29274241b539380f9eba41a2.jpg', '2019-08-21 12:53:23', '2019-08-21 12:53:23');
INSERT INTO `unimall_img` VALUES ('3022', '1', '1236772', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/d850d0faea3a406bb057cd1813fa9c78.jpg', '2019-08-21 12:53:23', '2019-08-21 12:53:23');
INSERT INTO `unimall_img` VALUES ('3023', '1', '1236773', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/4f817407a019439da765bf0dc5e7b3b7.jpg', '2019-08-21 12:53:36', '2019-08-21 12:53:36');
INSERT INTO `unimall_img` VALUES ('3024', '1', '1236773', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/435904c4297341839fc388679a550194.jpg', '2019-08-21 12:53:36', '2019-08-21 12:53:36');
INSERT INTO `unimall_img` VALUES ('3025', '1', '1236773', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a9b89d4306fc4134965aabecd125636a.jpg', '2019-08-21 12:53:36', '2019-08-21 12:53:36');
INSERT INTO `unimall_img` VALUES ('3029', '1', '1236776', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/22014e1c20c441949b9e90ef63fa0f64.jpg', '2019-08-21 12:54:02', '2019-08-21 12:54:02');
INSERT INTO `unimall_img` VALUES ('3030', '1', '1236777', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2e6c6eeefaf94fac94fcc1157d2fa037.jpeg', '2019-08-21 12:54:08', '2019-08-21 12:54:08');
INSERT INTO `unimall_img` VALUES ('3033', '1', '1236775', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-23 18:03:42', '2019-08-23 18:03:42');
INSERT INTO `unimall_img` VALUES ('3034', '1', '1236775', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/d424f75591254a5db702dad28467dee8.jpg', '2019-08-23 18:03:42', '2019-08-23 18:03:42');
INSERT INTO `unimall_img` VALUES ('3042', '1', '1236768', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2020-02-14 15:55:10', '2020-02-14 15:55:10');
INSERT INTO `unimall_img` VALUES ('3043', '1', '1236768', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/883b7d8828e24896889530884c76f88f.jpg', '2020-02-14 15:55:10', '2020-02-14 15:55:10');
INSERT INTO `unimall_img` VALUES ('3044', '1', '1236768', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/c5cbc9826fc242b1a251546141014197.jpg', '2020-02-14 15:55:10', '2020-02-14 15:55:10');
INSERT INTO `unimall_img` VALUES ('3045', '1', '1236782', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/34a3a981bcb84f35aec8ceb7ef9a6f6f.png', '2020-02-14 16:37:42', '2020-02-14 16:37:42');

-- ----------------------------
-- Table structure for unimall_order
-- ----------------------------
DROP TABLE IF EXISTS `unimall_order`;
CREATE TABLE `unimall_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `channel` varchar(255) NOT NULL,
  `order_no` varchar(255) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `status` int(11) NOT NULL,
  `sku_original_total_price` int(11) DEFAULT NULL,
  `sku_total_price` int(11) NOT NULL,
  `freight_price` int(11) NOT NULL,
  `coupon_price` int(11) DEFAULT NULL,
  `coupon_id` bigint(20) DEFAULT NULL,
  `group_shop_id` bigint(20) DEFAULT NULL,
  `actual_price` int(11) NOT NULL,
  `pay_price` int(11) DEFAULT NULL,
  `pay_id` varchar(255) DEFAULT NULL,
  `pay_channel` varchar(255) DEFAULT NULL,
  `gmt_pay` datetime DEFAULT NULL,
  `ship_no` varchar(255) DEFAULT NULL,
  `ship_code` varchar(255) DEFAULT NULL,
  `gmt_ship` datetime DEFAULT NULL,
  `gmt_confirm` datetime DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL COMMENT '若province字段为空，表示无需收货地址',
  `city` varchar(255) DEFAULT NULL,
  `county` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `consignee` varchar(255) DEFAULT NULL,
  `mono` varchar(512) DEFAULT NULL,
  `admin_mono_level` int(11) DEFAULT NULL,
  `admin_mono` varchar(255) DEFAULT NULL,
  `refund_reason` varchar(255) DEFAULT NULL,
  `gmt_update` datetime NOT NULL,
  `gmt_create` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_no` (`order_no`(20)) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=355 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of unimall_order
-- ----------------------------
INSERT INTO `unimall_order` VALUES ('2', 'MINI', '1012019071753561001', '2', '40', '16111', '16100', '0', null, null, null, '16100', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-07-21 11:00:00', '2019-07-06 17:53:57');
INSERT INTO `unimall_order` VALUES ('3', 'MINI', '2123', '2', '50', '16111', '16100', '0', null, null, null, '16100', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-07-06 17:53:57', '2019-07-06 17:53:57');
INSERT INTO `unimall_order` VALUES ('4', 'android', '1012019071541321001', '1', '40', '16111', '16100', '0', '500', '1', null, '15600', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-07-22 20:15:00', '2019-07-07 15:41:33');
INSERT INTO `unimall_order` VALUES ('5', 'android', '1012019071736031001', '1', '90', null, '12880', '0', null, null, null, '12880', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-07-21 10:51:00', '2019-07-07 17:36:03');
INSERT INTO `unimall_order` VALUES ('6', 'devtools', '1012019071839591001', '1', '90', null, '3220', '0', null, null, null, '3220', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-07-21 10:51:00', '2019-07-07 18:40:00');
INSERT INTO `unimall_order` VALUES ('7', 'devtools', '1012019071841201002', '1', '90', null, '3220', '0', null, null, null, '3220', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-07-21 10:51:00', '2019-07-07 18:41:21');
INSERT INTO `unimall_order` VALUES ('8', 'devtools', '1012019071945261003', '1', '90', null, '3220', '0', null, null, null, '3220', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-07-21 10:51:00', '2019-07-07 19:45:26');
INSERT INTO `unimall_order` VALUES ('9', 'devtools', '1012019072142171001', '3', '90', '3230', '3220', '0', null, null, null, '3220', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-07-21 10:51:00', '2019-07-07 21:42:17');
INSERT INTO `unimall_order` VALUES ('10', 'devtools', '1012019072144281002', '3', '90', '3230', '3220', '0', null, null, null, '3220', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-07-21 10:51:00', '2019-07-07 21:44:29');
INSERT INTO `unimall_order` VALUES ('11', 'devtools', '1012019072146061003', '3', '90', '3230', '3220', '0', null, null, null, '3220', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-07-21 10:51:00', '2019-07-07 21:46:06');
INSERT INTO `unimall_order` VALUES ('12', 'devtools', '1012019072222121004', '3', '90', '3230', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-07-21 10:51:00', '2019-07-07 22:22:12');
INSERT INTO `unimall_order` VALUES ('13', 'devtools', '1012019071159051001', '3', '70', '3230', '1', '0', null, null, null, '1', '1', '4200000354201907101770772119', 'WX', '2019-07-10 12:33:36', null, null, '2019-07-11 09:40:10', null, null, null, null, null, null, null, null, null, null, null, '2019-07-11 09:54:42', '2019-07-10 11:59:05');
INSERT INTO `unimall_order` VALUES ('14', 'devtools', '1012019071219581001', '3', '40', '3230', '1', '0', null, null, null, '1', '1', '4200000352201907108414424359', 'WX', '2019-07-10 12:20:32', null, null, '2019-07-11 13:21:51', null, null, null, null, null, null, null, null, null, null, null, '2019-07-11 13:22:37', '2019-07-10 12:19:59');
INSERT INTO `unimall_order` VALUES ('15', 'devtools', '1012019071229121002', '3', '70', '3230', '1', '0', null, null, null, '1', '1', '4200000357201907108263498131', 'WX', '2019-07-10 12:29:31', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-07-11 11:20:26', '2019-07-10 12:29:13');
INSERT INTO `unimall_order` VALUES ('16', 'devtools', '1012019071606101001', '3', '50', '6460', '2', '0', null, null, null, '2', '2', '4200000349201907114869333226', 'WX', '2019-07-11 16:07:12', '9895784136995', 'YZPY', '2019-07-11 16:07:59', null, null, null, null, null, null, null, null, null, null, null, '2019-07-11 17:49:25', '2019-07-11 16:06:11');
INSERT INTO `unimall_order` VALUES ('17', 'devtools', '1012019070950031001', '14', '50', '3230', '1', '0', null, null, null, '1', '1', '4200000350201907148369128001', 'WX', '2019-07-14 09:51:55', '123456789', 'SF', '2019-07-14 09:55:41', null, null, null, null, null, null, null, null, null, null, null, '2019-07-14 09:58:45', '2019-07-14 09:50:04');
INSERT INTO `unimall_order` VALUES ('18', 'android', '1012019071747581001', '15', '90', '3230', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-07-21 10:51:00', '2019-07-17 17:47:59');
INSERT INTO `unimall_order` VALUES ('19', 'devtools', '1012019071810461001', '16', '60', '3230', '1', '0', null, null, null, '1', '1', '4200000358201907175668437163', 'WX', '2019-07-18 17:45:31', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-07-17 18:10:47', '2019-07-17 18:10:47');
INSERT INTO `unimall_order` VALUES ('20', 'devtools', '1012019071814101002', '16', '80', '3230', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-07-19 18:39:58', '2019-07-17 18:14:11');
INSERT INTO `unimall_order` VALUES ('21', 'android', '1012019071904591001', '17', '90', '3230', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-07-21 10:51:00', '2019-07-17 19:04:59');
INSERT INTO `unimall_order` VALUES ('22', 'android', '1012019071124551001', '17', '90', '3230', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-07-21 10:51:00', '2019-07-18 11:24:56');
INSERT INTO `unimall_order` VALUES ('23', 'android', '1012019071217371001', '18', '20', '3230', '1', '0', null, null, null, '1', '1', '4200000347201907187364372564', 'WX', '2019-07-18 17:52:30', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-07-18 12:17:38', '2019-07-18 12:17:38');
INSERT INTO `unimall_order` VALUES ('24', 'android', '1012019071500371002', '18', '20', '3230', '1', '0', null, null, null, '1', '1', '4200000347201907187215434899', 'WX', '2019-07-18 15:00:49', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-07-18 15:00:38', '2019-07-18 15:00:38');
INSERT INTO `unimall_order` VALUES ('25', 'android', '1012019071546231001', '1', '20', '3230', '1', '0', null, null, null, '1', '1', '4200000349201907181737293016', 'WX', '2019-07-18 15:47:37', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-07-18 15:46:24', '2019-07-18 15:46:24');
INSERT INTO `unimall_order` VALUES ('26', 'devtools', '1012019071112511001', '16', '90', '3230', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-07-21 10:51:00', '2019-07-20 11:12:52');
INSERT INTO `unimall_order` VALUES ('27', 'devtools', '1012019071119121002', '16', '40', '3230', '1', '0', null, null, null, '1', '1', '4200000356201907204480394207', 'WX', '2019-07-20 11:33:44', null, null, '2019-08-03 11:21:42', null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 11:30:00', '2019-07-20 11:19:12');
INSERT INTO `unimall_order` VALUES ('28', 'devtools', '1012019071125451003', '16', '90', '3230', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-07-21 10:51:00', '2019-07-20 11:25:46');
INSERT INTO `unimall_order` VALUES ('29', 'devtools', '1012019071125461004', '16', '40', '3230', '1', '0', null, null, null, '1', '1', '4200000358201907208799813047', 'WX', '2019-07-20 11:26:12', '73114105363413', 'ZTO', '2019-07-20 11:28:22', null, null, null, null, null, null, null, null, null, null, null, '2019-07-27 15:45:00', '2019-07-20 11:25:46');
INSERT INTO `unimall_order` VALUES ('30', 'devtools', '1012019071214061001', '16', '90', '355', '340', '0', null, null, null, '340', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-07-23 12:30:00', '2019-07-23 12:14:07');
INSERT INTO `unimall_order` VALUES ('31', 'devtools', '1012019072046471001', '16', '90', '4211', '11414', '0', null, null, null, '11414', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-07-27 15:35:00', '2019-07-24 20:46:48');
INSERT INTO `unimall_order` VALUES ('32', 'android', '1012019080123131001', '22', '80', '100', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-04 01:32:47', '2019-08-04 01:23:14');
INSERT INTO `unimall_order` VALUES ('33', 'android', '1012019080124461002', '22', '80', '100', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-04 01:32:45', '2019-08-04 01:24:46');
INSERT INTO `unimall_order` VALUES ('34', 'ios', '1012019080128481003', '21', '90', '100', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-04 01:44:00', '2019-08-04 01:28:49');
INSERT INTO `unimall_order` VALUES ('35', 'android', '1012019080128511004', '22', '80', '1920', '36800', '0', null, null, null, '36800', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-04 01:32:44', '2019-08-04 01:28:51');
INSERT INTO `unimall_order` VALUES ('36', 'android', '1012019080149461005', '22', '90', '100', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-04 02:05:00', '2019-08-04 01:49:46');
INSERT INTO `unimall_order` VALUES ('37', 'android', '1012019080150011006', '22', '90', '1800', '1380', '0', null, null, null, '1380', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-04 02:06:00', '2019-08-04 01:50:02');
INSERT INTO `unimall_order` VALUES ('38', 'android', '1012019080154211007', '22', '90', '2020', '3298', '0', '10', '33', null, '3288', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-04 02:10:00', '2019-08-04 01:54:22');
INSERT INTO `unimall_order` VALUES ('39', 'devtools', '1012019081105391008', '21', '90', '100', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-04 11:21:00', '2019-08-04 11:05:40');
INSERT INTO `unimall_order` VALUES ('40', 'ios', '1012019081118071009', '21', '90', '100', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-04 11:34:00', '2019-08-04 11:18:08');
INSERT INTO `unimall_order` VALUES ('41', 'ios', '1012019082221321001', '21', '20', '100', '1', '0', null, null, null, '1', '1', '4200000345201908068459603910', 'WX', '2019-08-06 22:21:45', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-06 22:21:32', '2019-08-06 22:21:32');
INSERT INTO `unimall_order` VALUES ('42', 'android', '1012019082223471002', '22', '90', '1920', '1600', '0', null, null, null, '1600', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-06 22:39:00', '2019-08-06 22:23:48');
INSERT INTO `unimall_order` VALUES ('43', 'ios', '1012019081601541003', '21', '20', '100', '1', '0', null, null, null, '1', '1', '4200000342201908070393514517', 'WX', '2019-08-07 16:02:07', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-07 16:01:55', '2019-08-07 16:01:55');
INSERT INTO `unimall_order` VALUES ('44', 'android', '1012019081926471001', '23', '20', '100', '1', '0', null, null, null, '1', '1', '4200000342201908080966071497', 'WX', '2019-08-08 19:27:24', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-08 19:26:48', '2019-08-08 19:26:48');
INSERT INTO `unimall_order` VALUES ('45', 'android', '1012019081740401002', '25', '90', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-09 17:56:00', '2019-08-09 17:40:40');
INSERT INTO `unimall_order` VALUES ('46', 'android', '1012019081754381003', '28', '90', '33480', '27900', '0', null, null, null, '27900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-09 18:10:00', '2019-08-09 17:54:38');
INSERT INTO `unimall_order` VALUES ('47', 'android', '1012019081826381004', '30', '90', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-09 18:42:00', '2019-08-09 18:26:39');
INSERT INTO `unimall_order` VALUES ('48', 'android', '1012019081923391005', '36', '90', '33480', '27900', '0', null, null, null, '27900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-09 19:39:00', '2019-08-09 19:23:39');
INSERT INTO `unimall_order` VALUES ('49', 'android', '1012019081929141006', '37', '90', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-09 19:45:00', '2019-08-09 19:29:14');
INSERT INTO `unimall_order` VALUES ('50', 'ios', '1012019082014491007', '38', '90', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-09 20:30:00', '2019-08-09 20:14:49');
INSERT INTO `unimall_order` VALUES ('51', 'android', '1012019082117181008', '41', '90', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-09 21:33:00', '2019-08-09 21:17:18');
INSERT INTO `unimall_order` VALUES ('52', 'android', '1012019082122571009', '42', '90', '54900', '53200', '0', null, null, null, '53200', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-09 21:38:00', '2019-08-09 21:22:58');
INSERT INTO `unimall_order` VALUES ('53', 'android', '1012019082220391010', '46', '90', '1920', '1600', '0', null, null, null, '1600', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-09 22:36:00', '2019-08-09 22:20:39');
INSERT INTO `unimall_order` VALUES ('54', 'android', '1012019082227341011', '47', '90', '100', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-09 22:43:00', '2019-08-09 22:27:35');
INSERT INTO `unimall_order` VALUES ('55', 'android', '1012019082305571012', '48', '90', '57600', '46000', '0', null, null, null, '46000', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-09 23:21:00', '2019-08-09 23:05:57');
INSERT INTO `unimall_order` VALUES ('56', 'ios', '1012019082316141013', '49', '90', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-09 23:32:00', '2019-08-09 23:16:15');
INSERT INTO `unimall_order` VALUES ('57', 'ios', '1012019082316251014', '49', '90', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-09 23:32:00', '2019-08-09 23:16:26');
INSERT INTO `unimall_order` VALUES ('58', 'ios', '1012019082324271015', '50', '90', '100', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-09 23:40:00', '2019-08-09 23:24:28');
INSERT INTO `unimall_order` VALUES ('59', 'android', '1012019082339591016', '52', '90', '100', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-09 23:55:00', '2019-08-09 23:39:59');
INSERT INTO `unimall_order` VALUES ('60', 'android', '1012019082345121017', '53', '90', '100', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 00:01:00', '2019-08-09 23:45:13');
INSERT INTO `unimall_order` VALUES ('61', 'android', '1012019082347461018', '53', '90', '70080', '58400', '0', null, null, null, '58400', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 00:03:00', '2019-08-09 23:47:46');
INSERT INTO `unimall_order` VALUES ('62', 'ios', '1012019080020441019', '21', '20', '100', '1', '0', null, null, null, '1', '1', '4200000348201908106137600991', 'WX', '2019-08-10 00:20:55', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 00:20:45', '2019-08-10 00:20:45');
INSERT INTO `unimall_order` VALUES ('63', 'android', '1012019080022481020', '22', '20', '100', '1', '0', null, null, null, '1', '1', '4200000339201908105775048089', 'WX', '2019-08-10 00:23:00', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 00:22:49', '2019-08-10 00:22:49');
INSERT INTO `unimall_order` VALUES ('64', 'android', '1012019080023281021', '22', '20', '100', '1', '0', null, null, null, '1', '1', '4200000355201908100295307395', 'WX', '2019-08-10 00:23:36', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 00:23:28', '2019-08-10 00:23:28');
INSERT INTO `unimall_order` VALUES ('65', 'android', '1012019080023481022', '22', '90', '100', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 00:39:00', '2019-08-10 00:23:48');
INSERT INTO `unimall_order` VALUES ('66', 'android', '1012019080023481023', '22', '20', '100', '1', '0', null, null, null, '1', '1', '4200000358201908103118560062', 'WX', '2019-08-10 00:23:57', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 00:23:48', '2019-08-10 00:23:48');
INSERT INTO `unimall_order` VALUES ('67', 'ios', '1012019080025061024', '21', '20', '100', '1', '0', null, null, null, '1', '1', '4200000340201908100167678575', 'WX', '2019-08-10 00:25:12', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 00:25:06', '2019-08-10 00:25:06');
INSERT INTO `unimall_order` VALUES ('68', 'android', '1012019080059101025', '55', '90', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 01:15:00', '2019-08-10 00:59:11');
INSERT INTO `unimall_order` VALUES ('69', 'ios', '1012019080625521026', '58', '90', '1800', '1380', '0', null, null, null, '1380', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 06:41:00', '2019-08-10 06:25:53');
INSERT INTO `unimall_order` VALUES ('70', 'android', '1012019080857141027', '59', '90', '1920', '1600', '0', null, null, null, '1600', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 09:13:00', '2019-08-10 08:57:14');
INSERT INTO `unimall_order` VALUES ('71', 'ios', '1012019080915171028', '60', '90', '8640', '7200', '0', null, null, null, '7200', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 09:31:00', '2019-08-10 09:15:18');
INSERT INTO `unimall_order` VALUES ('72', 'ios', '1012019080950121029', '64', '90', '100', '4', '0', null, null, null, '4', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 10:06:00', '2019-08-10 09:50:13');
INSERT INTO `unimall_order` VALUES ('73', 'ios', '1012019080951071030', '64', '90', '54900', '53200', '0', null, null, null, '53200', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 10:07:00', '2019-08-10 09:51:08');
INSERT INTO `unimall_order` VALUES ('74', 'ios', '1012019081056491031', '21', '90', '100', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 11:12:00', '2019-08-10 10:56:49');
INSERT INTO `unimall_order` VALUES ('75', 'ios', '1012019081101281032', '70', '90', '10690', '8900', '0', null, null, null, '8900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 11:17:00', '2019-08-10 11:01:28');
INSERT INTO `unimall_order` VALUES ('76', 'android', '1012019081113561033', '71', '90', '100', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 11:29:00', '2019-08-10 11:13:57');
INSERT INTO `unimall_order` VALUES ('77', 'android', '1012019081114241034', '71', '90', '100', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 11:30:00', '2019-08-10 11:14:25');
INSERT INTO `unimall_order` VALUES ('78', 'android', '1012019081250581035', '71', '90', '100', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 13:06:00', '2019-08-10 12:50:59');
INSERT INTO `unimall_order` VALUES ('79', 'ios', '1012019081313161001', '21', '90', '100', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 13:29:00', '2019-08-10 13:13:16');
INSERT INTO `unimall_order` VALUES ('80', 'ios', '1012019081320281002', '75', '20', '100', '1', '0', null, null, null, '1', '1', '4200000339201908109569144596', 'WX', '2019-08-10 13:20:39', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 13:20:29', '2019-08-10 13:20:29');
INSERT INTO `unimall_order` VALUES ('81', 'android', '1012019081324391003', '71', '20', '100', '1', '0', null, null, null, '1', '1', '4200000345201908100657154680', 'WX', '2019-08-10 13:24:50', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 13:24:40', '2019-08-10 13:24:40');
INSERT INTO `unimall_order` VALUES ('82', 'android', '1012019081325061004', '71', '90', '100', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 13:41:00', '2019-08-10 13:25:06');
INSERT INTO `unimall_order` VALUES ('83', 'android', '1012019081325061005', '71', '90', '100', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 13:41:00', '2019-08-10 13:25:07');
INSERT INTO `unimall_order` VALUES ('84', 'android', '1012019081325191006', '71', '90', '100', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 13:41:00', '2019-08-10 13:25:20');
INSERT INTO `unimall_order` VALUES ('85', 'android', '1012019081325191007', '71', '20', '100', '1', '0', null, null, null, '1', '1', '4200000344201908105242346068', 'WX', '2019-08-10 13:25:30', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 13:25:20', '2019-08-10 13:25:20');
INSERT INTO `unimall_order` VALUES ('86', 'android', '1012019081325391008', '71', '90', '100', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 13:41:00', '2019-08-10 13:25:40');
INSERT INTO `unimall_order` VALUES ('87', 'android', '1012019081325401009', '71', '20', '100', '1', '0', null, null, null, '1', '1', '4200000340201908106814012233', 'WX', '2019-08-10 13:25:49', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 13:25:40', '2019-08-10 13:25:40');
INSERT INTO `unimall_order` VALUES ('88', 'android', '1012019081326051010', '71', '90', '100', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 13:42:00', '2019-08-10 13:26:06');
INSERT INTO `unimall_order` VALUES ('89', 'android', '1012019081326051011', '71', '90', '100', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 13:42:00', '2019-08-10 13:26:06');
INSERT INTO `unimall_order` VALUES ('90', 'android', '1012019081326061012', '71', '40', '100', '1', '0', null, null, null, '1', '1', '4200000342201908105604357493', 'WX', '2019-08-10 13:26:17', '73118089515017', 'ZTO', '2019-08-16 16:05:53', null, null, null, null, null, null, null, null, null, null, null, '2019-08-23 16:15:00', '2019-08-10 13:26:06');
INSERT INTO `unimall_order` VALUES ('91', 'android', '1012019081341451013', '75', '40', '100', '1', '0', null, null, null, '1', '1', '4200000350201908105946089704', 'WX', '2019-08-10 13:42:15', '73118089515017', 'ZTO', '2019-08-16 16:05:51', null, null, null, null, null, null, null, null, null, null, null, '2019-08-23 16:15:00', '2019-08-10 13:41:46');
INSERT INTO `unimall_order` VALUES ('92', 'ios', '1012019081358311014', '76', '90', '8640', '7200', '0', null, null, null, '7200', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 14:14:00', '2019-08-10 13:58:31');
INSERT INTO `unimall_order` VALUES ('93', 'ios', '1012019081426231015', '78', '90', '100', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 14:42:00', '2019-08-10 14:26:23');
INSERT INTO `unimall_order` VALUES ('94', 'ios', '1012019081556001016', '21', '40', '100', '1', '0', null, null, null, '1', '1', '4200000339201908105600698792', 'WX', '2019-08-10 15:56:12', '73118089515017', 'ZTO', '2019-08-16 16:05:50', null, null, null, null, null, null, null, null, null, null, null, '2019-08-23 16:15:00', '2019-08-10 15:56:00');
INSERT INTO `unimall_order` VALUES ('95', 'android', '1012019081559011017', '83', '90', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 16:15:00', '2019-08-10 15:59:02');
INSERT INTO `unimall_order` VALUES ('96', 'android', '1012019081559071018', '83', '90', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 16:15:00', '2019-08-10 15:59:08');
INSERT INTO `unimall_order` VALUES ('97', 'ios', '1012019081641111019', '85', '90', '57600', '46000', '0', null, null, null, '46000', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 16:57:00', '2019-08-10 16:41:12');
INSERT INTO `unimall_order` VALUES ('98', 'ios', '1012019081704471020', '87', '90', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 17:20:00', '2019-08-10 17:04:48');
INSERT INTO `unimall_order` VALUES ('99', 'android', '1012019081717391021', '72', '80', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 17:18:11', '2019-08-10 17:17:39');
INSERT INTO `unimall_order` VALUES ('100', 'android', '1012019081837351022', '88', '90', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 18:53:00', '2019-08-10 18:37:35');
INSERT INTO `unimall_order` VALUES ('101', 'android', '1012019081925591023', '37', '90', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 19:42:00', '2019-08-10 19:26:00');
INSERT INTO `unimall_order` VALUES ('102', 'ios', '1012019082125431024', '92', '90', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 21:41:00', '2019-08-10 21:25:43');
INSERT INTO `unimall_order` VALUES ('103', 'android', '1012019082154151025', '53', '80', '1920', '1600', '0', null, null, null, '1600', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 21:55:54', '2019-08-10 21:54:15');
INSERT INTO `unimall_order` VALUES ('104', 'android', '1012019082154511026', '53', '90', '100', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 22:10:00', '2019-08-10 21:54:51');
INSERT INTO `unimall_order` VALUES ('105', 'ios', '1012019082314351027', '96', '90', '100', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-10 23:30:00', '2019-08-10 23:14:35');
INSERT INTO `unimall_order` VALUES ('106', 'ios', '1012019080034501028', '97', '90', '33480', '27900', '0', null, null, null, '27900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-11 00:50:00', '2019-08-11 00:34:51');
INSERT INTO `unimall_order` VALUES ('107', 'android', '1012019080950091029', '102', '90', '1920', '1600', '0', null, null, null, '1600', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-11 10:06:00', '2019-08-11 09:50:10');
INSERT INTO `unimall_order` VALUES ('108', 'ios', '1012019081228031030', '21', '40', '100', '1', '0', null, null, null, '1', '1', '4200000346201908116412130539', 'WX', '2019-08-11 12:28:14', '73118089515017', 'ZTO', '2019-08-16 16:05:44', null, null, null, null, null, null, null, null, null, null, null, '2019-08-23 16:15:00', '2019-08-11 12:28:04');
INSERT INTO `unimall_order` VALUES ('109', 'ios', '1012019081232101031', '109', '90', '8640', '14400', '0', null, null, null, '14400', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2019-08-11 12:48:00', '2019-08-11 12:32:10');
INSERT INTO `unimall_order` VALUES ('110', 'ios', '1012019081422011001', '104', '90', '1800', '1380', '0', null, null, null, '1380', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-11 14:38:00', '2019-08-11 14:22:01');
INSERT INTO `unimall_order` VALUES ('111', 'ios', '1012019081422101002', '104', '90', '1800', '1380', '0', null, null, null, '1380', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-11 14:38:00', '2019-08-11 14:22:10');
INSERT INTO `unimall_order` VALUES ('112', 'ios', '1012019081446241003', '113', '90', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-11 15:02:00', '2019-08-11 14:46:25');
INSERT INTO `unimall_order` VALUES ('113', 'ios', '1012019081447161004', '113', '90', '100', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-11 15:03:00', '2019-08-11 14:47:17');
INSERT INTO `unimall_order` VALUES ('114', 'ios', '1012019081531121005', '115', '90', '33480', '27900', '0', null, null, null, '27900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-11 15:47:00', '2019-08-11 15:31:12');
INSERT INTO `unimall_order` VALUES ('115', 'ios', '1012019081531421006', '115', '90', '33480', '27900', '0', null, null, null, '27900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-11 15:47:00', '2019-08-11 15:31:42');
INSERT INTO `unimall_order` VALUES ('116', 'ios', '1012019081700191007', '117', '90', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-11 17:16:00', '2019-08-11 17:00:20');
INSERT INTO `unimall_order` VALUES ('117', 'android', '1012019081711001008', '118', '90', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-11 17:27:00', '2019-08-11 17:11:01');
INSERT INTO `unimall_order` VALUES ('118', 'ios', '1012019081748181009', '119', '90', '8640', '7200', '0', null, null, null, '7200', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-11 18:04:00', '2019-08-11 17:48:19');
INSERT INTO `unimall_order` VALUES ('119', 'ios', '1012019082155231010', '120', '90', '33480', '27900', '0', null, null, null, '27900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-11 22:11:00', '2019-08-11 21:55:23');
INSERT INTO `unimall_order` VALUES ('120', 'android', '1012019082351371011', '122', '90', '57600', '46000', '0', null, null, null, '46000', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-12 00:07:00', '2019-08-11 23:51:37');
INSERT INTO `unimall_order` VALUES ('121', 'android', '1012019080109141012', '125', '90', '1800', '2760', '0', null, null, null, '2760', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-12 01:25:00', '2019-08-12 01:09:14');
INSERT INTO `unimall_order` VALUES ('122', 'android', '1012019080813171013', '127', '90', '10690', '8900', '0', null, null, null, '8900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-12 08:29:00', '2019-08-12 08:13:18');
INSERT INTO `unimall_order` VALUES ('123', 'android', '1012019080933221014', '133', '90', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-12 09:49:00', '2019-08-12 09:33:22');
INSERT INTO `unimall_order` VALUES ('124', 'android', '1012019081012051015', '136', '90', '1920', '1600', '0', null, null, null, '1600', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-12 10:28:00', '2019-08-12 10:12:06');
INSERT INTO `unimall_order` VALUES ('125', 'android', '1012019081015011016', '137', '90', '10690', '8900', '0', null, null, null, '8900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-12 10:31:00', '2019-08-12 10:15:02');
INSERT INTO `unimall_order` VALUES ('126', 'android', '1012019081358591017', '142', '90', '56820', '54800', '0', null, null, null, '54800', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-12 14:14:00', '2019-08-12 13:58:59');
INSERT INTO `unimall_order` VALUES ('127', 'ios', '1012019081448221018', '146', '90', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-12 15:04:00', '2019-08-12 14:48:22');
INSERT INTO `unimall_order` VALUES ('128', 'ios', '1012019081553031019', '44', '90', '8640', '7200', '0', null, null, null, '7200', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-12 16:09:00', '2019-08-12 15:53:04');
INSERT INTO `unimall_order` VALUES ('129', 'android', '1012019081651501020', '150', '90', '8640', '7200', '0', null, null, null, '7200', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-12 17:07:00', '2019-08-12 16:51:51');
INSERT INTO `unimall_order` VALUES ('130', 'devtools', '1012019081656061001', '21', '40', '54900', '53200', '0', null, null, null, '53200', null, null, 'OFFLINE', null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-19 17:15:00', '2019-08-12 16:56:06');
INSERT INTO `unimall_order` VALUES ('131', 'android', '1012019081845191001', '155', '90', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-12 19:01:00', '2019-08-12 18:45:20');
INSERT INTO `unimall_order` VALUES ('132', 'ios', '1012019081852041002', '156', '90', '100', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-12 19:08:00', '2019-08-12 18:52:04');
INSERT INTO `unimall_order` VALUES ('133', 'ios', '1012019082034411003', '160', '90', '1920', '1600', '0', null, null, null, '1600', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-12 20:50:00', '2019-08-12 20:34:41');
INSERT INTO `unimall_order` VALUES ('134', 'android', '1012019082045211004', '161', '90', '33480', '27900', '0', null, null, null, '27900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-12 21:01:00', '2019-08-12 20:45:21');
INSERT INTO `unimall_order` VALUES ('135', 'android', '1012019080844091005', '162', '80', '34680', '86700', '0', null, null, null, '86700', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-13 08:44:29', '2019-08-13 08:44:09');
INSERT INTO `unimall_order` VALUES ('136', 'ios', '1012019080901591006', '163', '90', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-13 09:17:00', '2019-08-13 09:01:59');
INSERT INTO `unimall_order` VALUES ('137', 'ios', '1012019081055311007', '169', '90', '8640', '7200', '0', null, null, null, '7200', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-13 11:11:00', '2019-08-13 10:55:31');
INSERT INTO `unimall_order` VALUES ('138', 'ios', '1012019081102051008', '170', '90', '38800', '18800', '0', null, null, null, '18800', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-13 11:18:00', '2019-08-13 11:02:06');
INSERT INTO `unimall_order` VALUES ('139', 'android', '1012019081138141009', '171', '90', '57700', '46001', '0', null, null, null, '46001', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-13 11:54:00', '2019-08-13 11:38:15');
INSERT INTO `unimall_order` VALUES ('140', 'android', '1012019081203471010', '173', '90', '100', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-13 12:19:00', '2019-08-13 12:03:47');
INSERT INTO `unimall_order` VALUES ('141', 'android', '1012019081313331011', '174', '90', '57600', '46000', '0', null, null, null, '46000', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-13 13:29:00', '2019-08-13 13:13:34');
INSERT INTO `unimall_order` VALUES ('142', 'android', '1012019081321491012', '175', '90', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-13 13:37:00', '2019-08-13 13:21:50');
INSERT INTO `unimall_order` VALUES ('143', 'ios', '1012019081402261013', '177', '90', '1800', '1380', '0', null, null, null, '1380', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-13 14:18:00', '2019-08-13 14:02:26');
INSERT INTO `unimall_order` VALUES ('144', 'ios', '1012019081402261014', '177', '90', '1800', '1380', '0', null, null, null, '1380', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-13 14:18:00', '2019-08-13 14:02:26');
INSERT INTO `unimall_order` VALUES ('145', 'ios', '1012019081402261015', '177', '90', '1800', '1380', '0', null, null, null, '1380', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-13 14:18:00', '2019-08-13 14:02:26');
INSERT INTO `unimall_order` VALUES ('146', 'ios', '1012019081429201016', '179', '90', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-13 14:45:00', '2019-08-13 14:29:21');
INSERT INTO `unimall_order` VALUES ('147', 'android', '1012019081530491017', '181', '90', '54900', '53200', '0', null, null, null, '53200', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-13 15:46:00', '2019-08-13 15:30:50');
INSERT INTO `unimall_order` VALUES ('148', 'ios', '1012019081641391018', '182', '90', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-13 16:57:00', '2019-08-13 16:41:40');
INSERT INTO `unimall_order` VALUES ('149', 'android', '1012019082158371019', '175', '90', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-13 22:14:00', '2019-08-13 21:58:37');
INSERT INTO `unimall_order` VALUES ('150', 'android', '1012019080941131020', '66', '90', '54900', '53200', '0', null, null, null, '53200', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-14 09:57:00', '2019-08-14 09:41:14');
INSERT INTO `unimall_order` VALUES ('151', 'ios', '1012019081135351021', '188', '90', '100', '2', '0', null, null, null, '2', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-14 11:51:00', '2019-08-14 11:35:36');
INSERT INTO `unimall_order` VALUES ('152', 'android', '1012019081453401022', '191', '90', '10440', '8580', '0', null, null, null, '8580', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-14 15:09:00', '2019-08-14 14:53:41');
INSERT INTO `unimall_order` VALUES ('153', 'ios', '1012019082013261023', '199', '90', '92280', '74900', '0', null, null, null, '74900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-15 20:29:00', '2019-08-15 20:13:27');
INSERT INTO `unimall_order` VALUES ('154', 'ios', '1012019082042241024', '200', '90', '54900', '53200', '0', null, null, null, '53200', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-15 20:58:00', '2019-08-15 20:42:25');
INSERT INTO `unimall_order` VALUES ('155', 'android', '1012019081320261025', '209', '40', '38800', '18800', '0', null, null, null, '18800', '18800', '4200000346201908165828879725', 'WX', '2019-08-16 13:20:41', '73118089515017', 'ZTO', '2019-08-16 16:05:27', null, null, null, null, null, null, null, '', null, null, null, '2019-08-23 16:15:00', '2019-08-16 13:20:27');
INSERT INTO `unimall_order` VALUES ('156', 'devtools', '1012019082214491026', '21', '90', '3820', '2982', '0', null, null, null, '2982', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-16 22:30:00', '2019-08-16 22:14:50');
INSERT INTO `unimall_order` VALUES ('158', 'ios', '1012019081135271028', '21', '60', '100', '1', '0', null, null, null, '1', '1', '4200000341201908176903633386', 'WX', '2019-08-17 11:35:40', null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-20 22:36:29', '2019-08-17 11:35:28');
INSERT INTO `unimall_order` VALUES ('159', 'android', '1012019081204101029', '223', '90', '100', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-17 12:20:00', '2019-08-17 12:04:11');
INSERT INTO `unimall_order` VALUES ('160', 'ios', '1012019081233311030', '226', '90', '1920', '1600', '0', null, null, null, '1600', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-17 12:49:00', '2019-08-17 12:33:32');
INSERT INTO `unimall_order` VALUES ('161', 'ios', '1012019081239191031', '226', '90', '59520', '49200', '0', null, null, null, '49200', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-17 12:55:00', '2019-08-17 12:39:20');
INSERT INTO `unimall_order` VALUES ('162', 'ios', '1012019081346431032', '228', '80', '57600', '46000', '0', null, null, null, '46000', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-17 13:47:15', '2019-08-17 13:46:43');
INSERT INTO `unimall_order` VALUES ('163', 'ios', '1012019081414221033', '226', '90', '33480', '27900', '0', '100', '50', null, '27800', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-17 14:30:00', '2019-08-17 14:14:23');
INSERT INTO `unimall_order` VALUES ('164', 'ios', '1012019081654341034', '87', '60', '1920', '1600', '0', null, null, null, '1600', null, null, 'OFFLINE', null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-17 16:54:42', '2019-08-17 16:54:35');
INSERT INTO `unimall_order` VALUES ('165', 'android', '1012019081127371035', '237', '20', '57600', '46000', '0', null, null, null, '46000', null, null, 'OFFLINE', null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-18 11:27:49', '2019-08-18 11:27:37');
INSERT INTO `unimall_order` VALUES ('166', 'ios', '1012019081612351036', '240', '90', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-18 16:28:00', '2019-08-18 16:12:35');
INSERT INTO `unimall_order` VALUES ('167', 'ios', '1012019081036041037', '241', '60', '8640', '7200', '0', null, null, null, '7200', null, null, 'OFFLINE', null, null, null, null, null, null, null, null, null, null, null, '哈哈哈', null, null, null, '2019-08-19 10:36:10', '2019-08-19 10:36:05');
INSERT INTO `unimall_order` VALUES ('168', 'ios', '1012019081036291038', '241', '90', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-19 10:52:00', '2019-08-19 10:36:30');
INSERT INTO `unimall_order` VALUES ('169', 'android', '1012019082315581039', '248', '80', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-19 23:16:51', '2019-08-19 23:15:59');
INSERT INTO `unimall_order` VALUES ('170', 'ios', '1012019082348541040', '62', '20', '99900', '68800', '0', null, null, null, '68800', null, null, 'OFFLINE', null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-19 23:49:01', '2019-08-19 23:48:55');
INSERT INTO `unimall_order` VALUES ('173', 'ios', '1012019081240341003', '21', '90', '38800', '18800', '0', null, null, null, '18800', null, null, null, null, null, null, null, null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2019-08-21 12:56:00', '2019-08-21 12:40:35');
INSERT INTO `unimall_order` VALUES ('176', 'ios', '1012019081254171006', '21', '70', '100', '1', '0', null, null, null, '1', '1', '4200000347201908210269177193', 'WX', '2019-08-21 12:54:32', null, null, '2019-08-21 12:54:44', null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2019-08-21 13:02:11', '2019-08-21 12:54:18');
INSERT INTO `unimall_order` VALUES ('177', 'android', '1012019081401201001', '256', '90', '38800', '18800', '0', null, null, null, '18800', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-21 14:17:00', '2019-08-21 14:01:20');
INSERT INTO `unimall_order` VALUES ('178', 'android', '1012019081433061002', '203', '20', '38800', '18800', '0', null, null, null, '18800', null, null, 'OFFLINE', null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-21 14:33:13', '2019-08-21 14:33:06');
INSERT INTO `unimall_order` VALUES ('179', 'android', '1012019080920031003', '260', '20', '100', '1', '0', null, null, null, '1', '1', '4200000355201908224093758665', 'WX', '2019-08-22 09:20:16', null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-22 09:20:04', '2019-08-22 09:20:04');
INSERT INTO `unimall_order` VALUES ('180', 'ios', '1012019081002331004', '21', '20', '100', '1', '0', null, null, null, '1', '1', '4200000344201908224787571382', 'WX', '2019-08-22 10:02:43', null, null, null, null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2019-08-22 10:02:34', '2019-08-22 10:02:34');
INSERT INTO `unimall_order` VALUES ('181', 'android', '1012019081049411005', '229', '90', '38800', '18800', '0', null, null, null, '18800', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-22 11:05:00', '2019-08-22 10:49:42');
INSERT INTO `unimall_order` VALUES ('182', 'android', '1012019081356031006', '265', '90', '33480', '27900', '0', null, null, null, '27900', null, null, null, null, null, null, null, null, '浙江省', '温州市', '洞头县', '骨灰盒', '13856232563', '黑胡椒', '', null, null, null, '2019-08-22 14:12:00', '2019-08-22 13:56:04');
INSERT INTO `unimall_order` VALUES ('183', 'ios', '1012019081518481007', '266', '20', '100', '1', '0', null, null, null, '1', null, null, 'OFFLINE', null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-22 15:19:05', '2019-08-22 15:18:49');
INSERT INTO `unimall_order` VALUES ('184', 'android', '1012019081645121008', '267', '90', '38800', '18800', '0', '100', '50', null, '18700', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-22 17:01:00', '2019-08-22 16:45:13');
INSERT INTO `unimall_order` VALUES ('185', 'android', '1012019081645261009', '267', '80', '54900', '53200', '0', null, null, null, '53200', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-22 16:46:41', '2019-08-22 16:45:26');
INSERT INTO `unimall_order` VALUES ('186', 'android', '1012019080607401010', '259', '90', '8640', '7200', '0', null, null, null, '7200', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-23 06:23:00', '2019-08-23 06:07:40');
INSERT INTO `unimall_order` VALUES ('187', 'ios', '1012019081037301011', '21', '20', '100', '1', '0', null, null, null, '1', '1', '4200000340201908237033303701', 'WX', '2019-08-23 10:37:41', null, null, null, null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2019-08-23 10:37:30', '2019-08-23 10:37:30');
INSERT INTO `unimall_order` VALUES ('188', 'android', '1012019081313141012', '275', '20', '34680', '28900', '0', null, null, null, '28900', null, null, 'OFFLINE', null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-23 13:13:26', '2019-08-23 13:13:14');
INSERT INTO `unimall_order` VALUES ('189', 'android', '1012019081320141013', '275', '90', '54900', '106400', '0', null, null, null, '106400', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-23 13:36:00', '2019-08-23 13:20:15');
INSERT INTO `unimall_order` VALUES ('190', 'android', '1012019081320551014', '275', '20', '38800', '18800', '0', null, null, null, '18800', null, null, 'OFFLINE', null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-23 13:20:59', '2019-08-23 13:20:56');
INSERT INTO `unimall_order` VALUES ('191', 'android', '1012019081336411015', '276', '90', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-23 13:52:00', '2019-08-23 13:36:42');
INSERT INTO `unimall_order` VALUES ('192', 'ios', '1012019081509041016', '21', '90', '100', '1', '0', null, null, null, '20999', null, null, null, null, null, null, null, null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2019-08-23 15:25:00', '2019-08-23 15:09:04');
INSERT INTO `unimall_order` VALUES ('193', 'ios', '1012019081517141017', '21', '90', '100', '1', '0', null, null, null, '20901', null, null, null, null, null, null, null, null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2019-08-23 15:33:00', '2019-08-23 15:17:14');
INSERT INTO `unimall_order` VALUES ('194', 'ios', '1012019081527391018', '21', '90', '100', '1', '0', null, null, null, '20901', null, null, null, null, null, null, null, null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2019-08-23 15:43:00', '2019-08-23 15:27:40');
INSERT INTO `unimall_order` VALUES ('195', 'ios', '1012019081534011019', '21', '90', '100', '1', '0', null, null, null, '20901', null, null, null, null, null, null, null, null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2019-08-23 15:50:00', '2019-08-23 15:34:02');
INSERT INTO `unimall_order` VALUES ('196', 'ios', '1012019081540241001', '21', '90', '100', '1', '20900', null, null, null, '20901', null, null, null, null, null, null, null, null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2019-08-23 15:56:00', '2019-08-23 15:40:24');
INSERT INTO `unimall_order` VALUES ('197', 'android', '1012019081627151002', '22', '90', '100', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-23 16:43:00', '2019-08-23 16:27:15');
INSERT INTO `unimall_order` VALUES ('198', 'android', '1012019081627521003', '22', '90', '100', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-23 16:43:00', '2019-08-23 16:27:53');
INSERT INTO `unimall_order` VALUES ('199', 'ios', '1012019081628211004', '21', '90', '100', '1', '20900', null, null, null, '20901', null, null, null, null, null, null, null, null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2019-08-23 16:44:00', '2019-08-23 16:28:21');
INSERT INTO `unimall_order` VALUES ('200', 'ios', '1012019081637141005', '21', '90', '100', '1', '20900', null, null, null, '20901', null, null, null, null, null, null, null, null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2019-08-23 16:53:00', '2019-08-23 16:37:15');
INSERT INTO `unimall_order` VALUES ('201', 'android', '1012019081646101006', '22', '90', '100', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-23 17:02:00', '2019-08-23 16:46:10');
INSERT INTO `unimall_order` VALUES ('202', 'android', '1012019081646421007', '22', '90', '100', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-23 17:02:00', '2019-08-23 16:46:43');
INSERT INTO `unimall_order` VALUES ('203', 'ios', '1012019081657041008', '21', '90', '100', '1', '20900', null, null, null, '20901', null, null, null, null, null, null, null, null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2019-08-23 17:13:00', '2019-08-23 16:57:05');
INSERT INTO `unimall_order` VALUES ('204', 'android', '1012019081701401009', '22', '90', '100', '1', '20900', null, null, null, '20901', null, null, null, null, null, null, null, null, '重庆', '重庆市', '北碚区', '弄', '18883165287', '匡柏权', '', null, null, null, '2019-08-23 17:17:00', '2019-08-23 17:01:40');
INSERT INTO `unimall_order` VALUES ('205', 'android', '1012019081802511001', '22', '90', '100', '1', '21000', null, null, null, '21001', null, null, null, null, null, null, null, null, '重庆', '重庆市', '北碚区', '弄', '18883165287', '匡柏权', '', null, null, null, '2019-08-23 18:18:00', '2019-08-23 18:02:51');
INSERT INTO `unimall_order` VALUES ('206', 'android', '1012019081804391002', '22', '90', '100', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, '重庆', '重庆市', '北碚区', '弄', '18883165287', '匡柏权', '', null, null, null, '2019-08-23 18:20:00', '2019-08-23 18:04:39');
INSERT INTO `unimall_order` VALUES ('207', 'ios', '1012019081005291003', '21', '20', '100', '1', '0', null, null, null, '1', '1', '4200000356201908249708468787', 'WX', '2019-08-24 10:05:40', null, null, null, null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2019-08-24 10:05:30', '2019-08-24 10:05:30');
INSERT INTO `unimall_order` VALUES ('208', 'devtools', '1012019080748481004', '285', '90', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, '北京', '北京市', '西城区', '都城大厦1515', '13810819020', '李', '', null, null, null, '2019-08-25 08:04:00', '2019-08-25 07:48:48');
INSERT INTO `unimall_order` VALUES ('209', 'ios', '1012019081051141005', '21', '20', '100', '1', '0', null, null, null, '1', '1', '4200000347201908251820538182', 'WX', '2019-08-25 10:51:27', null, null, null, null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2019-08-25 10:51:15', '2019-08-25 10:51:15');
INSERT INTO `unimall_order` VALUES ('210', 'ios', '1012019081554461006', '286', '20', '54900', '53200', '0', null, null, null, '53200', null, null, 'OFFLINE', null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-25 15:54:52', '2019-08-25 15:54:46');
INSERT INTO `unimall_order` VALUES ('211', 'android', '1012019081038291007', '287', '90', '34680', '28900', '0', '100', '50', null, '28800', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-26 10:54:00', '2019-08-26 10:38:30');
INSERT INTO `unimall_order` VALUES ('212', 'android', '1012019081407531008', '289', '80', '57600', '46000', '0', null, null, null, '46000', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-26 14:11:32', '2019-08-26 14:07:54');
INSERT INTO `unimall_order` VALUES ('213', 'android', '1012019082057501009', '285', '90', '1920', '1600', '0', null, null, null, '1600', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-26 21:13:00', '2019-08-26 20:57:51');
INSERT INTO `unimall_order` VALUES ('214', 'android', '1012019081045511010', '287', '20', '54900', '53200', '0', '100', '50', null, '53100', null, null, 'OFFLINE', null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-27 10:46:04', '2019-08-27 10:45:52');
INSERT INTO `unimall_order` VALUES ('215', 'ios', '1012019081927321011', '21', '20', '100', '1', '0', null, null, null, '1', '1', '4200000358201908275325343588', 'WX', '2019-08-27 19:27:43', null, null, null, null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2019-08-27 19:27:32', '2019-08-27 19:27:32');
INSERT INTO `unimall_order` VALUES ('216', 'android', '1012019082143111012', '305', '20', '100', '1', '0', null, null, null, '1', null, null, 'OFFLINE', null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-28 21:43:19', '2019-08-28 21:43:11');
INSERT INTO `unimall_order` VALUES ('217', 'android', '1012019082346361013', '307', '90', '1920', '1600', '0', null, null, null, '1600', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-29 00:02:00', '2019-08-28 23:46:36');
INSERT INTO `unimall_order` VALUES ('218', 'android', '1012019080144591014', '308', '90', '100', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-29 02:00:00', '2019-08-29 01:44:59');
INSERT INTO `unimall_order` VALUES ('219', 'android', '1012019080943401015', '308', '90', '100', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-29 09:59:00', '2019-08-29 09:43:41');
INSERT INTO `unimall_order` VALUES ('220', 'android', '1012019081354211016', '303', '80', '54900', '53200', '0', null, null, null, '53200', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-29 13:54:54', '2019-08-29 13:54:22');
INSERT INTO `unimall_order` VALUES ('221', 'ios', '1012019081503471017', '21', '20', '100', '1', '0', null, null, null, '1', '1', '4200000349201908290008387652', 'WX', '2019-08-29 15:03:54', null, null, null, null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2019-08-29 15:03:48', '2019-08-29 15:03:48');
INSERT INTO `unimall_order` VALUES ('222', 'android', '1012019081627481018', '314', '90', '10690', '8900', '0', '100', '50', null, '8800', null, null, null, null, null, null, null, null, '山东省', '青岛市', '市北区', '测试', '17878787878', '玫瑰', '', null, null, null, '2019-08-29 16:43:00', '2019-08-29 16:27:49');
INSERT INTO `unimall_order` VALUES ('223', 'ios', '1012019081719261019', '311', '20', '100', '2', '0', null, null, null, '2', null, null, 'OFFLINE', null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-29 17:19:40', '2019-08-29 17:19:26');
INSERT INTO `unimall_order` VALUES ('224', 'ios', '1012019080825511020', '21', '20', '100', '1', '0', null, null, null, '1', '1', '4200000344201908307381782224', 'WX', '2019-08-30 08:26:01', null, null, null, null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2019-08-30 08:25:52', '2019-08-30 08:25:52');
INSERT INTO `unimall_order` VALUES ('225', 'android', '1012019082220381021', '321', '90', '10690', '8900', '0', null, null, null, '8900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-30 22:36:00', '2019-08-30 22:20:39');
INSERT INTO `unimall_order` VALUES ('226', 'ios', '1012019080847231022', '285', '90', '10690', '8900', '0', null, null, null, '8900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-31 09:03:00', '2019-08-31 08:47:23');
INSERT INTO `unimall_order` VALUES ('227', 'ios', '1012019080927211023', '285', '90', '1800', '1380', '0', null, null, null, '1380', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-31 09:43:00', '2019-08-31 09:27:22');
INSERT INTO `unimall_order` VALUES ('228', 'ios', '1012019080927391024', '285', '90', '1800', '1380', '0', null, null, null, '1380', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-08-31 09:43:00', '2019-08-31 09:27:40');
INSERT INTO `unimall_order` VALUES ('229', 'ios', '1012019081020341025', '21', '20', '100', '1', '0', null, null, null, '1', '1', '4200000351201908313417957680', 'WX', '2019-08-31 10:20:41', null, null, null, null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2019-08-31 10:20:34', '2019-08-31 10:20:34');
INSERT INTO `unimall_order` VALUES ('230', 'android', '1012019081458451026', '329', '40', '38800', '18800', '0', null, null, null, '18800', null, null, 'OFFLINE', null, null, null, '2019-09-04 14:44:48', null, null, null, null, null, null, null, '', null, null, null, '2019-09-11 14:45:00', '2019-08-31 14:58:45');
INSERT INTO `unimall_order` VALUES ('231', 'ios', '1012019081858001027', '331', '40', '33480', '27900', '0', null, null, null, '27900', null, null, 'OFFLINE', null, null, null, '2019-09-04 14:44:47', null, null, null, null, null, null, null, '', null, null, null, '2019-09-11 14:45:00', '2019-08-31 18:58:00');
INSERT INTO `unimall_order` VALUES ('232', 'ios', '1012019091633051028', '21', '40', '100', '1', '0', null, null, null, '1', '1', '4200000354201909015762739257', 'WX', '2019-09-01 16:33:16', null, null, '2019-09-04 14:44:45', null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2019-09-11 14:45:00', '2019-09-01 16:33:06');
INSERT INTO `unimall_order` VALUES ('233', 'ios', '1012019090722481029', '336', '50', '1800', '1380', '0', null, null, null, '1380', null, null, 'OFFLINE', null, null, null, '2019-09-04 14:44:44', null, null, null, null, null, null, null, '', null, null, null, '2019-09-08 11:02:30', '2019-09-02 07:22:49');
INSERT INTO `unimall_order` VALUES ('234', 'ios', '1012019090958341030', '21', '40', '100', '1', '0', null, null, null, '1', '1', '4200000346201909022406245255', 'WX', '2019-09-02 09:58:44', null, null, '2019-09-04 14:44:42', null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2019-09-11 14:45:00', '2019-09-02 09:58:34');
INSERT INTO `unimall_order` VALUES ('235', 'android', '1012019091029481031', '338', '90', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-02 10:45:00', '2019-09-02 10:29:49');
INSERT INTO `unimall_order` VALUES ('236', 'android', '1012019091029491032', '338', '90', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-02 10:45:00', '2019-09-02 10:29:49');
INSERT INTO `unimall_order` VALUES ('237', 'android', '1012019091031431033', '339', '90', '10690', '8900', '0', '100', '50', null, '8800', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-02 10:47:00', '2019-09-02 10:31:43');
INSERT INTO `unimall_order` VALUES ('238', 'android', '1012019091048261034', '340', '90', '54900', '53200', '0', null, null, null, '53200', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-02 11:04:00', '2019-09-02 10:48:26');
INSERT INTO `unimall_order` VALUES ('239', 'android', '1012019091237021035', '338', '40', '34680', '28900', '0', null, null, null, '28900', null, null, 'OFFLINE', null, null, null, '2019-09-04 14:44:41', null, null, null, null, null, null, null, '', null, null, null, '2019-09-11 14:45:00', '2019-09-02 12:37:02');
INSERT INTO `unimall_order` VALUES ('240', 'ios', '1012019091644231036', '344', '90', '38800', '18800', '0', null, null, null, '18800', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-02 17:00:00', '2019-09-02 16:44:23');
INSERT INTO `unimall_order` VALUES ('241', 'ios', '1012019091045131037', '21', '40', '100', '1', '0', null, null, null, '1', '1', '4200000345201909039461953951', 'WX', '2019-09-03 10:45:20', null, null, '2019-09-04 14:44:37', null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2019-09-11 14:45:00', '2019-09-03 10:45:13');
INSERT INTO `unimall_order` VALUES ('242', 'android', '1012019091407241038', '118', '90', '99900', '68800', '0', null, null, null, '68800', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-03 14:23:00', '2019-09-03 14:07:24');
INSERT INTO `unimall_order` VALUES ('243', 'ios', '1012019091735101039', '156', '40', '34680', '28900', '0', '100', '50', null, '28800', null, null, 'OFFLINE', null, null, null, '2019-09-04 14:44:35', null, null, null, null, null, null, null, '', null, null, null, '2019-09-11 14:45:00', '2019-09-03 17:35:11');
INSERT INTO `unimall_order` VALUES ('244', 'android', '1012019090849571040', '354', '90', '1800', '1380', '0', null, null, null, '1380', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-04 09:05:00', '2019-09-04 08:49:57');
INSERT INTO `unimall_order` VALUES ('245', 'android', '1012019090850381041', '354', '40', '1800', '2760', '0', null, null, null, '2760', null, null, 'OFFLINE', null, null, null, '2019-09-04 14:44:33', null, null, null, null, null, null, null, '', null, null, null, '2019-09-11 14:45:00', '2019-09-04 08:50:38');
INSERT INTO `unimall_order` VALUES ('246', 'android', '1012019091412441042', '355', '80', '1800', '1380', '0', null, null, null, '1380', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-04 14:14:26', '2019-09-04 14:12:45');
INSERT INTO `unimall_order` VALUES ('247', 'android', '1012019091412451043', '355', '90', '1800', '1380', '0', null, null, null, '1380', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-04 14:28:00', '2019-09-04 14:12:45');
INSERT INTO `unimall_order` VALUES ('248', 'android', '1012019091435191044', '75', '90', '100', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-04 14:51:00', '2019-09-04 14:35:20');
INSERT INTO `unimall_order` VALUES ('249', 'android', '1012019091443041045', '75', '40', '38800', '18800', '0', null, null, null, '18800', null, null, 'OFFLINE', null, null, null, '2019-09-04 14:44:32', null, null, null, null, null, null, null, '', null, null, null, '2019-09-11 14:45:00', '2019-09-04 14:43:05');
INSERT INTO `unimall_order` VALUES ('250', 'ios', '1012019091445131046', '21', '20', '100', '1', '0', null, null, null, '1', '1', '4200000358201909043148664505', 'WX', '2019-09-04 14:45:24', null, null, null, null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2019-09-04 14:45:14', '2019-09-04 14:45:14');
INSERT INTO `unimall_order` VALUES ('251', 'android', '1012019091446031047', '75', '90', '100', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-04 15:02:00', '2019-09-04 14:46:04');
INSERT INTO `unimall_order` VALUES ('252', 'ios', '1012019091530351048', '356', '90', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-04 15:46:00', '2019-09-04 15:30:35');
INSERT INTO `unimall_order` VALUES ('253', 'android', '1012019091738501049', '360', '90', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-05 17:54:00', '2019-09-05 17:38:51');
INSERT INTO `unimall_order` VALUES ('254', 'android', '1012019091918381050', '361', '90', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-05 19:34:00', '2019-09-05 19:18:38');
INSERT INTO `unimall_order` VALUES ('255', 'android', '1012019092258001051', '215', '90', '66240', '53200', '0', null, null, null, '53200', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-05 23:14:00', '2019-09-05 22:58:01');
INSERT INTO `unimall_order` VALUES ('256', 'android', '1012019090950101052', '363', '20', '114520', '100801', '0', '100', '50', null, '100701', null, null, 'OFFLINE', null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-06 09:50:21', '2019-09-06 09:50:11');
INSERT INTO `unimall_order` VALUES ('257', 'android', '1012019091107481053', '366', '90', '8640', '7200', '0', null, null, null, '7200', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-06 11:23:00', '2019-09-06 11:07:49');
INSERT INTO `unimall_order` VALUES ('258', 'android', '1012019091108241054', '366', '90', '100', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-06 11:24:00', '2019-09-06 11:08:25');
INSERT INTO `unimall_order` VALUES ('259', 'ios', '1012019091153291055', '21', '40', '100', '2', '0', null, null, null, '2', '2', '4200000409201909063569575259', 'WX', '2019-09-06 11:53:36', null, null, '2019-09-10 13:45:50', null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2019-09-17 14:00:00', '2019-09-06 11:53:30');
INSERT INTO `unimall_order` VALUES ('260', 'ios', '1012019091202581056', '367', '90', '54900', '53200', '0', null, null, null, '53200', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-06 12:18:00', '2019-09-06 12:02:58');
INSERT INTO `unimall_order` VALUES ('261', 'ios', '1012019091203531057', '367', '90', '57600', '46000', '0', '100', '50', null, '45900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '很多话', null, null, null, '2019-09-06 12:19:00', '2019-09-06 12:03:53');
INSERT INTO `unimall_order` VALUES ('262', 'android', '1012019091414351058', '355', '90', '100', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, '浙江省', '杭州市', '萧山区', '测试用', '18758186347', '测试', '', null, null, null, '2019-09-06 14:30:00', '2019-09-06 14:14:36');
INSERT INTO `unimall_order` VALUES ('263', 'android', '1012019091453571059', '369', '90', '54900', '53200', '0', null, null, null, '53200', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-06 15:09:00', '2019-09-06 14:53:58');
INSERT INTO `unimall_order` VALUES ('264', 'android', '1012019091454051060', '369', '40', '54900', '53200', '0', null, null, null, '53200', null, null, 'OFFLINE', null, null, null, '2019-09-10 13:45:47', null, null, null, null, null, null, null, '', null, null, null, '2019-09-17 14:00:00', '2019-09-06 14:54:05');
INSERT INTO `unimall_order` VALUES ('265', 'android', '1012019091511091061', '359', '90', '38800', '18800', '0', null, null, null, '18800', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-06 15:27:00', '2019-09-06 15:11:09');
INSERT INTO `unimall_order` VALUES ('266', 'ios', '1012019090820211062', '21', '40', '100', '1', '0', null, null, null, '1', '1', '4200000406201909070108061313', 'WX', '2019-09-07 08:20:28', null, null, '2019-09-10 13:45:46', null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2019-09-17 14:00:00', '2019-09-07 08:20:22');
INSERT INTO `unimall_order` VALUES ('267', 'ios', '1012019090935471063', '367', '90', '54900', '53200', '0', null, null, null, '53200', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-07 09:51:00', '2019-09-07 09:35:48');
INSERT INTO `unimall_order` VALUES ('268', 'ios', '1012019090936131064', '367', '90', '89580', '82100', '0', null, null, null, '82100', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-07 09:52:00', '2019-09-07 09:36:14');
INSERT INTO `unimall_order` VALUES ('269', 'ios', '1012019090938431065', '367', '90', '10690', '8900', '0', null, null, null, '8900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-07 09:54:00', '2019-09-07 09:38:44');
INSERT INTO `unimall_order` VALUES ('270', 'ios', '1012019091009031066', '367', '90', '1800', '1380', '0', '100', '50', null, '1280', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-07 10:25:00', '2019-09-07 10:09:04');
INSERT INTO `unimall_order` VALUES ('271', 'ios', '1012019091016511067', '367', '90', '36480', '150340', '0', '100', '50', null, '150240', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-07 10:32:00', '2019-09-07 10:16:51');
INSERT INTO `unimall_order` VALUES ('272', 'android', '1012019091226101068', '287', '90', '54900', '53200', '0', null, null, null, '53200', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-07 12:42:00', '2019-09-07 12:26:10');
INSERT INTO `unimall_order` VALUES ('273', 'android', '1012019091258041069', '287', '90', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, '广东省', '茂名市', '信宜市', '44400', '18125555555', '刚刚', '', null, null, null, '2019-09-07 13:14:00', '2019-09-07 12:58:04');
INSERT INTO `unimall_order` VALUES ('274', 'android', '1012019091301591070', '287', '90', '33480', '27900', '0', null, null, null, '27900', null, null, null, null, null, null, null, null, '河北省', '唐山市', '路北区', '44400', '18125555555', '刚刚', '', null, null, null, '2019-09-07 13:18:00', '2019-09-07 13:02:00');
INSERT INTO `unimall_order` VALUES ('275', 'android', '1012019091723191071', '209', '90', '38800', '18800', '0', '100', '50', null, '18700', null, null, null, null, null, null, null, null, '江西省', '南昌市', '青山湖区', '中大青山湖东园6栋1单元3402', '17779152309', '张运康', '', null, null, null, '2019-09-07 17:39:00', '2019-09-07 17:23:19');
INSERT INTO `unimall_order` VALUES ('276', 'android', '1012019091813031072', '209', '40', '38800', '18800', '0', null, null, null, '18800', null, null, 'OFFLINE', null, null, null, '2019-09-10 13:45:40', null, '江西省', '南昌市', '青山湖区', '中大青山湖东园6栋1单元3402', '17779152309', '张运康', '', null, null, null, '2019-09-17 14:00:00', '2019-09-07 18:13:04');
INSERT INTO `unimall_order` VALUES ('277', 'android', '1012019092135391073', '376', '90', '57600', '46000', '0', null, null, null, '46000', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-07 21:51:00', '2019-09-07 21:35:40');
INSERT INTO `unimall_order` VALUES ('278', 'ios', '1012019091102171074', '336', '40', '54900', '53200', '0', null, null, null, '53200', null, null, 'OFFLINE', null, null, null, '2019-09-10 13:45:33', null, null, null, null, null, null, null, '', null, null, null, '2019-09-17 14:00:00', '2019-09-08 11:02:17');
INSERT INTO `unimall_order` VALUES ('279', 'ios', '1012019091439301075', '21', '40', '100', '1', '0', null, null, null, '1', '1', '4200000410201909082880773770', 'WX', '2019-09-08 14:39:38', null, null, '2019-09-10 13:45:31', null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2019-09-17 14:00:00', '2019-09-08 14:39:30');
INSERT INTO `unimall_order` VALUES ('280', 'android', '1012019090843531076', '288', '90', '100', '2', '0', null, null, null, '2', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-09 08:59:00', '2019-09-09 08:43:53');
INSERT INTO `unimall_order` VALUES ('281', 'android', '1012019090845321077', '288', '90', '10690', '8900', '0', null, null, null, '8900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-09 09:01:00', '2019-09-09 08:45:33');
INSERT INTO `unimall_order` VALUES ('282', 'android', '1012019090912271078', '288', '90', '54900', '53200', '0', null, null, null, '53200', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-09 09:28:00', '2019-09-09 09:12:27');
INSERT INTO `unimall_order` VALUES ('283', 'ios', '1012019090916471079', '21', '40', '100', '1', '0', null, null, null, '1', '1', '4200000413201909093429950750', 'WX', '2019-09-09 09:16:59', null, null, '2019-09-10 13:45:28', null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2019-09-17 14:00:00', '2019-09-09 09:16:47');
INSERT INTO `unimall_order` VALUES ('284', 'ios', '1012019091844341080', '383', '90', '10690', '8900', '0', null, null, null, '8900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-09 19:00:00', '2019-09-09 18:44:34');
INSERT INTO `unimall_order` VALUES ('285', 'ios', '1012019092130251081', '21', '40', '100', '1', '0', null, null, null, '1', '1', '4200000420201909092835541662', 'WX', '2019-09-09 21:30:33', null, null, '2019-09-10 13:45:23', null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2019-09-17 14:00:00', '2019-09-09 21:30:26');
INSERT INTO `unimall_order` VALUES ('286', 'android', '1012019090843551082', '386', '90', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-10 08:59:00', '2019-09-10 08:43:56');
INSERT INTO `unimall_order` VALUES ('287', 'android', '1012019090849451083', '386', '90', '38800', '18800', '0', null, null, null, '18800', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-10 09:05:00', '2019-09-10 08:49:45');
INSERT INTO `unimall_order` VALUES ('288', 'android', '1012019091023231084', '387', '20', '34680', '28900', '0', null, null, null, '28900', null, null, 'OFFLINE', null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-10 10:23:31', '2019-09-10 10:23:23');
INSERT INTO `unimall_order` VALUES ('289', 'android', '1012019091038331085', '355', '40', '38800', '18800', '0', null, null, null, '18800', '18800', '4200000404201909104798901388', 'WX', '2019-09-10 10:38:45', '73119542983516', 'ZTO', '2019-09-10 23:14:13', null, '浙江省', '杭州市', '萧山区', '测试用', '18758186347', '测试', '北京源通空港电子商务有限公司，沈杰锋，18758186347', null, null, null, '2019-09-11 16:35:04', '2019-09-10 10:38:33');
INSERT INTO `unimall_order` VALUES ('290', 'android', '1012019091041391086', '388', '90', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-10 10:57:00', '2019-09-10 10:41:40');
INSERT INTO `unimall_order` VALUES ('291', 'android', '1012019091055101087', '387', '90', '10690', '8900', '0', null, null, null, '8900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-10 11:11:00', '2019-09-10 10:55:10');
INSERT INTO `unimall_order` VALUES ('292', 'android', '1012019091640031088', '394', '90', '38800', '18800', '0', null, null, null, '18800', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-10 16:56:00', '2019-09-10 16:40:03');
INSERT INTO `unimall_order` VALUES ('293', 'ios', '1012019091804391089', '397', '90', '99900', '68800', '0', null, null, null, '68800', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-10 18:20:00', '2019-09-10 18:04:40');
INSERT INTO `unimall_order` VALUES ('294', 'ios', '1012019091007231090', '21', '20', '100', '1', '0', null, null, null, '1', '1', '4200000407201909114903812518', 'WX', '2019-09-11 10:07:36', null, null, null, null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2019-09-11 10:07:23', '2019-09-11 10:07:23');
INSERT INTO `unimall_order` VALUES ('295', 'android', '1012019091157011091', '401', '20', '54900', '53200', '0', null, null, null, '53200', null, null, 'OFFLINE', null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-11 11:57:06', '2019-09-11 11:57:01');
INSERT INTO `unimall_order` VALUES ('296', 'android', '1012019091549331092', '402', '80', '57600', '46000', '0', null, null, null, '46000', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-11 15:50:23', '2019-09-11 15:49:34');
INSERT INTO `unimall_order` VALUES ('297', 'android', '1012019091550061093', '402', '60', '100', '1', '0', null, null, null, '1', '1', '4200000411201909112790450553', 'WX', '2019-09-11 15:50:16', null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-11 15:50:06', '2019-09-11 15:50:06');
INSERT INTO `unimall_order` VALUES ('298', 'android', '1012019091634461094', '355', '60', '100', '1', '0', null, null, null, '1', '1', '4200000409201909113479771143', 'WX', '2019-09-11 16:34:55', null, null, null, null, '浙江省', '杭州市', '萧山区', '测试用', '18758186347', '测试', '', null, null, null, '2019-09-11 16:34:47', '2019-09-11 16:34:47');
INSERT INTO `unimall_order` VALUES ('299', 'android', '1012019091944511095', '404', '90', '10690', '8900', '0', null, null, null, '8900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-11 20:00:00', '2019-09-11 19:44:51');
INSERT INTO `unimall_order` VALUES ('300', 'android', '1012019091018281096', '405', '90', '33480', '27900', '0', null, null, null, '27900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-12 10:34:00', '2019-09-12 10:18:29');
INSERT INTO `unimall_order` VALUES ('301', 'android', '1012019091312151097', '407', '90', '99900', '68800', '0', null, null, null, '68800', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-12 13:28:00', '2019-09-12 13:12:16');
INSERT INTO `unimall_order` VALUES ('302', 'android', '1012019091346381098', '408', '90', '57600', '46000', '0', null, null, null, '46000', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-12 14:02:00', '2019-09-12 13:46:38');
INSERT INTO `unimall_order` VALUES ('303', 'ios', '1012019091428211099', '21', '20', '100', '1', '0', null, null, null, '1', '1', '4200000419201909125549981216', 'WX', '2019-09-12 14:28:28', null, null, null, null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2019-09-12 14:28:22', '2019-09-12 14:28:22');
INSERT INTO `unimall_order` VALUES ('304', 'android', '1012019092027321100', '413', '20', '100', '1', '0', null, null, null, '1', '1', '4200000420201909126775101287', 'WX', '2019-09-12 20:27:43', null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-12 20:27:33', '2019-09-12 20:27:33');
INSERT INTO `unimall_order` VALUES ('305', 'android', '1012019090946491101', '413', '20', '100', '1', '0', null, null, null, '1', '1', '4200000402201909135084747390', 'WX', '2019-09-13 09:46:58', null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-13 09:46:49', '2019-09-13 09:46:49');
INSERT INTO `unimall_order` VALUES ('306', 'android', '1012019091504371102', '415', '90', '33480', '27900', '0', null, null, null, '27900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-13 15:20:00', '2019-09-13 15:04:38');
INSERT INTO `unimall_order` VALUES ('307', 'ios', '1012019092102321103', '416', '20', '54900', '53200', '0', null, null, null, '53200', null, null, 'OFFLINE', null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-13 21:02:43', '2019-09-13 21:02:32');
INSERT INTO `unimall_order` VALUES ('308', 'android', '1012019092300251104', '413', '20', '100', '1', '0', null, null, null, '1', '1', '4200000418201909139348279597', 'WX', '2019-09-13 23:00:36', null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-13 23:00:26', '2019-09-13 23:00:26');
INSERT INTO `unimall_order` VALUES ('309', 'android', '1012019090901401105', '418', '90', '99900', '68800', '0', null, null, null, '68800', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-14 09:17:00', '2019-09-14 09:01:40');
INSERT INTO `unimall_order` VALUES ('310', 'android', '1012019092012131106', '421', '20', '54900', '53200', '0', null, null, null, '53200', null, null, 'OFFLINE', null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-14 20:12:23', '2019-09-14 20:12:14');
INSERT INTO `unimall_order` VALUES ('311', 'android', '1012019092013061107', '421', '20', '100', '1', '0', null, null, null, '1', null, null, 'OFFLINE', null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-14 20:13:08', '2019-09-14 20:13:06');
INSERT INTO `unimall_order` VALUES ('312', 'android', '1012019092100371108', '413', '20', '100', '1', '0', null, null, null, '1', '1', '4200000415201909148278829982', 'WX', '2019-09-14 21:00:46', null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-14 21:00:38', '2019-09-14 21:00:38');
INSERT INTO `unimall_order` VALUES ('313', 'ios', '1012019091046241109', '423', '90', '42120', '35100', '0', '100', '50', null, '35000', null, null, null, null, null, null, null, null, '北京', '北京市', '东城区', '？我也不知道', '15968845617', '张三', '', null, null, null, '2019-09-15 11:02:00', '2019-09-15 10:46:25');
INSERT INTO `unimall_order` VALUES ('314', 'ios', '1012019091116461110', '21', '20', '100', '1', '0', null, null, null, '1', '1', '4200000403201909150620256749', 'WX', '2019-09-15 11:16:53', null, null, null, null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2019-09-15 11:16:47', '2019-09-15 11:16:47');
INSERT INTO `unimall_order` VALUES ('315', 'ios', '1012019091415011111', '424', '90', '1800', '1380', '0', null, null, null, '1380', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-15 14:31:00', '2019-09-15 14:15:01');
INSERT INTO `unimall_order` VALUES ('316', 'android', '1012019092057011112', '413', '20', '100', '1', '0', null, null, null, '1', '1', '4200000406201909152926871085', 'WX', '2019-09-15 20:57:10', null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-15 20:57:02', '2019-09-15 20:57:02');
INSERT INTO `unimall_order` VALUES ('317', 'ios', '1012019090818531113', '21', '20', '100', '1', '0', null, null, null, '1', '1', '4200000405201909164631481552', 'WX', '2019-09-16 08:19:01', null, null, null, null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2019-09-16 08:18:54', '2019-09-16 08:18:54');
INSERT INTO `unimall_order` VALUES ('318', 'android', '1012019091409291114', '413', '20', '100', '1', '0', null, null, null, '1', '1', '4200000420201909162678957060', 'WX', '2019-09-16 14:09:46', null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-16 14:09:30', '2019-09-16 14:09:30');
INSERT INTO `unimall_order` VALUES ('319', 'android', '1012019091541401115', '138', '90', '1800', '1380', '0', null, null, null, '1380', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-16 15:57:00', '2019-09-16 15:41:40');
INSERT INTO `unimall_order` VALUES ('320', 'android', '1012019091727081116', '431', '90', '99900', '206400', '0', null, null, null, '206400', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-16 17:43:00', '2019-09-16 17:27:09');
INSERT INTO `unimall_order` VALUES ('321', 'ios', '1012019092132261117', '21', '90', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2019-09-16 21:48:00', '2019-09-16 21:32:26');
INSERT INTO `unimall_order` VALUES ('322', 'ios', '1012019090736271118', '21', '20', '100', '1', '0', null, null, null, '1', '1', '4200000420201909177778069659', 'WX', '2019-09-17 07:36:33', null, null, null, null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2019-09-17 07:36:27', '2019-09-17 07:36:27');
INSERT INTO `unimall_order` VALUES ('323', 'ios', '1012019091202141119', '433', '20', '1800', '1380', '0', null, null, null, '1380', null, null, 'OFFLINE', null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-17 12:02:27', '2019-09-17 12:02:15');
INSERT INTO `unimall_order` VALUES ('324', 'android', '1012019091657461120', '436', '90', '99900', '68800', '0', null, null, null, '68800', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-17 17:13:00', '2019-09-17 16:57:47');
INSERT INTO `unimall_order` VALUES ('325', 'android', '1012019091701511121', '436', '90', '99900', '68800', '0', null, null, null, '68800', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-17 17:17:00', '2019-09-17 17:01:52');
INSERT INTO `unimall_order` VALUES ('326', 'android', '1012019091750501122', '437', '20', '54900', '53200', '0', null, null, null, '53200', null, null, 'OFFLINE', null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-17 17:51:07', '2019-09-17 17:50:51');
INSERT INTO `unimall_order` VALUES ('327', 'android', '1012019091844541123', '438', '90', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-17 19:00:00', '2019-09-17 18:44:55');
INSERT INTO `unimall_order` VALUES ('328', 'android', '1012019091845001124', '438', '90', '34680', '28900', '0', null, null, null, '28900', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-17 19:01:00', '2019-09-17 18:45:01');
INSERT INTO `unimall_order` VALUES ('329', 'android', '1012019092031101125', '413', '20', '100', '1', '0', null, null, null, '1', '1', '4200000417201909176103940938', 'WX', '2019-09-17 20:31:20', null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-17 20:31:11', '2019-09-17 20:31:11');
INSERT INTO `unimall_order` VALUES ('330', 'android', '1012019091112221126', '439', '90', '54900', '53200', '0', null, null, null, '53200', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-18 11:28:00', '2019-09-18 11:12:23');
INSERT INTO `unimall_order` VALUES ('331', 'ios', '1012019091220301127', '21', '20', '100', '1', '0', null, null, null, '1', '1', '4200000421201909185749675281', 'WX', '2019-09-18 12:20:42', null, null, null, null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2019-09-18 12:20:31', '2019-09-18 12:20:31');
INSERT INTO `unimall_order` VALUES ('332', 'android', '1012019091353581128', '441', '90', '1800', '1380', '0', null, null, null, '1380', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-18 14:09:00', '2019-09-18 13:53:59');
INSERT INTO `unimall_order` VALUES ('333', 'ios', '1012019091358131129', '442', '80', '1920', '1600', '0', null, null, null, '1600', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-18 13:59:19', '2019-09-18 13:58:13');
INSERT INTO `unimall_order` VALUES ('334', 'android', '1012019091443261130', '444', '90', '57600', '46000', '0', null, null, null, '46000', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-18 14:59:00', '2019-09-18 14:43:27');
INSERT INTO `unimall_order` VALUES ('335', 'android', '1012019091449401131', '445', '20', '54900', '53200', '0', null, null, null, '53200', null, null, 'OFFLINE', null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-18 14:49:52', '2019-09-18 14:49:41');
INSERT INTO `unimall_order` VALUES ('336', 'android', '1012019091611061132', '449', '90', '54900', '53200', '0', null, null, null, '53200', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-18 16:27:00', '2019-09-18 16:11:07');
INSERT INTO `unimall_order` VALUES ('337', 'android', '1012019091636181133', '449', '20', '34680', '28900', '0', null, null, null, '28900', null, null, 'OFFLINE', null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-18 16:36:22', '2019-09-18 16:36:19');
INSERT INTO `unimall_order` VALUES ('338', 'android', '1012019092121201134', '452', '20', '8640', '7200', '0', null, null, null, '7200', null, null, 'OFFLINE', null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-09-18 21:21:37', '2019-09-18 21:21:20');
INSERT INTO `unimall_order` VALUES ('339', 'devtools', '3012019102019401001', '21', '90', '100', '1', '0', null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', null, null, null, '2019-10-31 20:35:00', '2019-10-31 20:19:41');
INSERT INTO `unimall_order` VALUES ('340', 'devtools', '1012019102247451001', '21', '40', '100', '1', '0', null, null, null, '1', '1', '4200000420201910312215781660', 'WX', '2019-10-31 22:48:36', null, null, '2019-10-31 22:48:37', null, null, null, null, null, null, null, '', null, null, null, '2019-11-08 18:45:00', '2019-10-31 22:47:46');
INSERT INTO `unimall_order` VALUES ('341', 'devtools', '1012019102252041001', '21', '40', '100', '1', '0', null, null, null, '1', '1', '4200000420201910317618360929', 'WX', '2019-10-31 22:52:17', null, null, '2019-10-31 22:52:17', null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2019-11-08 18:45:00', '2019-10-31 22:52:04');
INSERT INTO `unimall_order` VALUES ('342', 'devtools', '1012019111551581001', '21', '40', '10', '1', '0', null, null, null, '1', '1', '4200000454201911068850116648', 'WX', '2019-11-06 15:56:24', null, null, '2019-11-06 15:56:25', null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2019-11-15 13:15:00', '2019-11-06 15:51:58');
INSERT INTO `unimall_order` VALUES ('343', 'devtools', '1012019111555351002', '21', '40', '10', '1', '0', null, null, null, '1', '1', '4200000465201911060135530039', 'WX', '2019-11-06 15:55:50', null, null, '2019-11-06 15:55:50', null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2019-11-15 13:15:00', '2019-11-06 15:55:36');
INSERT INTO `unimall_order` VALUES ('344', 'devtools', '1012019121115301001', '21', '20', '100', '1', '0', null, null, null, '1', '1', '4200000467201912303573573263', 'WX', '2019-12-30 11:15:54', null, null, null, null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2019-12-30 11:15:30', '2019-12-30 11:15:30');
INSERT INTO `unimall_order` VALUES ('345', 'devtools', '1012019121117101002', '21', '20', '100', '1', '0', null, null, null, '1', '1', '4200000453201912303795091706', 'WX', '2019-12-30 11:17:24', null, null, null, null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2019-12-30 11:17:10', '2019-12-30 11:17:10');
INSERT INTO `unimall_order` VALUES ('346', 'devtools', '1012019121120291001', '21', '20', '100', '1', '0', null, null, null, '1', '1', '4200000469201912301115128882', 'WX', '2019-12-30 11:20:40', null, null, null, null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2019-12-30 11:20:30', '2019-12-30 11:20:30');
INSERT INTO `unimall_order` VALUES ('347', 'devtools', '1012019121122441001', '21', '60', '100', '1', '0', null, null, null, '1', '1', '4200000458201912304006935031', 'WX', '2019-12-30 11:22:58', null, null, null, null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2019-12-30 11:22:44', '2019-12-30 11:22:44');
INSERT INTO `unimall_order` VALUES ('348', 'devtools', '1012019121124051001', '21', '60', '100', '1', '0', null, null, null, '1', '1', '4200000458201912309646658357', 'WX', '2019-12-30 11:24:17', null, null, null, null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, '2333', '2019-12-30 11:24:05', '2019-12-30 11:24:05');
INSERT INTO `unimall_order` VALUES ('349', 'devtools', '1012019121132291002', '21', '70', '100', '1', '0', null, null, null, '1', '1', '4200000471201912302771565463', 'WX', '2019-12-30 11:32:41', null, null, null, null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2020-02-14 15:12:58', '2019-12-30 11:32:29');
INSERT INTO `unimall_order` VALUES ('350', 'devtools', '1012020021514261001', '21', '70', '2000', '40', '0', null, null, null, '40', '40', '4200000511202002146819585165', 'WX', '2020-02-14 15:18:44', null, null, null, null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2020-02-14 15:19:37', '2020-02-14 15:14:27');
INSERT INTO `unimall_order` VALUES ('351', 'devtools', '1012020021906561001', '21', '40', '100', '2', '0', null, null, null, '2', '2', '4200000518202002143124618681', 'WX', '2020-02-14 19:12:56', null, null, '2020-02-14 19:16:35', null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2020-02-14 19:16:52', '2020-02-14 19:06:56');
INSERT INTO `unimall_order` VALUES ('352', 'devtools', '1012020021908191002', '21', '80', '100', '2', '0', null, null, null, '2', null, null, null, null, null, null, null, null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2020-02-14 19:08:31', '2020-02-14 19:08:19');
INSERT INTO `unimall_order` VALUES ('353', 'devtools', '1012020021918471001', '21', '40', '100', '2', '0', null, null, null, '2', '2', '4200000511202002144118853040', 'WX', '2020-02-14 19:19:03', null, null, '2020-02-14 19:19:17', null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', null, null, null, '2020-02-14 19:19:31', '2020-02-14 19:18:47');
INSERT INTO `unimall_order` VALUES ('354', 'devtools', '1012020021919491002', '21', '40', '100', '2', '0', null, null, null, '2', '2', '4200000520202002144063488033', 'WX', '2020-02-14 19:20:05', null, null, '2020-02-14 19:20:37', null, '重庆', '重庆市', '南岸区', '香溪路2号', '18584669549', '魏朝正', '', '1', 'helllo', null, '2020-02-15 16:37:12', '2020-02-14 19:19:50');

-- ----------------------------
-- Table structure for unimall_order_sku
-- ----------------------------
DROP TABLE IF EXISTS `unimall_order_sku`;
CREATE TABLE `unimall_order_sku` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sku_id` bigint(20) NOT NULL,
  `spu_id` bigint(20) NOT NULL,
  `order_id` bigint(20) NOT NULL,
  `order_no` varchar(255) NOT NULL,
  `spu_title` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `bar_code` varchar(255) NOT NULL,
  `num` int(11) NOT NULL,
  `unit` varchar(60) DEFAULT NULL,
  `original_price` int(11) NOT NULL,
  `price` int(11) NOT NULL,
  `img` varchar(255) NOT NULL,
  `gmt_update` datetime NOT NULL,
  `gmt_create` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=385 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of unimall_order_sku
-- ----------------------------
INSERT INTO `unimall_order_sku` VALUES ('1', '1', '28', '1', '1012019071753561001', '钛架防辐射抗蓝光男士', '标准', '111110000', '1', null, '3230', '3220', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/83ecf09f2c5a4e03a4618d71adc84b2c.png', '2019-07-06 17:53:57', '2019-07-06 17:53:57');
INSERT INTO `unimall_order_sku` VALUES ('2', '2', '28', '1', '1012019071753561001', '钛架防辐射抗蓝光男士', '小型', '111888111', '4', null, '3230', '3220', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/83ecf09f2c5a4e03a4618d71adc84b2c.png', '2019-07-06 17:53:57', '2019-07-06 17:53:57');
INSERT INTO `unimall_order_sku` VALUES ('3', '1', '28', '2', '1012019071753561001', '钛架防辐射抗蓝光男士', '标准', '111110000', '1', null, '3230', '3220', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/83ecf09f2c5a4e03a4618d71adc84b2c.png', '2019-07-06 17:53:57', '2019-07-06 17:53:57');
INSERT INTO `unimall_order_sku` VALUES ('4', '1', '28', '2', '1012019071753561001', '钛架防辐射抗蓝光男士', '标准', '111110000', '1', null, '3230', '3220', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/83ecf09f2c5a4e03a4618d71adc84b2c.png', '2019-07-06 17:53:57', '2019-07-06 17:53:57');
INSERT INTO `unimall_order_sku` VALUES ('5', '1', '28', '3', '1012019071753561001', '钛架防辐射抗蓝光男士', '标准', '111110000', '1', null, '3230', '3220', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/83ecf09f2c5a4e03a4618d71adc84b2c.png', '2019-07-06 17:53:57', '2019-07-06 17:53:57');
INSERT INTO `unimall_order_sku` VALUES ('6', '2', '28', '2', '1012019071753561001', '钛架防辐射抗蓝光男士', '标准', '111110000', '1', null, '3230', '3220', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/83ecf09f2c5a4e03a4618d71adc84b2c.png', '2019-07-06 17:53:57', '2019-07-06 17:53:57');
INSERT INTO `unimall_order_sku` VALUES ('7', '1', '28', '4', '1012019071541321001', '钛架防辐射抗蓝光男士', '标准', '111110000', '1', null, '3230', '3220', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/83ecf09f2c5a4e03a4618d71adc84b2c.png', '2019-07-07 15:41:33', '2019-07-07 15:41:33');
INSERT INTO `unimall_order_sku` VALUES ('8', '2', '28', '4', '1012019071541321001', '钛架防辐射抗蓝光男士', '小型', '111888111', '4', null, '3230', '3220', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/83ecf09f2c5a4e03a4618d71adc84b2c.png', '2019-07-07 15:41:33', '2019-07-07 15:41:33');
INSERT INTO `unimall_order_sku` VALUES ('9', '2', '28', '5', '1012019071736031001', '钛架防辐射抗蓝光男士', '小型', '111888111', '4', null, '3230', '3220', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/83ecf09f2c5a4e03a4618d71adc84b2c.png', '2019-07-07 17:36:03', '2019-07-07 17:36:03');
INSERT INTO `unimall_order_sku` VALUES ('10', '1', '28', '6', '1012019071839591001', '钛架防辐射抗蓝光男士', '标准', '111110000', '1', null, '3230', '3220', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/83ecf09f2c5a4e03a4618d71adc84b2c.png', '2019-07-07 18:40:00', '2019-07-07 18:40:00');
INSERT INTO `unimall_order_sku` VALUES ('11', '1', '28', '7', '1012019071841201002', '钛架防辐射抗蓝光男士', '标准', '111110000', '1', null, '3230', '3220', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/83ecf09f2c5a4e03a4618d71adc84b2c.png', '2019-07-07 18:41:21', '2019-07-07 18:41:21');
INSERT INTO `unimall_order_sku` VALUES ('12', '1', '28', '8', '1012019071945261003', '钛架防辐射抗蓝光男士', '标准', '111110000', '1', null, '3230', '3220', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/83ecf09f2c5a4e03a4618d71adc84b2c.png', '2019-07-07 19:45:26', '2019-07-07 19:45:26');
INSERT INTO `unimall_order_sku` VALUES ('13', '2', '28', '9', '1012019072142171001', '钛架防辐射抗蓝光男士', '小型', '111888111', '1', null, '3230', '3220', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/83ecf09f2c5a4e03a4618d71adc84b2c.png', '2019-07-07 21:42:17', '2019-07-07 21:42:17');
INSERT INTO `unimall_order_sku` VALUES ('14', '2', '28', '10', '1012019072144281002', '钛架防辐射抗蓝光男士', '小型', '111888111', '1', null, '3230', '3220', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/83ecf09f2c5a4e03a4618d71adc84b2c.png', '2019-07-07 21:44:29', '2019-07-07 21:44:29');
INSERT INTO `unimall_order_sku` VALUES ('15', '1', '28', '11', '1012019072146061003', '钛架防辐射抗蓝光男士', '标准', '111110000', '1', null, '3230', '3220', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/83ecf09f2c5a4e03a4618d71adc84b2c.png', '2019-07-07 21:46:06', '2019-07-07 21:46:06');
INSERT INTO `unimall_order_sku` VALUES ('16', '1', '28', '12', '1012019072222121004', '钛架防辐射抗蓝光男士', '标准', '111110000', '1', null, '3230', '1', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/83ecf09f2c5a4e03a4618d71adc84b2c.png', '2019-07-07 22:22:12', '2019-07-07 22:22:12');
INSERT INTO `unimall_order_sku` VALUES ('17', '2', '28', '13', '1012019071159051001', '钛架防辐射抗蓝光男士', '小型', '111888111', '1', null, '3230', '1', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/83ecf09f2c5a4e03a4618d71adc84b2c.png', '2019-07-10 11:59:05', '2019-07-10 11:59:05');
INSERT INTO `unimall_order_sku` VALUES ('18', '1', '28', '14', '1012019071219581001', '钛架防辐射抗蓝光男士', '标准', '111110000', '1', null, '3230', '1', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/83ecf09f2c5a4e03a4618d71adc84b2c.png', '2019-07-10 12:19:59', '2019-07-10 12:19:59');
INSERT INTO `unimall_order_sku` VALUES ('19', '1', '28', '15', '1012019071229121002', '钛架防辐射抗蓝光男士', '标准', '111110000', '1', null, '3230', '1', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/83ecf09f2c5a4e03a4618d71adc84b2c.png', '2019-07-10 12:29:13', '2019-07-10 12:29:13');
INSERT INTO `unimall_order_sku` VALUES ('20', '1', '28', '16', '1012019071606101001', '钛架防辐射抗蓝光男士', '标准', '111110000', '1', null, '3230', '1', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/83ecf09f2c5a4e03a4618d71adc84b2c.png', '2019-07-11 16:06:11', '2019-07-11 16:06:11');
INSERT INTO `unimall_order_sku` VALUES ('21', '2', '28', '16', '1012019071606101001', '钛架防辐射抗蓝光男士', '小型', '111888111', '1', null, '3230', '1', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/83ecf09f2c5a4e03a4618d71adc84b2c.png', '2019-07-11 16:06:11', '2019-07-11 16:06:11');
INSERT INTO `unimall_order_sku` VALUES ('22', '6', '28', '17', '1012019070950031001', '钛架防辐射抗蓝光男士', '标准', '111110000', '1', null, '3230', '1', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/83ecf09f2c5a4e03a4618d71adc84b2c.png', '2019-07-14 09:50:04', '2019-07-14 09:50:04');
INSERT INTO `unimall_order_sku` VALUES ('23', '6', '28', '18', '1012019071747581001', '钛架防辐射抗蓝光男士', '标准', '111110000', '1', null, '3230', '1', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/83ecf09f2c5a4e03a4618d71adc84b2c.png', '2019-07-17 17:47:59', '2019-07-17 17:47:59');
INSERT INTO `unimall_order_sku` VALUES ('24', '6', '28', '19', '1012019071810461001', '钛架防辐射抗蓝光男士', '标准', '111110000', '1', null, '3230', '1', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/83ecf09f2c5a4e03a4618d71adc84b2c.png', '2019-07-17 18:10:47', '2019-07-17 18:10:47');
INSERT INTO `unimall_order_sku` VALUES ('25', '6', '28', '20', '1012019071814101002', '钛架防辐射抗蓝光男士', '标准', '111110000', '1', null, '3230', '1', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/83ecf09f2c5a4e03a4618d71adc84b2c.png', '2019-07-17 18:14:11', '2019-07-17 18:14:11');
INSERT INTO `unimall_order_sku` VALUES ('26', '6', '28', '21', '1012019071904591001', '钛架防辐射抗蓝光男士', '标准', '111110000', '1', null, '3230', '1', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/83ecf09f2c5a4e03a4618d71adc84b2c.png', '2019-07-17 19:04:59', '2019-07-17 19:04:59');
INSERT INTO `unimall_order_sku` VALUES ('27', '6', '28', '22', '1012019071124551001', '钛架防辐射抗蓝光男士', '标准', '111110000', '1', null, '3230', '1', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/83ecf09f2c5a4e03a4618d71adc84b2c.png', '2019-07-18 11:24:56', '2019-07-18 11:24:56');
INSERT INTO `unimall_order_sku` VALUES ('28', '6', '28', '23', '1012019071217371001', '钛架防辐射抗蓝光男士', '标准', '111110000', '1', null, '3230', '1', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/83ecf09f2c5a4e03a4618d71adc84b2c.png', '2019-07-18 12:17:38', '2019-07-18 12:17:38');
INSERT INTO `unimall_order_sku` VALUES ('29', '6', '28', '24', '1012019071500371002', '钛架防辐射抗蓝光男士', '标准', '111110000', '1', null, '3230', '1', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/83ecf09f2c5a4e03a4618d71adc84b2c.png', '2019-07-18 15:00:38', '2019-07-18 15:00:38');
INSERT INTO `unimall_order_sku` VALUES ('30', '6', '28', '25', '1012019071546231001', '钛架防辐射抗蓝光男士', '标准', '111110000', '1', null, '3230', '1', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/83ecf09f2c5a4e03a4618d71adc84b2c.png', '2019-07-18 15:46:24', '2019-07-18 15:46:24');
INSERT INTO `unimall_order_sku` VALUES ('31', '6', '28', '26', '1012019071112511001', '钛架防辐射抗蓝光男士', '标准', '111110000', '1', null, '3230', '1', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/83ecf09f2c5a4e03a4618d71adc84b2c.png', '2019-07-20 11:12:52', '2019-07-20 11:12:52');
INSERT INTO `unimall_order_sku` VALUES ('32', '6', '28', '27', '1012019071119121002', '钛架防辐射抗蓝光男士', '标准', '111110000', '1', null, '3230', '1', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/83ecf09f2c5a4e03a4618d71adc84b2c.png', '2019-07-20 11:19:12', '2019-07-20 11:19:12');
INSERT INTO `unimall_order_sku` VALUES ('33', '7', '28', '28', '1012019071125451003', '钛架防辐射抗蓝光男士', '小型', '111888111', '1', null, '3230', '1', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/83ecf09f2c5a4e03a4618d71adc84b2c.png', '2019-07-20 11:25:46', '2019-07-20 11:25:46');
INSERT INTO `unimall_order_sku` VALUES ('34', '7', '28', '29', '1012019071125461004', '钛架防辐射抗蓝光男士', '小型', '111888111', '1', null, '3230', '1', 'https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/83ecf09f2c5a4e03a4618d71adc84b2c.png', '2019-07-20 11:25:46', '2019-07-20 11:25:46');
INSERT INTO `unimall_order_sku` VALUES ('35', '184', '1234179', '30', '1012019071214061001', '1*12_燕京鲜啤酒500ml', '1*12', '6946343901007', '1', null, '355', '340', 'http://kegooo.qnoddns.org.cn:8088/jms/fileSave/pic_small700_700/6946343901007.jpg', '2019-07-23 12:14:07', '2019-07-23 12:14:07');
INSERT INTO `unimall_order_sku` VALUES ('36', '184', '1234179', '31', '1012019072046471001', '1*12_燕京鲜啤酒500ml', '1*12', '6946343901007', '1', null, '355', '340', 'http://kegooo.qnoddns.org.cn:8088/jms/fileSave/pic_small700_700/6946343901007.jpg', '2019-07-24 20:46:48', '2019-07-24 20:46:48');
INSERT INTO `unimall_order_sku` VALUES ('37', '179', '1234174', '31', '1012019072046471001', '1*10_#南京十二衩（薄荷味）香烟', '1*10', '6901028062343', '4', null, '1886', '1886', 'http://kegooo.qnoddns.org.cn:8088/jms/fileSave/pic_small700_700/6926265352798.png', '2019-07-24 20:46:48', '2019-07-24 20:46:48');
INSERT INTO `unimall_order_sku` VALUES ('38', '755', '1234750', '31', '1012019072046471001', '1*150_A香仔一连Q蛋32g（原味）', '1*150', '6921931808340', '1', null, '100', '100', 'http://kegooo.qnoddns.org.cn:8088/jms/fileSave/pic_small700_700/6921931808340.jpg', '2019-07-24 20:46:48', '2019-07-24 20:46:48');
INSERT INTO `unimall_order_sku` VALUES ('39', '596', '1234591', '31', '1012019072046471001', '1*50_有友泡凤爪山椒味100g', '1*50', '6922145800526', '2', null, '535', '535', 'http://kegooo.qnoddns.org.cn:8088/jms/fileSave/pic_small700_700/6922145800526.jpg', '2019-07-24 20:46:48', '2019-07-24 20:46:48');
INSERT INTO `unimall_order_sku` VALUES ('40', '756', '1234751', '31', '1012019072046471001', '1*75_香仔二连Q蛋65g（原味）', '1*75', '6921931808357', '2', null, '200', '200', 'http://kegooo.qnoddns.org.cn:8088/jms/fileSave/pic_small700_700/6921931808357.jpg', '2019-07-24 20:46:48', '2019-07-24 20:46:48');
INSERT INTO `unimall_order_sku` VALUES ('41', '610', '1234605', '31', '1012019072046471001', '1*40_乐棒棒山椒凤爪130g', '1*40', '6928773073424', '2', null, '625', '625', 'http://kegooo.qnoddns.org.cn:8088/jms/fileSave/pic_small700_700/6928773073424.jpg', '2019-07-24 20:46:48', '2019-07-24 20:46:48');
INSERT INTO `unimall_order_sku` VALUES ('42', '757', '1234752', '31', '1012019072046471001', '1*75_香仔二连Q蛋65g（五香）', '1*75', '6921931808388', '2', null, '200', '200', 'http://kegooo.qnoddns.org.cn:8088/jms/fileSave/pic_small700_700/6921931808388.jpg', '2019-07-24 20:46:48', '2019-07-24 20:46:48');
INSERT INTO `unimall_order_sku` VALUES ('43', '758', '1234753', '31', '1012019072046471001', '1*50_调皮鹌鹑蛋80g（酱香味）', '1*50', '6931849900036', '1', null, '210', '210', 'http://kegooo.qnoddns.org.cn:8088/jms/fileSave/pic_small700_700/6931849900036.png', '2019-07-24 20:46:48', '2019-07-24 20:46:48');
INSERT INTO `unimall_order_sku` VALUES ('44', '759', '1234754', '31', '1012019072046471001', '1*150_香仔一连Q蛋32g（五香）', '1*150', '6921931803758', '1', null, '100', '100', 'http://kegooo.qnoddns.org.cn:8088/jms/fileSave/pic_small700_700/6921931803758.jpg', '2019-07-24 20:46:48', '2019-07-24 20:46:48');
INSERT INTO `unimall_order_sku` VALUES ('45', '2784', '1236775', '32', '1012019080123131001', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-04 01:23:14', '2019-08-04 01:23:14');
INSERT INTO `unimall_order_sku` VALUES ('46', '2784', '1236775', '33', '1012019080124461002', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-04 01:24:46', '2019-08-04 01:24:46');
INSERT INTO `unimall_order_sku` VALUES ('47', '2784', '1236775', '34', '1012019080128481003', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-04 01:28:49', '2019-08-04 01:28:49');
INSERT INTO `unimall_order_sku` VALUES ('48', '2776', '1236770', '35', '1012019080128511004', '宠儿香 家庭专用系列 美健膳食罐头 90g', '标准', '130001', '23', null, '1920', '1600', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2b86ba9cfc0c40fc82f7741c06270f36.jpg', '2019-08-04 01:28:51', '2019-08-04 01:28:51');
INSERT INTO `unimall_order_sku` VALUES ('49', '2784', '1236775', '36', '1012019080149461005', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-04 01:49:46', '2019-08-04 01:49:46');
INSERT INTO `unimall_order_sku` VALUES ('50', '2781', '1236772', '37', '1012019080150011006', '路斯 手工坊系列 香薰牛肉丁 200g', '200g', '160001', '1', null, '1800', '1380', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/e48b4f8c1c824e1a9b9f349be0275648.jpg', '2019-08-04 01:50:02', '2019-08-04 01:50:02');
INSERT INTO `unimall_order_sku` VALUES ('51', '2784', '1236775', '38', '1012019080154211007', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '98', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-04 01:54:22', '2019-08-04 01:54:22');
INSERT INTO `unimall_order_sku` VALUES ('52', '2776', '1236770', '38', '1012019080154211007', '宠儿香 家庭专用系列 美健膳食罐头 90g', '标准', '130001', '2', null, '1920', '1600', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2b86ba9cfc0c40fc82f7741c06270f36.jpg', '2019-08-04 01:54:22', '2019-08-04 01:54:22');
INSERT INTO `unimall_order_sku` VALUES ('53', '2784', '1236775', '39', '1012019081105391008', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-04 11:05:40', '2019-08-04 11:05:40');
INSERT INTO `unimall_order_sku` VALUES ('54', '2784', '1236775', '40', '1012019081118071009', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-04 11:18:08', '2019-08-04 11:18:08');
INSERT INTO `unimall_order_sku` VALUES ('55', '2784', '1236775', '41', '1012019082221321001', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-06 22:21:32', '2019-08-06 22:21:32');
INSERT INTO `unimall_order_sku` VALUES ('56', '2776', '1236770', '42', '1012019082223471002', '宠儿香 家庭专用系列 美健膳食罐头 90g', '标准', '130001', '1', null, '1920', '1600', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2b86ba9cfc0c40fc82f7741c06270f36.jpg', '2019-08-06 22:23:48', '2019-08-06 22:23:48');
INSERT INTO `unimall_order_sku` VALUES ('57', '2784', '1236775', '43', '1012019081601541003', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-07 16:01:55', '2019-08-07 16:01:55');
INSERT INTO `unimall_order_sku` VALUES ('58', '2784', '1236775', '44', '1012019081926471001', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-08 19:26:48', '2019-08-08 19:26:48');
INSERT INTO `unimall_order_sku` VALUES ('59', '2773', '1236768', '45', '1012019081740401002', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-09 17:40:40', '2019-08-09 17:40:40');
INSERT INTO `unimall_order_sku` VALUES ('60', '2783', '1236774', '46', '1012019081754381003', '百加世NOW FRESH 无谷成猫粮 4磅', '标准', '180001', '1', null, '33480', '27900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/8e168314e8f14558a89729ec4fb86f17.jpg', '2019-08-09 17:54:38', '2019-08-09 17:54:38');
INSERT INTO `unimall_order_sku` VALUES ('61', '2773', '1236768', '47', '1012019081826381004', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-09 18:26:39', '2019-08-09 18:26:39');
INSERT INTO `unimall_order_sku` VALUES ('62', '2783', '1236774', '48', '1012019081923391005', '百加世NOW FRESH 无谷成猫粮 4磅', '标准', '180001', '1', null, '33480', '27900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/8e168314e8f14558a89729ec4fb86f17.jpg', '2019-08-09 19:23:39', '2019-08-09 19:23:39');
INSERT INTO `unimall_order_sku` VALUES ('63', '2773', '1236768', '49', '1012019081929141006', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-09 19:29:14', '2019-08-09 19:29:14');
INSERT INTO `unimall_order_sku` VALUES ('64', '2773', '1236768', '50', '1012019082014491007', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-09 20:14:49', '2019-08-09 20:14:49');
INSERT INTO `unimall_order_sku` VALUES ('65', '2773', '1236768', '51', '1012019082117181008', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-09 21:17:18', '2019-08-09 21:17:18');
INSERT INTO `unimall_order_sku` VALUES ('66', '2782', '1236773', '52', '1012019082122571009', '比利时原装进口 欧帝亿IMPERIAL PAW 鸡肉幼猫猫粮 7.5kg', '7.5KG', '170001', '1', null, '54900', '53200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/4f817407a019439da765bf0dc5e7b3b7.jpg', '2019-08-09 21:22:58', '2019-08-09 21:22:58');
INSERT INTO `unimall_order_sku` VALUES ('67', '2776', '1236770', '53', '1012019082220391010', '宠儿香 家庭专用系列 美健膳食罐头 90g', '标准', '130001', '1', null, '1920', '1600', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2b86ba9cfc0c40fc82f7741c06270f36.jpg', '2019-08-09 22:20:39', '2019-08-09 22:20:39');
INSERT INTO `unimall_order_sku` VALUES ('68', '2784', '1236775', '54', '1012019082227341011', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-09 22:27:35', '2019-08-09 22:27:35');
INSERT INTO `unimall_order_sku` VALUES ('69', '2775', '1236769', '55', '1012019082305571012', '耐吉斯SOLUTION 比利时版 鸡肉+三文鱼配方奶糕粮 18磅/8.16kg', '8.16KG', '120001', '1', null, '57600', '46000', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/55c59f82af4e4aefb9f78ce2484b9f36.png', '2019-08-09 23:05:57', '2019-08-09 23:05:57');
INSERT INTO `unimall_order_sku` VALUES ('70', '2773', '1236768', '56', '1012019082316141013', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-09 23:16:15', '2019-08-09 23:16:15');
INSERT INTO `unimall_order_sku` VALUES ('71', '2773', '1236768', '57', '1012019082316251014', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-09 23:16:26', '2019-08-09 23:16:26');
INSERT INTO `unimall_order_sku` VALUES ('72', '2784', '1236775', '58', '1012019082324271015', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-09 23:24:28', '2019-08-09 23:24:28');
INSERT INTO `unimall_order_sku` VALUES ('73', '2784', '1236775', '59', '1012019082339591016', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-09 23:39:59', '2019-08-09 23:39:59');
INSERT INTO `unimall_order_sku` VALUES ('74', '2784', '1236775', '60', '1012019082345121017', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-09 23:45:13', '2019-08-09 23:45:13');
INSERT INTO `unimall_order_sku` VALUES ('75', '2783', '1236774', '61', '1012019082347461018', '百加世NOW FRESH 无谷成猫粮 4磅', '标准', '180001', '1', null, '33480', '27900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/8e168314e8f14558a89729ec4fb86f17.jpg', '2019-08-09 23:47:46', '2019-08-09 23:47:46');
INSERT INTO `unimall_order_sku` VALUES ('76', '2776', '1236770', '61', '1012019082347461018', '宠儿香 家庭专用系列 美健膳食罐头 90g', '标准', '130001', '1', null, '1920', '1600', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2b86ba9cfc0c40fc82f7741c06270f36.jpg', '2019-08-09 23:47:46', '2019-08-09 23:47:46');
INSERT INTO `unimall_order_sku` VALUES ('77', '2773', '1236768', '61', '1012019082347461018', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-09 23:47:46', '2019-08-09 23:47:46');
INSERT INTO `unimall_order_sku` VALUES ('78', '2784', '1236775', '62', '1012019080020441019', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-10 00:20:45', '2019-08-10 00:20:45');
INSERT INTO `unimall_order_sku` VALUES ('79', '2784', '1236775', '63', '1012019080022481020', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-10 00:22:49', '2019-08-10 00:22:49');
INSERT INTO `unimall_order_sku` VALUES ('80', '2784', '1236775', '64', '1012019080023281021', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-10 00:23:28', '2019-08-10 00:23:28');
INSERT INTO `unimall_order_sku` VALUES ('81', '2784', '1236775', '65', '1012019080023481022', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-10 00:23:48', '2019-08-10 00:23:48');
INSERT INTO `unimall_order_sku` VALUES ('82', '2784', '1236775', '66', '1012019080023481023', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-10 00:23:48', '2019-08-10 00:23:48');
INSERT INTO `unimall_order_sku` VALUES ('83', '2784', '1236775', '67', '1012019080025061024', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-10 00:25:06', '2019-08-10 00:25:06');
INSERT INTO `unimall_order_sku` VALUES ('84', '2773', '1236768', '68', '1012019080059101025', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-10 00:59:11', '2019-08-10 00:59:11');
INSERT INTO `unimall_order_sku` VALUES ('85', '2781', '1236772', '69', '1012019080625521026', '路斯 手工坊系列 香薰牛肉丁 200g', '200g', '160001', '1', null, '1800', '1380', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/e48b4f8c1c824e1a9b9f349be0275648.jpg', '2019-08-10 06:25:53', '2019-08-10 06:25:53');
INSERT INTO `unimall_order_sku` VALUES ('86', '2776', '1236770', '70', '1012019080857141027', '宠儿香 家庭专用系列 美健膳食罐头 90g', '标准', '130001', '1', null, '1920', '1600', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2b86ba9cfc0c40fc82f7741c06270f36.jpg', '2019-08-10 08:57:14', '2019-08-10 08:57:14');
INSERT INTO `unimall_order_sku` VALUES ('87', '2774', '1236768', '71', '1012019080915171028', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '1.5KG', '110001', '1', null, '8640', '7200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-10 09:15:18', '2019-08-10 09:15:18');
INSERT INTO `unimall_order_sku` VALUES ('88', '2784', '1236775', '72', '1012019080950121029', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '4', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-10 09:50:13', '2019-08-10 09:50:13');
INSERT INTO `unimall_order_sku` VALUES ('89', '2782', '1236773', '73', '1012019080951071030', '比利时原装进口 欧帝亿IMPERIAL PAW 鸡肉幼猫猫粮 7.5kg', '7.5KG', '170001', '1', null, '54900', '53200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/4f817407a019439da765bf0dc5e7b3b7.jpg', '2019-08-10 09:51:08', '2019-08-10 09:51:08');
INSERT INTO `unimall_order_sku` VALUES ('90', '2784', '1236775', '74', '1012019081056491031', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-10 10:56:49', '2019-08-10 10:56:49');
INSERT INTO `unimall_order_sku` VALUES ('91', '2778', '1236771', '75', '1012019081101281032', '昵趣NaTruse 山羊奶配方狗狗洁齿骨 盒装20g*40支', '蔓越莓', '150003', '1', null, '10690', '8900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/86338c9e576342baa0d079bc1caef9cc.jpg', '2019-08-10 11:01:28', '2019-08-10 11:01:28');
INSERT INTO `unimall_order_sku` VALUES ('92', '2784', '1236775', '76', '1012019081113561033', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-10 11:13:57', '2019-08-10 11:13:57');
INSERT INTO `unimall_order_sku` VALUES ('93', '2784', '1236775', '77', '1012019081114241034', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-10 11:14:25', '2019-08-10 11:14:25');
INSERT INTO `unimall_order_sku` VALUES ('94', '2784', '1236775', '78', '1012019081250581035', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-10 12:50:59', '2019-08-10 12:50:59');
INSERT INTO `unimall_order_sku` VALUES ('95', '2784', '1236775', '79', '1012019081313161001', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-10 13:13:16', '2019-08-10 13:13:16');
INSERT INTO `unimall_order_sku` VALUES ('96', '2784', '1236775', '80', '1012019081320281002', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-10 13:20:29', '2019-08-10 13:20:29');
INSERT INTO `unimall_order_sku` VALUES ('97', '2784', '1236775', '81', '1012019081324391003', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-10 13:24:40', '2019-08-10 13:24:40');
INSERT INTO `unimall_order_sku` VALUES ('98', '2784', '1236775', '82', '1012019081325061004', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-10 13:25:06', '2019-08-10 13:25:06');
INSERT INTO `unimall_order_sku` VALUES ('99', '2784', '1236775', '83', '1012019081325061005', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-10 13:25:07', '2019-08-10 13:25:07');
INSERT INTO `unimall_order_sku` VALUES ('100', '2784', '1236775', '84', '1012019081325191006', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-10 13:25:20', '2019-08-10 13:25:20');
INSERT INTO `unimall_order_sku` VALUES ('101', '2784', '1236775', '85', '1012019081325191007', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-10 13:25:20', '2019-08-10 13:25:20');
INSERT INTO `unimall_order_sku` VALUES ('102', '2784', '1236775', '86', '1012019081325391008', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-10 13:25:40', '2019-08-10 13:25:40');
INSERT INTO `unimall_order_sku` VALUES ('103', '2784', '1236775', '87', '1012019081325401009', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-10 13:25:40', '2019-08-10 13:25:40');
INSERT INTO `unimall_order_sku` VALUES ('104', '2784', '1236775', '88', '1012019081326051010', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-10 13:26:06', '2019-08-10 13:26:06');
INSERT INTO `unimall_order_sku` VALUES ('105', '2784', '1236775', '89', '1012019081326051011', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-10 13:26:06', '2019-08-10 13:26:06');
INSERT INTO `unimall_order_sku` VALUES ('106', '2784', '1236775', '90', '1012019081326061012', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-10 13:26:06', '2019-08-10 13:26:06');
INSERT INTO `unimall_order_sku` VALUES ('107', '2784', '1236775', '91', '1012019081341451013', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-10 13:41:46', '2019-08-10 13:41:46');
INSERT INTO `unimall_order_sku` VALUES ('108', '2774', '1236768', '92', '1012019081358311014', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '1.5KG', '110001', '1', null, '8640', '7200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-10 13:58:31', '2019-08-10 13:58:31');
INSERT INTO `unimall_order_sku` VALUES ('109', '2784', '1236775', '93', '1012019081426231015', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-10 14:26:23', '2019-08-10 14:26:23');
INSERT INTO `unimall_order_sku` VALUES ('110', '2784', '1236775', '94', '1012019081556001016', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-10 15:56:00', '2019-08-10 15:56:00');
INSERT INTO `unimall_order_sku` VALUES ('111', '2773', '1236768', '95', '1012019081559011017', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-10 15:59:02', '2019-08-10 15:59:02');
INSERT INTO `unimall_order_sku` VALUES ('112', '2773', '1236768', '96', '1012019081559071018', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-10 15:59:08', '2019-08-10 15:59:08');
INSERT INTO `unimall_order_sku` VALUES ('113', '2775', '1236769', '97', '1012019081641111019', '耐吉斯SOLUTION 比利时版 鸡肉+三文鱼配方奶糕粮 18磅/8.16kg', '8.16KG', '120001', '1', null, '57600', '46000', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/55c59f82af4e4aefb9f78ce2484b9f36.png', '2019-08-10 16:41:12', '2019-08-10 16:41:12');
INSERT INTO `unimall_order_sku` VALUES ('114', '2773', '1236768', '98', '1012019081704471020', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-10 17:04:48', '2019-08-10 17:04:48');
INSERT INTO `unimall_order_sku` VALUES ('115', '2773', '1236768', '99', '1012019081717391021', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-10 17:17:39', '2019-08-10 17:17:39');
INSERT INTO `unimall_order_sku` VALUES ('116', '2773', '1236768', '100', '1012019081837351022', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-10 18:37:35', '2019-08-10 18:37:35');
INSERT INTO `unimall_order_sku` VALUES ('117', '2773', '1236768', '101', '1012019081925591023', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-10 19:26:00', '2019-08-10 19:26:00');
INSERT INTO `unimall_order_sku` VALUES ('118', '2773', '1236768', '102', '1012019082125431024', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-10 21:25:43', '2019-08-10 21:25:43');
INSERT INTO `unimall_order_sku` VALUES ('119', '2776', '1236770', '103', '1012019082154151025', '宠儿香 家庭专用系列 美健膳食罐头 90g', '标准', '130001', '1', null, '1920', '1600', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2b86ba9cfc0c40fc82f7741c06270f36.jpg', '2019-08-10 21:54:15', '2019-08-10 21:54:15');
INSERT INTO `unimall_order_sku` VALUES ('120', '2784', '1236775', '104', '1012019082154511026', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-10 21:54:51', '2019-08-10 21:54:51');
INSERT INTO `unimall_order_sku` VALUES ('121', '2784', '1236775', '105', '1012019082314351027', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-10 23:14:35', '2019-08-10 23:14:35');
INSERT INTO `unimall_order_sku` VALUES ('122', '2783', '1236774', '106', '1012019080034501028', '百加世NOW FRESH 无谷成猫粮 4磅', '标准', '180001', '1', null, '33480', '27900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/8e168314e8f14558a89729ec4fb86f17.jpg', '2019-08-11 00:34:51', '2019-08-11 00:34:51');
INSERT INTO `unimall_order_sku` VALUES ('123', '2776', '1236770', '107', '1012019080950091029', '宠儿香 家庭专用系列 美健膳食罐头 90g', '标准', '130001', '1', null, '1920', '1600', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2b86ba9cfc0c40fc82f7741c06270f36.jpg', '2019-08-11 09:50:10', '2019-08-11 09:50:10');
INSERT INTO `unimall_order_sku` VALUES ('124', '2784', '1236775', '108', '1012019081228031030', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-11 12:28:04', '2019-08-11 12:28:04');
INSERT INTO `unimall_order_sku` VALUES ('125', '2774', '1236768', '109', '1012019081232101031', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '1.5KG', '110001', '2', null, '8640', '7200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-11 12:32:10', '2019-08-11 12:32:10');
INSERT INTO `unimall_order_sku` VALUES ('126', '2781', '1236772', '110', '1012019081422011001', '路斯 手工坊系列 香薰牛肉丁 200g', '200g', '160001', '1', null, '1800', '1380', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/e48b4f8c1c824e1a9b9f349be0275648.jpg', '2019-08-11 14:22:01', '2019-08-11 14:22:01');
INSERT INTO `unimall_order_sku` VALUES ('127', '2781', '1236772', '111', '1012019081422101002', '路斯 手工坊系列 香薰牛肉丁 200g', '200g', '160001', '1', null, '1800', '1380', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/e48b4f8c1c824e1a9b9f349be0275648.jpg', '2019-08-11 14:22:10', '2019-08-11 14:22:10');
INSERT INTO `unimall_order_sku` VALUES ('128', '2773', '1236768', '112', '1012019081446241003', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-11 14:46:25', '2019-08-11 14:46:25');
INSERT INTO `unimall_order_sku` VALUES ('129', '2784', '1236775', '113', '1012019081447161004', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-11 14:47:17', '2019-08-11 14:47:17');
INSERT INTO `unimall_order_sku` VALUES ('130', '2783', '1236774', '114', '1012019081531121005', '百加世NOW FRESH 无谷成猫粮 4磅', '标准', '180001', '1', null, '33480', '27900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/8e168314e8f14558a89729ec4fb86f17.jpg', '2019-08-11 15:31:12', '2019-08-11 15:31:12');
INSERT INTO `unimall_order_sku` VALUES ('131', '2783', '1236774', '115', '1012019081531421006', '百加世NOW FRESH 无谷成猫粮 4磅', '标准', '180001', '1', null, '33480', '27900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/8e168314e8f14558a89729ec4fb86f17.jpg', '2019-08-11 15:31:42', '2019-08-11 15:31:42');
INSERT INTO `unimall_order_sku` VALUES ('132', '2773', '1236768', '116', '1012019081700191007', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-11 17:00:20', '2019-08-11 17:00:20');
INSERT INTO `unimall_order_sku` VALUES ('133', '2773', '1236768', '117', '1012019081711001008', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-11 17:11:01', '2019-08-11 17:11:01');
INSERT INTO `unimall_order_sku` VALUES ('134', '2774', '1236768', '118', '1012019081748181009', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '1.5KG', '110001', '1', null, '8640', '7200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-11 17:48:19', '2019-08-11 17:48:19');
INSERT INTO `unimall_order_sku` VALUES ('135', '2783', '1236774', '119', '1012019082155231010', '百加世NOW FRESH 无谷成猫粮 4磅', '标准', '180001', '1', null, '33480', '27900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/8e168314e8f14558a89729ec4fb86f17.jpg', '2019-08-11 21:55:23', '2019-08-11 21:55:23');
INSERT INTO `unimall_order_sku` VALUES ('136', '2775', '1236769', '120', '1012019082351371011', '耐吉斯SOLUTION 比利时版 鸡肉+三文鱼配方奶糕粮 18磅/8.16kg', '8.16KG', '120001', '1', null, '57600', '46000', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/55c59f82af4e4aefb9f78ce2484b9f36.png', '2019-08-11 23:51:37', '2019-08-11 23:51:37');
INSERT INTO `unimall_order_sku` VALUES ('137', '2781', '1236772', '121', '1012019080109141012', '路斯 手工坊系列 香薰牛肉丁 200g', '200g', '160001', '2', null, '1800', '1380', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/e48b4f8c1c824e1a9b9f349be0275648.jpg', '2019-08-12 01:09:14', '2019-08-12 01:09:14');
INSERT INTO `unimall_order_sku` VALUES ('138', '2778', '1236771', '122', '1012019080813171013', '昵趣NaTruse 山羊奶配方狗狗洁齿骨 盒装20g*40支', '蔓越莓', '150003', '1', null, '10690', '8900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/86338c9e576342baa0d079bc1caef9cc.jpg', '2019-08-12 08:13:18', '2019-08-12 08:13:18');
INSERT INTO `unimall_order_sku` VALUES ('139', '2773', '1236768', '123', '1012019080933221014', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-12 09:33:22', '2019-08-12 09:33:22');
INSERT INTO `unimall_order_sku` VALUES ('140', '2776', '1236770', '124', '1012019081012051015', '宠儿香 家庭专用系列 美健膳食罐头 90g', '标准', '130001', '1', null, '1920', '1600', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2b86ba9cfc0c40fc82f7741c06270f36.jpg', '2019-08-12 10:12:06', '2019-08-12 10:12:06');
INSERT INTO `unimall_order_sku` VALUES ('141', '2779', '1236771', '125', '1012019081015011016', '昵趣NaTruse 山羊奶配方狗狗洁齿骨 盒装20g*40支', '螺旋藻', '150002', '1', null, '10690', '8900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/86338c9e576342baa0d079bc1caef9cc.jpg', '2019-08-12 10:15:02', '2019-08-12 10:15:02');
INSERT INTO `unimall_order_sku` VALUES ('142', '2782', '1236773', '126', '1012019081358591017', '比利时原装进口 欧帝亿IMPERIAL PAW 鸡肉幼猫猫粮 7.5kg', '7.5KG', '170001', '1', null, '54900', '53200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/4f817407a019439da765bf0dc5e7b3b7.jpg', '2019-08-12 13:58:59', '2019-08-12 13:58:59');
INSERT INTO `unimall_order_sku` VALUES ('143', '2776', '1236770', '126', '1012019081358591017', '宠儿香 家庭专用系列 美健膳食罐头 90g', '标准', '130001', '1', null, '1920', '1600', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2b86ba9cfc0c40fc82f7741c06270f36.jpg', '2019-08-12 13:58:59', '2019-08-12 13:58:59');
INSERT INTO `unimall_order_sku` VALUES ('144', '2773', '1236768', '127', '1012019081448221018', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-12 14:48:22', '2019-08-12 14:48:22');
INSERT INTO `unimall_order_sku` VALUES ('145', '2774', '1236768', '128', '1012019081553031019', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '1.5KG', '110001', '1', null, '8640', '7200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-12 15:53:04', '2019-08-12 15:53:04');
INSERT INTO `unimall_order_sku` VALUES ('146', '2774', '1236768', '129', '1012019081651501020', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '1.5KG', '110001', '1', null, '8640', '7200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-12 16:51:51', '2019-08-12 16:51:51');
INSERT INTO `unimall_order_sku` VALUES ('147', '2782', '1236773', '130', '1012019081656061001', '比利时原装进口 欧帝亿IMPERIAL PAW 鸡肉幼猫猫粮 7.5kg', '7.5KG', '170001', '1', null, '54900', '53200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/4f817407a019439da765bf0dc5e7b3b7.jpg', '2019-08-12 16:56:06', '2019-08-12 16:56:06');
INSERT INTO `unimall_order_sku` VALUES ('148', '2773', '1236768', '131', '1012019081845191001', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-12 18:45:20', '2019-08-12 18:45:20');
INSERT INTO `unimall_order_sku` VALUES ('149', '2784', '1236775', '132', '1012019081852041002', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-12 18:52:04', '2019-08-12 18:52:04');
INSERT INTO `unimall_order_sku` VALUES ('150', '2776', '1236770', '133', '1012019082034411003', '宠儿香 家庭专用系列 美健膳食罐头 90g', '标准', '130001', '1', null, '1920', '1600', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2b86ba9cfc0c40fc82f7741c06270f36.jpg', '2019-08-12 20:34:41', '2019-08-12 20:34:41');
INSERT INTO `unimall_order_sku` VALUES ('151', '2783', '1236774', '134', '1012019082045211004', '百加世NOW FRESH 无谷成猫粮 4磅', '标准', '180001', '1', null, '33480', '27900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/8e168314e8f14558a89729ec4fb86f17.jpg', '2019-08-12 20:45:21', '2019-08-12 20:45:21');
INSERT INTO `unimall_order_sku` VALUES ('152', '2773', '1236768', '135', '1012019080844091005', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '3', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-13 08:44:09', '2019-08-13 08:44:09');
INSERT INTO `unimall_order_sku` VALUES ('153', '2773', '1236768', '136', '1012019080901591006', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-13 09:01:59', '2019-08-13 09:01:59');
INSERT INTO `unimall_order_sku` VALUES ('154', '2774', '1236768', '137', '1012019081055311007', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '1.5KG', '110001', '1', null, '8640', '7200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-13 10:55:31', '2019-08-13 10:55:31');
INSERT INTO `unimall_order_sku` VALUES ('155', '2787', '1236777', '138', '1012019081102051008', 'unimall商业永久授权', '仅授权', '310001', '1', null, '38800', '18800', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2e6c6eeefaf94fac94fcc1157d2fa037.jpeg', '2019-08-13 11:02:06', '2019-08-13 11:02:06');
INSERT INTO `unimall_order_sku` VALUES ('156', '2784', '1236775', '139', '1012019081138141009', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-13 11:38:15', '2019-08-13 11:38:15');
INSERT INTO `unimall_order_sku` VALUES ('157', '2775', '1236769', '139', '1012019081138141009', '耐吉斯SOLUTION 比利时版 鸡肉+三文鱼配方奶糕粮 18磅/8.16kg', '8.16KG', '120001', '1', null, '57600', '46000', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/55c59f82af4e4aefb9f78ce2484b9f36.png', '2019-08-13 11:38:15', '2019-08-13 11:38:15');
INSERT INTO `unimall_order_sku` VALUES ('158', '2784', '1236775', '140', '1012019081203471010', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-13 12:03:47', '2019-08-13 12:03:47');
INSERT INTO `unimall_order_sku` VALUES ('159', '2775', '1236769', '141', '1012019081313331011', '耐吉斯SOLUTION 比利时版 鸡肉+三文鱼配方奶糕粮 18磅/8.16kg', '8.16KG', '120001', '1', null, '57600', '46000', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/55c59f82af4e4aefb9f78ce2484b9f36.png', '2019-08-13 13:13:34', '2019-08-13 13:13:34');
INSERT INTO `unimall_order_sku` VALUES ('160', '2773', '1236768', '142', '1012019081321491012', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-13 13:21:50', '2019-08-13 13:21:50');
INSERT INTO `unimall_order_sku` VALUES ('161', '2781', '1236772', '143', '1012019081402261013', '路斯 手工坊系列 香薰牛肉丁 200g', '200g', '160001', '1', null, '1800', '1380', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/e48b4f8c1c824e1a9b9f349be0275648.jpg', '2019-08-13 14:02:26', '2019-08-13 14:02:26');
INSERT INTO `unimall_order_sku` VALUES ('162', '2781', '1236772', '144', '1012019081402261014', '路斯 手工坊系列 香薰牛肉丁 200g', '200g', '160001', '1', null, '1800', '1380', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/e48b4f8c1c824e1a9b9f349be0275648.jpg', '2019-08-13 14:02:26', '2019-08-13 14:02:26');
INSERT INTO `unimall_order_sku` VALUES ('163', '2781', '1236772', '145', '1012019081402261015', '路斯 手工坊系列 香薰牛肉丁 200g', '200g', '160001', '1', null, '1800', '1380', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/e48b4f8c1c824e1a9b9f349be0275648.jpg', '2019-08-13 14:02:26', '2019-08-13 14:02:26');
INSERT INTO `unimall_order_sku` VALUES ('164', '2773', '1236768', '146', '1012019081429201016', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-13 14:29:21', '2019-08-13 14:29:21');
INSERT INTO `unimall_order_sku` VALUES ('165', '2782', '1236773', '147', '1012019081530491017', '比利时原装进口 欧帝亿IMPERIAL PAW 鸡肉幼猫猫粮 7.5kg', '7.5KG', '170001', '1', null, '54900', '53200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/4f817407a019439da765bf0dc5e7b3b7.jpg', '2019-08-13 15:30:50', '2019-08-13 15:30:50');
INSERT INTO `unimall_order_sku` VALUES ('166', '2773', '1236768', '148', '1012019081641391018', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-13 16:41:40', '2019-08-13 16:41:40');
INSERT INTO `unimall_order_sku` VALUES ('167', '2773', '1236768', '149', '1012019082158371019', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-13 21:58:37', '2019-08-13 21:58:37');
INSERT INTO `unimall_order_sku` VALUES ('168', '2782', '1236773', '150', '1012019080941131020', '比利时原装进口 欧帝亿IMPERIAL PAW 鸡肉幼猫猫粮 7.5kg', '7.5KG', '170001', '1', null, '54900', '53200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/4f817407a019439da765bf0dc5e7b3b7.jpg', '2019-08-14 09:41:14', '2019-08-14 09:41:14');
INSERT INTO `unimall_order_sku` VALUES ('169', '2785', '1236776', '151', '1012019081135351021', '乐优派 猫砂盆清理用具 4件套', '标准', '200001', '1', null, '100', '2', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/22014e1c20c441949b9e90ef63fa0f64.jpg', '2019-08-14 11:35:36', '2019-08-14 11:35:36');
INSERT INTO `unimall_order_sku` VALUES ('170', '2781', '1236772', '152', '1012019081453401022', '路斯 手工坊系列 香薰牛肉丁 200g', '200g', '160001', '1', null, '1800', '1380', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/e48b4f8c1c824e1a9b9f349be0275648.jpg', '2019-08-14 14:53:41', '2019-08-14 14:53:41');
INSERT INTO `unimall_order_sku` VALUES ('171', '2774', '1236768', '152', '1012019081453401022', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '1.5KG', '110001', '1', null, '8640', '7200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-14 14:53:41', '2019-08-14 14:53:41');
INSERT INTO `unimall_order_sku` VALUES ('172', '2773', '1236768', '153', '1012019082013261023', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-15 20:13:27', '2019-08-15 20:13:27');
INSERT INTO `unimall_order_sku` VALUES ('173', '2775', '1236769', '153', '1012019082013261023', '耐吉斯SOLUTION 比利时版 鸡肉+三文鱼配方奶糕粮 18磅/8.16kg', '8.16KG', '120001', '1', null, '57600', '46000', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/55c59f82af4e4aefb9f78ce2484b9f36.png', '2019-08-15 20:13:27', '2019-08-15 20:13:27');
INSERT INTO `unimall_order_sku` VALUES ('174', '2782', '1236773', '154', '1012019082042241024', '比利时原装进口 欧帝亿IMPERIAL PAW 鸡肉幼猫猫粮 7.5kg', '7.5KG', '170001', '1', null, '54900', '53200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/4f817407a019439da765bf0dc5e7b3b7.jpg', '2019-08-15 20:42:25', '2019-08-15 20:42:25');
INSERT INTO `unimall_order_sku` VALUES ('175', '2787', '1236777', '155', '1012019081320261025', 'unimall商业永久授权', '仅授权', '310001', '1', null, '38800', '18800', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2e6c6eeefaf94fac94fcc1157d2fa037.jpeg', '2019-08-16 13:20:27', '2019-08-16 13:20:27');
INSERT INTO `unimall_order_sku` VALUES ('176', '2776', '1236770', '156', '1012019082214491026', '宠儿香 家庭专用系列 美健膳食罐头 90g', '标准', '130001', '1', null, '1920', '1600', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2b86ba9cfc0c40fc82f7741c06270f36.jpg', '2019-08-16 22:14:50', '2019-08-16 22:14:50');
INSERT INTO `unimall_order_sku` VALUES ('177', '2781', '1236772', '156', '1012019082214491026', '路斯 手工坊系列 香薰牛肉丁 200g', '200g', '160001', '1', null, '1800', '1380', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/e48b4f8c1c824e1a9b9f349be0275648.jpg', '2019-08-16 22:14:50', '2019-08-16 22:14:50');
INSERT INTO `unimall_order_sku` VALUES ('178', '2785', '1236776', '156', '1012019082214491026', '乐优派 猫砂盆清理用具 4件套', '标准', '200001', '1', null, '100', '2', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/22014e1c20c441949b9e90ef63fa0f64.jpg', '2019-08-16 22:14:50', '2019-08-16 22:14:50');
INSERT INTO `unimall_order_sku` VALUES ('179', '2784', '1236775', '157', '1012019082218511027', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-16 22:18:52', '2019-08-16 22:18:52');
INSERT INTO `unimall_order_sku` VALUES ('180', '2784', '1236775', '158', '1012019081135271028', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-17 11:35:28', '2019-08-17 11:35:28');
INSERT INTO `unimall_order_sku` VALUES ('181', '2784', '1236775', '159', '1012019081204101029', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-17 12:04:11', '2019-08-17 12:04:11');
INSERT INTO `unimall_order_sku` VALUES ('182', '2776', '1236770', '160', '1012019081233311030', '宠儿香 家庭专用系列 美健膳食罐头 90g', '标准', '130001', '1', null, '1920', '1600', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2b86ba9cfc0c40fc82f7741c06270f36.jpg', '2019-08-17 12:33:32', '2019-08-17 12:33:32');
INSERT INTO `unimall_order_sku` VALUES ('183', '2775', '1236769', '161', '1012019081239191031', '耐吉斯SOLUTION 比利时版 鸡肉+三文鱼配方奶糕粮 18磅/8.16kg', '8.16KG', '120001', '1', null, '57600', '46000', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/55c59f82af4e4aefb9f78ce2484b9f36.png', '2019-08-17 12:39:20', '2019-08-17 12:39:20');
INSERT INTO `unimall_order_sku` VALUES ('184', '2776', '1236770', '161', '1012019081239191031', '宠儿香 家庭专用系列 美健膳食罐头 90g', '标准', '130001', '2', null, '1920', '1600', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2b86ba9cfc0c40fc82f7741c06270f36.jpg', '2019-08-17 12:39:20', '2019-08-17 12:39:20');
INSERT INTO `unimall_order_sku` VALUES ('185', '2775', '1236769', '162', '1012019081346431032', '耐吉斯SOLUTION 比利时版 鸡肉+三文鱼配方奶糕粮 18磅/8.16kg', '8.16KG', '120001', '1', null, '57600', '46000', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/55c59f82af4e4aefb9f78ce2484b9f36.png', '2019-08-17 13:46:43', '2019-08-17 13:46:43');
INSERT INTO `unimall_order_sku` VALUES ('186', '2783', '1236774', '163', '1012019081414221033', '百加世NOW FRESH 无谷成猫粮 4磅', '标准', '180001', '1', null, '33480', '27900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/8e168314e8f14558a89729ec4fb86f17.jpg', '2019-08-17 14:14:23', '2019-08-17 14:14:23');
INSERT INTO `unimall_order_sku` VALUES ('187', '2776', '1236770', '164', '1012019081654341034', '宠儿香 家庭专用系列 美健膳食罐头 90g', '标准', '130001', '1', null, '1920', '1600', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2b86ba9cfc0c40fc82f7741c06270f36.jpg', '2019-08-17 16:54:35', '2019-08-17 16:54:35');
INSERT INTO `unimall_order_sku` VALUES ('188', '2775', '1236769', '165', '1012019081127371035', '耐吉斯SOLUTION 比利时版 鸡肉+三文鱼配方奶糕粮 18磅/8.16kg', '8.16KG', '120001', '1', null, '57600', '46000', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/55c59f82af4e4aefb9f78ce2484b9f36.png', '2019-08-18 11:27:37', '2019-08-18 11:27:37');
INSERT INTO `unimall_order_sku` VALUES ('189', '2773', '1236768', '166', '1012019081612351036', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-18 16:12:35', '2019-08-18 16:12:35');
INSERT INTO `unimall_order_sku` VALUES ('190', '2774', '1236768', '167', '1012019081036041037', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '1.5KG', '110001', '1', null, '8640', '7200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-19 10:36:05', '2019-08-19 10:36:05');
INSERT INTO `unimall_order_sku` VALUES ('191', '2773', '1236768', '168', '1012019081036291038', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-19 10:36:30', '2019-08-19 10:36:30');
INSERT INTO `unimall_order_sku` VALUES ('192', '2773', '1236768', '169', '1012019082315581039', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-19 23:15:59', '2019-08-19 23:15:59');
INSERT INTO `unimall_order_sku` VALUES ('193', '2786', '1236777', '170', '1012019082348541040', 'unimall商业永久授权', '帮安装', '310002', '1', null, '99900', '68800', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2e6c6eeefaf94fac94fcc1157d2fa037.jpeg', '2019-08-19 23:48:55', '2019-08-19 23:48:55');
INSERT INTO `unimall_order_sku` VALUES ('194', '2784', '1236775', '171', '1012019081238311001', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-21 12:38:32', '2019-08-21 12:38:32');
INSERT INTO `unimall_order_sku` VALUES ('195', '2784', '1236775', '172', '1012019081239321002', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-21 12:39:33', '2019-08-21 12:39:33');
INSERT INTO `unimall_order_sku` VALUES ('196', '2787', '1236777', '173', '1012019081240341003', 'unimall商业永久授权', '仅授权', '310001', '1', null, '38800', '18800', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2e6c6eeefaf94fac94fcc1157d2fa037.jpeg', '2019-08-21 12:40:35', '2019-08-21 12:40:35');
INSERT INTO `unimall_order_sku` VALUES ('197', '2784', '1236775', '174', '1012019081241201004', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-21 12:41:20', '2019-08-21 12:41:20');
INSERT INTO `unimall_order_sku` VALUES ('198', '2784', '1236775', '175', '1012019081243421005', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-21 12:43:42', '2019-08-21 12:43:42');
INSERT INTO `unimall_order_sku` VALUES ('199', '2784', '1236775', '176', '1012019081254171006', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-21 12:54:18', '2019-08-21 12:54:18');
INSERT INTO `unimall_order_sku` VALUES ('200', '2787', '1236777', '177', '1012019081401201001', 'unimall商业永久授权', '仅授权', '310001', '1', null, '38800', '18800', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2e6c6eeefaf94fac94fcc1157d2fa037.jpeg', '2019-08-21 14:01:20', '2019-08-21 14:01:20');
INSERT INTO `unimall_order_sku` VALUES ('201', '2787', '1236777', '178', '1012019081433061002', 'unimall商业永久授权', '仅授权', '310001', '1', null, '38800', '18800', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2e6c6eeefaf94fac94fcc1157d2fa037.jpeg', '2019-08-21 14:33:06', '2019-08-21 14:33:06');
INSERT INTO `unimall_order_sku` VALUES ('202', '2784', '1236775', '179', '1012019080920031003', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-22 09:20:04', '2019-08-22 09:20:04');
INSERT INTO `unimall_order_sku` VALUES ('203', '2784', '1236775', '180', '1012019081002331004', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-22 10:02:34', '2019-08-22 10:02:34');
INSERT INTO `unimall_order_sku` VALUES ('204', '2787', '1236777', '181', '1012019081049411005', 'unimall商业永久授权', '仅授权', '310001', '1', null, '38800', '18800', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2e6c6eeefaf94fac94fcc1157d2fa037.jpeg', '2019-08-22 10:49:42', '2019-08-22 10:49:42');
INSERT INTO `unimall_order_sku` VALUES ('205', '2783', '1236774', '182', '1012019081356031006', '百加世NOW FRESH 无谷成猫粮 4磅', '标准', '180001', '1', null, '33480', '27900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/8e168314e8f14558a89729ec4fb86f17.jpg', '2019-08-22 13:56:04', '2019-08-22 13:56:04');
INSERT INTO `unimall_order_sku` VALUES ('206', '2784', '1236775', '183', '1012019081518481007', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-22 15:18:49', '2019-08-22 15:18:49');
INSERT INTO `unimall_order_sku` VALUES ('207', '2787', '1236777', '184', '1012019081645121008', 'unimall商业永久授权', '仅授权', '310001', '1', null, '38800', '18800', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2e6c6eeefaf94fac94fcc1157d2fa037.jpeg', '2019-08-22 16:45:13', '2019-08-22 16:45:13');
INSERT INTO `unimall_order_sku` VALUES ('208', '2782', '1236773', '185', '1012019081645261009', '比利时原装进口 欧帝亿IMPERIAL PAW 鸡肉幼猫猫粮 7.5kg', '7.5KG', '170001', '1', null, '54900', '53200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/4f817407a019439da765bf0dc5e7b3b7.jpg', '2019-08-22 16:45:26', '2019-08-22 16:45:26');
INSERT INTO `unimall_order_sku` VALUES ('209', '2774', '1236768', '186', '1012019080607401010', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '1.5KG', '110001', '1', null, '8640', '7200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-23 06:07:40', '2019-08-23 06:07:40');
INSERT INTO `unimall_order_sku` VALUES ('210', '2784', '1236775', '187', '1012019081037301011', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-23 10:37:30', '2019-08-23 10:37:30');
INSERT INTO `unimall_order_sku` VALUES ('211', '2773', '1236768', '188', '1012019081313141012', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-23 13:13:14', '2019-08-23 13:13:14');
INSERT INTO `unimall_order_sku` VALUES ('212', '2782', '1236773', '189', '1012019081320141013', '比利时原装进口 欧帝亿IMPERIAL PAW 鸡肉幼猫猫粮 7.5kg', '7.5KG', '170001', '2', null, '54900', '53200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/4f817407a019439da765bf0dc5e7b3b7.jpg', '2019-08-23 13:20:15', '2019-08-23 13:20:15');
INSERT INTO `unimall_order_sku` VALUES ('213', '2787', '1236777', '190', '1012019081320551014', 'unimall商业永久授权', '仅授权', '310001', '1', null, '38800', '18800', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2e6c6eeefaf94fac94fcc1157d2fa037.jpeg', '2019-08-23 13:20:56', '2019-08-23 13:20:56');
INSERT INTO `unimall_order_sku` VALUES ('214', '2773', '1236768', '191', '1012019081336411015', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-23 13:36:42', '2019-08-23 13:36:42');
INSERT INTO `unimall_order_sku` VALUES ('215', '2784', '1236775', '192', '1012019081509041016', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-23 15:09:04', '2019-08-23 15:09:04');
INSERT INTO `unimall_order_sku` VALUES ('216', '2784', '1236775', '193', '1012019081517141017', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-23 15:17:14', '2019-08-23 15:17:14');
INSERT INTO `unimall_order_sku` VALUES ('217', '2784', '1236775', '194', '1012019081527391018', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-23 15:27:40', '2019-08-23 15:27:40');
INSERT INTO `unimall_order_sku` VALUES ('218', '2784', '1236775', '195', '1012019081534011019', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-23 15:34:02', '2019-08-23 15:34:02');
INSERT INTO `unimall_order_sku` VALUES ('219', '2784', '1236775', '196', '1012019081540241001', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-23 15:40:24', '2019-08-23 15:40:24');
INSERT INTO `unimall_order_sku` VALUES ('220', '2784', '1236775', '197', '1012019081627151002', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-23 16:27:15', '2019-08-23 16:27:15');
INSERT INTO `unimall_order_sku` VALUES ('221', '2784', '1236775', '198', '1012019081627521003', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-23 16:27:53', '2019-08-23 16:27:53');
INSERT INTO `unimall_order_sku` VALUES ('222', '2784', '1236775', '199', '1012019081628211004', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-23 16:28:21', '2019-08-23 16:28:21');
INSERT INTO `unimall_order_sku` VALUES ('223', '2784', '1236775', '200', '1012019081637141005', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-23 16:37:15', '2019-08-23 16:37:15');
INSERT INTO `unimall_order_sku` VALUES ('224', '2784', '1236775', '201', '1012019081646101006', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-23 16:46:10', '2019-08-23 16:46:10');
INSERT INTO `unimall_order_sku` VALUES ('225', '2784', '1236775', '202', '1012019081646421007', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-23 16:46:43', '2019-08-23 16:46:43');
INSERT INTO `unimall_order_sku` VALUES ('226', '2784', '1236775', '203', '1012019081657041008', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-23 16:57:05', '2019-08-23 16:57:05');
INSERT INTO `unimall_order_sku` VALUES ('227', '2784', '1236775', '204', '1012019081701401009', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-23 17:01:40', '2019-08-23 17:01:40');
INSERT INTO `unimall_order_sku` VALUES ('228', '2784', '1236775', '205', '1012019081802511001', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-23 18:02:51', '2019-08-23 18:02:51');
INSERT INTO `unimall_order_sku` VALUES ('229', '2784', '1236775', '206', '1012019081804391002', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-23 18:04:39', '2019-08-23 18:04:39');
INSERT INTO `unimall_order_sku` VALUES ('230', '2784', '1236775', '207', '1012019081005291003', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-24 10:05:30', '2019-08-24 10:05:30');
INSERT INTO `unimall_order_sku` VALUES ('231', '2773', '1236768', '208', '1012019080748481004', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-25 07:48:48', '2019-08-25 07:48:48');
INSERT INTO `unimall_order_sku` VALUES ('232', '2784', '1236775', '209', '1012019081051141005', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-25 10:51:15', '2019-08-25 10:51:15');
INSERT INTO `unimall_order_sku` VALUES ('233', '2782', '1236773', '210', '1012019081554461006', '比利时原装进口 欧帝亿IMPERIAL PAW 鸡肉幼猫猫粮 7.5kg', '7.5KG', '170001', '1', null, '54900', '53200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/4f817407a019439da765bf0dc5e7b3b7.jpg', '2019-08-25 15:54:46', '2019-08-25 15:54:46');
INSERT INTO `unimall_order_sku` VALUES ('234', '2773', '1236768', '211', '1012019081038291007', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-08-26 10:38:30', '2019-08-26 10:38:30');
INSERT INTO `unimall_order_sku` VALUES ('235', '2775', '1236769', '212', '1012019081407531008', '耐吉斯SOLUTION 比利时版 鸡肉+三文鱼配方奶糕粮 18磅/8.16kg', '8.16KG', '120001', '1', null, '57600', '46000', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/55c59f82af4e4aefb9f78ce2484b9f36.png', '2019-08-26 14:07:54', '2019-08-26 14:07:54');
INSERT INTO `unimall_order_sku` VALUES ('236', '2776', '1236770', '213', '1012019082057501009', '宠儿香 家庭专用系列 美健膳食罐头 90g', '标准', '130001', '1', null, '1920', '1600', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2b86ba9cfc0c40fc82f7741c06270f36.jpg', '2019-08-26 20:57:51', '2019-08-26 20:57:51');
INSERT INTO `unimall_order_sku` VALUES ('237', '2782', '1236773', '214', '1012019081045511010', '比利时原装进口 欧帝亿IMPERIAL PAW 鸡肉幼猫猫粮 7.5kg', '7.5KG', '170001', '1', null, '54900', '53200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/4f817407a019439da765bf0dc5e7b3b7.jpg', '2019-08-27 10:45:52', '2019-08-27 10:45:52');
INSERT INTO `unimall_order_sku` VALUES ('238', '2784', '1236775', '215', '1012019081927321011', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-27 19:27:32', '2019-08-27 19:27:32');
INSERT INTO `unimall_order_sku` VALUES ('239', '2784', '1236775', '216', '1012019082143111012', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-28 21:43:11', '2019-08-28 21:43:11');
INSERT INTO `unimall_order_sku` VALUES ('240', '2776', '1236770', '217', '1012019082346361013', '宠儿香 家庭专用系列 美健膳食罐头 90g', '标准', '130001', '1', null, '1920', '1600', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2b86ba9cfc0c40fc82f7741c06270f36.jpg', '2019-08-28 23:46:36', '2019-08-28 23:46:36');
INSERT INTO `unimall_order_sku` VALUES ('241', '2784', '1236775', '218', '1012019080144591014', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-29 01:44:59', '2019-08-29 01:44:59');
INSERT INTO `unimall_order_sku` VALUES ('242', '2784', '1236775', '219', '1012019080943401015', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-29 09:43:41', '2019-08-29 09:43:41');
INSERT INTO `unimall_order_sku` VALUES ('243', '2782', '1236773', '220', '1012019081354211016', '比利时原装进口 欧帝亿IMPERIAL PAW 鸡肉幼猫猫粮 7.5kg', '7.5KG', '170001', '1', null, '54900', '53200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/4f817407a019439da765bf0dc5e7b3b7.jpg', '2019-08-29 13:54:22', '2019-08-29 13:54:22');
INSERT INTO `unimall_order_sku` VALUES ('244', '2784', '1236775', '221', '1012019081503471017', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-29 15:03:48', '2019-08-29 15:03:48');
INSERT INTO `unimall_order_sku` VALUES ('245', '2780', '1236771', '222', '1012019081627481018', '昵趣NaTruse 山羊奶配方狗狗洁齿骨 盒装20g*40支', '牛油果', '150001', '1', null, '10690', '8900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/86338c9e576342baa0d079bc1caef9cc.jpg', '2019-08-29 16:27:49', '2019-08-29 16:27:49');
INSERT INTO `unimall_order_sku` VALUES ('246', '2785', '1236776', '223', '1012019081719261019', '乐优派 猫砂盆清理用具 4件套', '标准', '200001', '1', null, '100', '2', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/22014e1c20c441949b9e90ef63fa0f64.jpg', '2019-08-29 17:19:26', '2019-08-29 17:19:26');
INSERT INTO `unimall_order_sku` VALUES ('247', '2784', '1236775', '224', '1012019080825511020', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-30 08:25:52', '2019-08-30 08:25:52');
INSERT INTO `unimall_order_sku` VALUES ('248', '2778', '1236771', '225', '1012019082220381021', '昵趣NaTruse 山羊奶配方狗狗洁齿骨 盒装20g*40支', '蔓越莓', '150003', '1', null, '10690', '8900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/86338c9e576342baa0d079bc1caef9cc.jpg', '2019-08-30 22:20:39', '2019-08-30 22:20:39');
INSERT INTO `unimall_order_sku` VALUES ('249', '2777', '1236771', '226', '1012019080847231022', '昵趣NaTruse 山羊奶配方狗狗洁齿骨 盒装20g*40支', '山羊奶', '150004', '1', null, '10690', '8900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/86338c9e576342baa0d079bc1caef9cc.jpg', '2019-08-31 08:47:23', '2019-08-31 08:47:23');
INSERT INTO `unimall_order_sku` VALUES ('250', '2781', '1236772', '227', '1012019080927211023', '路斯 手工坊系列 香薰牛肉丁 200g', '200g', '160001', '1', null, '1800', '1380', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/e48b4f8c1c824e1a9b9f349be0275648.jpg', '2019-08-31 09:27:22', '2019-08-31 09:27:22');
INSERT INTO `unimall_order_sku` VALUES ('251', '2781', '1236772', '228', '1012019080927391024', '路斯 手工坊系列 香薰牛肉丁 200g', '200g', '160001', '1', null, '1800', '1380', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/e48b4f8c1c824e1a9b9f349be0275648.jpg', '2019-08-31 09:27:40', '2019-08-31 09:27:40');
INSERT INTO `unimall_order_sku` VALUES ('252', '2784', '1236775', '229', '1012019081020341025', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-08-31 10:20:34', '2019-08-31 10:20:34');
INSERT INTO `unimall_order_sku` VALUES ('253', '2787', '1236777', '230', '1012019081458451026', 'unimall商业永久授权', '仅授权', '310001', '1', null, '38800', '18800', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2e6c6eeefaf94fac94fcc1157d2fa037.jpeg', '2019-08-31 14:58:45', '2019-08-31 14:58:45');
INSERT INTO `unimall_order_sku` VALUES ('254', '2783', '1236774', '231', '1012019081858001027', '百加世NOW FRESH 无谷成猫粮 4磅', '标准', '180001', '1', null, '33480', '27900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/8e168314e8f14558a89729ec4fb86f17.jpg', '2019-08-31 18:58:00', '2019-08-31 18:58:00');
INSERT INTO `unimall_order_sku` VALUES ('255', '2784', '1236775', '232', '1012019091633051028', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-09-01 16:33:06', '2019-09-01 16:33:06');
INSERT INTO `unimall_order_sku` VALUES ('256', '2781', '1236772', '233', '1012019090722481029', '路斯 手工坊系列 香薰牛肉丁 200g', '200g', '160001', '1', null, '1800', '1380', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/e48b4f8c1c824e1a9b9f349be0275648.jpg', '2019-09-02 07:22:49', '2019-09-02 07:22:49');
INSERT INTO `unimall_order_sku` VALUES ('257', '2784', '1236775', '234', '1012019090958341030', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-09-02 09:58:34', '2019-09-02 09:58:34');
INSERT INTO `unimall_order_sku` VALUES ('258', '2773', '1236768', '235', '1012019091029481031', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-09-02 10:29:49', '2019-09-02 10:29:49');
INSERT INTO `unimall_order_sku` VALUES ('259', '2773', '1236768', '236', '1012019091029491032', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-09-02 10:29:49', '2019-09-02 10:29:49');
INSERT INTO `unimall_order_sku` VALUES ('260', '2779', '1236771', '237', '1012019091031431033', '昵趣NaTruse 山羊奶配方狗狗洁齿骨 盒装20g*40支', '螺旋藻', '150002', '1', null, '10690', '8900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/86338c9e576342baa0d079bc1caef9cc.jpg', '2019-09-02 10:31:43', '2019-09-02 10:31:43');
INSERT INTO `unimall_order_sku` VALUES ('261', '2782', '1236773', '238', '1012019091048261034', '比利时原装进口 欧帝亿IMPERIAL PAW 鸡肉幼猫猫粮 7.5kg', '7.5KG', '170001', '1', null, '54900', '53200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/4f817407a019439da765bf0dc5e7b3b7.jpg', '2019-09-02 10:48:26', '2019-09-02 10:48:26');
INSERT INTO `unimall_order_sku` VALUES ('262', '2773', '1236768', '239', '1012019091237021035', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-09-02 12:37:02', '2019-09-02 12:37:02');
INSERT INTO `unimall_order_sku` VALUES ('263', '2787', '1236777', '240', '1012019091644231036', 'unimall商业永久授权', '仅授权', '310001', '1', null, '38800', '18800', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2e6c6eeefaf94fac94fcc1157d2fa037.jpeg', '2019-09-02 16:44:23', '2019-09-02 16:44:23');
INSERT INTO `unimall_order_sku` VALUES ('264', '2784', '1236775', '241', '1012019091045131037', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-09-03 10:45:13', '2019-09-03 10:45:13');
INSERT INTO `unimall_order_sku` VALUES ('265', '2786', '1236777', '242', '1012019091407241038', 'unimall商业永久授权', '帮安装', '310002', '1', null, '99900', '68800', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2e6c6eeefaf94fac94fcc1157d2fa037.jpeg', '2019-09-03 14:07:24', '2019-09-03 14:07:24');
INSERT INTO `unimall_order_sku` VALUES ('266', '2773', '1236768', '243', '1012019091735101039', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-09-03 17:35:11', '2019-09-03 17:35:11');
INSERT INTO `unimall_order_sku` VALUES ('267', '2781', '1236772', '244', '1012019090849571040', '路斯 手工坊系列 香薰牛肉丁 200g', '200g', '160001', '1', null, '1800', '1380', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/e48b4f8c1c824e1a9b9f349be0275648.jpg', '2019-09-04 08:49:57', '2019-09-04 08:49:57');
INSERT INTO `unimall_order_sku` VALUES ('268', '2781', '1236772', '245', '1012019090850381041', '路斯 手工坊系列 香薰牛肉丁 200g', '200g', '160001', '2', null, '1800', '1380', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/e48b4f8c1c824e1a9b9f349be0275648.jpg', '2019-09-04 08:50:38', '2019-09-04 08:50:38');
INSERT INTO `unimall_order_sku` VALUES ('269', '2781', '1236772', '246', '1012019091412441042', '路斯 手工坊系列 香薰牛肉丁 200g', '200g', '160001', '1', null, '1800', '1380', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/e48b4f8c1c824e1a9b9f349be0275648.jpg', '2019-09-04 14:12:45', '2019-09-04 14:12:45');
INSERT INTO `unimall_order_sku` VALUES ('270', '2781', '1236772', '247', '1012019091412451043', '路斯 手工坊系列 香薰牛肉丁 200g', '200g', '160001', '1', null, '1800', '1380', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/e48b4f8c1c824e1a9b9f349be0275648.jpg', '2019-09-04 14:12:45', '2019-09-04 14:12:45');
INSERT INTO `unimall_order_sku` VALUES ('271', '2784', '1236775', '248', '1012019091435191044', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-09-04 14:35:20', '2019-09-04 14:35:20');
INSERT INTO `unimall_order_sku` VALUES ('272', '2787', '1236777', '249', '1012019091443041045', 'unimall商业永久授权', '仅授权', '310001', '1', null, '38800', '18800', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2e6c6eeefaf94fac94fcc1157d2fa037.jpeg', '2019-09-04 14:43:05', '2019-09-04 14:43:05');
INSERT INTO `unimall_order_sku` VALUES ('273', '2784', '1236775', '250', '1012019091445131046', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-09-04 14:45:14', '2019-09-04 14:45:14');
INSERT INTO `unimall_order_sku` VALUES ('274', '2784', '1236775', '251', '1012019091446031047', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-09-04 14:46:04', '2019-09-04 14:46:04');
INSERT INTO `unimall_order_sku` VALUES ('275', '2773', '1236768', '252', '1012019091530351048', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-09-04 15:30:35', '2019-09-04 15:30:35');
INSERT INTO `unimall_order_sku` VALUES ('276', '2773', '1236768', '253', '1012019091738501049', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-09-05 17:38:51', '2019-09-05 17:38:51');
INSERT INTO `unimall_order_sku` VALUES ('277', '2773', '1236768', '254', '1012019091918381050', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-09-05 19:18:38', '2019-09-05 19:18:38');
INSERT INTO `unimall_order_sku` VALUES ('278', '2774', '1236768', '255', '1012019092258001051', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '1.5KG', '110001', '1', null, '8640', '7200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-09-05 22:58:01', '2019-09-05 22:58:01');
INSERT INTO `unimall_order_sku` VALUES ('279', '2775', '1236769', '255', '1012019092258001051', '耐吉斯SOLUTION 比利时版 鸡肉+三文鱼配方奶糕粮 18磅/8.16kg', '8.16KG', '120001', '1', null, '57600', '46000', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/55c59f82af4e4aefb9f78ce2484b9f36.png', '2019-09-05 22:58:01', '2019-09-05 22:58:01');
INSERT INTO `unimall_order_sku` VALUES ('280', '2782', '1236773', '256', '1012019090950101052', '比利时原装进口 欧帝亿IMPERIAL PAW 鸡肉幼猫猫粮 7.5kg', '7.5KG', '170001', '1', null, '54900', '53200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/4f817407a019439da765bf0dc5e7b3b7.jpg', '2019-09-06 09:50:11', '2019-09-06 09:50:11');
INSERT INTO `unimall_order_sku` VALUES ('281', '2776', '1236770', '256', '1012019090950101052', '宠儿香 家庭专用系列 美健膳食罐头 90g', '标准', '130001', '1', null, '1920', '1600', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2b86ba9cfc0c40fc82f7741c06270f36.jpg', '2019-09-06 09:50:11', '2019-09-06 09:50:11');
INSERT INTO `unimall_order_sku` VALUES ('282', '2775', '1236769', '256', '1012019090950101052', '耐吉斯SOLUTION 比利时版 鸡肉+三文鱼配方奶糕粮 18磅/8.16kg', '8.16KG', '120001', '1', null, '57600', '46000', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/55c59f82af4e4aefb9f78ce2484b9f36.png', '2019-09-06 09:50:11', '2019-09-06 09:50:11');
INSERT INTO `unimall_order_sku` VALUES ('283', '2784', '1236775', '256', '1012019090950101052', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-09-06 09:50:11', '2019-09-06 09:50:11');
INSERT INTO `unimall_order_sku` VALUES ('284', '2774', '1236768', '257', '1012019091107481053', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '1.5KG', '110001', '1', null, '8640', '7200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-09-06 11:07:49', '2019-09-06 11:07:49');
INSERT INTO `unimall_order_sku` VALUES ('285', '2784', '1236775', '258', '1012019091108241054', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-09-06 11:08:25', '2019-09-06 11:08:25');
INSERT INTO `unimall_order_sku` VALUES ('286', '2784', '1236775', '259', '1012019091153291055', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '2', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-09-06 11:53:30', '2019-09-06 11:53:30');
INSERT INTO `unimall_order_sku` VALUES ('287', '2782', '1236773', '260', '1012019091202581056', '比利时原装进口 欧帝亿IMPERIAL PAW 鸡肉幼猫猫粮 7.5kg', '7.5KG', '170001', '1', null, '54900', '53200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/4f817407a019439da765bf0dc5e7b3b7.jpg', '2019-09-06 12:02:58', '2019-09-06 12:02:58');
INSERT INTO `unimall_order_sku` VALUES ('288', '2775', '1236769', '261', '1012019091203531057', '耐吉斯SOLUTION 比利时版 鸡肉+三文鱼配方奶糕粮 18磅/8.16kg', '8.16KG', '120001', '1', null, '57600', '46000', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/55c59f82af4e4aefb9f78ce2484b9f36.png', '2019-09-06 12:03:53', '2019-09-06 12:03:53');
INSERT INTO `unimall_order_sku` VALUES ('289', '2784', '1236775', '262', '1012019091414351058', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-09-06 14:14:36', '2019-09-06 14:14:36');
INSERT INTO `unimall_order_sku` VALUES ('290', '2782', '1236773', '263', '1012019091453571059', '比利时原装进口 欧帝亿IMPERIAL PAW 鸡肉幼猫猫粮 7.5kg', '7.5KG', '170001', '1', null, '54900', '53200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/4f817407a019439da765bf0dc5e7b3b7.jpg', '2019-09-06 14:53:58', '2019-09-06 14:53:58');
INSERT INTO `unimall_order_sku` VALUES ('291', '2782', '1236773', '264', '1012019091454051060', '比利时原装进口 欧帝亿IMPERIAL PAW 鸡肉幼猫猫粮 7.5kg', '7.5KG', '170001', '1', null, '54900', '53200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/4f817407a019439da765bf0dc5e7b3b7.jpg', '2019-09-06 14:54:05', '2019-09-06 14:54:05');
INSERT INTO `unimall_order_sku` VALUES ('292', '2787', '1236777', '265', '1012019091511091061', 'unimall商业永久授权', '仅授权', '310001', '1', null, '38800', '18800', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2e6c6eeefaf94fac94fcc1157d2fa037.jpeg', '2019-09-06 15:11:09', '2019-09-06 15:11:09');
INSERT INTO `unimall_order_sku` VALUES ('293', '2784', '1236775', '266', '1012019090820211062', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-09-07 08:20:22', '2019-09-07 08:20:22');
INSERT INTO `unimall_order_sku` VALUES ('294', '2782', '1236773', '267', '1012019090935471063', '比利时原装进口 欧帝亿IMPERIAL PAW 鸡肉幼猫猫粮 7.5kg', '7.5KG', '170001', '1', null, '54900', '53200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/4f817407a019439da765bf0dc5e7b3b7.jpg', '2019-09-07 09:35:48', '2019-09-07 09:35:48');
INSERT INTO `unimall_order_sku` VALUES ('295', '2782', '1236773', '268', '1012019090936131064', '比利时原装进口 欧帝亿IMPERIAL PAW 鸡肉幼猫猫粮 7.5kg', '7.5KG', '170001', '1', null, '54900', '53200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/4f817407a019439da765bf0dc5e7b3b7.jpg', '2019-09-07 09:36:14', '2019-09-07 09:36:14');
INSERT INTO `unimall_order_sku` VALUES ('296', '2773', '1236768', '268', '1012019090936131064', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-09-07 09:36:14', '2019-09-07 09:36:14');
INSERT INTO `unimall_order_sku` VALUES ('297', '2777', '1236771', '269', '1012019090938431065', '昵趣NaTruse 山羊奶配方狗狗洁齿骨 盒装20g*40支', '山羊奶', '150004', '1', null, '10690', '8900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/86338c9e576342baa0d079bc1caef9cc.jpg', '2019-09-07 09:38:44', '2019-09-07 09:38:44');
INSERT INTO `unimall_order_sku` VALUES ('298', '2781', '1236772', '270', '1012019091009031066', '路斯 手工坊系列 香薰牛肉丁 200g', '200g', '160001', '1', null, '1800', '1380', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/e48b4f8c1c824e1a9b9f349be0275648.jpg', '2019-09-07 10:09:04', '2019-09-07 10:09:04');
INSERT INTO `unimall_order_sku` VALUES ('299', '2773', '1236768', '271', '1012019091016511067', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-09-07 10:16:51', '2019-09-07 10:16:51');
INSERT INTO `unimall_order_sku` VALUES ('300', '2781', '1236772', '271', '1012019091016511067', '路斯 手工坊系列 香薰牛肉丁 200g', '200g', '160001', '88', null, '1800', '1380', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/e48b4f8c1c824e1a9b9f349be0275648.jpg', '2019-09-07 10:16:51', '2019-09-07 10:16:51');
INSERT INTO `unimall_order_sku` VALUES ('301', '2782', '1236773', '272', '1012019091226101068', '比利时原装进口 欧帝亿IMPERIAL PAW 鸡肉幼猫猫粮 7.5kg', '7.5KG', '170001', '1', null, '54900', '53200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/4f817407a019439da765bf0dc5e7b3b7.jpg', '2019-09-07 12:26:10', '2019-09-07 12:26:10');
INSERT INTO `unimall_order_sku` VALUES ('302', '2773', '1236768', '273', '1012019091258041069', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-09-07 12:58:04', '2019-09-07 12:58:04');
INSERT INTO `unimall_order_sku` VALUES ('303', '2783', '1236774', '274', '1012019091301591070', '百加世NOW FRESH 无谷成猫粮 4磅', '标准', '180001', '1', null, '33480', '27900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/8e168314e8f14558a89729ec4fb86f17.jpg', '2019-09-07 13:02:00', '2019-09-07 13:02:00');
INSERT INTO `unimall_order_sku` VALUES ('304', '2787', '1236777', '275', '1012019091723191071', 'unimall商业永久授权', '仅授权', '310001', '1', null, '38800', '18800', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2e6c6eeefaf94fac94fcc1157d2fa037.jpeg', '2019-09-07 17:23:19', '2019-09-07 17:23:19');
INSERT INTO `unimall_order_sku` VALUES ('305', '2787', '1236777', '276', '1012019091813031072', 'unimall商业永久授权', '仅授权', '310001', '1', null, '38800', '18800', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2e6c6eeefaf94fac94fcc1157d2fa037.jpeg', '2019-09-07 18:13:04', '2019-09-07 18:13:04');
INSERT INTO `unimall_order_sku` VALUES ('306', '2775', '1236769', '277', '1012019092135391073', '耐吉斯SOLUTION 比利时版 鸡肉+三文鱼配方奶糕粮 18磅/8.16kg', '8.16KG', '120001', '1', null, '57600', '46000', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/55c59f82af4e4aefb9f78ce2484b9f36.png', '2019-09-07 21:35:40', '2019-09-07 21:35:40');
INSERT INTO `unimall_order_sku` VALUES ('307', '2782', '1236773', '278', '1012019091102171074', '比利时原装进口 欧帝亿IMPERIAL PAW 鸡肉幼猫猫粮 7.5kg', '7.5KG', '170001', '1', null, '54900', '53200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/4f817407a019439da765bf0dc5e7b3b7.jpg', '2019-09-08 11:02:17', '2019-09-08 11:02:17');
INSERT INTO `unimall_order_sku` VALUES ('308', '2784', '1236775', '279', '1012019091439301075', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-09-08 14:39:30', '2019-09-08 14:39:30');
INSERT INTO `unimall_order_sku` VALUES ('309', '2785', '1236776', '280', '1012019090843531076', '乐优派 猫砂盆清理用具 4件套', '标准', '200001', '1', null, '100', '2', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/22014e1c20c441949b9e90ef63fa0f64.jpg', '2019-09-09 08:43:53', '2019-09-09 08:43:53');
INSERT INTO `unimall_order_sku` VALUES ('310', '2777', '1236771', '281', '1012019090845321077', '昵趣NaTruse 山羊奶配方狗狗洁齿骨 盒装20g*40支', '山羊奶', '150004', '1', null, '10690', '8900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/86338c9e576342baa0d079bc1caef9cc.jpg', '2019-09-09 08:45:33', '2019-09-09 08:45:33');
INSERT INTO `unimall_order_sku` VALUES ('311', '2782', '1236773', '282', '1012019090912271078', '比利时原装进口 欧帝亿IMPERIAL PAW 鸡肉幼猫猫粮 7.5kg', '7.5KG', '170001', '1', null, '54900', '53200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/4f817407a019439da765bf0dc5e7b3b7.jpg', '2019-09-09 09:12:27', '2019-09-09 09:12:27');
INSERT INTO `unimall_order_sku` VALUES ('312', '2784', '1236775', '283', '1012019090916471079', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-09-09 09:16:47', '2019-09-09 09:16:47');
INSERT INTO `unimall_order_sku` VALUES ('313', '2777', '1236771', '284', '1012019091844341080', '昵趣NaTruse 山羊奶配方狗狗洁齿骨 盒装20g*40支', '山羊奶', '150004', '1', null, '10690', '8900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/86338c9e576342baa0d079bc1caef9cc.jpg', '2019-09-09 18:44:34', '2019-09-09 18:44:34');
INSERT INTO `unimall_order_sku` VALUES ('314', '2784', '1236775', '285', '1012019092130251081', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-09-09 21:30:26', '2019-09-09 21:30:26');
INSERT INTO `unimall_order_sku` VALUES ('315', '2773', '1236768', '286', '1012019090843551082', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-09-10 08:43:56', '2019-09-10 08:43:56');
INSERT INTO `unimall_order_sku` VALUES ('316', '2787', '1236777', '287', '1012019090849451083', 'unimall商业永久授权', '仅授权', '310001', '1', null, '38800', '18800', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2e6c6eeefaf94fac94fcc1157d2fa037.jpeg', '2019-09-10 08:49:45', '2019-09-10 08:49:45');
INSERT INTO `unimall_order_sku` VALUES ('317', '2773', '1236768', '288', '1012019091023231084', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-09-10 10:23:23', '2019-09-10 10:23:23');
INSERT INTO `unimall_order_sku` VALUES ('318', '2787', '1236777', '289', '1012019091038331085', 'unimall商业永久授权', '仅授权', '310001', '1', null, '38800', '18800', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2e6c6eeefaf94fac94fcc1157d2fa037.jpeg', '2019-09-10 10:38:33', '2019-09-10 10:38:33');
INSERT INTO `unimall_order_sku` VALUES ('319', '2773', '1236768', '290', '1012019091041391086', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-09-10 10:41:40', '2019-09-10 10:41:40');
INSERT INTO `unimall_order_sku` VALUES ('320', '2777', '1236771', '291', '1012019091055101087', '昵趣NaTruse 山羊奶配方狗狗洁齿骨 盒装20g*40支', '山羊奶', '150004', '1', null, '10690', '8900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/86338c9e576342baa0d079bc1caef9cc.jpg', '2019-09-10 10:55:10', '2019-09-10 10:55:10');
INSERT INTO `unimall_order_sku` VALUES ('321', '2787', '1236777', '292', '1012019091640031088', 'unimall商业永久授权', '仅授权', '310001', '1', null, '38800', '18800', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2e6c6eeefaf94fac94fcc1157d2fa037.jpeg', '2019-09-10 16:40:03', '2019-09-10 16:40:03');
INSERT INTO `unimall_order_sku` VALUES ('322', '2786', '1236777', '293', '1012019091804391089', 'unimall商业永久授权', '帮安装', '310002', '1', null, '99900', '68800', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2e6c6eeefaf94fac94fcc1157d2fa037.jpeg', '2019-09-10 18:04:40', '2019-09-10 18:04:40');
INSERT INTO `unimall_order_sku` VALUES ('323', '2784', '1236775', '294', '1012019091007231090', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-09-11 10:07:23', '2019-09-11 10:07:23');
INSERT INTO `unimall_order_sku` VALUES ('324', '2782', '1236773', '295', '1012019091157011091', '比利时原装进口 欧帝亿IMPERIAL PAW 鸡肉幼猫猫粮 7.5kg', '7.5KG', '170001', '1', null, '54900', '53200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/4f817407a019439da765bf0dc5e7b3b7.jpg', '2019-09-11 11:57:01', '2019-09-11 11:57:01');
INSERT INTO `unimall_order_sku` VALUES ('325', '2775', '1236769', '296', '1012019091549331092', '耐吉斯SOLUTION 比利时版 鸡肉+三文鱼配方奶糕粮 18磅/8.16kg', '8.16KG', '120001', '1', null, '57600', '46000', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/55c59f82af4e4aefb9f78ce2484b9f36.png', '2019-09-11 15:49:34', '2019-09-11 15:49:34');
INSERT INTO `unimall_order_sku` VALUES ('326', '2784', '1236775', '297', '1012019091550061093', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-09-11 15:50:06', '2019-09-11 15:50:06');
INSERT INTO `unimall_order_sku` VALUES ('327', '2784', '1236775', '298', '1012019091634461094', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-09-11 16:34:47', '2019-09-11 16:34:47');
INSERT INTO `unimall_order_sku` VALUES ('328', '2777', '1236771', '299', '1012019091944511095', '昵趣NaTruse 山羊奶配方狗狗洁齿骨 盒装20g*40支', '山羊奶', '150004', '1', null, '10690', '8900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/86338c9e576342baa0d079bc1caef9cc.jpg', '2019-09-11 19:44:51', '2019-09-11 19:44:51');
INSERT INTO `unimall_order_sku` VALUES ('329', '2783', '1236774', '300', '1012019091018281096', '百加世NOW FRESH 无谷成猫粮 4磅', '标准', '180001', '1', null, '33480', '27900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/8e168314e8f14558a89729ec4fb86f17.jpg', '2019-09-12 10:18:29', '2019-09-12 10:18:29');
INSERT INTO `unimall_order_sku` VALUES ('330', '2786', '1236777', '301', '1012019091312151097', 'unimall商业永久授权', '帮安装', '310002', '1', null, '99900', '68800', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2e6c6eeefaf94fac94fcc1157d2fa037.jpeg', '2019-09-12 13:12:16', '2019-09-12 13:12:16');
INSERT INTO `unimall_order_sku` VALUES ('331', '2775', '1236769', '302', '1012019091346381098', '耐吉斯SOLUTION 比利时版 鸡肉+三文鱼配方奶糕粮 18磅/8.16kg', '8.16KG', '120001', '1', null, '57600', '46000', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/55c59f82af4e4aefb9f78ce2484b9f36.png', '2019-09-12 13:46:38', '2019-09-12 13:46:38');
INSERT INTO `unimall_order_sku` VALUES ('332', '2784', '1236775', '303', '1012019091428211099', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-09-12 14:28:22', '2019-09-12 14:28:22');
INSERT INTO `unimall_order_sku` VALUES ('333', '2784', '1236775', '304', '1012019092027321100', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-09-12 20:27:33', '2019-09-12 20:27:33');
INSERT INTO `unimall_order_sku` VALUES ('334', '2784', '1236775', '305', '1012019090946491101', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-09-13 09:46:49', '2019-09-13 09:46:49');
INSERT INTO `unimall_order_sku` VALUES ('335', '2783', '1236774', '306', '1012019091504371102', '百加世NOW FRESH 无谷成猫粮 4磅', '标准', '180001', '1', null, '33480', '27900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/8e168314e8f14558a89729ec4fb86f17.jpg', '2019-09-13 15:04:38', '2019-09-13 15:04:38');
INSERT INTO `unimall_order_sku` VALUES ('336', '2782', '1236773', '307', '1012019092102321103', '比利时原装进口 欧帝亿IMPERIAL PAW 鸡肉幼猫猫粮 7.5kg', '7.5KG', '170001', '1', null, '54900', '53200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/4f817407a019439da765bf0dc5e7b3b7.jpg', '2019-09-13 21:02:32', '2019-09-13 21:02:32');
INSERT INTO `unimall_order_sku` VALUES ('337', '2784', '1236775', '308', '1012019092300251104', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-09-13 23:00:26', '2019-09-13 23:00:26');
INSERT INTO `unimall_order_sku` VALUES ('338', '2786', '1236777', '309', '1012019090901401105', 'unimall商业永久授权', '帮安装', '310002', '1', null, '99900', '68800', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2e6c6eeefaf94fac94fcc1157d2fa037.jpeg', '2019-09-14 09:01:40', '2019-09-14 09:01:40');
INSERT INTO `unimall_order_sku` VALUES ('339', '2782', '1236773', '310', '1012019092012131106', '比利时原装进口 欧帝亿IMPERIAL PAW 鸡肉幼猫猫粮 7.5kg', '7.5KG', '170001', '1', null, '54900', '53200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/4f817407a019439da765bf0dc5e7b3b7.jpg', '2019-09-14 20:12:14', '2019-09-14 20:12:14');
INSERT INTO `unimall_order_sku` VALUES ('340', '2784', '1236775', '311', '1012019092013061107', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-09-14 20:13:06', '2019-09-14 20:13:06');
INSERT INTO `unimall_order_sku` VALUES ('341', '2784', '1236775', '312', '1012019092100371108', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-09-14 21:00:38', '2019-09-14 21:00:38');
INSERT INTO `unimall_order_sku` VALUES ('342', '2774', '1236768', '313', '1012019091046241109', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '1.5KG', '110001', '1', null, '8640', '7200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-09-15 10:46:25', '2019-09-15 10:46:25');
INSERT INTO `unimall_order_sku` VALUES ('343', '2783', '1236774', '313', '1012019091046241109', '百加世NOW FRESH 无谷成猫粮 4磅', '标准', '180001', '1', null, '33480', '27900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/8e168314e8f14558a89729ec4fb86f17.jpg', '2019-09-15 10:46:25', '2019-09-15 10:46:25');
INSERT INTO `unimall_order_sku` VALUES ('344', '2784', '1236775', '314', '1012019091116461110', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-09-15 11:16:47', '2019-09-15 11:16:47');
INSERT INTO `unimall_order_sku` VALUES ('345', '2781', '1236772', '315', '1012019091415011111', '路斯 手工坊系列 香薰牛肉丁 200g', '200g', '160001', '1', null, '1800', '1380', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/e48b4f8c1c824e1a9b9f349be0275648.jpg', '2019-09-15 14:15:01', '2019-09-15 14:15:01');
INSERT INTO `unimall_order_sku` VALUES ('346', '2784', '1236775', '316', '1012019092057011112', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-09-15 20:57:02', '2019-09-15 20:57:02');
INSERT INTO `unimall_order_sku` VALUES ('347', '2784', '1236775', '317', '1012019090818531113', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-09-16 08:18:54', '2019-09-16 08:18:54');
INSERT INTO `unimall_order_sku` VALUES ('348', '2784', '1236775', '318', '1012019091409291114', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-09-16 14:09:30', '2019-09-16 14:09:30');
INSERT INTO `unimall_order_sku` VALUES ('349', '2781', '1236772', '319', '1012019091541401115', '路斯 手工坊系列 香薰牛肉丁 200g', '200g', '160001', '1', null, '1800', '1380', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/e48b4f8c1c824e1a9b9f349be0275648.jpg', '2019-09-16 15:41:40', '2019-09-16 15:41:40');
INSERT INTO `unimall_order_sku` VALUES ('350', '2786', '1236777', '320', '1012019091727081116', 'unimall商业永久授权', '帮安装', '310002', '3', null, '99900', '68800', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2e6c6eeefaf94fac94fcc1157d2fa037.jpeg', '2019-09-16 17:27:09', '2019-09-16 17:27:09');
INSERT INTO `unimall_order_sku` VALUES ('351', '2773', '1236768', '321', '1012019092132261117', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-09-16 21:32:26', '2019-09-16 21:32:26');
INSERT INTO `unimall_order_sku` VALUES ('352', '2784', '1236775', '322', '1012019090736271118', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-09-17 07:36:27', '2019-09-17 07:36:27');
INSERT INTO `unimall_order_sku` VALUES ('353', '2781', '1236772', '323', '1012019091202141119', '路斯 手工坊系列 香薰牛肉丁 200g', '200g', '160001', '1', null, '1800', '1380', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/e48b4f8c1c824e1a9b9f349be0275648.jpg', '2019-09-17 12:02:15', '2019-09-17 12:02:15');
INSERT INTO `unimall_order_sku` VALUES ('354', '2786', '1236777', '324', '1012019091657461120', 'unimall商业永久授权', '帮安装', '310002', '1', null, '99900', '68800', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2e6c6eeefaf94fac94fcc1157d2fa037.jpeg', '2019-09-17 16:57:47', '2019-09-17 16:57:47');
INSERT INTO `unimall_order_sku` VALUES ('355', '2786', '1236777', '325', '1012019091701511121', 'unimall商业永久授权', '帮安装', '310002', '1', null, '99900', '68800', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2e6c6eeefaf94fac94fcc1157d2fa037.jpeg', '2019-09-17 17:01:52', '2019-09-17 17:01:52');
INSERT INTO `unimall_order_sku` VALUES ('356', '2782', '1236773', '326', '1012019091750501122', '比利时原装进口 欧帝亿IMPERIAL PAW 鸡肉幼猫猫粮 7.5kg', '7.5KG', '170001', '1', null, '54900', '53200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/4f817407a019439da765bf0dc5e7b3b7.jpg', '2019-09-17 17:50:51', '2019-09-17 17:50:51');
INSERT INTO `unimall_order_sku` VALUES ('357', '2773', '1236768', '327', '1012019091844541123', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-09-17 18:44:55', '2019-09-17 18:44:55');
INSERT INTO `unimall_order_sku` VALUES ('358', '2773', '1236768', '328', '1012019091845001124', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-09-17 18:45:01', '2019-09-17 18:45:01');
INSERT INTO `unimall_order_sku` VALUES ('359', '2784', '1236775', '329', '1012019092031101125', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-09-17 20:31:11', '2019-09-17 20:31:11');
INSERT INTO `unimall_order_sku` VALUES ('360', '2782', '1236773', '330', '1012019091112221126', '比利时原装进口 欧帝亿IMPERIAL PAW 鸡肉幼猫猫粮 7.5kg', '7.5KG', '170001', '1', null, '54900', '53200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/4f817407a019439da765bf0dc5e7b3b7.jpg', '2019-09-18 11:12:23', '2019-09-18 11:12:23');
INSERT INTO `unimall_order_sku` VALUES ('361', '2784', '1236775', '331', '1012019091220301127', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-09-18 12:20:31', '2019-09-18 12:20:31');
INSERT INTO `unimall_order_sku` VALUES ('362', '2781', '1236772', '332', '1012019091353581128', '路斯 手工坊系列 香薰牛肉丁 200g', '200g', '160001', '1', null, '1800', '1380', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/e48b4f8c1c824e1a9b9f349be0275648.jpg', '2019-09-18 13:53:59', '2019-09-18 13:53:59');
INSERT INTO `unimall_order_sku` VALUES ('363', '2776', '1236770', '333', '1012019091358131129', '宠儿香 家庭专用系列 美健膳食罐头 90g', '标准', '130001', '1', null, '1920', '1600', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2b86ba9cfc0c40fc82f7741c06270f36.jpg', '2019-09-18 13:58:13', '2019-09-18 13:58:13');
INSERT INTO `unimall_order_sku` VALUES ('364', '2775', '1236769', '334', '1012019091443261130', '耐吉斯SOLUTION 比利时版 鸡肉+三文鱼配方奶糕粮 18磅/8.16kg', '8.16KG', '120001', '1', null, '57600', '46000', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/55c59f82af4e4aefb9f78ce2484b9f36.png', '2019-09-18 14:43:27', '2019-09-18 14:43:27');
INSERT INTO `unimall_order_sku` VALUES ('365', '2782', '1236773', '335', '1012019091449401131', '比利时原装进口 欧帝亿IMPERIAL PAW 鸡肉幼猫猫粮 7.5kg', '7.5KG', '170001', '1', null, '54900', '53200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/4f817407a019439da765bf0dc5e7b3b7.jpg', '2019-09-18 14:49:41', '2019-09-18 14:49:41');
INSERT INTO `unimall_order_sku` VALUES ('366', '2782', '1236773', '336', '1012019091611061132', '比利时原装进口 欧帝亿IMPERIAL PAW 鸡肉幼猫猫粮 7.5kg', '7.5KG', '170001', '1', null, '54900', '53200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/4f817407a019439da765bf0dc5e7b3b7.jpg', '2019-09-18 16:11:07', '2019-09-18 16:11:07');
INSERT INTO `unimall_order_sku` VALUES ('367', '2773', '1236768', '337', '1012019091636181133', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '7.5KG', '110002', '1', null, '34680', '28900', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-09-18 16:36:19', '2019-09-18 16:36:19');
INSERT INTO `unimall_order_sku` VALUES ('368', '2774', '1236768', '338', '1012019092121201134', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '1.5KG', '110001', '1', null, '8640', '7200', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '2019-09-18 21:21:20', '2019-09-18 21:21:20');
INSERT INTO `unimall_order_sku` VALUES ('369', '2784', '1236775', '339', '3012019102019401001', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-10-31 20:19:41', '2019-10-31 20:19:41');
INSERT INTO `unimall_order_sku` VALUES ('370', '2784', '1236775', '340', '1012019102247451001', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-10-31 22:47:46', '2019-10-31 22:47:46');
INSERT INTO `unimall_order_sku` VALUES ('371', '2784', '1236775', '341', '1012019102252041001', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', null, '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-10-31 22:52:04', '2019-10-31 22:52:04');
INSERT INTO `unimall_order_sku` VALUES ('372', '2799', '1236790', '342', '1012019111551581001', '[自动发货]Java XXX入门到入土', '自动发货', '2333333', '1', null, '10', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/1351de40106e4c9fb6383fb78416aa6c.png', '2019-11-06 15:51:58', '2019-11-06 15:51:58');
INSERT INTO `unimall_order_sku` VALUES ('373', '2799', '1236790', '343', '1012019111555351002', '[自动发货]Java XXX入门到入土', '自动发货', '2333333', '1', null, '10', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/1351de40106e4c9fb6383fb78416aa6c.png', '2019-11-06 15:55:36', '2019-11-06 15:55:36');
INSERT INTO `unimall_order_sku` VALUES ('374', '2784', '1236775', '344', '1012019121115301001', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', '袋', '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-12-30 11:15:30', '2019-12-30 11:15:30');
INSERT INTO `unimall_order_sku` VALUES ('375', '2784', '1236775', '345', '1012019121117101002', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', '袋', '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-12-30 11:17:10', '2019-12-30 11:17:10');
INSERT INTO `unimall_order_sku` VALUES ('376', '2784', '1236775', '346', '1012019121120291001', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', '袋', '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-12-30 11:20:30', '2019-12-30 11:20:30');
INSERT INTO `unimall_order_sku` VALUES ('377', '2784', '1236775', '347', '1012019121122441001', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', '袋', '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-12-30 11:22:44', '2019-12-30 11:22:44');
INSERT INTO `unimall_order_sku` VALUES ('378', '2784', '1236775', '348', '1012019121124051001', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', '袋', '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-12-30 11:24:05', '2019-12-30 11:24:05');
INSERT INTO `unimall_order_sku` VALUES ('379', '2784', '1236775', '349', '1012019121132291002', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '标准', '190001', '1', '袋', '100', '1', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '2019-12-30 11:32:29', '2019-12-30 11:32:29');
INSERT INTO `unimall_order_sku` VALUES ('380', '2785', '1236776', '350', '1012020021514261001', '乐优派 猫砂盆清理用具 4件套', '标准', '200001', '20', '套', '100', '2', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/22014e1c20c441949b9e90ef63fa0f64.jpg', '2020-02-14 15:14:27', '2020-02-14 15:14:27');
INSERT INTO `unimall_order_sku` VALUES ('381', '2785', '1236776', '351', '1012020021906561001', '乐优派 猫砂盆清理用具 4件套', '标准', '200001', '1', '套', '100', '2', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/22014e1c20c441949b9e90ef63fa0f64.jpg', '2020-02-14 19:06:56', '2020-02-14 19:06:56');
INSERT INTO `unimall_order_sku` VALUES ('382', '2785', '1236776', '352', '1012020021908191002', '乐优派 猫砂盆清理用具 4件套', '标准', '200001', '1', '套', '100', '2', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/22014e1c20c441949b9e90ef63fa0f64.jpg', '2020-02-14 19:08:19', '2020-02-14 19:08:19');
INSERT INTO `unimall_order_sku` VALUES ('383', '2785', '1236776', '353', '1012020021918471001', '乐优派 猫砂盆清理用具 4件套', '标准', '200001', '1', '套', '100', '2', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/22014e1c20c441949b9e90ef63fa0f64.jpg', '2020-02-14 19:18:47', '2020-02-14 19:18:47');
INSERT INTO `unimall_order_sku` VALUES ('384', '2785', '1236776', '354', '1012020021919491002', '乐优派 猫砂盆清理用具 4件套', '标准', '200001', '1', '套', '100', '2', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/22014e1c20c441949b9e90ef63fa0f64.jpg', '2020-02-14 19:19:50', '2020-02-14 19:19:50');

-- ----------------------------
-- Table structure for unimall_recommend
-- ----------------------------
DROP TABLE IF EXISTS `unimall_recommend`;
CREATE TABLE `unimall_recommend` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `spu_id` bigint(20) NOT NULL,
  `recommend_type` int(11) NOT NULL DEFAULT '1',
  `gmt_create` datetime DEFAULT NULL,
  `gmt_update` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of unimall_recommend
-- ----------------------------
INSERT INTO `unimall_recommend` VALUES ('1', '25', '1', '2019-07-08 08:55:05', '2019-07-08 08:55:06');
INSERT INTO `unimall_recommend` VALUES ('2', '28', '1', '2019-07-08 22:41:34', '2019-07-08 22:41:34');
INSERT INTO `unimall_recommend` VALUES ('3', '29', '1', '2019-07-08 22:41:54', '2019-07-08 22:41:54');
INSERT INTO `unimall_recommend` VALUES ('4', '22', '1', '2019-07-08 22:42:03', '2019-07-08 22:42:03');
INSERT INTO `unimall_recommend` VALUES ('5', '23', '1', '2019-07-08 22:42:32', '2019-07-08 22:42:32');
INSERT INTO `unimall_recommend` VALUES ('7', '35', '1', '2019-07-08 23:21:43', '2019-07-08 23:21:43');
INSERT INTO `unimall_recommend` VALUES ('14', '1236777', '1', '2019-08-21 12:34:36', '2019-08-21 12:34:36');
INSERT INTO `unimall_recommend` VALUES ('15', '1236768', '1', '2019-08-21 12:34:41', '2019-08-21 12:34:41');
INSERT INTO `unimall_recommend` VALUES ('16', '1236773', '1', '2019-08-21 12:34:51', '2019-08-21 12:34:51');
INSERT INTO `unimall_recommend` VALUES ('17', '1236776', '1', '2019-08-21 12:34:57', '2019-08-21 12:34:57');

-- ----------------------------
-- Table structure for unimall_role
-- ----------------------------
DROP TABLE IF EXISTS `unimall_role`;
CREATE TABLE `unimall_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(63) DEFAULT NULL,
  `desc` varchar(1023) DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '0.冻结 1.激活',
  `gmt_update` datetime NOT NULL COMMENT '更新时间',
  `gmt_create` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- ----------------------------
-- Records of unimall_role
-- ----------------------------
INSERT INTO `unimall_role` VALUES ('1', '超级管理员', '所有模块的权限', '1', '2019-04-08 22:40:45', '2019-04-08 22:40:50');
INSERT INTO `unimall_role` VALUES ('12', '游客权限', '重要部分没有编辑权限', '1', '2019-08-11 12:32:16', '2019-08-11 12:32:16');

-- ----------------------------
-- Table structure for unimall_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `unimall_role_permission`;
CREATE TABLE `unimall_role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `permission` varchar(63) DEFAULT NULL,
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `gmt_update` datetime NOT NULL COMMENT '更新时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=504 DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- ----------------------------
-- Records of unimall_role_permission
-- ----------------------------
INSERT INTO `unimall_role_permission` VALUES ('1', '1', '*', '0', '2019-01-01 00:00:00', '2019-01-01 00:00:00');
INSERT INTO `unimall_role_permission` VALUES ('2', '2', 'admin:category:read', '0', '2019-01-07 15:18:53', '2019-01-07 15:18:53');
INSERT INTO `unimall_role_permission` VALUES ('3', '2', 'admin:category:update', '0', '2019-01-07 15:18:53', '2019-01-07 15:18:53');
INSERT INTO `unimall_role_permission` VALUES ('4', '2', 'admin:category:delete', '0', '2019-01-07 15:18:53', '2019-01-07 15:18:53');
INSERT INTO `unimall_role_permission` VALUES ('5', '2', 'admin:category:create', '0', '2019-01-07 15:18:53', '2019-01-07 15:18:53');
INSERT INTO `unimall_role_permission` VALUES ('6', '2', 'admin:category:list', '0', '2019-01-07 15:18:53', '2019-01-07 15:18:53');
INSERT INTO `unimall_role_permission` VALUES ('7', '2', 'admin:brand:create', '0', '2019-01-07 15:18:53', '2019-01-07 15:18:53');
INSERT INTO `unimall_role_permission` VALUES ('8', '2', 'admin:brand:list', '0', '2019-01-07 15:18:53', '2019-01-07 15:18:53');
INSERT INTO `unimall_role_permission` VALUES ('9', '2', 'admin:brand:delete', '0', '2019-01-07 15:18:53', '2019-01-07 15:18:53');
INSERT INTO `unimall_role_permission` VALUES ('10', '2', 'admin:brand:read', '0', '2019-01-07 15:18:53', '2019-01-07 15:18:53');
INSERT INTO `unimall_role_permission` VALUES ('11', '2', 'admin:brand:update', '0', '2019-01-07 15:18:53', '2019-01-07 15:18:53');
INSERT INTO `unimall_role_permission` VALUES ('12', '3', 'admin:nearby:ad:list', '0', '2019-01-07 15:18:57', '2019-01-07 15:18:57');
INSERT INTO `unimall_role_permission` VALUES ('13', '3', 'admin:nearby:ad:delete', '0', '2019-01-07 15:18:57', '2019-01-07 15:18:57');
INSERT INTO `unimall_role_permission` VALUES ('14', '3', 'admin:nearby:ad:create', '0', '2019-01-07 15:18:57', '2019-01-07 15:18:57');
INSERT INTO `unimall_role_permission` VALUES ('15', '3', 'admin:nearby:ad:update', '0', '2019-01-07 15:18:57', '2019-01-07 15:18:57');
INSERT INTO `unimall_role_permission` VALUES ('16', '3', 'admin:nearby:ad:read', '0', '2019-01-07 15:18:57', '2019-01-07 15:18:57');
INSERT INTO `unimall_role_permission` VALUES ('17', '3', 'admin:groupon:list', '0', '2019-01-07 15:18:57', '2019-01-07 15:18:57');
INSERT INTO `unimall_role_permission` VALUES ('18', '3', 'admin:groupon:update', '0', '2019-01-07 15:18:57', '2019-01-07 15:18:57');
INSERT INTO `unimall_role_permission` VALUES ('19', '3', 'admin:groupon:create', '0', '2019-01-07 15:18:57', '2019-01-07 15:18:57');
INSERT INTO `unimall_role_permission` VALUES ('20', '3', 'admin:groupon:read', '0', '2019-01-07 15:18:57', '2019-01-07 15:18:57');
INSERT INTO `unimall_role_permission` VALUES ('21', '3', 'admin:groupon:delete', '0', '2019-01-07 15:18:57', '2019-01-07 15:18:57');
INSERT INTO `unimall_role_permission` VALUES ('22', '3', 'admin:topic:create', '0', '2019-01-07 15:18:57', '2019-01-07 15:18:57');
INSERT INTO `unimall_role_permission` VALUES ('23', '3', 'admin:topic:read', '0', '2019-01-07 15:18:57', '2019-01-07 15:18:57');
INSERT INTO `unimall_role_permission` VALUES ('24', '3', 'admin:topic:list', '0', '2019-01-07 15:18:57', '2019-01-07 15:18:57');
INSERT INTO `unimall_role_permission` VALUES ('25', '3', 'admin:topic:delete', '0', '2019-01-07 15:18:57', '2019-01-07 15:18:57');
INSERT INTO `unimall_role_permission` VALUES ('26', '3', 'admin:topic:update', '0', '2019-01-07 15:18:57', '2019-01-07 15:18:57');
INSERT INTO `unimall_role_permission` VALUES ('27', '3', 'admin:coupon:list', '0', '2019-01-07 15:18:57', '2019-01-07 15:18:57');
INSERT INTO `unimall_role_permission` VALUES ('28', '3', 'admin:coupon:delete', '0', '2019-01-07 15:18:57', '2019-01-07 15:18:57');
INSERT INTO `unimall_role_permission` VALUES ('29', '3', 'admin:coupon:read', '0', '2019-01-07 15:18:57', '2019-01-07 15:18:57');
INSERT INTO `unimall_role_permission` VALUES ('30', '3', 'admin:coupon:create', '0', '2019-01-07 15:18:57', '2019-01-07 15:18:57');
INSERT INTO `unimall_role_permission` VALUES ('31', '3', 'admin:coupon:update', '0', '2019-01-07 15:18:57', '2019-01-07 15:18:57');
INSERT INTO `unimall_role_permission` VALUES ('64', '15', 'admin:user:list', '0', '2019-04-12 14:18:07', '2019-04-12 14:18:07');
INSERT INTO `unimall_role_permission` VALUES ('65', '15', 'admin:role:update', '0', '2019-04-12 14:18:07', '2019-04-12 14:18:07');
INSERT INTO `unimall_role_permission` VALUES ('66', '15', 'admin:role:delete', '0', '2019-04-12 14:18:07', '2019-04-12 14:18:07');
INSERT INTO `unimall_role_permission` VALUES ('67', '15', 'admin:role:create', '0', '2019-04-12 14:18:07', '2019-04-12 14:18:07');
INSERT INTO `unimall_role_permission` VALUES ('343', '11', 'admin:admin:update', '0', '2019-05-04 12:28:02', '2019-05-04 12:28:02');
INSERT INTO `unimall_role_permission` VALUES ('344', '11', 'admin:admin:list', '0', '2019-05-04 12:28:02', '2019-05-04 12:28:02');
INSERT INTO `unimall_role_permission` VALUES ('345', '11', 'admin:admin:delete', '0', '2019-05-04 12:28:02', '2019-05-04 12:28:02');
INSERT INTO `unimall_role_permission` VALUES ('346', '11', 'admin:admin:create', '0', '2019-05-04 12:28:02', '2019-05-04 12:28:02');
INSERT INTO `unimall_role_permission` VALUES ('347', '11', 'admin:permission:list', '0', '2019-05-04 12:28:02', '2019-05-04 12:28:02');
INSERT INTO `unimall_role_permission` VALUES ('348', '11', 'admin:role:list', '0', '2019-05-04 12:28:02', '2019-05-04 12:28:02');
INSERT INTO `unimall_role_permission` VALUES ('349', '11', 'admin:user:list', '0', '2019-05-04 12:28:02', '2019-05-04 12:28:02');
INSERT INTO `unimall_role_permission` VALUES ('350', '11', 'admin:user:status', '0', '2019-05-04 12:28:02', '2019-05-04 12:28:02');
INSERT INTO `unimall_role_permission` VALUES ('351', '11', 'admin:order:detail', '0', '2019-05-04 12:28:02', '2019-05-04 12:28:02');
INSERT INTO `unimall_role_permission` VALUES ('352', '11', 'admin:order:ship', '0', '2019-05-04 12:28:02', '2019-05-04 12:28:02');
INSERT INTO `unimall_role_permission` VALUES ('353', '11', 'admin:order:list', '0', '2019-05-04 12:28:02', '2019-05-04 12:28:02');
INSERT INTO `unimall_role_permission` VALUES ('354', '11', 'admin:apply:list', '0', '2019-05-04 12:28:02', '2019-05-04 12:28:02');
INSERT INTO `unimall_role_permission` VALUES ('355', '11', 'admin:apply:apply', '0', '2019-05-04 12:28:02', '2019-05-04 12:28:02');
INSERT INTO `unimall_role_permission` VALUES ('356', '11', 'admin:apply:handle', '0', '2019-05-04 12:28:02', '2019-05-04 12:28:02');
INSERT INTO `unimall_role_permission` VALUES ('357', '11', 'admin:ad:update', '0', '2019-05-04 12:28:02', '2019-05-04 12:28:02');
INSERT INTO `unimall_role_permission` VALUES ('358', '11', 'admin:ad:list', '0', '2019-05-04 12:28:02', '2019-05-04 12:28:02');
INSERT INTO `unimall_role_permission` VALUES ('359', '11', 'admin:ad:delete', '0', '2019-05-04 12:28:02', '2019-05-04 12:28:02');
INSERT INTO `unimall_role_permission` VALUES ('360', '11', 'admin:ad:create', '0', '2019-05-04 12:28:02', '2019-05-04 12:28:02');
INSERT INTO `unimall_role_permission` VALUES ('361', '11', 'admin:article:update', '0', '2019-05-04 12:28:03', '2019-05-04 12:28:03');
INSERT INTO `unimall_role_permission` VALUES ('362', '11', 'admin:article:list', '0', '2019-05-04 12:28:03', '2019-05-04 12:28:03');
INSERT INTO `unimall_role_permission` VALUES ('363', '11', 'admin:article:status', '0', '2019-05-04 12:28:03', '2019-05-04 12:28:03');
INSERT INTO `unimall_role_permission` VALUES ('364', '11', 'admin:article:delete', '0', '2019-05-04 12:28:03', '2019-05-04 12:28:03');
INSERT INTO `unimall_role_permission` VALUES ('365', '11', 'admin:article:create', '0', '2019-05-04 12:28:03', '2019-05-04 12:28:03');
INSERT INTO `unimall_role_permission` VALUES ('366', '11', 'admin:life:list', '0', '2019-05-04 12:28:03', '2019-05-04 12:28:03');
INSERT INTO `unimall_role_permission` VALUES ('367', '11', 'admin:life:delete', '0', '2019-05-04 12:28:03', '2019-05-04 12:28:03');
INSERT INTO `unimall_role_permission` VALUES ('368', '11', 'admin:qq:update', '0', '2019-05-04 12:28:03', '2019-05-04 12:28:03');
INSERT INTO `unimall_role_permission` VALUES ('369', '11', 'admin:qq:list', '0', '2019-05-04 12:28:03', '2019-05-04 12:28:03');
INSERT INTO `unimall_role_permission` VALUES ('370', '11', 'admin:qq:delete', '0', '2019-05-04 12:28:03', '2019-05-04 12:28:03');
INSERT INTO `unimall_role_permission` VALUES ('371', '11', 'admin:qq:create', '0', '2019-05-04 12:28:03', '2019-05-04 12:28:03');
INSERT INTO `unimall_role_permission` VALUES ('441', '10', 'operation:order:detail', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('442', '10', 'operation:order:refund', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('443', '10', 'operation:order:ship', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('444', '10', 'operation:order:list', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('445', '10', 'operation:appraise:delete', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('446', '10', 'operation:appraise:query', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('447', '10', 'operation:freight:create', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('448', '10', 'operation:freight:delete', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('449', '10', 'operation:freight:update', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('450', '10', 'operation:freight:query', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('451', '10', 'promote:coupon:update', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('452', '10', 'promote:coupon:create', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('453', '10', 'promote:coupon:delete', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('454', '10', 'promote:coupon:query', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('455', '10', 'promote:recommend:query', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('456', '10', 'promote:recommend:create', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('457', '10', 'promote:recommend:delete', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('458', '10', 'promote:merchant:create', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('459', '10', 'promote:merchant:update', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('460', '10', 'promote:merchant:query', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('461', '10', 'promote:advertisement:query', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('462', '10', 'promote:advertisement:create', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('463', '10', 'promote:advertisement:delete', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('464', '10', 'promote:advertisement:update', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('465', '10', 'admin:role:permissionList', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('466', '10', 'admin:permission:list', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('467', '10', 'admin:role:update', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('468', '10', 'admin:role:list', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('469', '10', 'admin:role:delete', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('470', '10', 'admin:role:create', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('471', '10', 'system:user:update', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('472', '10', 'system:user:query', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('473', '10', 'system:user:delete', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('474', '10', 'system:user:create', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('475', '10', 'admin:admin:update', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('476', '10', 'admin:admin:list', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('477', '10', 'admin:admin:delete', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('478', '10', 'admin:admin:create', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('479', '10', 'operation:category:create', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('480', '10', 'operation:category:delete', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('481', '10', 'operation:category:update', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('482', '10', 'operation:category:query', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('483', '10', 'operation:goods:detail', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('484', '10', 'operation:goods:edit', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('485', '10', 'operation:goods:list', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('486', '10', 'operation:goods:delete', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('487', '10', 'operation:goods:create', '0', '2019-08-11 12:40:28', '2019-08-11 12:40:28');
INSERT INTO `unimall_role_permission` VALUES ('488', '12', 'operation:order:detail', '0', '2019-08-21 11:48:25', '2019-08-21 11:48:25');
INSERT INTO `unimall_role_permission` VALUES ('489', '12', 'operation:order:list', '0', '2019-08-21 11:48:25', '2019-08-21 11:48:25');
INSERT INTO `unimall_role_permission` VALUES ('490', '12', 'operation:appraise:query', '0', '2019-08-21 11:48:25', '2019-08-21 11:48:25');
INSERT INTO `unimall_role_permission` VALUES ('491', '12', 'operation:freight:query', '0', '2019-08-21 11:48:25', '2019-08-21 11:48:25');
INSERT INTO `unimall_role_permission` VALUES ('492', '12', 'admin:admin:list', '0', '2019-08-21 11:48:25', '2019-08-21 11:48:25');
INSERT INTO `unimall_role_permission` VALUES ('493', '12', 'admin:permission:list', '0', '2019-08-21 11:48:25', '2019-08-21 11:48:25');
INSERT INTO `unimall_role_permission` VALUES ('494', '12', 'admin:role:list', '0', '2019-08-21 11:48:25', '2019-08-21 11:48:25');
INSERT INTO `unimall_role_permission` VALUES ('495', '12', 'system:user:query', '0', '2019-08-21 11:48:25', '2019-08-21 11:48:25');
INSERT INTO `unimall_role_permission` VALUES ('496', '12', 'system:user:create', '0', '2019-08-21 11:48:25', '2019-08-21 11:48:25');
INSERT INTO `unimall_role_permission` VALUES ('497', '12', 'promote:recommend:query', '0', '2019-08-21 11:48:25', '2019-08-21 11:48:25');
INSERT INTO `unimall_role_permission` VALUES ('498', '12', 'promote:advertisement:delete', '0', '2019-08-21 11:48:25', '2019-08-21 11:48:25');
INSERT INTO `unimall_role_permission` VALUES ('499', '12', 'promote:merchant:query', '0', '2019-08-21 11:48:25', '2019-08-21 11:48:25');
INSERT INTO `unimall_role_permission` VALUES ('500', '12', 'promote:coupon:create', '0', '2019-08-21 11:48:25', '2019-08-21 11:48:25');
INSERT INTO `unimall_role_permission` VALUES ('501', '12', 'operation:category:create', '0', '2019-08-21 11:48:25', '2019-08-21 11:48:25');
INSERT INTO `unimall_role_permission` VALUES ('502', '12', 'operation:goods:detail', '0', '2019-08-21 11:48:25', '2019-08-21 11:48:25');
INSERT INTO `unimall_role_permission` VALUES ('503', '12', 'operation:goods:list', '0', '2019-08-21 11:48:25', '2019-08-21 11:48:25');

-- ----------------------------
-- Table structure for unimall_sku
-- ----------------------------
DROP TABLE IF EXISTS `unimall_sku`;
CREATE TABLE `unimall_sku` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `spu_id` bigint(20) NOT NULL,
  `bar_code` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `img` varchar(255) DEFAULT NULL,
  `original_price` int(11) NOT NULL,
  `price` int(11) NOT NULL,
  `vip_price` int(11) NOT NULL,
  `stock` int(11) NOT NULL DEFAULT '9999999',
  `freeze_stock` int(11) NOT NULL DEFAULT '0',
  `gmt_update` datetime NOT NULL,
  `gmt_create` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2800 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of unimall_sku
-- ----------------------------
INSERT INTO `unimall_sku` VALUES ('2773', '1236768', '110002', '7.5KG', null, '34680', '28900', '28800', '745', '0', '2020-02-14 15:55:10', '2019-08-01 22:30:06');
INSERT INTO `unimall_sku` VALUES ('2774', '1236768', '110001', '1.5KG', null, '8640', '7200', '7180', '785', '0', '2020-02-14 15:55:10', '2019-08-01 22:30:06');
INSERT INTO `unimall_sku` VALUES ('2775', '1236769', '120001', '8.16KG', null, '57600', '46000', '44500', '783', '8', '2019-08-21 12:53:05', '2019-08-01 23:17:20');
INSERT INTO `unimall_sku` VALUES ('2776', '1236770', '130001', '标准', null, '1920', '1600', '1500', '845', '4', '2019-08-21 12:53:11', '2019-08-01 23:20:26');
INSERT INTO `unimall_sku` VALUES ('2777', '1236771', '150004', '山羊奶', null, '10690', '8900', '8800', '94', '6', '2019-08-21 12:53:17', '2019-08-01 23:38:07');
INSERT INTO `unimall_sku` VALUES ('2778', '1236771', '150003', '蔓越莓', null, '10690', '8900', '8800', '97', '1', '2019-08-21 12:53:17', '2019-08-01 23:38:07');
INSERT INTO `unimall_sku` VALUES ('2779', '1236771', '150002', '螺旋藻', null, '10690', '8900', '8800', '98', '1', '2019-08-21 12:53:17', '2019-08-01 23:38:07');
INSERT INTO `unimall_sku` VALUES ('2780', '1236771', '150001', '牛油果', null, '10690', '8900', '8800', '99', '1', '2019-08-21 12:53:17', '2019-08-01 23:38:07');
INSERT INTO `unimall_sku` VALUES ('2781', '1236772', '160001', '200g', null, '1800', '1380', '1280', '776', '101', '2019-08-21 12:53:23', '2019-08-01 23:42:45');
INSERT INTO `unimall_sku` VALUES ('2782', '1236773', '170001', '7.5KG', null, '54900', '53200', '52800', '858', '23', '2019-08-21 12:53:36', '2019-08-01 23:47:55');
INSERT INTO `unimall_sku` VALUES ('2784', '1236775', '190001', '标准', null, '100', '1', '1', '675', '50', '2019-08-23 18:03:42', '2019-08-03 23:26:26');
INSERT INTO `unimall_sku` VALUES ('2785', '1236776', '200001', '标准', null, '100', '2', '1', '881', '23', '2019-08-21 12:54:02', '2019-08-03 23:28:44');
INSERT INTO `unimall_sku` VALUES ('2786', '1236777', '310002', '帮安装', null, '99900', '68800', '68800', '878', '9', '2019-08-21 12:54:08', '2019-08-11 12:30:00');
INSERT INTO `unimall_sku` VALUES ('2787', '1236777', '310001', '仅授权', null, '38800', '18800', '18800', '871', '14', '2019-08-21 12:54:08', '2019-08-11 12:30:00');
INSERT INTO `unimall_sku` VALUES ('2788', '1236779', '4897097140018', '小猪佩奇草莓味豆奶125ml', null, '168', '168', '168', '100', '0', '2019-10-17 12:57:52', '2019-10-17 12:57:52');
INSERT INTO `unimall_sku` VALUES ('2789', '1236780', '6915766002943', '美年达汽水600ml', null, '168', '168', '168', '101', '0', '2019-10-17 12:57:52', '2019-10-17 12:57:52');
INSERT INTO `unimall_sku` VALUES ('2790', '1236781', '6915766003759', '美莲达西瓜味600ml', null, '100', '100', '100', '103', '0', '2019-10-17 12:57:52', '2019-10-17 12:57:52');
INSERT INTO `unimall_sku` VALUES ('2791', '1236782', '6935768923251', '口水娃兰花豆（酱汁牛肉味）', null, '100', '100', '100', '100', '0', '2020-02-14 16:37:42', '2019-10-17 12:57:52');
INSERT INTO `unimall_sku` VALUES ('2792', '1236783', '6936003512230', '旺旺牛奶糖', null, '100', '100', '100', '106', '0', '2019-10-17 12:57:52', '2019-10-17 12:57:52');
INSERT INTO `unimall_sku` VALUES ('2793', '1236784', '6900404519075', '天友核桃花生奶200ml', null, '100', '100', '100', '107', '0', '2019-10-17 12:57:52', '2019-10-17 12:57:52');
INSERT INTO `unimall_sku` VALUES ('2794', '1236785', '6953392543294', '纯悦水', null, '100', '100', '100', '108', '0', '2019-10-17 12:57:52', '2019-10-17 12:57:52');
INSERT INTO `unimall_sku` VALUES ('2795', '1236786', '6956416203730', '水动乐桃味600ml', null, '100', '100', '100', '109', '0', '2019-10-17 12:57:52', '2019-10-17 12:57:52');
INSERT INTO `unimall_sku` VALUES ('2796', '1236787', '6956416205956', '美汁源果粒橙450ml', null, '208', '208', '208', '110', '0', '2019-10-17 12:57:52', '2019-10-17 12:57:52');
INSERT INTO `unimall_sku` VALUES ('2797', '1236788', '6907992507064', '伊利营养舒化(全脂奶）250ml', null, '100', '100', '100', '111', '0', '2019-10-17 12:57:52', '2019-10-17 12:57:52');
INSERT INTO `unimall_sku` VALUES ('2798', '1236789', '6931893420009', '正宗咪咪虾条18g', null, '130', '130', '130', '112', '0', '2019-10-17 12:57:52', '2019-10-17 12:57:52');
INSERT INTO `unimall_sku` VALUES ('2799', '1236768', '1', '1', null, '2200', '200', '200', '1111', '0', '2020-02-14 15:55:10', '2020-02-14 15:55:10');

-- ----------------------------
-- Table structure for unimall_spu
-- ----------------------------
DROP TABLE IF EXISTS `unimall_spu`;
CREATE TABLE `unimall_spu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `original_price` int(11) NOT NULL,
  `price` int(11) NOT NULL,
  `vip_price` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `sales` int(11) NOT NULL DEFAULT '0',
  `img` varchar(255) NOT NULL,
  `detail` text NOT NULL,
  `description` varchar(255) NOT NULL DEFAULT '介绍',
  `category_id` bigint(20) NOT NULL,
  `freight_template_id` bigint(20) NOT NULL DEFAULT '1',
  `unit` varchar(255) NOT NULL,
  `status` int(11) NOT NULL,
  `gmt_update` datetime NOT NULL,
  `gmt_create` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1236790 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of unimall_spu
-- ----------------------------
INSERT INTO `unimall_spu` VALUES ('1236768', '8640', '7200', '7180', '澳大利亚丝倍亮Supercoat 小型犬幼年期全价犬粮 1.5kg', '0', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/eae3baf6b6114139840320f06f4398f7.jpg', '<p><img src=\"https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/bg/b6f1e945da014fe18af270c21a209ea1.jpg\" alt=\"\" width=\"750\" height=\"536\" /><img src=\"https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/bg/cc99ce62d1e341fcbe45b302e60060e7.jpg\" alt=\"\" width=\"750\" height=\"536\" /></p>\r\n<p><img src=\"https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/bg/bbf4972823224ad6a9c1b874abd323fd.jpg\" alt=\"\" width=\"750\" height=\"502\" /></p>\r\n<p><img src=\"https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/bg/681848a52d5d4fb9b614097b3a27b21f.jpg\" alt=\"\" width=\"750\" height=\"467\" /></p>\r\n<p><img src=\"https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/bg/51fab2d442f94111a4de16cf4c3d80e5.jpg\" alt=\"\" width=\"750\" height=\"487\" /></p>\r\n<p><img src=\"https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/bg/d96b32645c1548a5baf22b9419fd41ec.jpg\" alt=\"\" width=\"750\" height=\"509\" /></p>\r\n<p><img src=\"https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/bg/68d22543be1845b38cafc4da5580a44a.jpg\" alt=\"\" width=\"750\" height=\"497\" /></p>\r\n<p><img src=\"https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/bg/edffabc845b04d7b93b5cf321a7def32.jpg\" alt=\"\" width=\"750\" height=\"466\" /></p>\r\n<p><img src=\"https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/bg/c456bbc315544b6ba8c5c3cfbdb4ebc2.jpg\" alt=\"\" width=\"750\" height=\"515\" /></p>\r\n<p><img src=\"https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/bg/17d84019ac404a00b1d11ffe89e54c5d.jpg\" alt=\"\" width=\"750\" height=\"467\" /></p>', '进口', '1036520', '13', '袋', '0', '2020-02-14 15:55:10', '2019-08-01 22:30:06');
INSERT INTO `unimall_spu` VALUES ('1236769', '57600', '46000', '45200', '耐吉斯SOLUTION 比利时版 鸡肉+三文鱼配方奶糕粮 18磅/8.16kg', '0', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/55c59f82af4e4aefb9f78ce2484b9f36.png', '<p><img src=\"https://img2.epetbar.com/common/upload/commonfile/20190605171622738081.jpg@300y-4ic\" /><img src=\"https://img2.epetbar.com/common/upload/commonfile/20190605171622738081.jpg@300y-5ic\" /><img src=\"https://img2.epetbar.com/common/upload/commonfile/20190605171622738081.jpg@300y-6ic\" /><img src=\"https://img2.epetbar.com/common/upload/commonfile/20190605171622738081.jpg@300y-7ic\" /><img src=\"https://img2.epetbar.com/common/upload/commonfile/20190605171622738081.jpg@300y-8ic\" /><img src=\"https://img2.epetbar.com/common/upload/commonfile/20190605171622738081.jpg@300y-9ic\" /><img src=\"https://img2.epetbar.com/common/upload/commonfile/20190605171622738081.jpg@300y-10ic\" /><img src=\"https://img2.epetbar.com/common/upload/commonfile/20190605171622738081.jpg@300y-11ic\" /><img src=\"https://img2.epetbar.com/common/upload/commonfile/20190605171622738081.jpg@300y-12ic\" /><img src=\"https://img2.epetbar.com/common/upload/commonfile/20190605171622738081.jpg@300y-13ic\" /><img src=\"https://img2.epetbar.com/common/upload/commonfile/20190605171622738081.jpg@300y-14ic\" /></p>', '介绍', '1036520', '13', '盒', '1', '2019-12-03 23:15:24', '2019-08-01 23:17:20');
INSERT INTO `unimall_spu` VALUES ('1236770', '1920', '1600', '1500', '宠儿香 家庭专用系列 美健膳食罐头 90g', '0', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2b86ba9cfc0c40fc82f7741c06270f36.jpg', '<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2017-11/27/13/030e8ed7916c22c44d82fd5fa91c6b3a.jpg@300y-0ic\" alt=\"5199945\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2017-11/27/13/030e8ed7916c22c44d82fd5fa91c6b3a.jpg@300y-1ic\" alt=\"5199945\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2017-11/27/13/030e8ed7916c22c44d82fd5fa91c6b3a.jpg@300y-2ic\" alt=\"5199945\" /></div>\n<div>&nbsp;</div>\n<p>&nbsp;</p>\n<div>&nbsp;</div>', '处方狗粮', '1036521', '13', '盒', '1', '2019-12-03 23:06:14', '2019-08-01 23:20:26');
INSERT INTO `unimall_spu` VALUES ('1236771', '11000', '8900', '8800', '昵趣NaTruse 山羊奶配方狗狗洁齿骨 盒装20g*40支', '0', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/86338c9e576342baa0d079bc1caef9cc.jpg', '<div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-10/09/18/44a6a5d7df1beb9fe42b24ef25b2bca1.jpg@300y-0ic\" alt=\"5930132\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-10/09/18/44a6a5d7df1beb9fe42b24ef25b2bca1.jpg@300y-1ic\" alt=\"5930132\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-10/09/18/44a6a5d7df1beb9fe42b24ef25b2bca1.jpg@300y-2ic\" alt=\"5930132\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-10/09/18/44a6a5d7df1beb9fe42b24ef25b2bca1.jpg@300y-3ic\" alt=\"5930132\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-10/09/18/44a6a5d7df1beb9fe42b24ef25b2bca1.jpg@300y-4ic\" alt=\"5930132\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-10/09/18/44a6a5d7df1beb9fe42b24ef25b2bca1.jpg@300y-5ic\" alt=\"5930132\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-10/09/18/44a6a5d7df1beb9fe42b24ef25b2bca1.jpg@300y-6ic\" alt=\"5930132\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-10/09/18/44a6a5d7df1beb9fe42b24ef25b2bca1.jpg@300y-7ic\" alt=\"5930132\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-10/09/18/44a6a5d7df1beb9fe42b24ef25b2bca1.jpg@300y-8ic\" alt=\"5930132\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-10/09/18/44a6a5d7df1beb9fe42b24ef25b2bca1.jpg@300y-9ic\" alt=\"5930132\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-10/09/18/44a6a5d7df1beb9fe42b24ef25b2bca1.jpg@300y-10ic\" alt=\"5930132\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-10/09/18/44a6a5d7df1beb9fe42b24ef25b2bca1.jpg@300y-11ic\" alt=\"5930132\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-10/09/18/44a6a5d7df1beb9fe42b24ef25b2bca1.jpg@300y-12ic\" alt=\"5930132\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-10/09/18/44a6a5d7df1beb9fe42b24ef25b2bca1.jpg@300y-13ic\" alt=\"5930132\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-10/09/18/44a6a5d7df1beb9fe42b24ef25b2bca1.jpg@300y-14ic\" alt=\"5930132\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-10/09/18/44a6a5d7df1beb9fe42b24ef25b2bca1.jpg@300y-15ic\" alt=\"5930132\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-10/09/18/44a6a5d7df1beb9fe42b24ef25b2bca1.jpg@300y-16ic\" alt=\"5930132\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-10/09/18/44a6a5d7df1beb9fe42b24ef25b2bca1.jpg@300y-17ic\" alt=\"5930132\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-10/09/18/44a6a5d7df1beb9fe42b24ef25b2bca1.jpg@300y-18ic\" alt=\"5930132\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-10/09/18/44a6a5d7df1beb9fe42b24ef25b2bca1.jpg@300y-19ic\" alt=\"5930132\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-10/09/18/44a6a5d7df1beb9fe42b24ef25b2bca1.jpg@300y-20ic\" alt=\"5930132\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-10/09/18/44a6a5d7df1beb9fe42b24ef25b2bca1.jpg@300y-21ic\" alt=\"5930132\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-10/09/18/44a6a5d7df1beb9fe42b24ef25b2bca1.jpg@300y-22ic\" alt=\"5930132\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-10/09/18/44a6a5d7df1beb9fe42b24ef25b2bca1.jpg@300y-23ic\" alt=\"5930132\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-10/09/18/44a6a5d7df1beb9fe42b24ef25b2bca1.jpg@300y-24ic\" alt=\"5930132\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-10/09/18/44a6a5d7df1beb9fe42b24ef25b2bca1.jpg@300y-25ic\" alt=\"5930132\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-10/09/18/44a6a5d7df1beb9fe42b24ef25b2bca1.jpg@300y-26ic\" alt=\"5930132\" /></div>\n</div>', '磨牙磨牙', '1036525', '13', '盒', '1', '2019-08-21 12:53:17', '2019-08-01 23:38:07');
INSERT INTO `unimall_spu` VALUES ('1236772', '1800', '1380', '1280', '路斯 手工坊系列 香薰牛肉丁 200g', '0', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/e48b4f8c1c824e1a9b9f349be0275648.jpg', '<div class=\"imgdetail\">\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2015-10/14/12/72abe78f09547e4ca969cffc8f25b401_1.jpg@!water\" alt=\"2991184\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2015-10/14/12/72abe78f09547e4ca969cffc8f25b401_2.jpg@!water\" alt=\"2991185\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2015-10/14/12/72abe78f09547e4ca969cffc8f25b401_3.jpg@!water\" alt=\"2991186\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2015-10/14/12/72abe78f09547e4ca969cffc8f25b401_4.jpg@!water\" alt=\"2991187\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2015-10/14/12/72abe78f09547e4ca969cffc8f25b401_5.jpg@!water\" alt=\"2991188\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2015-10/14/12/72abe78f09547e4ca969cffc8f25b401_6.jpg@!water\" alt=\"2991189\" /></div>\n<div>\n<div><img style=\"opacity: 1;\" src=\"//img1.epetbar.com/2017-10/17/18/15c25e20ce5117e368c5f55b907a65e9.jpg@!water\" alt=\"5095517\" /></div>\n</div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2015-10/14/12/72abe78f09547e4ca969cffc8f25b401_8.jpg@!water\" alt=\"2991191\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2015-10/14/12/72abe78f09547e4ca969cffc8f25b401_9.jpg@!water\" alt=\"2991192\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2015-10/14/12/72abe78f09547e4ca969cffc8f25b401_10.jpg@!water\" alt=\"2991193\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2015-10/14/12/72abe78f09547e4ca969cffc8f25b401_11.jpg@!water\" alt=\"2991194\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2015-10/14/12/72abe78f09547e4ca969cffc8f25b401_12.jpg@!water\" alt=\"2991195\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2015-10/14/12/72abe78f09547e4ca969cffc8f25b401_13.jpg@!water\" alt=\"2991196\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2015-10/14/12/72abe78f09547e4ca969cffc8f25b401_14.jpg@!water\" alt=\"2991197\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2015-10/14/12/72abe78f09547e4ca969cffc8f25b401_15.jpg@!water\" alt=\"2991198\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2015-10/14/12/72abe78f09547e4ca969cffc8f25b401_16.jpg@!water\" alt=\"2991199\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2015-10/14/12/72abe78f09547e4ca969cffc8f25b401_17.jpg@!water\" alt=\"2991200\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2015-10/14/12/72abe78f09547e4ca969cffc8f25b401_18.jpg@!water\" alt=\"2991201\" /></div>\n<div>\n<div><img style=\"opacity: 1;\" src=\"//img1.epetbar.com/2017-10/17/18/7f18f086302a6a86df1bedf6ef076ab9.jpg@!water\" alt=\"5095525\" /></div>\n</div>\n<div>&nbsp;</div>\n</div>', '根本停不下来', '1036524', '13', '袋', '1', '2019-08-21 12:53:23', '2019-08-01 23:42:45');
INSERT INTO `unimall_spu` VALUES ('1236773', '54900', '53200', '52800', '比利时原装进口 欧帝亿IMPERIAL PAW 鸡肉幼猫猫粮 7.5kg', '0', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/4f817407a019439da765bf0dc5e7b3b7.jpg', '<div class=\"imgdetail\">\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-12/12/09/6f43b0de50d2fcfc2d079d09e4d49b53.jpg@300y-0ic\" alt=\"6108398\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-12/12/09/6f43b0de50d2fcfc2d079d09e4d49b53.jpg@300y-1ic\" alt=\"6108398\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-12/12/09/6f43b0de50d2fcfc2d079d09e4d49b53.jpg@300y-2ic\" alt=\"6108398\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-12/12/09/6f43b0de50d2fcfc2d079d09e4d49b53.jpg@300y-3ic\" alt=\"6108398\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-12/12/09/6f43b0de50d2fcfc2d079d09e4d49b53.jpg@300y-4ic\" alt=\"6108398\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-12/12/09/6f43b0de50d2fcfc2d079d09e4d49b53.jpg@300y-5ic\" alt=\"6108398\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-12/12/09/6f43b0de50d2fcfc2d079d09e4d49b53.jpg@300y-6ic\" alt=\"6108398\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-12/12/09/6f43b0de50d2fcfc2d079d09e4d49b53.jpg@300y-7ic\" alt=\"6108398\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-12/12/09/6f43b0de50d2fcfc2d079d09e4d49b53.jpg@300y-8ic\" alt=\"6108398\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-12/12/09/6f43b0de50d2fcfc2d079d09e4d49b53.jpg@300y-9ic\" alt=\"6108398\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-12/12/09/6f43b0de50d2fcfc2d079d09e4d49b53.jpg@300y-10ic\" alt=\"6108398\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-12/12/09/6f43b0de50d2fcfc2d079d09e4d49b53.jpg@300y-11ic\" alt=\"6108398\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-12/12/09/6f43b0de50d2fcfc2d079d09e4d49b53.jpg@300y-12ic\" alt=\"6108398\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-12/12/09/6f43b0de50d2fcfc2d079d09e4d49b53.jpg@300y-13ic\" alt=\"6108398\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-12/12/09/6f43b0de50d2fcfc2d079d09e4d49b53.jpg@300y-14ic\" alt=\"6108398\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-12/12/09/6f43b0de50d2fcfc2d079d09e4d49b53.jpg@300y-15ic\" alt=\"6108398\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-12/12/09/6f43b0de50d2fcfc2d079d09e4d49b53.jpg@300y-16ic\" alt=\"6108398\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-12/12/09/6f43b0de50d2fcfc2d079d09e4d49b53.jpg@300y-17ic\" alt=\"6108398\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-12/12/09/6f43b0de50d2fcfc2d079d09e4d49b53.jpg@300y-18ic\" alt=\"6108398\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-12/12/09/6f43b0de50d2fcfc2d079d09e4d49b53.jpg@300y-19ic\" alt=\"6108398\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-12/12/09/6f43b0de50d2fcfc2d079d09e4d49b53.jpg@300y-20ic\" alt=\"6108398\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-12/12/09/6f43b0de50d2fcfc2d079d09e4d49b53.jpg@300y-21ic\" alt=\"6108398\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-12/12/09/6f43b0de50d2fcfc2d079d09e4d49b53.jpg@300y-22ic\" alt=\"6108398\" /></div>\n<div>&nbsp;</div>\n</div>', '豆豆豆', '1036527', '13', '盒', '1', '2019-08-21 12:53:36', '2019-08-01 23:47:55');
INSERT INTO `unimall_spu` VALUES ('1236775', '100', '1', '1', '俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg', '59', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg', '<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-04/24/11/ff2cde4febbaf4e3035804b41e6a0a0b.jpg@300y-0ic\" alt=\"5511368\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-04/24/11/ff2cde4febbaf4e3035804b41e6a0a0b.jpg@300y-1ic\" alt=\"5511368\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-04/24/11/ff2cde4febbaf4e3035804b41e6a0a0b.jpg@300y-2ic\" alt=\"5511368\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-04/24/11/ff2cde4febbaf4e3035804b41e6a0a0b.jpg@300y-3ic\" alt=\"5511368\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-04/24/11/ff2cde4febbaf4e3035804b41e6a0a0b.jpg@300y-4ic\" alt=\"5511368\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-04/24/11/ff2cde4febbaf4e3035804b41e6a0a0b.jpg@300y-5ic\" alt=\"5511368\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-04/24/11/ff2cde4febbaf4e3035804b41e6a0a0b.jpg@300y-6ic\" alt=\"5511368\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-04/24/11/ff2cde4febbaf4e3035804b41e6a0a0b.jpg@300y-7ic\" alt=\"5511368\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-04/24/11/ff2cde4febbaf4e3035804b41e6a0a0b.jpg@300y-8ic\" alt=\"5511368\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-04/24/11/ff2cde4febbaf4e3035804b41e6a0a0b.jpg@300y-9ic\" alt=\"5511368\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-04/24/11/ff2cde4febbaf4e3035804b41e6a0a0b.jpg@300y-10ic\" alt=\"5511368\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-04/24/11/ff2cde4febbaf4e3035804b41e6a0a0b.jpg@300y-11ic\" alt=\"5511368\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-04/24/11/ff2cde4febbaf4e3035804b41e6a0a0b.jpg@300y-12ic\" alt=\"5511368\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-04/24/11/ff2cde4febbaf4e3035804b41e6a0a0b.jpg@300y-13ic\" alt=\"5511368\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-04/24/11/ff2cde4febbaf4e3035804b41e6a0a0b.jpg@300y-14ic\" alt=\"5511368\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-04/24/11/ff2cde4febbaf4e3035804b41e6a0a0b.jpg@300y-15ic\" alt=\"5511368\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-04/24/11/ff2cde4febbaf4e3035804b41e6a0a0b.jpg@300y-16ic\" alt=\"5511368\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-04/24/11/ff2cde4febbaf4e3035804b41e6a0a0b.jpg@300y-17ic\" alt=\"5511368\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-04/24/11/ff2cde4febbaf4e3035804b41e6a0a0b.jpg@300y-18ic\" alt=\"5511368\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-04/24/11/ff2cde4febbaf4e3035804b41e6a0a0b.jpg@300y-19ic\" alt=\"5511368\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-04/24/11/ff2cde4febbaf4e3035804b41e6a0a0b.jpg@300y-20ic\" alt=\"5511368\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-04/24/11/ff2cde4febbaf4e3035804b41e6a0a0b.jpg@300y-21ic\" alt=\"5511368\" /></div>\n<div><img style=\"opacity: 1;\" src=\"https://img2.epetbar.com/2018-04/24/11/ff2cde4febbaf4e3035804b41e6a0a0b.jpg@300y-22ic\" alt=\"5511368\" /></div>\n<div>&nbsp;</div>\n<p>&nbsp;</p>', '介绍', '1036533', '13', '袋', '1', '2019-08-23 18:03:42', '2019-08-03 23:26:26');
INSERT INTO `unimall_spu` VALUES ('1236776', '100', '2', '1', '乐优派 猫砂盆清理用具 4件套', '23', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/22014e1c20c441949b9e90ef63fa0f64.jpg', '<div class=\"imgdetail\">\n<div><img style=\"opacity: 1;\" src=\"//img1.epetbar.com/2017-12/08/18/6b8b58041940cd7af2bb4d981cf9fa83.jpg@!water\" alt=\"5229927\" /></div>\n<div><img style=\"opacity: 1;\" src=\"//img1.epetbar.com/2017-12/08/18/384a0d38845eb37ab6531e830f9ae8c2.jpg@!water\" alt=\"5229928\" /></div>\n<div><img style=\"opacity: 1;\" src=\"//img1.epetbar.com/2017-12/08/18/d25c95da3b8ca05c91459c1da06eb677.jpg@!water\" alt=\"5229929\" /></div>\n<div><img style=\"opacity: 1;\" src=\"//img1.epetbar.com/2017-12/08/18/16ef42b306e40cd4ab9ac29ac6d8b76f.jpg@!water\" alt=\"5229930\" /></div>\n<div>&nbsp;</div>\n</div>', '介绍', '1036532', '13', '套', '1', '2019-08-21 12:54:02', '2019-08-03 23:28:44');
INSERT INTO `unimall_spu` VALUES ('1236777', '38800', '18800', '18800', 'unimall商业永久授权', '2', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/2e6c6eeefaf94fac94fcc1157d2fa037.jpeg', '<p>购买后，请备注好，被授权公司，电话地址。</p>\n<p><img src=\"https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/324525a7819944bab622e2f9cca8cc05.png\" alt=\"\" width=\"586\" height=\"867\" /></p>', '商业使用授权', '1036536', '13', '次', '1', '2019-08-21 12:54:08', '2019-08-11 12:30:00');
INSERT INTO `unimall_spu` VALUES ('1236779', '168', '168', '168', '小猪佩奇草莓味豆奶125ml', '0', 'https://easycampus-asset.oss-cn-shenzhen.aliyuncs.com/nopic.png', '小猪佩奇草莓味豆奶125ml', '介绍', '1036524', '13', '件', '1', '2019-12-03 23:04:05', '2019-10-17 12:57:52');
INSERT INTO `unimall_spu` VALUES ('1236780', '168', '168', '168', '美年达汽水600ml', '0', 'https://easycampus-asset.oss-cn-shenzhen.aliyuncs.com/nopic.png', '美年达汽水600ml', '介绍', '1036524', '13', '件', '1', '2019-10-17 12:57:52', '2019-10-17 12:57:52');
INSERT INTO `unimall_spu` VALUES ('1236781', '100', '100', '100', '美莲达西瓜味600ml', '0', 'https://easycampus-asset.oss-cn-shenzhen.aliyuncs.com/nopic.png', '美莲达西瓜味600ml', '介绍', '1036524', '13', '件', '1', '2019-10-17 12:57:52', '2019-10-17 12:57:52');
INSERT INTO `unimall_spu` VALUES ('1236782', '100', '100', '100', '口水娃兰花豆（酱汁牛肉味）', '0', 'https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/34a3a981bcb84f35aec8ceb7ef9a6f6f.png', '口水娃兰花豆（酱汁牛肉味）', '介绍', '1036524', '13', '袋', '1', '2020-02-14 16:37:42', '2019-10-17 12:57:52');
INSERT INTO `unimall_spu` VALUES ('1236783', '100', '100', '100', '旺旺牛奶糖', '0', 'https://easycampus-asset.oss-cn-shenzhen.aliyuncs.com/nopic.png', '旺旺牛奶糖', '介绍', '1036524', '13', '袋', '1', '2019-10-17 12:57:52', '2019-10-17 12:57:52');
INSERT INTO `unimall_spu` VALUES ('1236784', '100', '100', '100', '天友核桃花生奶200ml', '0', 'https://easycampus-asset.oss-cn-shenzhen.aliyuncs.com/nopic.png', '天友核桃花生奶200ml', '介绍', '1036524', '13', '件', '1', '2019-10-17 12:57:52', '2019-10-17 12:57:52');
INSERT INTO `unimall_spu` VALUES ('1236785', '100', '100', '100', '纯悦水', '0', 'https://easycampus-asset.oss-cn-shenzhen.aliyuncs.com/nopic.png', '纯悦水', '介绍', '1036524', '13', '件', '1', '2019-10-17 12:57:52', '2019-10-17 12:57:52');
INSERT INTO `unimall_spu` VALUES ('1236786', '100', '100', '100', '水动乐桃味600ml', '0', 'https://easycampus-asset.oss-cn-shenzhen.aliyuncs.com/nopic.png', '水动乐桃味600ml', '介绍', '1036524', '13', '件', '1', '2019-10-17 12:57:52', '2019-10-17 12:57:52');
INSERT INTO `unimall_spu` VALUES ('1236787', '208', '208', '208', '美汁源果粒橙450ml', '0', 'https://easycampus-asset.oss-cn-shenzhen.aliyuncs.com/nopic.png', '美汁源果粒橙450ml', '介绍', '1036524', '13', '瓶', '1', '2019-10-17 12:57:52', '2019-10-17 12:57:52');
INSERT INTO `unimall_spu` VALUES ('1236788', '100', '100', '100', '伊利营养舒化(全脂奶）250ml', '0', 'https://easycampus-asset.oss-cn-shenzhen.aliyuncs.com/nopic.png', '伊利营养舒化(全脂奶）250ml', '介绍', '1036524', '13', '件', '1', '2019-10-17 12:57:52', '2019-10-17 12:57:52');
INSERT INTO `unimall_spu` VALUES ('1236789', '130', '130', '130', '正宗咪咪虾条18g', '0', 'https://easycampus-asset.oss-cn-shenzhen.aliyuncs.com/nopic.png', '正宗咪咪虾条18g', '介绍', '1036524', '13', '件', '1', '2019-10-17 12:57:52', '2019-10-17 12:57:52');

-- ----------------------------
-- Table structure for unimall_spu_attribute
-- ----------------------------
DROP TABLE IF EXISTS `unimall_spu_attribute`;
CREATE TABLE `unimall_spu_attribute` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `spu_id` bigint(20) NOT NULL,
  `attribute` varchar(255) NOT NULL,
  `value` varchar(255) NOT NULL,
  `gmt_update` datetime NOT NULL,
  `gmt_create` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of unimall_spu_attribute
-- ----------------------------
INSERT INTO `unimall_spu_attribute` VALUES ('24', '1236769', '生产产地', '比利时', '2019-08-21 12:53:05', '2019-08-21 12:53:05');
INSERT INTO `unimall_spu_attribute` VALUES ('25', '1236770', '成份含量', '蛋白质4%,脂肪7%', '2019-08-21 12:53:11', '2019-08-21 12:53:11');
INSERT INTO `unimall_spu_attribute` VALUES ('26', '1236770', '主要成份', '牛肉,鸡肉及其副产品,燕麦全粉', '2019-08-21 12:53:11', '2019-08-21 12:53:11');
INSERT INTO `unimall_spu_attribute` VALUES ('27', '1236771', '生产产地', '中国', '2019-08-21 12:53:17', '2019-08-21 12:53:17');
INSERT INTO `unimall_spu_attribute` VALUES ('28', '1236772', '生产产地', '中国', '2019-08-21 12:53:23', '2019-08-21 12:53:23');
INSERT INTO `unimall_spu_attribute` VALUES ('29', '1236773', '生产产地', '比利时', '2019-08-21 12:53:36', '2019-08-21 12:53:36');
INSERT INTO `unimall_spu_attribute` VALUES ('31', '1236776', '产地', '中国', '2019-08-21 12:54:02', '2019-08-21 12:54:02');
INSERT INTO `unimall_spu_attribute` VALUES ('33', '1236775', '产地', '中国', '2019-08-23 18:03:42', '2019-08-23 18:03:42');

-- ----------------------------
-- Table structure for unimall_user
-- ----------------------------
DROP TABLE IF EXISTS `unimall_user`;
CREATE TABLE `unimall_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `phone` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `login_type` int(11) NOT NULL,
  `open_id` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `avatar_url` varchar(255) DEFAULT NULL,
  `level` int(11) NOT NULL DEFAULT '0' COMMENT '0.普通会员 1.VIP会员',
  `birthday` date DEFAULT NULL,
  `gender` int(11) DEFAULT '-1',
  `gmt_last_login` datetime DEFAULT NULL,
  `last_login_ip` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT '1',
  `gmt_update` datetime NOT NULL,
  `gmt_create` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unimall_user_phone_uindex` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=453 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of unimall_user
-- ----------------------------
INSERT INTO `unimall_user` VALUES ('21', null, null, '1', 'oiAuI5CW0p5YDegIPZEdklaSi4cM', 'rize魏朝正', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJNITTq63LcicD1plOldChUqVDmHqZ6bmHwrOa8bUiaaWob9L6via6xZswBuRv1WNdSq864uywClSXxA/132', '0', null, '1', '2020-02-15 19:58:53', '27.10.60.71', '1', '2020-02-15 19:58:53', '2019-08-01 23:09:21');
INSERT INTO `unimall_user` VALUES ('22', null, null, '1', 'oiAuI5F5l2b5mW70kSPSHgFK2-tY', 'emmmmmm', 'https://wx.qlogo.cn/mmopen/vi_32/kPibxI0A35FLnOichycdMN3rtaakrKuOrwrIAibFciaYEL1BRC8jD9yS5u4V1lgLoDQibLQVlE46JxK2u9HpFB59WRg/132', '0', null, '1', '2019-08-23 18:02:32', '27.10.60.71', '1', '2019-08-23 18:02:33', '2019-08-02 00:19:25');
INSERT INTO `unimall_user` VALUES ('23', null, null, '2', 'osTQe6JxcJjGf0YYYLe0MZnORyig', '格子君', 'http://thirdwx.qlogo.cn/mmopen/vi_32/CaicdKfndSvz7GDyhJwF8HWZcbfeMXZY5dm7lL2ew4iak8q2Q59FlJh7MeSPwvzS9ox0ia0EBHETXfXibUzvK4Qic2w/132', '0', null, '0', '2019-08-08 19:03:17', '27.10.60.71', '1', '2019-08-08 19:03:18', '2019-08-08 19:03:17');
INSERT INTO `unimall_user` VALUES ('24', null, null, '1', 'oiAuI5KmbnA0Qq6kiMY4q8Nd77eU', null, 'https://wx.qlogo.cn/mmopen/vi_32/icvFO1UdPCSkXAzEB3K5B2XXDaHc5AhfFkicdw9FOClySialCaRZD6UuHzB5eGYTSL0aI6vGiaepps9ibBbfGbmfYbg/132', '0', null, '1', '2019-08-09 17:38:29', '27.10.60.71', '1', '2019-08-09 17:38:29', '2019-08-09 17:38:29');
INSERT INTO `unimall_user` VALUES ('25', null, null, '1', 'oiAuI5FbL5LxOc9AvX6a-SHYyeyU', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLsThiaOiaRYUOVbhvFIykvH9Rib8mHkcNWm2u1yebUbV07X8XuqftnKpPL3T65SG3zntSxUuhI1Ds3g/132', '0', null, '1', '2019-08-09 17:40:03', '27.10.60.71', '1', '2019-08-09 17:40:03', '2019-08-09 17:40:03');
INSERT INTO `unimall_user` VALUES ('26', null, null, '1', 'oiAuI5J26PBTIy5aAZqI_7vmm18I', null, 'https://wx.qlogo.cn/mmopen/vi_32/8CcbUpHBBAFkt2qq9xiawETD1T9wyQznwr90tajICpbON4ZXibbnic2SSnEomHbVA3jdvXYYfic8V1JJ71qOFMWYMg/132', '0', null, '1', '2019-08-09 17:45:09', '27.10.60.71', '1', '2019-08-09 17:45:10', '2019-08-09 17:45:09');
INSERT INTO `unimall_user` VALUES ('27', null, null, '1', 'oiAuI5GKKGplsf-5skEiY58GktPo', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKW62ibaCmg5oB8YC8zorDbQniac5V4uOTHaBkddEq16LXhWtibVl4yzpZJzON23ibOS9Mz1WhZlFDibuw/132', '0', null, '1', '2019-08-09 17:45:42', '27.10.60.71', '1', '2019-08-09 17:45:42', '2019-08-09 17:45:42');
INSERT INTO `unimall_user` VALUES ('28', null, null, '1', 'oiAuI5H_IdTtQ7tGadgsCucMO7Tw', null, 'https://wx.qlogo.cn/mmopen/vi_32/LP5C9RHNibxic2CBQKteaz0dRGpPHxUnlzegUNssv7SFhByNuZFbhaZMQV4j4srUxogAmUttdfx6WcUFP6pyxNDg/132', '0', null, '1', '2019-08-09 17:54:29', '27.10.60.71', '1', '2019-08-09 17:54:29', '2019-08-09 17:54:29');
INSERT INTO `unimall_user` VALUES ('29', null, null, '1', 'oiAuI5CHdx3ArS0J-HI3CVLu1nlU', null, 'https://wx.qlogo.cn/mmopen/vi_32/yzJ6MDTEayuoKibzHpOuUAI79jaickuM2HNzwmRp1upVAOpPy3Jo1rztZYiaTZOVH5fKjZtDlUp7CJWh8MhNZSWEw/132', '0', null, '1', '2019-08-09 18:01:31', '27.10.60.71', '1', '2019-08-09 18:01:31', '2019-08-09 18:01:31');
INSERT INTO `unimall_user` VALUES ('30', null, null, '2', 'osTQe6Oz-cFcFZpvwKxcTyYla_aQ', 'ATerryang', 'http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJJscBficiaUXI9IlMwRN1hHmpDtcpmicZkiboEKibTRBnNqCP1pyC3L4icrNhliaN1h0UldI83eHmfBF3Xg/132', '0', null, '1', '2019-08-09 18:25:57', '27.10.60.71', '1', '2019-08-09 18:25:57', '2019-08-09 18:25:57');
INSERT INTO `unimall_user` VALUES ('31', null, null, '1', 'oiAuI5PBu4Xj-PMY0KK_JFLQ_zcA', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLg2vgKPQCKep4yN3ibbXzUplLdXkHjQMOPMml8HYMn6R6b7EEyfZcXySxrRX4MJa90CgyzzEZJYVg/132', '0', null, '1', '2019-08-09 18:28:14', '27.10.60.71', '1', '2019-08-09 18:28:14', '2019-08-09 18:28:14');
INSERT INTO `unimall_user` VALUES ('32', null, null, '1', 'oiAuI5NqpDfeEAxr1TLVSmN3GULU', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTK6o4EMl211ibXibzTSqn3XtcsOzgu5vozO7O1jGf3TIV2BPljfEqkeI9Y8dp4rsKTppWic2PDZb3m3g/132', '0', null, '1', '2019-09-12 14:35:51', '27.10.60.71', '1', '2019-09-12 14:35:52', '2019-08-09 18:46:17');
INSERT INTO `unimall_user` VALUES ('33', null, null, '1', 'oiAuI5BZ9xHBgkMfOa0YcWaOiV9Y', null, null, '0', null, '-1', '2019-08-09 18:56:54', '27.10.60.71', '1', '2019-08-09 18:56:54', '2019-08-09 18:56:54');
INSERT INTO `unimall_user` VALUES ('34', null, null, '1', 'oiAuI5LrGutE0YfDdmljKMpqrsrs', null, null, '0', null, '-1', '2019-08-09 19:00:54', '27.10.60.71', '1', '2019-08-09 19:00:54', '2019-08-09 19:00:54');
INSERT INTO `unimall_user` VALUES ('35', null, null, '1', 'oiAuI5GKdYzSJynOQQOy51xCuZxQ', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJDN5UfOncUWDJKqD8bGYDMXp8ibOXqvicpibmy7uTtGcxRs0CNZeYROurvdPjOGxPiatYkPRn5GD4YsA/132', '0', null, '1', '2019-08-09 19:23:03', '27.10.60.71', '1', '2019-08-09 19:23:04', '2019-08-09 19:23:03');
INSERT INTO `unimall_user` VALUES ('36', null, null, '1', 'oiAuI5GdGD2vAR3tyZZ5DXqlj_cE', null, 'https://wx.qlogo.cn/mmopen/vi_32/hibovdzTUthWSNA0W0v7iaCdgLiathkYlLiasHoFWCkDRGqLI3PAiabF6ibSBxj03Z7KXUZtOSZwA0CZDtvTicpL5fS3Q/132', '0', null, '1', '2019-08-09 19:23:09', '27.10.60.71', '1', '2019-08-09 19:23:09', '2019-08-09 19:23:09');
INSERT INTO `unimall_user` VALUES ('37', null, null, '2', 'osTQe6Pp78VsDs1fknPJmuf70kmc', '落叶飞拂', 'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83epgiccmTiaUszb1RWGRqAVvc7bPJqPR4rzqXahEo2AnEeic3mPgpib1cqaiaUuDh1XpmPvvT38JkTVOarQ/132', '0', null, '1', '2019-08-10 19:25:55', '27.10.60.71', '1', '2019-08-10 19:25:55', '2019-08-09 19:28:32');
INSERT INTO `unimall_user` VALUES ('38', null, null, '1', 'oiAuI5Cw8It4nefGh9EbxxQ82LPs', null, 'https://wx.qlogo.cn/mmopen/vi_32/8CcbUpHBBAFkt2qq9xiawEfysjCNmYtyuqY33fn1ib2MibgeMsNSD39gQ8KEHn3iaXkurUpibBX0W5T96zBEesice18g/132', '0', null, '1', '2019-08-09 20:14:32', '27.10.60.71', '1', '2019-08-09 20:14:32', '2019-08-09 20:14:32');
INSERT INTO `unimall_user` VALUES ('39', null, null, '1', 'oiAuI5HMW5ohrgnmilDXoLgu-f1c', null, 'https://wx.qlogo.cn/mmopen/vi_32/nia8QbFDQtY0OnInPhicfUhx3kXZ4iapiarscLHQJyFTGYpgibSR5ejsWYMggopibKwDWoibMPiaKqMBKCcpVWc10Qt27w/132', '0', null, '1', '2019-08-09 20:23:29', '27.10.60.71', '1', '2019-08-09 20:23:30', '2019-08-09 20:23:29');
INSERT INTO `unimall_user` VALUES ('40', null, null, '1', 'oiAuI5IODaPmnGBy_dKZcC-Yg_tg', null, 'https://wx.qlogo.cn/mmopen/vi_32/L9VVrMFqrzcMNibb7GldfMA8oWJLGTka3lJHMSHyOU8Z3dPqpxhkUZUdeLXnhqKxfq8YKEStHyszj9UjPqB15IA/132', '0', null, '1', '2019-08-09 20:48:56', '27.10.60.71', '1', '2019-08-09 20:48:57', '2019-08-09 20:48:56');
INSERT INTO `unimall_user` VALUES ('41', null, null, '1', 'oiAuI5JHUexFgWT5HVec5iUkWC08', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83er7Nia50icDZaEOrzvAckQFlpCJoOuXFkLRbxHeOE437bewzMn1c6MA9xiaLoMd9zsEBJicLXiaKkKIw6Q/132', '0', null, '1', '2019-08-09 21:16:50', '27.10.60.71', '1', '2019-08-09 21:16:50', '2019-08-09 21:16:50');
INSERT INTO `unimall_user` VALUES ('42', null, null, '2', 'osTQe6DptIHIwm48EfuGrWS6r5NY', 'dotcools', 'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83ep6iavjrZU2cIKcxu2Iu7pc0lic5hoQLTQWuiaRWEnedC0R83rgPLDG9yN1PEeyT1KjXJ5Ivu5jPMzhw/132', '0', null, '1', '2019-08-09 21:22:55', '27.10.60.71', '1', '2019-08-09 21:22:56', '2019-08-09 21:22:55');
INSERT INTO `unimall_user` VALUES ('43', null, null, '1', 'oiAuI5OWfzg5ua0cbTIBZbHhK-Jg', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJViaBkDfDB6Z4aib0gDDZXfgpgZfcOegWQuSEyIb1gqEfqmDibjYLVfvavIoAXp0G3mGcxmzYwvgm9A/132', '0', null, '1', '2019-08-14 22:56:19', '27.10.60.71', '1', '2019-08-14 22:56:19', '2019-08-09 21:25:19');
INSERT INTO `unimall_user` VALUES ('44', null, null, '1', 'oiAuI5GlxjibrQRJc6mEyP10pmIc', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKAyfsLlZkYjcUU7eDrDddJXwf9SDxSffROSmfX8eFJ598RIbblDw3lAgoy4VY208eibh5l03375zQ/132', '0', null, '1', '2019-08-12 15:52:52', '27.10.60.71', '1', '2019-08-12 15:52:52', '2019-08-09 21:31:11');
INSERT INTO `unimall_user` VALUES ('45', null, null, '1', 'oiAuI5Ap2Q_wyKip0GwYyLe5v4yI', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJShe1tuy6Khj85WKaFJo2OemPM8m5XvuUnWdNAGu3JUHPhEOQD9y7MNJfIicfX0gNiaIjJVKUsGINA/132', '0', null, '1', '2019-08-09 21:53:54', '27.10.60.71', '1', '2019-08-09 21:53:55', '2019-08-09 21:53:54');
INSERT INTO `unimall_user` VALUES ('46', null, null, '1', 'oiAuI5J-3zv6ti0e_trq5bNbFUM0', null, 'https://wx.qlogo.cn/mmopen/vi_32/9UQvP3rrVDfRctPu7SBnRuff6CSLNrbzlFFNHepmxqqshdOzg6IN8FDzJia5y5C6aZxZnHynjRNE3pKWOh2ZUDQ/132', '0', null, '1', '2019-08-09 22:11:31', '27.10.60.71', '1', '2019-08-09 22:11:31', '2019-08-09 22:11:31');
INSERT INTO `unimall_user` VALUES ('47', null, null, '2', 'osTQe6Cd9_YFfd074BYRlCnbzAJs', '超', 'http://thirdwx.qlogo.cn/mmopen/vi_32/icFUUvDyRafQWibhkZibmKMEcwEI3CoevD30rYAKUewF37kafic62GTMDV3sPics3QdWDztjkDK5piakkk2Nt9oNRrAA/132', '0', null, '1', '2019-08-09 22:26:38', '27.10.60.71', '1', '2019-08-09 22:26:38', '2019-08-09 22:26:38');
INSERT INTO `unimall_user` VALUES ('48', null, null, '1', 'oiAuI5LLqaM5w8t5avAvo7YuDs68', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLoEIea5aSyGEHdyVE2yUib7IkgPMUDCCu4cF3QhI54uO3jXrLVWsDWDcq17pg8sjQspTags1k8RcQ/132', '0', null, '1', '2019-08-09 23:05:48', '27.10.60.71', '1', '2019-08-09 23:05:48', '2019-08-09 23:05:48');
INSERT INTO `unimall_user` VALUES ('49', null, null, '1', 'oiAuI5DRfuVuOUQWcC11BV130woo', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83epYB8cBfGrDQLYSrib37UfFCmvBeICp3wxucIrvxLnnKOHIR87qcT1jyeknFsOgsvjOP3j8Hkzr6WA/132', '0', null, '1', '2019-08-09 23:15:57', '27.10.60.71', '1', '2019-08-09 23:15:57', '2019-08-09 23:15:57');
INSERT INTO `unimall_user` VALUES ('50', null, null, '1', 'oiAuI5IZQLAcCihAaOFpxkj8yB-8', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIrKg5HHPu3ULsEuuGaSF8LFE4n9BwS3rWPy2ZAMA0E7YHeAPcVKe5pWHNHk5LAl8JoJBhl6rjBcw/132', '0', null, '1', '2019-08-09 23:23:36', '27.10.60.71', '1', '2019-08-09 23:23:36', '2019-08-09 23:23:36');
INSERT INTO `unimall_user` VALUES ('51', null, null, '1', 'oiAuI5BePVZ0u6mWPWDGYTYGK8Qg', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoTbcLy18XryxQJKFXjctK7EELps0XSYBeRq1kGSm3dAxukqHibkhOVoNNNGGLVHEP2IMVqo56vR0A/132', '0', null, '1', '2019-08-09 23:31:35', '27.10.60.71', '1', '2019-08-09 23:31:35', '2019-08-09 23:31:35');
INSERT INTO `unimall_user` VALUES ('52', null, null, '1', 'oiAuI5O8ZBelj79agN5cv5HBPIBc', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83erRMKuRPmiauNkUYV1Y0GibVyw7mg3LwMXHoRsLfMfGh6laUu7tX3GFRIhiaZIrWIyoyThNM5cVxaDjA/132', '0', null, '1', '2019-08-09 23:38:52', '27.10.60.71', '1', '2019-08-09 23:38:52', '2019-08-09 23:38:52');
INSERT INTO `unimall_user` VALUES ('53', null, null, '1', 'oiAuI5KWveXRlzTTCR15AtvVfofU', null, 'https://wx.qlogo.cn/mmopen/vi_32/Pay4lfibWVtFKMbQRueP3g3QnkRibospR4mriadLNkqTLWEXNCbhU81Y8ztaEXC4eX6UEbpficyxch4s5X3GaDllXg/132', '0', null, '1', '2019-08-10 21:54:01', '27.10.60.71', '1', '2019-08-10 21:54:01', '2019-08-09 23:43:51');
INSERT INTO `unimall_user` VALUES ('54', null, null, '1', 'oiAuI5Oq2ysU41LyPGba0ipSTV18', null, 'https://wx.qlogo.cn/mmopen/vi_32/cb6tcs4tfPOETnuw5TZa2pPiaohtlTEpHFicrc2icicE0ZxEubvY1WulYEBVpPtliarquVWKUyF0ENWYrnW0nMNoJEg/132', '0', null, '1', '2019-08-10 00:03:02', '27.10.60.71', '1', '2019-08-10 00:03:02', '2019-08-10 00:03:02');
INSERT INTO `unimall_user` VALUES ('55', null, null, '2', 'osTQe6K10KALedhmE_xS7ZIkfSU0', 'heng·🎷', 'http://thirdwx.qlogo.cn/mmopen/vi_32/V4TMqcjrNkXUmmyt2Qr4rUtRXj1wP1177c4XOibNlxVWJH7XCJAibh6TiaCfouHkS3nK820x9GFEfjchT8UJuEwLw/132', '0', null, '1', '2019-08-10 00:57:32', '27.10.60.71', '1', '2019-08-10 00:57:33', '2019-08-10 00:57:32');
INSERT INTO `unimall_user` VALUES ('56', null, null, '1', 'oiAuI5BXa5rkpGgEnd-jcFHshwp8', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoloFAia8tnbgYbzyZvIC1z2pWSB2qXb0VJzicDpyliafFqImS3BIwBoKwiadvckHjZMYlJMgQyex5v3A/132', '0', null, '1', '2019-08-10 05:49:40', '27.10.60.71', '1', '2019-08-10 05:49:40', '2019-08-10 05:49:40');
INSERT INTO `unimall_user` VALUES ('57', null, null, '1', 'oiAuI5CmrWZ95cH9vC0-ChK46yZ4', null, 'https://wx.qlogo.cn/mmopen/vi_32/17jLdc7alyD3paCeByDrwlTqGbPaPyO3YNlOhDBcsIC3A4c4PDUn7KQs3Ot7CXaiboJdxr1wDyJXIwRtGzsY1zw/132', '0', null, '1', '2019-08-10 06:07:03', '27.10.60.71', '1', '2019-08-10 06:07:03', '2019-08-10 06:06:57');
INSERT INTO `unimall_user` VALUES ('58', null, null, '1', 'oiAuI5CU2TUTpcJ1mCrdo0VXWGSk', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLia4qBUs5bFsyNrye592V99zZTTeTRic7icjTIydcWDopdpqH1RUmNdpia4yZ9jshMp0vLPuxmnJ7lMw/132', '0', null, '1', '2019-08-10 06:25:08', '27.10.60.71', '1', '2019-08-10 06:25:08', '2019-08-10 06:25:08');
INSERT INTO `unimall_user` VALUES ('59', null, null, '1', 'oiAuI5KRNvVd49WXiveKHgeyYIbU', null, 'https://wx.qlogo.cn/mmopen/vi_32/3Nnic59p9OUZ4f00XzO4PCk8VOVyQLIicVJJpxUfG3uDgQBfr0uqYZQXjbt5NUbDkhnK9zHJlFA4lyH0bZWfa9nw/132', '0', null, '1', '2019-08-10 08:56:32', '27.10.60.71', '1', '2019-08-10 08:56:32', '2019-08-10 08:56:32');
INSERT INTO `unimall_user` VALUES ('60', null, null, '1', 'oiAuI5LJSpkk7o5eydolEPvQ7rB4', null, 'https://wx.qlogo.cn/mmopen/vi_32/ibicsSyiaSwf2TLVIEibC7HjB6Oic5KRHKicFWkqpLOEHGXrDfFUH3dleiag43uUiaicqSlway2QkPoicveGy4pEe5KFpTJw/132', '0', null, '1', '2019-08-10 09:14:35', '27.10.60.71', '1', '2019-08-10 09:14:35', '2019-08-10 09:14:35');
INSERT INTO `unimall_user` VALUES ('61', null, null, '1', 'oiAuI5JoTo0kJNqPyTLxCeWGqRoI', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83epKRaUeNHXhUjkhtiaMPKoWHKs3IlCrhIbtRyEmJiaIhB5VQ0xqVk6xmacIlAqnhicibZf4AfpfCfCAQg/132', '0', null, '1', '2019-08-10 09:21:35', '27.10.60.71', '1', '2019-08-10 09:21:35', '2019-08-10 09:21:35');
INSERT INTO `unimall_user` VALUES ('62', null, null, '1', 'oiAuI5F5GCyiOj8WQHv37mFl7pZg', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTICOAdU4ZDNQhbVo57aHLslHOuV9aicDRB5vicFXXgamqNS4ePYVWTzx72omefPnUfuBKOCfpia1OYfQ/132', '0', null, '1', '2019-08-20 19:08:42', '27.10.60.71', '1', '2019-08-20 19:08:42', '2019-08-10 09:24:32');
INSERT INTO `unimall_user` VALUES ('63', null, null, '1', 'oiAuI5MSBiqzw7Z-1MaRcF7T7q0o', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLVXJzAUmdoplQeRFJCOZvFjcSibD0hRv2OqyMu2MsyTqD4M1SNmLbOvec8TlUYt1iafjicecgcwuL2Q/132', '0', null, '1', '2019-08-10 09:24:36', '27.10.60.71', '1', '2019-08-10 09:24:36', '2019-08-10 09:24:36');
INSERT INTO `unimall_user` VALUES ('64', null, null, '1', 'oiAuI5HYZJfnWasr8XVfPg6tWY5I', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIavBvxZz8M4SQZ3SGOhZun1D9YDNSvDqUicDX3lEOINicGSxlrdE4wMq4q6RVTmpR9Eo2qOuFa5zNA/132', '0', null, '1', '2019-08-10 09:44:29', '27.10.60.71', '1', '2019-08-10 09:44:29', '2019-08-10 09:44:29');
INSERT INTO `unimall_user` VALUES ('65', null, null, '1', 'oiAuI5KPEsbUqJU2cr8zdYyGAhKo', null, 'https://wx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEIUQAgn4mcrABNpNqncMWMSZGdPicOdxHic5QZf88DbEYFWuiceIBfy2btITtSZrgvXwMyKZgXa1x5WA/132', '0', null, '1', '2019-09-14 10:38:05', '27.10.60.71', '1', '2019-09-14 10:38:05', '2019-08-10 10:28:34');
INSERT INTO `unimall_user` VALUES ('66', null, null, '1', 'oiAuI5NrQ6ZNQQ_DOotM4P8ZN3HI', null, 'https://wx.qlogo.cn/mmopen/vi_32/ZdHZ8vlDjCppdjX5XjgmDbDfjsfiaUiaefJxFuRxLhVr9KqGpdPyfKRzMQebzkD6iae0rvniagiapicoNKTgNe1PrZ9w/132', '0', null, '1', '2019-08-14 09:40:36', '27.10.60.71', '1', '2019-08-14 09:40:36', '2019-08-10 10:38:12');
INSERT INTO `unimall_user` VALUES ('67', null, null, '1', 'oiAuI5PLiyphejK-WG78YVzhM4cQ', null, 'https://wx.qlogo.cn/mmopen/vi_32/PuZTVOu1fHSEwoPqrhDiaic6TRKftJvVPzJr6BUYCb63nghddlzh0DCH1DBtuWrH04mqSvkPPicZM1SLQohKVNOFA/132', '0', null, '1', '2019-08-10 10:52:38', '27.10.60.71', '1', '2019-08-10 10:52:39', '2019-08-10 10:52:38');
INSERT INTO `unimall_user` VALUES ('68', null, null, '1', 'oiAuI5LFZeIB0Gesu9WaahrT_rRQ', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLFQV87VnbA0D844iaYAPa0DickXCugHVDEDPKBQu2OHpE737O3bdla5UKsGg41G8aanibxljvS8PCow/132', '0', null, '1', '2019-08-10 10:55:07', '27.10.60.71', '1', '2019-08-10 10:55:07', '2019-08-10 10:55:07');
INSERT INTO `unimall_user` VALUES ('69', null, null, '1', 'oiAuI5L1diB2gXx-5_v7AiSreXdA', null, 'https://wx.qlogo.cn/mmopen/vi_32/5gkAkgensNcfgL9kibROX8YVlrLEcRglc2DmN1I9GnIpR0M4tR4dUictVgSGgnrlkkgwTIz7UmdiaibGLeTZ38vZicg/132', '0', null, '1', '2019-08-10 11:00:54', '27.10.60.71', '1', '2019-08-10 11:00:54', '2019-08-10 11:00:54');
INSERT INTO `unimall_user` VALUES ('70', null, null, '1', 'oiAuI5B18BZ_r428eR2bhnmsiwug', null, 'https://wx.qlogo.cn/mmopen/vi_32/05Wiazxo0OS5JIUl2zCRovkb6bswiaCdOzZRnOhkp0sMVV7EnbLyW3iaCbwofESvhF0NkznbosXFsJvsibOcbmurHQ/132', '0', null, '1', '2019-08-29 16:44:10', '27.10.60.71', '1', '2019-08-29 16:44:11', '2019-08-10 11:01:21');
INSERT INTO `unimall_user` VALUES ('71', '15123112145', '$1$1512311$U2K6drzZ7eU7zDj2fAplF.', '0', null, null, null, '0', null, '-1', '2019-08-10 13:24:13', '27.10.60.71', '1', '2019-08-10 11:13:37', '2019-08-10 11:13:37');
INSERT INTO `unimall_user` VALUES ('72', null, null, '1', 'oiAuI5Lzxclmyh5HGkJOub98XkTA', '可可', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTK31Ys3gm2LPIwA8z6RjOZBUKl5w3mSB7YhjKnEegne7aWxrJ0cbQoKiaACr3q0uHGO7K2I9Df2rtg/132', '0', null, '1', '2019-08-12 02:26:49', '27.10.60.71', '1', '2019-08-12 02:26:49', '2019-08-10 11:47:29');
INSERT INTO `unimall_user` VALUES ('73', null, null, '2', 'osTQe6FMU5zekhAWhCej97-Okpw0', '文平', 'http://thirdwx.qlogo.cn/mmopen/vi_32/Q3auHgzwzM6ibpobUVKw1oRCXp4U3ljIo8tYJ90r4jL2MKhsPvNAjx61GBUAnN5TOAiampglejxB5BJP3vWGB6uA/132', '0', null, '1', '2019-08-10 12:15:14', '27.10.60.71', '1', '2019-08-10 12:15:15', '2019-08-10 12:15:14');
INSERT INTO `unimall_user` VALUES ('74', null, null, '1', 'oiAuI5EtOl8UtG-yuI6Zmr_t7OCs', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q3auHgzwzM6kRhRg6NMz64gdTEj1XhouHPhibY712uDbTC77ftvX6Sb0f6wN8Znibndb05QFqglyS2fMblayoE6Q/132', '0', null, '1', '2019-08-10 12:16:31', '27.10.60.71', '1', '2019-08-10 12:16:32', '2019-08-10 12:16:31');
INSERT INTO `unimall_user` VALUES ('75', '18584669549', '$1$1858466$.KpeCykargRyb27bGhdDF/', '0', null, 'Heeellllo', null, '0', null, '-1', '2019-12-29 09:39:16', '27.10.60.71', '1', '2019-08-10 13:20:19', '2019-08-10 13:19:55');
INSERT INTO `unimall_user` VALUES ('76', null, null, '1', 'oiAuI5AWeae3hgbaXpW6K3xlcTlI', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJ947XOoIWPCSexNbedWbUS7bBMly7OcATic39TkiaL2tukCnmj1TpEyWueAF6nFI5JEoSA88gvhSLg/132', '0', null, '1', '2019-08-10 13:58:03', '27.10.60.71', '1', '2019-08-10 13:58:03', '2019-08-10 13:58:03');
INSERT INTO `unimall_user` VALUES ('77', null, null, '1', 'oiAuI5IaGJMZvPRKn6yw2F4OnDKc', null, null, '0', null, '-1', '2019-08-10 14:06:05', '27.10.60.71', '1', '2019-08-10 14:06:05', '2019-08-10 14:06:05');
INSERT INTO `unimall_user` VALUES ('78', null, null, '1', 'oiAuI5N6GswuvjZb8XbNCfqVlrlE', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKicG5vnhqvw4vnYFtUA4gYdPd1TDmqwDZdma9pkEbcNYpWccAqEKuUzjqzOWDBzKoo7fAT0Yy0AaA/132', '0', null, '1', '2019-08-10 14:24:47', '27.10.60.71', '1', '2019-08-10 14:24:47', '2019-08-10 14:24:47');
INSERT INTO `unimall_user` VALUES ('79', null, null, '1', 'oiAuI5GwfsdQz4Nr8-WaIvdqxiTI', null, 'https://wx.qlogo.cn/mmopen/vi_32/oV1jMvgVgfCxJGsSibUqHeAWTgDU9Gkvv9lJKNYysIEByGIhqPaancXTZyibUrlHSE3kO2YsUXYpePbicN4QVj4KQ/132', '0', null, '1', '2019-08-10 14:29:11', '27.10.60.71', '1', '2019-08-10 14:29:11', '2019-08-10 14:29:11');
INSERT INTO `unimall_user` VALUES ('80', null, null, '1', 'oiAuI5DN-i64JC93gQomVku_jFD4', null, 'https://wx.qlogo.cn/mmopen/vi_32/TWoicsKV0zVbwLmmyewaCjef9Ib3yk0FwXNsAOfvnoHqmBICnLZEjK8TwbialrVztxf5vaXp46Kib77OebezicwQ8Q/132', '0', null, '1', '2019-08-10 14:31:58', '27.10.60.71', '1', '2019-08-10 14:31:58', '2019-08-10 14:31:58');
INSERT INTO `unimall_user` VALUES ('81', null, null, '1', 'oiAuI5Jgcs_6pQRartR1g6jMrCsA', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJTgNpkSibNiaDibUxQMcLEcbzmAkA4MKwA50dLqe3GJQQzR2PgdO4KsATSfPghCPib3v7qial6eTzEClw/132', '0', null, '1', '2019-08-10 14:54:48', '27.10.60.71', '1', '2019-08-10 14:54:48', '2019-08-10 14:54:48');
INSERT INTO `unimall_user` VALUES ('82', null, null, '1', 'oiAuI5JLR1o_1S2EYW9hgyiDQZ50', null, 'https://wx.qlogo.cn/mmopen/vi_32/GItrRq14xmN3kEymJQCw3OiboV5dUiaH5HRyhQGwFUDoOZ8TIOM3BDLrJCwR0rX2m3peQcCRX6yAQoD0BwkKhwbA/132', '0', null, '1', '2019-08-10 15:37:06', '27.10.60.71', '1', '2019-08-10 15:37:06', '2019-08-10 15:37:06');
INSERT INTO `unimall_user` VALUES ('83', null, null, '1', 'oiAuI5A4ERE1wRls_NKV8wb66i1w', null, 'https://wx.qlogo.cn/mmopen/vi_32/HUiaTtLWyQ6SNPiclibbSSHjWmM2ZTCicyicnjNPcRibCyDI5jw4l9ajoxIsRZqZ2WaH87icEmW0RvFI6BQgfeibHiafFdw/132', '0', null, '1', '2019-08-10 15:58:45', '27.10.60.71', '1', '2019-08-10 15:58:46', '2019-08-10 15:58:45');
INSERT INTO `unimall_user` VALUES ('84', null, null, '1', 'oiAuI5K1VvzF0C4q-EqRlHJreNg4', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKUqQficnsgtwpl8L67tNVuLNqs1CibGcV2EkdgOE2Uc1UETfRu3WYCpJXVQkJia6ruzOS9btA2TMWYQ/132', '0', null, '1', '2019-08-20 11:24:00', '27.10.60.71', '1', '2019-08-20 11:24:01', '2019-08-10 16:36:21');
INSERT INTO `unimall_user` VALUES ('85', null, null, '1', 'oiAuI5Oc74YK1hje_ubnfYt6z-bs', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKXgcsLsqQU2W32MbOgHdC3LfXOWzftV693nUIkXusL2hfuM1tRjKksq2ZcmrN09cQb7QRrbvWFMA/132', '0', null, '1', '2019-08-10 16:41:01', '27.10.60.71', '1', '2019-08-10 16:41:01', '2019-08-10 16:41:01');
INSERT INTO `unimall_user` VALUES ('86', null, null, '1', 'oiAuI5F7yOzcMLcTGPWo2jCL06W8', null, 'https://wx.qlogo.cn/mmopen/vi_32/zLqnqeGNiboSSic96nUedvaUibB716NPLQkjgFIMDCVVtWFrziaJRAUzJNEjQWfAK41CLBJLAibvUXXMRI7qtxdhLfg/132', '0', null, '1', '2019-08-10 16:46:51', '27.10.60.71', '1', '2019-08-10 16:46:51', '2019-08-10 16:46:51');
INSERT INTO `unimall_user` VALUES ('87', null, null, '1', 'oiAuI5CZ4Ba5glxpZXDiH2ZBUmfM', null, 'https://wx.qlogo.cn/mmopen/vi_32/BURC2shnicTiaTvTQaAZTTLHz10tibCsAPRReicnp5LKcgibIMS2jC4asouunbaVqJ2ibo7srp4ysFpt1xcl1aUvNPTw/132', '0', null, '1', '2019-08-17 16:54:07', '27.10.60.71', '1', '2019-08-17 16:54:07', '2019-08-10 17:04:12');
INSERT INTO `unimall_user` VALUES ('88', null, null, '1', 'oiAuI5ETHDrQiI9YTbdQyWXXN6Ag', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIWSfAiciaIN8UkzSCM8oZH59saG8xrCC6UCbUxiaIbicvGsSmvNLKnZXe6W0w5QvZvI3icWAGbq7QHxWQ/132', '0', null, '1', '2019-08-10 18:37:30', '27.10.60.71', '1', '2019-08-10 18:37:30', '2019-08-10 18:37:30');
INSERT INTO `unimall_user` VALUES ('89', null, null, '1', 'oiAuI5ME27k-geULIbP8JNij5v6s', null, 'https://wx.qlogo.cn/mmopen/vi_32/OVq8d3D1m1Q5iaCiaYtkop7XIP36ibhkrj1wLkNKb0IxQ4IeMLmRF6aB2qcZr5wNFH5IpicnIIlwvxCgtjhesI1BEg/132', '0', null, '1', '2019-08-10 18:48:53', '27.10.60.71', '1', '2019-08-10 18:48:53', '2019-08-10 18:48:53');
INSERT INTO `unimall_user` VALUES ('90', null, null, '1', 'oiAuI5Epgq3UmmOkGYRPgdt9ps3Y', null, 'https://wx.qlogo.cn/mmopen/vi_32/lZjDx0k6W7zUY08aoP85Kyb5uW5OjwIqDTHAFvqHDnvk1QZRsoNHmiby8mx2qCKqeYnkZ3CvYibFFFnnkeibTDZQA/132', '0', null, '1', '2019-08-10 20:12:00', '27.10.60.71', '1', '2019-08-10 20:12:00', '2019-08-10 20:12:00');
INSERT INTO `unimall_user` VALUES ('91', null, null, '1', 'oiAuI5Mf7dFPnkjEIYUVgaXTDnhk', null, null, '0', null, '-1', '2019-08-10 21:16:47', '27.10.60.71', '1', '2019-08-10 21:16:47', '2019-08-10 21:16:40');
INSERT INTO `unimall_user` VALUES ('92', null, null, '1', 'oiAuI5OZ71nVc4zM2kQ8o_YdpbE4', null, 'https://wx.qlogo.cn/mmopen/vi_32/MJdzHRsaDY4ww3iarutic9QTiafSqbtRD5gFib0q1BpnLv4874NU3oPUSrpHZ0oUDp7oGIU4yC1SbkicgYhyCeibIvSg/132', '0', null, '1', '2019-08-10 21:25:37', '27.10.60.71', '1', '2019-08-10 21:25:37', '2019-08-10 21:25:37');
INSERT INTO `unimall_user` VALUES ('93', null, null, '1', 'oiAuI5Jj_8WSZ1u215zWHlQxwAhc', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLV9rhlIrTlrbJMCrlCCrw7lCwVQDvqprk18xHhhHW4mOogBwhzbXgtmlDdZSPiaQX2pblfNmiaVI1A/132', '0', null, '1', '2019-08-10 21:49:56', '27.10.60.71', '1', '2019-08-10 21:49:57', '2019-08-10 21:49:56');
INSERT INTO `unimall_user` VALUES ('94', null, null, '1', 'oiAuI5HIFttwAzm8nhduo7HXB3R4', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTILAN1oSb4cQZolWsJ7Gd9vmfJwsCtM5yUgiaIhrXw7PQ56HoYSQj8wAT9bROabKuCotDXTIcRhwkg/132', '0', null, '1', '2019-08-10 22:39:01', '27.10.60.71', '1', '2019-08-10 22:39:01', '2019-08-10 22:39:01');
INSERT INTO `unimall_user` VALUES ('95', null, null, '1', 'oiAuI5EpcwgsgPpEIuL7yoJS86Ts', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIvEaKVSCgYWFR407yzfvD0FRpGI53aE5XVwWhOKbvib78AJJkfdzUTSc6ibYUDo4rkfxd9JFo2zxwA/132', '0', null, '1', '2019-08-10 23:05:48', '27.10.60.71', '1', '2019-08-10 23:05:48', '2019-08-10 23:05:48');
INSERT INTO `unimall_user` VALUES ('96', null, null, '1', 'oiAuI5KPx9-SgMgmDzrXsNiPmYaQ', null, null, '0', null, '-1', '2019-08-10 23:13:45', '27.10.60.71', '1', '2019-08-10 23:13:45', '2019-08-10 23:13:45');
INSERT INTO `unimall_user` VALUES ('97', null, null, '1', 'oiAuI5IxVI-B-6_vilJa3iLdeL7o', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eriakOicUNcyM9bib39JZMOQ01N0DqpyfD4ia6tJvbZicVAbicnuOSZboJP8lUcSeLy8wDRWdVAK7r1RK3A/132', '0', null, '1', '2019-08-11 00:16:45', '27.10.60.71', '1', '2019-08-11 00:16:45', '2019-08-11 00:16:45');
INSERT INTO `unimall_user` VALUES ('98', null, null, '2', 'osTQe6KmvEVKB1KAjob5RxxJUP-4', '章强', 'http://thirdwx.qlogo.cn/mmopen/vi_32/B1kqtcDOe12cLdBSM4JORqUic2pmoChCsGEMibnc2ZicnpMNINlZuIaB4PpbCic2QxpdJsHlRlqVia3KhbRDPGL99uQ/132', '0', null, '1', '2019-08-11 02:57:53', '27.10.60.71', '1', '2019-08-11 02:57:54', '2019-08-11 02:57:53');
INSERT INTO `unimall_user` VALUES ('99', null, null, '1', 'oiAuI5Jt63eHRS0NrsCMKb13-RDU', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLPoGDVWl319GG4HT1MyRnvnstMMxzPsx38OXACcicsRGbYtHYmT8j62p0PteV1f2XykqiaaraRIJ8A/132', '0', null, '1', '2019-08-11 05:37:06', '27.10.60.71', '1', '2019-08-11 05:37:06', '2019-08-11 05:37:06');
INSERT INTO `unimall_user` VALUES ('100', null, null, '1', 'oiAuI5P_0mf4csrqovqNgjClZI24', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLwe6yzcS9llcvPOibf27n8tHzFxKbbw4xibI4zwmzLVjibS1v0qv3cMtOvpKxia212bVVx5btOOKTAiaw/132', '0', null, '1', '2019-08-11 06:42:32', '27.10.60.71', '1', '2019-08-11 06:42:32', '2019-08-11 06:42:32');
INSERT INTO `unimall_user` VALUES ('101', null, null, '1', 'oiAuI5MhoNK93V6eBYVl1SMjTI6k', null, 'https://wx.qlogo.cn/mmopen/vi_32/TeUXRrLuW5nQUqHsiaIqFUrytQUy6sJaI66fzohNUfDeCVnctHFQicJOZaov1RYgzAWSnQ76vKicDe0xMibjpgpN9A/132', '0', null, '1', '2019-08-11 14:56:58', '27.10.60.71', '1', '2019-08-11 14:56:58', '2019-08-11 09:21:28');
INSERT INTO `unimall_user` VALUES ('102', null, null, '2', 'osTQe6PZ-G_QIBW9sSfpRWp3olBA', '欢乐马', 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q3auHgzwzM6e9W6KEkhtc5Vl3asItWkXkofJlxsw7ZmGFn1tgXjWeRI8aeBFgHqtjtHyz6QwhN5E6bHX2rhRsw/132', '0', null, '0', '2019-08-11 09:49:35', '27.10.60.71', '1', '2019-08-11 09:49:36', '2019-08-11 09:49:35');
INSERT INTO `unimall_user` VALUES ('103', null, null, '1', 'oiAuI5GIcqvWs2Op0Vr0zICn3fPg', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83epfmOHdm14RnhULNlAE810o9vd1uza485reb0Tk7gnM7TZqbcDicS8R2EMwzh44GFaicVd8hWFNwF1Q/132', '0', null, '1', '2019-08-13 13:09:31', '27.10.60.71', '1', '2019-08-13 13:09:31', '2019-08-11 09:53:12');
INSERT INTO `unimall_user` VALUES ('104', null, null, '1', 'oiAuI5Ghgg6IZkdwcIQKtnNb4r-c', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83erzRqh4S6sqhqO1njkf3qRZpmfTQdnVqmzE87JV2R0DRfQqibicYtZfAaRXEv98oZvmRQ3faqlUiapVw/132', '0', null, '1', '2019-08-13 13:18:35', '27.10.60.71', '1', '2019-08-13 13:18:35', '2019-08-11 10:00:28');
INSERT INTO `unimall_user` VALUES ('105', null, null, '1', 'oiAuI5Aeh0253rdApUZrp1QmOGAo', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83erzRqh4S6sqhs1B6ia4GXl1bpzMdZTFeVXv3PdDoxTJricFicE4IZUerM1kuuYcVMyFSekXJ8mibcYjhQ/132', '0', null, '1', '2019-09-05 08:04:26', '27.10.60.71', '1', '2019-09-05 08:04:27', '2019-08-11 10:40:59');
INSERT INTO `unimall_user` VALUES ('106', null, null, '1', 'oiAuI5C-5JXSvMbMPPfXiuzi7Qok', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIHunhiaMA2rcbFaXUcrqd2dc46fwB2pPDdfasPnx2viblicnMibObPFZ0ho6F3kF2Lv3221Ipzu4CZJg/132', '0', null, '1', '2019-08-20 22:10:15', '27.10.60.71', '1', '2019-08-20 22:10:15', '2019-08-11 10:56:50');
INSERT INTO `unimall_user` VALUES ('107', null, null, '2', 'osTQe6OfXn0BE_VuNwWfqbSDAsJk', '云中漫步', 'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83epFyCELpVg2fDvUAmfakArs4vdtia2U3eGG5IibpibWrbb1PkcgsQOSdibGOibS3HENPnO8UhAIs3jtB6g/132', '0', null, '1', '2019-08-11 11:01:44', '27.10.60.71', '1', '2019-08-11 11:01:44', '2019-08-11 11:01:44');
INSERT INTO `unimall_user` VALUES ('108', null, null, '1', 'oiAuI5GtUfCJKle-NU3Gl3BXjRX8', null, 'https://wx.qlogo.cn/mmopen/vi_32/H25dgE0a25FKFAt6ia0kfKCaf7PN5tCoI23yxKLVWu6d2I2cTyRySeGUG4lSpXUh8VSN87LfTkHSy6D68lVzkmQ/132', '0', null, '1', '2019-08-11 11:16:44', '27.10.60.71', '1', '2019-08-11 11:16:44', '2019-08-11 11:16:44');
INSERT INTO `unimall_user` VALUES ('109', null, null, '1', 'oiAuI5MzIAscO6y5iTtLBFQTlp6s', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eq1fuiaf4FibGoWrmCxg9AOBD2TcHeGZibHI6uAxvKupLIJ6QdzGPYZxMcR4UMOx96Db0DjFGsbhM8xw/132', '0', null, '1', '2019-08-11 12:31:02', '27.10.60.71', '1', '2019-08-11 12:31:02', '2019-08-11 12:31:02');
INSERT INTO `unimall_user` VALUES ('110', null, null, '1', 'oiAuI5AVULuYBXWfBp9nRVifSo2w', null, 'https://wx.qlogo.cn/mmopen/vi_32/Rv2qIF4ZrMWlI4HtoESSYT4bib1azcEqia4TWMZYx1AzoYbbOPKYpQrwOc5AseQY5NicQKiaQ3SgIJopUfGfu1iaCYA/132', '0', null, '1', '2019-08-11 12:38:05', '27.10.60.71', '1', '2019-08-11 12:38:05', '2019-08-11 12:38:05');
INSERT INTO `unimall_user` VALUES ('111', null, null, '1', 'oiAuI5BeEYbZmGv3VOZkTZzs-Q9I', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIxAIXQL4JxsjKNVfO3LiaBcbDO7WSBiajptjWSehEUNeLTNWpcq72QbIqNZ1TRFc7WSm63BJsadvOg/132', '0', null, '1', '2019-08-11 13:25:06', '27.10.60.71', '1', '2019-08-11 13:25:07', '2019-08-11 13:25:06');
INSERT INTO `unimall_user` VALUES ('112', null, null, '1', 'oiAuI5DXimn_tCZuO0anyI4-0JOU', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLe00c7whq5qC3413nacvCHdamibIr0nx0KBTA5OUgYVOLFibvJDGv0OCmR6SlvQYgg0nFe7yEm9Dibg/132', '0', null, '1', '2019-08-11 14:35:08', '27.10.60.71', '1', '2019-08-11 14:35:08', '2019-08-11 14:35:08');
INSERT INTO `unimall_user` VALUES ('113', null, null, '1', 'oiAuI5DuniCeNOHf65sWdukY9HFI', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJ7AatzrjqPL9vgWws4Pl5HmN67mh0rwCsR2bTzoVKBc9IBkkZ2du7gkzdMKuzdl4l3pLmiczXaFyw/132', '0', null, '1', '2019-08-11 14:45:16', '27.10.60.71', '1', '2019-08-11 14:45:16', '2019-08-11 14:45:16');
INSERT INTO `unimall_user` VALUES ('114', null, null, '1', 'oiAuI5DUvoO5kH5xeVB6KAvBvZzk', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoIUdYgslKQ1s6zDUxwsdpwN87IBSI2B44ACicIYBkLiaedA9ztuj58OrFiaeGnSsrSMZaUWPvBU45rQ/132', '0', null, '1', '2019-08-11 15:05:19', '27.10.60.71', '1', '2019-08-11 15:05:19', '2019-08-11 15:04:35');
INSERT INTO `unimall_user` VALUES ('115', null, null, '1', 'oiAuI5CxHPNVvhsIV_Ny-zIwqeAQ', null, 'https://wx.qlogo.cn/mmopen/vi_32/l9dqRoLkFJDYPX6ufGXGo0HmPZ4JaGib6FZP58QicUr01MCzNTAdCaPwsTibFiaQf8FlOq5BeXw747d6CklibicL3Qhg/132', '0', null, '1', '2019-08-11 15:31:02', '27.10.60.71', '1', '2019-08-11 15:31:02', '2019-08-11 15:31:02');
INSERT INTO `unimall_user` VALUES ('116', null, null, '1', 'oiAuI5BCTIlQWiv0-S7x8b8ZyFtw', null, 'https://wx.qlogo.cn/mmopen/vi_32/5gkAkgensNcfgL9kibROX8bIvLNt73cBV6CX5hhGzhdb4k0QURgD6KibB3osjYJVGkCVPAb7UiaQWl2la8bPeOZZg/132', '0', null, '1', '2019-08-11 15:33:17', '27.10.60.71', '1', '2019-08-11 15:33:17', '2019-08-11 15:33:17');
INSERT INTO `unimall_user` VALUES ('117', null, null, '1', 'oiAuI5MR_z90GCqkyNa9JlLJ5u0E', null, 'https://wx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEKIr1mZDgFeua0B3iaUYGIkkcTRNZDohCib7fzOjo52dubJ1nwa6mGvB3SPT15ib2y5OEVqK0EjoBrcw/132', '0', null, '1', '2019-08-11 17:00:11', '27.10.60.71', '1', '2019-08-11 17:00:11', '2019-08-11 17:00:11');
INSERT INTO `unimall_user` VALUES ('118', null, null, '2', 'osTQe6BNfEHzItA8EHGe0yY1hIAI', '流浪的家猫😝😜', 'http://thirdwx.qlogo.cn/mmopen/vi_32/xLZOUD1IwrEticTmF6qnVO5DDW2hrB4Hgt6LVJdyzQNaWC8YfDyxX5KUdkDiayxrDEYS6H8wA1ZyyfR5hguSJzqw/132', '0', null, '1', '2019-09-03 14:07:21', '27.10.60.71', '1', '2019-09-03 14:07:22', '2019-08-11 17:10:46');
INSERT INTO `unimall_user` VALUES ('119', null, null, '1', 'oiAuI5ObO1MJPKGDJrwT2EQd00Kw', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKUOdXYUhEIv1qxsjoaxZvfnb9tLRxDTZz0FsSXhP315ANuSp3IEnYGj1WeJiatK0lsZiaDSoVpdQWw/132', '0', null, '1', '2019-08-20 03:49:17', '27.10.60.71', '1', '2019-08-20 03:49:17', '2019-08-11 17:41:39');
INSERT INTO `unimall_user` VALUES ('120', null, null, '1', 'oiAuI5IRmUZxPy2ioaX8tq8OwFjo', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLLniccwC1MoouOcEF3q4v2xkWwYA8p9bvvcv8eia7iaFNJhkjU8Jz02PDESr8Vv86qZRr0pfbYPviaSA/132', '0', null, '1', '2019-08-11 21:54:56', '27.10.60.71', '1', '2019-08-11 21:54:56', '2019-08-11 21:54:56');
INSERT INTO `unimall_user` VALUES ('121', null, null, '1', 'oiAuI5MRqFrO89ZwGjOYZsmzf1oI', null, 'https://wx.qlogo.cn/mmopen/vi_32/jicgtv1vSxiciaAg1vl37sOmSp4Ph40dSLsZs9o65xj0J61K5uqcX5fD2za0uLXIMv2KF0iaHt9kHkuE7PYAZ7JAbw/132', '0', null, '1', '2019-08-11 22:39:46', '27.10.60.71', '1', '2019-08-11 22:39:46', '2019-08-11 22:39:46');
INSERT INTO `unimall_user` VALUES ('122', null, null, '1', 'oiAuI5E-luX5TLIy0fVc7l4qIgPs', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLT6ueicIhHsibv0B9P1Dm5icrfWm6ewxGJkibPKKCSILKRic0Zvpqnn84f0y4z3Fq3OPOvcwJzzLibjsvA/132', '0', null, '1', '2019-08-11 23:51:23', '27.10.60.71', '1', '2019-08-11 23:51:23', '2019-08-11 23:51:23');
INSERT INTO `unimall_user` VALUES ('123', null, null, '1', 'oiAuI5AA1cQgKMQhJXp70pFXgrM4', null, 'https://wx.qlogo.cn/mmopen/vi_32/DK9ibN3ibDbOHaYeicd9BvtniawFdh6YwibbuKOsBiafOd1XD9mb2TZjFEYEfMFGeu9WnhUAicsJfN146YiaicKWBUGZguA/132', '0', null, '1', '2019-08-12 00:16:32', '27.10.60.71', '1', '2019-08-12 00:16:32', '2019-08-12 00:16:32');
INSERT INTO `unimall_user` VALUES ('124', null, null, '1', 'oiAuI5Nk2hoxriki6mZz2wlx3yes', null, 'https://wx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEIZNRdWibFeDIUvcksyN8Y5m5kbDJDASrDlLBiagwGFicSn0h2iak1J4cNhImHOUh8CfBica9ibZqMg6sFA/132', '0', null, '1', '2019-08-12 00:44:20', '27.10.60.71', '1', '2019-08-12 00:44:20', '2019-08-12 00:44:20');
INSERT INTO `unimall_user` VALUES ('125', null, null, '1', 'oiAuI5G0oGJWmG5E934niR8XKS4M', null, 'https://wx.qlogo.cn/mmopen/vi_32/ZVgK6dXdl9ydjWqJ8w8MUbB2QTa14edFIg17Gw2YEdIAF3uD0uRKAicROg2rkQnhKoSrl60NzyjRgO7PG9jpSgQ/132', '0', null, '1', '2019-08-12 01:08:35', '27.10.60.71', '1', '2019-08-12 01:08:35', '2019-08-12 01:08:35');
INSERT INTO `unimall_user` VALUES ('126', null, null, '1', 'oiAuI5KpKDd032XopOHc_1jkL3O0', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLCm8OWkDoutY4s6xIs8wUkvAFcQM5PSeRltnX327la3Dn3Ly5GNtB9QdDkqFYDUM7N2vDTLDPYBg/132', '0', null, '1', '2019-08-12 01:46:07', '27.10.60.71', '1', '2019-08-12 01:46:07', '2019-08-12 01:46:07');
INSERT INTO `unimall_user` VALUES ('127', null, null, '1', 'oiAuI5JZyDaOhIw6oV5awjXuG5Vk', null, 'https://wx.qlogo.cn/mmopen/vi_32/mGfgwCK8RuV5FyuQWngx7pxgKWHexsCT9fwBm80icAw1pe4iaNV4mfC2iazoA7C2PiaAEM6JdGc5D3NNqC88trcPqw/132', '0', null, '2', '2019-08-24 08:35:06', '27.10.60.71', '1', '2019-08-24 08:35:06', '2019-08-12 08:12:32');
INSERT INTO `unimall_user` VALUES ('128', null, null, '1', 'oiAuI5OWSisI8e95HqE36SKrQPow', null, 'https://wx.qlogo.cn/mmopen/vi_32/R3hvIy3IO5EZth2eLnNAxQaicqJ2ibHwbNE2JthR3uAkOdYZOgfhQ3ibIXy937QvNFiaU9x01vpzm2iaFUrIlZf4ZUg/132', '0', null, '1', '2019-08-21 17:38:43', '27.10.60.71', '1', '2019-08-21 17:38:43', '2019-08-12 08:36:38');
INSERT INTO `unimall_user` VALUES ('129', null, null, '1', 'oiAuI5Lcq5KJMAmsW90Na7Ck6nhE', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIPsbt7BibERGicc95lJXqwWibpuFM1ykVxTW6iaNvj3zLlHGkCcj134KMumrcX49diaa00wJ47QOjq1xw/132', '0', null, '1', '2019-08-15 21:21:57', '27.10.60.71', '1', '2019-08-15 21:21:57', '2019-08-12 09:08:46');
INSERT INTO `unimall_user` VALUES ('130', null, null, '1', 'oiAuI5FArVotlDUe4CV7GkQyHJ7k', null, 'https://wx.qlogo.cn/mmopen/vi_32/3e8icfXhCM7ibiba7mYsHXYZUrVQ0APjqjT3OuhwXrBEL9C9aQnfLy2H0WiamaPeYptxaW8T8dNsQvtfVNdI3ToWqA/132', '0', null, '1', '2019-08-12 09:13:28', '27.10.60.71', '1', '2019-08-12 09:13:28', '2019-08-12 09:13:28');
INSERT INTO `unimall_user` VALUES ('131', null, null, '1', 'oiAuI5BP3ktMR3vtS8QsK-sG-JDM', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqViaxic2E8N4icpZAzgQGmlBGvSKo8d7iaRgqJVveKFJIFKGq9aGLW2ppGlK3ppdrOCDKjIXpnYRJ9mA/132', '0', null, '1', '2019-08-20 23:34:38', '27.10.60.71', '1', '2019-08-20 23:34:38', '2019-08-12 09:15:03');
INSERT INTO `unimall_user` VALUES ('132', null, null, '1', 'oiAuI5I2So8qfIbP51BDkP0IprYE', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83epZnG62cq5IxhwUVcuLNg8jZibHrwo1wywHiaqSbTA9semgZXYFfOWRqQAiaTqm5ftbYvvFlu3HloFPw/132', '0', null, '1', '2019-08-12 09:28:32', '27.10.60.71', '1', '2019-08-12 09:28:33', '2019-08-12 09:28:32');
INSERT INTO `unimall_user` VALUES ('133', null, null, '2', 'osTQe6NMF5_lzwEOhElImT5tUkww', '叶鹏', 'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoicu4KMn2VWVn2EGWwGJpMFSgXkmpJGaYz6e3UwBv8gwqDZxO19ibzBbfc6ohnMsrU2WULnkOgw1bw/132', '0', null, '1', '2019-09-01 11:30:54', '27.10.60.71', '1', '2019-09-01 11:30:54', '2019-08-12 09:33:20');
INSERT INTO `unimall_user` VALUES ('134', null, null, '1', 'oiAuI5G5IzaFkfsskgmhcLbqpJiM', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83erLPQzuicqpmedktibico1vDzcVMeaw97TzOuducJcSYwdiaZacZAF2Eq3FUpOx2THz2iaSvZVYwCIGH5g/132', '0', null, '1', '2019-08-12 09:43:07', '27.10.60.71', '1', '2019-08-12 09:43:08', '2019-08-12 09:43:07');
INSERT INTO `unimall_user` VALUES ('135', null, null, '1', 'oiAuI5MGkNS63GB0Mx9rwTqa-uDs', null, 'https://wx.qlogo.cn/mmopen/vi_32/cFtcpjRVvcmuD8EAWznuzAibibA27oibVgVIicrYGHNc4z6bv86opf9WW2dWsUv4om43vDFtJibiclSj21Q5WyRHnNpQ/132', '0', null, '1', '2019-08-12 09:43:45', '27.10.60.71', '1', '2019-08-12 09:43:45', '2019-08-12 09:43:45');
INSERT INTO `unimall_user` VALUES ('136', null, null, '1', 'oiAuI5J0Aj7481O4wHBJaTJspYzI', null, 'https://wx.qlogo.cn/mmopen/vi_32/Qnex1Ta2hKmraBXJFjhnMCtQOOegUAOwLV3ib3NOBuiclegYkTiabXFTZUy7RYSHGcFqphnKoAuOcs1QHwfJrX1sg/132', '0', null, '0', '2019-08-12 10:11:42', '27.10.60.71', '1', '2019-08-12 10:11:42', '2019-08-12 10:11:42');
INSERT INTO `unimall_user` VALUES ('137', null, null, '2', 'osTQe6KB5ugdDEfQXv4x-jd1TnHs', '天津洞庭潮科技有限公司', 'http://thirdwx.qlogo.cn/mmopen/vi_32/85qqVM7NEQmJJIuyYAvvy7mhT7nCZibENt9VaoOO9b9IWajicIdsthr0KibniaBoscic3BfpTJSugSbytpSeRdwwPPQ/132', '0', null, '0', '2019-08-22 18:32:59', '27.10.60.71', '1', '2019-08-22 18:33:00', '2019-08-12 10:14:39');
INSERT INTO `unimall_user` VALUES ('138', null, null, '1', 'oiAuI5N7_2dwmlcdCJHCnH3PTSsY', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoicygXVeGVR5oyr6YnWzlN5qcsKtkgHt4p95VVCAHy3XOgZMpLibAoCEBAWVtf7vQe2ehxa6nVwibhw/132', '0', null, '1', '2019-09-16 15:40:51', '27.10.60.71', '1', '2019-09-16 15:40:51', '2019-08-12 10:24:34');
INSERT INTO `unimall_user` VALUES ('139', null, null, '1', 'oiAuI5IkMjYa3oky9meszQyO8yEo', null, 'https://wx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEKhq1cqYVRRYmeYHhYtfBL5U6zj5ibG0fezJ8Z9qCPlrMISwe6mfMKIfYy3kIuRhQoLXqebT17waIA/132', '0', null, '1', '2019-08-12 11:20:21', '27.10.60.71', '1', '2019-08-12 11:20:21', '2019-08-12 11:20:21');
INSERT INTO `unimall_user` VALUES ('140', null, null, '1', 'oiAuI5PigAPka3OfcRkwvtZE9I6w', null, 'https://wx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEKspB1J2fmkCQc9mksp7ImN8REDT5EicInz5KtwwHRrlW56Od7oDVjd6QPoug20h1kc3zk2RXcYqcA/132', '0', null, '1', '2019-08-12 11:30:10', '27.10.60.71', '1', '2019-08-12 11:30:10', '2019-08-12 11:30:10');
INSERT INTO `unimall_user` VALUES ('141', null, null, '1', 'oiAuI5IDHLUGqKm58ZaghWj4YtBo', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLBmXKM1eNzac4C8XXSpyLMsNg9p3QWCYD4EumRhs4twK468GbqZwQD7Jyibt94JsWquKAzbzOZjww/132', '0', null, '1', '2019-08-13 17:17:38', '27.10.60.71', '1', '2019-08-13 17:17:38', '2019-08-12 13:08:37');
INSERT INTO `unimall_user` VALUES ('142', null, null, '1', 'oiAuI5PSn6vVeiGLkbTzWkG48aCg', null, 'https://wx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEKTouUvKicGncicuCf2fTvgEZZicmrq1ceU8SQts4ptzuibicCpfUhEZWqO56PBBwa1XSy8Wgj5RvsKu5Q/132', '0', null, '1', '2019-08-12 13:57:31', '27.10.60.71', '1', '2019-08-12 13:57:31', '2019-08-12 13:57:31');
INSERT INTO `unimall_user` VALUES ('143', null, null, '1', 'oiAuI5CMpWaX5zwzEOhNxN0OX0Wk', null, 'https://wx.qlogo.cn/mmopen/vi_32/PiajxSqBRaELOjiaruMGy2r7aIAJPHqzZyBFkwFXjGicduFratTmN3jcumOA0APULMicic8oiaqrVhLdj7vt5G9D4vWw/132', '0', null, '1', '2019-08-12 14:24:19', '27.10.60.71', '1', '2019-08-12 14:24:19', '2019-08-12 14:24:19');
INSERT INTO `unimall_user` VALUES ('144', null, null, '1', 'oiAuI5AR6A99VEeg5GjJJDhkgcoc', null, 'https://wx.qlogo.cn/mmopen/vi_32/2oXVicc8Gxa6PD988uTREticicNG4xMOf31IZlLBqZkqA156rI6VKbt8cnNdLDjKueUfBO8cm3Zp3tchH9Zm3r5fw/132', '0', null, '1', '2019-08-12 14:26:19', '27.10.60.71', '1', '2019-08-12 14:26:19', '2019-08-12 14:26:19');
INSERT INTO `unimall_user` VALUES ('145', null, null, '1', 'oiAuI5FX3ymZgasMH2_CKrlofwJA', null, null, '0', null, '-1', '2019-08-12 14:34:56', '27.10.60.71', '1', '2019-08-12 14:34:57', '2019-08-12 14:34:56');
INSERT INTO `unimall_user` VALUES ('146', null, null, '1', 'oiAuI5JdMzOYmy2N8li-F9VASjvs', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqKUBwnzCnRgxBfISTrsDhn8Fa1qqLuLeP45q3RHkEzK6acW1hZ7NY0jGjLmUgGeMtRCfhzTlPTxA/132', '0', null, '1', '2019-08-12 14:48:21', '27.10.60.71', '1', '2019-08-12 14:48:21', '2019-08-12 14:48:21');
INSERT INTO `unimall_user` VALUES ('147', null, null, '1', 'oiAuI5MOKeAWJtak_aM3JORCWFRc', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIoDibno8LcKQGWE5cpmVsbCszGKYtiakQOjC4nRQ5fOyicNMEION6dyNVibFKvmweOFaLicicTqdIHbnPQ/132', '0', null, '1', '2019-08-12 15:26:15', '27.10.60.71', '1', '2019-08-12 15:26:15', '2019-08-12 15:26:15');
INSERT INTO `unimall_user` VALUES ('148', null, null, '2', 'oRrdQt0_rksYgqoaqMcfJR_-mq2U', '侦探伟', 'http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKqKTSTcsjLwM0scaY4vibg7hSZwia9Uxpa5T5rpbNVd1OPx2SrsKsYdMGvHHKYeYbEHvcDia59jCwFw/132', '0', null, '1', '2019-08-12 15:41:36', '27.10.60.71', '1', '2019-08-12 15:41:36', '2019-08-12 15:41:36');
INSERT INTO `unimall_user` VALUES ('149', null, null, '1', 'oiAuI5Bt_3scNNrFTnD8EMpKNVVI', null, 'https://wx.qlogo.cn/mmopen/vi_32/jIwQI5rXhWcaTpkxza7y14w2AmkmtH3AtVVwDgbA6xzwRTbLlZLCXibFPp2EpZCREVkYxesPqKDdb8CyB3Pl9ibQ/132', '0', null, '1', '2019-08-12 15:44:58', '27.10.60.71', '1', '2019-08-12 15:44:58', '2019-08-12 15:44:58');
INSERT INTO `unimall_user` VALUES ('150', null, null, '1', 'oiAuI5J05LeeQoj70g7N9Ln_zO-Y', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83epYWk0YbuaX6hPVqzGnK7lYNM2dobaYpjB0pNGjibjLGKazWzp4Gibclnib7sxBeqWich7rufuibcoMyUw/132', '0', null, '1', '2019-08-12 17:35:37', '27.10.60.71', '1', '2019-08-12 17:35:37', '2019-08-12 16:50:50');
INSERT INTO `unimall_user` VALUES ('151', '15089145669', '$1$1508914$RUCXP1ma2roex/mG.XQGc0', '0', null, '沉潜', null, '0', null, '2', '2019-08-12 17:35:35', '27.10.60.71', '1', '2019-08-12 17:36:05', '2019-08-12 17:35:18');
INSERT INTO `unimall_user` VALUES ('152', null, null, '1', 'oiAuI5HM-PYlOD75YBce_M07fpFg', null, 'https://wx.qlogo.cn/mmopen/vi_32/E6a5Jd83tCuw5LSb6BTXhonu9ibjddgGhDJ9o1hLTrdb5Qia2KyiaVgFANuSTas9bHia1UdRcQbt2ib5ce3n58Tibtnw/132', '0', null, '1', '2019-08-12 17:48:18', '27.10.60.71', '1', '2019-08-12 17:48:18', '2019-08-12 17:48:10');
INSERT INTO `unimall_user` VALUES ('153', null, null, '1', 'oiAuI5JtwLjKsCJfPR0vpZxFxcTo', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJGp0zryiaomEA1uTxpibHiareV5KmtLiaqZuzwN2SpcEuaLIVLKftGic4DQycAzo6go7rAfx90VxHYLaQ/132', '0', null, '1', '2019-08-12 18:16:31', '27.10.60.71', '1', '2019-08-12 18:16:31', '2019-08-12 18:16:31');
INSERT INTO `unimall_user` VALUES ('154', null, null, '1', 'oiAuI5JCfQKOrxsaHawuDNLFJyT0', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTI0gCTYFc4yabx8XZ4GqA2mnCQfj5UUZMc5wAadTaylOPcYorKFbJSBEhBZsjtlq6hiafjMjvdsWDA/132', '0', null, '1', '2019-08-12 18:21:21', '27.10.60.71', '1', '2019-08-12 18:21:21', '2019-08-12 18:21:21');
INSERT INTO `unimall_user` VALUES ('155', null, null, '1', 'oiAuI5F_v-i585EnjiqcxtdPvViw', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIJghT6ia5AoYj1fnVuSAov8oicvib7X3kMEGq2zffzlZ3r4I7JSgBLTXbQnlRS517W1fOlaBO47MBibQ/132', '0', null, '1', '2019-08-12 18:44:04', '27.10.60.71', '1', '2019-08-12 18:44:04', '2019-08-12 18:44:04');
INSERT INTO `unimall_user` VALUES ('156', null, null, '1', 'oiAuI5OIP6bRNmke0-5820UO-4pY', null, 'https://wx.qlogo.cn/mmopen/vi_32/uha1bGRqPOLWboxtwqI0TgeFAQMAribgerU9uhN2e6OJNPx5EicbGM6euoDHBVALybdybGhS2diaDzHlXSUI1sx2w/132', '0', null, '2', '2019-09-03 17:32:55', '27.10.60.71', '1', '2019-09-03 17:35:44', '2019-08-12 18:51:16');
INSERT INTO `unimall_user` VALUES ('157', null, null, '1', 'oiAuI5Ll4emJnzvYCO0pzqdBQRrg', null, 'https://wx.qlogo.cn/mmopen/vi_32/VO56uDVUJap0aicLesKnwNyJBicVpZb7wSxPH4usoZt5RzUmMq1g953BVa9GrjPAGmkgZKKRdSRAAcJFIAxX6YrQ/132', '0', null, '1', '2019-08-12 18:57:16', '27.10.60.71', '1', '2019-08-12 18:57:16', '2019-08-12 18:57:16');
INSERT INTO `unimall_user` VALUES ('158', null, null, '1', 'oiAuI5DZMaAeLLcNzivvrV3se2Nk', null, 'https://wx.qlogo.cn/mmhead/I38E0TqMh5WQlicwzPhdHHiaHEUKGwVoTFeLTCQ54CXOQ/132', '0', null, '2', '2019-08-12 19:08:04', '27.10.60.71', '1', '2019-08-12 19:09:23', '2019-08-12 19:08:04');
INSERT INTO `unimall_user` VALUES ('159', null, null, '1', 'oiAuI5HxzJ0-_1_GQE-w1DcG1F4k', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLNgTWpN9vEUJX5K7Q4BUNOPX6iaRViaaGypoUZWfzMgfUGo5AG0icl5SpctGGicdbXBBqlekiaHT4nMMg/132', '0', null, '1', '2019-08-12 19:09:57', '27.10.60.71', '1', '2019-08-12 19:09:57', '2019-08-12 19:09:57');
INSERT INTO `unimall_user` VALUES ('160', null, null, '1', 'oiAuI5P0tqlUygBrulp5aE_DWIuQ', null, 'https://wx.qlogo.cn/mmopen/vi_32/z4hFFGreQKcicdCPVLr1Lr5HdNLb66496SibuENQG4JibmA34Nforq6ibstzuTZqDClXicWV2tgXghSibHibtJYCPcnXQ/132', '0', null, '1', '2019-08-12 20:34:04', '27.10.60.71', '1', '2019-08-12 20:34:05', '2019-08-12 20:34:04');
INSERT INTO `unimall_user` VALUES ('161', null, null, '1', 'oiAuI5Ix42gwfAQMxx_7sYMTlQag', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIiaAhIgicZhNicg0osSqc24FwDHWQYZBCZ8mEmGezbBZOR4pANWn8O5BqspIuWNhLyyqWan6jC16Pww/132', '0', null, '1', '2019-08-12 20:44:33', '27.10.60.71', '1', '2019-08-12 20:44:33', '2019-08-12 20:44:33');
INSERT INTO `unimall_user` VALUES ('162', null, null, '1', 'oiAuI5J-4v-gI-egMfuLe3-yXt7E', null, 'https://wx.qlogo.cn/mmopen/vi_32/N9IHq7Qkjq6oorguhJG8YVt1iac7dfYvJV4sOicgiaibCEgU1QBpdHI2TARRrFzd6Umrf61LspaMo0pIfprcW83DZg/132', '0', null, '1', '2019-08-13 08:42:09', '27.10.60.71', '1', '2019-08-13 08:42:09', '2019-08-13 08:42:09');
INSERT INTO `unimall_user` VALUES ('163', null, null, '1', 'oiAuI5Mnwml-4UbVpedWth1wWbi4', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKPw6IFLuyxuS8mkomfQWanoL52m7qL962pPcb130AmoYV7H0POQ7PiauSKCsEBExYgZNqRoBuiaDag/132', '0', null, '1', '2019-08-13 09:01:43', '27.10.60.71', '1', '2019-08-13 09:01:43', '2019-08-13 09:01:43');
INSERT INTO `unimall_user` VALUES ('164', null, null, '1', 'oiAuI5JdT4GBjdOmKhT_vBC_Strs', null, 'https://wx.qlogo.cn/mmopen/vi_32/DK9ibN3ibDbOHaYeicd9Bvtnlnb80T7xUeXMvQ8bL4CWN7qhwUwLnwJejPOENp6lYiaBszSkFyP7og3648mHVicMODw/132', '0', null, '1', '2019-08-13 09:18:28', '27.10.60.71', '1', '2019-08-13 09:18:28', '2019-08-13 09:18:28');
INSERT INTO `unimall_user` VALUES ('165', null, null, '1', 'oiAuI5HyhzPlyLslIcD-N7Z_mJ3E', null, 'https://wx.qlogo.cn/mmopen/vi_32/Ct9OXzjIiaMlZ04FRSpf7xv3UpF35Uf7wmtn9wvicaYk25sSialf77upF1j5xJ2yyDcFlkRTR7XNkib3icK4bCicaVTw/132', '0', null, '1', '2019-08-13 09:23:15', '27.10.60.71', '1', '2019-08-13 09:23:16', '2019-08-13 09:23:15');
INSERT INTO `unimall_user` VALUES ('166', null, null, '1', 'oiAuI5I1atjXvLEmPh6RigyxolZs', null, 'https://wx.qlogo.cn/mmopen/vi_32/mttmyPxrokNhnu4yfICxF6mMRImIIFqaSml1gGMBt8ic3nNUVvTyAqLQHKPZYF6kmNsL5xru0AVMAIkXUSUnmyw/132', '0', null, '1', '2019-08-13 09:24:22', '27.10.60.71', '1', '2019-08-13 09:24:23', '2019-08-13 09:24:22');
INSERT INTO `unimall_user` VALUES ('167', null, null, '1', 'oiAuI5Kh9KfOWOeGFuOoeu8WHK6c', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLlNJFICOnUkcetHbHf8zaEczBM3SPkWwWpGb3l4meCRUsW6v14Z8eicxtQI9nL019yh0Aia1t5NibNQ/132', '0', null, '1', '2019-08-13 10:14:16', '27.10.60.71', '1', '2019-08-13 10:14:16', '2019-08-13 10:14:16');
INSERT INTO `unimall_user` VALUES ('168', null, null, '1', 'oiAuI5CK5aa4mNj-LZFOZS0z-jIg', null, 'https://wx.qlogo.cn/mmopen/vi_32/xicdwbibicicicEiabp7oUgiaNQ0oTTKL1ebsCtVmdXohKptS8icIJWIgGE6AWf7S8vhDs6YhMhLNMMqW3WUqmOEv0nSnw/132', '0', null, '1', '2019-08-13 10:29:56', '27.10.60.71', '1', '2019-08-13 10:29:56', '2019-08-13 10:29:54');
INSERT INTO `unimall_user` VALUES ('169', null, null, '1', 'oiAuI5DmdB61WdtLB47-4g72HSzE', null, 'https://wx.qlogo.cn/mmopen/vi_32/YDnxFnvQiaEqzECh8hcC2PicxqVyOZJlibo4EVG9mWgJiasIiaHw68PoONURC8y4iblYjExbGiaZzmMAlaGUoWOXr41LQ/132', '0', null, '1', '2019-08-13 10:55:02', '27.10.60.71', '1', '2019-08-13 10:55:02', '2019-08-13 10:55:02');
INSERT INTO `unimall_user` VALUES ('170', null, null, '1', 'oiAuI5NEQz10_IfuyOfwygB_LJto', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eooeQ6pDqQDMtlXotkbJficqnj99CKicTicZ8icyp8WkhiaHWjq4r4W4WuZBFJAjSILe4icIzsTdT165C5g/132', '0', null, '1', '2019-08-13 10:57:42', '27.10.60.71', '1', '2019-08-13 10:57:42', '2019-08-13 10:57:42');
INSERT INTO `unimall_user` VALUES ('171', null, null, '1', 'oiAuI5I3zJfHnqjvj-FhFPHCzsWo', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLUnaicnVnYoUILy7t7L3qsmx8pphibmic544OL2dkDTIoZJW1QrkH7BCpylBHfHnWOicanHdqiaYYPscw/132', '0', null, '1', '2019-08-14 16:20:50', '27.10.60.71', '1', '2019-08-14 16:20:51', '2019-08-13 11:36:37');
INSERT INTO `unimall_user` VALUES ('172', null, null, '1', 'oiAuI5AVsEhhjU5gQsXYxmG1ra0c', null, 'https://wx.qlogo.cn/mmhead/DX1nQyXf4GTjCHopwWkMMuAfUwnW0fIPj15IxcHxPA8/132', '0', null, '2', '2019-08-13 11:49:39', '27.10.60.71', '1', '2019-08-13 11:51:00', '2019-08-13 11:49:39');
INSERT INTO `unimall_user` VALUES ('173', null, null, '1', 'oiAuI5CVYqRJeYGPOOtlkh9BEU-s', null, 'https://wx.qlogo.cn/mmopen/vi_32/noJKbw9WCqGZicicK7comWzomibubZSFbENVdldrHx00tHmXlmpGqKykYM8sThQJcMHI8u4ZOacvLvysThIHHBvbA/132', '0', null, '1', '2019-08-13 12:03:25', '27.10.60.71', '1', '2019-08-13 12:03:26', '2019-08-13 12:03:25');
INSERT INTO `unimall_user` VALUES ('174', null, null, '1', 'oiAuI5CZnmUzpUVnr-5w0fQUIQk4', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIRuA15kjYskbo5txHH2nntUAxwzU1r9BkEVoLWGeANphnfibnCElzic3ujhtNVIicpiaEKZ1S68hzkxw/132', '0', null, '1', '2019-08-13 13:12:48', '27.10.60.71', '1', '2019-08-13 13:12:48', '2019-08-13 13:12:48');
INSERT INTO `unimall_user` VALUES ('175', '18661482845', '$1$1866148$YDpiEDY55tT3hEaY66Hux/', '0', null, null, null, '0', null, '-1', '2019-08-13 21:58:35', '27.10.60.71', '1', '2019-08-13 13:20:33', '2019-08-13 13:20:33');
INSERT INTO `unimall_user` VALUES ('176', null, null, '1', 'oiAuI5G0OcKueCUcWiT7ho5R5Ajs', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJ19AR9PJ3cEvfnkwlBia0gtaXpyfFC7l4ZiblhHLUHRTbGypwkXdcuh9Mfls9yG35C0nAupR4XCwRA/132', '0', null, '1', '2019-08-13 13:25:36', '27.10.60.71', '1', '2019-08-13 13:25:37', '2019-08-13 13:25:36');
INSERT INTO `unimall_user` VALUES ('177', null, null, '1', 'oiAuI5Bjjx8rvqxnsjS5J8Fdned4', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLyXh2DCqkOkrXbqpy3vlCmv2bSSASbdl2usX4PPrmNJYvwSSxneNliaxabVjqI2EGe21138iaIvWNA/132', '0', null, '1', '2019-08-13 14:02:06', '27.10.60.71', '1', '2019-08-13 14:02:06', '2019-08-13 14:02:06');
INSERT INTO `unimall_user` VALUES ('178', null, null, '1', 'oiAuI5EqzhFoUTjsrgRPeJg4KW_M', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83ep2r1aUiaA0RfDaMXKIhed9iadI8TVHGuDicCAXCjGlGYYr8fdxGJmkBUFOUnoLLG5Qia7ickeF1A3r3Qg/132', '0', null, '1', '2019-08-13 14:17:01', '27.10.60.71', '1', '2019-08-13 14:17:01', '2019-08-13 14:17:01');
INSERT INTO `unimall_user` VALUES ('179', null, null, '1', 'oiAuI5N6mxEeiY5VYj1kYcWhvkVQ', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIkQibcXZNOtOTWEhmgibampFX3uTbx4OICKw5R3UTu1s9XdCUyZKPgp7CTsNrKCx8IHjJ4k0YKtzZQ/132', '0', null, '1', '2019-08-13 14:28:47', '27.10.60.71', '1', '2019-08-13 14:28:48', '2019-08-13 14:28:47');
INSERT INTO `unimall_user` VALUES ('180', null, null, '1', 'oiAuI5HwIsePi2ONMU87Msn2r4HQ', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJnryDfaYkQh2sJcaQ5o60WhVHRxgkcuzg8uoF3hkicmUw8OtRQVRmiaI0nNcXicQz6eCo1fwAzHLtPA/132', '0', null, '2', '2019-08-13 15:09:17', '27.10.60.71', '1', '2019-08-13 15:09:17', '2019-08-13 15:09:17');
INSERT INTO `unimall_user` VALUES ('181', null, null, '1', 'oiAuI5EHG1sQqhCaNooOVQFqdbI0', null, 'https://wx.qlogo.cn/mmopen/vi_32/BSS6QN63z4nnQJic7BGwqo1vAlune4YeCLAJsb91ItkZBPj4bwFFiaTVibPic2s9Sw1wVCvIUeg6vk9sFyI5EzKE2w/132', '0', null, '1', '2019-08-13 15:30:44', '27.10.60.71', '1', '2019-08-13 15:30:44', '2019-08-13 15:30:44');
INSERT INTO `unimall_user` VALUES ('182', null, null, '1', 'oiAuI5MD5stIFugvBfgKqVVzVGaQ', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLyXh2DCqkOkg5WgtIcyZSFWzich8NzIHvlVDjvPE31NwvfGyw29ibAVNWQjhP31ia8hgnxEN2tyicfSg/132', '0', null, '1', '2019-08-13 16:41:18', '27.10.60.71', '1', '2019-08-13 16:41:18', '2019-08-13 16:41:18');
INSERT INTO `unimall_user` VALUES ('183', null, null, '1', 'oiAuI5MHTmfKzsxctL9sV1H13wyk', null, 'https://wx.qlogo.cn/mmopen/vi_32/9dWfsO0TlWpePxTo0AZicPic1htPibE31BPckVb9LhxCd0jgeFtlaX2JS9sdyia6rjMZNpdm2GQ5pvzYgG1vJ1k3vQ/132', '0', null, '1', '2019-08-13 17:35:48', '27.10.60.71', '1', '2019-08-13 17:35:48', '2019-08-13 17:35:48');
INSERT INTO `unimall_user` VALUES ('184', null, null, '1', 'oiAuI5HsBzgbze7tEvTur2q75Cjc', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoblFC8H7e9pfzNn6eZYnjA7kfYN0C4yWrnMfqJn8iapLVQvLb3xIAx30wd9nZibT0iayqpJ1Av5CRNg/132', '0', null, '1', '2019-08-13 17:47:46', '27.10.60.71', '1', '2019-08-13 17:47:46', '2019-08-13 17:47:46');
INSERT INTO `unimall_user` VALUES ('185', null, null, '1', 'oiAuI5BXvt8Ve55-gIzR_ACv1e0I', null, 'https://wx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEIlQdVoxMCVuC3Q0NWJ1XxeibQq6yic9x5ib0OrTib9YhTIPxXtmT521xRfZSsrEWh5gLko798HkRArxg/132', '0', null, '1', '2019-08-13 18:10:08', '27.10.60.71', '1', '2019-08-13 18:10:08', '2019-08-13 18:10:08');
INSERT INTO `unimall_user` VALUES ('186', null, null, '1', 'oiAuI5NvZ1jZG4lmqIcr1VIoGsaE', null, 'https://wx.qlogo.cn/mmhead/LfzQuia77veeojJpycDYPicflevWiaHwpKOzgx6E4bnNok/132', '0', null, '2', '2019-08-14 11:02:33', '27.10.60.71', '1', '2019-08-14 11:03:52', '2019-08-14 11:02:33');
INSERT INTO `unimall_user` VALUES ('187', null, null, '1', 'oiAuI5Jj9B_DldPRk70_cz2w3_mc', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoTbcLy18Xry1wicKS6SVAUI3h8NXGgsEjvxr3V6dEbSlg5obI3iaibNJC0uribHJbmRssibE9JhtMqAQA/132', '0', null, '1', '2019-08-14 11:25:38', '27.10.60.71', '1', '2019-08-14 11:25:38', '2019-08-14 11:25:38');
INSERT INTO `unimall_user` VALUES ('188', null, null, '1', 'oiAuI5BGVcB-hWarMmtfk5BrGYso', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eozlUrKicenvYgPIusLGZvhhJQbg1via5DWxhtibia31rZGT5grtwFy2OV2ltnhr4f6HpIdOx77mtscjA/132', '0', null, '1', '2019-08-14 11:35:24', '27.10.60.71', '1', '2019-08-14 11:35:24', '2019-08-14 11:35:24');
INSERT INTO `unimall_user` VALUES ('189', null, null, '1', 'oiAuI5LbYMHlsNngW1M2Gys09t2w', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eo5HP1ibenk8NvGKBduPbp4CiayGX0MZ6dOvcWc2kOl4gZbu7sKqO0O7EsGWjRzy7NZWyVrxdztlTHA/132', '0', null, '1', '2019-08-14 14:14:30', '27.10.60.71', '1', '2019-08-14 14:14:30', '2019-08-14 14:14:30');
INSERT INTO `unimall_user` VALUES ('190', null, null, '2', 'osTQe6M2V_R5WhrntBx3EIgTxLTQ', '鱼小鱼', 'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eocWYCsRSrNfCF4hIDTicwxXn1HulIOrmQ23bPuvnfSD5bxpwo8LGaMJGfIkavJga7zAp9SGdy8UDg/132', '0', null, '1', '2019-08-14 14:35:36', '27.10.60.71', '1', '2019-08-14 14:35:36', '2019-08-14 14:35:36');
INSERT INTO `unimall_user` VALUES ('191', null, null, '1', 'oiAuI5BBS_Bcu41JvKj4qwD-a3Ac', null, 'https://wx.qlogo.cn/mmopen/vi_32/cibteupBCW1AxfQCkiaNas83nQarglMthuiaLkLg0ecaIjibXY18bV0O1zG27fvbohQqlWkia6BVpyJ2X2gEFe3hnFA/132', '0', null, '1', '2019-08-14 14:52:10', '27.10.60.71', '1', '2019-08-14 14:52:11', '2019-08-14 14:52:10');
INSERT INTO `unimall_user` VALUES ('192', null, null, '1', 'oiAuI5GShAP6SVYkoaBUwffaNW0E', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eprZkiagDicBT0Px8mAWAwKWKPOAw2TVwCxqOV33y8frSKthnwlsd6qnpywZp2ib5cp9M30tWaFt2PTA/132', '0', null, '1', '2019-08-14 15:58:09', '27.10.60.71', '1', '2019-08-14 15:58:09', '2019-08-14 15:58:09');
INSERT INTO `unimall_user` VALUES ('193', null, null, '1', 'oiAuI5K9kCtFqWLPpErAUPOdFduE', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTI39XReVrENhYK0icuibsk3y5QdwDsjNjicYzeKhJH37AOm0yByTjQ2iaic5wvbOlpHmScsrzibTARlndEA/132', '0', null, '1', '2019-08-14 16:44:40', '27.10.60.71', '1', '2019-08-14 16:44:40', '2019-08-14 16:44:40');
INSERT INTO `unimall_user` VALUES ('194', null, null, '1', 'oiAuI5GY5rxkfWkjwX8jZXGLCm_M', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLZwicQJiby5pMNvynuictwMiblKLqAx18HibR7my7bg208Gvp94gKqlRxKhItkFz5iawyo3RxgITs9pwPA/132', '0', null, '1', '2019-08-14 18:31:36', '27.10.60.71', '1', '2019-08-14 18:31:37', '2019-08-14 18:31:36');
INSERT INTO `unimall_user` VALUES ('195', null, null, '1', 'oiAuI5NT3FMfKIwGAnIRT8Oqz7w8', null, 'https://wx.qlogo.cn/mmopen/vi_32/R7GWPhB1Rjf5BlsNib9Jry98ltLT2YtzicoRtIBMiaiciav5GZVnElYmGIAKtwlviaYUCFmKa1iaZ0z7WAYe50vJsU00Q/132', '0', null, '1', '2019-08-30 23:38:59', '27.10.60.71', '1', '2019-08-30 23:39:00', '2019-08-15 04:19:14');
INSERT INTO `unimall_user` VALUES ('196', null, null, '1', 'oiAuI5EAefe_h2JUMrmBro95aVso', null, null, '0', null, '-1', '2019-08-15 16:48:32', '27.10.60.71', '1', '2019-08-15 16:48:32', '2019-08-15 16:48:32');
INSERT INTO `unimall_user` VALUES ('197', null, null, '1', 'oiAuI5KRbQfMsg-1YccfcH8NX5LE', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJzqGvQKictU9E03RgROooC3HVf99yM7eRb8oSo6TJVjTouibT7W2K1ZPw6lfDh6J6iatxQhKQfqHNwQ/132', '0', null, '1', '2019-08-21 18:54:16', '27.10.60.71', '1', '2019-08-21 18:54:17', '2019-08-15 17:05:43');
INSERT INTO `unimall_user` VALUES ('198', null, null, '1', 'oiAuI5MbfPjj1JlNtmgEhhRjGNB0', null, 'https://wx.qlogo.cn/mmopen/vi_32/G7pgyw0Tn41OD0N4KrfWmmTDMslZTBP64VgVpKNoPsdgLEPZ1HaEUp4fTbUe6yomynLr06kC6EIVOd75oH3uBw/132', '0', null, '1', '2019-08-15 20:04:25', '27.10.60.71', '1', '2019-08-15 20:04:25', '2019-08-15 20:04:25');
INSERT INTO `unimall_user` VALUES ('199', null, null, '1', 'oiAuI5HaNiatX6g_5V3dgw_b_WM4', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKUqQficnsgtwiaCx1OgCxVTJO7Tic3LsOrtBmHRq03A1B5ODgxkENo0Vl1wkD4LGuRuoPnRpDQfAN1A/132', '0', null, '1', '2019-08-15 20:12:23', '27.10.60.71', '1', '2019-08-15 20:12:23', '2019-08-15 20:12:23');
INSERT INTO `unimall_user` VALUES ('200', null, null, '1', 'oiAuI5HV4Mcnkvtj0L8zwTYl1Mfc', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eo6WkTIkvH8DVf5C5L3UpsWpmzf2dUJ6ScGycibpCzcqbHwgwXCp5sXVE1SOnLibtb6xZLf4Aicdjqdw/132', '0', null, '1', '2019-08-15 20:41:50', '27.10.60.71', '1', '2019-08-15 20:41:51', '2019-08-15 20:41:50');
INSERT INTO `unimall_user` VALUES ('201', null, null, '1', 'oiAuI5Lp7Z-Jrx0la-AfIEKm8CD0', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIqKadiapSpUtdB5b6FiavMNAuEJORkJEo8gNUnZ5cq2x5D5DFcSDV8biauzkOwW3IDW5y5UIVmicxUIg/132', '0', null, '1', '2019-09-15 16:28:44', '27.10.60.71', '1', '2019-09-15 16:28:44', '2019-08-15 23:04:07');
INSERT INTO `unimall_user` VALUES ('202', null, null, '1', 'oiAuI5AMwzSJd3qNHr-ldyLG6F40', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJTgNpkSibNiaDxbkRDiaN5clUIGFVCFOQYptBNoBzTmM3ehjvuwqOOZmdlbJaMPBKjML7hrZ3j3l4mA/132', '0', null, '1', '2019-08-15 23:24:11', '27.10.60.71', '1', '2019-08-15 23:24:11', '2019-08-15 23:24:11');
INSERT INTO `unimall_user` VALUES ('203', null, null, '1', 'oiAuI5KxYUZAPozuOIWoE52HOLzo', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLdJRI1tJBvFrko5xK5VSeJIMNxnewdDr3T47r7TdQ5UxPiaQPYkeKFK6t9SjRZLKHSzdlxF0hxcwg/132', '0', null, '1', '2019-08-21 14:32:12', '27.10.60.71', '1', '2019-08-21 14:32:12', '2019-08-16 08:19:15');
INSERT INTO `unimall_user` VALUES ('204', null, null, '1', 'oiAuI5Peuohs0fih4fq0aSUg0b3k', null, 'https://wx.qlogo.cn/mmopen/vi_32/ajNVdqHZLLCYBbeznqibTdeuYo9RbQA8fIdz2icsmMzicJwia73kVkSb0pFMz0BWcQz1gGDAm52JfgdHp2NMlLibxyQ/132', '0', null, '1', '2019-08-16 10:58:34', '27.10.60.71', '1', '2019-08-16 10:58:35', '2019-08-16 10:58:34');
INSERT INTO `unimall_user` VALUES ('205', null, null, '1', 'oiAuI5BWVbUUEjSu4xw_3_PqWp0Y', 'S', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIT0yX4KniaW8ONIfiaNFPBR4nRpjEflzicUNLNzGr1b0oUZknOA3SbRoTwiclAoQXriaak7IRxgRnC6EQ/132', '0', null, '2', '2019-08-16 11:02:55', '27.10.60.71', '1', '2019-08-16 11:03:32', '2019-08-16 11:02:55');
INSERT INTO `unimall_user` VALUES ('206', null, null, '1', 'oiAuI5LdvXLSuHhXFeRn7MY2KonE', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eo9zHr9c3uxCwOz5CasdH9l8qmKKrQxm2RZ8atLMibJKD0blGhKbKMHiaicibkYDEyicgTWYJYxUt6gczA/132', '0', null, '1', '2019-08-16 11:36:06', '27.10.60.71', '1', '2019-08-16 11:36:06', '2019-08-16 11:36:06');
INSERT INTO `unimall_user` VALUES ('207', null, null, '1', 'oiAuI5NOgC2PfdeaLxxr1i3Gw3a0', null, 'https://wx.qlogo.cn/mmopen/vi_32/iaSUDHup94R6jx7m9nJDzkeqsRmvdHn7icX8FzfIa5vHnic4LYQaPpedCffMkCMWrXicSX3duD7XDhcD8oWXA3AlBA/132', '0', null, '1', '2019-08-16 11:40:36', '27.10.60.71', '1', '2019-08-16 11:40:36', '2019-08-16 11:40:36');
INSERT INTO `unimall_user` VALUES ('208', null, null, '1', 'oiAuI5PjKQ47YFe39zYXcvmLh8nw', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83epBk3f82qmiaJFrqJ2gk6zEKUUicSqLwBaMf3LJx5QNYOTTiaaZp6SCNanEBucmSAkLEiag2rXyyDzzfg/132', '0', null, '1', '2019-08-16 13:14:01', '27.10.60.71', '1', '2019-08-16 13:14:01', '2019-08-16 13:14:01');
INSERT INTO `unimall_user` VALUES ('209', null, null, '1', 'oiAuI5L80qCZfU2HwFsyfV6trpUo', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eo0gXWnxEwHMXCX7FHficHgHSN0ialWp4gSuGibicTJhmB4ZZYIp9OQYSQGH6csNEiczmH934sLhEG534w/132', '0', null, '1', '2019-09-07 18:11:55', '27.10.60.71', '1', '2019-09-07 18:11:55', '2019-08-16 13:14:32');
INSERT INTO `unimall_user` VALUES ('210', null, null, '1', 'oiAuI5DkbzWFY_YfXcw_2orKFUck', null, 'https://wx.qlogo.cn/mmopen/vi_32/1icMFIe8D2Mjg3Qbd9bCJYxqYwwyC6Obl6Ktrxvxa5SC7h34jskJhTNiaALlaGT2jt9Fj1OiceT14l1bp0fqV4oQg/132', '0', null, '1', '2019-08-16 13:15:41', '27.10.60.71', '1', '2019-08-16 13:15:41', '2019-08-16 13:15:41');
INSERT INTO `unimall_user` VALUES ('211', null, null, '1', 'oiAuI5AweVjP6dzWDe_mobmut_Ns', null, 'https://wx.qlogo.cn/mmopen/vi_32/0XibgeBOwhKhb2x6IjOYKwRdfvxQiaAjhz4P97wGfwn1zKntePW9icJEtW0GC7TykpvIViccMNTXtfBTJia6TicD6RPw/132', '0', null, '1', '2019-08-16 13:39:27', '27.10.60.71', '1', '2019-08-16 13:39:28', '2019-08-16 13:39:27');
INSERT INTO `unimall_user` VALUES ('212', null, null, '1', 'oiAuI5HrDkT0b9swCHpvhK-ODtQY', null, 'https://wx.qlogo.cn/mmopen/vi_32/ZdHZ8vlDjCppdjX5XjgmDTHe4DLq5ibTgo61F0tgfJQ1HaOvuqMtKibj9HmkZKfEIj66Wyt0FF9D0RTLvGeiaEGxw/132', '0', null, '1', '2019-08-16 13:58:47', '27.10.60.71', '1', '2019-08-16 13:58:48', '2019-08-16 13:58:47');
INSERT INTO `unimall_user` VALUES ('213', null, null, '1', 'oiAuI5DPZH8MLb87vjMmNmQMn8i0', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKsAVRZbzCtrOagDBAvJaAiaz4JgQeaiaY5c1EMbIHLgczPXPZj6nBO2eiau0QHl3JFBciaYCml1OczTg/132', '0', null, '1', '2019-08-16 14:04:15', '27.10.60.71', '1', '2019-08-16 14:04:15', '2019-08-16 14:04:15');
INSERT INTO `unimall_user` VALUES ('214', null, null, '1', 'oiAuI5PpMggw5yOQBI2Y_KGJqOXM', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoibUyT6aTmlpfb0DEq3diaFduKJfIHcyUzbII2eGVvNGNNBoy7bNCbRpn0ZWusXmO8YMcLaB0PPf2w/132', '0', null, '1', '2019-08-20 11:07:22', '27.10.60.71', '1', '2019-08-20 11:07:22', '2019-08-16 14:44:59');
INSERT INTO `unimall_user` VALUES ('215', null, null, '1', 'oiAuI5HO_T9jTxbkoNwW2zuLxeUc', null, 'https://wx.qlogo.cn/mmopen/vi_32/YEYfloe7S4BSnSARHqojtric6J1WnP2iaQ2ibQicl7Nr3aPz1d9ib3sGAjakC3YaRkTP60TJPFVKLia9tibUjB3gOwicdA/132', '0', null, '1', '2019-09-05 22:57:18', '27.10.60.71', '1', '2019-09-05 22:57:19', '2019-08-16 16:14:54');
INSERT INTO `unimall_user` VALUES ('216', null, null, '1', 'oiAuI5PmNlPznA5dsIpk6swm8Xq8', null, 'https://wx.qlogo.cn/mmopen/vi_32/ajNVdqHZLLBKQ06JWbUicWRsdxNEPAiaTGVUGBeTuVhWHp8HL0FpKBrwbGCaia1ibWGZNNubYPWgHjehIR20sEFWmA/132', '0', null, '1', '2019-09-17 14:43:45', '27.10.60.71', '1', '2019-09-17 14:43:46', '2019-08-16 16:35:52');
INSERT INTO `unimall_user` VALUES ('217', null, null, '1', 'oiAuI5Bil896YB8-u_MO_3avVnBE', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKmLlP4j7HgfM9TzAv1MTZeI1f21QqKys7wsMOes6PSbFhFhAIXlm8Inb3wToVtnsZAr3hJFaLP4w/132', '0', null, '1', '2019-08-16 16:37:10', '27.10.60.71', '1', '2019-08-16 16:37:11', '2019-08-16 16:37:10');
INSERT INTO `unimall_user` VALUES ('218', null, null, '1', 'oiAuI5JefPQPs6yZv7pw3mieD4RU', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIBC94guaFqiaTrpC1rQocDficoECiaQ4eVVa9aVBgsQQvNkEHcGfxRKdIvyIH68iaZkzXraD7jJe7ZaA/132', '0', null, '1', '2019-08-16 17:15:31', '27.10.60.71', '1', '2019-08-16 17:15:31', '2019-08-16 17:15:31');
INSERT INTO `unimall_user` VALUES ('219', null, null, '1', 'oiAuI5NxpZreknXLsp5OuOpMGsZE', null, 'https://wx.qlogo.cn/mmopen/vi_32/LyZic27YhiaGYWUkic2QQ2WMuHO56xPQLanGicTt8b60iagw7jDibia46sk9U2UfWJdc61bic5mMTriaIkEuBMjiajaxoXEA/132', '0', null, '1', '2019-08-16 17:25:35', '27.10.60.71', '1', '2019-08-16 17:25:35', '2019-08-16 17:25:35');
INSERT INTO `unimall_user` VALUES ('220', null, null, '1', 'oiAuI5NqhNvfh01l1_UmhW54LIEU', null, null, '0', null, '-1', '2019-08-16 18:06:36', '27.10.60.71', '1', '2019-08-16 18:06:36', '2019-08-16 18:06:36');
INSERT INTO `unimall_user` VALUES ('221', null, null, '1', 'oiAuI5GYnpQ0hO3vR4fhto3XVKwY', null, 'https://wx.qlogo.cn/mmopen/vi_32/cb6tcs4tfPOETnuw5TZa2sM8M1SJZibTzGhB2IvuLbaGYiblz9icJ1Tvwx8mSEk20xHsaT33wSecY2Odkk9l9ZWiaw/132', '0', null, '1', '2019-08-16 19:23:29', '27.10.60.71', '1', '2019-08-16 19:23:29', '2019-08-16 19:23:29');
INSERT INTO `unimall_user` VALUES ('222', null, null, '1', 'oiAuI5NrNUbULrdixfMNO1-EYeMQ', null, 'https://wx.qlogo.cn/mmopen/vi_32/WlHqRUJ4EaZhCic3gDha5RicNDwkGX38q5SkzkAsLHhbhkdQ399ibe7kGiaB3xwzpCtMc1PY3TP7mFBRiazDBDGJb1Q/132', '0', null, '1', '2019-08-16 20:49:00', '27.10.60.71', '1', '2019-08-16 20:49:00', '2019-08-16 20:49:00');
INSERT INTO `unimall_user` VALUES ('223', null, null, '1', 'oiAuI5Jh2Gmh-uITqaDxJU077Crk', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKe3zMdArrlt2cz6qVo0CmPAMafTjjZKja4qB6uiaickBMn0y6zrZaRKn0Tib6n4KicNLQZicPMIv1BL5A/132', '0', null, '1', '2019-08-17 11:57:17', '27.10.60.71', '1', '2019-08-17 11:57:17', '2019-08-17 11:57:17');
INSERT INTO `unimall_user` VALUES ('224', null, null, '1', 'oiAuI5Ld8pQMfFIyhZ-pZ9dqqgNo', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoq7m8RwcAXytcJ7ng4YHfialOn75SPmHrVnfPJiaeH3sj8JzjVOibc2aczyE4oj6GeLb21ddguBkKicw/132', '0', null, '1', '2019-09-09 16:22:29', '27.10.60.71', '1', '2019-09-09 16:22:29', '2019-08-17 12:00:04');
INSERT INTO `unimall_user` VALUES ('225', '15208554142', '$1$1520855$Vl6Mj0Mp0d/plHAlWiFUt0', '0', null, null, null, '0', null, '-1', '2019-08-17 12:15:13', '27.10.60.71', '1', '2019-08-17 12:14:52', '2019-08-17 12:14:52');
INSERT INTO `unimall_user` VALUES ('226', null, null, '1', 'oiAuI5CBzr9OX6rl903xvxB2doE0', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIfEVwcGQd8VF6UIyLvbJI61zzoGPtWpf4MOrCKdTULiatR1icTZEqH6ScHSO6C893T7aFEZviahlQaw/132', '0', null, '1', '2019-08-17 19:40:11', '27.10.60.71', '1', '2019-08-17 19:40:11', '2019-08-17 12:32:34');
INSERT INTO `unimall_user` VALUES ('227', null, null, '1', 'oiAuI5EWCtkXueUKAkScpzXDPa1w', null, 'https://wx.qlogo.cn/mmopen/vi_32/mPYePe5UoZTicmia3k4Wpht3l9Pq6zx0ePic7EQdO3mvuXDeqvjTOZn3WRsb5mAWlticYJK89QurmIpDt14QJUHPHg/132', '0', null, '1', '2019-08-17 13:26:14', '27.10.60.71', '1', '2019-08-17 13:26:14', '2019-08-17 13:26:14');
INSERT INTO `unimall_user` VALUES ('228', null, null, '1', 'oiAuI5CfF4lQ1f4-ddtpxYMQzCFg', null, 'https://wx.qlogo.cn/mmopen/vi_32/Xgm9g9tBXyWRlT7WlcvMB9rsIGib3Y2pDyyn7nudzWHscZx3jcbAnOQ25iawBsnjnQ6twzu7ktNcXTlPmlM8mZew/132', '0', null, '1', '2019-08-20 12:17:50', '27.10.60.71', '1', '2019-08-20 12:17:50', '2019-08-17 13:45:20');
INSERT INTO `unimall_user` VALUES ('229', null, null, '1', 'oiAuI5ASFJ-2bBH0l6tE_7UJDkJ4', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJ947XOoIWPCZM7CtsENNvI1B3pz3SwFibbtpicmvjy2Wkq2PCq07HiaiazzlGg7ialvH2icUIuEOUE5tXA/132', '0', null, '1', '2019-08-22 10:49:33', '27.10.60.71', '1', '2019-08-22 10:49:34', '2019-08-17 14:34:13');
INSERT INTO `unimall_user` VALUES ('230', null, null, '1', 'oiAuI5Ov1vmOWrriIX3y-HkfZKlk', null, 'https://wx.qlogo.cn/mmopen/vi_32/DvEufIvl0FHFBISPhXuVbbzZGzmpRe1qMUNMpMupXFmwtxFBpg7vpX8zibRnCFZMNNfNm20IfmoUlia1vVkQPeZg/132', '0', null, '1', '2019-08-17 15:00:12', '27.10.60.71', '1', '2019-08-17 15:00:13', '2019-08-17 15:00:12');
INSERT INTO `unimall_user` VALUES ('231', null, null, '1', 'oiAuI5MYpeMPc8fUnR9_NBv187FQ', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eq4QQ4grZf88hcIHnQeQtnOC4xUetvZ6L3Y3Bmp57Yx9bN4B85pJwibsDoCpjcb4T5urM8r2z4g7icA/132', '0', null, '1', '2019-08-20 10:36:32', '27.10.60.71', '1', '2019-08-20 10:36:32', '2019-08-17 15:51:38');
INSERT INTO `unimall_user` VALUES ('232', null, null, '1', 'oiAuI5FCvgzpKHFnVQM92WVE5bF4', null, 'https://wx.qlogo.cn/mmopen/vi_32/7vDSZ8ibRgXBSFcIW4n9A9Dfwx9h1ByulHibCznZcjh21RsmHIhyN87zFhvZ7JtVh1KH7SZBibUtDI8YMydUJt86A/132', '0', null, '1', '2019-08-17 16:07:20', '27.10.60.71', '1', '2019-08-17 16:07:21', '2019-08-17 16:07:20');
INSERT INTO `unimall_user` VALUES ('233', null, null, '1', 'oiAuI5J9MYQBo8iB7945xG8wlJXE', null, 'https://wx.qlogo.cn/mmopen/vi_32/zP0hUKFyB2SzHdvSMv0OLoTfph4mHva0Efn1phKkFZv9P9OUFBE8jwFMynP9DUrPpvUliclEpZ99VzxQfGUDBOg/132', '0', null, '1', '2019-08-17 17:33:35', '27.10.60.71', '1', '2019-08-17 17:33:36', '2019-08-17 17:33:34');
INSERT INTO `unimall_user` VALUES ('234', null, null, '1', 'oiAuI5Py-FsbQqUMtcl5uGi_nxlw', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqQzsPhicSPCqFibYwibbERBg1F0jseJsgSmBaYQgEKPerSdSW9EmCpAGt710ABz3Qp8b90WBegfIt7w/132', '0', null, '1', '2019-08-17 17:50:16', '27.10.60.71', '1', '2019-08-17 17:50:16', '2019-08-17 17:50:16');
INSERT INTO `unimall_user` VALUES ('235', null, null, '1', 'oiAuI5EvW3MCL78g-Kp0TDUCEAxE', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83er2BibpcIRrkrdlVoib084wLTGtR67xnGFYD080Sqtbrtd9cad3rtqvC7VicSU8ibrfZIADxx0Fj9Q7Fw/132', '0', null, '1', '2019-08-17 19:39:17', '27.10.60.71', '1', '2019-08-17 19:39:17', '2019-08-17 19:39:17');
INSERT INTO `unimall_user` VALUES ('236', null, null, '1', 'oiAuI5M81UmexkJuhKw4Cg-SLNg4', '哈哈', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKAyfsLlZkYjbiar4icJfRGBHwzSS0iagWwyYiayg8fnfV4EfRZicNlxe5pxYEMic3O8gSQ4HOREBcoe7vQ/132', '0', null, '2', '2019-08-17 23:35:25', '27.10.60.71', '1', '2019-08-17 23:35:38', '2019-08-17 23:31:27');
INSERT INTO `unimall_user` VALUES ('237', null, null, '1', 'oiAuI5DviE8y3YjwYKx8xvdRtRmo', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKibGtIdiaibXCvCM3wKHsOqqiavSnztAKvpzMAqbMOTWKkyE8Syg5YJxmPW09urots1LC2ibSgYrygg3A/132', '0', null, '1', '2019-08-19 14:25:25', '27.10.60.71', '1', '2019-08-19 14:25:25', '2019-08-18 08:32:26');
INSERT INTO `unimall_user` VALUES ('238', null, null, '1', 'oiAuI5HvAFIgU9rPZWZ7Cm2FOyrA', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLdJRI1tJBvFoca1FL5KOHiburvicm2fy2pSFBwrSfiaybAlVLXwag4hR1ckpibJctaEDC01DajiaxHdAQ/132', '0', null, '1', '2019-08-18 09:56:31', '27.10.60.71', '1', '2019-08-18 09:56:32', '2019-08-18 09:56:31');
INSERT INTO `unimall_user` VALUES ('239', null, null, '1', 'oiAuI5N72g4MGTHVBti6ekcstDsE', null, 'https://wx.qlogo.cn/mmopen/vi_32/EcGGlpicPY9dicsTLzX0H78QcXgREFOPRSosCzrDVnFibe26eK1YS4A80fCjYNHe0zjOnfHtJpOetPfhH3hiaY2ZhA/132', '0', null, '1', '2019-08-18 10:18:56', '27.10.60.71', '1', '2019-08-18 10:18:56', '2019-08-18 10:18:56');
INSERT INTO `unimall_user` VALUES ('240', null, null, '1', 'oiAuI5CChsEdm_CUFEicQ0kMNoTk', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83epNVjeMoibVTGaTqibaXOMhmJbDwiaIp8zicvre5zW4qkdXWF1GktPDVU1PSF1icPWeiaP0nbs0vUn7QNWw/132', '0', null, '1', '2019-08-18 16:12:08', '27.10.60.71', '1', '2019-08-18 16:12:08', '2019-08-18 16:12:08');
INSERT INTO `unimall_user` VALUES ('241', null, null, '1', 'oiAuI5JmkiI1t9LvSrJHGnuKpDjU', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83epzC7O4jAY8R4NCYcDEtYTQzqEcTRyYiawuo3xrRI2vAXACtAqlPIYB7yBvFS2UOpnzXQjQa9SYHaA/132', '0', null, '1', '2019-08-19 11:33:51', '27.10.60.71', '1', '2019-08-19 11:33:51', '2019-08-19 10:08:53');
INSERT INTO `unimall_user` VALUES ('242', '13787797262', '$1$1378779$8WOotQD6MHQ63Yp9nTNmG0', '0', null, null, null, '0', null, '-1', '2019-08-19 11:48:40', '27.10.60.71', '1', '2019-08-19 11:48:40', '2019-08-19 11:48:40');
INSERT INTO `unimall_user` VALUES ('243', '17628068372', '$1$1762806$a.pB0YOb5cQvRI7oghduh0', '0', null, null, null, '0', null, '-1', '2019-08-19 12:01:11', '27.10.60.71', '1', '2019-08-19 12:01:11', '2019-08-19 12:01:11');
INSERT INTO `unimall_user` VALUES ('244', null, null, '1', 'oiAuI5NCCG-MNoCMicRSp-n2iyvE', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKwQZP8iblibztpLoKwuYXm62TtKh8tmy7aV7UZbIyKKySfQkZX0tvXliaxLTart7hqesvJqpjfMRibQw/132', '0', null, '1', '2019-08-19 17:33:36', '27.10.60.71', '1', '2019-08-19 17:33:36', '2019-08-19 16:52:21');
INSERT INTO `unimall_user` VALUES ('245', null, null, '1', 'oiAuI5CxL45OLFt9CPdJYfcyRqqo', null, 'https://wx.qlogo.cn/mmopen/vi_32/JG0Mw8gJUYZGBP7fv0Ep2DtIJDK3CdiavwqUiat7wL1Ax2kG8MWsyeZBRaib4hBbW7Jia54p5nFzz7ibdIeTeTkHh7g/132', '0', null, '1', '2019-08-19 17:34:18', '27.10.60.71', '1', '2019-08-19 17:34:18', '2019-08-19 17:34:18');
INSERT INTO `unimall_user` VALUES ('246', null, null, '1', 'oiAuI5D3w5__26cdkvLIIt-BSNMc', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLsThiaOiaRYUOTia5x6ibsFho9Cfia7uxDgBAibfAURib0LynqDzWhNnMbAYQHJH6fEcX2opmcSBOEXUkxg/132', '0', null, '1', '2019-08-19 21:48:39', '27.10.60.71', '1', '2019-08-19 21:48:39', '2019-08-19 21:48:39');
INSERT INTO `unimall_user` VALUES ('247', null, null, '1', 'oiAuI5DJS5KgYqMbAwhyPCV2azE0', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJ9OkEE4hRbaWO81Y4icyP28sX9H5TpR751iazTSgrT01jxlPsVzNXFcW4d9InDELibLqMycBXwlEulQ/132', '0', null, '1', '2019-08-19 21:53:01', '27.10.60.71', '1', '2019-08-19 21:53:01', '2019-08-19 21:53:01');
INSERT INTO `unimall_user` VALUES ('248', null, null, '1', 'oiAuI5H6PXsIbS1Hmu9Y1Vl3Pa7E', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJrLGicyVXia0WHAnU160AiabAhwLE4c4DrMw7x2rQYdQ2sskU8YKNfRzIlho4CzYW0NQFAxR48kAbicQ/132', '0', null, '1', '2019-08-19 23:15:29', '27.10.60.71', '1', '2019-08-19 23:15:30', '2019-08-19 23:15:29');
INSERT INTO `unimall_user` VALUES ('249', null, null, '1', 'oiAuI5DfntQWC5SZ_euWB2poWk5U', null, 'https://wx.qlogo.cn/mmopen/vi_32/PiajxSqBRaELJHZxSR7s46eqE7LqF1GvtfevticsCOkLBkddKVkf9FT0unolDNGuic5wqhHicDnVFtucStKTmLSYjQ/132', '0', null, '1', '2019-08-20 10:35:32', '27.10.60.71', '1', '2019-08-20 10:35:32', '2019-08-20 10:35:32');
INSERT INTO `unimall_user` VALUES ('250', null, null, '1', 'oiAuI5OVfLuYVZcIjP6IaGIoHjt0', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKOiciah00cnjKok2OKaFMdlcguUZmWspGalNibz80H3klJ1x5ibXa77Fopt5aibO6ibz1iauyctrHdkfh5w/132', '0', null, '1', '2019-08-20 15:57:34', '27.10.60.71', '1', '2019-08-20 15:57:34', '2019-08-20 15:57:34');
INSERT INTO `unimall_user` VALUES ('251', '17779152309', '$1$1777915$neGBC9KwMoFMA0VxzgJn70', '0', null, '康康', null, '0', null, '1', '2019-08-20 18:51:40', '27.10.60.71', '1', '2019-08-20 18:52:49', '2019-08-20 18:51:24');
INSERT INTO `unimall_user` VALUES ('252', null, null, '1', 'oiAuI5JATeDID5KdCJy8piDDtOds', null, 'https://wx.qlogo.cn/mmopen/vi_32/iaadC5T6yx7bd4wVRdaySs0iaD8zUBFBYUMKvTgZEdCZmZ7wicgEsvncOcZoWQpDjaTP90l5QT0LBiavibrlye4Um9A/132', '0', null, '1', '2019-08-20 19:00:50', '27.10.60.71', '1', '2019-08-20 19:00:50', '2019-08-20 19:00:50');
INSERT INTO `unimall_user` VALUES ('253', null, null, '1', 'oiAuI5Fu5VmKPtyPTHojyhDeXVMI', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLne5ySsv7slUytIPs3AyS41niaFhpXwic0P1NhlJJFDjbIIcL9Cc0CtmVTY6ocTgycDUn1Via0hveeQ/132', '0', null, '1', '2019-08-21 05:27:51', '27.10.60.71', '1', '2019-08-21 05:27:51', '2019-08-21 05:27:51');
INSERT INTO `unimall_user` VALUES ('254', null, null, '1', 'oiAuI5I9_Y6ZOOIv0EINaBtll2rs', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83erzokP3SdGQz9QgStgQM06icrKicmiaJfj2HOIgvKeX6fjMiczfNgB7oXwFeSRAUfHwPgCI0x8hqTgibAQ/132', '0', null, '1', '2019-08-21 10:51:01', '27.10.60.71', '1', '2019-08-21 10:51:02', '2019-08-21 10:51:01');
INSERT INTO `unimall_user` VALUES ('255', null, null, '1', 'oiAuI5K433leGM31Cs1CgtcPXv00', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83epoucTYIjUiaspgYJcWHRJudyxF5srXrp887gVy7bF6A4hudzYf1hG6kX4rNBuSVkpHdDTjspvYXRQ/132', '0', null, '1', '2019-08-21 11:42:46', '27.10.60.71', '1', '2019-08-21 11:42:46', '2019-08-21 11:42:46');
INSERT INTO `unimall_user` VALUES ('256', null, null, '1', 'oiAuI5MmcJLDw2pNCcRUgOd48xhY', null, 'https://wx.qlogo.cn/mmopen/vi_32/oMWNuowMHMHStpvEyJ27OB3WM8FZe2hxqvUJQTibNw75vATcBf1C0q6Dox8maKRpKSQv7981s3ibgOcuhibJgEgtw/132', '0', null, '1', '2019-08-21 14:00:37', '27.10.60.71', '1', '2019-08-21 14:00:37', '2019-08-21 14:00:37');
INSERT INTO `unimall_user` VALUES ('257', null, null, '1', 'oiAuI5BLqRn1dN1HY8ksLkxvjvNA', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqM9jPTDf48bcLXAOicuwQiar7jzibmwAoHJlVJFiboicxImDXDjKz9pmRpxicU2lb9wnlaMU1TmntNN7vA/132', '0', null, '1', '2019-08-21 17:04:41', '27.10.60.71', '1', '2019-08-21 17:04:42', '2019-08-21 17:04:41');
INSERT INTO `unimall_user` VALUES ('258', null, null, '1', 'oiAuI5GjV8S-0oNkGqXfCYapPUXw', null, 'https://wx.qlogo.cn/mmopen/vi_32/TibX0QdYzyCerUWMmhibq2TiatKO0Bu5LnibK7YznOTnXquB1rVdeJ2RVjNzSJ5WricbjIWW9NW7RXMOIEbF6kaymIA/132', '0', null, '1', '2019-08-22 07:09:37', '27.10.60.71', '1', '2019-08-22 07:09:38', '2019-08-22 07:09:37');
INSERT INTO `unimall_user` VALUES ('259', null, null, '2', 'osTQe6G3Lpq7JkkiaKvzkyy1nQZ8', 'Sam', 'http://thirdwx.qlogo.cn/mmopen/vi_32/uYDia5NALT0GwswIqLia3EL1JXe7BcqUjKWMblpGg0ib51ib50pSljH6XhDfe3ROTzrF2uL87B3Yh8U2WiaV7tWibE8g/132', '0', null, '1', '2019-09-09 05:56:24', '27.10.60.71', '1', '2019-09-09 05:56:26', '2019-08-22 07:15:37');
INSERT INTO `unimall_user` VALUES ('260', null, null, '1', 'oiAuI5N5Cg8TT32IFvjFvRYNVR3c', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKC8icZH2kuZicY7ap39l8tZgdcWhL3pjPiap7FTSUnoddJicgf6b4qlJoOb1dhx0nFz0uJzibC0av8MHQ/132', '0', null, '1', '2019-08-25 14:17:45', '27.10.60.71', '1', '2019-08-25 14:17:45', '2019-08-22 09:15:43');
INSERT INTO `unimall_user` VALUES ('261', null, null, '1', 'oiAuI5OoeZ_3LzLrkOkBoBAkh9dc', null, null, '0', null, '-1', '2019-08-22 10:30:40', '27.10.60.71', '1', '2019-08-22 10:30:40', '2019-08-22 10:30:40');
INSERT INTO `unimall_user` VALUES ('262', null, null, '1', 'oiAuI5KWUahVTLwcazhZE9fVpJtA', null, null, '0', null, '-1', '2019-08-22 12:06:07', '27.10.60.71', '1', '2019-08-22 12:06:07', '2019-08-22 12:06:07');
INSERT INTO `unimall_user` VALUES ('263', null, null, '1', 'oiAuI5PKreov9lBfiDhXxxHpJumw', null, 'https://wx.qlogo.cn/mmopen/vi_32/lMb6Utz93o4UOL7Jy4mQGyRv9IMYIMvR52DnibWnbKYcxJAGt7lH6XbuM07xhmdQkRZ9jEyNxpSXgiaq6uJ28PDQ/132', '0', null, '1', '2019-08-22 13:18:26', '27.10.60.71', '1', '2019-08-22 13:18:26', '2019-08-22 13:18:26');
INSERT INTO `unimall_user` VALUES ('264', null, null, '1', 'oiAuI5Llkp7LjSNXswIgVXt6RF_U', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83epZQAuoQsiaibRE9ia5ug0icTI6yuIvMWbPk3ezf8SlBzzpD40kYxe2JicUOENoWglZ9SibftH9CbslE3icQ/132', '0', null, '2', '2019-08-23 16:22:06', '27.10.60.71', '1', '2019-08-23 16:22:06', '2019-08-22 13:43:11');
INSERT INTO `unimall_user` VALUES ('265', null, null, '1', 'oiAuI5CNipINQTU_AUAOri7iJHJk', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIf8Yq0R40bdA8hNhRiarSHwcUHru55ibASmiaZMRrAygGA6cTGMWnHxu9YnCWxLMb7bHmfHrGickOiabg/132', '0', null, '1', '2019-08-22 13:55:07', '27.10.60.71', '1', '2019-08-22 13:55:07', '2019-08-22 13:55:07');
INSERT INTO `unimall_user` VALUES ('266', null, null, '1', 'oiAuI5LbicydiVDUCOHeRNxA8yVA', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIRjMGoKU3yAjmZNzlYIgwvWTXWOvpnjokaC8uQhoEMQGia4cibjLCRaoqvbI42PAtX54aCfFHXblkQ/132', '0', null, '1', '2019-08-22 15:18:27', '27.10.60.71', '1', '2019-08-22 15:18:27', '2019-08-22 15:18:27');
INSERT INTO `unimall_user` VALUES ('267', null, null, '2', 'osTQe6A_JnHtzBWEnctwUjJWZT00', 'tengq', 'http://thirdwx.qlogo.cn/mmopen/vi_32/6gyzWSSAGG3FetWd2beJgtHlJTFANORK4z5Z7aeOUZGgvZ2jkF48YNib4VzUC6Cfg3xGduscEPrkDLTGTicus4GQ/132', '0', null, '0', '2019-08-22 16:43:33', '27.10.60.71', '1', '2019-08-22 16:43:34', '2019-08-22 16:43:33');
INSERT INTO `unimall_user` VALUES ('268', '13011846130', '$1$1301184$w3Es18qLcH42kBEq7owNu/', '0', null, null, null, '0', null, '-1', '2019-08-22 17:05:32', '27.10.60.71', '1', '2019-08-22 17:05:14', '2019-08-22 17:05:14');
INSERT INTO `unimall_user` VALUES ('269', null, null, '1', 'oiAuI5IQTiQlJQdubghi05Tv6rGc', null, 'https://wx.qlogo.cn/mmopen/vi_32/BL7ic7uQTibM2gJSkFUhODiceHl0h3eQJGMJpEOibOzIWgHxV7gcpMtUro7iafK9ffDictNyCsaCgjOROOTLCKrHlZDg/132', '0', null, '1', '2019-08-22 18:37:36', '27.10.60.71', '1', '2019-08-22 18:37:36', '2019-08-22 18:37:36');
INSERT INTO `unimall_user` VALUES ('270', null, null, '1', 'oiAuI5Dc-lifSGJ56hjdXoEU4-dc', null, 'https://wx.qlogo.cn/mmopen/vi_32/EKagrrV6YQUdfmC7KTK0iagW5hAiarQaLfJjE3wTSKVTXddiajIsZsk6D9J8YKxFibvyja2j5iazsVxA6U6Ak8fsQgQ/132', '0', null, '1', '2019-08-22 21:58:43', '27.10.60.71', '1', '2019-08-22 21:58:43', '2019-08-22 21:58:43');
INSERT INTO `unimall_user` VALUES ('271', null, null, '1', 'oiAuI5Dc-lifSGJ56hjdXoEU4-dc', null, 'https://wx.qlogo.cn/mmopen/vi_32/EKagrrV6YQUdfmC7KTK0iagW5hAiarQaLfJjE3wTSKVTXddiajIsZsk6D9J8YKxFibvyja2j5iazsVxA6U6Ak8fsQgQ/132', '0', null, '1', '2019-08-22 21:58:43', '27.10.60.71', '1', '2019-08-22 21:58:43', '2019-08-22 21:58:43');
INSERT INTO `unimall_user` VALUES ('272', null, null, '1', 'oiAuI5Kc7xuOKKZa9PqORrkQjkK4', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83er5SNsSoiaZw4Szfl31FT5pv2PHrmGCRtyHbiam0Idm0vvticwzBQFhpW4RYdWyicmibZcICfhAjv1YhoA/132', '0', null, '1', '2019-08-23 05:16:48', '27.10.60.71', '1', '2019-08-23 05:16:49', '2019-08-23 05:16:48');
INSERT INTO `unimall_user` VALUES ('273', '13277068145', '$1$1327706$tTQ7PQQdUOi8AbZJCf/NC.', '0', null, null, null, '0', null, '-1', '2019-08-23 11:21:50', '27.10.60.71', '1', '2019-08-23 11:21:33', '2019-08-23 11:21:33');
INSERT INTO `unimall_user` VALUES ('274', null, null, '1', 'oiAuI5G4bg1EV4PXTgl0Uo5u6F2E', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKhHKtAqF1r5JuEBSvX7Qj6hplqTZeIgwYiaicAQ1cB4muFNrJDt7nNgr3QPZicfp2IHfuHzSNedIsrQ/132', '0', null, '1', '2019-08-24 14:14:45', '27.10.60.71', '1', '2019-08-24 14:14:46', '2019-08-23 13:03:46');
INSERT INTO `unimall_user` VALUES ('275', null, null, '1', 'oiAuI5F7hotd2QBTsnh-Jcd3TN8A', null, 'https://wx.qlogo.cn/mmopen/vi_32/UXlW7deXDI02vdxtWaIYjUHP2zdkaS9JibqbceSpiaMgiamy7aHLw74YNf6Gjv0zI6xnFgaHtfric34J3UHTYANLjQ/132', '0', null, '1', '2019-08-26 20:48:04', '27.10.60.71', '1', '2019-08-26 20:48:04', '2019-08-23 13:12:38');
INSERT INTO `unimall_user` VALUES ('276', null, null, '2', 'osTQe6J4YF2lHmWiI68iSASAXTgA', '天', 'http://thirdwx.qlogo.cn/mmopen/vi_32/sm0F9h4CoTSjSRapmicPt9uicia5nBTiaOGz8IAgHENTJx1xeoaiaooMvRvVowib9icDqZHadIL0Eib8cqQDyxmKWoEDicA/132', '0', null, '1', '2019-08-23 13:36:33', '27.10.60.71', '1', '2019-08-23 13:36:34', '2019-08-23 13:36:33');
INSERT INTO `unimall_user` VALUES ('277', null, null, '1', 'oiAuI5KSE4TFExmuKSNRCWmYKuJk', null, 'https://wx.qlogo.cn/mmopen/vi_32/4zKLZ8AJ5h2X8tyZ6hU8yq4oggdRDukZGbOBbfueZ2y9gsgkRibo9a6F8ibeJCEX8G33NUJqqx18KicOfLFhsCCPQ/132', '0', null, '1', '2019-08-29 13:24:10', '27.10.60.71', '1', '2019-08-29 13:24:11', '2019-08-23 15:35:42');
INSERT INTO `unimall_user` VALUES ('278', null, null, '1', 'oiAuI5EF5jCIAWDSga3YJbjLtQAk', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJSbibQr2mLmWprmKIyk25FszFNwZ6FXzFmbFwEh1c7u7IzBrs8sI1q2TcyFvF3ny0jX8T2RvBicT0A/132', '0', null, '1', '2019-08-23 15:43:50', '27.10.60.71', '1', '2019-08-23 15:43:50', '2019-08-23 15:43:50');
INSERT INTO `unimall_user` VALUES ('279', null, null, '1', 'oiAuI5M6LQdCiERAsGd_Kl3S89x4', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIavBvxZz8M4ZSR3y2sGiaicbWQib0ljHX5VfFnM1iaOr0ExFa6UdiaYtNnTe0xR9E3pyHKBh0XCFX6qHA/132', '0', null, '1', '2019-08-23 17:02:55', '27.10.60.71', '1', '2019-08-23 17:02:55', '2019-08-23 17:02:55');
INSERT INTO `unimall_user` VALUES ('280', null, null, '1', 'oiAuI5KyuZZMQU9GK8AxdxnXTSL4', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83epx777icgUsGOQVc9DxvFibj7U6Ovr3F9EjstEmFGPjaAc2MmUbkpO0mneuMIQoxNggg979XRw1bnBg/132', '0', null, '1', '2019-08-24 13:07:31', '27.10.60.71', '1', '2019-08-24 13:07:31', '2019-08-24 13:07:31');
INSERT INTO `unimall_user` VALUES ('281', null, null, '1', 'oiAuI5HtVtsU3KXmtFpCJuNSSkao', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83ephJrSCSKhvz3LjsH2ibUTC8eP3kdSFx6LH3TQnLRiat6ZoyOtHbseZ56joWs2eEGLqLVxS4vehIvUA/132', '0', null, '1', '2019-08-25 23:26:09', '27.10.60.71', '1', '2019-08-25 23:26:09', '2019-08-24 16:36:45');
INSERT INTO `unimall_user` VALUES ('282', null, null, '1', 'oiAuI5HX_jdWe-wrbHKiFE80zgpM', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83ep8q1Kf0I8r68eAKjEAg0cTAzdPib9RiceQY611C4oWibPa9ZDkzrhYzsNYpzQx8ject0IFnyKG3eJAA/132', '0', null, '1', '2019-08-24 22:54:36', '27.10.60.71', '1', '2019-08-24 22:54:36', '2019-08-24 22:54:36');
INSERT INTO `unimall_user` VALUES ('283', null, null, '1', 'oiAuI5Oo1cKShOcb-hOo5nfkUB4M', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83ep67Qd1IcI0XQNpf0T3RGsctb8uGicic3qiaOxGBz3zSiaa3dCM3fkS995LfEibctJuo8jacBuy1ib30Faw/132', '0', null, '1', '2019-08-25 00:25:02', '27.10.60.71', '1', '2019-08-25 00:25:03', '2019-08-25 00:25:02');
INSERT INTO `unimall_user` VALUES ('284', null, null, '2', 'osTQe6NWRJhEte7VTEom2ay7VdMk', 'Miv', 'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eq8mWHcUrvRkac14zxt5oILVFD9sCaZu7Nl4Pmr3WiccVia3BLu8NHdic8E1oURkG4ia2BT4VQ2yCbq6A/132', '0', null, '1', '2019-08-25 02:21:15', '27.10.60.71', '1', '2019-08-25 02:21:16', '2019-08-25 02:21:15');
INSERT INTO `unimall_user` VALUES ('285', '13810819020', '$1$1381081$Yvq2u4nzCFdUF4DfKqNE3.', '0', null, 'lee', null, '0', null, '1', '2019-08-31 09:25:32', '27.10.60.71', '1', '2019-08-31 08:47:11', '2019-08-25 07:45:19');
INSERT INTO `unimall_user` VALUES ('286', null, null, '1', 'oiAuI5Kz2CwZ6u_dUd5APXPe_zn8', null, 'https://wx.qlogo.cn/mmopen/vi_32/ZdHZ8vlDjCppdjX5XjgmDS5RT0knyEIqnMTqDIgssTu16QPkU5RApcMoeXL8zQSicB0kTVsuY3bywZz9V5CalgQ/132', '0', null, '1', '2019-08-25 15:54:38', '27.10.60.71', '1', '2019-08-25 15:54:38', '2019-08-25 15:54:38');
INSERT INTO `unimall_user` VALUES ('287', null, null, '1', 'oiAuI5PW14tW9FcvlnWBlvFArx0k', '法师', 'https://wx.qlogo.cn/mmopen/vi_32/t5WJ9oHH88hukicgCAMWib7qkgxQ5Wfg7b3sdicXp6ULyZb4XNoFjshVDtGxwMfic4vk66tvQ0YG16YPK1zaEwUMOQ/132', '0', null, '1', '2019-09-07 23:23:45', '27.10.60.71', '1', '2019-09-07 23:23:45', '2019-08-26 10:37:15');
INSERT INTO `unimall_user` VALUES ('288', null, null, '1', 'oiAuI5M-tNAZLWHcecmNl838SzEg', null, 'https://wx.qlogo.cn/mmopen/vi_32/9K3iaJmNFk5ZUgnBs52YrxcE3nRnIm5eAib6cLEzSkkmPOqjCy5wDucg9PnodCzEtS4tePMW3LvQgmjbGNHFWIoQ/132', '0', null, '1', '2019-09-09 08:43:29', '27.10.60.71', '1', '2019-09-09 08:43:29', '2019-08-26 11:52:11');
INSERT INTO `unimall_user` VALUES ('289', null, null, '1', 'oiAuI5HA9nTz_88ugOSSPJhNkurg', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLCbnGYOhwYmDyBHQZcN6UibufTa7STrGno8mz3FNy2IvR5IicFia0GIbfRXcb6stZkT2jic7wR4aHCrg/132', '0', null, '1', '2019-08-26 14:45:45', '27.10.60.71', '1', '2019-08-26 14:45:45', '2019-08-26 14:06:50');
INSERT INTO `unimall_user` VALUES ('290', null, null, '1', 'oiAuI5Ap1QfKvdJ0lFiAP316cFrA', null, 'https://wx.qlogo.cn/mmopen/vi_32/WlHqRUJ4EaZmPzO9ia4A03N7zyWb4u3YUXNZAJkodhKHNGzmuEGb0TZGjXJyZ1y13Sw5JhMjAGFiaGFfdQQKsibcA/132', '0', null, '1', '2019-08-26 15:20:35', '27.10.60.71', '1', '2019-08-26 15:20:36', '2019-08-26 15:20:35');
INSERT INTO `unimall_user` VALUES ('291', null, null, '1', 'oiAuI5Kwwi3h2Sk4I-hir31jQZzU', null, 'https://wx.qlogo.cn/mmopen/vi_32/SLyfSBr698OwFacNricibmq9VsvPeusSxEkdf9Trqjmef2kSVxqTPWo4FaNkcXE3En9kXiba6CIedNn0OuM78EcoQ/132', '0', null, '1', '2019-08-26 15:26:14', '27.10.60.71', '1', '2019-08-26 15:26:14', '2019-08-26 15:26:14');
INSERT INTO `unimall_user` VALUES ('292', null, null, '1', 'oiAuI5NzD3MdCDstJPnvqZMOUIGE', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIZxvAEgGnt0ibOQeSiaaR8dic0VaP4V5hgfBibX62YAJhQomdHK0wUI4qZwYw8S82XL5as2jc35jZ2qw/132', '0', null, '2', '2019-08-26 15:29:56', '27.10.60.71', '1', '2019-08-26 15:29:56', '2019-08-26 15:29:56');
INSERT INTO `unimall_user` VALUES ('293', null, null, '1', 'oiAuI5BN_PARmlsrdQLVgVvbHXrQ', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKR40G4hjeiceFnjsoueWGFGx0Gic1hEBTkAd9icBW5IaoBY1ua9VWQA9CJjXrdoIC6JGga26jW5nA7w/132', '0', null, '1', '2019-08-26 17:05:24', '27.10.60.71', '1', '2019-08-26 17:05:24', '2019-08-26 17:05:24');
INSERT INTO `unimall_user` VALUES ('294', null, null, '1', 'oiAuI5FFa6qZH0bRJ5xbE-S9UrI8', null, 'https://wx.qlogo.cn/mmopen/vi_32/PiajxSqBRaELGMmIsicyClxxqdINqlgGibkgzsqSJedvaNOic5S4lbicUXdhr0Xtjtciam1Uo5T7Bdic0lTIgf6TE9eyA/132', '0', null, '1', '2019-08-26 17:08:12', '27.10.60.71', '1', '2019-08-26 17:08:12', '2019-08-26 17:08:12');
INSERT INTO `unimall_user` VALUES ('295', null, null, '1', 'oiAuI5Cf8-Cvhky9QY_0mctvsYxo', null, 'https://wx.qlogo.cn/mmopen/vi_32/KjQwgJXsbgXEhOPLuX9UttLUARwWjjEdq6Uc1cKjGRdKsaMBPfU0fMGicw2SAW9YDZsBovx2pYmMocWlfz6APPg/132', '0', null, '1', '2019-08-26 23:14:57', '27.10.60.71', '1', '2019-08-26 23:14:57', '2019-08-26 23:14:57');
INSERT INTO `unimall_user` VALUES ('296', null, null, '1', 'oiAuI5CMj5zRXOv9G0ke28-PzvXg', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTI39XReVrENhWYFVYH5WXcqkOXsnCBrsgEInHah7h6l6eTUeSGGIkPxuCWAFIoLdPwWuxIZwCTXmQ/132', '0', null, '1', '2019-08-27 13:33:14', '27.10.60.71', '1', '2019-08-27 13:33:15', '2019-08-27 13:33:14');
INSERT INTO `unimall_user` VALUES ('297', null, null, '1', 'oiAuI5CN3uELGBG0Jnb7d6wcwzWg', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83erD62pY9jlickrOX7rcicSH5shNu5XB6qnP79ft75IZoN5YZNoJpicGicVbQTDdicRdMuh4W3NElP5XekA/132', '0', null, '1', '2019-08-27 16:32:08', '27.10.60.71', '1', '2019-08-27 16:32:08', '2019-08-27 16:32:08');
INSERT INTO `unimall_user` VALUES ('298', null, null, '1', 'oiAuI5JCwqNz58pNS9LUthHNlY64', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83erRMKuRPmiauNlXHzHibyyiaxke0OyM4IR2DwSw9WsZ0QyeOWvGlFhic5g36S9XllfNSBTX6xzgUibvXOA/132', '0', null, '1', '2019-08-27 17:32:01', '27.10.60.71', '1', '2019-08-27 17:32:02', '2019-08-27 17:32:01');
INSERT INTO `unimall_user` VALUES ('299', null, null, '1', 'oiAuI5M1oEvDa55U0HnvDK89Vj64', null, 'https://wx.qlogo.cn/mmopen/vi_32/l3N0kle8FBseHdrqGquaicvTqvv9D4YD1hkdJIcbicicfyWYia8hoIoZeZ4ujsoCspA86P2nmmZNbSTcL7QGRthwag/132', '0', null, '1', '2019-08-27 17:58:31', '27.10.60.71', '1', '2019-08-27 17:58:31', '2019-08-27 17:58:31');
INSERT INTO `unimall_user` VALUES ('300', null, null, '1', 'oiAuI5NqwW63zYWJOULP8Fmbhdwk', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLbswPFgMWmExWnBRAAqBqkrg1XlKrDKuULianqzbTXMHbC5hN9Q8FB7eTLuwyY2ajEN33rmKVXkTQ/132', '0', null, '1', '2019-08-27 18:24:03', '27.10.60.71', '1', '2019-08-27 18:24:03', '2019-08-27 18:24:03');
INSERT INTO `unimall_user` VALUES ('301', null, null, '1', 'oiAuI5FI15swsZ3nqT62iMf2WRJI', null, 'https://wx.qlogo.cn/mmopen/vi_32/dwP3oJmQJsDhfbV1Tt3epeTmrPHyUcT7NVdZmrXmic8cehUnFjER1IibPlH4AgMsBxAXKQc4eck2pdPA5ZFI3aZg/132', '0', null, '1', '2019-08-27 19:41:06', '27.10.60.71', '1', '2019-08-27 19:41:06', '2019-08-27 19:41:06');
INSERT INTO `unimall_user` VALUES ('302', null, null, '1', 'oiAuI5FHvEgSyLQpaEJy12hQaSlo', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoThvnXzvKicuBAJ6fXiaCeiaqngxPoFVgT8lEQqKGINQPLkErleIuGKer2CN08ktJnPpEGXL2ayFiaIg/132', '0', null, '1', '2019-09-05 14:36:06', '27.10.60.71', '1', '2019-09-05 14:36:07', '2019-08-27 23:04:37');
INSERT INTO `unimall_user` VALUES ('303', null, null, '1', 'oiAuI5IcsVM1gm81xAuu2sMRoDNY', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKUOdXYUhEIvicB3b5PIVj4piaicBIryznF9q5R6Bzx7E4aamlCcDGUd2Mlhnn0usicRicoZF2yjeweGJA/132', '0', null, '1', '2019-08-29 13:53:49', '27.10.60.71', '1', '2019-08-29 13:53:49', '2019-08-28 16:12:44');
INSERT INTO `unimall_user` VALUES ('304', null, null, '1', 'oiAuI5OgqkRKlPAesIQYYxcaRcNM', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83epKMx3tL6bsCC6UTXRNRCha8HLKV4qDT7hkibct5O040ZGibONLib6pQODqF2HdhpfXbibVxMtOXSYiaQg/132', '0', null, '1', '2019-09-16 16:04:37', '27.10.60.71', '1', '2019-09-16 16:04:37', '2019-08-28 16:14:15');
INSERT INTO `unimall_user` VALUES ('305', null, null, '2', 'oRrdQt_prKrfsn7uKn6K0TVCbbc0', '云中漫步', 'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83erjNiapEcib2arrmdoPianhGKROVk47FEsD6cEXpLLokd1zd7vBs3bb3a1q5BhaLMJDHuLonUib6NQUEw/132', '0', null, '1', '2019-09-18 21:24:31', '27.10.60.71', '1', '2019-09-18 21:24:31', '2019-08-28 21:36:35');
INSERT INTO `unimall_user` VALUES ('306', null, null, '1', 'oiAuI5DUrnK7MoHoorjuqOqzVcZ0', null, 'https://wx.qlogo.cn/mmopen/vi_32/ajNVdqHZLLCFSJ4Lkfdib7DoHoJW25UWfDOguI1qiaUYeJasJr6T4eCJuK9ICs2ujO98zecWTfFtgRlEpxrQ6G5w/132', '0', null, '1', '2019-08-28 22:04:17', '27.10.60.71', '1', '2019-08-28 22:04:17', '2019-08-28 22:04:17');
INSERT INTO `unimall_user` VALUES ('307', null, null, '1', 'oiAuI5Jp_CGzXvnnRd2LDg90zopc', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLbGP6MsLzkIe8mIMI70zdjn7pUaJ3X1kd9QIibEMZEgjNYWVcNBx3pYQJQxjkFF8F69QRKibWqbmSQ/132', '0', null, '1', '2019-08-28 23:46:18', '27.10.60.71', '1', '2019-08-28 23:46:19', '2019-08-28 23:46:18');
INSERT INTO `unimall_user` VALUES ('308', '18316842740', '$1$1831684$y4yEzpa6RP77fBVjKKhZ41', '0', null, null, null, '0', null, '-1', '2019-08-29 09:43:30', '27.10.60.71', '1', '2019-08-29 01:43:58', '2019-08-29 01:43:58');
INSERT INTO `unimall_user` VALUES ('309', null, null, '1', 'oiAuI5AUHZGSNUBZx2649Of6h5Ag', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83erBKCjpXnAKXYRBfuZ8H4OAzHTWG6n0IQTWj2HuYaibLGSctnR0oDBnYEP0lS1XG4ib5zqVLecWvVMQ/132', '0', null, '1', '2019-08-29 10:00:00', '27.10.60.71', '1', '2019-08-29 10:00:00', '2019-08-29 09:16:28');
INSERT INTO `unimall_user` VALUES ('310', null, null, '1', 'oiAuI5Ay4NajqmxZVNaswbPN-NKc', null, 'https://wx.qlogo.cn/mmopen/vi_32/Mr1d38hPHgyT2auBCtls8MhLqYfDdibib5yPjdEjF1jgs4vK0f78uvRGvYjxUFWSlIdVfJLuTRHIV2kG8HmaeIVQ/132', '0', null, '1', '2019-08-29 09:50:00', '27.10.60.71', '1', '2019-08-29 09:50:01', '2019-08-29 09:50:00');
INSERT INTO `unimall_user` VALUES ('311', null, null, '2', 'oRrdQt4ytJRQF9NZ8arPHDoi8slU', 'Mr.L', 'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83ertT6oIhl9T0Htr5SIkb7CeHcoBH87oiaiaH7NOUuDWZpaTOZBnlfibXjKBULs92ZZJ4YzR4XleAagzw/132', '0', null, '1', '2019-08-29 17:55:38', '27.10.60.71', '1', '2019-08-29 17:55:39', '2019-08-29 10:19:24');
INSERT INTO `unimall_user` VALUES ('312', null, null, '1', 'oiAuI5IJ0WHxmEZ035cMXrh240_U', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83errIS3m4y5RzWwq7uYJj5mhcvb3ekZr39dY9ia8IUh5G4tWyulBHLsLJE1vfRj2HL0KSBYcOLOb69A/132', '0', null, '1', '2019-08-29 10:22:07', '27.10.60.71', '1', '2019-08-29 10:22:07', '2019-08-29 10:22:07');
INSERT INTO `unimall_user` VALUES ('313', null, null, '1', 'oiAuI5J4TwcvD0QJNA9TJ7AKK9_k', null, 'https://wx.qlogo.cn/mmopen/vi_32/tdBGWN4uMXcljH5AcvS8LQSanEzVpVmvKQR6D7OiaL6lW7BcTXAsHByA0oO7VDbvD9yBa1TstfHAINHRKrbhd6w/132', '0', null, '1', '2019-08-29 11:44:18', '27.10.60.71', '1', '2019-08-29 11:44:19', '2019-08-29 11:44:18');
INSERT INTO `unimall_user` VALUES ('314', null, null, '1', 'oiAuI5B_Q2ekGjgxAIB05o8e2Ec4', null, 'https://wx.qlogo.cn/mmopen/vi_32/ew2kZafQCicU2197p2Gz1IPlcOEQAt5uTQial1uZghNLqR8RynJd5N8qwjjAfEsKJDicuPMicbLCpPepjhoqjLnPAw/132', '0', null, '1', '2019-08-29 16:08:56', '27.10.60.71', '1', '2019-08-29 16:08:57', '2019-08-29 16:08:56');
INSERT INTO `unimall_user` VALUES ('315', null, null, '1', 'oiAuI5GEU19hUmiA9JJi_SAzsWsE', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoarxYHRvibBn1IiaXyRsnHvGw4iaVoagiaqrBgFXb0SX49FdY4YnnO9hpgX6rQHmqNNuzzibNrD7ZCT9g/132', '0', null, '1', '2019-08-29 16:33:50', '27.10.60.71', '1', '2019-08-29 16:33:50', '2019-08-29 16:33:50');
INSERT INTO `unimall_user` VALUES ('316', null, null, '1', 'oiAuI5AuM_VTHVS-WuAalo4N_YqI', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJwdpfTn2EjeffoS1mfcxSXT8BzQ5R5zAtPHfZBcTjsKo1eNextvmRy6icQtvP3TYbics5p9nutC2pw/132', '0', null, '1', '2019-08-30 08:44:53', '27.10.60.71', '1', '2019-08-30 08:44:53', '2019-08-30 08:44:53');
INSERT INTO `unimall_user` VALUES ('317', null, null, '1', 'oiAuI5B7uVs_W4lNGDchTeWyl2nQ', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIrrX8cxo1ISZVwYcDlodoic5DS2bPwIt6eVuTOBrtEZlhcW5bgv1w5ZTQzK4XPEbpOicR9Kjiav4wNA/132', '0', null, '1', '2019-09-08 22:14:41', '27.10.60.71', '1', '2019-09-08 22:14:42', '2019-08-30 14:41:03');
INSERT INTO `unimall_user` VALUES ('318', null, null, '1', 'oiAuI5I2i4NXHBl28IYRRTGoMk2A', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eo8NMONgicic7pfbBCZ4rEUKNuG5CyKmahKgnKpGdBqiaZhweKiahs0qaDauOM4O8emwHicowic7MJkg2FQ/132', '0', null, '1', '2019-08-30 15:14:30', '27.10.60.71', '1', '2019-08-30 15:14:30', '2019-08-30 15:14:30');
INSERT INTO `unimall_user` VALUES ('319', null, null, '1', 'oiAuI5I0a0ELQ8HuKoYgrbAVBxCw', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqAQKRdr2GjYibkcskRFHwqDUrXH6R8yqiaMhSeuCgJCcTle9xUwiaURB5ERibKSnXCy58zEicfrNtSQ8Q/132', '0', null, '1', '2019-08-30 15:45:51', '27.10.60.71', '1', '2019-08-30 15:45:51', '2019-08-30 15:45:51');
INSERT INTO `unimall_user` VALUES ('320', null, null, '1', 'oiAuI5LvKOKkadURcWbjDW-WGK3w', null, 'https://wx.qlogo.cn/mmopen/vi_32/6ogCPmD6xicg97WUOJT8zG4gzzXic0lo7kKdYClibnh8xcFFaJAibHLdCxGxQ6wabLBSiaCLgSBGpawzbr4HX9JrLLA/132', '0', null, '1', '2019-08-30 20:20:45', '27.10.60.71', '1', '2019-08-30 20:20:47', '2019-08-30 20:20:45');
INSERT INTO `unimall_user` VALUES ('321', null, null, '2', 'osTQe6DVv2UeIc7PRQWDFfMtAmmk', '小猛', 'http://thirdwx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEI3q2FaMIf2CXzH3AxnamiaSiaaSGD8OmiaajBz2GQpyQWKnjD8picXUhgEDyZmWKYj6o2vwHUZgjvXhQ/132', '0', null, '1', '2019-09-02 08:58:19', '27.10.60.71', '1', '2019-09-02 08:58:20', '2019-08-30 22:19:08');
INSERT INTO `unimall_user` VALUES ('322', '13714216227', '$1$1371421$6RAcKNAOkb5I9KjibaLjf1', '0', null, null, null, '0', null, '-1', '2019-08-30 22:39:47', '27.10.60.71', '1', '2019-08-30 22:39:47', '2019-08-30 22:39:47');
INSERT INTO `unimall_user` VALUES ('323', null, null, '1', 'oiAuI5PhJC7Nel2pGxt6TKrRsXug', null, 'https://wx.qlogo.cn/mmopen/vi_32/PiajxSqBRaELHtuC2spPYcvRg8Hese5yTfLg7YTia8Td5sSwQ2Ata2NVwelKcneRrMN3e4uwTJrjUBQQdplPoQoQ/132', '0', null, '1', '2019-09-03 08:48:41', '27.10.60.71', '1', '2019-09-03 08:48:42', '2019-08-30 22:48:03');
INSERT INTO `unimall_user` VALUES ('324', null, null, '1', 'oiAuI5Nzwnpz3ZL_Jl8jUei6u1WU', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83ersOibIibodHJFacORWianIiaQAxlMHRJ4lKDPic11dyxdaSU8XpQVtMFqIVO1DX9GR9KgT6Ze4zdX36lA/132', '0', null, '1', '2019-08-30 23:36:23', '27.10.60.71', '1', '2019-08-30 23:36:23', '2019-08-30 23:36:23');
INSERT INTO `unimall_user` VALUES ('325', null, null, '1', 'oiAuI5OaTVv3dZRKRIQVxZvApL_Q', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTL3xC3su7xUBXju2I28Z1pNIjdhk1AJfzIia82SMSkwhp1Rq1e9N96iapwib6qIBLXHrxv43d8dpvSJQ/132', '0', null, '1', '2019-08-31 08:39:48', '27.10.60.71', '1', '2019-08-31 08:39:48', '2019-08-31 08:39:48');
INSERT INTO `unimall_user` VALUES ('326', null, null, '1', 'oiAuI5DHnYTc_dbZjM-8scz71J3g', null, 'https://wx.qlogo.cn/mmopen/vi_32/0ZIguuJjvM1ypZgTo3QXLia8oRIq93H9PWuUEB5IRP2uDTpOgTbJ5c2f8N7Ab6MGQyMsLeAt7lcSjWicBhjUo0lg/132', '0', null, '1', '2019-08-31 09:14:01', '27.10.60.71', '1', '2019-08-31 09:14:01', '2019-08-31 09:14:01');
INSERT INTO `unimall_user` VALUES ('327', null, null, '1', 'oiAuI5O5w_IHlD5JiQxgx4KID2qk', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKeY4HjTSJWiavqMEj5zCUq3YWsynAS4Eu4zOqiafqO16vVszT40YU27ojGMgpWNWYCsEhgTeKDBzaQ/132', '0', null, '1', '2019-08-31 10:01:32', '27.10.60.71', '1', '2019-08-31 10:01:32', '2019-08-31 10:01:32');
INSERT INTO `unimall_user` VALUES ('328', null, null, '1', 'oiAuI5OelZ6ylqbnmHrHXCF8bqUQ', null, 'https://wx.qlogo.cn/mmopen/vi_32/zPrayCW4wvykp7pn7QXZ9LIhtOvQ3MwC2a62A7iaJ4RusjZQQ03oa1tJkoynGBE4kcmT4RztBBoyPFBdZn8libMQ/132', '0', null, '1', '2019-08-31 14:30:16', '27.10.60.71', '1', '2019-08-31 14:30:16', '2019-08-31 14:30:16');
INSERT INTO `unimall_user` VALUES ('329', null, null, '1', 'oiAuI5C_f1d7f5_tQ_Dn7rQAgxuo', null, 'https://wx.qlogo.cn/mmopen/vi_32/slNgAB7g27ib3iadK5EP0kXtF6HhjrrzgJTNdsx5UBcibeXZNZcsLy8nibPRJR5JbfBACecOKo7VLNzuElQLZZfPbQ/132', '0', null, '1', '2019-08-31 14:58:00', '27.10.60.71', '1', '2019-08-31 14:58:00', '2019-08-31 14:58:00');
INSERT INTO `unimall_user` VALUES ('330', null, null, '1', 'oiAuI5Ha88wU8rX2xNiA1cnaU1P4', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJax67vicpTYwQMP3Tp7eiaooZVROCYKTm4JICQ4yb68bwiaXX5cQ7OH3ku2jTiaocKrNtpB023car76A/132', '0', null, '1', '2019-08-31 16:55:35', '27.10.60.71', '1', '2019-08-31 16:55:35', '2019-08-31 16:55:35');
INSERT INTO `unimall_user` VALUES ('331', null, null, '1', 'oiAuI5PS6vNhF6MDFCodROvl4vO8', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83erWcywV76l5Gs5SY3dBPExAQreAfBfQpjFCGhHBmo3erzLF7Aiac4bw6E9icvpXpVlwRtJGcibzBvUkA/132', '0', null, '1', '2019-08-31 18:57:37', '27.10.60.71', '1', '2019-08-31 18:57:37', '2019-08-31 18:57:37');
INSERT INTO `unimall_user` VALUES ('332', null, null, '1', 'oiAuI5MooJTpW7sWG7WWZkXmXDhY', null, 'https://wx.qlogo.cn/mmhead/KGibZnZyTbMvia3vye6IMXy4QFhNp7xx0PtfzftmqsibvQ/132', '0', null, '2', '2019-09-01 06:05:37', '27.10.60.71', '1', '2019-09-01 06:06:57', '2019-09-01 06:05:37');
INSERT INTO `unimall_user` VALUES ('333', null, null, '1', 'oiAuI5J4TEBstISYCyGXRe1v-OrA', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIDqHQQByGiaXRkEvax4icQ9cgFkWUlQpNPatKVmbZ0A0dnKyIOEqYFWTjp8ypSh7AmxOXsmhDBKiatg/132', '0', null, '1', '2019-09-01 12:06:37', '27.10.60.71', '1', '2019-09-01 12:06:37', '2019-09-01 12:06:37');
INSERT INTO `unimall_user` VALUES ('334', null, null, '1', 'oiAuI5HJ6yCyvr0oZXkmqHGCIcXc', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKmyUF0MWNDUSZKchmDIuZjRRPFBMHBzZZzb7Mz8prnMjh6ofDpdPiaibUfUH8m8mfzfFGNxN6jEcvg/132', '0', null, '1', '2019-09-01 14:18:49', '27.10.60.71', '1', '2019-09-01 14:18:49', '2019-09-01 14:18:49');
INSERT INTO `unimall_user` VALUES ('335', null, null, '1', 'oiAuI5KXRTJh24VA66OBrq8buVyY', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83epmm5lks3pj5N2kGEV6Fpc0zx8fvWj9WRaLwATSrIgp2w7XpAJY6WYmAwqia63sX0ficFGMibke6ZCWQ/132', '0', null, '1', '2019-09-01 20:05:42', '27.10.60.71', '1', '2019-09-01 20:05:42', '2019-09-01 20:05:42');
INSERT INTO `unimall_user` VALUES ('336', null, null, '1', 'oiAuI5D44o1Dq18ErihwbssDQzso', null, 'https://wx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEJ04UXL8rYbQjwPIMuoQfH9WptTlMia3F9YFBZDJx43c4YrGMua2fgGMtvicdzA8unFtGBM1JOUqgicg/132', '0', null, '1', '2019-09-08 11:02:08', '27.10.60.71', '1', '2019-09-08 11:02:08', '2019-09-02 07:21:25');
INSERT INTO `unimall_user` VALUES ('337', null, null, '1', 'oiAuI5Gch1MEinbQj56jhKzzOKek', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLwQEKO0zW4F45u1fCicSbJrMJdfc7ERv92ZuhwPApzHRhyDV8fS2bknfxh1CbGqrE3wiblx9zQeU1g/132', '0', null, '1', '2019-09-02 10:25:02', '27.10.60.71', '1', '2019-09-02 10:25:02', '2019-09-02 10:25:02');
INSERT INTO `unimall_user` VALUES ('338', null, null, '1', 'oiAuI5NMhnPTjNg7p__6SmwBHLPU', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIeTbiaxRAOWlMAHoHoy0TBI0SLfxrWBN2ibIXxUgAy8IYrmsxibeKvEWoZIgMu0Q9osVHzcVhSibQ9Ng/132', '0', null, '1', '2019-09-02 12:35:43', '27.10.60.71', '1', '2019-09-02 12:35:43', '2019-09-02 10:29:17');
INSERT INTO `unimall_user` VALUES ('339', null, null, '1', 'oiAuI5ILudL12Li9oAY_FNf0V-RE', null, 'https://wx.qlogo.cn/mmopen/vi_32/0dzg4yTyEJc0ibGTyDRnYjXqQibiasylAfGmvgI4oHI4skGysGFVcp3cFDW6HhOHC2ojqYy2mfiaL4tMnlb7QUj7XQ/132', '0', null, '1', '2019-09-02 10:31:04', '27.10.60.71', '1', '2019-09-02 10:31:04', '2019-09-02 10:31:04');
INSERT INTO `unimall_user` VALUES ('340', '15820006163', '$1$1582000$e89QLMdDhkyg7coEmR3YD1', '0', null, '666', null, '0', null, '1', '2019-09-11 09:14:02', '27.10.60.71', '1', '2019-09-02 10:43:32', '2019-09-02 10:43:07');
INSERT INTO `unimall_user` VALUES ('341', null, null, '1', 'oiAuI5INPQ4Z9lrb-s4F65VHi-OQ', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTK40cFN9aJ7Vyjp38nUgkJAGPpXAtH3CKv8lADKeaxg1eaHZibSYohadPPZahvFunHzlVGgvt8WnPA/132', '0', null, '1', '2019-09-02 11:42:00', '27.10.60.71', '1', '2019-09-02 11:42:00', '2019-09-02 11:42:00');
INSERT INTO `unimall_user` VALUES ('342', null, null, '1', 'oiAuI5InjGB-W4Wr5d12X_V9aCsY', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLsicicIm0AoZtWhB3uVHjAhr2EGqBTSCDzjEYkaicyMTBQvxmPPOs6eWempVT2wIAUkyBicBcEzT5sKQ/132', '0', null, '1', '2019-09-02 13:13:19', '27.10.60.71', '1', '2019-09-02 13:13:19', '2019-09-02 13:13:19');
INSERT INTO `unimall_user` VALUES ('343', null, null, '1', 'oiAuI5JGshlyOm8F6s7avUU5yu8I', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eprCUrlTgJeNpqlsQ8rxwwfhZ0S05B1B8EY31mNvsNyODVDhB0nHhlfrpS7Smnv4tkfD4rHS0u1iaw/132', '0', null, '2', '2019-09-02 14:05:04', '27.10.60.71', '1', '2019-09-02 14:05:04', '2019-09-02 14:05:04');
INSERT INTO `unimall_user` VALUES ('344', null, null, '1', 'oiAuI5PYzfE2ZLB-NC4o6VvS3Rb4', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLygZkPHuOuic54J75h4ia9F7MuicJgtytzoXWkxnycGM2CuOWeWy3ickHmuf474fWQ3iaUDumWfpQDaxQ/132', '0', null, '1', '2019-09-02 16:44:16', '27.10.60.71', '1', '2019-09-02 16:44:17', '2019-09-02 16:44:16');
INSERT INTO `unimall_user` VALUES ('345', null, null, '1', 'oiAuI5BRnf4oEdelRx80voP5BwXY', null, 'https://wx.qlogo.cn/mmopen/vi_32/BKiaib4tNuibBhLZllDoTIBQNwlF7HtRnysaTxSUcvxke9IzmKYKWckyXKZKeeKib9nXQQmib3A8C6VF9kiayqDzsWPQ/132', '0', null, '1', '2019-09-03 03:46:55', '27.10.60.71', '1', '2019-09-03 03:46:55', '2019-09-03 03:46:55');
INSERT INTO `unimall_user` VALUES ('346', null, null, '1', 'oiAuI5LCt4ENAr_Wt3Qd3FoFxH7I', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIhXCp4ta9tKqvtBEbKL4LUtSrnU73JVjnDynFYwEGtP8ZWA5qfVAKjNHbOOgvrZmZoBxap1rRBgw/132', '0', null, '1', '2019-09-03 11:02:14', '27.10.60.71', '1', '2019-09-03 11:02:14', '2019-09-03 11:02:14');
INSERT INTO `unimall_user` VALUES ('347', null, null, '1', 'oiAuI5CuOIg1G_8DTwFhQBSp8MLk', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJjmm55tEEf3m4t9UPZibewmKF8sewSDdAlgVOq8rBkjedpB2PzG3lhl9A5P2twUxURpwib1HK22CPQ/132', '0', null, '1', '2019-09-03 11:11:57', '27.10.60.71', '1', '2019-09-03 11:11:57', '2019-09-03 11:11:57');
INSERT INTO `unimall_user` VALUES ('348', null, null, '1', 'oiAuI5PzyWImmvIMfBSJ8MPT88GU', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJOntKGNt1fvUgy6AVZVXXX4sXQvgvIvWR4UPuCCIKSsKD3I3oOHoVbgibPf7iakeaChwc5picWG79kQ/132', '0', null, '1', '2019-09-03 14:15:31', '27.10.60.71', '1', '2019-09-03 14:15:31', '2019-09-03 14:15:31');
INSERT INTO `unimall_user` VALUES ('349', null, null, '1', 'oiAuI5MNPHZQqYnQepa4DTbYjCdw', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKbUic06Aibsg0GqGQzbxVicic9ch4p86BPAXCQXiaymlnNgkgAXDKh2Eib2W5yzmxa2d4cBroibZDs28ickw/132', '0', null, '1', '2019-09-03 14:20:32', '27.10.60.71', '1', '2019-09-03 14:20:32', '2019-09-03 14:20:32');
INSERT INTO `unimall_user` VALUES ('350', null, null, '1', 'oiAuI5CQfYOfAk5vlV6oES9hakRw', null, null, '0', null, '-1', '2019-09-03 14:51:28', '27.10.60.71', '1', '2019-09-03 14:51:28', '2019-09-03 14:51:28');
INSERT INTO `unimall_user` VALUES ('351', null, null, '1', 'oiAuI5HBJjiC7CW2zArsY5jiPVv4', null, 'https://wx.qlogo.cn/mmopen/vi_32/wkEn6344kLxIu29mCazEkmricvftpNMu6tPQOpydav9lgia3U3IiaYms72Bk4NZ0AiaMfFERx9YgghnE04dteNJ85A/132', '0', null, '1', '2019-09-03 15:00:36', '27.10.60.71', '1', '2019-09-03 15:00:37', '2019-09-03 15:00:36');
INSERT INTO `unimall_user` VALUES ('352', null, null, '1', 'oiAuI5DbOw-eFYlKAe4sTBmC5fMA', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqLjJrgib5wN0uIoNerOxqqwhsCPtAowTQ320nk52lh1VbB71Nwj0LicGfFkia4BmCVicKlA7KWVndVBA/132', '0', null, '1', '2019-09-03 16:56:58', '27.10.60.71', '1', '2019-09-03 16:56:59', '2019-09-03 16:56:58');
INSERT INTO `unimall_user` VALUES ('353', null, null, '1', 'oiAuI5LhDZpf-10uizR6BeMAQm-M', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKIMTKCP4Cz12UULAGOY21sB3RpUWQibBogG8GmwOzl4EJm51RhN3XTDibcFrDwImzeibYl58bHMCS9A/132', '0', null, '1', '2019-09-03 17:02:30', '27.10.60.71', '1', '2019-09-03 17:02:30', '2019-09-03 17:02:30');
INSERT INTO `unimall_user` VALUES ('354', null, null, '1', 'oiAuI5CRAtbRcsQMs3zrSX8cLoDI', null, 'https://wx.qlogo.cn/mmopen/vi_32/umjkN6lcc1smgN2ibtwurQlJE9wYXQiansatMPl64goPwqo7bKPPQQMA8Pr9atgTdpia0fbDSEKvab9pFichAiak7Ig/132', '0', null, '1', '2019-09-04 08:49:39', '27.10.60.71', '1', '2019-09-04 08:49:40', '2019-09-04 08:49:39');
INSERT INTO `unimall_user` VALUES ('355', null, null, '1', 'oiAuI5GlF__rJSSlOGKKLjBDnpbM', null, 'https://wx.qlogo.cn/mmopen/vi_32/oUicWDFmZmf8TRvFPAkDYcSALs8eAiby6iakPlnjicjRhP13Kpxaiam60KJdrPAF1XynVpVTNaia3XgQdp3gXEwyzrIA/132', '0', null, '1', '2019-09-11 17:47:45', '27.10.60.71', '1', '2019-09-11 17:47:45', '2019-09-04 10:10:42');
INSERT INTO `unimall_user` VALUES ('356', null, null, '1', 'oiAuI5Alib_6udRo6FuyEpBa66Zk', null, 'https://wx.qlogo.cn/mmopen/vi_32/zBOJHoNowqbvP0uwd6KdDD3dWRLYiaqenN0eAbDpSzsAATB3y35icx0GmWh95XKYVVs1MDjKHy23iaxRPpe6avib6A/132', '0', null, '1', '2019-09-04 15:30:16', '27.10.60.71', '1', '2019-09-04 15:30:16', '2019-09-04 15:30:16');
INSERT INTO `unimall_user` VALUES ('357', null, null, '1', 'oiAuI5DFMbndnx4_4H_AzSW8WxdI', null, 'https://wx.qlogo.cn/mmopen/vi_32/NJiaS2TRoQAvRKj4N5KqSuvfjWDCloYAgeY9POQKFtk8zuZEu8CGENHhZCE25UibMUKHdpm5RcVJzYuy9ucickk0w/132', '0', null, '0', '2019-09-18 21:16:24', '27.10.60.71', '1', '2019-09-18 21:16:25', '2019-09-04 17:28:10');
INSERT INTO `unimall_user` VALUES ('358', null, null, '1', 'oiAuI5EdcswqFxS86A4XUGcqiHcw', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83erPMtAfnQdpx013jcPibGZkNnRLYFN6lwPuCVdIibP5pPv3uKQaISAkgUTUUJZcciaLzwrF8VlylGhfQ/132', '0', null, '1', '2019-09-05 14:59:43', '27.10.60.71', '1', '2019-09-05 14:59:43', '2019-09-05 14:59:43');
INSERT INTO `unimall_user` VALUES ('359', null, null, '1', 'oiAuI5Nb0MtSN2e-RE90MW5-cGeA', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJ1bf1wUGI2KnKRANSrdicPp4mvQLxeRA8LgstmMqaHeYqNicqSGOD0wLIjWbXSszmgicjyV0WZETfAQ/132', '0', null, '1', '2019-09-06 15:10:48', '27.10.60.71', '1', '2019-09-06 15:10:49', '2019-09-05 15:40:54');
INSERT INTO `unimall_user` VALUES ('360', null, null, '1', 'oiAuI5LIflV040G55q5G14iatOXM', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLpSwG8Qg23TSAhKT2UdhOI62eJDKzxYFh1QR6zgWaleAakGWbWVqKsXLtIwq7D7lkuXSiaiatR2MSw/132', '0', null, '1', '2019-09-05 17:38:34', '27.10.60.71', '1', '2019-09-05 17:38:34', '2019-09-05 17:38:34');
INSERT INTO `unimall_user` VALUES ('361', null, null, '1', 'oiAuI5PxTTC7qYPq-bE8K6DeabU4', null, 'https://wx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEK06hv5DrA8p1ibWWyycpPm9qrUvclqjqX1054reVJV630UTbAgqHou8Fh1ldtkSN1K0pqzgOiaCIicg/132', '0', null, '1', '2019-09-05 19:18:26', '27.10.60.71', '1', '2019-09-05 19:18:27', '2019-09-05 19:18:26');
INSERT INTO `unimall_user` VALUES ('362', null, null, '1', 'oiAuI5BYebRW4uexCLQqBp3VlkDQ', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoPnhonGHcvaRrQRv1lsQHVzybXTBGbp7dLS1eaOeD5kDvhkHwMQCicvscSFp65ibqkWhFsHW5ThnIg/132', '0', null, '1', '2019-09-05 22:51:56', '27.10.60.71', '1', '2019-09-05 22:51:56', '2019-09-05 22:51:56');
INSERT INTO `unimall_user` VALUES ('363', null, null, '1', 'oiAuI5CujGe_PaIPu5TxRSPEbc3c', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIlhicoqtARrNj6T62Zaksv2ibicoQwq4eVyibIPz78ibmA1ajGrhFmBVKBtPbT3l238hFB2rGTMxib6Bug/132', '0', null, '1', '2019-09-06 09:46:56', '27.10.60.71', '1', '2019-09-06 09:46:56', '2019-09-06 09:46:56');
INSERT INTO `unimall_user` VALUES ('364', null, null, '1', 'oiAuI5KkvO3PxfjinCBqf5JFYfVw', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKiaqdojQuvn9udUhribmut3ynT3dB87kdGbhQya3BKdCycez2he1nAdEeEOuPIibQ2VmVIMPbOeibh3A/132', '0', null, '1', '2019-09-06 10:50:01', '27.10.60.71', '1', '2019-09-06 10:50:01', '2019-09-06 10:50:01');
INSERT INTO `unimall_user` VALUES ('365', null, null, '1', 'oiAuI5FNBqq6vDyRfuBx0lxmb4Yg', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLsicicIm0AoZtSrUciak1yXj3y1gvwT1ic9s7p4GYwibCEN8Wzw9ibRLoeJHs1cHia6t3Fggw7viaiaKIXX9Q/132', '0', null, '1', '2019-09-06 10:53:12', '27.10.60.71', '1', '2019-09-06 10:53:58', '2019-09-06 10:53:12');
INSERT INTO `unimall_user` VALUES ('366', null, null, '1', 'oiAuI5MfMOoY7kiBg-TshDZAjg08', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTI6kdVK5dNmVwANxvyVwQnoCLvJgscNaz6CiagbUMibBjjzRbCeSm16rCkJUUakaXp7E63Qwtlnl68g/132', '0', null, '1', '2019-09-06 11:07:38', '27.10.60.71', '1', '2019-09-06 11:07:38', '2019-09-06 11:07:38');
INSERT INTO `unimall_user` VALUES ('367', null, null, '2', 'oRrdQt3wSh7tLaAkGTYaT_XoYdG0', 'Soul-Type', 'http://thirdwx.qlogo.cn/mmopen/vi_32/JRsX9GSGiaPfbzk8eeq0icWqrjt4xt3zzIJJrYJfUtw1wlAVI7dibhvGXUS5PgfZgBu3WUr3CpteaibVzqhOrMGkiaw/132', '0', null, '1', '2019-09-18 20:44:59', '27.10.60.71', '1', '2019-09-18 20:44:59', '2019-09-06 11:52:29');
INSERT INTO `unimall_user` VALUES ('368', null, null, '1', 'oiAuI5FXOEUkfUtj3a0OWWXS_clA', null, 'https://wx.qlogo.cn/mmopen/vi_32/ZEd2yHgmdjBnt64ocwtqL2U2IhVjjmBpLH7fNvzLMVQryanedlVfibqg5uFth7Ave6ibfvJoLzjhF8qznwHUBEBg/132', '0', null, '1', '2019-09-06 13:09:43', '27.10.60.71', '1', '2019-09-06 13:09:43', '2019-09-06 13:09:43');
INSERT INTO `unimall_user` VALUES ('369', null, null, '1', 'oiAuI5HqGWahNdfonxHzR59h3fn8', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoz88bFEhWYuEFZ0zvYqhXjG7iatPXZbEIAyuwggBzwhAicTU9YkLEl7q0k2IxnqmoHhWibxSt9yt7KQ/132', '0', null, '1', '2019-09-06 14:53:50', '27.10.60.71', '1', '2019-09-06 14:53:51', '2019-09-06 14:53:50');
INSERT INTO `unimall_user` VALUES ('370', null, null, '1', 'oiAuI5N3htCmDfh79hd5RnYACMos', null, 'https://wx.qlogo.cn/mmopen/vi_32/LjlicotBRStJLtdQmv506KdiaJcywxI4sCmRvlwLe9qwlQErJwZfTibhIzcH5LZf9efgIufibAj8TuVEQ3OTcGib9Hg/132', '0', null, '1', '2019-09-06 15:50:29', '27.10.60.71', '1', '2019-09-06 15:50:29', '2019-09-06 15:50:29');
INSERT INTO `unimall_user` VALUES ('371', null, null, '1', 'oiAuI5EH_S5VUhFBDHP1Hum96qKE', null, 'https://wx.qlogo.cn/mmopen/vi_32/K6v7bVicvYokEY7AAAGD0icIH0BdT29ALiczOvm1hVTpGLUzWibZQ8p7BKw4s3bqriaN9xtUy0aS9RK0KUDa47Uj6IQ/132', '0', null, '1', '2019-09-06 16:23:10', '27.10.60.71', '1', '2019-09-06 16:23:10', '2019-09-06 16:23:10');
INSERT INTO `unimall_user` VALUES ('372', '18758186347', '$1$1875818$jykOFE2xuDDGsenXdVBd..', '0', null, null, null, '0', null, '-1', '2019-09-06 17:59:10', '27.10.60.71', '1', '2019-09-06 17:59:10', '2019-09-06 17:59:10');
INSERT INTO `unimall_user` VALUES ('373', null, null, '1', 'oiAuI5J7fLtHoZlADXEg5uqVP704', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTI5na6qtIrtPekXDssqf4YNXuQomG1icV9XS6znhbg7RcKOvqXuHPtUWBua4icgPvRHpkVJxI8vbQqA/132', '0', null, '1', '2019-09-06 21:37:34', '27.10.60.71', '1', '2019-09-06 21:37:34', '2019-09-06 21:37:34');
INSERT INTO `unimall_user` VALUES ('374', null, null, '1', 'oiAuI5IuSifGmVRqMouwPRJ-f2sc', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJxNpWRCIaJib36Y8wmQfknsd4WcOOqZkKyOhXy7K9oMAdfqDe4gYOUTcJd7esufWdDU52a56ctrpQ/132', '0', null, '1', '2019-09-07 12:08:55', '27.10.60.71', '1', '2019-09-07 12:08:55', '2019-09-07 12:08:12');
INSERT INTO `unimall_user` VALUES ('375', null, null, '1', 'oiAuI5MeNlifAlUNpq1_sGKr7g7Q', null, 'https://wx.qlogo.cn/mmopen/vi_32/spNUJKUTzWVyKWvwTMqoQGicChpSM7k07oiaXZGcDALoTaqbyuXccibXXFBaEbQ6pK9wJ2EVmcFI81ogQSC3h6PGQ/132', '0', null, '1', '2019-09-07 16:11:25', '27.10.60.71', '1', '2019-09-07 16:11:26', '2019-09-07 16:11:25');
INSERT INTO `unimall_user` VALUES ('376', null, null, '1', 'oiAuI5My3zuFfw9CZfnARyz852As', null, null, '0', null, '-1', '2019-09-08 17:51:28', '27.10.60.71', '1', '2019-09-08 17:51:29', '2019-09-07 21:34:37');
INSERT INTO `unimall_user` VALUES ('377', null, null, '1', 'oiAuI5BVDyr6msXSiF5WrgHwKuZg', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83erlbUoazL05oBRjz51dibl2nVLEVw0icNCOTQzhm9YwfbU8Xmfh8XnACr94BTsEvyNzAWvBqH4pVibuw/132', '0', null, '1', '2019-09-08 01:42:36', '27.10.60.71', '1', '2019-09-08 01:42:36', '2019-09-08 01:42:36');
INSERT INTO `unimall_user` VALUES ('378', null, null, '1', 'oiAuI5KJCXRKctanXMgymOby4Ksw', '嗯', 'https://wx.qlogo.cn/mmopen/vi_32/agUicKIy7jpH0lc9E07NqaOa771TWxTHCa1ZjJ49RwWicESJVOKuDTTAiaF06KibUxyo9UuIoAaSRWzmRGIx2E1gAQ/132', '0', null, '1', '2019-09-08 11:20:42', '27.10.60.71', '1', '2019-09-08 11:20:42', '2019-09-08 10:10:48');
INSERT INTO `unimall_user` VALUES ('379', null, null, '1', 'oiAuI5MRrojyr1AqzbKWln6kUiOo', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqViaxic2E8N4icrLkQyWE4t5q0I7PPA5bKj1C0uzb36pDhAJuaGuA7CMYPqXtLa6JiaA83S3GibUXhOhA/132', '0', null, '1', '2019-09-08 15:00:21', '27.10.60.71', '1', '2019-09-08 15:00:21', '2019-09-08 15:00:21');
INSERT INTO `unimall_user` VALUES ('380', null, null, '1', 'oiAuI5D665qKRGjHi8_AGZBXEkxY', null, 'https://wx.qlogo.cn/mmopen/vi_32/uWOy6rvJoEGAicibFLdUp7XHJYqsdtctbQRQM509CwrorcYeIBM224KiatYe5xrIxnql8oyfAVot7kfwLuapiceibmw/132', '0', null, '1', '2019-09-08 20:12:56', '27.10.60.71', '1', '2019-09-08 20:12:56', '2019-09-08 20:12:56');
INSERT INTO `unimall_user` VALUES ('381', null, null, '1', 'oiAuI5F4cHt3xm9NGc4A2hXPQH_8', null, 'https://wx.qlogo.cn/mmopen/vi_32/VGbg1PhNF6YUXA3cBFQFM1qZianafEn0vn3r4oeh4BG35caiafiaEflB1icuCrnpoib49UnHBic4tiazcr2DTRiadpLH6A/132', '0', null, '0', '2019-09-09 16:46:13', '27.10.60.71', '1', '2019-09-09 16:46:13', '2019-09-09 16:46:13');
INSERT INTO `unimall_user` VALUES ('382', null, null, '2', 'osTQe6OqkOcPXGfS8mn4tEddT7lo', '小金鱼', 'http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLO39uBW4E1WyiarSayfXkVEHM53IBbTPULSJr6nW9pxUicDrLFKJldnmUXoXByic9yCfGVGLB75BdBQ/132', '0', null, '1', '2019-09-11 07:40:46', '27.10.60.71', '1', '2019-09-11 07:40:47', '2019-09-09 17:58:05');
INSERT INTO `unimall_user` VALUES ('383', null, null, '1', 'oiAuI5ELrnviNqX7huxP_RGGl1GA', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIavBvxZz8M4SCGAkzMjbDS3JibStficwIwAGetaEZqmyW3tYZIEJROiau1A5ZZ7fwv2PjlOsqYJIEicQ/132', '0', null, '1', '2019-09-09 18:44:25', '27.10.60.71', '1', '2019-09-09 18:44:25', '2019-09-09 18:44:25');
INSERT INTO `unimall_user` VALUES ('384', null, null, '1', 'oiAuI5H-k8pQ8_SXwdxs0bkS3pB8', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqGTPGPZZQeljDxp2ut5kqf634TDeNneM4lWnZNSOnxVvgAWfP0JGWEibawKnTQfTibxup5Pqoaic5bA/132', '0', null, '1', '2019-09-09 19:03:02', '27.10.60.71', '1', '2019-09-09 19:03:02', '2019-09-09 19:03:02');
INSERT INTO `unimall_user` VALUES ('385', null, null, '1', 'oiAuI5E9cNNN_Gs4g7M_xasTmGKE', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIhXCp4ta9tKrsgibjb8cDOjTVjxdzePia04jA6ib43Ft0bZt1KZUr6Wq53SlWvjRCeiabS97bv50C6vg/132', '0', null, '1', '2019-09-09 19:05:12', '27.10.60.71', '1', '2019-09-09 19:05:12', '2019-09-09 19:05:12');
INSERT INTO `unimall_user` VALUES ('386', null, null, '1', 'oiAuI5IMH2DP2MMzNcfvdVot2reI', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83epmiax6QzjpTmp2tlvCp9WxJOticDGmH6XEet8UR05Oic3nKjJ5icGicPNbnw8ib4PFAa1syDyGnek0JhlA/132', '0', null, '1', '2019-09-10 08:43:48', '27.10.60.71', '1', '2019-09-10 08:43:48', '2019-09-10 08:43:48');
INSERT INTO `unimall_user` VALUES ('387', '13213552127', '$1$1321355$I6o/.Tf1aDRc4oZLeXQGV.', '0', null, null, null, '0', null, '-1', '2019-09-10 10:32:53', '27.10.60.71', '1', '2019-09-10 10:22:57', '2019-09-10 10:22:57');
INSERT INTO `unimall_user` VALUES ('388', '15036326132', '$1$1503632$eF4Bm1V0LNCMUXMOH7JS1.', '0', null, '非凡', null, '0', null, '-1', '2019-09-10 10:40:51', '27.10.60.71', '1', '2019-09-10 10:34:07', '2019-09-10 10:33:13');
INSERT INTO `unimall_user` VALUES ('389', null, null, '1', 'oiAuI5BRWP7KntDNuVwg53__PyFo', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLZwicQJiby5pMJKRKPgrnZSDB9s6azrC6H0picKqxmHn14iaiahNa6zNM1ia53Nh00RcrfEpibYGIgq2iaWg/132', '0', null, '1', '2019-09-10 10:49:52', '27.10.60.71', '1', '2019-09-10 10:49:52', '2019-09-10 10:49:52');
INSERT INTO `unimall_user` VALUES ('390', null, null, '1', 'oiAuI5AQhNOG7KMFPRowSVdsPipI', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83epNUbhUcJN0ykJfx37vmzxsTIc2qoa27iaDXLF0Y4FjKQJYXYPIricGMYibTqC9B5k431LF2ozd7EQHQ/132', '0', null, '1', '2019-09-10 11:02:47', '27.10.60.71', '1', '2019-09-10 11:02:47', '2019-09-10 11:02:47');
INSERT INTO `unimall_user` VALUES ('391', null, null, '1', 'oiAuI5ImwQz-EnHLTOCwatNKHtvo', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKUWdGOkmDx6icSX38icNLmfiaOdbbH4CXxtWWUMEicwZ1a6DeicrQPSfPMCBA4IMicFQS1zKeTbvq7qhWA/132', '0', null, '1', '2019-09-10 11:05:38', '27.10.60.71', '1', '2019-09-10 11:05:38', '2019-09-10 11:05:38');
INSERT INTO `unimall_user` VALUES ('392', null, null, '1', 'oiAuI5KdwoPMuGzQd5b_AH8jbzuc', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKb5BzdGZbSYKicoJibNWmcYobsibXfE8ficm60gGrfkDibrS6RPnkTHVVwr2THP9kFUq6Pd8icd7wIktfQ/132', '0', null, '1', '2019-09-10 15:29:14', '27.10.60.71', '1', '2019-09-10 15:29:14', '2019-09-10 15:29:14');
INSERT INTO `unimall_user` VALUES ('393', null, null, '1', 'oiAuI5DZegOYdKKmLA353oX8sZH0', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLWoErbcp4EfK5Zf5sygAkvjzGE2PeGooHD8wD0saHXvicFPOfGEreSk1DVRRQ06XkSbfEGia46J9bQ/132', '0', null, '1', '2019-09-11 13:59:37', '27.10.60.71', '1', '2019-09-11 13:59:38', '2019-09-10 16:33:18');
INSERT INTO `unimall_user` VALUES ('394', null, null, '1', 'oiAuI5GZzo21HBaVq8Pe7MnnwI2c', null, 'https://wx.qlogo.cn/mmopen/vi_32/AiarZyFyseFZ3nLmOMvBXLLjnhv3hcSN1Y2khwoia7msA2mxq0uwrlrjQVtpJib0jpvsd5l5Cic7haVzJ6WSjLuDOw/132', '0', null, '1', '2019-09-10 16:38:04', '27.10.60.71', '1', '2019-09-10 16:38:04', '2019-09-10 16:38:04');
INSERT INTO `unimall_user` VALUES ('395', null, null, '1', 'oiAuI5LqrUSvkFvC2BHvCEyZ3G-E', null, 'https://wx.qlogo.cn/mmopen/vi_32/zBOJHoNowqbvP0uwd6KdDGzhjGwKKIDRKEEEoESGClwoiazvQHCgjGw5oaQCtEacft4XmaA62hL3HZ8uYVl7ESA/132', '0', null, '1', '2019-09-10 17:09:45', '27.10.60.71', '1', '2019-09-10 17:09:45', '2019-09-10 17:09:45');
INSERT INTO `unimall_user` VALUES ('396', null, null, '1', 'oiAuI5CsAM6sbhZ_iDSuwWjvudGg', null, null, '0', null, '-1', '2019-09-10 17:45:35', '27.10.60.71', '1', '2019-09-10 17:45:35', '2019-09-10 17:45:35');
INSERT INTO `unimall_user` VALUES ('397', null, null, '1', 'oiAuI5Ag8L-Fm4374Wbs_6tPRFKk', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKmtBMS7f36dLP6H2sozkTkl6DZp6DwejUEMnp8Xy90rEZvgopzRKTH4jMSVWOljsS335TJPKdS6w/132', '0', null, '1', '2019-09-10 18:04:11', '27.10.60.71', '1', '2019-09-10 18:04:11', '2019-09-10 18:04:11');
INSERT INTO `unimall_user` VALUES ('398', null, null, '1', 'oiAuI5Gf_opbXbY2v6fVOmmGkVII', null, 'https://wx.qlogo.cn/mmopen/vi_32/umjkN6lcc1s3noWibNWmn2ZxXyiak2icPwhXOicJUth22njs8kvEVwKdP2fcR2WsMz25VnpGQoicbIsyMoCfRox83Xw/132', '0', null, '1', '2019-09-11 03:01:14', '27.10.60.71', '1', '2019-09-11 03:01:14', '2019-09-11 02:18:09');
INSERT INTO `unimall_user` VALUES ('399', null, null, '1', 'oiAuI5Md__WLWpO4A6CakgXgyGvw', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIicdpDquSgVaE17Bcq8mJRvjFyaYlXV95KoJX4uiaGyf6zwJjZ7EdJRbG7TZ363VtAKENTr27wlUMg/132', '0', null, '1', '2019-09-11 10:11:04', '27.10.60.71', '1', '2019-09-11 10:11:04', '2019-09-11 10:11:04');
INSERT INTO `unimall_user` VALUES ('400', null, null, '1', 'oiAuI5Fwr19dLs9DNpQuKgXr9orI', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLDKPNzVFAucypT15N2k5MeJmEgmKfkDlNzDRebUm8rOLH1hUPJpXicX0fSNyaXYjVu4Rk8n3ZfDrQ/132', '0', null, '1', '2019-09-11 11:46:12', '27.10.60.71', '1', '2019-09-11 11:46:12', '2019-09-11 11:46:12');
INSERT INTO `unimall_user` VALUES ('401', null, null, '1', 'oiAuI5Em3q4N_9WbnCZFsm6tlt1s', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqIia4Df2GsiaJ5YZPPde4YDib7CmZpAgbMVlnCYknBMmWFuNcysB4AXt7ILarQb3oXHMWymsoVwMh0g/132', '0', null, '1', '2019-09-11 11:56:42', '27.10.60.71', '1', '2019-09-11 11:56:42', '2019-09-11 11:56:42');
INSERT INTO `unimall_user` VALUES ('402', null, null, '1', 'oiAuI5D1BnNWf_GlDon4EF1ki0eo', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqE7apAuEtFhQ0ulAm1b5yLKNe4NSbXaDzZLtuUAvpvJJwblzCaCFfGAicuRFvJ5TvsmU3Po7A8M2Q/132', '0', null, '1', '2019-09-16 11:57:08', '27.10.60.71', '1', '2019-09-16 11:57:08', '2019-09-11 15:49:17');
INSERT INTO `unimall_user` VALUES ('403', null, null, '1', 'oiAuI5Hzg0-PoUMlZ_Q0r-3OlVIs', null, null, '0', null, '-1', '2019-09-11 16:53:06', '27.10.60.71', '1', '2019-09-11 16:53:06', '2019-09-11 16:53:06');
INSERT INTO `unimall_user` VALUES ('404', null, null, '1', 'oiAuI5CFldPvzca_YmGxmc-AQO3A', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqBYD3lFhbm50IYzO7ICV87ibbk3jOp7C9ib5QEjtV9ZeO3SHUXAaAJEpK4oHmcBDjC2XQl6lhTOyZA/132', '0', null, '1', '2019-09-11 19:44:26', '27.10.60.71', '1', '2019-09-11 19:44:27', '2019-09-11 19:44:26');
INSERT INTO `unimall_user` VALUES ('405', null, null, '1', 'oiAuI5PS41U-eQoQbiAwE52kG9us', null, 'https://wx.qlogo.cn/mmopen/vi_32/nOs9YyFciaKImp1oTUAqre6MW4HUTM0X5t4yy3DeC5KFwBpafmXficWjQsKnMSp8Ids4tXmYyEa5NicsnE7hmvHuQ/132', '0', null, '2', '2019-09-12 10:18:24', '27.10.60.71', '1', '2019-09-12 10:18:24', '2019-09-12 10:18:24');
INSERT INTO `unimall_user` VALUES ('406', null, null, '1', 'oiAuI5Nq50TJn12S-wRaxwXjXdKI', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIy8a7Qv45JvyyqJ7xcveoLsycKQShuB8wuOicjno4PltyGKEpgSAza3YtRcClOz8Xs0XLBdjB4zww/132', '0', null, '2', '2019-09-12 11:43:43', '27.10.60.71', '1', '2019-09-12 11:43:43', '2019-09-12 11:43:43');
INSERT INTO `unimall_user` VALUES ('407', null, null, '1', 'oiAuI5OpCGC2-RDL3zkOotxH13bI', null, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q3auHgzwzM5iaqAGXvVh7HAGicZg8PkQzM4cAwOrMia1Sepkbyms2Nky80LG7udArD0XibDnMD48dIPeCgNyaQdjmA/132', '0', null, '1', '2019-09-12 13:12:07', '27.10.60.71', '1', '2019-09-12 13:12:08', '2019-09-12 13:12:07');
INSERT INTO `unimall_user` VALUES ('408', null, null, '1', 'oiAuI5NL0PNkMXMyle2Hw0iNMqRM', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIfibOclEoPxw7r3hp2EZ4m3BnTXtvnmvt6mG6lI7k7c1E2fFpmlW51I9v4BRY1oJstK6bCd9p2RmA/132', '0', null, '1', '2019-09-12 13:46:29', '27.10.60.71', '1', '2019-09-12 13:46:29', '2019-09-12 13:46:29');
INSERT INTO `unimall_user` VALUES ('409', null, null, '1', 'oiAuI5IQ6OnhiB8ROOcUQtKOxQu0', null, 'https://wx.qlogo.cn/mmopen/vi_32/KQX6TickePupty87TUpAISNdPkpBkfga2fZvA9mCZwKEIdK2ywR1FpicibMx4JaOBUkgcw57HJTg6VftUeow25LpA/132', '0', null, '1', '2019-09-12 16:51:09', '27.10.60.71', '1', '2019-09-12 16:51:09', '2019-09-12 16:51:09');
INSERT INTO `unimall_user` VALUES ('410', null, null, '1', 'oiAuI5P0EbIP576ReZrjVSuuOjCY', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83epc44TEbOQZyrGdElulzypDStSBLOTSqib2rJosPWXhfxpXMOxGDyywu0ZCP1ic5ICKEJ00aWg0GXRg/132', '0', null, '1', '2019-09-12 17:09:17', '27.10.60.71', '1', '2019-09-12 17:09:17', '2019-09-12 17:09:17');
INSERT INTO `unimall_user` VALUES ('411', null, null, '2', 'osTQe6CZx3BnLtoEDU-Vg9UcRRBE', '胡健-Jack.h', 'http://thirdwx.qlogo.cn/mmopen/vi_32/Mm2t9EHvYFmicU4b30GDupMeUHn6myfDibUHWFC78iaEH1ju0SKHjfU18VJtLeVlGCp81iaZribb5IURRpOA0hvJyNw/132', '0', null, '1', '2019-09-16 14:39:21', '27.10.60.71', '1', '2019-09-16 14:39:21', '2019-09-12 19:42:29');
INSERT INTO `unimall_user` VALUES ('412', null, null, '1', 'oiAuI5DkGWFDgQHAhhz3Znh64tko', null, 'https://wx.qlogo.cn/mmopen/vi_32/gZagmeUAqZuvpiadUP9k9vyjh3rmXibyYHswMcXXZpny2x4quWwzVNia1WABWFKVvuXpZ3Av56Yib9FmrWhgycsBibA/132', '0', null, '1', '2019-09-12 19:52:56', '27.10.60.71', '1', '2019-09-12 19:52:57', '2019-09-12 19:52:56');
INSERT INTO `unimall_user` VALUES ('413', null, null, '1', 'oiAuI5PDGqnKVPrt9-Uj-GgFFrng', null, 'https://wx.qlogo.cn/mmopen/vi_32/wJFzptJRadoiakRPdXGvB2R7aOicmNJAqiagBlMF7icA8GibRpJnIIAhXrdxgqBVrOUsFWMksF2HBjxzuIic72jKficyA/132', '0', null, '1', '2019-09-17 20:31:06', '27.10.60.71', '1', '2019-09-17 20:31:07', '2019-09-12 20:27:14');
INSERT INTO `unimall_user` VALUES ('414', null, null, '1', 'oiAuI5BhukfPGM88hwH1UG-jhEKU', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIBC94guaFqiaYff9g5pC1YFjdX4Y3JenGf61sBtJdVwmOjfZxpxmqJGavpOIlpN005eILxKr3y1mA/132', '0', null, '1', '2019-09-13 12:29:08', '27.10.60.71', '1', '2019-09-13 12:29:08', '2019-09-13 12:29:08');
INSERT INTO `unimall_user` VALUES ('415', null, null, '1', 'oiAuI5PAtNHkMzQlFqDBSHiTTEUg', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqyM0p4eDsJJ9BALicRACFdzpEfwSXJNHsyAa69tRsKFv3ic5JJk4Pt9y9hhFwiblX1ZLI8lzSBW3tuw/132', '0', null, '1', '2019-09-13 15:03:34', '27.10.60.71', '1', '2019-09-13 15:03:35', '2019-09-13 15:03:34');
INSERT INTO `unimall_user` VALUES ('416', null, null, '1', 'oiAuI5Azg7yBEqmbv5sUY7Fb95x4', null, 'https://wx.qlogo.cn/mmopen/vi_32/oUicWDFmZmf8TRvFPAkDYcYCibibQTicNlpp8a4icNPaSes1bQecoBH6snjNDm6ia1icmaKyU6x6fjicibibzoWFNqEoDW9g/132', '0', null, '1', '2019-09-13 21:02:13', '27.10.60.71', '1', '2019-09-13 21:02:14', '2019-09-13 21:02:13');
INSERT INTO `unimall_user` VALUES ('417', null, null, '1', 'oiAuI5BRc9z5tKTkNzaOSsxj730Q', null, 'https://wx.qlogo.cn/mmhead/pwIibagokcTkKvibEvUSBEt8L7hxJvJfU73FGLY01VsoE/132', '0', null, '2', '2019-09-14 03:18:55', '27.10.60.71', '1', '2019-09-14 03:20:18', '2019-09-14 03:18:55');
INSERT INTO `unimall_user` VALUES ('418', null, null, '1', 'oiAuI5DfoKkI7RbKkkLCCFcc06PY', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83epBic2ZB2xibtJ4BBEj5z7ngRCmlCR4wJ6YHctw3m7dAia2Y2N861TXNSdtwWmo7xY3jk173OqCUTJoA/132', '0', null, '1', '2019-09-14 08:52:40', '27.10.60.71', '1', '2019-09-14 08:52:41', '2019-09-14 08:52:40');
INSERT INTO `unimall_user` VALUES ('419', null, null, '1', 'oiAuI5AuWoBfJwXB8b_sBmi9buBw', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKTHX3FJS9ib6gBOrDokU8hA4ZIuvCibXWdjeq0mhB2EIibSOT3iauHfLpLo9oY7AZXicQzicbJkX8BIjvQ/132', '0', null, '1', '2019-09-14 16:10:03', '27.10.60.71', '1', '2019-09-14 16:10:03', '2019-09-14 16:10:03');
INSERT INTO `unimall_user` VALUES ('420', null, null, '1', 'oiAuI5HHswmliN6ySU7p_iyydERk', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoUtyltfgLJ4mLot65Fnzy6lH8MOAjeDAQlBJdMRmor5uAFTdYibXwsDOAiaUfhE4F0icvYRxXMGbH7Q/132', '0', null, '2', '2019-09-14 17:32:28', '27.10.60.71', '1', '2019-09-14 17:32:28', '2019-09-14 17:32:28');
INSERT INTO `unimall_user` VALUES ('421', null, null, '1', 'oiAuI5PjYSDdZ7FlwmjIWUotHoY8', null, 'https://wx.qlogo.cn/mmopen/vi_32/P4TkR2pFqic9mialHZauictmPQ0ibdSzpEwfhZRuB6V130tANoNHfcHvkFOI2RiaYMhzNIUsRAfbucx84kBsiaPXn9Jw/132', '0', null, '1', '2019-09-14 20:11:49', '27.10.60.71', '1', '2019-09-14 20:11:50', '2019-09-14 20:11:49');
INSERT INTO `unimall_user` VALUES ('422', null, null, '1', 'oiAuI5J5Wft-UmhusChlG0GXdw_A', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJYEXtfBhiblqibS6yr9SPlSiaQwaUD74OmrEI1tFicVcNp8Bsb5V9kZ3GaVM8UwcabGCYibtTSgA5vcew/132', '0', null, '1', '2019-09-15 09:42:39', '27.10.60.71', '1', '2019-09-15 09:42:39', '2019-09-15 09:42:39');
INSERT INTO `unimall_user` VALUES ('423', null, null, '1', 'oiAuI5MYpAXgLnhGl_YsNdrSps6s', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJBiaicvTx0icMqM46el4taCOabHicuL8f92RanWP0S4E8ibs3qkT8erosNxA4mu4KdJIxp6BPyrB0wavw/132', '0', null, '1', '2019-09-15 10:43:58', '27.10.60.71', '1', '2019-09-15 10:43:58', '2019-09-15 10:43:58');
INSERT INTO `unimall_user` VALUES ('424', null, null, '1', 'oiAuI5D3eCroD2s_CiUSac4973RQ', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eogibCalv30ibunJHB7acx6nLotQPkicnLlJ385tA9ImQsiclvMrkZMPyO8fJeTbrDUt7zicDxcct1ZYpw/132', '0', null, '1', '2019-09-15 14:14:45', '27.10.60.71', '1', '2019-09-15 14:14:45', '2019-09-15 14:14:45');
INSERT INTO `unimall_user` VALUES ('425', null, null, '1', 'oiAuI5Axp5oJtLOTh8U74jo31T-I', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJnugUNWBtcsicMlJdLNOWZlU3QIeJkvO790DAyicmA3CYS7WcvH9AIaezKclqtHxxz0Pz2ICvIXR3A/132', '0', null, '1', '2019-09-15 14:21:43', '27.10.60.71', '1', '2019-09-15 14:21:43', '2019-09-15 14:21:43');
INSERT INTO `unimall_user` VALUES ('426', null, null, '1', 'oiAuI5NhfaUZRcJEs3YExXYltfdw', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eooHchHwuNUibkOGop0STDHEQ5arBlIuqyzC4eGUOqX9lcelh1DqX1EWkAbu3MdGh9jz6jFDbet8lQ/132', '0', null, '1', '2019-09-15 22:27:27', '27.10.60.71', '1', '2019-09-15 22:27:27', '2019-09-15 22:27:27');
INSERT INTO `unimall_user` VALUES ('427', '18581599773', '$1$1858159$PhPwRVL8L/Iwb9gOEbWnS1', '0', null, null, null, '0', null, '-1', '2019-09-16 09:18:38', '27.10.60.71', '1', '2019-09-16 09:18:09', '2019-09-16 09:18:09');
INSERT INTO `unimall_user` VALUES ('428', null, null, '1', 'oiAuI5BnEvVhMBM8yxBGVlF-auJU', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIiaAhIgicZhNicl4KLNjowiclQQqkPy9sqzicgQ8YW1becZEasRIpRY4fFLJbwFj0sMpt8ju00c5gkCRA/132', '0', null, '1', '2019-09-16 10:20:08', '27.10.60.71', '1', '2019-09-16 10:20:08', '2019-09-16 10:20:08');
INSERT INTO `unimall_user` VALUES ('429', null, null, '2', 'osTQe6B4B6IVaaNSvpUuEuvtIPoA', '潘伟℡¹⁸⁶⁵⁶⁵⁵³¹⁹⁸', 'http://thirdwx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEJYjMg33dsWFJXEwqfrLib7azUdsxEsX2pzjHHYCQgbJp7klpOn0gMoibMhm9ZMIWFRSSoVPaCXO5tA/132', '0', null, '1', '2019-09-16 20:12:59', '27.10.60.71', '1', '2019-09-16 20:13:00', '2019-09-16 14:40:10');
INSERT INTO `unimall_user` VALUES ('430', null, null, '1', 'oiAuI5G10ruwkbfTrejcFnKFw4XU', null, 'https://wx.qlogo.cn/mmopen/vi_32/5gkAkgensNcfgL9kibROX8TxStyCoUS4ZRJkcD7gJH4wd6P8B0ReSL6PPqOdic4essXicNVYXppewRMLHmkbJdS5w/132', '0', null, '1', '2019-09-16 17:54:01', '27.10.60.71', '1', '2019-09-16 17:54:01', '2019-09-16 17:01:14');
INSERT INTO `unimall_user` VALUES ('431', null, null, '1', 'oiAuI5PKRJNJNUJ04QZ8PG-COtro', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIkQibcXZNOtOZVjetP3D0O6lOoBibFibTE7OV9ickCicdjKPH8icVPWOZGQqK60YZ5bicAraFFztFkDIp4Q/132', '0', null, '1', '2019-09-16 17:26:37', '27.10.60.71', '1', '2019-09-16 17:26:38', '2019-09-16 17:26:37');
INSERT INTO `unimall_user` VALUES ('432', null, null, '1', 'oiAuI5OGhJ5bEkJlpemfvtWFVPgo', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83erzokP3SdGQzwoqvsjoyE4DnicMTpL0Bz1ShokqI1BNo0zseYE0qAPbicyROyKRQ1uLRxgos9K5zRkg/132', '0', null, '1', '2019-09-17 10:38:57', '27.10.60.71', '1', '2019-09-17 10:38:57', '2019-09-17 10:38:57');
INSERT INTO `unimall_user` VALUES ('433', null, null, '1', 'oiAuI5AhtUUd3NHK8ZmkvhmIppxw', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83erz8QhS1dy2DpY3X3WnpmxYYfRZXvNicJmld2BarbRauon121QIOI9YnNh4aniaPraCAghlZAjE92BA/132', '0', null, '1', '2019-09-17 12:02:09', '27.10.60.71', '1', '2019-09-17 12:02:09', '2019-09-17 12:02:09');
INSERT INTO `unimall_user` VALUES ('434', null, null, '1', 'oiAuI5GAYJQup9Kbw4t7I5OCb8rk', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLkichfwhtzkGyF0B14MRDQZZibb1FCuYm0wj9ErRicrHrqqxxO3IFHrjX6pYT1Rt6VDFAqe1K43LBxA/132', '0', null, '1', '2019-09-17 15:52:18', '27.10.60.71', '1', '2019-09-17 15:52:18', '2019-09-17 13:33:24');
INSERT INTO `unimall_user` VALUES ('435', '18383171358', '$1$1838317$3BKwY19Euvyf66ryz07JE/', '0', null, '，，，', null, '0', null, '-1', '2019-09-17 16:58:34', '27.10.60.71', '1', '2019-09-17 16:55:20', '2019-09-17 16:54:47');
INSERT INTO `unimall_user` VALUES ('436', null, null, '1', 'oiAuI5DkerkbZbuwdRHqQ5kXRQT8', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJ96Ug9OkP4NbZe2zlttjvzV6n7aQ4hvkn0XAykvibRFaaNhZQ3YIgcIGj3iaLUypY1pzkOBXBFqrXw/132', '0', null, '1', '2019-09-17 17:01:43', '27.10.60.71', '1', '2019-09-17 17:01:43', '2019-09-17 16:56:14');
INSERT INTO `unimall_user` VALUES ('437', null, null, '1', 'oiAuI5Hoab9fGL4wxJzxqz_0Pt_o', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIynHFEfUu56BddD8uwcJVOOgUy8YrCSwdlco6f1DfThicPoYDmVTDlnqmfUHgczxu0bE924AlyibMg/132', '0', null, '1', '2019-09-17 17:49:55', '27.10.60.71', '1', '2019-09-17 17:49:55', '2019-09-17 17:49:55');
INSERT INTO `unimall_user` VALUES ('438', null, null, '1', 'oiAuI5JTTM2YyHWQDbgdovnEQBho', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoMlana7vic327rqherELe9Eia43Oic4wku3BFktib9y4JiaB5h5KonSA458xMGxroJv3OxYdfb4u3rmxA/132', '0', null, '1', '2019-09-17 18:44:45', '27.10.60.71', '1', '2019-09-17 18:44:45', '2019-09-17 18:44:45');
INSERT INTO `unimall_user` VALUES ('439', null, null, '1', 'oiAuI5C66-HWNkNAiD6wpZYl9N6s', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIwDsE0A0FKAmOibk2mYs1DgzJicJGmydQEIKUnVEbjxvz5WfhTdMT3WYorVAUWcxhToUicaRZgIo7jg/132', '0', null, '1', '2019-09-18 11:10:06', '27.10.60.71', '1', '2019-09-18 11:10:06', '2019-09-18 11:10:06');
INSERT INTO `unimall_user` VALUES ('440', null, null, '2', 'osTQe6GNXdbLq1zmb0uTBFjowpow', 'null', 'http://thirdwx.qlogo.cn/mmopen/vi_32/vVnhVX8IHNDRnU96Nk6CLRUh0vPKDh7gYuA3SBwtBV3AAwozslp41FQUhGvtYy3EDyXuwGTt9T06abRrZ38rnQ/132', '0', null, '0', '2019-09-18 13:41:12', '27.10.60.71', '1', '2019-09-18 13:41:13', '2019-09-18 13:41:12');
INSERT INTO `unimall_user` VALUES ('441', null, null, '1', 'oiAuI5FrFD3u0TQR4FqDLpjgWzP0', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLTc7AzUyqH2fvRZfn6pYv1iab2u4gEZ6qvLOPGg13EHmubmYpDONVLyGurZ5owvWdWxEu8o99iaoEA/132', '0', null, '1', '2019-09-18 17:51:10', '27.10.60.71', '1', '2019-09-18 17:51:11', '2019-09-18 13:53:45');
INSERT INTO `unimall_user` VALUES ('442', null, null, '1', 'oiAuI5KrKvJUVfy0Ug7MrVQc_OJ4', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJp5yxOicJU1yw8ic6N2FECxDHficvXJicCfU8MhOGYp67byn7mGByas1jTUPkRzU0FxZxBXvIYwW7icTg/132', '0', null, '1', '2019-09-18 13:56:49', '27.10.60.71', '1', '2019-09-18 13:56:49', '2019-09-18 13:56:49');
INSERT INTO `unimall_user` VALUES ('443', null, null, '1', 'oiAuI5OvQdAN9oNWytsr6ZZpvYNI', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqTET3ia211ZZXbqsG6NQTv1iaRFlSwtHib3hicULIvDicnSUj1aajiaic3X4CFCfib6ziaVKuL4a4o664N4wQ/132', '0', null, '1', '2019-09-18 14:25:45', '27.10.60.71', '1', '2019-09-18 14:25:45', '2019-09-18 14:25:45');
INSERT INTO `unimall_user` VALUES ('444', null, null, '1', 'oiAuI5Mk0Lt-42WuZA6uDiBE2dzQ', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLLcrOHsYImlH66VvsYVOYFxJlKQKubVpUTYY1r51vVA1gjWRX8Xccia6Oyic4Xjtkcf7zCeZwWUUSw/132', '0', null, '1', '2019-09-18 14:43:06', '27.10.60.71', '1', '2019-09-18 14:43:06', '2019-09-18 14:43:06');
INSERT INTO `unimall_user` VALUES ('445', null, null, '1', 'oiAuI5KhP6TCyiSPFfWTfV-DmwOE', null, 'https://wx.qlogo.cn/mmopen/vi_32/GK5qHkXfibkKL0SQSduh5TOrh27gZyZBjMEvF2h9R5jeJKvdiaThxtaznfEHVMTUhuia0p6QvOzSSBoia6UPAsEklA/132', '0', null, '2', '2019-09-18 14:50:43', '27.10.60.71', '1', '2019-09-18 14:50:43', '2019-09-18 14:49:32');
INSERT INTO `unimall_user` VALUES ('446', null, null, '1', 'oiAuI5IZAZDLg6UQqsNSOINNcV7c', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eq0zXicZRuj8O1SdsMEQAiccftvb422ZeOhb0ObQYAWfY7PyNB4j4Rt4icgIxZZ4iciaXMR9WrNYic6KRLg/132', '0', null, '1', '2019-09-18 15:44:44', '27.10.60.71', '1', '2019-09-18 15:44:44', '2019-09-18 15:44:44');
INSERT INTO `unimall_user` VALUES ('447', '18505518325', '$1$1850551$NfrzeNnCkPW4o9wUeo5dn0', '0', null, null, null, '0', null, '-1', '2019-09-18 15:53:07', '27.10.60.71', '1', '2019-09-18 15:52:59', '2019-09-18 15:52:59');
INSERT INTO `unimall_user` VALUES ('448', null, null, '1', 'oiAuI5AgkoDll3F3xJNM-GM2MRUQ', null, 'https://wx.qlogo.cn/mmopen/vi_32/4oLIRNRBbC85WNDtticJkjdZt1OkwyCE5wN1r1LQndSNpzyu8dW5gf6zZ8tsQKSSbcN8p8YaPA9crmSicgbZOrQQ/132', '0', null, '1', '2019-09-18 16:06:20', '27.10.60.71', '1', '2019-09-18 16:06:20', '2019-09-18 16:06:20');
INSERT INTO `unimall_user` VALUES ('449', null, null, '1', 'oiAuI5PpQ9VNxKZoRJ-_MSwr02gY', null, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJ4n1B0sQAQ6dLklfeMbFk1qyxSyibEEzhIGgmeA07YrQQIsic4CFwMHOKomcjykqU3qwYWmxEfot5g/132', '0', null, '1', '2019-09-18 16:10:05', '27.10.60.71', '1', '2019-09-18 16:10:06', '2019-09-18 16:10:05');
INSERT INTO `unimall_user` VALUES ('450', null, null, '2', 'osTQe6DbvmrbNs7a7BQpErvbAKP4', 'momo', 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q3auHgzwzM5g08Zc1VxdZbynnbaiamtJduFXuT8JeIGmhho7plEahnOkiatQt4YDTlZ06QzwiaHRMGhIYWyZnA2BQ/132', '0', null, '0', '2019-09-18 17:17:40', '27.10.60.71', '1', '2019-09-18 17:17:40', '2019-09-18 17:17:40');
INSERT INTO `unimall_user` VALUES ('451', null, null, '1', 'oiAuI5Jv-hCUqRS8iC2oEY169B30', null, 'https://wx.qlogo.cn/mmopen/vi_32/78vDM2vOOAhW6r8lQLUqYDf4cAunAfHCw3cEQSkPdkoxW5dO6tYyibolcnd7OJVQHC16QEj1uSItAukOVJCOqhA/132', '0', null, '1', '2019-09-18 19:26:54', '27.10.60.71', '1', '2019-09-18 19:26:54', '2019-09-18 19:26:54');
INSERT INTO `unimall_user` VALUES ('452', null, null, '1', 'oiAuI5IO10rGrqGtT8QCGJ5LhWYY', null, 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83er10PIGJLOxgZYwhH9djwicknqVloXYj1wQvdKyWAtuIlshGicxD2z4Wb72Mib0dboqSdLgx1rnd8jWg/132', '0', null, '1', '2019-09-18 21:20:45', '27.10.60.71', '1', '2019-09-18 21:20:45', '2019-09-18 21:20:45');

-- ----------------------------
-- Table structure for unimall_user_coupon
-- ----------------------------
DROP TABLE IF EXISTS `unimall_user_coupon`;
CREATE TABLE `unimall_user_coupon` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `coupon_id` bigint(20) NOT NULL,
  `order_id` bigint(20) DEFAULT NULL COMMENT '使用订单Id',
  `gmt_used` datetime DEFAULT NULL COMMENT '使用时间，若使用时间为空，表示未使用',
  `gmt_start` datetime NOT NULL COMMENT '领取优惠券时写入',
  `gmt_end` datetime NOT NULL COMMENT '领取优惠券根据策略计算',
  `gmt_update` datetime NOT NULL,
  `gmt_create` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户优惠卷表';

-- ----------------------------
-- Records of unimall_user_coupon
-- ----------------------------
INSERT INTO `unimall_user_coupon` VALUES ('3', '5', '3', '20', '2019-10-23 14:02:50', '2019-10-22 11:04:37', '2019-11-02 13:04:37', '2019-10-23 13:04:37', '2019-10-23 13:04:37');
INSERT INTO `unimall_user_coupon` VALUES ('9', '5', '7', null, null, '2019-10-23 16:16:45', '2019-10-24 16:16:45', '2019-10-23 16:16:45', '2019-10-23 16:16:45');
INSERT INTO `unimall_user_coupon` VALUES ('10', '5', '8', null, null, '2019-10-22 16:23:27', '2019-10-24 16:23:42', '2019-10-23 16:34:11', '2019-10-23 16:34:11');
INSERT INTO `unimall_user_coupon` VALUES ('11', '5', '8', null, null, '2019-10-23 02:00:00', '2019-10-24 16:23:42', '2019-10-23 16:53:08', '2019-10-23 16:53:08');
INSERT INTO `unimall_user_coupon` VALUES ('12', '5', '9', null, null, '2019-10-23 02:00:58', '2019-10-24 16:58:58', '2019-10-23 16:58:58', '2019-10-23 16:58:58');
INSERT INTO `unimall_user_coupon` VALUES ('13', '5', '9', null, null, '2019-10-22 17:08:08', '2019-10-24 17:08:08', '2019-10-23 17:08:08', '2019-10-23 17:08:08');
INSERT INTO `unimall_user_coupon` VALUES ('14', '5', '9', null, null, '2019-10-23 17:12:05', '2019-10-24 17:12:05', '2019-10-23 17:12:05', '2019-10-23 17:12:05');
INSERT INTO `unimall_user_coupon` VALUES ('15', '5', '10', null, null, '2019-10-23 17:17:02', '2019-11-02 17:17:02', '2019-10-23 17:17:02', '2019-10-23 17:17:02');

-- ----------------------------
-- Table structure for unimall_user_form_id
-- ----------------------------
DROP TABLE IF EXISTS `unimall_user_form_id`;
CREATE TABLE `unimall_user_form_id` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `openid` varchar(255) NOT NULL,
  `form_id` varchar(255) NOT NULL,
  `gmt_update` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `gmt_create` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of unimall_user_form_id
-- ----------------------------
INSERT INTO `unimall_user_form_id` VALUES ('5', '21', 'oiAuI5CW0p5YDegIPZEdklaSi4cM', 'wx06155539740854d985dd31791841788700', '2019-11-06 15:55:37', '2019-11-06 15:55:37');
INSERT INTO `unimall_user_form_id` VALUES ('6', '21', 'oiAuI5CW0p5YDegIPZEdklaSi4cM', 'wx30111532906108fbd2cc64441132666700', '2019-12-30 11:15:31', '2019-12-30 11:15:31');
INSERT INTO `unimall_user_form_id` VALUES ('7', '21', 'oiAuI5CW0p5YDegIPZEdklaSi4cM', 'wx3011171278937425fec0cf291505295100', '2019-12-30 11:17:12', '2019-12-30 11:17:12');
INSERT INTO `unimall_user_form_id` VALUES ('8', '21', 'oiAuI5CW0p5YDegIPZEdklaSi4cM', 'wx30112033793921dacad3b1ac1068657500', '2019-12-30 11:20:32', '2019-12-30 11:20:32');
INSERT INTO `unimall_user_form_id` VALUES ('9', '21', 'oiAuI5CW0p5YDegIPZEdklaSi4cM', 'wx3011224682540389b3ddfe831841777500', '2019-12-30 11:22:45', '2019-12-30 11:22:45');
INSERT INTO `unimall_user_form_id` VALUES ('10', '21', 'oiAuI5CW0p5YDegIPZEdklaSi4cM', 'wx30112408433344528a5f04b01779437800', '2019-12-30 11:24:07', '2019-12-30 11:24:07');
INSERT INTO `unimall_user_form_id` VALUES ('11', '21', 'oiAuI5CW0p5YDegIPZEdklaSi4cM', 'wx301132320799910d2eaabafb1002210100', '2019-12-30 11:32:31', '2019-12-30 11:32:31');
INSERT INTO `unimall_user_form_id` VALUES ('12', '21', 'oiAuI5CW0p5YDegIPZEdklaSi4cM', 'wx14151430198403862757b8621487132800', '2020-02-14 15:14:28', '2020-02-14 15:14:28');
INSERT INTO `unimall_user_form_id` VALUES ('13', '21', 'oiAuI5CW0p5YDegIPZEdklaSi4cM', 'wx14190839118088888ad6d1d41671443000', '2020-02-14 19:08:39', '2020-02-14 19:08:39');
INSERT INTO `unimall_user_form_id` VALUES ('14', '21', 'oiAuI5CW0p5YDegIPZEdklaSi4cM', 'wx14191849222124341492bfda1867055100', '2020-02-14 19:18:49', '2020-02-14 19:18:49');
INSERT INTO `unimall_user_form_id` VALUES ('15', '21', 'oiAuI5CW0p5YDegIPZEdklaSi4cM', 'wx1419195254031953eaa3d7421014060000', '2020-02-14 19:19:52', '2020-02-14 19:19:52');
