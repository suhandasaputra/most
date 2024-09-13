/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ppn.scheduler;

import com.ppnlib.database.DatabaseProcess;
import java.util.GregorianCalendar;
import org.apache.log4j.Logger;

/**
 *
 * @author herrysuganda
 */
public class CleningDataScheduler extends Thread {

    private static Logger log = Logger.getLogger(CleningDataScheduler.class);

    @Override
    public void run() {
        DatabaseProcess dp = new DatabaseProcess();
//        System.out.println("GetInstant : " + InitParameterSingleton.getInstance().isStatusThread());
//        while (InitParameterSingleton.getInstance().isStatusThread()) {
        try {
            boolean x = true;
            while (x) {
                GregorianCalendar cal = new GregorianCalendar();
            if (cal.get(GregorianCalendar.HOUR_OF_DAY) == 0) {
                System.out.println("cleaning data to backup running !");
                log.info("cleaning data to backup running ! " );
                dp.cleanData();
            }
                cal = null;
//                Thread.sleep(dp.getIntervalCheckPaycode());
                Thread.sleep(1000*60*30);
//                                Thread.sleep(1000*60);


            }
        } catch (InterruptedException ex) {
//                ex.printStackTrace();
            log.error(ex.getMessage());
        }
//        }
    }
}
