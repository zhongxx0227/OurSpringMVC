package cqu.test;

import cqu.ioc.annotation.MyController;
import cqu.mvc.annotation.MyRequestMapping;
import cqu.mvc.model.Dao.FileDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

/**
 * 控制函数 处理提交逻辑
 */
@MyController
@MyRequestMapping("/upload")
public class uploadcontroller {
    @MyRequestMapping(value="/submit",method = "POST")
    public String submit(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException {
        Part part= req.getPart("file");
        String fileHeader=part.getHeader("content-disposition");
        String fileName=fileHeader.substring(fileHeader.indexOf("filename=")+10, fileHeader.lastIndexOf("\""));
        FileDao.addFileEntity(fileName,fileName);
//        跳转到finish页面
        return "finish";
    }
}
