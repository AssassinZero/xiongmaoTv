package com.ycsxt.admin.xiongmaotv;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.wearable.view.GridViewPager;
import android.widget.RelativeLayout;

import com.ycsxt.admin.xiongmaotv.adapter.SimplePagerAdapter;
import com.ycsxt.admin.xiongmaotv.adapter.SimplePagerSatusAdapter;
import com.ycsxt.admin.xiongmaotv.domain.WarpParcl;
import com.ycsxt.admin.xiongmaotv.domain.YuLeXingYanItems;
import com.ycsxt.admin.xiongmaotv.fragment.PlayFragment;
import com.ycsxt.admin.xiongmaotv.utils.DimenUtils;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

public class PlayActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private android.support.v4.view.ViewPager playpager;
    private android.widget.RelativeLayout activityplay;
    private List<YuLeXingYanItems.DataBean.ItemsBean> list;
    private int position;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_LAYOUT_STABLE | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_play);


        WarpParcl o = (WarpParcl) getIntent().getSerializableExtra("list");
        list = (List<YuLeXingYanItems.DataBean.ItemsBean>) o.o;
        position = getIntent().getIntExtra("position", 0);
        this.activityplay = (RelativeLayout) findViewById(R.id.activity_play);
        this.playpager = (ViewPager) findViewById(R.id.playpager);
        // ViewPager通过什么实现的Scroller.scrollTo,ScrlloBys

        initData();
        initAdapter();
        initListenr();
        }

    private void initListenr() {
        playpager.setOnPageChangeListener(this);
    }

    private void initAdapter() {
        playpager.setPageMargin(DimenUtils.dp2px(5));

        playpager.setAdapter(new SimplePagerSatusAdapter(getSupportFragmentManager(), fragmentList));
        playpager.setCurrentItem(position);
    }

    private void initData() {
        fragmentList = new ArrayList<>();
        for (YuLeXingYanItems.DataBean.ItemsBean bean : list
                ) {
            PlayFragment playFragment = new PlayFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("item", bean);
            playFragment.setArguments(bundle);
            fragmentList.add(playFragment);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < fragmentList.size(); i++) {
            fragmentList.get(i).setUserVisibleHint(i==position);
            if(i!=position){
                PlayFragment fragment = (PlayFragment) fragmentList.get(i);
                fragment.restore();
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
