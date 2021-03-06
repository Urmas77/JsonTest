package fi.swarco.dataHandling.omniaServerDataHandling;
import java.sql.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.connections.SwarcoConnections;
import fi.swarco.dataHandling.pojos.OmniaMeasurementData;
import fi.swarco.dataHandling.queriesSql.mySQL.InsertMeasurementDataMySql;
import fi.swarco.dataHandling.queriesSql.mySQL.SelectMeasurementDataMySqlWhere;
import fi.swarco.omniaDataTransferServices.MessageUtils;
import fi.swarco.serviceOperations.SwarcoTimeUtilities;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import static fi.swarco.CONSTANT.*;
public class OmniaMeasurementListDataLevel {
    static Logger logger = Logger.getLogger(OmniaMeasurementListDataLevel.class.getName());
    List<OmniaMeasurementData> OmniaMeasurementDataUnits = Collections.synchronizedList(new LinkedList<OmniaMeasurementData>());  // ????
    static Connection gSqlCon;
    private SwarcoEnumerations.ConnectionType SqlConnectionType=SwarcoEnumerations.ConnectionType.NOT_DEFINED;
    private SwarcoEnumerations.RequestOriginType requestOrigin= SwarcoEnumerations.RequestOriginType.NORMALROAD;
    private OmniaMeasurementData foundRec;
    public OmniaMeasurementData getFoundRec() { return foundRec;}
   public  void setFoundRec(OmniaMeasurementData pFoundRec) {foundRec=pFoundRec;}
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
    //********************************************************************************************************
    public int DoesLineAlreadyExsist(OmniaMeasurementData pOmniaMeasurementData) throws SQLException {
        String SQL="";
        java.sql.PreparedStatement stmt=null;
        OmniaMeasurementData oi = new OmniaMeasurementData();
        oi.MakeEmptyElement();
        setFoundRec(oi);
        try {
            SelectMeasurementDataMySqlWhere st = new SelectMeasurementDataMySqlWhere();
            SQL =st.getStatement();
            ResultSet rs;
            //logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            stmt.setLong(1,pOmniaMeasurementData.getOmniaCode());
            stmt.setLong(2,pOmniaMeasurementData.getIntersectionId());
            stmt.setLong(3,pOmniaMeasurementData.getControllerId());
            stmt.setLong(4,pOmniaMeasurementData.getDetectorId());
            stmt.setString(5,pOmniaMeasurementData.getMeasurementTime());
            rs = stmt.executeQuery();
            OmniaMeasurementData cc;
            while (rs.next()) {
                // logger.info("*****inside rsloop");
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
                if (((cc.getOmniaCode()==(pOmniaMeasurementData.getOmniaCode())) &&
                   (cc.getIntersectionId()==(pOmniaMeasurementData.getIntersectionId()) &&
                   (cc.getControllerId()==(pOmniaMeasurementData.getControllerId())) &&
                   (cc.getDetectorId()==(pOmniaMeasurementData.getDetectorId())) &&
                   (cc.getMeasurementTime().equals(pOmniaMeasurementData.getMeasurementTime()))))) {
                    logger.info(" l�yty");
                    setFoundRec(cc);
                    stmt.close();
                    rs.close();
                    return INT_RET_FOUND;
                }
            }
            logger.info("ei l�ytyny");
            stmt.close();
            rs.close();
            return INT_RET_NOT_FOUND;
        } catch(Exception e) {
            logger.info(" catch 11");
            logger.info(e.getMessage());
            e.printStackTrace();
            logger.info("Failed e=",e);
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            gSqlCon.close();
            return INT_RET_NOT_OK;
        }
    }
    public int IsItChanged(OmniaMeasurementData pC1 ,OmniaMeasurementData pC2) throws SQLException {
        int iRet =  NOT_CHANGED;
        if (!(pC1.getOmniaName().equals(pC2.getOmniaName()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getOmniaPublicationStatus()==(pC2.getOmniaPublicationStatus()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getDetectorTypeId()==(pC2.getDetectorTypeId()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getDetectorExternalCode().equals(pC2.getDetectorExternalCode()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getDetectorMaintenanceCode().equals(pC2.getDetectorMaintenanceCode()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getDetectorUnitId()==(pC2.getDetectorUnitId()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getDetectorDataPreviousUpdate().equals(pC2.getDetectorDataPreviousUpdate()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getDetectorDescription().equals(pC2.getDetectorDescription()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getMeasurementVehicleCount()==(pC2.getMeasurementVehicleCount()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getMeasurementMeanVehicleSpeed()==(pC2.getMeasurementMeanVehicleSpeed()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getMeasurementOccupancyProcent()==(pC2.getMeasurementOccupancyProcent()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getMeasurementAccurancy()==(pC2.getMeasurementAccurancy()))) {
            iRet =  CHANGED;
        }
        if (iRet==CHANGED) {
            logger.info("***Changed1  pC1.toString()" + pC1.toString());
            logger.info("***Changed2  pC2.toString()" + pC2.toString());
        }
        return iRet;
    }
    public int MakeDeleteInsert(OmniaMeasurementData pC1) throws SQLException {
        int iRet = INT_RET_OK;
        iRet = DeleteOldOmniaInterserctioDataLineFromDb(pC1);
        if (iRet < 0) {
            logger.info("iRet = " + iRet);
            iRet = UNSUCCESSFUL_DATABASE_DELETE_OPERATION;
            logger.info("iRet = " + iRet);
            return iRet;
        }
        iRet = AddNewOmniaMeasurementData(pC1);
        if (iRet < 0) {
            logger.info("***********************Successful delete unsuccesful insert iRet = " + iRet);
            iRet = UNSUCCESSFUL_DATABASE_DELETE_OPERATION;
            logger.info("***********************Successful delete unsuccesful insert iRet = " + iRet);
            return iRet;
        }
        return iRet;
    }
    private int DeleteOldOmniaInterserctioDataLineFromDb(OmniaMeasurementData pC1 ) throws SQLException {
        int iRet;
        String SQL = "";
        try {
            java.sql.PreparedStatement stmt;
            SQL = " delete from Omniameasurementdata ";
            SQL = SQL + "where OmniaCode = " + pC1.getOmniaCode() + " and " +
                    " IntersectionId = " + pC1.getIntersectionId() + " and " +
                    " ControllerId = " + pC1.getControllerId() + " and " +
                    " DetectorId = " + pC1.getDetectorId() + " and " +
                    " MeasurementTime = '" + pC1.getMeasurementTime()  +  "';";
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
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            e.printStackTrace();
            gSqlCon.close();
            return UNSUCCESSFUL_DATABASE_OPERATION;
        }
    }
    public static int AddNewOmniaMeasurementData(OmniaMeasurementData pOmniaMeasurementData) throws SQLException {
        int iRet;
        String SQL="";
        java.sql.PreparedStatement stmt;
        try {
            InsertMeasurementDataMySql st = new InsertMeasurementDataMySql();
            SQL =st.getStatement();
            stmt = gSqlCon.prepareStatement(SQL);
            int pos=0;
            pos=1;   // old was zero
            stmt.setLong(pos,pOmniaMeasurementData.getOmniaCode());
            pos=pos+1;
            stmt.setString(pos,pOmniaMeasurementData.getOmniaName());
            pos=pos+1;
            stmt.setLong(pos,pOmniaMeasurementData.getOmniaPublicationStatus());
            pos=pos+1;
            stmt.setLong(pos,pOmniaMeasurementData.getIntersectionId());
            pos=pos+1;
            stmt.setLong(pos,pOmniaMeasurementData.getControllerId());
            pos=pos+1;
            stmt.setString(pos,pOmniaMeasurementData.getMeasurementTime());  // ...Sql and setTimestamp ?
            pos=pos+1;
            stmt.setLong(pos,pOmniaMeasurementData.getDetectorId());
            pos=pos+1;
            stmt.setLong(pos,pOmniaMeasurementData.getDetectorTypeId());
            pos=pos+1;
            stmt.setString(pos,pOmniaMeasurementData.getDetectorExternalCode());
            pos=pos+1;
            stmt.setString(pos,pOmniaMeasurementData.getDetectorMaintenanceCode());
            pos=pos+1;
            stmt.setLong(pos,pOmniaMeasurementData.getDetectorUnitId());
            pos=pos+1;
            stmt.setString(pos,pOmniaMeasurementData.getDetectorDataPreviousUpdate());  //...Sql and setTimestamp ?
            pos=pos+1;
            stmt.setString(pos,pOmniaMeasurementData.getDetectorDescription());
            pos=pos+1;
            stmt.setLong(pos,pOmniaMeasurementData.getMeasurementVehicleCount());
            pos=pos+1;
            stmt.setDouble(pos,pOmniaMeasurementData.getMeasurementMeanVehicleSpeed());
            pos=pos+1;
            stmt.setDouble(pos,pOmniaMeasurementData.getMeasurementOccupancyProcent());
            pos=pos+1;
            stmt.setDouble(pos,pOmniaMeasurementData.getMeasurementAccurancy());
            iRet = stmt.executeUpdate();
            stmt.close();
            if (iRet!=1) {
                iRet=UNSUCCESSFUL_DATABASE_INSERT_OPERATION;
                return iRet;
            }
            return iRet;
        } catch(Exception e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            logger.info( "failed! {}", e );
            logger.info(" catch 11");
            logger.info(e.getMessage());
            e.printStackTrace();
            gSqlCon.close();
            return UNSUCCESSFUL_DATABASE_OPERATION;
        }
    }
    public  List<OmniaMeasurementData> GetOmniaMeasurementDataList()  {
        OmniaMeasurementData cc = new OmniaMeasurementData();
        if (OmniaMeasurementDataUnits.isEmpty()==true) {
            cc= new OmniaMeasurementData();
            cc.MakeEmptyElement();
            OmniaMeasurementDataUnits.add(cc);
        }
        return OmniaMeasurementDataUnits;
    }
    public  int JsonOmniaMeasurementSql(String pMeasurementsData) {
        int iRet=0;
        String strHelp1=NO_VALUE;
        try {
            Gson myGson = new Gson();
            MessageUtils mu = new MessageUtils();
            strHelp1 = mu.StripFileStartEnd(pMeasurementsData);
 //           logger.info("stripped strHelp1 = " + strHelp1);
            int iHere = strHelp1.indexOf("}");
            int iRound =1;
            int iHereOld=0;
            String strHelp2 = strHelp1.substring(0, iHere + 1);
            OmniaMeasurementData aO1 = new OmniaMeasurementData();
            SwarcoTimeUtilities sw = new  SwarcoTimeUtilities();
            String swarcoTime="";
            while  (iHere>0 ) {
                JsonParser jsonParser = new JsonParser();
                aO1 = myGson.fromJson(strHelp2, OmniaMeasurementData.class);
                swarcoTime =sw.ToSwarcoTime(aO1.getDetectorDataPreviousUpdate());
                aO1.setDetectorDataPreviousUpdate(swarcoTime);
                iRet= DoesLineAlreadyExsist(aO1);
                if (iRet==INT_RET_NOT_FOUND) {
                    iRet=iRet = AddNewOmniaMeasurementData(aO1);
                    //                  logger.info("  one line inserted iRet = " + iRet);
                    if (iRet!=INT_RET_OK) {
                        logger.info("Unsuccessful OmniameasurementData insert iRet = " + iRet);
                    }
                } else if (iRet==INT_RET_FOUND) {
                    iRet=IsItChanged(aO1,getFoundRec());
                    if (iRet==CHANGED) {
                        iRet =MakeDeleteInsert(aO1);   // Keys are same
                        if (iRet!=INT_RET_OK) {
                            logger.info("Unsuccessful MakeDeleteInsert operation iRet = " + iRet);
                            return iRet;
                        }
                    }
                } else {
                    logger.info("Unsuccessful DoesLineAlreadyExsist insert iRet = " + iRet);
                    return iRet;
                }
                iHereOld =iHere+1;
                strHelp1 = strHelp1.substring(iHereOld);
                iHere = strHelp1.indexOf("}");
                strHelp2 = strHelp1.substring(0, iHere + 1);
                iRound=iRound+1;
            }
        } catch (Exception e) {
            logger.info("e.getMessage()= " + e.getMessage());
            e.printStackTrace();
            logger.info( "failed! {}", e );
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            return iRet;
        }
        return iRet;
    }
}
