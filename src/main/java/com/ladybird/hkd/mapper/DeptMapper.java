package com.ladybird.hkd.mapper;

import com.ladybird.hkd.model.pojo.Department;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DeptMapper {
    void addDept(Department department);

    void delDept(@Param("dept_num") String dept_num);

    void updateDept(Department department);

    Department findDept(Department department);


    Department findDeptByName(String dept_name);

    Department findDeptByNum(String dept_num);

    List<Department> selByTeacher(@Param("t_num") String t_num) throws Exception;
}
