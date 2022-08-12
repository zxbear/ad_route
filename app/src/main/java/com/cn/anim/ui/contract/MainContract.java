package com.cn.anim.ui.contract;


import com.cn.anim.entity.FunctionMenu;
import com.cn.base.activity.BaseMvpView;

import java.util.List;


public interface MainContract {

    interface View extends BaseMvpView {

        void initMenuList(List<FunctionMenu> list);
    }

    interface Presenter {
        void getMenuList();
    }
}
