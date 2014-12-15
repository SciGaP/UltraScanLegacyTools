package org.ogce.gram.job.utils;

public class Constants {

    public static final String OUTPUT_LOCATION = "outputLocation";
    public static final String US3DB_URL = "us3dburl";
    public static final String EXPERIMENT_ID = "experimentID";
    public static final String RESOURCE = "resource";
    public static final String US3DBNAME = "us3dbName";
    public static final String STDOUT_NAME = "stdout";
    public static final String STDERR_NAME = "stderr";
    public static final String TAR_FILE_NAME = "tarfileName";

    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    public static String US3SELECTDATA = "Select * from analysis where gfacID = ?";

    public static String US3INSERTDATA = "INSERT INTO analysis (gfacID, stdout, stderr, tarfile, status, cluster, us3_db) VALUES (?,?,?,?,?,?,?) ";

    public static String US3UPDATEDATA = "UPDATE analysis set stdout =? , stderr = ?, tarfile = ?, status = ? where gfacID = ?";
}
