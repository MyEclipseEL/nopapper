package com.ladybird.hkd.exception;

/**
 * @author Shen
 * @description:
 * @create: 2019-04-20
 */
public class AuthorizationException extends RuntimeException {
    public AuthorizationException(String message) {
        super(message);
    }
}
