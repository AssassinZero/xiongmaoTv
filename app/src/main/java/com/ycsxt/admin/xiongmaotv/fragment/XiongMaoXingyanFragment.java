package com.ycsxt.admin.xiongmaotv.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ycsxt.admin.xiongmaotv.MyApplication;
import com.ycsxt.admin.xiongmaotv.PlayActivity;
import com.ycsxt.admin.xiongmaotv.R;
import com.ycsxt.admin.xiongmaotv.XiangQingActivity;
import com.ycsxt.admin.xiongmaotv.adapter.BannersAdapter;
import com.ycsxt.admin.xiongmaotv.adapter.YuLeXingYanAdapter;
import com.ycsxt.admin.xiongmaotv.domain.WarpParcl;
import com.ycsxt.admin.xiongmaotv.domain.YuLeXingYanItems;
import com.ycsxt.admin.xiongmaotv.domain.ZhongLeiItems;
import com.ycsxt.admin.xiongmaotv.service.YuLeXingYanService;
import com.ycsxt.admin.xiongmaotv.view.SuperRecyclerView;
import com.ycsxt.admin.xiongmaotv.view.SuperViewPager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ycsxt.admin.xiongmaotv.R.id.dotsGroup;
import static com.ycsxt.admin.xiongmaotv.R.id.iv;
import static com.ycsxt.admin.xiongmaotv.R.id.light;

/**
 * Created by admin on 2017/2/9.
 */

public class XiongMaoXingyanFragment extends BaseZhongLeiFragment implements SuperViewPager.OnItemClickListener, ViewPager.OnPageChangeListener, View.OnClickListener {
    private SuperViewPager superViewPager;
    private LinearLayout dotsGroup;
    private View swithHead;
    private YuLeXingYanAdapter yuLeXingYanAdapter;

    @Override
    protected void initChildView() {
        super.initChildView();
        yuLeXingYanAdapter = new YuLeXingYanAdapter(list, a);
        getRecycler().setAdapter(yuLeXingYanAdapter);
        getSwip().setRefreshing(true);

        loadChildData();
    }

    int page = 1;

    private void loadChildData() {

        MyApplication.app.xingYanretrofit.create(YuLeXingYanService.class).getItems(page + "").enqueue(
                new Callback<YuLeXingYanItems>() {
                    @Override
                    public void onResponse(Call<YuLeXingYanItems> call, Response<YuLeXingYanItems> response) {
                        YuLeXingYanItems body = response.body();
                        if (page == 1) {
                            getSwip().setRefreshing(false);
                        }
                        handleContainter(body);

                    }

                    @Override
                    public void onFailure(Call<YuLeXingYanItems> call, Throwable t) {
                        toast("失败--》" + t.getMessage());
                        if (page == 1) {
                            getSwip().setRefreshing(false);
                        }
                    }
                }
        );
    }
    List<YuLeXingYanItems.DataBean.ItemsBean> list=new ArrayList<>();
    private void handleContainter(YuLeXingYanItems body) {
        List<YuLeXingYanItems.DataBean.BannersBean> banners = body.getData().getBanners();
        handleBanners(banners);
        handleSwitch();
        List<YuLeXingYanItems.DataBean.ItemsBean> items = body.getData().getItems();
        if(page==1){
            list.clear();
        }
        list.addAll(items);
        if(items.size()!=20){
            setLoadDataSuccess(true);
        }else {
            page++;
        }
        getRecycler().getAdapter().notifyDataSetChanged();

    }

    @Override
    protected void pullMore() {
        super.pullMore();
        page=1;
        setLoadDataSuccess(false);
        loadChildData();
    }

    private void handleSwitch() {
        if(swithHead==null) {
            swithHead = View.inflate(a, R.layout.layout_yulexingyan_head, null);
            swithHead.findViewById(R.id.daTuRB).setOnClickListener(this);
            swithHead.findViewById(R.id.xiaoTuRB).setOnClickListener(this);
            RecyclerView.LayoutParams params=new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,dp2px(40));
            swithHead.setLayoutParams(params);
            getRecycler().addHeadView(swithHead);
            // 模拟点击
           swithHead.post(new Runnable() {
               @Override
               public void run() {
                   swithHead.findViewById(R.id.daTuRB).performClick();
               }
           });
        }

    }

    private void handleBanners(List<YuLeXingYanItems.DataBean.BannersBean> banners) {
        if (banners != null && banners.size() > 0) {
            // 先留着
            if (superViewPager == null) {
                View inflate = View.inflate(a, R.layout.layout_head, null);
                dotsGroup = (LinearLayout) inflate.findViewById(R.id.dotsGroup);
                superViewPager = (SuperViewPager) inflate.findViewById(R.id.pager);
                // TODO 带考察viewPager是否可以点击
                RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, dp2px(150));
                params.bottomMargin = dp2px(10);
                inflate.setLayoutParams(params);
                getRecycler().addHeadView(inflate);
                superViewPager.setOnItemClickListener(this);
            }

            initHeadPager(banners);

        }
    }

    private void initHeadPager(List<YuLeXingYanItems.DataBean.BannersBean> banners) {
        List<View> viewList = new ArrayList<>();
        if (banners.size() == 1) {
            banners.addAll(banners);
            banners.addAll(banners);
        } else if (banners.size() == 2) {
            banners.addAll(banners);
        }
        for (YuLeXingYanItems.DataBean.BannersBean b : banners
                ) {
            String smallimg = b.getImg();
            View v = getIv(smallimg);
            viewList.add(v);
            superViewPager.addOnPageChangeListener(this);
        }
        dotsGroup.removeAllViews();
        initDots(banners);
        superViewPager.setAdapter(new BannersAdapter(viewList));
    }

    private void initDots(List<YuLeXingYanItems.DataBean.BannersBean> viewList) {
        Set<YuLeXingYanItems.DataBean.BannersBean> set = new HashSet<>();
        set.addAll(viewList);
        for (YuLeXingYanItems.DataBean.BannersBean v : set
                ) {
            View dot = getDot();
            dotsGroup.addView(dot);
        }
        switchDotSelectedStatus(0);
    }

    private View getDot() {
        ImageView iv = new ImageView(a);
        iv.setImageResource(R.drawable.selector_dot);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dp2px(20), dp2px(20));
        params.rightMargin = dp2px(5);
        iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        iv.setLayoutParams(params);
        return iv;
    }

    @Override
    public void onItemClickListener(ViewPager pager, View view, int position) {

    }

    private View getIv(String smallimg) {
        ImageView iv = new ImageView(a);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        Glide.with(a).load(smallimg).placeholder(R.drawable.bg).override(200,150).into(iv);
        Glide.with(a).load(smallimg).placeholder(R.drawable.bg).into(iv);
        return iv;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (superViewPager.getChildCount() > 0) {
            position %= dotsGroup.getChildCount();
            switchDotSelectedStatus(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void switchDotSelectedStatus(int i) {
        for (int j = 0; j < dotsGroup.getChildCount(); j++) {
            dotsGroup.getChildAt(j).setSelected(i == j);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.daTuRB){
           final GridLayoutManager manager = (GridLayoutManager) getRecycler().getLayoutManager();
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {

                    return manager.getSpanCount();
                }
            });
            yuLeXingYanAdapter.setType(YuLeXingYanAdapter.DATUTYPE);
            getRecycler().getAdapter().notifyDataSetChanged();
        }
        if(v.getId()==R.id.xiaoTuRB){
            final GridLayoutManager manager = (GridLayoutManager) getRecycler().getLayoutManager();
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int itemViewType = getRecycler().getAdapter().getItemViewType(position);
                    if(itemViewType<= SuperRecyclerView.HEAD||itemViewType>=SuperRecyclerView.FOOTER){
                        return manager.getSpanCount();
                    }
                    return 1;

                }
            });
            yuLeXingYanAdapter.setType(YuLeXingYanAdapter.XIAOTUTYPE);
            getRecycler().getAdapter().notifyDataSetChanged();

        }
    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View view, int position) {
        super.onItemClick(recyclerView, view, position);
        if(position>=getRecycler().getHeadCount()&&position<list.size()+getRecycler().getHeadCount()){
            Intent intent = new Intent(a, PlayActivity.class);
            intent.putExtra("list",new WarpParcl(list));

//            View viewById = view.findViewById(R.id.iv);
//            viewById.setDrawingCacheEnabled(true);
//            Bitmap drawingCache = viewById.getDrawingCache();
//            MyApplication.app.bitmap=drawingCache;
//            intent.putExtra("mir",drawingCache);
            intent.putExtra("position",position-getRecycler().getHeadCount());
            startActivity(intent);
        }
    }
}
