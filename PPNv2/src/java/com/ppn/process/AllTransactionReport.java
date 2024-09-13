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
import com.ppnlib.parameter.RuleNameParameter;
import com.ppnlib.parameter.StaticParameter;
//import com.ppnlib.singleton.SwingSingleton;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author herrysuganda
 */
public class AllTransactionReport {

    private static Logger log = Logger.getLogger(AllTransactionReport.class);

    public void generateReport(HashMap hmData) {
        log.info("Generate All Transaction Report ...");
        System.out.println("Generate All Transaction Report ...");
        DatabaseProcess dp = new DatabaseProcess();
        FileWriter fw = null;
        BufferedWriter bw = null;
        String fileName = null;
        File file = null;
        List listTrx = null;
        Tempmsg tempmsg = null;
        long amountTotal = 0;

        try {
            System.out.println("Folder checker ...");
            FileFolderFunction.checkFolder(RuleNameParameter.folder_all);
            FileFolderFunction.checkFolder(RuleNameParameter.folder_all + "/" + StringFunction.getCurrentDateYYYYMMDD()+ "/" + StringFunction.getCurrentDateYYYYMMDD());
            fileName = "c:\\generatefile\\" + RuleNameParameter.folder_all + "/" + StringFunction.getCurrentDateYYYYMMDD() + "/" + StringFunction.getCurrentDateYYYYMMDDHHMMSS() + RuleNameParameter.filename_delimiter
                    + "000" + RuleNameParameter.filename_delimiter + RuleNameParameter.typefile_all + RuleNameParameter.ext_csv;
            file = new File(fileName);
            file.createNewFile();

            fw = new FileWriter(file.getAbsoluteFile());
            bw = new BufferedWriter(fw);
//            HashMap hmData = new HashMap();
            listTrx = (List) dp.getAllTransaction(hmData);
            System.out.println("list nya :"+listTrx);
            bw.write("Daily Report All Trancation All Bank");
            bw.write(RuleNameParameter.newline_code);
            bw.write(RuleNameParameter.newline_code);
            bw.write(RuleNameParameter.header_createdatetime + StringFunction.getCurrentDateDDMMYYYYHHMMSS2());
            bw.write(RuleNameParameter.newline_code);
            bw.write(RuleNameParameter.header_transactiondate + hmData.get(RuleNameParameter.startDate).toString() + " - "
                    + hmData.get(RuleNameParameter.endDate).toString());
            bw.write(RuleNameParameter.newline_code);
            bw.write(RuleNameParameter.header_totalrecord + listTrx.size());
            bw.write(RuleNameParameter.newline_code);
            bw.write(RuleNameParameter.newline_code);
//            bw.write(HeaderReport.getLineHeaderDetail());
            bw.write(RuleNameParameter.newline_code);
            
            // need banget
//            bw.write(StaticParameter.comma + HeaderReport.getHeaderBprd());
            bw.write(RuleNameParameter.newline_code);
//            bw.write(HeaderReport.getLineHeaderDetail());
            bw.write(RuleNameParameter.newline_code);
//            System.out.println(listTrx.size());
            for (int i = 0; i < listTrx.size(); i++) {
                tempmsg = (Tempmsg) listTrx.get(i);
//                switch (tempmsg.getRcInternal()) {
//                    case "00":
//                        tempmsg.setRcdesc("Successfull");
//                        break;
//                    case "68":
//                        tempmsg.setRcdesc("Suspect");
//                        break;
//                    default:
//                        tempmsg.setRcdesc("Failed");
//                        break;
//                }

                bw.write(StaticParameter.comma + StringFunction.pad(String.valueOf(i + 1), StaticParameter.length_no, " ", "Left") + StaticParameter.comma
                        + StringFunction.pad(tempmsg.getId(), StaticParameter.length_id, " ", "Left") + StaticParameter.comma
                        + StringFunction.pad(tempmsg.getTanggal_trx(), StaticParameter.length_tanggal_trx, " ", "Left") + StaticParameter.comma
                        + StringFunction.pad(tempmsg.getNo_struk(), StaticParameter.length_no_struk, " ", "Left") + StaticParameter.comma
                        + StringFunction.pad("\""+tempmsg.getDescription().replace("\"", "\"\"")+"\"", StaticParameter.length_description, " ", "Left") + StaticParameter.comma
                        + StringFunction.pad(tempmsg.getAmount_trx(), StaticParameter.length_amount_trx, " ", "Left") + StaticParameter.comma
                        + StringFunction.pad(tempmsg.getNopd(), StaticParameter.length_nopd, " ", "Left") + StaticParameter.comma
                        + StringFunction.pad(tempmsg.getSource(), StaticParameter.length_source, " ", "Left"));
                bw.write(RuleNameParameter.newline_code);
                tempmsg = null;
            }
//            bw.write(HeaderReport.getLineHeaderDetail());
            bw.write(RuleNameParameter.newline_code);
            bw.write(StaticParameter.comma + StaticParameter.comma + StaticParameter.comma + StaticParameter.comma + StaticParameter.comma + StaticParameter.comma + StaticParameter.comma + StaticParameter.comma + StaticParameter.comma + StaticParameter.comma + StaticParameter.comma + StaticParameter.comma + StaticParameter.comma + StaticParameter.comma + StaticParameter.comma + StaticParameter.comma + HeaderReport.getAmountLeftSpace2()
                    + StringFunction.pad(String.valueOf(amountTotal), StaticParameter.length_amount, " ", "Right"));
            log.info("Generate All Transaction Report Done");
            System.out.println("Generate All Transaction Report Done");
        } catch (IOException ex) {
            log.error(ex.getMessage());
            log.info("Generate All Transaction Report Failed");
            System.out.println("Generate All Transaction Report Failed");
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
