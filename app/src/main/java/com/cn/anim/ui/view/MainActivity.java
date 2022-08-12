package com.cn.anim.ui.view;


import android.view.View;

import com.cn.anim.R;
import com.cn.anim.adpter.FunctionMenuAdapter;
import com.cn.anim.entity.FunctionMenu;
import com.cn.anim.ui.contract.MainContract;
import com.cn.anim.ui.presenter.MainPresenter;
import com.cn.base.activity.BaseMvpActivity;
import com.cn.base.adapter.BaseAdapter;
import com.zz.route.Router;
import com.zz.routeat.Route;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

@Route("com/cn/anim/main")
public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainContract.View, BaseAdapter.onClickmItemLisener {
    private RecyclerView rlv;
    private FunctionMenuAdapter mAdapter;

    @Override
    public int getView() {
        return R.layout.activity_main;
    }

    @Override
    public MainPresenter getPrensent() {
        return new MainPresenter(this, this);
    }

    @Override
    public void setTitleBar() {
        myTitle.setTitle("路由跳转");
        myTitle.setBackEnable(false);
    }

    @Override
    public void initView() {
        rlv = findViewById(R.id.rlv);
        rlv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void initData() {
        mPresenter.getMenuList();
    }

    @Override
    public void initMenuList(List<FunctionMenu> list) {
        if (mAdapter == null) {
            mAdapter = new FunctionMenuAdapter(this, list);
            mAdapter.setOnItemClickLisener(this);
            rlv.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }


    /**
     * 点击功能菜单的监听事件
     *
     * @param v tag == FunctionMenu
     */
    @Override
    public void onClickItem(View v) {
        //openActivity(((FunctionMenu) v.getTag()).getActive());

        Router.getInstance().starActivity(this,"com/cn/test1");
    }
}