/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ppn.process;

import com.ppnlib.database.DatabaseProcess;
import com.ppnlib.function.CheckSumFunction;
import com.ppnlib.parameter.RuleNameParameter;
import com.ppnlib.singleton.ResponseCodeSingleton;
import java.util.HashMap;

/**
 *
 * @author herrysuganda
 */
public class Login_merchant {

    DatabaseProcess dp = new DatabaseProcess();

    public HashMap process(HashMap inputMap) {
         try {
            if (inputMap.get(RuleNameParameter.DISP_USER) == null
                    || dp.userMerchantCheck(inputMap)) {
                inputMap.put(RuleNameParameter.RESP_CODE, "0000");
                inputMap.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get("0000").toString());
            }
            dp = null;
        } catch (Exception e) {
            inputMap.put(RuleNameParameter.RESP_CODE, "1007");
            inputMap.put(RuleNameParameter.RESP_DESC, "Message request tidak sesuai");
        }
        return inputMap;
    }
}
