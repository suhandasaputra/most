/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ppnlib.singleton;

import java.io.File;
import javax.sql.DataSource;

/**
 *
 * @author herry.suganda
 */
public class DatasourceEntry1 {

    private String ftpFilePath;

    public static DatasourceEntry1 getLog() {
        return log;
    }

    public static void setLog(DatasourceEntry1 log) {
        DatasourceEntry1.log = log;
    }

    private static DatasourceEntry1 log = null;
    private DataSource postgreDS = null;

    public DatasourceEntry1() {

    }

    public static DatasourceEntry1 getInstance() {
        if (log == null) {
            log = new DatasourceEntry1();
        }

        return log;
    }

    /**
     * @return the postgreDS
     */
    public DataSource getPostgreDS() {
        return postgreDS;
    }

    /**
     * @param postgreDS the postgreDS to set
     */
    public void setPostgreDS(DataSource postgreDS) {
        this.postgreDS = postgreDS;
    }

    /**
     * @return the ftpFilePath
     */
    public String getFtpFilePath() {
        return ftpFilePath;
    }

    private void checkRootFolder() {
        File f = new File(ftpFilePath);
        if (!f.canExecute()) {
            f.mkdir();
        }
    }

}
