package com.gm.jianmerchant.bean;

/**
 * Created by Administrator on 2016/7/16.
 */
public class HttpResult<T> {
    private String count;
    private String start;
    private String total;
    private String title;

    private T subjects;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public T getSubjects() {
        return subjects;
    }

    public void setSubjects(T subjects) {
        this.subjects = subjects;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "count='" + count + '\'' +
                ", start='" + start + '\'' +
                ", total='" + total + '\'' +
                ", title='" + title + '\'' +
                ", subjects=" + subjects +
                '}';
    }
}
