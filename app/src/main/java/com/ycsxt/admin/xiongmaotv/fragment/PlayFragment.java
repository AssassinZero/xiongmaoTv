package com.ycsxt.admin.xiongmaotv.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.ycsxt.admin.xiongmaotv.R;
import com.ycsxt.admin.xiongmaotv.adapter.GuanKanAdapter;
import com.ycsxt.admin.xiongmaotv.domain.YuLeXingYanItems;
import com.ycsxt.admin.xiongmaotv.view.SuperRecyclerView;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by admin on 2017/2/16.
 */

public class PlayFragment extends BaseFragment implements SurfaceHolder.Callback, IMediaPlayer.OnCompletionListener, IMediaPlayer.OnErrorListener, IMediaPlayer.OnPreparedListener {
    boolean isLoadData;
    private SurfaceView sufaceView;
    private IjkMediaPlayer ijkMediaPlayer;
    private ImageView markIv;
    private YuLeXingYanItems.DataBean.ItemsBean item;
    private SuperRecyclerView guankanRecycler;
    private List<Integer> headlist;
    private TextSwitcher welcomeTv;
    public Bitmap mir;

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        // 使用container的布局参数
        item = (YuLeXingYanItems.DataBean.ItemsBean) getArguments().getSerializable("item");
        root = inflater.inflate(R.layout.fragment_play, container, false);
        sufaceView = (SurfaceView) root.findViewById(R.id.sufaceView);

        markIv = (ImageView) root.findViewById(R.id.markIv);
        CircleImageView headIv = (CircleImageView) root.findViewById(R.id.headIv);
        TextView niknameTv = (TextView) root.findViewById(R.id.nikeName1Tv);
        Glide.with(a).load(item.getAvatar()).into(headIv);
        niknameTv.setText(item.getNickName());
        log(item.getPhoto());
        sufaceView.getHolder().addCallback(PlayFragment.this);
        Glide.with(a).load(item.getPhoto()).asBitmap().error(R.mipmap.welcome).placeholder(R.mipmap.welcome).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                mir = resource;
                log("下载成功");
                restore();
            }
        });

        log(item.getNickName());
        welcomeTv = (TextSwitcher) root.findViewById(R.id.welcomeTv);
        welcomeTv.setInAnimation(a, android.R.anim.fade_in);
        welcomeTv.setOutAnimation(a, android.R.anim.fade_out);
        guankanRecycler = (SuperRecyclerView) root.findViewById(R.id.liebiao);
        guankanRecycler.setLayoutManager(new LinearLayoutManager(a, LinearLayoutManager.HORIZONTAL, false));
        return root;
    }

    public void restore() {
        if(root==null)return;
        root.post(new Runnable() {
            @Override
            public void run() {
                // 绘制
            try {
                Canvas canvas = sufaceView.getHolder().lockCanvas(new Rect(0, 0, root.getWidth(), root.getHeight()));
                Matrix matrix = new Matrix();
                float max = Math.max(root.getWidth() * 1.0f / mir.getWidth(), root.getHeight() * 1.0f / mir.getHeight());
                // 0.1 0.5f
                matrix.setScale(max, max);
                if(canvas==null)return;
                canvas.setMatrix(matrix);
                if (mir == null) {
                    mir = BitmapFactory.decodeResource(getResources(), R.mipmap.welcome);
                }
                canvas.drawBitmap(mir, 0, 0, null);
                // 显示出来，surfaceFliger
                sufaceView.getHolder().unlockCanvasAndPost(canvas);
            }catch (Exception e){

            }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ijkMediaPlayer = new IjkMediaPlayer();
        ijkMediaPlayer.setOnCompletionListener(this);
        ijkMediaPlayer.setOnErrorListener(this);
        ijkMediaPlayer.setOnPreparedListener(this);
        View view = super.onCreateView(inflater, container, savedInstanceState);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (ijkMediaPlayer != null) {
            ijkMediaPlayer.stop();
            ijkMediaPlayer.release();
            ijkMediaPlayer = null;
        }
    }

    @Override
    public void initData() {
        super.initData();
        if (getUserVisibleHint() == true && TextUtils.isEmpty(ijkMediaPlayer.getDataSource())) {
            // 加载数据。 第一页加载没有问题
            YuLeXingYanItems.DataBean.ItemsBean item = (YuLeXingYanItems.DataBean.ItemsBean) getArguments().getSerializable("item");
            startPlay(item.getStreamurl());
        }
        if (getUserVisibleHint() == true && !isLoadData) {
            isLoadData = true;
            lazyData();
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (root != null) {
            if (isVisibleToUser) {
                // 是开播放
                if (!isLoadData) {
                    isLoadData = true;
                    lazyData();
                }
                startPlay(item.getStreamurl());
            } else {
                // 停止播放
                showMark();
                stopPlay();

            }
        }
    }

    int[] heads = new int[]{
            R.mipmap.ftq_numer_0,
            R.mipmap.ftq_numer_1,
            R.mipmap.ftq_numer_2,
            R.mipmap.ftq_numer_3,
            R.mipmap.ftq_numer_4,
            R.mipmap.ftq_numer_5,
            R.mipmap.ftq_numer_6,
            R.mipmap.ftq_numer_7,

    };
    String[] name = new String[]{
            "阳光下的小树",
            "张三",
            "风吹雪花",
            "嫂子是谁",
            "我爱主播",
            "我是老公"
    };

    private void lazyData() {
        headlist = new ArrayList<>();
        headlist.add(R.mipmap.ftq_numer_2);
        headlist.add(R.mipmap.ftq_numer_5);
        initHeadAdapter();
    }

    Handler loopHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (headlist != null) {
                Integer head = heads[(int) (heads.length * Math.random())];
                headlist.add(0, head);
                guankanRecycler.getAdapter().notifyItemInserted(0);
                // 在哪个地方插入
                guankanRecycler.scrollToPosition(0);

                welcomeTv.setText(name[(int) (name.length * Math.random())] + "来了");

            }
            loopHandler.sendEmptyMessageDelayed(0, 1500);
        }
    };

    private void initHeadAdapter() {
        guankanRecycler.setAdapter(new GuanKanAdapter(headlist, a));

    }

    private void stopPlay() {
        if (ijkMediaPlayer != null) {
            ijkMediaPlayer.pause();
            ijkMediaPlayer.stop();
            ijkMediaPlayer.reset();

            loopHandler.removeCallbacksAndMessages(null);
        }
    }

    private void startPlay(String path) {
        showMark();


        ijkMediaPlayer.reset();
        try {
            ijkMediaPlayer.setDataSource(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ijkMediaPlayer.prepareAsync();

        loopHandler.sendEmptyMessageDelayed(0, 1500);
    }

    private void showMark() {
        markIv.setVisibility(View.VISIBLE);

    }

    @Override
    public void initAdapter() {
        super.initAdapter();
    }

    @Override
    public void surfaceCreated(final SurfaceHolder holder) {
        // 将这个bitmap画到bitmap
        if (mir != null) {
            restore();
        }

        if (null != ijkMediaPlayer && !TextUtils.isEmpty(ijkMediaPlayer.getDataSource())) {
            ijkMediaPlayer.start();
        }
        Log.e("tag", item.getNickName() + "----->onCeate");

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        log(item.getNickName()+"onChanged");
    }

    @Override
    public void surfaceDestroyed(final SurfaceHolder holder) {

        if (null != ijkMediaPlayer && ijkMediaPlayer.isPlaying()) {
            ijkMediaPlayer.pause();
            ijkMediaPlayer.stop();
        }
        log(item.getNickName() + "---->onSufaceDestoryed");


    }

    @Override
    public void onCompletion(IMediaPlayer iMediaPlayer) {
        //完成
        toast("完成了");
    }

    @Override
    public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
        // 完成了
        toast("失败了");
        return true;
    }

    @Override
    public void onPrepared(IMediaPlayer iMediaPlayer) {
        SurfaceHolder holder = sufaceView.getHolder();
        iMediaPlayer.setDisplay(holder);
        iMediaPlayer.start();
        hideMark();
    }

    private void hideMark() {
        markIv.setVisibility(View.GONE);
    }
}
