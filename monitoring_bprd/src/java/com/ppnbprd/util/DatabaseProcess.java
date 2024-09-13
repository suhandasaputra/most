/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ppnbprd.util;

import com.ppn.entity.ConvertDateFormat;
import com.ppn.model.Report;
import com.ppn.model.merchant;
import com.ppnbprd.servlet.DatasourceEntry;
import com.ppnlib.function.JsonProcess;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    
    public ArrayList<Report> getAlltransaction() throws ParseException {
        ArrayList<Report> listTransaction = new ArrayList<Report>();
        SimpleDateFormat requesttime1 = new SimpleDateFormat("ddMMyyyyHHmmss");
        SimpleDateFormat requesttime3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat requesttime2 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
        Connection conn = null;
        PreparedStatement stat1 = null;
        ResultSet rs1 = null;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat1 = conn.prepareStatement("select * from (select * from bprd union all select * from bprd_backup ) as id");
            rs1 = stat1.executeQuery();
            while (rs1.next()) {
                Report report = new Report();
                if (rs1.getString("id") == null) {
                    report.setId("-");
                } else {
                    report.setId(rs1.getString("id"));
                }
                if (rs1.getString("tanggal_trx") == null) {
                    report.setTanggal_trx("-");
                } else {
                    Date responsetime = requesttime1.parse(rs1.getString("tanggal_trx"));
                    report.setTanggal_trx(requesttime2.format(responsetime));
                }
                if (rs1.getString("no_struk") == null) {
                    report.setNo_struk("-");
                } else {
                    report.setNo_struk(rs1.getString("no_struk"));
                }
                if (rs1.getString("description") == null) {
                    report.setDescription("-");
                } else {
//                    report.setDescription(rs1.getString("description"));
//
                    report.setDescription("");
                    
                    JSONArray listdetail = null;
                    JSONParser parser = new JSONParser();
                    
                    try {
                        listdetail = (JSONArray) parser.parse(rs1.getString("description"));
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
                        report.setDescription(detail);
                        
                    }
                }
                if (rs1.getString("amount_trx") == null) {
                    report.setAmount_trx("-");
                } else {
                    report.setAmount_trx(rs1.getString("amount_trx"));
                }
                if (rs1.getString("nopd") == null) {
                    report.setNopd("-");
                } else {
                    report.setNopd(rs1.getString("nopd"));
                }
                
                if (rs1.getString("source") == null) {
                    report.setSource("-");
                } else {
                    report.setSource(rs1.getString("source"));
                }
                listTransaction.add(report);
            }
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clearDBConnection(conn);
            clearStatment(stat1);
            clearResultset(rs1);
        }
        return listTransaction;
    }
}
