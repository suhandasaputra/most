/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.asacba.om;

import com.ppnlib.function.CheckSumFunction;
import com.ppnlib.function.JsonProcess;
import com.ppnlib.function.TripleDesEnc;
import com.ppnlib.parameter.RuleNameParameter;
import com.ppnlib.parameter.StaticParameter;
import java.util.HashMap;
import test.asacba.function.KirimMessageFromOM;

/**
 *
 * @author herrysuganda
 */
public class RequestUlangKodeBayar {

    public static void main(String[] args) throws Exception {

        HashMap req = new HashMap();

        req.put(RuleNameParameter.UNIQ_CODE, "JDKF9384039488930");
        req.put(RuleNameParameter.TRX_DATE, "20160128120118");
        req.put(RuleNameParameter.MERC_CODE, "001");
        req.put(RuleNameParameter.MERC_PASS, "password");
        req.put(RuleNameParameter.AMOUNT, "6600000");
        req.put(RuleNameParameter.PROC_CODE, StaticParameter.PROCCODE_REQUEST_ULANG_KODE_BAYAR);
        int checksum = CheckSumFunction.getCheckSum(req.get(RuleNameParameter.MERC_CODE).toString());
        checksum += CheckSumFunction.getCheckSum(req.get(RuleNameParameter.AMOUNT).toString());
        req.put(RuleNameParameter.CHECK_SUM, checksum);

        String reqMsg = JsonProcess.generateJson(req);
        System.out.println(reqMsg);
        
        String encReqMsg = TripleDesEnc.encProcess("inikeyencryptmerchant001", reqMsg);
        
        HashMap reqOM = new HashMap();
        reqOM.put(RuleNameParameter.MERC_CODE, "001");
        reqOM.put(RuleNameParameter.DATA, encReqMsg);
        
        
        req = KirimMessageFromOM.kirimMessage(reqOM);
        
        String decData = TripleDesEnc.decProcess("inikeyencryptmerchant001", req.get(RuleNameParameter.DATA).toString()); 
        System.out.println("dec response : " + decData);
    }
}
