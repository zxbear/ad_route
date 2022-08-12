package com.cn.anim.ui.view;


import android.view.View;

import com.cn.anim.R;
import com.cn.anim.ui.contract.Test1Constract;
import com.cn.anim.ui.presenter.Test1Presenter;
import com.cn.base.activity.BaseMvpActivity;
import com.zz.route.Router;
import com.zz.routeat.Route;

@Route("com/cn/test1")
public class Test1Activity extends BaseMvpActivity<Test1Presenter> implements Test1Constract.View {
    @Override
    public int getView() {
        return R.layout.activity_test1_layout;
    }

    @Override
    public Test1Presenter getPrensent() {
        return new Test1Presenter(this, this);
    }

    @Override
    public void setTitleBar() {
        myTitle.setTitle("testModule");
    }

    @Override
    public void initView() {
        findViewById(R.id.btn_t1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.getInstance().starActivity(Test1Activity.this,"com/cn/test2");
            }
        });
    }

    @Override
    public void initData() {

    }
}
