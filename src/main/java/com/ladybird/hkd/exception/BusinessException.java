package com.ladybird.hkd.exception;

/**
 * @author Shen
 * @description: 业务异常类
 * @create: 2019-03-18
 */
public class BusinessException extends RuntimeException{
    public BusinessException(String message) {
        super(message);
    }
}
