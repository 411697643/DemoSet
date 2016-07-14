package com.gm.demoset;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/7/14.
 */
public class HttpMethods {
    private static HttpMethods ourInstance =null;
    public static final String BASE_URL = "https://api.douban.com/v2/movie/";

    private static final int DEFAULT_TIMEOUT = 5;
    private final Retrofit retrofit;
    private final MovieService movieService;

    public static HttpMethods getInstance() {
        if (ourInstance == null) {
            synchronized (HttpMethods.class) {
                if (ourInstance == null) {
                    ourInstance = new HttpMethods();
                }
            }
        }
        return ourInstance;
    }

    private HttpMethods() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder().client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        movieService = retrofit.create(MovieService.class);
    }

    public void getMoview(Subscriber<HttpResult<List<Subject>>> subscriber, int start, int count) {
        movieService.getMovie(start, count)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
