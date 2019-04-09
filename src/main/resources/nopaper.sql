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

/*Table structure for table `admins` */

DROP TABLE IF EXISTS `admins`;

CREATE TABLE `admins` (
  `id` varchar(15) NOT NULL,
  `name` varchar(15) NOT NULL,
  `pwd` varchar(15) NOT NULL,
  `group_id` tinyint(2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `group_id` (`group_id`),
  CONSTRAINT `admins_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `groups` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `admins` */

insert  into `admins`(`id`,`name`,`pwd`,`group_id`) values ('2016','1','1',1);

/*Table structure for table `cos_items` */

DROP TABLE IF EXISTS `cos_items`;

CREATE TABLE `cos_items` (
  `id` varchar(30) NOT NULL,
  `url` varchar(50) NOT NULL,
  `course` varchar(15) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `cos_items` */

/*Table structure for table `course` */

DROP TABLE IF EXISTS `course`;

CREATE TABLE `course` (
  `c_id` varchar(15) NOT NULL COMMENT '课程id',
  `c_name` varchar(30) NOT NULL COMMENT '课程名字',
  `tip` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `course` */

insert  into `course`(`c_id`,`c_name`,`tip`) values ('1','软件工程',NULL),('2','马克思',NULL);

/*Table structure for table `department` */

DROP TABLE IF EXISTS `department`;

CREATE TABLE `department` (
  `dept_num` varchar(30) NOT NULL COMMENT '专业代码',
  `dept_name` varchar(30) NOT NULL COMMENT '专业名称',
  `faculty` varchar(30) NOT NULL COMMENT '所属学院',
  `tip` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dept_num`),
  KEY `faculty` (`faculty`),
  CONSTRAINT `department_ibfk_1` FOREIGN KEY (`faculty`) REFERENCES `faculty` (`fac_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `department` */

insert  into `department`(`dept_num`,`dept_name`,`faculty`,`tip`) values ('10001','软件工程','10001',NULL),('10002','物联网','10001',NULL);

/*Table structure for table `exam` */

DROP TABLE IF EXISTS `exam`;

CREATE TABLE `exam` (
  `exam_id` varchar(50) NOT NULL COMMENT '考试号',
  `course` varchar(15) NOT NULL COMMENT '考试课程',
  `teacher` varchar(15) NOT NULL COMMENT '老师',
  `grade` varchar(255) NOT NULL COMMENT '考试班级',
  `dept` varchar(30) DEFAULT NULL COMMENT '考试专业',
  `finish_time` datetime NOT NULL COMMENT '考试结束时间',
  `begin_time` datetime NOT NULL COMMENT '开始时间',
  `duration` int(15) NOT NULL COMMENT '考试时长',
  `state` int(2) NOT NULL DEFAULT '1' COMMENT '状态 默认1表示考试开始',
  `tip` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`exam_id`),
  KEY `coures` (`course`),
  KEY `dept` (`dept`),
  KEY `grade` (`grade`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `exam` */

insert  into `exam`(`exam_id`,`course`,`teacher`,`grade`,`dept`,`finish_time`,`begin_time`,`duration`,`state`,`tip`) values ('2019040305502211','1','1','1,3',NULL,'2019-04-03 19:50:22','2019-04-03 17:50:22',120,1,NULL),('2019040705133511','1','1','4',NULL,'2019-04-07 19:13:35','2019-04-07 17:13:35',120,1,NULL),('2019040804464921','1','2','5,6',NULL,'2019-04-08 18:46:49','2019-04-08 16:46:49',120,1,NULL),('2019040904130511','1','1','7',NULL,'2019-04-09 18:13:06','2019-04-09 16:13:05',120,1,NULL);

/*Table structure for table `faculty` */

DROP TABLE IF EXISTS `faculty`;

CREATE TABLE `faculty` (
  `fac_num` varchar(30) NOT NULL COMMENT '学院代码',
  `fac_name` varchar(30) NOT NULL COMMENT '学院名称',
  `tip` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`fac_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `faculty` */

insert  into `faculty`(`fac_num`,`fac_name`,`tip`) values ('10001','计算机与信息工程',NULL);

/*Table structure for table `grade` */

DROP TABLE IF EXISTS `grade`;

CREATE TABLE `grade` (
  `g_id` varchar(15) NOT NULL COMMENT '年级id',
  `dept` varchar(30) NOT NULL,
  `g_year` int(4) NOT NULL,
  `g_class` int(3) NOT NULL,
  `tip` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`g_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `grade` */

insert  into `grade`(`g_id`,`dept`,`g_year`,`g_class`,`tip`) values ('1','10001',2015,1,NULL),('2','10001',2015,2,NULL),('3','10002',2016,1,NULL),('4','10002',2017,1,NULL),('5','10002',2017,2,NULL),('6','10002',2017,3,NULL),('7','10002',2017,4,NULL);

/*Table structure for table `groups` */

DROP TABLE IF EXISTS `groups`;

CREATE TABLE `groups` (
  `group_id` tinyint(2) NOT NULL,
  `group_type` varchar(15) NOT NULL,
  `tip` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `groups` */

insert  into `groups`(`group_id`,`group_type`,`tip`) values (1,'管理员',NULL),(2,'教师',NULL);

/*Table structure for table `item_type` */

DROP TABLE IF EXISTS `item_type`;

CREATE TABLE `item_type` (
  `type_id` char(2) NOT NULL COMMENT '题型编号',
  `type_name` varchar(15) NOT NULL COMMENT '题型名称',
  `type_score` int(4) DEFAULT NULL COMMENT '题型分值',
  `tip` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `item_type` */

insert  into `item_type`(`type_id`,`type_name`,`type_score`,`tip`) values ('A','单选题',15,NULL),('B','多选题',15,NULL),('C','判断题',15,NULL);

/*Table structure for table `items` */

DROP TABLE IF EXISTS `items`;

CREATE TABLE `items` (
  `item_id` int(15) NOT NULL AUTO_INCREMENT COMMENT '题目id',
  `item_title` varchar(250) DEFAULT NULL COMMENT '题目标题',
  `item_desc` varchar(250) NOT NULL COMMENT '题目描述',
  `item_valid` varchar(250) NOT NULL COMMENT '题目正确答案',
  `item_choice` varchar(250) DEFAULT NULL COMMENT '题目错误答案',
  `item_type` char(2) NOT NULL COMMENT '题目类型',
  `course` varchar(15) NOT NULL COMMENT '附属课程',
  `tip` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`item_id`),
  KEY `course` (`course`),
  KEY `item_type` (`item_type`),
  CONSTRAINT `items_ibfk_2` FOREIGN KEY (`item_type`) REFERENCES `item_type` (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

/*Data for the table `items` */

insert  into `items`(`item_id`,`item_title`,`item_desc`,`item_valid`,`item_choice`,`item_type`,`course`,`tip`) values (1,NULL,'作为中国共产党和社会主义事业指导思想得马克思主义','D','指马克思恩格斯创立的基本理论、基本观点和学说得体系|@|是非正宗得马克思主义|@|是实用的马克思主义|@|是继承和发展了的马克思主义','A','1',NULL),(2,NULL,'题目描述','C,D','错误答案1|@|错误答案2|@|正确答案1|@|正确答案2|@|错误答案','B','1',NULL),(3,NULL,'题目描述','正确','','C','1',NULL),(4,NULL,'题目描述','D','错误答案1|@|错误答案2|@|错误答案3|@|正确答案1','A','1',NULL),(5,NULL,'dwadaw','A','wewewe|@|rrwqr|@|wqqdsf|@|sdwada','A','1',NULL),(6,NULL,'中国第一届领导人','B','刘少奇|@|毛泽东|@|周恩来|@|习近平','A','1',NULL),(7,NULL,'一下哪些是机动车','A,B','小轿车|@|卡车|@|滑板|@|自行车|@|电动车','B','1',NULL),(8,NULL,'rtwqwe','C,D','gfdtrr|@|trfd|@|dwwadw|@|wqwgf|@|sadwa','B','1',NULL),(9,NULL,'五四运动是1919年','正确','','C','1',NULL),(10,NULL,'iuytr','错误','','C','1',NULL),(11,NULL,'五四运动是哪一年','A','1919|@|1918|@|1920|@|1929','A','1',NULL),(12,NULL,'五四运动是哪一年','A','1919|@|1918|@|1920|@|1929','A','1',NULL),(13,NULL,'五四运动是哪一年','A','1919|@|1918|@|1920|@|1929','A','1',NULL),(14,NULL,'五四运动是哪一年','A','1919|@|1918|@|1920|@|1929','A','1',NULL),(15,NULL,'五四运动是哪一年','A','1919|@|1918|@|1920|@|1929','A','1',NULL),(16,NULL,'五四运动是哪一年','A','1919|@|1918|@|1920|@|1929','A','1',NULL),(17,NULL,'苹果是一种水果','正确',NULL,'C','1',NULL),(18,NULL,'马铃薯是水果','错误',NULL,'C','1',NULL),(27,NULL,'以下哪些是蔬菜','A,C','白菜|@|苹果 |@|黄瓜|@|马铃薯|@|草莓','B','1',NULL),(28,NULL,'以下哪些是蔬菜','A','白菜 |@|苹果|@| 香蕉|@| 菠萝|@|草莓','B','1',NULL),(29,NULL,'以下哪些是蔬菜','A,C,D','白菜 |@|苹果|@|马铃薯 |@|番茄|@|草莓','B','1',NULL),(30,NULL,'以下哪些是蔬菜','A,B,C,D','白菜 |@|马铃薯 |@|番茄 |@|黄瓜|@|草莓','B','1',NULL),(32,NULL,'中国第一届领导人','B','刘少奇|@|毛泽东|@|周恩来|@|习近平','A','1',NULL),(33,NULL,'中国第一届领导人是毛泽东吗','正确',NULL,'C','1',NULL),(37,NULL,'下面哪些不是我国的主席','C','刘少奇|@|毛泽东|@|周恩来|@|习近平','A','1',NULL),(38,NULL,'下面哪些不是我国的主席','C','刘少奇|@|毛泽东|@|周恩来|@|习近平','A','1',NULL),(39,NULL,'下面哪些不是我国的主席','C','刘少奇|@|毛泽东|@|周恩来|@|习近平','A','1',NULL);

/*Table structure for table `office` */

DROP TABLE IF EXISTS `office`;

CREATE TABLE `office` (
  `office_id` varchar(30) NOT NULL COMMENT '教研室号',
  `office_name` varchar(30) NOT NULL COMMENT '教研室名称',
  `dept` varchar(30) NOT NULL COMMENT '所属系别',
  `tip` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`office_id`),
  KEY `dept` (`dept`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `office` */

insert  into `office`(`office_id`,`office_name`,`dept`,`tip`) values ('1001','软件调研室','10001',NULL);

/*Table structure for table `paper_edit` */

DROP TABLE IF EXISTS `paper_edit`;

CREATE TABLE `paper_edit` (
  `id` varchar(15) NOT NULL,
  `course` varchar(15) NOT NULL COMMENT '课程',
  `single_count` int(10) NOT NULL COMMENT '单选个数',
  `single_score` int(10) DEFAULT NULL,
  `multiple_count` int(10) NOT NULL COMMENT '多选个数',
  `multiple_score` int(10) DEFAULT NULL,
  `checking_count` int(10) NOT NULL COMMENT '判断题个数',
  `checking_score` int(10) DEFAULT NULL,
  `duration` int(15) NOT NULL,
  `tip` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `course` (`course`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `paper_edit` */

insert  into `paper_edit`(`id`,`course`,`single_count`,`single_score`,`multiple_count`,`multiple_score`,`checking_count`,`checking_score`,`duration`,`tip`) values ('1','1',4,5,5,8,4,10,120,NULL);

/*Table structure for table `score` */

DROP TABLE IF EXISTS `score`;

CREATE TABLE `score` (
  `student` varchar(10) NOT NULL COMMENT '学号',
  `exam` varchar(50) NOT NULL COMMENT '考试号',
  `s_score` decimal(10,2) NOT NULL COMMENT '成绩',
  `tip` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`student`,`exam`),
  KEY `exam` (`exam`),
  CONSTRAINT `score_ibfk_2` FOREIGN KEY (`student`) REFERENCES `students` (`stu_num`),
  CONSTRAINT `score_ibfk_3` FOREIGN KEY (`exam`) REFERENCES `exam` (`exam_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `score` */

insert  into `score`(`student`,`exam`,`s_score`,`tip`) values ('1','2019040305502211','20.00',NULL),('2','2019040705133511','18.00',NULL),('4','2019040804464921','35.00',NULL);

/*Table structure for table `students` */

DROP TABLE IF EXISTS `students`;

CREATE TABLE `students` (
  `stu_num` varchar(10) NOT NULL,
  `stu_ID` varchar(18) NOT NULL COMMENT '身份证号',
  `stu_name` varchar(15) NOT NULL COMMENT '学生姓名',
  `stu_faculty` varchar(30) NOT NULL COMMENT '学院',
  `dept` varchar(30) NOT NULL COMMENT '专业',
  `grade` varchar(15) NOT NULL COMMENT '班级',
  `stu_pwd` varchar(18) NOT NULL DEFAULT '1' COMMENT '登陆密码',
  `tip` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`stu_num`),
  KEY `grade` (`grade`),
  KEY `stu_dept` (`dept`),
  KEY `stu_faculty` (`stu_faculty`),
  CONSTRAINT `students_ibfk_2` FOREIGN KEY (`dept`) REFERENCES `department` (`dept_num`),
  CONSTRAINT `students_ibfk_3` FOREIGN KEY (`stu_faculty`) REFERENCES `faculty` (`fac_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `students` */

insert  into `students`(`stu_num`,`stu_ID`,`stu_name`,`stu_faculty`,`dept`,`grade`,`stu_pwd`,`tip`) values ('1','1','aa','10001','10001','1','1',NULL),('2','2','2','10001','10002','4','1',NULL),('2016034400','555555555555555555','bb','10001','10001','2','123456',NULL),('3','3','3','10001','10001','3','3',NULL),('4','4','4','10001','10002','5','4',NULL),('5','5','5','10001','10002','7','5',NULL);

/*Table structure for table `teach` */

DROP TABLE IF EXISTS `teach`;

CREATE TABLE `teach` (
  `teach_id` varchar(10) NOT NULL,
  `teacher` varchar(15) NOT NULL COMMENT '教师工号',
  `course` varchar(15) NOT NULL COMMENT '课程',
  `dept` varchar(30) DEFAULT NULL COMMENT '专业',
  `grade` varchar(255) NOT NULL COMMENT '班级',
  `tip` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`teach_id`),
  KEY `teacher` (`teacher`),
  KEY `dept` (`dept`),
  KEY `course` (`course`),
  CONSTRAINT `teach_ibfk_1` FOREIGN KEY (`teacher`) REFERENCES `teachers` (`t_num`),
  CONSTRAINT `teach_ibfk_2` FOREIGN KEY (`course`) REFERENCES `course` (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `teach` */

insert  into `teach`(`teach_id`,`teacher`,`course`,`dept`,`grade`,`tip`) values ('1','1','1','10001','1,2,3,4,7',NULL),('2','1','2',NULL,'1,2,7',NULL),('3','2','1',NULL,'4,5,6',NULL);

/*Table structure for table `teachers` */

DROP TABLE IF EXISTS `teachers`;

CREATE TABLE `teachers` (
  `t_num` varchar(15) NOT NULL COMMENT '教师工号',
  `t_name` varchar(15) NOT NULL COMMENT '老师姓名',
  `t_faculty` varchar(30) NOT NULL COMMENT '学院',
  `t_dept` varchar(30) NOT NULL COMMENT '专业',
  `t_office` varchar(30) DEFAULT NULL COMMENT '教研室',
  `t_pwd` varchar(15) NOT NULL COMMENT '密码',
  `group_id` tinyint(2) NOT NULL COMMENT '权限',
  `tip` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`t_num`),
  KEY `t_office` (`t_office`),
  KEY `t_faculty` (`t_faculty`),
  KEY `t_dept` (`t_dept`),
  KEY `group_id` (`group_id`),
  CONSTRAINT `teachers_ibfk_3` FOREIGN KEY (`t_faculty`) REFERENCES `faculty` (`fac_num`),
  CONSTRAINT `teachers_ibfk_4` FOREIGN KEY (`t_dept`) REFERENCES `department` (`dept_num`),
  CONSTRAINT `teachers_ibfk_5` FOREIGN KEY (`group_id`) REFERENCES `groups` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `teachers` */

insert  into `teachers`(`t_num`,`t_name`,`t_faculty`,`t_dept`,`t_office`,`t_pwd`,`group_id`,`tip`) values ('1','1','10001','10001','1001','1',2,NULL),('1001','admin','10001','10001','1001','123456',2,NULL),('2','2','10001','10001',NULL,'2',2,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
