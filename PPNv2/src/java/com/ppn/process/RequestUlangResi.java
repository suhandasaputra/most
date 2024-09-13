/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ppn.process;

import com.ppnlib.database.DatabaseProcess;
import com.ppnlib.function.CheckSumFunction;
import com.ppnlib.singleton.ResponseCodeSingleton;
import com.ppnlib.parameter.RuleNameParameter;
import java.util.HashMap;

/**
 *
 * @author herrysuganda
 */
public class RequestUlangResi {

    public HashMap process(HashMap inputMap) {
        DatabaseProcess dp = new DatabaseProcess();
        String responseCode = "0000";
        String paycode = dp.getPayCode(inputMap);
        
        if (!"".equals(paycode)) {
            inputMap.put(RuleNameParameter.NO_RESI, paycode);
            inputMap.put(RuleNameParameter.CHECK_SUM, CheckSumFunction.getCheckSum(responseCode) + CheckSumFunction.getCheckSum(inputMap.get(RuleNameParameter.NO_RESI).toString()));
        } else {
            responseCode = "1005";
            inputMap.put(RuleNameParameter.CHECK_SUM, CheckSumFunction.getCheckSum(responseCode));
        }
        inputMap.put(RuleNameParameter.RESP_CODE, responseCode);
        inputMap.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get(responseCode).toString());

        inputMap.remove(RuleNameParameter.MERC_CODE);
        inputMap.remove(RuleNameParameter.MERC_PASS);
        inputMap.remove(RuleNameParameter.AMOUNT);

        dp = null;
        return inputMap;
    }
}
