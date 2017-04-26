package com.example.administrator.zhihu;

import android.app.Application;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatDelegate;

/**
 * 作者：JTR on 2017/4/26 15:40
 * 邮箱：2091320109@qq.com
 */
public class ZhihuApplication extends Application {
    private static ZhihuApplication applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        SharedPreferences sp = applicationContext.getSharedPreferences("user_settings", MODE_PRIVATE);

        if(sp.getInt("theme",0)==1)
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

    }


}
