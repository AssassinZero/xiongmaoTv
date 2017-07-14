package com.ycsxt.admin.xiongmaotv.service;

import com.ycsxt.admin.xiongmaotv.domain.JingCaiTuiJianItems;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by admin on 2017/2/14.
 */

public interface JingCaiItemsService {
    @GET("ajax_get_live_list_by_multicate?pagenum=4&hotroom=1&sproom=1&__version=2.1.9.1720&__plat=android")
    Call<JingCaiTuiJianItems> getItems();
}
