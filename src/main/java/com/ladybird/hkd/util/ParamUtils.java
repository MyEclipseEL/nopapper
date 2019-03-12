package com.ladybird.hkd.util;

import com.ladybird.hkd.exception.NullException;

/**
 * @author Shen
 * @description: 参数工具类，校验一些参数
 * @create: 2019-03-11
 */
public class ParamUtils {
    public static boolean stringIsNull(String value){
        if (value == null || "".equals(value.trim())) {
            return true;
        }
        return false;
    }

    public static boolean stringIsNull(String value,String value2){
        if ((value == null || "".equals(value.trim())) || (value2 == null ||"".equals(value2.trim()))) {
            return true;
        }
        return false;
    }
}
