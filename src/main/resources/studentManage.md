##StudentManageController 学生信息管理类
###点击选择学院
```$xslt
GET /message/selectAllFaculty
```
参数
```$xslt
body:{
	null
   
}
```
返回
```
success:{
    'code'  : 0,
    'msg'   : 'success',
    'data'  : [
        {
            "fac_num": "10001",
            "fac_name": "计算机与信息工程",
            "tip": "1"
        },
        {
            "fac_num": "10002",
            "fac_name": "马克思",
            "tip": "2"
        }
        
    ]
}
error:{
          'code'  : 1003,1004...,
          'msg'   : '查询失败',
          'data'  : null
      }
```
###点击选择专业
```$xslt
GET /message/selectAllDept
```
参数
```$xslt
body:{
	fac_num:"10001",  //学院ID
}
```
success:{
    'code'  : 0,
    'msg'   : 'success',
    'data'  :  {
					"dept_num": "10001",
					"dept_name": "软件工程",    
					"faculty":"10001",
					"tip":"1"
				},
				{
					"dept_num": "10002",
					"dept_name": "物联网工程",    
					"faculty":"10002",
					"tip":"2"
				}
}
error:{
          'code'  : 1003,1004...,
          'msg'   : '失败原因',
          'data'  : null
      }
```
###选择学年
```$xslt
GET /studentManage/selectAllGrade
```
参数
```$xslt
body:{
	null
}
```
success:{
    'code'  : 0,
    'msg'   : 'success',
    'data'  :  grades:[
						{g_year:2015},
						{g_year:2016},
						{g_year:2017},
						{g_year:2018}
					  ]
}
error:{
          'code'  : 1003,1004...,
          'msg'   : '失败原因',
          'data'  : null
      }
```
###显示所有学生
```$xslt
GET /studentManage/selectStudent
```
参数
```$xslt
body:{
	stu_faculty:"计算机院",   //就读学院
	dept:"10001",       //就读专业
	grade:"1"			//就读班级
}
```
success:{
    'code'  : 0,
    'msg'   : 'success',
    'data'  :  [
				
				{
					"stu_num":"2016034400",
					"stu_ID":"555555555555555555",
					"stu_name":"bb",
					"stu_faculty":"10001",
					"dept":"10001",
					"grade":"1",
					"stu_pwd":"123456"
				}
				{
					"stu_num":"2016034400",
					"stu_ID":"555555555555555555",
					"stu_name":"bb",
					"stu_faculty":"10001",
					"dept":"10001",
					"grade":"1",
					"stu_pwd":"123456"
				}
				.........
				]
error:{
          'code'  : 1003,1004...,
          'msg'   : '失败原因',
          'data'  : null
      }
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
	"grade":"1",//就读班级
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
	"grade":"1",//就读班级
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
###查询学生
```$xslt
GET /studentManage/updateStudent
```
参数
```$xslt
body:{
	"stu_num":"2016034400",//学号
	
	"stu_name":"bb",//姓名
	
}
```
success:{
    'code'  : 0,
    'msg'   : 'success',
    'data'  : Student:{
				"stu_num":"2016034400",
				"stu_ID":"555555555555555555",
				"stu_name":"bb",
				"stu_faculty":"10001",
				"dept":"10001",
				"grade":"1",
				"stu_pwd":"123456"
	
				} 
	}
error:{
          'code'  : 1003,1004...,
          'msg'   : '失败原因',
          'data'  : null
      }
```