/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ppnlib.function;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Hex;

/**
 *
 * @author herrysuganda
 */
public class SHA256Enc {

    
    public static String encryptProc(String data){
        try {
            MessageDigest sha256digester = MessageDigest.getInstance("SHA-256");
            byte[] digest = sha256digester.digest(data.getBytes("UTF-8"));
            String keygen = new String(Hex.encodeHex(digest));
            return keygen;
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
//    public static void main(String[] args) {
//        System.out.println(SHA256Enc.encryptProc("password"));
//    }
}