package com.ladybird.hkd.service.impl;

import com.ladybird.hkd.exception.ExcelImportException;
import com.ladybird.hkd.mapper.DeptMapper;
import com.ladybird.hkd.mapper.FacultyMapper;
import com.ladybird.hkd.mapper.TeacherMapper;
import com.ladybird.hkd.model.example.TeacherExample;
import com.ladybird.hkd.model.json.ResultJson;
import com.ladybird.hkd.model.pojo.Department;
import com.ladybird.hkd.model.pojo.Faculty;
import com.ladybird.hkd.model.pojo.Teach;
import com.ladybird.hkd.model.pojo.Teacher;
import com.ladybird.hkd.service.TeacherManageService;
import com.ladybird.hkd.util.excel.ReadTeacherExcel;
import com.ladybird.hkd.util.excel.ReadTeachesExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherManageServiceImpl implements TeacherManageService {

    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private FacultyMapper facultyMapper;
    @Override
    public ResultJson selectTeacher(Teacher teacher) {
        return null;
    }

    @Override
    public List<TeacherExample> addTeachers(MultipartFile multipartFile) throws Exception {
        ReadTeacherExcel readTeacherExcel = new ReadTeacherExcel();
        List<TeacherExample> result = readTeacherExcel.getExcelInfo(multipartFile);
        List<Teacher> teachers = new ArrayList<>();
        for (TeacherExample t : result) {
            Teacher teacher = new Teacher();
            teacher.setT_num(t.getT_num());
            teacher.setT_name(t.getT_name());
            Faculty faculty = facultyMapper.findFaculty(
                    new Faculty(null, t.getT_faculty().getFac_name(), null));
            teacher.setT_faculty(faculty.getFac_num());
            teachers.add(teacher);
            t.setT_faculty(faculty);
        }
        Integer count = teacherMapper.checkInTeachers(teachers);
        if (count < result.size())
            throw new ExcelImportException("<导入教师信息>：应导入" + result.size() + "条，实际导入" + count + "条");
        return result;
    }
}
