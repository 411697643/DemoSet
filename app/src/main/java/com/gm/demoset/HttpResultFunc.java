package com.gm.demoset;

import rx.functions.Func1;

/**
 * Created by Administrator on 2016/7/14.
 */
public class HttpResultFunc<T> implements Func1<HttpResult<T>,T> {
    @Override
    public T call(HttpResult<T> httpResult) {
        if (httpResult.getTitle() == null) {

        }
        return httpResult.getSubjects();
    }

}
