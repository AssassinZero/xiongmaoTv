package com.ycsxt.admin.xiongmaotv.utils;

import android.util.Log;

import com.ycsxt.admin.xiongmaotv.MyApplication;

/**
 * Created by admin on 2017/2/9.
 */

public class LogUtils {
    public static void e(String tag,String msg){
        if(MyApplication.idDebug)
        Log.e(tag,msg);
    }
}
