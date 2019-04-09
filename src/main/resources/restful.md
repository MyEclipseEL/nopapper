#restfulAPI接口文档
```$xslt
base url: http://nopaper.eiber.cn/paperless
```
**tap：除登陆外所有的请求请带上token ，格式见前几个请求**

##TeacherController 教师管理
###teacher login
```
POST /teacher/login   //x-www-form-urlencoded  
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

##ItemController 题目管理类
###请求所有题目  

```$xslt
GET /item/items     
```
参数
```$xslt
header:{"authorization":"aa access_token"}
{
    "course"    : 1 课程号  //不传则查找所有科目题型
    "item_type" :"A" 题型   //不传则查询所有题型
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
                                      "item_choice": [
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
###修改题目
```
POST /item/changeItem       //x-www-form-urlencoded
```
参数
```
{
    "item_id": "4",             //题目id
    "item_title": null,         //(没有填写则不需传)
    "item_desc": "题目描述",    //题目
    "item_valid": [             //正确答案
        正确答案1"
    ],
    "item_choice": [            //选项
        "错误答案1",
        "正确答案1",
        "错误答案2",
        "错误答案3"
    ],
    "item_type": "A",               //题型
    "course": "1"                   //课程
    "tip"   :                   //备注 （没填可不传）
}
```
返回
```
success:{
    'code'  : 0,
    'msg'   : '成功',
    'data'  : [
        {
        "item_id": "4",             //题目id
        "item_title": null,         
        "item_desc": "题目描述",    //题目
        "item_valid": [             //正确答案
            正确答案1"
        ],
        "item_choice": [            //选项
            "错误答案1",
            "正确答案1",
            "错误答案2",
            "错误答案3"
        ],
        "item_type": "A",               //题型
        "course": "1"                   //课程
        }
    ]
}
```

###添加题目
```
POST /item/addItem      //x-www-form-urlencoded
```
参数
```
{
    "item_title": null,         //（可不填）
    "item_desc": "题目描述",    //题目
    "item_valid": [             //正确答案
        正确答案1"
    ],
    "item_choice": [            //选项
        "错误答案1",
        "正确答案1",
        "错误答案2",
        "错误答案3"
    ],
     "item_type": "A",               //题型
    "course": "1"                   //课程
    "tip"   :                   //备注(可不填写)
}
```
返回
```
{
    "item_id"   : 22
    "item_title": null,         
    "item_desc": "题目描述",    //题目
    "item_valid": [             //正确答案
        正确答案1"
    ],
    "item_choice": [            //选项
        "错误答案1",
        "正确答案1",
        "错误答案2",
        "错误答案3"
    ],
     "item_type": "A",               //题型
    "course": "1"                   //课程
    "tip"   :                   //备注
}
```

###导入题目
```
POST  /item/upload          form-data
```
参数
```
{
    "course"    :   ""
    "item_type" :   "A"
    "file"      :   "****.xls"
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
        },
        {
            "type_id": "B",
            "type_name": "多选题",
        },
        {
            "type_id": "C",
            "type_name": "判断题",
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
     	"duration"      :120 时长 （分钟）
     }
```
返回
```$xslt
{
    "message": "success",
    "code": 0,
    "data": {
        "id": null,
        "course"      :1,  
        "single_count":2,
        "single_score":5,
        "multiple_count":3,
        "multiple_score":15,
        "checking_count":3,
        "checking_score":15
        "duration"      :120 时长 （分钟）
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
        "course"      :1,  
        "single_count":2,
        "single_score":5,
        "multiple_count":3,
        "multiple_score":15,
        "checking_count":3,
        "checking_score":15
        "duration"      :120 时长 （分钟）
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
        "finish_time": ,            //结束时间
        "begin_time": 1552564208,   //实际开始时间
        "duration": 120,            //考试时长
        "state": 1                  //考试状态
    }
]
```










