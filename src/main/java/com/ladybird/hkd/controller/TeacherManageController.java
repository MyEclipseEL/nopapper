package com.ladybird.hkd.controller;

import com.ladybird.hkd.model.json.ResultJson;
import com.ladybird.hkd.model.pojo.Teacher;
import com.ladybird.hkd.service.TeacherManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/teacherManage")
public class TeacherManageController extends BaseController {

    @Autowired
    private TeacherManageService teacherManageService;
    @RequestMapping("/selectTeacher")
    @ResponseBody
    public ResultJson selectTeacher(Teacher teacher){
        return teacherManageService.selectTeacher(teacher);
    }

}
