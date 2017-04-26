package com.example.administrator.zhihu.http;

import java.util.List;

/**
 * 作者：JTR on 2016/11/25 10:31
 * 邮箱：2091320109@qq.com
 */
public class HttpResult<T> {
    private String limit;
    private List<?> subscribed;
    private T others;

    public List<?> getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(List<?> subscribed) {
        this.subscribed = subscribed;
    }

    public T getOthers() {

        return others;
    }

    public void setOthers(T others) {
        this.others = others;
    }

    public String getLimit() {

        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }
}
