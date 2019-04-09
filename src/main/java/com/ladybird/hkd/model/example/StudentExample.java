package com.ladybird.hkd.model.example;

import com.ladybird.hkd.model.pojo.Grade;

/**
 * @author Shen
 * @description:
 * @create: 2019-04-16
 */
public class StudentExample {
    private String stu_num;  //学号
    private String stu_ID;   //身份证号
    private String stu_name; //姓名
    private GradeExample grade;      //就读班级
    private String stu_pwd;  //登录密码

    public String getStu_num() {
        return stu_num;
    }

    public void setStu_num(String stu_num) {
        this.stu_num = stu_num;
    }

    public String getStu_ID() {
        return stu_ID;
    }

    public void setStu_ID(String stu_ID) {
        this.stu_ID = stu_ID;
    }

    public String getStu_name() {
        return stu_name;
    }

    public void setStu_name(String stu_name) {
        this.stu_name = stu_name;
    }

    public GradeExample getGrade() {
        return grade;
    }

    public void setGrade(GradeExample grade) {
        this.grade = grade;
    }

    public String getStu_pwd() {
        return stu_pwd;
    }

    public void setStu_pwd(String stu_pwd) {
        this.stu_pwd = stu_pwd;
    }
}
