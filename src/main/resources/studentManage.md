##StudentManageController 学生信息管理类
```
###添加学生
```$xslt
GET /studentManage/addStudent
```
参数
```$xslt
body:{
	"stu_num":"2016034400",//学号
	"stu_ID":"555555555555555555",//身份证号
	"stu_name":"bb",//姓名
	"stu_faculty":"10001",//就读学院
	"dept":"10001",//就读专业
	"gradeExample":"1",//就读班级
	"stu_pwd":"123456"//登录密码
}
```
success:{
    'code'  : 0,
    'msg'   : '添加成功',
    'data'  : null 
	}
error:{
          'code'  : 1003,1004...,
          'msg'   : '失败原因',
          'data'  : null
      }
```
###删除学生
```$xslt
GET /studentManage/deleteStudent
```
参数
```$xslt
body:{
	"stu_num":"1"
}
```
success:{
    'code'  : 0,
    'msg'   : '删除成功',
    'data'  : null 
	}
error:{
          'code'  : 1003,1004...,
          'msg'   : '失败原因',
          'data'  : null
      }
```
###修改学生
```$xslt
GET /studentManage/updateStudent
```
参数
```$xslt
body:{
	"stu_num":"2016034400",//学号
	"stu_ID":"555555555555555555",//身份证号
	"stu_name":"bb",//姓名
	"stu_faculty":"10001",//就读学院
	"dept":"10001",//就读专业
	"gradeExample":"1",//就读班级
	"stu_pwd":"123456"//登录密码
}
```
success:{
    'code'  : 0,
    'msg'   : '修改成功',
    'data'  : null 
	}
error:{
          'code'  : 1003,1004...,
          'msg'   : '失败原因',
          'data'  : null
      }
```
###查询学生(所有查询)
```$xslt
GET /studentManage/selectStudent
```
参数
```$xslt
body:{
	
	
	"stu_name":"bb",//姓名
	"stu_faculty":"计算机院"
	"dept":"软件工程专业"
}
```
success:{
    'code'  : 0,
    'msg'   : 'success',
    'data'  : StudentVo:{
                stu_name:"bb", //姓名
                stu_num:"2016034400" , //学号
                dept_name:"软件工程", //就读专业
                g_year:2015,
                g_class:3,
                stu_ID:"11111111111111111"//身份证
               
				} 
	}
error:{
          'code'  : 1003,1004...,
          'msg'   : '失败原因',
          'data'  : null
      }
```