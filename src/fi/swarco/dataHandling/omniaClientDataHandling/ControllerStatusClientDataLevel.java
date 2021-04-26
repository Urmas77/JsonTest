package fi.swarco.dataHandling.omniaClientDataHandling;
import com.google.gson.*;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.connections.SwarcoConnections;
import fi.swarco.dataHandling.pojos.OmniaControllerStatusData;
import fi.swarco.dataHandling.pojos.OmniaControllerStatusDataJson;
import fi.swarco.dataHandling.pojos.OmniaMeasurementDataShort;
import fi.swarco.dataHandling.pojos.OmniaMeasurementDataShortJson;
import fi.swarco.dataHandling.queriesSql.sqlServer.GetControllerStatusSqlServerDataGroup;
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
public class ControllerStatusClientDataLevel {
    private static Logger logger = Logger.getLogger(ControllerStatusClientDataLevel.class.getName());
    private List<OmniaControllerStatusData> DmUnits = Collections.synchronizedList(new LinkedList<>());  // ????
    private static Connection gSqlCon;
    private SwarcoEnumerations.ConnectionType SqlConnectionType=SwarcoEnumerations.ConnectionType.NOT_DEFINED;
    private SwarcoEnumerations.RequestOriginType requestOrigin= SwarcoEnumerations.RequestOriginType.NORMALROAD;
    public List<OmniaControllerStatusData>  getListOfOmniaControllerStatusData() {return DmUnits;};
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
    public String GetOmniaControllerStatusDataString(long plngIntersectionId,String pstrTimestamp) throws SQLException{
        String strHelp1=NO_VALUE;
        int iRet = GetOmniaControllerStatusData(plngIntersectionId, pstrTimestamp);
        if  (iRet ==INT_RET_OK) {
            strHelp1 = GetControllerStatusStringJson();
        }
        return strHelp1;
    }
    public String GetControllerStatusDataGroupString(String pstrTimestamp) throws SQLException{
        String strHelp1=NO_VALUE;
        int iRet = GetOmniaControllerStatusDataGroup(pstrTimestamp);
        if  (iRet ==INT_RET_OK) {
            strHelp1 = GetControllerStatusStringJson();
        }
        return strHelp1;
    }
    private int GetOmniaControllerStatusDataGroup(String pstrTimestamp) throws SQLException{
        DmUnits.clear();
        java.sql.PreparedStatement stmt;
 //       logger.info("Start ");
        GetControllerStatusSqlServerDataGroup st= new GetControllerStatusSqlServerDataGroup();
        String SQL = st.getStatement();
        OmniaControllerStatusData cc;
        int pos;
        try {
            stmt = gSqlCon.prepareStatement(SQL);
            pos=1;
            java.sql.Timestamp  tStamp=java.sql.Timestamp.valueOf(pstrTimestamp);
            stmt.setTimestamp(pos,tStamp);
            ResultSet rs;
            rs = stmt.executeQuery();
   //         logger.info(" rs.getFetchSize() = " + rs.getFetchSize());
            while (rs.next()) {
                cc= new OmniaControllerStatusData();
                cc.MakeEmptyElement();
                cc.setOmniaCode(rs.getLong(1));
                cc.setIntersectionId(rs.getLong(2));
                cc.setControllerId(rs.getLong(2));
                cc.setMeasurementTime(rs.getString(3));
                cc.setIntersectionCode(rs.getString(4));
                cc.setControllerProgramNumber(rs.getLong(5));
                cc.setControllerProgramDescription(rs.getString(6));
                cc.setControllerStatus(rs.getString(7));
                cc.setTransferTime(rs.getString(8));
                cc.setTransferState(rs.getLong(9));
                DmUnits.add(cc);
            }
            stmt.close();
            rs.close();
            if (DmUnits.isEmpty()) {
                cc= new OmniaControllerStatusData();
                cc.MakeEmptyElement();
                DmUnits.add(cc);
                return INT_RET_NOT_OK;
            }
            //         logger.info("bef ret iRet OK");
            return INT_RET_OK;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info("error e=",e);
            cc= new OmniaControllerStatusData();
            cc.MakeEmptyElement();
            DmUnits.add(cc);
            gSqlCon.close();
            return INT_RET_NOT_OK;
        }
    }
    private int GetOmniaControllerStatusData(long plngIntersectionId, String pstrTimestamp) throws SQLException{
        DmUnits.clear();
        java.sql.PreparedStatement stmt;
        logger.info("Start ");
        GetControllerStatusSqlServerDataGroup st= new GetControllerStatusSqlServerDataGroup();
        String SQL = st.getStatement();
        OmniaControllerStatusData cc;
        int pos;
        try {
            stmt = gSqlCon.prepareStatement(SQL);
            pos=1;
            stmt.setLong(pos,plngIntersectionId);
            pos=pos+1;
            java.sql.Timestamp  tStamp=java.sql.Timestamp.valueOf(pstrTimestamp);
            stmt.setTimestamp(pos,tStamp);
            ResultSet rs;
            rs = stmt.executeQuery();
            logger.info(" rs.getFetchSize() = " + rs.getFetchSize());
            while (rs.next()) {
                cc= new  OmniaControllerStatusData();
                cc.MakeEmptyElement();
                cc.setOmniaCode(rs.getLong(1));
                cc.setIntersectionId(rs.getLong(2));
                cc.setControllerId(rs.getLong(3));
                cc.setMeasurementTime(rs.getString(4));
                cc.setIntersectionCode(rs.getString(5));
                cc.setControllerProgramNumber(rs.getLong(6));
                cc.setControllerProgramDescription(rs.getString(7));
                cc.setControllerStatus(rs.getString(8));
                cc.setTransferTime(rs.getString(9));
                cc.setTransferState(rs.getLong(10));
                DmUnits.add(cc);
            }
            stmt.close();
            rs.close();
            if (DmUnits.isEmpty()) {
                cc= new OmniaControllerStatusData();
                cc.MakeEmptyElement();
                DmUnits.add(cc);
                return INT_RET_NOT_OK;
            }
            //         logger.info("bef ret iRet OK");
            return INT_RET_OK;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info("error e=",e);
            cc= new OmniaControllerStatusData();
            cc.MakeEmptyElement();
            DmUnits.add(cc);
            gSqlCon.close();
            return INT_RET_NOT_OK;
        }
    }
    private String GetControllerStatusStringJson( ){
        Gson myGson = new Gson();  //RETHINK NOT ADDED LOGIN
        MessageUtils mu = new MessageUtils();
        String strHelp1 = NO_VALUE;
        String strHelp2 = "";
        String strHelpSpare="";
        OmniaControllerStatusData aDetMea = new OmniaControllerStatusData();
        OmniaControllerStatusDataJson aDetTran = new OmniaControllerStatusDataJson();
        aDetMea.MakeEmptyElement();
        aDetTran.MakeEmptyElement();
        for (OmniaControllerStatusData dmUnit : DmUnits) {
            aDetMea = dmUnit;
            aDetTran = aDetMea.SetJsonTransferItem();
            //           logger.info("aDetTran.toString().length() = " + aDetTran.toString().length());
            strHelp1 = myGson.toJson(aDetTran);
            if (strHelpSpare.equals(strHelp1)) {
                //    logger.info("**** not addad got it strHelpSpare = " + strHelpSpare);
                //    logger.info("**** not added got it strHelp1 = " + strHelp1);
                // do not add anything here
                // System.exit(99);
            } else {
                // strHelpSpare = strHelp1;
                //  logger.info("evevery ****** strHelpSpare = " + strHelpSpare);
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
    public  List<OmniaControllerStatusData> GetControllerStatusDataList()  {
        OmniaControllerStatusData cc;
        if (DmUnits.isEmpty()) {
            cc= new OmniaControllerStatusData();
            cc.MakeEmptyElement();
            DmUnits.add(cc);
        }
        return DmUnits;
    }
}
