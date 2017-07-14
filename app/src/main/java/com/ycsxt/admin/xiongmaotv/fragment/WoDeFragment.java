package com.ycsxt.admin.xiongmaotv.fragment;

import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by admin on 2017/2/9.
 */

public class WoDeFragment extends BaseNavigationFragment {

    @Override
    protected void setContentView(ViewGroup group) {
        TextView tv=new TextView(a);
        tv.setTextSize(20);
        tv.setGravity(Gravity.CENTER);
        tv.setText(getClass().getSimpleName());
        group.addView(tv);
    }
}
