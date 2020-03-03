package com.terry.iocdemo;


import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author terry
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(listenerSetter = "setOnLongClickListener"
        ,listenerType = View.OnLongClickListener.class
        ,callbackMethod = "OnLongClick")
public @interface OnLongClick {
    int[] value() default  -1;

}
