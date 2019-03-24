package com.ladybird.hkd.mapper;

import com.ladybird.hkd.model.pojo.Department;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface DeptMapper {
    int addDept(Department department);

    int delDept(@Param("dept_num") String dept_num);

    int updateDept(Department department);

    Department findDept(Department department);


    Department findDeptByName(String dept_name);

    Department findDeptByNum(String dept_num);
}
