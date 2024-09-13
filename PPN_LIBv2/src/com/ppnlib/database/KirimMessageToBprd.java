/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ppnlib.database;

import com.ppnlib.function.JsonProcess;
import com.ppnlib.function.SendHttpProcess;
import com.ppnlib.function.SendHttpProcessbprd;
import com.ppnlib.function.XMLFunction;
import java.util.HashMap;
import org.apache.log4j.Logger;

/**
 *
 * @author herrysuganda
 */
public class KirimMessageToBprd {

    private static final Logger log = Logger.getLogger(KirimMessageToBprd.class);

    public static HashMap kirimMessageToBprd(HashMap req) {

        String reqMsg = JsonProcess.generateJson(req);
        System.out.println("request to bprd = " + reqMsg);
        log.info("=======================================START REQUEST TO BPRD=======================================");
        log.info("request to bprd : " + reqMsg);
        log.info("========================================END REQUEST TO BPRD========================================");
        log.info("");

        String respMsg = new SendHttpProcessbprd().sendHttpRequestbprd("http://soadev.jakarta.go.id/posPaymentBPRD", reqMsg);
        System.out.println("response from bprd = " + respMsg);
        log.info("=======================================START RESPONSE FROM BPRD====================================");
        log.info("response from bprd : " + respMsg);
        log.info("========================================END RESPONSE FROM BPRD=====================================");
        log.info("");

        HashMap resp = JsonProcess.decodeJson(respMsg);

        return resp;
    }
//    public static HashMap kirimMessageXML(HashMap req) {
//
//        String reqMsg = XMLFunction.convertHashmapToXML(req,"data");
//        System.out.println("request =" + reqMsg);
//        String respMsg = new SendHttpProcess().sendHttpRequest("http://localhost:8080/PPN/OnlineMerchantXML", reqMsg);
////        String respMsg = new SendHttpProcess().sendHttpRequest("http://app.q-payment.com:8080/PPN/OnlineMerchantXML", reqMsg);
//        System.out.println("response =" + respMsg);
//        HashMap resp = XMLFunction.convertXMLtoHashmap(respMsg);
//
//        return resp;
//    }
}
