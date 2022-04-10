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
    <title>演示echart2做图</title>
    <script type="text/javascript">
        $(function () {
            //当容器加载完成之后，对容器调用工具函数

            //基于准备好的dom，初始化实例
            var myChart = echarts.init(document.getElementById('main'));

            //指定图标的配置项和数据
            var option = {
                title: {
                    text: '交易统计图标',
                    subtext: '交易的各个阶段的数据量'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c}%"
                },
                toolbox: {
                    feature: {
                        dataView: {readOnly: false},
                        restore: {},
                        saveAsImage: {}
                    }
                },
                series: [
                    {
                        name: '数据量',
                        type: 'funnel',
                        left: '10%',
                        width: '80%',
                        label: {
                            formatter: '{b}'
                        },
                        labelLine: {
                            show: true
                        },
                        itemStyle: {
                            opacity: 0.9
                        },
                        emphasis: {
                            label: {
                                position: 'inside',
                                formatter: '{b}: {c}%'
                            }
                        },
                        data: [
                            {value: 60, name: '访问'},
                            {value: 40, name: '咨询'},
                            {value: 20, name: '订单'},
                            {value: 80, name: '点击'},
                            {value: 100, name: '展现'}
                        ]
                    }
                ]
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
