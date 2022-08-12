package com.cn.anim.entity;

import android.app.Activity;

public class FunctionMenu<T extends Class<Activity>> {
    private String name;
    private T active;

    public FunctionMenu(String name, T active) {
        this.name = name;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getActive() {
        return active;
    }

    public void setActive(T active) {
        this.active = active;
    }
}
