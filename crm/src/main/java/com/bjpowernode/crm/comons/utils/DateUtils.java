package com.bjpowernode.crm.comons.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 对date类型数据进行封装
 */
public class DateUtils {

    public static String formateDateTime(Date date){
        //对指定的date对象格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-HH-dd HH:mm:ss");
        String datestr =  sdf.format(date);
        return datestr;
    }
}
