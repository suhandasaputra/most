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
public class DatasourceEntry {

    private String ftpFilePath;

    public static DatasourceEntry getLog() {
        return log;
    }

    public static void setLog(DatasourceEntry log) {
        DatasourceEntry.log = log;
    }

    private static DatasourceEntry log = null;
    private DataSource postgreDS = null;

    public DatasourceEntry() {

    }

    public static DatasourceEntry getInstance() {
        if (log == null) {
            log = new DatasourceEntry();
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
