package fi.swarco.influxoperations;

import okhttp3.OkHttpClient;
import org.apache.log4j.Logger;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static fi.swarco.CONSTANT.INT_RET_OK;

public class InfluxDBOwn {
    private String InfluxConnUrlStart;
    private String Influxdbuser;
    private String Influxpassword;
    private InfluxDB curInfluxDB;
    public void   setInfluxConnUrlStart(String pInfluxConnUrlStart) {InfluxConnUrlStart=pInfluxConnUrlStart;};
    public void   setInfluxdbuser(String pInfluxdbuser) {Influxdbuser=pInfluxdbuser;};
    public void   setInfluxpassword(String pInfluxpassword) {Influxpassword=pInfluxpassword;};
    public InfluxDB  getInfluxDb()  {return curInfluxDB;};
    static Logger logger = Logger.getLogger(InfluxDBOwn.class.getName());
    private InfluxDB influxDB;
    public void setUp1() throws InterruptedException, IOException {
        // logger.info("InfluxConnUrlStart =" + InfluxConnUrlStart);
        // logger.info("Influxdbuser = " + Influxdbuser);
        //  logger.info("Influxpassword = " + Influxpassword);
        //this.influxDB = InfluxDBFactory.connect("http://localhost:8086","root", "root");
        OkHttpClient.Builder jisClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .retryOnConnectionFailure(true);
          String strHelp5 = "OkHttpClient.Builder jisClient = new OkHttpClient.Builder()";
                 strHelp5 = strHelp5 +".connectTimeout(5, TimeUnit.MINUTES)";
                 strHelp5 = strHelp5 +".readTimeout(5, TimeUnit.MINUTES)";
                 strHelp5 = strHelp5 +".writeTimeout(5, TimeUnit.MINUTES)";
                 strHelp5 = strHelp5 +".retryOnConnectionFailure(true);";
                 logger.info("*********************************************************************************");
                 logger.info("strHelp5 = " + strHelp5);
       // InfluxDB influxDB = InfluxDBFactory.connect("http://localhost:8086", client);
        this.influxDB = InfluxDBFactory.connect(InfluxConnUrlStart,Influxdbuser,Influxpassword,jisClient);
        boolean influxDBstarted = false;
        do {
            Pong response;
            try {
                response = this.influxDB.ping();
                System.out.println(response);
                if (!response.getVersion().equalsIgnoreCase("unknown")) {
                    influxDBstarted = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Thread.sleep(100L);
        } while (!influxDBstarted);
        curInfluxDB=this.influxDB;
        //	logger.info("#  Connected to InfluxDB Version: " + this.influxDB.version() + " #");
    }
    public void setUp() throws InterruptedException, IOException {
        String strConnect;
        logger.info("jjjjj jatri jatri");
        strConnect ="http://localhost:8086";
        strConnect = strConnect +" " +"root" + " " + "root";
        logger.info("connecttionstring = " +  strConnect);
        //this.influxDB = InfluxDBFactory.connect("http://" + TestUtils.getInfluxIP() + ":8086", "root", "root");
        this.influxDB = InfluxDBFactory.connect("http://localhost:8086","root", "root");
        boolean influxDBstarted = false;
        do {
            Pong response;
            try {
                response = this.influxDB.ping();
                System.out.println(response);
                if (!response.getVersion().equalsIgnoreCase("unknown")) {
                    influxDBstarted = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Thread.sleep(100L);
        } while (!influxDBstarted);
        logger.info("#  Connected to InfluxDB Version: " + this.influxDB.version() + " #");
    }
    public void testPing() {
        Pong result = this.influxDB.ping();
        logger.info ("Pong result =" + result);
    }
    public void testVersion() {
        String version = this.influxDB.version();
        logger.info("version =" + version);
    }
    public void testQuery() {
        this.influxDB.query(new Query("CREATE DATABASE mydb2222", "mydb2222"));
        this.influxDB.query(new Query("DROP DATABASE mydb2", "mydb2222"));
    }
    //1482321500000000000
    public QueryResult readInfluxMeasurementDataLineExcact(String pdbName, String pTime) {
        String statement ="select devpoint, voltage from D3007 where time = " + pTime + " devpoint='Testi' limit 1 ";
        logger.info("statement = "+ statement);
        Query query = new Query(statement, pdbName);
        QueryResult result = this.influxDB.query(query);
        //    logger.info("result =" + result.toString());
        result.getResults();
        return result;
    }
    //********************************************************************************
    public QueryResult readInfluxSeriesDataLine(String pdbName,String pSerieName) {
         String statement = "select SerieName,ControllerId,DetectorId,IntersectionId,OmniaCode,Value  FROM SerieName where SerieName=" + pSerieName;
        logger.info("statement = "+ statement);
        Query query = new Query(statement, pdbName);
        QueryResult result = this.influxDB.query(query,TimeUnit.MILLISECONDS);
        //  logger.info("result =" + result.toString());
        result.getResults();
        return result;
    }
    //********************************************************************************************'**
    public QueryResult readInfluxMeasurementDataLine(String pdbName,
                                                     String PDevice_Id,
                                                     String pDevpoint) {
        String statement ="select devpoint, voltage from D3007 where devpoint='"+ pDevpoint +"' limit 1  ";
        logger.info("statement = "+ statement);
        Query query = new Query(statement, pdbName);
        QueryResult result = this.influxDB.query(query,TimeUnit.MILLISECONDS);
        //  logger.info("result =" + result.toString());
        result.getResults();
        return result;
    }
    public QueryResult readInfluxMeasurementDataLineVT2(String pdbName,
                                                        String pDevice_Id,
                                                        String pDevpoint) {
        logger.info("pdbName = " + pdbName);
        logger.info("pDevice_Id = " + pDevice_Id);
        logger.info("pDevpoint = " + pDevpoint);
        String statement = "select device_id, value from "  + pDevpoint ;
        // statement = statement + " where device_id='" + pDevice_Id +"' limit 1";
        //       device_id =~ /e1:b7:f:cb:b:d/
        statement = statement + " where device_id =~ /" + pDevice_Id +"/ limit 1";
        //		"select devpoint, voltage, time  from D3007 where device_id='"+ pDeviceId +"' limit 1  ";
        logger.info("statement = "+ statement);
        Query query = new Query(statement, pdbName);
        QueryResult result = this.influxDB.query(query,TimeUnit.MILLISECONDS);
        //  logger.info("result =" + result.toString());
        result.getResults();
        return result;
    }
    public QueryResult readMeasurementDataLineVT2(String pdbName,
                                                  String pDevice_Id,
                                                  String pDevpoint) {
        logger.info("pdbName = " + pdbName);
        logger.info("pDevice_Id = " + pDevice_Id);
        logger.info("pDevpoint = " + pDevpoint);
        String statement = "select time,device_id,device_name,seconds,unit,alue from "  + pDevpoint ;
        statement = statement + " where device_id =~ /" + pDevice_Id +"/ limit 1000";    // RETHINK JJJJJJJ
        logger.info("statement = "+ statement);
        Query query = new Query(statement, pdbName);
        QueryResult result = this.influxDB.query(query,TimeUnit.MILLISECONDS);
        logger.info("result =" + result.toString());
        result.getResults();
        return result;
    }
    public QueryResult MeasurementDataLineVT2Nlines(
            String pdbName,
            String pDevice_Id,
            String pDevpoint,
            String pCurTime,
            int pNumberOflines) {
        logger.info("********************pdbName = " + pdbName);
        logger.info("pDevice_Id = " + pDevice_Id);
        logger.info("pDevpoint = " + pDevpoint);
        logger.info("pNumberOflines = " + pNumberOflines);
        String statement = "select time,device_id,device_name,seconds,unit,alue from "  + pDevpoint ;
        statement = statement + " where device_id =~ /" + pDevice_Id +"/ and time > "+ pCurTime + " limit " + Integer.toString(pNumberOflines);
        logger.info("statement = "+ statement);
        Query query = new Query(statement, pdbName);
        QueryResult result = this.influxDB.query(query,TimeUnit.MILLISECONDS);
        logger.info("********************result =" + result.toString());
        result.getResults();
        return result;
    }
    public QueryResult readInfluxMeasurementDataLineVT2Nlines(
            String pdbName,
            String pDevice_Id,
            String pDevpoint,
            String pCurTime,
            int pNumberOflines) {
        logger.info("pdbName = " + pdbName);
        logger.info("pDevice_Id = " + pDevice_Id);
        logger.info("pDevpoint = " + pDevpoint);
        logger.info("pNumberOflines = " + pNumberOflines);
        String statement = "select device_id, value from "  + pDevpoint ;
        // statement = statement + " where device_id='" + pDevice_Id +"' limit 1";
        //       device_id =~ /e1:b7:f:cb:b:d/
        statement = statement + " where device_id =~ /" + pDevice_Id +"/ and time > "+ pCurTime + " limit " + Integer.toString(pNumberOflines);
        //		"select devpoint, voltage, time  from D3007 where device_id='"+ pDeviceId +"' limit 1  ";
        logger.info("statement = "+ statement);
        Query query = new Query(statement, pdbName);
        QueryResult result = this.influxDB.query(query,TimeUnit.MILLISECONDS);
        //  logger.info("result =" + result.toString());
        result.getResults();
        return result;
    }
    //*********************************************************************************************
    public QueryResult readInfluxMeasurementDataLine1(String pdbName, String pDevpoint) {
        String statement ="select devpoint, voltage from D3007 where devpoint='"+ pDevpoint +"' limit 1  ";
        logger.info("statement = "+ statement);
        Query query = new Query(statement, pdbName);
        QueryResult result = this.influxDB.query(query,TimeUnit.MILLISECONDS);
        //  logger.info("result =" + result.toString());
        result.getResults();
        return result;
    }
    public QueryResult readInfluxMeasurementDataLines10(String pdbName,String pTime,String pDevpoint) {
        String statement ="select devpoint, voltage from D3007 where devpoint='"+ pDevpoint +"' and time >" + pTime +" limit 1000"
                + " ";
        //logger.info("********************************************************************************************");
        //logger.info("statement = "+ statement);
        //logger.info("********************************************************************************************");
        Query query = new Query(statement, pdbName);
        //QueryResult result// = this.influxDB.query(query);
        QueryResult result    =   this.influxDB.query(query, TimeUnit.MILLISECONDS);
        //logger.info("result =" + result.toString());
        result.getResults();
        return result;
    }
    public QueryResult readInfluxMeasurementDataLines100(String pdbName,String pTime,String pDevpoint) {
        String statement ="select devpoint, voltage from D3007 where devpoint='"+ pDevpoint +"' and time >" + pTime +" limit 100"
                + " ";
        logger.info("********************************************************************************************");
        logger.info("statement = "+ statement);
        logger.info("********************************************************************************************");
        Query query = new Query(statement, pdbName);
        QueryResult result    =   this.influxDB.query(query, TimeUnit.MILLISECONDS);
        result.getResults();
        return result;
    }
    public int WriteSingleSerialLine(String device, String time, String mpoint, String mValue ) {
        String dbName = "jatri8";  // old was jatri5
        String sLine = "YYYY" + device +",devpoint="+ mpoint +   " voltage=" + mValue +" " + time;
        logger.info("sLine = "+ sLine);
        this.influxDB.write(dbName, "autogen", InfluxDB.ConsistencyLevel.ONE, Arrays.asList(sLine));
        return 1;
    }
    public int WriteSingleSerialLine2(String pDataBase, String pLine) {
        logger.info("pLine = "+ pLine);
        this.influxDB.write(pDataBase, "autogen", InfluxDB.ConsistencyLevel.ONE, Arrays.asList(pLine));
        return INT_RET_OK;   // RETHINK retcode Try/catch someting
    }
    //********************************************************************************************************************
    public void testWriteStringData() {
        String dbName = "write_unittest_" + System.currentTimeMillis();
        dbName="jatri2";
        this.influxDB.write(dbName, "autogen", InfluxDB.ConsistencyLevel.ONE, "cpu,atag=test idle=90,usertime=9,system=1");
        Query query = new Query("SELECT * FROM cpu GROUP BY *", dbName);
        QueryResult result = this.influxDB.query(query);
    }
    public void testWriteMultipleStringData() {
        String dbName = "write_unittest_" + System.currentTimeMillis();
        dbName="jatri5";
        this.influxDB.write(dbName, "autogen", InfluxDB.ConsistencyLevel.ONE, "cpu,atag=test1 idle=100,usertime=10,system=1\ncpu,atag=test2 idle=200,usertime=20,system=2\ncpu,atag=test3 idle=300,usertime=30,system=3");
        Query query = new Query("SELECT * FROM cpu GROUP BY *", dbName);
        QueryResult result = this.influxDB.query(query);
    }
    public void jisWriteMultipleStringDataLines() {
        String dbName="jatri5";
        logger.info("dbName = "  + dbName);
        System.out.println("dbName = "  + dbName);
        this.influxDB.write(dbName, "autogen", InfluxDB.ConsistencyLevel.ONE, Arrays.asList(
                "E12356,devpoint=p1z1 voltage=303 143958793099998888",
                "E12356,devpoint=p1z2 voltage=304 143958792099998888",
                "E12356,devpoint=ber1 voltage=305 1439587920999988888"
        ));
    }
    public int WriteSwarcoLineFromString(String pDbName, String pLine) {
     //     logger.info("pLine = "+ pLine);
 //       logger.info("InfluxDB.ConsistencyLevel.ONE = " + InfluxDB.ConsistencyLevel.ONE);
        this.influxDB.write(pDbName, "autogen", InfluxDB.ConsistencyLevel.ONE, Arrays.asList(pLine));
        return INT_RET_OK;
    }
     public int WriteListOfSwarcosLineFromString(String pDbName, List<String> pLines) throws InterruptedException, SocketTimeoutException {
         this.influxDB.write(pDbName, "autogen", InfluxDB.ConsistencyLevel.ONE, pLines);
         //this.influxDB.write(pDbName, "autogen", InfluxDB.ConsistencyLevel.ONE, Arrays.asList(pLine));
         return INT_RET_OK;
     }
    public int jisWriteSerialLine(String device, String time, String mpoint, String mValue ) {
        String dbName = "jatri5";
        String sLine = "D" + device +",devpoint="+ mpoint +   " voltage=" + mValue +" " + time;
        logger.info("sLine = "+ sLine);
        logger.info("InfluxDB.ConsistencyLevel.ONE = " + InfluxDB.ConsistencyLevel.ONE);
        this.influxDB.write(dbName, "autogen", InfluxDB.ConsistencyLevel.ONE, Arrays.asList(sLine));
        return 1;
    }
    public int WriteSerialLine(String dbName, String device, String time, String mpoint, String mValue ) {
        String sLine = "J" + device +",devpoint="+ mpoint +   " value=" + mValue +" " + time;
        logger.info("sLine = "+ sLine);
        logger.info("InfluxDB.ConsistencyLevel.ONE = " + InfluxDB.ConsistencyLevel.ONE);
        this.influxDB.write(dbName, "autogen", InfluxDB.ConsistencyLevel.ONE, Arrays.asList(sLine));
        return 1;
    }
    public int WriteBothSerialLines(String device, String time, String mpoint, String mValue, String formulatedValue ) {
        String dbName = "jatri9";   // old was jatri5  // old was jatri 7, jatri8 22.10 2017
        String sLine = "D" + device +",devpoint="+ mpoint +   " voltage=" + mValue +" " + time;
        String fLine = "H" + device +",devpoint="+ mpoint +   " voltage=" + formulatedValue +" " + time;
        this.influxDB.write(dbName, "autogen", InfluxDB.ConsistencyLevel.ONE, Arrays.asList(sLine,fLine));
        return 1;
    }
    public void testWriteMultipleStringDataLines() {
        String dbName = "write_unittest_" + System.currentTimeMillis();
        dbName="jatri4";
        this.influxDB.write(dbName, "autogen", InfluxDB.ConsistencyLevel.ONE, Arrays.asList(
                "cpu,atag=test1 idle=100,usertime=10,system=1",
                "cpu,atag=test2 idle=200,usertime=20,system=2",
                "cpu,atag=test3 idle=300,usertime=30,system=3"
        ));
        Query query = new Query("SELECT * FROM cpu GROUP BY *", dbName);
        QueryResult result = this.influxDB.query(query);
    }
    public void testIsBatchEnabled() {
        this.influxDB.enableBatch(1, 1, TimeUnit.SECONDS);
    }
}
