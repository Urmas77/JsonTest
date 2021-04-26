package fi.swarco.dataHandling.omniaServerDataHandling;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.connections.SwarcoConnections;
import fi.swarco.dataHandling.pojos.OmniaDetectorServer;
import fi.swarco.dataHandling.queriesSql.mySQL.*;
import fi.swarco.omniaDataTransferServices.MessageUtils;
import fi.swarco.serviceOperations.SwarcoTimeUtilities;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import static fi.swarco.CONSTANT.*;
import static fi.swarco.CONSTANT.UNSUCCESSFUL_DATABASE_INSERT_OPERATION;
public class DetectorServerListDataLevel {
    private static Logger logger = Logger.getLogger(DetectorServerListDataLevel.class.getName());
    List<OmniaDetectorServer> OmniaDetectorDataUnits = Collections.synchronizedList(new LinkedList<OmniaDetectorServer>());  // ????
    private OmniaDetectorServer foundRec = new OmniaDetectorServer();
    public OmniaDetectorServer getFoundRec() { return foundRec;}
    public  void setFoundRec(OmniaDetectorServer pFoundRec) {foundRec=pFoundRec;}
    static Connection gSqlCon;
    private SwarcoEnumerations.ConnectionType SqlConnectionType=SwarcoEnumerations.ConnectionType.NOT_DEFINED;
    public  int MakeConnection(SwarcoEnumerations.ConnectionType pSqlCon) {
        SwarcoConnections vg = new SwarcoConnections();
        logger.info("pSqlCon = "+ pSqlCon);
        int iRet = vg.MakeConnection(pSqlCon);
        if (iRet!=1) {
            return iRet;
        }
        SqlConnectionType=pSqlCon;
  //      logger.info("SqlConnectionType = " + SqlConnectionType);
        gSqlCon = vg.getSqlCon();
        return DATABASE_CONNECTION_OK;
    }
    private OmniaDetectorServer GetOmniaDetectorData (long plngDetectorId, String pstrTimestamp) throws SQLException {
// there is only one element
        String SQL="";
        java.sql.PreparedStatement stmt;
        logger.info(" SqlConnectionType =" + SqlConnectionType);
        logger.info("Start ");
        SelectDetectorDataMySql st = new SelectDetectorDataMySql();
        SQL =st.getStatement() ;
        logger.info("SqlConnectionTypeyyyy= "+ SqlConnectionType);
        logger.info("SQL = " +SQL);
        int pos=0;
        OmniaDetectorServer ce;
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
                stmt.close();
                rs.close();
                return ce;
            }
            stmt.close();
            rs.close();
            ce = new OmniaDetectorServer();
            ce.MakeEmptyElement();
            return ce;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            ce= new OmniaDetectorServer();
            ce.MakeEmptyElement();
            gSqlCon.close();
            return ce;
        }
    }
    public int OmniaDetectorDataList () throws SQLException {
        OmniaDetectorDataUnits.clear();
        String SQL="";
        java.sql.PreparedStatement stmt;
        logger.info(" SqlConnectionType =" + SqlConnectionType);
        logger.info("Start ");
        SelectDetectorDataMySql st = new   SelectDetectorDataMySql();
        SQL =st.getStatement() ;
        logger.info("SqlConnectionTypeyyyy= "+ SqlConnectionType);
        logger.info("SQL = " +SQL);
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
                OmniaDetectorDataUnits.add(ce);
            }
            stmt.close();
            rs.close();
            if (OmniaDetectorDataUnits.isEmpty()==true) {
                ce= new OmniaDetectorServer();
                ce.MakeEmptyElement();
                OmniaDetectorDataUnits.add(ce);
                return -1;
            }
            logger.info("bef ret iRet OK");
            return INT_RET_OK;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            ce= new OmniaDetectorServer();
            ce.MakeEmptyElement();
            OmniaDetectorDataUnits.add(ce);
            gSqlCon.close();
            return -1;
        }
    }



    public int DoesLineAlreadyExist(OmniaDetectorServer pOmniaData) throws SQLException {
        String SQL="";
        java.sql.PreparedStatement stmt=null;
        OmniaDetectorServer oi = new OmniaDetectorServer();
        oi.MakeEmptyElement();
        setFoundRec(oi);
        try {
            SelectDetectorDataMySqlWhere st = new SelectDetectorDataMySqlWhere();
            SQL =st.getStatement();
            ResultSet rs;
            stmt = gSqlCon.prepareStatement(SQL);
            stmt.setLong(1,pOmniaData.getOmniaCode());
            stmt.setLong(2,pOmniaData.getIntersectionId());
            stmt.setLong(3,pOmniaData.getControllerId());
            stmt.setLong(4,pOmniaData.getDetectorId());
            logger.info("pOmniaData.getOmniaCode() =" + pOmniaData.getOmniaCode());
            logger.info("pOmniaData.getIntersectionId() =" + pOmniaData.getIntersectionId());
            logger.info("pOmniaData.getControllerId() =" + pOmniaData.getControllerId());
            logger.info("pOmniaData.getDetectorId() =" + pOmniaData.getDetectorId());
            rs = stmt.executeQuery();
            OmniaDetectorServer cc;
            while (rs.next()) {
                cc= new OmniaDetectorServer();
                cc.setOmniaCode(rs.getLong(1));
                cc.setOmniaName(rs.getString(2));
                cc.setOmniaPublicationStatus(rs.getLong(3));
                cc.setIntersectionId(rs.getLong(4));
                cc.setControllerId(rs.getLong(5));
                cc.setDetectorId(rs.getLong(6));
                cc.setDetectorTypeId(rs.getLong(7));
                cc.setDetectorProgressId(rs.getLong(8));
                cc.setDetectorMaintenanceCode(rs.getString(9));
                cc.setDetectorMeasurementStationId(rs.getLong(10));
                cc.setDetectorExternalCode(rs.getString(11));
                cc.setDetectorSubSystemId(rs.getLong(12));
                cc.setDetectorUnitId(rs.getLong(13));
                cc.setDetectorVisible(rs.getLong(14));
                cc.setDetectorDeleted(rs.getLong(15));
                cc.setDetectorDataPreviousUpdate(rs.getString(16));
                cc.setDetectorGuid(rs.getString(17));
                cc.setDetectorDescription(rs.getString(18));
                cc.setDetectorAreaId(rs.getLong(19));
                cc.setCreated(rs.getString(20));
                cc.setDetectorObjectPriorityId(rs.getLong(21));
                cc.setDetectorParkingHouseId(rs.getLong(22));
                logger.info("pOmniaData.getOmniaCode() =" + pOmniaData.getOmniaCode());
                logger.info("pOmniaData.getIntersectionId() =" + pOmniaData.getIntersectionId());
                logger.info("pOmniaData.getControllerId() =" + pOmniaData.getControllerId());
                logger.info("pOmniaData.getDetectorId() =" + pOmniaData.getDetectorId());
                logger.info("cc.getOmniaCode() =" + cc.getOmniaCode());
                logger.info("cc.getIntersectionId() =" + cc.getIntersectionId());
                logger.info("cc.getControllerId() =" + cc.getControllerId());
                logger.info("cc.getDetectorId() =" + cc.getDetectorId());
                if ((cc.getOmniaCode()==(pOmniaData.getOmniaCode()) &&
                   (cc.getIntersectionId()==(pOmniaData.getIntersectionId())   &&
                   (cc.getControllerId()==(pOmniaData.getControllerId())   &&
                   (cc.getDetectorId()==(pOmniaData.getDetectorId()) ))))) {
                    logger.info("*****1111***** löyty");
                    setFoundRec(cc);
                    stmt.close();
                    rs.close();
                    return INT_RET_FOUND;
                }
            }
            stmt.close();
            rs.close();
            logger.info("*****1111***** ei löytyny");
            return INT_RET_NOT_FOUND;
        } catch(Exception e) {
            logger.info(" catch 11");
            logger.info(e.getMessage());
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            e.printStackTrace();
            gSqlCon.close();
            return INT_RET_NOT_OK;
        }
    }
    public int IsItChanged(OmniaDetectorServer pC1 ,OmniaDetectorServer pC2) {
        int iRet =  NOT_CHANGED;
        if (!(pC1.getOmniaName().equals(pC1.getOmniaName()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getOmniaPublicationStatus()==(pC2.getOmniaPublicationStatus()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getIntersectionId()==(pC2.getIntersectionId()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getControllerId()==(pC2.getControllerId()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getDetectorTypeId()==(pC2.getDetectorTypeId()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getDetectorProgressId()==(pC2.getDetectorProgressId()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getDetectorMaintenanceCode().equals(pC2.getDetectorMaintenanceCode()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getDetectorMeasurementStationId()==pC2.getDetectorMeasurementStationId())) {
            iRet =  CHANGED;
        }
        if (!(pC1.getDetectorExternalCode().equals(pC2.getDetectorExternalCode()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getDetectorSubSystemId()==(pC2.getDetectorSubSystemId()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getDetectorUnitId()==(pC2.getDetectorUnitId()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getDetectorVisible()==(pC2.getDetectorVisible()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getDetectorDeleted()==(pC2.getDetectorDeleted()))) {
            iRet =  CHANGED;
        }
     //   if (!(pC1.getDetectorDataPreviousUpdate().equals(pC2.getDetectorDataPreviousUpdate()))) {
     //       iRet =  CHANGED;
     //   }
        if (!(pC1.getDetectorGuid().equals(pC2.getDetectorGuid()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getDetectorDescription().equals(pC2.getDetectorDescription()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getDetectorAreaId()==(pC2.getDetectorAreaId()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getDetectorObjectPriorityId()==(pC2.getDetectorObjectPriorityId()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getDetectorParkingHouseId()==(pC2.getDetectorParkingHouseId()))) {
            iRet =  CHANGED;
        }
        if (iRet==CHANGED) {
           logger.info("***Changed1  pC1.toString()" + pC1.toString());
           logger.info("***Changed2  pC2.toString()" + pC2.toString());
        }
        return iRet;
    }
    public int MakeDeleteInsert(OmniaDetectorServer pC1) throws SQLException {
        int iRet = INT_RET_OK;
        iRet = DeleteOldDetectorLineFromDb(pC1);
        if (iRet < 0) {
            logger.info("iRet = " + iRet);
            iRet = UNSUCCESSFUL_DATABASE_DELETE_OPERATION;
            logger.info("iRet = " + iRet);
            return iRet;
        }
        iRet = AddNewDetectorData(pC1);
        if (iRet < 0) {
            logger.info("***********************Successful delete unsuccesful insert iRet = " + iRet);
            iRet = UNSUCCESSFUL_DATABASE_DELETE_OPERATION;
            logger.info("***********************Successful delete unsuccesful insert iRet = " + iRet);
            return iRet;
        }
        return iRet;
    }
    private int DeleteOldDetectorLineFromDb(OmniaDetectorServer pC1 ) throws SQLException {
        int iRet;
        String SQL = "";
        try {
            java.sql.PreparedStatement stmt;
            SQL = " delete from DetectorData ";
            SQL = SQL + "where OmniaCode = " + pC1.getOmniaCode() + " and " + " DetectorId = " + pC1.getDetectorId() + ";";
            logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
//            logger.info("Lines deleted iRet = " + iRet);
            stmt.close();
            if (iRet < 0) {
                logger.info("iRet = " + iRet);
                iRet = UNSUCCESSFUL_DATABASE_DELETE_OPERATION;
                logger.info("iRet = " + iRet);
                return iRet;
            }
            return iRet;
        } catch (Exception e) {
            logger.info(" catch 11");
            logger.info(e.getMessage());
            e.printStackTrace();
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            gSqlCon.close();
            return UNSUCCESSFUL_DATABASE_OPERATION;
        }
    }
    public  int AddNewDetectorData(OmniaDetectorServer pOmniaData) throws SQLException {
        int iRet;
        String SQL="";
        java.sql.PreparedStatement stmt;
        try {
            InsertDetectorMySql st = new InsertDetectorMySql();
            SQL =st.getStatement();
            stmt = gSqlCon.prepareStatement(SQL);
            int pos=0;
            pos=1;
            stmt.setLong(pos,pOmniaData.getOmniaCode());
            pos=pos+1;
            stmt.setString(pos,pOmniaData.getOmniaName());
            pos=pos+1;
            stmt.setLong(pos,pOmniaData.getOmniaPublicationStatus());
            pos=pos+1;
            stmt.setLong(pos,pOmniaData.getIntersectionId());
            pos=pos+1;
            stmt.setLong(pos,pOmniaData.getControllerId());
            pos=pos+1;
            stmt.setLong(pos,pOmniaData.getDetectorId());
            pos=pos+1;
            stmt.setLong(pos,pOmniaData.getDetectorTypeId());
            pos=pos+1;
            stmt.setLong(pos,pOmniaData.getDetectorProgressId());
            pos=pos+1;
            stmt.setString(pos,pOmniaData.getDetectorMaintenanceCode());
            pos=pos+1;
            stmt.setLong(pos,pOmniaData.getDetectorMeasurementStationId());
            pos=pos+1;
            stmt.setString(pos,pOmniaData.getDetectorExternalCode());
            pos=pos+1;
            stmt.setLong(pos,pOmniaData.getDetectorSubSystemId());
            pos=pos+1;
            stmt.setLong(pos,pOmniaData.getDetectorUnitId());
            pos=pos+1;
            stmt.setLong(pos,pOmniaData.getDetectorVisible());
            pos=pos+1;
            stmt.setLong(pos,pOmniaData.getDetectorDeleted());
            pos=pos+1;
            stmt.setString(pos,pOmniaData.getDetectorDataPreviousUpdate());
            pos=pos+1;
            stmt.setString(pos,pOmniaData.getDetectorGuid());
            pos=pos+1;
            stmt.setString(pos,pOmniaData.getDetectorDescription());
            pos=pos+1;
            stmt.setLong(pos,pOmniaData.getDetectorAreaId());
            pos=pos+1;
            stmt.setString(pos,pOmniaData.getCreated());
            pos=pos+1;
            stmt.setLong(pos,pOmniaData.getDetectorObjectPriorityId());
            pos=pos+1;
            stmt.setLong(pos,pOmniaData.getDetectorParkingHouseId());
            iRet = stmt.executeUpdate();
            stmt.close();
            return iRet;
        } catch(Exception e) {
            logger.info(" catch 11  e=",e);
            logger.info(e.getMessage());
            e.printStackTrace();
            gSqlCon.close();
            return UNSUCCESSFUL_DATABASE_INSERT_OPERATION;
        }
    }
    public List<OmniaDetectorServer> GetOmniaIntersectionDataList()  {
        OmniaDetectorServer cc = new OmniaDetectorServer();
        if (OmniaDetectorDataUnits.isEmpty()==true) {
            cc= new OmniaDetectorServer();
            cc.MakeEmptyElement();
            OmniaDetectorDataUnits.add(cc);
        }
        return OmniaDetectorDataUnits;
    }
    public  int JsonDetectorSql(String pPermanentData) throws SQLException{
        int iRet=0;
        String strHelp1=NO_VALUE;
        try {
            Gson myGson = new Gson();
            MessageUtils mu = new MessageUtils();
      //    logger.info("pPermanentData = " + pPermanentData);
            strHelp1 = mu.ThrowChecksumAway(pPermanentData);
       //     logger.info("strHelp1 = " + strHelp1);
            JsonParser jsonParser = new JsonParser();
            OmniaDetectorServer aOmniaDetector1 = myGson.fromJson(strHelp1, OmniaDetectorServer.class);
            SwarcoTimeUtilities sw = new  SwarcoTimeUtilities();
            String swarcoTime =sw.ToSwarcoTime(aOmniaDetector1.getDetectorDataPreviousUpdate());
            aOmniaDetector1.setDetectorDataPreviousUpdate(swarcoTime);
            swarcoTime =sw.ToSwarcoTime(aOmniaDetector1.getCreated());
            aOmniaDetector1.setCreated(swarcoTime);
            iRet= DoesLineAlreadyExist(aOmniaDetector1);
            if (iRet==INT_RET_NOT_FOUND) {
                iRet=AddNewDetectorData(aOmniaDetector1);
                logger.info("  one line inserted iRet = " + iRet);
                if (iRet!=INT_RET_OK) {
                    logger.info("Unsuccessful OmniaIntersectionData insert iRet = " + iRet);
                }
                return iRet;
            } else if (iRet==INT_RET_FOUND) {
                iRet=IsItChanged(aOmniaDetector1,getFoundRec());
                if (iRet==CHANGED) {
                    iRet =MakeDeleteInsert(aOmniaDetector1);   // Keys are same
                    if (iRet!=INT_RET_OK) {
                        logger.info("Unsuccessful MakeDeleteInsert operation iRet = " + iRet);
                        return iRet;
                    }
                }
                return iRet;    // NOT_CHANGED
            } else {
                logger.info("Unsuccessful DoesLineAlreadyExist insert iRet = " + iRet);
                return iRet;
            }
        } catch (Exception e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            e.printStackTrace();
            gSqlCon.close();
            return UNSUCCESSFUL_DATABASE_INSERT_OPERATION;
        }
    }
}

