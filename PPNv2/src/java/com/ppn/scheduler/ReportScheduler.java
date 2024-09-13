/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ppn.scheduler;

import com.ppnlib.database.DatabaseProcess;
import com.ppn.process.CreateReport;
import java.util.GregorianCalendar;
import org.apache.log4j.Logger;

/**
 *
 * @author herrysuganda
 */
public class ReportScheduler extends Thread {

    private static Logger log = Logger.getLogger(ReportScheduler.class);

    @Override
    public void run() {
        DatabaseProcess dp = new DatabaseProcess();
        CreateReport cr = new CreateReport();

        try {
            boolean x = true;
            while (x) {
                GregorianCalendar cal = new GregorianCalendar();
                if (cal.get(GregorianCalendar.HOUR_OF_DAY) == 22) {
                    System.out.println("1. Report schduler running . . !");
                    log.info("Report schduler running . . !");
                    if (dp.getEODDone()) {
                        GregorianCalendar startDate = new GregorianCalendar();
                        startDate.add(GregorianCalendar.DATE, -1);
//                        GregorianCalendar endDate = new GregorianCalendar();
                        cr.createReportProcess(startDate, startDate);
                        dp.updateEODDone();
                        startDate = null;
//                        endDate = null;
                    }
                }
                cal = null;
//                Thread.sleep(1000 * 60);

                Thread.sleep(1000 * 60 * 30);
            }
        } catch (InterruptedException ex) {
//                ex.printStackTrace();
            log.error(ex.getMessage());
        }
    }
}
