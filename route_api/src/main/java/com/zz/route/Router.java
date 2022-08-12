package com.zz.route;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Router {
    private static Map<String, Class<? extends Activity>> routers = new HashMap<>();

    private Router() {
    }

    public static Router getInstance() {
        return SingerHolder.instance;
    }

    private static class SingerHolder {
        private static final Router instance = new Router();
    }

    /**
     * 初始化
     */
    public static void init(Application application) {
        try {
        Set<String> classNames = ClassUtils.getFileNameByPackageName(application,"com.zxbear.routers");
            for (String name : classNames) {
                //1、获取类加载器
                Class<?> aClass = Class.forName(name);
                //强转对应的类
                IRoteLoad iRoteLoad = (IRoteLoad) aClass.newInstance();
                iRoteLoad.loadInto(routers);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void regist(String path, Class<? extends Activity> cls) {
//        routers.put(path, cls);
//    }

    public void starActivity(Activity context, String path) {
        Class<? extends Activity> cls = routers.get(path);
        if (cls != null) {
            Intent intent = new Intent(context, cls);
            context.startActivity(intent);
        }
    }
}
