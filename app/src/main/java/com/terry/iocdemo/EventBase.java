package com.terry.iocdemo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author terry
 */

//此处使用ANNOTATION_TYPE，表示该注解在其他注解之上
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {


    //setOnClickListener 订阅关系

    String listenerSetter();

    //new View.OnClickListener() 事件本身
    Class<?> listenerType();

    //3.事件处理程序
    String callbackMethod();
}
