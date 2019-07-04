package com.wwq.date;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * @author wwq
 * @date 2019年6月25日 17:49:42
 * */
public class DateUtils {

    private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    /**
     * 获取当前时间的字符串 如:
     * @return 当前时间的字符串
     * */
    public static String getNowTimeString(){
        String dateString="";
        Date date=new Date();
        dateString=sdf.format(date);
        return dateString;
    }

    public static void main(String[] args) {
        System.out.println(getNowTimeString());
    }
}
