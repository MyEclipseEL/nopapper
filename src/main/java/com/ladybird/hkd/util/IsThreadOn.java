package com.ladybird.hkd.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author Shen
 * @description: 判断组卷线程是否开启
 * @create: 2019-04-20
 */
public class IsThreadOn {
    //
    public static boolean threadIsOn() throws IOException{
//        String flag = FileUtils.readFileToString(new File(UrlConf.LOCAL_THREAD_COUNT));
        String flag = FileUtils.readFileToString(new File(UrlConf.SERVER_THREAD_COUNT));

        if (flag.equals("1")) {
            return true;
        }
        letThreadOn();
        return false;
    }

    public static void letThreadOn() throws IOException{
//        FileUtils.writeStringToFile(new File(UrlConf.LOCAL_THREAD_COUNT),"1");
        //服务器地址
        FileUtils.writeStringToFile(new File(UrlConf.SERVER_THREAD_COUNT),"1");
    }

    public static void letThreadOff()throws IOException {
//        FileUtils.writeStringToFile(new File(UrlConf.LOCAL_THREAD_COUNT),"0");
        //服务器地址
                FileUtils.writeStringToFile(new File(UrlConf.SERVER_THREAD_COUNT),"0");

    }
}
