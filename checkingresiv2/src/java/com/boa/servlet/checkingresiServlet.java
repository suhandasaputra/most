/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boa.servlet;

//import com.boa.dao.LoginProcess;
import com.boa.entity.SendHttpProcessQuantum;
import com.cr.parameter.checkParameter;
import com.ppnlib.function.JsonProcess;
import com.ppnlib.function.SHA256Enc;
//import com.yabes.function.JsonProcess;
//import com.yabes.function.SHA256Enc;
//import com.yabes.function.StringFunction;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author syukur
 */
public class checkingresiServlet extends HttpServlet {

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(checkingresiServlet.class);

    private static final long serialVersionUID = 1L;

    public checkingresiServlet() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String statusgagal = "Nomor Resi tidak ada";
        String statusberhasil = "Berhasil";
        HashMap req = new HashMap();
        req.put(checkParameter.DISP_USER, "iqbal");
        req.put(checkParameter.DISP_PASS, SHA256Enc.encryptProc("123456"));
        req.put(checkParameter.NO_RESI, request.getParameter("noresi"));
        req.put(checkParameter.SPLIT_BILL, request.getParameter("split_bill"));
        System.out.println(" REQ : " + req.toString());
        String reqMsg = JsonProcess.generateJson(req);
//        String respMsg = new SendHttpProcessQuantum().sendHttpRequest("http://app.q-payment.com:8080/PPNv2/OnlineCustomer", reqMsg);
        String respMsg = new SendHttpProcessQuantum().sendHttpRequest("http://localhost:8080/PPNv2/OnlineCustomer", reqMsg);
        HashMap resultresponse = JsonProcess.decodeJson(respMsg);
        System.out.println("response nya : " + resultresponse);
        if (resultresponse.get("RESP_CODE").equals("0000")) {
            JSONArray listdetail = null;
            JSONParser parser = new JSONParser();
            try {
                listdetail = (JSONArray) parser.parse(resultresponse.get("DETAIL").toString());
            } catch (ParseException ex) {
                Logger.getLogger(checkingresiServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            HttpSession session = request.getSession(false);
//            session.setAttribute("data", resultresponse.get("DATA"));
            session.setAttribute("NO_RESI", resultresponse.get("NO_RESI"));
            session.setAttribute("SPLIT_BILL", resultresponse.get("SPLIT_BILL"));
            if (resultresponse.get("PAYMENT_METHODE").equals("0")) {
                session.setAttribute("PAYMENT_METHODE", "CASH");
            } else if (resultresponse.get("PAYMENT_METHODE").equals("1")) {
                session.setAttribute("PAYMENT_METHODE", "DEBET");
            }
            session.setAttribute("CARD_NUMBER", resultresponse.get("CARD_NUMBER"));

            SimpleDateFormat requesttime1 = new SimpleDateFormat("yyyyMMddHHmmss");
            SimpleDateFormat requesttime2 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");

            Date tgl_trx;
            try {
                tgl_trx = requesttime1.parse(resultresponse.get("TRX_DATE").toString());
                session.setAttribute("TRX_DATE", requesttime2.format(tgl_trx));
            } catch (java.text.ParseException ex) {
                Logger.getLogger(checkingresiServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            session.setAttribute("AMOUNT", resultresponse.get("AMOUNT"));
            session.setAttribute("PPN", resultresponse.get("PPN"));
            session.setAttribute("SERVICE_TAX", resultresponse.get("SERVICE_TAX"));
            session.setAttribute("INSTITUTE_ID", resultresponse.get("INSTITUTE_ID"));
            session.setAttribute("INSTITUTE_NAME", resultresponse.get("INSTITUTE_NAME"));
            session.setAttribute("POS_ID", resultresponse.get("POS_ID"));
            session.setAttribute("POS_CODE", resultresponse.get("POS_CODE"));
//            session.setAttribute("NOPD", resultresponse.get("NOPD"));
//            session.setAttribute("SOURCE", resultresponse.get("SOURCE"));

            session.setAttribute("PROD_NAME", " ");
            for (int i = 0; i < listdetail.size(); i++) {
                HashMap item = JsonProcess.decodeJson(listdetail.get(i).toString());
                if (i == 0) {
                    session.setAttribute("PROD_NAME", session.getAttribute("PROD_NAME").toString() + item.get("PROD_NAME") + " " + item.get("TOTAL") + " " + "Rp" + item.get("SUM_AMOUNT"));
                } else {
                    session.setAttribute("PROD_NAME", session.getAttribute("PROD_NAME").toString() + ", " + item.get("PROD_NAME") + " " + item.get("TOTAL") + " " + "Rp" + item.get("SUM_AMOUNT"));
                }
            }
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/checking_resi.jsp");
            PrintWriter out = response.getWriter();
            out.println("<div class=\"alert alert-success status-custom\">\n"
                    + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                    + "     <i class=\"icon fa fa-warning\"></i>" + statusberhasil + "\n"
                    + "</div>");
            rd.include(request, response);
        } else {
            HttpSession session = request.getSession(false);
            session.invalidate();
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/checking_resi.jsp");
            PrintWriter out = response.getWriter();
            out.println("<div class=\"alert alert-danger status-custom\">\n"
                    + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                    + "     <i class=\"icon fa fa-warning\"></i>" + statusgagal + "\n"
                    + "</div>");
            
            
            
            out.println("<div class=\"container-md\" style=\"; height: auto; width: 500px; text-align-last: center;\">\n" +
"            <div class=\"row row-cols-1 row-cols-lg-1\">\n" +
"                <table style=\"font-size: 12px\">\n" +
"                    <tr>\n" +
"                        <th onclick=\"detail_global('"+statusgagal+"')\" colspan=\"12\" style=\"background-color: ` + global_data.status + `; color: ` + global_data_c + `; border:2px solid white\">` + detail.device_name.replace(\"RADAR\", \"\") + `</th>\n" +
"                    </tr>\n" +
"                    <tr>\n" +
"                        <td onclick=\"detail_sensors()\" colspan=\"2\" style=\"background-color: ` + sensor_data.status + `; color: ` + sensor_data_c + `; border:2px solid white\">Sensors</td>\n" +
"                        <td onclick=\"detail_infra()\" colspan=\"10\" style=\"background-color: ` + infrastructure_data.status + `; color: ` + infrastructure_data_c + `; border:2px solid white\">Infrastructure</td>\n" +
"                    </tr>\n" +
"                    <tr>\n" +
"                        <td style=\"background-color: ` + mssr_data.status + `; color: ` + mssr_data_c + `; border:2px solid white\">MSSR-S</td>\n" +
"                        <td style=\"background-color: ` + pedestal_data.status + `; color: ` + pedestal_data_c + `; border:2px solid white\">PEDESTAL</td>\n" +
"                        <td colspan=\"2\" style=\"background-color: ` + clocks_data.status + `; color: ` + clocks_data_c + `; border:2px solid white\">Clocks</td>\n" +
"                        <td colspan=\"5\" style=\"background-color: ` + network_data.status + `; color: ` + network_data_c + `; border:2px solid white\">NETWORK</td>\n" +
"                        <td style=\"background-color: ` + sdcs_data.status + `; color: ` + sdcs_data_c + `; border:2px solid white\">SDCS</td>\n" +
"                        <td style=\"background-color: ` + aei_data.status + `; color: ` + aei_data_c + `; border:2px solid white\">AEI</td>\n" +
"                        <td style=\"background-color: ` + cms_data.status + `; color: ` + cms_data_c + `; border:2px solid white\">CMS</td>\n" +
"                    </tr>\n" +
"                    <tr>\n" +
"                        <td colspan=\"2\"></td>\n" +
"                        <td style=\"background-color: ` + ntp1_data.status + `; color: ` + ntp1_data_c + `; border:2px solid white\">NTP1</td>\n" +
"                        <td style=\"background-color: ` + ntp2_data.status + `; color: ` + ntp2_data_c + `; border:2px solid white\">NTP2</td>\n" +
"                        <td style=\"background-color: ` + tx_data.status + `; color: ` + tx_data_c + `; border:2px solid white\">TX</td>\n" +
"                        <td style=\"background-color: ` + switch1_data.status + `; color: ` + switch1_data_c + `; border:2px solid white\">SW1</td>\n" +
"                        <td style=\"background-color: ` + switch2_data.status + `; color: ` + switch2_data_c + `; border:2px solid white\">SW2</td>\n" +
"                        <td style=\"background-color: ` + lan1_data.status + `; color: ` + lan1_data_c + `; border:2px solid white\">L1</td>\n" +
"                        <td style=\"background-color: ` + lan2_data.status + `; color: ` + lan2_data_c + `; border:2px solid white\">L2</td>\n" +
"                        <td colspan=\"3\"></td>\n" +
"                    </tr>\n" +
"                    <tr style=\"height: 40px; color:#66b1ff\">\n" +
"                        <td colspan=\"3\" id=\"mantap\"><i class=\"fa fa-bar-chart\" style=\"margin-right: 5px\"></i>Avaibility</td>\n" +
"                        <td colspan=\"4\"><i class=\"fa fa-bar-chart\" style=\"margin-right: 5px\"></i>Statistik</td>\n" +
"                        <td colspan=\"5\"><i class=\"fa fa-file-text-o\" style=\"margin-right: 5px;\"></i>Catatan Inspektur</td>\n" +
"                    </tr>\n" +
"                    <tr style=\"height: 40px;text-align-last: left;border-top:1px solid #eff1f5\">\n" +
"                        <td colspan=\"3\" style=\"padding-left: 20px;\">Device ID</td>\n" +
"                        <td colspan=\"4\">` + detail.device_id + `</td>\n" +
"                        <td colspan=\"5\">0</td>\n" +
"                    </tr>\n" +
"                    <tr style=\"height: 40px;text-align-last: left;border-top:1px solid #eff1f5\">\n" +
"                        <td colspan=\"3\" style=\"padding-left: 20px\">Device Name</td>\n" +
"                        <td colspan=\"4\">` + detail.device_name + `</td>\n" +
"                    </tr>\n" +
"                    <tr style=\"height: 40px;text-align-last: left;border-top:1px solid #eff1f5\">\n" +
"                        <td colspan=\"3\" style=\"padding-left: 20px\">Device Category</td>\n" +
"                        <td colspan=\"4\">` + detail.device_category + `</td>\n" +
"                    </tr>\n" +
"                    <tr style=\"height: 40px;text-align-last: left;border-top:1px solid #eff1f5\">\n" +
"                        <td colspan=\"3\" style=\"padding-left: 20px\">Device Group</td>\n" +
"                        <td colspan=\"4\">` + detail.device_group + `</td>\n" +
"                    </tr>\n" +
"                    <tr style=\"height: 40px;text-align-last: left;border-top:1px solid #eff1f5\">\n" +
"                        <td colspan=\"3\" style=\"padding-left: 20px\">System</td>\n" +
"                        <td colspan=\"4\">` + detail.device_group + `</td>\n" +
"                    </tr>\n" +
"                    <tr style=\"height: 40px;text-align-last: left;border-top:1px solid #eff1f5\">\n" +
"                        <td colspan=\"3\" style=\"padding-left: 20px\">Location</td>\n" +
"                        <td colspan=\"4\">` + detail.location_name + `</td>\n" +
"                    </tr>\n" +
"                </table>\n" +
"            </div>\n" +
"        </div>");
            rd.include(request, response);
        }
    }
}
