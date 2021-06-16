package fi.swarco.dataHandling.omniaClientDataHandling;
import java.sql.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import com.google.gson.Gson;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.connections.SwarcoConnections;
import fi.swarco.dataHandling.omniaServerDataHandling.OmniaMeasurementListDataLevel;
import fi.swarco.dataHandling.pojos.OmniaMeasurementDataShort;
import fi.swarco.dataHandling.pojos.OmniaMeasurementDataShortJson;
import fi.swarco.dataHandling.queriesSql.mySQL.InsertOmniaMeasurementDataShortMySql;
import fi.swarco.dataHandling.queriesSql.mySQL.InsertOmniaMeasurementDataShortMySqlLahti;
import fi.swarco.dataHandling.queriesSql.mySQL.SelectMeasurementDataShortMySqlWhere;
import fi.swarco.dataHandling.queriesSql.mySQL.SelectMeasurementDataShortMySqlWhereLahti;
import fi.swarco.omniaDataTransferServices.MessageUtils;
import fi.swarco.serviceOperations.SwarcoTimeUtilities;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import static fi.swarco.CONSTANT.*;
public class OmniaMeasurementShortListDataLevel {
    static Logger logger = Logger.getLogger(OmniaMeasurementListDataLevel.class.getName());
    List<OmniaMeasurementDataShort> OmniaMeasurementDataUnits = Collections.synchronizedList(new LinkedList<>());  // ????
    static Connection gSqlCon;
    private SwarcoEnumerations.ConnectionType SqlConnectionType=SwarcoEnumerations.ConnectionType.NOT_DEFINED;
    private SwarcoEnumerations.RequestOriginType requestOrigin= SwarcoEnumerations.RequestOriginType.NORMALROAD;
    private OmniaMeasurementDataShort foundRec;
    public OmniaMeasurementDataShort getFoundRec() { return foundRec;}
    public  void setFoundRec(OmniaMeasurementDataShort pFoundRec) {
 //       logger.info("pFoundRec.toString() = "+ pFoundRec.toString());
        foundRec=pFoundRec;
    }
    public void setRequestOrigin(SwarcoEnumerations.RequestOriginType prequestOrigin) {
        requestOrigin=prequestOrigin;
    }
    public  int MakeConnection(SwarcoEnumerations.ConnectionType pSqlCon) {
        SwarcoConnections vg = new SwarcoConnections();
        int iRet = vg.MakeConnection(pSqlCon);
        if (iRet!=1) {
            return iRet;
        }
        SqlConnectionType=pSqlCon;
        gSqlCon = vg.getSqlCon();
        return DATABASE_CONNECTION_OK;
    }
    //********************************************************************************************************
    public int DoesLineAlreadyExist(OmniaMeasurementDataShort pOmniaMeasurementDataShort) {
        SwarcoTimeUtilities  stu = new SwarcoTimeUtilities();
        String sWarcoTime;
        String SQL;
        java.sql.PreparedStatement stmt;
        OmniaMeasurementDataShort oi = new OmniaMeasurementDataShort();
        OmniaMeasurementDataShort cc;
        oi.MakeEmptyElement();
        setFoundRec(oi);
        try {
            SelectMeasurementDataShortMySqlWhere st = new SelectMeasurementDataShortMySqlWhere();
            SQL =st.getStatement();
            ResultSet rs;
            stmt = gSqlCon.prepareStatement(SQL);
            stmt.setLong(1,pOmniaMeasurementDataShort.getOmniaCode());
            stmt.setLong(2,pOmniaMeasurementDataShort.getIntersectionId());
            stmt.setLong(3,pOmniaMeasurementDataShort.getControllerId());
            stmt.setLong(4,pOmniaMeasurementDataShort.getDetectorId());
            stmt.setString(5,pOmniaMeasurementDataShort.getMeasurementTime());
  //          logger.info(" pOmniaMeasurementDataShort.toString() = " + pOmniaMeasurementDataShort.toString());
            rs = stmt.executeQuery();

            while (rs.next()) {
                // logger.info("*****inside rsloop");
                cc= new OmniaMeasurementDataShort();
                cc.MakeEmptyElement();
                cc.setOmniaCode(rs.getLong(1));
                cc.setIntersectionId(rs.getLong(2));
                cc.setControllerId(rs.getLong(3));
                cc.setMeasurementTime(rs.getString(4));
                cc.setDetectorId(rs.getLong(5));
                cc.setDetectorExternalCode(rs.getString(6));
                cc.setVehicleCount(rs.getLong(7));
                cc.setMeanVehicleSpeed(rs.getDouble(8));
                cc.setOccupancyProcent(rs.getDouble(9));
                cc.setAccurancy(rs.getDouble(10));
  //              logger.info("OmniaCode p  = " + pOmniaMeasurementDataShort.getOmniaCode() + " cc " +  cc.getOmniaCode());
  //              logger.info("IntersectionId p  = " + pOmniaMeasurementDataShort.getIntersectionId() + " cc + "+  cc.getIntersectionId());
  //             logger.info("ControllerId p  = " + pOmniaMeasurementDataShort.getControllerId() + " cc " + cc.getControllerId());
  //              logger.info("DetectorId p  = " + pOmniaMeasurementDataShort.getDetectorId() + " cc " + cc.getDetectorId());
  //              logger.info("MeasurementTime p  = " + pOmniaMeasurementDataShort.getMeasurementTime() + " cc " + cc.getMeasurementTime());
                 sWarcoTime  =stu.ToSwarcoTime( pOmniaMeasurementDataShort.getMeasurementTime());
     //            logger.info("MeasurementTime sWarcoTime  = " + sWarcoTime + " cc " + cc.getMeasurementTime());
                if (((cc.getOmniaCode()==(pOmniaMeasurementDataShort.getOmniaCode())) &&
                   (cc.getIntersectionId()==(pOmniaMeasurementDataShort.getIntersectionId()) &&
                   (cc.getControllerId()==(pOmniaMeasurementDataShort.getControllerId())) &&
                   (cc.getDetectorId()==(pOmniaMeasurementDataShort.getDetectorId())) &&
                   (cc.getMeasurementTime().equals(sWarcoTime))))) {
       //             logger.info(" löyty  cc.toString()= " + cc.toString());
       //             logger.info(" löyty  pOmniaMeasurementDataShort.toString()= " + pOmniaMeasurementDataShort.toString());
                    setFoundRec(cc);
                    stmt.close();
                    rs.close();
                    return INT_RET_FOUND;
                }
            }
            stmt.close();
            rs.close();
//            logger.info("ei löytyny");
            return INT_RET_NOT_FOUND;
        } catch(Exception e) {
            logger.info(" catch 11");
            logger.info(e.getMessage());
            e.printStackTrace();
            logger.info("Failed e=",e);
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            return INT_RET_NOT_OK;
        }
    }
    public int DoesLineAlreadyExistLahti(OmniaMeasurementDataShort pOmniaMeasurementDataShort) {
        SwarcoTimeUtilities  stu = new SwarcoTimeUtilities();
        String sWarcoTime;
        String SQL;
        java.sql.PreparedStatement stmt;
        OmniaMeasurementDataShort oi = new OmniaMeasurementDataShort();
        OmniaMeasurementDataShort cc;
        oi.MakeEmptyElement();
        setFoundRec(oi);
        try {
            SelectMeasurementDataShortMySqlWhereLahti st = new SelectMeasurementDataShortMySqlWhereLahti();
            SQL =st.getStatement();
            ResultSet rs;
            stmt = gSqlCon.prepareStatement(SQL);
            stmt.setLong(1,pOmniaMeasurementDataShort.getOmniaCode());
            stmt.setLong(2,pOmniaMeasurementDataShort.getIntersectionId());
            stmt.setLong(3,pOmniaMeasurementDataShort.getControllerId());
            stmt.setLong(4,pOmniaMeasurementDataShort.getDetectorId());
            stmt.setString(5,pOmniaMeasurementDataShort.getMeasurementTime());
            //          logger.info(" pOmniaMeasurementDataShort.toString() = " + pOmniaMeasurementDataShort.toString());
            rs = stmt.executeQuery();

            while (rs.next()) {
                // logger.info("*****inside rsloop");
                cc= new OmniaMeasurementDataShort();
                cc.MakeEmptyElement();
                cc.setOmniaCode(rs.getLong(1));
                cc.setIntersectionId(rs.getLong(2));
                cc.setControllerId(rs.getLong(3));
                cc.setMeasurementTime(rs.getString(4));
                cc.setDetectorId(rs.getLong(5));
                cc.setDetectorExternalCode(rs.getString(6));
                cc.setVehicleCount(rs.getLong(7));
                cc.setMeanVehicleSpeed(rs.getDouble(8));
                cc.setOccupancyProcent(rs.getDouble(9));
                cc.setAccurancy(rs.getDouble(10));
                //              logger.info("OmniaCode p  = " + pOmniaMeasurementDataShort.getOmniaCode() + " cc " +  cc.getOmniaCode());
                //              logger.info("IntersectionId p  = " + pOmniaMeasurementDataShort.getIntersectionId() + " cc + "+  cc.getIntersectionId());
                //             logger.info("ControllerId p  = " + pOmniaMeasurementDataShort.getControllerId() + " cc " + cc.getControllerId());
                //              logger.info("DetectorId p  = " + pOmniaMeasurementDataShort.getDetectorId() + " cc " + cc.getDetectorId());
                //              logger.info("MeasurementTime p  = " + pOmniaMeasurementDataShort.getMeasurementTime() + " cc " + cc.getMeasurementTime());
                sWarcoTime  =stu.ToSwarcoTime( pOmniaMeasurementDataShort.getMeasurementTime());
                //            logger.info("MeasurementTime sWarcoTime  = " + sWarcoTime + " cc " + cc.getMeasurementTime());
                if (((cc.getOmniaCode()==(pOmniaMeasurementDataShort.getOmniaCode())) &&
                        (cc.getIntersectionId()==(pOmniaMeasurementDataShort.getIntersectionId()) &&
                                (cc.getControllerId()==(pOmniaMeasurementDataShort.getControllerId())) &&
                                (cc.getDetectorId()==(pOmniaMeasurementDataShort.getDetectorId())) &&
                                (cc.getMeasurementTime().equals(sWarcoTime))))) {
                    //             logger.info(" löyty  cc.toString()= " + cc.toString());
                    //             logger.info(" löyty  pOmniaMeasurementDataShort.toString()= " + pOmniaMeasurementDataShort.toString());
                    setFoundRec(cc);
                    stmt.close();
                    rs.close();
                    return INT_RET_FOUND;
                }
            }
            stmt.close();
            rs.close();
//            logger.info("ei löytyny");
            return INT_RET_NOT_FOUND;
        } catch(Exception e) {
            logger.info(" catch 11");
            logger.info(e.getMessage());
            e.printStackTrace();
            logger.info("Failed e=",e);
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            return INT_RET_NOT_OK;
        }
    }
    public int IsItChanged(OmniaMeasurementDataShort pC1Neww ,OmniaMeasurementDataShort pC2Old) {
        int iRet =  NOT_CHANGED;
        if (!(pC1Neww.getDetectorExternalCode().equals(pC2Old.getDetectorExternalCode()))) {
            iRet =  CHANGED;
        }
        if (!(pC1Neww.getVehicleCount()==(pC2Old.getVehicleCount()))) {
            iRet =  CHANGED;
        }
        if (!(pC1Neww.getMeanVehicleSpeed()==(pC2Old.getMeanVehicleSpeed()))) {
            iRet =  CHANGED;
        }
        if (!(pC1Neww.getOccupancyProcent()==(pC2Old.getOccupancyProcent()))) {
            iRet =  CHANGED;
        }
        if (!(pC1Neww.getAccurancy()==(pC2Old.getAccurancy()))) {
            iRet =  CHANGED;
        }
        if (iRet==CHANGED) {
  //          logger.info("***Changed1  pC1Neww.toString()" + pC1Neww.toString());
  //          logger.info("***Changed2  pC2Old.toString()" + pC2Old.toString());
        }
        return iRet;
    }
    public int MakeDeleteInsert(OmniaMeasurementDataShort pC1) throws SQLException{
        int iRet;
        iRet = DeleteOldOmniaMeasurementDataShortLineFromDb(pC1);
        logger.info("***deletedinserted  pC1.toString()" + pC1.toString());
        if (iRet < 0) {
 //           logger.info("iRet = " + iRet);
            iRet = UNSUCCESSFUL_DATABASE_DELETE_OPERATION;
            logger.info("iRet = " + iRet);
            return iRet;
        }
        iRet = AddNewOmniaMeasurementDataShort(pC1);
        if (iRet < 0) {
            iRet = UNSUCCESSFUL_DATABASE_DELETE_OPERATION;
            logger.info("***********************  unsuccessful delete iRet = " + iRet);
            return iRet;
        }
        return iRet;
    }
    public int MakeDeleteInsertLahti(OmniaMeasurementDataShort pC1) throws SQLException{
        int iRet;
        iRet = DeleteOldOmniaMeasurementDataShortLineFromDbLahti(pC1);
        logger.info("***deletedinserted  pC1.toString()" + pC1.toString());
        if (iRet < 0) {
            //           logger.info("iRet = " + iRet);
            iRet = UNSUCCESSFUL_DATABASE_DELETE_OPERATION;
            logger.info("iRet = " + iRet);
            return iRet;
        }
        iRet = AddNewOmniaMeasurementDataShortLahti(pC1);
        if (iRet < 0) {
            iRet = UNSUCCESSFUL_DATABASE_DELETE_OPERATION;
            logger.info("***********************  unsuccessful delete iRet = " + iRet);
            return iRet;
        }
        return iRet;
    }
    private int DeleteOldOmniaMeasurementDataShortLineFromDbLahti(OmniaMeasurementDataShort pC1 ) throws SQLException{
        int iRet;
        String SQL;
        try {
            java.sql.PreparedStatement stmt;
            SQL = " delete from OmniaMeasurementDataShortLahti ";
            SQL = SQL + "where OmniaCode = " + pC1.getOmniaCode() + " and " +
                    " IntersectionId = " + pC1.getIntersectionId() + " and " +
                    " ControllerId = " + pC1.getControllerId() + " and " +
                    " DetectorId = " + pC1.getDetectorId() + " and " +
                    " MeasurementTime = '" + pC1.getMeasurementTime()  +  "';";
            logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            //      logger.info("Lines deleted iRet = " + iRet);
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
    private int DeleteOldOmniaMeasurementDataShortLineFromDb(OmniaMeasurementDataShort pC1 ) throws SQLException{
        int iRet;
        String SQL;
        try {
            java.sql.PreparedStatement stmt;
            SQL = " delete from OmniaMeasurementDataShort ";
            SQL = SQL + "where OmniaCode = " + pC1.getOmniaCode() + " and " +
                    " IntersectionId = " + pC1.getIntersectionId() + " and " +
                    " ControllerId = " + pC1.getControllerId() + " and " +
                    " DetectorId = " + pC1.getDetectorId() + " and " +
                    " MeasurementTime = '" + pC1.getMeasurementTime()  +  "';";
           logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
      //      logger.info("Lines deleted iRet = " + iRet);
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
    public static int MakeDataTransferOperations() throws SQLException{
        int iRet;
        String SQL;
        try {
            java.sql.PreparedStatement stmt;
            SQL = " call FromShortToStorageMinute(); ";
            logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            if (iRet >= 0) iRet=INT_RET_OK;
            stmt.close();
            if (iRet < 0) {
                logger.info("iRet = " + iRet);
                iRet = UNSUCCESSFUL_DATABASE_DATA_TRANSFER_OPERATION;
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
    public static int MakeDataTransferOperationsLahti() throws SQLException{
        int iRet;
        String SQL;
        try {
            java.sql.PreparedStatement stmt;
            SQL = " call FromShortToStorageMinuteLahti(); ";
            logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            if (iRet >= 0) iRet=INT_RET_OK;
            stmt.close();
            if (iRet < 0) {
                logger.info("iRet = " + iRet);
                iRet = UNSUCCESSFUL_DATABASE_DATA_TRANSFER_OPERATION;
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
    public static int AddNewOmniaMeasurementDataShort(OmniaMeasurementDataShort pOmniaMeasurementDataShort) throws SQLException{
        int iRet;
        String SQL="";
        java.sql.PreparedStatement stmt;
        try {
        InsertOmniaMeasurementDataShortMySql st = new InsertOmniaMeasurementDataShortMySql();
            SQL =st.getStatement();
            stmt = gSqlCon.prepareStatement(SQL);
            int pos=0;
            pos=1;   // old was zero
            stmt.setLong(pos,pOmniaMeasurementDataShort.getOmniaCode());
            pos=pos+1;
            stmt.setLong(pos,pOmniaMeasurementDataShort.getIntersectionId());
            pos=pos+1;
            stmt.setLong(pos,pOmniaMeasurementDataShort.getControllerId());
            pos=pos+1;
            stmt.setString(pos,pOmniaMeasurementDataShort.getMeasurementTime());  // ...Sql and setTimestamp ?
            pos=pos+1;
            stmt.setLong(pos,pOmniaMeasurementDataShort.getDetectorId());
            pos=pos+1;
            stmt.setString(pos,pOmniaMeasurementDataShort.getDetectorExternalCode());
            pos=pos+1;
            stmt.setLong(pos,pOmniaMeasurementDataShort.getVehicleCount());
            pos=pos+1;
            stmt.setDouble(pos,pOmniaMeasurementDataShort.getMeanVehicleSpeed());
            pos=pos+1;
            stmt.setDouble(pos,pOmniaMeasurementDataShort.getOccupancyProcent());
            pos=pos+1;
            stmt.setDouble(pos,pOmniaMeasurementDataShort.getAccurancy());
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

    public static int AddNewOmniaMeasurementDataShortLahti(OmniaMeasurementDataShort pOmniaMeasurementDataShort) throws SQLException{
        int iRet;
        String SQL="";
        java.sql.PreparedStatement stmt;
        try {
            InsertOmniaMeasurementDataShortMySqlLahti st = new InsertOmniaMeasurementDataShortMySqlLahti();
            SQL =st.getStatement();
            stmt = gSqlCon.prepareStatement(SQL);
            int pos=0;
            pos=1;   // old was zero
            stmt.setLong(pos,pOmniaMeasurementDataShort.getOmniaCode());
            pos=pos+1;
            stmt.setLong(pos,pOmniaMeasurementDataShort.getIntersectionId());
            pos=pos+1;
            stmt.setLong(pos,pOmniaMeasurementDataShort.getControllerId());
            pos=pos+1;
            stmt.setString(pos,pOmniaMeasurementDataShort.getMeasurementTime());  // ...Sql and setTimestamp ?
            pos=pos+1;
            stmt.setLong(pos,pOmniaMeasurementDataShort.getDetectorId());
            pos=pos+1;
            stmt.setString(pos,pOmniaMeasurementDataShort.getDetectorExternalCode());
            pos=pos+1;
            stmt.setLong(pos,pOmniaMeasurementDataShort.getVehicleCount());
            pos=pos+1;
            stmt.setDouble(pos,pOmniaMeasurementDataShort.getMeanVehicleSpeed());
            pos=pos+1;
            stmt.setDouble(pos,pOmniaMeasurementDataShort.getOccupancyProcent());
            pos=pos+1;
            stmt.setDouble(pos,pOmniaMeasurementDataShort.getAccurancy());
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
    public  List<OmniaMeasurementDataShort> GetOmniaMeasurementDataList()  {
        OmniaMeasurementDataShort cc = new OmniaMeasurementDataShort();
        if (OmniaMeasurementDataUnits.isEmpty()) {
            cc= new OmniaMeasurementDataShort();
            cc.MakeEmptyElement();
            OmniaMeasurementDataUnits.add(cc);
        }
        return OmniaMeasurementDataUnits;
    }
    public  long JsonOmniaGetOmniaCode(String pMeasurementsDataShort) {
        Gson myGson = new Gson();
        MessageUtils mu = new MessageUtils();
        String strHelp1 = mu.StripFileStartEnd(pMeasurementsDataShort);
        if (strHelp1.equals(NO_VALUE)) {
           return INT_RET_NOT_OK;
        }
        int iHere = strHelp1.indexOf("}");
        long iRet;
        String strHelp2 = strHelp1.substring(0, iHere + 1);
        OmniaMeasurementDataShort aO1;
        OmniaMeasurementDataShortJson ceJson = new OmniaMeasurementDataShortJson();
        if (iHere > 0) {
           ceJson = myGson.fromJson(strHelp2, OmniaMeasurementDataShortJson.class);
           return ceJson.getOmniaCode();
        }
        return INT_RET_NOT_OK;
    }
    public  int JsonOmniaMeasurementShortSql(String pMeasurementsDataShort) throws SQLException{
        int iRet=0;
        String strHelp1=NO_VALUE;
        try {
            Gson myGson = new Gson();
            MessageUtils mu = new MessageUtils();
            strHelp1 = mu.StripFileStartEnd(pMeasurementsDataShort);
            if (strHelp1.equals(NO_VALUE)) {
                return INT_RET_NOT_OK;
            }
            int iHere = strHelp1.indexOf("}");
            int iRound =1;
            int iHereOld=0;
           // JsonParser jsonParser;
            String strHelp2 = strHelp1.substring(0, iHere + 1);
            OmniaMeasurementDataShort aO1;
            OmniaMeasurementDataShortJson ceJson = new OmniaMeasurementDataShortJson();
            SwarcoTimeUtilities sw = new  SwarcoTimeUtilities();
            String swarcoTime="";
            while  (iHere>0 ) {
                 //  jsonParser = new JsonParser();
                   ceJson = myGson.fromJson(strHelp2, OmniaMeasurementDataShortJson.class);
                   aO1=ceJson.MakeItemFromJsonTransferItem();
    //              logger.info("ceJson.toString().length() = " + ceJson.toString().length());
    //               logger.info("aO1.toString() = " +aO1.toString());
                   iRet= DoesLineAlreadyExist(aO1);
                if (iRet==INT_RET_NOT_FOUND) {
                    iRet=AddNewOmniaMeasurementDataShort(aO1);
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
                    logger.info("Unsuccessful DoesLineAlreadyExist insert iRet = " + iRet);
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
            gSqlCon.close();
            return INT_RET_NOT_OK;
        }
        return iRet;
    }
    public  int JsonOmniaMeasurementShortSqlLahti(String pMeasurementsDataShort) throws SQLException{
        int iRet=0;
        String strHelp1=NO_VALUE;
        try {
            Gson myGson = new Gson();
            MessageUtils mu = new MessageUtils();
            strHelp1 = mu.StripFileStartEnd(pMeasurementsDataShort);
            if (strHelp1.equals(NO_VALUE)) {
                return INT_RET_NOT_OK;
            }
            int iHere = strHelp1.indexOf("}");
            int iRound =1;
            int iHereOld=0;
            // JsonParser jsonParser;
            String strHelp2 = strHelp1.substring(0, iHere + 1);
            OmniaMeasurementDataShort aO1;
            OmniaMeasurementDataShortJson ceJson = new OmniaMeasurementDataShortJson();
            SwarcoTimeUtilities sw = new  SwarcoTimeUtilities();
            String swarcoTime="";
            while  (iHere>0 ) {
                //  jsonParser = new JsonParser();
                ceJson = myGson.fromJson(strHelp2, OmniaMeasurementDataShortJson.class);
                aO1=ceJson.MakeItemFromJsonTransferItem();
                //              logger.info("ceJson.toString().length() = " + ceJson.toString().length());
                //               logger.info("aO1.toString() = " +aO1.toString());
                iRet= DoesLineAlreadyExist(aO1);
                if (iRet==INT_RET_NOT_FOUND) {
                    iRet=AddNewOmniaMeasurementDataShortLahti(aO1);
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
                    logger.info("Unsuccessful DoesLineAlreadyExist insert iRet = " + iRet);
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
            gSqlCon.close();
            return INT_RET_NOT_OK;
        }
        return iRet;
    }

}
