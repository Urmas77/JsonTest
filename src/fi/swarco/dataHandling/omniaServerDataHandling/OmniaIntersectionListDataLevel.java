package fi.swarco.dataHandling.omniaServerDataHandling;
import java.sql.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.connections.SwarcoConnections;
import fi.swarco.dataHandling.pojos.OmniaIntersectionData;
import fi.swarco.dataHandling.queriesSql.mySQL.InsertIntersectionDataMySql;
import fi.swarco.dataHandling.queriesSql.sqlServer.OmniaIntersectionDataSqlServerSelectJson;
import fi.swarco.dataHandling.queriesSql.mySQL.SelectIntersectionDataMySqlWhere;
import fi.swarco.omniaDataTransferServices.MessageUtils;
import fi.swarco.omniaDataTransferServices.SwarcoTimeUtilities;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import static fi.swarco.CONSTANT.*;
public class OmniaIntersectionListDataLevel {
    private static Logger logger = Logger.getLogger(OmniaIntersectionListDataLevel.class.getName());
    List<OmniaIntersectionData> OmniaIntersectionDataUnits = Collections.synchronizedList(new LinkedList<OmniaIntersectionData>());  // ????
    static Connection gSqlCon;
    private SwarcoEnumerations.ConnectionType SqlConnectionType=SwarcoEnumerations.ConnectionType.NOT_DEFINED;
    SwarcoEnumerations.RequestOriginType requestOrigin= SwarcoEnumerations.RequestOriginType.NORMALROAD;
    private OmniaIntersectionData foundRec = new OmniaIntersectionData();
    public OmniaIntersectionData getFoundRec() { return foundRec;}
    public  void setFoundRec(OmniaIntersectionData pFoundRec) {foundRec=pFoundRec;}
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
    public int OmniaIntersectionDataList () throws SQLException {
        OmniaIntersectionDataUnits.clear();
        String SQL="";
        java.sql.PreparedStatement stmt;
        logger.info(" SqlConnectionType =" + SqlConnectionType);
        logger.info("Start ");
        OmniaIntersectionDataSqlServerSelectJson st = new OmniaIntersectionDataSqlServerSelectJson();
        SQL =st.getStatement() ;
        logger.info("SqlConnectionTypeyyyy= "+ SqlConnectionType);
        logger.info("SQL = " +SQL);
        OmniaIntersectionData ce;
        try {
            stmt = gSqlCon.prepareStatement(SQL);
            ResultSet rs;
            rs = stmt.executeQuery();
            logger.info(" rs.getFetchSize() = " + rs.getFetchSize());
            while (rs.next()) {
                ce= new OmniaIntersectionData();
                ce.setOmniaCode(rs.getLong(1));
                ce.setOmniaName(rs.getString(2));
                ce.setOmniaPublicationStatus(rs.getLong(3));
                ce.setIntersectionId(rs.getLong(4));
                ce.setIntesectionDescription(rs.getString(5));
                ce.setIntersectionAreaId(rs.getLong(6));
                ce.setIntersectionExternalCode(rs.getString(7));
                ce.setIntersectionDataPreviousUpdate(rs.getString(8));   // ...Sql Timestamp ?
                ce.setControllerId(rs.getLong(9));
                ce.setControllerDescription(rs.getString(10));
                ce.setControllerExternalCode(rs.getString(11));
                ce.setControllerDataPreviousUpdate(rs.getString(12));  // ...Sql Timestamp ?
                OmniaIntersectionDataUnits.add(ce);
            }
            stmt.close();
            rs.close();
            if (OmniaIntersectionDataUnits.isEmpty()==true) {
                ce= new OmniaIntersectionData();
                ce.MakeEmptyElement();
                OmniaIntersectionDataUnits.add(ce);
                return -1;
            }
            logger.info("bef ret iRet OK");
            return INT_RET_OK;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            ce= new OmniaIntersectionData();
            ce.MakeEmptyElement();
            OmniaIntersectionDataUnits.add(ce);
            gSqlCon.close();
            return -1;
        }
    }
    public int DoesLineAlreadyExsist(OmniaIntersectionData pOmniaIntersectionData) throws SQLException {
        String SQL="";
        java.sql.PreparedStatement stmt=null;
        OmniaIntersectionData oi = new OmniaIntersectionData();
        oi.MakeEmptyElement();
        setFoundRec(oi);
        try {
            SelectIntersectionDataMySqlWhere st = new SelectIntersectionDataMySqlWhere();
            SQL =st.getStatement();
            ResultSet rs;
            stmt = gSqlCon.prepareStatement(SQL);
            stmt.setLong(1,pOmniaIntersectionData.getOmniaCode());
            stmt.setLong(2,pOmniaIntersectionData.getIntersectionId());
            stmt.setLong(3,pOmniaIntersectionData.getControllerId());
            rs = stmt.executeQuery();
            OmniaIntersectionData cc;
            while (rs.next()) {
   //             logger.info("*****inside rsloop");
                cc= new OmniaIntersectionData();
                cc.setOmniaCode(rs.getLong(1));
                cc.setOmniaName(rs.getString(2));
                cc.setOmniaPublicationStatus(rs.getLong(3));
                cc.setIntersectionId(rs.getLong(4));
                cc.setIntesectionDescription(rs.getString(5));
                cc.setIntersectionAreaId(rs.getLong(6));
                cc.setIntersectionExternalCode(rs.getString(7));
                cc.setIntersectionDataPreviousUpdate(rs.getString(8));
                cc.setControllerId(rs.getLong(9));
                cc.setControllerDescription(rs.getString(10));
                cc.setControllerExternalCode(rs.getString(11));
                cc.setControllerDataPreviousUpdate(rs.getString(12));
                if (((cc.getOmniaCode()==(pOmniaIntersectionData.getOmniaCode()) &&
                   (cc.getIntersectionId()==(pOmniaIntersectionData.getIntersectionId()) &&
                   (cc.getControllerId()==(pOmniaIntersectionData.getControllerId())))))) {
                    logger.info("löyty");
                    setFoundRec(cc);
                    stmt.close();
                    rs.close();
                    return INT_RET_FOUND;
                }
            }
            stmt.close();
            rs.close();
            logger.info("ei l�ytyny");
            return INT_RET_NOT_FOUND;
        } catch(Exception e) {
            logger.info(" catch 11");
            logger.info(e.getMessage());
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            e.printStackTrace();
            gSqlCon.close();
            return INT_RET_NOT_OK;
        }
    }
    public int IsItChanged(OmniaIntersectionData pC1 ,OmniaIntersectionData pC2) {
        int iRet =  NOT_CHANGED;
        if (!(pC1.getOmniaName().equals(pC1.getOmniaName()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getOmniaPublicationStatus()==(pC2.getOmniaPublicationStatus()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getIntesectionDescription().equals(pC2.getIntesectionDescription()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getIntersectionAreaId()==(pC2.getIntersectionAreaId()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getIntersectionExternalCode().equals(pC2.getIntersectionExternalCode()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getIntersectionDataPreviousUpdate().equals(pC2.getIntersectionDataPreviousUpdate()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getControllerDescription().equals(pC2.getControllerDescription()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getControllerExternalCode().equals(pC2.getControllerExternalCode()))) {
            iRet =  CHANGED;
        }
        if (!(pC1.getControllerDataPreviousUpdate().equals(pC2.getControllerDataPreviousUpdate()))) {
            iRet =  CHANGED;
        }
//        if (iRet==CHANGED) {
//            logger.info("***Changed1  pC1.toString()" + pC1.toString());
//            logger.info("***Changed2  pC2.toString()" + pC2.toString());
//        }
        return iRet;
    }
    public int MakeDeleteInsert(OmniaIntersectionData pC1) throws SQLException {
        int iRet = INT_RET_OK;
        iRet = DeleteOldOmniaInterserctioDataLineFromDb(pC1);
        if (iRet < 0) {
            logger.info("iRet = " + iRet);
            iRet = UNSUCCESSFUL_DATABASE_DELETE_OPERATION;
            logger.info("iRet = " + iRet);
            return iRet;
        }
        iRet = AddNewOmniaIntersectionData(pC1);
        if (iRet < 0) {
            logger.info("***********************Successful delete unsuccesful insert iRet = " + iRet);
            iRet = UNSUCCESSFUL_DATABASE_DELETE_OPERATION;
            logger.info("***********************Successful delete unsuccesful insert iRet = " + iRet);
            return iRet;
        }
        return iRet;
    }
    private int DeleteOldOmniaInterserctioDataLineFromDb(OmniaIntersectionData pC1 ) throws SQLException {
        int iRet;
        String SQL = "";
        try {
            java.sql.PreparedStatement stmt;
            SQL = " delete from OmniaIntersectionData ";
            SQL = SQL + "where OmniaCode = " + pC1.getOmniaCode() + " and " +
                        " IntersectionId = " + pC1.getIntersectionId() + " and " +
                        " ControllerId = " + pC1.getControllerId() +  ";";
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
            e.printStackTrace();
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            gSqlCon.close();
            return UNSUCCESSFUL_DATABASE_OPERATION;
        }
    }
    public  int AddNewOmniaIntersectionData(OmniaIntersectionData pOmniaIntersectionData) throws SQLException {
        int iRet;
        String SQL="";
        java.sql.PreparedStatement stmt;
        try {
            InsertIntersectionDataMySql st = new InsertIntersectionDataMySql();
            SQL =st.getStatement();
//            logger.info("SQL = " + SQL);
//            logger.info("pOmniaIntersectionData.toString()=" + pOmniaIntersectionData.toString());
            stmt = gSqlCon.prepareStatement(SQL);
            int pos=0;
            pos=1;
            stmt.setLong(pos,pOmniaIntersectionData.getOmniaCode());
            pos=pos+1;
            stmt.setString(pos,pOmniaIntersectionData.getOmniaName());
            pos=pos+1;
            stmt.setLong(pos,pOmniaIntersectionData.getOmniaPublicationStatus());
            pos=pos+1;
            stmt.setLong(pos,pOmniaIntersectionData.getIntersectionId());
            pos=pos+1;
            stmt.setString(pos,pOmniaIntersectionData.getIntesectionDescription());
            pos=pos+1;
            stmt.setLong(pos,pOmniaIntersectionData.getIntersectionAreaId());
            pos=pos+1;
            stmt.setString(pos,pOmniaIntersectionData.getControllerExternalCode());
            pos=pos+1;
            stmt.setString(pos,pOmniaIntersectionData.getControllerDataPreviousUpdate());
            pos=pos+1;
            stmt.setLong(pos,pOmniaIntersectionData.getControllerId());
            pos=pos+1;
            stmt.setString(pos,pOmniaIntersectionData.getControllerDescription());
            pos=pos+1;
            stmt.setString(pos,pOmniaIntersectionData.getControllerExternalCode());
            pos=pos+1;
            stmt.setString(pos,pOmniaIntersectionData.getIntersectionDataPreviousUpdate());
            iRet = stmt.executeUpdate();
            stmt.close();
            return iRet;
        } catch(Exception e) {
            logger.info(" catch 11  e=",e);
            logger.info(e.getMessage());
            e.printStackTrace();
            gSqlCon.close();
            return UNSUCCESSFUL_DATABASE_INSERT_OPERATION;
        }
    }
    public  List<OmniaIntersectionData> GetOmniaIntersectionDataList()  {
        OmniaIntersectionData cc = new OmniaIntersectionData();
        if (OmniaIntersectionDataUnits.isEmpty()==true) {
            cc= new OmniaIntersectionData();
            cc.MakeEmptyElement();
            OmniaIntersectionDataUnits.add(cc);
        }
        return OmniaIntersectionDataUnits;
    }
    public  int JsonOmniaIntersectionSql(String pPermanentData) throws SQLException{
        int iRet=0;
        String strHelp1=NO_VALUE;
        try {
            Gson myGson = new Gson();
            MessageUtils mu = new MessageUtils();
            strHelp1 = pPermanentData;
            JsonParser jsonParser = new JsonParser();
            OmniaIntersectionData aOmniaIntersection1 = myGson.fromJson(strHelp1, OmniaIntersectionData.class);
            SwarcoTimeUtilities sw = new  SwarcoTimeUtilities();
            String swarcoTime =sw.ToSwarcoTime(aOmniaIntersection1.getIntersectionDataPreviousUpdate());
            aOmniaIntersection1.setIntersectionDataPreviousUpdate(swarcoTime);
            swarcoTime = sw.ToSwarcoTime(aOmniaIntersection1.getControllerDataPreviousUpdate());
            aOmniaIntersection1.setControllerDataPreviousUpdate(swarcoTime);
            iRet= DoesLineAlreadyExsist(aOmniaIntersection1);
            if (iRet==INT_RET_NOT_FOUND) {
               iRet=AddNewOmniaIntersectionData(aOmniaIntersection1);
               logger.info("  one line inserted iRet = " + iRet);
               if (iRet!=INT_RET_OK) {
                   logger.info("Unsuccessful OmniaIntersectionData insert iRet = " + iRet);
               }
               return iRet;
              } else if (iRet==INT_RET_FOUND) {
                  iRet=IsItChanged(aOmniaIntersection1,getFoundRec());
                  if (iRet==CHANGED) {
                      iRet =MakeDeleteInsert(aOmniaIntersection1);   // Keys are same
                      if (iRet!=INT_RET_OK) {
                          logger.info("Unsuccessful MakeDeleteInsert operation iRet = " + iRet);
                          return iRet;
                      }
                  }
                  return iRet;    // NOT_CHANGED
               } else {
                   logger.info("Unsuccessful DoesLineAlreadyExsist insert iRet = " + iRet);
                   return iRet;
               }
        } catch (Exception e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            e.printStackTrace();
            gSqlCon.close();
            return UNSUCCESSFUL_DATABASE_INSERT_OPERATION;
        }
    }
}








