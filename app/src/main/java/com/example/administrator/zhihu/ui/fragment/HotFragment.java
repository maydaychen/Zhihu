package com.example.administrator.zhihu.ui.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.zhihu.Config;
import com.example.administrator.zhihu.R;
import com.example.administrator.zhihu.adapter.HotAdapter;
import com.example.administrator.zhihu.bean.StoryBean;
import com.example.administrator.zhihu.ui.activity.ContentActivity;
import com.example.administrator.zhihu.utils;
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

public class HotFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.rv_hot)
    RecyclerView rvHot;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView.LayoutManager layoutManager;
    HotAdapter hotAdapter;

    public HotFragment() {
        // Required empty public constructor
    }

    String url = "http://news-at.zhihu.com/api/4/news/latest";
    OkHttpClient okHttpClient;
    CacheControl my_cache;
    private Gson mGson = new Gson();

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String data = msg.obj.toString();
            Log.d(Config.TAG, "handleMessage" + data);
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
                        rvHot.setAdapter(hotAdapter);

                        hotAdapter.setOnItemClickListener((view, data1) -> {
                            Intent intent = new Intent(getActivity(), ContentActivity.class);
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_hot, container, false);
        ButterKnife.bind(this, root);
        initView();

        initRequest();
        return root;
    }

    private void initView() {
        layoutManager = new LinearLayoutManager(getActivity());
        rvHot.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvHot.setHasFixedSize(true);

        rvHot.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //当前RecyclerView显示出来的最后一个的item的position
                int lastPosition = -1;

                //当前状态为停止滑动状态SCROLL_STATE_IDLE时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if (layoutManager instanceof GridLayoutManager) {
                        //通过LayoutManager找到当前显示的最后的item的position
                        lastPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                    } else if (layoutManager instanceof LinearLayoutManager) {
                        lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                        //因为StaggeredGridLayoutManager的特殊性可能导致最后显示的item存在多个，所以这里取到的是一个数组
                        //得到这个数组后再取到数组中position值最大的那个就是最后显示的position值了
                        int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                        ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions);
                        lastPosition = findMax(lastPositions);
                    }

                    //时判断界面显示的最后item的position是否等于itemCount总数-1也就是最后一个item的position
                    //如果相等则说明已经滑动到最后了
                    if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1) {
                        Snackbar.make(getView(), "滑动到底了", Snackbar.LENGTH_LONG)
                                .show();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            }
        });

        CacheControl.Builder builder =
                new CacheControl.Builder().
                        maxAge(6, TimeUnit.SECONDS).//这个是控制缓存的最大生命时间
                        maxStale(1, TimeUnit.SECONDS);//这个是控制缓存的过时时间

        my_cache = builder.build();
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    Interceptor interceptor = chain -> {
        Request request = chain.request();

        if (utils.isNetworkAvailable(getActivity())) {
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
        //设置缓存 /data/data/包名下
//        File cacheDirectory = new File(CacheActivity.this.getCacheDir(), "okthhpqq");
        //设置到sd卡里面
        File cacheDirectory = new File(getActivity().getExternalCacheDir(), "0810");
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
                if (response.isSuccessful()) {
                    handler.sendMessage(handler.obtainMessage(0, data));
                } else {
                    handler.sendMessage(handler.obtainMessage(0, "数据请求失败"));
                }
            }
        });
    }
}
