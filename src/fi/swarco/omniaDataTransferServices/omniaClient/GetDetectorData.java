package fi.swarco.omniaDataTransferServices.omniaClient;
//GetDetectorData

import fi.swarco.CONSTANT;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.connections.SwarcoConnections;
import fi.swarco.dataHandling.queriesSql.sqlServer.InsertDetectorTasks;
import fi.swarco.dataHandling.queriesSql.sqlServer.InsertIntersectionTasks;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

import static fi.swarco.CONSTANT.*;
import static fi.swarco.CONSTANT.INT_RET_OK;
public class GetDetectorData {
    private static Logger logger = Logger.getLogger(GetDetectorData.class.getName());
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
    public int AddDetectorDataLines() throws SQLException {
        int iRet=INT_RET_OK;
        String SQL="";
        java.sql.PreparedStatement stmt;
        try {
            InsertDetectorTasks st = new InsertDetectorTasks();
            SQL =st.getStatement();
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            stmt.close();
            if (iRet<0) {
                iRet= CONSTANT.UNSUCCESSFUL_DATABASE_INSERT_OPERATION;
                return iRet;
            }
            return iRet;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
        }
        return iRet;
    }
    public int AddIntersectionControllerDataLines()  {
        int iRet=INT_RET_OK;
        String SQL="";
        java.sql.PreparedStatement stmt;
        try {
            InsertIntersectionTasks st = new InsertIntersectionTasks();
            SQL =st.getStatement();
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            stmt.close();
            if (iRet<0) {
                iRet= CONSTANT.UNSUCCESSFUL_DATABASE_INSERT_OPERATION;
                return iRet;
            }
            return iRet;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
        }
        return iRet;
    }


}





