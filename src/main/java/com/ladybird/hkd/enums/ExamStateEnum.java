package com.ladybird.hkd.enums;

/**
 * Created by 和泉纱雾 on 2019/3/13.
 */
public enum ExamStateEnum {
    BEGIN ("考试开始！",1),
    FINISH("考试已经结束", -1),
    PREPAR("考试还未开始！", 0),
    ;
    private Integer code;
    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    ExamStateEnum(String msg, Integer code) {
        this.code = code;
        this.msg = msg;
    }
}
