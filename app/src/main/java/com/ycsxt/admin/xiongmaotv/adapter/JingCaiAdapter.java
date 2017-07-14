package com.ycsxt.admin.xiongmaotv.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ycsxt.admin.xiongmaotv.R;
import com.ycsxt.admin.xiongmaotv.domain.JingCaiTuiJianItems;
import com.ycsxt.admin.xiongmaotv.domain.ZhongLeiItems;
import com.ycsxt.admin.xiongmaotv.view.SuperRecyclerView;

import java.util.List;

/**
 * Created by admin on 2017/2/14.
 */

public class JingCaiAdapter extends SimpleRecyclerAdapter<JingCaiTuiJianItems.DataBean> implements View.OnClickListener, SuperRecyclerView.OnItemClickListenr {
    private OnJingCaiItemClickListener listener;

    public JingCaiAdapter(List<JingCaiTuiJianItems.DataBean> list, Context ctx) {
        super(list, ctx);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHodler(View.inflate(ctx, R.layout.item_jingcai, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        JingCaiTuiJianItems.DataBean dataBean = list.get(position);
        MyHodler h = (MyHodler) holder;
        int total = dataBean.getTotal();
        ((MyHodler) holder).moreTv.setVisibility(View.GONE);
        if (total > 4) {
            h.moreTv.setVisibility(View.VISIBLE);
        }
        h.moreTv.setOnClickListener(this);
        h.moreTv.setTag(h.moreTv.getId(), dataBean);
        ((MyHodler) holder).nameTv.setText(dataBean.getType().getCname());
        ((MyHodler) holder).recyclerView.setAdapter(new ZhongLeiItemAdapter(dataBean.getItems(), ctx));
        h.recyclerView.setTag(h.recyclerView.getId(), dataBean.getItems());
        h.recyclerView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onMoreClick((JingCaiTuiJianItems.DataBean) v.getTag(v.getId()));
        }
    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View view, int position) {
        if (listener != null) {
            List<ZhongLeiItems.DataBean.ItemsBean> itemsBeanList = (List<ZhongLeiItems.DataBean.ItemsBean>) recyclerView.getTag(recyclerView.getId());
            listener.onItemClick(itemsBeanList.get(position));
        }
    }

    class MyHodler extends RecyclerView.ViewHolder {
        TextView nameTv;
        TextView moreTv;
        SuperRecyclerView recyclerView;

        public MyHodler(View itemView) {
            super(itemView);
            itemView.setBackgroundColor(Color.WHITE);
            nameTv = (TextView) itemView.findViewById(R.id.nameTv);
            moreTv = (TextView) itemView.findViewById(R.id.moreTv);
            recyclerView = (SuperRecyclerView) itemView.findViewById(R.id.recycler);
            recyclerView.setLayoutManager(new GridLayoutManager(ctx, 2));
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, dp2px(300));
            params.bottomMargin = dp2px(10);
            itemView.setLayoutParams(params);
        }
    }

    public interface OnJingCaiItemClickListener {
        void onMoreClick(JingCaiTuiJianItems.DataBean dataBean);

        void onItemClick(ZhongLeiItems.DataBean.ItemsBean itemsBean);

    }

    public void setOnJingCaiItemClickListener(OnJingCaiItemClickListener listener) {
        this.listener = listener;
    }
}
