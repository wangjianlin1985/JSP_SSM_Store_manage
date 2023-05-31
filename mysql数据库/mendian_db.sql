/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : mendian_db

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2018-07-28 20:17:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL default '',
  `password` varchar(32) default NULL,
  PRIMARY KEY  (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a');

-- ----------------------------
-- Table structure for `t_attendance`
-- ----------------------------
DROP TABLE IF EXISTS `t_attendance`;
CREATE TABLE `t_attendance` (
  `attendanceId` int(11) NOT NULL auto_increment COMMENT '考勤id',
  `attendanceDate` varchar(20) default NULL COMMENT '考勤日期',
  `employeeObj` varchar(30) NOT NULL COMMENT '考勤员工',
  `attendanceStateObj` int(11) NOT NULL COMMENT '考勤结果',
  `attendanceMemo` varchar(800) default NULL COMMENT '考勤备注',
  PRIMARY KEY  (`attendanceId`),
  KEY `employeeObj` (`employeeObj`),
  KEY `attendanceStateObj` (`attendanceStateObj`),
  CONSTRAINT `t_attendance_ibfk_1` FOREIGN KEY (`employeeObj`) REFERENCES `t_employee` (`employeeNo`),
  CONSTRAINT `t_attendance_ibfk_2` FOREIGN KEY (`attendanceStateObj`) REFERENCES `t_attendancestate` (`stateId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_attendance
-- ----------------------------
INSERT INTO `t_attendance` VALUES ('1', '2018-03-05', 'EM001', '2', '迟到了5分钟');
INSERT INTO `t_attendance` VALUES ('2', '2018-03-12', 'EM002', '3', '扣款100');

-- ----------------------------
-- Table structure for `t_attendancestate`
-- ----------------------------
DROP TABLE IF EXISTS `t_attendancestate`;
CREATE TABLE `t_attendancestate` (
  `stateId` int(11) NOT NULL auto_increment COMMENT '考勤状态id',
  `stateName` varchar(20) NOT NULL COMMENT '考勤状态名称',
  PRIMARY KEY  (`stateId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_attendancestate
-- ----------------------------
INSERT INTO `t_attendancestate` VALUES ('1', '正常');
INSERT INTO `t_attendancestate` VALUES ('2', '迟到');
INSERT INTO `t_attendancestate` VALUES ('3', '早退');
INSERT INTO `t_attendancestate` VALUES ('4', '旷工');

-- ----------------------------
-- Table structure for `t_buyinfo`
-- ----------------------------
DROP TABLE IF EXISTS `t_buyinfo`;
CREATE TABLE `t_buyinfo` (
  `buyId` int(11) NOT NULL auto_increment COMMENT '进货id',
  `productObj` int(11) NOT NULL COMMENT '进货商品',
  `supplierObj` int(11) NOT NULL COMMENT '供应商',
  `price` float NOT NULL COMMENT '进货单价',
  `number` int(11) NOT NULL COMMENT '进货数量',
  `totalPrice` float NOT NULL COMMENT '总价格',
  `buyDate` varchar(20) default NULL COMMENT '进货日期',
  `buyMemo` varchar(800) default NULL COMMENT '进货备注',
  `employeeObj` varchar(30) NOT NULL COMMENT '进货员工',
  PRIMARY KEY  (`buyId`),
  KEY `productObj` (`productObj`),
  KEY `supplierObj` (`supplierObj`),
  KEY `employeeObj` (`employeeObj`),
  CONSTRAINT `t_buyinfo_ibfk_1` FOREIGN KEY (`productObj`) REFERENCES `t_product` (`productId`),
  CONSTRAINT `t_buyinfo_ibfk_2` FOREIGN KEY (`supplierObj`) REFERENCES `t_suppllier` (`supplierId`),
  CONSTRAINT `t_buyinfo_ibfk_3` FOREIGN KEY (`employeeObj`) REFERENCES `t_employee` (`employeeNo`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_buyinfo
-- ----------------------------
INSERT INTO `t_buyinfo` VALUES ('1', '1', '1', '150', '20', '3000', '2018-03-01', '11412', 'EM001');
INSERT INTO `t_buyinfo` VALUES ('2', '1', '1', '150', '10', '1500', '2018-03-10', 'test', 'EM001');
INSERT INTO `t_buyinfo` VALUES ('3', '2', '1', '120', '20', '2400', '2018-03-12', '进货了', 'EM001');
INSERT INTO `t_buyinfo` VALUES ('4', '2', '1', '120', '10', '1200', '2018-03-12', '测试进货', 'EM001');
INSERT INTO `t_buyinfo` VALUES ('5', '2', '1', '120', '12', '1440', '2018-03-12', '我来进货一个', 'EM001');
INSERT INTO `t_buyinfo` VALUES ('6', '2', '1', '120', '20', '2400', '2018-03-12', '我的进货记录', 'EM002');
INSERT INTO `t_buyinfo` VALUES ('7', '3', '1', '20', '20', '400', '2018-03-12', 'test', 'EM002');

-- ----------------------------
-- Table structure for `t_employee`
-- ----------------------------
DROP TABLE IF EXISTS `t_employee`;
CREATE TABLE `t_employee` (
  `employeeNo` varchar(30) NOT NULL COMMENT 'employeeNo',
  `password` varchar(30) NOT NULL COMMENT '登录密码',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `gender` varchar(4) NOT NULL COMMENT '性别',
  `birthDate` varchar(20) default NULL COMMENT '出生日期',
  `employeePhoto` varchar(60) NOT NULL COMMENT '雇员照片',
  `telephone` varchar(20) NOT NULL COMMENT '联系电话',
  `email` varchar(50) NOT NULL COMMENT '邮箱',
  `address` varchar(80) default NULL COMMENT '家庭地址',
  `regTime` varchar(20) default NULL COMMENT '注册时间',
  PRIMARY KEY  (`employeeNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_employee
-- ----------------------------
INSERT INTO `t_employee` VALUES ('EM001', '123', '双鱼林', '男', '2018-03-01', 'upload/f34dbf84-2975-402b-920a-84c5c53735a1.jpg', '13984019834', 'lingege@163.com', '四川达州', '2018-03-06 11:08:55');
INSERT INTO `t_employee` VALUES ('EM002', '123', '张晓馨', '女', '2018-03-06', 'upload/32319049-0ed3-4942-aba0-f3c91d765ba4.jpg', '13983908342', 'xiaoxing@163.com', '福建南平', '2018-03-12 11:27:47');

-- ----------------------------
-- Table structure for `t_loginfo`
-- ----------------------------
DROP TABLE IF EXISTS `t_loginfo`;
CREATE TABLE `t_loginfo` (
  `logId` int(11) NOT NULL auto_increment COMMENT '日志id',
  `logType` varchar(30) NOT NULL COMMENT '日志类型',
  `logContent` varchar(500) NOT NULL COMMENT '日志内容',
  `logTime` varchar(20) default NULL COMMENT '日志时间',
  PRIMARY KEY  (`logId`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_loginfo
-- ----------------------------
INSERT INTO `t_loginfo` VALUES ('1', '商品进货', '员工EM001进货了商品xxx', '2018-03-06 11:12:45');
INSERT INTO `t_loginfo` VALUES ('2', '系统登录', '超级管理员a登录系统', '2018-03-12 11:16:06');
INSERT INTO `t_loginfo` VALUES ('3', '系统登录', '员工EM001登录系统', '2018-03-12 11:16:42');
INSERT INTO `t_loginfo` VALUES ('4', '商品销售', '员工操作苏泊尔电饭煲销售数量：2入库成功', '2018-03-12 11:18:30');
INSERT INTO `t_loginfo` VALUES ('5', '系统登录', '超级管理员a登录系统', '2018-03-12 11:18:43');
INSERT INTO `t_loginfo` VALUES ('6', '系统登录', '员工EM001登录系统', '2018-03-12 11:21:10');
INSERT INTO `t_loginfo` VALUES ('7', '商品进货', '员工EM001操作商品美的电磁炉进货数量：10登记成功', '2018-03-12 11:21:43');
INSERT INTO `t_loginfo` VALUES ('8', '系统登录', '超级管理员a登录系统', '2018-03-12 11:21:53');
INSERT INTO `t_loginfo` VALUES ('9', '系统登录', '员工EM001登录系统', '2018-03-12 11:23:20');
INSERT INTO `t_loginfo` VALUES ('10', '系统登录', '员工EM001登录系统', '2018-03-12 11:23:20');
INSERT INTO `t_loginfo` VALUES ('11', '商品进货', '员工EM001操作商品美的电磁炉进货数量：12登记成功', '2018-03-12 11:23:43');
INSERT INTO `t_loginfo` VALUES ('12', '系统登录', '超级管理员a登录系统', '2018-03-12 11:23:49');
INSERT INTO `t_loginfo` VALUES ('13', '商品登记', '商品飘柔洗发水登记成功', '2018-03-12 11:30:11');
INSERT INTO `t_loginfo` VALUES ('14', '系统登录', '员工EM002登录系统', '2018-03-12 11:35:51');
INSERT INTO `t_loginfo` VALUES ('15', '商品销售', '员工操作苏泊尔电饭煲销售数量：1登记成功', '2018-03-12 11:36:17');
INSERT INTO `t_loginfo` VALUES ('16', '商品销售', '员工操作飘柔洗发水750ml销售数量：2登记成功', '2018-03-12 11:36:17');
INSERT INTO `t_loginfo` VALUES ('17', '系统登录', '普通管理员gl002登录系统', '2018-03-12 11:36:32');
INSERT INTO `t_loginfo` VALUES ('18', '系统登录', '超级管理员a登录系统', '2018-03-12 11:36:51');
INSERT INTO `t_loginfo` VALUES ('19', '系统登录', '员工EM002登录系统', '2018-03-12 11:46:50');
INSERT INTO `t_loginfo` VALUES ('20', '商品进货', '员工EM002操作商品美的电磁炉进货数量：20登记成功', '2018-03-12 11:47:35');
INSERT INTO `t_loginfo` VALUES ('21', '商品进货', '员工EM002操作商品飘柔洗发水750ml进货数量：20登记成功', '2018-03-12 11:48:03');
INSERT INTO `t_loginfo` VALUES ('22', '商品销售', '员工操作美的电磁炉销售数量：1登记成功', '2018-03-12 11:49:07');
INSERT INTO `t_loginfo` VALUES ('23', '商品销售', '员工操作飘柔洗发水750ml销售数量：2登记成功', '2018-03-12 11:49:07');
INSERT INTO `t_loginfo` VALUES ('24', '系统登录', '普通管理员gl001登录系统', '2018-03-12 11:51:54');
INSERT INTO `t_loginfo` VALUES ('25', '系统登录', '普通管理员gl001登录系统', '2018-03-12 11:56:25');
INSERT INTO `t_loginfo` VALUES ('26', '系统登录', '超级管理员a登录系统', '2018-03-12 11:56:50');
INSERT INTO `t_loginfo` VALUES ('27', '系统登录', '超级管理员a登录系统', '2018-07-28 19:58:50');
INSERT INTO `t_loginfo` VALUES ('28', '系统登录', '员工EM001登录系统', '2018-07-28 20:02:33');
INSERT INTO `t_loginfo` VALUES ('29', '商品销售', '员工操作苏泊尔电饭煲销售数量：2登记成功', '2018-07-28 20:04:36');
INSERT INTO `t_loginfo` VALUES ('30', '商品销售', '员工操作美的电磁炉销售数量：1登记成功', '2018-07-28 20:04:36');
INSERT INTO `t_loginfo` VALUES ('31', '系统登录', '超级管理员a登录系统', '2018-07-28 20:09:44');
INSERT INTO `t_loginfo` VALUES ('32', '系统登录', '普通管理员gl001登录系统', '2018-07-28 20:14:50');

-- ----------------------------
-- Table structure for `t_lostproduct`
-- ----------------------------
DROP TABLE IF EXISTS `t_lostproduct`;
CREATE TABLE `t_lostproduct` (
  `lostId` int(11) NOT NULL auto_increment COMMENT '记录id',
  `productObj` int(11) NOT NULL COMMENT '丢失物品',
  `lostNumber` int(11) NOT NULL COMMENT '丢失数量',
  `lostTime` varchar(20) default NULL COMMENT '丢失时间',
  `lostPlace` varchar(40) NOT NULL COMMENT '丢失地点',
  `productMoney` float NOT NULL COMMENT '总价值',
  `employeeObj` varchar(30) NOT NULL COMMENT '操作员',
  PRIMARY KEY  (`lostId`),
  KEY `productObj` (`productObj`),
  KEY `employeeObj` (`employeeObj`),
  CONSTRAINT `t_lostproduct_ibfk_1` FOREIGN KEY (`productObj`) REFERENCES `t_product` (`productId`),
  CONSTRAINT `t_lostproduct_ibfk_2` FOREIGN KEY (`employeeObj`) REFERENCES `t_employee` (`employeeNo`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_lostproduct
-- ----------------------------
INSERT INTO `t_lostproduct` VALUES ('1', '1', '2', '2018-03-06 11:11:33', '门店超市', '700', 'EM001');
INSERT INTO `t_lostproduct` VALUES ('2', '2', '3', '2018-03-11 23:29:24', '门店超市', '500', 'EM001');
INSERT INTO `t_lostproduct` VALUES ('3', '1', '1', '2018-03-12 11:50:06', '门店里面被盗', '100', 'EM002');

-- ----------------------------
-- Table structure for `t_manager`
-- ----------------------------
DROP TABLE IF EXISTS `t_manager`;
CREATE TABLE `t_manager` (
  `managerUserName` varchar(20) NOT NULL COMMENT 'managerUserName',
  `password` varchar(20) NOT NULL COMMENT '登录密码',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `sex` varchar(4) NOT NULL COMMENT '性别',
  `birthday` varchar(20) default NULL COMMENT '出生日期',
  `telephone` varchar(20) NOT NULL COMMENT '联系电话',
  `managerMemo` varchar(800) default NULL COMMENT '管理备注',
  PRIMARY KEY  (`managerUserName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_manager
-- ----------------------------
INSERT INTO `t_manager` VALUES ('gl001', '123', '王忠', '男', '2018-03-02', '13959342344', 'test');
INSERT INTO `t_manager` VALUES ('gl002', '123', '李二', '男', '2018-03-06', '13984893423', 'test');

-- ----------------------------
-- Table structure for `t_member`
-- ----------------------------
DROP TABLE IF EXISTS `t_member`;
CREATE TABLE `t_member` (
  `memberUserName` varchar(30) NOT NULL COMMENT 'memberUserName',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `gender` varchar(4) NOT NULL COMMENT '性别',
  `birthDate` varchar(20) default NULL COMMENT '出生日期',
  `telephone` varchar(20) NOT NULL COMMENT '联系电话',
  `address` varchar(80) default NULL COMMENT '住宅地址',
  `memberMemo` varchar(800) default NULL COMMENT '会员备注',
  PRIMARY KEY  (`memberUserName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_member
-- ----------------------------
INSERT INTO `t_member` VALUES ('member1', '李明', '男', '2018-03-01', '13539802432', '四川成都红星路13号', 'test');
INSERT INTO `t_member` VALUES ('member2', '王超', '男', '2018-03-06', '13985083423', '四川攀枝花', '高级会员');

-- ----------------------------
-- Table structure for `t_product`
-- ----------------------------
DROP TABLE IF EXISTS `t_product`;
CREATE TABLE `t_product` (
  `productId` int(11) NOT NULL auto_increment COMMENT '商品id',
  `productClassObj` int(11) NOT NULL COMMENT '商品类别',
  `productName` varchar(50) NOT NULL COMMENT '商品名称',
  `mainPhoto` varchar(60) NOT NULL COMMENT '商品主图',
  `productPrice` float NOT NULL COMMENT '商品单价',
  `productCount` int(11) NOT NULL COMMENT '商品库存',
  `productDesc` varchar(5000) NOT NULL COMMENT '商品描述',
  `addTime` varchar(20) default NULL COMMENT '发布时间',
  PRIMARY KEY  (`productId`),
  KEY `productClassObj` (`productClassObj`),
  CONSTRAINT `t_product_ibfk_1` FOREIGN KEY (`productClassObj`) REFERENCES `t_productclass` (`classId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_product
-- ----------------------------
INSERT INTO `t_product` VALUES ('1', '1', '苏泊尔电饭煲', 'upload/d5e3f711-6906-47da-85d3-51e0cdfbb001.jpg', '299', '50', '<ul style=\"list-style-type: none;\" class=\" list-paddingleft-2\"><li><p>证书编号：2016010717889932</p></li><li><p>证书状态：有效</p></li><li><p>产品名称：全智能电压力锅</p></li><li><p>3C规格型号：CYSB50YCW10D-100,CYSB50YCW10DJ-100,CYSB50YCW10DS-1...</p></li><li><p>产品名称：SUPOR/苏泊尔 CYSB50YCW1...</p></li><li><p>品牌:&nbsp;SUPOR/苏泊尔</p></li><li><p>型号:&nbsp;CYSB50YCW10D-100</p></li><li><p>颜色分类:&nbsp;金色</p></li><li><p>控制方式:&nbsp;微电脑式</p></li><li><p>压力锅功能:&nbsp;煲&nbsp;蒸&nbsp;煮&nbsp;炖&nbsp;焖&nbsp;预约&nbsp;定时</p></li><li><p>压力锅口规格:&nbsp;21cm(含)-25cm(含)</p></li><li><p>售后服务:&nbsp;全国联保</p></li><li><p>无水炖功能:&nbsp;支持</p></li><li><p>适用人数:&nbsp;5人-6人</p></li></ul><p><br/></p>', '2018-03-06 11:10:04');
INSERT INTO `t_product` VALUES ('2', '1', '美的电磁炉', 'upload/26dc1821-2d0f-4a81-80f0-d9b3387312ef.jpg', '238', '82', '<ul style=\"list-style-type: none;\" class=\" list-paddingleft-2\"><li><p>证书编号：2014010711717366</p></li><li><p>证书状态：有效</p></li><li><p>申请人名称：广东美的生活电器制造有限公司</p></li><li><p>制造商名称：广东美的生活电器制造有限公司</p></li><li><p>产品名称：多功能电磁炉</p></li><li><p>3C产品型号：C22-RT2248,C22-RT2249,C22-RT2251,C22-WT2212,C22-WT...</p></li><li><p>3C规格型号：见附件</p></li><li><p>产品名称：Midea/美的 C21-WH2106</p></li><li><p>电磁炉品牌:&nbsp;Midea/美的</p></li><li><p>型号:&nbsp;C21-WH2106</p></li><li><p>货号:&nbsp;WH2106</p></li><li><p>颜色分类:&nbsp;【尊享版】浩瀚黑</p></li><li><p>电磁炉炉头:&nbsp;1个</p></li><li><p>功能:&nbsp;余温&nbsp;爆炒&nbsp;炒菜&nbsp;炖奶&nbsp;蒸煮&nbsp;煮粥&nbsp;煲汤&nbsp;火锅&nbsp;烧水&nbsp;煎炸&nbsp;定时&nbsp;加热</p></li><li><p>电磁炉面板类型:&nbsp;哑光蓝盾微晶面板</p></li><li><p>售后服务:&nbsp;全国联保</p></li><li><p>操作方式:&nbsp;整版滑动触摸</p></li><li><p>能效等级:&nbsp;三级</p></li></ul><p><br/></p>', '2018-03-11 19:00:37');
INSERT INTO `t_product` VALUES ('3', '2', '飘柔洗发水750ml', 'upload/ef6a8c65-6f88-447a-b377-f7bfb5386940.jpg', '28', '100', '<ul style=\"list-style-type: none;\" class=\" list-paddingleft-2\"><li><p>产品名称：REJOICE/飘柔 滋润去屑洗...</p></li><li><p>是否为特殊用途化妆品:&nbsp;否</p></li><li><p>品牌:&nbsp;REJOICE/飘柔</p></li><li><p>型号:&nbsp;滋润去屑洗发露</p></li><li><p>是否量贩装:&nbsp;否</p></li><li><p>功效:&nbsp;去屑止痒</p></li><li><p>净含量: 750ml</p></li><li><p>适用对象:&nbsp;通用</p></li><li><p>规格类型:&nbsp;常规单品</p></li><li><p>保质期:&nbsp;3年</p></li><li><p>产地:&nbsp;中国大陆</p></li><li><p>是否进口:&nbsp;国产</p></li></ul><p><br/></p>', '2018-03-12 11:30:11');

-- ----------------------------
-- Table structure for `t_productclass`
-- ----------------------------
DROP TABLE IF EXISTS `t_productclass`;
CREATE TABLE `t_productclass` (
  `classId` int(11) NOT NULL auto_increment COMMENT '类别id',
  `className` varchar(40) NOT NULL COMMENT '类别名称',
  `classDesc` varchar(500) NOT NULL COMMENT '类别描述',
  PRIMARY KEY  (`classId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_productclass
-- ----------------------------
INSERT INTO `t_productclass` VALUES ('1', '厨房用品', '厨房里面的东西');
INSERT INTO `t_productclass` VALUES ('2', '生活用品', '和日常生活相关的');

-- ----------------------------
-- Table structure for `t_sell`
-- ----------------------------
DROP TABLE IF EXISTS `t_sell`;
CREATE TABLE `t_sell` (
  `sellId` int(11) NOT NULL auto_increment COMMENT '记录id',
  `sellNo` varchar(20) NOT NULL COMMENT '订单号',
  `productObj` int(11) NOT NULL COMMENT '销售商品',
  `price` float NOT NULL COMMENT '销售价格',
  `number` int(11) NOT NULL COMMENT '销售数量',
  `totalPrice` float NOT NULL COMMENT '销售总价',
  `memberObj` varchar(30) NOT NULL COMMENT '购买会员',
  `employeeObj` varchar(30) NOT NULL COMMENT '销售员工',
  `sellTime` varchar(20) default NULL COMMENT '销售时间',
  PRIMARY KEY  (`sellId`),
  KEY `productObj` (`productObj`),
  KEY `employeeObj` (`employeeObj`),
  KEY `memberObj` (`memberObj`),
  CONSTRAINT `t_sell_ibfk_1` FOREIGN KEY (`productObj`) REFERENCES `t_product` (`productId`),
  CONSTRAINT `t_sell_ibfk_2` FOREIGN KEY (`employeeObj`) REFERENCES `t_employee` (`employeeNo`),
  CONSTRAINT `t_sell_ibfk_3` FOREIGN KEY (`memberObj`) REFERENCES `t_member` (`memberUserName`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sell
-- ----------------------------
INSERT INTO `t_sell` VALUES ('1', 'EM00120180306111113', '1', '350', '2', '700', 'member1', 'EM001', '2018-03-01 11:11:20');
INSERT INTO `t_sell` VALUES ('2', 'EM00120180311223342', '1', '299', '4', '1196', 'member1', 'EM001', '2018-03-02 22:33:42');
INSERT INTO `t_sell` VALUES ('3', 'EM00120180311223342', '2', '238', '2', '476', 'member1', 'EM001', '2018-03-02 22:33:42');
INSERT INTO `t_sell` VALUES ('4', 'EM00120180311230608', '1', '299', '2', '598', 'member1', 'EM001', '2018-03-03 23:06:08');
INSERT INTO `t_sell` VALUES ('5', 'EM00120180311230611', '1', '299', '2', '598', 'member1', 'EM001', '2018-03-04 23:06:11');
INSERT INTO `t_sell` VALUES ('6', 'EM00120180311230618', '1', '299', '2', '598', 'member1', 'EM001', '2018-03-05 23:06:18');
INSERT INTO `t_sell` VALUES ('7', 'EM00120180311230626', '1', '299', '2', '598', 'member1', 'EM001', '2018-03-06 23:06:26');
INSERT INTO `t_sell` VALUES ('8', 'EM00120180311230739', '1', '299', '2', '598', 'member1', 'EM001', '2018-03-07 23:07:39');
INSERT INTO `t_sell` VALUES ('9', 'EM00120180311230739', '2', '238', '1', '238', 'member1', 'EM001', '2018-03-07 23:07:39');
INSERT INTO `t_sell` VALUES ('10', 'EM00120180311230843', '1', '299', '2', '598', 'member1', 'EM001', '2018-03-08 23:08:43');
INSERT INTO `t_sell` VALUES ('11', 'EM00120180311231028', '1', '299', '2', '598', 'member1', 'EM001', '2018-03-09 23:10:28');
INSERT INTO `t_sell` VALUES ('12', 'EM00120180311231028', '2', '238', '1', '238', 'member1', 'EM001', '2018-03-09 23:10:28');
INSERT INTO `t_sell` VALUES ('13', 'EM00120180312111830', '1', '299', '2', '598', 'member1', 'EM001', '2018-03-12 11:18:30');
INSERT INTO `t_sell` VALUES ('14', 'EM00220180312113617', '1', '299', '1', '299', 'member1', 'EM002', '2018-03-12 11:36:17');
INSERT INTO `t_sell` VALUES ('15', 'EM00220180312113617', '3', '28', '2', '56', 'member1', 'EM002', '2018-03-12 11:36:17');
INSERT INTO `t_sell` VALUES ('16', 'EM00220180312114907', '2', '238', '1', '238', 'member1', 'EM002', '2018-03-12 11:49:07');
INSERT INTO `t_sell` VALUES ('17', 'EM00220180312114907', '3', '28', '2', '56', 'member1', 'EM002', '2018-03-12 11:49:07');
INSERT INTO `t_sell` VALUES ('18', 'EM00120180728200436', '1', '299', '2', '598', 'member1', 'EM001', '2018-07-28 20:04:36');
INSERT INTO `t_sell` VALUES ('19', 'EM00120180728200436', '2', '238', '1', '238', 'member1', 'EM001', '2018-07-28 20:04:36');

-- ----------------------------
-- Table structure for `t_sellcart`
-- ----------------------------
DROP TABLE IF EXISTS `t_sellcart`;
CREATE TABLE `t_sellcart` (
  `sellCartId` int(11) NOT NULL auto_increment COMMENT '购物车id',
  `employeeObj` varchar(30) NOT NULL COMMENT '员工',
  `productObj` int(11) NOT NULL COMMENT '商品',
  `productCount` int(11) NOT NULL COMMENT '商品数量',
  PRIMARY KEY  (`sellCartId`),
  KEY `employeeObj` (`employeeObj`),
  KEY `productObj` (`productObj`),
  CONSTRAINT `t_sellcart_ibfk_1` FOREIGN KEY (`employeeObj`) REFERENCES `t_employee` (`employeeNo`),
  CONSTRAINT `t_sellcart_ibfk_2` FOREIGN KEY (`productObj`) REFERENCES `t_product` (`productId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sellcart
-- ----------------------------

-- ----------------------------
-- Table structure for `t_suppllier`
-- ----------------------------
DROP TABLE IF EXISTS `t_suppllier`;
CREATE TABLE `t_suppllier` (
  `supplierId` int(11) NOT NULL auto_increment COMMENT '供应商id',
  `supplierName` varchar(20) NOT NULL COMMENT '供应商名称',
  `supplierLawyer` varchar(20) NOT NULL COMMENT '法人代表',
  `supplierTelephone` varchar(20) NOT NULL COMMENT '供应商电话',
  `supplierAddress` varchar(80) NOT NULL COMMENT '供应商地址',
  PRIMARY KEY  (`supplierId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_suppllier
-- ----------------------------
INSERT INTO `t_suppllier` VALUES ('1', '四川供应商总代理', '王强', '028-83948083', '四川成都建设路');

-- ----------------------------
-- Table structure for `t_ziliao`
-- ----------------------------
DROP TABLE IF EXISTS `t_ziliao`;
CREATE TABLE `t_ziliao` (
  `ziliaoId` int(11) NOT NULL auto_increment COMMENT '资料id',
  `title` varchar(60) NOT NULL COMMENT '标题',
  `content` varchar(8000) NOT NULL COMMENT '描述',
  `ziliaoFile` varchar(60) NOT NULL COMMENT '资料文件',
  `addTime` varchar(20) default NULL COMMENT '添加时间',
  PRIMARY KEY  (`ziliaoId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_ziliao
-- ----------------------------
INSERT INTO `t_ziliao` VALUES ('1', '员工手册文件', '<p>为了方便门店的管理，我们门店的员工制度规则，请下载阅读！</p><p><br/></p>', 'upload/9b4efb2f-6b24-4988-a26b-c221379104ff.doc', '2018-03-06 11:12:19');
