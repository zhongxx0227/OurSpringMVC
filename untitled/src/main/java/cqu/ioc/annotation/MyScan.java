package cqu.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**注解扫描
 * 扫描文件种所有的注解
 * 标记在启动类之上
 */
@Target(ElementType.TYPE)//用于描述类
@Retention(RetentionPolicy.RUNTIME)//定义被他所注解的注解保留多久  RUNTIME代表注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在
public @interface MyScan {
    String value() default "";
}
