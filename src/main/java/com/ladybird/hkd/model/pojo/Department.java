package com.ladybird.hkd.model.pojo;

/**
 * @author Shen
 * @description: 专业实体
 * @create: 2019-03-13
 */
public class Department {
    private String dept_num;
    private String dept_name;
    private Faculty faculty;

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

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
}
