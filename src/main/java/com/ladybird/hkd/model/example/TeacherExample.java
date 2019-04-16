package com.ladybird.hkd.model.example;

import com.ladybird.hkd.model.pojo.Faculty;
import com.ladybird.hkd.model.pojo.Group;

import java.io.Serializable;

/**
 * @author Shen
 * @description:
 * @create: 2019-04-12
 */
public class TeacherExample implements Serializable {
    private String t_num;        //教师工号
    private String t_name;       //教师姓名
    private Faculty t_faculty;    //所属学院
    private DepartmentExample t_dept;       //所属专业
    private String t_office;     //所属教研室
    private String t_pwd;        //登陆密码
    private Group group_id;     //权限组

    public String getT_num() {
        return t_num;
    }

    public void setT_num(String t_num) {
        this.t_num = t_num;
    }

    public String getT_name() {
        return t_name;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }

    public Faculty getT_faculty() {
        return t_faculty;
    }

    public void setT_faculty(Faculty t_faculty) {
        this.t_faculty = t_faculty;
    }

    public DepartmentExample getT_dept() {
        return t_dept;
    }

    public void setT_dept(DepartmentExample t_dept) {
        this.t_dept = t_dept;
    }

    public String getT_office() {
        return t_office;
    }

    public void setT_office(String t_office) {
        this.t_office = t_office;
    }

    public String getT_pwd() {
        return t_pwd;
    }

    public void setT_pwd(String t_pwd) {
        this.t_pwd = t_pwd;
    }

    public Group getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Group group_id) {
        this.group_id = group_id;
    }
}
