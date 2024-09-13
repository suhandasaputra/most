/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.asacba.bank;

import com.ppnlib.function.CheckSumFunction;

/**
 *
 * @author adi
 */
public class ChecksumFormula {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        // == Formula CHECKSUM    ==
        //
        //     sample amount = abcd
        // (i) no urut desc  = 4321
        //
        // ======================================
        //
        // a*(2^i) + b*(2^i) + c*(2^i) + d*(2^i)
        //
        // ======================================

        String a = "12345";
        System.out.println("-------------------");
        System.out.println(a);
        System.out.println("-------------------\n");
        int checksum = 0;
        for (int i = 0; i < a.length(); i++) {
            System.out.print(a.substring(a.length() - (i + 1), a.length() - i));
            System.out.print(" * ");
            System.out.print(" ( 2^" + i + " ) ");// + (int) Math.pow(2, i));
            System.out.print(" = ");
            System.out.println((Integer.parseInt(a.substring(a.length() - (i + 1), a.length() - i))) * ((int) Math.pow(2, i)));
            checksum += (Integer.parseInt(a.substring(a.length() - (i + 1), a.length() - i))) * ((int) Math.pow(2, i));
        }
        System.out.println("-------------------(+) ");
        System.out.println("              = " + checksum);

    }
}
