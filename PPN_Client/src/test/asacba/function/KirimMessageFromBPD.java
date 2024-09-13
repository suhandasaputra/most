/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.asacba.function;

import com.ppnlib.function.JsonProcess;
import com.ppnlib.function.SendHttpProcess;
import java.util.HashMap;

/**
 *
 * @author herrysuganda
 */
public class KirimMessageFromBPD {
    public static HashMap kirimMessage (HashMap req){
        
        String reqMsg = JsonProcess.generateJson(req);
        System.out.println("request =" + reqMsg);
        String respMsg = new SendHttpProcess().sendHttpRequest("http://localhost:8080/ASACBA/PaymentBank", reqMsg);

        System.out.println("response ="+respMsg);
        HashMap resp = JsonProcess.decodeJson(respMsg);
        
        return resp;
    }
}
