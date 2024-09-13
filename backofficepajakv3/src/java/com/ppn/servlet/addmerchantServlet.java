/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ppn.servlet;

import com.ppn.model.merchant;
import com.ppn.util.DatabaseProcess;
//import com.boa.dao.AgentYabes;
//import com.boa.dao.UserYabes;
//import com.boa.model.Agentyabes;
//import com.boa.util.DatabaseProcess;
//import com.yabes.function.SHA256Enc;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author syukur
 */
public class addmerchantServlet extends HttpServlet {

    DatabaseProcess dp = new DatabaseProcess();
    private static final long serialVersionUID = 1L;
//    private AgentYabes dao;
//    private UserYabes dao1;
    private static String INSERT = "add_agent.jsp";
    private static String UPDATE = "update_agent.jsp";

    public addmerchantServlet() {
//        dao = new AgentYabes();
//        dao1 = new UserYabes();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");
        if (action.equalsIgnoreCase("addmerchant")) {
            merchant mrc = new merchant();
            mrc.setInstituteid(request.getParameter("instituteid"));
            mrc.setInstitutename(request.getParameter("institutename"));
            mrc.setPosid(request.getParameter("posid"));
            mrc.setPoscode(request.getParameter("poscode"));
            mrc.setKey(request.getParameter("key"));

            mrc.setAlamat(request.getParameter("alamat"));
            mrc.setEmail(request.getParameter("email"));
            mrc.setNpwp(request.getParameter("npwp"));
            mrc.setMerek(request.getParameter("merek"));
            mrc.setTipe(request.getParameter("tipe"));
            mrc.setSerial_number(request.getParameter("serial_number"));
            mrc.setImei(request.getParameter("imei"));
            mrc.setNopd(request.getParameter("nopd"));

            String status = dp.addMerchant(mrc);
            if (status.equalsIgnoreCase("Sukses menambahkan Merchant")) {
//                HttpSession session = request.getSession(true);
//                String username = session.getAttribute("username").toString();
//                String activitas = "menambahkan agent yabes " + request.getParameter("agentId");
//                dp.userYabes(username, activitas);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/listmerchant.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-check\"></i>" + status + "\n"
                        + "</div>");
                rd.include(request, response);
            } else if (status.equalsIgnoreCase("Maaf Merchant sudah ada")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/addmerchant.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i>" + status + "\n"
                        + "</div>");
                rd.include(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/addmerchant.jsp");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i>" + status + "\n"
                        + "</div>");
                rd.include(request, response);
            }
        }
//        else if (action.equalsIgnoreCase("updateagent")) {
//
//            Agentyabes agentyabes = new Agentyabes();
//            agentyabes.setAgent_id(request.getParameter("agent_id"));
//            agentyabes.setAgent_name(request.getParameter("agent_name"));
//            agentyabes.setAgent_address(request.getParameter("address"));
//            agentyabes.setAgent_phone(request.getParameter("phonenumber"));
//            agentyabes.setAgent_pass(SHA256Enc.encryptProc("123456"));
//            agentyabes.setAgent_pin(SHA256Enc.encryptProc("123456"));
////            agentyabes.setAgent_pass(SHA256Enc.encryptProc(request.getParameter("password")));
////            agentyabes.setAgent_pin(SHA256Enc.encryptProc(request.getParameter("agent_pin")));
//
//            String status = dp.updateAgent(agentyabes);
//            if (status.equalsIgnoreCase("Sukses mengupdate Agent")) {
//                HttpSession session = request.getSession(true);
//                String username = session.getAttribute("username").toString();
//                String activitas = "update agent " + request.getParameter("agentId");
//                dp.userYabes(username, activitas);
//                RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_agent.jsp");
//                PrintWriter out = response.getWriter();
//                out.println("<div class=\"alert alert-success status-custom\">\n"
//                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
//                        + "     <i class=\"icon fa fa-check\"></i>" + status + "\n"
//                        + "</div>");
//                rd.include(request, response);
//            } else if (status.equalsIgnoreCase("Maaf agent id sudah ada")) {
//                RequestDispatcher rd = getServletContext().getRequestDispatcher("/update_agent.jsp");
//                PrintWriter out = response.getWriter();
//                out.println("<div class=\"alert alert-danger status-custom\">\n"
//                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
//                        + "     <i class=\"icon fa fa-warning\"></i>" + status + "\n"
//                        + "</div>");
//                rd.include(request, response);
//            } else {
//                RequestDispatcher rd = getServletContext().getRequestDispatcher("/update_agent.jsp");
//                PrintWriter out = response.getWriter();
//                out.println("<div class=\"alert alert-danger status-custom\">\n"
//                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
//                        + "     <i class=\"icon fa fa-warning\"></i>" + status + "\n"
//                        + "</div>");
//                rd.include(request, response);
//            }
//
//        }
    }
}
