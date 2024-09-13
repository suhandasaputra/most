/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ppn.process;

import com.ppnlib.database.DatabaseProcess;
import com.ppnlib.function.TransactionFunction;
import com.ppnlib.singleton.ResponseCodeSingleton;
import com.ppnlib.parameter.RuleNameParameter;
import java.util.HashMap;
import org.json.simple.parser.ParseException;

/**
 *
 * @author adi
 */
public class RequestResi {

    public HashMap process(HashMap hmData) throws ParseException, java.text.ParseException {
        DatabaseProcess dp = new DatabaseProcess();
        TransactionFunction tf = new TransactionFunction();
        String unqueId = hmData.get(RuleNameParameter.INSTITUTE_ID).toString()
                + hmData.get(RuleNameParameter.POS_ID).toString()
                + hmData.get(RuleNameParameter.POS_CODE).toString();
        hmData.put(RuleNameParameter.UNIQ_CODE, unqueId);
        //nanti perlu unique id + rrn checker sebelum  request resi
        String responseCode = "0000";
        String noResi = hmData.get(RuleNameParameter.NO_RESI).toString();
       
        if (dp.saveNewPaycode(hmData)) {
            hmData.put(RuleNameParameter.NO_RESI, noResi);
        } else {
            responseCode = "0001";
        }
        hmData.put(RuleNameParameter.RESP_CODE, responseCode);
        hmData.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get(responseCode).toString());

        hmData.remove(RuleNameParameter.DETAIL);
        hmData.remove(RuleNameParameter.MERC_CODE);
        hmData.remove(RuleNameParameter.MERC_PASS);
        hmData.remove(RuleNameParameter.AMOUNT);

        tf = null;
        dp = null;
        return hmData;
    }
}
