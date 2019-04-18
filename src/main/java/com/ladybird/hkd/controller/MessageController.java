package com.ladybird.hkd.controller;



import com.ladybird.hkd.annotation.CheckGroup;
import com.ladybird.hkd.annotation.CheckToken;
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
import com.ladybird.hkd.util.UrlConf;
import com.mysql.cj.jdbc.exceptions.PacketTooBigException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    //导入班级信息
    @CheckToken
    @CheckGroup
    @ResponseBody
    @RequestMapping(value = "/uploadGrade")
    public Object uploadGrade(HttpServletRequest request)throws Exception {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(4 * 1024 * 1024);
        List<FileItem> fileItems = new ArrayList<>();
        try {
            fileItems = upload.parseRequest(request);
        } catch (FileUploadException fe) {
            fe.printStackTrace();
        }
        File file = new File("");
        MultipartFile multipartFile = null;
        for (FileItem item : fileItems) {
            try {
                File fullFile = new File(item.getName());
                file = new File(UrlConf.LOCAL_UPLOAD_PATH, fullFile.getName());
                item.write(file);
            } catch (NullPointerException npe) {
                throw new PacketTooBigException("<上传班级信息>：请选择上传文件！");
            }
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                    ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
        } catch (FileNotFoundException fe) {
            throw new PacketTooBigException("<上传班级信息>：请选择上传文件！");
        }
        return ResultJson.Success(messageService.addGrades(multipartFile));
    }

    //通过专业查找未授课班级
    @CheckToken
    @CheckGroup
    @ResponseBody
    @RequestMapping(value = "/gradesNotTeach")
    public Object gradesNotTeach(String course, String teacher, String dept,String year) throws Exception{
        return ResultJson.Success(messageService.selGradesNotTeach(course, teacher, dept,year));
    }

}
