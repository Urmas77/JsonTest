package fi.swarco.dataHandling.omniaClientDataHandling;
import com.google.gson.*;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.connections.SwarcoConnections;
import fi.swarco.dataHandling.pojos.OmniaMeasurementDataShort;
import fi.swarco.dataHandling.pojos.OmniaMeasurementDataShortJson;
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
    private List<OmniaMeasurementDataShort> DmUnits = Collections.synchronizedList(new LinkedList<>());  // ????
    private static Connection gSqlCon;
    private SwarcoEnumerations.ConnectionType SqlConnectionType=SwarcoEnumerations.ConnectionType.NOT_DEFINED;
    private SwarcoEnumerations.RequestOriginType requestOrigin= SwarcoEnumerations.RequestOriginType.NORMALROAD;
    public void setRequestOrigin(SwarcoEnumerations.RequestOriginType prequestOrigin) {
        requestOrigin=prequestOrigin;
    }
    public  int MakeConnection(SwarcoEnumerations.ConnectionType pSqlCon) {
        SwarcoConnections vg = new SwarcoConnections();
 //       logger.info("pSqlCon = "+ pSqlCon);
        int iRet = vg.MakeConnection(pSqlCon);
        if (iRet!=1) {
            return iRet;
        }
        SqlConnectionType=pSqlCon;
 //       logger.info("SqlConnectionType = " + SqlConnectionType);
        gSqlCon = vg.getSqlCon();
        return DATABASE_CONNECTION_OK;
    }
    public String GetMeasurementsDataString(long plngIntersectionId, long plngControllerId,String pstrTimestamp) throws SQLException{
        String strHelp1=NO_VALUE;
        int iRet = GetOmniaMeasurementsDataShort(plngIntersectionId, plngControllerId, pstrTimestamp);
        if  (iRet ==INT_RET_OK) {
            strHelp1 = GetMeasurementsDataShortJsonString();
        }
        return strHelp1;
    }
    private int GetOmniaMeasurementsDataShort(long plngIntersectionId, long plngControllerId,String pstrTimestamp) throws SQLException{
        DmUnits.clear();
        java.sql.PreparedStatement stmt;
        logger.info("Start ");
        GetMeasurementShortSqlServerData st= new GetMeasurementShortSqlServerData();
        String SQL = st.getStatement();
        OmniaMeasurementDataShort cc;
        int pos;
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
                cc.setDetectorExternalCode(rs.getString(6));   // old column value was 5   = DetectorId
                cc.setVehicleCount(rs.getLong(7));
                cc.setMeanVehicleSpeed(rs.getDouble(8));
                cc.setOccupancyProcent(rs.getDouble(9));
                cc.setAccurancy(rs.getDouble(10));
                 DmUnits.add(cc);
            }
            stmt.close();
            rs.close();
            if (DmUnits.isEmpty()) {
                cc= new OmniaMeasurementDataShort();
                cc.MakeEmptyElement();
                DmUnits.add(cc);
                return INT_RET_NOT_OK;
            }
   //         logger.info("bef ret iRet OK");
            return INT_RET_OK;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info("error e=",e);
            cc= new OmniaMeasurementDataShort();
            cc.MakeEmptyElement();
            DmUnits.add(cc);
            gSqlCon.close();
            return INT_RET_NOT_OK;
        }
    }
    private String GetMeasurementsDataShortJsonString( ){
        Gson myGson = new Gson();
        MessageUtils mu = new MessageUtils();
        String strHelp1 = NO_VALUE;
        String strHelp2 = "";
        String strHelpSpare="";
        OmniaMeasurementDataShort aDetMea = new OmniaMeasurementDataShort();
        OmniaMeasurementDataShortJson aDetTran = new OmniaMeasurementDataShortJson();
        aDetMea.MakeEmptyElement();
        aDetTran.MakeEmptyElement();
        for (OmniaMeasurementDataShort dmUnit : DmUnits) {
            aDetMea = dmUnit;
            aDetTran = aDetMea.SetJsonTransferItem();
            //           logger.info("aDetTran.toString().length() = " + aDetTran.toString().length());
            strHelp1 = myGson.toJson(aDetTran);
            if (strHelpSpare.equals(strHelp1)) {
                logger.info("**** not addaed got it strHelpSpare = " + strHelpSpare);
                logger.info("**** not added got it strHelp1 = " + strHelp1);
                // do not add anything here
                // System.exit(99);
            } else {
               // strHelpSpare = strHelp1;
                logger.info("evevery ****** strHelpSpare = " + strHelpSpare);
                strHelp2 = strHelp2 + strHelp1;
            }
            strHelpSpare = strHelp1;
            aDetMea.MakeEmptyElement();
        }
        if (strHelp1.equals(NO_VALUE)) {
            return NO_VALUE;
        }
        strHelp2= mu.AddBrackets(strHelp2);
        return strHelp2;
    }
    public  List<OmniaMeasurementDataShort> GetDetectorMeasurementsDataList()  {
        OmniaMeasurementDataShort cc;
        if (DmUnits.isEmpty()) {
            cc= new OmniaMeasurementDataShort();
            cc.MakeEmptyElement();
            DmUnits.add(cc);
        }
        return DmUnits;
    }
}


