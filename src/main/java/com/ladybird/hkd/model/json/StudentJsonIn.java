package com.ladybird.hkd.model.json;

import com.ladybird.hkd.exception.ParamException;

/**
 * @author Shen
 * @description: 学生入参
 * @create: 2019-03-22
 */
public class StudentJsonIn {
    private String stu_num;
    private String stu_ID;
    private String stu_pwd;
    private String score;

    public static void ValidRequiredPwd(StudentJsonIn temp) throws ParamException {
        if (temp.getStu_num() == null || "".equals(temp.getStu_num())) {
            throw new ParamException("学号必须填！");
        }
        if (temp.getStu_ID() == null || "".equals(temp.getStu_ID())) {
            throw new ParamException("身份证号必须填!");
        }
        if (temp.getStu_pwd() == null || "".equals(temp.getStu_pwd())) {
            throw new ParamException("密码不能为空！");
        }

    }

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

    public String getStu_pwd() {
        return stu_pwd;
    }

    public void setStu_pwd(String stu_pwd) {
        this.stu_pwd = stu_pwd;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
