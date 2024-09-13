/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ppn.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
//import com.mbo.dao.MonitoringppobMonth;
import com.ppn.model.Monitoringppobmonth;
import com.ppn.util.DatabaseProcess;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author syukur
 */
public class MonitoringppobmonthServlet extends HttpServlet {

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(MonitoringppobmonthServlet.class);

    private static final long serialVersionUID = 1L;

    public MonitoringppobmonthServlet() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Monitoringppobmonth> alltoday = new ArrayList<Monitoringppobmonth>();
        DatabaseProcess dp = new DatabaseProcess();
        alltoday = dp.getPpobMonth();
        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(alltoday, new TypeToken<List<Monitoringppobmonth>>() {
        }.getType());

        JsonArray jsonArray = element.getAsJsonArray();
        log.info(jsonArray);
        response.setContentType("application/json");
        response.getWriter().print(jsonArray);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
