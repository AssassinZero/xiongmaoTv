package com.ycsxt.admin.xiongmaotv;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends BaseActivity {
    Handler handler=new Handler();
    boolean isFirst=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if(isFirst&&hasFocus){
            isFirst=false;
            handleLaunchNext();
        }
        super.onWindowFocusChanged(hasFocus);
    }

    private void handleLaunchNext() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 将所有未完成的任务清空掉
        handler.removeCallbacksAndMessages(null);
    }
}
