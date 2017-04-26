package com.example.administrator.zhihu.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * 作者：JTR on 2017/4/26 10:43
 * 邮箱：2091320109@qq.com
 */
public abstract class BaseQuickAdapter<T, K extends BaseViewHolder> extends RecyclerView.Adapter<K> {
    protected List<T> mData;
    protected Context mContext;
    protected int mLayoutResId;

    public BaseQuickAdapter(@LayoutRes int layoutResId, @Nullable List<T> data, Context context) {
        this.mData = data == null ? new ArrayList<T>() : data;
        this.mContext = context;
        if (layoutResId != 0) {
            this.mLayoutResId = layoutResId;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public K onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(mLayoutResId, parent, false);
        return (K) new BaseViewHolder(mContext, item);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
        convert(holder, mData.get(position));
    }



    protected abstract void convert(BaseViewHolder helper, T item);
}
