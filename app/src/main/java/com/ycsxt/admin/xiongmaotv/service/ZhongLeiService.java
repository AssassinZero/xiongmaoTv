package com.ycsxt.admin.xiongmaotv.service;

import com.ycsxt.admin.xiongmaotv.domain.QuanBuZhiBoItems;
import com.ycsxt.admin.xiongmaotv.domain.ZhongLeiItems;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by admin on 2017/2/10.
 */

public interface ZhongLeiService {
    @GET("ajax_get_live_list_by_cate?pagenum=20&sproom=1&__version=2.1.9.1720&__plat=android&banner=1&slider=1")
    Call<ZhongLeiItems> getZhongLeiItems(@Query("cate") String cate, @Query("pageno") String pageno);

    @GET("ajax_live_lists?pagenum=20&status=2&order=person_num&sproom=1&__version=2.1.9.1720&__plat=android&banner=1")
    Call<QuanBuZhiBoItems> getQuanbuItems(@Query("pageno") String pageno);
}
