package fi.swarco.dataHandling;
import java.sql.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.connections.SwarcoConnections;
import fi.swarco.dataHandling.omniaClientDataHandling.DetectorMeasurementsClientDataLevel;
import fi.swarco.dataHandling.omniaClientDataHandling.DetectorMeasurementsShortClientDataLevel;
import fi.swarco.dataHandling.omniaClientDataHandling.OmniaIntersectionListClientDataLevel;
import fi.swarco.dataHandling.pojos.TRPXMeasurementTaskData;
import fi.swarco.dataHandling.queriesSql.sqlServer.JiMeasurementTaskSelectSqlServer;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import static fi.swarco.CONSTANT.*;
public class MeasurementTaskHandling {
    private static Logger logger = Logger.getLogger(MeasurementTaskHandling.class.getName());
    private List<TRPXMeasurementTaskData> TaskUnits = Collections.synchronizedList(new LinkedList<TRPXMeasurementTaskData>());  // ????
    static Connection gSqlCon;
    private static SwarcoEnumerations.ConnectionType SqlConnectionType = SwarcoEnumerations.ConnectionType.NOT_DEFINED;
    SwarcoEnumerations.RequestOriginType requestOrigin = SwarcoEnumerations.RequestOriginType.NORMALROAD;
    public void setRequestOrigin(SwarcoEnumerations.RequestOriginType prequestOrigin) {
        requestOrigin = prequestOrigin;
    }
    public static int MakeConnection(SwarcoEnumerations.ConnectionType pSqlCon) throws SQLException {
        SwarcoConnections vg = new SwarcoConnections();
        logger.info("pSqlCon = " + pSqlCon);
        int iRet = vg.MakeConnection(pSqlCon);
        if (iRet != INT_RET_OK) {
            return iRet;
        }
        SqlConnectionType = pSqlCon;
        logger.info("SqlConnectionType = " + SqlConnectionType);
        gSqlCon = vg.getSqlCon();
        return INT_RET_OK;
    }
    public int MeasurementTaskDataList() throws SQLException {
        TaskUnits.clear();
        String SQL = "";
        java.sql.PreparedStatement stmt;
        ResultSet rs;
        JiMeasurementTaskSelectSqlServer st = new JiMeasurementTaskSelectSqlServer();
        SQL = st.getStatement();
        logger.info("SqlConnectionTypeyyyy= " + SqlConnectionType);
        logger.info("SQL = " + SQL);
        TRPXMeasurementTaskData ce;
        try {
            stmt = gSqlCon.prepareStatement(SQL);
            rs = stmt.executeQuery();
            logger.info(" rs.getFetchSize() = " + rs.getFetchSize());
            while (rs.next()) {
                ce = new TRPXMeasurementTaskData();
                ce.setMeasurementTaskIdindex(rs.getLong(1));
                ce.setOmniaCode(rs.getLong(2));
                ce.setIntersectionId(rs.getLong(3));
                ce.setControllerId(rs.getLong(4));
                ce.setDetectorId(rs.getLong(5));
                ce.setDetectorMeasuresTimestamp(rs.getString(6));
                ce.setTaskType(rs.getString(7));
                ce.setTaskStatus(rs.getLong(8));
                ce.setCreated(rs.getString(9));
                TaskUnits.add(ce);
            }
            stmt.close();
            rs.close();
            if (TaskUnits.isEmpty() == true) {
                ce = new TRPXMeasurementTaskData();
                ce.MakeEmptyElement();
                TaskUnits.add(ce);
                return NO_TASK_LIST;
            }
            logger.info("bef ret iRet OK");
            return INT_RET_OK;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            ce = new TRPXMeasurementTaskData();
            ce.MakeEmptyElement();
            TaskUnits.add(ce);
            gSqlCon.close();
            return NO_TASK_LIST;
        }
    }
    public int AnyWorkWork() throws SQLException{
        int iRet = INT_RET_NOT_OK;
        String SQL = "";
        try {
            SQL = "select count(*) from TRPX_MeasurementTask_Work;";
            logger.info("SQL = " + SQL);
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            ResultSet rs;
            rs = stmt.executeQuery();
            logger.info(" rs.getFetchSize() = " + rs.getFetchSize());
            while (rs.next()) {
                iRet = rs.getInt(1);
                stmt.close();
                rs.close();
                return iRet;
            }
            stmt.close();
            rs.close();
            return iRet;
        } catch (Exception e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            logger.info(" catch 11");
            logger.info(e.getMessage());
            e.printStackTrace();
            gSqlCon.close();
            return UNSUCCESSFUL_DATABASE_OPERATION;
        }
    }
    public int AnyWork() throws SQLException{
        int iRet = INT_RET_NOT_OK;
        String SQL = "";
        try {
            SQL = "select count(*) from TRPX_MeasurementTask;";
            logger.info("SQL = " + SQL);
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            ResultSet rs;
            rs = stmt.executeQuery();
            logger.info(" rs.getFetchSize() = " + rs.getFetchSize());
             while (rs.next()) {
                iRet = rs.getInt(1);
                stmt.close();
                rs.close();
                return iRet;
            }
            stmt.close();
            rs.close();
            return iRet;
        } catch (Exception e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            logger.info(" catch 11");
            logger.info(e.getMessage());
            e.printStackTrace();
            gSqlCon.close();
            return UNSUCCESSFUL_DATABASE_OPERATION;
        }
    }
    public int AnyWorkMeasurements() throws SQLException{
        int iRet = INT_RET_NOT_OK;
        String SQL = "";
        try {
            SQL = "select count(*) from TRPX_MeasurementTask where taskType='NOTDEFINED';";
            logger.info("SQL = " + SQL);
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            ResultSet rs;
            rs = stmt.executeQuery();
            logger.info(" rs.getFetchSize() = " + rs.getFetchSize());
            while (rs.next()) {
                iRet = rs.getInt(1);
                stmt.close();
                rs.close();
                return iRet;
            }
            stmt.close();
            rs.close();
            return iRet;
        } catch (Exception e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            logger.info(" catch 11");
            logger.info(e.getMessage());
            e.printStackTrace();
            gSqlCon.close();
            return UNSUCCESSFUL_DATABASE_OPERATION;
        }
    }
    public int AnyWorkIntersection() throws SQLException{
        int iRet = INT_RET_NOT_OK;
        String SQL = "";
        try {
            SQL = "select count(*) from TRPX_MeasurementTask  where taskType='INTERSECTIONDATACHANGE' ;";
            logger.info("SQL = " + SQL);
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            ResultSet rs;
            rs = stmt.executeQuery();
            logger.info(" rs.getFetchSize() = " + rs.getFetchSize());
            while (rs.next()) {
                iRet = rs.getInt(1);
                stmt.close();
                rs.close();
                return iRet;
            }
            stmt.close();
            rs.close();
            return iRet;
        } catch (Exception e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            logger.info(" catch 11");
            logger.info(e.getMessage());
            e.printStackTrace();
            gSqlCon.close();
            return UNSUCCESSFUL_DATABASE_OPERATION;
        }
    }
    public int AnyWorkController() throws SQLException{
        int iRet = INT_RET_NOT_OK;
        String SQL = "";
        try {
            SQL = "select count(*) from TRPX_MeasurementTask  where taskType='CONTROLLERDATACHANGE';";
            logger.info("SQL = " + SQL);
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            ResultSet rs;
            rs = stmt.executeQuery();
            logger.info(" rs.getFetchSize() = " + rs.getFetchSize());
            while (rs.next()) {
                iRet = rs.getInt(1);
                stmt.close();
                rs.close();
                return iRet;
            }
            stmt.close();
            rs.close();
            return iRet;
        } catch (Exception e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            logger.info(" catch 11");
            logger.info(e.getMessage());
            e.printStackTrace();
            gSqlCon.close();
            return UNSUCCESSFUL_DATABASE_OPERATION;
        }
    }
    public int AnyWorkDetector() throws SQLException{
        int iRet = INT_RET_NOT_OK;
        String SQL = "";
        try {
            SQL = "select count(*) from TRPX_MeasurementTask where taskType='DETECTORDATACHANGE';";
            logger.info("SQL = " + SQL);
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            ResultSet rs;
            rs = stmt.executeQuery();
            logger.info(" rs.getFetchSize() = " + rs.getFetchSize());
            while (rs.next()) {
                iRet = rs.getInt(1);
                stmt.close();
                rs.close();
                return iRet;
            }
            stmt.close();
            rs.close();
            return iRet;
        } catch (Exception e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            logger.info(" catch 11");
            logger.info(e.getMessage());
            e.printStackTrace();
            gSqlCon.close();
            return UNSUCCESSFUL_DATABASE_OPERATION;
        }
    }
public int TransferIntersectionTasksToWorkQueue() throws SQLException{
        int iRet;
        String SQL = "";
        try {
            SQL = "insert into TRPX_MeasurementTask_Work ";
            SQL = SQL + " ([MeasurementTask_idindex] ";
            SQL = SQL + " ,[OmniaCode] ";
            SQL = SQL + " ,[IntersectionID] ";
            SQL = SQL + " ,[ControllerId] ";
            SQL = SQL + " ,[DetectorID] ";
            SQL = SQL + " ,[DetectorMeasuresTimestamp] ";
            SQL = SQL + " ,[TaskType] ";
            SQL = SQL + " ,[TaskStatus] ";
            SQL = SQL + " ,[Created] ";
            SQL = SQL + " ,[Updated] ";
            SQL = SQL + " ,[WorkCreated]) ";
            SQL = SQL + "select ";
            SQL = SQL + " [MeasurementTask_idindex] ";
            SQL = SQL + " ,[OmniaCode] ";
            SQL = SQL + " ,[IntersectionID] ";
            SQL = SQL + " ,[ControllerId] ";
            SQL = SQL + " ,[DetectorID] ";
            SQL = SQL + " ,[DetectorMeasuresTimestamp] ";
            SQL = SQL + " ,[TaskType] ";
            SQL = SQL + " ,[TaskStatus] ";
            SQL = SQL + " ,[Created] ";
            SQL = SQL + " ,GETDATE() ";
            SQL = SQL + " , GETDATE() ";
            SQL = SQL + " from TRPX_MeasurementTask  ";
            SQL = SQL + "where TaskType = 'INTERSECTIONDATACHANGE' and  " ;
            SQL = SQL +  " DetectorMeasuresTimestamp = ";
            SQL = SQL + " (select top 1 DetectorMeasuresTimestamp from TRPX_MeasurementTask  where TaskType = 'INTERSECTIONDATACHANGE'  order by DetectorMeasuresTimestamp asc) ";
            logger.info("SQL = " + SQL);
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            stmt.close();
            logger.info("Lines inserted iRet = " + iRet);
            if (iRet < 0) {
                gSqlCon.close();
                iRet = TASK_TRANSFER_ERROR;
                return iRet;
            }
            return TASK_TRANSFER_OK;
        } catch (Exception e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            logger.info(" catch 11");
            logger.info(e.getMessage());
            e.printStackTrace();
            gSqlCon.close();
            return TASK_TRANSFER_ERROR;
        }
    }
    public int TransferControllerTasksToWorkQueue() throws SQLException{
        int iRet;
        String SQL = "";
        try {
            SQL = "insert into TRPX_MeasurementTask_Work ";
            SQL = SQL + " ([MeasurementTask_idindex] ";
            SQL = SQL + " ,[OmniaCode] ";
            SQL = SQL + " ,[IntersectionID] ";
            SQL = SQL + " ,[ControllerId] ";
            SQL = SQL + " ,[DetectorID] ";
            SQL = SQL + " ,[DetectorMeasuresTimestamp] ";
            SQL = SQL + " ,[TaskType] ";
            SQL = SQL + " ,[TaskStatus] ";
            SQL = SQL + " ,[Created] ";
            SQL = SQL + " ,[Updated] ";
            SQL = SQL + " ,[WorkCreated]) ";
            SQL = SQL + "select ";
            SQL = SQL + " [MeasurementTask_idindex] ";
            SQL = SQL + " ,[OmniaCode] ";
            SQL = SQL + " ,[IntersectionID] ";
            SQL = SQL + " ,[ControllerId] ";
            SQL = SQL + " ,[DetectorID] ";
            SQL = SQL + " ,[DetectorMeasuresTimestamp] ";
            SQL = SQL + " ,[TaskType] ";
            SQL = SQL + " ,[TaskStatus] ";
            SQL = SQL + " ,[Created] ";
            SQL = SQL + " ,GETDATE() ";
            SQL = SQL + " , GETDATE() ";
            SQL = SQL + " from TRPX_MeasurementTask  ";
            SQL = SQL + "where TaskType = 'CONTROLLERDATACHANGE' and  " ;
                    SQL = SQL +  " DetectorMeasuresTimestamp = ";
            SQL = SQL + " (select top 1 DetectorMeasuresTimestamp from TRPX_MeasurementTask where  TaskType = 'CONTROLLERDATACHANGE'  order by DetectorMeasuresTimestamp asc) ";
            logger.info("SQL = " + SQL);
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            logger.info("Lines inserted iRet = " + iRet);
            stmt.close();
            if (iRet < 0) {
                gSqlCon.close();
                iRet = TASK_TRANSFER_ERROR;
                return iRet;
            }
            return TASK_TRANSFER_OK;
        } catch (Exception e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            logger.info(" catch 11");
            logger.info(e.getMessage());
            e.printStackTrace();
            gSqlCon.close();
            return TASK_TRANSFER_ERROR;
        }
    }
    public int TransferDetectorTasksToWorkQueue() throws SQLException{
        int iRet;
        String SQL = "";
        try {
            SQL = "insert into TRPX_MeasurementTask_Work ";
            SQL = SQL + " ([MeasurementTask_idindex] ";
            SQL = SQL + " ,[OmniaCode] ";
            SQL = SQL + " ,[IntersectionID] ";
            SQL = SQL + " ,[ControllerId] ";
            SQL = SQL + " ,[DetectorID] ";
            SQL = SQL + " ,[DetectorMeasuresTimestamp] ";
            SQL = SQL + " ,[TaskType] ";
            SQL = SQL + " ,[TaskStatus] ";
            SQL = SQL + " ,[Created] ";
            SQL = SQL + " ,[Updated] ";
            SQL = SQL + " ,[WorkCreated]) ";
            SQL = SQL + "select ";
            SQL = SQL + " [MeasurementTask_idindex] ";
            SQL = SQL + " ,[OmniaCode] ";
            SQL = SQL + " ,[IntersectionID] ";
            SQL = SQL + " ,[ControllerId] ";
            SQL = SQL + " ,[DetectorID] ";
            SQL = SQL + " ,[DetectorMeasuresTimestamp] ";
            SQL = SQL + " ,[TaskType] ";
            SQL = SQL + " ,[TaskStatus] ";
            SQL = SQL + " ,[Created] ";
            SQL = SQL + " ,GETDATE() ";
            SQL = SQL + " , GETDATE() ";
            SQL = SQL + " from TRPX_MeasurementTask  ";
            SQL = SQL + "where TaskType = 'DETECTORDATACHANGE' and  " ;
            SQL = SQL +  " DetectorMeasuresTimestamp = ";
            SQL = SQL + " (select top 1 DetectorMeasuresTimestamp from TRPX_MeasurementTask where  TaskType = 'DETECTORDATACHANGE'  order by DetectorMeasuresTimestamp asc) ";
            logger.info("SQL = " + SQL);
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            logger.info("Lines inserted iRet = " + iRet);
            stmt.close();
            if (iRet < 0) {
                gSqlCon.close();
                iRet = TASK_TRANSFER_ERROR;
                return iRet;
            }
            return TASK_TRANSFER_OK;
        } catch (Exception e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            logger.info(" catch 11");
            logger.info(e.getMessage());
            e.printStackTrace();
            gSqlCon.close();
            return TASK_TRANSFER_ERROR;
        }
    }
    public int TransferMeasurementTasksToWorkQueue() throws SQLException{
        int iRet;
        String SQL = "";
        try {
            SQL = "insert into TRPX_MeasurementTask_Work ";
            SQL = SQL + " ([MeasurementTask_idindex] ";
            SQL = SQL + " ,[OmniaCode] ";
            SQL = SQL + " ,[IntersectionID] ";
            SQL = SQL + " ,[ControllerId] ";
            SQL = SQL + " ,[DetectorID] ";
            SQL = SQL + " ,[DetectorMeasuresTimestamp] ";
            SQL = SQL + " ,[TaskType] ";
            SQL = SQL + " ,[TaskStatus] ";
            SQL = SQL + " ,[Created] ";
            SQL = SQL + " ,[Updated] ";
            SQL = SQL + " ,[WorkCreated]) ";
            SQL = SQL + "select ";
            SQL = SQL + " [MeasurementTask_idindex] ";
            SQL = SQL + " ,[OmniaCode] ";
            SQL = SQL + " ,[IntersectionID] ";
            SQL = SQL + " ,[ControllerId] ";
            SQL = SQL + " ,[DetectorID] ";
            SQL = SQL + " ,[DetectorMeasuresTimestamp] ";
            SQL = SQL + " ,[TaskType] ";
            SQL = SQL + " ,[TaskStatus] ";
            SQL = SQL + " ,[Created] ";
            SQL = SQL + " ,GETDATE() ";
            SQL = SQL + " , GETDATE() ";
            SQL = SQL + " from TRPX_MeasurementTask  ";
            SQL = SQL + "where TaskType = 'NOTDEFINED' and  " ;
                    SQL = SQL +  " DetectorMeasuresTimestamp = ";
            SQL = SQL + " (select top 1 DetectorMeasuresTimestamp from TRPX_MeasurementTask where  TaskType = 'NOTDEFINED'  order by DetectorMeasuresTimestamp asc) ";
            logger.info("SQL = " + SQL);
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            logger.info("Lines inserted iRet = " + iRet);
            stmt.close();
            if (iRet < 0) {
                gSqlCon.close();
                iRet = TASK_TRANSFER_ERROR;
                return iRet;
            }
            return TASK_TRANSFER_OK;
        } catch (Exception e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            logger.info(" catch 11");
            logger.info(e.getMessage());
            e.printStackTrace();
            gSqlCon.close();
            return TASK_TRANSFER_ERROR;
        }
    }
    public int TransferTasksToWorkQueue()  throws SQLException {
        int iRet;
        String SQL = "";
        try {
            SQL = "insert into TRPX_MeasurementTask_Work ";
            SQL = SQL + " ([MeasurementTask_idindex] ";
            SQL = SQL + " ,[OmniaCode] ";
            SQL = SQL + " ,[IntersectionID] ";
            SQL = SQL + " ,[ControllerId] ";
            SQL = SQL + " ,[DetectorID] ";
            SQL = SQL + " ,[DetectorMeasuresTimestamp] ";
            SQL = SQL + " ,[TaskType] ";
            SQL = SQL + " ,[TaskStatus] ";
            SQL = SQL + " ,[Created] ";
            SQL = SQL + " ,[Updated] ";
            SQL = SQL + " ,[WorkCreated]) ";
            SQL = SQL + "select ";
            SQL = SQL + " [MeasurementTask_idindex] ";
            SQL = SQL + " ,[OmniaCode] ";
            SQL = SQL + " ,[IntersectionID] ";
            SQL = SQL + " ,[ControllerId] ";
            SQL = SQL + " ,[DetectorID] ";
            SQL = SQL + " ,[DetectorMeasuresTimestamp] ";
            SQL = SQL + " ,[TaskType] ";
            SQL = SQL + " ,[TaskStatus] ";
            SQL = SQL + " ,[Created] ";
            SQL = SQL + " ,GETDATE() ";
            SQL = SQL + " , GETDATE() ";
            SQL = SQL + " from TRPX_MeasurementTask  ";
            SQL = SQL + "where DetectorMeasuresTimestamp = ";
            SQL = SQL + " (select top 1 DetectorMeasuresTimestamp from TRPX_MeasurementTask order by DetectorMeasuresTimestamp asc) ";
            logger.info("SQL = " + SQL);
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            logger.info("Lines inserted iRet = " + iRet);
            stmt.close();
            if (iRet < 0) {
                gSqlCon.close();
                iRet = TASK_TRANSFER_ERROR;
                return iRet;
            }
            return TASK_TRANSFER_OK;
        } catch (Exception e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            logger.info(" catch 11");
            logger.info(e.getMessage());
            e.printStackTrace();
            gSqlCon.close();
            return TASK_TRANSFER_ERROR;
        }
    }
    public int FillUpIntersectionTasks() throws SQLException{
        int iRet;
        String SQL = "";
        try {
            SQL = " update TRPX_MeasurementTask_Work ";
            SQL = SQL + " set TRPX_MeasurementTask_Work.OmniaCode  = TRPX_Super2.OmniaCode,  ";
            SQL = SQL + " TRPX_MeasurementTask_Work.IntersectionID = TRPX_Super2.IntersectionID, ";
            SQL = SQL + " TRPX_MeasurementTask_Work.ControllerId = TRPX_Super2.ControllerId, ";
            SQL = SQL + "TRPX_MeasurementTask_Work.TaskStatus=1, ";
            SQL = SQL + "TRPX_MeasurementTask_Work.Updated=getdate() ";
            SQL = SQL + "from TRPX_MeasurementTask_Work ";
            SQL = SQL + "join TRPX_Super2  on TRPX_MeasurementTask_Work.IntersectionID=TRPX_Super2.IntersectionID ";
            SQL = SQL + "where  TRPX_MeasurementTask_Work.TaskType='INTERSECTIONDATACHANGE' and ";
            SQL = SQL + "TRPX_MeasurementTask_Work.TaskStatus=0 and ";
            SQL = SQL + "TRPX_MeasurementTask_Work.DetectorMeasuresTimestamp = ";
            SQL = SQL + "  (select top 1 DetectorMeasuresTimestamp from TRPX_MeasurementTask_Work ";
            SQL = SQL + "where  TaskType='INTERSECTIONDATACHANGE' and ";
            SQL = SQL + " TaskStatus=0 ";
            SQL = SQL + "order by DetectorMeasuresTimestamp desc); ";
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            logger.info("Lines updated  iRet = " + iRet);
            stmt.close();
            if (iRet < 0) {
                gSqlCon.close();
                iRet = FILL_UP_TASK_ERROR;
                return iRet;
            }
            return FILL_UP_TASK_OK;
        } catch (Exception e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            logger.info(" catch 11");
            logger.info(e.getMessage());
            e.printStackTrace();
            gSqlCon.close();
            return FILL_UP_TASK_ERROR;
        }
    }
    public int FillUpTasks() throws SQLException{
        int iRet;
        String SQL = "";
        try {
            SQL = " update TRPX_MeasurementTask_Work ";
            SQL = SQL + " set TRPX_MeasurementTask_Work.OmniaCode  = TRPX_Super2.OmniaCode,  ";
            SQL = SQL + " TRPX_MeasurementTask_Work.IntersectionID = TRPX_Super2.IntersectionID, ";
            SQL = SQL + " TRPX_MeasurementTask_Work.ControllerId = TRPX_Super2.ControllerId, ";
            SQL = SQL + "TRPX_MeasurementTask_Work.TaskStatus=1, ";
            SQL = SQL + "TRPX_MeasurementTask_Work.Updated=getdate() ";
            SQL = SQL + "from TRPX_MeasurementTask_Work ";
            SQL = SQL + "join TRPX_Super2  on TRPX_MeasurementTask_Work.DetectorId=TRPX_Super2.DetectorID ";
            SQL = SQL + "where  TRPX_MeasurementTask_Work.TaskType='NOTDEFINED' and ";
            SQL = SQL + "TRPX_MeasurementTask_Work.TaskStatus=0 and ";
            SQL = SQL + "TRPX_MeasurementTask_Work.DetectorMeasuresTimestamp = ";
            SQL = SQL + "  (select top 1 DetectorMeasuresTimestamp from TRPX_MeasurementTask_Work ";
            SQL = SQL + "where  TaskType='NOTDEFINED' and ";
            SQL = SQL + " TaskStatus=0 ";
            SQL = SQL + "order by DetectorMeasuresTimestamp desc); ";
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            logger.info("Lines filledUp??? updated  iRet = " + iRet);
            stmt.close();
            if (iRet < 0) {
                gSqlCon.close();
                iRet = FILL_UP_TASK_ERROR;
                return iRet;
            }
            return FILL_UP_TASK_OK;
        } catch (Exception e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            logger.info(" catch 11");
            logger.info(e.getMessage());
            e.printStackTrace();
            gSqlCon.close();
            return FILL_UP_TASK_ERROR;
        }
    }
    public int DeleteNotFilledIntersectionTasks() throws SQLException{
        int iRet;
        String SQL = "";
        try {
            SQL = "delete from TRPX_MeasurementTask_Work  ";
            SQL = SQL + " where   ";
            SQL = SQL + " OmniaCode=7777 and  ";
            SQL = SQL + " IntersectionID=7777 and  ";
            SQL = SQL + " ControllerId=7777 and  ";
            SQL = SQL + " TaskType='INTERSECTIONDATACHANGE' and  ";
            SQL = SQL + " TaskStatus=0;  ";
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            logger.info("Lines updated  iRet = " + iRet);
            stmt.close();
            if (iRet < 0) {
                gSqlCon.close();
                iRet = DELETE_UNFILLABLE_TASK_ERROR;
                return iRet;
            }
            return DELETE_UNFILLABLE_TASK_OK;
        } catch (Exception e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            logger.info(" catch 11");
            logger.info(e.getMessage());
            e.printStackTrace();
            gSqlCon.close();
            return DELETE_UNFILLABLE_TASK_ERROR;
        }
    }
    public int DeleteNotFilledControllerTasks() throws SQLException{
        int iRet;
        String SQL = "";
        try {
            SQL = "delete from TRPX_MeasurementTask_Work  ";
            SQL = SQL + " where   ";
            SQL = SQL + " OmniaCode=7777 and  ";
            SQL = SQL + " IntersectionID=7777 and  ";
            SQL = SQL + " ControllerId=7777 and  ";
            SQL = SQL + " TaskType='CONTROLLERDATACHANGE' and  ";
            SQL = SQL + " TaskStatus=0;  ";
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            logger.info("Lines updated  iRet = " + iRet);
            stmt.close();
            if (iRet < 0) {
                iRet = DELETE_UNFILLABLE_TASK_ERROR;
                gSqlCon.close();
                return iRet;
            }
            return DELETE_UNFILLABLE_TASK_OK;
        } catch (Exception e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            logger.info(" catch 11");
            logger.info(e.getMessage());
            e.printStackTrace();
            gSqlCon.close();
            return DELETE_UNFILLABLE_TASK_ERROR;
        }
    }
    public int DeleteNotDetectorTasks() throws SQLException{
        int iRet;
        String SQL = "";
        try {
            SQL = "delete from TRPX_MeasurementTask_Work  ";
            SQL = SQL + " where   ";
            SQL = SQL + " OmniaCode=7777 and  ";
            SQL = SQL + " IntersectionID=7777 and  ";
            SQL = SQL + " ControllerId=7777 and  ";
            SQL = SQL + " TaskType='DETECTORDATACHANGE' and  ";
            SQL = SQL + " TaskStatus=0;  ";
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            logger.info("Lines updated  iRet = " + iRet);
            stmt.close();
            if (iRet < 0) {
                gSqlCon.close();
                iRet = DELETE_UNFILLABLE_TASK_ERROR;
                return iRet;
            }
            return DELETE_UNFILLABLE_TASK_OK;
        } catch (Exception e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            logger.info(" catch 11");
            logger.info(e.getMessage());
            e.printStackTrace();
            gSqlCon.close();
            return DELETE_UNFILLABLE_TASK_ERROR;
        }
    }
    public int DeleteTrashTasksBeforeHand() throws SQLException{
   // if controller or Intersection is invisible or deleted data will not be transferred
   // JIs 07/10 2019
   //
        int iRet;
        String SQL = "";
        try {
            SQL = "delete from TRPX_MeasurementTask  ";
            SQL = SQL + " where MeasurementTask_idindex in (   ";
            SQL = SQL + " select MeasurementTask_idindex from TRPX_MeasurementTask Task  ";
            SQL = SQL + "  join TRPX_Super2Invisible invi on invi.Detectorid=task.detectorid   ";
            SQL = SQL + " where  DetectorMeasuresTimestamp = ";
            SQL = SQL + " (select top 1 DetectorMeasuresTimestamp from TRPX_MeasurementTask where  TaskType = 'NOTDEFINED'  order by DetectorMeasuresTimestamp asc))";
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            logger.info("Lines updated  iRet = " + iRet);
            stmt.close();
            if (iRet < 0) {
                iRet = DELETE_TRASH_TASK_ERROR;
                return iRet;
            }
            return DELETE_TRASH_TASK_OK;
        } catch (Exception e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            logger.info(" catch 11");
            logger.info(e.getMessage());
            e.printStackTrace();
            return DELETE_TRASH_TASK_ERROR ;
        }
    }
    public int DeleteTrashTasksAfterHand() throws SQLException{
        // if controller or Intersection is invisible or deleted data will not be transferred
        // JIs 07/10 2019
        //
        int iRet;
        String SQL = "";
        try {
            SQL = "delete from TRPX_MeasurementTask_Work  ";
            SQL = SQL + " where MeasurementTask_idindex in (   ";
            SQL = SQL + " select MeasurementTask_idindex from TRPX_MeasurementTask_Work Task  ";
            SQL = SQL + "  join TRPX_Super2Invisible invi on invi.Detectorid=task.detectorid   ";
            SQL = SQL + " where  DetectorMeasuresTimestamp = ";
            SQL = SQL + " (select top 1 DetectorMeasuresTimestamp from TRPX_MeasurementTask_work where  TaskType = 'NOTDEFINED'  order by DetectorMeasuresTimestamp asc))";
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            logger.info("Lines deleted  iRet = " + iRet);
            stmt.close();
            if (iRet < 0) {
                iRet = DELETE_TRASH_TASK_ERROR;
                gSqlCon.close();
                return iRet;
            }
            return DELETE_TRASH_TASK_OK;
        } catch (Exception e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            logger.info(" catch 11");
            logger.info(e.getMessage());
            e.printStackTrace();
            gSqlCon.close();
            return DELETE_TRASH_TASK_ERROR ;
        }
    }
    public int DeleteNotFilledTasks() throws SQLException{
        int iRet;
        String SQL = "";
        try {
            SQL = "delete from TRPX_MeasurementTask_Work  ";
            SQL = SQL + " where   ";
            SQL = SQL + " OmniaCode=7777 and  ";
            SQL = SQL + " IntersectionID=7777 and  ";
            SQL = SQL + " ControllerId=7777 and  ";
            SQL = SQL + " TaskType='NOTDEFINED' and  ";
            SQL = SQL + " TaskStatus=0;  ";
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            logger.info("Lines updated  iRet = " + iRet);
            stmt.close();
            if (iRet < 0) {
                gSqlCon.close();
                iRet = DELETE_UNFILLABLE_TASK_ERROR;
                return iRet;
            }
            return DELETE_UNFILLABLE_TASK_OK;
        } catch (Exception e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            logger.info(" catch 11");
            logger.info(e.getMessage());
            e.printStackTrace();
            gSqlCon.close();
            return DELETE_UNFILLABLE_TASK_ERROR;
        }
    }
    public TRPXMeasurementTaskData GetFirstUndoneTaskFromList() {
        TRPXMeasurementTaskData ce = new TRPXMeasurementTaskData();
        logger.info("TaskUnits.size()= " + TaskUnits.size());
        for (int i = 0; i < TaskUnits.size(); i++) {
            ce = TaskUnits.get(i);
            logger.info("ce.toString()=" + ce.toString());
            return ce;
        }
        ce.MakeEmptyElement();
        return ce;
    }

    public int DeleteDoneTaskFromDb(TRPXMeasurementTaskData ce) throws SQLException{
        int iRet;
        String SQL = "";
        logger.info("ce.toString()=" + ce.toString());
        try {
            java.sql.PreparedStatement stmt;
            SQL = " delete from  TRPX_MeasurementTask " ;
            SQL = SQL +  "where MeasurementTask_idindex in ";
            SQL = SQL + "(select task.MeasurementTask_idindex from TRPX_MeasurementTask task  ";
            SQL = SQL +" join TRPX_MeasurementTask_Work work on  ";
            SQL = SQL + " work.DetectorID=task.DetectorID and ";
            SQL = SQL + " work.DetectorMeasuresTimestamp = task.DetectorMeasuresTimestamp ";
            SQL = SQL +  " where work.TaskType='NOTDEFINED' and ";
            SQL = SQL +" work.OmniaCode=2 and  ";
            SQL = SQL +" work.IntersectionID= " + ce.getIntersectionId()   +  " and ";
            SQL = SQL +" work.ControllerID=  " + ce.getControllerId()   + " and ";
            SQL = SQL +" work.TaskStatus=1 and ";
            SQL = SQL +" work.DetectorMeasuresTimestamp =  " +  "'" + ce.getDetectorMeasuresTimestamp() + "');";
            logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            logger.info("Lines deleted iRet = " + iRet);
            stmt.close();
            if (iRet < 0) {
                gSqlCon.close();
                iRet = UNSUCCESSFUL_DATABASE_DELETE_OPERATION;
                return iRet;
            }
            return iRet;
        } catch (Exception e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            logger.info(" catch 11");
            logger.info(e.getMessage());
            e.printStackTrace();
            gSqlCon.close();
            return UNSUCCESSFUL_DATABASE_OPERATION;
        }
    }
    public int DeleteIntersectionTasksFromDb() throws SQLException{
        // According to oldest timestamp
        // these tasks has been transferred under work to TRPX_MeasurementTask_Work table
        int iRet;
        String SQL = "";
        try {
            java.sql.PreparedStatement stmt;
            SQL = " delete from TRPX_MeasurementTask ";
            SQL = SQL + "where TaskType='INTERSECTIONDATACHANGE'  and ";
            SQL = SQL + "  DetectorMeasuresTimestamp = (select top 1 DetectorMeasuresTimestamp from TRPX_MeasurementTask where TaskType='INTERSECTIONDATACHANGE' order by   DetectorMeasuresTimestamp asc);";
            logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            logger.info("Lines deleted iRet = " + iRet);
            stmt.close();
            if (iRet < 0) {
                stmt.close();
                gSqlCon.close();
                iRet = UNSUCCESSFUL_DATABASE_DELETE_OPERATION;
                return iRet;
            }
            return iRet;
        } catch (Exception e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            logger.info(" catch 11");
            logger.info(e.getMessage());
            e.printStackTrace();
            gSqlCon.close();
            return UNSUCCESSFUL_DATABASE_OPERATION;
        }
    }
    public int DeleteControllerTasksFromDb() throws SQLException{
        int iRet;
        String SQL = "";
        try {
            java.sql.PreparedStatement stmt;
            SQL = " delete from TRPX_MeasurementTask ";
            SQL = SQL + "where TaskType='CONTROLLERDATACHANGE'  and ";
            SQL = SQL + "  DetectorMeasuresTimestamp = (select top 1 DetectorMeasuresTimestamp from TRPX_MeasurementTask where TaskType='CONTROLLERDATACHANGE' order by   DetectorMeasuresTimestamp asc);";
            logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            logger.info("Lines deleted iRet = " + iRet);
            stmt.close();
            if (iRet < 0) {
                gSqlCon.close();
                iRet = UNSUCCESSFUL_DATABASE_DELETE_OPERATION;
                return iRet;
            }
            return iRet;
        } catch (Exception e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            logger.info(" catch 11");
            logger.info(e.getMessage());
            e.printStackTrace();
            gSqlCon.close();
            return UNSUCCESSFUL_DATABASE_OPERATION;
        }
    }
    public int DeleteDetectorTasksFromDb() throws SQLException{
        int iRet;
        String SQL = "";
        try {
            java.sql.PreparedStatement stmt;
            SQL = " delete from TRPX_MeasurementTask ";
            SQL = SQL + "where TaskType='DETECTORDATACHANGE'  and ";
            SQL = SQL + "  DetectorMeasuresTimestamp = (select top 1 DetectorMeasuresTimestamp from TRPX_MeasurementTask where TaskType='DETECTORDATACHANGE' order by   DetectorMeasuresTimestamp asc);";
            logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            logger.info("Lines deleted iRet = " + iRet);
            stmt.close();
            if (iRet < 0) {
                gSqlCon.close();
                iRet = UNSUCCESSFUL_DATABASE_DELETE_OPERATION;
                return iRet;
            }
            return iRet;
        } catch (Exception e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            logger.info(" catch 11");
            logger.info(e.getMessage());
            e.printStackTrace();
            gSqlCon.close();
            return UNSUCCESSFUL_DATABASE_OPERATION;
        }
    }
    public int DeleteMeasurementTasksFromDb() throws SQLException{
        int iRet;
        String SQL = "";
        try {
            java.sql.PreparedStatement stmt;
            SQL = " delete from TRPX_MeasurementTask ";
            SQL = SQL + "where TaskType='NOTDEFINED'  and ";
            SQL = SQL + "  DetectorMeasuresTimestamp = (select top 1 DetectorMeasuresTimestamp from TRPX_MeasurementTask where TaskType='NOTDEFINED' order by   DetectorMeasuresTimestamp asc);";
            logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            logger.info("Lines deleted iRet = " + iRet);
            stmt.close();
            if (iRet < 0) {
                gSqlCon.close();
                iRet = UNSUCCESSFUL_DATABASE_DELETE_OPERATION;
                return iRet;
            }
            return iRet;
        } catch (Exception e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            logger.info(" catch 11");
            logger.info(e.getMessage());
            e.printStackTrace();
            gSqlCon.close();
            return UNSUCCESSFUL_DATABASE_OPERATION;
        }
    }
    public int DeleteTasksFromDb() throws SQLException{
        int iRet;
        String SQL = "";
        try {
            java.sql.PreparedStatement stmt;
            SQL = " delete from TRPX_MeasurementTask ";
            SQL = SQL + "where ";
            SQL = SQL + "  DetectorMeasuresTimestamp = (select top 1 DetectorMeasuresTimestamp from TRPX_MeasurementTask order by   DetectorMeasuresTimestamp asc);";
            logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            logger.info("Lines deleted iRet = " + iRet);
            stmt.close();
            if (iRet < 0) {
                gSqlCon.close();
                iRet = UNSUCCESSFUL_DATABASE_DELETE_OPERATION;
                return iRet;
            }
            return iRet;
        } catch (Exception e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            logger.info(" catch 11");
            logger.info(e.getMessage());
            e.printStackTrace();
            gSqlCon.close();
            return UNSUCCESSFUL_DATABASE_OPERATION;
        }
    }
    public int DeleteDoneTaskFromWorkDb(TRPXMeasurementTaskData ce) throws SQLException{
        // RETHINK TaskType
        int iRet;
        String SQL = "";
        try {
            java.sql.PreparedStatement stmt;
            SQL = " delete from TRPX_MeasurementTask_Work ";
            SQL = SQL + "where ";
            SQL = SQL + "OmniaCode=" + ce.getOmniaCode() + " and ";
            SQL = SQL + "IntersectionID=" + ce.getIntersectionId() + " and ";
            SQL = SQL + "ControllerID=" + ce.getControllerId() + " and ";
            SQL = SQL + "DetectorMeasuresTimestamp = '" + ce.getDetectorMeasuresTimestamp() + "' and ";
            SQL = SQL + "TaskStatus =  " + MEASUREMENT_TASK_STATUS_OK + ";";
            logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            logger.info("Lines deleted iRet = " + iRet);
            stmt.close();
            if (iRet < 0) {
                gSqlCon.close();
                iRet = UNSUCCESSFUL_DATABASE_DELETE_OPERATION;
                return iRet;
            }
            return INT_RET_OK;
        } catch (Exception e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            logger.info(" catch 11");
            logger.info(e.getMessage());
            e.printStackTrace();
            gSqlCon.close();
            return UNSUCCESSFUL_DATABASE_OPERATION;
        }
    }
    public int UpdateTaskFromDbForClearance(TRPXMeasurementTaskData ce) throws SQLException{
        int iRet;
        String SQL = "";
        logger.info("ce.toString()=" + ce.toString());
        try {
            java.sql.PreparedStatement stmt;
            SQL = " update TRPX_MeasurementTask_Work  set TaskStatus = " + MEASUREMENT_TASK_STATUS_CLEARING + " ";
            SQL = SQL + "where ";
            SQL = SQL + " MeasurementTask_idindex = " + ce.getMeasurementTaskIdindex() + ";";
            logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            logger.info("Lines deleted iRet = " + iRet);
            stmt.close();
            if (iRet < 0) {
                gSqlCon.close();
                iRet = UNSUCCESSFUL_DATABASE_UPDATE_OPERATION;
                return iRet;
            }
            return iRet;
        } catch (Exception e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            logger.info(" catch 11");
            logger.info(e.getMessage());
            e.printStackTrace();
            gSqlCon.close();
            return UNSUCCESSFUL_DATABASE_OPERATION;
        }
    }
    public String getPermanentSqlDataSpare(long plngIntersectionId, long plngControllerId) throws SQLException{
        String SQL = "";
        String strRet = NO_VALUE;
        int iRet = 0;
        java.sql.PreparedStatement stmt;
        try {
            SQL = " exec dbo.TRPX_GetPermanentDataSpareSql  ";
            SQL = SQL + plngIntersectionId + ",";
            SQL = SQL + plngControllerId + " ;";
            logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            ResultSet rs;
            rs = stmt.executeQuery();
            logger.info(" rs.getFetchSize() = " + rs.getFetchSize());
            stmt.close();
            while (rs.next()) {
                strRet = rs.getString(1);
                return strRet;
            }
        } catch (SQLException e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            e.printStackTrace();
            gSqlCon.close();
            return NO_VALUE;
        }
        return NO_VALUE;
   }
   private String getPermanentJsonDataSpare(long plngIntersectionId, long plngControllerId) throws SQLException{
        String SQL = "";
        String strRet = NO_VALUE;
        int iRet = 0;
        java.sql.PreparedStatement stmt;
        try {
            SQL = " select dbo.TRPX_GetPermanentDataSpare( ";
            SQL = SQL + plngIntersectionId + ",";
            SQL = SQL + plngControllerId + ")";
            logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            ResultSet rs;
            rs = stmt.executeQuery();
            logger.info(" rs.getFetchSize() = " + rs.getFetchSize());
            stmt.close();
            while (rs.next()) {
                strRet = rs.getString(1);
                return strRet;
            }
        } catch (SQLException e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            e.printStackTrace();
            gSqlCon.close();
            return NO_VALUE;
        }
        return NO_VALUE;
    }
    public String getPermanentSqlData(long plngIntersectionId, long plngControllerId, String pstrTimestamp) throws SQLException  {
        String SQL = "";
        String strRet = NO_VALUE;
        int iRet = 0;
        java.sql.PreparedStatement stmt;
        OmniaIntersectionListClientDataLevel oi = new OmniaIntersectionListClientDataLevel();
        iRet = oi.MakeConnection(SwarcoEnumerations.ConnectionType.SQLSERVER_LOCAL_JOMNIATEST);
        if (iRet == DATABASE_CONNECTION_OK) {
            strRet = oi.GetPermanentJsonData(plngIntersectionId, plngControllerId, pstrTimestamp);
        }
        return strRet;
    }
    public String getMeasurementSqlData(long plngIntersectionId, long plngControllerId, String pstrTimestamp) throws SQLException{
        String SQL = "";
        String strRet = NO_VALUE;
        int iRet = 0;
        java.sql.PreparedStatement stmt;
        DetectorMeasurementsClientDataLevel oi = new DetectorMeasurementsClientDataLevel();
        iRet = oi.MakeConnection(SwarcoEnumerations.ConnectionType.SQLSERVER_LOCAL_JOMNIATEST);
        if (iRet == DATABASE_CONNECTION_OK) {
            strRet = oi.GetMeasurementsDataString(plngIntersectionId, plngControllerId, pstrTimestamp);
        }
        return strRet;
    }
    public String getMeasurementShortSqlData(long plngIntersectionId, long plngControllerId, String pstrTimestamp) throws SQLException{
        String SQL = "";
        String strRet = NO_VALUE;
        int iRet = 0;
        java.sql.PreparedStatement stmt;
        DetectorMeasurementsShortClientDataLevel oi = new DetectorMeasurementsShortClientDataLevel();
        iRet = oi.MakeConnection(SwarcoEnumerations.ConnectionType.SQLSERVER_LOCAL_JOMNIATEST);
        if (iRet == DATABASE_CONNECTION_OK) {
            strRet = oi.GetMeasurementsDataString(plngIntersectionId, plngControllerId, pstrTimestamp);
        }
        return strRet;
    }
}

