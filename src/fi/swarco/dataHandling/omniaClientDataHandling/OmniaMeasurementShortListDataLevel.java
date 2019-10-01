package fi.swarco.dataHandling.omniaClientDataHandling;
import java.sql.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.connections.SwarcoConnections;
import fi.swarco.dataHandling.pojos.OmniaMeasurementDataShort;
import fi.swarco.dataHandling.queriesSql.mySQL.InsertOmniaMeasurementDataShortMySql;
import fi.swarco.dataHandling.queriesSql.mySQL.OmniaMeasurementDataShortMySqlSelectWhere;
import fi.swarco.omniaDataTransferServices.MessageUtils;
import fi.swarco.omniaDataTransferServices.SwarcoTimeUtilities;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import static fi.swarco.CONSTANT.*;
public class OmniaMeasurementShortListDataLevel {
    static Logger logger = Logger.getLogger(fi.swarco.dataHandling.OmniaMeasurementListDataLevel.class.getName());
    List<OmniaMeasurementDataShort> OmniaMeasurementDataUnits = Collections.synchronizedList(new LinkedList<OmniaMeasurementDataShort>());  // ????
    static Connection gSqlCon;
    private SwarcoEnumerations.ConnectionType SqlConnectionType=SwarcoEnumerations.ConnectionType.NOT_DEFINED;
    private SwarcoEnumerations.RequestOriginType requestOrigin= SwarcoEnumerations.RequestOriginType.NORMALROAD;
    private OmniaMeasurementDataShort foundRec;
    public OmniaMeasurementDataShort getFoundRec() { return foundRec;}

    public  void setFoundRec(OmniaMeasurementDataShort pFoundRec) {foundRec=pFoundRec;}

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
    public int DoesLineAlreadyExsist(OmniaMeasurementDataShort pOmniaMeasurementDataShort) {
        String SQL="";
        java.sql.PreparedStatement stmt=null;
        OmniaMeasurementDataShort oi = new OmniaMeasurementDataShort();
        oi.MakeEmptyElement();
        setFoundRec(oi);
        try {
        //    OmniaMeasurementDataMySqlSelectWhere st = new OmniaMeasurementDataMySqlSelectWhere();
            OmniaMeasurementDataShortMySqlSelectWhere st = new OmniaMeasurementDataShortMySqlSelectWhere();
            SQL =st.getStatement();
            //      logger.debug("SqlConnectionTypeyyyy= "+ SqlConnectionType);
            ResultSet rs;
            //logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            //    logger.info("pOmniaIntersectionData.getOmniaCode()=" + pOmniaMeasurementData.getOmniaCode());
            //    logger.info("pOmniaIntersectionData.getIntersectionId()=" + pOmniaMeasurementData.getIntersectionId());
            //    logger.info("pOmniaIntersectionData.getControllerId()=" + pOmniaMeasurementData.getControllerId());
            //    logger.info("pOmniaIntersectionData.getDetectorId()=" + pOmniaMeasurementData.getDetectorId());
            //    logger.info("pOmniaIntersectionData.getMeasurementTime()=" + pOmniaMeasurementData.getMeasurementTime());
            stmt.setLong(1,pOmniaMeasurementDataShort.getOmniaCode());
            stmt.setLong(2,pOmniaMeasurementDataShort.getIntersectionId());
            stmt.setLong(3,pOmniaMeasurementDataShort.getControllerId());
            stmt.setLong(4,pOmniaMeasurementDataShort.getDetectorId());
            stmt.setString(5,pOmniaMeasurementDataShort.getMeasurementTime());
            rs = stmt.executeQuery();
            OmniaMeasurementDataShort cc;
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
                cc.setMeanVehicleSpeed(rs.getLong(7));
                cc.setOccupancyProcent(rs.getLong(9));
                cc.setAccurancy(rs.getLong(10));
                if (((cc.getOmniaCode()==(pOmniaMeasurementDataShort.getOmniaCode())) &&
                        (cc.getIntersectionId()==(pOmniaMeasurementDataShort.getIntersectionId()) &&
                                (cc.getControllerId()==(pOmniaMeasurementDataShort.getControllerId())) &&
                                (cc.getDetectorId()==(pOmniaMeasurementDataShort.getDetectorId())) &&
                                (cc.getMeasurementTime().equals(pOmniaMeasurementDataShort.getMeasurementTime()))))) {
                    logger.info(" l�yty");
                    setFoundRec(cc);
                    return INT_RET_FOUND;
                }
            }
            //    logger.info("ei l�ytyny");
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
    public int IsItChanged(OmniaMeasurementDataShort pC1 ,OmniaMeasurementDataShort pC2) {
        int iRet =  NOT_CHANGED;
        if (!(pC1.getDetectorExternalCode().equals(pC2.getDetectorExternalCode()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getVehicleCount()==(pC2.getVehicleCount()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getMeanVehicleSpeed()==(pC2.getMeanVehicleSpeed()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getOccupancyProcent()==(pC2.getOccupancyProcent()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getAccurancy()==(pC2.getAccurancy()))) {
            iRet =  CHANGED;
        }
        if (iRet==CHANGED) {
            logger.info("***Changed1  pC1.toString()" + pC1.toString());
            logger.info("***Changed2  pC2.toString()" + pC2.toString());
        }
        return iRet;
    }
    public int MakeDeleteInsert(OmniaMeasurementDataShort pC1) {
        int iRet = INT_RET_OK;
        iRet = DeleteOldOmniaMeasurementDataShortLineFromDb(pC1);
        if (iRet < 0) {
            logger.info("iRet = " + iRet);
            iRet = UNSUCCESSFUL_DATABASE_DELETE_OPERATION;
            logger.info("iRet = " + iRet);
            return iRet;
        }
        iRet = AddNewOmniaMeasurementDataShort(pC1);
        if (iRet < 0) {
            logger.info("***********************Successful delete unsuccesful insert iRet = " + iRet);
            iRet = UNSUCCESSFUL_DATABASE_DELETE_OPERATION;
            logger.info("***********************Successful delete unsuccesful insert iRet = " + iRet);
            return iRet;
        }
        return iRet;
    }
    private int DeleteOldOmniaMeasurementDataShortLineFromDb(OmniaMeasurementDataShort pC1 ) {
        int iRet;
        String SQL = "";
        try {
            java.sql.PreparedStatement stmt;
            SQL = " delete from OmniameasurementdataShort ";
            SQL = SQL + "where OmniaCode = " + pC1.getOmniaCode() + " and " +
                    " IntersectionId = " + pC1.getIntersectionId() + " and " +
                    " ControllerId = " + pC1.getControllerId() + " and " +
                    " DetectorId = " + pC1.getDetectorId() + " and " +
                    " MeasurementTime = '" + pC1.getMeasurementTime()  +  "';";
            logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            logger.info("Lines deleted iRet = " + iRet);
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
            return UNSUCCESSFUL_DATABASE_OPERATION;
        }
    }
    public static int AddNewOmniaMeasurementDataShort(OmniaMeasurementDataShort pOmniaMeasurementDataShort) {
        int iRet;
        String SQL="";
        java.sql.PreparedStatement stmt;
        try {
        InsertOmniaMeasurementDataShortMySql st = new InsertOmniaMeasurementDataShortMySql();
            SQL =st.getStatement();
            //         logger.info("SQL = " + SQL);
            //         logger.info("pOmniaMeasurementData.toString()=" + pOmniaMeasurementData.toString());
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
            return UNSUCCESSFUL_DATABASE_OPERATION;
        }
    }
    public  List<OmniaMeasurementDataShort> GetOmniaMeasurementDataList()  {
        OmniaMeasurementDataShort cc = new OmniaMeasurementDataShort();
        if (OmniaMeasurementDataUnits.isEmpty()==true) {
            cc= new OmniaMeasurementDataShort();
            cc.MakeEmptyElement();
            OmniaMeasurementDataUnits.add(cc);
        }
        return OmniaMeasurementDataUnits;
    }
    public  int JsonOmniaMeasurementShortSql(String pMeasurementsDataShort) {
        int iRet=0;
        String strHelp1=NO_VALUE;
        try {
            Gson myGson = new Gson();
            MessageUtils mu = new MessageUtils();
            strHelp1 = mu.StripFileStartEnd(pMeasurementsDataShort);
            //           logger.info("stripped strHelp1 = " + strHelp1);
            int iHere = strHelp1.indexOf("}");
            int iRound =1;
            int iHereOld=0;
            String strHelp2 = strHelp1.substring(0, iHere + 1);
            OmniaMeasurementDataShort aO1 = new OmniaMeasurementDataShort();
            SwarcoTimeUtilities sw = new  SwarcoTimeUtilities();
            String swarcoTime="";
            while  (iHere>0 ) {
//                logger.info("stripped only first  iRound = " + iRound);
                //           logger.info("stripped only first strHelp2 = " + strHelp2 + " strHelp2 iRound = " + iRound);
                JsonParser jsonParser = new JsonParser();
                //  OmniaMeasurementData aO1 = myGson.fromJson(strHelp2, OmniaMeasurementData.class);
                aO1 = myGson.fromJson(strHelp2, OmniaMeasurementDataShort.class);
// check do
// check does the line already exists
// no: make insert
// yes: is it changed
//        no: do nothing
//        yes:
//        make delete and insert
//        write to logs
//        Database triggers write old lines to history table
                iRet= DoesLineAlreadyExsist(aO1);
                if (iRet==INT_RET_NOT_FOUND) {
                    iRet=iRet = AddNewOmniaMeasurementDataShort(aO1);
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





