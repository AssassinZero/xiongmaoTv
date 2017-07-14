package com.ycsxt.admin.xiongmaotv;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.ycsxt.admin.xiongmaotv.utils.LogUtils;

/**
 * Created by admin on 2017/2/9.
 */

public class BaseActivity extends AppCompatActivity {

    public void log(String msg){
        LogUtils.e(getClass().getSimpleName(),msg);
    }
    public void back(View v) {
        finish();
    }
    public void toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
