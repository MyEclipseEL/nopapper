package com.ladybird.hkd.exception;

/**
 * Created by 和泉纱雾 on 2019/3/5.
 */
public class NopaperException extends RuntimeException{
    private String message;

    public NopaperException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
