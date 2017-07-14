package com.ycsxt.admin.xiongmaotv;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ycsxt.admin.xiongmaotv.domain.ZhongLeiBean;
import com.ycsxt.admin.xiongmaotv.fragment.BaseChildZhongLeiFragment;
import com.ycsxt.admin.xiongmaotv.view.SwipBackLayout;

public class XiangQingActivity extends BaseActivity implements SwipBackLayout.OnSwipBackToggleListener {

    private android.widget.TextView titleTv;
    private android.widget.FrameLayout group;
    private com.ycsxt.admin.xiongmaotv.view.SwipBackLayout activityxiangqing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiang_qing);
        this.activityxiangqing = (SwipBackLayout) findViewById(R.id.activity_xiang_qing);
        this.group = (FrameLayout) findViewById(R.id.group);
        this.titleTv = (TextView) findViewById(R.id.titleTv);
        activityxiangqing.setOnSwipBackToggleListener(this);
        ZhongLeiBean bean = getIntent().getParcelableExtra("bean");
        titleTv.setText(bean.getCname());
        BaseChildZhongLeiFragment baseChildZhongLeiFragment = new BaseChildZhongLeiFragment();
        Bundle bundle = new Bundle();
        bundle.putString("ename",bean.getEname());
        baseChildZhongLeiFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.group,baseChildZhongLeiFragment).commit();
    }

    @Override
    public void onOpen() {

    }

    @Override
    public void back(View v) {
        activityxiangqing.close();
    }

    @Override
    public void onClose() {
        finish();
    }

    @Override
    public void onBackPressed() {
        activityxiangqing.close();
    }
}
