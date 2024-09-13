/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ppn.process;

import com.ppnlib.database.DatabaseProcess;
import com.ppnlib.entity.Tempmsg;
import com.ppnlib.function.FileFolderFunction;
import com.ppnlib.function.HeaderReport;
import com.ppnlib.function.StringFunction;
import com.ppnlib.singleton.DatasourceEntry;
import com.ppnlib.parameter.RuleNameParameter;
import com.ppnlib.parameter.StaticParameter;
//import com.ppnlib.singleton.SwingSingleton;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author herrysuganda
 */
public class AllTransactionReportPerBank {

    private static Logger log = Logger.getLogger(AllTransactionReportPerBank.class);

    public void generateReport(GregorianCalendar startDate, GregorianCalendar endDate, String bankCodeFrom) {
        log.info("Generate All Transaction Report Per NOPD ..." + " (" + bankCodeFrom + ")");
        System.out.println("5. Generate All Transaction Report Per NOPD ..." + " (" + bankCodeFrom + ")");
        DatabaseProcess dp = new DatabaseProcess();
        FileWriter fw = null;
        BufferedWriter bw = null;
        String fileName = null;
        File file = null;
        List listTrx = null;
        Tempmsg tempmsg = null;
        long amountTotalTrx = 0;
        long amountTotalVoid = 0;

        try {
            String bankFolder = StringFunction.getDateYYYYMMDD(startDate);
            FileFolderFunction.checkFolder(bankFolder);
            fileName = RuleNameParameter.generate_file + bankFolder + "/" + bankCodeFrom + StringFunction.getDateYYYYMMDD(startDate)
                    + RuleNameParameter.ext_csv;
            file = new File(fileName);
            file.createNewFile();

            fw = new FileWriter(file.getAbsoluteFile());
            bw = new BufferedWriter(fw);

            listTrx = dp.getAllTransaction(startDate, endDate, bankCodeFrom);
            bw.write("Daily Report All Trancation Per NOPD (" + bankCodeFrom + ")");
            bw.write(RuleNameParameter.newline_code);
            bw.write(RuleNameParameter.newline_code);
            bw.write(RuleNameParameter.header_createdatetime + StringFunction.getCurrentDateYYYYMMDDHHMMSS2());
            bw.write(RuleNameParameter.newline_code);
//            bw.write(RuleNameParameter.header_transactiondate + hmData.get(RuleNameParameter.startDate) + " - "
//                    + hmData.get(RuleNameParameter.endDate));
            bw.write(RuleNameParameter.header_transactiondate + StringFunction.getDateYYYY_MM_DD(startDate) + " - "
                    + StringFunction.getDateYYYY_MM_DD(endDate));
            bw.write(RuleNameParameter.newline_code);
            bw.write(RuleNameParameter.header_totalrecord + listTrx.size());
            bw.write(RuleNameParameter.newline_code);
            bw.write(RuleNameParameter.newline_code);
//            bw.write(HeaderReport.getLineHeaderDetailPerBank());
            bw.write(RuleNameParameter.newline_code);
            
            
            
            //need banget
//            bw.write(StaticParameter.comma + HeaderReport.getHeaderBprd());
            bw.write(RuleNameParameter.newline_code);
//            bw.write(HeaderReport.getLineHeaderDetailPerBank());
            bw.write(RuleNameParameter.newline_code);
//            System.out.println(listTrx.size());
            for (int i = 0; i < listTrx.size(); i++) {
                tempmsg = (Tempmsg) listTrx.get(i);

                bw.write(StaticParameter.comma + StringFunction.pad(String.valueOf(i + 1), StaticParameter.length_no, " ", "Left") + StaticParameter.comma
                        + StringFunction.pad(tempmsg.getId(), StaticParameter.length_id, " ", "Left") + StaticParameter.comma
                        + StringFunction.pad(tempmsg.getTanggal_trx(), StaticParameter.length_tanggal_trx, " ", "Left") + StaticParameter.comma
                        + StringFunction.pad(tempmsg.getNo_struk(), StaticParameter.length_no_struk, " ", "Left") + StaticParameter.comma
                        + StringFunction.pad("\"" + tempmsg.getDescription().replace("\"", "\"\"") + "\"", StaticParameter.length_description, " ", "Left") + StaticParameter.comma
                        + StringFunction.pad(tempmsg.getAmount_trx().toString(), StaticParameter.length_amount_trx, " ", "Left") + StaticParameter.comma
                        + StringFunction.pad(tempmsg.getNopd(), StaticParameter.length_nopd, " ", "Left") + StaticParameter.comma
                        + StringFunction.pad(tempmsg.getSource(), StaticParameter.length_source, " ", "Left"));

                bw.write(RuleNameParameter.newline_code);
                amountTotalTrx += Double.parseDouble(tempmsg.getAmount_trx());
            }
//            bw.write(HeaderReport.getLineHeaderDetailPerBank());
            bw.write(RuleNameParameter.newline_code);
            bw.write(StaticParameter.comma + StaticParameter.comma + StaticParameter.comma + StaticParameter.comma + StaticParameter.comma + StaticParameter.comma + "Total Amount :"
                    + StringFunction.pad(String.valueOf(amountTotalTrx), StaticParameter.length_amount, " ", "Right"));

            bw.write(RuleNameParameter.newline_code);
            bw.write(RuleNameParameter.newline_code);
            bw.write(RuleNameParameter.newline_code);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            listTrx = dp.getAllVoid(startDate, endDate, bankCodeFrom);
            bw.write("Daily Report All Void Per NOPD (" + bankCodeFrom + ")");
            bw.write(RuleNameParameter.newline_code);
            bw.write(RuleNameParameter.newline_code);
            bw.write(RuleNameParameter.header_createdatetime + StringFunction.getCurrentDateYYYYMMDDHHMMSS2());
            bw.write(RuleNameParameter.newline_code);
//            bw.write(RuleNameParameter.header_transactiondate + hmData.get(RuleNameParameter.startDate) + " - "
//                    + hmData.get(RuleNameParameter.endDate));
            bw.write(RuleNameParameter.header_transactiondate + StringFunction.getDateYYYY_MM_DD(startDate) + " - "
                    + StringFunction.getDateYYYY_MM_DD(endDate));
            bw.write(RuleNameParameter.newline_code);
            bw.write(RuleNameParameter.header_totalrecord + listTrx.size());
            bw.write(RuleNameParameter.newline_code);
            bw.write(RuleNameParameter.newline_code);
//            bw.write(HeaderReport.getLineHeaderDetailPerBank());
            bw.write(RuleNameParameter.newline_code);
            //need banget
//            bw.write(StaticParameter.comma + HeaderReport.getHeaderBprd());
            bw.write(RuleNameParameter.newline_code);
//            bw.write(HeaderReport.getLineHeaderDetailPerBank());
            bw.write(RuleNameParameter.newline_code);
//            System.out.println(listTrx.size());
            for (int i = 0; i < listTrx.size(); i++) {
                tempmsg = (Tempmsg) listTrx.get(i);

                bw.write(StaticParameter.comma + StringFunction.pad(String.valueOf(i + 1), StaticParameter.length_no, " ", "Left") + StaticParameter.comma
                        + StringFunction.pad(tempmsg.getId(), StaticParameter.length_id, " ", "Left") + StaticParameter.comma
                        + StringFunction.pad(tempmsg.getTanggal_trx(), StaticParameter.length_tanggal_trx, " ", "Left") + StaticParameter.comma
                        + StringFunction.pad(tempmsg.getNo_struk(), StaticParameter.length_no_struk, " ", "Left") + StaticParameter.comma
                        + StringFunction.pad("\"" + tempmsg.getDescription().replace("\"", "\"\"") + "\"", StaticParameter.length_description, " ", "Left") + StaticParameter.comma
                        + StringFunction.pad(tempmsg.getAmount_trx().toString(), StaticParameter.length_amount_trx, " ", "Left") + StaticParameter.comma
                        + StringFunction.pad(tempmsg.getNopd(), StaticParameter.length_nopd, " ", "Left") + StaticParameter.comma
                        + StringFunction.pad(tempmsg.getSource(), StaticParameter.length_source, " ", "Left"));

                bw.write(RuleNameParameter.newline_code);
                amountTotalVoid += Double.parseDouble(tempmsg.getAmount_trx());
            }
//            bw.write(HeaderReport.getLineHeaderDetailPerBank());
            bw.write(RuleNameParameter.newline_code);
            bw.write(StaticParameter.comma + StaticParameter.comma + StaticParameter.comma + StaticParameter.comma + StaticParameter.comma + StaticParameter.comma + "Total Amount :"
                    + StringFunction.pad(String.valueOf(amountTotalVoid), StaticParameter.length_amount, " ", "Right"));

            bw.close();
            log.info("Generate All Transaction Report Per NOPD Done" + " (" + bankCodeFrom + ")");
            System.out.println("Generate All Transaction Report Per NOPD Done" + " (" + bankCodeFrom + ")");
        } catch (IOException ex) {
            log.error(ex.getMessage());
            log.info("Generate All Transaction Report Per NOPD Failed" + " (" + bankCodeFrom + ")");
            System.out.println("Generate All Transaction Report Per NOPD Failed" + " (" + bankCodeFrom + ")");
        } finally {
            try {
                dp = null;
                fileName = null;
                file = null;
                listTrx = null;
                tempmsg = null;
                bw.close();
                fw = null;
                bw = null;
            } catch (IOException ex) {
                ex.getMessage();
            }
        }

    }
}
