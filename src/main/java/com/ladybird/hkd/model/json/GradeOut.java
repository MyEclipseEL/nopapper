package com.ladybird.hkd.model.json;

import com.ladybird.hkd.model.example.DepartmentExample;

/**
 * @author Shen
 * @description:
 * @create: 2019-03-20
 */
public class GradeOut {
    private String g_id;
    private int g_year;
    private int g_class;
    private DepartmentExample dept;

    public String getG_id() {
        return g_id;
    }

    public void setG_id(String g_id) {
        this.g_id = g_id;
    }

    public int getG_year() {
        return g_year;
    }

    public void setG_year(int g_year) {
        this.g_year = g_year;
    }

    public int getG_class() {
        return g_class;
    }

    public void setG_class(int g_class) {
        this.g_class = g_class;
    }

    public DepartmentExample getDept() {
        return dept;
    }

    public void setDept(DepartmentExample dept) {
        this.dept = dept;
    }
}
