/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.asacba.om;

import com.ppnlib.function.JsonProcess;
import com.ppnlib.parameter.RuleNameParameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import test.asacba.function.KirimMessageFromOM;

/**
 *
 * @author herrysuganda
 */
public class RequestNoResiJson {

    public static void main(String[] args) throws Exception {

        HashMap detail1 = new HashMap();
        detail1.put(RuleNameParameter.PROD_NAME, "pisang goreng");
        detail1.put(RuleNameParameter.TOTAL, "4");
        detail1.put(RuleNameParameter.SUM_AMOUNT, "10.000");
        HashMap detail2 = new HashMap();
        detail2.put(RuleNameParameter.PROD_NAME, "sop buntut");
        detail2.put(RuleNameParameter.TOTAL, "6");
        detail2.put(RuleNameParameter.SUM_AMOUNT, "300.000");
        HashMap detail3 = new HashMap();
        detail3.put(RuleNameParameter.PROD_NAME, "nasi");
        detail3.put(RuleNameParameter.TOTAL, "8");
        detail3.put(RuleNameParameter.SUM_AMOUNT, "40.000");

        List listDetail = new ArrayList();
        listDetail.add(detail1);
        listDetail.add(detail2);
        listDetail.add(detail3);

        HashMap req = new HashMap();
        req.put(RuleNameParameter.PROC_CODE, "001");
        req.put(RuleNameParameter.SERIAL_KEY, "7c4a8d09ca3762af61e59520943dc26494f8941b");
        req.put(RuleNameParameter.TRX_DATE, "20180111151320");
        req.put(RuleNameParameter.POS_CODE, "12980");
        req.put(RuleNameParameter.INSTITUTE_ID, "0001");
        req.put(RuleNameParameter.POS_ID, "301");
        req.put(RuleNameParameter.AMOUNT, "10000");
        req.put(RuleNameParameter.PPN, "1000");
        req.put(RuleNameParameter.RRN, "123456789");
        req.put(RuleNameParameter.NO_RESI, "000130112980173070023");

        req.put(RuleNameParameter.DETAIL, listDetail);
        String reqMsg = JsonProcess.generateJson(req);
//        System.out.println(reqMsg);
        HashMap resp = KirimMessageFromOM.kirimMessage(JsonProcess.decodeJson(reqMsg));
//        System.out.println(JsonProcess.generateJson(resp));
//        String decData = TripleDesEnc.decProcess("inikeyencryptmerchant001", req.get(RuleNameParameter.DATA).toString()); 
//        System.out.println("dec response : " + decData);
    }
}
