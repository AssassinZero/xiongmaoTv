package com.ycsxt.admin.xiongmaotv.utils;

import com.ycsxt.admin.xiongmaotv.MyApplication;

/**
 * Created by admin on 2017/2/10.
 */

public class DimenUtils {
    public static  int dp2px(int dp){
        float v = MyApplication.app.getResources().getDisplayMetrics().density * dp + 0.5f;
        return (int) v;
    }
}
