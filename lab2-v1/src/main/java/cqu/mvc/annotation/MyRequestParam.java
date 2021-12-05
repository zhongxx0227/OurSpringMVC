package cqu.mvc.annotation;

import java.lang.annotation.*;

/**
 * 被该注解标记的参数会在initHandlerAdapters()方法中被填入其value中指定的值
 */
@Target(ElementType.PARAMETER)//该注解只能用在参数上
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyRequestParam {
    String value();
}