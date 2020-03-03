package com.terry.iocdemo;

import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class InjectUtils {



    public static void inject(Object context){

        //布局的注入
        injectLayout(context);
        //控件的注入
        injectView(context);
        //事件的注入
        injectClick(context);
    }


    //注入布局；
    private static void injectLayout(Object context) {
        int layoutId = 0;
        //通过反射拿到需要注入的Activity;
        Class<?> clazz = context.getClass();
        //在clazz上面去执行setContentView
         ContentView contentView = clazz.getAnnotation(ContentView.class);
         //获取注解括号后面的内容；
         if (contentView != null){
             layoutId= contentView.value();
             //反射去执行setContentView;
             try {
               //获取反射类里的方法；
               Method method = context.getClass().getMethod("setContentView",int.class);
               //调用方法
               method.invoke(context,layoutId);

              }catch (Exception e){
                 e.printStackTrace();
             }

         }
    }



    //控件的注入；
    private static void injectView(Object context) {
        Class<?> clazz = context.getClass();
        //通过注解获取到反射类的所有的字段（此处是Activity中所有的控件）
        Field[] fields= clazz.getDeclaredFields();

        for (Field field : fields) {
            ViewInject viewInject = field.getAnnotation(ViewInject.class);
            if (viewInject != null){
                //获取控件的id
                int valued = viewInject.value();
                //反射执行findViewById方法；
                try {

                    Method method = clazz.getMethod("findViewById",int.class);
                    View view = (View) method.invoke(context,valued);
                    //AccessibleTest类中的成员变量为private,故必须进行此操作

                    field.setAccessible(true);
                    field.set(context,view);

                  } catch (Exception e) {

                     e.printStackTrace();
                }

            }

        }


    }


    private static void injectClick(Object context) {

      //需要一次性注入android的23种事件；
        Class<?> clazz = context.getClass();
       Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
           Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                //annotation 是事件比如onClick 就去取对应的注解；
                Class<?> annotationClass = annotation.annotationType();
                EventBase eventBase = annotationClass.getAnnotation(EventBase.class);
                //如果没有eventBase 则表示当前方法不是一个事件处理的方法
                if (eventBase == null){
                    //不是事件处理方法；
                    continue;
                }
                //开始获取事件处理的相关信息；
                String listenerSetter = eventBase.listenerSetter();
                Class<?> listenerType = eventBase.listenerType();
                //事件处理程序
                String callBackMethod = eventBase.callbackMethod();

                Method valueMethod = null;
                try {
                   //反射得到Id,再根据ID号得到对应的VIEW;
                    valueMethod = annotationClass.getDeclaredMethod("value");
                    int[] viewId = (int[]) valueMethod.invoke(annotation);
                    for (int id : viewId) {
                        //为了得到Button对象；
                        Method findViewById = clazz.getMethod("findViewById",int.class);
                        View view = (View) findViewById.invoke(context,id);

                        if (view == null){
                            continue;
                        }

                        //activity == context; click = method;
                        ListenerInvocationHandler listenerInvocationHandler =
                                new ListenerInvocationHandler(context,method);


                        //建立代理关系；
                        Object proxy = Proxy.newProxyInstance(listenerType.getClassLoader(),
                                new Class[]{listenerType},listenerInvocationHandler);


                        //让proxy去执行的onClick();
                        Method onClickMethod = view.getClass().getMethod(listenerSetter,listenerType);
                        onClickMethod.invoke(view,proxy);

                        //此时，点击按钮就会去执行代理中invoke，方法；



                    }

                  }catch (Exception e){
                    e.printStackTrace();
                }

            }
            
        }

    }

}
