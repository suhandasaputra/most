/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ppnlib.function;

import com.ppnlib.parameter.RuleNameParameter;
import com.ppnlib.singleton.DatasourceEntry;
import java.io.File;

/**
 *
 * @author herrysuganda
 */
public class FileFolderFunction {

    public static void checkFolder(String folderName) {
//        System.out.println(DBConnection.getInstance().getFtpFilePath()+folderName);
        File f = new File(RuleNameParameter.generate_file + folderName);
        if (!f.canExecute()) {
            f.mkdir();
            System.out.println("7. sukses create : " + RuleNameParameter.generate_file + folderName);

        } else {
            System.out.println("7. cannot create folder");
        }
    }

}
