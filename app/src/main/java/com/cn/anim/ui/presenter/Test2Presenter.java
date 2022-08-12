package com.cn.anim.ui.presenter;

import android.content.Context;

import com.cn.anim.ui.contract.Test2Constract;
import com.cn.base.activity.BaseMvpPresenter;

public class Test2Presenter extends BaseMvpPresenter<Test2Constract.View> implements Test2Constract.Presenter{
    public Test2Presenter(Test2Constract.View mView, Context mContext) {
        super(mView, mContext);
    }
}
