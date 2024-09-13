/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ppn.model;

import java.util.HashMap;

/**
 *
 * @author syukur
 */




public class Report {
    
        private String id;
        private String tanggal_trx;
        private String no_struk;
        private String description;
        private String amount_trx;
        private String nopd;
        private String source;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTanggal_trx() {
        return tanggal_trx;
    }

    public void setTanggal_trx(String tanggal_trx) {
        this.tanggal_trx = tanggal_trx;
    }

    public String getNo_struk() {
        return no_struk;
    }

    public void setNo_struk(String no_struk) {
        this.no_struk = no_struk;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount_trx() {
        return amount_trx;
    }

    public void setAmount_trx(String amount_trx) {
        this.amount_trx = amount_trx;
    }

    public String getNopd() {
        return nopd;
    }

    public void setNopd(String nopd) {
        this.nopd = nopd;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

}
