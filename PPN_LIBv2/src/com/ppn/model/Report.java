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
    
        private String nopd;
    private String source;

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

    
    private String service_tax;

    public String getService_tax() {
        return service_tax;
    }

    public void setService_tax(String service_tax) {
        this.service_tax = service_tax;
    }
    private String institutename;

    public String getInstitutename() {
        return institutename;
    }

    public void setInstitutename(String institutename) {
        this.institutename = institutename;
    }
    private String dt_internal;

    public String getDt_internal() {
        return dt_internal;
    }

    public void setDt_internal(String dt_internal) {
        this.dt_internal = dt_internal;
    }
    private String noresi;
    private HashMap data;

    public String getNoresi() {
        return noresi;
    }

    public HashMap getData() {
        return data;
    }

    public void setData(HashMap data) {
        this.data = data;
    }

    public void setNoresi(String noresi) {
        this.noresi = noresi;
    }

    public String getDt_merc() {
        return dt_merc;
    }

    public void setDt_merc(String dt_merc) {
        this.dt_merc = dt_merc;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPpn() {
        return ppn;
    }

    public void setPpn(String ppn) {
        this.ppn = ppn;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getInstituteid() {
        return instituteid;
    }

    public void setInstituteid(String instituteid) {
        this.instituteid = instituteid;
    }


    public String getPoscode() {
        return poscode;
    }

    public void setPoscode(String poscode) {
        this.poscode = poscode;
    }
    
    
    
    private String split_bill;
    private String payment_methode;
    private String card_number;

    public String getSplit_bill() {
        return split_bill;
    }

    public void setSplit_bill(String split_bill) {
        this.split_bill = split_bill;
    }

    public String getPayment_methode() {
        return payment_methode;
    }

    public void setPayment_methode(String payment_methode) {
        this.payment_methode = payment_methode;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }
    private String dt_merc;
    private String amount;
    private String ppn;
    private String detail;
    private String instituteid;
    private String posid;

    public String getPosid() {
        return posid;
    }

    public void setPosid(String posid) {
        this.posid = posid;
    }
    private String poscode;
    

    
}
