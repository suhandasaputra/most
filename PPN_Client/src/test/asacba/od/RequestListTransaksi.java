/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.asacba.od;

import test.asacba.oc.*;
import com.ppnlib.function.JsonProcess;
import com.ppnlib.parameter.RuleNameParameter;
import java.util.HashMap;
import test.asacba.function.KirimMessageFromOD;

/**
 *
 * @author herrysuganda
 */
public class RequestListTransaksi {

    public static void main(String[] args) throws Exception {

        HashMap req = new HashMap();
        req.put(RuleNameParameter.PROC_CODE, "201");
        req.put(RuleNameParameter.DISP_USER, "iqbal");
        req.put(RuleNameParameter.DISP_PASS, "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92");
        String reqMsg = JsonProcess.generateJson(req);

        HashMap resp = KirimMessageFromOD.kirimMessage(JsonProcess.decodeJson(reqMsg));
    }
}
