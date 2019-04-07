#restfulAPI接口文档
```$xslt
base url: http://nopaper.eiber.cn/nopapper
```
**tap：除登陆外所有的请求请带上token ，格式见前几个请求**
##TeacherController 教师管理
###teacher login
```
POST /teacher/login
```
参数
```$xslt
body:{
    t_num:'1', //教师工号
    t_pwd:'1'  //密码
}
```
返回
```
success:{
    'code'  : 0,
    'msg'   : '成功',
    'data'  : {'access_token':'aaaa','refresh_token:'aaa'}
}
error:{
          'code'  : 1003,1004...,
          'msg'   : '失败原因',
          'data'  : null
      }
```

###老师获取教授课程
```
GET  /teacher/examCourses
POST /teacher/examCourses
```
参数
```
null
```
返回
```
{
    "message": "success",
    "code": 0,
    "data": [
        {
            "c_id": "1",
            "c_name": "软件工程",
            "tip": null
        }
    ]
}
```
###老师请求未参加该门考试的班级
```
GET  /teacher/examGrades
POST /teacher/examGrades
```
参数
```text
{
"course":"1"

}
```
参数 url
```text
{
 "message": "success",
    "code": 0,
    "data": [
        {
            "g_id": "3",
            "g_year": 2016,
            "g_class": 1,
            "dept": {
                "dept_num": "10001",
                "dept_name": "软件工程",
                "faculty": {
                    "fac_num": "10001",
                    "fac_name": "计算机与信息工程",
                    "tip": null
                },
                "tip": null
            }
        }
    ]
 }
```
###教师选择班级开始考试
```text
GET  /teacher/begin
POST /teacher/begin
```
参数 x-www-form-urlencoded
```text
{
"course"    : 1
"duration"  : 120 (分钟数)
"grades"    : 3,4
}
```
返回
```text
{
    "message": "success",
    "code": 0,
    "data": {
        "exam_id": "2019033111004211",
        "course": {
            "c_id": "1",
            "c_name": "软件工程",
            "tip": null
        },
        "grade": "3,4",
        "dept": null,
        "begin_time": 1554001243000,(毫秒数，除1000使用)
        "duration": 120,
        "state": 1
    }
}
```

##StudentController 学生管理类
###student login
```$xslt
POST /student/login
```
参数 x-www-form-urlencoded
```$xslt
body:{
    'stu_num'   : '1', //学号
    'stu_pwd'   : '1', //密码
    'stu_ID'    : '1' //身份证
}
```
返回
```$xslt
success:{
    "access_token": "a86d462f028b44179fcb842f9d3fd10e.d86c828583c5c6160e8acfee88ba1590",
    "refresh_token": "f907308faa044c87a71613ba007a2706.e57941dbe1246fe97a4ffc16e85b5df9"
}
error:{
          'code'  : 1003,1004...,
          'msg'   : '失败原因',
          'data'  : null
      }
```

##ItemController 题目管理类
###请求试卷题目


##ExamController 考试管理
###老师开始考试
```$xslt
GET /exam/beginExam
```
参数 url
```$xslt
{
    "exam"  : 1     考试id
}
```
返回
```$xslt
{
    "code"  : "0",
    "msg"   : "成功",
    "data"  : null
}
```

###提交考试分数
```$xslt
GET /exam/commitPaper
```
参数 url
```$xslt
{
    "exam"    : 201903190941011100012,
    "score"     : 99
}
```
返回
```$xslt
{
    "code"  : "0",
    "msg"   : "成功",
    "data"  : null
}
```

###学生请求考试安排
```$xslt
GET /exam/exam
```
参数
```$xslt
无
```
返回
```$xslt
{
    "exam_id": "1",
    "course": {
        "c_id": 1,
        "c_name": "软件工程"
    },
    "grade": {
        "g_id": 1,
        "g_year": 2015,
        "g_class": 1
    },
    "pre_time": 1552541400,     //预设时间
    "begin_time": 1552978538,   //开始时间
    "duration": 120,
    "state": 1
}
```