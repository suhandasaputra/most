/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ppnlib.singleton;

import java.util.HashMap;

/**
 *
 * @author herrysuganda
 */
public class ResponseCodeSingleton {

    private static ResponseCodeSingleton log = null;
    private HashMap responseCode;

    public static ResponseCodeSingleton getInstance() {
        if (log == null) {
            log = new ResponseCodeSingleton();
        }

        return log;
    }

    public ResponseCodeSingleton() {
        responseCode = new HashMap();
        responseCode.put("0000", "Successful");
        responseCode.put("0001", "Gagal, Institusi ID tidak Terdaftar");
        responseCode.put("0002", "Gagal, POS ID tidak Terdaftar");
        responseCode.put("0099", "Gagal, Pos Code tidak terdaftar");
        responseCode.put("0053", "Gagal, noresi sudah ada(duplicate)");
        responseCode.put("0015", "Parameter tidak lengkap");
        responseCode.put("1005", "Gagal, noresi tidak ada");
        responseCode.put("1006", "Gagal, Serial Key Tidak Terdaftar");
        responseCode.put("1007", "Sukses, Transaksi sudah pernah tercatat");
        responseCode.put("2008", "Void gagal, Terminal tidak sesuai");
        responseCode.put("2009", "Customer tidak terdaftar");
        responseCode.put("2010", "Saldo Customer tidak cukup");
        responseCode.put("2011", "Pembayaran ditolak, Sudah terbayar");
//        responseCode.put("2012", "Terdapat masalah pada proses");
//        responseCode.put("1013", "Password merchant salah");
//        responseCode.put("1014", "Password merchant salah");
//        responseCode.put("1015", "Password merchant salah");
//        responseCode.put("1016", "Password merchant salah");
//        responseCode.put("1017", "Password merchant salah");
//        responseCode.put("1018", "Password merchant salah");
//        responseCode.put("1019", "Password merchant salah");
    }

    /**
     * @return the responseCode
     */
    public HashMap getResponseCode() {
        return responseCode;
    }

}
