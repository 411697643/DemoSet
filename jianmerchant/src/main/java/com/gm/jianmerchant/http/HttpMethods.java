package com.gm.jianmerchant.http;

import com.gm.jianmerchant.bean.HttpResult;
import com.gm.jianmerchant.bean.Subject;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/7/16.
 */
public class HttpMethods {
    private static HttpMethods ourInstance = null;
    private final HttpMethodsInterface methodsInterface;

//    public static HttpMethods getInstance() {
//        if (ourInstance == null) {
//            synchronized (HttpMethods.class) {
//                if (ourInstance == null) {
//                    ourInstance = new HttpMethods();
//                }
//            }
//        }
//        return ourInstance;
//    }
    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance(){
        return SingletonHolder.INSTANCE;
    }

    private HttpMethods() {
        OkHttpClient.Builder ok = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS);
        Retrofit.Builder builder = new Retrofit.Builder();
        Retrofit retrofit = builder.baseUrl(HttpConstants.DOU_BAN)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(ok.build())
                .build();

        methodsInterface = retrofit.create(HttpMethodsInterface.class);
    }

    private class HttpResultFun<T> implements Func1<HttpResult<T>,T>{

        @Override
        public T call(HttpResult<T> httpResult) {
            if (Integer.valueOf(httpResult.getCount())<1) {
                    throw new APIExecption(Integer.valueOf(httpResult.getCount()));

            }

            return httpResult.getSubjects();
        }
    }

    public void getTopMovie(Subscriber<List<Subject>> subscriber, int start, int count) {
        rx.Observable<HttpResult<List<Subject>>> topMovie = methodsInterface.getTopMovie(start, count);
        topMovie.map(new HttpResultFun<List<Subject>>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }


    private class APIExecption extends RuntimeException {
        public APIExecption(Integer count) {
            this(getAPIExecptionMessage(count));
        }

        public APIExecption(String message) {
            super(message);
        }
    }

    private static String getAPIExecptionMessage(Integer count) {
        String message ="";
        if (count < 1) {
            message = "没有获取到电影信息";
        }
        return message;
    }


}
