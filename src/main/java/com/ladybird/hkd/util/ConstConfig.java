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
    public static final String CURRENT_OBJECT= "CURRENT_OBJECT";

    /**
     * token有效期
     * */
    public static final int TOKEN_EXPIRES_HOUR = 20;

    /**
     * refresh_token有效期
     * */
    public static final int RETOKEN_EXPIRES_HOUR = 20;

    /**
     * 存放Authorization的字段
     */
    public static final String AUTHORIZATION = "authorization";

    public static final String AUTHORIZATION_RE = "reToken";

    public static String SECRET_KEY = "LadybirdNoPaper_PaperlessExamSystem";

    public static String[] number2China = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二", "十三", "十三"
            , "十四", "十五", "十六", "十七", "十八", "十九", "二十", "二十一", "二十二", "二十三", "二十四", "二十五", "二十六"
            , "二十七", "二十九", "三十", "三十一", "三十二", "三十三", "三十四", "三十五", "三十六", "三十七", "三十八", "三十九"};


}
