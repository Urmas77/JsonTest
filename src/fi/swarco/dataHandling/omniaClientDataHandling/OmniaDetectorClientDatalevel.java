package fi.swarco.dataHandling.omniaClientDataHandling;
        import java.sql.*;
        import com.google.gson.Gson;
        import com.google.gson.JsonParser;
        import fi.swarco.SwarcoEnumerations;
        import fi.swarco.connections.SwarcoConnections;
        import fi.swarco.dataHandling.pojos.OmniaDetector;
        import fi.swarco.dataHandling.queriesSql.sqlServer.GetDetectorSqlServerData;
        import fi.swarco.omniaDataTransferServices.MessageUtils;
        import org.apache.commons.lang.exception.ExceptionUtils;
        import org.apache.log4j.Logger;
        import static fi.swarco.CONSTANT.*;
public class OmniaDetectorClientDatalevel {
    private static Logger logger = Logger.getLogger(OmniaDetectorClientDatalevel.class.getName());
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
    private OmniaDetector GetOmniaDetectorData (long plngDetectorId, String pstrTimestamp) throws SQLException {
// there is only one element
        String SQL="";
        java.sql.PreparedStatement stmt;
        logger.info(" SqlConnectionType =" + SqlConnectionType);
        logger.info("Start ");
        GetDetectorSqlServerData st = new GetDetectorSqlServerData();
        SQL =st.getStatement() ;
        logger.info("SqlConnectionTypeyyyy= "+ SqlConnectionType);
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
    public String GetDetectorJsonData(long plngDetectorId, String pstrTimestamp) throws SQLException {
        OmniaDetector ce = GetOmniaDetectorData(plngDetectorId, pstrTimestamp);
        String strHelp1 = GetDetectorJsonString(ce);


        return strHelp1;
    }
}
