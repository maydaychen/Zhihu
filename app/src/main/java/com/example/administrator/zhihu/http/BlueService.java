package com.example.administrator.zhihu.http;

import com.example.administrator.zhihu.bean.CommentBean;
import com.example.administrator.zhihu.bean.ThemeBean;

import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 作者：JTR on 2016/11/24 14:15
 * 邮箱：2091320109@qq.com
 */
public interface BlueService {
    @GET("themes")
    rx.Observable<ThemeBean> getString();

    @GET("story-extra/{id}")
    rx.Observable<CommentBean> getComment(@Path("id") String id);
}