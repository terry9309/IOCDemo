package com.terry.iocdemo;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author terry
 */


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)

public @interface ViewInject {

    int value();
}
