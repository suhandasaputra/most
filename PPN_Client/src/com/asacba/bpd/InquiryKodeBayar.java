/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asacba.bpd;

import com.ppnlib.function.CheckSumFunction;
import com.ppnlib.parameter.RuleNameParameter;
import com.ppnlib.parameter.StaticParameter;
import java.util.HashMap;
import test.asacba.function.KirimMessageFromBPD;

/**
 *
 * @author herrysuganda
 */
public class InquiryKodeBayar {

    public static void main(String[] args) throws Exception {


        HashMap req = new HashMap();

        req.put(RuleNameParameter.NO_RESI, "001142000005");//1413
        req.put(RuleNameParameter.REF_NO, "664736882766");
        req.put(RuleNameParameter.BANK_CODE, "147");
        req.put(RuleNameParameter.PROC_CODE, StaticParameter.PROCCODE_INQUIRY_KODE_BAYAR);
        int checksum = CheckSumFunction.getCheckSum(req.get(RuleNameParameter.NO_RESI).toString());
        checksum += CheckSumFunction.getCheckSum(req.get(RuleNameParameter.REF_NO).toString());
        checksum += CheckSumFunction.getCheckSum(req.get(RuleNameParameter.BANK_CODE).toString());
        req.put(RuleNameParameter.CHECK_SUM, checksum);

        req = KirimMessageFromBPD.kirimMessage(req);
        
        System.out.println("dec response : " + req);
    }
}
