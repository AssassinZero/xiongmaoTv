package com.ycsxt.admin.xiongmaotv.view;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by admin on 2017/2/7.
 */

public class SwipBackLayout extends FrameLayout {
    private OnSwipBackToggleListener swipBackToggleListener;
    private GestureDetector detector;
    private int scaledTouchSlop;
    private int scaledMinimumFlingVelocity;
    private boolean isActivity = true;
    private ViewGroup rootView;

    public SwipBackLayout(Context context) {
        super(context);
        init();
    }

    private void init() {
        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        scaledTouchSlop = configuration.getScaledTouchSlop();
        scaledMinimumFlingVelocity = configuration.getScaledMinimumFlingVelocity();
        Log.e("tag", scaledTouchSlop + "");
        detector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                // e1 按下的事件  e2 是移动的事件

                float minX = e2.getRawX() - e1.getRawX();
                float minY = e2.getRawY() - e1.getRawY();
                if ((minX > scaledTouchSlop && minX/2 > minY)) {
                    rootView.setTranslationX(minX);
                    return true;
                }

                return super.onScroll(e1, e2, distanceX, distanceY);
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (velocityX > velocityY && velocityX > rootView.getWidth()) {
                    Log.e("tag", scaledMinimumFlingVelocity + "");
                    relaseClose(rootView.getTranslationX());
                    return true;
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
        //TODO 长按事件 处理
        // 禁止长按事件,如果未true，将会调用onLongPress方法，为false将会调用onScroll方法
        detector.setIsLongpressEnabled(false);


    }


    public SwipBackLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SwipBackLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void startAni() {
        post(new Runnable() {
            @Override
            public void run() {
                if (isActivity) {
                    rootView = (ViewGroup) getRootView();
                } else {
                    rootView = SwipBackLayout.this;
                }
                rootView.setTranslationX(rootView.getWidth());
                // 通过设置动画的延迟时间  解决卡顿
                rootView.animate().setStartDelay(50).translationX(0).setDuration(500).setListener(new MySimpleAnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        if (swipBackToggleListener != null) {
                            swipBackToggleListener.onOpen();
                        }
                    }
                }).start();
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // post 发送到handler中，什么时候调用，当view的大小确定之后调用
        startAni();


    }

    public void close() {

        rootView.animate().translationX(rootView.getWidth()).setDuration(500).setListener(new MySimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (swipBackToggleListener != null) {
                    swipBackToggleListener.onClose();
                }
            }
        }).start();
    }



    public interface OnSwipBackToggleListener {
        void onOpen();

        void onClose();
    }

    public void setOnSwipBackToggleListener(OnSwipBackToggleListener listener) {
        this.swipBackToggleListener = listener;
    }

    class MySimpleAnimatorListener implements Animator.AnimatorListener {

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 拦截水平滑动事件
        // 拦截水平滑动事件
        boolean b = detector.onTouchEvent(ev);
        if (b)
            return b;
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 修改rootview的translationX

        boolean b = detector.onTouchEvent(event);
        // 如果没有处理fling事件 我处理松手事件
        if (!b)
            handleUp(event);
        return true;
    }

    private void handleUp(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            float translationX = rootView.getTranslationX();
            if (translationX >= 0 && translationX <= rootView.getWidth() / 3) {
                relaseOpen(translationX);
            } else {
                relaseClose(translationX);
            }
        }
    }

    private void relaseClose(float translationX) {
        rootView.animate().translationX(rootView.getWidth()).setDuration((long) (1.0f * (rootView.getWidth() - translationX) / rootView.getWidth() * 500))
                .setListener(new MySimpleAnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (swipBackToggleListener != null)
                            swipBackToggleListener.onClose();

                    }
                })
                .start();
    }

    private void relaseOpen(float translationX) {
        rootView.animate().translationX(0).setDuration((long) (1.0f * translationX / rootView.getWidth() * 500)).setListener(new MySimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (swipBackToggleListener != null) {
                    swipBackToggleListener.onOpen();
                }
            }
        }).start();
    }

    public void isActivity(boolean b) {
        this.isActivity = b;
    }

}
