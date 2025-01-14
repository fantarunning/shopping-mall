/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3306
 Source Schema         : store

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 09/01/2025 15:10:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '账号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1261023234 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '管理员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'admin', '111111', '管理员', 'http://localhost:8080/files/1734800996126-OIP-C.jpg', 'ADMIN', '18899991111', 'admin2@xm.com');
INSERT INTO `admin` VALUES (1261023233, 'test', '123456', '王五', 'http://localhost:8080/files/1734800996126-OIP-C.jpg', 'ADMIN', '14573667784', '123@qq.com');

-- ----------------------------
-- Table structure for adminmanage
-- ----------------------------
DROP TABLE IF EXISTS `adminmanage`;
CREATE TABLE `adminmanage`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '电话',
  `avatar` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '头像',
  `role` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '角色',
  `email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `money` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '金额',
  `sex` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '性别',
  `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '密码',
  `intro` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '简介',
  `createtime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '用户信息表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of adminmanage
-- ----------------------------
INSERT INTO `adminmanage` VALUES (1, 'admin', '12121212121', '/uploads/1.jpg', '管理员', '899999595@qq.com', '9888.0', '女', '111111', '吃吃吃', '2024-12-20 02:32:11');
INSERT INTO `adminmanage` VALUES (2, '哆啦A梦', '16123599785', '/uploads/2.jpg', '管理员', '110@qq.com', '13808', '男', '111111', '喝喝喝', '2024-11-01 17:02:28');
INSERT INTO `adminmanage` VALUES (3, '大雄', '12333555555', '/uploads/3.jpg', '管理员', '1111135613@qq.com', '572.0', '男', '111111', '玩玩玩', '2024-06-29 17:43:21');

-- ----------------------------
-- Table structure for cartitem
-- ----------------------------
DROP TABLE IF EXISTS `cartitem`;
CREATE TABLE `cartitem`  (
  `cart_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '购物车id',
  `good_id` int(0) NOT NULL COMMENT '商品id',
  `count` int(0) NOT NULL COMMENT '商品数量',
  `goodsname` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '商品名称',
  `price` decimal(10, 2) NOT NULL COMMENT '商品价格',
  `createtime` datetime(0) NULL DEFAULT NULL COMMENT '购物车创建时间',
  `user_id` int(0) NOT NULL COMMENT '用户id',
  `standard` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '规格',
  PRIMARY KEY (`cart_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for commentmanage
-- ----------------------------
DROP TABLE IF EXISTS `commentmanage`;
CREATE TABLE `commentmanage`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `fid` int(0) NULL DEFAULT NULL COMMENT '父ID',
  `level` int(0) NULL DEFAULT NULL COMMENT '层级',
  `username` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `content` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '留言内容',
  `goodsid` int(0) NULL DEFAULT NULL COMMENT '商品id',
  `picurl` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '图片',
  `score` int(0) NULL DEFAULT NULL COMMENT '评分',
  `createtime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '留言表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of commentmanage
-- ----------------------------
INSERT INTO `commentmanage` VALUES (1, 1, 0, '用户2', '香味真的好闻，淡淡的清香，很喜欢', 1, 'http://localhost:8080/files/1734698388670-1.jpg', 5, '2024-12-20 23:56:21');
INSERT INTO `commentmanage` VALUES (2, 1, 1, '用户2', '香味留香时长很长', 1, 'http://localhost:8080/files/1734698388670-1.jpg', 5, '2024-12-18 23:57:36');

-- ----------------------------
-- Table structure for goodsmanage
-- ----------------------------
DROP TABLE IF EXISTS `goodsmanage`;
CREATE TABLE `goodsmanage`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `goodsname` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `price` double(10, 2) NULL DEFAULT NULL COMMENT '单价',
  `intro` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '简介',
  `amount` int(0) NULL DEFAULT NULL COMMENT '库存',
  `type` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商品类别',
  `imgurl` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '图片',
  `remark` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除，0未删除，1删除',
  `recommend` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否推荐。0不推荐，1推荐',
  `category_id` bigint(0) NULL DEFAULT NULL COMMENT '分类id',
  `sales` bigint(0) NOT NULL DEFAULT 0 COMMENT '销量',
  `sale_money` double(10, 2) NULL DEFAULT 0.00 COMMENT '销售额',
  `discount` double(10, 2) NOT NULL DEFAULT 1.00 COMMENT '折扣',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 483991558 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of goodsmanage
-- ----------------------------
INSERT INTO `goodsmanage` VALUES (1, '山茶花香薰', 55.00, '淡淡山茶花香', 50, '液体香薰', '1.jpg', '已上架', 0, 0, NULL, 0, 0.00, 1.00);
INSERT INTO `goodsmanage` VALUES (2, '郁金香香薰', 49.90, '留香持久', 23, '液体香薰', '2.jpg', '未上架', 0, 0, NULL, 0, 0.00, 1.00);
INSERT INTO `goodsmanage` VALUES (3, '助眠香薰', 48.00, '不刺鼻', 66, '固体香薰', '3.png', '已上架', 0, 0, NULL, 0, 0.00, 1.00);
INSERT INTO `goodsmanage` VALUES (4, '室内香薰', 39.90, '舒服放松', 100, '燃体香薰', '4.png', '未上架', 0, 0, NULL, 0, 0.00, 1.00);
INSERT INTO `goodsmanage` VALUES (5, '全套制作工具', 666.00, '全套香薰制作工具', 15, '工具', '5.jpg', '未上架', 0, 0, NULL, 0, 0.00, 1.00);
INSERT INTO `goodsmanage` VALUES (6, '原料', 12.00, '香薰原料，自己制作', 12, '原料', '6.png', '已下架', 0, 0, NULL, 0, 0.00, 1.00);
INSERT INTO `goodsmanage` VALUES (7, '山茶花香薰', 55.00, '淡淡山茶花香', 50, '液体香薰', 'http://localhost:8080/files/1734698417840-1.jpg', '已上架', 0, 0, NULL, 0, 0.00, 1.00);
INSERT INTO `goodsmanage` VALUES (8, '郁金香香薰', 49.90, '留香持久', 23, '液体香薰', 'http://localhost:8080/files/1734698417840-1.jpg', '未上架', 0, 0, NULL, 0, 0.00, 1.00);
INSERT INTO `goodsmanage` VALUES (9, '助眠香薰', 48.00, '不刺鼻', 66, '固体香薰', 'http://localhost:8080/files/1734698417840-1.jpg', '已上架', 0, 0, NULL, 0, 0.00, 1.00);
INSERT INTO `goodsmanage` VALUES (10, '室内香薰', 39.90, '舒服放松', 100, '燃体香薰', 'http://localhost:8080/files/1734698417840-1.jpg', '未上架', 0, 0, NULL, 0, 0.00, 1.00);
INSERT INTO `goodsmanage` VALUES (11, '全套制作工具', 666.00, '全套香薰制作工具', 15, '工具', 'http://localhost:8080/files/1734698417840-1.jpg', '未上架', 0, 0, NULL, 0, 0.00, 1.00);
INSERT INTO `goodsmanage` VALUES (12, '原料', 12.00, '香薰原料，自己制作', 12, '原料', 'http://localhost:8080/files/1734698417840-1.jpg', '已下架', 0, 0, NULL, 0, 0.00, 1.00);
INSERT INTO `goodsmanage` VALUES (483991557, '1', 1.00, '1', 1, '液体香薰', 'http://localhost:8080/files/1734698428366-1.jpg', '未上架', 0, 0, NULL, 0, 0.00, 1.00);

-- ----------------------------
-- Table structure for noticemanage
-- ----------------------------
DROP TABLE IF EXISTS `noticemanage`;
CREATE TABLE `noticemanage`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '公告标题',
  `content` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '公告内容',
  `time` datetime(0) NULL DEFAULT NULL COMMENT '发布时间',
  `notice_type` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '公告类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of noticemanage
-- ----------------------------
INSERT INTO `noticemanage` VALUES (1, '招聘店家100家', '招聘店家100家，过审核即可，前50家不收取平台费用', '2024-12-18 23:58:38', '招聘公告');
INSERT INTO `noticemanage` VALUES (2, '香薰制作赛事', '阿巴阿巴,香薰比赛，获得胜利者奖金1000元', '2024-12-19 00:00:49', '香薰赛事');
INSERT INTO `noticemanage` VALUES (7, '5', '5555555', '2024-12-21 00:05:41', '香薰赛事');

-- ----------------------------
-- Table structure for ordermanage
-- ----------------------------
DROP TABLE IF EXISTS `ordermanage`;
CREATE TABLE `ordermanage`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `picture` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商品图片',
  `order_num` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '订单编号',
  `goodsname` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `type` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商品类型',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '单价',
  `number` int(0) NULL DEFAULT NULL COMMENT '数量',
  `price_all` decimal(10, 2) NULL DEFAULT NULL COMMENT '总价',
  `status` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '订单状态',
  `people` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '购买人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '订单创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ordermanage
-- ----------------------------
INSERT INTO `ordermanage` VALUES (-157806590, '', '1', '1', '固体香薰', 1.00, 1, NULL, '已取消', '1', '2024-12-19 23:49:08');
INSERT INTO `ordermanage` VALUES (1, '1.png', '123', '室内香薰', '固体香薰', 49.00, 1, 49.00, '已支付', '穆大侠', '2024-11-25 00:00:00');
INSERT INTO `ordermanage` VALUES (12, '10.jpg', '887', '室内香薰', '液体香薰', 18.00, 1, 18.00, '已发货', '懒洋洋', '2024-11-22 23:44:23');
INSERT INTO `ordermanage` VALUES (13, '9.jpg', '696', '衣服香香', '半流体香薰', 49.90, 5, 249.50, '已发货', '喜羊羊', '2024-11-06 23:43:48');
INSERT INTO `ordermanage` VALUES (14, '7.jpeg', '333', '室内香薰', '液体香薰', 39.00, 1, 39.00, '已取消', '派大星', '2024-11-06 23:43:48');
INSERT INTO `ordermanage` VALUES (15, '5.png', '111', '室内香薰', '液体香薰', 55.00, 10, 550.00, '未发货', '大雄3', '2024-11-21 23:44:33');
INSERT INTO `ordermanage` VALUES (16, '1.png', '123', '室内香薰', '固体香薰', 49.00, 1, 49.00, '已支付', '穆大侠', '2024-11-25 00:00:00');
INSERT INTO `ordermanage` VALUES (17, '2.jpg', '456', '衣服香香', '液体香薰', 89.00, 1, 89.00, '已支付', '锅盖头', '2024-11-25 00:00:00');
INSERT INTO `ordermanage` VALUES (18, '3.jpeg', '789', '无味卫生间', '液体香薰', 12.00, 2, 24.00, '待支付', '小霞儿', '2024-11-24 00:00:00');
INSERT INTO `ordermanage` VALUES (19, '4.png', '753', '香薰皂', '固体香薰', 9.90, 1, 9.90, '已支付', '木木', '2024-11-24 00:00:00');
INSERT INTO `ordermanage` VALUES (20, '5.png', '159', '室内香薰', '液体香薰', 55.00, 1, 55.00, '已发货', '大雄', '2024-11-21 23:44:33');
INSERT INTO `ordermanage` VALUES (21, '6.jpg', '782', '衣服香香', '液体香薰', 33.00, 3, 99.00, '已完成', '海绵宝宝', '2024-11-06 23:43:48');
INSERT INTO `ordermanage` VALUES (22, '7.jpeg', '254', '室内香薰', '液体香薰', 39.00, 1, 39.00, '已取消', '派大星', '2024-11-06 23:43:48');
INSERT INTO `ordermanage` VALUES (23, '8.jpeg', '722', '香薰皂', '液体香薰', 27.00, 2, 54.00, '已完成', '癞皮狗', '2024-11-06 23:43:48');
INSERT INTO `ordermanage` VALUES (24, '9.jpg', '722', '衣服香香', '半流体香薰', 49.90, 5, 249.50, '已发货', '喜羊羊', '2024-11-06 23:43:48');
INSERT INTO `ordermanage` VALUES (26, '10.jpg', '735', '室内香薰', '液体香薰', 18.00, 1, 18.00, '已发货', '懒洋洋', '2024-11-22 23:44:23');
INSERT INTO `ordermanage` VALUES (27, '10.jpg', '887', '室内香薰', '液体香薰', 18.00, 1, 18.00, '已发货', '懒洋洋', '2024-11-22 23:44:23');
INSERT INTO `ordermanage` VALUES (28, '9.jpg', '696', '衣服香香', '半流体香薰', 49.90, 5, 249.50, '已发货', '喜羊羊', '2024-11-06 23:43:48');
INSERT INTO `ordermanage` VALUES (29, '7.jpeg', '333', '室内香薰', '液体香薰', 39.00, 1, 39.00, '已取消', '派大星', '2024-11-06 23:43:48');
INSERT INTO `ordermanage` VALUES (30, '5.png', '112', '室内香薰', '液体香薰', 55.00, 10, 550.00, '未发货', '大雄3', '2024-11-21 23:44:33');
INSERT INTO `ordermanage` VALUES (31, '', '1', '1', '固体香薰', 1.00, 1, NULL, '已取消', '1', '2024-12-19 23:49:08');
INSERT INTO `ordermanage` VALUES (32, '1.png', '123', '室内香薰', '固体香薰', 49.00, 1, 49.00, '已支付', '穆大侠', '2024-11-25 00:00:00');
INSERT INTO `ordermanage` VALUES (33, '10.jpg', '887', '室内香薰', '液体香薰', 18.00, 1, 18.00, '已发货', '懒洋洋', '2024-11-22 23:44:23');
INSERT INTO `ordermanage` VALUES (34, '9.jpg', '696', '衣服香香', '半流体香薰', 49.90, 5, 249.50, '已发货', '喜羊羊', '2024-11-06 23:43:48');
INSERT INTO `ordermanage` VALUES (35, '7.jpeg', '333', '室内香薰', '液体香薰', 39.00, 1, 39.00, '已取消', '派大星', '2024-11-06 23:43:48');
INSERT INTO `ordermanage` VALUES (36, '5.png', '111', '室内香薰', '液体香薰', 55.00, 10, 550.00, '未发货', '大雄3', '2024-11-21 23:44:33');
INSERT INTO `ordermanage` VALUES (37, '1.png', '123', '室内香薰', '固体香薰', 49.00, 1, 49.00, '已支付', '穆大侠', '2024-11-25 00:00:00');
INSERT INTO `ordermanage` VALUES (38, '2.jpg', '456', '衣服香香', '液体香薰', 89.00, 1, 89.00, '已支付', '锅盖头', '2024-11-25 00:00:00');
INSERT INTO `ordermanage` VALUES (39, '3.jpeg', '789', '无味卫生间', '液体香薰', 12.00, 2, 24.00, '待支付', '小霞儿', '2024-11-24 00:00:00');
INSERT INTO `ordermanage` VALUES (40, '4.png', '753', '香薰皂', '固体香薰', 9.90, 1, 9.90, '已支付', '木木', '2024-11-24 00:00:00');
INSERT INTO `ordermanage` VALUES (41, '5.png', '159', '室内香薰', '液体香薰', 55.00, 1, 55.00, '已发货', '大雄', '2024-11-21 23:44:33');
INSERT INTO `ordermanage` VALUES (42, '6.jpg', '782', '衣服香香', '液体香薰', 33.00, 3, 99.00, '已完成', '海绵宝宝', '2024-11-06 23:43:48');
INSERT INTO `ordermanage` VALUES (43, '7.jpeg', '254', '室内香薰', '液体香薰', 39.00, 1, 39.00, '已取消', '派大星', '2024-11-06 23:43:48');
INSERT INTO `ordermanage` VALUES (44, '8.jpeg', '722', '香薰皂', '液体香薰', 27.00, 2, 54.00, '已完成', '癞皮狗', '2024-11-06 23:43:48');
INSERT INTO `ordermanage` VALUES (45, '9.jpg', '722', '衣服香香', '半流体香薰', 49.90, 5, 249.50, '已发货', '喜羊羊', '2024-11-06 23:43:48');
INSERT INTO `ordermanage` VALUES (46, '10.jpg', '725', '室内香薰', '液体香薰', 18.00, 1, 18.00, '已发货', '懒洋洋', '2024-11-22 23:44:23');
INSERT INTO `ordermanage` VALUES (47, '10.jpg', '887', '室内香薰', '液体香薰', 18.00, 1, 18.00, '已发货', '懒洋洋', '2024-11-22 23:44:23');
INSERT INTO `ordermanage` VALUES (48, '9.jpg', '696', '衣服香香', '半流体香薰', 49.90, 5, 249.50, '已发货', '喜羊羊', '2024-11-06 23:43:48');
INSERT INTO `ordermanage` VALUES (49, '7.jpeg', '333', '室内香薰', '液体香薰', 39.00, 1, 39.00, '已取消', '派大星', '2024-11-06 23:43:48');
INSERT INTO `ordermanage` VALUES (50, 'http://localhost:8080/files/1734698388670-1.jpg', '112', '室内香薰', '液体香薰', 55.00, 10, 550.00, '已发货', '大雄3', '2024-11-21 23:44:33');

-- ----------------------------
-- Table structure for popularmanage
-- ----------------------------
DROP TABLE IF EXISTS `popularmanage`;
CREATE TABLE `popularmanage`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '科普主题',
  `content` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '内容',
  `url` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '图片或视频',
  `type` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '科普类型',
  `author` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '发布人',
  `time` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '发布时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of popularmanage
-- ----------------------------
INSERT INTO `popularmanage` VALUES (1, '原料科普', '香薰原料属性和作用科普', 'a.png', '原料科普', 'a', '2024-12-18 23:58:38');
INSERT INTO `popularmanage` VALUES (2, '注意事项', '使用香薰时注意事项', 'b.png', '注意事项', 'b', '2024-12-18 23:58:38');
INSERT INTO `popularmanage` VALUES (3, '教学', '如何制作香薰', 'http://localhost:8080/files/1734849203902-1.jpg', '教学视频', 'f', '2024-12-18 23:58:38');
INSERT INTO `popularmanage` VALUES (4, '1', '1', 'http://localhost:8080/files/1734870730163-OIP-C.jpg', '教学视频', '1', '2024-12-22 20:30:29');

-- ----------------------------
-- Table structure for type1
-- ----------------------------
DROP TABLE IF EXISTS `type1`;
CREATE TABLE `type1`  (
  `id` int(0) NOT NULL COMMENT 'id',
  `type` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '商品一级分类',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of type1
-- ----------------------------
INSERT INTO `type1` VALUES (1, '原料');
INSERT INTO `type1` VALUES (2, '工具');
INSERT INTO `type1` VALUES (3, '液体香薰');
INSERT INTO `type1` VALUES (4, '燃体香薰');
INSERT INTO `type1` VALUES (5, '固体香薰');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '账号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1261023251 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '管理员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'aaa', '111111', '小红帽', 'http://localhost:8080/files/1734800996126-OIP-C.jpg', 'USER', '18866551111', 'admin1@xm.com');
INSERT INTO `user` VALUES (1261023233, 'bbb', '123456', '大灰狼', 'http://localhost:8080/files/1734800996126-OIP-C.jpg', 'USER', '14573667784', '123@qq.com');
INSERT INTO `user` VALUES (1261023250, '1', '1', '1', NULL, 'USER', NULL, NULL);

-- ----------------------------
-- Table structure for usermanage
-- ----------------------------
DROP TABLE IF EXISTS `usermanage`;
CREATE TABLE `usermanage`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '电话',
  `avatar` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '头像',
  `email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `role` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '角色',
  `money` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '金额',
  `sex` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '性别',
  `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '密码',
  `intro` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '简介',
  `createtime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '用户信息表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of usermanage
-- ----------------------------
INSERT INTO `usermanage` VALUES (1, 'admin', '12121212121', '/uploads/1.jpg', '899999595@qq.com', '管理员', '9888.0', '女', '111111', '吃吃吃', '2024-12-20 02:32:11');
INSERT INTO `usermanage` VALUES (2, '哆啦A梦', '16123599785', '/uploads/2.jpg', '110@qq.com', '管理员', '13808', '男', '111111', '喝喝喝', '2024-11-01 17:02:28');
INSERT INTO `usermanage` VALUES (3, '大雄', '12333555555', '/uploads/3.jpg', '1111135613@qq.com', '管理员', '572.0', '男', '111111', '玩玩玩', '2024-06-29 17:43:21');
INSERT INTO `usermanage` VALUES (4, '用户1', '18888888888', '/uploads/4.jpg', '2222222222@qq.com', '用户', '300', '男', '123456', '买买买', '2024-06-29 17:43:21');
INSERT INTO `usermanage` VALUES (5, '用户2', '15356565233', '/uploads/5.jpg', '5555555555@qq.com', '用户', '5000', '女', '123456', '看看看', '2024-11-01 17:02:28');
INSERT INTO `usermanage` VALUES (7, 'test3', '12345678901', '7.jpg', '12345678@qq.com', '用户', '12345', '女', '123456', '成功！', '2024-11-08 15:24:24');
INSERT INTO `usermanage` VALUES (11, '1', '12222222222', 'http://localhost:8080/files/1734867892440-1.jpg', '1', '用户', '1', '男', '1', '1', '2024-12-22 19:45:02');

SET FOREIGN_KEY_CHECKS = 1;
