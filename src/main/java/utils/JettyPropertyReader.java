package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.Properties;

/**
 * Created by redin on 5/3/17.
 */
public class JettyPropertyReader {

    private int port;
    private int retainsDays;
    private String logPath;

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final String PROP_FILE_NAME="jetty.properties";

    public JettyPropertyReader() {
        try{
            readProperties();
        }
        catch (Exception e){
            log.error("fail to read '" + PROP_FILE_NAME + "' file", e);
        }
    }


    public void readProperties() throws Exception{

        Properties properties = new Properties();

        try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROP_FILE_NAME)){
            properties.load(inputStream);
            port = Integer.valueOf(properties.getProperty("port"));
            logPath = properties.getProperty("log_path");

            retainsDays = Integer.valueOf(properties.getProperty("retain_days"));
        }
    }

    public int getPort() {
        return port;
    }

    public int getRetainsDays() {
        return retainsDays;
    }

    public String getLogPath() {
        return logPath;
    }
}
