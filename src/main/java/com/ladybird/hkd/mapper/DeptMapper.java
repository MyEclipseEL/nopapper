package com.ladybird.hkd.mapper;

import com.ladybird.hkd.model.example.DepartmentExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DeptMapper {
    /*

    int delDept(@Param("dept_num") String dept_num);





    DepartmentExample findDeptByName(String dept_name);


    List<DepartmentExample> selByTeacher(@Param("t_num") String t_num) throws Exception;
    DepartmentExample findDeptByNum(String dept_num);*/
    int addDept(DepartmentExample departmentExample) throws Exception;
    List<DepartmentExample> selectAllDept(String faculty) throws Exception;

    int updateDept(DepartmentExample departmentExample) throws Exception;

    DepartmentExample findDept(DepartmentExample departmentExample) throws Exception;


    int selectDeptByPrimary(String dept_num) throws Exception;
    List<DepartmentExample> selByTeacher(@Param("t_num") String t_num) throws Exception;

}
