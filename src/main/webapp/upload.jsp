<%--
  Created by IntelliJ IDEA.
  User: kingdee
  Date: 2017/6/14
  Time: 15:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>文件上传</title>
</head>

<body>
<form action="/upload.do" enctype="multipart/form-data" method="post">

    上传文件：<input type="file" name="file"><br/>
    <input type="submit" value="提交">仅用于会计备份恢复xml文件的科目查重
</form>
</body>
</html>
