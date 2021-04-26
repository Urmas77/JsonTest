package fi.swarco.dataHandling.omniaServerDataHandling;
import java.sql.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.connections.SwarcoConnections;
import fi.swarco.dataHandling.pojos.OmniaControllerStatusData;
import fi.swarco.dataHandling.queriesSql.mySQL.InsertOmniaControllerStatusDataMySql;
import fi.swarco.dataHandling.queriesSql.mySQL.SelectOmniaControllerStatusDataMySqlWhere;
import fi.swarco.omniaDataTransferServices.MessageUtils;
import fi.swarco.serviceOperations.SwarcoTimeUtilities;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import static fi.swarco.CONSTANT.*;
public class OmniaControllerStatusListDataLevel {
    static Logger logger = Logger.getLogger(OmniaMeasurementListDataLevel.class.getName());
    List<OmniaControllerStatusData> OmniaControllerStatusDataUnits = Collections.synchronizedList(new LinkedList<OmniaControllerStatusData>());  // ????
    static Connection gSqlCon;
    private SwarcoEnumerations.ConnectionType SqlConnectionType=SwarcoEnumerations.ConnectionType.NOT_DEFINED;
    private SwarcoEnumerations.RequestOriginType requestOrigin= SwarcoEnumerations.RequestOriginType.NORMALROAD;
    private OmniaControllerStatusData foundRec;
    public OmniaControllerStatusData getFoundRec() { return foundRec;}
    public  void setFoundRec( OmniaControllerStatusData pFoundRec) {foundRec=pFoundRec;}
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
 //       logger.info("SqlConnectionType = " + SqlConnectionType);
        gSqlCon = vg.getSqlCon();
        return DATABASE_CONNECTION_OK;
    }
    //********************************************************************************************************
    public int DoesLineAlreadyExsist(OmniaControllerStatusData pOmniaControllerStatusData) throws SQLException {
        String SQL="";
        java.sql.PreparedStatement stmt=null;
        OmniaControllerStatusData oi = new OmniaControllerStatusData();
        oi.MakeEmptyElement();
        setFoundRec(oi);
        try {
            SelectOmniaControllerStatusDataMySqlWhere st = new SelectOmniaControllerStatusDataMySqlWhere();
            SQL =st.getStatement();
            ResultSet rs;
            //logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            stmt.setLong(1,pOmniaControllerStatusData.getOmniaCode());
            stmt.setLong(2,pOmniaControllerStatusData.getIntersectionId());
            stmt.setLong(3,pOmniaControllerStatusData.getControllerId());
            stmt.setString(4,pOmniaControllerStatusData.getMeasurementTime());
        //    logger.info ("pOmniaControllerStatusData.getOmniaCode() =" +pOmniaControllerStatusData.getOmniaCode());
        //    logger.info ("pOmniaControllerStatusData.getIntersectionId() =" +pOmniaControllerStatusData.getIntersectionId());
        //    logger.info ("pOmniaControllerStatusData.pOmniaControllerStatusData.getControllerId() =" +pOmniaControllerStatusData.getControllerId());
        //    logger.info ("pOmniaControllerStatusData.getMeasurementTime() =" +pOmniaControllerStatusData.getMeasurementTime());
            rs = stmt.executeQuery();
            OmniaControllerStatusData cc;
            while (rs.next()) {
                // logger.info("*****inside rsloop");
                cc= new OmniaControllerStatusData();
                cc.MakeEmptyElement();
                cc.setOmniaCode(rs.getLong(1));
                cc.setIntersectionId(rs.getLong(2));
                cc.setControllerId(rs.getLong(3));
                cc.setMeasurementTime(rs.getString(4));
                cc.setIntersectionCode(rs.getString(5));
                cc.setControllerProgramNumber(rs.getLong(6));
                cc.setControllerProgramDescription(rs.getString(7));
                cc.setControllerStatus(rs.getString(8));
//                logger.info ("cc..getOmniaCode() =" +cc.getOmniaCode());
//                logger.info ("cc.getIntersectionId() =" +cc.getIntersectionId());
//                logger.info ("cc.pOmniaControllerStatusData.getControllerId() =" +cc.getControllerId());
//                logger.info ("cc.getMeasurementTime() =" +cc.getMeasurementTime());
                if (((cc.getOmniaCode()==(pOmniaControllerStatusData.getOmniaCode())) &&
                        (cc.getIntersectionId()==(pOmniaControllerStatusData.getIntersectionId()) &&
                                (cc.getControllerId()==(pOmniaControllerStatusData.getControllerId())) &&
                                (cc.getMeasurementTime().equals(pOmniaControllerStatusData.getMeasurementTime()))))) {
  //                  logger.info(" l�yty");
                    setFoundRec(cc);
                    stmt.close();
                    rs.close();
                    return INT_RET_FOUND;
                }
            }
    //        logger.info("ei l�ytyny");
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
    public int IsItChanged(OmniaControllerStatusData pC1 ,OmniaControllerStatusData pC2) throws SQLException {
        int iRet =  NOT_CHANGED;
        if (!(pC1.getIntersectionCode().equals(pC2.getIntersectionCode()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getControllerProgramNumber()==(pC2.getControllerProgramNumber()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getControllerProgramDescription()==(pC2.getControllerProgramDescription()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getControllerStatus().equals(pC2.getControllerStatus()))) {
            iRet =  CHANGED;
        }
        if (iRet==CHANGED) {
            logger.info("***Changed1  pC1.toString()" + pC1.toString());
            logger.info("***Changed2  pC2.toString()" + pC2.toString());
        }
        return iRet;
    }
    public int MakeDeleteInsert(OmniaControllerStatusData pC1) throws SQLException {
        int iRet = INT_RET_OK;
        iRet = DeleteOldOmniaControllerStatusDataLineFromDb (pC1);
        if (iRet < 0) {
            logger.info("iRet = " + iRet);
            iRet = UNSUCCESSFUL_DATABASE_DELETE_OPERATION;
            logger.info("iRet = " + iRet);
            return iRet;
        }
        iRet = AddNewOmniaControllerStatusData(pC1);
        if (iRet < 0) {
            logger.info("***********************Successful delete unsuccesful insert iRet = " + iRet);
            iRet = UNSUCCESSFUL_DATABASE_DELETE_OPERATION;
            logger.info("***********************Successful delete unsuccesful insert iRet = " + iRet);
            return iRet;
        }
        return iRet;
    }
    private int DeleteOldOmniaControllerStatusDataLineFromDb(OmniaControllerStatusData pC1 ) throws SQLException {
        int iRet;
        String SQL = "";
        try {
            java.sql.PreparedStatement stmt;
            SQL = " delete from  OmniaControllerStatusData ";
            SQL = SQL + "where OmniaCode = " + pC1.getOmniaCode() + " and " +
                    " IntersectionId = " + pC1.getIntersectionId() + " and " +
                    " ControllerId = " + pC1.getControllerId() + " and " +
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
    public static int AddNewOmniaControllerStatusData(OmniaControllerStatusData pOmniaControllerStatusData) throws SQLException {
        int iRet;
        String SQL="";
        java.sql.PreparedStatement stmt;
        try {
            InsertOmniaControllerStatusDataMySql st = new InsertOmniaControllerStatusDataMySql();
            SQL =st.getStatement();
            stmt = gSqlCon.prepareStatement(SQL);
            int pos=0;
            pos=1;   // old was zero
            stmt.setLong(pos,pOmniaControllerStatusData.getOmniaCode());
            pos=pos+1;
            stmt.setLong(pos,pOmniaControllerStatusData.getIntersectionId());
            pos=pos+1;
            stmt.setLong(pos,pOmniaControllerStatusData.getControllerId());
            pos=pos+1;
            stmt.setString(pos,pOmniaControllerStatusData.getMeasurementTime());  // ...Sql and setTimestamp ?
            pos=pos+1;
            stmt.setString(pos,pOmniaControllerStatusData.getIntersectionCode());
            pos=pos+1;
            stmt.setLong(pos,pOmniaControllerStatusData.getControllerProgramNumber());
            pos=pos+1;
            stmt.setString(pos,pOmniaControllerStatusData.getControllerProgramDescription());
            pos=pos+1;
            stmt.setString(pos,pOmniaControllerStatusData.getControllerStatus());
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
    public  List<OmniaControllerStatusData> GetOmniaControllerStatusDataList()  {
        OmniaControllerStatusData cc = new OmniaControllerStatusData();
        if (OmniaControllerStatusDataUnits .isEmpty()==true) {
            cc= new OmniaControllerStatusData();
            cc.MakeEmptyElement();
            OmniaControllerStatusDataUnits.add(cc);
        }
        return OmniaControllerStatusDataUnits;
    }
    public  int JsonOmniaControllerStatusDataSql(String pOmniaControllerStatusData) {
        int iRet=0;
        String strHelp1=NO_VALUE;
        try {
            Gson myGson = new Gson();
            MessageUtils mu = new MessageUtils();
            JsonParser jsonParser;
 //           logger.info("stripped pOmniaControllerStatusData = " + pOmniaControllerStatusData);
            strHelp1 = mu.StripFileStartEnd(pOmniaControllerStatusData);
//            logger.info("stripped strHelp1 = " + strHelp1);
            int iHere = strHelp1.indexOf("}");
            int iRound =1;
            int iHereOld=0;
            String strHelp2 = strHelp1.substring(0, iHere + 1);
            OmniaControllerStatusData aO1 = new OmniaControllerStatusData();
            SwarcoTimeUtilities sw = new  SwarcoTimeUtilities();
            String swarcoTime="";
            while  (iHere>0 ) {
                jsonParser = new JsonParser();
              //  aO1 = myGson.fromJson(strHelp2, OmniaMeasurementData.class);
              //  swarcoTime =sw.ToSwarcoTime(aO1.getDetectorDataPreviousUpdate());
              //  aO1.setDetectorDataPreviousUpdate(swarcoTime);
                aO1 = myGson.fromJson(strHelp2, OmniaControllerStatusData.class);
                swarcoTime =sw.ToSwarcoTime(aO1.getTransferTime());
                aO1.setTransferTime(swarcoTime);
                iRet= DoesLineAlreadyExsist(aO1);
                if (iRet==INT_RET_NOT_FOUND) {
                    iRet= AddNewOmniaControllerStatusData(aO1);
     //                  logger.info("  one line inserted iRet = " + iRet);
                    if (iRet!=INT_RET_OK) {
                        logger.info("Unsuccessful  insert iRet = " + iRet);
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
