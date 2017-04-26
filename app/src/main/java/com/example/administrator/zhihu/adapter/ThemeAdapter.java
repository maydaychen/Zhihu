package com.example.administrator.zhihu.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.zhihu.R;
import com.loopj.android.image.SmartImageView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/15.
 */
public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.ViewHolder> implements View.OnClickListener {
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<Map<String, Object>> mData;

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int data);
    }

    public ThemeAdapter(List<Map<String, Object>> mData) {
        this.mData = mData;
    }



    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_themes, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.mTextView.setText((String) mData.get(position).get("name"));
        viewHolder.description.setText((String) mData.get(position).get("description"));
        viewHolder.imageView.setImageUrl((String) mData.get(position).get("thumbnail"));
        viewHolder.itemView.setTag(position);
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        int num = mData.size();
        return num;
    }



    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (Integer) v.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public TextView description;
        public SmartImageView imageView;

        public ViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.tv_theme_item_theme);
            description = (TextView) view.findViewById(R.id.tv_theme_item_content);
            imageView = (SmartImageView) view.findViewById(R.id.iv_theme_item);
        }
    }
}
