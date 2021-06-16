package fi.swarco.dataHandling;
import java.sql.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.connections.SwarcoConnections;
import fi.swarco.dataHandling.omniaClientDataHandling.*;
import fi.swarco.dataHandling.pojos.TRPXMeasurementTaskData;
import fi.swarco.dataHandling.queriesSql.sqlServer.JiMeasurementTaskSelectSqlServer;
import fi.swarco.omniaDataTransferServices.FileOperations;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import static fi.swarco.CONSTANT.*;
import static fi.swarco.omniaDataTransferServices.omniaClient.OmniaClient.getOmniaCode;
import static fi.swarco.omniaDataTransferServices.omniaClient.OmniaClient.getSqlServerConnectionType;
import static fi.swarco.omniaDataTransferServices.omniviewClient.OmniviewClient.GMaxClientMaxControllersInMessage;
public class MeasurementTaskHandling {
    private static Logger logger = Logger.getLogger(MeasurementTaskHandling.class.getName());
    private List<TRPXMeasurementTaskData> TaskUnits = Collections.synchronizedList(new LinkedList<>());  // ????
    private static Connection gSqlCon;
    private FileOperations fo = new FileOperations();
    private static SwarcoEnumerations.ConnectionType SqlConnectionType = SwarcoEnumerations.ConnectionType.NOT_DEFINED;
    private SwarcoEnumerations.RequestOriginType requestOrigin = SwarcoEnumerations.RequestOriginType.NORMALROAD;

    public void setRequestOrigin(SwarcoEnumerations.RequestOriginType prequestOrigin) {
        requestOrigin = prequestOrigin;
    }

    public static int MakeConnection(SwarcoEnumerations.ConnectionType pSqlCon) {
        SwarcoConnections vg = new SwarcoConnections();
        //   logger.info("pSqlCon = " + pSqlCon);
        int iRet = vg.MakeConnection(pSqlCon);
        if (iRet != INT_RET_OK) {
            return iRet;
        }
        SqlConnectionType = pSqlCon;
        gSqlCon = vg.getSqlCon();
        return INT_RET_OK;
    }

    public int CountOmniviewWorkTypes() throws SQLException {
        int iRet = 0;
        String SQL;
        try {
            SQL = "select COUNT(*)  from TRPX_MeasurementTask_work ";
            SQL = SQL + "where tasktype not in  ";
            SQL = SQL + "(select top 1  tasktype  from TRPX_MeasurementTask_work);";
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            ResultSet rs;
            rs = stmt.executeQuery();
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

    public String GetFirstUndoneOmniviewTaskWorktype() throws SQLException {
        String strRet = TT_NOT_DEFINED;
        try {
            String SQL = "select distinct tasktype from TRPX_MeasurementTask_Work;";
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            ResultSet rs;
            rs = stmt.executeQuery();
            while (rs.next()) {
                strRet = rs.getString(1);
                stmt.close();
                rs.close();
                return strRet;
            }
            stmt.close();
            rs.close();
            return strRet;
        } catch (Exception e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            logger.info(" catch 11");
            logger.info(e.getMessage());
            e.printStackTrace();
            gSqlCon.close();
            return TT_NOT_DEFINED;
        }
    }

    public int MeasurementTaskDataList() throws SQLException {
        TaskUnits.clear();
        String SQL;
        java.sql.PreparedStatement stmt;
        ResultSet rs;
        JiMeasurementTaskSelectSqlServer st = new JiMeasurementTaskSelectSqlServer();
        SQL = st.getStatement();
        //   logger.info("SqlConnectionTypeyyyy= " + SqlConnectionType);
        //   logger.info("SQL = " + SQL);
        TRPXMeasurementTaskData ce;
        try {
            stmt = gSqlCon.prepareStatement(SQL);
            rs = stmt.executeQuery();
            while (rs.next()) {
                ce = new TRPXMeasurementTaskData();
                ce.setMeasurementTaskIdindex(rs.getLong(1));
                ce.setOmniaCode(rs.getLong(2));
                ce.setIntersectionId(rs.getLong(3));
                ce.setControllerId(rs.getLong(4));
                ce.setDetectorId(rs.getLong(5));
                ce.setDetectorMeasuresTimestamp(rs.getString(6));
                ce.setPermanentDataTimestamp(rs.getString(7));
                ce.setTaskType(rs.getString(8));
                ce.setTaskStatus(rs.getLong(9));
                ce.setCreated(rs.getString(10));
                TaskUnits.add(ce);
            }
            stmt.close();
            rs.close();
            if (TaskUnits.isEmpty()) {
                ce = new TRPXMeasurementTaskData();
                ce.MakeEmptyElement();
                TaskUnits.add(ce);
                return NO_TASK_LIST;
            }
            //   logger.info("bef ret iRet OK");
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


    public int AnyWorkWork() throws SQLException {
        int iRet = INT_RET_NOT_OK;
        try {
            String SQL = "select count(*) from TRPX_MeasurementTask_Work;";
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            ResultSet rs;
            rs = stmt.executeQuery();
            while (rs.next()) {
                iRet = rs.getInt(1);
                stmt.close();
                rs.close();
                return iRet;
            }
            stmt.close();
            rs.close();
            logger.info("AnyWork iRet = " + iRet);
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

    public int AnyWork() throws SQLException {
        int iRet = INT_RET_NOT_OK;
        try {
            String SQL = "select count(*) from TRPX_MeasurementTask;";
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

    public int AnyWorkMeasurementsOmniview() throws SQLException {
        // JIs 11.2 2021 klo 11:15
        int iRet = INT_RET_NOT_OK;
        try {
            java.sql.PreparedStatement stmt;
            String SQL = "select count(*) from TRPX_MeasurementTask_WORK where taskType='MEASUREMENTDATAINSERT';";
            stmt = gSqlCon.prepareStatement(SQL);
            ResultSet rs;
            rs = stmt.executeQuery();
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

    public int AnyWorkControllerStatusOmniview() throws SQLException {
        // JIs 9.4 2021 klo 10:25
        int iRet = INT_RET_NOT_OK;
        try {
            java.sql.PreparedStatement stmt;
            String SQL = "select count(*) from TRPX_MeasurementTask_WORK where taskType='CONTROLLERSTATUSDATAINSERT';";
            stmt = gSqlCon.prepareStatement(SQL);
            ResultSet rs;
            rs = stmt.executeQuery();
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
    public int AnyWorkMeasurementsNew1() throws SQLException {
    // Omna client without workqueue
        int iRet = INT_RET_NOT_OK;
        try {
            String SQL =  "select [dbo].[TRPX_GetMeasurementWorkLineCount]();";
            //   String SQL = " select * from [dbo].[TRPX_Next_DetectorMeasures_Jobs_CountV];";
         //  String SQL = " SELECT count(*) FROM TRPX_DetectorMeasures_TimestampV v1 ";
         //   SQL =SQL + "where  not exists  ";
         //   SQL =SQL + " (select * from TRPX_TransferredLines tr1  where  tr1.DetectorMeasuresTimestamp= v1.transferTime); ";
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            ResultSet rs;
            rs = stmt.executeQuery();
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
    public int AnyWorkMeasurementsNew2() throws SQLException {
        // Omna client without workqueue
        int iRet = INT_RET_NOT_OK;
        try {
            String SQL = "select count(*) from TRPX_TransferredLines tr1  where  tr1.transferred_state=3;";
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            ResultSet rs;
            rs = stmt.executeQuery();
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
    public int AnyWorkMeasurements() throws SQLException {
        int iRet = INT_RET_NOT_OK;
        try {
            String SQL = "select count(*) from TRPX_MeasurementTask where taskType='MEASUREMENTDATAINSERT';";
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            ResultSet rs;
            rs = stmt.executeQuery();
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

    public int AnyWorkIntersection() throws SQLException {
        int iRet = INT_RET_NOT_OK;
        try {
            String SQL = "select count(*) from TRPX_MeasurementTask  where taskType='INTERSECTIONDATACHANGE' ;";
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            ResultSet rs;
            rs = stmt.executeQuery();
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

    public int AnyWorkController() throws SQLException {
        int iRet = INT_RET_NOT_OK;
        try {
            String SQL = "select count(*) from TRPX_MeasurementTask  where taskType='CONTROLLERDATACHANGE';";
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            ResultSet rs;
            rs = stmt.executeQuery();
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

    public int AnyWorkDetector() throws SQLException {
        int iRet = INT_RET_NOT_OK;
        try {
            String SQL = "select count(*) from TRPX_MeasurementTask where taskType='DETECTORDATACHANGE';";
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            ResultSet rs;
            rs = stmt.executeQuery();
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

    public int AnyWorkIntersectionOmniview() throws SQLException {
        int iRet = INT_RET_NOT_OK;
        try {
            String SQL = "select count(*) from TRPX_MeasurementTask_Work  where taskType='INTERSECTIONDATACHANGE' ;";
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            ResultSet rs;
            rs = stmt.executeQuery();
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

    public int CreateNeededPermanentDataWorks() throws SQLException {
        int iRet;
        try {
            java.sql.PreparedStatement stmt;
            String SQL = " [dbo].[TRPX_CreateNeededPermanentDataTransferWorks]  ";
            //    logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            stmt.close();
            if (iRet < 0) {
                gSqlCon.close();
                iRet = UNSUCCESSFUL_DATABASE_INSERT_OPERATION;
                return iRet;
            }
            iRet = INT_RET_OK;
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

    public int AnyWorkControllerOmniview() throws SQLException {
        int iRet = INT_RET_NOT_OK;
        try {
            String SQL = "select count(*) from TRPX_MeasurementTask_Work  where taskType='CONTROLLERDATACHANGE';";
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            ResultSet rs;
            rs = stmt.executeQuery();
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

    public int AnyWorkDetectorOmniview() throws SQLException {
        int iRet = INT_RET_NOT_OK;
        try {
            String SQL = "select count(*) from TRPX_MeasurementTask_Work where taskType='DETECTORDATACHANGE';";
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            ResultSet rs;
            rs = stmt.executeQuery();
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

    public int TransferIntersectionTasksToWorkQueue() throws SQLException {
        int iRet;
        try {
            String SQL = "insert into TRPX_MeasurementTask_Work ";
            SQL = SQL + " ([MeasurementTask_idindex] ";
            SQL = SQL + " ,[OmniaCode] ";
            SQL = SQL + " ,[IntersectionID] ";
            SQL = SQL + " ,[ControllerId] ";
            SQL = SQL + " ,[DetectorID] ";
            SQL = SQL + " ,[DetectorMeasuresTimestamp] ";
            SQL = SQL + " ,[PermanentDataTimestamp] ";
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
            SQL = SQL + " ,[PermanentDataTimestamp] ";
            SQL = SQL + " ,[TaskType] ";
            SQL = SQL + " ,[TaskStatus] ";
            SQL = SQL + " ,[Created] ";
            SQL = SQL + " ,GETDATE() ";
            SQL = SQL + " , GETDATE() ";
            SQL = SQL + " from TRPX_MeasurementTask  ";
            SQL = SQL + "where TaskType = 'INTERSECTIONDATACHANGE' and  ";
            SQL = SQL + " PermanentDataTimestamp  = ";
            SQL = SQL + " (select top 1 PermanentDataTimestamp  from TRPX_MeasurementTask  where TaskType = 'INTERSECTIONDATACHANGE'  order by PermanentDataTimestamp  asc) ";
//            logger.info("SQL = " + SQL);
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            stmt.close();
            //          logger.info("Lines inserted iRet = " + iRet);
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

    public int TransferControllerTasksToWorkQueue() throws SQLException {
        int iRet;
        try {
            String SQL = "insert into TRPX_MeasurementTask_Work ";
            SQL = SQL + " ([MeasurementTask_idindex] ";
            SQL = SQL + " ,[OmniaCode] ";
            SQL = SQL + " ,[IntersectionID] ";
            SQL = SQL + " ,[ControllerId] ";
            SQL = SQL + " ,[DetectorID] ";
            SQL = SQL + " ,[DetectorMeasuresTimestamp] ";
            SQL = SQL + " ,[PermanentDataTimestamp] ";
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
            SQL = SQL + " ,[PermanentDataTimestamp] ";
            SQL = SQL + " ,[TaskType] ";
            SQL = SQL + " ,[TaskStatus] ";
            SQL = SQL + " ,[Created] ";
            SQL = SQL + " ,GETDATE() ";
            SQL = SQL + " , GETDATE() ";
            SQL = SQL + " from TRPX_MeasurementTask  ";
            SQL = SQL + "where TaskType = 'CONTROLLERDATACHANGE' and  ";
            SQL = SQL + " PermanentDataTimestamp  = ";
            SQL = SQL + " (select top 1 PermanentDataTimestamp  from TRPX_MeasurementTask where  TaskType = 'CONTROLLERDATACHANGE'  order by PermanentDataTimestamp  asc) ";
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            //  logger.info("Lines inserted iRet = " + iRet);
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

    public int GetCurrentWorkTaskIdindex() throws SQLException {
        int intRet;
        int iRet = 0;
        java.sql.PreparedStatement stmt;
        try {
            String SQL = " select  top 1 MeasurementTask_idindex  from TRPX_MeasurementTask_Work  where ";
            SQL = SQL + "TaskType IN ('DETECTORDATACHANGE','CONTROLLERDATACHANGE','INTERSECTIONDATACHANGE') AND TaskStatus=1 ";
            //       logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            ResultSet rs;
            rs = stmt.executeQuery();
            //    logger.info(" rs.getFetchSize() = " + rs.getFetchSize());
            while (rs.next()) {
                intRet = rs.getInt(1);
                stmt.close();
                rs.close();
                return intRet;
            }
        } catch (SQLException e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            e.printStackTrace();
            gSqlCon.close();
            return INT_RET_NOT_OK;
        }
        return INT_RET_NOT_OK;
    }

    public int TransferDetectorTasksToWorkQueue() throws SQLException {
        int iRet;
        try {
            String SQL = "insert into TRPX_MeasurementTask_Work ";
            SQL = SQL + " ([MeasurementTask_idindex] ";
            SQL = SQL + " ,[OmniaCode] ";
            SQL = SQL + " ,[IntersectionID] ";
            SQL = SQL + " ,[ControllerId] ";
            SQL = SQL + " ,[DetectorID] ";
            SQL = SQL + " ,[DetectorMeasuresTimestamp] ";
            SQL = SQL + " ,[PermanentDataTimestamp] ";
            SQL = SQL + " ,[TaskType] ";
            SQL = SQL + " ,[TaskStatus] ";
            SQL = SQL + " ,[Created] ";
            SQL = SQL + " ,[Updated] ";
            SQL = SQL + " ,[WorkCreated]) ";
            SQL = SQL + "select ";
            SQL = SQL + " Top 1 [MeasurementTask_idindex] ";
            SQL = SQL + " ,[OmniaCode] ";
            SQL = SQL + " ,[IntersectionID] ";
            SQL = SQL + " ,[ControllerId] ";
            SQL = SQL + " ,[DetectorID] ";
            SQL = SQL + " ,[DetectorMeasuresTimestamp] ";
            SQL = SQL + " ,[PermanentDataTimestamp] ";
            SQL = SQL + " ,[TaskType] ";
            SQL = SQL + " ,[TaskStatus] ";
            SQL = SQL + " ,[Created] ";
            SQL = SQL + " ,GETDATE() ";
            SQL = SQL + " , GETDATE() ";
            SQL = SQL + " from TRPX_MeasurementTask  ";
            SQL = SQL + "where TaskType = 'DETECTORDATACHANGE' and  ";
            SQL = SQL + " PermanentDataTimestamp  = ";
            SQL = SQL + " (select top 1 PermanentDataTimestamp  from TRPX_MeasurementTask where  TaskType = 'DETECTORDATACHANGE') ";
            SQL = SQL + " order by PermanentDataTimestamp  asc ";
            //   logger.info("SQL = " + SQL);
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            //  logger.info("Lines inserted iRet = " + iRet);
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

    public int TransferMeasurementTasksCountYoungest() throws SQLException {
        int iRet = INT_RET_NOT_OK;
        try {
            String SQL = "select count(*) from TRPX_MeasurementTask ";
            SQL = SQL + " where DetectorMeasuresTimestamp = ";
            SQL = SQL + " (select max(DetectorMeasuresTimestamp) from TRPX_MeasurementTask) ";
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            ResultSet rs;
            rs = stmt.executeQuery();
            while (rs.next()) {
                iRet = rs.getInt(1);
                stmt.close();
                rs.close();
                return iRet;
            }
            stmt.close();
            rs.close();
            logger.info("TransferMeasurementTasksCountYongest iRet = " + iRet);
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

    public int TransferMeasurementTasksCountOldest() throws SQLException {
        int iRet = INT_RET_NOT_OK;
        try {
            String SQL = "select count(*) from TRPX_MeasurementTask ";
            SQL = SQL + " where DetectorMeasuresTimestamp = ";
            SQL = SQL + " (select min(DetectorMeasuresTimestamp) from TRPX_MeasurementTask) ";
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            ResultSet rs;
            rs = stmt.executeQuery();
            while (rs.next()) {
                iRet = rs.getInt(1);
                stmt.close();
                rs.close();
                return iRet;
            }
            stmt.close();
            rs.close();
            logger.info("TransferMeasurementTasksCountOldest iRet = " + iRet);
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

    public int TransferMeasurementTasksToWorkQueueOldest() throws SQLException {
        int iRet;
        try {
            String SQL = "insert into TRPX_MeasurementTask_Work ";
            SQL = SQL + " ([MeasurementTask_idindex] ";
            SQL = SQL + " ,[OmniaCode] ";
            SQL = SQL + " ,[IntersectionID] ";
            SQL = SQL + " ,[ControllerId] ";
            SQL = SQL + " ,[DetectorID] ";
            SQL = SQL + " ,[DetectorMeasuresTimestamp] ";
            SQL = SQL + " ,[PermanentDataTimestamp] ";
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
            SQL = SQL + " ,[PermanentDataTimestamp] ";
            SQL = SQL + " ,[TaskType] ";
            SQL = SQL + " ,[TaskStatus] ";
            SQL = SQL + " ,[Created] ";
            SQL = SQL + " ,GETDATE() ";
            SQL = SQL + " , GETDATE() ";
            SQL = SQL + " from TRPX_MeasurementTask  ";
            SQL = SQL + "where TaskType = 'MEASUREMENTDATAINSERT' and  ";
            SQL = SQL + " DetectorMeasuresTimestamp = ";
            SQL = SQL + " (select  min(DetectorMeasuresTimestamp) from TRPX_MeasurementTask where  TaskType = 'MEASUREMENTDATAINSERT') ;";
            logger.info("SQL = " + SQL);
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            //   logger.info("Lines inserted iRet = " + iRet);
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


    public int TransferMeasurementTasksToWorkQueue() throws SQLException {
        int iRet;
        try {
            String SQL = "insert into TRPX_MeasurementTask_Work ";
            SQL = SQL + " ([MeasurementTask_idindex] ";
            SQL = SQL + " ,[OmniaCode] ";
            SQL = SQL + " ,[IntersectionID] ";
            SQL = SQL + " ,[ControllerId] ";
            SQL = SQL + " ,[DetectorID] ";
            SQL = SQL + " ,[DetectorMeasuresTimestamp] ";
            SQL = SQL + " ,[PermanentDataTimestamp] ";
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
            SQL = SQL + " ,[PermanentDataTimestamp] ";
            SQL = SQL + " ,[TaskType] ";
            SQL = SQL + " ,[TaskStatus] ";
            SQL = SQL + " ,[Created] ";
            SQL = SQL + " ,GETDATE() ";
            SQL = SQL + " , GETDATE() ";
            SQL = SQL + " from TRPX_MeasurementTask  ";
            SQL = SQL + "where TaskType = 'MEASUREMENTDATAINSERT' and  ";
            SQL = SQL + " DetectorMeasuresTimestamp = ";
            SQL = SQL + " (select  max(DetectorMeasuresTimestamp) from TRPX_MeasurementTask where  TaskType = 'MEASUREMENTDATAINSERT') ;";
            logger.info("SQL = " + SQL);
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            //   logger.info("Lines inserted iRet = " + iRet);
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

    public int TransferTasksToWorkQueue() throws SQLException {
        int iRet;
        try {
            String SQL = "insert into TRPX_MeasurementTask_Work ";
            SQL = SQL + " ([MeasurementTask_idindex] ";
            SQL = SQL + " ,[OmniaCode] ";
            SQL = SQL + " ,[IntersectionID] ";
            SQL = SQL + " ,[ControllerId] ";
            SQL = SQL + " ,[DetectorID] ";
            SQL = SQL + " ,[DetectorMeasuresTimestamp] ";
            SQL = SQL + " ,[PermanentDataTimestamp] ";
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
            SQL = SQL + " ,[PermanentDataTimestamp] ";
            SQL = SQL + " ,[TaskType] ";
            SQL = SQL + " ,[TaskStatus] ";
            SQL = SQL + " ,[Created] ";
            SQL = SQL + " ,GETDATE() ";
            SQL = SQL + " , GETDATE() ";
            SQL = SQL + " from TRPX_MeasurementTask  ";
            SQL = SQL + "where DetectorMeasuresTimestamp = ";
            SQL = SQL + " (select top 1 DetectorMeasuresTimestamp from TRPX_MeasurementTask order by DetectorMeasuresTimestamp asc) ";
//            logger.info("SQL = " + SQL);
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            //  logger.info("Lines inserted iRet = " + iRet);
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

    public int FillUpIntersectionTasks() throws SQLException {
        int iRet;
        try {
            String SQL = " update TRPX_MeasurementTask_Work ";
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
            //   logger.info("Lines updated  iRet = " + iRet);
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

    public int FillUpControllerTasks() throws SQLException {
        int iRet;
        try {
            String SQL = " update TRPX_MeasurementTask_Work ";
            SQL = SQL + " set TRPX_MeasurementTask_Work.OmniaCode  = TRPX_Super2.OmniaCode,  ";
            SQL = SQL + " TRPX_MeasurementTask_Work.IntersectionID = TRPX_Super2.IntersectionID, ";
            SQL = SQL + " TRPX_MeasurementTask_Work.ControllerId = TRPX_Super2.ControllerId, ";
            SQL = SQL + "TRPX_MeasurementTask_Work.TaskStatus=1, ";
            SQL = SQL + "TRPX_MeasurementTask_Work.Updated=getdate() ";
            SQL = SQL + "from TRPX_MeasurementTask_Work ";
            SQL = SQL + "join TRPX_Super2  on TRPX_MeasurementTask_Work.ControllerID=TRPX_Super2.ControllerID ";
            SQL = SQL + "where  TRPX_MeasurementTask_Work.TaskType='CONTROLLERDATACHANGE' and ";
            SQL = SQL + "TRPX_MeasurementTask_Work.TaskStatus=0 and ";
            SQL = SQL + "TRPX_MeasurementTask_Work.DetectorMeasuresTimestamp = ";
            SQL = SQL + "  (select top 1 DetectorMeasuresTimestamp from TRPX_MeasurementTask_Work ";
            SQL = SQL + "where  TaskType='CONTROLLERDATACHANGE' and ";
            SQL = SQL + " TaskStatus=0 ";
            SQL = SQL + "order by DetectorMeasuresTimestamp desc); ";
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            //   logger.info("Lines updated  iRet = " + iRet);
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

    public int FillUpDetectorTasks() throws SQLException {
        int iRet;  //RETHINK 14.10 2019
        try {
            String SQL = " update TRPX_MeasurementTask_Work ";
            SQL = SQL + " set TRPX_MeasurementTask_Work.OmniaCode  = TRPX_Super2.OmniaCode,  ";
            SQL = SQL + " TRPX_MeasurementTask_Work.IntersectionID = TRPX_Super2.IntersectionID, ";
            SQL = SQL + " TRPX_MeasurementTask_Work.ControllerId = TRPX_Super2.ControllerId, ";
            SQL = SQL + "TRPX_MeasurementTask_Work.TaskStatus=1, ";
            SQL = SQL + "TRPX_MeasurementTask_Work.Updated=getdate() ";
            SQL = SQL + "from TRPX_MeasurementTask_Work ";
            SQL = SQL + "join TRPX_Super2  on TRPX_MeasurementTask_Work.DetectorID=TRPX_Super2.DetectorID ";
            SQL = SQL + "where  TRPX_MeasurementTask_Work.TaskType='DETECTORDATACHANGE' and ";
            SQL = SQL + "TRPX_MeasurementTask_Work.TaskStatus=0 and ";
            SQL = SQL + "TRPX_MeasurementTask_Work.DetectorMeasuresTimestamp = ";
            SQL = SQL + "  (select top 1 DetectorMeasuresTimestamp from TRPX_MeasurementTask_Work ";
            SQL = SQL + "where  TaskType='DETECTORDATACHANGE' and ";
            SQL = SQL + " TaskStatus=0 ";
            SQL = SQL + "order by DetectorMeasuresTimestamp desc); ";
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            //  logger.info("Lines updated  iRet = " + iRet);
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

    public int FillUpTasks() throws SQLException {
        int iRet;
        try {
            String SQL = " update TRPX_MeasurementTask_Work ";
            SQL = SQL + " set TRPX_MeasurementTask_Work.OmniaCode  = TRPX_Super2.OmniaCode,  ";
            SQL = SQL + " TRPX_MeasurementTask_Work.IntersectionID = TRPX_Super2.IntersectionID, ";
            SQL = SQL + " TRPX_MeasurementTask_Work.ControllerId = TRPX_Super2.ControllerId, ";
            SQL = SQL + "TRPX_MeasurementTask_Work.TaskStatus=1, ";
            SQL = SQL + "TRPX_MeasurementTask_Work.Updated=getdate() ";
            SQL = SQL + "from TRPX_MeasurementTask_Work ";
            SQL = SQL + "join TRPX_Super2  on TRPX_MeasurementTask_Work.DetectorId=TRPX_Super2.DetectorID ";
            SQL = SQL + "where  TRPX_MeasurementTask_Work.TaskType='MEASUREMENTDATAINSERT' and ";
            SQL = SQL + "TRPX_MeasurementTask_Work.TaskStatus=0 and ";
            SQL = SQL + "TRPX_MeasurementTask_Work.DetectorMeasuresTimestamp = ";
            SQL = SQL + "  (select top 1 DetectorMeasuresTimestamp from TRPX_MeasurementTask_Work ";
            SQL = SQL + "where  TaskType='MEASUREMENTDATAINSERT' and ";
            SQL = SQL + " TaskStatus=0 ";
            SQL = SQL + "order by DetectorMeasuresTimestamp desc); ";
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            //   logger.info("SQL = " + SQL);
            //   logger.info("Lines updated  iRet = " + iRet);
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

    public int DeleteNotFilledIntersectionTasks() throws SQLException {
        int iRet;
        try {
            String SQL = "delete from TRPX_MeasurementTask_Work  ";
            SQL = SQL + " where   ";
            SQL = SQL + " OmniaCode=7777 and  ";
            SQL = SQL + " IntersectionID=7777 and  ";
            SQL = SQL + " ControllerId=7777 and  ";
            SQL = SQL + " TaskType='INTERSECTIONDATACHANGE' and  ";
            SQL = SQL + " TaskStatus=0;  ";
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            //   logger.info("Lines updated  iRet = " + iRet);
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

    public int DeleteNotFilledControllerTasks() throws SQLException {
        int iRet;
        try {
            String SQL = "delete from TRPX_MeasurementTask_Work  ";
            SQL = SQL + " where   ";
            SQL = SQL + " OmniaCode=7777 and  ";
            SQL = SQL + " IntersectionID=7777 and  ";
            SQL = SQL + " ControllerId=7777 and  ";
            SQL = SQL + " TaskType='CONTROLLERDATACHANGE' and  ";
            SQL = SQL + " TaskStatus=0;  ";
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            //     logger.info("Lines updated  iRet = " + iRet);
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

    public int DeleteNotFilledDetectorTasks() throws SQLException {
        int iRet;
        try {
            String SQL = "delete from TRPX_MeasurementTask_Work  ";
            SQL = SQL + " where   ";
            SQL = SQL + " OmniaCode=7777 and  ";
            SQL = SQL + " IntersectionID=7777 and  ";
            SQL = SQL + " ControllerId=7777 and  ";
            SQL = SQL + " TaskType='DETECTORDATACHANGE' and  ";
            SQL = SQL + " TaskStatus=0;  ";
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            //       logger.info("Lines updated  iRet = " + iRet);
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

    private int CountTrashTasksBeforeDelete() throws SQLException {
// Take first count  RETHINK Jis 18.11 2019     copied also to MeasurementTaskWorkHandling
        int iRet;
        try {
            String SQL = " select count(*) from TRPX_MeasurementTask task";
            SQL = SQL + "  join TRPX_Super2Invisible invi on invi.Detectorid=task.detectorid";
            String strRet;
            java.sql.PreparedStatement stmt;
//                logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            ResultSet rs;
            rs = stmt.executeQuery();
            while (rs.next()) {
                iRet = rs.getInt(1);
                stmt.close();
                rs.close();
                return iRet;
            }
        } catch (SQLException e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            e.printStackTrace();
            gSqlCon.close();
            return INT_RET_NOT_OK;
        }
        return INT_RET_NOT_OK;
    }

    public int DeleteTrashTasksAfterHandFromTaskList() throws SQLException {
// clean also task list if other error message is given RETHINK
// Jis 18.11 2019 copied also to MeasurementTaskWorkHandling
        int iRet = CountTrashTasksBeforeDelete();
        if (iRet == 0) {   // nothing to delete
            return DELETE_TRASH_TASKTASK_OK;
        }
        if (iRet < 0) {   // nothing to delete
            logger.info(" Count error iRet =" + iRet);
            return DELETE_TRASH_TASKTASK_OK;
        }
        try {
            String SQL = "delete from TRPX_MeasurementTask  ";
            SQL = SQL + " where MeasurementTask_idindex in (   ";
            SQL = SQL + " select MeasurementTask_idindex from TRPX_MeasurementTask task";
            SQL = SQL + "  join TRPX_Super2Invisible invi on invi.Detectorid=task.detectorid)";
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            //       logger.info("Lines deleted ************************** iRet = " + iRet);
            stmt.close();
            if (iRet < 0) {
                iRet = DELETE_TRASH_TASKTASK_ERROR;
                gSqlCon.close();
                return iRet;
            }
            return DELETE_TRASH_TASKTASK_OK;
        } catch (Exception e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            logger.info(" catch 11");
            logger.info(e.getMessage());
            e.printStackTrace();
            gSqlCon.close();
            return DELETE_TRASH_TASK_ERROR;
        }
    }

    public int DeleteTrashTasksBeforeHand() {
        int iRet;
        try {
            String SQL = "delete from TRPX_MeasurementTask  ";
            SQL = SQL + " where MeasurementTask_idindex in (   ";
            SQL = SQL + " select MeasurementTask_idindex from TRPX_MeasurementTask Task  ";
            SQL = SQL + "  join TRPX_Super2Invisible invi on invi.Detectorid=task.detectorid);   ";
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            logger.info("Lines deleted  iRet = " + iRet);
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
            return DELETE_TRASH_TASK_ERROR;
        }
    }

    public int DeleteTrashTasksAfterHand() throws SQLException {
        int iRet;
        try {
            String SQL = "delete from TRPX_MeasurementTask_Work  ";
            SQL = SQL + " where MeasurementTask_idindex in (   ";
            SQL = SQL + " select MeasurementTask_idindex from TRPX_MeasurementTask_Work Task  ";
            SQL = SQL + "  join TRPX_Super2Invisible invi on invi.Detectorid=task.detectorid   ";
            SQL = SQL + " where  DetectorMeasuresTimestamp = ";
            SQL = SQL + " (select top 1 DetectorMeasuresTimestamp from TRPX_MeasurementTask_work where  TaskType = 'MEASUREMENTDATAINSERT'  order by DetectorMeasuresTimestamp asc))";
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            //     logger.info("Lines deleted  iRet = " + iRet);
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
            return DELETE_TRASH_TASK_ERROR;
        }
    }

    public int DeleteNotFilledTasks() throws SQLException {
        int iRet;
        try {
            String SQL = "delete from TRPX_MeasurementTask_Work  ";
            SQL = SQL + " where   ";
            SQL = SQL + " IntersectionID=7777 and  ";
            SQL = SQL + " ControllerId=7777 and  ";
            SQL = SQL + " TaskType='MEASUREMENTDATAINSERT' and  ";
            SQL = SQL + " TaskStatus=0;  ";
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            //      logger.info("Lines updated  iRet = " + iRet);
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
        for (int i = 0; i < TaskUnits.size(); i++) {
            ce = TaskUnits.get(i);
        //    logger.info("ce.toString()=" + ce.toString());
            return ce;
        }
        ce.MakeEmptyElement();
        return ce;
    }

    public int DeleteDoneTaskFromDb(TRPXMeasurementTaskData ce) throws SQLException {
        int iRet;
               logger.info("ce.toString()=" + ce.toString());
        try {
            java.sql.PreparedStatement stmt;
            String SQL = " delete from  TRPX_MeasurementTask ";
            SQL = SQL + "where MeasurementTask_idindex in ";
            SQL = SQL + "(select task.MeasurementTask_idindex from TRPX_MeasurementTask task  ";
            SQL = SQL + " join TRPX_MeasurementTask_Work work on  ";
            SQL = SQL + " work.DetectorID=task.DetectorID and ";
            SQL = SQL + " work.DetectorMeasuresTimestamp = task.DetectorMeasuresTimestamp ";
            SQL = SQL + " where work.TaskType='MEASUREMENTDATAINSERT' and ";
            SQL = SQL + " work.OmniaCode= " + ce.getOmniaCode() + "and  ";
            SQL = SQL + " work.IntersectionID= " + ce.getIntersectionId() + " and ";
            SQL = SQL + " work.ControllerID=  " + ce.getControllerId() + " and ";
            SQL = SQL + " work.TaskStatus=1 and ";
            SQL = SQL + " work.DetectorMeasuresTimestamp =  " + "'" + ce.getDetectorMeasuresTimestamp() + "');";
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

    public int DeleteDoneTaskGroupFromDb() throws SQLException {
        int iRet;
        //       logger.info("ce.toString()=" + ce.toString());
        try {
            java.sql.PreparedStatement stmt;
            String SQL = " delete from  TRPX_MeasurementTask ";
            SQL = SQL + "where MeasurementTask_idindex in ";
            SQL = SQL + "(select work.MeasurementTask_idindex from TRPX_MeasurementTask_Work work ";
            SQL = SQL + " join TRPX_ControllerCount con  on con.controllerid= work.controllerid  and con.handled=2);";
            //    logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            //  logger.info("Lines deleted iRet = " + iRet);
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

    public int DeleteDoneTaskUsingIdentityFromDb(long pIdentity) throws SQLException {
        int iRet;
        try {
            java.sql.PreparedStatement stmt;
            String SQL = " delete from  TRPX_MeasurementTask ";
            SQL = SQL + "where MeasurementTask_idindex   = " + pIdentity;
            //          logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            //logger.info("Lines deleted iRet = " + iRet);
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

    public int DeleteDoneTaskWorkUsingIdentityFromDb(long pIdentity) throws SQLException {
        // RETHINK TaskType
        int iRet;
        try {
            java.sql.PreparedStatement stmt;
            String SQL = " delete from TRPX_MeasurementTask_Work ";
            SQL = SQL + "where MeasurementTask_idindex   = " + pIdentity;
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            //  logger.info("Lines deleted iRet = " + iRet);
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

    public int DeleteIntersectionTasksFromDb() throws SQLException {
        int iRet;
        try {
            java.sql.PreparedStatement stmt;
            String SQL = " delete from TRPX_MeasurementTask ";
            SQL = SQL + "where TaskType='INTERSECTIONDATACHANGE'  and ";
            SQL = SQL + "  PermanentDataTimestamp  = (select top 1 PermanentDataTimestamp  from TRPX_MeasurementTask where TaskType='INTERSECTIONDATACHANGE' order by   PermanentDataTimestamp  asc);";
            //logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            // logger.info("Lines deleted iRet = " + iRet);
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

    public int DeleteControllerTasksFromDb() throws SQLException {
        int iRet;
        try {
            java.sql.PreparedStatement stmt;
            String SQL = " delete from TRPX_MeasurementTask ";
            SQL = SQL + "where TaskType='CONTROLLERDATACHANGE'  and ";
            SQL = SQL + "  PermanentDataTimestamp  = (select top 1 PermanentDataTimestamp  from TRPX_MeasurementTask where TaskType='CONTROLLERDATACHANGE' order by PermanentDataTimestamp  asc);";
            //          logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            //    logger.info("Lines deleted iRet = " + iRet);
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

    public int DeleteDetectorTasksFromDb() throws SQLException {
        int iRet;
        try {
            java.sql.PreparedStatement stmt;
            String SQL = " delete from TRPX_MeasurementTask ";
            SQL = SQL + "where TaskType='DETECTORDATACHANGE'  and ";
            SQL = SQL + "  PermanentDataTimestamp  = (select top 1 PermanentDataTimestamp  from TRPX_MeasurementTask where TaskType='DETECTORDATACHANGE' order by  PermanentDataTimestamp  asc);";
            //        logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            //     logger.info("Lines deleted iRet = " + iRet);
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

    public int DeleteMeasurementTasksFromDb() throws SQLException {
        int iRet;
        try {
            java.sql.PreparedStatement stmt;
            String SQL = " delete from TRPX_MeasurementTask ";
            SQL = SQL + "where TaskType='MEASUREMENTDATAINSERT'  and ";
            SQL = SQL + "  DetectorMeasuresTimestamp = (select top 1 DetectorMeasuresTimestamp from TRPX_MeasurementTask where TaskType='MEASUREMENTDATAINSERT' order by   DetectorMeasuresTimestamp asc);";
//            logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            //      logger.info("Lines deleted iRet = " + iRet);
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

    public int DeleteTasksFromDb() throws SQLException {
        int iRet;
        try {
            java.sql.PreparedStatement stmt;
            String SQL = " delete from TRPX_MeasurementTask ";
            SQL = SQL + "where ";
            SQL = SQL + "  DetectorMeasuresTimestamp = (select top 1 DetectorMeasuresTimestamp from TRPX_MeasurementTask order by   DetectorMeasuresTimestamp asc);";
            //          logger.info("SQL = " + SQL);
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

    public int DeleteDoneTaskFromWorkDb(TRPXMeasurementTaskData ce) throws SQLException {
        int iRet;
        try {
            java.sql.PreparedStatement stmt;
            String SQL = " delete from TRPX_MeasurementTask_Work ";
            SQL = SQL + "where ";
            SQL = SQL + "OmniaCode=" + ce.getOmniaCode() + " and ";
            SQL = SQL + "IntersectionID=" + ce.getIntersectionId() + " and ";
            SQL = SQL + "ControllerID=" + ce.getControllerId() + " and ";
            SQL = SQL + "DetectorMeasuresTimestamp = '" + ce.getDetectorMeasuresTimestamp() + "' and ";
            SQL = SQL + "TaskStatus =  " + MEASUREMENT_TASK_STATUS_OK + ";";
            //         logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            //        logger.info("Lines deleted iRet = " + iRet);
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

    public int MarkOmniviewControllerStatusBackToBeingTransfer() throws SQLException {
        int iRet;
        try {
            String SQL = " update controllerstatus set transfer_state = 0, transfer_time = getdate() ";
            SQL = SQL + " where  transfer_state = 1; ";
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            stmt.close();
            logger.info(" tranfer_state to 0 updated lines number  iRet  = " + iRet);
            if (iRet < 0) {
                gSqlCon.close();
                logger.info(" iRet  = " + iRet);
                return TASK_TRANSFERSTATE_UPDATE_ERROR;
            }
            return TASK_TRANSFERSTATE_UPDATE_OK;
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

    public int MarkOmniviewMeasurementsBackToBeingTransfer() throws SQLException {
        int iRet;
        try {
            String SQL = " update measurements set transfer_state = 0, transfer_time = getdate() ";
            SQL = SQL + " where  transfer_state = 1; ";
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            stmt.close();
            logger.info(" tranfer_state to 0 updated lines number  iRet  = " + iRet);
            if (iRet < 0) {
                gSqlCon.close();
                logger.info(" iRet  = " + iRet);
                return TASK_TRANSFERSTATE_UPDATE_ERROR;
            }
            return TASK_TRANSFERSTATE_UPDATE_OK;
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

    public int MarkOmniviewMeasurementsHandled() throws SQLException {
        int iRet;
        try {
            String SQL = " update measurements set transfer_state = 2, transfer_time = getdate() ";
            SQL = SQL + " where measurement_id in (select measurement_id from measurements ms  ";
            SQL = SQL + " join TRPX_MeasurementTask_Work wo on ms.Intersection_id = wo.IntersectionID and  ";
            SQL = SQL + " ms.Intersection_id = wo.ControllerId and  ";
            SQL = SQL + " ms.detector_id = wo.DetectorID and  ";
            SQL = SQL + " ms.measurement_time = wo.DetectorMeasuresTimestamp  ";
            SQL = SQL + " where ms.transfer_state = 1)  ";
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
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

    public int MarkOmniviewControllerStatusesHandled() throws SQLException {
        int iRet;
        try {
            String SQL = " update controllerstatus set transfer_state = 2, transfer_time = getdate() ";
            SQL = SQL + " where status_id in (select status_id from controllerstatus ms  ";
            SQL = SQL + " join TRPX_MeasurementTask_Work wo on ms.Intersection_id = wo.IntersectionID and  ";
            SQL = SQL + " ms.Intersection_id = wo.ControllerId and  ";
            SQL = SQL + " ms.measurement_time = wo.DetectorMeasuresTimestamp  ";
            SQL = SQL + " where ms.transfer_state = 1)  ";
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
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

    public int DeleteDoneTaskFromOmniView() throws SQLException {
        int iRet;
        try {
            java.sql.PreparedStatement stmt;
            String SQL = " delete from TRPX_MeasurementTask_Work ";
            SQL = SQL + " where MeasurementTask_idindex in (";
            SQL = SQL + " select MeasurementTask_idindex from TRPX_MeasurementTask_Work ";
            SQL = SQL + " where taskstatus=1)" + ";";
            //           logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            //           logger.info("Lines deleted iRet = " + iRet);
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

    public int DeleteDoneTaskFromGroupWorkDb() throws SQLException {
        int iRet;
        try {
            java.sql.PreparedStatement stmt;
            String SQL = " delete from TRPX_MeasurementTask_Work ";
            SQL = SQL + " where work_idindex in (";
            SQL = SQL + " select work.work_idindex from TRPX_MeasurementTask_Work work";
            SQL = SQL + " join TRPX_ControllerCount con  on con.controllerid= work.controllerid  and con.handled=2)" + ";";
            //         logger.info("SQL = " + SQL);
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

    public int DeleteDoneMeasurementTaskWorkFromDb() throws SQLException {
        // only in Omnia not in Omnivue
        int iRet;
        try {
            java.sql.PreparedStatement stmt;
            String SQL = " delete from TRPX_MeasurementTask_Work ";
            logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            //        logger.info("Lines deleted iRet = " + iRet);
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


    public int DeleteDonePermanentdataTaskFromDb() throws SQLException {
        int iRet;
        try {
            java.sql.PreparedStatement stmt;
            String SQL = " exec dbo.TRPX_MarkPermanentdatatasksDone;";
            logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            logger.info(" iRet = " + iRet);
            if (iRet == 0) {
                iRet = INT_RET_OK;
            }
            stmt.close();
            if (iRet < 0) {
                gSqlCon.close();
                iRet = UNSUCCESSFUL_DATABASE_OPERATION;
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

    public int MarkControllerGroupHandled() throws SQLException {
        int iRet;
        try {
            java.sql.PreparedStatement stmt;
            String SQL = " exec TRPX_MarkControllerGroupHandled;";
            //          logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            //         logger.info(" iRet = " + iRet);
            if (iRet == 0) {
                iRet = INT_RET_OK;
            }
            stmt.close();
            if (iRet < 0) {
                gSqlCon.close();
                iRet = UNSUCCESSFUL_DATABASE_OPERATION;
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
    public int UpdateTaskFromDbForClearance(TRPXMeasurementTaskData ce) throws SQLException {
        int iRet;
        logger.info("ce.toString()=" + ce.toString());
        try {
            java.sql.PreparedStatement stmt;
            String SQL = " update TRPX_MeasurementTask_Work  set TaskStatus = " + MEASUREMENT_TASK_STATUS_CLEARING + " ";
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

    public String GetPermanentSqlDataSpare(long plngIntersectionId, long plngControllerId) throws SQLException {
        String strRet;
        int iRet = 0;
        java.sql.PreparedStatement stmt;
        try {
            String SQL = " exec dbo.TRPX_GetPermanentDataSpareSql  ";
            SQL = SQL + plngIntersectionId + ",";
            SQL = SQL + plngControllerId + " ;";
            //    logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            ResultSet rs;
            rs = stmt.executeQuery();
            //    logger.info(" rs.getFetchSize() = " + rs.getFetchSize());
            while (rs.next()) {
                strRet = rs.getString(1);
                stmt.close();
                rs.close();
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

    public String GetCurrentWorkTimestamp() throws SQLException {
        String strRet;
        int iRet = 0;
        java.sql.PreparedStatement stmt;
        try {
            String SQL = "  select [dbo].[TRPX_GetTopDetectorMeasuresTimestampFromMeasurementTaskWork](); ";
            //     logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            ResultSet rs;
            rs = stmt.executeQuery();
            while (rs.next()) {
                strRet = rs.getString(1);
                strRet = strRet.substring(0, 19);
                stmt.close();
                rs.close();
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

    private String GetPermanentJsonDataSpare(long plngIntersectionId, long plngControllerId) throws SQLException {
        String strRet;
        int iRet = 0;
        java.sql.PreparedStatement stmt;
        try {
            String SQL = " select dbo.TRPX_GetPermanentDataSpare( ";
            SQL = SQL + plngIntersectionId + ",";
            SQL = SQL + plngControllerId + ")";
            //     logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            ResultSet rs;
            rs = stmt.executeQuery();
            while (rs.next()) {
                strRet = rs.getString(1);
                stmt.close();
                rs.close();
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

    public String GetPermanentSqlData(long plngIntersectionId, long plngControllerId, String pstrTimestamp) throws SQLException {
        String strRet = NO_VALUE;
        int iRet;
        java.sql.PreparedStatement stmt;
        OmniaIntersectionListClientDataLevel oi = new OmniaIntersectionListClientDataLevel();
        SwarcoEnumerations.ConnectionType oConnType;
        oConnType = getSqlServerConnectionType();
        iRet = oi.MakeConnection(oConnType);
        if (iRet == DATABASE_CONNECTION_OK) {
            strRet = oi.GetPermanentJsonData(plngIntersectionId, plngControllerId, pstrTimestamp);
        }
        return strRet;
    }

    public String GetMeasurementSqlData(long plngIntersectionId, long plngControllerId, String pstrTimestamp) throws SQLException {
        String strRet = NO_VALUE;
        int iRet;
        java.sql.PreparedStatement stmt;
        DetectorMeasurementsClientDataLevel oi = new DetectorMeasurementsClientDataLevel();
        SwarcoEnumerations.ConnectionType oConnType;
        oConnType = getSqlServerConnectionType();
        iRet = oi.MakeConnection(oConnType);
        if (iRet == DATABASE_CONNECTION_OK) {
            strRet = oi.GetMeasurementsDataString(plngIntersectionId, plngControllerId, pstrTimestamp);
        }
        return strRet;
    }

    public int CreateInfluxWriteFile(List<String> pWriteList) {
        fo.initFileOperations();
        String strInfluxFileName = fo.getFullInfluxFileName();
        int iRet = fo.closeAndDeleteNormalFile(strInfluxFileName);
        logger.info("pWriteList.size()= " + pWriteList.size());
        for (int i = 0; i < pWriteList.size(); i++) {
            iRet = fo.addOmniaInfluxLine(pWriteList.get(i), strInfluxFileName);
            if (iRet != INT_RET_OK) {
                return iRet;
            }
        }
        return INT_RET_OK;
    }

    public int BuildMeasurementShortSqlDataFile(long plngIntersectionId, long plngControllerId, String pstrTimestamp) throws SQLException {
        int iRet;
        fo.initFileOperations();
        String strInfluxFileName = fo.getFullInfluxFileName();
        iRet = fo.closeAndDeleteNormalFile(strInfluxFileName);
        iRet = fo.addOmniaInfluxLine("cpu_load_short,host=server111 value=0.12345 1422568543702900257", "jatri1");
        iRet = fo.addOmniaInfluxLine(NEW_LINE_LINUX, "jatri1");
        iRet = fo.addOmniaInfluxLine("cpu_load_short,host=server02 value=0.678999 1422568543702900266", "jatri1");
        return iRet;
    }

    // RETHINK RETHINK
    public String GetControllerStatusSqlData(long plngIntersectionId, long plngControllerId, String pstrTimestamp) throws SQLException {
        String strRet = NO_VALUE;
        int iRet;
        java.sql.PreparedStatement stmt;
        DetectorMeasurementsShortClientDataLevel oi = new DetectorMeasurementsShortClientDataLevel();
        SwarcoEnumerations.ConnectionType oConnType;
        oConnType = getSqlServerConnectionType();
        iRet = oi.MakeConnection(oConnType);
        if (iRet == DATABASE_CONNECTION_OK) {
            strRet = oi.GetMeasurementsDataString(plngIntersectionId, plngControllerId, pstrTimestamp);
        }
        return strRet;
    }

    //  RETHINK RETHINK
    public String GetMeasurementShortSqlData(long plngIntersectionId, long plngControllerId, String pstrTimestamp) throws SQLException {
        String strRet = NO_VALUE;
        int iRet;
        java.sql.PreparedStatement stmt;
        DetectorMeasurementsShortClientDataLevel oi = new DetectorMeasurementsShortClientDataLevel();
        SwarcoEnumerations.ConnectionType oConnType;
        oConnType = getSqlServerConnectionType();
        iRet = oi.MakeConnection(oConnType);
        if (iRet == DATABASE_CONNECTION_OK) {
            strRet = oi.GetMeasurementsDataString(plngIntersectionId, plngControllerId, pstrTimestamp);
        }
        return strRet;
    }

    public String GetMeasurementShortSqlDataGroup(String pstrTimestamp) throws SQLException {
        String strRet = NO_VALUE;
        int iRet;
        java.sql.PreparedStatement stmt;
        DetectorMeasurementsShortClientDataLevel oi = new DetectorMeasurementsShortClientDataLevel();
        SwarcoEnumerations.ConnectionType oConnType;
        oConnType = getSqlServerConnectionType();
        iRet = oi.MakeConnection(oConnType);
        if (iRet == DATABASE_CONNECTION_OK) {
            strRet = oi.GetMeasurementsDataGroupString(pstrTimestamp);
        }
        return strRet;
    }

    public String GetControllerStatusSqlDataGroup(String pstrTimestamp) throws SQLException {
        String strRet = NO_VALUE;
        int iRet;
        java.sql.PreparedStatement stmt;
        ControllerStatusClientDataLevel oi = new ControllerStatusClientDataLevel();
        //DetectorMeasurementsShortClientDataLevel oi = new DetectorMeasurementsShortClientDataLevel();
        SwarcoEnumerations.ConnectionType oConnType;
        oConnType = getSqlServerConnectionType();
        iRet = oi.MakeConnection(oConnType);
        if (iRet == DATABASE_CONNECTION_OK) {
            strRet = oi.GetControllerStatusDataGroupString(pstrTimestamp);
            // strRet = oi.GetOmniaControllerStatusDataGroupString(pstrTimestamp);
            //  strRet = oi.GetMeasurementsDataGroupString(pstrTimestamp);
        }
        return strRet;
    }

    public String GetDetectorOmniviewSqlData(long plngcontrollerID, long plngDetectorId, String pstrTimestamp) throws SQLException {
        String strRet = NO_VALUE;
        int iRet;
        java.sql.PreparedStatement stmt;
        OmniaDetectorClientDatalevel oi = new OmniaDetectorClientDatalevel();
        SwarcoEnumerations.ConnectionType oConnType;
        oConnType = getSqlServerConnectionType();
        iRet = oi.MakeConnection(oConnType);
        if (iRet == DATABASE_CONNECTION_OK) {
            strRet = oi.GetDetectorJsonDataOmniview(plngcontrollerID, plngDetectorId, pstrTimestamp);
        }
        return strRet;
    }

    public String GetDetectorSqlData(long plngDetectorId, String pstrTimestamp) throws SQLException {
        String strRet = NO_VALUE;
        int iRet;
        java.sql.PreparedStatement stmt;
        OmniaDetectorClientDatalevel oi = new OmniaDetectorClientDatalevel();
        SwarcoEnumerations.ConnectionType oConnType;
        oConnType = getSqlServerConnectionType();
        iRet = oi.MakeConnection(oConnType);
        if (iRet == DATABASE_CONNECTION_OK) {
            strRet = oi.GetDetectorJsonData(plngDetectorId, pstrTimestamp);
        }
        return strRet;
    }

    public String GetIntersectionControllerSqlData(long plngIntersectionId, long plngControllerId, String pstrTimestamp, String pWorkType) throws SQLException {
        String strRet = NO_VALUE;
        int iRet;
        java.sql.PreparedStatement stmt;
        OmniaIntesectionControllerClientDatalevel oi = new OmniaIntesectionControllerClientDatalevel();
        SwarcoEnumerations.ConnectionType oConnType;
        oConnType = getSqlServerConnectionType();
        iRet = oi.MakeConnection(oConnType);
        if (iRet == DATABASE_CONNECTION_OK) {
            if (pWorkType.equals(TT_CONTROLLER_DATA_CHANGE)) {
                strRet = oi.GetControllerJsonData(plngIntersectionId, plngControllerId, pstrTimestamp);
            } else if (pWorkType.equals(TT_INTERSECTION_DATA_CHANGE)) {
                strRet = oi.GetIntersectionJsonData(plngIntersectionId, plngControllerId, pstrTimestamp);
            }
        }
        return strRet;
    }

    public String GetIntersectionControllerSqlDataForOmniview(long plngIntersectionId, long plngControllerId, String pstrTimestamp, String pWorkType) throws SQLException {
        String strRet = NO_VALUE;
        int iRet;
        java.sql.PreparedStatement stmt;
        OmniaIntesectionControllerClientDatalevel oi = new OmniaIntesectionControllerClientDatalevel();
        SwarcoEnumerations.ConnectionType oConnType;
        oConnType = getSqlServerConnectionType();
        iRet = oi.MakeConnection(oConnType);
        if (iRet == DATABASE_CONNECTION_OK) {
            if (pWorkType.equals(TT_CONTROLLER_DATA_CHANGE)) {
// RETHINK delete newer called JIs 10.2 2021
                strRet = oi.GetControllerJsonData(plngIntersectionId, plngControllerId, pstrTimestamp);
            } else if (pWorkType.equals(TT_INTERSECTION_DATA_CHANGE)) {
                strRet = oi.GetIntersectionJsonDataForOmniview(plngIntersectionId, plngControllerId, pstrTimestamp);
            }
        }
        return strRet;
    }

    public int CreateOmniviewTypeMeasurementsWorks() throws SQLException {
        int iRet;
        try {
            java.sql.PreparedStatement stmt;
// get parameter here from global variable
            //    String SQL = " exec [dbo].[TRPX_CreateOldestMeasurementTimestampDataTransferWorks];";
            String SQL = " exec [dbo].[TRPX_CreateOldestMeasurementTimestampDataTransferWorks2] " + GMaxClientMaxControllersInMessage + " ;";
            //      logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            stmt.close();
            if (iRet < 0) {
                gSqlCon.close();
                iRet = UNSUCCESSFUL_DATABASE_INSERT_OPERATION;
                return iRet;
            }
            iRet = INT_RET_OK;
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

    public int CreateOmniviewTypeControllerStatusWorks() throws SQLException {
        int iRet;
        try {
            java.sql.PreparedStatement stmt;
// get parameter here from global variable
            //    String SQL = " exec [dbo].[TRPX_CreateOldestMeasurementTimestampDataTransferWorks];";
            String SQL = " exec [dbo].[TRPX_CreateOldestControllerStatusTimestampDataTransferWorks] " + GMaxClientMaxControllersInMessage + " ;";
            //        logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            stmt.close();
            if (iRet < 0) {
                gSqlCon.close();
                iRet = UNSUCCESSFUL_DATABASE_INSERT_OPERATION;
                return iRet;
            }
            iRet = INT_RET_OK;
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

    public int StartOmniviewControllerStatusWorks() throws SQLException {
        // not needed because of groups   RETHINK JIs 9.4 2021 klo 10:20
        int iRet;
        try {
            java.sql.PreparedStatement stmt;
            // String SQL = " exec [dbo].[TRPX_StartOmniviewMeasurementWorks]; ";
            String SQL = " exec [dbo].[TRPX_StartOmniviewControllerStatusWorks]; ";
            logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            stmt.close();
            if (iRet < 0) {
                gSqlCon.close();
                iRet = UNSUCCESSFUL_DATABASE_INSERT_OPERATION;
                return iRet;
            }
            iRet = INT_RET_OK;
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

    public int StartOmniviewMeasurementWorks() throws SQLException {
        // not needed because of groups   RETHINK JIs 9.4 2021 klo 10:20
        int iRet;
        try {
            java.sql.PreparedStatement stmt;
            String SQL = " exec [dbo].[TRPX_StartOmniviewMeasurementWorks]; ";
            logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            stmt.close();
            if (iRet < 0) {
                gSqlCon.close();
                iRet = UNSUCCESSFUL_DATABASE_INSERT_OPERATION;
                return iRet;
            }
            iRet = INT_RET_OK;
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

    public int CreateControllerGroups() throws SQLException {
        int iRet;
        try {
            java.sql.PreparedStatement stmt;
            String SQL = " exec [dbo].[TRPX_CreateControllerGroups];  ";
            logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            stmt.close();
            if (iRet < 0) {
                gSqlCon.close();
                iRet = UNSUCCESSFUL_DATABASE_INSERT_OPERATION;
                return iRet;
            }
            iRet = INT_RET_OK;
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

    public int CreateControllerGroups3(long plngDetectorCount) throws SQLException {
        // JIs 27.4 2021 klo 15:25
        int iRet;
        try {
            java.sql.PreparedStatement stmt;
            String SQL = " exec [dbo].[TRPX_CreateControllerGroups3] ";
            SQL = SQL + plngDetectorCount + " ;";
            //     logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            stmt.close();
            if (iRet < 0) {
                gSqlCon.close();
                iRet = UNSUCCESSFUL_DATABASE_INSERT_OPERATION;
                return iRet;
            }
            iRet = INT_RET_OK;
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
    public int GetFirstDebtPaymentTimestampToTransfer() throws SQLException {
        // JIs 27.4 2021 klo 15:25
        int iRet;
        try {
            java.sql.PreparedStatement stmt;
            String SQL = " exec [dbo].[TRPX_GetFirstDebtPaymentTimestampToTransfer] ";
            SQL = SQL + getOmniaCode() + " ;";
            //     logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            stmt.close();
            if (iRet < 0) {
                gSqlCon.close();
                iRet = UNSUCCESSFUL_DATABASE_INSERT_OPERATION;
                return iRet;
            }
            iRet = INT_RET_OK;
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
    public int HourlyUpdateTransferredLines() throws SQLException {
        int iRet;
        try {
            java.sql.PreparedStatement stmt;
            String SQL = " exec [dbo].[TRPX_HourlyUpdateTranferredState] ";
            SQL = SQL + getOmniaCode() + " ;";
            //     logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            stmt.close();
            if (iRet < 0) {
                gSqlCon.close();
                iRet = UNSUCCESSFUL_DATABASE_INSERT_OPERATION;
                return iRet;
            }
            iRet = INT_RET_OK;
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
    public int UpdateTransferredState() throws SQLException {
        int iRet;
        try {
            java.sql.PreparedStatement stmt;
            String SQL = " exec [dbo].[TRPX_UpdateTransferredState] ";
            SQL = SQL + getOmniaCode() + " ;";
            logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            stmt.close();
            if (iRet < 0) {
                gSqlCon.close();
                iRet = UNSUCCESSFUL_DATABASE_INSERT_OPERATION;
                return iRet;
            }
            iRet = INT_RET_OK;
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
    public int GetNextTimestampToTransfer(int pintSelectionMode) throws SQLException {
        int iRet;
        try {
            java.sql.PreparedStatement stmt;
            // TRPX_GetNextTimestampToTransfer @pOmniaCode int,  @pISelectionMode int
            String SQL = " exec [dbo].[TRPX_GetNextTimestampToTransfer] ";
            SQL = SQL + getOmniaCode() + ", ";
            SQL = SQL + pintSelectionMode +" ;";
            logger.info("SQL = " + SQL);
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
            stmt.close();
            if (iRet < 0) {
                gSqlCon.close();
                iRet = UNSUCCESSFUL_DATABASE_INSERT_OPERATION;
                return iRet;
            }
            iRet = INT_RET_OK;
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




}






