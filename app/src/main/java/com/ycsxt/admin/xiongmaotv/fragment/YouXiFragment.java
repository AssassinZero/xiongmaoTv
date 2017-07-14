package com.ycsxt.admin.xiongmaotv.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ycsxt.admin.xiongmaotv.MyApplication;
import com.ycsxt.admin.xiongmaotv.R;
import com.ycsxt.admin.xiongmaotv.XiangQingActivity;
import com.ycsxt.admin.xiongmaotv.adapter.YouXiAdapter;
import com.ycsxt.admin.xiongmaotv.domain.YouXiBeans;
import com.ycsxt.admin.xiongmaotv.domain.ZhongLeiBean;
import com.ycsxt.admin.xiongmaotv.service.YouXiItemService;
import com.ycsxt.admin.xiongmaotv.view.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 2017/2/9.
 */

public class YouXiFragment extends BaseNavigationFragment implements SwipeRefreshLayout.OnRefreshListener, SuperRecyclerView.OnItemClickListenr {

    private SwipeRefreshLayout swip;
    private SuperRecyclerView recycler;
    List<ZhongLeiBean> list=new ArrayList<>();
    @Override
    protected void setContentView(ViewGroup group) {
        View inflate = View.inflate(a, R.layout.fragemnt_youxi, null);
        swip = (SwipeRefreshLayout) inflate.findViewById(R.id.swip);
        swip.setOnRefreshListener(this);
        recycler = (SuperRecyclerView) inflate.findViewById(R.id.recycler);
        recycler.setLayoutManager(new GridLayoutManager(a, 3));
        group.addView(inflate, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        ));
        recycler.setBackgroundColor(Color.RED);
        recycler.setPadding(dp2px(5),dp2px(5),dp2px(5),dp2px(5));
        recycler.setOnItemClickListener(this);

    }

    @Override
    public void initData() {
        super.initData();
        swip.setRefreshing(true);
        loadData();
    }

    private void loadData() {
        MyApplication.app.retrofit.create(YouXiItemService.class).getZhongLei().enqueue(new Callback<YouXiBeans>() {
            @Override
            public void onResponse(Call<YouXiBeans> call, Response<YouXiBeans> response) {
                List<ZhongLeiBean> data = response.body().getData();
                list.clear();
                list.addAll(data);
                handleContainer(list);
                swip.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<YouXiBeans> call, Throwable t) {
                swip.setRefreshing(false);
                toast("出错了  ");
            }
        });
    }

    private void handleContainer(List<ZhongLeiBean> data) {
        recycler.setAdapter(new YouXiAdapter(data,a));
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View view, int position) {
        Intent intent=new Intent(a, XiangQingActivity.class);
        intent.putExtra("bean",list.get(position));
        startActivity(intent);
    }
}
