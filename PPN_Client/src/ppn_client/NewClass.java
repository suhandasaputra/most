/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ppn_client;

import com.ppnlib.function.JsonProcess;

/**
 *
 * @author matajari
 */
public class NewClass {

    public static void main(String[] args) {
        String a = "{\"RESP_DESC\":\"Successful\",\"DISP_PASS\":\"8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92\",\"POS_ID\":\"001\",\"RESP_CODE\":\"0000\",\"DISP_USER\":\"iqbal\",\"DETAIL\":\"[{\\\"TOTAL\\\":\\\"1\\\",\\\"PROD_NAME\\\":\\\"GETUK\\\",\\\"SUM_AMOUNT\\\":\\\"5000\\\"},{\\\"TOTAL\\\":\\\"1\\\",\\\"PROD_NAME\\\":\\\"KUE CUCUR\\\",\\\"SUM_AMOUNT\\\":\\\"5000\\\"}]\",\"INSTITUTE_ID\":\"0002\",\"PAYMENT_METHODE\":\"0\",\"SPLIT_BILL\":\"0\",\"PPN\":\"1000\",\"CARD_NUMBER\":\"0\",\"NOPD\":null,\"NO_RESI\":\"000200119141091137149\",\"TRX_DATE\":\"20190902091137\",\"AMOUNT\":10000,\"INSTITUTE_NAME\":\"sugistore\",\"SERVICE_TAX\":\"0\",\"SOURCE\":null,\"POS_CODE\":\"19141\"}";
        System.out.println("a : " + JsonProcess.decodeJson(a));
    }
}
