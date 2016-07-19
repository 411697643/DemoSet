package com.gm.jianmerchant.http;

import com.gm.jianmerchant.bean.HttpResult;
import com.gm.jianmerchant.bean.Subject;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016/7/16.
 */
public interface HttpMethodsInterface {
    @GET("top250")
    rx.Observable<HttpResult<List<Subject>>> getTopMovie(@Query("start") int start, @Query("count") int count);
}
