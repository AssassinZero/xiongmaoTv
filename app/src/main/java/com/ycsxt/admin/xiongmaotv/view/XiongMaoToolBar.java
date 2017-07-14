package com.ycsxt.admin.xiongmaotv.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ycsxt.admin.xiongmaotv.R;

/**
 * Created by admin on 2017/2/9.
 */

public class XiongMaoToolBar extends RelativeLayout implements View.OnClickListener {

    private TextView titleTv;
    private ImageView logoIv;
    private ImageView searchIv;
    private OnSearchItemClickListener listener;

    public XiongMaoToolBar(Context context) {
        super(context);
        init();
    }

    private void init() {
//        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.layout_xiongmao_toolbar, null);
//        addView(rootView);
        LayoutInflater.from(getContext()).inflate(R.layout.layout_xiongmao_toolbar, this);
        titleTv = (TextView) this.findViewById(R.id.titleTv);
        logoIv = (ImageView) this.findViewById(R.id.logoIv);
        searchIv = (ImageView) this.findViewById(R.id.searchIv);
        searchIv.setOnClickListener(this);
    }

    public void setTitle(String title) {
        titleTv.setText(title);
    }

    public void setLogoResource(int resId) {
        logoIv.setImageResource(resId);
    }

    public void setLogoVisiable(int visiable) {
        logoIv.setVisibility(visiable);
    }

    public void setTitleVisiable(int visiable) {
        titleTv.setVisibility(visiable);
    }

    public void setSearchResource(int resId) {
        searchIv.setImageResource(resId);
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onSecrchClick();
        }
    }

    public interface OnSearchItemClickListener {
        void onSecrchClick();
    }

    public void setOnSearchItemClickListener(OnSearchItemClickListener listener) {
        this.listener = listener;
    }


    public XiongMaoToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public XiongMaoToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
}
