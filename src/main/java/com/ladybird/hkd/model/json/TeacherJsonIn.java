package com.ladybird.hkd.model.json;

import com.ladybird.hkd.exception.ParamException;
import com.ladybird.hkd.model.pojo.Teacher;
import com.ladybird.hkd.util.ParamUtils;

/**
 * @author Shen
 * @description: 教师入参
 * @create: 2019-03-13
 */
public class TeacherJsonIn {
    private String t_num;        //教师工号
    private String t_name;       //教师姓名
    private String t_faculty;    //所属学院
    private String t_dept;       //所属专业
    private String t_office;     //所属教研室
    private String t_pwd;        //登陆密码
    private String group_id;     //权限组

    public static void ValidNumPwd(Teacher temp) throws ParamException,Exception{
        if (ParamUtils.stringIsNull(temp.getT_num())) {
            throw new ParamException("请填写工号！");
        }
        if (ParamUtils.stringIsNull(temp.getT_pwd())) {
            throw new ParamException("请填写密码！");
        }
    }

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

    public String getT_faculty() {
        return t_faculty;
    }

    public void setT_faculty(String t_faculty) {
        this.t_faculty = t_faculty;
    }

    public String getT_dept() {
        return t_dept;
    }

    public void setT_dept(String t_dept) {
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

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }
}
