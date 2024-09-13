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
            <li class="header"><h3>MONITORING</h3></li>
            <li><a href="list_monitoring.jsp"><i class="fa fa-file"></i><span>Online Monitoring</span></a></li>
            <li class="treeview active">
                <a href="#"><i class="fa fa-file"></i><span>Merchant Status</span><i class="fa fa-angle-left pull-right"></i></a>
                <ul class="treeview-menu">
                    <li class="active"><a href="today.jsp">Today</a></li>
                    <li><a href="merchant.jsp">Last Active</a></li>
                </ul>
            </li>
            <!--<li><a href="report.jsp"><i class="fa fa-file"></i><span>Reporting</span></a></li>-->
            <li class="treeview">
                <a href="#"><i class="fa fa-file"></i><span>Monitoring</span><i class="fa fa-angle-left pull-right"></i></a>
                <ul class="treeview-menu">
                    <li><a href="monitoringharian.jsp">Daily Monitoring</a></li>
                    <li><a href="monitoringbulanan.jsp">Monthly Monitoring</a></li>
                    <li><a href="monitoringtahunan.jsp">Annual Monitoring</a></li>
                </ul>
            </li>
            <li><a href="monitoringproduct.jsp"><i class="fa fa-file"></i><span>Product Monitoring</span></a></li>
        </ul>
        <!-- /.sidebar-menu -->
    </section>
    <!-- /.sidebar -->
</aside>
