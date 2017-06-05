package io.github.maydaychen.mylibrary.net;

/**
 * 作者：JTR on 2016/11/25 14:55
 * 邮箱：2091320109@qq.com
 */
public interface SubscriberOnNextAndErrorListener<T> {
    void onNext(T t);
    void onError(Throwable e);
}