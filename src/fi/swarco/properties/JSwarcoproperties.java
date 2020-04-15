package fi.swarco.properties;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import org.apache.log4j.Logger;
import static fi.swarco.CONSTANT.INT_RET_NOT_OK;
import static fi.swarco.CONSTANT.INT_RET_OK;
public class JSwarcoproperties {
    private static Logger logger = Logger.getLogger(JSwarcoproperties.class.getName());
    private String InfluxConnUrlStart;
    private String Influxdbuser;
    private String Influxpassword;
    public String getInfluxConnUrlStart() {return InfluxConnUrlStart;};
    public void   setInfluxConnUrlStart(String pInfluxConnUrlStart) {InfluxConnUrlStart=pInfluxConnUrlStart;};
    public String getInfluxdbuser() {return Influxdbuser;};
    public void   setInfluxdbuser(String pInfluxdbuser) {Influxdbuser=pInfluxdbuser;};
    public String getInfluxpassword() {return Influxpassword;};
    public void   setInfluxpassword(String pInfluxpassword) {Influxpassword=pInfluxpassword;};
    private String InfluxDbName1;
    public String getInfluxDbName1() {return InfluxDbName1;};
    public void   setInfluxDbName1(String pInfluxDbName1) {InfluxDbName1=pInfluxDbName1;};
    private String OmniaClientUrl;
    public String getOmniaClientUrl() {
        return OmniaClientUrl;
    }
    public void setOmniaClientUrl(String pOmniaClientUrl) {
        OmniaClientUrl = pOmniaClientUrl;
    }
    //OmniaClientWorkWaitSleep
    private String OmniaClientWorkWaitSleep;
    public String getOmniaClientWorkWaitSleep() {
        return OmniaClientWorkWaitSleep;
    }
    public void setOmniaClientWorkWaitSleep(String pOmniaClientWorkWaitSleep) {
        OmniaClientWorkWaitSleep = pOmniaClientWorkWaitSleep;
    }
    private String OmniaClientSleepMs;
    public String getOmniaClientSleepMs() {
        return OmniaClientSleepMs;
    }
    public void setOmniaClientSleepMs(String pOmniaClientSleepMs) {
        OmniaClientSleepMs = pOmniaClientSleepMs;
    }
    //OmniaClientDetectorDataTime
    private String OmniaClientDetectorDataTime;
    public String getOmniaClientDetectorDataTime() { return OmniaClientDetectorDataTime;}
    public void setOmniaClientDetectorDataTime(String pOmniaClientDetectorDataTime)
    {OmniaClientDetectorDataTime=pOmniaClientDetectorDataTime;}

    private String[] timeFormat = new String[16];
    public String[] getTimeFormat() {
        return timeFormat;
    }
    public void setTimeFormat(String[] pTimeFormat) {
        timeFormat = pTimeFormat;
    }
    private String SqlServerDriver;
    private String SqlServerConnUrlStart;
    private String SqlServerpassword;
    private String SqlServerdatabase;
    private String SqlServerdbuser;
    private String SqlServerConnUrlWhole;
    private String SqlServerPort;
    public String getSqlServerPort() {
        return SqlServerPort;
    }
    public void setSqlServerPort(String pSqlServerPort) {
        SqlServerPort = pSqlServerPort;
    }
    public String getSqlServerDriver() {
        return SqlServerDriver;
    }
    public void setSqlServerDriver(String pSqlServerDriver) {
        SqlServerDriver = pSqlServerDriver;
    }
    public String getSqlServerConnUrlStart() {
        return SqlServerConnUrlStart;
    }
    public void setSqlServerConnUrlStart(String pSqlServerConnUrlStart) {
        SqlServerConnUrlStart = pSqlServerConnUrlStart;
    }
    public String getSqlServerpassword() {
        return SqlServerpassword;
    }
    public void setSqlServerpassword(String pSqlServerpassword) {
        SqlServerpassword = pSqlServerpassword;
    }
    public String getSqlServerdatabase() {
        return SqlServerdatabase;
    }
    public void setSqlServerdatabase(String pSqlServerdatabase) {
        SqlServerdatabase = pSqlServerdatabase;
    }
    public String getSqlServerdbuser() {
        return SqlServerdbuser;
    }
    public void setSqlServerdbuser(String pSqlServerdbuser) {
        SqlServerdbuser = pSqlServerdbuser;
    }
    public String getSqlServerConnUrlWhole() {
        return SqlServerConnUrlWhole;
    }
    public void setSqlServerConnUrlWhole(String pSqlServerConnUrlWhole) {
        SqlServerConnUrlWhole = pSqlServerConnUrlWhole;
    }
    private String MySqlDriver;
    private String MySqlConnUrlStart;
    private String MySqlConnUrlStartDbase;
    private String MySqlpassword;
    private String MySqldatabase;
    private String MySqldbuser;
    private String MySqlConnUrlWhole;
    private String MySqlServerTimeZone;
    public String getMySqlDriver() {
        return MySqlDriver;
    }
    public void setMySqlDriver(String pMySqlDriver) {
        MySqlDriver = pMySqlDriver;
    }
    public String getMySqlConnUrlStart() {
        return MySqlConnUrlStart;
    }
    public void setMySqlConnUrlStart(String pMySqlConnUrlStart) {
        MySqlConnUrlStart = pMySqlConnUrlStart;
    }
    public String getMySqlConnUrlStartDbase() {
        return MySqlConnUrlStartDbase;
    }
    public void setMySqlConnUrlStartDbase(String pMySqlConnUrlStartDbase) {
        MySqlConnUrlStartDbase = pMySqlConnUrlStartDbase;
    }
    public String getMySqlpassword() {
        return MySqlpassword;
    }
    public void setMySqlpassword(String pMySqlpassword) {
        MySqlpassword = pMySqlpassword;
    }
    public String getMySqldatabase() {
        return MySqldatabase;
    }
    public void setMySqldatabase(String pMySqldatabase) {
        MySqldatabase = pMySqldatabase;
    }
    public String getMySqldbuser() {
        return MySqldbuser;
    }
    public void setMySqldbuser(String pMySqldbuser) {
        MySqldbuser = pMySqldbuser;
    }
    public String getMySqlConnUrlWhole() {
        return MySqlConnUrlWhole;
    }
    public void setMySqlServerTimeZone(String pMySqlServerTimeZone) {
        MySqlServerTimeZone = pMySqlServerTimeZone;
    }
    public String getMySqlServerTimeZone() {
        return MySqlServerTimeZone;
    }
    public void setMySqlConnUrlWhole(String pMySqlConnUrlWhole) {
        MySqlConnUrlWhole = pMySqlConnUrlWhole;
    }
    //
    private String FilePathString;
    public String getFilePathString() {
        return FilePathString;
    }
    public void setFilePathString(String pFilePathString) {
        FilePathString = pFilePathString;
    }
    private String FilePathStringOmniaClient;
    public String getFilePathStringOmniaClient() {
        return FilePathStringOmniaClient;
    }
    public void setFilePathStringOmniaClient(String pFilePathStringOmniaClient) {
        FilePathStringOmniaClient = pFilePathStringOmniaClient;
    }
    private String FilePathStringOmniaCloud;
    public String getFilePathStringOmniaCloud() {
        return FilePathStringOmniaCloud;
    }
    public void setFilePathStringOmniaCloud(String pFilePathStringOmniaCloud) {
        FilePathStringOmniaCloud = pFilePathStringOmniaCloud;
    }
    //FilePathStringOmniaCustomerClient
    private String FilePathStringOmniaCustomerClient;
    public String getFilePathStringOmniaCustomerClient() {
        return FilePathStringOmniaCustomerClient;
    }
    public void setFilePathStringOmniaCustomerClient(String pFilePathStringOmniaCustomerClient) {
        FilePathStringOmniaCustomerClient = pFilePathStringOmniaCustomerClient;
    }
    private String FilePathStringOmniaInflux;
    public String getFilePathStringOmniaInflux(){return FilePathStringOmniaInflux;}
    public void setFilePathStringOmniaInflux(String pFilePathStringOmniaInflux) {
        FilePathStringOmniaInflux = pFilePathStringOmniaInflux;
    }
    private  String FileNameInflux1;
    public String getFileNameInflux1(){return FileNameInflux1;}
    public void setFileNameInflux1(String pFileNameInflux1) {
        FileNameInflux1 = pFileNameInflux1;
    }


    private String Log4JPathAndFileName;
    public String getLog4JPathAndFileName() {
        return Log4JPathAndFileName;
    }
    public void setLog4JPathAndFileName(String pLog4JPathAndFileName) {
        Log4JPathAndFileName= pLog4JPathAndFileName;
    }
    private Properties prop;

    public int getLog4JProperties() {
        prop = new Properties();
        InputStream input = null;
        try {
            String filename = "log4j.properties";  // old one Swarco.
            input = JSwarcoproperties.class.getClassLoader().getResourceAsStream(filename);
            logger.info("input = " + input);
            if (input != null) {
                prop.load(input);
            } else {
                logger.info("Sorry, unable to find " + filename);
                return INT_RET_NOT_OK;
            }
            int iRet = setLog4jProps();
            if (iRet != 1) {
                logger.info("No log4J props in this environment!");
            }

            return INT_RET_OK;
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return INT_RET_OK;
    }
    private int setLog4jProps() {
        setLog4JPathAndFileName(prop.getProperty("log4j.appender.R.File"));
        return INT_RET_OK;
    }
        public int getSwarcoProperties() {
        prop = new Properties();
        InputStream input = null;
        try {
            String filename = "swarco.properties";  // old one Swarco.
            input = JSwarcoproperties.class.getClassLoader().getResourceAsStream(filename);
        //   logger.info("input = " + input);
         //  System.out.println("input = " + input);
            if (input != null) {
                prop.load(input);
            } else {
                System.out.println("Sorry, unable to find " + filename);
                logger.info("Sorry, unable to find " + filename);
                System.exit(1);
                return INT_RET_NOT_OK;
            }
            int iRet = setSqlServerProps();
            if (iRet != 1) {
                logger.info("No SqlServer props in this environment!");
            }
            iRet = setMySqlProps();
            if (iRet != 1) {
                logger.info("No MySql props in this environment!");
            }
            iRet = setInfluxProps();
            if (iRet != 1) {
                logger.info("No MySql props in this environment!");
            }
            iRet = setFileProps();
            if (iRet != 1) {
                logger.info("No flat file props in this environment!");
            }
            iRet = setTimeFormatProps();
            if (iRet != 1) {
                logger.info("No Time format props in this environment!");
            }
            iRet = setOmniaClientProps();
            if (iRet != 1) {
                logger.info("No OnmniaClient props in this environment!");
            }
            return INT_RET_OK;
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return INT_RET_OK;
    }
    private int setSqlServerProps() {
        setSqlServerDriver(prop.getProperty("SqlServerDriver"));
        setSqlServerConnUrlStart(prop.getProperty("SqlServerConnectionUrlStart"));
        setSqlServerPort(prop.getProperty("SqlServerPort"));
        setSqlServerpassword(prop.getProperty("SqlServerpassword"));
        setSqlServerdatabase(prop.getProperty("SqlServerdatabase"));
        setSqlServerdbuser(prop.getProperty("SqlServerdbuser"));
        String strHelp1 = prop.getProperty("SqlServerConnectionUrlStart") + ";";
        strHelp1 = strHelp1 + "database=" + getSqlServerdatabase() + ";";    // +";";
        // strHelp1= strHelp1 +  getSqlServerPort()+",";
        strHelp1 = strHelp1 + "user=" + getSqlServerdbuser() + ",";
        strHelp1 = strHelp1 + "password=" + getSqlServerpassword();
        setSqlServerConnUrlWhole(strHelp1);
      //  logger.info("SqlServerConnUrlStart = " + prop.getProperty("SqlServerConnectionUrlStart"));
        //logger.info("SqlServerPort = "+ prop.getProperty("SqlServerPort"));
        //logger.info("SqlServerpassword = " + prop.getProperty("SqlServerpassword"));
        //logger.info("SqlServerdatabase = " + prop.getProperty("SqlServerdatabase"));
        //logger.info("SqlServerdbuser = " + prop.getProperty("SqlServerdbuser"));
        //logger.info("getpSqlServerConnUrlWhole() = " + getSqlServerConnUrlWhole());
        return INT_RET_OK;
    }
    private int setMySqlProps() {
        setMySqlDriver(prop.getProperty("MySqlDriver"));
        setMySqlConnUrlStart(prop.getProperty("MySqlConnectionUrlStart"));
        setMySqlpassword(prop.getProperty("MySqlpassword"));
        setMySqldatabase(prop.getProperty("MySqldatabase"));
        setMySqldbuser(prop.getProperty("MySqldbuser"));
        setMySqlServerTimeZone(prop.getProperty("MySqlServerTimeZone"));
        setMySqlConnUrlStartDbase(prop.getProperty("MySqlConnectionUrlStart") + prop.getProperty("MySqldatabase"));
        String strHelp1 = prop.getProperty("MySqlConnectionUrlStart");    // +";" removed
        strHelp1 = strHelp1 + getMySqldatabase() +","; // JIs 18.6 11:44
        strHelp1 = strHelp1 + getMySqldbuser() + ",";
        strHelp1 = strHelp1 + getMySqlpassword();
        //   strHelp1 =strHelp1 + "database=" + getMySqldatabase()  +",";
        //   strHelp1 = strHelp1 + "user=" + getMySqldbuser() +",";
        //   strHelp1 = strHelp1 + "password=" + getMySqlpassword();
           setMySqlConnUrlWhole(strHelp1);
    //      logger.info("MySqlConnUrlStart = " + prop.getProperty("MySqlConnectionUrlStart"));
      //    logger.info("MySqlpassword = " + prop.getProperty("MySqlpassword"));
      //    logger.info("MySqldatabase = " + prop.getProperty("MySqldatabase"));
      //    logger.info("MySqldbuser = " + prop.getProperty("MySqldbuser"));
      //    logger.info("MySqlServerTimeZone = " + prop.getProperty("MySqlServerTimeZone"));
      //    logger.info("getpMySqlConnUrlWhole() = " + getMySqlConnUrlWhole());
        return INT_RET_OK;
    }
    private int setInfluxProps () {
        setInfluxConnUrlStart(prop.getProperty("InfluxConnectionUrlStart"));
        setInfluxdbuser(prop.getProperty("Influxdbuser"));
        setInfluxpassword(prop.getProperty("Influxpassword"));
        setInfluxDbName1(prop.getProperty("InfluxDbName1"));
      //  logger.info("getInfluxConnUrlStart() = " + getInfluxConnUrlStart());
      //  logger.info("getInfluxdbuser() = " + getInfluxdbuser());
      //  logger.info("getInfluxpassword() = " + getInfluxpassword());
      //  logger.info("getInfluxDbName1() = " + getInfluxDbName1());
        return 1;
    }
    private int setFileProps() {
        setFilePathString(prop.getProperty("FilePathString"));
       // logger.info("getFilePathString() = " + getFilePathString());
        setFilePathStringOmniaClient(prop.getProperty("FilePathStringOmniaClient"));
       // logger.info("getFilePathStringOmniaClient() = " + getFilePathStringOmniaClient());
        setFilePathStringOmniaCloud(prop.getProperty("FilePathStringOmniaCloud"));
       // logger.info("getFilePathStringOmniaCloud() = " + getFilePathStringOmniaCloud());
        setFilePathStringOmniaCustomerClient(prop.getProperty("FilePathStringOmniaCustomerClient"));
       // logger.info("getFilePathStringOmniaCustomerClient() = " + getFilePathStringOmniaCustomerClient());
        setFilePathStringOmniaInflux(prop.getProperty("FilePathStringOmniaInflux"));
        //logger.info("getFilePathStringOmniaInflux() = " + getFilePathStringOmniaInflux());
        setFileNameInflux1(prop.getProperty("FileNameInflux1"));
        //logger.info("getFileNameInflux1() = " + getFileNameInflux1());
        return INT_RET_OK;
    }
   private int setTimeFormatProps() {
        timeFormat[0] = prop.getProperty("TimeF1");
        timeFormat[1] = prop.getProperty("TimeF2");
        timeFormat[2] = prop.getProperty("TimeF3");
        timeFormat[3] = prop.getProperty("TimeF4");
        timeFormat[4] = prop.getProperty("TimeF5");
        timeFormat[5] = prop.getProperty("TimeF6");
        timeFormat[6] = prop.getProperty("TimeF7");
        timeFormat[7] = prop.getProperty("TimeF8");
        timeFormat[8] = prop.getProperty("TimeF9");
        timeFormat[9] = prop.getProperty("TimeF10");
        timeFormat[10] = prop.getProperty("TimeF11");
        timeFormat[11] = prop.getProperty("TimeF12");
        timeFormat[12] = prop.getProperty("TimeF13");
        timeFormat[13] = prop.getProperty("TimeF14");
        timeFormat[14] = prop.getProperty("TimeF15");
        timeFormat[15] = prop.getProperty("TimeF16");
        String theString = Arrays.toString(timeFormat);
    //    logger.info("TimeArray[] theString = " + theString);
        return INT_RET_OK;
    }
    private int setOmniaClientProps(){
        setOmniaClientUrl(prop.getProperty("OmniaClientUrl")) ;
    //    logger.info("getOmniaClientUrl()= " + getOmniaClientUrl());
        setOmniaClientSleepMs(prop.getProperty("OmniaClientSleepMs"));
    //    logger.info("getOmniaClientSleepMs() = " + getOmniaClientSleepMs());
        setOmniaClientWorkWaitSleep(prop.getProperty("OmniaClientWorkWaitSleep"));
    //    logger.info("getOmniaClientWorkWaitSleep() = " + getOmniaClientWorkWaitSleep());
        setOmniaClientDetectorDataTime(prop.getProperty("OmniaClientDetectorDataTime"));
       // logger.info("getOmniaClientDetectorDataTime() = " + getOmniaClientDetectorDataTime());
        return INT_RET_OK;
    }
}

