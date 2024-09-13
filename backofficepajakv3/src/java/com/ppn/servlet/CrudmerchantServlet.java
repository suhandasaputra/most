/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ppn.servlet;

//import com.boa.dao.BillerProduct;
//import com.boa.dao.UserYabes;
import com.ppn.model.merchant;
import com.ppn.util.DatabaseProcess;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author syukur
 */
public class CrudmerchantServlet extends HttpServlet {

    DatabaseProcess dp = new DatabaseProcess();

    private static final long serialVersionUID = 1L;
//    private BillerProduct dao;
//    private UserYabes dao1;
//    private static String INSERT = "add_product_biller.jsp";
    private static String UPDATE = "updateMerchant.jsp";

    public CrudmerchantServlet() {
//        dao = new BillerProduct();
//        dao1 = new UserYabes();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");
        if (action.equalsIgnoreCase("delete")) {

            System.out.println("ini adalah hasilnya");
            String instituteid = request.getParameter("instituteid");
            String posid = request.getParameter("posid");
                        String poscode = request.getParameter("poscode");
            String status = dp.deletePosid(instituteid, posid, poscode);
            String status1 = "gagal menghapus MERCHANT POS";
            if (status.equals("Sukses menghapus MERCHANT POS")) {
                HttpSession session = request.getSession(true);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/listmerchant.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>" + status + "\n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/listmerchant.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i>" + status1 + "\n"
                        + "</div>");
                rd.include(request, response);
            }
        } else if (action.equalsIgnoreCase("update")) {
            forward = UPDATE;
            String instituteid = request.getParameter("instituteid");
            String posid = request.getParameter("posid");
            String poscode = request.getParameter("poscode");
            merchant merc = dp.getMerchantByPosid(instituteid, posid, poscode);
            request.setAttribute("merc", merc);
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");
        if (action.equalsIgnoreCase("updatemerchant")) {
            merchant merc = new merchant();
            merc.setInstituteid(request.getParameter("instituteid"));
            merc.setInstitutename(request.getParameter("institutename"));
            merc.setPosid(request.getParameter("posid"));
            merc.setPoscode(request.getParameter("poscode"));
            merc.setKey(request.getParameter("key"));
            merc.setAlamat(request.getParameter("alamat"));
            merc.setEmail(request.getParameter("email"));
            merc.setNpwp(request.getParameter("npwp"));
            merc.setMerek(request.getParameter("merek"));
            merc.setTipe(request.getParameter("tipe"));
            merc.setSerial_number(request.getParameter("serial_number"));
            merc.setImei(request.getParameter("imei"));
            merc.setNopd(request.getParameter("nopd"));

            String status = dp.updateMerchant(merc);
            String status1 = "Gagal, mengupdate merchant";
            if (status.equals("Sukses mengupdate merchant")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/listmerchant.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>" + status + "\n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/listmerchant.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i>" + status1 + "\n"
                        + "</div>");
                rd.include(request, response);
            }
        }
    }
}
