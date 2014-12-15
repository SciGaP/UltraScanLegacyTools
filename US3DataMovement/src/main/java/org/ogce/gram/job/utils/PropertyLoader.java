package org.ogce.gram.job.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Properties;

public class PropertyLoader {
    public static final String DM_PROPERTIES="datamovement.properties";
    protected static Properties properties = new Properties();
    private final static Logger logger = LoggerFactory.getLogger(PropertyLoader.class);

    private static void loadProperties() throws Exception{
        URL url = getPropertyFileURL();
        try {
            properties.load(url.openStream());
            logger.info("Settings loaded from "+url.toString());
        } catch (Exception e) {
            logger.error("Error while loading properties", e);
            throw new Exception(e);
        }
    }

    public static String getSetting(String key) throws Exception{
        loadProperties();
        String rawValue = null;
        if (properties.containsKey(key)) {
            rawValue = properties.getProperty(key);
        }
        return rawValue;
    }

    protected static URL getPropertyFileURL() {
        return PropertyLoader.class.getClassLoader().getResource(DM_PROPERTIES);
    }

    public static String getJDBCURL() throws Exception{
        try {
            return getSetting(Constants.US3DB_URL);
        } catch (Exception e) {
            logger.error("Error while loading properties", e);
            throw new Exception(e);
        }
    }

    public static String getOutputLocation() throws Exception{
        try {
            return getSetting(Constants.OUTPUT_LOCATION);
        } catch (Exception e) {
            logger.error("Error while loading properties", e);
            throw new Exception(e);
        }
    }

    public static String getExperimentId() throws Exception{
        try {
            return getSetting(Constants.EXPERIMENT_ID);
        } catch (Exception e) {
            logger.error("Error while loading properties", e);
            throw new Exception(e);
        }
    }

    public static String getResourceName() throws Exception{
        try {
            return getSetting(Constants.RESOURCE);
        } catch (Exception e) {
            logger.error("Error while loading properties", e);
            throw new Exception(e);
        }
    }

    public static String getUS3LimsDBName() throws Exception{
        try {
            return getSetting(Constants.US3DBNAME);
        } catch (Exception e) {
            logger.error("Error while loading properties", e);
            throw new Exception(e);
        }
    }

    public static String getSTDOutName() throws Exception{
        try {
            return getSetting(Constants.STDOUT_NAME);
        } catch (Exception e) {
            logger.error("Error while loading properties", e);
            throw new Exception(e);
        }
    }

    public static String getSTDErrName() throws Exception{
        try {
            return getSetting(Constants.STDERR_NAME);
        } catch (Exception e) {
            logger.error("Error while loading properties", e);
            throw new Exception(e);
        }
    }

    public static String getTarFileName() throws Exception{
        try {
            return getSetting(Constants.TAR_FILE_NAME);
        } catch (Exception e) {
            logger.error("Error while loading properties", e);
            throw new Exception(e);
        }
    }
}
