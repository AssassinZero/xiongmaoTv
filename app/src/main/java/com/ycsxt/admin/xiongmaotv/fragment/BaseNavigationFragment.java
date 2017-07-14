package com.ycsxt.admin.xiongmaotv.fragment;

import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ycsxt.admin.xiongmaotv.R;
import com.ycsxt.admin.xiongmaotv.view.XiongMaoToolBar;

/**
 * Created by admin on 2017/2/9.
 */

public abstract class BaseNavigationFragment extends BaseFragment implements XiongMaoToolBar.OnSearchItemClickListener {

    private XiongMaoToolBar toolbar;
    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        ViewGroup inflate = (ViewGroup) inflater.inflate(R.layout.layout_base_navigation, container, false);
        init(inflate);
        setContentView(inflate);
        return inflate;
    }

    protected abstract void setContentView(ViewGroup group);

    private void init(View inflate) {
        toolbar = (XiongMaoToolBar) inflate.findViewById(R.id.xiongMaoToolBar);
        toolbar.setOnSearchItemClickListener(this);
    }

    @Override
    public void onSecrchClick() {

    }
    public   XiongMaoToolBar getToolbar(){
        return toolbar;
    }
}
