#restfulAPI接口文档
```$xslt
base url: http://nopaper.eiber.cn/paperless
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

###配置试卷题型分数


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
        "course"      :1,  
     	"single_count":2,
     	"single_score":5,
     	"multiple_count":3,
     	"multiple_score":15,
     	"checking_count":3,
     	"checking_score":15
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

###添加考试 
```$xslt
POST /exam/addExam
```
参数
```$xslt
{
	"course":1,
	"grades":[1,2],
	"dept"  :"10001",
	"pre_time":1553002861000,
	"duration":120
}
```
返回
```$xslt
{
    "message": "success",
    "code": 0,
    "data": {
        "exam_id": "2019031909410111",
        "course": {
            "c_id": 1,
            "c_name": "软件工程"
        },
        "grade": {
            "g_id": 1,
            "g_year": 2015,
            "g_class": 1
        },
        "pre_time": "2019-03-19 21:41:01",
        "begin_time": null,
        "duration": 120,
        "state": 0
    }
}
```









