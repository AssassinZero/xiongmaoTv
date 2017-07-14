package com.ycsxt.admin.xiongmaotv.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by admin on 2017/2/9.
 */

public class SimplePagerAdapter extends FragmentPagerAdapter {
    // FragmentStatePagerAdapter 大量的pager  通过事物的detach走的是onDetach
//     FragmentPagerAdapter 使用于少量的pager  通过事物的detach走的是onDeatoryView
    List<Fragment> list;
    public SimplePagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list=list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

}
