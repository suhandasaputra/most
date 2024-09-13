/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.asacba.om;

import com.ppnlib.function.JsonProcess;
import com.ppnlib.function.XMLFunction;
import com.ppnlib.parameter.RuleNameParameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import test.asacba.function.KirimMessageFromOM;

/**
 *
 * @author herrysuganda
 */
public class RequestNoResiXML {

    public static void main(String[] args) throws Exception {

        HashMap detail1 = new HashMap();
        detail1.put(RuleNameParameter.PROD_NAME, "Ayam dada");
        detail1.put(RuleNameParameter.TOTAL, "4");
        detail1.put(RuleNameParameter.SUM_AMOUNT, "150.000");
        HashMap detail2 = new HashMap();
        detail2.put(RuleNameParameter.PROD_NAME, "Es Teh Manis");
        detail2.put(RuleNameParameter.TOTAL, "6");
        detail2.put(RuleNameParameter.SUM_AMOUNT, "30.000");
        HashMap detail3 = new HashMap();
        detail3.put(RuleNameParameter.PROD_NAME, "nasi");
        detail3.put(RuleNameParameter.TOTAL, "8");
        detail3.put(RuleNameParameter.SUM_AMOUNT, "20.000");

        List listDetail = new ArrayList();
        listDetail.add(detail1);
        listDetail.add(detail2);
        listDetail.add(detail3);

        HashMap req = new HashMap();
        req.put(RuleNameParameter.TRX_DATE, "2017-11-02 13:44:38");
        req.put(RuleNameParameter.POS_CODE, "12980");
        req.put(RuleNameParameter.INSTITUTE_ID, "0501");
        req.put(RuleNameParameter.POS_ID, "327");
        req.put(RuleNameParameter.AMOUNT, "200000");
        req.put(RuleNameParameter.PPN, "20000");
        req.put(RuleNameParameter.RRN, "192828171");
        req.put(RuleNameParameter.DETAIL, listDetail);
        
        String reqMsg = XMLFunction.convertHashmapToXML(req, "data");
        System.out.println(reqMsg);
        System.out.println("====================================");
        HashMap test = XMLFunction.convertXMLtoHashmap(reqMsg);
//        System.out.println(XMLFunction.convertXMLtoHashmap(reqMsg));
        System.out.println("====================================");
        System.out.println(JsonProcess.generateJson(test));
//        HashMap resp = KirimMessageFromOM.kirimMessageXML(XMLFunction.convertXMLtoHashmap(reqMsg));
//        System.out.println(JsonProcess.generateJson(resp));
//        String decData = TripleDesEnc.decProcess("inikeyencryptmerchant001", req.get(RuleNameParameter.DATA).toString()); 
//        System.out.println("dec response : " + decData);
    }
}
