/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ppn.process;

import com.ppnlib.database.DatabaseProcess;
import com.ppnlib.parameter.RuleNameParameter;
import java.util.HashMap;

/**
 *
 * @author herrysuganda
 */
public class DetailResi {

    DatabaseProcess dp = new DatabaseProcess();

    public HashMap process(HashMap inputMap) {
        try {
            String noresi = inputMap.get(RuleNameParameter.NO_RESI).toString();
            if (noresi.length() == 21) {
                inputMap = dp.getDetailResi(inputMap);
            } else {
                inputMap.put(RuleNameParameter.RESP_CODE, "2013");
                inputMap.put(RuleNameParameter.RESP_DESC, "No resi salah / tidak sesuai");
            }
            dp = null;
        } catch (Exception e) {
            e.printStackTrace();
            inputMap.put(RuleNameParameter.RESP_CODE, "1007");
            inputMap.put(RuleNameParameter.RESP_DESC, "Message request tidak sesuai");
        }
        return inputMap;
    }
}
