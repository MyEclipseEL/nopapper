package com.ladybird.hkd.mapper;

import com.ladybird.hkd.model.pojo.Department;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DeptMapper {
    /*

    int delDept(@Param("dept_num") String dept_num);





    Department findDeptByName(String dept_name);


    List<Department> selByTeacher(@Param("t_num") String t_num) throws Exception;
    Department findDeptByNum(String dept_num);*/
    int addDept(Department department) throws Exception;
    List<Department> selectAllDept(String faculty) throws Exception;

    int updateDept(Department department) throws Exception;

    Department findDept(Department department) throws Exception;


    int selectDeptByPrimary(String dept_num) throws Exception;
    List<Department> selByTeacher(@Param("t_num") String t_num) throws Exception;

}
