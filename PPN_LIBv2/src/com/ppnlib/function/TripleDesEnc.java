/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ppnlib.function;

import com.ppnlib.parameter.RuleNameParameter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import org.apache.commons.codec.binary.Hex;

/**
 *
 * @author herrysuganda
 */
public class TripleDesEnc {

//    public static void main(String[] args) {
//        try {
//            // Check to see whether there is a provider that can do TripleDES
//            // encryption. If not, explicitly install the SunJCE provider.
////            try {
////                Cipher c = Cipher.getInstance("DESede");
////            } catch (Exception e) {
////                // An exception here probably means the JCE provider hasn't
////                // been permanently installed on this system by listing it
////                // in the $JAVA_HOME/jre/lib/security/java.security file.
////                // Therefore, we have to install the JCE provider explicitly.
////                System.err.println("Installing SunJCE provider.");
////                Provider sunjce = new com.sun.crypto.provider.SunJCE();
////                Security.addProvider(sunjce);
////            }
////            args = new String[2];
////            args[0] = "-g";
////            args[1] = System.getProperty("user.dir")+"\\tripeldeskey2.txt";
//
//            
//            // This is where we'll read the key from or write it to
////            File keyfile = new File(args[1]);
//
//            // Now check the first arg to see what we're going to do
////            if (args[0].equals("-g")) { // Generate a key
////                System.out.print("Generating key. This may take some time...");
////                System.out.flush();
////                SecretKey key = generateKey();
////                writeKey(key, keyfile);
////                System.out.println("done.");
////                System.out.println("Secret key written to " + args[1] + ". Protect that file carefully!");
////            } else if (args[0].equals("-e")) { // Encrypt stdin to stdout
////                SecretKey key = readKey(keyfile);
////                encrypt(key, System.in, System.out);
////            } else if (args[0].equals("-d")) { // Decrypt stdin to stdout
////                SecretKey key = readKey(keyfile);
////                decrypt(key, System.in, System.out);
////            }
//
//            SecretKey key = readKey("matajarimitrasolusiutama");
////            String encResult = encrypt(key, "data yang akan di enckripsi".trim().getBytes());
//            String encResult = encrypt(key, "Herry suganda format json \"dfdf\"=\"sdfsdfsdffs\"".trim().getBytes());
//            System.out.println(encResult.trim());
//            String decResult = decrypt(key, encResult);
//            System.out.println(decResult);
//        } catch (Exception e) {
//            System.err.println(e);
//            System.err.println("Usage: java " + TripleDesEnc.class.getName() + " -d|-e|-g <keyfile>");
//        }
//    }
    
    public static String encProcess(String keyEnc, String data) throws Exception{
        SecretKey key = readKey(keyEnc);
        String encResult = encrypt(key, data.trim().getBytes());
        return encResult.trim();
    }
    
    public static String decProcess(String keyEnc, String data) throws Exception{
        SecretKey key = readKey(keyEnc);
        return decrypt(key, data);
    }

    /** Generate a secret TripleDES encryption/decryption key */
    public static SecretKey generateKey() throws NoSuchAlgorithmException {
        // Get a key generator for Triple DES (a.k.a DESede)
        KeyGenerator keygen = KeyGenerator.getInstance("DESede");
        // Use it to generate a key
        return keygen.generateKey();
    }

    /** Save the specified TripleDES SecretKey to the specified file */
    public static void writeKey(SecretKey key, File f) throws IOException,
            NoSuchAlgorithmException, InvalidKeySpecException {
        // Convert the secret key to an array of bytes like this
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DESede");
        DESedeKeySpec keyspec = (DESedeKeySpec) keyfactory.getKeySpec(key,
                DESedeKeySpec.class);
        byte[] rawkey = keyspec.getKey();

        // Write the raw key to the file
        FileOutputStream out = new FileOutputStream(f);
        out.write(rawkey);
        out.close();
    }

    /** Read a TripleDES secret key from the specified file */
    public static SecretKey readKey(File f) throws IOException,
            NoSuchAlgorithmException, InvalidKeyException,
            InvalidKeySpecException {
        // Read the raw bytes from the keyfile
        DataInputStream in = new DataInputStream(new FileInputStream(f));
        byte[] rawkey = new byte[(int) f.length()];
        in.readFully(rawkey);
        in.close();

        // Convert the raw bytes to a secret key like this
        DESedeKeySpec keyspec = new DESedeKeySpec(rawkey);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DESede");
        SecretKey key = keyfactory.generateSecret(keyspec);
        return key;
    }

    public static SecretKey readKey(String keyString) throws IOException,
            NoSuchAlgorithmException, InvalidKeyException,
            InvalidKeySpecException {
        // Read the raw bytes from the keyfile
        byte[] rawkey = keyString.getBytes();
        // Convert the raw bytes to a secret key like this
        DESedeKeySpec keyspec = new DESedeKeySpec(rawkey);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DESede");
        SecretKey key = keyfactory.generateSecret(keyspec);
        return key;
    }

    /**
     * Use the specified TripleDES key to encrypt bytes from the input stream
     * and write them to the output stream. This method uses CipherOutputStream
     * to perform the encryption and write bytes at the same time.
     */
    public static void encrypt(SecretKey key, InputStream in, OutputStream out)
            throws NoSuchAlgorithmException, InvalidKeyException,
            NoSuchPaddingException, IOException {
        // Create and initialize the encryption engine
        Cipher cipher = Cipher.getInstance("DESede");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        // Create a special output stream to do the work for us
        CipherOutputStream cos = new CipherOutputStream(out, cipher);

        // Read from the input and write to the encrypting output stream
        byte[] buffer = new byte[2048];
        int bytesRead;
        while ((bytesRead = in.read(buffer)) != -1) {
            cos.write(buffer, 0, bytesRead);
        }
        cos.close();

        // For extra security, don't leave any plaintext hanging around memory.
        java.util.Arrays.fill(buffer, (byte) 0);
    }

    
    /**
     * Use the specified TripleDES key to decrypt bytes ready from the input
     * stream and write them to the output stream. This method uses uses Cipher
     * directly to show how it can be done without CipherInputStream and
     * CipherOutputStream.
     */
    public static void decrypt(SecretKey key, InputStream in, OutputStream out)
            throws NoSuchAlgorithmException, InvalidKeyException, IOException,
            IllegalBlockSizeException, NoSuchPaddingException,
            BadPaddingException {
        // Create and initialize the decryption engine
        Cipher cipher = Cipher.getInstance("DESede");
        cipher.init(Cipher.DECRYPT_MODE, key);

        // Read bytes, decrypt, and write them out.
        byte[] buffer = new byte[2048];
        int bytesRead;
        while ((bytesRead = in.read(buffer)) != -1) {
            out.write(cipher.update(buffer, 0, bytesRead));
        }

        // Write out the final bunch of decrypted bytes
        out.write(cipher.doFinal());
        out.flush();
    }

    public static String encrypt(SecretKey key,byte[] plain ) throws Exception
    {
        Cipher cipher = Cipher.getInstance("DESede");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(plain);
        return new String(Hex.encodeHex(encrypted));
    }
    public static String decrypt(SecretKey key,String decString ) throws Exception
    {
        byte[] cipher = Hex.decodeHex(decString.toCharArray());
        Cipher dcipher = Cipher.getInstance("DESede");
        dcipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decrypted = dcipher.doFinal(cipher);
        return new String(decrypted).trim();
    }
    
//    public static void main(String[] args) throws Exception {
//        System.out.println(TripleDesEnc.encProcess(RuleNameParameter.key_untuk_encrypt_key_merchant, "inikeyencryptmerchant001"));
//    }
}
