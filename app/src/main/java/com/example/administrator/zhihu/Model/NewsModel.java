package com.example.administrator.zhihu.Model;

import android.content.Context;

import com.example.administrator.zhihu.http.NewsListener;

/**
 * 作者：JTR on 2016/11/14 11:23
 * 邮箱：2091320109@qq.com
 */
public interface NewsModel {
    void getWeather(Context context,NewsListener newsListener,String url);
}
