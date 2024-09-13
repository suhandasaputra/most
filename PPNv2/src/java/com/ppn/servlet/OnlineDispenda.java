/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ppn.servlet;

import com.ppn.process.DetailResi;
import com.ppn.process.Login;
import com.ppn.process.Login_merchant;
import com.ppn.process.Report;
import com.ppnlib.database.DatabaseProcess;
import com.ppnlib.function.JsonProcess;
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

/**
 *
 * @author adi
 */
public class OnlineDispenda extends HttpServlet {

    private static Logger log = Logger.getLogger(OnlineDispenda.class);
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
            throws ServletException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
        HashMap hmResponse = new HashMap();
        String responseCode = "";
        String line = "";
        String inputString = "";
        HashMap hmData;
        DatabaseProcess db = new DatabaseProcess();
        while ((line = in.readLine()) != null) {
            inputString += line;
        }
        inputString = inputString.replace("%20", " ");
        log.info("\n" + "Dispenda Message Incoming :" + inputString + "\n");
        try {
            hmData = JsonProcess.decodeJson(inputString);
//            if (hmData.get(RuleNameParameter.DISP_USER) == null
//                    || !db.userCustomerCheck(hmData)) {
//                hmData.put(RuleNameParameter.RESP_CODE, "03");
//                hmData.put(RuleNameParameter.RESP_DESC, "Gagal, mohon teliti kembali");
//            }
            if (hmData.get(RuleNameParameter.PROC_CODE).toString().equals("101")) {
                System.out.println("masuk 101 coy......");
                hmData = new Login().process(hmData);
            } else if (hmData.get(RuleNameParameter.PROC_CODE).toString().equals("500")) {
                hmData = new Login_merchant().process(hmData);
            }
            else if (hmData.get(RuleNameParameter.PROC_CODE).toString().equals("900")) {
                hmData = new Report().process(hmData);
            }
            log.info("\n" + "Dispenda Message outgoing :" + JsonProcess.generateJson(hmData) + "\n");
            response.getOutputStream().write(JsonProcess.generateJson(hmData).getBytes());
            response.getOutputStream().flush();
        } catch (Exception e) {
            hmData = new HashMap();
            hmData.put(RuleNameParameter.RESP_CODE, "0030");
            hmData.put(RuleNameParameter.RESP_DESC, "message tidak dikenali");
            log.info("\n" + "Dispenda Message outgoing :" + JsonProcess.generateJson(hmData) + "\n");
            response.getOutputStream().write(JsonProcess.generateJson(hmData).getBytes());
            response.getOutputStream().flush();
        }
    }

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
        processRequest(request, response);
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
        processRequest(request, response);
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
