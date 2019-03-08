package com.ladybird.hkd.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created by 和泉纱雾 on 2019/3/5.
 */
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface LoginToken {
    boolean required() default true;
}
