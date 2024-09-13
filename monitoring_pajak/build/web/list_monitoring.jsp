<%-- 
    Document   : monitoringtransaksi
    Created on : May 26, 2016, 11:45:00 AM
    Author     : syukur
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  "http://www.w3.org/TR/html4/loose.dtd">

<%@include file='defaultextend.jsp' %>
<meta http-equiv='refresh' content='60'>
<script>
    $(document).ready(function () {
        $('#transaction thead tr.filters th').each(function () {
            var title = $(this).text();
            if ($(this).hasClass("input-filter")) {
                $(this).html('<input name ="' + $.trim(title).replace(/ /g, '') + '" type="text" class = "form-control" placeholder="Segggarch ' + $.trim(title) + '" />');
            } else if ($(this).hasClass("date-filter")) {
                $(this).html('<div class="input-prepend input-group"><span class="add-on input-group-addon"><i class="glyphicon glyphicon-calendar fa fa-calendar"></i></span><input type="text" style="width: 200px" name="' + $.trim(title).replace(/ /g, '') + '"  placeholder="Searjjjch ' + $.trim(title) + '" class="form-control daterange"/></div>');
            }
        });


        var table = $('#transaction').DataTable({
//            retrieve: true,
            dom: 'Bfrtip',
            "ajax": {
                "url": "/monitoring_pajak/ReportServlet?action=Listtransaction",
                "dataSrc": ""
            },
            "columns": [
                {data: "noresi"},
                {data: "split_bill"},
                {data: "instituteid"},
                {data: "institutename"},
                {data: "posid"},
                {data: "poscode"},
                {data: "dt_merc"},
                {data: "amount",
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
                {data: "ppn",
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
                {data: "service_tax",
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
                {data: "detail"
                    ,
                    render: function (data) {
                        data.split(",");
                        var i;
                        for (i = 0; i < data.length; i++) {
                        }
                        return data;
                    }
                },
                {data: "payment_methode"},
//                {data: "card_number"},
                {data: "nopd"}
//                ,
//                {data: "source"}
            ],
            buttons: [
            ],
            responsive: true,
            "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
            orderCellsTop: true,
            scrollX: true,
            colReorder: true,
            language: {
                searchPlaceholder: 'Search all columns',
                lengthMenu: '<div class="input-group"><span class="input-group-addon"><span class="glyphicon glyphicon-menu-hamburger"></span></span>_MENU_</div>',
                processing: "<img src='image/loading.gif'>"
            },
            processing: true,
            "initComplete": function (settings, json) {
                //  $("#mytable_processing").css("visibility", "hidden");
                $('#transaction').fadeIn();
            },
            "order": [[6, "desc"]],
            "footerCallback": function (row, data, start, end, display) {
                var api = this.api(), data;
                var intVal = function (i) {
                    return typeof i === 'string' ?
                            i * 1 :
                            typeof i === 'number' ?
                            i : 0;
                };
                total = api
                        .column(7)
                        .data()
                        .reduce(function (a, b) {
                            return intVal(a) + intVal(b);
                        }, 0);

                total1 = api
                        .column(8)
                        .data()
                        .reduce(function (a, b) {
                            return intVal(a) + intVal(b);
                        }, 0);

                $(api.column(0).footer()).html('Total Amount : Rp ' + total + ' Total PPN : Rp ' + total1);
            }



        });
//        new $.fn.dataTable.Buttons(table, {
//            buttons: [
//                {
//                    extend: 'colvis',
//                    text: 'Column visibility'
//
//                },
//            ]
//        });
        //add button to top
        table.buttons(0, null).container().prependTo(
                table.table().container()
                );
        //remove class from search filter
        ($("#transaction_filter input").removeClass("input-sm"));

        //instantiate datepicker and choose your format of the dates
        $('.daterange').daterangepicker({
            ranges: {
                "Today": [moment(), moment()],
                'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                '7 last days': [moment().subtract(6, 'days'), moment()],
                '30 last days': [moment().subtract(29, 'days'), moment()],
//                'This month': [moment().startOf('month'), moment().endOf('month')],
//                'Last month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')],
//                'Blank date': [moment("0001-01-01"), moment("0001-01-01")]
            }
            ,
            autoUpdateInput: false,
            opens: "left",
            locale: {
                cancelLabel: 'Clear',
                format: 'DD-MMM-YYYY'
            }
        });
        var startDate;
        var endDate;
        var dataIdx;
        //current data column to work with
        $("#transaction_wrapper thead").on("mousedown", "th", function (event) {
            var visIdx = $(this).parent().children().index($(this));
            dataIdx = table.column.index('fromVisible', visIdx);
        });
        // Function for converting a dd/mmm/yyyy date value into a numeric string for comparison (example 01-Dec-2010 becomes 20101201
        function parseDateValue(rawDate) {

            var d = moment(rawDate, "DD-MMM-YYYY").format('DD-MM-YYYY');
            var dateArray = d.split("-");
            var parsedDate = dateArray[2] + dateArray[1] + dateArray[0];
            return parsedDate;
        }
        //filter on daterange
        $(".daterange").on('apply.daterangepicker', function (ev, picker) {
            ev.preventDefault();
            //if blank date option was selected
            if ((picker.startDate.format('DD-MMM-YYYY') == "01-Jan-0001") && (picker.endDate.format('DD-MMM-YYYY')) == "01-Jan-0001") {
                $(this).val('Blank');


                val = "^$";

                table.column(dataIdx)
                        .search(val, true, false, true)
                        .draw();

            } else {
                //set field value
                $(this).val(picker.startDate.format('DD-MMM-YYYY') + ' to ' + picker.endDate.format('DD-MMM-YYYY'));

                //run date filter
                startDate = picker.startDate.format('DD-MMM-YYYY');
                endDate = picker.endDate.format('DD-MMM-YYYY');

                var dateStart = parseDateValue(startDate);
                var dateEnd = parseDateValue(endDate);

                var filteredData = table
                        .column(dataIdx)
                        .data()
                        .filter(function (value, index) {

                            var evalDate = value === "" ? 0 : parseDateValue(value);
                            if ((isNaN(dateStart) && isNaN(dateEnd)) || (evalDate >= dateStart && evalDate <= dateEnd)) {

                                return true;
                            }
                            return false;
                        });


                var val = "";
                for (var count = 0; count < filteredData.length; count++) {

                    val += filteredData[count] + "|";
                }

                val = val.slice(0, -1);


                table.column(dataIdx)
                        .search(val ? "^" + val + "$" : "^" + "-" + "$", true, false, true)
                        .draw();
            }
        });
        $(".daterange").on('cancel.daterangepicker', function (ev, picker) {
            ev.preventDefault();
            $(this).val('');
            table.column(dataIdx)
                    .search("")
                    .draw();

        });
        //hide unnecessary columns
        var column = table.columns($('.HideColumn'));
        // Toggle the visibility
        column.visible(!column.visible());
        // Apply the search
        $.each($('.input-filter', table.table().header()), function () {
            var column = table.column($(this).index());
            //onsole.log(column);
            $('input', this).on('keyup change', function () {
                if (column.search() !== this.value) {
                    column
                            .search(this.value)
                            .draw();
                }
            });
        });

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
            var ggwp = document.createElement('table');
            time = day + "-" + month + "-" + year + " " + hours + ":" + minutes + ":" + seconds;
            var lblTime = document.getElementById("time");
            lblTime.innerHTML = time;
        }
        setInterval(Timer, 1000);
    });
</script>
<body class="hold-transition skin-black-light sidebar-collapse">
    <%
        session.getMaxInactiveInterval();
        if (session.getAttribute("username") == null) {

            response.sendRedirect("index.jsp");
        }
    %>
    <div class="wrapper">
        <%@include file='header.jsp' %>
        <%@include file='sidebar_list_monitoring.jsp' %>
        <!-- Content Wrapper. Contains page content -->
        <div class="content-wrapper">
            <!-- Content Header (Page header) -->
            <section class="content-header">
                <h1>
                    <b>MONITORING ONLINE SYSTEM TAX</b>
                    <div align="right">
                        <small id="time" color="black"></small>
                    </div>
                </h1>
                <ol class="breadcrumb">
                    <!--<li><a href="WelcomePage.jsp"><i class="fa fa-dashboard"></i> Home</a></li>-->
                    <!--                    <a href="list_transaction_yabes.jsp" class="btn btn-default button-collection"
                                           tabindex="0" aria-controls="transaction" href="#">
                                            <span>REFRESH</span>
                                        </a>-->
                </ol>
            </section>

            <!-- Main content -->
            <section class="content">
                <div class="row">
                    <div class="col-md-12">
                        <div class="box">
                            <div class="box-header with-border">
                                <h3 class="box-title"><b>TRANSACTION MONITORING</b></h3>
                                <div class="col-md-12">
                                    <table id="transaction" class="table display table-striped table-bordered responsive-utilities jambo_table" cellspacing="0" width="100%">
                                        <thead>
                                            <tr>
                                                <th><Strong>Nomor Resi</strong></th>
                                                <th><Strong>Split Bill</Strong></th>
                                                <th><Strong>Institusi ID</Strong></th>
                                                <th><Strong>Nama Institusi</Strong></th>
                                                <th><Strong>POS ID</Strong></th>
                                                <th><Strong>Kode Pos</Strong></th>
                                                <th><Strong>Date Merchant</Strong></th>
                                                <th><Strong>Amount</Strong></th>
                                                <th><Strong>PPN</Strong></th>
                                                <th><Strong>Service Tax</Strong></th>
                                                <th><Strong>Detail</Strong></th>
                                                <th><Strong>Payment Methode</Strong></th>
                                                <!--<th><Strong>Card Number</Strong></th>-->
                                                <th><Strong>Nopd</Strong></th>
                                                <!--<th><Strong>Source</Strong></th>-->
                                            </tr>
                                            <!--                                            <tr class="filters">
                                                                                            <th class="input-filter">Nomor Resi</th>
                                                                                            <th class="date-filter">Date Merchant</th>
                                                                                            <th class="input-filter">Amount</th>
                                                                                            <th class="input-filter">PPN</th>
                                                                                            <th class="input-filter">Detail</th>
                                                                                            <th class="input-filter">Institusi ID</th>
                                                                                            <th class="input-filter">POS ID</th>
                                                                                            <th class="input-filter">Kode Pos</th>                                                
                                            
                                                                                        </tr>-->
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


