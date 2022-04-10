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
            //发送请求
            $.ajax({
                url:'workbench/chart/transaction/queryCountGroupBy.do',
                type:'post',
                dataType:'json',
                success:function (data) {
                    //调用echarts工具函数,显示漏斗图
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
                                data: data
                            }
                        ]
                    };
                    //使用刚指定的配置项和数据显示图表
                    myChart.setOption(option);
                }
            })
        });
    </script>
</head>
<body>
<div id="main" style="width: 600px;height: 400px;"></div>
</body>
</html>
