package com.example.administrator.zhihu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

/**
 * 作者：JTR on 2017/4/26 10:42
 * 邮箱：2091320109@qq.com
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    private final SparseArray<View> views;
    private final Context context;
    private View convertView;
//    private final LinkedHashSet<Integer> childClickViewIds;
    private BaseQuickAdapter adapter;

    protected BaseViewHolder(Context context, View view) {
        super(view);
        this.context = context;
        this.views = new SparseArray<>();
        convertView = view;
    }
    protected <T extends View> T retrieveView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }
    public BaseViewHolder setText(int viewId, CharSequence value) {
        TextView view = retrieveView(viewId);
        view.setText(value);
        return this;
    }
    public BaseViewHolder setImageUrl(int viewId, String imageUrl) {
        SmartImageView view = retrieveView(viewId);
        view.setImageUrl(imageUrl);
//        Picasso.with(context).load(imageUrl).into(view);
        return this;
    }
    public BaseViewHolder setVisible(int viewId, boolean visible) {
        View view = retrieveView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }
    public BaseViewHolder linkify(int viewId) {
        TextView view = retrieveView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    @Deprecated
    public BaseViewHolder setOnItemClickListener(int viewId, AdapterView.OnItemClickListener listener) {
        AdapterView view = getView(viewId);
        view.setOnItemClickListener(listener);
        return this;
    }

//    public BaseViewHolder addOnClickListener(final int viewId) {
//        childClickViewIds.add(viewId);
//        final View view = getView(viewId);
//        if (view != null) {
//            if (!view.isClickable()) {
//                view.setClickable(true);
//            }
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (adapter.getOnItemChildClickListener() != null) {
//                        adapter.getOnItemChildClickListener().onItemChildClick(adapter, v, getClickPosition());
//                    }
//                }
//            });
//        }
//
//        return this;
//    }

    //此处省略若干常用赋值常用方法
}