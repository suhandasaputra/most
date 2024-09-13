/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.asacba.function;

import com.ppnlib.function.JsonProcess;
import com.ppnlib.function.SendHttpProcess;
import com.ppnlib.function.XMLFunction;
import java.util.HashMap;

/**
 *
 * @author herrysuganda
 */
public class KirimMessageFromOD {

    public static HashMap kirimMessage(HashMap req) {

        String reqMsg = JsonProcess.generateJson(req);
        System.out.println("request =" + reqMsg);
//        String respMsg = new SendHttpProcess().sendHttpRequest("http://localhost:8080/PPN/OnlineDispenda", reqMsg);
        String respMsg = new SendHttpProcess().sendHttpRequest("http://localhost:9090/PPN/OnlineDispenda", reqMsg);
        System.out.println("response =" + respMsg);
        HashMap resp = JsonProcess.decodeJson(respMsg);

        return resp;
    }
    public static HashMap kirimMessageXML(HashMap req) {

        String reqMsg = XMLFunction.convertHashmapToXML(req,"data");
        System.out.println("request =" + reqMsg);
        String respMsg = new SendHttpProcess().sendHttpRequest("http://localhost:8080/PPN/OnlineMerchantXML", reqMsg);
//        String respMsg = new SendHttpProcess().sendHttpRequest("http://app.q-payment.com:8080/PPN/OnlineMerchantXML", reqMsg);
        System.out.println("response =" + respMsg);
        HashMap resp = XMLFunction.convertXMLtoHashmap(respMsg);

        return resp;
    }
}
