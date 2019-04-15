##TeacherManageController 教师管理
###teacher查询
```
GET/teacherManage/selectTeacher 
```
参数
```$xslt

body:{
    t_num:'1', //教师工号
    t_name:'1'  //姓名
    faculty:'1' //学院
    null
}
```
返回
```
success:{
            "message": "success",
            "code": 0,
            "data": [
                {
                    "t_num": "1",
                    "t_name": "1",
                    "t_faculty": null,
                    "t_dept": {
                        "dept_num": "10001",
                        "dept_name": "软件工程",
                        "faculty": {
                            "fac_num": "10001",
                            "fac_name": "计算机与信息工程",
                            "tip": null
                        },
                        "tip": null
                    },
                    "t_office": null,
                    "t_pwd": null,
                    "group_id": null
                },
                {
                    "t_num": "1001",
                    "t_name": "admin",
                    "t_faculty": null,
                    "t_dept": {
                        "dept_num": "10001",
                        "dept_name": "软件工程",
                        "faculty": {
                            "fac_num": "10001",
                            "fac_name": "计算机与信息工程",
                            "tip": null
                        },
                        "tip": null
                    },
                    "t_office": null,
                    "t_pwd": null,
                    "group_id": null
                },
                
            ]
        }
error:{
          'code'  : 1003,1004...,
          'msg'   : '失败原因',
          'data'  : null
      }
```
##学院下拉框
```$xslt
GET /studentManage/backFaculty
```
参数
```$xslt
body:{
	null
}
```
success:{
    
        "message": "success",
        "code": 0,
        "data": [
            {
                "fac_num": "10001",
                "fac_name": "计算机与信息工程",
                "tip": null
            }
        ]
     
   	}
error:{
          'code'  : 1003,1004...,
          'msg'   : '失败原因',
          'data'  : null
      }

###专业下拉框
```$xslt
GET /studentManage/backDepartment
```
参数
```$xslt
body:{
	"fac_num": "10001",
}
```
success:{
            "message": "success",
            "code": 0,
            "data": [
                {
                    "dept_num": "10001",
                    "dept_name": "软件工程",
                    "faculty": null,
                    "tip": null
                },
                {
                    "dept_num": "10002",
                    "dept_name": "物联网",
                    "faculty": null,
                    "tip": null
                }
            ]
        }
error:{
          'code'  : 1003,1004...,
          'msg'   : '失败原因',
          'data'  : null
      }

###添加教师
``$xslt
GET /studentManage/addTeacher
```
参数
```$xslt
body:{
	"t_num":'1',  //教师工号
	"t_name":'aaa',   //老师姓名
	"t_faculty":'10001',   //学院
	"t_dept":'10001'   //专业
}
```
success:{
            "message": "添加成功",
            "code": 0,
            "data": null
        }
error:{
          'code'  : 1003,1004...,
          'msg'   : '失败原因',
          'data'  : null
      }

###更新教师
``$xslt
GET /studentManage/updateTeacher
```
参数
```$xslt
body:{
	"t_num":'1',  //教师工号
	"t_name":'aaa',   //老师姓名
	"t_faculty":'10001',   //学院
	"t_dept":'10001'   //专业
}
```
success:{
            "message": "更新成功",
            "code": 0,
            "data": null
        }
error:{
          'code'  : 1003,1004...,
          'msg'   : '失败原因',
          'data'  : null
      }

###删除教师
``$xslt
GET /studentManage/deleteTeacher
```
参数
```$xslt
body:{
	"t_num":'1',  //教师工号
}
```
success:{
            "message": "删除成功",
            "code": 0,
            "data": null
        }
error:{
          'code'  : 1003,1004...,
          'msg'   : '失败原因',
          'data'  : null
      }
