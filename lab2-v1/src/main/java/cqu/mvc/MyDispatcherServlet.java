package cqu.mvc;


import cqu.ioc.annotation.MyController;
import cqu.ioc.context.MyApplication;
import cqu.ioc.context.MyContext;
import cqu.mvc.annotation.MyRequestMapping;
import cqu.mvc.annotation.MyRequestParam;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

/**
*自定义的servlet,继承于HTTPServlet
 */

//Tomcat中必须设置@MultipartConfig标注才能使用getPart()相关API
@MultipartConfig
public class MyDispatcherServlet extends HttpServlet{
    private Properties properties=new Properties();
    MyContext ioc=new MyContext();
    private List<Handler> handlerMapping = new ArrayList<Handler>();
    private Map<Handler, MyHandlerAdapter> handleradapter  =new HashMap<>();

    public static final String CONFIGLOCATION = "contextConfigLocation";
    public static final String PROPERTY = "scanPackage";

//    初始化 重写init方法
    @Override
    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        String location = getInitParameter(CONFIGLOCATION);
        initconfig(location);
        String packetlocation=properties.getProperty(PROPERTY);
        //传入容器和要扫描的包的名字 在myapplication初始化时扫描
        MyApplication myapplication=new MyApplication(ioc,packetlocation);
        initmappings(ioc);
        inithandleradapter();
    }

    /*
重写doget和dopost方法
 */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doDispatch(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Handler handler=getHandler(req);
        if(handler!=null){
            MyHandlerAdapter myhandleradapter=getHandlerAdapter(handler);
            myhandleradapter.handle(req,resp,handler);
        }
    }

    /*
    初始化配置 扫描propertiest中的所有内容
     */
    private void  initconfig(String location){
        System.out.println("properties文件的路径"+location);
        //把web.xml中的contextConfigLocation对应value值的文件加载到留里面
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(location);
        try {
            //用Properties文件加载文件里的内容
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //关流
            if(null!=resourceAsStream){
                try {
                    resourceAsStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
    扫描ioc容器
    往handlermapping中放入handler
     */
    private void initmappings(MyContext mycontext){
        Map<Class, Object>objectmap=mycontext.getObjectMap();
        if(objectmap.isEmpty()){return;}
//      遍历ioc容器中元素 得到handler对象的各个属性
        for (Object object: objectmap.values()) {
//            需取以下值
            Method meth=null;
            Pattern pattern=null;

            String url1=" ";
            if (!object.getClass().isAnnotationPresent(MyController.class)) continue;
            Class<?> clazz = object.getClass();
            if (clazz.isAnnotationPresent(MyRequestMapping.class)) {
                url1 = clazz.getAnnotation(MyRequestMapping.class).value();
            }
            
            Method[] methods = clazz.getMethods();
            for (Method method1 : methods) {
                if (!method1.isAnnotationPresent(MyRequestMapping.class)) continue;
                meth=method1;
                String requesttype=" ";
                requesttype = method1.getAnnotation(MyRequestMapping.class).method();

                String url2 = method1.getAnnotation(MyRequestMapping.class).value();
                String url = (url1 + url2).replaceAll("/+", "/");
                System.out.println("url-pattern"+url);
                pattern = Pattern.compile(url);

//                Handler handler=new Handler(object, meth, pattern,requesttype);
                Handler handler=new Handler(object, meth, url,requesttype);
                handlerMapping.add(handler);
            }
        }
    }



    private void inithandleradapter() {
        if (handlerMapping.isEmpty()) return;

        for (Handler handler : handlerMapping) {
            Map<String, Integer> ptype = new HashMap<String, Integer>();
            Method method = handler.getMethod();
            Class<?>[] parameterTypes = method.getParameterTypes();

            //得到注解
            Annotation[][] ann = method.getParameterAnnotations();
            //遍历所有的注解
            for (int i = 0; i < ann.length; i++) {
                for(int j=0;j<ann[i].length;j++){
                    Annotation a=ann[i][j];
                    if(a instanceof MyRequestParam){
                        String paramName = ((MyRequestParam) a).value();
                        if(!"".equals(paramName)){
                            ptype.put(paramName,i);
                        }
                    }
                }
            }

            //遍历所有的参数
            for (int i = 0; i < parameterTypes.length; i++) {
                Class<?> type = parameterTypes[i];
                if (type == HttpServletRequest.class || type == HttpServletResponse.class) {
                    String name=type.getName();
                    ptype.put(name,i);
                }
            }
            handleradapter.put(handler,new MyHandlerAdapter(ptype));
        }
    }


    private Handler getHandler(HttpServletRequest req) {
        if(handlerMapping.isEmpty())return null;

        String reqtype = req.getMethod();

        String webpath = req.getContextPath();
        String url = req.getRequestURI();
        url = url.replace(webpath,"").replaceAll("/+","/");

        for (Handler handler:handlerMapping){
            String handtype=handler.getRequstType();
            String handurl=handler.geturl();
            if(handurl.equals(url) && handtype.equals(reqtype)){
                return handler;
            }
        }
        return null;
    }

    private MyHandlerAdapter getHandlerAdapter(Handler handler) {
        if(handleradapter.isEmpty())return null;
        return handleradapter.get(handler);
    }
}
