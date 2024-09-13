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
//import com.boa.dao.ReportYabes;
import com.ppn.model.Report;
import com.ppnmonitoring.util.DatabaseProcess;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author syukur
 */
public class ReportServlet extends HttpServlet {
    DatabaseProcess dp = new DatabaseProcess();

    private static final long serialVersionUID = 1L;
//    private ReportYabes dao;
//    private static String UPDATE = "update_limit.jsp";

    public ReportServlet() {
//        dao = new ReportYabes();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("Listtransaction")) {
            ArrayList<Report> listTransaction = new ArrayList<Report>();
            try {
                listTransaction = dp.getAlltransaction();
            } catch (ParseException ex) {
                Logger.getLogger(ReportServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            Gson gson = new Gson();
            JsonElement element = gson.toJsonTree(listTransaction, new TypeToken<List<Report>>() {
            }.getType());
            JsonArray jsonArray = element.getAsJsonArray();
            response.setContentType("application/json");
            response.getWriter().print(jsonArray);
        } else if (action.equalsIgnoreCase("update")) {
        }
    }
}
