package com.ycsxt.admin.xiongmaotv.fragment;

import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ycsxt.admin.xiongmaotv.R;
import com.ycsxt.admin.xiongmaotv.view.SuperRecyclerView;

/**
 * Created by admin on 2017/2/9.
 */

public class BaseZhongLeiFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, SuperRecyclerView.OnItemClickListenr {

    private SwipeRefreshLayout swip;
    private SuperRecyclerView recycler;
    protected  int colume=2;
    private GridLayoutManager gridLayoutManager;
    private boolean loadData;

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        View inflate = inflater.inflate(R.layout.fragment_basezhonglei, container, false);
        swip = (SwipeRefreshLayout) inflate.findViewById(R.id.swip);
        recycler = (SuperRecyclerView) inflate.findViewById(R.id.recycler);
        gridLayoutManager = new GridLayoutManager(a, colume);
        recycler.setLayoutManager(gridLayoutManager);
        recycler.setOnItemClickListener(this);
        // 滚动的时候调用
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //滚动状态改变的时候调用

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                gridLayoutManager.findLastCompletelyVisibleItemPosition();最后一个完整可见的
                if(recyclerView.getAdapter()!=null&&recyclerView.getChildCount()>0){
                    int lastVisibleItemPosition = gridLayoutManager.findLastVisibleItemPosition();
                    if(lastVisibleItemPosition==recyclerView.getAdapter().getItemCount()-1&&!loadData){
                        loadData=true;
                        loadFooterData();
                    }
                }


            }
        });
        swip.setOnRefreshListener(this);
        initChildView();
        return inflate;
    }

    protected void loadFooterData() {

    }

    public SwipeRefreshLayout getSwip(){
        return swip;
    }
    protected void initChildView() {

    }

    public void setColume(int colume){
        this.colume=colume;
    }

    @Override
    public void onRefresh() {
        pullMore();
    }

    protected void pullMore() {

    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View view, int position) {

    }
    public SuperRecyclerView getRecycler(){
        return recycler;
    }
    public void setLoadDataSuccess(boolean success){
        loadData=success;
    }
}
