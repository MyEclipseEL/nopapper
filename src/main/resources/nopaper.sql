/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.22-log : Database - nopaper
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
USE `nopaper`;

/*Table structure for table `exam` */

DROP TABLE IF EXISTS `exam`;

CREATE TABLE `exam` (
  `exam_id` varchar(50) NOT NULL COMMENT '考试号',
  `course` int(15) NOT NULL COMMENT '考试课程',
  `grade` int(15) NOT NULL COMMENT '考试班级',
  `dept` varchar(30) NOT NULL COMMENT '考试专业',
  `pre_time` datetime NOT NULL COMMENT '考试预设时间',
  `begin_time` datetime DEFAULT NULL COMMENT '开始时间',
  `duration` int(15) NOT NULL COMMENT '考试时长',
  `state` int(2) NOT NULL DEFAULT '0' COMMENT '状态 默认0表示考试未开始',
  `tip` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`exam_id`),
  KEY `coures` (`course`),
  KEY `dept` (`dept`),
  KEY `grade` (`grade`),
  CONSTRAINT `exam_ibfk_1` FOREIGN KEY (`course`) REFERENCES `course` (`c_id`),
  CONSTRAINT `exam_ibfk_2` FOREIGN KEY (`dept`) REFERENCES `department` (`dept_num`),
  CONSTRAINT `exam_ibfk_3` FOREIGN KEY (`grade`) REFERENCES `grade` (`g_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `exam` */

insert  into `exam`(`exam_id`,`course`,`grade`,`dept`,`pre_time`,`begin_time`,`duration`,`state`,`tip`) values ('1',1,1,'10001','2019-03-14 13:30:00','2019-03-19 14:55:38',120,1,NULL),('2019031909410111',1,1,'10001','2019-03-19 21:41:01',NULL,120,0,NULL),('201903190941011100011',1,1,'10001','2019-03-19 21:41:01',NULL,120,0,NULL),('201903190941011100012',1,2,'10001','2019-03-19 21:41:01',NULL,120,0,NULL),('2019031909410112',1,2,'10001','2019-03-19 21:41:01',NULL,120,0,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
