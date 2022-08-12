package com.cn.anim.ui.presenter;

import android.content.Context;

import com.cn.anim.ui.contract.Test1Constract;
import com.cn.base.activity.BaseMvpPresenter;

public class Test1Presenter extends BaseMvpPresenter<Test1Constract.View> implements Test1Constract.Presenter{
    public Test1Presenter(Test1Constract.View mView, Context mContext) {
        super(mView, mContext);
    }
}
