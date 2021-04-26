package fi.swarco.dataHandling.omniaClientDataHandling;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.connections.SwarcoConnections;
import fi.swarco.dataHandling.pojos.OmniaDetector;
import fi.swarco.dataHandling.pojos.OmniaIntersectionControllerData;
import fi.swarco.dataHandling.queriesSql.sqlServer.GetControllerSqlServerData;
import fi.swarco.dataHandling.queriesSql.sqlServer.GetDetectorSqlServerData;
import fi.swarco.dataHandling.queriesSql.sqlServer.GetIntersectionControllerSqlServerData;
import fi.swarco.dataHandling.queriesSql.sqlServer.GetIntersectionSqlServerData;
import fi.swarco.omniaDataTransferServices.MessageUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import static fi.swarco.CONSTANT.DATABASE_CONNECTION_OK;
import static fi.swarco.CONSTANT.NO_VALUE;
public class OmniaIntesectionControllerClientDatalevel{
    private static Logger logger = Logger.getLogger(OmniaIntesectionControllerClientDatalevel.class.getName());
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
    private OmniaIntersectionControllerData GetOmniaIntesectionControllerData (long plngIntersectionId,long plngControllerId ,String pstrTimestamp) throws SQLException {
// there is only one element
        String SQL="";
        String strHelp1;
        java.sql.PreparedStatement stmt;
        logger.info(" SqlConnectionType =" + SqlConnectionType);
        logger.info("Start ");
        GetIntersectionControllerSqlServerData st = new GetIntersectionControllerSqlServerData();
        SQL =st.getStatement() ;
        logger.info("SqlConnectionTypeyyyy= "+ SqlConnectionType);
        logger.info("SQL = " +SQL);
        int pos=0;
        OmniaIntersectionControllerData ce;
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
                ce = new OmniaIntersectionControllerData();
                ce.setOmniaCode(rs.getLong(1));
                ce.setOmniaName(rs.getString(2));
                ce.setOmniaPublicationStatus(rs.getLong(3));
                ce.setIntersectionId(rs.getLong(4));
                ce.setIntesectionDescription(rs.getString(5));
                ce.setIntersectionAreaId(rs.getLong(6));
                ce.setIntersectionMaintenanceAreaId(rs.getLong(7));
                ce.setIntersectionExternalCode(rs.getString(8));
                ce.setIntersectionSubSystemId(rs.getLong(9));
                ce.setIntersectionVisible(rs.getLong(10));
                ce.setIntersectionDeleted(rs.getLong(11));
                ce.setIntersectionDataPreviousUpdate(rs.getString(12));
                strHelp1 = rs.getString(13);
                logger.info(" rs.getString(13) =" + strHelp1 );
                ce.setIntersectionGuid(rs.getString(13));
                logger.info(" rs.getLong(14) =" + rs.getLong(14));
                ce.setIntersectionProgressId(rs.getLong(14));
                logger.info(" rs.getLong(15) =" + rs.getLong(15));
                ce.setControllerId(rs.getLong(15));
                logger.info(" rs.getString(16) ="+ rs.getString(16));
                ce.setControllerDescription(rs.getString(16));
                ce.setControllerTypeId(rs.getLong(17));
                ce.setControllerRoadsideUnitId(rs.getLong(18));
                ce.setControllerExternalCode(rs.getString(19));
                ce.setControllerSubSystemId(rs.getLong(20));
                ce.setControllerObjectPriorityId(rs.getLong(21));
                ce.setControllerVisible(rs.getLong(22));
                ce.setControllerDeleted(rs.getLong(23));
                ce.setControllerDataPreviousUpdate(rs.getString(24));
                ce.setControllerGuid(rs.getString(25));
                stmt.close();
                rs.close();
                return ce;
            }
            stmt.close();
            rs.close();
            ce = new OmniaIntersectionControllerData();
            ce.MakeEmptyElement();
            return ce;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            ce= new OmniaIntersectionControllerData();
            ce.MakeEmptyElement();
            gSqlCon.close();
            return ce;
        }
    }
    private OmniaIntersectionControllerData GetOmniaIntesectionData (long plngIntersectionId,long plngControllerId ,String pstrTimestamp) throws SQLException {
// there is only one element
        String SQL="";
        java.sql.PreparedStatement stmt;
        logger.info(" SqlConnectionType =" + SqlConnectionType);
        logger.info("Start ");
        logger.info(" plngIntersectionId = " + plngIntersectionId  + " plngControllerId"  + plngControllerId + " pstrTimestamp = " + pstrTimestamp);
        GetIntersectionSqlServerData st = new GetIntersectionSqlServerData();
        SQL =st.getStatement() ;
        logger.info("SqlConnectionTypeyyyy= "+ SqlConnectionType);
        logger.info("SQL = " +SQL);
        int pos=0;
        OmniaIntersectionControllerData ce;
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
                ce = new OmniaIntersectionControllerData();
                ce.setOmniaCode(rs.getLong(1));
                ce.setOmniaName(rs.getString(2));
                ce.setOmniaPublicationStatus(rs.getLong(3));
                ce.setIntersectionId(rs.getLong(4));
                ce.setIntesectionDescription(rs.getString(5));
                ce.setIntersectionAreaId(rs.getLong(6));
                ce.setIntersectionMaintenanceAreaId(rs.getLong(7));
                ce.setIntersectionExternalCode(rs.getString(8));
                ce.setIntersectionSubSystemId(rs.getLong(9));
                ce.setIntersectionVisible(rs.getLong(10));
                ce.setIntersectionDeleted(rs.getLong(11));
                ce.setIntersectionDataPreviousUpdate(rs.getString(12));
                logger.info("  rs.getString(13) =" + rs.getString(13));
                ce.setIntersectionGuid(rs.getString(13));
                logger.info("  rs.getLong(14) =" + rs.getLong(14));
                ce.setIntersectionProgressId(rs.getLong(14));
                logger.info("  rs.getLong(15) =" + rs.getLong(15));
                ce.setControllerId(rs.getLong(15));
                logger.info("  rs.getString(16) =" + rs.getString(16));
                ce.setControllerDescription(rs.getString(16));
                ce.setControllerTypeId(rs.getLong(17));
                ce.setControllerRoadsideUnitId(rs.getLong(18));
                ce.setControllerExternalCode(rs.getString(19));
                ce.setControllerSubSystemId(rs.getLong(20));
                ce.setControllerObjectPriorityId(rs.getLong(21));
                ce.setControllerVisible(rs.getLong(22));
                ce.setControllerDeleted(rs.getLong(23));
                ce.setControllerDataPreviousUpdate(rs.getString(24));
                ce.setControllerGuid(rs.getString(25));
                stmt.close();
                rs.close();
                return ce;
            }
            stmt.close();
            rs.close();
            ce = new OmniaIntersectionControllerData();
            ce.MakeEmptyElement();
            return ce;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            ce= new OmniaIntersectionControllerData();
            ce.MakeEmptyElement();
            gSqlCon.close();
            return ce;
        }
    }
    private OmniaIntersectionControllerData GetOmniaIntesectionDataForOmniview (long plngIntersectionId,long plngControllerId ,String pstrTimestamp) throws SQLException {
// there is only one element
        String SQL="";
        java.sql.PreparedStatement stmt;
        logger.info(" SqlConnectionType =" + SqlConnectionType);
        logger.info("Start ");
        logger.info(" plngIntersectionId = " + plngIntersectionId  + " plngControllerId"  + plngControllerId + " pstrTimestamp = " + pstrTimestamp);
        GetIntersectionSqlServerData st = new GetIntersectionSqlServerData();
        SQL =st.getStatement() ;
        logger.info("SqlConnectionTypeyyyy= "+ SqlConnectionType);
        logger.info("SQL = " +SQL);
        int pos=0;
        OmniaIntersectionControllerData ce;
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
                ce = new OmniaIntersectionControllerData();
                logger.info("  rs.getLong(1) =" + rs.getLong(1));
                ce.setOmniaCode(rs.getLong(1));
                logger.info("  rs.getString(2) =" + rs.getString(2));
                ce.setOmniaName(rs.getString(2));
                logger.info("  rs.getLong(3) =" + rs.getLong(3));
                ce.setOmniaPublicationStatus(rs.getLong(3));
                logger.info("  rs.getLong(4) =" + rs.getLong(4));
                ce.setIntersectionId(rs.getLong(4));
                logger.info("  rs.getString(5) =" + rs.getString(5));
                ce.setIntesectionDescription(rs.getString(5));
                logger.info("  rs.getLong(6) =" + rs.getLong(6));
                ce.setIntersectionAreaId(rs.getLong(6));
                logger.info("  rs.getLong(7) =" + rs.getLong(7));
                ce.setIntersectionMaintenanceAreaId(rs.getLong(7));
                logger.info("  rs.getString(8) =" + rs.getString(8));
                ce.setIntersectionExternalCode(rs.getString(8));
                logger.info("  rs.getLong(9) =" + rs.getLong(9));
                ce.setIntersectionSubSystemId(rs.getLong(9));
                logger.info("  rs.getLong(10) =" + rs.getLong(10));
                ce.setIntersectionVisible(rs.getLong(10));
                logger.info("  rs.getLong(11) =" + rs.getLong(11));
                ce.setIntersectionDeleted(rs.getLong(11));
                logger.info("  rs.getString(12) =" + rs.getString(12));
                ce.setIntersectionDataPreviousUpdate(rs.getString(12));
                logger.info("  rs.getString(13) =" + rs.getString(13));
                ce.setIntersectionGuid(rs.getString(13));
                logger.info("  rs.getLong(14) =" + rs.getLong(14));
                ce.setControllerId(rs.getLong(14));
                logger.info("  rs.getString(15) =" + rs.getString(15));
                ce.setControllerDescription(rs.getString(15));
                logger.info("  rs.getLong(16) =" + rs.getLong(16));
                ce.setIntersectionProgressId(rs.getLong(16));
                logger.info("  rs.getLong(17) =" + rs.getLong(17));
                ce.setControllerTypeId(rs.getLong(17));
                logger.info("  rs.getLong(18) =" + rs.getLong(18));
                ce.setControllerRoadsideUnitId(rs.getLong(18));
                logger.info("  rs.getString(19) =" + rs.getString(19));
                ce.setControllerExternalCode(rs.getString(19));
                logger.info("  rs.getLong(20) =" + rs.getLong(20));
                ce.setControllerSubSystemId(rs.getLong(20));
                logger.info("  rs.getLong(21) =" + rs.getLong(21));
                ce.setControllerObjectPriorityId(rs.getLong(21));
                logger.info("  rs.getLong(22) =" + rs.getLong(22));
                ce.setControllerVisible(rs.getLong(22));
                logger.info("  rs.getString(23) =" + rs.getString(23));
                ce.setControllerDataPreviousUpdate(rs.getString(23));
                stmt.close();
                rs.close();
                return ce;
            }
            stmt.close();
            rs.close();
            ce = new OmniaIntersectionControllerData();
            ce.MakeEmptyElement();
            return ce;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            ce= new OmniaIntersectionControllerData();
            ce.MakeEmptyElement();
            gSqlCon.close();
            return ce;
        }
    }



    private OmniaIntersectionControllerData GetOmniaControllerData (long plngIntersectionId,long plngControllerId ,String pstrTimestamp) throws SQLException {
// there is only one element
        String SQL="";
        java.sql.PreparedStatement stmt;
        logger.info(" SqlConnectionType =" + SqlConnectionType);
        logger.info("Start ");
        GetControllerSqlServerData st = new GetControllerSqlServerData();
        SQL =st.getStatement() ;
        logger.info("SqlConnectionTypeyyyy= "+ SqlConnectionType);
        logger.info("SQL = " +SQL);
        int pos=0;
        OmniaIntersectionControllerData ce;
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
                ce = new OmniaIntersectionControllerData();
                ce.setOmniaCode(rs.getLong(1));
                ce.setOmniaName(rs.getString(2));
                ce.setOmniaPublicationStatus(rs.getLong(3));
                ce.setIntersectionId(rs.getLong(4));
                ce.setIntesectionDescription(rs.getString(5));
                ce.setIntersectionAreaId(rs.getLong(6));
                ce.setIntersectionMaintenanceAreaId(rs.getLong(7));
                ce.setIntersectionExternalCode(rs.getString(8));
                ce.setIntersectionSubSystemId(rs.getLong(9));
                ce.setIntersectionVisible(rs.getLong(10));
                ce.setIntersectionDeleted(rs.getLong(11));
                ce.setIntersectionDataPreviousUpdate(rs.getString(12));
                ce.setIntersectionGuid(rs.getString(13));
                ce.setIntersectionProgressId(rs.getLong(14));
                ce.setControllerId(rs.getLong(15));
                ce.setControllerDescription(rs.getString(16));
                ce.setControllerTypeId(rs.getLong(17));
                ce.setControllerRoadsideUnitId(rs.getLong(18));
                ce.setControllerExternalCode(rs.getString(19));
                ce.setControllerSubSystemId(rs.getLong(20));
                ce.setControllerObjectPriorityId(rs.getLong(21));
                ce.setControllerVisible(rs.getLong(22));
                ce.setControllerDeleted(rs.getLong(23));
                ce.setControllerDataPreviousUpdate(rs.getString(24));
                ce.setControllerGuid(rs.getString(25));
                stmt.close();
                rs.close();
                return ce;
            }
            stmt.close();
            rs.close();
            ce = new OmniaIntersectionControllerData();
            ce.MakeEmptyElement();
            return ce;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            ce= new OmniaIntersectionControllerData();
            ce.MakeEmptyElement();
            gSqlCon.close();
            return ce;
        }
    }
    private  String  GetIntersectionControllerJsonString(OmniaIntersectionControllerData pCe) {
        int iRet=0;
        String strHelp1=NO_VALUE;
        try {
            Gson myGson = new Gson();
            MessageUtils mu = new MessageUtils();
            logger.info("pCe.toString() = "+ pCe.toString());
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
    public String GetIntersectionControllerJsonData(long plngIntersectionId,long plngControllerId, String pstrTimestamp) throws SQLException {
        OmniaIntersectionControllerData ce =  GetOmniaIntesectionControllerData(plngIntersectionId,plngControllerId ,pstrTimestamp);
        String strHelp1 = GetIntersectionControllerJsonString(ce);
        return strHelp1;
    }

    public String GetControllerJsonData(long plngIntersectionId,long plngControllerId, String pstrTimestamp) throws SQLException {
        OmniaIntersectionControllerData ce =  GetOmniaControllerData(plngIntersectionId,plngControllerId ,pstrTimestamp);
        String strHelp1 = GetIntersectionControllerJsonString(ce);
        return strHelp1;
    }
    public String GetIntersectionJsonData(long plngIntersectionId,long plngControllerId, String pstrTimestamp) throws SQLException {

        OmniaIntersectionControllerData ce =  GetOmniaIntesectionData(plngIntersectionId,plngControllerId ,pstrTimestamp);
        String strHelp1 = GetIntersectionControllerJsonString(ce);
        return strHelp1;
    }
    public String GetIntersectionJsonDataForOmniview(long plngIntersectionId,long plngControllerId, String pstrTimestamp) throws SQLException {
// ************************
        OmniaIntersectionControllerData ce =  GetOmniaIntesectionDataForOmniview(plngIntersectionId,plngControllerId ,pstrTimestamp);
        String strHelp1 = GetIntersectionControllerJsonString(ce);
        return strHelp1;
    }
}
