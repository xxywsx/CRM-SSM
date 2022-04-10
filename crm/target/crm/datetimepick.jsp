<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String basePath= request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <!--引入jquery-->
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <!--引进前端框架-->
    <link rel="stylesheet" href="jquery/bootstrap_3.3.0/css/bootstrap.min.css"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <!--引进日历的插件-->
    <link rel="stylesheet" href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css">
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
    <title>演示插件</title>

    <script type="text/javascript">
        $(function (){
           //当容器加载完成，对容器调用工具函数
            $("#myDate").datetimepicker({
                language:'zh-CN',//语言
                format:'yyyy-mm-dd',//格式
                minView:'month',//可以 选择的最小视图
                initialDate:new Date(),//初始化显示的时间
                autoclose:true,//设置选择完后自动关闭日历
                todayBtn:true,//是否显示今天的按钮
                clearBtn:true
            });
        });
    </script>
</head>
<body>
<input type="text" id="myDate" readonly>

</body>
</html>
