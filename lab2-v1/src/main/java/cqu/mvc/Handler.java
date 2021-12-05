package cqu.mvc;

import java.lang.reflect.Method;

/**
 * handler类
 * 保存handler信息，包括controller对象，方法，url和访问类型
 */
public class Handler {

    private Object controller;
    private Method method;
    private String url;
    private String requstType;

    public Handler(Object controller, Method method, String url,String requstType) {
        System.out.println("url "+url);
        this.controller = controller;
        this.method = method;
        this.url = url;
        this.requstType=requstType;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
    
    public String geturl() {
        return url;
    }
    
    public void seturl(String url) {
        this.url = url;
    }
    
    public String getRequstType() {
        return requstType;
    }
}