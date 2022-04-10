<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
    <title>文件上传</title>
</head>
<body>
<form action="workbench/activity/fileUpload.do" method="post" enctype="multipart/form-data">
<input type="file" name="myfile">
    <input type="text" name="userName">
    <input type="submit" value="提交">
</form>
</body>
</html>
