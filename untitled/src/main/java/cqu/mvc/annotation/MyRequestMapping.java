package cqu.mvc.annotation;

import java.lang.annotation.*;

/**
 * 使类或方法映射到value中指定的url方法上，实现了RESTFUL风格
 */
@Target({ElementType.TYPE,ElementType.METHOD})//该注解可以用于方法上
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyRequestMapping {
    String value() default "";
    String method() default "";
}