package com.gm.demoset;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016/7/14.
 */
public interface MovieService {
    //    @GET("top250")
//    Call<MovieEntity> getMovie(@Query("start") int start,@Query("count") int count);
    @GET("top250")
    rx.Observable<HttpResult<List<Subject>>> getMovie(@Query("start") int start, @Query("count") int count);

}
