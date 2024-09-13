/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ppnlib.entity;

/**
 *
 * @author herrysuganda
 */
public class MerchantEntity {
    private String merchantId;
    private String merchantName;
    private String merchantKey;
    private String merchantPass;
    private String merchantUrl;
    private String merchantStatus;

    /**
     * @return the merchantId
     */
    public String getMerchantId() {
        return merchantId;
    }

    /**
     * @param merchantId the merchantId to set
     */
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    /**
     * @return the merchantName
     */
    public String getMerchantName() {
        return merchantName;
    }

    /**
     * @param merchantName the merchantName to set
     */
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    /**
     * @return the merchantKey
     */
    public String getMerchantKey() {
        return merchantKey;
    }

    /**
     * @param merchantKey the merchantKey to set
     */
    public void setMerchantKey(String merchantKey) {
        this.merchantKey = merchantKey;
    }

    /**
     * @return the merchantPass
     */
    public String getMerchantPass() {
        return merchantPass;
    }

    /**
     * @param merchantPass the merchantPass to set
     */
    public void setMerchantPass(String merchantPass) {
        this.merchantPass = merchantPass;
    }

    /**
     * @return the merchantUrl
     */
    public String getMerchantUrl() {
        return merchantUrl;
    }

    /**
     * @param merchantUrl the merchantUrl to set
     */
    public void setMerchantUrl(String merchantUrl) {
        this.merchantUrl = merchantUrl;
    }

    /**
     * @return the merchantStatus
     */
    public String getMerchantStatus() {
        return merchantStatus;
    }

    /**
     * @param merchantStatus the merchantStatus to set
     */
    public void setMerchantStatus(String merchantStatus) {
        this.merchantStatus = merchantStatus;
    }
}
