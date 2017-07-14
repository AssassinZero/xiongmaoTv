package com.ycsxt.admin.xiongmaotv.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ycsxt.admin.xiongmaotv.MyApplication;
import com.ycsxt.admin.xiongmaotv.adapter.QuanBuItemAdapter;
import com.ycsxt.admin.xiongmaotv.domain.QuanBuZhiBoItems;
import com.ycsxt.admin.xiongmaotv.service.ZhongLeiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 2017/2/13.
 */

public class QuanBuZhiBoFragment extends BaseZhongLeiFragment {
    int page = 1;
    List<QuanBuZhiBoItems.DataBean.ItemsBean> lisr = new ArrayList<>();

    @Override
    protected void initChildView() {
        super.initChildView();
        getRecycler().setAdapter(new QuanBuItemAdapter(lisr, a));
        loadChildData();
    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View view, int position) {
        super.onItemClick(recyclerView, view, position);
        toast(position+"");
    }

    private void loadChildData() {
        if (page == 1) {
            getSwip().setRefreshing(true);
        } else {
            setLoadDataSuccess(true);
        }
        MyApplication.app.retrofit.create(ZhongLeiService.class).getQuanbuItems(page + "").enqueue(new Callback<QuanBuZhiBoItems>() {
            @Override
            public void onResponse(Call<QuanBuZhiBoItems> call, Response<QuanBuZhiBoItems> response) {
                QuanBuZhiBoItems body = response.body();
                if (page == 1) {
                    getSwip().setRefreshing(false);
                }
                handleContent(body);

            }

            @Override
            public void onFailure(Call<QuanBuZhiBoItems> call, Throwable t) {
                toast("加载失败-->" + t.getMessage());
                if (page == 1) {
                    getSwip().setRefreshing(false);
                }
            }
        });
    }

    private void handleContent(QuanBuZhiBoItems body) {
        List<QuanBuZhiBoItems.DataBean.ItemsBean> items = body.getData().getItems();
        if (page == 1) {
            lisr.clear();
        }
        setLoadDataSuccess(false);
        if (items.size() == 0) {
            setLoadDataSuccess(true);
            toast("没有心数据了");
            return;
        }
        page++;
        lisr.addAll(items);
        getRecycler().getAdapter().notifyDataSetChanged();

    }

    @Override
    protected void pullMore() {
        super.pullMore();
        page=1;
        loadChildData();
    }

    @Override
    public void initAdapter() {
        super.initAdapter();
    }

    @Override
    protected void loadFooterData() {
        super.loadFooterData();
        loadChildData();
    }
}
