package com.zz.route;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dalvik.system.DexFile;

public class ClassUtils {
    public static Set<String> getFileNameByPackageName(Application context, String packageName) {
        Set<String> classNames = new HashSet<>();
        List<String> paths = getSourcePaths(context);
        for (String path : paths) {
            DexFile dexFile = null;
            try {
                dexFile = new DexFile(path);
                Enumeration<String> dens = dexFile.entries();
                while (dens.hasMoreElements()){
                    String className = dens.nextElement();
                    if (className.startsWith(packageName)){
                        classNames.add(className);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (null!=dexFile){
                    try {
                        dexFile.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return classNames;
    }

    private static List<String> getSourcePaths(Application context) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
            List<String> sourcePaths = new ArrayList<>();
            //当前应用的apk文件
            sourcePaths.add(applicationInfo.sourceDir);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (null != applicationInfo.splitSourceDirs) {
                    sourcePaths.addAll(Arrays.asList(applicationInfo.splitSourceDirs));
                }
            }
            return sourcePaths;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
