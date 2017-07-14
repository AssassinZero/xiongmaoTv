package com.ycsxt.admin.xiongmaotv.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ycsxt.admin.xiongmaotv.MyApplication;
import com.ycsxt.admin.xiongmaotv.R;
import com.ycsxt.admin.xiongmaotv.adapter.YuLeAdapter;
import com.ycsxt.admin.xiongmaotv.domain.YouXiBeans;
import com.ycsxt.admin.xiongmaotv.domain.ZhongLeiBean;
import com.ycsxt.admin.xiongmaotv.service.YouXiItemService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 2017/2/13.
 */

public class YuLuFragment extends BaseNavigationFragment {

    private View inflate;
    private TabLayout tab;
    private ViewPager pager;

    @Override
    protected void setContentView(ViewGroup group) {
        inflate = View.inflate(a, R.layout.fragment_yule, null);
        tab = (TabLayout) inflate.findViewById(R.id.tab);
        pager = (ViewPager) inflate.findViewById(R.id.yulePager);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        group.addView(inflate, params);
        loadTabs();
    }



    private void loadTabs() {
        MyApplication.app.retrofit.create(YouXiItemService.class).getZhongLei().enqueue(new Callback<YouXiBeans>() {
            @Override
            public void onResponse(Call<YouXiBeans> call, Response<YouXiBeans> response) {
                YouXiBeans body = response.body();
                List<ZhongLeiBean> data = body.getData();
                handleContainer(data);
            }

            @Override
            public void onFailure(Call<YouXiBeans> call, Throwable t) {
                toast("请求失败");
            }
        });
    }

    private void handleContainer(List<ZhongLeiBean> data) {
        List<Fragment> list = new ArrayList<>();
        for (ZhongLeiBean bean : data) {
            BaseChildZhongLeiFragment baseChildZhongLeiFragment = new BaseChildZhongLeiFragment();
            Bundle bundle = new Bundle();
            bundle.putString("ename", bean.getEname());
            baseChildZhongLeiFragment.setArguments(bundle);
            list.add(baseChildZhongLeiFragment);
        }
        data.add(0,new ZhongLeiBean("熊猫星颜","熊猫星颜","","","",""));
        list.add(0,new XiongMaoXingyanFragment());
        pager.setAdapter(new YuLeAdapter(getFragmentManager(), list, data));
        tab.setupWithViewPager(pager);
    }

    @Override
    public void initAdapter() {
        super.initAdapter();
    }

}
