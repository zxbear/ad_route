package com.zz.route;

import android.app.Activity;

import java.util.Map;

public interface IRoteLoad {
    void loadInto(Map<String,Class<? extends Activity>> routers);
}
