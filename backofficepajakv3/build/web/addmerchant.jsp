<%-- 
    Document   : addmerchant
    Created on : May 26, 2016, 11:45:00 AM
    Author     : syukur
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  "http://www.w3.org/TR/html4/loose.dtd">
<%@include file='defaultextend.jsp' %>
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
<%
     session.getMaxInactiveInterval();
    if (session.getAttribute("username") == null) {
       
        response.sendRedirect("index.jsp");
    }
%>
<body class="hold-transition skin-black-light sidebar-mini">
    <div class="wrapper">
        <%@include file='header.jsp' %>
        <%@include file='sidebar_addmerchant.jsp' %>
        <!-- Content Wrapper. Contains page content -->
        <div class="content-wrapper">
            <!-- Content Header (Page header) -->
            <section class="content-header">
                <b>BACK OFFICE</b> <br><br>
                <div align="right">
                        <small id="time" color="black"></small>
                    </div>
                <ol class="breadcrumb">
                    <a href="addmerchant.jsp" class="btn btn-default button-collection">
                        <span>REFRESH</span>
                    </a>
                </ol>
            </section>

            <!-- Main content -->
            <section class="content">
                <div class="row">
                    <div class="col-md-12">
                        <div class="box">
                            <div class="box-header with-border">
                                <h3 class="box-title">ADD MERCHANT</h3>
                            </div>
                            <!-- /.box-header -->
                            <form class="form-horizontal" method="POST" action="addmerchantServlet?action=addmerchant" name="frmAddMerchant">
                                <div class="box-body">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Institute ID</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" maxlength="4" name="instituteid" placeholder="example : instituteid" required>
                                            </div>
                                        </div> 
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Institute Name</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" name="institutename" placeholder="example : institutename" required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">POS ID</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" maxlength="3" name="posid" placeholder="example : posid" required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">ZIP CODE</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" maxlength="5" name="poscode" placeholder="example : poscode" required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">SERIAL KEY</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" maxlength="40" name="key" placeholder="example : 000000000000000000000000000000000000000" required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">ALAMAT</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" maxlength="100" name="alamat" placeholder="example : jakarta" required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">EMAIL</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="email" maxlength="30" name="email" placeholder="example : email@gmail.com" required>
                                            </div>
                                        </div><div class="form-group">
                                            <label class="col-sm-2 control-label">NPWP</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" maxlength="20" name="npwp" placeholder="example : 00000000000000000000" required>
                                            </div>
                                        </div><div class="form-group">
                                            <label class="col-sm-2 control-label">MEREK</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" maxlength="20" name="merek" placeholder="example : quinos" required>
                                            </div>
                                        </div><div class="form-group">
                                            <label class="col-sm-2 control-label">TIPE</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" maxlength="20" name="tipe" placeholder="example : XYZ" required>
                                            </div>
                                        </div><div class="form-group">
                                            <label class="col-sm-2 control-label">SERIAL NUMBER</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" maxlength="40" name="serial_number" placeholder="example : SN000000000000" required>
                                            </div>
                                        </div><div class="form-group">
                                            <label class="col-sm-2 control-label">IMEI</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" maxlength="30" name="imei" placeholder="example : 987654323456789088888" required>
                                            </div>
                                        </div><div class="form-group">
                                            <label class="col-sm-2 control-label">NOPD</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" maxlength="20" name="nopd" placeholder="example : 00000000000000000000" required>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                                <div class="box-footer">
                                    <button type="submit" value="Submit" class="btn btn-success pull-right">Add</button>
                                </div>
                            </form>
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


