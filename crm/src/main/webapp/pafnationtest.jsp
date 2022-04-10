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

    <link rel="stylesheet" type="text/css" href="jquery/bs_pagination-master/css/jquery.bs_pagination.min.css">
    <script type="text/javascript" src="jquery/bs_pagination-master/js/jquery.bs_pagination.min.js"></script>
    <script type="text/javascript" src="jquery/bs_pagination-master/localization/en.js"></script>

    <title>演示分页插件的使用</title>
    <script type="text/javascript">
        $(function () {
            $("#demo_pag1").bs_pagination({
                totalPages:100,//总页数
                currentPage: 1,//当前页号
                rowsPerPage:10,//每页条数
                totalRows:1000,//总条数

                visiblePageLinks:5,//可见的卡片数

                showGoToPage:true,//是否显示到指定页面
                showRowsInfo:true,//是否显示“每页显示条数”
                showRowsPerPage:true,//是否显示记录的信息

                //用户切换页号，回触发此函数
                onChangePage: function(event,pageObj) { // returns page_num and rows_per_page after a link has clicked
                    //js代码
                    alert(pageObj.totalRows);
                }
            });
        });
    </script>
</head>
<body>
<div id="demo_pag1">
</div>
</body>
</html>