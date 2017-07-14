package com.ycsxt.admin.xiongmaotv.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.ycsxt.admin.xiongmaotv.utils.LogUtils;

import java.util.List;

/**
 * Created by admin on 2017/2/10.
 */

public class BannersAdapter extends PagerAdapter {
    List<View> list;

    public BannersAdapter(List<View> list) {
        this.list = list;

    }




    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = list.get(position % list.size());
        if(container!=null)
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
