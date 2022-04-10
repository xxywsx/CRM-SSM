<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>

    <link rel="stylesheet" type="text/css" href="jquery/bootstrap_3.3.0/css/bootstrap.min.css">
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>

    <script type="text/javascript" src="jquery/bs_typeahead/bootstrap3-typeahead.min.js"></script>
    <title>测试自动补全插件</title>
    <script type="text/javascript">
        $(function () {
           //当容器加载完成之后，对容器调用工具函数
            $("#customerName    ").typeahead({
               //source:['京东商城','阿里巴巴','百度有限公司','字节跳动有限公司','动力节点有限公司']
                source:function (jquery,process) {//process是个函数，能够将字符串赋值给source，source根据关键字在字符串中查询，从而自动补全公司名称、

                    //发送查询请求
                    $.ajax({
                        url:'workbench/transaction/queryAllCustomerName.do',
                        data:{
                            customerName:jquery
                        },
                        type:'post',
                        dataType:'json',
                        success:function (data) {
                            process(data);
                        }
                    });
                }
            });
        });
    </script>
</head>
<body>
<input type="text" id="customerName">
</body>
</html>
