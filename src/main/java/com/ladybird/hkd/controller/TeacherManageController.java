package com.ladybird.hkd.controller;

import com.ladybird.hkd.annotation.CheckGroup;
import com.ladybird.hkd.annotation.CheckToken;
import com.ladybird.hkd.exception.ParamException;
import com.ladybird.hkd.model.example.TeacherExample;
import com.ladybird.hkd.model.json.ResultJson;
import com.ladybird.hkd.model.pojo.Teacher;
import com.ladybird.hkd.service.TeacherManageService;
import com.ladybird.hkd.util.UrlConf;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/teacherManage")
public class TeacherManageController extends BaseController {

    @Autowired
    private TeacherManageService teacherManageService;
    @RequestMapping("/selectTeacher")
    @ResponseBody
    public ResultJson selectTeacher(Teacher teacher){
        return teacherManageService.selectTeacher(teacher);
    }


    @CheckGroup
    @CheckToken
    @ResponseBody
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public Object importTeachers(HttpServletRequest request) throws Exception{
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(4*1024*1024);
        List<FileItem> fileItemList = new ArrayList<>();
        try {
            fileItemList = upload.parseRequest(request);
        } catch (FileUploadException fe) {
            fe.printStackTrace();
        }
        File file = new File("");
        MultipartFile multipartFile = null;
        for (FileItem item : fileItemList) {
            try {
                File fullFile = new File(item.getName());
                file = new File(UrlConf.LOCAL_UPLOAD_PATH,fullFile.getName());
//                file = new File(UrlConf.SERVER_UPLOAD_PATH);
                item.write(file);
            } catch (NullPointerException npe) {
                throw new ParamException("<导入教师信息>：请选择上传的文件");
            }
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                    ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
        } catch (FileNotFoundException fe) {
            throw new ParamException("<导入教师信息>：请选择上传的文件！");
        }
        List<TeacherExample> teacherExamples = teacherManageService.addTeachers(multipartFile);
        FileUtils.deleteQuietly(file);
        return ResultJson.Success(teacherExamples);
    }

}
