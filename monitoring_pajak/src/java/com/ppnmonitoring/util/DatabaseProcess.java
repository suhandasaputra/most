/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ppnmonitoring.util;

import com.ppn.entity.ConvertDateFormat;
import com.ppn.model.Monitoringppobmonth;
import com.ppn.model.Monitoringppobtoday;
import com.ppn.model.Monitoringppobyear;
import com.ppn.model.Report;
import com.ppn.model.Product;
import com.ppn.model.merchant;
import com.ppnmonitoring.servlet.DatasourceEntry;
import com.ppnlib.function.JsonProcess;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

public class DatabaseProcess {

    private static final Logger log = Logger.getLogger(DatabaseProcess.class);

    private void clearStatment(PreparedStatement stat) {
//        log.info("stat 2 : " + stat);
        if (stat != null) {
            try {
//                log.info("stat A");
                stat.clearBatch();
//                log.info("stat B");
                stat.clearParameters();
//                log.info("stat C");
                stat.close();
//                log.info("stat D");
                stat = null;
//                log.info("stat E");
            } catch (SQLException ex) {
//                log.error("clearStatment : " +ex.getMessage());
//                ex.printStackTrace();
            }
        }
    }

    private void clearDBConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException ex) {
//                log.error("clearDBConnection : "+ex.getMessage());
            }
        }
    }

    private void clearResultset(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
                rs = null;
            } catch (SQLException ex) {
//                log.error("clearResultset : "+ex.getMessage());
            }
        }
    }

    private void clearAllConnStatRS(Connection conn, PreparedStatement stat, ResultSet rs) {
        clearResultset(rs);
        clearStatment(stat);
        clearDBConnection(conn);
    }

    public ArrayList<Report> getAlltransaction() throws ParseException {
        ArrayList<Report> listTransaction = new ArrayList<Report>();
        SimpleDateFormat requesttime1 = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat requesttime3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat requesttime2 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
        Connection conn = null;
        PreparedStatement stat1 = null;
        ResultSet rs1 = null;
        PreparedStatement stat2 = null;
        ResultSet rs2 = null;

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
//            stat1 = conn.prepareStatement("select * from (select * from listtax union all select * from listtax_backup ) as noresi where status_paid = 1");
            stat1 = conn.prepareStatement("select * from (select * from listtax union all select * from listtax_backup ) as noresi");
            rs1 = stat1.executeQuery();
            while (rs1.next()) {
                Report reportyabes = new Report();
                stat2 = conn.prepareStatement("select institutename from merchant where instituteid='" + rs1.getString("instituteid") + "'");
                rs2 = stat2.executeQuery();
                while (rs2.next()) {
                    if (rs2.getString("institutename") == null) {
                        reportyabes.setInstitutename("-");
                    } else {
                        reportyabes.setInstitutename(rs2.getString("institutename"));
                    }
                }
                if (rs1.getString("noresi") == null) {
                    reportyabes.setNoresi("-");
                } else {
                    reportyabes.setNoresi(rs1.getString("noresi"));
                }

                if (rs1.getString("split_bill") == null) {
                    reportyabes.setSplit_bill("-");
                } else {
                    reportyabes.setSplit_bill(rs1.getString("split_bill"));
                }

                if (rs1.getString("dt_merc") == null) {
                    reportyabes.setDt_merc("-");
                } else {
                    Date responsetime = requesttime1.parse(rs1.getString("dt_merc"));
                    reportyabes.setDt_merc(requesttime2.format(responsetime));
                }
                if (rs1.getString("dt_internal") == null) {
                    reportyabes.setDt_internal("-");
                } else {
                    Date responsetime = requesttime3.parse(rs1.getString("dt_internal"));
                    reportyabes.setDt_internal(requesttime2.format(responsetime));

//                    reportyabes.setDt_internal(ConvertDateFormat.formatDate(rs1.getString("dt_internal"), "yyyy-MM-dd HH:mm:ss", "dd-MMM-yyyy HH:mm:ss"));
//                      reportyabes.setDt_internal("yyyyMMddHHmmss", "yyyy-MMM-dd HH:mm:ss"));
                }
                if (rs1.getString("amount") == null) {
                    reportyabes.setAmount("-");
                } else {
                    reportyabes.setAmount(rs1.getString("amount"));
                }
                if (rs1.getString("ppn") == null) {
                    reportyabes.setPpn("-");
                } else {
                    reportyabes.setPpn(rs1.getString("ppn"));
                }

                if (rs1.getString("service_tax") == null) {
                    reportyabes.setService_tax("-");
                } else {
                    reportyabes.setService_tax(rs1.getString("service_tax"));
                }

                if (rs1.getString("detail") == null) {
                    reportyabes.setDetail("-");
                } else {
                    reportyabes.setDetail("");

                    JSONArray listdetail = null;
                    JSONParser parser = new JSONParser();
                    try {
                        listdetail = (JSONArray) parser.parse(rs1.getString("DETAIL"));
                    } catch (org.json.simple.parser.ParseException ex) {
                        java.util.logging.Logger.getLogger(DatabaseProcess.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    String detail = "";
                    for (int i = 0; i < listdetail.size(); i++) {
                        HashMap item = JsonProcess.decodeJson(listdetail.get(i).toString());
                        detail += item.get("PROD_NAME").toString() + " " + item.get("TOTAL").toString() + " Rp " + item.get("SUM_AMOUNT").toString();
                        if (i < listdetail.size() - 1) {
                            detail += ", " + "<br>";
                        }
                        reportyabes.setDetail(detail);
                    }
                }
                if (rs1.getString("instituteid") == null) {
                    reportyabes.setInstituteid("-");
                } else {
                    reportyabes.setInstituteid(rs1.getString("instituteid"));
                }
                if (rs1.getString("posid") == null) {
                    reportyabes.setPosid("-");
                } else {
                    reportyabes.setPosid(rs1.getString("posid"));
                }
                if (rs1.getString("poscode") == null) {
                    reportyabes.setPoscode("-");
                } else {
                    reportyabes.setPoscode(rs1.getString("poscode"));
                }
                if (rs1.getString("payment_methode") == null) {
                    reportyabes.setPayment_methode("-");
                } else if (rs1.getInt("payment_methode") == 0) {
                    reportyabes.setPayment_methode("Cash");
                } else if (rs1.getInt("payment_methode") == 1) {
                    reportyabes.setPayment_methode("Debit");
                }
                if (rs1.getString("card_number") == null) {
                    reportyabes.setCard_number("-");
                } else {
                    reportyabes.setCard_number(rs1.getString("card_number"));
                }

                if (rs1.getString("nopd") == null) {
                    reportyabes.setNopd("-");
                } else {
                    reportyabes.setNopd(rs1.getString("nopd"));
                }
                if (rs1.getString("source") == null) {
                    reportyabes.setSource("-");
                } else {
                    reportyabes.setSource(rs1.getString("source"));
                }

                listTransaction.add(reportyabes);
            }
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clearDBConnection(conn);
            clearStatment(stat1);
            clearResultset(rs1);
            clearStatment(stat2);
            clearResultset(rs2);
        }
        return listTransaction;
    }

//    public ArrayList<Product> getAllProduct(){
//        ArrayList<Product> product = new ArrayList<Product>();
//        Connection conn = null;
//        PreparedStatement stat1 = null;
//        ResultSet rs1 = null;
//        try {
//            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
//            stat1 = conn.prepareStatement("SELECT product_name, sum(total::integer)as total, sum( sum_amount ) as total_amount FROM listitem_detail GROUP BY product_name");
//            rs1 = stat1.executeQuery();
//            while (rs1.next()) {
//                Product enak = new Product();
//                enak.setProduct_name(rs1.getString("product_name"));
//                enak.setTotal(rs1.getString("total"));
//                enak.setTotal_amount(rs1.getString("total_amount"));
//                System.out.println("ini adalah product : "+rs1.getString("product_name"));
//                System.out.println("ini adalah total : "+rs1.getString("total"));
//                System.out.println("ini adalah total amount : "+rs1.getString("total_amount"));
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        } finally {
//            clearDBConnection(conn);
//            clearStatment(stat1);
//            clearResultset(rs1);
//        }
//        return product;
//    }
    public ArrayList<Product> getAllproduct() throws SQLException {
        ArrayList<Product> ppobList = new ArrayList<Product>();
        Connection conn = null;
        PreparedStatement stat1 = null;
        PreparedStatement stat2 = null;

        ResultSet rs1 = null;
        ResultSet rs2 = null;

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat1 = conn.prepareStatement("SELECT product_name, sum(total::integer)as total, sum( sum_amount ) as total_amount FROM listitem_detail GROUP BY product_name");
            rs1 = stat1.executeQuery();
            while (rs1.next()) {
                Product ppob = new Product();
                ppob.setProduct_name(rs1.getString("product_name"));
                ppob.setTotal(rs1.getString("total"));
                ppob.setTotal_amount(rs1.getString("total_amount"));

                ppobList.add(ppob);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            clearStatment(stat1);
            clearStatment(stat2);

            clearDBConnection(conn);
            clearResultset(rs1);
            clearResultset(rs2);

        }
        return ppobList;
    }

    public ArrayList<merchant> getMerchantstatus() throws SQLException {
        ArrayList<merchant> ppobList = new ArrayList<merchant>();
        Connection conn = null;
        PreparedStatement stat1 = null;
        PreparedStatement stat2 = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat1 = conn.prepareStatement("select instituteid, institutename, posid, poscode, nopd from merchant");
            rs1 = stat1.executeQuery();
            while (rs1.next()) {
                merchant ppob = new merchant();
                ppob.setInstituteid(rs1.getString("instituteid"));
                ppob.setPosid(rs1.getString("posid"));
                ppob.setPoscode(rs1.getString("poscode"));
                ppob.setInstitutename(rs1.getString("institutename"));
                ppob.setNopd(rs1.getString("nopd"));

//                stat2 = conn.prepareStatement("select dt_merc, instituteid, posid, dt_internal from (select dt_merc, instituteid, posid, dt_internal from listtax union all select dt_merc, instituteid, posid, dt_internal from listtax_backup) AS noresi where instituteid='" + rs1.getString("instituteid") + "' and posid='" + rs1.getString("posid") + "' order by dt_merc desc LIMIT 1");
                stat2 = conn.prepareStatement("select instituteid, posid, dt_internal from active where instituteid='" + rs1.getString("instituteid") + "' and posid='" + rs1.getString("posid") + "' and poscode='" + rs1.getString("poscode") + "' order by dt_internal desc LIMIT 1");
                rs2 = stat2.executeQuery();
                if (rs2.next()) {
                    ppob.setStatus("green");
//                    ppob.setDt_merc(rs2.getString("dt_merc"));
                    ppob.setDt_merc(ConvertDateFormat.formatDate(rs2.getString("dt_internal"), "yyyy-MM-dd HH:mm:ss", "dd-MMM-yyyy HH:mm:ss"));
                } else {
                    ppob.setStatus("red");
                    ppob.setDt_merc("-");

                }
                ppobList.add(ppob);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            clearStatment(stat1);
            clearStatment(stat2);
//            clearStatment(stat3);
            clearDBConnection(conn);
            clearResultset(rs1);
            clearResultset(rs2);
//            clearResultset(rs3);
        }
        return ppobList;
    }

    public ArrayList<merchant> getMerchantstatustoday() throws SQLException {
        ArrayList<merchant> ppobList = new ArrayList<merchant>();
        Connection conn = null;
        PreparedStatement stat1 = null;
        PreparedStatement stat2 = null;
//        PreparedStatement stat3 = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
//        ResultSet rs3 = null;

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat1 = conn.prepareStatement("select instituteid, institutename, posid, poscode, nopd from merchant");
            rs1 = stat1.executeQuery();
            while (rs1.next()) {
                merchant ppob = new merchant();
                ppob.setInstituteid(rs1.getString("instituteid"));
                ppob.setPosid(rs1.getString("posid"));
                ppob.setPoscode(rs1.getString("poscode"));
                ppob.setInstitutename(rs1.getString("institutename"));
                ppob.setNopd(rs1.getString("nopd"));
//                stat2 = conn.prepareStatement("select dt_merc, instituteid, posid, dt_internal from (select dt_merc, instituteid, posid, dt_internal from listtax union all select dt_merc, instituteid, posid, dt_internal from listtax_backup) AS noresi where instituteid='" + rs1.getString("instituteid") + "' and posid='" + rs1.getString("posid") + "' and dt_internal > current_date order by posid LIMIT 1");
                stat2 = conn.prepareStatement("select instituteid, posid, dt_internal from active where instituteid='" + rs1.getString("instituteid") + "' and posid='" + rs1.getString("posid") + "' and poscode='" + rs1.getString("poscode") + "' and dt_internal > current_date order by dt_internal desc LIMIT 1");
                rs2 = stat2.executeQuery();
                if (rs2.next()) {
                    ppob.setStatus("green");
//                    ppob.setDt_merc(rs2.getString("dt_merc"));
                    ppob.setDt_merc(ConvertDateFormat.formatDate(rs2.getString("dt_internal"), "yyyy-MM-dd HH:mm:ss", "dd-MMM-yyyy HH:mm:ss"));
                } else {
                    ppob.setStatus("red");
                    ppob.setDt_merc("-");

                }
                ppobList.add(ppob);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            clearStatment(stat1);
            clearStatment(stat2);
//            clearStatment(stat3);
            clearDBConnection(conn);
            clearResultset(rs1);
            clearResultset(rs2);
//            clearResultset(rs3);
        }
        return ppobList;
    }

    public ArrayList<Monitoringppobtoday> getPpobToday() {
        ArrayList<Monitoringppobtoday> ppobtodayList = new ArrayList<Monitoringppobtoday>();
        Connection conn = null;
        PreparedStatement stat1 = null;
        PreparedStatement stat2 = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat1 = conn.prepareStatement("select * from merchant");
            rs1 = stat1.executeQuery();
            while (rs1.next()) {
                Monitoringppobtoday ppobtoday = new Monitoringppobtoday();
                ppobtoday.setInstituteid(rs1.getString("instituteid"));
                ppobtoday.setInstitutename(rs1.getString("institutename"));
                ppobtoday.setPosid(rs1.getString("posid"));
//                conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
//                stat2 = conn.prepareStatement("select count(posid) as transaction, SUM(amount) as amount, SUM(ppn) as ppn from listtax where instituteid='" + rs1.getString("instituteid") + "' and posid='" + rs1.getString("posid") + "' and dt_internal > current_date group by instituteid order by instituteid desc");
                stat2 = conn.prepareStatement("SELECT instituteid, sum(posid)as posid, sum( amount ) as amount, sum(ppn)as ppn FROM (\n"
                        + "SELECT instituteid, count(posid)as posid, sum( amount ) as amount, sum(ppn)as ppn FROM listtax where instituteid='" + rs1.getString("instituteid") + "' and posid='" + rs1.getString("posid") + "' and status_paid=1 and dt_internal > current_date GROUP BY instituteid\n"
                        + "UNION ALL SELECT instituteid, count(posid)as posid, sum( amount ) as amount, sum(ppn)as ppn FROM listtax_backup where instituteid='" + rs1.getString("instituteid") + "' and posid='" + rs1.getString("posid") + "' and status_paid=1 and dt_internal > current_date GROUP BY instituteid) AS jenis GROUP BY instituteid");
                rs2 = stat2.executeQuery();
                if (rs2.next()) {
                    ppobtoday.setTransaction(rs2.getString("posid"));
                    ppobtoday.setAmount(rs2.getString("amount"));
                    ppobtoday.setPpn(rs2.getString("ppn"));

                } else {
                    ppobtoday.setTransaction("0");
                    ppobtoday.setAmount("0");
                    ppobtoday.setPpn("0");
                }
                ppobtodayList.add(ppobtoday);
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            clearStatment(stat1);
            clearStatment(stat2);
            clearDBConnection(conn);
            clearResultset(rs1);
            clearResultset(rs2);
        }
        return ppobtodayList;
    }

    public ArrayList<Monitoringppobmonth> getPpobMonth() {
//        String tanggal = "-12";
        ArrayList<Monitoringppobmonth> ppobmonthList = new ArrayList<Monitoringppobmonth>();
        Connection conn = null;
        PreparedStatement stat1 = null;
        PreparedStatement stat2 = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;

        Calendar now = Calendar.getInstance();
        int jumlah_hari_bulan = now.get(Calendar.DAY_OF_MONTH);
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat1 = conn.prepareStatement("select * from merchant");
            rs1 = stat1.executeQuery();
            while (rs1.next()) {
                Monitoringppobmonth ppobmonth = new Monitoringppobmonth();
                ppobmonth.setInstituteid(rs1.getString("instituteid"));
                ppobmonth.setInstitutename(rs1.getString("institutename"));
                ppobmonth.setPosid(rs1.getString("posid"));
//                conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
//                stat2 = conn.prepareStatement("select count(posid) as transaction, SUM(amount) as amount, SUM(ppn) as ppn from listtax where instituteid='"+rs1.getString("instituteid")+"' and posid='" + rs1.getString("posid") + "' group by instituteid order by instituteid desc");
                stat2 = conn.prepareStatement("SELECT instituteid, sum(posid)as posid, sum( amount ) as amount, sum(ppn)as ppn FROM (\n"
                        + "SELECT instituteid, count(posid)as posid, sum( amount ) as amount, sum(ppn)as ppn FROM listtax where instituteid='" + rs1.getString("instituteid") + "' and posid='" + rs1.getString("posid") + "' and status_paid=1 and dt_internal > current_date -" + (jumlah_hari_bulan - 1) + " GROUP BY instituteid\n"
                        + "UNION ALL SELECT instituteid, count(posid)as posid, sum( amount ) as amount, sum(ppn)as ppn FROM listtax_backup where instituteid='" + rs1.getString("instituteid") + "' and posid='" + rs1.getString("posid") + "' and status_paid=1 and dt_internal > current_date -" + (jumlah_hari_bulan - 1) + " GROUP BY instituteid) AS jenis GROUP BY instituteid");

                rs2 = stat2.executeQuery();
                if (rs2.next()) {
                    ppobmonth.setTransaction(rs2.getString("posid"));
                    ppobmonth.setAmount(rs2.getString("amount"));
                    ppobmonth.setPpn(rs2.getString("ppn"));
                } else {
                    ppobmonth.setTransaction("0");
                    ppobmonth.setAmount("0");
                    ppobmonth.setPpn("0");
                }
                ppobmonthList.add(ppobmonth);
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            clearStatment(stat1);
            clearStatment(stat2);
            clearDBConnection(conn);
            clearResultset(rs1);
            clearResultset(rs2);
        }
        return ppobmonthList;
    }
//

    public ArrayList<Monitoringppobyear> getPpobYear() {
        ArrayList<Monitoringppobyear> ppobyearList = new ArrayList<Monitoringppobyear>();
        Connection conn = null;
        PreparedStatement stat1 = null;
        PreparedStatement stat2 = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;

        Calendar now = Calendar.getInstance();
        int jumlah_hari_tahun = now.get(Calendar.DAY_OF_YEAR);

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat1 = conn.prepareStatement("select * from merchant");
            rs1 = stat1.executeQuery();
            while (rs1.next()) {
                Monitoringppobyear ppobyear = new Monitoringppobyear();
                ppobyear.setInstituteid(rs1.getString("instituteid"));
                ppobyear.setInstitutename(rs1.getString("institutename"));
                ppobyear.setPosid(rs1.getString("posid"));
//                conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
//                stat2 = conn.prepareStatement("select count(posid) as transaction, SUM(amount) as amount, SUM(ppn) as ppn from listtax where instituteid='" + rs1.getString("instituteid") + "' and posid='" + rs1.getString("posid") + "' group by instituteid order by instituteid desc");
                stat2 = conn.prepareStatement("SELECT instituteid, sum(posid)as posid, sum( amount ) as amount, sum(ppn)as ppn FROM (\n"
                        + "SELECT instituteid, count(posid)as posid, sum( amount ) as amount, sum(ppn)as ppn FROM listtax where instituteid='" + rs1.getString("instituteid") + "' and posid='" + rs1.getString("posid") + "' and status_paid=1 and dt_internal > current_date -" + (jumlah_hari_tahun - 1) + " GROUP BY instituteid\n"
                        + "UNION ALL SELECT instituteid, count(posid)as posid, sum( amount ) as amount, sum(ppn)as ppn FROM listtax_backup where instituteid='" + rs1.getString("instituteid") + "' and posid='" + rs1.getString("posid") + "' and status_paid=1 and dt_internal > current_date -" + (jumlah_hari_tahun - 1) + " GROUP BY instituteid) AS jenis GROUP BY instituteid");
                rs2 = stat2.executeQuery();
                if (rs2.next()) {
                    ppobyear.setTransaction(rs2.getString("posid"));
                    ppobyear.setAmount(rs2.getString("amount"));
                    ppobyear.setPpn(rs2.getString("ppn"));
                } else {
                    ppobyear.setTransaction("0");
                    ppobyear.setAmount("0");
                    ppobyear.setPpn("0");
                }
                ppobyearList.add(ppobyear);
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            clearStatment(stat1);
            clearStatment(stat2);
            clearDBConnection(conn);
            clearResultset(rs1);
            clearResultset(rs2);
        }
        return ppobyearList;
    }

    public HashMap getgrap() {
        System.out.println("heheheh");
        HashMap result = new HashMap();
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        PreparedStatement stat1 = null;
        ResultSet rs1 = null;

        PreparedStatement stat2 = null;
        ResultSet rs2 = null;
        PreparedStatement stat3 = null;
        ResultSet rs3 = null;

        result.put("resp_code", "0001");
        result.put("resp_desc", "Failed");
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();

//            stat = conn.prepareStatement("select distinct TO_CHAR(dt_internal, 'YYYY-MM-DD') as tgl, sum(amount)as amount, sum(ppn)as ppn from listtax_backup where dt_internal>=(to_char(now()::date,'YYYY-MM-01'))::date group by tgl order by TO_CHAR(dt_internal, 'YYYY-MM-DD') desc");
            stat = conn.prepareStatement("SELECT distinct tgl, sum(amount)as amount, sum(ppn)as ppn FROM (\n"
                    + "   SELECT distinct TO_CHAR(dt_internal, 'YYYY-MM-DD') as tgl, sum(amount)as amount, sum(ppn)as ppn FROM listtax group by tgl\n"
                    + "   UNION ALL SELECT TO_CHAR(dt_internal, 'YYYY-MM-DD') as tgl, sum(amount)as amount, sum(ppn)as ppn FROM listtax_backup group by tgl) AS jenis GROUP BY tgl order by tgl desc");

            rs = stat.executeQuery();
            ArrayList day_trx = new ArrayList();
            ArrayList day_amount = new ArrayList();
            ArrayList day_sumtrx = new ArrayList();
            while (rs.next()) {
                day_trx.add(rs.getString("tgl"));
                day_amount.add(rs.getInt("amount"));
                day_sumtrx.add(rs.getString("ppn"));
                result.put("tgl", day_trx);
                result.put("amount", day_amount);
                result.put("ppn", day_sumtrx);

                stat1 = conn.prepareStatement("SELECT product_name, sum(total::integer)as total, sum( sum_amount ) as total_amount FROM listitem_detail GROUP BY product_name order by total desc limit 5");
                rs1 = stat1.executeQuery();
                ArrayList prod_name = new ArrayList();
                ArrayList total = new ArrayList();
                while (rs1.next()) {
                    prod_name.add(rs1.getString("product_name"));
                    total.add(rs1.getInt("total"));
                    result.put("prod_name", prod_name);
                    result.put("total", total);
/////////////////////////////////////////////////////////////////////////////////

                    ArrayList instiid = new ArrayList();
                    ArrayList ppn1 = new ArrayList();
                    stat3 = conn.prepareStatement("SELECT instituteid, sum(ppn)as ppn FROM (\n"
                            + "          SELECT instituteid, sum(ppn)as ppn FROM listtax where status_paid=1 GROUP BY instituteid\n"
                            + "          UNION ALL SELECT instituteid, sum(ppn)as ppn FROM listtax_backup where status_paid=1 GROUP BY instituteid) AS jenis GROUP BY instituteid");
                    rs3 = stat3.executeQuery();
                    while (rs3.next()) {
                        stat2 = conn.prepareStatement("SELECT institutename from merchant where instituteid = ?");
                        stat2.setString(1, rs3.getString("instituteid"));
                        rs2 = stat2.executeQuery();
                        while (rs2.next()) {
                            instiid.add(rs2.getString("institutename"));
                            result.put("instituteid", instiid);
                        }
                        ppn1.add(rs3.getInt("ppn"));
                        result.put("ppn1", ppn1);
                    }
/////////////////////////////////////////////////////////////////////////////////
                }
                result.put("resp_code", "0000");
                result.put("resp_desc", "Success");
            }
        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
            return result;
        } finally {
            clearAllConnStatRS(conn, stat, rs);
            clearAllConnStatRS(conn, stat1, rs1);

        }
        return result;
    }

//    public HashMap getDonut() {
//        HashMap result = new HashMap();
//        Connection conn = null;
//        PreparedStatement stat = null;
//        ResultSet rs = null;
//        
//        result.put("resp_code", "0001");
//        result.put("resp_desc", "Failed");
//        try {
//            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
//            
//            stat = conn.prepareStatement("SELECT product_name, sum(total::integer)as total, sum( sum_amount ) as total_amount FROM listitem_detail GROUP BY product_name order by total desc");
//            rs = stat.executeQuery();
//            ArrayList prod_name = new ArrayList();
//            ArrayList total = new ArrayList();
//            while (rs.next()) {
//                prod_name.add(rs.getString("product_name").toString());
//                total.add(rs.getInt("total"));
//                result.put("prod_name", prod_name);
//                result.put("total", total);
//                
//                result.put("resp_code", "0000");
//                result.put("resp_desc", "Success");
//            }
//        } catch (SQLException e) {
//            System.out.println(e);
//            e.printStackTrace();
//            return result;
//        } finally {
//            clearAllConnStatRS(conn, stat, rs);
//        }
//        return result;
//    }
}
