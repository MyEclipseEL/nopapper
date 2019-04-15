package com.ladybird.hkd.mapper;

import com.ladybird.hkd.model.example.DepartmentExample;
import com.ladybird.hkd.model.pojo.Department;
import com.ladybird.hkd.model.pojo.Faculty;
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
    int addDept(Department department) throws Exception;
    List<Department> selectAllDept(Faculty faculty) throws Exception;

    int updateDept(Department department) throws Exception;

    Department findDept(Department department) throws Exception;


    int selectDeptByPrimary(String dept_num) throws Exception;

    List<DepartmentExample> selByTeacher(@Param("t_num") String t_num) throws Exception;



    List<Department> findDeptByFac(@Param("fac_num") String fac_num) throws Exception;
}
