package fi.swarco.dataHandling.omniaClientDataHandling;
import static fi.swarco.CONSTANT.*;
import static fi.swarco.SwarcoEnumerations.ConnectionType.INFLUX_LOCAL;
import static fi.swarco.SwarcoEnumerations.ConnectionType.SQLSERVER_LOCAL_JOMNIATEST;
import fi.swarco.dataHandling.queriesSql.sqlServer.GetOldestUnusedTimeStampFromMeasurements;
import fi.swarco.dataHandling.queriesSql.sqlServer.GetTimeSerieSqlServerData;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.connections.SwarcoConnections;
import fi.swarco.properties.JSwarcoproperties;
import fi.swarco.influxoperations.InfluxDBOwn;
import fi.swarco.dataHandling.pojos.influx.InfluxSqlToTimeSerie;
import org.influxdb.InfluxDB;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
public class TimeSeriesClientDataLevel {
    public List<InfluxSqlToTimeSerie> getMeasurementUnits() {
        return MeasurementUnits;
    }
    public void setMeasurementUnits(List<InfluxSqlToTimeSerie> pMeasurementUnits) {
        this.MeasurementUnits = pMeasurementUnits;
    }
    private static Logger logger = Logger.getLogger(TimeSeriesClientDataLevel.class.getName());
    static Connection gSqlCon;
    private InfluxDB curInfluxDB;
    private SwarcoEnumerations.ConnectionType SqlConnectionType=SwarcoEnumerations.ConnectionType.NOT_DEFINED;
    List<InfluxSqlToTimeSerie> MeasurementUnits = Collections.synchronizedList(new LinkedList<InfluxSqlToTimeSerie>());  // ????
    InfluxDBOwn ts = new InfluxDBOwn();
    int iRet=0;
    public  int MakeConnection(SwarcoEnumerations.ConnectionType pSqlCon1,SwarcoEnumerations.ConnectionType pSqlCon2) {
        JSwarcoproperties swarcop = new JSwarcoproperties();
        SwarcoConnections vg = new SwarcoConnections();
        try {
            iRet = swarcop.getSwarcoProperties();
            if (iRet != 1) {
                logger.info ("Ei saatu properteja");
                return iRet;
            }
            if ((pSqlCon1.equals(INFLUX_LOCAL) || (pSqlCon2.equals(INFLUX_LOCAL)))) {
                ts.setInfluxConnUrlStart(swarcop.getInfluxConnUrlStart());
                ts.setInfluxdbuser(swarcop.getInfluxdbuser());
                ts.setInfluxpassword(swarcop.getInfluxdbuser());
                ts.setUp1();
              // exit program no return here RETHINKshow series

            }
            if ((pSqlCon1.equals(SQLSERVER_LOCAL_JOMNIATEST) || (pSqlCon2.equals(SQLSERVER_LOCAL_JOMNIATEST)))) {
                int iRet = vg.MakeConnection(SQLSERVER_LOCAL_JOMNIATEST);
                if (iRet!=INT_RET_OK) {
                    return iRet;
                }
                SqlConnectionType=SQLSERVER_LOCAL_JOMNIATEST;
                logger.info("SqlConnectionType = " + SqlConnectionType);
                gSqlCon = vg.getSqlCon();
                return DATABASE_CONNECTION_OK;
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        return INT_RET_OK;
    }
    public String FillDetectorMeasurementsList (long pDetectorId,int pIntCount,String pStrStart ) throws SQLException {
        MeasurementUnits.clear();
        String SQL;
        String startTime=UNIX_START_TIME;
        java.sql.PreparedStatement stmt;
        logger.info(" SqlConnectionType =" + SqlConnectionType);
        logger.info("Start ");
        GetTimeSerieSqlServerData st = new GetTimeSerieSqlServerData();
        SQL =st.getStatement(pIntCount) ;
        //logger.info("SqlConnectionTypeyyyy= "+ SqlConnectionType);
        //logger.info("SQL = " +SQL);
        InfluxSqlToTimeSerie ce;
        try {
            stmt = gSqlCon.prepareStatement(SQL);
            ResultSet rs;
            logger.info(" 1: pDetectorId ="+ pDetectorId);
            logger.info(" 2: pStrStart ="+ pStrStart);
            stmt.setLong(1,pDetectorId);
            stmt.setString(2,pStrStart);
            rs = stmt.executeQuery();
            while (rs.next()) {
                ce= new InfluxSqlToTimeSerie();
                ce.setSerieName(rs.getString(1));
                ce.setOmniaCode(rs.getLong(2));
                ce.setIntersectionId(rs.getLong(3));
                ce.setControllerId(rs.getLong(4));
                ce.setDetectorId(rs.getLong(5));
                ce.setDetectorExternalCode(rs.getString(6));
                ce.setMeasurementTime(rs.getString(7));
                startTime=rs.getString(7);
                ce.setVehicleCount(rs.getLong(8));
                ce.setMeanVehicleSpeed(rs.getDouble(9));
                ce.setOccupancyProcent(rs.getDouble(10));
                ce.setAccurancy(rs.getDouble(11));
                MeasurementUnits.add(ce);
            }
            stmt.close();
            rs.close();
            if (MeasurementUnits.isEmpty()==true) {
                ce= new InfluxSqlToTimeSerie();
                ce.MakeEmptyElement();
                MeasurementUnits.add(ce);
                return SWARCO_END_TIME;
            }
            logger.info("bef ret iRet OK stratTime ="+ startTime);
            return startTime;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            ce= new InfluxSqlToTimeSerie();
            ce.MakeEmptyElement();
            MeasurementUnits.add(ce);
            gSqlCon.close();
            return RET_NOT_OK;
        }
    }
    public int WriteDetectorMeasurementsListToInflux ( ) {
        InfluxDBOwn it  = new InfluxDBOwn();
        int iRet=INT_RET_OK;
        InfluxSqlToTimeSerie ce = new InfluxSqlToTimeSerie();
        logger.info("MeasurementUnits.size()= " + MeasurementUnits.size());
        for (int i = 0; i < MeasurementUnits.size(); i++) {
            ce=MeasurementUnits.get(i);
          //  logger.info("ce.toString() = " + ce.toString());
              // first try write line by line
             //   public int WriteSwarcoLineFromString(String pDbname, String pLine) {
            logger.info("ce.GetTimeSerieString() = " + ce.GetTimeSerieString());
            iRet=ts.WriteSwarcoLineFromString("swarco_test",ce.GetTimeSerieString());
           if  (iRet!=INT_RET_OK) {
               logger.info("unsuccessful influx write operation iRet = " + iRet);
               return iRet;
            }
        }
       return INT_RET_OK;
    }
    public String GetNextUnhandledMeasurement(Long pDetectorId, String pStartTime) throws SQLException {
        String SQL;
        String strRet;
        java.sql.PreparedStatement stmt;
        try {
            GetOldestUnusedTimeStampFromMeasurements st = new GetOldestUnusedTimeStampFromMeasurements();
            SQL =st.getStatement();
            ResultSet rs;
            stmt = gSqlCon.prepareStatement(SQL);
            stmt.setLong(1,pDetectorId);
            stmt.setString(2,pStartTime);
            logger.info("pDetectorId =" + pDetectorId);
            logger.info("pStartTime =" + pStartTime);
            rs = stmt.executeQuery();
            strRet=NO_VALUE;
            while (rs.next()) {
                   strRet = rs.getString(1);
            }
            stmt.close();
            rs.close();
            return strRet;
        } catch(Exception e) {
            logger.info(" catch 11");
            logger.info(e.getMessage());
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            e.printStackTrace();
            gSqlCon.close();
            return NO_VALUE;
        }
    }
   public  List<InfluxSqlToTimeSerie> GetInfluxSqlToTimeSerieList()  {
       InfluxSqlToTimeSerie cc = new InfluxSqlToTimeSerie();
       if (MeasurementUnits.isEmpty()==true) {
          cc= new InfluxSqlToTimeSerie();
          cc.MakeEmptyElement();
          MeasurementUnits.add(cc);
       }
       return MeasurementUnits;
   }
    public  void SetInfluxSqlToTimeSerieList(List<InfluxSqlToTimeSerie> pList)  {
        InfluxSqlToTimeSerie cc = new InfluxSqlToTimeSerie();
        if (pList.isEmpty()==true) {
            cc= new InfluxSqlToTimeSerie();
            cc.MakeEmptyElement();
            MeasurementUnits.add(cc);
        } else {
            MeasurementUnits=pList;
        }
    }
}
