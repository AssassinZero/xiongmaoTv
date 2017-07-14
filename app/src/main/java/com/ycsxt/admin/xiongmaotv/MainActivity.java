package com.ycsxt.admin.xiongmaotv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.ycsxt.admin.xiongmaotv.fragment.ShouYeFragment;
import com.ycsxt.admin.xiongmaotv.fragment.WoDeFragment;
import com.ycsxt.admin.xiongmaotv.fragment.YouXiFragment;
import com.ycsxt.admin.xiongmaotv.fragment.YuLuFragment;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private RadioButton shouyeRB;
    private RadioButton youxiRB;
    private RadioButton yuleRB;
    private RadioButton wodeRB;
    private ShouYeFragment shouYeFragment;
    private YouXiFragment youXiFragment;
    private YuLuFragment yuLeFragment;
    private WoDeFragment woDeFragment;
    private FragmentManager fm;
    private RadioGroup bottomGroup;
    private  int preBottomSelectedId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initFragment();
        initListener();
    }

    private void initListener() {
        shouyeRB.setOnClickListener(this);
        youxiRB.setOnClickListener(this);
        wodeRB.setOnClickListener(this);
        yuleRB.setOnClickListener(this);
    }

    private void initFragment() {
        fm = getSupportFragmentManager();
        shouYeFragment = new ShouYeFragment();
        youXiFragment = new YouXiFragment();
        yuLeFragment = new YuLuFragment();
        woDeFragment = new WoDeFragment();
        // fragment添加删除的时候 会走生命周期
        fm.beginTransaction().replace(R.id.fragGroup, shouYeFragment).commit();
        preBottomSelectedId=shouyeRB.getId();
    }

    private void initView() {
        shouyeRB = (RadioButton) findViewById(R.id.shouyeRB);
        shouyeRB.setChecked(true);
        youxiRB = (RadioButton) findViewById(R.id.youxiRB);
        yuleRB = (RadioButton) findViewById(R.id.yuleRB);
        wodeRB = (RadioButton) findViewById(R.id.wodeRB);
        bottomGroup = (RadioGroup) findViewById(R.id.bottom);

    }

    @Override
    public void onClick(final View v) {
        handleBottomNavigationItemClick(v);
        // TODO 深入理解
       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               handleToolbarSatusChanged(v);
           }
       },0);
    }

    private void handleToolbarSatusChanged(View v) {
        switch (v.getId()){
            case R.id.shouyeRB:
                shouYeFragment.getToolbar().setLogoVisiable(View.VISIBLE);
                shouYeFragment.getToolbar().setTitleVisiable(View.GONE);
                shouYeFragment.getToolbar().setSearchResource(R.drawable.selector_xiongmao_toolbar_search);
                break;
            case R.id.youxiRB:
               log(youXiFragment.getToolbar()+"----<");
                youXiFragment.getToolbar().setLogoVisiable(View.GONE);

                youXiFragment.getToolbar().setTitleVisiable(View.VISIBLE);
                youXiFragment.getToolbar().setTitle("游戏");

                break;
            case R.id.wodeRB:
                woDeFragment.getToolbar().setLogoVisiable(View.GONE);
                woDeFragment.getToolbar().setTitleVisiable(View.VISIBLE);
                woDeFragment.getToolbar().setTitle("我的");
                woDeFragment.getToolbar().setSearchResource(R.drawable.selector_xiongmao_toolbar_setting);
                break;
            case  R.id.yuleRB:

                yuLeFragment.getToolbar().setLogoVisiable(View.GONE);
                yuLeFragment.getToolbar().setTitleVisiable(View.VISIBLE);
                yuLeFragment.getToolbar().setTitle("娱乐");
                break;
        }
    }

    private void handleBottomNavigationItemClick(View v) {
       boolean isEqual= checkCurrentSelectedId(v);
        if(isEqual){
            log("返回了");
            return;
        }
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        switch (v.getId()) {
            case R.id.shouyeRB:
                preBottomSelectedId=shouyeRB.getId();
                fragmentTransaction.replace(R.id.fragGroup,shouYeFragment);

                break;
            case R.id.wodeRB:
                preBottomSelectedId=wodeRB.getId();
                fragmentTransaction.replace(R.id.fragGroup,woDeFragment);

                break;
            case R.id.youxiRB:
                preBottomSelectedId=youxiRB.getId();
                fragmentTransaction.replace(R.id.fragGroup,youXiFragment);

                break;
            case R.id.yuleRB:
                preBottomSelectedId=yuleRB.getId();
                fragmentTransaction.replace(R.id.fragGroup,yuLeFragment);

                break;

        }
        fragmentTransaction.commit();
    }

    private boolean checkCurrentSelectedId(View v) {
        // 当按下的时候，会响应点击和选中。因此恒等
//        return bottomGroup.getCheckedRadioButtonId()==v.getId();
        return preBottomSelectedId==v.getId();
    }

}
