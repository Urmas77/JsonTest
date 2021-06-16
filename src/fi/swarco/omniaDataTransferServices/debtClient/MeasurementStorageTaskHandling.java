package fi.swarco.omniaDataTransferServices.debtClient;
import java.sql.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.connections.SwarcoConnections;
import fi.swarco.dataHandling.omniaClientDataHandling.*;
import fi.swarco.dataHandling.pojos.TRPXMeasurementTaskData;
import fi.swarco.dataHandling.queriesSql.sqlServer.JiMeasurementStorageTaskSelectSqlServer;
import fi.swarco.dataHandling.queriesSql.sqlServer.JiMeasurementTaskSelectSqlServer;
import fi.swarco.omniaDataTransferServices.FileOperations;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import static fi.swarco.CONSTANT.*;
import static fi.swarco.omniaDataTransferServices.omniaClient.OmniaClient.getSqlServerConnectionType;

public class MeasurementStorageTaskHandling {
    private static Logger logger = Logger.getLogger(MeasurementStorageTaskHandling.class.getName());
    private List<TRPXMeasurementTaskData> TaskUnits = Collections.synchronizedList(new LinkedList<>());  // ????
    private static Connection gSqlCon;
    private  FileOperations fo =new FileOperations();
    private static SwarcoEnumerations.ConnectionType SqlConnectionType = SwarcoEnumerations.ConnectionType.NOT_DEFINED;
    private SwarcoEnumerations.RequestOriginType requestOrigin = SwarcoEnumerations.RequestOriginType.NORMALROAD;
    public void setRequestOrigin(SwarcoEnumerations.RequestOriginType prequestOrigin) {
        requestOrigin = prequestOrigin;
    }
    public List<TRPXMeasurementTaskData> getMeasurementTaskData() {return TaskUnits; };

    public static int MakeConnection(SwarcoEnumerations.ConnectionType pSqlCon)  {
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
    public int MeasurementTaskDataList() throws SQLException {
        TaskUnits.clear();
        String SQL;
        java.sql.PreparedStatement stmt;
        ResultSet rs;
       // JiMeasurementTaskSelectSqlServer st = new JiMeasurementTaskSelectSqlServer();
        JiMeasurementStorageTaskSelectSqlServer st = new JiMeasurementStorageTaskSelectSqlServer();
        SQL = st.getStatement();
  //      logger.info("SqlConnectionTypeyyyy= " + SqlConnectionType);
  //      logger.info("SQL = " + SQL);
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
    public int AnyWorkWork() throws SQLException{    // ???
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
        try {
            String SQL = "select count(*) from TRPX_MeasurementTask_Storage";
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
        try {
            String SQL = "select count(*) from TRPX_MeasurementTask_Storage where taskType='MEASUREMENTDATAINSERT';";
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
   
    public int TransferMeasurementTasksToWorkQueue() throws SQLException{
        int iRet;
        try {
            String SQL = "insert into TRPX_MeasurementTask_Storage_Work ";
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
            SQL = SQL + "where TaskType = 'MEASUREMENTDATAINSERT' and  " ;
            SQL = SQL +  " DetectorMeasuresTimestamp = ";
            SQL = SQL + " (select  max(DetectorMeasuresTimestamp) from TRPX_MeasurementTask_Storage where  TaskType = 'MEASUREMENTDATAINSERT') ";
            SQL = SQL + " order by DetectorMeasuresTimestamp asc ";
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
     public int FillUpTasks() throws SQLException{
        int iRet;
        try {
            String SQL = " update TRPX_MeasurementTask_Storage_Work ";
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
 
    private int CountTrashTasksBeforeDelete() throws SQLException{
// Take first count  RETHINK Jis 18.11 2019     copied also to MeasurementTaskWorkHandling
        int iRet;
        try {
            String SQL = " select count(*) from TRPX_MeasurementTask_Storage task";
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
    public int DeleteTrashTasksAfterHandFromTaskList() throws SQLException{
// clean also task list if other error message is given RETHINK Jis 18.11 2019 copied also to MeasurementTaskWorkHandling
        int iRet = CountTrashTasksBeforeDelete();
        if (iRet == 0) {   // nothing to delete
            return DELETE_TRASH_TASKTASK_OK;
        }
        if (iRet  < 0) {   // nothing to delete
            logger.info(" Count error iRet =" + iRet);
            return DELETE_TRASH_TASKTASK_OK;
        }
        try {
            String SQL = "delete from TRPX_MeasurementTask_Storage  ";
            SQL = SQL + " where MeasurementTask_idindex in (   ";
            SQL = SQL + " select MeasurementTask_idindex from TRPX_MeasurementTask_Storage task";
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
            return DELETE_TRASH_TASK_ERROR ;
        }
    }
    public int DeleteTrashTasksBeforeHand() {
        int iRet;
        try {
            String SQL = "delete from TRPX_MeasurementTask  ";
            SQL = SQL + " where MeasurementTask_idindex in (   ";
            SQL = SQL + " select MeasurementTask_idindex from TRPX_MeasurementTask_Storage Task  ";
            SQL = SQL + "  join TRPX_Super2Invisible invi on invi.Detectorid=task.detectorid   ";
            SQL = SQL + " where  DetectorMeasuresTimestamp = ";
            SQL = SQL + " (select top 1 DetectorMeasuresTimestamp from TRPX_MeasurementTask_Storage where  TaskType = 'MEASUREMENTDATAINSERT'  order by DetectorMeasuresTimestamp asc))";
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
      //      logger.info("Lines deleted  iRet = " + iRet);
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
        int iRet;
        try {
            String SQL = "delete from TRPX_MeasurementTask_Work  ";
            SQL = SQL + " where MeasurementTask_idindex in (   ";
            SQL = SQL + " select MeasurementTask_idindex from TRPX_MeasurementTask_Work_Storage Task  ";
            SQL = SQL + "  join TRPX_Super2Invisible invi on invi.Detectorid=task.detectorid   ";
            SQL = SQL + " where  DetectorMeasuresTimestamp = ";
            SQL = SQL + " (select top 1 DetectorMeasuresTimestamp from TRPX_MeasurementTask_Storage_work where  TaskType = 'MEASUREMENTDATAINSERT'  order by DetectorMeasuresTimestamp asc))";
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
            return DELETE_TRASH_TASK_ERROR ;
        }
    }
    public int DeleteNotFilledTasks() throws SQLException{
        int iRet;
        try {
            String SQL = "delete from TRPX_MeasurementTask_Storage_Work  ";
            SQL = SQL + " where   ";
            SQL = SQL + " OmniaCode=7777 and  ";
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
   //     logger.info("TaskUnits.size()= " + TaskUnits.size());
        for (int i = 0; i < TaskUnits.size(); i++) {
            ce = TaskUnits.get(i);
//            logger.info("ce.toString()=" + ce.toString());
            return ce;
        }
        ce.MakeEmptyElement();
        return ce;
    }
    public int DeleteDoneTaskFromDb(TRPXMeasurementTaskData ce) throws SQLException{
        int iRet;
 //       logger.info("ce.toString()=" + ce.toString());
        try {
            java.sql.PreparedStatement stmt;
            String SQL = " delete from  TRPX_MeasurementTask_Storage " ;
            SQL = SQL +  "where MeasurementTask_idindex in ";
            SQL = SQL + "(select task.MeasurementTask_idindex from TRPX_MeasurementTask_Storage task  ";
            SQL = SQL +" join TRPX_MeasurementTask_Storage_Work work on  ";
            SQL = SQL + " work.DetectorID=task.DetectorID and ";
            SQL = SQL + " work.DetectorMeasuresTimestamp = task.DetectorMeasuresTimestamp ";
            SQL = SQL +  " where work.TaskType='MEASUREMENTDATAINSERT' and ";
            SQL = SQL +" work.OmniaCode= "  +  ce.getOmniaCode()  + "and  ";
            SQL = SQL +" work.IntersectionID= " + ce.getIntersectionId()   +  " and ";
            SQL = SQL +" work.ControllerID=  " + ce.getControllerId()   + " and ";
            SQL = SQL +" work.TaskStatus=1 and ";
            SQL = SQL +" work.DetectorMeasuresTimestamp =  " +  "'" + ce.getDetectorMeasuresTimestamp() + "');";
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
 

    public int DeleteDoneTaskUsingIdentityFromDb( long pIdentity) throws SQLException{
        int iRet;
        try {
            java.sql.PreparedStatement stmt;
            String SQL = " delete from  TRPX_MeasurementTask_Storage " ;
            SQL = SQL +  "where MeasurementTask_idindex   = "  + pIdentity ;
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
    public int DeleteDoneTaskWorkUsingIdentityFromDb(long pIdentity) throws SQLException{
        // RETHINK TaskType
        int iRet;
        try {
            java.sql.PreparedStatement stmt;
            String SQL = " delete from TRPX_MeasurementTask_Storage_Work ";
            SQL = SQL +  "where MeasurementTask_idindex   = "  + pIdentity ;
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
    public int DeleteMeasurementTasksFromDb() throws SQLException{
        int iRet;
        try {
            java.sql.PreparedStatement stmt;
            String SQL = " delete from TRPX_MeasurementTask_Storage ";
            SQL = SQL + "where TaskType='MEASUREMENTDATAINSERT'  and ";
            SQL = SQL + "  DetectorMeasuresTimestamp = (select top 1 DetectorMeasuresTimestamp from TRPX_MeasurementTask_Storage where TaskType='MEASUREMENTDATAINSERT' order by   DetectorMeasuresTimestamp asc);";
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
    public int DeleteTasksFromDb() throws SQLException{
        int iRet;
        try {
            java.sql.PreparedStatement stmt;
            String SQL = " delete from TRPX_MeasurementTask_Storage ";
            SQL = SQL + "where ";
            SQL = SQL + "  DetectorMeasuresTimestamp = (select top 1 DetectorMeasuresTimestamp from TRPX_MeasurementTask_Storage order by   DetectorMeasuresTimestamp asc);";
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
    public int DeleteDoneTaskFromWorkDb(TRPXMeasurementTaskData ce) throws SQLException{
        int iRet;
        try {
            java.sql.PreparedStatement stmt;
            String SQL = " delete from TRPX_MeasurementTask_Storage_Work ";
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
 
    public String getMeasurementSqlData(long plngIntersectionId, long plngControllerId, String pstrTimestamp) throws SQLException{
        String strRet = NO_VALUE;
        int iRet;
        java.sql.PreparedStatement stmt;
        DetectorMeasurementsClientDataLevel oi = new DetectorMeasurementsClientDataLevel();
        SwarcoEnumerations.ConnectionType  oConnType;
        oConnType=getSqlServerConnectionType();
        iRet = oi.MakeConnection(oConnType);
        if (iRet == DATABASE_CONNECTION_OK) {
            strRet = oi.GetMeasurementsDataString(plngIntersectionId, plngControllerId, pstrTimestamp);
        }
        return strRet;
    }


    public String getMeasurementShortSqlData(long plngIntersectionId, long plngControllerId, String pstrTimestamp) throws SQLException{
        String strRet = NO_VALUE;
        int iRet;
        java.sql.PreparedStatement stmt;
        DetectorMeasurementsShortClientDataLevel oi = new DetectorMeasurementsShortClientDataLevel();
        SwarcoEnumerations.ConnectionType  oConnType;
        oConnType=getSqlServerConnectionType();
        iRet = oi.MakeConnection(oConnType);
        if (iRet == DATABASE_CONNECTION_OK) {
            strRet = oi.GetMeasurementsDataString(plngIntersectionId, plngControllerId, pstrTimestamp);
        }
        return strRet;
    }

}