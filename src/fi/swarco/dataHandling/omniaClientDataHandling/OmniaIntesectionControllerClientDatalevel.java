package fi.swarco.dataHandling.omniaClientDataHandling;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.connections.SwarcoConnections;
import fi.swarco.dataHandling.pojos.OmniaDetector;
import fi.swarco.dataHandling.pojos.OmniaIntersectionControllerData;
import fi.swarco.dataHandling.queriesSql.sqlServer.GetDetectorSqlServerData;
import fi.swarco.dataHandling.queriesSql.sqlServer.GetIntersectionControllerSqlServerData;
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
}
