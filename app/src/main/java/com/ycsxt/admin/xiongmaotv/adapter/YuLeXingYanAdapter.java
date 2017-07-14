package com.ycsxt.admin.xiongmaotv.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ycsxt.admin.xiongmaotv.R;
import com.ycsxt.admin.xiongmaotv.domain.YuLeXingYanItems;

import java.util.List;

/**
 * Created by admin on 2017/2/14.
 */

public class YuLeXingYanAdapter extends SimpleRecyclerAdapter<YuLeXingYanItems.DataBean.ItemsBean> {
    private int type = DATUTYPE;

    public YuLeXingYanAdapter(List<YuLeXingYanItems.DataBean.ItemsBean> list, Context ctx) {
        super(list, ctx);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (type == DATUTYPE) {
            DatuHolder datuHolder = new DatuHolder(View.inflate(ctx, R.layout.item_datu_yulexingyan, null));
            return datuHolder;
        } else {
            XiaoTuHodler datuHolder = new XiaoTuHodler(View.inflate(ctx, R.layout.item_xiaotutu_yulexingyan, null));
            return datuHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (type == DATUTYPE)
            handleDatu(holder, position);
        else {
            handleXiaoTu(holder, position);
        }
    }

    private void handleXiaoTu(RecyclerView.ViewHolder holder, int position) {
        XiaoTuHodler h = (XiaoTuHodler) holder;
        YuLeXingYanItems.DataBean.ItemsBean itemsBean = list.get(position);
        h.nikeNameTv.setText(itemsBean.getNickName());
        h.locationTv.setText(itemsBean.getCity());
        Glide.with(ctx).load(itemsBean.getPhoto()).into(h.iv);
    }

    private void handleDatu(RecyclerView.ViewHolder holder, int position) {
        DatuHolder h = (DatuHolder) holder;
        YuLeXingYanItems.DataBean.ItemsBean itemsBean = list.get(position);
        h.nikeNameTv.setText(itemsBean.getNickName());
        h.locationTv.setText(itemsBean.getCity());
        Glide.with(ctx).load(itemsBean.getPhoto()).into(h.iv);
        Glide.with(ctx).load(itemsBean.getAvatar()).into(h.headIv);
    }

    class DatuHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        ImageView headIv;
        TextView nikeNameTv;
        TextView locationTv;

        public DatuHolder(View itemView) {
            super(itemView);
            locationTv = (TextView) itemView.findViewById(R.id.locationTv);
            nikeNameTv = (TextView) itemView.findViewById(R.id.nickNameTv);
            headIv = (ImageView) itemView.findViewById(R.id.headIv);
            iv = (ImageView) itemView.findViewById(R.id.iv);
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, dp2px(500));
            params.leftMargin = dp2px(5);
            params.bottomMargin = dp2px(5);
            itemView.setLayoutParams(params);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return type;
    }

    class XiaoTuHodler extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView nikeNameTv;
        TextView locationTv;

        public XiaoTuHodler(View itemView) {
            super(itemView);
            locationTv = (TextView) itemView.findViewById(R.id.locationTv);
            nikeNameTv = (TextView) itemView.findViewById(R.id.nickNameTv);
            iv = (ImageView) itemView.findViewById(R.id.iv);
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, dp2px(250));
            params.leftMargin = dp2px(5);
            params.bottomMargin = dp2px(5);
            itemView.setLayoutParams(params);
        }
    }

    public void setType(int type) {
        this.type = type;
    }

    public static final int DATUTYPE = 1;
    public static final int XIAOTUTYPE = 2;
}
