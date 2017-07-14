package com.ycsxt.admin.xiongmaotv.utils;

import com.ycsxt.admin.xiongmaotv.MyApplication;

/**
 * Created by admin on 2017/2/9.
 */

public class SPUtils {
    public static void put(String key,String value){
        MyApplication.app.getSharedPreferences("setting",0).edit()
                .putString(key,value).commit();
    }
    public static String get(String key){
       return MyApplication.app.getSharedPreferences("setting",0).getString(key,"");
    }
}
