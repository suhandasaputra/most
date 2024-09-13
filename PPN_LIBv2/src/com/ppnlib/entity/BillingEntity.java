/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ppnlib.entity;

import java.util.HashMap;

/**
 *
 * @author adi
 */
public class BillingEntity {

    private HashMap HmData;
    private String CompanyCode;
    private String TransactionNo1;
    private String ChannelType;
    private String TransactionDate;
    private String TransactionTime;
    private String TraceNo;
    private String TransactionNo2;
    private String Status;
    private String CustomerName;
    private String BillDescription;
    private String BillAmount;
    private String MerchantId;
    private String MerchantName;
    private String ItemDetails;
    private String Reason;

    public HashMap getHmData() {
        return HmData;
    }

    public void setHmData(HashMap hmData) {
        this.HmData = hmData;
    }

    public String getCompanyCode() {
        return CompanyCode;
    }

    public void setCompanyCode(String CompanyCode) {
        this.CompanyCode = CompanyCode;
    }

    public String getTransactionNo1() {
        return TransactionNo1;
    }

    public void setTransactionNo1(String TransactionNo1) {
        this.TransactionNo1 = TransactionNo1;
    }

    public String getChannelType() {
        return ChannelType;
    }

    public void setChannelType(String ChannelType) {
        this.ChannelType = ChannelType;
    }

    public String getTransactionDate() {
        return TransactionDate;
    }

    public void setTransactionDate(String TransactionDate) {
        this.TransactionDate = TransactionDate;
    }

    public String getTransactionTime() {
        return TransactionTime;
    }

    public void setTransactionTime(String TransactionTime) {
        this.TransactionTime = TransactionTime;
    }

    public String getTraceNo() {
        return TraceNo;
    }

    public void setTraceNo(String TraceNo) {
        this.TraceNo = TraceNo;
    }

    public String getTransactionNo2() {
        return TransactionNo2;
    }

    public void setTransactionNo2(String TransactionNo2) {
        this.TransactionNo2 = TransactionNo2;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String CustomerName) {
        this.CustomerName = CustomerName;
    }

    public String getBillDescription() {
        return BillDescription;
    }

    public void setBillDescription(String BillDescription) {
        this.BillDescription = BillDescription;
    }

    public String getBillAmount() {
        return BillAmount;
    }

    public void setBillAmount(String BillAmount) {
        this.BillAmount = BillAmount;
    }

    public String getMerchantId() {
        return MerchantId;
    }

    public void setMerchantId(String MerchantId) {
        this.MerchantId = MerchantId;
    }

    public String getMerchantName() {
        return MerchantName;
    }

    public void setMerchantName(String MerchantName) {
        this.MerchantName = MerchantName;
    }

    public String getItemDetails() {
        return ItemDetails;
    }

    public void setItemDetails(String ItemDetails) {
        this.ItemDetails = ItemDetails;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String Reason) {
        this.Reason = Reason;
    }

}
