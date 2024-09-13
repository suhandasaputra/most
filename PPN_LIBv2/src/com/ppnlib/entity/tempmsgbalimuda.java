/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ppnlib.entity;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author matajari
 */
public class tempmsgbalimuda implements Serializable {

    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
     */
    /**
     *
     * @author suhanda
     */
    private static long serialVersionUID = 1L;

    private String msgid;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static void setSerialVersionUID(long serialVersionUID) {
        tempmsgbalimuda.serialVersionUID = serialVersionUID;
    }

    private String no;
    private String requesttime;
    private String noref;
    private String userid;
    private String product;
    private String typetransaction;
    private String hargabeli;
    private String hargajual;
    private String feebeli;
    private String feejual;
    private String profit;
    private String prev_bal;
    private String saldo;
    private String saldoawal;
    private String saldoakhir;
    private String billername;
    private String tagihan;
    private String tagihanuser;

    private String billercode;
    private String amount;
    private String before_balance;
    private String after_balance;
    private String bank_name;
    private String account_number;
    private String transfer_date;
    private String topup_date;

    public String getBillercode() {
        return billercode;
    }

    public void setBillercode(String billercode) {
        this.billercode = billercode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBefore_balance() {
        return before_balance;
    }

    public void setBefore_balance(String before_balance) {
        this.before_balance = before_balance;
    }

    public String getAfter_balance() {
        return after_balance;
    }

    public void setAfter_balance(String after_balance) {
        this.after_balance = after_balance;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getTransfer_date() {
        return transfer_date;
    }

    public void setTransfer_date(String transfer_date) {
        this.transfer_date = transfer_date;
    }

    public String getTopup_date() {
        return topup_date;
    }

    public void setTopup_date(String topup_date) {
        this.topup_date = topup_date;
    }

    public String getTagihanuser() {
        return tagihanuser;
    }

    public void setTagihanuser(String tagihanuser) {
        this.tagihanuser = tagihanuser;
    }
    private String statustransaksi;

    public String getStatustransaksi() {
        return statustransaksi;
    }

    public void setStatustransaksi(String statustransaksi) {
        this.statustransaksi = statustransaksi;
    }

    public String getTagihan() {
        return tagihan;
    }

    public void setTagihan(String tagihan) {
        this.tagihan = tagihan;
    }

    public String getBillername() {
        return billername;
    }

    public void setBillername(String billername) {
        this.billername = billername;
    }

    public String getSaldoawal() {
        return saldoawal;
    }

    public void setSaldoawal(String saldoawal) {
        this.saldoawal = saldoawal;
    }

    public String getSaldoakhir() {
        return saldoakhir;
    }

    public void setSaldoakhir(String saldoakhir) {
        this.saldoakhir = saldoakhir;
    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getRequesttime() {
        return requesttime;
    }

    public void setRequesttime(String requesttime) {
        this.requesttime = requesttime;
    }

    public String getNoref() {
        return noref;
    }

    public void setNoref(String noref) {
        this.noref = noref;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getTypetransaction() {
        return typetransaction;
    }

    public void setTypetransaction(String typetransaction) {
        this.typetransaction = typetransaction;
    }

    public String getHargabeli() {
        return hargabeli;
    }

    public void setHargabeli(String hargabeli) {
        this.hargabeli = hargabeli;
    }

    public String getHargajual() {
        return hargajual;
    }

    public void setHargajual(String hargajual) {
        this.hargajual = hargajual;
    }

    public String getFeebeli() {
        return feebeli;
    }

    public void setFeebeli(String feebeli) {
        this.feebeli = feebeli;
    }

    public String getFeejual() {
        return feejual;
    }

    public void setFeejual(String feejual) {
        this.feejual = feejual;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getPrev_bal() {
        return prev_bal;
    }

    public void setPrev_bal(String prev_bal) {
        this.prev_bal = prev_bal;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

}
