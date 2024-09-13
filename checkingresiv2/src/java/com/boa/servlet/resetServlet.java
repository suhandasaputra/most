/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boa.servlet;

import java.io.IOException;
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
public class resetServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
//        session.removeAttribute("NO_RESI");
//        session.removeAttribute("TRX_DATE");
//        session.removeAttribute("AMOUNT");
//        session.removeAttribute("PPN");
//        session.removeAttribute("DETAIL");
//        session.removeAttribute("INSTITUSI_ID");
//        session.removeAttribute("POS_ID");
//        session.removeAttribute("KODE_POS");
        session.invalidate();
//        session.getMaxInactiveInterval();
        response.sendRedirect("checking_resi.jsp");
    }
}
