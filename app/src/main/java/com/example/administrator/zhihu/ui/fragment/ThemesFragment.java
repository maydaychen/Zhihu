package com.example.administrator.zhihu.ui.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.zhihu.R;
import com.example.administrator.zhihu.adapter.ThemeAdapter;
import com.example.administrator.zhihu.bean.ThemeBean;
import com.example.administrator.zhihu.http.HttpMethods;
import com.example.administrator.zhihu.ui.activity.ThemeContentActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.maydaychen.mylibrary.net.ProgressSubscriber;
import io.github.maydaychen.mylibrary.net.SubscriberOnNextListener;

/**
 * 作者：Administrator on 2016/7/15 16:29
 * 邮箱：2091320109@qq.com
 */
public class ThemesFragment extends Fragment {
    @BindView(R.id.rv_themes)
    RecyclerView rvThemes;
    private List<ThemeBean.OthersBean> theme_list = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    ThemeAdapter themeAdapter;
    private SubscriberOnNextListener<ThemeBean> getTopMovieOnNext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTopMovieOnNext = themeBean -> {
            List<Map<String, Object>> list = new ArrayList<>();
            for(ThemeBean.OthersBean othersBeen:themeBean.getOthers()){
                theme_list.add(othersBeen);
                Map<String, Object> map = new HashMap<>();
                map.put("thumbnail", othersBeen.getThumbnail());
                map.put("name", othersBeen.getName());
                map.put("description", othersBeen.getDescription());
                list.add(map);
            }
            //创建并设置Adapter
            themeAdapter = new ThemeAdapter(list);
            rvThemes.setAdapter(themeAdapter);
            themeAdapter.setOnItemClickListener((view, data) -> {
                Intent intent = new Intent(getActivity(), ThemeContentActivity.class);
                intent.putExtra("id", theme_list.get(data).getId());
                intent.putExtra("name", theme_list.get(data).getName());
                startActivity(intent);
            });
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_themes, container, false);
        ButterKnife.bind(this, root);
        initView();
        initData();
        return root;
    }

    public void initView() {
        layoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
        rvThemes.setLayoutManager(layoutManager);
    }

    public void initData() {
        HttpMethods.getInstance().getString(
                new ProgressSubscriber<>(getTopMovieOnNext, getActivity()));
    }
}
