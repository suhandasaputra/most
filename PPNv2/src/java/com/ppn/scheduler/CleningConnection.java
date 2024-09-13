///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.ppn.scheduler;
//
//import com.ppnlib.database.DatabaseProcess;
//import com.ppnlib.singleton.InitParameterSingleton;
//import java.util.GregorianCalendar;
//import org.apache.log4j.Logger;
//
///**
// *
// * @author herrysuganda
// */
//public class CleningConnection extends Thread {
//
//    private static Logger log = Logger.getLogger(CleningConnection.class);
//
//    @Override
//    public void run() {
//        DatabaseProcess dp = new DatabaseProcess();
////        System.out.println("GetInstant : " + InitParameterSingleton.getInstance().isStatusThread());
////        while (InitParameterSingleton.getInstance().isStatusThread()) {
//        try {
//            boolean x = true;
//            while (x) {
////                GregorianCalendar cal = new GregorianCalendar();
////            if (cal.get(GregorianCalendar.HOUR_OF_DAY) == 23) {
//                System.out.println("cleaning connection running !");
//                log.info("cleaning connection running ! " );
////matiin dulu                dp.cleanConnection();
////            }
////                cal = null;
////                Thread.sleep(dp.getIntervalCheckPaycode());
//                Thread.sleep(1000*60);
//
//            }
//        } catch (InterruptedException ex) {
////                ex.printStackTrace();
//            log.error(ex.getMessage());
//        }
////        }
//    }
//}
