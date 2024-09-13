/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ppn.process;

import com.ppnlib.database.DatabaseProcess;
//import com.ppnlib.singleton.SwingSingleton;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author herrysuganda
 */
public class CreateReport {

    private static final Logger log = Logger.getLogger(CreateReport.class);

    public boolean createReportProcess(GregorianCalendar startDate, GregorianCalendar endDate) {
//        SwingSingleton.resetLogReporting();
        log.info("create report process . . ");
        System.out.println("3. create report process . . ");

//        new AllTransactionReport().generateReport(hmData);
//        System.out.println("Create Report Done !");
//        new SuccessTransactionReport().generateReport(startDate, endDate);
//        new SuccessTransactionReportSystem().generateReport(startDate, endDate);
//        new ReconciliationReport().generateReport(startDate, endDate);
//        new SuccessTransactionReportGroupProduct().generateReport(startDate, endDate);
//        new FailedTransactionReport().generateReport(startDate, endDate);
//        new SuspectTransactionReport().generateReport(startDate, endDate);
//ini dimatiin dulu
        DatabaseProcess dp = new DatabaseProcess();
        List bankList = dp.getBankCodeFrom(startDate, endDate);
        for (int i = 0; i < bankList.size(); i++) {
            new AllTransactionReportPerBank().generateReport(startDate, endDate, bankList.get(i).toString());
        }

//        dp.updateEODDone();
//        log.info("Generate EOD Report Done !");
//        System.out.println("Generate EOD Report Done !");
        return true;
    }
}
