package com.ladybird.hkd.service.impl;

import com.ladybird.hkd.mapper.StudentManageMapper;
import com.ladybird.hkd.model.example.Grade;
import com.ladybird.hkd.model.json.ResultJson;
import com.ladybird.hkd.model.pojo.Student;
import com.ladybird.hkd.service.StudentManageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("studentManageService")
public class StudentManageServiceImpl implements StudentManageService {

    @Autowired
    private StudentManageMapper studentManageMapper;
    public ResultJson selectAllGrade(int gradelow) throws Exception {
        List<Integer> grades = studentManageMapper.selectAllGrade(gradelow);
        if(grades == null || grades.isEmpty()){
            return ResultJson.Forbidden("年级数据为空");
        }
        return ResultJson.Success(grades);
    }


    public ResultJson selectStudent(Student student) throws Exception {
        if(!StringUtils.isNotBlank(student.getStu_faculty())||!StringUtils.isNotBlank(student.getDept())||!StringUtils.isNotBlank(student.getGrade())){
            return ResultJson.ParameterError();
        }
        List<Student> students = studentManageMapper.selectStudent(student);
        return ResultJson.Success(students);
    }


    public ResultJson addStudent(Student student) throws Exception {
        if(!StringUtils.isNotBlank(student.getStu_num())||!StringUtils.isNotBlank(student.getStu_ID())||!StringUtils.isNotBlank(student.getStu_name())||!StringUtils.isNotBlank(student.getStu_faculty())||!StringUtils.isNotBlank(student.getDept())||!StringUtils.isNotBlank(student.getGrade())||!StringUtils.isNotBlank(student.getStu_pwd())){
            return ResultJson.ParameterError();
        }
        int resultCount = studentManageMapper.addStudent(student);
        if(resultCount > 0){
            return ResultJson.Success("添加成功");
        }
        return ResultJson.Forbidden("添加失败");

    }

    @Override
    public ResultJson deleteStudent(String stu_num) throws Exception {
        if (!StringUtils.isNotBlank(stu_num)){
            return ResultJson.ParameterError();
        }
        int resultCount = studentManageMapper.deleteStudent(stu_num);
        if(resultCount > 0){
            return ResultJson.Success("删除成功");
        }
        return ResultJson.Forbidden("删除失败");
    }

    @Override
    public ResultJson updateStudent(Student student) throws Exception {
        if(!StringUtils.isNotBlank(student.getStu_num())||!StringUtils.isNotBlank(student.getStu_ID())||!StringUtils.isNotBlank(student.getStu_name())||!StringUtils.isNotBlank(student.getStu_faculty())||!StringUtils.isNotBlank(student.getDept())||!StringUtils.isNotBlank(student.getGrade())||!StringUtils.isNotBlank(student.getStu_pwd())){
            return ResultJson.ParameterError();
        }
        int resultCount = studentManageMapper.updateStudent(student);
        if (resultCount > 0){
            return ResultJson.Success("修改成功");
        }
        return ResultJson.Forbidden("修改失败");
    }

    @Override
    public ResultJson findStudent(Student student) throws Exception {
        if(!StringUtils.isNotBlank(student.getStu_num())||!StringUtils.isNotBlank(student.getStu_name())){
            return ResultJson.ParameterError();
        }
        Student student1 = studentManageMapper.findStudent(student);
        if(student1 == null){
            return ResultJson.Forbidden("查询失败");
        }
        return ResultJson.Success(student1);
    }
}
