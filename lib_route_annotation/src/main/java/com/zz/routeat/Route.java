package com.zz.routeat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)//编译时起效
@Target({ElementType.TYPE})
public @interface Route {
    String value();
}
