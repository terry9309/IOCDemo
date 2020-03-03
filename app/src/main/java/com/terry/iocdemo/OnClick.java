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
@EventBase(listenerSetter = "setOnClickListener"
           ,listenerType = View.OnClickListener.class
           ,callbackMethod = "OnClick")


public @interface OnClick {

    int[] value() default  -1; //此处表示赋值-1；

}
