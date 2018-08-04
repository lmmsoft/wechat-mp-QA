DROP DATABASE IF EXISTS marriage;
CREATE DATABASE marriage;

USE marriage;

DROP TABLE IF EXISTS `t_hello_world`;
CREATE TABLE t_hello_world (
  id     int          NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name   VARCHAR(255) NOT NULL,
  weight FLOAT        NOT NULL
)
  ENGINE = INNODB
  DEFAULT CHARSET = utf8mb4;

DROP TABLE IF EXISTS `t_user`;
CREATE TABLE t_user (
  id             int          NOT NULL PRIMARY KEY  AUTO_INCREMENT,
  wechatUserId   VARCHAR(255) NOT NULL UNIQUE,
  wechatName     VARCHAR(255),
  wechatImageUrl VARCHAR(255),
  userName       VARCHAR(255),
  registerType   int          NOT NULL
)
  DEFAULT CHARSET = utf8mb4;

DROP TABLE IF EXISTS `t_user_answer`;
CREATE TABLE t_user_answer (
  id              INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
  wechatUserId    VARCHAR(255) NOT NULL,
  questionId      INT          NOT NULL,
  userAnswerIndex int          NOT NULL,
  updateTime      DATETIME     NOT NULL             DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY `wechatUserId` (`wechatUserId`,`questionId`)
)
  DEFAULT CHARSET = utf8mb4;