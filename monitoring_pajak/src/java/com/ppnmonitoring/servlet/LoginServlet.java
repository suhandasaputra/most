package com.ppnmonitoring.servlet;

import com.ppn.function.SendHttpProcessQuantum;
import com.ppn.parameter.checkParameter;
import com.ppnlib.function.JsonProcess;
import com.ppnlib.function.SHA256Enc;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author syukur
 */
public class LoginServlet extends HttpServlet {

//    private final MessageProcess mp = new MessageProcessImpl();
    public LoginServlet() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap req = new HashMap();
        req.put(checkParameter.DISP_USER, request.getParameter("DISP_USER"));
        req.put(checkParameter.DISP_PASS, SHA256Enc.encryptProc(request.getParameter("DISP_PASS")));
        req.put(checkParameter.PROC_CODE, "101");
        System.out.println(" REQ : " + req.toString());

        String reqMsg = JsonProcess.generateJson(req);
//        String respMsg = new SendHttpProcessQuantum().sendHttpRequest("http://103.31.235.170/PPNv2/OnlineDispenda", reqMsg);
        String respMsg = new SendHttpProcessQuantum().sendHttpRequest("http://localhost:8080/PPNv2/OnlineDispenda", reqMsg);
        HashMap resultresponse = JsonProcess.decodeJson(respMsg);

        HashMap DATA = new HashMap();
        DATA = (HashMap) resultresponse.get("DATA");
//        System.out.println("dddenak :"+DATA);
        HttpSession session = request.getSession(true);
        session.setAttribute("username", request.getParameter("DISP_USER"));
//        session.setAttribute("NO_RESI", resultresponse.get("NO_RESI"));
//        session.setAttribute("TRX_DATE", DATA.get("TRX_DATE"));
//        session.setAttribute("AMOUNT", DATA.get("AMOUNT"));
//        session.setAttribute("PPN", DATA.get("PPN"));
//        session.setAttribute("DETAIL", DATA.get("DETAIL"));
//        session.setAttribute("INSTITUTE_ID", DATA.get("INSTITUTE_ID"));
//        session.setAttribute("POS_ID", DATA.get("POS_ID"));
//        session.setAttribute("POS_CODE", DATA.get("POS_CODE"));
        try {
            if (resultresponse.get("RESP_CODE").equals("0000")) {
                
                response.sendRedirect("WelcomePage.jsp");
            } else {
                response.sendRedirect("index.jsp");
            }
        } catch (Exception e) {
            response.sendRedirect("index.jsp");
        }
    }
}
