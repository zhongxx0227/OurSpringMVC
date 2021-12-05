package cqu.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标有该注解的类会在IoC容器中产生相对应的对象
 */
@Target(ElementType.TYPE)//代表该注解会用于接口、类、枚举
@Retention(RetentionPolicy.RUNTIME)//定义被他所注解的注解保留多久  RUNTIME代表注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在
public @interface MyCompetent {
}
