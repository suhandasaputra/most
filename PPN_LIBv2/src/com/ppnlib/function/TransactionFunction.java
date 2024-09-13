/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ppnlib.function;

import com.ppnlib.database.DatabaseProcess;
import java.util.GregorianCalendar;

/**
 *
 * @author adi
 */
public class TransactionFunction {

    public String generateResi(String uniqueId) {
        String noResi = null;
        GregorianCalendar cal = new GregorianCalendar();
        DatabaseProcess dp = new DatabaseProcess();
        noResi = uniqueId
                + StringFunction.pad(String.valueOf(cal.get(GregorianCalendar.YEAR)), 4, "0", "Right").substring(2)
                + StringFunction.pad(String.valueOf(cal.get(GregorianCalendar.DAY_OF_YEAR)), 3, "0", "Right") + // hari ke n ditahun ini
                StringFunction.pad(dp.getNextStan(), 4, "0", "Right");
//      StringFunction.pad("1", 4, "0", "Right");
//      dp = null;
        cal = null;
//                System.out.println(noResi);

        return noResi;
    }

//    public static void main(String[] args) {
//        
//    TransactionFunction tf = new TransactionFunction();
//    String paycode =tf.generateResi("050132712980");    //institute + terminal + pos
//        System.out.println(paycode);
//    }
}
