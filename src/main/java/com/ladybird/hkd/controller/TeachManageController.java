package com.ladybird.hkd.controller;

import com.ladybird.hkd.model.json.ResultJson;
import com.ladybird.hkd.service.TeacherManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/teachManage")
public class TeachManageController extends BaseController{
    @Autowired
    private TeacherManageService teacherManageService;
    @RequestMapping(value = "/selectTeacher",method = RequestMethod.GET)
    @ResponseBody
    public ResultJson selectTeacher() throws Exception {
        return teacherManageService.selectTeacher(null,null, pageNum, pageSize);
    }
    @RequestMapping(value = "teaching",method = RequestMethod.GET)
    @ResponseBody
    public ResultJson teaching(String t_num) throws Exception{
        return teacherManageService.teaching(t_num);
    }

}
