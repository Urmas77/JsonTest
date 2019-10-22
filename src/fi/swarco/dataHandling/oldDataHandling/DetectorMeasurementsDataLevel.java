package fi.swarco.dataHandling.oldDataHandling;
import com.google.gson.*;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.connections.SwarcoConnections;
import fi.swarco.dataHandling.pojos.oLdPojos.DetectorMeasurements;
import fi.swarco.dataHandling.queriesSql.mySQL.SelectDetectorMeasurementsMySql;
import fi.swarco.messageHandling.ParameterWrapper;
import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import static fi.swarco.CONSTANT.*;
public class DetectorMeasurementsDataLevel {
    private static Logger logger = Logger.getLogger(DetectorMeasurementsDataLevel.class.getName());
    List<DetectorMeasurements> DmUnits = Collections.synchronizedList(new LinkedList<DetectorMeasurements>());  // ????
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
    public String MakeWhereCurrentState(ParameterWrapper pPw) {
        String strHelp1 = NO_VALUE;
        if (pPw.getOmniaCodeCounter() == 1) {
            strHelp1 = " where OmniaCode=" + pPw.getOmniaCode() + " ";
        }
        if (pPw.getOmniaNameCounter() == 1) {
            strHelp1 = " where OmniaName='" + pPw.getOmniaName() + "'";
        }
        if (pPw.getIntersectionIdCounter() == 1) {
            strHelp1 = strHelp1 + " and IntersectionId=" + pPw.getIntersectionId() + " ";
        }
        if (pPw.getIntersectionDescriptionCounter() == 1) {
            strHelp1 = strHelp1 + " and IntersectionDescription='" + pPw.getIntersectionDescription() + "'";
        }
        if (pPw.getControllerIdCounter() == 1) {
            strHelp1 = strHelp1 + " and ControllerId=" + pPw.getControllerId() + " ";
        }
        if (pPw.getControllerExternalCodeCounter() == 1) {
            strHelp1 = strHelp1 + " and ControllerExternalCode='" + pPw.getControllerExternalCode() + "'";
        }
        if (pPw.getDetectorIdCounter() == 1) {
            strHelp1 = strHelp1 + " and DetectorId=" + pPw.getDetectorId() + " ";
        }
        if (pPw.getDetectorExternalCodeCounter() == 1) {
            strHelp1 = strHelp1 + " and DetectorExternalCode='" + pPw.getDetectorExternalCode() + "'";
        }
        if (pPw.getStartTimeCounter() == 1) {
            strHelp1 = strHelp1 + " and  measurementTime>='" + pPw.getStartTime() + "'";
        }
        if (pPw.getEndTimeCounter() == 1) {
            strHelp1 = strHelp1 + " and  measurementTime<='" + pPw.getEndTime() + "'";
        }
        logger.info("********************** where strHelp1 =" + strHelp1);
        if (strHelp1.equals(NO_VALUE)) {
            return strHelp1;
        }
        return strHelp1+";";
    }
    public int DetectorMeasurementsDataListWhere (String strWhere) {
        DmUnits.clear();
        String SQL="";
        java.sql.PreparedStatement stmt;
        logger.info("Start ");
        SelectDetectorMeasurementsMySql st = new SelectDetectorMeasurementsMySql();
        SQL =st.getStatement() + strWhere ;
        logger.info("SQL = " +SQL);
        DetectorMeasurements ce;
        try {
            stmt = gSqlCon.prepareStatement(SQL);
            ResultSet rs;
            rs = stmt.executeQuery();
            logger.info(" rs.getFetchSize() = " + rs.getFetchSize());
            while (rs.next()) {
                ce= new DetectorMeasurements();
                ce.setOmniaCode(rs.getLong(1));
                ce.setOmniaName(rs.getString(2));
                ce.setIntersectionId(rs.getLong(3));
                ce.setIntersectionDescription(rs.getString(4));
                ce.setControllerId(rs.getLong(5));
                ce.setControllerExternalCode(rs.getString(6));
                ce.setDetectorId(rs.getLong(7));
                ce.setDetectorExternalCode(rs.getString(8));
                ce.setMeasurementTime(rs.getString(9));
                ce.setMeasurementVehicleCount(rs.getLong(10));
                ce.setMeasurementMeanVehicleSpeed(rs.getDouble(11));
                ce.setMeasurementOccupancyProcent(rs.getDouble(12));
                ce.setMeasurementAccurancy(rs.getDouble(13));
                DmUnits.add(ce);
            }
            if (DmUnits.isEmpty()) {
                ce= new DetectorMeasurements();
                ce.MakeEmptyElement();
                DmUnits.add(ce);
                return INT_RET_NOT_OK;
            }
            logger.info("bef ret iRet OK");
            return INT_RET_OK;
        } catch (SQLException e) {
            logger.info("error e=",e);
            e.printStackTrace();
            ce= new DetectorMeasurements();
            ce.MakeEmptyElement();
            DmUnits.add(ce);
            return INT_RET_NOT_OK;
        }
    }
    public int DetectorMeasurementsDataList () {
        DmUnits.clear();
        String SQL="";
        java.sql.PreparedStatement stmt;
        logger.info(" SqlConnectionType =" + SqlConnectionType);
        logger.info("Start ");
        SelectDetectorMeasurementsMySql st = new SelectDetectorMeasurementsMySql();
        SQL =st.getStatement() ;
        logger.info("SqlConnectionTypeyyyy= "+ SqlConnectionType);
        logger.info("SQL = " +SQL);
        DetectorMeasurements ce;
        try {
            stmt = gSqlCon.prepareStatement(SQL);
            ResultSet rs;
            rs = stmt.executeQuery();
            logger.info(" rs.getFetchSize() = " + rs.getFetchSize());
            while (rs.next()) {
                ce= new DetectorMeasurements();
                ce.setOmniaCode(rs.getLong(1));
                ce.setOmniaName(rs.getString(2));
                ce.setIntersectionId(rs.getLong(3));
                ce.setIntersectionDescription(rs.getString(4));
                ce.setControllerId(rs.getLong(5));
                ce.setControllerExternalCode(rs.getString(6));
                ce.setDetectorId(rs.getLong(7));
                ce.setDetectorExternalCode(rs.getString(8));
                ce.setMeasurementTime(rs.getString(9));
                ce.setMeasurementVehicleCount(rs.getLong(10));
                ce.setMeasurementMeanVehicleSpeed(rs.getDouble(11));
                ce.setMeasurementOccupancyProcent(rs.getDouble(12));
                ce.setMeasurementAccurancy(rs.getDouble(13));
                DmUnits.add(ce);
            }
            if (DmUnits.isEmpty()) {
                ce= new DetectorMeasurements();
                ce.MakeEmptyElement();
                DmUnits.add(ce);
                return INT_RET_NOT_OK;
            }
            logger.info("bef ret iRet OK");
            return INT_RET_OK;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info("error e=",e);
            ce= new DetectorMeasurements();
            ce.MakeEmptyElement();
            DmUnits.add(ce);
            return INT_RET_NOT_OK;
        }
    }
    public  List<DetectorMeasurements> GetDetectorMeasurementsDataList()  {
        DetectorMeasurements cc = new DetectorMeasurements();
        if (DmUnits.isEmpty()) {
            cc= new DetectorMeasurements();
            cc.MakeEmptyElement();
            DmUnits.add(cc);
        }
        return DmUnits;
    }
    public String MakeJsonString(List<DetectorMeasurements> pDu ) {
        Gson myGson = new Gson();
        String strHelp1 = NO_VALUE;
        String strHelp2 = "";
        DetectorMeasurements aDetMea = new DetectorMeasurements();
        aDetMea.MakeEmptyElement();
        for (int i = 0; i < pDu.size(); i++) {
            aDetMea = pDu.get(i);
      //      logger.info("i,aDetMea.toString() = " + i + "," + aDetMea.toString());
            strHelp1 = myGson.toJson(aDetMea);
            strHelp2 = strHelp2 + strHelp1;
            aDetMea.MakeEmptyElement();
        }
        if (strHelp1.equals(NO_VALUE)) {
            return NO_VALUE;
        }
        return strHelp2;
    }
}
