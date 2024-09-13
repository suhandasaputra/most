/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ppn.servlet;

import com.ppn.process.RequestResi;
import com.ppnlib.database.DatabaseProcess;
//import com.asacbalib.function.CheckSumFunction;
import com.ppnlib.function.JsonProcess;
import com.ppnlib.singleton.ResponseCodeSingleton;
import com.ppnlib.parameter.RuleNameParameter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.xml.sax.InputSource;
import java.util.ArrayList;
import java.io.StringReader;
import java.util.List;
import java.util.logging.Level;
import com.ppnlib.function.XMLFunction;
import org.json.simple.parser.ParseException;

/**
 *
 * @author adi
 */
public class OnlineMerchantXML extends HttpServlet {

    private static Logger log = Logger.getLogger(OnlineMerchantXML.class);
    private DatabaseProcess dp = new DatabaseProcess();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JDOMException, ParseException, java.text.ParseException {
        BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
        HashMap hmResponse = new HashMap();
        String responseCode = "";

        String line = "";
        String inputString = "";
        while ((line = in.readLine()) != null) {
            inputString += line;
        }
        inputString = inputString.replace("%20", " ");
        System.out.println("OM Incoming : " + inputString);
        log.info("OM Incoming :" + inputString + "\n");
        if ("".equals(inputString)) {
            log.error("Masuk message kosong dari online merchant\n");
            response.getOutputStream().write("message tidak dikenali".getBytes());
            response.getOutputStream().flush();
            return;
        }

        InputSource is = new InputSource(new StringReader(inputString));
        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(is);
        Element rootElement = document.getRootElement();
        Element detail = rootElement.getChild("DETAIL");
        Element total = (Element) detail.getContent().get(0);
        Element prodname = (Element) detail.getContent().get(1);
        Element sum_amount = (Element) detail.getContent().get(2);
        String StrProdname = "";
        String StrTotal = "";
        String StrSumAmount = "";
        HashMap hmData = new HashMap();

        List listDetail = new ArrayList();
        for (int i = 0; i < prodname.getContentSize(); i++) {
//            listDetail = new ArrayList();
            StrTotal = total.getContent().get(i).getValue();
            StrProdname = prodname.getContent().get(i).getValue();
            StrSumAmount = sum_amount.getContent().get(i).getValue();
            HashMap detail1 = new HashMap();
            detail1.put("PROD_NAME", StrProdname);
            detail1.put("TOTAL", StrTotal);
            detail1.put("SUM_AMOUNT", StrSumAmount);
            listDetail.add(detail1);
        }

        hmData.put(RuleNameParameter.POS_ID, rootElement.getChild(RuleNameParameter.POS_ID).getValue());
        hmData.put(RuleNameParameter.INSTITUTE_ID, rootElement.getChild(RuleNameParameter.INSTITUTE_ID).getValue());
        hmData.put(RuleNameParameter.POS_CODE, rootElement.getChild(RuleNameParameter.POS_CODE).getValue());
        hmData.put(RuleNameParameter.PPN, rootElement.getChild(RuleNameParameter.PPN).getValue());
        hmData.put(RuleNameParameter.RRN, rootElement.getChild(RuleNameParameter.RRN).getValue());
        hmData.put(RuleNameParameter.TRX_DATE, rootElement.getChild(RuleNameParameter.TRX_DATE).getValue());
        hmData.put(RuleNameParameter.AMOUNT, rootElement.getChild(RuleNameParameter.AMOUNT).getValue());
        hmData.put(RuleNameParameter.DETAIL, listDetail);
        String reqMsg = JsonProcess.generateJson(hmData);
        System.out.println("reqMsg : " + reqMsg);

//        HashMap hmData = JsonProcess.decodeJson(inputString);
//        if (hmData.get(RuleNameParameter.DATA) == null) {
//            try {
//                responseCode = "01";
//                hmResponse.put(RuleNameParameter.RESP_CODE, responseCode);
//                hmResponse.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get(responseCode).toString());
////                hmData.put(RuleNameParameter.DATA, JsonProcess.generateJson(hmResponse));
//                response.getOutputStream().write(JsonProcess.generateJson(hmData).getBytes());
//                response.getOutputStream().flush();
//                dp.saveLogTrx(hmData.get(RuleNameParameter.MERC_CODE).toString(), "", responseCode, "-", "-", JsonProcess.generateJson(hmResponse));
//                return;
//            } catch (Exception ex) {
//                log.error(ex.getMessage());
//            }
//        }
        hmData = new RequestResi().process(JsonProcess.decodeJson(reqMsg));
//        hmData.put(RuleNameParameter.DATA, JsonProcess.generateJson(hmData));
//                inputMap.put(RuleNameParameter.UNIQ_CODE, unqueId);

        hmData.remove(RuleNameParameter.UNIQ_CODE);
        response.getOutputStream().write(XMLFunction.convertHashmapToXML(hmData, "DATA").getBytes());
        response.getOutputStream().flush();
        dp.saveLogTrx(hmData.get(RuleNameParameter.INSTITUTE_ID).toString(), "", hmData.get(RuleNameParameter.RESP_CODE).toString(),
                "", inputString, XMLFunction.convertHashmapToXML(hmData, "DATA"), hmData.get(RuleNameParameter.NO_RESI).toString(), hmData.get(RuleNameParameter.RESP_DESC).toString());
        System.out.println("berhasil ");
        log.info("OM Outgoing :" + XMLFunction.convertHashmapToXML(hmData, "DATA") + "\n");

        System.out.println("Outgoing : " + XMLFunction.convertHashmapToXML(hmData, "DATA"));

    }

//    System.out.println ("berhasil");
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JDOMException ex) {
            java.util.logging.Logger.getLogger(OnlineMerchantXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            java.util.logging.Logger.getLogger(OnlineMerchantXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (java.text.ParseException ex) {
            java.util.logging.Logger.getLogger(OnlineMerchantXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JDOMException ex) {
            java.util.logging.Logger.getLogger(OnlineMerchantXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            java.util.logging.Logger.getLogger(OnlineMerchantXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (java.text.ParseException ex) {
            java.util.logging.Logger.getLogger(OnlineMerchantXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
