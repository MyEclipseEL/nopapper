package com.ladybird.hkd.pojo;

/**
 * Created by 和泉纱雾 on 2019/3/4.
 */
public class Student {
    private String stu_num;  //学号
    private String stu_ID;   //身份证号
    private String stu_name; //姓名
    private String stu_faculty;//就读学院
    private String stu_dept; //就读专业
    private int grade;      //就读班级
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

    public String getStu_faculty() {
        return stu_faculty;
    }

    public void setStu_faculty(String stu_faculty) {
        this.stu_faculty = stu_faculty;
    }

    public String getStu_dept() {
        return stu_dept;
    }

    public void setStu_dept(String stu_dept) {
        this.stu_dept = stu_dept;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getStu_pwd() {
        return stu_pwd;
    }

    public void setStu_pwd(String stu_pwd) {
        this.stu_pwd = stu_pwd;
    }
}
