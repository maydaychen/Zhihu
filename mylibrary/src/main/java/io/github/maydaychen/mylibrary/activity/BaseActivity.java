package io.github.maydaychen.mylibrary.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import io.github.maydaychen.mylibrary.R;
import io.github.maydaychen.mylibrary.widget.StatusBarCompat;


/**
 * 作者：JTR on 2016/8/31 15:36
 * 邮箱：2091320109@qq.com
 */
public abstract class BaseActivity extends AppCompatActivity {
    public abstract void initView(Bundle savedInstanceState);

    public abstract void initData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.compat(this, ContextCompat.getColor(this, R.color.title_blue));
        getSupportActionBar().hide();
        initView(savedInstanceState);
        initData();
    }
}
