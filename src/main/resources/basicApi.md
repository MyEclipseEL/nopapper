##BasicController 基本信息管理类
###添加课程
```$xslt
GET /basic/addCourse
```
参数
```$xslt
body:{
	c_name:'1',  //课程名称
   
}
```
返回
```
success:{
    'code'  : 0,
    'msg'   : '添加成功"',
    'data'  : null
}
error:{
          'code'  : 1003,1004...,
          'msg'   : '失败原因',
          'data'  : null
      }
```

###删除课程
```$xslt
GET /basic/delCourse
```
参数
```$xslt
body:{
	c_id:1,  //课程ID
   
}
```
返回
```
success:{
    'code'  : 0,
    'msg'   : '删除成功"',
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
GET /basic/updateCourse
```
参数
```$xslt
body:{
	c_id:1,  //课程ID
	c_naem:'1'  //课程名称
}
```
返回
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
GET /basic/findCourse
```
参数
```$xslt
body:{
	c_id:1,  //课程ID
	c_naem:'1'  //课程名称
}
```
返回
```
success:{
    'code'  : 0,
    'msg'   : '查询成功',
    'data'  : "course": {"c_id": 1,"c_name": "软件工程"}
          
}
error:{
          'code'  : 1003,1004...,
          'msg'   : '失败原因',
          'data'  : null
      }
```
###添加专业
```$xslt
GET /basic/addDept
```
参数
```$xslt
body:{
	dept_num:'1',  //专业代码
    dept_name:'1'  //专业名称
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
###删除专业
```$xslt
GET /basic/delDept
```
参数
```$xslt
body:{
	dept_num:'1',  //专业代码
    
}
```
返回
```
success:{
    'code'  : 0,
    'msg'   : '删除成功',
    'data'  : null
}
error:{
          'code'  : 1003,1004...,
          'msg'   : '删除失败原因',
          'data'  : null
      }
```
###修改专业
```$xslt
GET /basic/updateDept
```
参数
```$xslt
body:{
	dept_num:'1',  //专业代码
    dept_name:'1'  //专业名称
}
```
返回
```
success:{
    'code'  : 0,
    'msg'   : '修改成功',
    'data'  : null
}
error:{
          'code'  : 1003,1004...,
          'msg'   : '修改失败原因',
          'data'  : null
      }
```
###查询专业
```$xslt
GET /basic/findDept
```
参数
```$xslt
body:{
	dept_num:'1',  //专业代码
    dept_name:'1'  //专业名称
}
```
返回
```
success:{
    'code'  : 0,
    'msg'   : 'success',
    'data'  : "departmentExample": {"dept_num": '1001',"dept_name": "软件工程"}
}
error:{
          'code'  : 1003,1004...,
          'msg'   : '删除失败原因',
          'data'  : null
      }
```
###添加学院
```$xslt
GET /basic/addFaculty
```
参数
```$xslt
body:{
	fac_num:'1',  //学院代码
    fac_name:'1'  //学院名称
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
          'msg'   : '添加失败',
          'data'  : null
      }
```
###删除学院
```$xslt
GET /basic/delFaculty
```
参数
```$xslt
body:{
	fac_num:'1',  //学院代码
   
}
```
返回
```
success:{
    'code'  : 0,
    'msg'   : '删除成功',
    'data'  : null
}
error:{
          'code'  : 1003,1004...,
          'msg'   : '删除失败原因',
          'data'  : null
      }
```

###修改学院
```$xslt
GET /basic/updateFaculty
```
参数
```$xslt
body:{
	fac_num:'1',  //学院代码
    fac_name:'1'   //学院名字
}
```
返回
```
success:{
    'code'  : 0,
    'msg'   : '修改成功',
    'data'  : null
}
error:{
          'code'  : 1003,1004...,
          'msg'   : '修改失败原因',
          'data'  : null
      }
```
###查询学院
```$xslt
GET /basic/findFaculty
```
参数
```$xslt
body:{
	fac_num:'1',  //学院代码
    fac_name:'1'   //学院名字
}
```
返回
```
success:{
    'code'  : 0,
    'msg'   : 'success',
    'data'  : "course": {"fac_num": '10001',"fac_name": "计算机与信息工程"}
}
error:{
          'code'  : 1003,1004...,
          'msg'   : '查询失败原因',
          'data'  : null
      }
```

###添加班级
```$xslt
GET /basic/addGradey
```
参数
```$xslt
body:{
	g_year:'1',  //年
    g_class:'1'   //班
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
          'msg'   : '添加失败原因',
          'data'  : null
      }
```
###删除班级
```$xslt
GET /basic/delGradey
```
参数
```$xslt
body:{
	g_id:1,  //年级id
  
}
```
返回
```
success:{
    'code'  : 0,
    'msg'   : '删除成功',
    'data'  : null
	
}
error:{
          'code'  : 1003,1004...,
          'msg'   : '删除失败原因',
          'data'  : null
      }
```
###修改班级
```$xslt
GET /basic/updateGradey
```
参数
```$xslt
body:{
	g_id : 1;    //年级id
	g_year:'1',  //年
    g_class:'1'   //班
}
```
返回
```
success:{
    'code'  : 0,
    'msg'   : '修改成功',
    'data'  : null
	
}
error:{
          'code'  : 1003,1004...,
          'msg'   : '修改失败原因',
          'data'  : null
      }
```