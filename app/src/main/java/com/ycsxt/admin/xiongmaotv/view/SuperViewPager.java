package com.ycsxt.admin.xiongmaotv.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.ycsxt.admin.xiongmaotv.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/2/10.
 */

public class SuperViewPager extends ViewPager {
    private OnItemClickListener listener;
    private boolean isDrag;

    public SuperViewPager(Context context) {
        super(context);
        init();
    }

    private boolean isLoop = true;
    public List<View> list = new ArrayList<>();

    public void setLoop(boolean isLoop) {
        this.isLoop = isLoop;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            getAdapter().getCount();
            if (list.size() == 0) return;
            // 刚才那个是反复添加了
            //0 1 2 3 0 1 2 3(错误)
            // 0 1 2 3 4 5 6 7....
            int i = getCurrentItem() + 1;
            if (i >= getAdapter().getCount()) {
                i = 0;
            }
            setCurrentItem(i);
            handler.sendEmptyMessageDelayed(0, 3000);
        }
    };

    @Override
    public void setAdapter(final PagerAdapter adapter) {
        super.setAdapter(adapter);
        handleViews(adapter);
        handlerListener();
    }

    private void handlerListener() {
        for (int i = 0; i < list.size(); i++) {
            final View view = list.get(i);
            final int finalI = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClickListener(SuperViewPager.this, view, finalI);
                    }
                }
            });
        }
    }

    private void handleViews(PagerAdapter adapter) {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            View o = (View) adapter.instantiateItem(null, i);
            if (list.contains(o)) {
                return;
            } else {
                // 导致list一直0.
                list.add(o);
            }

        }
    }


    public SuperViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
//        setOnPageChangeListener();
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == SCROLL_STATE_DRAGGING) {
                    isDrag = true;
                    handler.removeCallbacksAndMessages(null);
                } else if (state == SCROLL_STATE_IDLE && isDrag) {
                    isDrag = false;
                    ;
                    handler.sendEmptyMessageDelayed(0, 3000);
                }
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startLooper();
    }

    private void startLooper() {
        if (list.size() > 0 && isLoop)
            handler.sendEmptyMessageDelayed(0, 3000);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        handler.removeCallbacksAndMessages(null);
    }


    public interface OnItemClickListener {
        void onItemClickListener(ViewPager pager, View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.listener = clickListener;
    }
    public void stopLooper(){
        handler.removeCallbacksAndMessages(null);
    }
    public void startLoop(){
        handler.sendEmptyMessageDelayed(0,3000);
    }
}
