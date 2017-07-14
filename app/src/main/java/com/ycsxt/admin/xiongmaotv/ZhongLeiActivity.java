package com.ycsxt.admin.xiongmaotv;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ycsxt.admin.xiongmaotv.adapter.SimpleAnimatorListsner;
import com.ycsxt.admin.xiongmaotv.adapter.ZhongLeiLocalAdapter;
import com.ycsxt.admin.xiongmaotv.domain.Model;
import com.ycsxt.admin.xiongmaotv.domain.YouXiBeans;
import com.ycsxt.admin.xiongmaotv.domain.ZhongLeiBean;
import com.ycsxt.admin.xiongmaotv.service.YouXiItemService;
import com.ycsxt.admin.xiongmaotv.utils.SPUtils;
import com.ycsxt.admin.xiongmaotv.view.SuperRecyclerView;
import com.ycsxt.admin.xiongmaotv.view.SwipBackLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ZhongLeiActivity extends BaseActivity implements SwipBackLayout.OnSwipBackToggleListener {

    private SuperRecyclerView dragrecycler;
    private SuperRecyclerView allrecycler;
    private LinearLayout activityzhonglei;
    List<ZhongLeiBean> netList = new ArrayList<>();
    List<ZhongLeiBean> localList = new ArrayList<>();
    private ScrollView continer;
    private ZhongLeiBean jingcai;
    private ZhongLeiBean quanbu;
    private FrameLayout group;
    boolean toTop = false;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private boolean toBottom;
    private SwipBackLayout swipBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhong_lei);

        this.group = (FrameLayout) findViewById(R.id.group);
        this.continer = (ScrollView) findViewById(R.id.continer);
        this.activityzhonglei = (LinearLayout) findViewById(R.id.activity_zhong_lei);
        this.allrecycler = (SuperRecyclerView) findViewById(R.id.allrecycler);
        this.dragrecycler = (SuperRecyclerView) findViewById(R.id.dragrecycler);
        // gone会调用 layout
        //
        swipBackLayout = (SwipBackLayout) findViewById(R.id.swip);
        swipBackLayout.setOnSwipBackToggleListener(this);
        continer.setVisibility(View.INVISIBLE);
        initData();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void initData() {
        String s = SPUtils.get(Model.ZHONGLEIITELS);
        List<ZhongLeiBean> tmp = new Gson().fromJson(s, new TypeToken<List<ZhongLeiBean>>() {
        }.getType());
        localList.addAll(tmp);
        jingcai = localList.remove(0);
        quanbu = localList.remove(0);
        MyApplication.app.retrofit.create(YouXiItemService.class).getZhongLei().enqueue(new Callback<YouXiBeans>() {
            @Override
            public void onResponse(Call<YouXiBeans> call, Response<YouXiBeans> response) {
                List<ZhongLeiBean> data = response.body().getData();
                HashSet<ZhongLeiBean> zhongLeiBeen = new HashSet<>(data);
                zhongLeiBeen.removeAll(localList);
                netList.addAll(zhongLeiBeen);
                handUi();
            }

            @Override
            public void onFailure(Call<YouXiBeans> call, Throwable t) {
                toast("失败");
            }
        });
    }


    private void handUi() {
        dragrecycler.setLayoutManager(new GridLayoutManager(this, 3));
        allrecycler.setLayoutManager(new GridLayoutManager(this, 3));
        dragrecycler.setAdapter(new ZhongLeiLocalAdapter(localList, this, ZhongLeiLocalAdapter.TYPELOCAL));
        allrecycler.setAdapter(new ZhongLeiLocalAdapter(netList, this, ZhongLeiLocalAdapter.TYPENET));
        continer.setVisibility(View.VISIBLE);
        initDrag();
        initListener();
    }

    private void initListener() {
        allrecycler.setOnItemClickListener(new SuperRecyclerView.OnItemClickListenr() {
            @Override
            public void onItemClick(RecyclerView recyclerView, View view, int position) {
                // 这个返回的种类bean
                if (netList.get(position).getEname().equals("") || toTop || toBottom) return;
                if (localList.size() >= 30) {
                    toast("不能再多了");
                    return;
                }
                toTop = true;
                ZhongLeiBean set = netList.set(position, new ZhongLeiBean("", "", "", "", "", ""));
                allrecycler.getAdapter().notifyItemChanged(position);
                Object[] o = new Object[]{set, position};
                view.setTag(o);
                add(view);
            }
        });
        dragrecycler.setOnItemClickListener(new SuperRecyclerView.OnItemClickListenr() {
            @Override
            public void onItemClick(RecyclerView recyclerView, final View view, final int position) {
                if (localList.get(position).getEname().equals("") || toBottom || toTop) return;
                if (localList.size() <= 3) {
                    toast("不能再少了");
                    return;
                }
                toBottom = true;
                addBottomZhanWei(view);
                final ZhongLeiBean set = localList.set(position, new ZhongLeiBean("", "", "", "", "", ""));
                dragrecycler.getAdapter().notifyItemChanged(position);

                allrecycler.post(new Runnable() {
                    @Override
                    public void run() {
                        int[] startLocation = new int[2];
                        view.getLocationOnScreen(startLocation);
                        View iv = getIv(view, startLocation);
                        Object[] o = new Object[]{set, position};
                        iv.setTag(o);
                        iv.setTranslationY(10);
                        group.addView(iv);
                        View lastChild = allrecycler.getChildAt(netList.size() - 1);
                        int[] endLocation = new int[2];
                        lastChild.getLocationOnScreen(endLocation);
                        int endX = endLocation[0] - startLocation[0];
                        int endY = endLocation[1] - startLocation[1];
                        PropertyValuesHolder hx = PropertyValuesHolder.ofFloat("translationX", endX);
                        PropertyValuesHolder hy = PropertyValuesHolder.ofFloat("translationY", endY);
                        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(iv, hx, hy);
                        animator.addListener(new SimpleAnimatorListsner() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                ObjectAnimator a = (ObjectAnimator) animation;
//                                View swrpView = (View) a.getTarget();
                                View warpView = (View) a.getTarget();
                                Object[] tag = (Object[]) warpView.getTag();
                                ZhongLeiBean o = (ZhongLeiBean) tag[0];
                                int p = (int) tag[1];
                                localList.remove(p);
                                dragrecycler.getAdapter().notifyDataSetChanged();
                                netList.set(netList.size() - 1, o);
                                allrecycler.getAdapter().notifyDataSetChanged();
                                group.removeView(warpView);
                                toBottom = false;
                            }
                        });
                        animator.setDuration(500);
                        animator.start();

                    }
                });
            }
        });
    }

    private void addBottomZhanWei(View view) {
        netList.add(new ZhongLeiBean("", "", "", "", "", ""));
        allrecycler.getAdapter().notifyDataSetChanged();
    }

    private void add(View view) {
        addZhanWei(view);
        startDongHua(view);
    }

    private void startDongHua(final View view) {
        dragrecycler.post(new Runnable() {
            @Override
            public void run() {
                int[] startLocation = new int[2];
                view.getLocationOnScreen(startLocation);
                View childAt = dragrecycler.getChildAt(localList.size() - 1);
                int[] endLocation = new int[2];
                childAt.getLocationOnScreen(endLocation);
                int endX = endLocation[0] - startLocation[0];
                int endY = endLocation[1] - startLocation[1];
                // 缓存view的bitmap，可以通过view,getDrawingCache（）获得
                View temp = getIv(view, startLocation);
                group.addView(temp);
                PropertyValuesHolder hx = PropertyValuesHolder.ofFloat("translationX", endX);
                PropertyValuesHolder hy = PropertyValuesHolder.ofFloat("translationY", endY);
                ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(temp, hx, hy);
                animator.addListener(new SimpleAnimatorListsner() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        ObjectAnimator an = (ObjectAnimator) animation;
                        View warpView = (View) an.getTarget();
                        Object[] tag = (Object[]) warpView.getTag();
                        ZhongLeiBean o = (ZhongLeiBean) tag[0];
                        int p = (int) tag[1];
                        netList.remove(p);
                        allrecycler.getAdapter().notifyDataSetChanged();
                        localList.set(localList.size() - 1, o);
                        dragrecycler.getAdapter().notifyDataSetChanged();
                        group.removeView(warpView);
                        toTop = false;
                    }
                });
                animator.setDuration(500);
                animator.start();
            }
        });
    }


    private View getIv(View view, int[] startLocation) {
        view.setDrawingCacheEnabled(true);
        Bitmap drawingCache = view.getDrawingCache();
        ImageView iv = new ImageView(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(view.getWidth(), view.getHeight());
        int[] minLocaltion = new int[2];
        group.getLocationOnScreen(minLocaltion);
        params.leftMargin = startLocation[0] - minLocaltion[0];


        params.topMargin = startLocation[1] - minLocaltion[1];
        iv.setLayoutParams(params);
        iv.setImageBitmap(drawingCache);
        iv.setTag(view.getTag());
        return iv;
    }

    private void addZhanWei(View view) {
        ZhongLeiBean zhongLeiBean = new ZhongLeiBean();
        zhongLeiBean.setCname("");
        localList.add(zhongLeiBean);
        dragrecycler.getAdapter().notifyDataSetChanged();
    }

    private void initDrag() {
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP, 0) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int fromposition = viewHolder.getAdapterPosition();
                int toposition = target.getAdapterPosition();
                dragrecycler.getAdapter().notifyItemMoved(fromposition, toposition);
                Collections.swap(localList, fromposition, toposition);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                    viewHolder.itemView.setScaleX(1.1f);
                    viewHolder.itemView.setScaleY(1.1f);
                }
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setScaleX(1.0f);
                viewHolder.itemView.setScaleY(1.0f);
            }
        });
        helper.attachToRecyclerView(dragrecycler);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("ZhongLei Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    @Override
    public void onOpen() {

    }

    @Override
    public void onClose() {
        localList.add(0, quanbu);
        localList.add(0, jingcai);
        SPUtils.put(Model.ZHONGLEIITELS, new Gson().toJson(localList));
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onBackPressed() {
        swipBackLayout.close();
//        super.onBackPressed();
    }

    @Override
    public void back(View v) {
//        super.back(v);
        swipBackLayout.close();
    }
}
