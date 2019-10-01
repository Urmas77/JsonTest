package fi.swarco.dataHandling.omniaClientDataHandling;
import com.google.gson.*;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.connections.SwarcoConnections;
import fi.swarco.dataHandling.pojos.OmniaMeasurementDataShort;
import fi.swarco.dataHandling.queriesSql.sqlServer.GetMeasurementShortSqlServerData;
import fi.swarco.omniaDataTransferServices.MessageUtils;
import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import static fi.swarco.CONSTANT.*;
public class DetectorMeasurementsShortClientDataLevel {
    private static Logger logger = Logger.getLogger(DetectorMeasurementsShortClientDataLevel.class.getName());
    private List<OmniaMeasurementDataShort> DmUnits = Collections.synchronizedList(new LinkedList<OmniaMeasurementDataShort>());  // ????
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
    public String GetMeasurementsDataString(long plngIntersectionId, long plngControllerId,String pstrTimestamp) {
        String strHelp1=NO_VALUE;
        int iRet = GetOmniaMeasurementsDataShort(plngIntersectionId, plngControllerId, pstrTimestamp);
        if  (iRet ==INT_RET_OK) {
            strHelp1 = GetMeasurementsDataShortJsonString();
        }
        return strHelp1;
    }
    private int GetOmniaMeasurementsDataShort(long plngIntersectionId, long plngControllerId,String pstrTimestamp) {
        DmUnits.clear();
        String SQL="";
        java.sql.PreparedStatement stmt;
        logger.info(" SqlConnectionType =" + SqlConnectionType);
        logger.info("Start ");
 //       GetMeasurementSqlServerData st= new GetMeasurementSqlServerData();
        GetMeasurementShortSqlServerData st= new GetMeasurementShortSqlServerData();
        SQL =st.getStatement() ;
        logger.info("SqlConnectionTypeyyyy= "+ SqlConnectionType);
        logger.info("SQL = " +SQL);
        //DetectorMeasurements ce;
        OmniaMeasurementDataShort cc;
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
            logger.info(" rs.getFetchSize() = " + rs.getFetchSize());
            while (rs.next()) {
                cc= new OmniaMeasurementDataShort();
                cc.MakeEmptyElement();
                cc.setOmniaCode(rs.getLong(1));
                cc.setIntersectionId(rs.getLong(2));
                cc.setControllerId(rs.getLong(3));
                cc.setMeasurementTime(rs.getString(4));
                cc.setDetectorId(rs.getLong(5));
                cc.setDetectorExternalCode(rs.getString(5));
                cc.setVehicleCount(rs.getLong(7));
                cc.setMeanVehicleSpeed(rs.getLong(8));
                cc.setOccupancyProcent(rs.getLong(9));
                cc.setAccurancy(rs.getLong(10));
                DmUnits.add(cc);
            }
            if (DmUnits.isEmpty()) {
                cc= new OmniaMeasurementDataShort();
                cc.MakeEmptyElement();
                DmUnits.add(cc);
                return INT_RET_NOT_OK;
            }
            logger.info("bef ret iRet OK");
            return INT_RET_OK;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info("error e=",e);
            cc= new OmniaMeasurementDataShort();
            cc.MakeEmptyElement();
            DmUnits.add(cc);
            return INT_RET_NOT_OK;
        }
    }
    private String GetMeasurementsDataShortJsonString( ) {
        Gson myGson = new Gson();
        MessageUtils mu = new MessageUtils();
        String strHelp1 = NO_VALUE;
        String strHelp2 = "";
        OmniaMeasurementDataShort aDetMea = new OmniaMeasurementDataShort();
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
//        logger.info("kkkkkkk strHelp2 = "+  strHelp2);
        strHelp2= mu.AddBrackets(strHelp2);
//        logger.info("kkkkkkk strHelp2 = " + strHelp2);
        return strHelp2;
    }
    public  List<OmniaMeasurementDataShort> GetDetectorMeasurementsDataList()  {
        OmniaMeasurementDataShort cc = new OmniaMeasurementDataShort();
        if (DmUnits.isEmpty()) {
            cc= new OmniaMeasurementDataShort();
            cc.MakeEmptyElement();
            DmUnits.add(cc);
        }
        return DmUnits;
    }
}


