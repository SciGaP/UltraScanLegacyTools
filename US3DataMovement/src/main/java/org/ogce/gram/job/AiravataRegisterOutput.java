package org.ogce.gram.job;

import org.ogce.gram.job.utils.Constants;

import org.ogce.gram.job.utils.PropertyLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.sql.*;

public class AiravataRegisterOutput {
    protected static Logger log = LoggerFactory.getLogger(AiravataRegisterOutput.class);

    public static void main(String[] args) {
        try {
            String outputlocation = PropertyLoader.getOutputLocation();
            //TO Update based on the Job
            String experimentID = PropertyLoader.getExperimentId();
            // Following two properties are in job xml on REST service Tomcat
            String resource = PropertyLoader.getResourceName();
            String us3db = PropertyLoader.getUS3LimsDBName();
            String stdOutName = PropertyLoader.getSTDOutName();
            String stdErrName = PropertyLoader.getSTDErrName();
            String tarFileName = PropertyLoader.getTarFileName();

            File locaStdOutFile = new File(outputlocation + stdOutName);
            File locaStdErrFile = new File(outputlocation + stdErrName);
            File locaOutputFile = new File(outputlocation + tarFileName);

            AiravataRegisterOutput.registerData(locaOutputFile, locaStdOutFile, locaStdErrFile, experimentID, resource, us3db);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void registerData(File outputDataDir,File stdoutPath, File stderrPath, String experimentId,String cluster,String us3db) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        PreparedStatement statement1 = null;
        ResultSet results = null;
        try {
            String path = "";
            if (outputDataDir != null && !outputDataDir.getPath().isEmpty())
                path = outputDataDir.getPath();
            FileInputStream tarData = null;
            File tarFile = null;
            if (!path.isEmpty()) {
                tarFile = new File(path);
                tarData = new FileInputStream(tarFile);
            }

            FileInputStream stdout = null;
            FileInputStream stderr = null;
            if (stdoutPath != null)
                stdout = new FileInputStream(stdoutPath);
            if (stderrPath != null)
                stderr = new FileInputStream(stderrPath);
        
            Class.forName(Constants.JDBC_DRIVER);
            connection = DriverManager.getConnection(PropertyLoader.getJDBCURL());
            statement = connection.prepareStatement(Constants.US3SELECTDATA);
            statement.setString(1, experimentId);
            results = statement.executeQuery();
            if (results.next()) {
                statement1 = connection.prepareStatement(Constants.US3UPDATEDATA);
                statement1.setBinaryStream(1, stdout);
                statement1.setBinaryStream(2, stderr);
                if (tarFile != null && tarFile.length() > 0)
                    statement1.setBinaryStream(3, tarData, tarFile.length());
                else
                    statement1.setBinaryStream(3, null);
                statement1.setString(4, "COMPLETE");
                statement1.setString(5, experimentId);
                statement1.executeUpdate();
                log.info("Update output for "+ experimentId+ " with Experiment status: COMPLETE");
                
            } else {
             statement1 = connection.prepareStatement(Constants.US3INSERTDATA);
                statement1.setString(1, experimentId);
                statement1.setBinaryStream(2, stdout);
                statement1.setBinaryStream(3, stderr);
                if (tarFile != null && tarFile.length() > 0)
                    statement1.setBinaryStream(4, tarData, tarFile.length());
                else
                    statement1.setBinaryStream(4, null);
                statement1.setString(5, "COMPLETE");
                statement1.setString(6, cluster);
                statement1.setString(7, us3db);
                statement1.executeUpdate();
                log.info("inserted output for "+ experimentId+ " with Experiment status: COMPLETE");
            }         
        } catch (Exception e) {
            throw new Exception("Erorr inserting output data to DB :" + e);
        } finally {
            try {
                if (results != null) {
                    results.close();
                }
                if (statement1 != null) {
                    statement1.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new Exception("Erorr closing connections : " + e);
            }
        }
    }
}
