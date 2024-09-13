/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ppnlib.database;

//import com.ppn.model.Report;
import com.ppnlib.entity.MerchantEntity;
import com.ppnlib.entity.Tempmsg;
import com.ppnlib.function.JsonProcess;
import com.ppnlib.function.SHA256Enc;
import com.ppnlib.function.StringFunction;
import com.ppnlib.singleton.DatasourceEntry;
import com.ppnlib.singleton.ResponseCodeSingleton;
import com.ppnlib.parameter.RuleNameParameter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author herrysuganda
 */
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

    public boolean checkingResi(HashMap hmData) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String noresi = "";
        String splitbill = "";

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
//            stat = conn.prepareStatement("select * from (select noresi from listtax union all select noresi from listtax_backup ) AS noresi where noresi= ? ");
            stat = conn.prepareStatement("select * from (select noresi, split_bill from listtax union all select noresi, split_bill from listtax_backup ) AS noresi where noresi= ? AND split_bill = ?");
            stat.setString(1, hmData.get(RuleNameParameter.NO_RESI).toString());
            stat.setInt(2, Integer.valueOf(hmData.get(RuleNameParameter.SPLIT_BILL).toString()));
            rs = stat.executeQuery();
            while (rs.next()) {
                noresi = rs.getString("noresi");
                splitbill = rs.getString("split_bill");
            }
            if (hmData.get(RuleNameParameter.NO_RESI).toString().equals(noresi)) {
                if (hmData.get(RuleNameParameter.SPLIT_BILL).toString().equals(splitbill)) {
                    return false;
                }
            }
        } catch (SQLException ex) {
            System.out.println("checkingResi :" + ex.getMessage());
            log.error("checkingResi : " + ex.getMessage());
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return true;
    }

    public boolean checkingCustomer(HashMap hmData) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String customer = "";
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("SELECT customer_id FROM customer WHERE customer_id = ? ");
            stat.setString(1, hmData.get(RuleNameParameter.CUSTOMER_ID).toString());
            rs = stat.executeQuery();
            while (rs.next()) {
                customer = rs.getString("customer_id");
            }
            if (hmData.get(RuleNameParameter.CUSTOMER_ID).toString().equals(customer)) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("checkingCustomer :" + ex.getMessage());
            log.error("checkingCustomer : " + ex.getMessage());
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return false;
    }

    public boolean checkingBalance(HashMap hmData) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        PreparedStatement stat1 = null;
        ResultSet rs1 = null;
        String noresi = hmData.get(RuleNameParameter.NO_RESI).toString();
        int amount = 0;
        int customer_balance = 0;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("select amount, noresi from (select amount, noresi from listtax union all select amount, noresi from listtax_backup ) AS amount WHERE noresi = ?");
            stat.setString(1, noresi);
            rs = stat.executeQuery();
            while (rs.next()) {
                amount = rs.getInt("amount");
                stat1 = conn.prepareStatement("SELECT * FROM customer WHERE customer_id = ? ");
                stat1.setString(1, hmData.get(RuleNameParameter.CUSTOMER_ID).toString());
                rs1 = stat1.executeQuery();
                while (rs1.next()) {
                    customer_balance = rs1.getInt("customer_balance");
                    if (customer_balance >= amount) {
                        return true;
                    }
                }
            }

        } catch (SQLException ex) {
            System.out.println("checkingBalance :" + ex.getMessage());
            log.error("checkingBalance : " + ex.getMessage());
        } finally {
            clearAllConnStatRS(conn, stat, rs);
            clearAllConnStatRS(conn, stat1, rs1);

        }
        return false;
    }

    public HashMap cekBal(HashMap inputMap) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("SELECT * FROM customer WHERE customer_id=?");
            stat.setString(1, inputMap.get(RuleNameParameter.CUSTOMER_ID).toString());
            rs = stat.executeQuery();
            while (rs.next()) {
                inputMap.put(RuleNameParameter.CUSTOMER_BALANCE, rs.getString("customer_balance"));
            }
        } catch (SQLException ex) {
            log.error("cekBal : " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return inputMap;
    }

    public boolean checkingStatusPaid(HashMap hmData) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        int status_paid = 0;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("select noresi, status_paid from (select noresi, status_paid from listtax union all select noresi, status_paid from listtax_backup ) AS status_paid WHERE noresi = ?");
            stat.setString(1, hmData.get(RuleNameParameter.NO_RESI).toString());
            rs = stat.executeQuery();
            while (rs.next()) {
                status_paid = rs.getInt("status_paid");
                if (status_paid == 0) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            System.out.println("checkingStatusPaid :" + ex.getMessage());
            log.error("checkingStatusPaid : " + ex.getMessage());
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return false;
    }

    public boolean checkingResiOnListVoid(HashMap hmData) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String noresi = "";
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("SELECT * FROM listvoid WHERE noresi = ? ");
            stat.setString(1, hmData.get(RuleNameParameter.NO_RESI).toString());
            rs = stat.executeQuery();
            while (rs.next()) {
                noresi = rs.getString("noresi");
            }
            if (hmData.get(RuleNameParameter.NO_RESI).toString().equals(noresi)) {
                return false;
            }
        } catch (SQLException ex) {
            System.out.println("checkingResiOnListVoid :" + ex.getMessage());
            log.error("checkingResiOnListVoid : " + ex.getMessage());
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return true;
    }

    public boolean checkingResiOnListTax(HashMap hmData) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String noresi = "";
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();

            stat = conn.prepareStatement("select * from (select noresi from listtax union all select noresi from listtax_backup ) AS noresi where noresi = ? ");
            stat.setString(1, hmData.get(RuleNameParameter.NO_RESI).toString());
            rs = stat.executeQuery();
            while (rs.next()) {
                noresi = rs.getString("noresi");
            }
            if (hmData.get(RuleNameParameter.NO_RESI).toString().equals(noresi)) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("checkingResiOnListTax :" + ex.getMessage());
            log.error("checkingResiOnListTax : " + ex.getMessage());
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return false;
    }

    public boolean checkingMerchant(HashMap hmData) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String merchant = "";
//         System.out.println("tes bray");
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("SELECT instituteid FROM merchant WHERE instituteid = ? ");
            stat.setString(1, hmData.get(RuleNameParameter.INSTITUTE_ID).toString());
            rs = stat.executeQuery();
            while (rs.next()) {
                merchant = rs.getString("instituteid");
//                System.out.println("ini adalah institusi id :"+rs.getString("instituteid"));
            }
            if (hmData.get(RuleNameParameter.INSTITUTE_ID).toString().equals(merchant)) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("checkingMerchant :" + ex.getMessage());
            log.error("checkingMerchant : " + ex.getMessage());
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return false;
    }

    public boolean checkingSerialKey(HashMap hmData) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String keynya = "";
//         System.out.println("tes bray");
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("SELECT key FROM merchant WHERE instituteid = ? and posid = ? and poscode = ? and key = ?");
            stat.setString(1, hmData.get(RuleNameParameter.INSTITUTE_ID).toString());
            stat.setString(2, hmData.get(RuleNameParameter.POS_ID).toString());
            stat.setString(3, hmData.get(RuleNameParameter.POS_CODE).toString());
            stat.setString(4, hmData.get(RuleNameParameter.SERIAL_KEY).toString());
//            System.out.println(hmData.get(RuleNameParameter.INSTITUTE_ID).toString()+" "+hmData.get(RuleNameParameter.POS_ID).toString()+" "+hmData.get(RuleNameParameter.POS_CODE).toString()+" "+hmData.get(RuleNameParameter.SERIAL_KEY).toString());

            rs = stat.executeQuery();
            while (rs.next()) {
                keynya = rs.getString("key");
//                System.out.println("ini adalah institusi id :"+rs.getString("instituteid"));
            }
            if (hmData.get(RuleNameParameter.SERIAL_KEY).toString().equals(keynya)) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("checkingSerialKey :" + ex.getMessage());
            log.error("checkingSerialKey : " + ex.getMessage());
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return false;
    }

    public boolean checkingPOS(HashMap hmData) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String pos = "";

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("SELECT posid FROM merchant WHERE posid = ? and instituteid = ? ");
            stat.setString(1, hmData.get(RuleNameParameter.POS_ID).toString());
            stat.setString(2, hmData.get(RuleNameParameter.INSTITUTE_ID).toString());
//            stat.setString(3, hmData.get(RuleNameParameter.SERIAL_KEY).toString());

            rs = stat.executeQuery();
            while (rs.next()) {
                pos = rs.getString("posid");
            }
            if (hmData.get(RuleNameParameter.POS_ID).toString().equals(pos)) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("checkingPOS :" + ex.getMessage());
            log.error("checkingPOS : " + ex.getMessage());
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return false;
    }

    public boolean checkingPoscode(HashMap hmData) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String poscode = "";

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("SELECT poscode FROM merchant WHERE poscode = ? and instituteid = ? and posid = ?");
            stat.setString(1, hmData.get(RuleNameParameter.POS_CODE).toString());
            stat.setString(2, hmData.get(RuleNameParameter.INSTITUTE_ID).toString());
            stat.setString(3, hmData.get(RuleNameParameter.POS_ID).toString());
//            stat.setString(4, hmData.get(RuleNameParameter.SERIAL_KEY).toString());

            rs = stat.executeQuery();
            while (rs.next()) {
                poscode = rs.getString("poscode");
            }
            if (hmData.get(RuleNameParameter.POS_CODE).toString().equals(poscode)) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("checkingPoscode :" + ex.getMessage());
            log.error("checkingPoscode : " + ex.getMessage());
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return false;
    }

    public boolean checkingEcho(HashMap hmData) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String merchant = "";
        String posid = "";
        String poscode = "";
//        String key = "";
        String dt_merc = "";
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("select * from active WHERE instituteid =? and posid =? and poscode =? and dt_merc =?");
//            stat = conn.prepareStatement("SELECT * FROM listtax WHERE instituteid = ? and posid = ? and poscode = ? and key = ? ");
            stat.setString(1, hmData.get(RuleNameParameter.INSTITUTE_ID).toString());
            stat.setString(2, hmData.get(RuleNameParameter.POS_ID).toString());
            stat.setString(3, hmData.get(RuleNameParameter.POS_CODE).toString());
            stat.setString(4, hmData.get(RuleNameParameter.TRX_DATE).toString());
            rs = stat.executeQuery();
            while (rs.next()) {
                merchant = rs.getString("instituteid");
                posid = rs.getString("posid");
                poscode = rs.getString("poscode");
//                key = rs.getString("key");
                dt_merc = rs.getString("dt_merc");
            }
            if (hmData.get(RuleNameParameter.INSTITUTE_ID).toString().equals(merchant)) {
                if (hmData.get(RuleNameParameter.POS_ID).toString().equals(posid)) {
                    if (hmData.get(RuleNameParameter.POS_CODE).toString().equals(poscode)) {
//                        if (hmData.get(RuleNameParameter.SERIAL_KEY).toString().equals(key)) {
                        if (hmData.get(RuleNameParameter.TRX_DATE).toString().equals(dt_merc)) {
//                                System.out.println("oooooooooooooooooo");
                            return false;
                        }
//                        }
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("checkingEcho :" + ex.getMessage());
            log.error("checkingEcho : " + ex.getMessage());
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return true;
    }

    public boolean checkingMerchantPosidPosCodeKey(HashMap hmData) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String merchant = "";
        String posid = "";
        String poscode = "";
        String key = "";

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("select * from (select * from listtax union all select * from listtax_backup ) AS noresi WHERE instituteid =? and posid =? and poscode =? and key =?");
//            stat = conn.prepareStatement("SELECT * FROM listtax WHERE instituteid = ? and posid = ? and poscode = ? and key = ? ");
            stat.setString(1, hmData.get(RuleNameParameter.INSTITUTE_ID).toString());
            stat.setString(2, hmData.get(RuleNameParameter.POS_ID).toString());
            stat.setString(3, hmData.get(RuleNameParameter.POS_CODE).toString());
            stat.setString(4, hmData.get(RuleNameParameter.SERIAL_KEY).toString());
            rs = stat.executeQuery();
            while (rs.next()) {
                merchant = rs.getString("instituteid");
                posid = rs.getString("posid");
                poscode = rs.getString("poscode");
                key = rs.getString("key");
            }
            if (hmData.get(RuleNameParameter.INSTITUTE_ID).toString().equals(merchant)) {
                if (hmData.get(RuleNameParameter.POS_ID).toString().equals(posid)) {
                    if (hmData.get(RuleNameParameter.POS_CODE).toString().equals(poscode)) {
                        if (hmData.get(RuleNameParameter.SERIAL_KEY).toString().equals(key)) {
                            return true;
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("checkingPoscode :" + ex.getMessage());
            log.error("checkingPoscode : " + ex.getMessage());
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return false;
    }

    public boolean userCustomerCheck(HashMap hmData) {
        System.out.println("gagagaga");
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String passdb = "";
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("SELECT * FROM ppn_user where disp_user = ?");
            stat.setString(1, hmData.get(RuleNameParameter.DISP_USER).toString());
            rs = stat.executeQuery();
            while (rs.next()) {
                passdb = rs.getString("disp_pass");
            }
            if (hmData.get(RuleNameParameter.DISP_PASS).toString().equals(passdb)) {
                return true;
            }
        } catch (SQLException ex) {
            log.error("userCustomerCheck : " + ex.getMessage());
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return false;
    }

    public boolean userMerchantCheck(HashMap hmData) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String passdb = "";
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("SELECT * FROM merchant where instituteid = ?");
            stat.setString(1, hmData.get(RuleNameParameter.DISP_USER).toString());
            rs = stat.executeQuery();
            while (rs.next()) {
                passdb = SHA256Enc.encryptProc(rs.getString("nopd"));
            }
            if (hmData.get(RuleNameParameter.DISP_PASS).toString().equals(passdb)) {
                return true;
            }
        } catch (SQLException ex) {
            log.error("userCustomerCheck : " + ex.getMessage());
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return false;
    }

    public boolean noresiChecking(HashMap inputMap) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String noresi = "";
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("SELECT * FROM listtrx where rrn = ?");
            stat.setString(1, inputMap.get(RuleNameParameter.NO_RESI).toString());
            rs = stat.executeQuery();
            while (rs.next()) {
                noresi = rs.getString("noresi");
            }
            if (inputMap.get(RuleNameParameter.NO_RESI).toString().equals(noresi)) {
                return false;
            }
        } catch (SQLException ex) {
            System.out.println("noresiChecking :" + ex.getMessage());
            log.error("noresiChecking : " + ex.getMessage());
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return true;
    }

    public HashMap getAllTransaction(HashMap inputMap) {
        Connection conn = null;
        PreparedStatement stat2 = null;
        PreparedStatement stat1 = null;
        ResultSet rs2 = null;
        ResultSet rs1 = null;
        List resultRow = new ArrayList();
        List resultColumn = new ArrayList();
        List list = new ArrayList();
        SimpleDateFormat requesttime1 = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat requesttime3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        SimpleDateFormat requesttime2 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat1 = conn.prepareStatement("select * from (select * from listtax union all select * from listtax_backup ) as noresi");
            rs1 = stat1.executeQuery();
            while (rs1.next()) {
                resultColumn.add(rs1.getString("noresi"));
                resultColumn.add(rs1.getString("split_bill"));
                resultRow.add(StringUtils.join(resultColumn, "|"));
                resultColumn.clear();
            }
            list.add(StringUtils.join(resultRow, ";"));
            inputMap.put("list", list.toString());
//            inputMap.put(RuleNameParameter.RESP_CODE, "0000");
//            inputMap.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get("0000").toString());
        } catch (SQLException ex) {
            log.error("getReport : " + ex.getMessage());
        } finally {
            clearDBConnection(conn);
            clearStatment(stat1);
            clearStatment(stat2);
            clearResultset(rs1);
            clearResultset(rs2);
        }
        return inputMap;
    }

    public HashMap getDetailResi(HashMap inputMap) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        PreparedStatement stat1 = null;
        ResultSet rs1 = null;
        ArrayList resultColumn = new ArrayList();
        ArrayList resultRow = new ArrayList();
        ArrayList list = new ArrayList();
        String noresi = inputMap.get(RuleNameParameter.NO_RESI).toString();
        String splitbill = inputMap.get(RuleNameParameter.SPLIT_BILL).toString();
        inputMap.put(RuleNameParameter.RESP_CODE, "2008");
        inputMap.put(RuleNameParameter.RESP_DESC, "Data transaksi tidak ditemukan / mohon teliti kembali");

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("SELECT * FROM (SELECT * FROM listtax union all SELECT * FROM listtax_backup)AS noresi where noresi = ? and split_bill = ? ");
            stat.setString(1, noresi);
            stat.setInt(2, Integer.valueOf(splitbill));
//            stat.setInt(3, 1);
            rs = stat.executeQuery();
            while (rs.next()) {
                stat1 = conn.prepareStatement("select * from merchant where instituteid = '" + rs.getString("instituteid") + "'");
                rs1 = stat1.executeQuery();
                while (rs1.next()) {
                    inputMap.put(RuleNameParameter.SPLIT_BILL, rs.getString("split_bill"));
                    inputMap.put(RuleNameParameter.TRX_DATE, rs.getString("dt_merc"));
                    inputMap.put(RuleNameParameter.AMOUNT, rs.getLong("amount"));
                    inputMap.put(RuleNameParameter.PPN, rs.getString("ppn"));
                    inputMap.put(RuleNameParameter.SERVICE_TAX, rs.getString("service_tax"));
                    inputMap.put(RuleNameParameter.DETAIL, rs.getString("detail"));
                    inputMap.put(RuleNameParameter.INSTITUTE_ID, rs.getString("instituteid"));
                    inputMap.put(RuleNameParameter.INSTITUTE_NAME, rs1.getString("institutename"));
                    inputMap.put(RuleNameParameter.POS_ID, rs.getString("posid"));
                    inputMap.put(RuleNameParameter.POS_CODE, rs.getString("poscode"));
                    inputMap.put(RuleNameParameter.PAYMENT_METHODE, rs.getString("payment_methode"));
                    inputMap.put(RuleNameParameter.CARD_NUMBER, rs.getString("card_number"));
                    inputMap.put(RuleNameParameter.NOPD, rs.getString("nopd"));
                    inputMap.put(RuleNameParameter.SOURCE, rs.getString("source"));
                    inputMap.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get("0000").toString());
                    inputMap.put(RuleNameParameter.RESP_CODE, "0000");
                }

            }

        } catch (SQLException ex) {
            log.error("getDetailResi2 : " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return inputMap;
    }

    public String getNextStan() {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        int currentStan = 1;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("SELECT stanno FROM stanmanage where standate = current_date");
            rs = stat.executeQuery();
            while (rs.next()) {
                currentStan = rs.getInt("stanno");
            }
            clearStatment(stat);
            stat = conn.prepareStatement("UPDATE stanmanage SET stanno=? WHERE standate=current_date;");
            stat.setInt(1, currentStan + 1);
            stat.executeUpdate();
        } catch (SQLException ex) {
            log.error("getNextStan : " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return String.valueOf(currentStan);
    }

    public Integer getIntervalCheckPaycode() {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        int days = 0;
        int hours = 0;
        int minutes = 0;
        int interval = 0;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("SELECT * FROM param_interval where id = 1");
            rs = stat.executeQuery();
            while (rs.next()) {
                days = Integer.valueOf(rs.getString("days")) * 1000 * 60 * 60 * 24; //convert to milisc
                hours = Integer.valueOf(rs.getString("hours")) * 1000 * 60 * 60; //convert to milisc
                minutes = Integer.valueOf(rs.getString("minutes")) * 1000 * 60; //convert to milisc

            }
            clearStatment(stat);
            interval = days + hours + minutes;
        } catch (SQLException ex) {
            log.error("getInterval Paycode checker : " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return interval;
    }

    public String getPaycodeLifeTime() {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String days = "0";
        String hours = "0";
        String minutes = "0";
        String ExpiredTime = "1days 0hours 0minutes";
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("SELECT * FROM param_interval where id = 2");
            rs = stat.executeQuery();
            while (rs.next()) {
                days = rs.getString("days");
                hours = rs.getString("hours");
                minutes = rs.getString("minutes");
            }
            clearStatment(stat);
            ExpiredTime = days + "days " + hours + "hours " + minutes + "minutes ";
        } catch (SQLException ex) {
            log.error("getInterval Paycode LifeTime : " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return ExpiredTime;
    }

    public MerchantEntity getMerchantDetail(String merchantId) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        MerchantEntity merchant = new MerchantEntity();
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("SELECT merc_pass, merc_key, merc_status, url_confirm FROM merchant where merc_id = ?");
            stat.setString(1, merchantId);
            rs = stat.executeQuery();
            while (rs.next()) {
                merchant.setMerchantKey(rs.getString("merc_key"));
                merchant.setMerchantPass(rs.getString("merc_pass"));
                merchant.setMerchantStatus(rs.getString("merc_status"));
                merchant.setMerchantUrl(rs.getString("url_confirm"));
            }
        } catch (SQLException ex) {
            log.error("getMerchantDetail : " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return merchant;
    }

    public String getPayCode(HashMap inputMap) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        MerchantEntity merchant = new MerchantEntity();
        String paycode = "";
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("SELECT pay_code FROM billing where unique_code=? and dt_merc=? and amount=? and merc_id=?");
            stat.setString(1, inputMap.get(RuleNameParameter.UNIQ_CODE).toString());
            stat.setString(2, inputMap.get(RuleNameParameter.TRX_DATE).toString());
            stat.setDouble(3, Double.valueOf(inputMap.get(RuleNameParameter.AMOUNT).toString()));
            stat.setString(4, inputMap.get(RuleNameParameter.MERC_CODE).toString());
            rs = stat.executeQuery();
            while (rs.next()) {
                paycode = rs.getString("pay_code");
            }
        } catch (SQLException ex) {
            log.error("getPayCode : " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return paycode;
    }

    public boolean getStatusPaymentPayCode(HashMap inputMap) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        MerchantEntity merchant = new MerchantEntity();
        String paycode = "";
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("SELECT pay_status FROM billing where dt_merc=? and merc_id=? and pay_code=?");
            stat.setString(1, inputMap.get(RuleNameParameter.TRX_DATE).toString());
            stat.setString(2, inputMap.get(RuleNameParameter.MERC_CODE).toString());
            stat.setString(3, inputMap.get(RuleNameParameter.NO_RESI).toString());
            rs = stat.executeQuery();
            while (rs.next()) {
                return rs.getBoolean("pay_status");
            }
        } catch (SQLException ex) {
            log.error("getStatusPaymentPayCode : " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return false;
    }

    public boolean echoMessage(HashMap hmData) throws ParseException {
        Connection conn = null;
        PreparedStatement stat = null;
        PreparedStatement stat1 = null;
//        ResultSet rs = null;
//        String dt_merc = hmData.get(RuleNameParameter.TRX_DATE).toString();
        String instituteid = hmData.get(RuleNameParameter.INSTITUTE_ID).toString();
        String posid = hmData.get(RuleNameParameter.POS_ID).toString();
        String poscode = hmData.get(RuleNameParameter.POS_CODE).toString();
        String key = hmData.get(RuleNameParameter.SERIAL_KEY).toString();

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("INSERT INTO active (instituteid, posid, poscode, key) VALUES (?, ?, ?, ?)");
//            stat.setString(1, dt_merc);
            stat.setString(1, instituteid);
            stat.setString(2, posid);
            stat.setString(3, poscode);
            stat.setString(4, key);
            stat.executeUpdate();
            clearStatment(stat);
        } catch (SQLException ex) {
            log.error("echoMessage : " + ex.getMessage());
            ex.printStackTrace();
            return false;
        } finally {
            clearStatment(stat);
            clearDBConnection(conn);
        }
        return true;
    }

    public boolean saveNewPaycode(HashMap hmData) throws ParseException, java.text.ParseException {
        Connection conn = null;
        PreparedStatement stat = null;
        PreparedStatement stat1 = null;
        PreparedStatement stat2 = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        String nopd = "";
        JSONArray listdetail = null;
        JSONParser parser = new JSONParser();
        String amount_trx = hmData.get(RuleNameParameter.AMOUNT).toString();
        String noresi = hmData.get(RuleNameParameter.NO_RESI).toString();
        String id = hmData.get(RuleNameParameter.NO_RESI).toString()
                + hmData.get(RuleNameParameter.SPLIT_BILL).toString();

        String unqueId = hmData.get(RuleNameParameter.INSTITUTE_ID).toString()
                + hmData.get(RuleNameParameter.POS_ID).toString()
                + hmData.get(RuleNameParameter.POS_CODE).toString();
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat2 = conn.prepareStatement("SELECT nopd from merchant where instituteid = ?");
            stat2.setString(1, hmData.get(RuleNameParameter.INSTITUTE_ID).toString());
            rs2 = stat2.executeQuery();
            while (rs2.next()) {
                nopd = rs2.getString("nopd");
            }
            stat = conn.prepareStatement("INSERT INTO listtax(noresi, unique_code, dt_merc, amount, ppn, detail, "
                    + "instituteid, posid, poscode, rrn, proc_code, key, service_tax, split_bill, payment_methode, card_number, nopd, source, status_paid) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stat.setString(1, hmData.get(RuleNameParameter.NO_RESI).toString());
            stat.setString(2, unqueId);
            stat.setString(3, hmData.get(RuleNameParameter.TRX_DATE).toString());
            stat.setDouble(4, Double.valueOf(hmData.get(RuleNameParameter.AMOUNT).toString()));
            stat.setDouble(5, Double.valueOf(hmData.get(RuleNameParameter.PPN).toString()));
            stat.setString(6, hmData.get(RuleNameParameter.DETAIL).toString());
            stat.setString(7, hmData.get(RuleNameParameter.INSTITUTE_ID).toString());
            stat.setString(8, hmData.get(RuleNameParameter.POS_ID).toString());
            stat.setString(9, hmData.get(RuleNameParameter.POS_CODE).toString());
            stat.setString(10, hmData.get(RuleNameParameter.RRN).toString());
            stat.setString(11, hmData.get(RuleNameParameter.PROC_CODE).toString());
            stat.setString(12, hmData.get(RuleNameParameter.SERIAL_KEY).toString());
            if (hmData.containsKey(RuleNameParameter.SERVICE_TAX)) {
                int servicetax = 0;
                stat.setInt(13, servicetax);
            } else {
                stat.setDouble(13, Double.valueOf(hmData.get(RuleNameParameter.SERVICE_TAX).toString()));
            }
            if (hmData.containsKey(RuleNameParameter.SPLIT_BILL)) {
                int splitbill = 0;
                stat.setInt(14, splitbill);
            } else {
                stat.setDouble(14, Double.valueOf(hmData.get(RuleNameParameter.SPLIT_BILL).toString()));
            }
            stat.setDouble(15, Double.valueOf(hmData.get(RuleNameParameter.PAYMENT_METHODE).toString()));
            if (hmData.containsKey(RuleNameParameter.CARD_NUMBER)) {
                int cardnumber = 0;
                stat.setInt(16, cardnumber);
            } else {
                stat.setDouble(16, Double.valueOf(hmData.get(RuleNameParameter.CARD_NUMBER).toString()));
            }
            stat.setString(17, nopd);
            stat.setString(18, "OPCR");
            int pay_met = Integer.valueOf(hmData.get(RuleNameParameter.PAYMENT_METHODE).toString());
            if (pay_met == 0) {
                stat.setInt(19, 0);
            } else if (pay_met == 1) {
                stat.setInt(19, 1);
            }

            stat.executeUpdate();
            stat.close();

            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMddHHmmss");
            SimpleDateFormat formatter2 = new SimpleDateFormat("ddMMyyyyHHmmss");
            String tanggal_trx = hmData.get(RuleNameParameter.TRX_DATE).toString();
            java.util.Date date = formatter1.parse(tanggal_trx);

            stat = conn.prepareStatement("INSERT INTO bprd (id, tanggal_trx, no_struk, description, amount_trx, nopd, source, tanggal) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            stat.setString(1, id);
            stat.setString(2, formatter2.format(date));
            stat.setString(3, hmData.get(RuleNameParameter.RRN).toString());
            stat.setString(4, hmData.get(RuleNameParameter.DETAIL).toString());
            stat.setInt(5, Integer.valueOf(amount_trx));
            stat.setString(6, nopd);
            stat.setString(7, "OPCR");
            stat.setString(8, tanggal_trx.substring(0, 8));
            stat.executeUpdate();
            stat.close();

            stat = conn.prepareStatement("UPDATE active set dt_internal = now() where instituteid = ? and posid = ? and poscode = ? and key = ?");
            stat.setString(1, hmData.get(RuleNameParameter.INSTITUTE_ID).toString());
            stat.setString(2, hmData.get(RuleNameParameter.POS_ID).toString());
            stat.setString(3, hmData.get(RuleNameParameter.POS_CODE).toString());
            stat.setString(4, hmData.get(RuleNameParameter.SERIAL_KEY).toString());
            stat.executeUpdate();
            stat.close();

            System.out.println("///////////////////////////////////////////////////////////////////");
//            HashMap req = new HashMap();
//            req.put(RuleNameParameter.ID, id);
//            req.put(RuleNameParameter.TANGGAL_TRX, formatter2.format(date));
//            req.put(RuleNameParameter.NO_STRUK, hmData.get(RuleNameParameter.RRN).toString());
//            req.put(RuleNameParameter.DESCRIPTION, hmData.get(RuleNameParameter.DETAIL).toString());
//            req.put(RuleNameParameter.AMOUNT_TRX, Integer.valueOf(amount_trx));
//            req.put(RuleNameParameter.NOPD, nopd);
//            req.put(RuleNameParameter.SOURCE, "OPCR");
//            String reqMsg = JsonProcess.generateJson(req);
//            HashMap resp = KirimMessageToBprd.kirimMessageToBprd(JsonProcess.decodeJson(reqMsg));

            System.out.println("///////////////////////////////////////////////////////////////////");

            try {
                stat1 = conn.prepareStatement("SELECT noresi FROM listitem_detail WHERE noresi = ? ");
                stat1.setString(1, noresi);
                rs = stat1.executeQuery();
                if (rs.next()) {
                    System.out.println("tidak ada :" + rs.getString("noresi"));
                } else {
                    listdetail = (JSONArray) parser.parse(hmData.get(RuleNameParameter.DETAIL).toString());
                    for (int i = 0; i < listdetail.size(); i++) {
                        HashMap item = JsonProcess.decodeJson(listdetail.get(i).toString());
                        stat = conn.prepareStatement("INSERT INTO listitem_detail(noresi, product_name, total, sum_amount, rowstruk) VALUES (?, ?, ?, ?, ?)");
                        stat.setString(1, hmData.get(RuleNameParameter.NO_RESI).toString());
                        stat.setString(2, item.get("PROD_NAME").toString());
                        stat.setString(3, item.get("TOTAL").toString());
                        stat.setDouble(4, Double.valueOf(item.get("SUM_AMOUNT").toString()));
                        stat.setInt(5, i + 1);
                        stat.executeUpdate();
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } catch (SQLException ex) {
            log.error("saveNewPaycode : " + hmData.get(RuleNameParameter.INSTITUTE_ID).toString() + " - " + hmData.get(RuleNameParameter.POS_CODE).toString() + " - " + ex.getMessage());
            ex.printStackTrace();
            return false;
        } finally {
            clearAllConnStatRS(conn, stat, rs);
            clearStatment(stat1);
            clearStatment(stat2);
            clearResultset(rs2);
        }
        return true;
    }

    public boolean saveVoidAndDelete(HashMap hmData) throws java.text.ParseException {
        Connection conn = null;
        PreparedStatement stat = null;
        PreparedStatement stat1 = null;
        ResultSet rs = null;
        String nopd = "";
        String amount_trx = hmData.get(RuleNameParameter.AMOUNT).toString();
        String id = hmData.get(RuleNameParameter.NO_RESI).toString()
                + hmData.get(RuleNameParameter.SPLIT_BILL).toString();
        String unqueId = hmData.get(RuleNameParameter.INSTITUTE_ID).toString()
                + hmData.get(RuleNameParameter.POS_ID).toString()
                + hmData.get(RuleNameParameter.POS_CODE).toString();
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat1 = conn.prepareStatement("SELECT nopd from merchant where instituteid = ?");
            stat1.setString(1, hmData.get(RuleNameParameter.INSTITUTE_ID).toString());
            rs = stat1.executeQuery();
            while (rs.next()) {
                nopd = rs.getString("nopd");
            }
            stat = conn.prepareStatement("INSERT INTO listvoid(noresi, unique_code, dt_merc, amount, ppn, detail, instituteid, posid, poscode, rrn, proc_code, key, service_tax, split_bill, payment_methode, card_number, nopd, source) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stat.setString(1, hmData.get(RuleNameParameter.NO_RESI).toString());
            stat.setString(2, unqueId);
            stat.setString(3, hmData.get(RuleNameParameter.TRX_DATE).toString());
            stat.setDouble(4, Double.valueOf(hmData.get(RuleNameParameter.AMOUNT).toString()));
            stat.setDouble(5, Double.valueOf(hmData.get(RuleNameParameter.PPN).toString()));
            stat.setString(6, hmData.get(RuleNameParameter.DETAIL).toString());
            stat.setString(7, hmData.get(RuleNameParameter.INSTITUTE_ID).toString());
            stat.setString(8, hmData.get(RuleNameParameter.POS_ID).toString());
            stat.setString(9, hmData.get(RuleNameParameter.POS_CODE).toString());
            stat.setString(10, hmData.get(RuleNameParameter.RRN).toString());
            stat.setString(11, hmData.get(RuleNameParameter.PROC_CODE).toString());
            stat.setString(12, hmData.get(RuleNameParameter.SERIAL_KEY).toString());

            if (hmData.get(RuleNameParameter.SERVICE_TAX) == null) {
                int a = 0;
                stat.setInt(13, a);
            } else {
                stat.setDouble(13, Double.valueOf(hmData.get(RuleNameParameter.SERVICE_TAX).toString()));
            }
            if (hmData.get(RuleNameParameter.SPLIT_BILL) == null) {
                int splitbill = 0;
                stat.setInt(14, splitbill);
            } else {
                stat.setDouble(14, Double.valueOf(hmData.get(RuleNameParameter.SPLIT_BILL).toString()));
            }
            stat.setDouble(15, Double.valueOf(hmData.get(RuleNameParameter.PAYMENT_METHODE).toString()));
            if (hmData.get(RuleNameParameter.CARD_NUMBER) == null) {
                int cardnumber = 0;
                stat.setInt(16, cardnumber);
            } else {
                stat.setDouble(16, Double.valueOf(hmData.get(RuleNameParameter.CARD_NUMBER).toString()));
            }
            stat.setString(17, nopd);
            stat.setString(18, "OPCR");
            stat.executeUpdate();
            stat.close();

            String tanggal_trx = hmData.get(RuleNameParameter.TRX_DATE).toString();
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMddHHmmss");
            SimpleDateFormat formatter2 = new SimpleDateFormat("ddMMyyyyHHmmss");
            java.util.Date date = formatter1.parse(tanggal_trx);
            stat = conn.prepareStatement("INSERT INTO bprd_void (id, tanggal_trx, no_struk, description, amount_trx, nopd, source, tanggal) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            stat.setString(1, id);
            stat.setString(2, formatter2.format(date));
            stat.setString(3, hmData.get(RuleNameParameter.RRN).toString());
            stat.setString(4, hmData.get(RuleNameParameter.DETAIL).toString());
            stat.setInt(5, Integer.valueOf(amount_trx));
            stat.setString(6, nopd);
            stat.setString(7, "OPCR");
            stat.setString(8, tanggal_trx.substring(0, 8));

            stat.executeUpdate();
            stat.close();

//            HashMap req = new HashMap();
//            req.put(RuleNameParameter.ID, id);
//            req.put(RuleNameParameter.TANGGAL_TRX, formatter2.format(date));
//            req.put(RuleNameParameter.NO_STRUK, hmData.get(RuleNameParameter.RRN).toString());
//            req.put(RuleNameParameter.DESCRIPTION, "VOID");
//            req.put(RuleNameParameter.AMOUNT_TRX, "-" + Integer.valueOf(amount_trx));
//            req.put(RuleNameParameter.NOPD, nopd);
//            req.put(RuleNameParameter.SOURCE, "OPCR");
//            String reqMsg = JsonProcess.generateJson(req);
//            HashMap resp = KirimMessageToBprd.kirimMessageToBprd(JsonProcess.decodeJson(reqMsg));
            stat = conn.prepareStatement("UPDATE active set dt_internal = now() where instituteid = ? and posid = ? and poscode = ? and key = ?");
            stat.setString(1, hmData.get(RuleNameParameter.INSTITUTE_ID).toString());
            stat.setString(2, hmData.get(RuleNameParameter.POS_ID).toString());
            stat.setString(3, hmData.get(RuleNameParameter.POS_CODE).toString());
            stat.setString(4, hmData.get(RuleNameParameter.SERIAL_KEY).toString());
            stat.executeUpdate();
            stat.close();

            stat = conn.prepareStatement("DELETE FROM listtax WHERE noresi=?");
            stat.setString(1, hmData.get(RuleNameParameter.NO_RESI).toString());
            stat.executeUpdate();
            stat.close();

            stat = conn.prepareStatement("DELETE FROM listtax_backup WHERE noresi=?");
            stat.setString(1, hmData.get(RuleNameParameter.NO_RESI).toString());
            stat.executeUpdate();
            stat.close();

            stat = conn.prepareStatement("DELETE FROM bprd WHERE id=?");
            stat.setString(1, id);
            stat.executeUpdate();
            stat.close();

            stat = conn.prepareStatement("DELETE FROM bprd_backup WHERE id=?");
            stat.setString(1, id);
            stat.executeUpdate();
            stat.close();
            stat = conn.prepareStatement("DELETE FROM listitem_detail WHERE noresi = ?");
            stat.setString(1, hmData.get(RuleNameParameter.NO_RESI).toString());
            stat.executeUpdate();

        } catch (SQLException ex) {
            log.error("saveVoidAndDelete : " + hmData.get(RuleNameParameter.INSTITUTE_ID).toString() + " - " + hmData.get(RuleNameParameter.POS_CODE).toString() + " - " + ex.getMessage());
            ex.printStackTrace();
            return false;
        } finally {
            clearStatment(stat1);
            clearAllConnStatRS(conn, stat, rs);
        }
        return true;
    }

    public HashMap getBill(HashMap inputMap) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String respcode = "2008";
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("SELECT a.unique_code, a.dt_merc, a.amount, a.detail, a.merc_id, b.merc_name, a.pay_status FROM billing a join merchant b on a.merc_id = b.merc_id where a.pay_code = ?"
                    + " order by dt_internal desc fetch first 1 rows only");
            stat.setString(1, inputMap.get(RuleNameParameter.NO_RESI).toString());
            rs = stat.executeQuery();
            while (rs.next()) {
                if (rs.getBoolean("pay_status")) {
                    respcode = "2009";
                } else {
                    respcode = "0000";
                }
                inputMap.put(RuleNameParameter.DETAIL, rs.getString("detail"));
                inputMap.put(RuleNameParameter.UNIQ_CODE, rs.getString("unique_code"));
                inputMap.put(RuleNameParameter.TRX_DATE, rs.getString("dt_merc"));
                inputMap.put(RuleNameParameter.AMOUNT, rs.getLong("amount"));
                inputMap.put(RuleNameParameter.MERC_CODE, rs.getString("merc_id"));
                inputMap.put(RuleNameParameter.MERC_NAME, rs.getString("merc_name"));

            }
            inputMap.put(RuleNameParameter.RESP_CODE, respcode);
            inputMap.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get(respcode).toString());

        } catch (SQLException ex) {
            log.error("getBill : " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return inputMap;
    }

    public HashMap saveLogTrx(String merchantId, String posid, String respcode, String procCode, String reqmsg, String respmsg, String noresi, String respdesc) {
        Connection conn = null;
        PreparedStatement stat = null;
        HashMap resData = new HashMap();

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("INSERT INTO log_trx(merc_id, posid, proc_code, resp_code, req_msg, resp_msg, noresi, resp_desc) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            stat.setString(1, merchantId);
            stat.setString(2, posid);
            stat.setString(3, procCode);
            stat.setString(4, respcode);
            stat.setString(5, reqmsg);
            stat.setString(6, respmsg);
            stat.setString(7, noresi);
            stat.setString(8, respdesc);

            stat.executeUpdate();
        } catch (SQLException ex) {
            log.error("saveLogTrx : " + ex.getMessage());
            ex.printStackTrace();
//            return inputMap;

        } finally {
            clearStatment(stat);
            clearDBConnection(conn);
        }
        return resData;

    }

    public boolean updateLogTrx(HashMap inputMap, String respcode) {
        Connection conn = null;
        PreparedStatement stat = null;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("UPDATE log_trx SET resp_code=?, dt_res = current_timestamp  where ");
            stat.setString(1, respcode);

            stat.executeUpdate();
        } catch (SQLException ex) {
            log.error("saveLogTrx : " + ex.getMessage());
            ex.printStackTrace();
            return false;
        } finally {
            clearStatment(stat);
            clearDBConnection(conn);
        }
        return true;
    }

    public boolean saveTrxHist(HashMap inputMap, String respcode) {
        Connection conn = null;
        PreparedStatement stat = null;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("INSERT INTO pymthist(pay_code, bank_code, dt_bank, amount, refno_bank, resp_code) VALUES (?, ?, ?, ?, ?, ?)");
            stat.setString(1, inputMap.get(RuleNameParameter.NO_RESI).toString());
            stat.setString(2, inputMap.get(RuleNameParameter.BANK_CODE).toString());
            stat.setString(3, inputMap.get(RuleNameParameter.TRX_DATE).toString());
            stat.setDouble(4, Double.valueOf(inputMap.get(RuleNameParameter.AMOUNT).toString()));
            stat.setString(5, inputMap.get(RuleNameParameter.REF_NO).toString());
            stat.setString(6, respcode);
            stat.executeUpdate();
            stat.clearParameters();
            stat.close();
            stat = null;
            stat = conn.prepareStatement(
                    "INSERT INTO paid_bill(pay_code, unique_code, dt_merc, dt_internal, amount, detail,pay_status, merc_id)"
                    + "SELECT pay_code, unique_code, dt_merc, dt_internal, amount, detail,'TRUE', merc_id from billing WHERE pay_code=? and unique_code=? and dt_merc=?");
            stat.setString(1, inputMap.get(RuleNameParameter.NO_RESI).toString());
            stat.setString(2, inputMap.get(RuleNameParameter.UNIQ_CODE).toString());
            stat.setString(3, inputMap.get(RuleNameParameter.TRX_DATE).toString());
            stat.executeUpdate();
            stat.clearParameters();
            stat.close();
            stat = null;
            stat = conn.prepareStatement("DELETE FROM billing WHERE pay_code=? and unique_code=? and dt_merc=?");
            stat.setString(1, inputMap.get(RuleNameParameter.NO_RESI).toString());
            stat.setString(2, inputMap.get(RuleNameParameter.UNIQ_CODE).toString());
            stat.setString(3, inputMap.get(RuleNameParameter.TRX_DATE).toString());
            stat.executeUpdate();

        } catch (SQLException ex) {
            log.error("saveTrxHist : " + inputMap.get(RuleNameParameter.MERC_CODE).toString() + " - " + inputMap.get(RuleNameParameter.UNIQ_CODE).toString() + " - " + ex.getMessage());
            ex.printStackTrace();
            return false;
        } finally {
            clearStatment(stat);
            clearDBConnection(conn);
        }
        return true;
    }

    //sample EOD
    public boolean cleanData() {
        Connection conn = null;
        PreparedStatement stat = null;
//        GregorianCalendar cal = new GregorianCalendar();
//        cal.add(GregorianCalendar.DAY_OF_YEAR, -1);
//        Date tanggalhapus = new Date(cal.getTimeInMillis());
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
//            String ExpiredTime = getPaycodeLifeTime();
            log.info("Check Paycode LifeTime : ...");
            stat = conn.prepareStatement(
                    "INSERT INTO log_trx_backup(dt_internal, merc_id, posid, proc_code, resp_code, req_msg, resp_msg, countid, noresi, resp_desc)"
                    + "SELECT dt_internal, merc_id, posid, proc_code, resp_code, req_msg, resp_msg, countid, noresi, resp_desc from log_trx WHERE dt_internal < current_timestamp ");
            log.info("Backup trx ke log_trx_backup..");
            System.out.println("Backup trx ke log_trx_backup..");

            stat.executeUpdate();
            clearStatment(stat);
            stat = conn.prepareStatement("DELETE FROM log_trx WHERE dt_internal < current_timestamp");
            stat.executeUpdate();
            log.info("Clear data from log_trx..");
            System.out.println("Clear data from log_trx..");
            clearStatment(stat);
            log.info("Check Paycode LifeTime : ...");
            stat = conn.prepareStatement(
                    "INSERT INTO listtax_backup(noresi, unique_code, dt_merc, dt_internal, amount, ppn, detail, instituteid, posid, poscode, rrn, proc_code, key, service_tax, split_bill, payment_methode, card_number, nopd, source)"
                    + "SELECT noresi, unique_code, dt_merc, dt_internal, amount, ppn, detail, instituteid, posid, poscode, rrn, proc_code, key, service_tax, split_bill, payment_methode, card_number, nopd, source from listtax WHERE dt_internal < current_timestamp ");
            log.info("Backup listtax to listtax_backup ...");
            System.out.println("Backup listtax to listtax_backup ..");
            stat.executeUpdate();
            clearStatment(stat);
            stat = conn.prepareStatement("DELETE FROM listtax WHERE dt_internal < current_timestamp");
            stat.executeUpdate();
            log.info("Clear data from listtax");
            System.out.println("Clear data from listtax . .");
            clearStatment(stat);

            stat = conn.prepareStatement(
                    "INSERT INTO bprd_backup(id, tanggal_trx, no_struk, description, amount_trx, nopd, source, dt_internal, tanggal)"
                    + "SELECT id, tanggal_trx, no_struk, description, amount_trx, nopd, source, dt_internal, tanggal from bprd WHERE dt_internal < current_timestamp ");
            log.info("Backup trx bprd to bprd_backup ...");
            System.out.println("Backup trx bprd to bprd_backup ..");

            stat.executeUpdate();
            clearStatment(stat);
            stat = conn.prepareStatement("DELETE FROM bprd WHERE dt_internal < current_timestamp");
            stat.executeUpdate();
            log.info("Clear data from bprd");
            System.out.println("Clear data from bprd. . ");
            clearStatment(stat);

            stat = conn.prepareStatement("UPDATE configapp SET eoddone=true");
            stat.executeUpdate();
            clearStatment(stat);
            log.info("Update eoddone be true..");
            System.out.println("Update eoddone be true..");

        } catch (SQLException ex) {
            log.error("cleanData : " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            clearStatment(stat);
            clearDBConnection(conn);
        }
        return true;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //untuk reeport bprd 
    public boolean getEODDone() {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {
            System.out.println("2. get eoddone true or false . .");

            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("SELECT eoddone FROM configapp");
            rs = stat.executeQuery();
            while (rs.next()) {
                return rs.getBoolean("eoddone");
            }

        } catch (SQLException ex) {
//            ex.printStackTrace();
            log.error("getMessageIncoming : " + ex.getMessage());

        } finally {
            clearResultset(rs);
            clearStatment(stat);
        }
        return false;
    }

    public boolean updateEODDone() {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        System.out.println("update eod done to false. .");
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("UPDATE configapp SET eoddone=false");
            stat.executeUpdate();

        } catch (SQLException ex) {
//            ex.printStackTrace();
            log.error("updateEODDone : " + ex.getMessage());

        } finally {
            clearResultset(rs);
            clearStatment(stat);
        }
        return false;
    }

    public boolean updateEODDoneTrue() {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("UPDATE configapp SET eoddone=true");
            stat.executeUpdate();

        } catch (SQLException ex) {
//            ex.printStackTrace();
            log.error("updateEODDone : " + ex.getMessage());

        } finally {
            clearResultset(rs);
            clearStatment(stat);
        }
        return false;
    }

    public List getAllTransaction(GregorianCalendar startDate, GregorianCalendar endDate, String bankCodeFrom) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        List l = new ArrayList();
        Tempmsg tempmsg = null;

//        String startDate = hmData.get(RuleNameParameter.startDate).toString();
//        String endDate = hmData.get(RuleNameParameter.endDate).toString();
        String selectBankCodeFrom = "";
        System.out.println("get all trx . .");
        try {
//            System.out.println("masuk ke datatbase :"+startDate +endDate);
            System.out.println(DatasourceEntry.getInstance().getPostgreDS().getConnection());
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("SELECT * FROM bprd_backup WHERE (?<=date(tanggal) and date(tanggal)<=?) and nopd =?");
            stat.setDate(1, new Date(startDate.getTimeInMillis()));
            stat.setDate(2, new Date(endDate.getTimeInMillis()));
            stat.setString(3, bankCodeFrom);

//            System.out.println("h :" + StringFunction.getCurrentDateYYYYMMDDHHMMSS(startDate));
//            System.out.println("j :" + StringFunction.getCurrentDateYYYYMMDDHHMMSS(endDate));
//             System.out.println("k :" + startDate);
//            System.out.println("l :" + endDate.getTimeInMillis()); System.out.println("h :" + startDate.getTimeInMillis());
//            System.out.println("m :" + endDate.getTimeInMillis());
            rs = stat.executeQuery();
//            GregorianCalendar calTrx = new GregorianCalendar();
            while (rs.next()) {
                tempmsg = new Tempmsg();
                tempmsg.setId("'" + rs.getString("id"));
                tempmsg.setTanggal_trx("'" + rs.getString("tanggal_trx"));
                tempmsg.setNo_struk(rs.getString("no_struk"));
                tempmsg.setDescription(rs.getString("description"));
                tempmsg.setAmount_trx(rs.getString("amount_trx"));
                tempmsg.setNopd("'" + rs.getString("nopd"));
                tempmsg.setSource(rs.getString("source"));

                l.add(tempmsg);
            }
        } catch (SQLException ex) {
//            ex.printStackTrace();
            log.error("getAllTransaction : " + ex.getMessage());

        } finally {
            clearResultset(rs);
            clearStatment(stat);
        }
        return l;
    }

    public List getAllVoid(GregorianCalendar startDate, GregorianCalendar endDate, String bankCodeFrom) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        List l = new ArrayList();
        Tempmsg tempmsg = null;

//        String startDate = hmData.get(RuleNameParameter.startDate).toString();
//        String endDate = hmData.get(RuleNameParameter.endDate).toString();
        String selectBankCodeFrom = "";
        System.out.println("get all void . .");
        try {
//            System.out.println("masuk ke datatbase :"+startDate +endDate);
            System.out.println(DatasourceEntry.getInstance().getPostgreDS().getConnection());
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("SELECT * FROM bprd_void WHERE (?<=date(tanggal) and date(tanggal)<=?) and nopd =?");
            stat.setDate(1, new Date(startDate.getTimeInMillis()));
            stat.setDate(2, new Date(endDate.getTimeInMillis()));
            stat.setString(3, bankCodeFrom);

//            System.out.println("h :" + StringFunction.getCurrentDateYYYYMMDDHHMMSS(startDate));
//            System.out.println("j :" + StringFunction.getCurrentDateYYYYMMDDHHMMSS(endDate));
//             System.out.println("k :" + startDate);
//            System.out.println("l :" + endDate.getTimeInMillis()); System.out.println("h :" + startDate.getTimeInMillis());
//            System.out.println("m :" + endDate.getTimeInMillis());
            rs = stat.executeQuery();
//            GregorianCalendar calTrx = new GregorianCalendar();
            while (rs.next()) {
                tempmsg = new Tempmsg();
                tempmsg.setId("'" + rs.getString("id"));
                tempmsg.setTanggal_trx("'" + rs.getString("tanggal_trx"));
                tempmsg.setNo_struk(rs.getString("no_struk"));
                tempmsg.setDescription(rs.getString("description"));
                tempmsg.setAmount_trx(rs.getString("amount_trx"));
                tempmsg.setNopd("'" + rs.getString("nopd"));
                tempmsg.setSource(rs.getString("source"));

                l.add(tempmsg);
            }
        } catch (SQLException ex) {
//            ex.printStackTrace();
            log.error("getAllVoid : " + ex.getMessage());

        } finally {
            clearResultset(rs);
            clearStatment(stat);
        }
        return l;
    }

//    public List getAllTransaction(GregorianCalendar startDate, GregorianCalendar endDate, String bankCodeFrom, String typeRC) {
//        Connection conn = null;
//        PreparedStatement stat = null;
//        ResultSet rs = null;
//        List l = new ArrayList();
//        Tempmsg tempmsg = null;
//        try {
//            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
//            String select1 = "SELECT a.noref, a.bankcode, a.amount, a.bankcodefrom, a.requesttime, a.rcinternal, "
//                    + " a.fromaccount, a.productcode, a.custno, a.terminalid, b.tcbiller, b.trancodename, b.feebeli, b.feejual, b.feebpd, b.feebank, b.feeskema, c.conname "
//                    + " FROM tempmsg_backup a, trancode b, socketconn c where a.proccode = '280000' and (?<=date(a.requesttime) and date(a.requesttime)<=?) ";
//            String select2 = " and a.productcode = b.trancodeid and c.bankcode = a.bankcode order by requesttime";
//            String selectBankCodeFrom = "";
//            String selectRCInternal = "";
//            if (bankCodeFrom != null) {
//                selectBankCodeFrom = " and bankcodefrom = '" + bankCodeFrom + "' ";
//            }
//            if (typeRC.equals(RuleNameParameter.typefile_all)) {
//
//            } else if (typeRC.equals(RuleNameParameter.typefile_failed)) {
//                selectRCInternal = " and rcinternal not in ('00','68','20') ";
//            } else if (typeRC.equals(RuleNameParameter.typefile_suspect)) {
//                selectRCInternal = " and rcinternal in ('68','20') ";
//            } else if (typeRC.equals(RuleNameParameter.typefile_success)) {
//                selectRCInternal = " and rcinternal='00' ";
//            }
//            stat = conn.prepareStatement(select1 + selectBankCodeFrom + selectRCInternal + select2);
//            stat.setDate(1, new Date(startDate.getTimeInMillis()));
////            endDate.add(GregorianCalendar.DATE, 1);
//            stat.setDate(2, new Date(endDate.getTimeInMillis()));
////            endDate.add(GregorianCalendar.DATE, -1);
////System.out.println("statment : " +stat.toString());
//            rs = stat.executeQuery();
//            GregorianCalendar calTrx = new GregorianCalendar();
//            while (rs.next()) {
//                tempmsg = new Tempmsg();
//                tempmsg.setNoref(rs.getString("noref"));
//                tempmsg.setBankcode(rs.getString("conname"));
//                if (rs.getString("bankcode").equals("vdn") || rs.getString("bankcode").equals("vdn2")) {
//                    tempmsg.setAmount(rs.getString("amount").substring(0, rs.getString("amount").length() - 0));
//                } else {
//                    tempmsg.setAmount(rs.getString("amount").substring(0, rs.getString("amount").length() - 2));
//                }
//                tempmsg.setCustNo(rs.getString("custno"));
////                System.out.println("Gettempbankcode from : " + getBankName(rs.getString("bankcodefrom")));
//                tempmsg.setBankcodefrom(rs.getString("bankcodefrom"));
//                tempmsg.setBankcodefromname(getBankName(rs.getString("bankcodefrom")));
//                calTrx.setTimeInMillis(rs.getTimestamp("requesttime").getTime());
//                tempmsg.setTglTrx(StringFunction.getDateDD_MM_YYYY(calTrx));
//                tempmsg.setTimeTrx(StringFunction.getCurrentTimeHH_mm_ss(calTrx));
//                tempmsg.setRcInternal(rs.getString("rcinternal"));
//                tempmsg.setFromaccount(rs.getString("fromaccount"));
//                tempmsg.setProccode(rs.getString("productcode"));
//                tempmsg.setTcbiller(rs.getString("tcbiller"));
//                tempmsg.setTerminalid(rs.getString("terminalid"));
//                tempmsg.setTrancodeName(rs.getString("trancodename"));
//                tempmsg.setFeebpdnet(rs.getLong("feebank"));
//                if (rs.getString("feeskema").equals("1")) {
//                    tempmsg.setFeebpd(rs.getLong("feejual"));
//                } else {
//                    tempmsg.setFeebpd(rs.getLong("feebpd"));
//                }
//                l.add(tempmsg);
//            }
//        } catch (SQLException ex) {
////            ex.printStackTrace();
//            log.error("getAllTransaction : " + ex.getMessage());
//
//        } finally {
//            clearResultset(rs);
//            clearStatment(stat);
//        }
//        return l;
//    }
//    public List getAllTransaction2(GregorianCalendar startDate, GregorianCalendar endDate, String bankCodeFrom, String typeRC) {
//        Connection conn = null;
//        PreparedStatement stat = null;
//        ResultSet rs = null;
//        
//        List l = new ArrayList();
//        Tempmsg tempmsg = null;
//        try {
//            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
//            String select1 = "SELECT a.trxidbackend,a.noref, a.bankcode, a.amount, a.bankcodefrom, a.requesttime, a.rcinternal, "
//                    + " a.fromaccount, a.productcode, a.custno, a.terminalid, b.tcbiller, b.trancodename, b.feebeli, b.feejual, b.feebpd, b.feebank, b.feeskema, c.conname "
//                    + " FROM tempmsg_backup a, trancode b, socketconn c where a.proccode = '280000' and (?<=date(a.requesttime) and date(a.requesttime)<=?) ";
//            String select2 = " and a.productcode = b.trancodeid and c.bankcode = a.bankcode order by requesttime";
//            String selectBankCodeFrom = "";
//            String selectRCInternal = "";
//            if (bankCodeFrom != null) {
//                selectBankCodeFrom = " and bankcodefrom = '" + bankCodeFrom + "' ";
//            }
//            if (typeRC.equals(RuleNameParameter.typefile_all)) {
//
//            } else if (typeRC.equals(RuleNameParameter.typefile_failed)) {
//                selectRCInternal = " and rcinternal not in ('00','68','20') ";
//            } else if (typeRC.equals(RuleNameParameter.typefile_suspect)) {
//                selectRCInternal = " and rcinternal in ('68','20') ";
//            } else if (typeRC.equals(RuleNameParameter.typefile_success)) {
//                selectRCInternal = " and rcinternal='00' ";
//            }
//            stat = conn.prepareStatement(select1 + selectBankCodeFrom + selectRCInternal + select2);
//            stat.setDate(1, new Date(startDate.getTimeInMillis()));
////            endDate.add(GregorianCalendar.DATE, 1);
//            stat.setDate(2, new Date(endDate.getTimeInMillis()));
////            endDate.add(GregorianCalendar.DATE, -1);
//
//            rs = stat.executeQuery();
//            GregorianCalendar calTrx = new GregorianCalendar();
//            while (rs.next()) {
//                tempmsg = new Tempmsg();
//                tempmsg.setNoref(rs.getString("noref"));
//                tempmsg.setTrxidbackend(rs.getString("trxidbackend"));
//                tempmsg.setBankcode(rs.getString("conname"));
//                if (rs.getString("conname").equals("Verdana")) {
//                    tempmsg.setAmount(rs.getString("amount").substring(0, rs.getString("amount").length() - 0));
//                } else {
//                    tempmsg.setAmount(rs.getString("amount").substring(0, rs.getString("amount").length() - 2));
//                }
//                tempmsg.setCustNo(rs.getString("custno"));
//                tempmsg.setBankcodefrom(rs.getString("bankcodefrom"));
//                calTrx.setTimeInMillis(rs.getTimestamp("requesttime").getTime());
//                tempmsg.setTglTrx(StringFunction.getDateYYYY_MM_DD(calTrx));
//                tempmsg.setTimeTrx(StringFunction.getCurrentTimeHH_mm_ss(calTrx));
//                tempmsg.setRcInternal(rs.getString("rcinternal"));
//                tempmsg.setFromaccount(rs.getString("fromaccount"));
//                tempmsg.setProccode(rs.getString("productcode"));
//                tempmsg.setTcbiller(rs.getString("tcbiller"));
//                tempmsg.setTerminalid(rs.getString("terminalid"));
//                tempmsg.setTrancodeName(rs.getString("trancodename"));
//                tempmsg.setFeebpdnet(rs.getLong("feebank"));
//                if (rs.getString("feeskema").equals("1")) {
//                    tempmsg.setFeebpd(rs.getLong("feejual"));
//                } else {
//                    tempmsg.setFeebpd(rs.getLong("feebpd"));
//                }
//                l.add(tempmsg);
//            }
//        } catch (SQLException ex) {
////            ex.printStackTrace();
//            log.error("getAllTransaction : " + ex.getMessage());
//
//        } finally {
//            clearResultset(rs);
//            clearStatment(stat);
//        }
//        return l;
//    }
//    public List getAllTransactionByProduct(GregorianCalendar startDate, GregorianCalendar endDate, String bankCodeFrom, String typeRC) {
//        Connection conn = null;
//        PreparedStatement stat = null;
//        ResultSet rs = null;
//        
//        List l2 = new ArrayList();
//        Tempmsg tempmsg = null;
//        try {
//            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
//            String select1 = "SELECT  SUM(a.amount::integer)as amount,  a.productcode as productcode,max(b.trancodename) as trancodename, max(b.tcbiller) as tcbiller,max(b.feeskema) as feeskema, SUM(b.feebeli) as feebeli,SUM(b.feejual) as feejual, SUM(b.feebpd) as feebpd, SUM(b.feebank) as feebank, MAX(c.conname) as conname FROM tempmsg_backup a, trancode b, socketconn c where a.responsecode='0000' and (?<=date(a.requesttime) and date(a.requesttime)<=?) and a.proccode = '280000' and a.productcode = b.trancodeid and c.bankcode = a.bankcode ";
//            String select2 = " and c.bankcode = b.bankcode GROUP by a.productcode order by a.productcode";
//            String selectBankCodeFrom = "";
////            String selectRCInternal = "";
//            if (bankCodeFrom != null) {
//                selectBankCodeFrom = " and bankcodefrom = '" + bankCodeFrom + "' ";
//            }
////            if (typeRC.equals(RuleNameParamater.typefile_all)) {
////
////            } else if (typeRC.equals(RuleNameParamater.typefile_failed)) {
////                selectRCInternal = " and rcinternal not in ('00','68','20') ";
////            } else if (typeRC.equals(RuleNameParamater.typefile_suspect)) {
////                selectRCInternal = " and rcinternal in ('68','20') ";
////            } else if (typeRC.equals(RuleNameParamater.typefile_success)) {
////                selectRCInternal = " and rcinternal='00' ";
////            }
//            stat = conn.prepareStatement(select1 + selectBankCodeFrom + select2);
//            stat.setDate(1, new Date(startDate.getTimeInMillis()));
//            endDate.add(GregorianCalendar.DATE, 1);
//            stat.setDate(2, new Date(endDate.getTimeInMillis()));
//            endDate.add(GregorianCalendar.DATE, -1);
//            rs = stat.executeQuery();
//
//            GregorianCalendar calTrx = new GregorianCalendar();
//            while (rs.next()) {
//                tempmsg = new Tempmsg();
////                tempmsg.setNoref(rs.getString("noref"));
//                tempmsg.setBankcode(rs.getString("conname"));
//////                tempmsg.setCustNo(rs.getString("custno"));
//////                tempmsg.setBankcodefrom(rs.getString("bankcodefrom"));
//////                calTrx.setTimeInMillis(rs.getTimestamp("requesttime").getTime());
//////                tempmsg.setTglTrx(StringFunction.getDateDD_MM_YYYY(calTrx));
//////                tempmsg.setTimeTrx(StringFunction.getCurrentTimeHH_mm_ss(calTrx));
//////                tempmsg.setRcInternal(rs.getString("rcinternal"));
//////                tempmsg.setFromaccount(rs.getString("fromaccount"));
//                if (rs.getString("conname").equals("Verdana")) {
//                    tempmsg.setAmount(rs.getString("amount").substring(0, rs.getString("amount").length() - 0));
//                } else {
//                    tempmsg.setAmount(rs.getString("amount").substring(0, rs.getString("amount").length() - 2));
//                }
////                tempmsg.setAmount(rs.getString("amount"));
//
//                tempmsg.setProccode(rs.getString("productcode"));
//                tempmsg.setTcbiller(rs.getString("tcbiller"));
//                tempmsg.setTrancodeName(rs.getString("trancodename"));
//                tempmsg.setFeebpdnet(rs.getLong("feebank"));
//                if (rs.getString("feeskema").equals("1")) {
//                    tempmsg.setFeebpd(rs.getLong("feejual"));
//                } else {
//                    tempmsg.setFeebpd(rs.getLong("feebpd"));
//                }
//                l2.add(tempmsg);
//
//            }
//        } catch (SQLException ex) {
////            ex.printStackTrace();
//            log.error("getAllTransactionByProduct : " + ex.getMessage());
//
//        } finally {
//            clearResultset(rs);
//            clearStatment(stat);
//        }
//        return l2;
//    }
//    SELECT distinct(a.bankcodefrom)FROM tempmsg_backup a where proccode = '280000' bankcodefrom !='';
    public String getBankName(String bankcode) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        String bankname = "";
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            String select1 = "select bankname from listbank where bankcode like '" + bankcode + "'";// and (a.requesttime>=? and a.requesttime<=?) ";
            stat = conn.prepareStatement(select1);
//            System.out.println("getBankname Q : " + select1);
            rs = stat.executeQuery();
            while (rs.next()) {
                bankname = rs.getString("bankname");

            }
        } catch (SQLException ex) {
//            ex.printStackTrace();
            log.error("getBankCodeFrom : " + ex.getMessage());

        } finally {
            clearResultset(rs);
            clearStatment(stat);
        }
//        System.out.println("return bankname : " + bankname);
        return bankname;
    }

    public List getBankCodeFrom(GregorianCalendar startDate, GregorianCalendar endDate) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        System.out.println("4. get bankcode. .");
        List l = new ArrayList();
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            String select1 = "SELECT distinct nopd FROM bprd_backup";// and (a.requesttime>=? and a.requesttime<=?) ";
            stat = conn.prepareStatement(select1);
            rs = stat.executeQuery();
            while (rs.next()) {
                l.add(rs.getString("nopd"));
                System.out.println("ini adalah nopd :" + rs.getString("nopd"));
            }
        } catch (SQLException ex) {
//            ex.printStackTrace();
            log.error("nopd : " + ex.getMessage());

        } finally {
            clearResultset(rs);
            clearStatment(stat);
        }
        return l;
    }

    public List getBankCodeFrom2() {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        List l = new ArrayList();
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            String select1 = "SELECT distinct(a.bankcodefrom)FROM tempmsg_backup a where proccode = '280000' bankcodefrom !=''";

            stat = conn.prepareStatement(select1);
            rs = stat.executeQuery();
            while (rs.next()) {
                l.add(rs.getString("bankcodefrom"));
            }
        } catch (SQLException ex) {
//            ex.printStackTrace();
            log.error("getBankCodeFrom : " + ex.getMessage());

        } finally {
            clearResultset(rs);
            clearStatment(stat);
        }
        return l;
    }

    public boolean saveDebet(HashMap hmData) throws ParseException, java.text.ParseException {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        PreparedStatement stat1 = null;
        ResultSet rs1 = null;
        PreparedStatement stat2 = null;
        ResultSet rs2 = null;
        PreparedStatement stat3 = null;

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("select * from (select * from listtax union all select * from listtax_backup ) AS noresi WHERE noresi = ?");
            stat.setString(1, hmData.get(RuleNameParameter.NO_RESI).toString());
            rs = stat.executeQuery();
            while (rs.next()) {
                int amount = rs.getInt("amount");
                int ppn = rs.getInt("ppn");
                int total_payment = amount + ppn;
                String instituteid = rs.getString("instituteid");

                stat1 = conn.prepareStatement("select * from cust_debetproc(?, ?)");
                stat1.setString(1, hmData.get(RuleNameParameter.CUSTOMER_ID).toString());
                stat1.setInt(2, total_payment);
                rs1 = stat1.executeQuery();
                while (rs1.next()) {
                    if (rs1.getString("vo_status").equals("L00")) {
                        stat2 = conn.prepareStatement("select * from merc_reciveproc(?, ?)");
                        stat2.setString(1, instituteid);
                        stat2.setInt(2, total_payment);
                        rs2 = stat2.executeQuery();
                        while (rs2.next()) {
                            if (rs2.getString("vo_status").equals("L00")) {
                                stat3 = conn.prepareStatement("UPDATE listtax SET status_paid = 1 WHERE noresi = ?");
                                stat3.setString(1, hmData.get(RuleNameParameter.NO_RESI).toString());
                                stat3.executeUpdate();
                                stat3.close();
                                stat3 = conn.prepareStatement("UPDATE listtax_backup SET status_paid = 1 WHERE noresi = ?");
                                stat3.setString(1, hmData.get(RuleNameParameter.NO_RESI).toString());
                                stat3.executeUpdate();
                                JSONArray listdetail = null;
                                JSONParser parser = new JSONParser();
                                listdetail = (JSONArray) parser.parse(rs.getString("detail"));
                                hmData.put(RuleNameParameter.DETAIL, listdetail);
                                hmData.put(RuleNameParameter.INSTITUTE_ID, rs.getString("instituteid"));
                                hmData.put(RuleNameParameter.POS_ID, rs.getString("posid"));
                                hmData.put(RuleNameParameter.POS_CODE, rs.getString("poscode"));
                                hmData.put(RuleNameParameter.TRX_DATE, rs.getString("dt_merc"));
                                hmData.put(RuleNameParameter.AMOUNT, rs.getString("amount"));
                                hmData.put(RuleNameParameter.PPN, rs.getString("ppn"));
                                hmData.put(RuleNameParameter.TOTAL_PAYMENT, total_payment);
                            }
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            log.error("saveDebet : " + ex.getMessage());
            ex.printStackTrace();
            return false;
        } finally {
            clearAllConnStatRS(conn, stat, rs);
            clearAllConnStatRS(conn, stat1, rs1);
            clearAllConnStatRS(conn, stat2, rs2);
//            clearAllConnStatRS(conn, stat4, rs4);
            clearStatment(stat3);
        }
        return true;
    }

}
