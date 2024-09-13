/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ppnbprd.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.ppn.model.Report;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.gson.JsonArray;
import com.ppn.function.SendHttpProcessQuantum;
import com.ppn.parameter.checkParameter;
import com.ppnbprd.util.DatabaseProcess;
import com.ppnlib.function.JsonProcess;
import com.ppnlib.function.SHA256Enc;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author syukur
 */
public class ReportServlet extends HttpServlet {

    DatabaseProcess dp = new DatabaseProcess();

    private static final long serialVersionUID = 1L;
//    private ReportYabes dao;
    private static String UPDATE = "update_limit.jsp";

    public ReportServlet() {
//        dao = new ReportYabes();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
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

        
    }
}




///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.ppn.servlet;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonElement;
//import com.google.gson.reflect.TypeToken;
////import com.boa.dao.ReportYabes;
//import com.ppn.model.Report;
////import com.ppn.util.DatabaseProcess;
//import java.io.IOException;
//import java.text.ParseException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import com.google.gson.JsonArray;
//import com.ppn.function.SendHttpProcessQuantum;
//import com.ppn.parameter.checkParameter;
//import com.ppnlib.database.DatabaseProcess;
//import com.ppnlib.function.JsonProcess;
//import com.ppnlib.function.SHA256Enc;
//import java.util.HashMap;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// *
// * @author syukur
// */
//public class ReportServlet extends HttpServlet {
//
//    DatabaseProcess dp = new DatabaseProcess();
//
//    private static final long serialVersionUID = 1L;
////    private ReportYabes dao;
//    private static String UPDATE = "update_limit.jsp";
//
//    public ReportServlet() {
////        dao = new ReportYabes();
//    }
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//        HashMap req = new HashMap();
//        req.put(checkParameter.DISP_USER, "iqbal");
//        req.put(checkParameter.DISP_PASS, "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92");
//        req.put(checkParameter.PROC_CODE, "900");
//        System.out.println(" REQ : " + req.toString());
//        String reqMsg = JsonProcess.generateJson(req);
//        String respMsg = new SendHttpProcessQuantum().sendHttpRequest("http://localhost:9090/PPNv2/OnlineDispenda", reqMsg);
//        HashMap resultresponse = JsonProcess.decodeJson(respMsg);
//        
//        
//        HashMap isi = new HashMap();
//        HashMap a = new HashMap();
//        List list2 = new ArrayList();
//        System.out.println("ini resulrespon DALAM STRING json 2 :"+resultresponse);
//        System.out.println("ini resulrespon get list to string :"+resultresponse.get("list").toString());
//        
//        String[] slist = resultresponse.get("list").toString().split(";");
//        for (int i = 0; i < slist.length; i++) {
//            String[] isilist = slist[i].split("\\|");
//            for (int j = 0; j < isilist.length; j++) {
//                            System.out.println("ini isi istnya :"+isilist[0]);
//
//                            System.out.println("ini isi istnya tostring :"+isilist[1]);
//
//                HashMap detail = new HashMap();
//                detail.put("noresi", isilist[0]);
//                detail.put("split_bill", isilist[1]);
//                list2.add(detail);
//            }
//        }
//        isi.put("ListTRX", list2);
//        String jsonstring = (JsonProcess.generateJson(isi));
//        HashMap RespClient = JsonProcess.decodeJson(jsonstring);
//        response.getWriter().print(RespClient.get("ListTRX"));
//        
//
//    }
//}
