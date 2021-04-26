package fi.swarco.dataHandling;
import java.sql.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import fi.swarco.CONSTANT;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.connections.SwarcoConnections;
import fi.swarco.dataHandling.pojos.RawData;
import fi.swarco.dataHandling.queriesSql.mySQL.InsertRawDataMySql;
import fi.swarco.dataHandling.queriesSql.sqlServer.InsertRawDataSqlServer;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import static fi.swarco.CONSTANT.*;
public class RawDataDataListLevel {
    private static Logger logger = Logger.getLogger(RawDataDataListLevel.class.getName());
    List<RawData> RawDataUnits = Collections.synchronizedList(new LinkedList<>());  // ????
    static Connection gSqlCon;
    private SwarcoEnumerations.ConnectionType SqlConnectionType=SwarcoEnumerations.ConnectionType.NOT_DEFINED;
    public SwarcoEnumerations.ConnectionType getSqlConnectionType() {
        return SqlConnectionType;
    }
    public void setSqlConnectionType( SwarcoEnumerations.ConnectionType pSqlConnectionType) {
        this.SqlConnectionType = pSqlConnectionType;
    }
    SwarcoEnumerations.RequestOriginType requestOrigin= SwarcoEnumerations.RequestOriginType.NORMALROAD;
    public void setRequestOrigin(SwarcoEnumerations.RequestOriginType prequestOrigin) {
        requestOrigin=prequestOrigin;
    }
    public  int MakeConnection(SwarcoEnumerations.ConnectionType pSqlCon) {
        SwarcoConnections vg = new SwarcoConnections();
        int iRet = vg.MakeConnection(pSqlCon);
        if (iRet!=INT_RET_OK) {
            return iRet;
        }
        setSqlConnectionType(pSqlCon);
        gSqlCon = vg.getSqlCon();
        return DATABASE_CONNECTION_OK;
    }
    public  RawDataDataListLevel () {}
    public int AddNewRawData(RawData pRawData) throws SQLException{
        int iRet;
        String SQL="";
        java.sql.PreparedStatement stmt;
        try {
            if (getSqlConnectionType().equals(SwarcoEnumerations.ConnectionType.MYSQL_LOCAL_JATRI2)) {
               InsertRawDataMySql st = new InsertRawDataMySql();
               SQL =st.getStatement();
  //              logger.info("MySql  SQL = " + SQL );
            } else  {   // RETHINK everything else is here
               InsertRawDataSqlServer st = new InsertRawDataSqlServer();
               SQL =st.getStatement();
 //               logger.info(" sql server SQL = "+ SQL);
            }
           stmt = gSqlCon.prepareStatement(SQL);
           int pos=0;
           pos=1;
           stmt.setLong(pos,pRawData.getRawDataSourceId());
           pos=pos+1;
           stmt.setLong(pos,pRawData.getRawDataStatus());
           pos=pos+1;
           stmt.setString(pos,pRawData.getRawDataStatusString());
           pos=pos+1;
           stmt.setString(pos,pRawData.getRawDataLine());
           pos=pos+1;
           stmt.setString(pos,pRawData.getTimestamp());
           iRet = stmt.executeUpdate();
           stmt.close();
           if (iRet!=1) {
               iRet= CONSTANT.UNSUCCESSFUL_DATABASE_INSERT_OPERATION;
               return iRet;
           }
           return iRet;
        } catch(Exception e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
             e.printStackTrace();
           // stmt.close();    RETHINK ????
            gSqlCon.close();
            return UNSUCCESSFUL_DATABASE_OPERATION;
        }
    }
}
