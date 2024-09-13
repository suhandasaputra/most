<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<body class="hold-transition skin-red-light sidebar-mini">
    <div class="wrapper">
        <%@include file='header.jsp' %>
        <%@include file='sidebar_left.jsp' %>
        <!-- Content Wrapper. Contains page content -->
        <div class="content-wrapper">
            <!-- Content Header (Page header) -->
            <section class="content-header">
                <h1><b>
                        WELCOME IN ONLINE MONITORING PAJAK 
                        <div align="right">
                            <medium id="time"></medium>
                        </div>
                    </b>
                </h1>
            </section >
            <div class="content">
                <div class="row">
                    <div class="col-md-12">
                        <div class="box">
                            <div class="box-header with-border">
                                <h1><b>CHECKING RESI</b></h1>
                                <form action="checkingresiServlet" method="post">
                                    <fieldset>
                                        <div class="form-group">
                                            <input class="content-header" name="noresi" type="text">
                                            &nbsp; &nbsp; &nbsp; Split Bill : <select name="split_bill">
                                                <option value="0">0</option>
                                                <option value="1">1</option>
                                                <option value="2">2</option>
                                                <option value="3">3</option>
                                                <option value="4">4</option>
                                                <option value="5">5</option>
                                                <option value="6">6</option>
                                                <option value="7">7</option>
                                                <option value="8">8</option>
                                                <option value="9">9</option>
                                                <option value="10">10</option>
                                            </select>
                                        </div>

                                        <div align="left">
                                            <input class="btn btn-bitbucket" type="submit" value="CHECKING"/>
                                        </div>
                                        <!--<input class="btn btn-lg btn-success" type="reset" value="RESET"/>-->
                                    </fieldset>
                                </form>  
                                <br>
                                <table id="ppobtable" class="display table table-bordered table-striped" cellspacing="0" width="100%">
                                    <thead>
                                        <tr>
                                            <th><Strong>NOMOR RESI</strong></th>
                                            <th><Strong>SPLIT BILL</Strong></th>
                                            <th><Strong>TANGGAL MERCHANT</Strong></th>
                                            <th><Strong>AMOUNT</Strong></th>
                                            <th><Strong>PPN</Strong></th>
                                            <th><Strong>SERVICE TAX</Strong></th>
                                            <th><Strong>DETAIL</Strong></th>
                                            <th><Strong>INSTITUSI ID</Strong></th>
                                            <th><Strong>INSTITUSI NAME</Strong></th>
                                            <th><Strong>POS ID</Strong></th>
                                            <th><Strong>KODE POS</Strong></th>
                                            <th><Strong>PAYMENT METHODE</Strong></th>
                                        </tr>
                                    <td>${sessionScope.NO_RESI}</td>
                                    <td>${sessionScope.SPLIT_BILL}</td>
                                    <td>${sessionScope.TRX_DATE}</td>
                                    <td>${sessionScope.AMOUNT}</td>
                                    <td>${sessionScope.PPN}</td>
                                    <td>${sessionScope.SERVICE_TAX}</td>
                                    <td>${sessionScope.PROD_NAME}</td>
                                    <td>${sessionScope.INSTITUTE_ID}</td>
                                    <td>${sessionScope.INSTITUTE_NAME}</td>
                                    <td>${sessionScope.POS_ID}</td>
                                    <td>${sessionScope.POS_CODE}</td>
                                    <td>${sessionScope.PAYMENT_METHODE}</td>
                                    </thead>
                                </table>
                                <form action="resetServlet" method="post">
                                    <fieldset>
                                        <input class="btn btn-lg btn-success" type="submit" value="CLEAR"/>
                                    </fieldset>
                                </form>
                            </div>
                            <div class="timeline-body">
                                <a class="thumbnail fancybox" rel="ligthbox" href="image/langkah-langkah.png" >
                                    <img class="img-responsive" alt="" src="image/langkah-langkah.png" />
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@include file='footer.jsp' %>
    </div>
</body>
