package com.ycsxt.admin.xiongmaotv.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ycsxt.admin.xiongmaotv.R;
import com.ycsxt.admin.xiongmaotv.domain.ZhongLeiBean;

import java.util.List;

/**
 * Created by admin on 2017/2/13.
 */

public class YouXiAdapter extends SimpleRecyclerAdapter<ZhongLeiBean> {
    public YouXiAdapter(List<ZhongLeiBean> list, Context ctx) {
        super(list, ctx);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(View.inflate(ctx, R.layout.item_youxi, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder h = (MyHolder) holder;
        ZhongLeiBean zhongLeiBean = list.get(position);
        h.tv.setText(zhongLeiBean.getCname());
        Glide.with(ctx).load(zhongLeiBean.getImg()).placeholder(R.drawable.bg).into(h.iv);
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                dp2px(250)
        );
        if((position+1)%3!=0)
        params.rightMargin=dp2px(5);
        if(list.size()-position-1>=2)
        params.bottomMargin=dp2px(5);
       h.itemView.setLayoutParams(params);
    }

    class MyHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv;

        public MyHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
            iv = (ImageView) itemView.findViewById(R.id.iv);

        }
    }
}
