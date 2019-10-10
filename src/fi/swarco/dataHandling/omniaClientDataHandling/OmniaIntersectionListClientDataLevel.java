package fi.swarco.dataHandling.omniaClientDataHandling;
import java.sql.*;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.connections.SwarcoConnections;
import fi.swarco.dataHandling.pojos.OmniaIntersectionData;
import fi.swarco.dataHandling.pojos.OmniaIntersectionDataClient;
import fi.swarco.dataHandling.queriesSql.sqlServer.GetPermanentSqlServerData;
import fi.swarco.omniaDataTransferServices.MessageUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import static fi.swarco.CONSTANT.*;
public class OmniaIntersectionListClientDataLevel {
    private static Logger logger = Logger.getLogger(OmniaIntersectionListClientDataLevel.class.getName());
    static Connection gSqlCon;
    private SwarcoEnumerations.ConnectionType SqlConnectionType=SwarcoEnumerations.ConnectionType.NOT_DEFINED;
    private SwarcoEnumerations.RequestOriginType requestOrigin= SwarcoEnumerations.RequestOriginType.NORMALROAD;
    public void setRequestOrigin(SwarcoEnumerations.RequestOriginType prequestOrigin) {
        requestOrigin=prequestOrigin;
    }
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
    private OmniaIntersectionDataClient GetOmniaPermanentData (long plngIntersectionId, long plngControllerId,String pstrTimestamp) throws SQLException {
// there is only one element
        String SQL="";
        java.sql.PreparedStatement stmt;
        logger.info(" SqlConnectionType =" + SqlConnectionType);
        logger.info("Start ");
        GetPermanentSqlServerData st = new GetPermanentSqlServerData();
        SQL =st.getStatement() ;
        logger.info("SqlConnectionTypeyyyy= "+ SqlConnectionType);
        logger.info("SQL = " +SQL);
        int pos=0;
        OmniaIntersectionDataClient ce;
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
                ce = new OmniaIntersectionDataClient();
                ce.setOmniaCode(rs.getLong(1));
                ce.setOmniaName(rs.getString(2));
                ce.setOmniaPublicationStatus(rs.getLong(3));
                ce.setIntersectionId(rs.getLong(4));
                ce.setIntesectionDescription(rs.getString(5));
                ce.setIntersectionAreaId(rs.getLong(6));
                ce.setIntersectionExternalCode(rs.getString(7));
                ce.setIntersectionDataPreviousUpdate(rs.getString(8));   // ...Sql Timestamp ?
                ce.setControllerId(rs.getLong(9));
                ce.setControllerDescription(rs.getString(10));
                ce.setControllerExternalCode(rs.getString(11));
                ce.setControllerDataPreviousUpdate(rs.getString(12));  // ...Sql Timestamp ?
                stmt.close();
                rs.close();
                return ce;
            }
            stmt.close();
            rs.close();
            ce = new OmniaIntersectionDataClient();
            ce.MakeEmptyElement();
            return ce;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            ce= new OmniaIntersectionDataClient();
            ce.MakeEmptyElement();
            gSqlCon.close();
            return ce;
        }
    }
    private  String  GetpermanentDataJsonString(OmniaIntersectionDataClient pCe) {
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
    public String GetPermanentJsonData(long plngIntersectionId, long plngControllerId,String pstrTimestamp) throws SQLException {
        OmniaIntersectionDataClient ce = GetOmniaPermanentData(plngIntersectionId, plngControllerId, pstrTimestamp);
        String strHelp1 = GetpermanentDataJsonString(ce);
        return strHelp1;
    }
}
