<%-- 
    Document   : sidebar
    Created on : May 25, 2016, 4:48:17 PM
    Author     : syukur
--%>
<!--Untuk edit css sidebar left-->
<!-- Left side column. contains the logo and sidebar -->
<aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
        <ul class="sidebar-menu">
            <li class="header"><h3><b>BACKOFFICE</b></h3></li>
            <!--<li><a href="list_transaction_yabes.jsp"><i class="fa fa-file"></i><span>Online Monitoring</span></a></li>-->
            
            <li class="treeview active">
                <a href="#"><i class="fa fa-file"></i><span>Merchant</span><i class="fa fa-angle-left pull-right"></i></a>
                <ul class="treeview-menu">
                    <li><a href="listmerchant.jsp">List Merchant</a></li>
                    <li class="active"><a href="addmerchant.jsp">Add Merchant</a></li>
                </ul>
            </li>
            
            <li><a href="report.jsp"><i class="fa fa-file"></i><span>Reporting</span></a></li>
<!--            <li class="treeview">
                <a href="#"><i class="fa fa-file"></i><span>Monitoring</span><i class="fa fa-angle-left pull-right"></i></a>
                <ul class="treeview-menu">
                    <li><a href="monitoringharian.jsp">Daily Monitoring</a></li>
                    <li><a href="monitoringbulanan.jsp">Monthly Monitoring</a></li>
                    <li><a href="monitoringtahunan.jsp">Annual Monitoring</a></li>
                </ul>
            </li>-->
         
        </ul>
        <!-- /.sidebar-menu -->
    </section>
    <!-- /.sidebar -->
</aside>
