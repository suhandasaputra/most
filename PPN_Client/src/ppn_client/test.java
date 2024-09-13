/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ppn_client;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author ADI WIBOWO <PT MATAJARI MITRA SOLUSI>
 */
public class test {

    public static void main(String[] args) {
        List resultColumn = new ArrayList();
        List resultRow1 = new ArrayList();
        List resultRow2 = new ArrayList();
        List resultRow3 = new ArrayList();
        List resultRow4 = new ArrayList();
        List resultRow5 = new ArrayList();
        String s = "1;2;3;4;5;6;7;8;9;10;11;12;13;14;15;16;17;18;19;20;21;22;";
//        String s = "1;2;3;4;";
        String[] part = s.split(";");
        int y = 1;
        System.out.println("FULL STRING : "+s);
        for (int i = 0; i < part.length; i++) {
//            System.out.println("array : " + part[i]);
            if (y == 1) {
                resultRow1.add(part[i]);
                y += 1;
            } else if (y == 2) {
                resultRow2.add(part[i]);
                 y += 1;
            } else if (y == 3) {
                resultRow3.add(part[i]);
                  y += 1;
            } else if (y == 4) {
                resultRow4.add(part[i]);
                 y += 1;
            } else if (y == 5) {
                resultRow5.add(part[i]);
                y = 1;
            }

        }
        
        System.out.println("LIST1 : "+resultRow1);
        System.out.println("LIST2 : "+resultRow2);
        System.out.println("LIST3 : "+resultRow3);
        System.out.println("LIST4 : "+resultRow4);
        System.out.println("LIST5 : "+resultRow5);
    }
}
