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
public class Report {

    DatabaseProcess dp = new DatabaseProcess();

    public HashMap process(HashMap inputMap) {
        try {
            inputMap = dp.getAllTransaction(inputMap);
            dp = null;
        } catch (Exception e) {
            inputMap.put(RuleNameParameter.RESP_CODE, "1007");
            inputMap.put(RuleNameParameter.RESP_DESC, "Message request tidak sesuai");
        }
        return inputMap;
    }
}
