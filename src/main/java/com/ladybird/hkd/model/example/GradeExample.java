package com.ladybird.hkd.model.example;


/**
 * @author Shen
 * @description: 年级实体
 * @create: 2019-03-13
 */
public class GradeExample {
    private String g_id;
    private Integer g_year;
    private Integer g_class;
    private DepartmentExample dept;

    public GradeExample() {
    }


    public DepartmentExample getDept() {
        return dept;
    }

    public void setDept(DepartmentExample dept) {
        this.dept = dept;
    }

    public String getG_id() {
        return g_id;
    }

    public void setG_id(String g_id) {
        this.g_id = g_id;
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
}