<%-- 
    Document   : monitoringharian
    Created on : May 26, 2016, 11:45:00 AM
    Author     : syukur
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  "http://www.w3.org/TR/html4/loose.dtd">

<%@include file='defaultextend.jsp' %>
<script>
    $(document).ready(function () {
        function datatable1() {
            table1 = $("#ppobmonth").dataTable({
                retrieve: true,
                 dom: 'Bfrtip',
                "ajax": {
                    "url": "/monitoring_pajak/MonitoringppobmonthServlet",
                    "dataSrc": ""
                },
                "columns": [
                    {"data": "instituteid"},
                    {"data": "institutename"},
                    {"data": "posid"},
                    {"data": "transaction",
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
                    {"data": "amount",
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
                    {"data": "ppn",
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
                 buttons: [
                    {
                        extend: 'collection',
                        text: 'Export',
                        buttons:
                                [
                                    {
                                        extend: "copyHtml5",
                                        title: "Montly Monitoring ",
                                        exportOptions: {columns: ':visible:not()'}, //last column has the action types detail/edit/delete
                                        footer: true
                                    },
                                    {
                                        extend: "csvHtml5",
                                        title: "Montly Monitoring ",
                                        exportOptions: {columns: ':visible:not()'},
                                        footer: true
                                    },
                                    {
                                        extend: "excelHtml5",
                                        title: "Montly Monitoring ",
                                        exportOptions: {columns: ':visible:not()'},
                                        footer: true
                                    },
                                    {
                                        extend: "pdfHtml5",
                                        title: "Montly Monitoring ",
                                        exportOptions: {columns: ':visible:not()'},
                                        footer: true
                                    },
                                    {
                                        extend: "print",
                                        exportOptions: {columns: ':visible:not()'},
                                        footer: true
                                    }

                                ]
                    }
                ],
                "order": [[3, "desc"]],
                
                "footerCallback": function (row, data, start, end, display) {
                    var api = this.api(), data;
                    var intVal = function (i) {
                        return typeof i === 'string' ?
                                i * 1 :
                                typeof i === 'number' ?
                                i : 0;
                    };
                    total = api
                            .column(4)
                            .data()
                            .reduce(function (a, b) {
                                return intVal(a) + intVal(b);
                            }, 0);

                    total1 = api
                            .column(5)
                            .data()
                            .reduce(function (a, b) {
                                return intVal(a) + intVal(b);
                            }, 0);

                    $(api.column(0).footer()).html('Total Amount : Rp ' + total + ' Total PPN : Rp ' + total1);
                }

            });
        }
//        function Timer() {
//            var date = new Date();
//            var day = date.getDate();
//            var month = date.getMonth() + 1;
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
            daytime = day;
            yeartime = year;
            monthly = month;
//            if (month == '1') {
//                monthtime = 'January';
//            } else if (month == '2') {
//                monthtime = 'February';
//            } else if (month == '3') {
//                monthtime = 'March';
//            } else if (month == '4') {
//                monthtime = 'April';
//            } else if (month == '5') {
//                monthtime = 'May';
//            } else if (month == '6') {
//                monthtime = 'June';
//            } else if (month == '7') {
//                monthtime = 'July';
//            } else if (month == '8') {
//                monthtime = 'August';
//            } else if (month == '9') {
//                monthtime = 'September';
//            } else if (month == '10') {
//                monthtime = 'October';
//            } else if (month == '11') {
//                monthtime = 'November';
//            } else if (month == '12') {
//                monthtime = 'December';
//            }

            var lblTime = document.getElementById("time");
            lblTime.innerHTML = time;
            var monthTime = document.getElementById("month");
            monthTime.innerHTML = 'Month : '+monthly;
        }
        setInterval(Timer, 1000);
        setInterval(datatable1, 1000);
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
        <%@include file='sidebar_monitoringbulanan.jsp' %>

        <!-- Content Wrapper. Contains page content -->
        <div class="content-wrapper">
            <!-- Content Header (Page header) -->
            <section class="content-header">
                <h1>
                    <b>MONITORING ONLINE SYSTEM TAX</b><br><br>
                    <div align="right">
                        <small id="time" color="black"></small>
                    </div>
                </h1>
                <ol class="breadcrumb">
                    <!--<li><a href="WelcomePage.jsp"><i class="fa fa-dashboard"></i> Home</a></li>-->
                    <!--<li><a href="monitoringtransaksi_ppob_month.jsp">Monitoring Transaksi PPOB Month</a></li>-->
                    <a href="monitoringbulanan.jsp" class="btn btn-default button-collection">
                        <span>REFRESH</span>
                    </a>
                </ol>
            </section>

            <!-- Main content -->
            <section class="content">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="box box-info">
                            <div class="box-header with-border">
                                <h3 class="box-title"><b>MONTHLY MONITORING</b></h3>
                                <h3 class="box-title" style="right: 30px;position: absolute" id="month"></h3>
                            </div>
                            <!-- /.box-header -->
                            <div class="box-body">
                                <table id="ppobmonth" class="display table table-bordered">
                                    <thead>    
                                        <tr>
                                            <th>Institute Id</th>
                                            <th>Institute Name</th>
                                            <th>POS ID</th>
                                            <th>Total Transaction</th>
                                            <th>Total Amount</th>
                                            <th>Total PPN</th>
                                        </tr>
                                    </thead>
                                     <tfoot>
                                        <tr>
                                            <td ></td>
                                        </tr>
                                    </tfoot>
                                </table>
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


