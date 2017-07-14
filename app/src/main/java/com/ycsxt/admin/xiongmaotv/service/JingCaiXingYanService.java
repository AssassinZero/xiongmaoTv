package com.ycsxt.admin.xiongmaotv.service;

import com.ycsxt.admin.xiongmaotv.domain.JingCaiXingYanItems;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by admin on 2017/2/14.
 */

public interface JingCaiXingYanService {
    @GET("room/index?_version=2.1.9.1720&__plat=android&__channel=guanwang")
    Call<JingCaiXingYanItems> getJingCaiItems();
}
