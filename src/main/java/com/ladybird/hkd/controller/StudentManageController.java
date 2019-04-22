package com.ladybird.hkd.controller;


import com.ladybird.hkd.annotation.CheckGroup;
import com.ladybird.hkd.annotation.CheckToken;
import com.ladybird.hkd.exception.ParamException;
import com.ladybird.hkd.model.json.ResultJson;
import com.ladybird.hkd.model.pojo.Faculty;
import com.ladybird.hkd.model.pojo.Student;
import com.ladybird.hkd.service.MessageService;
import com.ladybird.hkd.service.StudentManageService;
import com.ladybird.hkd.util.UrlConf;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.http.entity.ContentType;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/studentManage")
public class StudentManageController extends BaseController{
    @Autowired
    private StudentManageService studentManageService;
    @Autowired
    private MessageService messageService;
   @RequestMapping(value = "/backFaculty", method = RequestMethod.GET)
   @ResponseBody
   public ResultJson backFaculty() throws Exception{
       return messageService.selectAllFaculty();
   }
   @RequestMapping(value = "/backDepartment",method = RequestMethod.GET)
   @ResponseBody
   public ResultJson backDepartment(String fac_num) throws Exception{
       return messageService.findDeptByFac(fac_num);
   }
   @RequestMapping(value = "/backGrade",method = RequestMethod.GET)
   @ResponseBody
   public ResultJson backGrade(@RequestParam(value ="dept_num") String dept) throws Exception{
        return studentManageService.backGrade(dept);
   }
    @RequestMapping(value = "/addStudent",method = RequestMethod.GET)
    @ResponseBody
    public ResultJson addStudent(Student student) throws Exception{
        return studentManageService.addStudent(student);
    }
    @RequestMapping(value = "/deleteStudent", method = RequestMethod.GET)
    @ResponseBody
    public  ResultJson deleteStudent(String stu_num) throws Exception{
        return studentManageService.deleteStudent(stu_num);
    }
    @RequestMapping(value = "/updateStudent", method = RequestMethod.GET)
    @ResponseBody
    public ResultJson updateStudent(Student student) throws Exception{
        return studentManageService.updateStudent(student);
    }

    @RequestMapping(value = "/selectStudent", method = RequestMethod.GET)
    @ResponseBody
    public ResultJson selectStudent(Student student, @RequestParam(value = "pageNum",defaultValue = "1")int pageNum, @RequestParam(value = "pageSize",defaultValue = "10") int pageSize) throws Exception{
        return studentManageService.selectStudent(student,pageNum,pageSize);
    }
    @CheckGroup
    @CheckToken
    @RequestMapping(value="/uploadStudent",method=RequestMethod.POST)
    @ResponseBody
    public Object uploadStudent(HttpServletRequest request) throws Exception {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(4*1024*1024);

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
//                file = new File(UrlConf.LOCAL_UPLOAD_PATH, fullFile.getName());
//            file = new File(UrlConf.SERVER_UPLOAD_PATH);
                file = new File(UrlConf.PEI_UPLOAD_PATH, fullFile.getName());
                item.write(file);
            } catch (NullPointerException npe) {
                throw new ParamException("请选择上传到文件！");
            }
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                    ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
        } catch (FileNotFoundException fe) {
            throw new ParamException("请选择上传的文件！");
        }
        //调用service
        FileUtils.deleteQuietly(file);
        return ResultJson.Success(studentManageService.uploadStudent(multipartFile));
    }

}
