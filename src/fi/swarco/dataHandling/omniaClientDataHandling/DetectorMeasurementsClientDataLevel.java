package fi.swarco.dataHandling.omniaClientDataHandling;
import com.google.gson.*;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.connections.SwarcoConnections;
import fi.swarco.dataHandling.pojos.OmniaMeasurementData;
import fi.swarco.dataHandling.queriesSql.sqlServer.GetMeasurementSqlServerData;
import fi.swarco.omniaDataTransferServices.MessageUtils;
import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import static fi.swarco.CONSTANT.*;
public class DetectorMeasurementsClientDataLevel {
    private static Logger logger = Logger.getLogger(DetectorMeasurementsClientDataLevel.class.getName());
    private List<OmniaMeasurementData> DmUnits = Collections.synchronizedList(new LinkedList<OmniaMeasurementData>());  // ????
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
    public String GetMeasurementsDataString(long plngIntersectionId, long plngControllerId,String pstrTimestamp) throws SQLException{
        String strHelp1=NO_VALUE;
        int iRet = GetOmniaMeasurementsData(plngIntersectionId, plngControllerId, pstrTimestamp);
        if  (iRet ==INT_RET_OK) {
            strHelp1 = GetMeasurementsDataJsonString();
        }
        return strHelp1;
    }
    private int GetOmniaMeasurementsData(long plngIntersectionId, long plngControllerId,String pstrTimestamp) throws SQLException{
        DmUnits.clear();
        String SQL="";
        java.sql.PreparedStatement stmt;
    //    logger.info(" SqlConnectionType =" + SqlConnectionType);
    //    logger.info("Start ");
        GetMeasurementSqlServerData st= new GetMeasurementSqlServerData();
        SQL =st.getStatement() ;
     //   logger.info("SqlConnectionTypeyyyy= "+ SqlConnectionType);
     //   logger.info("SQL = " +SQL);
        //DetectorMeasurements ce;
        OmniaMeasurementData cc;
        int pos=0;
        try {
            stmt = gSqlCon.prepareStatement(SQL);
            pos=1;
            stmt.setLong(pos,plngIntersectionId);
            pos=pos+1;
            stmt.setLong(pos,plngControllerId);
            pos=pos+1;
            java.sql.Timestamp  tStamp=java.sql.Timestamp.valueOf(pstrTimestamp);
            stmt.setTimestamp(pos,tStamp);
            ResultSet rs;
            rs = stmt.executeQuery();
         //   logger.info(" rs.getFetchSize() = " + rs.getFetchSize());
            while (rs.next()) {
                cc= new OmniaMeasurementData();
                cc.MakeEmptyElement();
                cc.setOmniaCode(rs.getLong(1));
                cc.setOmniaName(rs.getString(2));
                cc.setOmniaPublicationStatus(rs.getLong(3));
                cc.setIntersectionId(rs.getLong(4));
                cc.setControllerId(rs.getLong(5));
                cc.setMeasurementTime(rs.getString(6));
                cc.setDetectorId(rs.getLong(7));
                cc.setDetectorTypeId(rs.getLong(8));
                cc.setDetectorExternalCode(rs.getString(9));
                cc.setDetectorMaintenanceCode(rs.getString(10));
                cc.setDetectorUnitId(rs.getLong(11));
                cc.setDetectorDataPreviousUpdate(rs.getString(12));
                cc.setDetectorDescription(rs.getString(13));
                cc.setMeasurementVehicleCount(rs.getLong(14));
                cc.setMeasurementMeanVehicleSpeed(rs.getLong(15));
                cc.setMeasurementOccupancyProcent(rs.getLong(16));
                cc.setMeasurementAccurancy(rs.getLong(17));
                DmUnits.add(cc);
            }
            stmt.close();
            rs.close();
            if (DmUnits.isEmpty()) {
                cc= new OmniaMeasurementData();
                cc.MakeEmptyElement();
                DmUnits.add(cc);
                return INT_RET_NOT_OK;
            }
       //     logger.info("bef ret iRet OK");
            return INT_RET_OK;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info("error e=",e);
            cc= new OmniaMeasurementData();
            cc.MakeEmptyElement();
            DmUnits.add(cc);
            gSqlCon.close();
            return INT_RET_NOT_OK;
        }
    }
    private String GetMeasurementsDataJsonString( ) {
        Gson myGson = new Gson();
        MessageUtils mu = new MessageUtils();
        String strHelp1 = NO_VALUE;
        String strHelp2 = "";
        OmniaMeasurementData aDetMea = new OmniaMeasurementData();
        aDetMea.MakeEmptyElement();
        for (int i = 0; i < DmUnits.size(); i++) {
            aDetMea = DmUnits.get(i);
     //       logger.info("i,DmUnits.toString() = " + i + "," + DmUnits.toString());
            strHelp1 = myGson.toJson(aDetMea);
            strHelp2 = strHelp2 + strHelp1;
            aDetMea.MakeEmptyElement();
        }
        if (strHelp1.equals(NO_VALUE)) {
            return NO_VALUE;
        }
        strHelp2= mu.AddBrackets(strHelp2);
        return strHelp2;
    }
    public  List<OmniaMeasurementData> GetDetectorMeasurementsDataList()  {
        OmniaMeasurementData cc;
        if (DmUnits.isEmpty()) {
            cc= new OmniaMeasurementData();
            cc.MakeEmptyElement();
            DmUnits.add(cc);
        }
        return DmUnits;
    }
}

