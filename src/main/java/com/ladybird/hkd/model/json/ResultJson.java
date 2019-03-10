package com.ladybird.hkd.model.json;

/**
 * @author Shen
 * @Description: 用于整理返回前端数据的类
 * @create: 2019-03-18
 */
public class ResultJson {

    private String message;
    private Integer code;
    private Object data;



    public ResultJson(Integer code,String message,  Object data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static ResultJson Success(){return new ResultJson(0, "success", null);}

    public static ResultJson Success(Object data){return new ResultJson(0,"success",data);}

    public static ResultJson ParameterError(){return new ResultJson(1000,"请求参数错误",null);}

    public static ResultJson ParameterException(String msg, Object data) {
        return new ResultJson(1000, msg, data);
    }

    public static ResultJson BusinessErrorException(String msg,Object data){
        return new ResultJson(1001, msg, data);
    }

    public static ResultJson TokenRedisException() {
        return new ResultJson(1005, "token存储redis时出错", null);
    }

    public static ResultJson Forbidden(String msg) {
        return new ResultJson(1003, msg, null);
    }

    public static ResultJson ServerException(){return new ResultJson(9999,"系统错误，请联系开发人员!",null);}

}
