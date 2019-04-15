package com.ladybird.hkd.controller;



import com.ladybird.hkd.exception.BusinessException;
import com.ladybird.hkd.model.json.ResultJson;
import com.ladybird.hkd.model.json.TeacherJsonOut;
import com.ladybird.hkd.model.pojo.Course;
import com.ladybird.hkd.model.pojo.Department;
import com.ladybird.hkd.model.pojo.Faculty;
import com.ladybird.hkd.service.MessageService;
import com.ladybird.hkd.service.TeacherService;
import com.ladybird.hkd.util.ConstConfig;
import com.ladybird.hkd.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Controller
@RequestMapping("/message")
public class MessageController extends BaseController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private TeacherService teacherService;

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

    @RequestMapping(value = "/findDeptByFac",method = RequestMethod.GET)
    @ResponseBody
    public ResultJson findDeptByFac(String fac_num) throws Exception{
        return messageService.findDeptByFac(fac_num);
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

    //查询教师教授的课程
    @ResponseBody
    @RequestMapping(value = "/teaches")
    public Object teaches(NativeWebRequest request,String t_num) throws Exception{
        //获取登陆教师的信息
        String teacherJson = (String) request.getAttribute(ConstConfig.CURRENT_OBJECT, RequestAttributes.SCOPE_REQUEST);
        String adminJson = (String) request.getAttribute(ConstConfig.CURRENT_OBJECT, RequestAttributes.SCOPE_REQUEST);
        List<Course> courses = new ArrayList<>();
        if (teacherJson != null) {
            //转为对象
            TeacherJsonOut teacherJsonOut = JsonUtil.jsonToPojo(teacherJson, TeacherJsonOut.class);
            try {
                t_num = teacherJsonOut.getT_num();
            } catch (NullPointerException npe) {
                throw new BusinessException("token中没有用户信息！");
            }
            courses = teacherService.checkOutCourseByNum(t_num);
        }else {

        }

        return ResultJson.Success(courses);
    }

}
