package com.ycsxt.admin.xiongmaotv.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ycsxt.admin.xiongmaotv.R;
import com.ycsxt.admin.xiongmaotv.domain.QuanBuZhiBoItems;
import com.ycsxt.admin.xiongmaotv.domain.ZhongLeiItems;

import java.util.List;

/**
 * Created by admin on 2017/2/10.
 */

public class QuanBuItemAdapter extends SimpleRecyclerAdapter<QuanBuZhiBoItems.DataBean.ItemsBean> {


    public QuanBuItemAdapter(List<QuanBuZhiBoItems.DataBean.ItemsBean> list, Context ctx) {
        super(list, ctx);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(View.inflate(ctx, R.layout.item_zhonglei, null));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final MyHolder h = (MyHolder) holder;
        QuanBuZhiBoItems.DataBean.ItemsBean itemsBean = list.get(position);
        h.nameTv.setText(itemsBean.getName());
        h.nikeNameTv.setText(itemsBean.getUserinfo().getNickName());
        h.guankanTv.setText(itemsBean.getPerson_num());
        final String img = itemsBean.getPictures().getImg();
//        img.substring(0,im)
        Glide.with(ctx).load(img).placeholder(R.drawable.bg).into(h.iv);
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView nameTv;
        TextView nikeNameTv;
        TextView guankanTv;
        ImageView iv;

        public MyHolder(View itemView) {
            super(itemView);
            nameTv = (TextView) itemView.findViewById(R.id.nameTv);
            nikeNameTv = (TextView) itemView.findViewById(R.id.nickNameTv);
            guankanTv = (TextView) itemView.findViewById(R.id.guankanTv);
            iv = (ImageView) itemView.findViewById(R.id.iv);
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, dp2px(130));
            params.rightMargin = dp2px(5);
            params.bottomMargin = dp2px(5);
            itemView.setLayoutParams(params);
        }
    }
}
