package com.ladybird.hkd.util.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Shen
 * @description: 时间格式转换器
 * @create: 2019-03-20
 */
public class DateConverter implements Converter<String,Date> {
    @Override
    public Date convert(String s) {
        try {
            // 把字符串转换为日期类型（这里的yyy-MM-dd HH:mm:ss根据前端什么样式就改为什么样式）
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
            Date date = simpleDateFormat.parse(s);

            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 如果转换异常则返回空
        return null;
    }
}
