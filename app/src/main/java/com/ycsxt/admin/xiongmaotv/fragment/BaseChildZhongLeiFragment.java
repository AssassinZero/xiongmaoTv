package com.ycsxt.admin.xiongmaotv.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.ycsxt.admin.xiongmaotv.MyApplication;
import com.ycsxt.admin.xiongmaotv.R;
import com.ycsxt.admin.xiongmaotv.adapter.BannersAdapter;
import com.ycsxt.admin.xiongmaotv.adapter.ZhongLeiItemAdapter;
import com.ycsxt.admin.xiongmaotv.domain.ZhongLeiItems;
import com.ycsxt.admin.xiongmaotv.service.ZhongLeiService;
import com.ycsxt.admin.xiongmaotv.view.SuperViewPager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 2017/2/9.
 */

/**
 * 只是针对具体的游戏种类
 */
public class BaseChildZhongLeiFragment extends BaseZhongLeiFragment implements View.OnClickListener, ViewPager.OnPageChangeListener, SuperViewPager.OnItemClickListener {
    List<ZhongLeiItems.DataBean.ItemsBean> list = new ArrayList<>();
    private SuperViewPager superViewPager;
    private View footer;
    private View loadLayout;
    private View errorLayout;
    private View nodataLayout;
    private LinearLayout dotsGroup;
    private List<ZhongLeiItems.DataBean.BannersBean> banners;

    @Override
    public void initData() {
        super.initData();
        // 有没有头部根据数据源
        getSwip().setRefreshing(true);
        loadData();
    }

    int page = 1;

    private void loadData() {
        String ename = getArguments().getString("ename");
       if(!ename.equals("熊猫星颜")) {
           loadOther(ename);
       }
    }



    private void loadOther(String ename) {
        MyApplication.app.retrofit.create(ZhongLeiService.class).getZhongLeiItems(ename, page + "").enqueue(
                new Callback<ZhongLeiItems>() {
                    @Override
                    public void onResponse(Call<ZhongLeiItems> call, Response<ZhongLeiItems> response) {
                        ZhongLeiItems body = response.body();
                        if (page == 1) {
                            getSwip().setRefreshing(false);
                        }
                        handleData(body);

                    }

                    @Override
                    public void onFailure(Call<ZhongLeiItems> call, Throwable t) {
                        toast("请求失败");
                        if (page == 1) {
                            getSwip().setRefreshing(false);
                        } else {
                            switchError();
                        }
                    }
                }
        );
    }

    private void handleData(ZhongLeiItems body) {
        banners = body.getData().getBanners();
        if (!(banners == null || banners.size() == 0)) {
            handleHead(banners);
        }
        List<ZhongLeiItems.DataBean.ItemsBean> items = body.getData().getItems();
        //TODO 下拉刷新 上拉加载效果不一致
        if (page == 1) {
            // 清空之前的数据
            list.clear();
        } else {
            setLoadDataSuccess(false);
        }
        if (items.size() >0) {
            list.addAll(items);

            handlerContainer();
            if(items.size()<20&&page==1){
                handlEmptyFooterData();
                // 设置boolean
            }
            page++;
        } else {
            handlEmptyFooterData();
        }
    }

    private void handlerContainer() {
        getRecycler().getAdapter().notifyDataSetChanged();
    }

    /**
     * 处理我们的footer
     */
    private void handlEmptyFooterData() {
        switchtoNodata();
        setLoadDataSuccess(true);
    }

    private void handleHead(List<ZhongLeiItems.DataBean.BannersBean> banners) {
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

    private void initHeadPager(List<ZhongLeiItems.DataBean.BannersBean> banners) {
        List<View> viewList = new ArrayList<>();
        if(banners.size()==1){
            banners.addAll(banners);
            banners.addAll(banners);
        }else if(banners.size()==2) {
            banners.addAll(banners);
        }
        for (ZhongLeiItems.DataBean.BannersBean b : banners
                ) {
            String smallimg = b.getBigimg();
            View v = getIv(smallimg);
            viewList.add(v);
            superViewPager.addOnPageChangeListener(this);
        }
        dotsGroup.removeAllViews();
        initDots(banners);
        superViewPager.setAdapter(new BannersAdapter(viewList));

    }

    private void initDots(List<ZhongLeiItems.DataBean.BannersBean> viewList) {
        Set<ZhongLeiItems.DataBean.BannersBean> set=new HashSet<>();
        set.addAll(viewList);
        for (ZhongLeiItems.DataBean.BannersBean v : set
                ) {
            View dot=getDot();
            dotsGroup.addView(dot);
        }
       switchDotSelectedStatus(0);
    }

    private void switchDotSelectedStatus(int i) {
        for (int j = 0; j < dotsGroup.getChildCount(); j++) {
            dotsGroup.getChildAt(j).setSelected(i==j);
        }
    }

    private View getDot() {
        ImageView iv=new ImageView(a);
        iv.setImageResource(R.drawable.selector_dot);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(dp2px(20),dp2px(20));
        params.rightMargin=dp2px(5);
        iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        iv.setLayoutParams(params);
        return iv;
    }


    private View getIv(String smallimg) {
        ImageView iv = new ImageView(a);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        Glide.with(a).load(smallimg).placeholder(R.drawable.bg).override(200,150).into(iv);
        Glide.with(a).load(smallimg).placeholder(R.drawable.bg).into(iv);
        return iv;
    }

    @Override
    public void initAdapter() {
        super.initAdapter();
    }

    @Override
    protected void initChildView() {
        super.initChildView();
        // 完成头部和尾部
        getRecycler().setAdapter(new ZhongLeiItemAdapter(list, a));
        // 添加尾巴
        initFooter();
    }

    private void initFooter() {
        footer = View.inflate(a, R.layout.item_zhonglei_footer, null);
        loadLayout = footer.findViewById(R.id.loadLayout);
        errorLayout = footer.findViewById(R.id.errorLayout);
        errorLayout.setOnClickListener(this);
        nodataLayout = footer.findViewById(R.id.nodataLayout);
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(-1, dp2px(30));
        footer.setLayoutParams(params);
        getRecycler().addFooterView(footer);
        switchtoLoad();
    }

    private void switchtoLoad() {
        // GONE的话会不会导致 footer重新layout呢?(会)
        // INVISIABLE 会不会导致重新layout?（不会）
        loadLayout.setVisibility(View.VISIBLE);
        errorLayout.setVisibility(View.GONE);
        nodataLayout.setVisibility(View.GONE);
    }

    private void switchtoNodata() {
        loadLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
        nodataLayout.setVisibility(View.VISIBLE);
    }

    private void switchError() {
        loadLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
        nodataLayout.setVisibility(View.GONE);
    }

    @Override
    protected void pullMore() {
        super.pullMore();
        // page设置为1
        page = 1;
        // 可以根据情况判断
        setLoadDataSuccess(false);
        switchtoLoad();
        loadData();
    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View view, int position) {
        if(position>=getRecycler().getHeadCount()&&position<list.size()+getRecycler().getHeadCount()){
            toast(position-getRecycler().getHeadCount()+"");
        }
    }

    @Override
    protected void loadFooterData() {
        super.loadFooterData();
        loadData();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == errorLayout.getId()) {
            handleErrorLayoutClick();
        }
    }

    private void handleErrorLayoutClick() {
        switchtoLoad();
        loadData();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(superViewPager.getChildCount()>0){
            position%=dotsGroup.getChildCount();
            switchDotSelectedStatus(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public void onItemClickListener(ViewPager pager, View view, int position) {
        toast(position+"");
    }

}
