/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ppn.model;

/**
 *
 * @author syukur
 */
public class Agentyabes {

    private String agent_id;
    private String agent_pass;
    private String agent_name;
    private String phonenumber;

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }


    private String balance;
    private String agent_address;
    private String amount;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    private String agent_pin;
    private String agent_phone;
    private int agent_level;

    public int getAgent_level() {
        return agent_level;
    }

    public void setAgent_level(int agent_level) {
        this.agent_level = agent_level;
    }

    /**
     * @return the agent_id
     */
    public String getAgent_id() {
        return agent_id;
    }

    /**
     * @param agent_id the agent_id to set
     */
    public void setAgent_id(String agent_id) {
        this.agent_id = agent_id;
    }

    /**
     * @return the agent_pass
     */
    public String getAgent_pass() {
        return agent_pass;
    }

    /**
     * @param agent_pass the agent_pass to set
     */
    public void setAgent_pass(String agent_pass) {
        this.agent_pass = agent_pass;
    }

    /**
     * @return the agent_name
     */
    public String getAgent_name() {
        return agent_name;
    }

    /**
     * @param agent_name the agent_name to set
     */
    public void setAgent_name(String agent_name) {
        this.agent_name = agent_name;
    }

    /**
     * @return the agent_address
     */
    public String getAgent_address() {
        return agent_address;
    }

    /**
     * @param agent_address the agent_address to set
     */
    public void setAgent_address(String agent_address) {
        this.agent_address = agent_address;
    }

    /**
     * @return the agent_pin
     */
    public String getAgent_pin() {
        return agent_pin;
    }

    /**
     * @param agent_pin the agent_pin to set
     */
    public void setAgent_pin(String agent_pin) {
        this.agent_pin = agent_pin;
    }

    /**
     * @return the agent_phone
     */
    public String getAgent_phone() {
        return agent_phone;
    }

    /**
     * @param agent_phone the agent_phone to set
     */
    public void setAgent_phone(String agent_phone) {
        this.agent_phone = agent_phone;
    }
}
