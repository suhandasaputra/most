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
                    "url": "/monitoring_bprd/merchantServlet",
                    "dataSrc": ""
                },
                "columns": [
                    {"data": "instituteid"},
                    {"data": "institutename"},
                    {"data": "posid"},
                    {"data": "poscode"},
                    {"data": "nopd"},
                    {"data": "dt_merc"},
                    {"data": "status",
                        render: function (data) {
                            if (data == 'red') {
                                return '<div class="progress"><div class="progress-bar progress-bar-danger" style="width: 100%"></div></div>';
                            } else if (data == 'green') {
                                return '<div class="progress"><div class="progress-bar progress-bar-success" style="width: 100%"></div></div>';
                            }
                        }
                    }
                ],
                "order": [[0, "asc"]]
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
        <%@include file='sidebar_merchant.jsp' %>

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
                                <h3 class="box-title"><b>MERCHANT STATUS LAST ACTIVE</b></h3>
                            </div>
                            <!-- /.box-header -->
                            <div class="box-body">
                                <div class="col-md-12">
                                    <table id="ppobtable" class="display table table-bordered table-striped" cellspacing="0" width="100%">
                                        <thead>
                                            
                                            <tr class="filters">
                                                <th class="input-filter"><strong>Institusi ID</strong></th>
                                                <th class="input-filter"><strong>Institute Name</strong></th>  
                                                <th class="input-filter"><strong>POS ID</strong></th>   
                                                <th class="input-filter"><strong>ZIP CODE</strong></th>   
                                                <th class="input-filter"><strong>NOPD</strong></th>   
                                                <th class="input-filter"><strong>Last Active</strong></th>      
                                                <th class="input-filter"><strong>Status</strong></th>
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


