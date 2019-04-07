package com.ladybird.hkd.model.example;


import com.ladybird.hkd.model.pojo.Department;
import com.ladybird.hkd.model.pojo.Faculty;

public class DepartmentExample  {
    private String dept_num;
    private String dept_name;
    private Faculty faculty;
    private String tip;

    public String getDept_num() {
        return dept_num;
    }

    public void setDept_num(String dept_num) {
        this.dept_num = dept_num;
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}

