/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ppnlib.function;

import com.ppnlib.parameter.StaticParameter;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.logging.Level;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.log4j.Logger;

/**
 *
 * @author herrysuganda
 */
public class SendHttpProcess {

    private static final Logger log = Logger.getLogger(SendHttpProcess.class);

    public String sendHttpRequest(String urlpath, String message) {
//        System.setProperty("sun.net.client.defaultReadTimeout", "3000");
        String result = "";
        message = message.replaceAll(" ", "%20");
        byte[] postData = message.getBytes(Charset.forName("UTF-8"));
        try {
            log.info(urlpath + "?" + message);
            System.out.println(urlpath + "?" + message);
            URL url = new URL(urlpath);
            DataOutputStream wr = null;
            BufferedReader in = null;
            if (urlpath.substring(0, 5).toLowerCase().equals("https")) {
                TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }};
                final SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
//                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                HostnameVerifier allHostsValid = new HostnameVerifier() {
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                };

                // Install the all-trusting host verifier
//                HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
//                javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
//                        new javax.net.ssl.HostnameVerifier() {
//
//                            public boolean verify(String hostname,
//                                    javax.net.ssl.SSLSession sslSession) {
//                                
//                                return true;
//                            }
//                        });
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setSSLSocketFactory(sc.getSocketFactory());
                conn.setHostnameVerifier(allHostsValid);
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestMethod("POST");
                conn.setReadTimeout(StaticParameter.timeout_second * 1000);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("charset", "utf-8");
                conn.setRequestProperty("Content-Length", Integer.toString(postData.length));
                conn.setUseCaches(false);
                wr = new DataOutputStream(conn.getOutputStream());
                wr.write(postData);
                in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestMethod("POST");
                conn.setReadTimeout(StaticParameter.timeout_second * 1000);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//                conn.setRequestProperty("Content-Type", "text/xml");
                conn.setRequestProperty("charset", "utf-8");
                conn.setRequestProperty("Content-Length", Integer.toString(postData.length));
                conn.setUseCaches(false);
                wr = new DataOutputStream(conn.getOutputStream());
                wr.write(postData);
                in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            }

//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                String encoding = Base64.getEncoder().encodeToString(("RizaV:r1z4v").getBytes("UTF-8"));
//                conn.setRequestMethod("POST");
//                conn.setDoOutput(true);
//                conn.setDoInput(true);
//                conn.setRequestProperty("Authorization", "Basic " + encoding);
//                conn.setReadTimeout(StaticParameter.timeout_second * 1000);
//                conn.setRequestProperty("Content-Type", "application/json");
////                conn.setRequestProperty("Content-Type", "text/xml");
//                conn.setRequestProperty("charset", "utf-8");
//                conn.setRequestProperty("Content-Length", Integer.toString(postData.length));
//                conn.setUseCaches(false);
//                wr = new DataOutputStream(conn.getOutputStream());
//                wr.write(postData);
//                in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            }

            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                result += inputLine;
            }
            in.close();
            in = null;
            inputLine = null;

        } catch (SocketTimeoutException ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
            return "timeout";

        } catch (IOException ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
            return "error";
        } catch (KeyManagementException ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
            return "error";
        } catch (NoSuchAlgorithmException ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
            return "error";
        }
//        System.out.println("result : " + result);
        return result;

    }
}
