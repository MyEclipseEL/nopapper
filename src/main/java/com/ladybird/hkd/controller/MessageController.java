package com.ladybird.hkd.controller;


import com.ladybird.hkd.model.json.ResultJson;
import com.ladybird.hkd.model.pojo.Course;
import com.ladybird.hkd.model.pojo.Department;
import com.ladybird.hkd.model.pojo.Faculty;
import com.ladybird.hkd.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 */
@Controller
@RequestMapping("/message")
public class MessageController extends BaseController {

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/selectAllFaculty",method = RequestMethod.GET)
    @ResponseBody
    public ResultJson selectAllFaculty() throws Exception{
        return messageService.selectAllFaculty();
    }
    @RequestMapping(value = "/addFaculty",method = RequestMethod.GET)
    @ResponseBody
    public ResultJson addFaculty(Faculty faculty) throws Exception {

       return  messageService.addFaculty(faculty);

    }
    @RequestMapping(value = "/updateFaculty",method = RequestMethod.GET)
    @ResponseBody
    public ResultJson updateFaculty(Faculty faculty) throws Exception{
        return messageService.updateFaculty(faculty);
    }
    @RequestMapping(value = "/findFaculty",method = RequestMethod.GET)
    @ResponseBody
    public ResultJson findFaculty(Faculty faculty) throws Exception{
        return messageService.findFaculty(faculty);
    }

    @RequestMapping(value = "/selectAllDept",method = RequestMethod.GET)
    @ResponseBody
    public ResultJson selectAllDept(String faculty) throws Exception{
        return messageService.selectAllDept(faculty);
    }
    @RequestMapping(value = "/addDept",method = RequestMethod.GET)
    @ResponseBody
    public ResultJson addDept(Department department) throws Exception{
        return messageService.addDept(department);
    }
    @RequestMapping(value = "/updateDept",method = RequestMethod.GET)
    @ResponseBody
    public ResultJson updateDept(Department department) throws Exception{
        return messageService.updateDept(department);
    }
    @RequestMapping(value = "/findDept",method = RequestMethod.GET)
    @ResponseBody
    public ResultJson findDept(Department department) throws Exception{
        return messageService.findDept(department);
    }

    @RequestMapping(value = "/selectAllCourse",method = RequestMethod.GET)
    @ResponseBody
    public ResultJson selectAllCourse() throws Exception{
        return messageService.selectAllCourse();
    }
    @RequestMapping(value = "/addCourse",method = RequestMethod.GET)
    @ResponseBody
    public ResultJson addCourse(Course course) throws Exception{
        return messageService.addCourse(course);
    }
    @RequestMapping(value = "/updateCourse",method = RequestMethod.GET)
    @ResponseBody
    public ResultJson updateCourse(Course course) throws Exception{
        return messageService.updateCourse(course);
    }
    @RequestMapping(value = "/findCourse",method = RequestMethod.GET)
    @ResponseBody
    public ResultJson findCourse(Course course) throws Exception{
        return messageService.findCourse(course);
    }

}
