package com.ycsxt.admin.xiongmaotv.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/2/9.
 */

public class SuperRecyclerView extends RecyclerView {

    private GestureDetector destor;
    private OnItemClickListenr onItemClickListenr;
    private List<View> heads = new ArrayList<>();
    private List<View> footers = new ArrayList<>();

    public SuperRecyclerView(Context context) {
        super(context);
        init();
    }

    public void addHeadView(View headView) {
        check();
        heads.add(headView);
        notifyData();
    }

    private void notifyData() {
        if (getAdapter() != null) {
            getAdapter().notifyDataSetChanged();
        }
    }

    public void addFooterView(View headView) {
        check();
        footers.add(headView);
        notifyData();
    }

    public void removeHeadView(View headView) {
        heads.remove(headView);
        notifyData();
    }

    public void removeFooterView(View headView) {
        footers.remove(headView);
        notifyData();
    }

    @Override
    public void setAdapter(Adapter adapter) {
//        if (heads.size() > 0 || footers.size() > 0) {
        adapter = new WarpAdapter(adapter);
//        }
        super.setAdapter(adapter);
    }

    public int getHeadCount() {
        return heads.size();
    }

    public int getFooterCount() {
        return footers.size();
    }

    private void check() {
//        if (getAdapter() != null) {
//            throw new IllegalStateException("添加头部和尾部一定要在setAdapter之前");
//        }
    }


    private void init() {
        destor = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                handleOnItemClick(e);
                return true;
            }
        });
        post(new Runnable() {
            @Override
            public void run() {
                final LayoutManager layoutManager = getLayoutManager();
                if (layoutManager == null) {
                    setLayoutManager(new LinearLayoutManager(getContext()));
                } else {
                    if (layoutManager instanceof GridLayoutManager) {
                        ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                            @Override
                            public int getSpanSize(int position) {

                                if (getAdapter() != null) {
                                    int itemViewType = getAdapter().getItemViewType(position);
                                    if (itemViewType <= HEAD || itemViewType >= FOOTER)
                                        return ((GridLayoutManager) layoutManager).getSpanCount();
                                }
                                return 1;
                            }
                        });
                    }
                }
            }
        });
    }

    private void handleOnItemClick(MotionEvent e) {
        if (onItemClickListenr != null) {
            float x = e.getX();
            float y = e.getY();
            View childViewUnder = findChildViewUnder(x, y);
            int position = -1;
            if (childViewUnder != null) {
                ViewHolder containingViewHolder = findContainingViewHolder(childViewUnder);
                position = containingViewHolder.getAdapterPosition();
            }
            if (position != -1)
                onItemClickListenr.onItemClick(SuperRecyclerView.this, childViewUnder, position);
        }

    }

    public SuperRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        destor.onTouchEvent(e);

        return super.onTouchEvent(e);
    }

    public SuperRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public interface OnItemClickListenr {
        void onItemClick(RecyclerView recyclerView, View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListenr onItemClickListener) {
        this.onItemClickListenr = onItemClickListener;
    }

    class WarpAdapter extends RecyclerView.Adapter<ViewHolder> {
        private RecyclerView.Adapter rawAdapter;

        public WarpAdapter(Adapter rawAdapter) {
            this.rawAdapter = rawAdapter;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // 根据不同的type，创建不同的recyclerBin。如果type相同的话，就复用同一个recyclerBin
            if (viewType <= HEAD) {
                int position = HEAD - viewType;
                return new ViewHolder(heads.get(position)) {
                };
            }
            if (viewType >= FOOTER) {
                int position = viewType - FOOTER;
                return new ViewHolder(footers.get(position)) {
                };
            }
            return rawAdapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            int itemViewType = getItemViewType(position);
            if (itemViewType > HEAD && itemViewType < FOOTER) {
                rawAdapter.onBindViewHolder(holder, position - heads.size());
            }
        }

        @Override
        public int getItemCount() {
            // 如果没有数据源也可以显示尾巴 下面写法
//            return heads.size()+footers.size()+rawAdapter.getItemCount();
            if (rawAdapter.getItemCount() > 0) {
                return heads.size() + footers.size() + rawAdapter.getItemCount();
            }
            return 0;
        }

        @Override
        public int getItemViewType(int position) {
            if (position < heads.size()) {
                return HEAD - position;
            } else if (position < heads.size() + rawAdapter.getItemCount()) {
                return rawAdapter.getItemViewType(position - heads.size());
            } else {
                return FOOTER + (position - heads.size() - rawAdapter.getItemCount());
            }
        }

    }

  public static  final    int HEAD = -100;
   public static  final int FOOTER = 100;
}
