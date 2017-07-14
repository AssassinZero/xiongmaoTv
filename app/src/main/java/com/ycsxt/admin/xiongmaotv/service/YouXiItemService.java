package com.ycsxt.admin.xiongmaotv.service;

import com.ycsxt.admin.xiongmaotv.domain.YouXiBeans;
import com.ycsxt.admin.xiongmaotv.domain.ZhongLeiBean;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by admin on 2017/2/10.
 */

public interface YouXiItemService  {
@GET("index.php?method=category.list&type=game&__version=2.1.9.1720&__plat=android")
    Call<YouXiBeans> getZhongLei();
}
