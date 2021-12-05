package cqu.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;

/**
 * 将控制类及其方法映射并填入参数，实现页面跳转的逻辑
 */
public class MyHandlerAdapter {

    private Map<String, Integer> paramType;

    public MyHandlerAdapter(Map<String, Integer> paramType) {
        this.paramType = paramType;
    }

    public void handle(HttpServletRequest req, HttpServletResponse resp, Handler handler) throws Exception {

        Class<?>[] ptype = handler.getMethod().getParameterTypes();
        Object[] pvalues = new Object[ptype.length];

        String reqname=HttpServletRequest.class.getName();
        String resname=HttpServletResponse.class.getName();

        if (paramType.containsKey(reqname)) {
            pvalues[paramType.get(reqname)] = req;
        }
        if (paramType.containsKey(resname)) {
            pvalues[paramType.get(resname)] = resp;
        }

        for (Map.Entry<String, Integer> entry : paramType.entrySet()) {
            String pname = entry.getKey();
            Integer index = entry.getValue();

            String[] values = req.getParameterValues(pname);
            if (values != null && values.length != 0) {
                String value = Arrays.toString(values).replaceAll("\\[|\\]", "").replaceAll(",\\s", ",");
                pvalues[index] = returnvaluetype(value, ptype[index]);
            }
        }

        if (handler.getMethod().getReturnType() == String.class) {
            try{
                String viewName = (String) handler.getMethod().invoke(handler.getController(), pvalues);
                render(req, resp, viewName);
            }
            catch (InvocationTargetException e){
                System.out.println("此处接收被调用方法内部未被捕获的异常");
                Throwable t = e.getTargetException();// 获取目标异常
                t.printStackTrace();
            }
        }
        else {
            handler.getMethod().invoke(handler.getController(), pvalues);
        }
    }

    private void render(HttpServletRequest request, HttpServletResponse res, String viewName) throws Exception {
        request.getRequestDispatcher("/"+viewName + ".jsp").forward(request, res);
    }

    private Object returnvaluetype(String value, Class<?> clazz) {
        if (clazz == String.class) {
            return value;
        }
        else if (clazz == Integer.class) {
            return Integer.valueOf(value);
        }
        else if (clazz == int.class) {
            return Integer.valueOf(value).intValue();
        }
        return null;
    }

    public Map<String, Integer> getParamType() {
        return paramType;
    }

    public void setParamType(Map<String, Integer> paramType) {
        this.paramType = paramType;
    }
}