/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ppn.servlet;

//import com.mbo.dao.MonitoringPpob;
import com.ppn.model.merchant;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.ppn.util.DatabaseProcess;
import java.util.List;
import com.google.gson.JsonArray;

/**
 *
 * @author syukur
 */
public class merchantServlet extends HttpServlet {
    
    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(merchantServlet.class);

    private static final long serialVersionUID = 1L;

    public merchantServlet() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        ArrayList<Monitoringppobs> ppob = new ArrayList<Monitoringppobs>();
//        ppob = DatabaseProcess.getAllPpobs();
        try {
            ArrayList<merchant> ppob = new ArrayList<merchant>();
            DatabaseProcess dp = new DatabaseProcess();
            ppob = dp.getMerchantstatus();
            Gson gson = new Gson();
            JsonElement element = gson.toJsonTree(ppob, new TypeToken<List<merchant>>() {
            }.getType());

            JsonArray jsonArray = element.getAsJsonArray();
            log.info(jsonArray);
            response.setContentType("application/json");
            response.getWriter().print(jsonArray);
        } catch (Exception e) {
            System.out.println("Error : " + e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
