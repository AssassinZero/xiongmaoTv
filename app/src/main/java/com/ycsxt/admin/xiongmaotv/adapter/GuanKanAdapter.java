package com.ycsxt.admin.xiongmaotv.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by admin on 2017/2/17.
 */

public class GuanKanAdapter extends SimpleRecyclerAdapter<Integer> {
    public GuanKanAdapter(List<Integer> list, Context ctx) {
        super(list, ctx);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerView.ViewHolder(new CircleImageView(ctx)) {
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CircleImageView itemView = (CircleImageView) holder.itemView;
        RecyclerView.LayoutParams params=new RecyclerView.LayoutParams(dp2px(30),dp2px(30));
        params.rightMargin=dp2px(5);
        itemView.setLayoutParams(params);
        itemView.setImageResource(list.get(position));
    }
}
