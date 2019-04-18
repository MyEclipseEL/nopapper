package com.ladybird.hkd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ladybird.hkd.mapper.*;
import com.ladybird.hkd.model.example.StudentExample;
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
    private StudentMapper studentMapper;
    @Autowired
    private FacultyMapper facultyMapper;
    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private GradeMapper gradeMapper;
    public ResultJson selectAllGrade(int gradelow) throws Exception {
        List<Integer> grades = studentManageMapper.selectAllGrade(gradelow);
        if(grades == null || grades.isEmpty()){
            return ResultJson.Forbidden("年级数据为空");
        }
        return ResultJson.Success(grades);
    }


    public ResultJson selectStudent(Student student, int pageNum, int pageSize) throws Exception {
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
       //startPage--start
        //填充自己的sql查询逻辑
        //pageHelper--收尾
        PageHelper.startPage(pageNum,pageSize);
        List<StudentVo> studentVos = studentManageMapper.selectStudent(student);
        if(studentVos==null||studentVos.isEmpty()){
            return ResultJson.Forbidden("查询失败");
        }
        PageInfo pageResult = new PageInfo(studentVos);
        return ResultJson.Success(pageResult);
    }


    public ResultJson addStudent(Student student) throws Exception {
        if(!StringUtils.isNotBlank(student.getStu_num())
                ||!StringUtils.isNotBlank(student.getStu_ID())
                ||!StringUtils.isNotBlank(student.getStu_name())
                ||!StringUtils.isNotBlank(student.getStu_faculty())
                ||!StringUtils.isNotBlank(student.getDept())
                ||!StringUtils.isNotBlank(student.getGrade())){
            return ResultJson.ParameterError();
        }

        Student s = studentMapper.findByNum(student.getStu_num());


        if(s != null){
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
        if(!StringUtils.isNotBlank(student.getStu_num())||!StringUtils.isNotBlank(student.getStu_ID())||!StringUtils.isNotBlank(student.getStu_name())||!StringUtils.isNotBlank(student.getStu_faculty())||!StringUtils.isNotBlank(student.getDept())||!StringUtils.isNotBlank(student.getGrade())){
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

    public List<StudentExample> uploadStudent(MultipartFile multipartFile)throws Exception{
        //创建处理Excel的类
        PeopleExcel peopleExcel = new PeopleExcel();
        List<StudentExample> students = peopleExcel.getExcelInfo(multipartFile);
        for (StudentExample student : students) {
            Student s = new Student();
            s.setStu_num(student.getStu_num());
            s.setStu_name(student.getStu_name());
            s.setStu_ID(student.getStu_ID());
            Faculty faculty = new Faculty();
            faculty.setFac_name(student.getFaculty().getFac_name());
            Faculty faculty1 = facultyMapper.findFaculty(faculty);
            s.setStu_faculty(faculty1.getFac_num());
            Department department = new Department();
            department.setDept_name(student.getDepartment().getDept_name());
            Department dept = deptMapper.findDept(department);
            s.setDept(dept.getDept_num());
            Grade grade = new Grade();
            grade.setDept(dept.getDept_num());
            grade.setG_year(student.getGrade().getG_year());
            grade.setG_class(student.getGrade().getG_class());
            Grade grade1 = gradeMapper.findGrade(grade);
            s.setGrade(grade1.getG_id());
            studentManageMapper.addStudent(s);
        }
        return students;
    }
}
