<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  "http://www.w3.org/TR/html4/loose.dtd">

<%
    session.getMaxInactiveInterval();
    if (session.getAttribute("username") == null) {

        response.sendRedirect("index.jsp");
    }
%>
<%@include file='defaultextend.jsp' %>
<style>
    #statistic {
        margin-top: 5px;
        margin-bottom: 5px;
        margin-left: 15px;
        background-color: white;
        display: flex;
        position: relative;
        width: 920px;
    }
    #statistic1 {
        margin-top: 5px;
        margin-bottom: 5px;
        margin-left: 15px;
        background-color: white;
        display: flex;
        position: relative;
        width: 450px;
        height: auto;
    }
    #statistic2 {
        margin-top: 5px;
        margin-bottom: 5px;
        margin-left: 20px;
        background-color: white;
        display: flex;
        position: relative;
        width: 450px;
    }

    #statistic_title {
        font-weight: 600;
    }    
    #statistic_title1 {
        font-weight: 600;
    }
    #statistic_title2 {
        font-weight: 600;
    }
    #chart {
        max-width: 860px;
        margin: 35px auto;
    }
    #chart1 {
        width: 450px;
        /*max-width: 860px;*/
        margin: 35px auto;
    }
    #chart2 {
        width: 450px;
        /*max-width: 380px;*/
        margin: 35px auto;
    }
    #gghh {
        display: inline-flex;
    }
</style>

<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/apexcharts"></script>
<script src="https://cdn.jsdelivr.net/npm/vue-apexcharts"></script>
<script>
    $(document).ready(function () {
        function Timer() {
            var date = new Date();
            var day = date.getDate();
            var month;
            switch (new Date().getMonth()) {
                case 0:
                    month = "January";
                    break;
                case 1:
                    month = "Februari";
                    break;
                case 2:
                    month = "March";
                    break;
                case 3:
                    month = "April";
                    break;
                case 4:
                    month = "May";
                    break;
                case 5:
                    month = "June";
                    break;
                case  6:
                    month = "July";
                    break;
                case  7:
                    month = "August";
                    break;
                case  8:
                    month = "September";
                    break;
                case  9:
                    month = "October";
                    break;
                case  10:
                    month = "November";
                    break;
                case  11:
                    month = "December";
            }
            var year = date.getFullYear();
            var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
            var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
            var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
            time = day + "-" + month + "-" + year + " " + hours + ":" + minutes + ":" + seconds;
            var lblTime = document.getElementById("time");
            lblTime.innerHTML = time;
        }
        setInterval(Timer, 1000);
    });
</script>
<body class="hold-transition skin-black-light sidebar-mini" onload="func_grap()">
    <div class="wrapper">
        <%@include file='header.jsp' %>
        <%@include file='sidebar_left.jsp' %>
        <!-- Content Wrapper. Contains page content -->
        <div class="content-wrapper">
            <!-- Content Header (Page header) -->
            <section class="content-header">
                <h1>
                    <b>WELCOME IN MONITORING ONLINE SYSTEM TAX</b>
                    <div align="right">
                        <small id="time" color="black"></small>
                    </div>
                </h1>
            </section >
            <div class="content">
                <div class="row">
                    <div class="col-md-12">
                        <div class="box">
                            <div class="box-header with-border">
                                <h3  class="box-title"><b>MONITORING</b></h3>
                            </div>                            
                        </div>
                    </div>



                    <div id="statistic">
                        <div id="title_bar" style="position: absolute; margin-left: 20px; margin-top: 10px;">Transaction</div>
                        <br>
                        <div id="app" style="">
                            <div id="chart" style="width: 860px; float: left">
                                <apexchart id="ff" type="area" height="250" :options="chartOptions" :series="series"></apexchart>
                            </div>
                        </div>
                    </div>

                    <div id="gghh">
                        <div id="statistic1">
                            <div id="title_bar1" style="position: absolute; margin-left: 20px; margin-top: 10px;">Product</div>
                            <br>
                            <div id="app1" style="display: contents">
                                <div id="chart1">
                                    <apexchart id="ff1" type="pie" width="380" :options="chartOptions1" :series="series"></apexchart>
                                </div>
                            </div>
                        </div>

                        <div id="statistic2">
                            <div id="title_bar2" style="position: absolute; margin-left: 20px; margin-top: 10px;">Merchant</div>
                            <br>
                            <div id="app2" style="display: contents">
                                <div id="chart2" style="">
                                    <apexchart type="donut" :options="chartOptions2" :series="series"></apexchart>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- /.content-wrapper -->

        <%@include file='footer.jsp' %>
        <%@include file='sidebar_right.jsp' %>
    </div>
</body>
<script>
    function func_grap() {
        $("#ff").remove();
        $('#chart').append('<apexchart id="ff" type="area" height="250" :options="chartOptions" :series="series"></apexchart>');
        var datjson2 = new Object();
        datjson2.bulan_val = $('#a').val();
        $.ajax({
            url: "GraphServlet",
            type: 'post',
            dataType: "json",
            data: JSON.stringify(datjson2),
            contentType: 'application/json',
            mimeType: 'application/json',
            success: function (response) {
                if (response.resp_code == 0000) {
                    var data_tgl = response.tgl;
                    var data_amount = response.amount;
                    var data_ppn = response.ppn;

                    var data_prod_name = response.prod_name;
                    var data_total = response.total;

                    var data_instituteid = response.instituteid;
                    var data_ppn1 = response.ppn1;
                    new Vue({
                        el: '#app',
                        components: {
                            apexchart: VueApexCharts
                        },
                        data: {
                            series: [{
                                    name: 'amount',
                                    data: data_amount
                                }, {
                                    name: 'ppn',
                                    data: data_ppn
                                }],
                            chartOptions: {
                                chart: {
                                    height: 350,
                                    type: 'area'
                                },
                                dataLabels: {
                                    enabled: true
                                },
                                stroke: {
                                    curve: 'smooth'
                                },
                                xaxis: {
                                    type: 'datetime',
                                    categories: data_tgl
                                },
                                tooltip: {
                                    x: {
                                        format: 'dd/MM/yy HH:mm'
                                    }
                                }
                            }
                        }
                    })








                    new Vue({
                        el: '#app1',
                        components: {
                            apexchart: VueApexCharts,
                        },
                        data: {

                            series: data_total,
                            chartOptions1: {
                                chart1: {
                                    width: 380,
                                    type: 'pie',
                                },
                                labels: data_prod_name,
                                responsive: [{
                                        breakpoint: 480,
                                        options: {
                                            chart: {
                                                width: 200
                                            },
                                            legend: {
                                                position: 'bottom'
                                            }
                                        }
                                    }]
                            },
                        },
                    })

                    new Vue({
                        el: '#app2',
                        components: {
                            apexchart: VueApexCharts,
                        },
                        data: {
                            series: data_ppn1,
                            chartOptions2: {
                                chart2: {
                                    type: 'donut',
                                },
                                labels: data_instituteid,
                                responsive: [{
                                        breakpoint: 480,
                                        options: {
                                            chart: {
                                                width: 200
                                            },
                                            legend: {
                                                position: 'bottom'
                                            }
                                        }
                                    }]
                            },
                        },

                    })
                }
            }
        });

















    }
</script>