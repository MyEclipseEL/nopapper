package com.ladybird.hkd.model.pojo;

import java.io.Serializable;

/**
 * @author Shen
 * @description:
 * @create: 2019-04-18
 */
public class Department implements Serializable{
    private String dept_num;
    private String dept_name;
    private String faculty;
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

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
