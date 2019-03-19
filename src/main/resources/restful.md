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


##StudentController 学生管理类
###student login
```$xslt
POST /student/login
```
参数
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
###请求所有题目 --一个科目 
```$xslt
GET /item/items
```
参数
```$xslt
header:{"authorization":"aa access_token"}
{
    "course": 1 课程号
}
```
返回
```$xslt
success{
    "code"  : 0,
    "msg"   : "成功",
    "data"  :     {
                      "message": "success",
                      "code": 0,
                      "data": [
                          {
                              "itemType": {
                                  "type_id": "A",
                                  "type_name": "单选题",
                                  "type_score": 10
                              },
                              "items": [
                                  {
                                      "item_id": "4",
                                      "item_title": null,
                                      "item_desc": "题目描述",
                                      "item_valid": [
                                          "正确答案1"
                                      ],
                                      "answer": [
                                          "错误答案1",
                                          "正确答案1",
                                          "错误答案2",
                                          "错误答案3"
                                      ],
                                      "item_type": "单选题",
                                      "course": "1"
                                  },
                           }    ]   
                      ]
                  }
}

```
###请求试卷题目

###配置试卷题型分数
```$xslt
POST /item/editTypeScore
```
参数
```$xslt
header:{"authorization":"aa access_token"}
{
    "exam": 1 考试号
}
```

###查找所有的题型
```$xslt
GET /item/checkOutTypes
```
参数
```$xslt
null
```
返回
```$xslt
{
    "message": "success",
    "code": 0,
    "data": [
        {
            "type_id": "A",
            "type_name": "单选题",
            "type_score": 15
        },
        {
            "type_id": "B",
            "type_name": "多选题",
            "type_score": 15
        },
        {
            "type_id": "C",
            "type_name": "判断题",
            "type_score": 15
        }
    ]
}
```


##ExamController 考试管理
###配置试卷题型分布
```$xslt
POST /exam/editPaper
```
参数
```$xslt
body:{
     	"single_choice":3,
     	"multiple_choice":3,
     	"checking":3
     }
```
返回
```$xslt
{
    "message": "success",
    "code": 0,
    "data": {
        "id": null,
        "single_choice": 3,
        "multiple_choice": 3,
        "checking": 3
    }
}
```

###请求当前试卷题型分布
```$xslt
GET /exam/checkOutPaper
```
参数
```$xslt
无
```
返回
```$xslt
{
    "message": "success",
    "code": 0,
    "data": {
        "id": 1,
        "single_choice": 3,
        "multiple_choice": 3,
        "checking": 3
    }
}
```

###获取考试信息
```$xslt
GET /exam/exams
```
参数
```$xslt
null
```
返回
```$xslt
[
    {
        "exam_id": "1",
        "course": {
            "c_id": 1,
            "c_name": "软件工程"
        },
        "grade": {
            "g_id": 1,
            "g_year": 2015,     //级
            "g_class": 1        //班
        },
        "pre_time": 1552541400,     //预计开始时间
        "begin_time": 1552564208,   //实际开始时间
        "duration": 120,            //考试时长
        "state": 1                  //考试状态
    }
]
```

###老师开始考试
```$xslt
GET /exam/beginExam
```
参数
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
参数
```$xslt
{
    "course"    : 1,
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



