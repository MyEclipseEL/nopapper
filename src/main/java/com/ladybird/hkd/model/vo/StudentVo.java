package com.ladybird.hkd.model.vo;

import java.io.Serializable;

public class StudentVo implements Serializable {

    private String stu_name; //姓名
    private String stu_num;  //学号
    private String dept_name; //就读专业
    private Integer g_year;
    private Integer g_class;

    private String stu_ID;   //身份证号

    public String getStu_name() {
        return stu_name;
    }

    public void setStu_name(String stu_name) {
        this.stu_name = stu_name;
    }

    public String getStu_num() {
        return stu_num;
    }

    public void setStu_num(String stu_num) {
        this.stu_num = stu_num;
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public Integer getG_year() {
        return g_year;
    }

    public void setG_year(Integer g_year) {
        this.g_year = g_year;
    }

    public Integer getG_class() {
        return g_class;
    }

    public void setG_class(Integer g_class) {
        this.g_class = g_class;
    }

    public String getStu_ID() {
        return stu_ID;
    }

    public void setStu_ID(String stu_ID) {
        this.stu_ID = stu_ID;
    }
}
