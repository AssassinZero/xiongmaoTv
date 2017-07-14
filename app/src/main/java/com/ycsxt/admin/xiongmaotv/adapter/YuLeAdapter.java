package com.ycsxt.admin.xiongmaotv.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.ycsxt.admin.xiongmaotv.domain.ZhongLeiBean;

import java.util.List;

/**
 * Created by admin on 2017/2/13.
 */

public class YuLeAdapter extends SimplePagerSatusAdapter {

    private final List<ZhongLeiBean> zhongLeiBeanList;

    public YuLeAdapter(FragmentManager fm, List<Fragment> list, List<ZhongLeiBean> zhongLeiBeanList) {
        super(fm, list);
        this.zhongLeiBeanList =zhongLeiBeanList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return zhongLeiBeanList.get(position).getCname();
    }
}
