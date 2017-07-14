package com.ycsxt.admin.xiongmaotv.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ycsxt.admin.xiongmaotv.R;
import com.ycsxt.admin.xiongmaotv.ZhongLeiActivity;
import com.ycsxt.admin.xiongmaotv.adapter.SimplePagerSatusAdapter;
import com.ycsxt.admin.xiongmaotv.domain.Model;
import com.ycsxt.admin.xiongmaotv.domain.ZhongLeiBean;
import com.ycsxt.admin.xiongmaotv.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/2/9.
 */

public class ShouYeFragment extends BaseNavigationFragment implements View.OnClickListener {


    private View root;
    private TabLayout tabLayout;
    private ViewPager shoueYePager;
    private List<ZhongLeiBean> zhongLeiBeanList=new ArrayList<>();
    List<Fragment> list=new ArrayList<>();
    @Override
    protected void setContentView(ViewGroup group) {
        root = View.inflate(a, R.layout.fragment_shouye, null);
        init();
        group.addView(root);
    }

    private void init() {
        tabLayout = (TabLayout) root.findViewById(R.id.tab);
        shoueYePager = (ViewPager) root.findViewById(R.id.shouyePager);
        root.findViewById(R.id.shouyeAdd).setOnClickListener(this);
        root.findViewById(R.id.shouyeAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(a, ZhongLeiActivity.class);
                startActivityForResult(intent,1);
            }
        });
        initTabs(tabLayout);
        initAllPager();
        initAllPagerSettings();
    }

    private void initAllPagerSettings() {
//        shoueYePager.addOnPageChangeListener();
//        shoueYePager.removeOnPageChangeListener();
        shoueYePager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                shoueYePager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        }

    private void initTabs(TabLayout tabLayout) {
        initZhongleis();
        initItems(tabLayout);

    }

    private void initAllPager() {
        list.clear();
        for (ZhongLeiBean b:zhongLeiBeanList
             ) {
            if(b.getEname().equals("精彩推荐")) {


                list.add(new JingCaiTuiJianFragment());
            }else if(b.getEname().equals("全部直播")){
                list.add(new QuanBuZhiBoFragment());
            }else {
                BaseChildZhongLeiFragment baseChildZhongLeiFragment = new BaseChildZhongLeiFragment();
                Bundle bundle=new Bundle();
                bundle.putString("ename",b.getEname());

                baseChildZhongLeiFragment.setArguments(bundle);
                list.add(baseChildZhongLeiFragment);
            }
        }

        shoueYePager.setAdapter(new SimplePagerSatusAdapter(getFragmentManager(),list));
    }

    private void initItems(TabLayout tabLayout) {
        tabLayout.removeAllTabs();
        for (int i=0;i<zhongLeiBeanList.size();i++){
            ZhongLeiBean zhongLeiBean = zhongLeiBeanList.get(i);
            String cname = zhongLeiBean.getCname();
            View tabView = View.inflate(a, R.layout.layout_item_tabs, null);
            TextView textView = (TextView) tabView.findViewById(R.id.tv);
            textView.setText(cname);
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setCustomView(tabView);
            tabLayout.addTab(tab);
        }
    }

    private void initZhongleis() {
        zhongLeiBeanList.clear();
        String s = SPUtils.get(Model.ZHONGLEIITELS);
        List<ZhongLeiBean> temp = new Gson().fromJson(s, new TypeToken<List<ZhongLeiBean>>() {
        }.getType());

        zhongLeiBeanList.addAll(temp);
    }

    @Override
    public void onClick(View v) {
//        TODO 跳转到选择的Activity
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!= Activity.RESULT_OK)return;
        if(requestCode==1){
            initTabs(tabLayout);
            initAllPager();
            initAllPagerSettings();
        }
    }
}
