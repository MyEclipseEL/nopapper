package com.ladybird.hkd.controller;

import com.ladybird.hkd.model.json.ResultJson;
import com.ladybird.hkd.model.pojo.Faculty;
import com.ladybird.hkd.model.pojo.Teacher;
import com.ladybird.hkd.service.MessageService;
import com.ladybird.hkd.service.TeacherManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/teacherManage")
public class TeacherManageController extends BaseController {

    @Autowired
    private TeacherManageService teacherManageService;
    @Autowired
    private MessageService messageService;
    @RequestMapping("/selectTeacher")
    @ResponseBody
    public ResultJson selectTeacher(Teacher teacher,String faculty) throws Exception{
        return teacherManageService.selectTeacher(teacher,faculty);
    }
    @RequestMapping(value = "/backFaculty", method = RequestMethod.GET)
    @ResponseBody
    public ResultJson backFaculty() throws Exception{
        return messageService.selectAllFaculty();
    }
    @RequestMapping(value = "/backDepartment",method = RequestMethod.GET)
    @ResponseBody
    public ResultJson backDepartment(Faculty faculty) throws Exception{
        return messageService.selectAllDept(faculty);
    }
    @RequestMapping(value = "/addTeacher",method = RequestMethod.GET)
    @ResponseBody
    public ResultJson addTeacher (Teacher teacher) throws Exception{
        return teacherManageService.addTeacher(teacher);
    }
    @RequestMapping(value = "/updateTeacher",method = RequestMethod.GET)
    @ResponseBody
    public ResultJson updateTeacher(Teacher teacher) throws Exception{
        return teacherManageService.updateTeacher(teacher);
    }
    @RequestMapping(value = "/deleteTeacher",method = RequestMethod.GET)
    @ResponseBody
    public ResultJson deleteTeacher(String t_num) throws Exception{
        return teacherManageService.deleteTeacher(t_num);
    }

}
