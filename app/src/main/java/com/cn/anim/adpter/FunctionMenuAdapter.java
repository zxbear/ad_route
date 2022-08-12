package com.cn.anim.adpter;

import android.content.Context;

import com.cn.anim.R;
import com.cn.anim.entity.FunctionMenu;
import com.cn.base.adapter.BaseAdapter;

import java.util.List;

public class FunctionMenuAdapter extends BaseAdapter<FunctionMenu> {
    public FunctionMenuAdapter(Context myContext, List<FunctionMenu> mData) {
        super(myContext, mData);
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_function_menu_layout;
    }

    @Override
    public void onBindMViewHolder(ViewHolder holder, FunctionMenu item, int position) {
        holder.getTextView(R.id.tv_function_name).setText(item.getName());
    }
}
