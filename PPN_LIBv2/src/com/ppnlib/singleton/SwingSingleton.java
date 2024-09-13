///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.ppnlib.singleton;
//
//import javax.sql.DataSource;
//import javax.swing.JTextArea;
//
///**
// *
// * @author herry.suganda
// */
//public class SwingSingleton {
//
//    private static SwingSingleton log = null;
//    private static JTextArea logarea = null;
//
//    /**
//     * @return the logarea
//     */
//    public static JTextArea getLogarea() {
//        return logarea;
//    }
//
//    /**
//     * @param aLogarea the logarea to set
//     */
//    public static void setLogarea(JTextArea aLogarea) {
//        logarea = aLogarea;
//    }
//    
//    public SwingSingleton() {
//        
//    }
//
//    public static SwingSingleton getInstance() {
//        if (log == null) {
//            log = new SwingSingleton();
//        }
//
//        return log;
//    }
//    
//    public static void setLogReporting(String txt){
//        logarea.setText(logarea.getText() + txt + "\r\n");
//    }
//    
//    public static void resetLogReporting(){
//        logarea.setText("");
//    }
//
//
//}
