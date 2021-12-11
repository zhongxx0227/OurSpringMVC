package cqu.ioc.annotation;

import java.lang.annotation.*;

/**
 * 被该注解标记的将会被绑定为一个handler类
 */
@Target(ElementType.TYPE)//代表该注解会用于接口、类、枚举
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyController {
    String value() default "";
}