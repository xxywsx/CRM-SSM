package com.bjpowernode.crm.comons.utils;

import java.util.UUID;

public class UUIDUtils {
    //自动生成ID值
    public static String getUUID(){
      return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
