package com.ladybird.hkd.service.impl;

import com.ladybird.hkd.mapper.DeptMapper;
import com.ladybird.hkd.mapper.FacultyMapper;
import com.ladybird.hkd.mapper.GradeMapper;
import com.ladybird.hkd.mapper.StudentManageMapper;
import com.ladybird.hkd.model.json.ResultJson;
import com.ladybird.hkd.model.pojo.Department;
import com.ladybird.hkd.model.pojo.Faculty;
import com.ladybird.hkd.model.pojo.Grade;
import com.ladybird.hkd.model.pojo.Student;
import com.ladybird.hkd.model.vo.StudentVo;
import com.ladybird.hkd.service.StudentManageService;
import com.ladybird.hkd.util.excel.PeopleExcel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Service("studentManageService")
public class StudentManageServiceImpl implements StudentManageService {

    @Autowired
    private StudentManageMapper studentManageMapper;
    @Autowired
    private FacultyMapper facultyMapper;
    @Autowired
    private DeptMapper deptMapper;
    public ResultJson selectAllGrade(int gradelow) throws Exception {
        List<Integer> grades = studentManageMapper.selectAllGrade(gradelow);
        if(grades == null || grades.isEmpty()){
            return ResultJson.Forbidden("年级数据为空");
        }
        return ResultJson.Success(grades);
    }


    public ResultJson selectStudent(Student student) throws Exception {
        if(StringUtils.isNotBlank(student.getStu_faculty())){
            Faculty faculty = new Faculty();
            faculty.setFac_name(student.getStu_faculty());
            Faculty faculty1 = facultyMapper.findFaculty(faculty);
            student.setStu_faculty(faculty1.getFac_num());
        }
        if(StringUtils.isNotBlank(student.getDept())){
            Department department = new Department();
            department.setDept_name(student.getDept());
            Department dept = deptMapper.findDept(department);
            student.setDept(dept.getDept_num());
        }
        List<StudentVo> studentVos = studentManageMapper.selectStudent(student);
        if(studentVos==null||studentVos.isEmpty()){
            return ResultJson.Forbidden("查询失败");
        }

        return ResultJson.Success(studentVos);
    }


    public ResultJson addStudent(Student student) throws Exception {
        if(!StringUtils.isNotBlank(student.getStu_num())||!StringUtils.isNotBlank(student.getStu_ID())||!StringUtils.isNotBlank(student.getStu_name())||!StringUtils.isNotBlank(student.getStu_faculty())||!StringUtils.isNotBlank(student.getDept())||!StringUtils.isNotBlank(student.getGrade())||!StringUtils.isNotBlank(student.getStu_pwd())){
            return ResultJson.ParameterError();
        }
        Student s = new Student();
        s.setStu_num(student.getStu_num());
        List<StudentVo> studentVos = studentManageMapper.selectStudent(s);
        if(studentVos != null || studentVos.isEmpty()){
            return this.updateStudent(student);
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

    @Override
    public ResultJson backGrade(String dept) throws Exception {
        List<Grade> gradeByDept = gradeMapper.findGradeByDept(dept);
        if (gradeByDept == null || gradeByDept.isEmpty()){
            return ResultJson.Forbidden("查询失败");
        }
        return ResultJson.Success(gradeByDept);
    }


    public List<Student> uploadStudent(MultipartFile multipartFile)throws Exception{
        String fileName = multipartFile.getOriginalFilename();    //获取文件名
        PeopleExcel peopleExcel = new PeopleExcel();
       List<Student> students = peopleExcel.batchInputStudent(fileName, multipartFile);
        for (Student student : students) {
            studentManageMapper.addStudent(student);
        }
        return students;
    }
}
