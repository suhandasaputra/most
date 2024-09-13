/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ppn.servlet;

import com.ppn.process.CreateReport;
import com.ppn.process.RequestResi;
import com.ppnlib.database.DatabaseProcess;
//import com.asacbalib.function.CheckSumFunction;
import com.ppnlib.function.JsonProcess;
import com.ppnlib.singleton.ResponseCodeSingleton;
import com.ppnlib.parameter.RuleNameParameter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author adi
 */
public class OnlineMerchant extends HttpServlet {

    private static Logger log = Logger.getLogger(OnlineMerchant.class);
    private DatabaseProcess dp = new DatabaseProcess();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
        HashMap hmResponse = new HashMap();
        String responseCode = "";

        String line = "";
        String inputString = "";
        HashMap hmData;
        DatabaseProcess db = new DatabaseProcess();
        while ((line = in.readLine()) != null) {
            inputString += line;
        }
        inputString = inputString.replace("%20", " ");
        log.info("\n" + "PPN Message Incoming from Merchant :" + inputString + "\n");
        System.out.println("PPN Message Incoming from Merchant :" + inputString);
        try {
            hmData = JsonProcess.decodeJson(inputString);

            if (hmData.get(RuleNameParameter.PROC_CODE).toString().equals("001")) {
                if (dp.checkingMerchant(hmData)) {
                    if (dp.checkingPOS(hmData)) {
                        if (dp.checkingPoscode(hmData)) {
                            if (dp.checkingSerialKey(hmData)) {
                                if (dp.checkingResi(hmData)) {
                                    dp.saveNewPaycode(hmData);
                                    hmData.put(RuleNameParameter.RESP_CODE, "0000");
                                    hmData.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get("0000"));
                                } else {
                                    hmData.put(RuleNameParameter.RESP_CODE, "0053");
                                    hmData.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get("0053"));
                                }
                            } else {
                                hmData.put(RuleNameParameter.RESP_CODE, "1006");
                                hmData.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get("1006"));
                            }
                        } else {
                            hmData.put(RuleNameParameter.RESP_CODE, "0099");
                            hmData.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get("0099"));
                        }
                    } else {
                        hmData.put(RuleNameParameter.RESP_CODE, "0002");
                        hmData.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get("0002"));
                    }
                } else {
                    hmData.put(RuleNameParameter.RESP_CODE, "0001");
                    hmData.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get("0001"));
                }
                dp.saveLogTrx(hmData.get(RuleNameParameter.INSTITUTE_ID).toString(), hmData.get(RuleNameParameter.POS_ID).toString(), hmData.get(RuleNameParameter.RESP_CODE).toString(),
                        hmData.get(RuleNameParameter.PROC_CODE).toString(), inputString, JsonProcess.generateJson(hmData), hmData.get(RuleNameParameter.NO_RESI).toString(), hmData.get(RuleNameParameter.RESP_DESC).toString());
            } else if (hmData.get(RuleNameParameter.PROC_CODE).toString().equals("002")) {
                if (dp.checkingMerchant(hmData)) {
                    if (dp.checkingPOS(hmData)) {
                        if (dp.checkingPoscode(hmData)) {
                            if (dp.checkingSerialKey(hmData)) {
                                if (dp.checkingResi(hmData)) {
                                    dp.saveNewPaycode(hmData);
                                    hmData.put(RuleNameParameter.RESP_CODE, "0000");
                                    hmData.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get("0000"));
                                } else {
                                    hmData.put(RuleNameParameter.RESP_CODE, "1007");
                                    hmData.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get("1007"));
                                }
                            } else {
                                hmData.put(RuleNameParameter.RESP_CODE, "1006");
                                hmData.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get("1006"));
                            }
                        } else {
                            hmData.put(RuleNameParameter.RESP_CODE, "0099");
                            hmData.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get("0099"));
                        }
                    } else {
                        hmData.put(RuleNameParameter.RESP_CODE, "0002");
                        hmData.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get("0002"));
                    }
                } else {
                    hmData.put(RuleNameParameter.RESP_CODE, "0001");
                    hmData.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get("0001"));
                }
                dp.saveLogTrx(hmData.get(RuleNameParameter.INSTITUTE_ID).toString(), hmData.get(RuleNameParameter.POS_ID).toString(), hmData.get(RuleNameParameter.RESP_CODE).toString(),
                        hmData.get(RuleNameParameter.PROC_CODE).toString(), inputString, JsonProcess.generateJson(hmData), hmData.get(RuleNameParameter.NO_RESI).toString(), hmData.get(RuleNameParameter.RESP_DESC).toString());

            } else if (hmData.get(RuleNameParameter.PROC_CODE).toString().equals("003")) {
                if (dp.checkingMerchant(hmData)) {
                    if (dp.checkingPOS(hmData)) {
                        if (dp.checkingPoscode(hmData)) {
                            if (dp.checkingSerialKey(hmData)) {
                                if (dp.checkingResiOnListTax(hmData)) {
                                    if (dp.checkingMerchantPosidPosCodeKey(hmData)) {
                                        if (dp.saveVoidAndDelete(hmData)) {
                                            hmData.put(RuleNameParameter.RESP_CODE, "0000");
                                            hmData.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get("0000") + ", Transaksi Dibatalkan");
                                        } else {
                                            hmData.put(RuleNameParameter.RESP_CODE, "0053");
                                            hmData.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get("0053"));
                                        }
                                    } else {
                                        hmData.put(RuleNameParameter.RESP_CODE, "2008");
                                        hmData.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get("2008"));
                                    }
                                } else {
                                    hmData.put(RuleNameParameter.RESP_CODE, "1005");
                                    hmData.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get("1005"));
                                }
                            } else {
                                hmData.put(RuleNameParameter.RESP_CODE, "1006");
                                hmData.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get("1006"));
                            }
                        } else {
                            hmData.put(RuleNameParameter.RESP_CODE, "0099");
                            hmData.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get("0099"));
                        }
                    } else {
                        hmData.put(RuleNameParameter.RESP_CODE, "0002");
                        hmData.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get("0002"));
                    }
                } else {
                    hmData.put(RuleNameParameter.RESP_CODE, "0001");
                    hmData.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get("0001"));
                }
                dp.saveLogTrx(hmData.get(RuleNameParameter.INSTITUTE_ID).toString(), hmData.get(RuleNameParameter.POS_ID).toString(), hmData.get(RuleNameParameter.RESP_CODE).toString(),
                        hmData.get(RuleNameParameter.PROC_CODE).toString(), inputString, JsonProcess.generateJson(hmData), hmData.get(RuleNameParameter.NO_RESI).toString(), hmData.get(RuleNameParameter.RESP_DESC).toString());
            } else if (hmData.get(RuleNameParameter.PROC_CODE).toString().equals("004")) {
                if (dp.checkingMerchant(hmData)) {
                    if (dp.checkingPOS(hmData)) {
                        if (dp.checkingPoscode(hmData)) {
                            if (dp.checkingSerialKey(hmData)) {
//                                if (dp.checkingEcho(hmData)) {
                                dp.echoMessage(hmData);
//                                    hmData.put(RuleNameParameter.RESP_CODE, "0000");
//                                    hmData.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get("0000"));
//                                } else {
//                                    hmData.put(RuleNameParameter.RESP_CODE, "1007");
//                                    hmData.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get("1007"));
//                                }
                            } else {
                                hmData.put(RuleNameParameter.RESP_CODE, "1006");
                                hmData.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get("1006"));
                            }
                        } else {
                            hmData.put(RuleNameParameter.RESP_CODE, "0099");
                            hmData.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get("0099"));
                        }
                    } else {
                        hmData.put(RuleNameParameter.RESP_CODE, "0002");
                        hmData.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get("0002"));
                    }
                } else {
                    hmData.put(RuleNameParameter.RESP_CODE, "0001");
                    hmData.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get("0001"));
                }
            } else if (hmData.get(RuleNameParameter.PROC_CODE).toString().equals("006")) {
                if (dp.checkingResiOnListTax(hmData)) {
                    if (dp.checkingCustomer(hmData)) {
                        if (dp.checkingStatusPaid(hmData)) {
                            if (dp.checkingBalance(hmData)) {
                                dp.saveDebet(hmData);
                                hmData.put(RuleNameParameter.RESP_CODE, "0000");
                                hmData.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get("0000"));
                            } else {
                                hmData.put(RuleNameParameter.RESP_CODE, "2010");
                                hmData.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get("2010"));
                            }
                        } else {
                            hmData.put(RuleNameParameter.RESP_CODE, "2011");
                            hmData.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get("2011"));
                        }
                    } else {
                        hmData.put(RuleNameParameter.RESP_CODE, "2009");
                        hmData.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get("2009"));
                    }

                } else {
                    hmData.put(RuleNameParameter.RESP_CODE, "1005");
                    hmData.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get("1005"));
                }
            } else if (hmData.get(RuleNameParameter.PROC_CODE).toString().equals("007")) {
                if (dp.checkingCustomer(hmData)) {
                    dp.cekBal(hmData);
                    hmData.put(RuleNameParameter.RESP_CODE, "0000");
                    hmData.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get("0000"));
                } else {
                    hmData.put(RuleNameParameter.RESP_CODE, "2009");
                    hmData.put(RuleNameParameter.RESP_DESC, ResponseCodeSingleton.getInstance().getResponseCode().get("2009"));
                }
            }
            log.info("\n" + "PPN Message outgoing to Merchant :" + JsonProcess.generateJson(hmData) + "\n");
            System.out.println("PPN Message outgoing to Merchant :" + JsonProcess.generateJson(hmData));
            response.getOutputStream().write(JsonProcess.generateJson(hmData).getBytes());
            response.getOutputStream().flush();
        } catch (Exception e) {
            hmData = new HashMap();
            hmData.put(RuleNameParameter.RESP_CODE, "0015");
            hmData.put(RuleNameParameter.RESP_DESC, "parameter tidak lengkap");
            log.info("\n" + "PPN Message outgoing to Merchant :" + JsonProcess.generateJson(hmData) + "\n");
            System.out.println("PPN Message outgoing to Merchant :" + JsonProcess.generateJson(hmData));
            response.getOutputStream().write(JsonProcess.generateJson(hmData).getBytes());
            response.getOutputStream().flush();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
