package cqu.ioc.context;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * ioc容器
 */
public class MyContext {

//    装有ioc容器的对象实例
    private Map<Class, Object> objectMap=new HashMap<>();

//    添加类，并创建相应的实例对象到map容器中
    protected void push(Class c) {
//        通过反射创建新的类
        try {
            objectMap.put(c, c.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    protected Set<Class> getClassSet() {
        return objectMap.keySet();
    }

    public Map<Class, Object> getObjectMap() {
        return this.objectMap;
    }

}
