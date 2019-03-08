package com.ladybird.hkd.vo;

import java.util.List;

/**
 * Created by 和泉纱雾 on 2019/3/6.
 */
public class ResultObject<T> {
    private String message;
    private List<T> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
