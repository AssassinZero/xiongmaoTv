package com.ycsxt.admin.xiongmaotv.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ycsxt.admin.xiongmaotv.utils.DimenUtils;
import com.ycsxt.admin.xiongmaotv.utils.LogUtils;

/**
 * Created by admin on 2017/2/9.
 */

public abstract class BaseFragment extends Fragment {
    protected View root;
    protected Activity a;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.a =activity;
    }
    public void log(String msg){
        LogUtils.e(getClass().getSimpleName(),msg);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(root==null){
            // 可以放心的使用replace ，hide/show
            // root的状态都保存起来了
            root = initView(inflater, container);
            initData();
            initAdapter();
        }

        return root;
    }
    public abstract  View  initView(LayoutInflater inflater, @Nullable ViewGroup container);
    public void initData(){};
    public void initAdapter(){};
    public void toast(String str){
        Toast.makeText(a, ""+str, Toast.LENGTH_SHORT).show();
    }
    public int dp2px(int dp){
        return DimenUtils.dp2px(dp);
    }
}
