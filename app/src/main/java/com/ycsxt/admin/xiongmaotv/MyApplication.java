package com.ycsxt.admin.xiongmaotv;

import android.app.Application;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.ycsxt.admin.xiongmaotv.domain.Model;
import com.ycsxt.admin.xiongmaotv.domain.ZhongLeiBean;
import com.ycsxt.admin.xiongmaotv.utils.LogUtils;
import com.ycsxt.admin.xiongmaotv.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 2017/2/9.
 */

public class MyApplication extends Application {
    public static boolean idDebug=true;
    public static MyApplication app;
    public  Retrofit retrofit;
    public  Retrofit xingYanretrofit;
    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
        initLocalZhongLeiData();
        initNetUtils();
    }

    private void initNetUtils() {
        retrofit=new Retrofit.Builder().baseUrl("http://api.m.panda.tv/").addConverterFactory(GsonConverterFactory.create()).build();
        xingYanretrofit=new Retrofit.Builder().baseUrl("http://m.api.xingyan.panda.tv/").addConverterFactory(GsonConverterFactory.create()).build();

    }

    private void initLocalZhongLeiData() {
        String s = SPUtils.get(Model.ZHONGLEIITELS);
        if(TextUtils.isEmpty(s)){
            setdefaultZhongLeiItemsData();
        }
    }

    private void setdefaultZhongLeiItemsData() {
        List<ZhongLeiBean> zhongLeiBeen=new ArrayList<>();
        zhongLeiBeen.add(new ZhongLeiBean("精彩推荐","精彩推荐",null,null,null,null));
        zhongLeiBeen.add(new ZhongLeiBean("全部直播","全部直播",null,null,null,null));
        zhongLeiBeen.add(new ZhongLeiBean("英雄联盟","lol",null,null,null,null));
        zhongLeiBeen.add(new ZhongLeiBean("守望先锋","overwatch",null,null,null,null));
        zhongLeiBeen.add(new ZhongLeiBean("炉石传说","hearthstone",null,null,null,null));
        // gson是专门解析json的，可以见javabean转换成json字符串，也可以将json字符串转换成javabean
        String s = new Gson().toJson(zhongLeiBeen);
        SPUtils.put(Model.ZHONGLEIITELS,s);
    }
}
