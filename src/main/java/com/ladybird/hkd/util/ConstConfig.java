package com.ladybird.hkd.util;

/**
 * Created by 和泉纱雾 on 2019/3/7.
 */
public class ConstConfig {

    /**
     * 存储当前登陆的对象
     */
    public static final String CURRENT_OBJECT_TOKEN = "CURRENT_OBJECT_TOKEN";

    /**
     * 存储当前登陆的对象的id字段
     */
    public static final String CURRENT_OBJECT_ID = "CURRENT_OBJECT_ID";

    /**
     * token有效期
     * */
    public static final int TOKEN_EXPIRES_HOUR = 28;

    /**
     * refresh_token有效期
     * */
    public static final int RETOKEN_EXPIRES_HOUR = 72;

    /**
     * 存放Authorization的字段
     */
    public static final String AUTHORIZATION = "authorization";

    public static String SECRET_KEY = "LadybirdNoPaper_PaperlessExamSystem";

}
