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

/*Table structure for table `course` */

DROP TABLE IF EXISTS `course`;

CREATE TABLE `course` (
  `c_id` int(15) NOT NULL AUTO_INCREMENT COMMENT '课程id',
  `c_name` varchar(30) NOT NULL COMMENT '课程名字',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `course` */

/*Table structure for table `department` */

DROP TABLE IF EXISTS `department`;

CREATE TABLE `department` (
  `dept_num` varchar(30) NOT NULL COMMENT '专业代码',
  `dept_name` varchar(30) NOT NULL COMMENT '专业名称',
  `faculty` varchar(30) NOT NULL COMMENT '开设学院',
  PRIMARY KEY (`dept_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `department` */

/*Table structure for table `faculty` */

DROP TABLE IF EXISTS `faculty`;

CREATE TABLE `faculty` (
  `fac_num` varchar(30) NOT NULL COMMENT '学院代码',
  `fac_name` varchar(30) NOT NULL COMMENT '学院名称',
  PRIMARY KEY (`fac_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `faculty` */

/*Table structure for table `grade` */

DROP TABLE IF EXISTS `grade`;

CREATE TABLE `grade` (
  `g_id` int(15) NOT NULL AUTO_INCREMENT COMMENT '年级id',
  `g_year` year(4) NOT NULL,
  `g_class` int(3) NOT NULL,
  PRIMARY KEY (`g_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `grade` */

insert  into `grade`(`g_id`,`g_year`,`g_class`) values (1,2015,1),(2,2015,2);

/*Table structure for table `item_type` */

DROP TABLE IF EXISTS `item_type`;

CREATE TABLE `item_type` (
  `type_id` char(2) NOT NULL COMMENT '题型编号',
  `type_name` varchar(15) NOT NULL COMMENT '题型名称',
  `type_score` int(4) NOT NULL COMMENT '题型分值',
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `item_type` */

/*Table structure for table `items` */

DROP TABLE IF EXISTS `items`;

CREATE TABLE `items` (
  `item_id` int(15) NOT NULL AUTO_INCREMENT COMMENT '题目id',
  `item_title` varchar(250) DEFAULT NULL COMMENT '题目标题',
  `item_desc` varchar(250) NOT NULL COMMENT '题目描述',
  `item_valid` varchar(250) NOT NULL COMMENT '题目正确答案',
  `item_wrong` varchar(250) NOT NULL COMMENT '题目错误答案',
  `item_type` char(2) NOT NULL COMMENT '题目类型',
  `course` int(15) NOT NULL COMMENT '附属课程',
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `items` */

/*Table structure for table `office` */

DROP TABLE IF EXISTS `office`;

CREATE TABLE `office` (
  `office_id` varchar(30) NOT NULL COMMENT '教研室号',
  `office_name` varchar(30) NOT NULL COMMENT '教研室名称',
  `dept` varchar(30) NOT NULL COMMENT '所属系别',
  PRIMARY KEY (`office_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `office` */

/*Table structure for table `score` */

DROP TABLE IF EXISTS `score`;

CREATE TABLE `score` (
  `student` varchar(10) NOT NULL COMMENT '学号',
  `course` int(15) NOT NULL COMMENT '课程号',
  `s_score` decimal(10,0) NOT NULL COMMENT '成绩',
  PRIMARY KEY (`student`,`course`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `score` */

/*Table structure for table `students` */

DROP TABLE IF EXISTS `students`;

CREATE TABLE `students` (
  `stu_num` varchar(10) NOT NULL,
  `stu_ID` varchar(18) NOT NULL COMMENT '身份证号',
  `stu_name` varchar(15) NOT NULL COMMENT '学生姓名',
  `stu_faculty` varchar(30) NOT NULL COMMENT '学院',
  `stu_dept` varchar(30) NOT NULL COMMENT '专业',
  `grade` int(15) NOT NULL COMMENT '班级',
  `stu_pwd` varchar(18) NOT NULL COMMENT '登陆密码',
  PRIMARY KEY (`stu_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `students` */

insert  into `students`(`stu_num`,`stu_ID`,`stu_name`,`stu_faculty`,`stu_dept`,`grade`,`stu_pwd`) values ('2016034400','555555555555555555','','计算机与信息工程','软件',2,'123456');

/*Table structure for table `teachers` */

DROP TABLE IF EXISTS `teachers`;

CREATE TABLE `teachers` (
  `t_num` varchar(15) NOT NULL COMMENT '教师工号',
  `t_name` varchar(15) NOT NULL COMMENT '老师姓名',
  `t_faculty` varchar(30) NOT NULL COMMENT '学院',
  `t_dept` varchar(30) NOT NULL COMMENT '专业',
  `t_office` varchar(30) NOT NULL COMMENT '教研室',
  `t_pwd` varchar(15) NOT NULL COMMENT '密码',
  PRIMARY KEY (`t_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `teachers` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
