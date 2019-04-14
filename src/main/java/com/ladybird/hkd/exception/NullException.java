package com.ladybird.hkd.exception;

/**
 * @author Shen
 * @description: 空参数异常类
 * @create: 2019-03-11
 */
public class NullException extends RuntimeException{
    public NullException(String message) {
        super(message);
    }
}
