package cqu.ioc.context;


import cqu.ioc.annotation.MyAutoWired;
import cqu.ioc.annotation.MyCompetent;
import cqu.ioc.annotation.MyController;
import cqu.ioc.annotation.MyScan;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * IoC 功能实现
 */
public class MyApplication {
    //实例化一个ioc容器
    private MyContext mycontext;
    //存储扫描出的类名
    private List<String> classNames = new ArrayList<>();

    public MyApplication(MyContext mycontext,String packetlocation) {
        this.mycontext = mycontext;
        doScanner(packetlocation);
        doInstance();
    }

    //扫描包得到包下所有类的名字
    private void doScanner(String packetlocation) {
        URL url  =this.getClass().getClassLoader().getResource("/"+packetlocation.replaceAll("\\.", "/"));
        File dir = new File(url.getFile());
        for (File file : dir.listFiles()) {
            if(!file.isDirectory()){
                String className =packetlocation +"." +file.getName().replace(".class", "");
                classNames.add(className);
            }else{
//              递归读取包
                doScanner(packetlocation+"."+file.getName());
            }
        }
    }

//    通过得到的所有类的名字得到类的实例
    private void doInstance() {
        if (classNames.isEmpty()) {
            return;
        }
        for (String className : classNames) {
            try {
                //把类搞出来,反射来实例化(只有加@MyController的类需要实例化)
                Class<?> clazz =Class.forName(className);
                if(clazz.isAnnotationPresent(MyController.class)){
                    //调用mycontext中的push函数 放入这个类和他的实例
                    mycontext.push(clazz);
                    System.out.print("扫描注入后ioc容器的内容:");
                    System.out.println(mycontext.getObjectMap());
                }else{
                    continue;
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    private MyContext getMyContext() {
        return mycontext;
    }

}
