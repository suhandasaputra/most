<%-- 
    Document   : merchant
    Created on : May 26, 2016, 11:44:01 AM
    Author     : syukur
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  "http://www.w3.org/TR/html4/loose.dtd">
<%@include file='defaultextend.jsp' %>
<script>
    $(document).ready(function () {
        function datatable() {
            table = $("#ppobtable").dataTable({
                retrieve: true,
                "ajax": {
                    "url": "/monitoring_pajak/MonitoringproductServlet",
                    "dataSrc": ""
                },
                "columns": [
                    {"data": "product_name"},
                    {"data": "total",
                render: function (data) {
                            var rx = /(\d+)(\d{3})/;
                            return String(data).replace(/^\d+/, function (w) {
                                while (rx.test(w)) {
                                    w = w.replace(rx, '$1,$2');
                                }
                                return w;
                            });
                        }    
                },
                    {"data": "total_amount",
                render: function (data) {
                            var rx = /(\d+)(\d{3})/;
                            return String(data).replace(/^\d+/, function (w) {
                                while (rx.test(w)) {
                                    w = w.replace(rx, '$1,$2');
                                }
                                return w;
                            });
                        }    
                }
                ],
                "order": [[1, "desc"]]
            });
        }
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
        setInterval(datatable, 1000);
    });
</script>

<body class="hold-transition skin-black-light sidebar-mini">
    <%
        session.getMaxInactiveInterval();
        if (session.getAttribute("username") == null) {

            response.sendRedirect("index.jsp");
        }
    %>
    <div class="wrapper">

        <%@include file='header.jsp' %>
        <%@include file='sidebar_monitoringproduct.jsp' %>

        <!-- Content Wrapper. Contains page content -->
        <div class="content-wrapper">
            <!-- Content Header (Page header) -->
            <section class="content-header">
                <h1>
                    <b>MONITORING ONLINE SYSTEM TAX</b> <br><br>
                    <div align="right">
                        <small id="time" color="black"></small>
                    </div>
                </h1>
                <ol class="breadcrumb">
                    <!--<li><a href="WelcomePage.jsp"><i class="fa fa-dashboard"></i> Home</a></li>-->
                    <!--<li><a href="monitoringppob.jsp">Monitoring PPOB</a></li>-->
                    <a href="merchant.jsp" class="btn btn-default button-collection">
                        <span>REFRESH</span>
                    </a>
                </ol>
            </section>

            <!-- Main content -->
            <section class="content">
                <div class="row">
                    <div class="col-md-12">
                        <div class="box box-info">
                            <div class="box-header with-border">
                                <h3 class="box-title"><b>PRODUCT MONITORING</b></h3>
                            </div>
                            <!-- /.box-header -->
                            <div class="box-body">
                                <div class="col-md-12">
                                    <table id="ppobtable" class="display table table-bordered table-striped" cellspacing="0" width="100%">
                                        <thead>
                                           
                                            <tr class="filters">
                                                <th class="input-filter"><strong>Product Name</strong></th>
                                                <th class="input-filter"><strong>Total</strong></th>  
                                                <th class="input-filter"><strong>Total Amount</strong></th>
                                            </tr>


                                        </thead>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <!-- /.content -->
        </div>
        <!-- /.content-wrapper -->

        <%@include file='footer.jsp' %>
        <%@include file='sidebar_right.jsp' %>
    </div>
</body>


