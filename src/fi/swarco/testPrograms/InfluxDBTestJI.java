package fi.swarco.testPrograms;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import org.apache.log4j.Logger;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.InfluxDB;

public class InfluxDBTestJI {
    private String InfluxConnUrlStart;
    private String Influxdbuser;
    private String Influxpassword;
    private InfluxDB curInfluxDB;
    public void   setInfluxConnUrlStart(String pInfluxConnUrlStart) {InfluxConnUrlStart=pInfluxConnUrlStart;};
    public void   setInfluxdbuser(String pInfluxdbuser) {Influxdbuser=pInfluxdbuser;};
    public void   setInfluxpassword(String pInfluxpassword) {Influxpassword=pInfluxpassword;};
    public InfluxDB  getInfluxDb()  {return curInfluxDB;};
    static Logger logger = Logger.getLogger(InfluxDBTestJI.class.getName());
    private InfluxDB influxDB;
    public void setUp1() throws InterruptedException, IOException {
        setInfluxConnUrlStart("http://192.168.111.121:8086");
       // setInfluxConnUrlStart("http://localhost:8086");
        // setInfluxConnUrlStart("http://194.100.23.105:8086");
        OkHttpClient.Builder jisClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .retryOnConnectionFailure(true);
        setInfluxdbuser("root");
        setInfluxpassword("root");
        logger.info("SETUP 1 InfluxConnUrlStart =" + InfluxConnUrlStart);
        logger.info("Influxdbuser = " + Influxdbuser);
        logger.info("Influxpassword = " + Influxpassword);
        String strHelp5 = "OkHttpClient.Builder jisClient = new OkHttpClient.Builder()";
        strHelp5 = strHelp5 +".connectTimeout(5, TimeUnit.MINUTES)";
        strHelp5 = strHelp5 +".readTimeout(5, TimeUnit.MINUTES)";
        strHelp5 = strHelp5 +".writeTimeout(5, TimeUnit.MINUTES)";
        strHelp5 = strHelp5 +".retryOnConnectionFailure(true);";
        logger.info("*********************************************************************************");
        logger.info("strHelp5 = " + strHelp5);

        //this.influxDB = InfluxDBFactory.connect("http://localhost:8086","root", "root");
       this.influxDB = InfluxDBFactory.connect(InfluxConnUrlStart,Influxdbuser,Influxpassword,jisClient);
     //   this.influxDB = InfluxDBFactory.connect("http://194.100.23.105:8086","root", "root");
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
                // NOOP intentional
                e.printStackTrace();
            }
            Thread.sleep(100L);
        } while (!influxDBstarted);
        curInfluxDB=this.influxDB;    // set here for another users JI Rethink 2.7 2016
        logger.info("##################################################################################");
        logger.info("# SETUP 1  Connected to InfluxDB Version: " + this.influxDB.version() + " #");
        logger.info("##################################################################################");
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
                // NOOP intentional
                e.printStackTrace();
            }
            Thread.sleep(100L);
        } while (!influxDBstarted);
        logger.info("##################################################################################");
        logger.info("#  Connected to InfluxDB Version: " + this.influxDB.version() + " #");
        logger.info("##################################################################################");
    }
    /**
     * Test for a ping.
     */
    public void testPing() {
        Pong result = this.influxDB.ping();
        logger.info ("Pong result =" + result);
    }
    /**
     * Test that version works.
     */
    public void testVersion() {
        String version = this.influxDB.version();
        logger.info("version =" + version);
    }
    /**
     * Simple Test for a query.
     */
    public void testQuery() {
        this.influxDB.query(new Query("CREATE DATABASE mydb2222", "mydb2222"));
        //this.influxDB.query(new Query("DROP DATABASE mydb2", "mydb2222"));
    }
    public void jatriWrite() {
        String dbName="jatri1";
        logger.debug("JatriWrite dbName ="+ dbName);
   //     this.influxDB.createDatabase(dbName);
        BatchPoints batchPoints = BatchPoints.database(dbName).tag("device", "D1235").retentionPolicy("autogenselect * from ").build();
        Point point1 = Point.measurement("pumb")
                .tag("devpoint", "a1z1")
                .addField("voltage", 3L)
                .addField("uusertime", 9L)
                .build();
//		Point point2 = Point.measurement("disk").tag("atag", "test").addField("used", 80L).addField("free", 1L).build();
        batchPoints.point(point1);
//		batchPoints.point(point2);
        this.influxDB.write(batchPoints);

//		Query query = new Query("SELECT * FROM  GROUP BY *", dbName);
//		QueryResult result = this.influxDB.query(query);
    }

    /**
     * Test writing to the database using string protocol.
     */
    public void testWriteStringData() {
        String dbName = "write_unittest_" + System.currentTimeMillis();
        dbName="jatri1";
     //   this.influxDB.createDatabase(dbName);
        this.influxDB.write(dbName, "autogen", InfluxDB.ConsistencyLevel.ONE, "cpu,atag=test idle=90,usertime=9,system=1");
        Query query = new Query("SELECT * FROM cpu GROUP BY *", dbName);
        QueryResult result = this.influxDB.query(query);
    }
    public void testWriteMultipleStringData() {
        String dbName = "write_unittest_" + System.currentTimeMillis();
        dbName="jatri1";
       // this.influxDB.createDatabase(dbName);
        this.influxDB.write(dbName, "autogen", InfluxDB.ConsistencyLevel.ONE, "cpu,atag=test1 idle=100,usertime=10,system=1\ncpu,atag=test2 idle=200,usertime=20,system=2\ncpu,atag=test3 idle=300,usertime=30,system=3");
        Query query = new Query("SELECT * FROM cpu GROUP BY *", dbName);
        QueryResult result = this.influxDB.query(query);
      //  this.influxDB.deleteDatabase(dbName);
    }
    public void jisWriteMultipleStringDataLines() {
        String dbName = "write_unittest_" + System.currentTimeMillis();
        dbName="jatri1";
        //this.influxDB.createDatabase(dbName);
        this.influxDB.write(dbName, "autogen", InfluxDB.ConsistencyLevel.ONE, Arrays.asList(
                "E1235,devpoint=p1z1 voltage=303,uusertime=269353117i",
                "E1235,devpoint=p1z2 voltage=303,uusertime=269353117i",
                "E1235,devpoint=ber1 voltage=303,uusertime=269353117i"
        ));
    }
    public void WriteToSwarcoExampleTimeSerie() {
        String dbName;
        String  strHelp1,strHelp2,strHelp3,strHelp4,strHelp5;
      //  dbName="jatri1";
        dbName="swarco_test";
        //strHelp1 ="Example1,omniacode=2,intersectionid=5555555,controllerid=66666666,detectorid=12345,detectorexternalcode='DET1341'";
        strHelp1 ="Example2,omniacode=2,intersectionid=5555555,controllerid=66666666,detectorid=12345,detectorexternalcode='DET1341' ";
        strHelp1 = strHelp1 + " vehiclecount=567,meanvehiclespeed=36.89,occupancyprocent=11.33,accurancy=99.1,measurementtime=1489096808000i";
        strHelp2 ="Example2,omniacode=2,intersectionid=5555555,controllerid=66666666,detectorid=12345,detectorexternalcode='DET1341' ";
        strHelp2 = strHelp2 + " vehiclecount=777,meanvehiclespeed=37.89,occupancyprocent=11.33,accurancy=99.1,measurementtime=1489096844000i";
        strHelp3 ="Example2,omniacode=2,intersectionid=5555555,controllerid=66666666,detectorid=12345,detectorexternalcode='DET1341' ";
        strHelp3 = strHelp3 + " vehiclecount=888,meanvehiclespeed=36.89,occupancyprocent=11.33,accurancy=99.1,measurementtime=1489096868000i";
        strHelp4 ="Example2,omniacode=2,intersectionid=5555555,controllerid=66666666,detectorid=12345,detectorexternalcode='DET1341' ";
        strHelp4 = strHelp4 + " vehiclecount=999,meanvehiclespeed=36.89,occupancyprocent=11.33,accurancy=99.1,measurementtime=1489096904000i";
        strHelp5 ="Example2,omniacode=2,intersectionid=5555555,controllerid=66666666,detectorid=123457,detectorexternalcode='DET13417' ";
        strHelp5 = strHelp5 + " vehiclecount=567,meanvehiclespeed=38.89,occupancyprocent=15.33,accurancy=98.1,measurementtime=1489096808000i";


        this.influxDB.write(dbName, "autogen", InfluxDB.ConsistencyLevel.ONE, Arrays.asList(
                strHelp4));
    }


    public void WriteSerieName(String pstrName) {
        String dbName;
        String  strHelp1,strHelp2,strHelp3,strHelp4,strHelp5;
        //  dbName="jatri1";
        dbName="jatri1";
        //strHelp1 ="Example1,omniacode=2,intersectionid=5555555,controllerid=66666666,detectorid=12345,detectorexternalcode='DET1341'";
        strHelp1 ="SerieName,omniacode=2,intersectionid=5555555,controllerid=66666666,detectorid=12345,";
        strHelp1 = strHelp1 + "seriename=" +pstrName + " value=1"   ;
        logger.info("strHelp1 = " + strHelp1);
        this.influxDB.write(dbName, "autogen", InfluxDB.ConsistencyLevel.ONE, Arrays.asList(strHelp1));
    }


    public QueryResult readInfluxMeasurementDataLine1(String pdbName, String pDevpoint) {
        String statement ="select devpoint, voltage from D3007 where devpoint='"+ pDevpoint +"' limit 1  ";
        logger.info("statement = "+ statement);
        Query query = new Query(statement, pdbName);
        QueryResult result = this.influxDB.query(query,TimeUnit.MILLISECONDS);
        //  logger.info("result =" + result.toString());
        result.getResults();
        return result;
    }
    public int IsSerieNameUsed(String pSerieName) {
        String dbName ="jatri1";
        String statement ="select * from SerieName where seriename='"+ pSerieName +"'";
        logger.info("********************************************************************************************");
        logger.info("statement = "+ statement);
        logger.info("********************************************************************************************");
        Query query = new Query(statement, dbName);
        //QueryResult result// = this.influxDB.query(query);
        QueryResult result    =   this.influxDB.query(query, TimeUnit.MILLISECONDS);
        logger.info("result =" + result.toString());
        return 1;
        //result.getResults();
        //return result;
    }






    public void WriteToSwarcoExampleTimeSerie3() {
        String dbName;
        String  strHelp1,strHelp2,strHelp3,strHelp4,strHelp5;
        //  dbName="jatri1";
        dbName="swarco_test";
        //strHelp1 ="Example1,omniacode=2,intersectionid=5555555,controllerid=66666666,detectorid=12345,detectorexternalcode='DET1341'";
        strHelp1 ="Example3,omniacode=2,intersectionid=5555555,controllerid=66666666,detectorid=12345,detectorexternalcode='DET1341' ";
        strHelp1 = strHelp1 + " vehiclecount=567,meanvehiclespeed=36.89,occupancyprocent=11.33,accurancy=99.1 1489096808000";
        strHelp2 ="Example3,omniacode=2,intersectionid=5555555,controllerid=66666666,detectorid=12345,detectorexternalcode='DET1341' ";
        strHelp2 = strHelp2 + " vehiclecount=777,meanvehiclespeed=37.89,occupancyprocent=11.33,accurancy=99.1 1489096844000";
        strHelp3 ="Example3,omniacode=2,intersectionid=5555555,controllerid=66666666,detectorid=12345,detectorexternalcode='DET1341' ";
        strHelp3 = strHelp3 + " vehiclecount=888,meanvehiclespeed=36.89,occupancyprocent=11.33,accurancy=99.1 1489096868000";
        strHelp4 ="Example3,omniacode=2,intersectionid=5555555,controllerid=66666666,detectorid=12345,detectorexternalcode='DET1341' ";
        strHelp4 = strHelp4 + " vehiclecount=999,meanvehiclespeed=36.89,occupancyprocent=11.33,accurancy=99.1 1489096904000";
        strHelp5 ="Example3,omniacode=2,intersectionid=5555555,controllerid=66666666,detectorid=123457,detectorexternalcode='DET13417' ";
        strHelp5 = strHelp5 + " vehiclecount=567,meanvehiclespeed=38.89,occupancyprocent=15.33,accurancy=98.1 1489096808000";
        this.influxDB.write(dbName, "autogen", InfluxDB.ConsistencyLevel.ONE, Arrays.asList(strHelp1,strHelp2,strHelp3,strHelp4,strHelp5));
        //this.influxDB.write(dbName, "autogen", InfluxDB.ConsistencyLevel.ONE, Arrays.asList(strHelp2));
    }
    public int jisWriteSerialLine(String device, String time, String mpoint, String mValue ) {
        String dbName = "jatri1";
        String sLine = "D" + device +",devpoint="+ mpoint  + " voltage=" + mValue + ",uusertime=" + time +"i";
        logger.info("sLine = "+ sLine);
       // this.influxDB.createDatabase(dbName);
        this.influxDB.write(dbName, "autogen", InfluxDB.ConsistencyLevel.ONE, sLine);
        // RETHINK
        logger.info("InfluxDB.ConsistencyLevel.ONE = " + InfluxDB.ConsistencyLevel.ONE);
       // this.influxDB.write2(dbName, "default", InfluxDB.ConsistencyLevel.ONE, Arrays.asList(sLine));
        return 1;
        //"E1235,devpoint=p1z2 voltage=303,uusertime=269353117i",
    }
    public void testWriteMultipleStringDataLines() {
        String dbName = "write_unittest_" + System.currentTimeMillis();
        dbName="jatri1";
      //  this.influxDB.createDatabase(dbName);
        this.influxDB.write(dbName, "autogen", InfluxDB.ConsistencyLevel.ONE, Arrays.asList(
                "cpu,atag=test1 idle=100,usertime=10,system=1",
                "cpu,atag=test2 idle=200,usertime=20,system=2",
                "cpu,atag=test3 idle=300,usertime=30,system=3"
        ));
        Query query = new Query("SELECT * FROM cpu GROUP BY *", dbName);
        QueryResult result = this.influxDB.query(query);
   //     this.influxDB.deleteDatabase(dbName);
    }
    public void testCreateNumericNamedDatabase() {
        String numericDbName = "123";
     //   this.influxDB.createDatabase(numericDbName);
       // List<String> result = this.influxDB.describeDatabases();
       // logger.info("result = "+ result );
       // this.influxDB.deleteDatabase(numericDbName);
    //    result = this.influxDB.describeDatabases();
    //    logger.info("result = "+ result );
    }
    public void testIsBatchEnabled() {
        this.influxDB.enableBatch(1, 1, TimeUnit.SECONDS);
    }
}



