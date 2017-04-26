package com.example.administrator.zhihu.http;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.administrator.zhihu.Model.NewsModel;
import com.example.administrator.zhihu.utils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：JTR on 2016/11/14 11:23
 * 邮箱：2091320109@qq.com
 */
public class NewsModelImpl implements NewsModel {
    private final static String TAG = "CacheActivity1";
    OkHttpClient okHttpClient;
    CacheControl my_cache;
//    String url = "http://news-at.zhihu.com/api/4/news/";

    @Override
    public void getWeather(Context context, NewsListener newsListener, String url) {

        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String data = msg.obj.toString();
                Log.d(TAG, "handleMessage" + data);
                switch (msg.what) {
                    case 0:
                        newsListener.onSuccess(data);
                        break;
                    case 1:
                        break;
                }
            }
        };

        CacheControl.Builder builder =
                new CacheControl.Builder().
                        maxAge(6, TimeUnit.SECONDS).//这个是控制缓存的最大生命时间
                        maxStale(1, TimeUnit.SECONDS);//这个是控制缓存的过时时间

        my_cache = builder.build();

        Interceptor interceptor = chain -> {
            Request request = chain.request();

            if (utils.isNetworkAvailable(context)) {
                Response response = chain.proceed(request);
                int maxAge = 6; // 在线缓存在1分钟内可读取
                String cacheControl = request.cacheControl().toString();
                Log.d(TAG, "在线缓存在1分钟内可读取" + cacheControl);
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                Log.d(TAG, "离线时缓存时间设置");
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)//或者直接用系统的
                        .build();

                Response response = chain.proceed(request);
                //下面注释的部分设置也没有效果，因为在上面已经设置了
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=50")
                        .build();
            }
        };

        //设置缓存 /data/data/包名下
//        File cacheDirectory = new File(CacheActivity.this.getCacheDir(), "okthhpqq");
        //设置到sd卡里面
        File cacheDirectory = new File(context.getExternalCacheDir(), "0810");
        Log.i(TAG, "cacheDirectory == " + cacheDirectory.getAbsolutePath());
        Cache cache = new Cache(cacheDirectory, 10 * 1024 * 1024);

//        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)//请求超时时间
                .cache(cache)//设置缓存
                .addInterceptor(interceptor)
                .addNetworkInterceptor(interceptor)
                //.addInterceptor(httpLoggingInterceptor)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .cacheControl(my_cache)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendMessage(handler.obtainMessage(0, "数据请求失败"));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                Log.i(TAG, "data == " + data);
                if (response.isSuccessful()) {
                    handler.sendMessage(handler.obtainMessage(0, data));
                } else {
                    handler.sendMessage(handler.obtainMessage(0, "数据请求失败"));
                }
            }
        });

    }
}
