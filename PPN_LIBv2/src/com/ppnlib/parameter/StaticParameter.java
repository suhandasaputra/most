/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ppnlib.parameter;

/**
 *
 * @author herrysuganda
 */
public class StaticParameter {

    public static int timeout_second = 10;
    public static String PROCCODE_REQUEST_KODE_BAYAR = "100010";
    public static String PROCCODE_REQUEST_ULANG_KODE_BAYAR = "100011";
    public static String PROCCODE_CHECK_STATUS_PEMBAYARAN = "100022";
    public static String PROCCODE_KONFIRMASI_PEMBAYARAN_DARI_BPD = "200020";
    public static String PROCCODE_CHECK_KONFIRMASI_PEMBAYARAN_DARI_BPD = "200021";
    public static String PROCCODE_KONFIRMASI_PEMBAYARAN_KE_ONLINE_MERCHANT = "100023";
    public static String PROCCODE_INQUIRY_KODE_BAYAR = "200022";
    
    //////////////////////////////////////////////////////////////////////////////////////
    
    
    public static String newLine = "\r\n";
    public static String comma = ",";
    public static String delimiter = ";";
    
    public static int length_typetrx = 17;
    public static int length_id = 40;
    public static int length_tanggal_trx = 25;
    public static int length_no_struk = 35;
    public static int length_description = 1000;
    public static int length_amount_trx = 12;
    public static int length_nopd = 50;
    public static int length_source = 50;
    

    public static int length_no = 5;
    public static int length_tgl = 12;
    public static int length_jam = 10;
    public static int length_terminalid = 16;
    public static int length_noref = 42;
    public static int length_channel = 20;
    public static int length_customerno = 20;
    public static int length_bankcodefrom = 11;
    public static int length_bankcodefromname = 25;
    public static int length_productcode = 15;
    public static int length_productname = 40;
    public static int length_tcbiller = 25;
    public static int length_account = 16;
    public static int length_amount = 20;
    public static int length_hargaPokok = 20;
    public static int length_rc = 16;
    public static int length_rcdesc = 25;
    public static int length_feebpd = 18;
    public static int length_feebank = 18;
    public static int length_feetotal = 18;
    public static int length_header_tgltrx = 55;
    public static int length_all_headertitle = length_no + length_tgl + length_jam + length_noref + length_terminalid + length_channel + length_customerno + length_bankcodefrom
            + length_productcode + length_tcbiller + length_productname + length_account + length_amount + length_rc;
    public static int length_all_headertitle_perbank = length_no + length_tgl + length_jam + length_noref + length_terminalid + length_customerno
            + length_productcode + length_productname + length_account + length_amount + length_rc;
    public static int length_success_headertitle_perbank = length_hargaPokok + length_no + length_tgl + length_jam + length_noref + length_terminalid + length_customerno
            + length_productcode + length_productname + length_account + length_amount + length_feebpd + length_feebank + length_feetotal;
    public static int length_success_headertitle = length_no + length_tgl + length_jam + length_noref + length_terminalid + length_channel + length_customerno + length_bankcodefrom
            + length_productcode + length_account + length_amount + length_feebpd + length_feebank + length_feetotal + length_tcbiller + length_productname + length_hargaPokok;
    public static int length_amountspace = length_no + length_tgl + length_jam + length_noref + length_terminalid + length_channel + length_customerno + length_bankcodefrom
            + length_productcode + length_account + length_tcbiller + length_productname;
    public static int length_amountspace_perbank = length_no + length_tgl + length_jam + length_noref + length_customerno
            + length_productcode + length_productname + length_account;
    public static int length_amountspace_perproduct = length_no + length_productcode + length_productname + length_tcbiller + length_channel;
    public static int length_suspect_all = length_no + length_tgl + length_jam + length_noref + length_channel + length_customerno + length_bankcodefrom + length_terminalid + length_productcode + length_tcbiller + length_productname + length_account + length_rc;


}
