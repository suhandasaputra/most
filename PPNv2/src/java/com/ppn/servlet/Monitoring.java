package com.ppn.servlet;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.asacba.servlet;
//
////import com.asacba.process.CheckStatusPembayaran;
//import com.asacba.process.InquirySprint;
////import com.asacba.process.InquiryKodeBayarBPD;
//import com.asacba.process.PaymentSprint;
////import com.asacba.process.KonfirmasiPembayaranBPD;
////import com.asacba.process.RequestUlangKodeBayar;
//import com.asacbalib.database.DatabaseProcess;
//import com.asacbalib.function.CheckSumFunction;
//import com.asacbalib.function.JsonProcess;
//import com.asacbalib.singleton.ResponseCodeSingleton;
//import com.asaclib.parameter.RuleNameParameter;
//import com.asaclib.parameter.StaticParameter;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.HashMap;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import org.apache.log4j.Logger;
//
///**
// *
// * @author herrysuganda
// */
//public class Monitoring extends HttpServlet {
//
//    private static Logger log = Logger.getLogger(Monitoring.class);
//    private DatabaseProcess dp = new DatabaseProcess();
//
//    /**
//     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
//     * methods.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
//        String responseCode = "";
//
//        String line = "";
//        String inputString = "";
//        while ((line = in.readLine()) != null) {
//            inputString += line;
//        }
//
//        System.out.println("BPDNet Incoming : " + inputString);
//        log.info("BPDNet Incoming :" + inputString + "\n");
//
//        if ("".equals(inputString)) {
//            log.error("Masuk message kosong dari BPDNet\n");
//            response.getOutputStream().write("message tidak dikenali".getBytes());
//            response.getOutputStream().flush();
//            return;
//        }
//
//        HashMap hmData = JsonProcess.decodeJson(inputString);
//        dp.saveLogTrx("", hmData.get(RuleNameParameter.BANK_CODE).toString(), "",
//                hmData.get(RuleNameParameter.PROC_CODE).toString(), inputString, JsonProcess.generateJson(hmData));
////        int checksum = CheckSumFunction.getCheckSum(hmData.get(RuleNameParameter.PAY_CODE).toString());
////        checksum += CheckSumFunction.getCheckSum(hmData.get(RuleNameParameter.BANK_CODE).toString());
////        checksum += CheckSumFunction.getCheckSum(hmData.get(RuleNameParameter.REF_NO).toString());
////
////        if (!StaticParameter.PROCCODE_INQUIRY_KODE_BAYAR.equals(hmData.get(RuleNameParameter.PROC_CODE).toString())) {
////            checksum += CheckSumFunction.getCheckSum(hmData.get(RuleNameParameter.AMOUNT).toString());
////        }
////
////        if (Integer.parseInt(hmData.get(RuleNameParameter.CHECK_SUM).toString()) != checksum) {
////            try {
////                responseCode = "1003";
////                hmData.put(RuleNameParameter.RESP_CODE, responseCode);
////                hmData.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get(responseCode).toString());
////
////                checksum = CheckSumFunction.getCheckSum(hmData.get(RuleNameParameter.PAY_CODE).toString());
////                checksum += CheckSumFunction.getCheckSum(hmData.get(RuleNameParameter.RESP_CODE).toString());
////                checksum += CheckSumFunction.getCheckSum(hmData.get(RuleNameParameter.REF_NO).toString());
////                hmData.put(RuleNameParameter.CHECK_SUM, checksum);
////
////                response.getOutputStream().write(JsonProcess.generateJson(hmData).getBytes());
////                response.getOutputStream().flush();
////                return;
////            } catch (Exception ex) {
////                log.error(ex.getMessage());
////            }
////        }
//        try {
//            if (StaticParameter.PROCCODE_INQUIRY_KODE_BAYAR.equals(hmData.get(RuleNameParameter.PROC_CODE).toString())) {
//                hmData = new InquirySprint().process(hmData);
//            } else if (StaticParameter.PROCCODE_KONFIRMASI_PEMBAYARAN_DARI_BPD.equals(hmData.get(RuleNameParameter.PROC_CODE).toString())) {
//                hmData = new PaymentSprint().process(hmData);
//            }
//
//        } catch (Exception ex) {
//            log.error(ex.getMessage());
//            ex.printStackTrace();
//            responseCode = "2012";
//            hmData.put(RuleNameParameter.RESP_CODE, responseCode);
//            hmData.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get(responseCode).toString());
//
//        }
//        dp.updateLogTrx(hmData, responseCode);
//        response.getOutputStream().write(JsonProcess.generateJson(hmData).getBytes());
//        response.getOutputStream().flush();
//        System.out.println("berhasil");
//        log.info("OM outgoing :" + JsonProcess.generateJson(hmData).replace("\\", "") + "\n");
//    }
//
//    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
//    /**
//     * Handles the HTTP <code>GET</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Handles the HTTP <code>POST</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Returns a short description of the servlet.
//     *
//     * @return a String containing servlet description
//     */
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>
//
//}
