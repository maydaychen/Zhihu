package io.github.maydaychen.mylibrary.net;

import android.content.Context;
import android.widget.Toast;

import rx.Subscriber;

/**
 * 作者：JTR on 2016/11/25 14:54
 * 邮箱：2091320109@qq.com
 */
public class ProgressErrorSubscriber<T> extends Subscriber<T> {

    private SubscriberOnNextAndErrorListener mSubscriberOnNextAndErrorListener;
    private Context context;

    public ProgressErrorSubscriber(SubscriberOnNextAndErrorListener mSubscriberOnNextAndErrorListener, Context context) {
        this.mSubscriberOnNextAndErrorListener = mSubscriberOnNextAndErrorListener;
        this.context = context;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onCompleted() {
//        Toast.makeText(context, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Throwable e) {
        if (e.getMessage().equals("Failed to connect to /101.231.124.9:56678")) {
            Toast.makeText(context, "网络连接错误，请稍后重试…", Toast.LENGTH_SHORT).show();
        } else if (e.getMessage().equals("failed to connect to /101.231.124.9 (port 56678) after 5000ms")) {
            Toast.makeText(context, "网络连接超时，请稍后重试…", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        mSubscriberOnNextAndErrorListener.onError(e);
    }

    @Override
    public void onNext(T t) {
        mSubscriberOnNextAndErrorListener.onNext(t);
    }
}