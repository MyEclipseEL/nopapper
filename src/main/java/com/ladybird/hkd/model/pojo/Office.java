package com.ladybird.hkd.model.pojo;

import com.ladybird.hkd.model.example.DepartmentExample;

/**
 * @author Shen
 * @description: 教研室实体
 * @create: 2019-03-13
 */
public class Office {
    private String office_id;
    private String office_name;
    private DepartmentExample dept;

    public String getOffice_id() {
        return office_id;
    }

    public void setOffice_id(String office_id) {
        this.office_id = office_id;
    }

    public String getOffice_name() {
        return office_name;
    }

    public void setOffice_name(String office_name) {
        this.office_name = office_name;
    }

    public DepartmentExample getDept() {
        return dept;
    }

    public void setDept(DepartmentExample dept) {
        this.dept = dept;
    }
}
