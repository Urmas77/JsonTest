package fi.swarco.dataHandling.omniaClientDataHandling;
        import java.io.IOException;
        import java.sql.*;
        import java.util.Collections;
        import java.util.LinkedList;
        import java.util.List;
       import com.google.gson.Gson;
        import com.google.gson.JsonParser;
        import fi.swarco.SwarcoEnumerations;
        import fi.swarco.connections.SwarcoConnections;
        import fi.swarco.dataHandling.pojos.OmniaDetector;
        import fi.swarco.dataHandling.pojos.OmniaDetectorServer;
        import fi.swarco.dataHandling.pojos.influx.InfluxSqlToTimeSerie;
        import fi.swarco.dataHandling.queriesSql.sqlServer.GetDetectorOmniviewSqlServerData;
        import fi.swarco.dataHandling.queriesSql.sqlServer.GetDetectorSqlServerData;
        import fi.swarco.dataHandling.queriesSql.sqlServer.GetVisibleDetectorsSqlServerData;
        import fi.swarco.influxoperations.InfluxDBOwn;
        import fi.swarco.omniaDataTransferServices.MessageUtils;
        import fi.swarco.properties.JSwarcoproperties;
        import org.apache.commons.lang.exception.ExceptionUtils;
        import org.apache.log4j.Logger;
        import static fi.swarco.CONSTANT.*;
        import static fi.swarco.SwarcoEnumerations.ConnectionType.INFLUX_LOCAL;
        import static fi.swarco.SwarcoEnumerations.ConnectionType.SQLSERVER_LOCAL_JOMNIATEST;

public class OmniaDetectorClientDatalevel {
    private static Logger logger = Logger.getLogger(OmniaDetectorClientDatalevel.class.getName());
    List<OmniaDetectorServer> DataUnits = Collections.synchronizedList(new LinkedList<OmniaDetectorServer>()); // ????
    static Connection gSqlCon;
    int iRet;
    InfluxDBOwn ts = new InfluxDBOwn();
    private SwarcoEnumerations.ConnectionType SqlConnectionType=SwarcoEnumerations.ConnectionType.NOT_DEFINED;
    public  int MakeConnection(SwarcoEnumerations.ConnectionType pSqlCon) {
        SwarcoConnections vg = new SwarcoConnections();
        logger.info("pSqlCon = "+ pSqlCon);
        iRet = vg.MakeConnection(pSqlCon);
        if (iRet!=1) {
            return iRet;
        }
        SqlConnectionType=pSqlCon;
        logger.info("SqlConnectionType = " + SqlConnectionType);
        gSqlCon = vg.getSqlCon();
        return DATABASE_CONNECTION_OK;
    }
    public  int MakeConnection2(SwarcoEnumerations.ConnectionType pSqlCon1,SwarcoEnumerations.ConnectionType pSqlCon2) {
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
    public int GetVisibleDetectorsData () throws SQLException {
        DataUnits.clear();
        String SQL="";
        java.sql.PreparedStatement stmt;
        logger.info(" SqlConnectionType =" + SqlConnectionType);
        logger.info("Start ");
        GetVisibleDetectorsSqlServerData st = new GetVisibleDetectorsSqlServerData();
        SQL =st.getStatement() ;
        OmniaDetectorServer ce;
        try {
            stmt = gSqlCon.prepareStatement(SQL);
            ResultSet rs;
            rs = stmt.executeQuery();
            logger.info(" rs.getFetchSize() = " + rs.getFetchSize());
            while (rs.next()) {
                ce = new OmniaDetectorServer();
                ce.setOmniaCode(rs.getLong(1));
                ce.setOmniaName(rs.getString(2));
                ce.setOmniaPublicationStatus(rs.getLong(3));
                ce.setIntersectionId(rs.getLong(4));
                ce.setControllerId(rs.getLong(5));
                ce.setDetectorId(rs.getLong(6));
                ce.setDetectorTypeId(rs.getLong(7));
                ce.setDetectorProgressId(rs.getLong(8));
                ce.setDetectorMaintenanceCode(rs.getString(9));
                ce.setDetectorMeasurementStationId(rs.getLong(10));
                ce.setDetectorExternalCode(rs.getString(11));
                ce.setDetectorSubSystemId(rs.getLong(12));
                ce.setDetectorUnitId(rs.getLong(13));
                ce.setDetectorVisible(rs.getLong(14));
                ce.setDetectorDeleted(rs.getLong(15));
                ce.setDetectorDataPreviousUpdate(rs.getString(16));
                ce.setDetectorGuid(rs.getString(17));
                ce.setDetectorDescription(rs.getString(18));
                ce.setDetectorAreaId(rs.getLong(19));
                ce.setCreated(rs.getString(20));
                ce.setDetectorObjectPriorityId(rs.getLong(21));
                ce.setDetectorParkingHouseId(rs.getLong(22));
                DataUnits.add(ce);
                logger.info("ce.toString() = "+ ce.toString());
            }
            stmt.close();
            rs.close();
            if (DataUnits.isEmpty()==true) {
                ce= new OmniaDetectorServer();
                ce.MakeEmptyElement();
                DataUnits.add(ce);
                return INT_RET_NOT_OK;
            }
            logger.info("bef ret iRet OK");
            return INT_RET_OK;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            ce= new OmniaDetectorServer();
            ce.MakeEmptyElement();
            DataUnits.add(ce);
            gSqlCon.close();
            return INT_RET_NOT_OK;
        }
    }
    public int WriteDetectorsDataToInflux(){
        InfluxDBOwn it  = new InfluxDBOwn();
        int iRet=INT_RET_OK;
        OmniaDetectorServer ce;
        logger.info("DataUnits.size()= " + DataUnits.size());
        for (int i = 0; i < DataUnits.size(); i++) {
            ce=DataUnits.get(i);
            logger.info("ce.GetDetectorNodeJsString(Controllers) = " + ce.GetDetectorNodeJsString("Controllers"));
            iRet=ts.WriteSwarcoLineFromString("swarco_test",ce.GetDetectorNodeJsString("Controllers"));
            if  (iRet!=INT_RET_OK) {
                logger.info("unsuccessful influx write operation iRet = " + iRet);
                return iRet;
            }
        }
        return INT_RET_OK;
    }
    private OmniaDetector GetOmniaDetectorData (long plngDetectorId, String pstrTimestamp) throws SQLException {
// there is only one element
        String SQL="";
        java.sql.PreparedStatement stmt;
        logger.info(" SqlConnectionType =" + SqlConnectionType);
        logger.info("Start ");
        GetDetectorSqlServerData st = new GetDetectorSqlServerData();
        SQL =st.getStatement() ;
 //       logger.info("SqlConnectionTypeyyyy= "+ SqlConnectionType);
         logger.info("SQL = " +SQL);
        int pos=0;
        OmniaDetector ce;
        try {
            stmt = gSqlCon.prepareStatement(SQL);
            pos=1;   // 1
            stmt.setLong(pos,plngDetectorId);
            pos=pos+1;
            java.sql.Timestamp  tStamp=java.sql.Timestamp.valueOf(pstrTimestamp);
            stmt.setTimestamp(pos,tStamp);
            ResultSet rs;
            rs = stmt.executeQuery();
            logger.info(" rs.getFetchSize() = " + rs.getFetchSize());
            while (rs.next()) {
                ce = new OmniaDetector();
   //             logger.info(" rs.getLong(1) =" +rs.getLong(1));
                ce.setOmniaCode(rs.getLong(1));
   //             logger.info(" rs.getString(2) =" +rs.getString(2));
                ce.setOmniaName(rs.getString(2));
   //             logger.info(" rs.getLong(3) =" +rs.getLong(3));
                ce.setOmniaPublicationStatus(rs.getLong(3));
    //            logger.info(" rs.getLong(4) =" +rs.getLong(4));
                ce.setIntersectionId(rs.getLong(4));
      //          logger.info(" rs.getLong(5) =" +rs.getLong(5));
                ce.setControllerId(rs.getLong(5));
       //         logger.info(" rs.getLong(6) =" +rs.getLong(6));
                ce.setDetectorId(rs.getLong(6));
       //         logger.info(" rs.getLong(7) =" +rs.getLong(7));
                ce.setDetectorTypeId(rs.getLong(7));
        //        logger.info(" rs.getLong(8) =" +rs.getLong(8));
                ce.setDetectorProgressId(rs.getLong(8));
         //       logger.info(" rs.getString(9) =" +rs.getString(9));
                ce.setDetectorMaintenanceCode(rs.getString(9));
          //      logger.info(" rs.getLong(10) =" +rs.getLong(10));
                ce.setDetectorMeasurementStationId(rs.getLong(10));
           //     logger.info(" rs.getString(11) =" +rs.getString(11));
                ce.setDetectorExternalCode(rs.getString(11));
           //     logger.info(" rs.getLong(12) =" +rs.getLong(12));
                ce.setDetectorSubSystemId(rs.getLong(12));
            //    logger.info(" rs.getLong(13) =" +rs.getLong(13));
                ce.setDetectorUnitId(rs.getLong(13));
            //    logger.info(" rs.getLong(14) =" +rs.getLong(14));
                ce.setDetectorVisible(rs.getLong(14));
            //    logger.info(" rs.getLong(15) =" +rs.getLong(15));
                ce.setDetectorDeleted(rs.getLong(15));
             //   logger.info(" rs.getString(16) =" +rs.getString(16));
                ce.setDetectorDataPreviousUpdate(rs.getString(16));
             //   logger.info(" rs.getString(17) =" +rs.getString(17));
                ce.setDetectorGuid(rs.getString(17));
             //   logger.info(" rs.getString(18) =" +rs.getString(18));
                ce.setDetectorDescription(rs.getString(18));
             //   logger.info(" rs.getLong(19) =" +rs.getLong(19));
                ce.setDetectorAreaId(rs.getLong(19));
             //   logger.info(" rs.getString(20) =" +rs.getString(20));
                ce.setCreated(rs.getString(20));
           //     logger.info(" rs.getLong(21) =" +rs.getLong(21));
                ce.setDetectorObjectPriorityId(rs.getLong(21));
           //     logger.info(" rs.getLong(22) =" +rs.getLong(22));
                ce.setDetectorParkingHouseId(rs.getLong(22));
                stmt.close();
                rs.close();
                return ce;
            }
            stmt.close();
            rs.close();
            ce = new OmniaDetector();
            ce.MakeEmptyElement();
            return ce;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            ce= new OmniaDetector();
            ce.MakeEmptyElement();
            gSqlCon.close();
            return ce;
        }
    }
    private OmniaDetector GetOmniviewDetectorData (long plngControllerId,long plngDetectorId, String pstrTimestamp) throws SQLException {
// there is only one element
        String SQL="";
        java.sql.PreparedStatement stmt;
     //   logger.info(" SqlConnectionType =" + SqlConnectionType);
     //   logger.info("Start ");
        GetDetectorOmniviewSqlServerData st = new GetDetectorOmniviewSqlServerData();
        SQL =st.getStatement() ;
        //       logger.info("SqlConnectionTypeyyyy= "+ SqlConnectionType);
     //   logger.info( "plngControllerId = "+ plngControllerId+ " plngDetectorId = "+ plngDetectorId + " pstrTimestamp = "+pstrTimestamp);
     //   logger.info("SQL = " +SQL);
        int pos=0;
        OmniaDetector ce;
        try {
            stmt = gSqlCon.prepareStatement(SQL);
            pos=1;   // 1
            stmt.setLong(pos, plngControllerId);
            pos=pos+1;
            stmt.setLong(pos,plngDetectorId);
            pos=pos+1;
            java.sql.Timestamp  tStamp=java.sql.Timestamp.valueOf(pstrTimestamp);
            stmt.setTimestamp(pos,tStamp);
            ResultSet rs;
            rs = stmt.executeQuery();
       //     logger.info(" rs.getFetchSize() = " + rs.getFetchSize());
            while (rs.next()) {
                ce = new OmniaDetector();
         //       logger.info(" rs.getLong(1) =" +rs.getLong(1));
                ce.setOmniaCode(rs.getLong(1));
         //       logger.info(" rs.getString(2) =" +rs.getString(2));
                ce.setOmniaName(rs.getString(2));
         //       logger.info(" rs.getLong(3) =" +rs.getLong(3));
                ce.setOmniaPublicationStatus(rs.getLong(3));
          //      logger.info(" rs.getLong(4) =" +rs.getLong(4));
                ce.setIntersectionId(rs.getLong(4));
          //      logger.info(" rs.getLong(5) =" +rs.getLong(5));
                ce.setControllerId(rs.getLong(5));
          //      logger.info(" rs.getLong(6) =" +rs.getLong(6));
                ce.setDetectorId(rs.getLong(6));
          //      logger.info(" rs.getLong(7) =" +rs.getLong(7));
                ce.setDetectorTypeId(rs.getLong(7));
          //      logger.info(" rs.getLong(8) =" +rs.getLong(8));
                ce.setDetectorProgressId(rs.getLong(8));
          //      logger.info(" rs.getString(9) =" +rs.getString(9));
                ce.setDetectorMaintenanceCode(rs.getString(9));
          //      logger.info(" rs.getLong(10) =" +rs.getLong(10));
                ce.setDetectorMeasurementStationId(rs.getLong(10));
           //     logger.info(" rs.getString(11) =" +rs.getString(11));
                ce.setDetectorExternalCode(rs.getString(11));
           //     logger.info(" rs.getLong(12) =" +rs.getLong(12));
                ce.setDetectorSubSystemId(rs.getLong(12));
           //     logger.info(" rs.getLong(13) =" +rs.getLong(13));
                ce.setDetectorUnitId(rs.getLong(13));
           //     logger.info(" rs.getLong(14) =" +rs.getLong(14));
                ce.setDetectorVisible(rs.getLong(14));
           //     logger.info(" rs.getLong(15) =" +rs.getLong(15));
                ce.setDetectorDeleted(rs.getLong(15));
            //    logger.info(" rs.getString(16) =" +rs.getString(16));
                ce.setDetectorDataPreviousUpdate(rs.getString(16));
            //    logger.info(" rs.getString(17) =" +rs.getString(17));
                ce.setDetectorDescription(rs.getString(17));
            //    logger.info(" rs.getLong(18) =" +rs.getLong(18));
                ce.setDetectorAreaId(rs.getLong(18));
            //    logger.info(" rs.getString(19) =" +rs.getString(19));
                ce.setCreated(rs.getString(19));
            //    logger.info(" rs.getLong(20) =" +rs.getLong(20));
                ce.setDetectorObjectPriorityId(rs.getLong(20));
            //    logger.info(" rs.getLong(21) =" +rs.getLong(21));
                ce.setDetectorParkingHouseId(rs.getLong(21));
                stmt.close();
                rs.close();
                return ce;
            }
            stmt.close();
            rs.close();
            ce = new OmniaDetector();
            ce.MakeEmptyElement();
            return ce;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            ce= new OmniaDetector();
            ce.MakeEmptyElement();
            gSqlCon.close();
            return ce;
        }
    }

    private  String  GetDetectorJsonString(OmniaDetector pCe) {
        int iRet=0;
        String strHelp1=NO_VALUE;
        try {
            Gson myGson = new Gson();
            MessageUtils mu = new MessageUtils();
     //       logger.info("pCe.toString() = "+ pCe.toString());
            JsonParser jsonParser = new JsonParser();
            strHelp1 = myGson.toJson(pCe);
            return strHelp1 ;
        } catch (Exception e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            e.printStackTrace();
            return NO_VALUE;
        }
    }
    public String GetDetectorJsonData(long plngDetectorId, String pstrTimestamp) throws SQLException {
        OmniaDetector ce = GetOmniaDetectorData(plngDetectorId, pstrTimestamp);
        String strHelp1 = GetDetectorJsonString(ce);
        return strHelp1;
    }
    public  List<OmniaDetectorServer> GetOmniaSuperDataList()  {
        OmniaDetectorServer cc = new OmniaDetectorServer();
        if (DataUnits.isEmpty()==true) {
            cc= new OmniaDetectorServer();
            cc.MakeEmptyElement();
            DataUnits.add(cc);
        }
        return DataUnits;
    }

    public String GetDetectorJsonDataOmniview(long plngControllerId ,long plngDetectorId, String pstrTimestamp) throws SQLException {
        OmniaDetector ce = GetOmniviewDetectorData(plngControllerId,plngDetectorId, pstrTimestamp);
        String strHelp1 = GetDetectorJsonString(ce);
        return strHelp1;
    }
}
