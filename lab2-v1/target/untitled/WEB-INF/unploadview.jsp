<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>文件上传</title>
</head>
<body>
<form action="upload.do" enctype="multipart/form-data" method="post"> //关键句1
    <table>
        <tr> <td>文件名：</td>
            <td><input type="file" name="fileName" size="30" /></td> //关键句2
        </tr>
        <tr>
            <td><input type="submit" value="上传" /></td>
        </tr>
    </table>
</form>
</body>
</html>