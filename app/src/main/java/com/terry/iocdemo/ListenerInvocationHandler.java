package com.terry.iocdemo;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 这个类用来代理，new View.OnClickListener()对象；
 *  并执行这个对象的onClick方法
 */


public class ListenerInvocationHandler implements InvocationHandler {

    //需要在onClick中执行Activity.click();
    private  Object activity;
    private Method activityMethod;

    public ListenerInvocationHandler(Object activity, Method activityMethod) {
        this.activity = activity;
        this.activityMethod = activityMethod;
    }



    /**
     *  表示onClick的执行；
     *  程序执行onClick方法时，就会转到这里；
     *  框架中不直接执行onClick;
     */

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
       //此处调用被注解的click();
        return activityMethod.invoke(activity,args);
    }


}
