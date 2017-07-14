package com.ycsxt.admin.xiongmaotv.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ycsxt.admin.xiongmaotv.R;
import com.ycsxt.admin.xiongmaotv.domain.ZhongLeiBean;

import java.util.List;

/**
 * Created by admin on 2017/2/10.
 */

public class ZhongLeiLocalAdapter extends SimpleRecyclerAdapter<ZhongLeiBean> {

    private int type;
    public static final int TYPELOCAL = 1;
    public static final int TYPENET = 2;

    public ZhongLeiLocalAdapter(List<ZhongLeiBean> list, Context ctx, int type) {
        super(list, ctx);
        this.type = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (type == TYPELOCAL) {
            return new MyHolder(View.inflate(ctx, R.layout.item_zhonglei_local, null));
        } else
            return new MyHolder(View.inflate(ctx, R.layout.item_zhonglei_net, null));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder h = (MyHolder) holder;
        ZhongLeiBean zhongLeiBean = list.get(position);
        h.tv.setText(zhongLeiBean.getCname());
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public MyHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView;
            RecyclerView.LayoutParams params=new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                    dp2px(30));
            params.rightMargin=dp2px(10);
            params.bottomMargin=dp2px(10);
            params.topMargin=dp2px(10);
            params.leftMargin=dp2px(10);
            itemView.setLayoutParams(params);

        }
    }
}
