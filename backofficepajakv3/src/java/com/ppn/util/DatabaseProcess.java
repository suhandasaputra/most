/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ppn.util;

import com.ppn.entity.ConvertDateFormat;
import com.ppn.model.Monitoringppobmonth;
import com.ppn.model.Monitoringppobtoday;
import com.ppn.model.Monitoringppobyear;
import com.ppn.model.Reportyabes;
import com.ppn.model.merchant;
import com.ppn.servlet.DatasourceEntry;
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

    public String deletePosid(String instituteid, String posid, String poscode) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String status;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("delete from merchant where instituteid=? and posid=?");
            stat.setString(1, instituteid);
            stat.setString(2, posid);
            stat.executeUpdate();
            stat.close();
            
            stat = conn.prepareStatement("delete from merchant_balance where instituteid=?");
            stat.setString(1, instituteid);
            stat.executeUpdate();
            stat.close();
            
            stat = conn.prepareStatement("delete from active where instituteid=? and posid=?");
            stat.setString(1, instituteid);
            stat.setString(2, posid);
            stat.executeUpdate();
            
            
        } catch (SQLException e) {
            e.printStackTrace();
            return status = e.getMessage();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return status = "Sukses menghapus MERCHANT POS";
    }

    public merchant getMerchantByPosid(String instituteid, String posid, String poscode) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        merchant merc = new merchant();
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat = conn.prepareStatement("select * from merchant where instituteid=? and posid=? and poscode=?");
            stat.setString(1, instituteid);
            stat.setString(2, posid);
            stat.setString(3, poscode);
            rs = stat.executeQuery();
            while (rs.next()) {
                merc.setInstituteid(rs.getString("instituteid"));
                merc.setPosid(rs.getString("posid"));
                merc.setInstitutename(rs.getString("institutename"));
                merc.setPoscode(rs.getString("poscode"));
                merc.setKey(rs.getString("key"));
                merc.setAlamat(rs.getString("alamat"));
                merc.setEmail(rs.getString("email"));
                merc.setNpwp(rs.getString("npwp"));
                merc.setMerek(rs.getString("merek"));
                merc.setTipe(rs.getString("tipe"));
                merc.setSerial_number(rs.getString("serial_number"));
                merc.setImei(rs.getString("imei"));
                merc.setNopd(rs.getString("nopd"));

            }
//            System.out.println("key nya :"+rs.getString("key"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return merc;
    }

    public String updateMerchant(merchant merc) {
        System.out.println("masuk ga nih");
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        String status;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
//            stat = conn.prepareStatement("update merchant set institutename=?, posid=?, alamat=?, email=?, npwp=?, merek=?, tipe=?, serial_number=?, imei=?, nopd=? where instituteid=? and posid=? and poscode=?");

            stat = conn.prepareStatement("update merchant set institutename=?, alamat=?, email=?, npwp=?, merek=?, tipe=?, serial_number=?, imei=?, nopd=? where instituteid=? and posid=? and poscode=?");
//            stat.setString(1, merc.getInstituteid());
            stat.setString(1, merc.getInstitutename());
            stat.setString(2, merc.getAlamat());
            stat.setString(3, merc.getEmail());
            stat.setString(4, merc.getNpwp());
            stat.setString(5, merc.getMerek());
            stat.setString(6, merc.getTipe());
            stat.setString(7, merc.getSerial_number());
            stat.setString(8, merc.getImei());
            stat.setString(9, merc.getNopd());
            stat.setString(10, merc.getInstituteid());
            stat.setString(11, merc.getPosid());
            stat.setString(12, merc.getPoscode());
//            stat.setString(5, merc.getInstituteid());
//            stat.setString(6, merc.getPosid());
//            stat.setString(7, merc.getPoscode());

            stat.executeUpdate();
        } catch (SQLException e) {
            return status = e.getMessage();
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return status = "Sukses mengupdate merchant";
    }

    public String addMerchant(merchant merchant) {
        Connection conn = null;
        String status;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            boolean status1 = false;
            stat = conn.prepareStatement("select * from merchant where instituteid=? and posid=? and poscode=?");
            stat.setString(1, merchant.getInstituteid());
            stat.setString(2, merchant.getPosid());
            stat.setString(3, merchant.getPoscode());
            rs = stat.executeQuery();
            status1 = rs.next();
            stat.close();
            if (status1 == true) {
                return status = "Maaf Merchant sudah ada";
            } else {
                stat = conn.prepareStatement("INSERT INTO merchant(instituteid, institutename, posid, key, poscode, alamat, email, npwp, merek, tipe, serial_number, imei, nopd) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                stat.setString(1, merchant.getInstituteid());
                stat.setString(2, merchant.getInstitutename());
                stat.setString(3, merchant.getPosid());
                stat.setString(4, merchant.getKey());
                stat.setString(5, merchant.getPoscode());
                stat.setString(6, merchant.getAlamat());
                stat.setString(7, merchant.getEmail());
                stat.setString(8, merchant.getNpwp());
                stat.setString(9, merchant.getMerek());
                stat.setString(10, merchant.getTipe());
                stat.setString(11, merchant.getSerial_number());
                stat.setString(12, merchant.getImei());
                stat.setString(13, merchant.getNopd());
                stat.executeUpdate();
                stat.close();
                
                stat = conn.prepareStatement("INSERT INTO active (instituteid, posid, poscode, key) VALUES (?, ?, ?, ?)");
                stat.setString(1, merchant.getInstituteid());
                stat.setString(2, merchant.getPosid());
                stat.setString(3, merchant.getPoscode());
                stat.setString(4, merchant.getKey());
                stat.executeUpdate();
                stat.close();
                
                stat = conn.prepareStatement("INSERT INTO merchant_balance (instituteid, balance) VALUES (?, 0)");
                stat.setString(1, merchant.getInstituteid());
                stat.executeUpdate();
                
                
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            clearAllConnStatRS(conn, stat, rs);
        }
        return status = "Sukses menambahkan Merchant";
    }

    public ArrayList<Reportyabes> getAlltransactionyabes() throws ParseException {
        ArrayList<Reportyabes> listTransaction = new ArrayList<Reportyabes>();
        SimpleDateFormat requesttime1 = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat requesttime3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        SimpleDateFormat requesttime2 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
        Connection conn = null;
        PreparedStatement stat1 = null;
        ResultSet rs1 = null;
        PreparedStatement stat2 = null;
        ResultSet rs2 = null;

        try {
            conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
            stat1 = conn.prepareStatement("select * from (select * from listtax union all select * from listtax_backup ) as noresi");
            rs1 = stat1.executeQuery();
            while (rs1.next()) {
                Reportyabes reportyabes = new Reportyabes();
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
                        listdetail = (JSONArray) parser.parse(rs1.getString("detail"));
                        System.out.println("ini detailnya brow jj :" + rs1.getString("detail"));
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
                } else if(rs1.getInt("payment_methode") == 0) {
                    reportyabes.setPayment_methode("Cash");
                } else if(rs1.getInt("payment_methode") == 1) {
                    reportyabes.setPayment_methode("Debet");
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
                if (rs1.getString("status_paid") == null) {
                    reportyabes.setStatus_paid("-");
                } else if(rs1.getInt("status_paid") == 0) {
                    reportyabes.setStatus_paid("Belum Terbayar");
                } else if(rs1.getInt("status_paid") == 1) {
                    reportyabes.setStatus_paid("Terbayar");
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

    public ArrayList<merchant> getMerchantstatus() throws SQLException {
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
            stat1 = conn.prepareStatement("select instituteid, posid, institutename, poscode, key, alamat, email, npwp, merek, tipe, serial_number, imei, nopd from merchant");
            rs1 = stat1.executeQuery();
            while (rs1.next()) {
                merchant ppob = new merchant();
                ppob.setInstituteid(rs1.getString("instituteid"));
                ppob.setPosid(rs1.getString("posid"));
                ppob.setPoscode(rs1.getString("poscode"));
                ppob.setInstitutename(rs1.getString("institutename"));

                ppob.setKey(rs1.getString("key"));
                ppob.setAlamat(rs1.getString("alamat"));
                ppob.setEmail(rs1.getString("email"));
                ppob.setNpwp(rs1.getString("npwp"));
                ppob.setMerek(rs1.getString("merek"));
                ppob.setTipe(rs1.getString("tipe"));
                ppob.setSerial_number(rs1.getString("serial_number"));
                ppob.setImei(rs1.getString("imei"));
                ppob.setNopd(rs1.getString("nopd"));
//                ppob.setInstitutename(rs1.getString("institutename"));

                stat2 = conn.prepareStatement("select dt_merc from listtax where instituteid='" + rs1.getString("instituteid") + "' and posid='" + rs1.getString("posid") + "' order by posid LIMIT 1");
                rs2 = stat2.executeQuery();
                if (rs2.next()) {
                    ppob.setStatus("green");
                    ppob.setDt_merc(rs2.getString("dt_merc"));
                    ppob.setDt_merc(ConvertDateFormat.formatDate(rs2.getString("dt_merc"), "yyyyMMddHHmmss", "dd-MMM-yyyy HH:mm:ss"));
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
                stat2 = conn.prepareStatement("select count(posid) as transaction, SUM(amount) as amount, SUM(ppn) as ppn from listtax where instituteid='" + rs1.getString("instituteid") + "' and posid='" + rs1.getString("posid") + "' and dt_internal > current_date group by instituteid order by instituteid desc");
                rs2 = stat2.executeQuery();
                if (rs2.next()) {
                    ppobtoday.setTransaction(rs2.getString("transaction"));
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
        ArrayList<Monitoringppobmonth> ppobmonthList = new ArrayList<Monitoringppobmonth>();
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
                Monitoringppobmonth ppobmonth = new Monitoringppobmonth();
                ppobmonth.setInstituteid(rs1.getString("instituteid"));
                ppobmonth.setInstitutename(rs1.getString("institutename"));
                ppobmonth.setPosid(rs1.getString("posid"));
//                conn = DatasourceEntry.getInstance().getPostgreDS().getConnection();
//                stat2 = conn.prepareStatement("select count(posid) as transaction, SUM(amount) as amount, SUM(ppn) as ppn from listtax where instituteid='"+rs1.getString("instituteid")+"' and posid='" + rs1.getString("posid") + "' group by instituteid order by instituteid desc");
                stat2 = conn.prepareStatement("select count(posid) as transaction, SUM(amount) as amount, SUM(ppn) as ppn from listtax where instituteid='" + rs1.getString("instituteid") + "' and posid='" + rs1.getString("posid") + "' and dt_internal > current_date -11 group by instituteid order by instituteid desc");

                rs2 = stat2.executeQuery();
                if (rs2.next()) {
                    ppobmonth.setTransaction(rs2.getString("transaction"));
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
                stat2 = conn.prepareStatement("select count(posid) as transaction, SUM(amount) as amount, SUM(ppn) as ppn from listtax where instituteid='" + rs1.getString("instituteid") + "' and posid='" + rs1.getString("posid") + "' group by instituteid order by instituteid desc");
                rs2 = stat2.executeQuery();
                if (rs2.next()) {
                    ppobyear.setTransaction(rs2.getString("transaction"));
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
}
