/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ppn.process;

import com.ppnlib.database.DatabaseProcess;
import com.ppnlib.function.CheckSumFunction;
import com.ppnlib.parameter.RuleNameParameter;
import java.util.HashMap;

/**
 *
 * @author herrysuganda
 */
public class CheckResi {

    public HashMap process(HashMap inputMap) {
        DatabaseProcess dp = new DatabaseProcess();
        inputMap = dp.getBill(inputMap);
        int checksum = CheckSumFunction.getCheckSum(inputMap.get(RuleNameParameter.NO_RESI).toString());
        checksum += CheckSumFunction.getCheckSum(inputMap.get(RuleNameParameter.REF_NO).toString());
        if ("0000".equals(inputMap.get(RuleNameParameter.RESP_CODE).toString())) {
            checksum += CheckSumFunction.getCheckSum(inputMap.get(RuleNameParameter.AMOUNT).toString());
            checksum += CheckSumFunction.getCheckSum(inputMap.get(RuleNameParameter.MERC_CODE).toString());
        }
        
        checksum += CheckSumFunction.getCheckSum(inputMap.get(RuleNameParameter.RESP_CODE).toString());
        inputMap.put(RuleNameParameter.CHECK_SUM, checksum);

//        inputMap.remove(RuleNameParameter.BANK_CODE);

        dp = null;
        return inputMap;
    }
}
