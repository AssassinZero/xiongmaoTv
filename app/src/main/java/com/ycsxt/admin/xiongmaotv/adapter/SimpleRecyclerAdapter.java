package com.ycsxt.admin.xiongmaotv.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ycsxt.admin.xiongmaotv.utils.DimenUtils;

import java.util.List;

/**
 * Created by admin on 2017/2/10.
 */

public abstract class  SimpleRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
   protected List<T> list;
    protected Context ctx;
    public SimpleRecyclerAdapter(List<T> list,Context ctx) {
        this.list = list;
        this.ctx=ctx;
    }

    @Override
    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) ;

    @Override
    public  abstract void onBindViewHolder(RecyclerView.ViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return list.size();
    }
    public int dp2px(int dp){
       return DimenUtils.dp2px(dp);
    }
}
