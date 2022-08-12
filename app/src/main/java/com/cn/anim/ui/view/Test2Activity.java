package com.cn.anim.ui.view;


import android.view.View;

import com.cn.anim.R;
import com.cn.anim.ui.contract.Test2Constract;
import com.cn.anim.ui.presenter.Test2Presenter;
import com.cn.base.activity.BaseMvpActivity;
import com.zz.route.Router;
import com.zz.routeat.Route;

@Route("com/cn/test2")
public class Test2Activity extends BaseMvpActivity<Test2Presenter> implements Test2Constract.View {
    @Override
    public int getView() {
        return R.layout.activity_test2_layout;
    }

    @Override
    public Test2Presenter getPrensent() {
        return new Test2Presenter(this, this);
    }

    @Override
    public void setTitleBar() {
        myTitle.setTitle("test2Module");
    }

    @Override
    public void initView() {
        findViewById(R.id.btn_t1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.getInstance().starActivity(Test2Activity.this,"com/cn/anim/main");
            }
        });
    }

    @Override
    public void initData() {
    }



}
