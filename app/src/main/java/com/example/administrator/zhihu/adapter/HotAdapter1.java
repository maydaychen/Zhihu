package com.example.administrator.zhihu.adapter;

import android.content.Context;

import com.example.administrator.zhihu.R;

import java.util.List;
import java.util.Map;

/**
 * 作者：JTR on 2017/4/26 11:07
 * 邮箱：2091320109@qq.com
 */
public class HotAdapter1 extends BaseQuickAdapter<Map<String, String>, BaseViewHolder> {
//    private List<Map<String, Object>> mData;

    public HotAdapter1(Context context, List<Map<String, String>> list) {
        super(R.layout.item_hot, list, context);
    }

    @Override
    protected void convert(BaseViewHolder helper, Map<String, String> item) {
        helper.setText(R.id.tv_hot_item,item.get("title"))
        .setImageUrl(R.id.iv_hot_item,item.get("images"));

    }
}
