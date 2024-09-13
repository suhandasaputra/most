/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.ppn.process.CreateReport;
import com.ppnlib.function.JsonProcess;
import com.ppnlib.parameter.RuleNameParameter;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
//import test.asacba.function.KirimMessageFromOM;

/**
 *
 * @author herrysuganda
 */
public class testreport {

    public static void main(String[] args) throws Exception {
        
        GregorianCalendar calStart = new GregorianCalendar();
        calStart.set(26, 03, 2018);
        GregorianCalendar calEnd = new GregorianCalendar();
        calEnd.set(27, 03, 2018);
        System.out.println("start :");
        System.out.println("end");
//        new CreateReport().createReportProcess(calStart, calEnd);
    }
}
