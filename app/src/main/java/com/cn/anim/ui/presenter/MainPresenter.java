package com.cn.anim.ui.presenter;

import android.content.Context;

import com.cn.anim.entity.FunctionMenu;
import com.cn.anim.ui.contract.MainContract;
import com.cn.base.activity.BaseMvpPresenter;

import java.util.ArrayList;
import java.util.List;


public class MainPresenter extends BaseMvpPresenter<MainContract.View> implements MainContract.Presenter {
    private List<FunctionMenu> datas;

    public MainPresenter(MainContract.View mView, Context mContext) {
        super(mView, mContext);
        datas = new ArrayList<>();
    }

    @Override
    public void getMenuList() {
        datas.add(new FunctionMenu("跳转-test1module", null));
        mView.initMenuList(datas);
    }
}
