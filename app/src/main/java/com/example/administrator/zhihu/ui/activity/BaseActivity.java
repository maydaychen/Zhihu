package com.example.administrator.zhihu.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Weshine on 2017/5/31.
 */

public class BaseActivity extends AppCompatActivity{
    public void initView(Bundle savedInstanceState) {

    }

    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(savedInstanceState);
        initData();
    }
}
