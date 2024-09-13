/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ppnlib.function;

import com.ppnlib.parameter.RuleNameParameter;
import com.ppnlib.parameter.StaticParameter;

/**
 *
 * @author herrysuganda
 */
public class HeaderReport {

    public static String getHeaderDetailSuccess() {
        return StringFunction.pad(RuleNameParameter.no, StaticParameter.length_no, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.tanggal, StaticParameter.length_tgl, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.jam, StaticParameter.length_jam, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.noref, StaticParameter.length_noref, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.channel, StaticParameter.length_channel, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.customerNo, StaticParameter.length_customerno, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.kodeBank, StaticParameter.length_bankcodefrom, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.namaBank, StaticParameter.length_bankcodefromname, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.terminalId, StaticParameter.length_terminalid, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.procCode, StaticParameter.length_productcode, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.tcbiller, StaticParameter.length_tcbiller, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.productName, StaticParameter.length_productname, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.account, StaticParameter.length_account, " ", "Left") + StaticParameter.comma
                //                    + StringFunction.pad(RuleNameParamater.rc, StaticParameter.length_rc, " ", "Left")
                + StringFunction.pad(RuleNameParameter.amount, StaticParameter.length_amount, " ", "Right") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.hargaPokok, StaticParameter.length_hargaPokok, " ", "Right") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.feebpd, StaticParameter.length_feebpd, " ", "Right") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.feebank, StaticParameter.length_feebank, " ", "Right") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.feetotal, StaticParameter.length_feetotal, " ", "Right");
    }

    public static String getHeaderDetailSuccessByProduct() {
        return StringFunction.pad(RuleNameParameter.no, StaticParameter.length_no, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.procCode, StaticParameter.length_productcode, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.tcbiller, StaticParameter.length_tcbiller, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.productName, StaticParameter.length_productname, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.channel, StaticParameter.length_channel, " ", "Left") + StaticParameter.comma
                //                    + StringFunction.pad(RuleNameParamater.rc, StaticParameter.length_rc, " ", "Left")
                + StringFunction.pad(RuleNameParameter.amount, StaticParameter.length_amount, " ", "Right") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.hargaPokok, StaticParameter.length_hargaPokok, " ", "Right") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.feebpd, StaticParameter.length_feebpd, " ", "Right") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.feebank, StaticParameter.length_feebank, " ", "Right") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.feetotal, StaticParameter.length_feetotal, " ", "Right");
    }

    public static String getHeaderDetailSuccessByProductPerBank() {
        return StringFunction.pad(RuleNameParameter.no, StaticParameter.length_no, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.procCode, StaticParameter.length_productcode, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.productName, StaticParameter.length_productname, " ", "Left") + StaticParameter.comma
                //                    + StringFunction.pad(RuleNameParamater.rc, StaticParameter.length_rc, " ", "Left")
                + StringFunction.pad(RuleNameParameter.amount, StaticParameter.length_amount, " ", "Right") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.hargaPokok, StaticParameter.length_hargaPokok, " ", "Right") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.feebpd, StaticParameter.length_feebpd, " ", "Right") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.feebank, StaticParameter.length_feebank, " ", "Right") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.feetotal, StaticParameter.length_feetotal, " ", "Right");
    }

    public static String getHeaderDetailPerBank() {
        return StringFunction.pad(RuleNameParameter.no, StaticParameter.length_no, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.tanggal, StaticParameter.length_tgl, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.jam, StaticParameter.length_jam, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.terminalId, StaticParameter.length_terminalid, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.noref, StaticParameter.length_noref, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.customerNo, StaticParameter.length_customerno, " ", "Left") + StaticParameter.comma
                //                    + StringFunction.pad(RuleNameParamater.kodeBank, StaticParameter.length_bankcodefrom, " ", "Left")
                + StringFunction.pad(RuleNameParameter.procCode, StaticParameter.length_productcode, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.productName, StaticParameter.length_productname, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.account, StaticParameter.length_account, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.rc, StaticParameter.length_rc, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.amount, StaticParameter.length_amount, " ", "Right");
    }

    public static String getHeaderDetailSuccessPerBank() {
        return StringFunction.pad(RuleNameParameter.no, StaticParameter.length_no, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.tanggal, StaticParameter.length_tgl, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.jam, StaticParameter.length_jam, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.terminalId, StaticParameter.length_terminalid, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.noref, StaticParameter.length_noref, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.customerNo, StaticParameter.length_customerno, " ", "Left") + StaticParameter.comma
                //                    + StringFunction.pad(RuleNameParamater.kodeBank, StaticParameter.length_bankcodefrom, " ", "Left")
                + StringFunction.pad(RuleNameParameter.procCode, StaticParameter.length_productcode, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.productName, StaticParameter.length_productname, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.account, StaticParameter.length_account, " ", "Left") + StaticParameter.comma
                //                    + StringFunction.pad(RuleNameParamater.rc, StaticParameter.length_rc, " ", "Left")
                + StringFunction.pad(RuleNameParameter.amount, StaticParameter.length_amount, " ", "Right") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.hargaPokok, StaticParameter.length_hargaPokok, " ", "Right") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.feebpd, StaticParameter.length_feebpd, " ", "Right") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.feebank, StaticParameter.length_feebank, " ", "Right") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.feetotal, StaticParameter.length_feetotal, " ", "Right");
    }

    public static String getHeaderDetail() {
        return StringFunction.pad(RuleNameParameter.no, StaticParameter.length_no, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.tanggal, StaticParameter.length_tgl, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.jam, StaticParameter.length_jam, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.noref, StaticParameter.length_noref, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.channel, StaticParameter.length_channel, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.customerNo, StaticParameter.length_customerno, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.kodeBank, StaticParameter.length_bankcodefrom, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.namaBank, StaticParameter.length_bankcodefromname, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.terminalId, StaticParameter.length_terminalid, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.procCode, StaticParameter.length_productcode, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.tcbiller, StaticParameter.length_tcbiller, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.productName, StaticParameter.length_productname, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.account, StaticParameter.length_account, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.rc, StaticParameter.length_rc, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.amount, StaticParameter.length_amount, " ", "Right");
    }

    public static String getHeaderDetail2() {
        return StringFunction.pad(RuleNameParameter.no, StaticParameter.length_no, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.tanggal, StaticParameter.length_tgl, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.jam, StaticParameter.length_jam, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.noref, StaticParameter.length_noref, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.channel, StaticParameter.length_channel, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.customerNo, StaticParameter.length_customerno, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.kodeBank, StaticParameter.length_bankcodefrom, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.namaBank, StaticParameter.length_bankcodefromname, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.terminalId, StaticParameter.length_terminalid, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.procCode, StaticParameter.length_productcode, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.tcbiller, StaticParameter.length_tcbiller, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.productName, StaticParameter.length_productname, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.account, StaticParameter.length_account, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.rc, StaticParameter.length_rc, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.rc_desc, StaticParameter.length_rcdesc, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.amount, StaticParameter.length_amount, " ", "Right");
    }

    public static String getHeaderRekon() {

        return StringFunction.pad(RuleNameParameter.no, StaticParameter.length_no, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.requesttime, StaticParameter.length_tanggal_trx, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.noref, StaticParameter.length_noref, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.userid, StaticParameter.length_id, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.product, StaticParameter.length_productname, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.typetransaction, StaticParameter.length_typetrx, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.hargabeli, StaticParameter.length_amount, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.hargajual, StaticParameter.length_amount, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.tagihanuser, StaticParameter.length_amount, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.tagihanbiller, StaticParameter.length_amount, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.feebeli, StaticParameter.length_amount, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.feejual, StaticParameter.length_amount, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.profit, StaticParameter.length_amount, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.prev_bal, StaticParameter.length_amount, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.saldo, StaticParameter.length_amount, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.statustransaksi, StaticParameter.length_amount, " ", "Left");

    }
    
    public static String getHeaderTopup() {
							
        return StringFunction.pad(RuleNameParameter.no, StaticParameter.length_no, " ", "Left") + StaticParameter.comma
                + StringFunction.pad("Biller Code", StaticParameter.length_tanggal_trx, " ", "Left") + StaticParameter.comma
                + StringFunction.pad("Amount", StaticParameter.length_noref, " ", "Left") + StaticParameter.comma
                + StringFunction.pad("Before Balance", StaticParameter.length_id, " ", "Left") + StaticParameter.comma
                + StringFunction.pad("After Balance", StaticParameter.length_productname, " ", "Left") + StaticParameter.comma
                + StringFunction.pad("Bank Name", StaticParameter.length_typetrx, " ", "Left") + StaticParameter.comma
                + StringFunction.pad("Account Number", StaticParameter.length_amount, " ", "Left") + StaticParameter.comma
                + StringFunction.pad("Transfer Date", StaticParameter.length_amount, " ", "Left") + StaticParameter.comma
                + StringFunction.pad("Top up Date", StaticParameter.length_amount, " ", "Left");

    }

    public static String getHeaderRekonAllBiller() {

        return StringFunction.pad(RuleNameParameter.no, StaticParameter.length_no, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.requesttime, StaticParameter.length_tanggal_trx, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.noref, StaticParameter.length_noref, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.userid, StaticParameter.length_id, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.product, StaticParameter.length_productname, " ", "Left") + StaticParameter.comma
                                + StringFunction.pad(RuleNameParameter.billername, StaticParameter.length_productname, " ", "Left") + StaticParameter.comma

                + StringFunction.pad(RuleNameParameter.typetransaction, StaticParameter.length_typetrx, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.hargabeli, StaticParameter.length_amount, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.hargajual, StaticParameter.length_amount, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.tagihanuser, StaticParameter.length_amount, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.tagihanbiller, StaticParameter.length_amount, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.feebeli, StaticParameter.length_amount, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.feejual, StaticParameter.length_amount, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.profit, StaticParameter.length_amount, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.prev_bal, StaticParameter.length_amount, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.saldo, StaticParameter.length_amount, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.statustransaksi, StaticParameter.length_amount, " ", "Left");

    }

    public static String getHeaderSettlement() {

        return StringFunction.pad(RuleNameParameter.saldoawal, StaticParameter.length_tanggal_trx, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.incoming, StaticParameter.length_tanggal_trx, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.outgoing, StaticParameter.length_tanggal_trx, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.purchase, StaticParameter.length_tanggal_trx, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.payment, StaticParameter.length_tanggal_trx, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.profit, StaticParameter.length_tanggal_trx, " ", "Left") + StaticParameter.comma
                + StringFunction.pad(RuleNameParameter.saldoakhir, StaticParameter.length_nopd, " ", "Left");

    }

    public static String getLineHeaderDetailSuccess() {
        return StringFunction.pad("", StaticParameter.length_success_headertitle, "-", "Left");
    }

    public static String getLineHeaderDetailSuccessGroupProduct() {
        return StringFunction.pad("", StaticParameter.length_amountspace_perproduct + StaticParameter.length_hargaPokok + StaticParameter.length_feebank + StaticParameter.length_feebpd + StaticParameter.length_feetotal + StaticParameter.length_amount, "-", "Left");
    }

    public static String getLineHeaderDetailSuccessGroupProductPerbank() {
        return StringFunction.pad("", StaticParameter.length_amountspace_perproduct + StaticParameter.length_feebank + StaticParameter.length_feebpd + StaticParameter.length_feetotal + StaticParameter.length_amount - StaticParameter.length_channel - StaticParameter.length_tcbiller, "-", "Left");
    }

    public static String getLineHeaderDetail() {
        return StringFunction.pad("", StaticParameter.length_all_headertitle, "-", "Left");
    }

    public static String getLineHeaderDetailPerBank() {
        return StringFunction.pad("", StaticParameter.length_all_headertitle_perbank, "-", "Left");
    }

    public static String getLineHeaderDetailSuccessPerBank() {
        return StringFunction.pad("", StaticParameter.length_success_headertitle_perbank, "-", "Left");
    }

    public static String getAmountLeftSpace5() {
        return StringFunction.pad("", StaticParameter.length_amountspace_perproduct, " ", "Left");
    }

    public static String getAmountLeftSpace6() {
        return StringFunction.pad("", StaticParameter.length_amountspace_perproduct - StaticParameter.length_tcbiller - StaticParameter.length_channel, " ", "Left");
    }

    public static String getAmountLeftSpace() {
        return StringFunction.pad("", StaticParameter.length_amountspace, " ", "Left");
    }

    public static String getAmountLeftSpace4() {
        return StringFunction.pad("", StaticParameter.length_success_headertitle_perbank - StaticParameter.length_hargaPokok - StaticParameter.length_amount - StaticParameter.length_feebank - StaticParameter.length_feetotal - StaticParameter.length_feebpd, " ", "Left");
    }

    public static String getAmountLeftSpace3() {
        return StringFunction.pad("", StaticParameter.length_suspect_all, " ", "Left");
    }

    public static String getAmountLeftSpace2() {
        return StringFunction.pad("", StaticParameter.length_all_headertitle - StaticParameter.length_amount, " ", "Left");
    }

    public static String getAmountLeftSpacePerBank() {
        return StringFunction.pad("", StaticParameter.length_all_headertitle_perbank - StaticParameter.length_amount, " ", "Left");
    }
}
