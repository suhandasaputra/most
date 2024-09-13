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
//import com.mbo.dao.MonitoringppobYear;
import com.ppn.model.Monitoringppobyear;
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
public class MonitoringppobyearServlet extends HttpServlet {

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(MonitoringppobyearServlet.class);

    private static final long serialVersionUID = 1L;

    public MonitoringppobyearServlet() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Monitoringppobyear> alltoday = new ArrayList<Monitoringppobyear>();
        DatabaseProcess dp = new DatabaseProcess();
        alltoday = dp.getPpobYear();
        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(alltoday, new TypeToken<List<Monitoringppobyear>>() {
        }.getType());

        JsonArray jsonArray = element.getAsJsonArray();
        response.setContentType("application/json");
        log.info(jsonArray);
        response.getWriter().print(jsonArray);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
