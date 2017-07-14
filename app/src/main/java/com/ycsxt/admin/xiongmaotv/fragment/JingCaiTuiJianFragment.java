package com.ycsxt.admin.xiongmaotv.fragment;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.ycsxt.admin.xiongmaotv.MyApplication;
import com.ycsxt.admin.xiongmaotv.R;
import com.ycsxt.admin.xiongmaotv.XiangQingActivity;
import com.ycsxt.admin.xiongmaotv.adapter.BannersAdapter;
import com.ycsxt.admin.xiongmaotv.adapter.JingCaiAdapter;
import com.ycsxt.admin.xiongmaotv.domain.JingCaiBaners;
import com.ycsxt.admin.xiongmaotv.domain.JingCaiTuiJianItems;
import com.ycsxt.admin.xiongmaotv.domain.JingCaiXingYanItems;
import com.ycsxt.admin.xiongmaotv.domain.ZhongLeiBean;
import com.ycsxt.admin.xiongmaotv.domain.ZhongLeiItems;
import com.ycsxt.admin.xiongmaotv.service.JingCaiBannerService;
import com.ycsxt.admin.xiongmaotv.service.JingCaiItemsService;
import com.ycsxt.admin.xiongmaotv.service.JingCaiXingYanService;
import com.ycsxt.admin.xiongmaotv.view.SuperViewPager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 2017/2/13.
 */

public class JingCaiTuiJianFragment extends BaseZhongLeiFragment implements SuperViewPager.OnItemClickListener, ViewPager.OnPageChangeListener, JingCaiAdapter.OnJingCaiItemClickListener {
    private SuperViewPager superViewPager;
    private LinearLayout dotsGroup;
    List<JingCaiTuiJianItems.DataBean> list = new ArrayList<>();
    private JingCaiAdapter jingCaiAdapter;

    @Override
    protected void initChildView() {
        setLoadDataSuccess(true);
        getRecycler().setLayoutManager(new LinearLayoutManager(a));
        jingCaiAdapter = new JingCaiAdapter(list, a);
        jingCaiAdapter.setOnJingCaiItemClickListener(this);
        getRecycler().setAdapter(jingCaiAdapter);
        loadChildData();
    }

    private void loadChildData() {
        loadBannesData();
        loadContinerData();

    }

    private void loadXingYanData() {
        MyApplication.app.xingYanretrofit.create(JingCaiXingYanService.class).getJingCaiItems().enqueue(new Callback<JingCaiXingYanItems>() {
            @Override
            public void onResponse(Call<JingCaiXingYanItems> call, Response<JingCaiXingYanItems> response) {
                JingCaiXingYanItems.DataBean data = response.body().getData();
                String place = data.getPlace();
                int position = Integer.parseInt(place);
                if (position < 0) position = 0;
                list.add(position, convertToJingCaiItem(data));
                getRecycler().getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<JingCaiXingYanItems> call, Throwable t) {
                getRecycler().getAdapter().notifyDataSetChanged();
            }
        });
    }

    private JingCaiTuiJianItems.DataBean convertToJingCaiItem(JingCaiXingYanItems.DataBean data) {
        JingCaiTuiJianItems.DataBean da = new JingCaiTuiJianItems.DataBean();
        JingCaiTuiJianItems.DataBean.TypeBean typeBean = new JingCaiTuiJianItems.DataBean.TypeBean();
        typeBean.setCname(data.getCname());
        da.setType(typeBean);
        da.setTotal(0);
        List<ZhongLeiItems.DataBean.ItemsBean> lisst = new ArrayList<>();
        for (JingCaiXingYanItems.DataBean.ItemsBean item : data.getItems()) {
            ZhongLeiItems.DataBean.ItemsBean itemsBean = new ZhongLeiItems.DataBean.ItemsBean();
            itemsBean.setName(item.getName());
            ZhongLeiItems.DataBean.ItemsBean.UserinfoBean userinfoBean = new ZhongLeiItems.DataBean.ItemsBean.UserinfoBean();
            userinfoBean.setNickName(item.getNickName());
            itemsBean.setUserinfo(userinfoBean);
            itemsBean.setPerson_num(item.getPersonnum());
            ZhongLeiItems.DataBean.ItemsBean.PicturesBean picturesBean = new ZhongLeiItems.DataBean.ItemsBean.PicturesBean();
            picturesBean.setImg(item.getPhoto());
            itemsBean.setPictures(picturesBean);
            lisst.add(itemsBean);
        }
        da.setItems(lisst);
        return da;
    }

    private void loadContinerData() {
        MyApplication.app.retrofit.create(JingCaiItemsService.class)
                .getItems().enqueue(new Callback<JingCaiTuiJianItems>() {
            @Override
            public void onResponse(Call<JingCaiTuiJianItems> call, Response<JingCaiTuiJianItems> response) {
                loadXingYanData();
                List<JingCaiTuiJianItems.DataBean> data = response.body().getData();
                handleConatinerData(data);

            }

            @Override
            public void onFailure(Call<JingCaiTuiJianItems> call, Throwable t) {

            }
        });
    }

    private void handleConatinerData(List<JingCaiTuiJianItems.DataBean> data) {
        list.clear();
        list.addAll(data);

    }

    private void loadBannesData() {
        MyApplication.app.retrofit.create(JingCaiBannerService.class).getBanners().enqueue(new Callback<JingCaiBaners>() {
            @Override
            public void onResponse(Call<JingCaiBaners> call, Response<JingCaiBaners> response) {
                getSwip().setRefreshing(false);
                List<JingCaiBaners.DataBean> data = response.body().getData();
                handleBanners(data);
            }

            @Override
            public void onFailure(Call<JingCaiBaners> call, Throwable t) {
                getSwip().setRefreshing(false);
            }
        });
    }

    private void handleBanners(List<JingCaiBaners.DataBean> data) {
        if (superViewPager == null) {
            View inflate = View.inflate(a, R.layout.layout_head, null);
            dotsGroup = (LinearLayout) inflate.findViewById(R.id.dotsGroup);
            superViewPager = (SuperViewPager) inflate.findViewById(R.id.pager);
            // TODO 带考察viewPager是否可以点击
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, dp2px(200));
            params.bottomMargin = dp2px(10);
            inflate.setLayoutParams(params);
            getRecycler().addHeadView(inflate);
            superViewPager.setOnItemClickListener(this);
        }

        initHeadPager(data);
    }

    private void initHeadPager(List<JingCaiBaners.DataBean> banners) {
        List<View> viewList = new ArrayList<>();
        if (banners.size() == 1) {
            banners.addAll(banners);
            banners.addAll(banners);
        } else if (banners.size() == 2) {
            banners.addAll(banners);
        }
        for (JingCaiBaners.DataBean b : banners
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

    private void initDots(List<JingCaiBaners.DataBean> viewList) {
        Set<JingCaiBaners.DataBean> set = new HashSet<>();
        set.addAll(viewList);
        for (JingCaiBaners.DataBean v : set
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
    protected void pullMore() {
        super.pullMore();
        loadChildData();
    }

    @Override
    public void onItemClickListener(ViewPager pager, View view, int position) {

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

    private void switchDotSelectedStatus(int i) {
        for (int j = 0; j < dotsGroup.getChildCount(); j++) {
            dotsGroup.getChildAt(j).setSelected(i == j);
        }
    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onMoreClick(JingCaiTuiJianItems.DataBean dataBean) {
        Intent intent=new Intent(a, XiangQingActivity.class);
        ZhongLeiBean bean=new ZhongLeiBean();
        bean.setCname(dataBean.getType().getCname());
        bean.setEname(dataBean.getType().getEname());
        intent.putExtra("bean",bean);
        startActivity(intent);

    }

    @Override
    public void onItemClick(ZhongLeiItems.DataBean.ItemsBean itemsBean) {
        toast(itemsBean.getName());
    }
}
