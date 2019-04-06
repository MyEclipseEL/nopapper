package com.ladybird.hkd.controller;


import com.ladybird.hkd.model.json.ResultJson;
import com.ladybird.hkd.model.pojo.Student;
import com.ladybird.hkd.service.StudentManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;

@Controller
@RequestMapping("/studentManage")
public class StudentManageController {

    @Autowired
    private StudentManageService studentManageService;
    @RequestMapping(value = "/selectAllGrade",method = RequestMethod.GET)
    @ResponseBody
    public ResultJson selectAllGrade()throws Exception{
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        int gradelow;
        if(month >= 9){
            gradelow = year-4;
            return studentManageService.selectAllGrade(gradelow);
        }else{
            gradelow = year-5;
            return studentManageService.selectAllGrade(gradelow);
        }

    }
    @RequestMapping(value = "/selectStudent",method = RequestMethod.GET)
    @ResponseBody
    public ResultJson selectStudent(Student student) throws Exception{
        return studentManageService.selectStudent(student);
    }
    @RequestMapping(value = "/addStudent",method = RequestMethod.GET)
    @ResponseBody
    public ResultJson addStudent(Student student) throws Exception{
        return studentManageService.addStudent(student);
    }
    @RequestMapping(value = "deleteStudent", method = RequestMethod.GET)
    @ResponseBody
    public  ResultJson deleteStudent(String stu_num) throws Exception{
        return studentManageService.deleteStudent(stu_num);
    }
    @RequestMapping(value = "updateStudent", method = RequestMethod.GET)
    @ResponseBody
    public ResultJson updateStudent(Student student) throws Exception{
        return studentManageService.updateStudent(student);
    }

    @RequestMapping(value = "findStudent", method = RequestMethod.GET)
    @ResponseBody
    public ResultJson findStudent(Student student) throws Exception{
        return studentManageService.findStudent(student);
    }



}
