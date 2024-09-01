/*
 Navicat Premium Data Transfer

 Source Server Type    : MySQL
 Source Server Version : 90001
 Source Schema         : demo111

 Target Server Type    : MySQL
 Target Server Version : 90001
 File Encoding         : 65001

*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '用户名',
  `age` int NULL DEFAULT NULL COMMENT '年龄',
  `gender` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '性别',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '家庭地址',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '李白', 21, '男', '爱民街38号', '2024-08-24 16:00:00');
INSERT INTO `user` VALUES (2, '杜甫', 19, '男', '爱民街38号', '2024-08-08 14:15:06');
INSERT INTO `user` VALUES (3, '薛之谦', 26, '男', '爱民街38号', '2024-08-08 14:15:06');
INSERT INTO `user` VALUES (4, '张三', 21, '男', '爱民街38号', '2024-08-08 14:15:06');
INSERT INTO `user` VALUES (6, '王也', 29, '男', '爱民街38号', '2024-08-08 14:15:06');
INSERT INTO `user` VALUES (10, '蔡徐坤', 26, '男', '长江大道21号', '2024-08-24 06:47:36');
INSERT INTO `user` VALUES (11, '蔡徐坤2', 23, '男', '长江大道21号', '2024-08-24 14:53:40');
INSERT INTO `user` VALUES (12, '欧阳娜娜', 27, '女', '北海大街21号', '2024-08-24 14:57:15');
INSERT INTO `user` VALUES (13, '李白1', 25, '女', '宝安区99999999', '2024-08-24 16:20:28');
INSERT INTO `user` VALUES (14, '彭九1', 31, '女', '呵呵呵大街13e号', '2024-08-24 16:19:36');
INSERT INTO `user` VALUES (15, '武藤9', 39, '男', '飞火大街23号', '2024-08-24 15:54:46');
INSERT INTO `user` VALUES (16, '林八百', 56, '女', '八百大道890号', '2024-08-24 16:19:32');
INSERT INTO `user` VALUES (17, '藤11111', 11, '男', '11大街89号11', '2024-08-24 16:00:00');
INSERT INTO `user` VALUES (18, '欧阳娜娜22', 27, '男', '北海大街22号', '2024-08-25 11:25:19');
INSERT INTO `user` VALUES (20, '百里欧耶', 23, '男', '北海大街22号', '2024-08-24 22:12:36');
INSERT INTO `user` VALUES (21, '薛之谦234', 23, '男', '哈哈街道123', '2024-08-24 22:10:38');
INSERT INTO `user` VALUES (22, '高育良', 52, '男', '汉东省京州大道23号', '2024-08-25 09:29:17');

SET FOREIGN_KEY_CHECKS = 1;
