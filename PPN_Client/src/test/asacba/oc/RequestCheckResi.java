/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.asacba.oc;

import com.ppnlib.function.JsonProcess;
import com.ppnlib.parameter.RuleNameParameter;
import java.util.HashMap;
import test.asacba.function.KirimMessageFromOC;

/**
 *
 * @author herrysuganda
 */
public class RequestCheckResi {

    public static void main(String[] args) throws Exception {

        HashMap req = new HashMap();
        req.put(RuleNameParameter.NO_RESI, "050132712980173120003");
        req.put(RuleNameParameter.DISP_USER, "iqbal");
        req.put(RuleNameParameter.DISP_PASS, "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92");
        String reqMsg = JsonProcess.generateJson(req);

        HashMap resp = KirimMessageFromOC.kirimMessage(JsonProcess.decodeJson(reqMsg));
    }
}
