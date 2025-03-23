-- ----------------------------
-- Table structure for T_USER
-- ----------------------------
CREATE TABLE t_user (
   ID INT NOT NULL ,
   USERNAME VARCHAR(20) NOT NULL ,
   PASSWD VARCHAR(128) NOT NULL ,
   CREATE_TIME DATETIME NULL ,
   STATUS CHAR(1) NOT NULL ,
   PRIMARY KEY (ID)
);

-- ----------------------------
-- Column comments for T_USER
-- ----------------------------
ALTER TABLE t_user MODIFY COLUMN USERNAME VARCHAR(20) COMMENT '用户名';
ALTER TABLE t_user MODIFY COLUMN PASSWD VARCHAR(128) COMMENT '密码';
ALTER TABLE t_user MODIFY COLUMN CREATE_TIME DATETIME COMMENT '创建时间';
ALTER TABLE t_user MODIFY COLUMN STATUS CHAR(1) COMMENT '是否有效 1：有效  0：锁定';

-- ----------------------------
-- Records of T_USER
-- ----------------------------
INSERT INTO t_user (ID, USERNAME, PASSWD, CREATE_TIME, STATUS) VALUES (2, 'test', '7a38c13ec5e9310aed731de58bbc4214', STR_TO_DATE('2017-11-19 17:20:21', '%Y-%m-%d %H:%i:%s'), '0');
INSERT INTO t_user (ID, USERNAME, PASSWD, CREATE_TIME, STATUS) VALUES (1, 'mrbird', '42ee25d1e43e9f57119a00d0a39e5250', STR_TO_DATE('2017-11-19 10:52:48', '%Y-%m-%d %H:%i:%s'), '1');
