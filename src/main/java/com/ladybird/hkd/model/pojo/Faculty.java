package com.ladybird.hkd.model.pojo;

import java.io.Serializable;

public class Faculty implements Serializable{
    private String fac_num;
    private String fac_name;
    private String tip;

    public String getFac_num() {
        return fac_num;
    }

    public void setFac_num(String fac_num) {
        this.fac_num = fac_num;
    }

    public String getFac_name() {
        return fac_name;
    }

    public void setFac_name(String fac_name) {
        this.fac_name = fac_name;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
