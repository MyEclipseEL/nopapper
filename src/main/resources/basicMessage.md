##MessageController 基本信息管理类
###点击学院管理
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

###添加学院
```$xslt
GET /message/addFaculty
```
参数
```$xslt
body:{
	fac_num:"10001",  //学院ID
	fac_name:"计算机"  //学院名字
   
}
```
返回
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

###修改学院
```$xslt
GET /message/updateFaculty
```
参数
```$xslt
body:{
	fac_num:"10001",  //学院ID
	fac_name:"计算机"  //学院名字
}
```
返回
```
success:{
    'code'  : 0,
    'msg'   : '修改成功"',
    'data'  : null
}
error:{
          'code'  : 1003,1004...,
          'msg'   : '失败原因',
          'data'  : null
      }
```

###查询学院
```$xslt
GET /message/findFaculty
```
参数
```$xslt
body:{
	fac_num:"10001",  //学院ID
	or
	fac_name:"计算机"  //学院名字
}
```
返回
```
success:{
    'code'  : 0,
    'msg'   : 'success',
    'data'  : "Faculty": {
					"fac_num": "10001",
					"fac_name": "计算机院",    
					"tip":"1"
			   }
}
error:{
          'code'  : 1003,1004...,
          'msg'   : '失败原因',
          'data'  : null
      }
```

###点击学院进入专业管理
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
###添加专业
```$xslt
GET /message/addDept
```
参数
```$xslt
body:{
	dept_num:"10001",  //专业ID
	dept_name:"软件工程",   //专业名字
	faculty:"10001",	//学院ID
	
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
###修改专业
```$xslt
GET /message/updateDept
```
参数
```$xslt
body:{
	dept_num:"10001",  //专业ID
	dept_name:"软件工程",   //专业名字	
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
###查询专业
```$xslt
GET /message/findDept
```
参数
```$xslt
body:{
	dept_num:"10001",  //专业ID
	dept_name:"软件工程",   //专业名字	
}
```
success:{
    'code'  : 0,
    'msg'   : 'success',
    'data'  : Department:{
					"dept_num": "10001",
					"dept_name": "软件工程",    
					"faculty":"10001",
					"tip":"1"
				}
}
error:{
          'code'  : 1003,1004...,
          'msg'   : '失败原因',
          'data'  : null
      }
```

###管理所有课程
```$xslt
GET /message/selectAllCourse
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
    'data'  : 	{
					"c_id": "1",
					"c_name": "马克思主义理论",    
					"tip":"1"
				},
				{
					"c_id": "2",
					"c_name": "中国近代史",    
					"tip":"2"
				}
}
error:{
          'code'  : 1003,1004...,
          'msg'   : '失败原因',
          'data'  : null
      }
```
###添加课程
```$xslt
GET /message/addCourse
```
参数
```$xslt
body:{
	c_id: "1",     //课程ID
	c_name: "马克思主义理论",     //课程名字
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
###修改课程
```$xslt
GET /message/updateCourse
```
参数
```$xslt
body:{
	c_id: "1",     //课程ID
	c_name: "马克思主义理论",     //课程名字
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
###查询课程
```$xslt
GET /message/findCourse
```
参数
```$xslt
body:{
	c_id: "1",     //课程ID
	c_name: "马克思主义理论",     //课程名字
}
```
success:{
    'code'  : 0,
    'msg'   : '修改成功',
    'data'  : {
				"c_id":"1"
				"c_name":"马克思主义理论"
				"tip":"1"
				
			   }
}
error:{
          'code'  : 1003,1004...,
          'msg'   : '失败原因',
          'data'  : null
      }
```