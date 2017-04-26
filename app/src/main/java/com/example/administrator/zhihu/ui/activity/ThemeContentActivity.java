package com.example.administrator.zhihu.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.zhihu.R;
import com.example.administrator.zhihu.adapter.HotAdapter;
import com.example.administrator.zhihu.bean.StoryBean;
import com.example.administrator.zhihu.utils;
import com.github.rubensousa.floatingtoolbar.FloatingToolbar;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ThemeContentActivity extends AppCompatActivity implements FloatingToolbar.ItemClickListener {

    @BindView(R.id.rv_story_bean)
    RecyclerView rvStoryBean;
    private FloatingToolbar mFloatingToolbar;
    private Toolbar mToolbar;
    String url = "http://news-at.zhihu.com/api/4/theme/";
    OkHttpClient okHttpClient;
    CacheControl my_cache;
    private Gson mGson = new Gson();
    RecyclerView.LayoutManager layoutManager;
    HotAdapter hotAdapter;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String data = msg.obj.toString();
            switch (msg.what) {
                case 0:
                    try {
                        StoryBean storyBean = mGson.fromJson(data, StoryBean.class);
                        List<Map<String, Object>> list = new ArrayList<>();
                        for (int j = 0; j < storyBean.getStories().size(); j++) {
                            Map<String, Object> map = new HashMap<>();
                            if (null != storyBean.getStories().get(j).getImages()) {
                                map.put("images", storyBean.getStories().get(j).getImages().get(0));
                            }
                            map.put("title", storyBean.getStories().get(j).getTitle());
                            list.add(map);
                        }
                        //创建并设置Adapter
                        hotAdapter = new HotAdapter(list);
                        rvStoryBean.setAdapter(hotAdapter);
                        hotAdapter.setOnItemClickListener((view, data1) -> {
                            Intent intent = new Intent(ThemeContentActivity.this, ContentActivity.class);
                            intent.putExtra("ID", storyBean.getStories().get(data1).getId());
                            startActivity(intent);
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

                case 1:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_content);
        ButterKnife.bind(this);
        url += getIntent().getIntExtra("id", 11);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("name"));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_white_24dp);
        toolbar.setNavigationOnClickListener(v -> finish());

        layoutManager = new LinearLayoutManager(ThemeContentActivity.this);
        rvStoryBean.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvStoryBean.setHasFixedSize(true);

        mFloatingToolbar = (FloatingToolbar) findViewById(R.id.floatingToolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        mFloatingToolbar.setClickListener(this);
        mFloatingToolbar.attachFab(fab);

        final View customView = mFloatingToolbar.getCustomView();
        if (customView != null) {
        }

        mFloatingToolbar.setClickListener(new FloatingToolbar.ItemClickListener() {
            @Override
            public void onItemClick(MenuItem item) {

                int id = item.getItemId();
                switch (id) {
                    case R.id.action_unread:
                        Toast.makeText(ThemeContentActivity.this, "hihihihi", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onItemLongClick(MenuItem item) {
            }
        });

        CacheControl.Builder builder =
                new CacheControl.Builder().
                        maxAge(6, TimeUnit.SECONDS).//这个是控制缓存的最大生命时间
                        maxStale(1, TimeUnit.SECONDS);//这个是控制缓存的过时时间
        my_cache = builder.build();
        initRequest();
    }

    @Override
    public void onItemClick(MenuItem menuItem) {
    }

    @Override
    public void onItemLongClick(MenuItem menuItem) {
    }

    Interceptor interceptor = chain -> {
        Request request = chain.request();

        if (utils.isNetworkAvailable(ThemeContentActivity.this)) {
            Response response = chain.proceed(request);
            int maxAge = 6; // 在线缓存在1分钟内可读取
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
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

    private void initRequest() {
        File cacheDirectory = new File(getExternalCacheDir(), "0810");
        Cache cache = new Cache(cacheDirectory, 10 * 1024 * 1024);

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
                if (response.isSuccessful()) {
                    handler.sendMessage(handler.obtainMessage(0, data));
                } else {
                    handler.sendMessage(handler.obtainMessage(0, "数据请求失败"));
                }
            }
        });

    }
}
