<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <!--引入插件-->
    <script type="text/javascript" src="jquery/echars/echarts.min.js"></script>
    <title>演示echart做图</title>
    <script type="text/javascript">
        $(function () {
            //当容器加载完成之后，对容器调用工具函数

            //基于准备好的dom，初始化实例
            var myChart = echarts.init(document.getElementById('main'));

            //指定图标的配置项和数据
            var option = {
                title: {//标题
                    text: 'ECharts 入门实例'
                },
                tooltip: {},//提示框
                legend: {//图例
                    data: ['销量']
                },
                xAxis: {//x轴
                    data: ["衬衫", "羊毛衫", "裤子", "高跟鞋", "袜子", "雪梵山"]
                },
                yAxis: {},//y轴
                series: [{//系列
                    name: '销量',//系列名称
                    type: 'line',//系列类型 bar--柱状图 line--折线图
                    data: [5, 20, 36, 60, 10, 20]//系列的数据
                }]
            };
            //使用刚指定的配置项和数据显示图表
            myChart.setOption(option);
        });
    </script>
</head>
<body>
<div id="main" style="width: 600px;height: 400px;"></div>
</body>
</html>
