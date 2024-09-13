/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ppnmonitoring.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
//import com.mbo.dao.MonitoringppobToday;
import com.ppn.model.Monitoringppobtoday;
import com.ppnmonitoring.util.DatabaseProcess;
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
public class MonitoringppobtodayServlet extends HttpServlet {

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(MonitoringppobtodayServlet.class);

    private static final long serialVersionUID = 1L;

    public MonitoringppobtodayServlet() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Monitoringppobtoday> alltoday = new ArrayList<Monitoringppobtoday>();
        DatabaseProcess dp = new DatabaseProcess();
        alltoday = dp.getPpobToday();
        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(alltoday, new TypeToken<List<Monitoringppobtoday>>() {
        }.getType());

        JsonArray jsonArray = element.getAsJsonArray();
        log.info(jsonArray);
        response.setContentType("application/json");
        response.getWriter().print(jsonArray);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
