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
  id            int          NOT NULL PRIMARY KEY  AUTO_INCREMENT,
  wechatUserId  VARCHAR(255) NOT NULL UNIQUE,
  registerType  int          NOT NULL,
  nickname      VARCHAR(255),
  headimgurl    VARCHAR(2083),
  userName      VARCHAR(255),
  sex           VARCHAR(255),
  language      VARCHAR(255),
  city          VARCHAR(255),
  province      VARCHAR(255),
  country       VARCHAR(255),
  access_token  VARCHAR(255),
  refresh_token VARCHAR(255),
  unionid       VARCHAR(255),
  openid        VARCHAR(255),
  code          VARCHAR(255)
)
  DEFAULT CHARSET = utf8mb4;

DROP TABLE IF EXISTS `t_user_answer`;
CREATE TABLE t_user_answer (
  id              INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
  wechatUserId    VARCHAR(255) NOT NULL,
  questionId      INT          NOT NULL,
  userAnswerIndex int          NOT NULL,
  updateTime      DATETIME     NOT NULL             DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY `wechatUserId` (`wechatUserId`, `questionId`)
)
  DEFAULT CHARSET = utf8mb4;