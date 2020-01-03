package fi.swarco.dataHandling.omniaServerDataHandling;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.connections.SwarcoConnections;
import fi.swarco.dataHandling.pojos.IntersectionControllerDataServer;
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
public class IntersectionControllerListDatalevel {
    private static Logger logger = Logger.getLogger(IntersectionControllerListDatalevel.class.getName());
    List<IntersectionControllerDataServer> OmniaIntersectionControllerDataUnits = Collections.synchronizedList(new LinkedList<>());  // ????
    private IntersectionControllerDataServer foundRec = new IntersectionControllerDataServer();
    public IntersectionControllerDataServer getFoundRec() { return foundRec;}
    public  void setFoundRec(IntersectionControllerDataServer pFoundRec) {foundRec=pFoundRec;}
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
        logger.info("SqlConnectionType = " + SqlConnectionType);
        gSqlCon = vg.getSqlCon();
        return DATABASE_CONNECTION_OK;
    }
    private IntersectionControllerDataServer GetOmniaIntesectionControllerData (long plngIntersectionId, long plngControllerId , String pstrTimestamp) throws SQLException {
        java.sql.PreparedStatement stmt;
        logger.info(" SqlConnectionType =" + SqlConnectionType);
        logger.info("Start ");
        SelectIntersectionControllerDataMySql st = new SelectIntersectionControllerDataMySql();
        String SQL =st.getStatement() ;
        logger.info("SqlConnectionTypeyyyy= "+ SqlConnectionType);
        logger.info("SQL = " +SQL);
        int pos;
        IntersectionControllerDataServer ce;
        try {
            stmt = gSqlCon.prepareStatement(SQL);
            pos=1;   // 1
            stmt.setLong(pos,plngIntersectionId);
            pos=pos+1;
            stmt.setLong(pos,plngControllerId);
            pos=pos+1;
            java.sql.Timestamp  tStamp=java.sql.Timestamp.valueOf(pstrTimestamp);
            stmt.setTimestamp(pos,tStamp);
            ResultSet rs;
            rs = stmt.executeQuery();
            logger.info(" rs.getFetchSize() = " + rs.getFetchSize());
            while (rs.next()) {
                ce = new IntersectionControllerDataServer();
                ce.setOmniaCode(rs.getLong(2));
                ce.setOmniaName(rs.getString(3));
                ce.setOmniaPublicationStatus(rs.getLong(4));
                ce.setIntersectionId(rs.getLong(5));
                ce.setIntesectionDescription(rs.getString(6));
                ce.setIntersectionAreaId(rs.getLong(7));
                ce.setIntersectionMaintenanceAreaId(rs.getLong(8));
                ce.setIntersectionExternalCode(rs.getString(9));
                ce.setIntersectionSubSystemId(rs.getLong(10));
                ce.setIntersectionVisible(rs.getLong(11));
                ce.setIntersectionDeleted(rs.getLong(12));
                ce.setIntersectionDataPreviousUpdate(rs.getString(13));
                ce.setIntersectionGuid(rs.getString(14));
                ce.setIntersectionProgressId(rs.getLong(15));
                ce.setControllerId(rs.getLong(16));
                ce.setControllerDescription(rs.getString(17));
                ce.setControllerTypeId(rs.getLong(18));
                ce.setControllerRoadsideUnitId(rs.getLong(19));
                ce.setControllerExternalCode(rs.getString(20));
                ce.setControllerSubSystemId(rs.getLong(21));
                ce.setControllerObjectPriorityId(rs.getLong(22));
                ce.setControllerVisible(rs.getLong(23));
                ce.setControllerDeleted(rs.getLong(24));
                ce.setControllerDataPreviousUpdate(rs.getString(25));
                ce.setControllerGuid(rs.getString(26));
                ce.setCreated(rs.getString(27));
                stmt.close();
                rs.close();
                return ce;
            }
            stmt.close();
            rs.close();
            ce = new IntersectionControllerDataServer();
            ce.MakeEmptyElement();
            return ce;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            ce= new IntersectionControllerDataServer();
            ce.MakeEmptyElement();
            gSqlCon.close();
            return ce;
        }
    }
    public int OmniaIntersectionDataList () throws SQLException {
        OmniaIntersectionControllerDataUnits.clear();
        java.sql.PreparedStatement stmt;
        logger.info(" SqlConnectionType =" + SqlConnectionType);
        logger.info("Start ");
        SelectIntersectionControllerDataMySql st = new SelectIntersectionControllerDataMySql();
        String SQL =st.getStatement() ;
        logger.info("SqlConnectionTypeyyyy= "+ SqlConnectionType);
        logger.info("SQL = " +SQL);
        IntersectionControllerDataServer ce;
        try {
            stmt = gSqlCon.prepareStatement(SQL);
            ResultSet rs;
            rs = stmt.executeQuery();
            logger.info(" rs.getFetchSize() = " + rs.getFetchSize());
            while (rs.next()) {
                ce = new IntersectionControllerDataServer();
                ce.setOmniaCode(rs.getLong(2));
                ce.setOmniaName(rs.getString(3));
                ce.setOmniaPublicationStatus(rs.getLong(4));
                ce.setIntersectionId(rs.getLong(5));
                ce.setIntesectionDescription(rs.getString(6));
                ce.setIntersectionAreaId(rs.getLong(7));
                ce.setIntersectionMaintenanceAreaId(rs.getLong(8));
                ce.setIntersectionExternalCode(rs.getString(9));
                ce.setIntersectionSubSystemId(rs.getLong(10));
                ce.setIntersectionVisible(rs.getLong(11));
                ce.setIntersectionDeleted(rs.getLong(12));
                ce.setIntersectionDataPreviousUpdate(rs.getString(13));
                ce.setIntersectionGuid(rs.getString(14));
                ce.setIntersectionProgressId(rs.getLong(15));
                ce.setControllerId(rs.getLong(16));
                ce.setControllerDescription(rs.getString(17));
                ce.setControllerTypeId(rs.getLong(18));
                ce.setControllerRoadsideUnitId(rs.getLong(19));
                ce.setControllerExternalCode(rs.getString(20));
                ce.setControllerSubSystemId(rs.getLong(21));
                ce.setControllerObjectPriorityId(rs.getLong(22));
                ce.setControllerVisible(rs.getLong(23));
                ce.setControllerDeleted(rs.getLong(24));
                ce.setControllerDataPreviousUpdate(rs.getString(25));
                ce.setControllerGuid(rs.getString(26));
                ce.setCreated(rs.getString(27));
                OmniaIntersectionControllerDataUnits.add(ce);
            }
            stmt.close();
            rs.close();
            if (OmniaIntersectionControllerDataUnits.isEmpty()) {
                ce= new IntersectionControllerDataServer();
                ce.MakeEmptyElement();
                OmniaIntersectionControllerDataUnits.add(ce);
                return -1;
            }
            logger.info("bef ret iRet OK");
            return INT_RET_OK;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            ce= new IntersectionControllerDataServer();
            ce.MakeEmptyElement();
            OmniaIntersectionControllerDataUnits.add(ce);
            gSqlCon.close();
            return -1;
        }
    }
    public int DoesLineAlreadyExist(IntersectionControllerDataServer pIntersectionData) throws SQLException {
        java.sql.PreparedStatement stmt;
        try {
            SelectIntersectionControllerDataMySqlWhere st = new SelectIntersectionControllerDataMySqlWhere();
            String SQL =st.getStatement();
            ResultSet rs;   // seuraavalta lentää
            stmt = gSqlCon.prepareStatement(SQL);
            stmt.setLong(1,pIntersectionData.getOmniaCode());
            stmt.setLong(2,pIntersectionData.getIntersectionId());
            stmt.setLong(3,pIntersectionData.getControllerId());
            rs = stmt.executeQuery();
            IntersectionControllerDataServer cc=new IntersectionControllerDataServer();
            while (rs.next()) {
                cc.setOmniaCode(rs.getLong(1));
                cc.setOmniaName(rs.getString(2));
                cc.setOmniaPublicationStatus(rs.getLong(3));
                cc.setIntersectionId(rs.getLong(4));
                cc.setIntesectionDescription(rs.getString(5));
                cc.setIntersectionAreaId(rs.getLong(6));
                cc.setIntersectionMaintenanceAreaId(rs.getLong(7));
                cc.setIntersectionExternalCode(rs.getString(8));
                cc.setIntersectionSubSystemId(rs.getLong(9));
                cc.setIntersectionVisible(rs.getLong(10));
                cc.setIntersectionDeleted(rs.getLong(11));
                cc.setIntersectionDataPreviousUpdate(rs.getString(12));
                cc.setIntersectionGuid(rs.getString(13));
                cc.setIntersectionProgressId(rs.getLong(14));
                cc.setControllerId(rs.getLong(15));
                cc.setControllerDescription(rs.getString(16));
                cc.setControllerTypeId(rs.getLong(17));
                cc.setControllerRoadsideUnitId(rs.getLong(18));
                cc.setControllerExternalCode(rs.getString(19));
                cc.setControllerSubSystemId(rs.getLong(20));
                cc.setControllerObjectPriorityId(rs.getLong(21));
                cc.setControllerVisible(rs.getLong(22));
                cc.setControllerDeleted(rs.getLong(23));
                cc.setControllerDataPreviousUpdate(rs.getString(24));
                cc.setControllerGuid(rs.getString(25));
                cc.setCreated(rs.getString(26));
                if (((cc.getOmniaCode()==(pIntersectionData.getOmniaCode()) &&
                   (cc.getIntersectionId()==(pIntersectionData.getIntersectionId()) &&
                   (cc.getControllerId()==(pIntersectionData.getControllerId())))))) {
                    logger.info("löyty");
                    setFoundRec(cc);
                    stmt.close();
                    rs.close();
                    return INT_RET_FOUND;
                }
            }
            stmt.close();
            rs.close();
            cc.MakeEmptyElement();
            setFoundRec(cc);
            logger.info("ei l�ytyny");
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
    public int IsItChanged(IntersectionControllerDataServer pC1 , IntersectionControllerDataServer pC2) {
        int iRet =  NOT_CHANGED;
        if (!(pC1.getOmniaName().equals(pC1.getOmniaName()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getOmniaPublicationStatus()==(pC2.getOmniaPublicationStatus()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getIntesectionDescription().equals(pC2.getIntesectionDescription()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getIntersectionAreaId()==(pC2.getIntersectionAreaId()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getIntersectionMaintenanceAreaId()==(pC2.getIntersectionMaintenanceAreaId()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getIntersectionExternalCode().equals(pC2.getIntersectionExternalCode()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getIntersectionSubSystemId()==pC2.getIntersectionSubSystemId())) {
            iRet =  CHANGED;
        }
        if (!(pC1.getIntersectionVisible()==pC2.getIntersectionVisible())) {
            iRet =  CHANGED;
        }
        if (!(pC1.getIntersectionDeleted()==pC2.getIntersectionDeleted())) {
            iRet =  CHANGED;
        }
        if (!(pC1.getIntersectionDataPreviousUpdate().equals(pC2.getIntersectionDataPreviousUpdate()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getIntersectionGuid().equals(pC2.getIntersectionGuid()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getIntersectionProgressId()==(pC2.getIntersectionProgressId()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getControllerId()==(pC2.getControllerId()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getControllerDescription().equals(pC2.getControllerDescription()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getControllerTypeId()==(pC2.getControllerTypeId()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getControllerRoadsideUnitId()==(pC2.getControllerRoadsideUnitId()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getControllerExternalCode().equals(pC2.getControllerExternalCode()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getControllerSubSystemId()==(pC2.getControllerSubSystemId()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getControllerObjectPriorityId()==(pC2.getControllerObjectPriorityId()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getControllerVisible()==(pC2.getControllerVisible()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getControllerDeleted()==(pC2.getControllerDeleted()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getControllerDataPreviousUpdate().equals(pC2.getControllerDataPreviousUpdate()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getControllerGuid().equals(pC2.getControllerGuid()))) {
            iRet =  CHANGED;
        }
       if (iRet==CHANGED) {
            logger.info("***Changed1  pC1.toString()" + pC1.toString());
            logger.info("***Changed2  pC2.toString()" + pC2.toString());
        }
        return iRet;
    }
    public int MakeDeleteInsert(IntersectionControllerDataServer pC1) throws SQLException {
        int iRet;
        iRet = DeleteOldIntersectionControllerDataLineFromDb(pC1);
        if (iRet < 0) {
            logger.info("iRet = " + iRet);
            iRet = UNSUCCESSFUL_DATABASE_DELETE_OPERATION;
            logger.info("iRet = " + iRet);
            return iRet;
        }
        iRet = AddNewOmniaIntersectionControllerData(pC1);
        if (iRet < 0) {
            logger.info("***********************Successful delete unsuccesful insert iRet = " + iRet);
            iRet = UNSUCCESSFUL_DATABASE_DELETE_OPERATION;
            logger.info("***********************Successful delete unsuccesful insert iRet = " + iRet);
            return iRet;
        }
        return iRet;
    }
    private int DeleteOldIntersectionControllerDataLineFromDb(IntersectionControllerDataServer pC1 ) throws SQLException {
        int iRet;
        try {
            java.sql.PreparedStatement stmt;
            String SQL = "delete from IntersectionControllerData ";
            SQL = SQL + "where OmniaCode = " + pC1.getOmniaCode() + " and " +
                    " IntersectionId = " + pC1.getIntersectionId() + " and " +
                    " ControllerId = " + pC1.getControllerId() +  ";";
            logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            logger.info("Lines deleted iRet = " + iRet);
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
    public  int AddNewOmniaIntersectionControllerData(IntersectionControllerDataServer pOmniaData) throws SQLException {
        int iRet;
        java.sql.PreparedStatement stmt;
        try {
            InsertIntersectionControllerDataMySql st = new InsertIntersectionControllerDataMySql();
            String SQL =st.getStatement();
//            logger.info("SQL = " + SQL);
//            logger.info("pOmniaIntersectionData.toString()=" + pOmniaIntersectionData.toString());
            stmt = gSqlCon.prepareStatement(SQL);
            int pos;
            pos=1;
            stmt.setLong(pos,pOmniaData.getOmniaCode());
            pos=pos+1;
            stmt.setString(pos,pOmniaData.getOmniaName());
            pos=pos+1;
            stmt.setLong(pos,pOmniaData.getOmniaPublicationStatus());
            pos=pos+1;
            stmt.setLong(pos,pOmniaData.getIntersectionId());
            pos=pos+1;
            stmt.setString(pos,pOmniaData.getIntesectionDescription());
            pos=pos+1;
            stmt.setLong(pos,pOmniaData.getIntersectionAreaId());
            pos=pos+1;
            stmt.setLong(pos,pOmniaData.getIntersectionMaintenanceAreaId());
            pos=pos+1;
            stmt.setString(pos,pOmniaData.getIntersectionExternalCode());
            pos=pos+1;
            stmt.setLong(pos,pOmniaData.getIntersectionSubSystemId());
            pos=pos+1;
            stmt.setLong(pos,pOmniaData.getIntersectionVisible());
            pos=pos+1;
            stmt.setLong(pos,pOmniaData.getIntersectionDeleted());
            pos=pos+1;
            stmt.setString(pos,pOmniaData.getIntersectionDataPreviousUpdate());
            pos=pos+1;
            stmt.setString(pos,pOmniaData.getIntersectionGuid());
            pos=pos+1;
            stmt.setLong(pos,pOmniaData.getIntersectionProgressId());
            pos=pos+1;
            stmt.setLong(pos,pOmniaData.getControllerId());
            pos=pos+1;
            stmt.setString(pos,pOmniaData.getControllerDescription());
            pos=pos+1;
            stmt.setLong(pos,pOmniaData.getControllerTypeId());
            pos=pos+1;
            stmt.setLong(pos,pOmniaData.getControllerRoadsideUnitId());
            pos=pos+1;
            stmt.setString(pos,pOmniaData.getControllerExternalCode());
            pos=pos+1;
            stmt.setLong(pos,pOmniaData.getControllerSubSystemId());
            pos=pos+1;
            stmt.setLong(pos,pOmniaData.getControllerObjectPriorityId());
            pos=pos+1;
            stmt.setLong(pos,pOmniaData.getControllerVisible());
            pos=pos+1;
            stmt.setLong(pos,pOmniaData.getControllerDeleted());
            pos=pos+1;
            stmt.setString(pos,pOmniaData.getControllerDataPreviousUpdate());
            pos=pos+1;
            stmt.setString(pos,pOmniaData.getControllerGuid());
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
    public List<IntersectionControllerDataServer> GetOmniaIntersectionDataList()  {
        IntersectionControllerDataServer cc;
        if (OmniaIntersectionControllerDataUnits.isEmpty()) {
            cc= new IntersectionControllerDataServer();
            cc.MakeEmptyElement();
            OmniaIntersectionControllerDataUnits.add(cc);
        }
        return OmniaIntersectionControllerDataUnits;
    }
    public  int JsonOmniaIntersectionControllerSql(String pPermanentData) throws SQLException{
        int iRet;
        String strHelp1;
        try {
            Gson myGson = new Gson();
            MessageUtils mu = new MessageUtils();
            strHelp1 = mu.ThrowChecksumAway(pPermanentData);
            logger.info("strHelp1 = "+ strHelp1);
            JsonParser jsonParser = new JsonParser();
            IntersectionControllerDataServer aOmniaIntersection1 = myGson.fromJson(strHelp1, IntersectionControllerDataServer.class);
            SwarcoTimeUtilities sw = new  SwarcoTimeUtilities();
            String swarcoTime =sw.ToSwarcoTime(aOmniaIntersection1.getIntersectionDataPreviousUpdate());
            aOmniaIntersection1.setIntersectionDataPreviousUpdate(swarcoTime);
            swarcoTime = sw.ToSwarcoTime(aOmniaIntersection1.getControllerDataPreviousUpdate());
            aOmniaIntersection1.setControllerDataPreviousUpdate(swarcoTime);
            swarcoTime = sw.ToSwarcoTime(aOmniaIntersection1.getCreated());
            aOmniaIntersection1.setCreated(swarcoTime);
            iRet= DoesLineAlreadyExist(aOmniaIntersection1);
            if (iRet==INT_RET_NOT_FOUND) {
                iRet=AddNewOmniaIntersectionControllerData(aOmniaIntersection1);
                logger.info(" One line inserted iRet = " + iRet);
                if (iRet!=INT_RET_OK) {
                    logger.info("Unsuccessful OmniaIntersectionData insert iRet = " + iRet);
                }
                return iRet;
            } else if (iRet==INT_RET_FOUND) {
                iRet=IsItChanged(aOmniaIntersection1,getFoundRec());
                if (iRet==CHANGED) {
                    iRet =MakeDeleteInsert(aOmniaIntersection1);   // Keys are same
                    if (iRet!=INT_RET_OK) {
                        logger.info("Unsuccessful MakeDeleteInsert operation iRet = " + iRet);
                        return iRet;
                    }
                }
                return iRet;    // NOT_CHANGED
            } else {
                logger.info("Unsuccessful DoesLineAlreadyExsist insert iRet = " + iRet);
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







