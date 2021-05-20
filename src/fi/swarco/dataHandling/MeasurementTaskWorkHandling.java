package fi.swarco.dataHandling;
import java.sql.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.connections.SwarcoConnections;
import fi.swarco.dataHandling.pojos.TRPXMeasurementTaskWorkData;
import fi.swarco.dataHandling.queriesSql.sqlServer.JiMeasurementTaskWorkSelectSqlServer;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import static fi.swarco.CONSTANT.*;
public class MeasurementTaskWorkHandling {
    private static Logger logger = Logger.getLogger(MeasurementTaskWorkHandling.class.getName());
    private List<TRPXMeasurementTaskWorkData> TaskUnits = Collections.synchronizedList(new LinkedList<>());  // ????
    static Connection gSqlCon;
    private static SwarcoEnumerations.ConnectionType SqlConnectionType = SwarcoEnumerations.ConnectionType.NOT_DEFINED;
    private SwarcoEnumerations.RequestOriginType requestOrigin = SwarcoEnumerations.RequestOriginType.NORMALROAD;
    public void setRequestOrigin(SwarcoEnumerations.RequestOriginType prequestOrigin) {
        requestOrigin = prequestOrigin;
    }
    public static int MakeConnection(SwarcoEnumerations.ConnectionType pSqlCon)  {
        SwarcoConnections vg = new SwarcoConnections();
//        logger.info("pSqlCon = " + pSqlCon);
        int iRet = vg.MakeConnection(pSqlCon);
        if (iRet != INT_RET_OK) {
            return iRet;
        }
        SqlConnectionType = pSqlCon;
  //      logger.info(" *** this line must be seen SqlConnectionType = " + SqlConnectionType);
        gSqlCon = vg.getSqlCon();
        return INT_RET_OK;
    }
    public int MeasurementTaskWorkDataList() throws SQLException {
        TaskUnits.clear();
        java.sql.PreparedStatement stmt;
        ResultSet rs;
        JiMeasurementTaskWorkSelectSqlServer st = new JiMeasurementTaskWorkSelectSqlServer();
        String SQL = st.getStatement();
 //       logger.info("SqlConnectionTypeyyyy= " + SqlConnectionType);
    //    logger.info("SQL = " + SQL);
        TRPXMeasurementTaskWorkData ce;
        try {
            stmt = gSqlCon.prepareStatement(SQL);
            rs = stmt.executeQuery();
      //      logger.info(" rs.getFetchSize() = " + rs.getFetchSize());
            while (rs.next()) {
                ce = new TRPXMeasurementTaskWorkData();
                ce.setWorkIdIndex(rs.getLong(1));
                ce.setMeasurementTaskIdIndex(rs.getLong(2));
                ce.setOmniaCode(rs.getLong(3));
                ce.setIntersectionId(rs.getLong(4));
                ce.setControllerId(rs.getLong(5));
                ce.setDetectorId(rs.getLong(6));
                ce.setDetectorMeasuresTimestamp(rs.getString(7));
                ce.setPermanentDataTimestamp(rs.getString(8));
                ce.setTaskType(rs.getString(9));
                ce.setTaskStatus(rs.getLong(10));
                ce.setCreated(rs.getString(11));
                ce.setWorkCreated(rs.getString(12));
                TaskUnits.add(ce);
            }
            stmt.close();
            rs.close();
            if (TaskUnits.isEmpty()) {
                ce = new TRPXMeasurementTaskWorkData();
                ce.MakeEmptyElement();
                TaskUnits.add(ce);
                return NO_TASK_LIST;
            }
      //      logger.info("bef ret iRet OK");
            return INT_RET_OK;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            ce = new TRPXMeasurementTaskWorkData();
            ce.MakeEmptyElement();
            TaskUnits.add(ce);
            gSqlCon.close();
            return NO_TASK_LIST;
        }
    }
    public TRPXMeasurementTaskWorkData GetFirstTaskWorkFromList() {
        TRPXMeasurementTaskWorkData ce = new TRPXMeasurementTaskWorkData();
 //       logger.info("TaskUnits.size()= " + TaskUnits.size());
        for (int i = 0; i < TaskUnits.size(); i++) {
            ce = TaskUnits.get(i);
      //      logger.info("ce.toString()=" + ce.toString());
            return ce;
        }
        ce.MakeEmptyElement();
        return ce;
    }
    public int AnyWorkWork() throws SQLException{
        int iRet = INT_RET_NOT_OK;
        try {
            String SQL = "select count(*) from TRPX_MeasurementTask_Work;";
//            logger.info("SQL = " + SQL);
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            ResultSet rs;
            rs = stmt.executeQuery();
 //           logger.info(" rs.getFetchSize() = " + rs.getFetchSize());
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
    public TRPXMeasurementTaskWorkData GetFirstWorkTask() throws SQLException{
       int iRet =  MeasurementTaskWorkDataList();
       TRPXMeasurementTaskWorkData tr;
       if (iRet<0) {
           tr = new TRPXMeasurementTaskWorkData();
           tr.MakeEmptyElement();
           return tr;
       }
        tr =GetFirstTaskWorkFromList();
        return tr;
    }
    public int MakeStartOmniviewClientDbOperations() throws SQLException{
        // RETHINK TaskType
        int iRet;
  //      logger.info("Moi Jatri2");
        try {
        // this transfer all permanent data
            String SQL = " exec dbo.TRPX_StartOmniviewClientDbOperations; ";
            // this only truncates work table
          //  String SQL = " exec dbo.TRPX_StartOmniviewClientDbOperations2; ";
        //    logger.info("SQL = " + SQL);
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
       //     logger.info(" iRet = " + iRet);
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
    public String  GetWorkTaskType() throws SQLException {
        String strRet;
        int iRet = 0;
        java.sql.PreparedStatement stmt;
        try {
            String SQL = " select [dbo].[TRPX_GetWorkTaskType]() ";
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
            return ERROR_VALUE;
        }
        return ERROR_VALUE;
    }
    public int DeleteTasksFromWorkDb() throws SQLException{
        // RETHINK TaskType
        int iRet;
        try {
            java.sql.PreparedStatement stmt;
            String SQL = " delete from TRPX_MeasurementTask_Work ";
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
       //     logger.info("Lines deleted iRet = " + iRet);
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
    private int CountTrashTasksBeforeDelete() throws SQLException{
// Take first count  RETHINK Jis 18.11 2019     copied also to MeasurementTaskHandling
        int iRet;
        try {
            String SQL = " select count(*) from TRPX_MeasurementTask task";
            SQL = SQL + "  join TRPX_Super2Invisible invi on invi.Detectorid=task.detectorid";
            String strRet;
            java.sql.PreparedStatement stmt;
            logger.info("SQL = " + SQL);
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
    private int DeleteTrashTasksAfterHandFromTaskList() throws SQLException{
// clean also task list if other error message is given RETHINK Jis 18.11 2019 copied from to MeasurementTaskHandling
        int iRet = CountTrashTasksBeforeDelete();
        if (iRet == 0) {   // nothing to delete
            return DELETE_TRASH_TASKTASK_OK;
        }
        if (iRet  < 0) {   // nothing to delete
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
        //    logger.info("Lines deleted ************************** iRet = " + iRet);
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
    public int ExtraCleanUp() throws SQLException{
        int iRet;
        try {
           iRet = DeleteTrashTasksAfterHandFromTaskList();
            if (iRet < 0) {
                gSqlCon.close();
                iRet = EXTRACLEANUP_DELETE_TASK_ERROR;
                return iRet;
            }
            return EXTRACLEANUP_TASK_OK;
        } catch (Exception e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            logger.info(" catch 11");
            logger.info(e.getMessage());
            e.printStackTrace();
            gSqlCon.close();
            return EXTRACLEANUP_DELETE_TASK_ERROR;
        }
    }
   public int ReCreateIntersectionTask(TRPXMeasurementTaskWorkData pCe) throws SQLException {
        int iRet;
        try {
            java.sql.PreparedStatement stmt;
            String SQL = "INSERT INTO [dbo].[TRPX_MeasurementTask] ( ";
            SQL = SQL + " [OmniaCode] ";
            SQL = SQL + " ,[IntersectionID] ";
            SQL = SQL + " ,[ControllerId] ";
            SQL = SQL + " ,[DetectorID] ";
            SQL = SQL + " ,[DetectorMeasuresTimestamp] ";
            SQL = SQL + " ,[PermanentDataTimestamp]  ";
            SQL = SQL + " ,[TaskType] ";
            SQL = SQL + " ,[TaskStatus] ";
            SQL = SQL + " ,[Created])  ";
            SQL = SQL + " select  ";
            SQL = SQL + " 7777  ";
            SQL = SQL + " ,[IntersectionID]  ";
            SQL = SQL + " ,7777 ";
            SQL = SQL + " ,7777 ";
            SQL = SQL + " , [LastUpdate]  ";
            SQL = SQL + " , [LastUpdate]  ";
            SQL = SQL + " ,'INTERSECTIONDATACHANGE' ";
            SQL = SQL + " ,0 ";
            SQL = SQL + " ,GETDATE() ";
            SQL = SQL + " from [dbo].[Intersections]  where visible=1 and deleted=0 and ";
            SQL = SQL + " IntersectionId = " + pCe.getIntersectionId() + " and ";
            SQL = SQL + "  LastUpdate = '" + pCe.getPermanentDataTimestamp() + "'";
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
       //     logger.info("Lines inserted iRet = " + iRet);
            stmt.close();
            if (iRet < 0) {
                gSqlCon.close();
                iRet = UNSUCCESSFUL_DATABASE_INSERT_OPERATION;
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
    public int ReCreateMeasurementsTasks(TRPXMeasurementTaskWorkData pCe) throws SQLException {
        int iRet;
        try {
            java.sql.PreparedStatement stmt;
            String SQL = "INSERT INTO [dbo].[TRPX_MeasurementTask]( ";
            SQL = SQL + " [OmniaCode] ";
            SQL = SQL + " ,[IntersectionID] ";
            SQL = SQL + " ,[ControllerId] ";
            SQL = SQL + " ,[DetectorID] ";
            SQL = SQL + " ,[DetectorMeasuresTimestamp] ";
            SQL = SQL + " ,[PermanentDataTimestamp]  ";
            SQL = SQL + " ,[TaskType] ";
            SQL = SQL + " ,[TaskStatus] ";
            SQL = SQL + " ,[Created])  ";
            SQL = SQL + " select  ";
            SQL = SQL + " 7777  ";
            SQL = SQL + " ,7777  ";
            SQL = SQL + " ,7777 ";
            SQL = SQL + " ,[DetectorId] ";
            SQL = SQL + " , [Timestamp]  ";
            SQL = SQL + " , [Timestamp] ";
            SQL = SQL + " ,'MEASUREMENTDATAINSERT' ";
            SQL = SQL + " ,0 ";
            SQL = SQL + " ,GETDATE() ";
            SQL = SQL + " from [dbo].[TM_DetectorMeasures]  where  ";
            SQL = SQL + "  [Timestamp] = '" + pCe.getDetectorMeasuresTimestamp() + "'";
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
       //     logger.info("Lines inserted iRet = " + iRet);
            stmt.close();
            if (iRet < 0) {
                gSqlCon.close();
                iRet = UNSUCCESSFUL_DATABASE_INSERT_OPERATION;
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
    public int ReCreateControllerTask(TRPXMeasurementTaskWorkData pCe) throws SQLException {
        int iRet;
        try {
            java.sql.PreparedStatement stmt;
            String SQL = "INSERT INTO [dbo].[TRPX_MeasurementTask] ( ";
            SQL = SQL + " [OmniaCode] ";
            SQL = SQL + " ,[IntersectionID] ";
            SQL = SQL + " ,[ControllerId] ";
            SQL = SQL + " ,[DetectorID] ";
            SQL = SQL + " ,[DetectorMeasuresTimestamp] ";
            SQL = SQL + " ,[PermanentDataTimestamp]  ";
            SQL = SQL + " ,[TaskType] ";
            SQL = SQL + " ,[TaskStatus] ";
            SQL = SQL + " ,[Created])  ";
            SQL = SQL + " select  ";
            SQL = SQL + " 7777  ";
            SQL = SQL + " ,7777  ";
            SQL = SQL + " ,[ControllerId] ";
            SQL = SQL + " ,7777 ";
            SQL = SQL + " , [LastUpdate]  ";
            SQL = SQL + " , [LastUpdate]  ";
            SQL = SQL + " ,'CONTROLLERDATACHANGE' ";
            SQL = SQL + " ,0 ";
            SQL = SQL + " ,GETDATE() ";
            SQL = SQL + " from [dbo].[Controllers]  where visible=1 and deleted=0 and ";
            SQL = SQL + " ControllerId = " + pCe.getControllerId() + " and ";
            SQL = SQL + "  LastUpdate = '" + pCe.getPermanentDataTimestamp() + "'";
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
       //     logger.info("Lines inserted iRet = " + iRet);
            stmt.close();
            if (iRet < 0) {
                gSqlCon.close();
                iRet = UNSUCCESSFUL_DATABASE_INSERT_OPERATION;
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
    public int ReCreateDetectorTask(TRPXMeasurementTaskWorkData pCe) throws SQLException {
        int iRet;
        try {
            java.sql.PreparedStatement stmt;
            String SQL = "INSERT INTO [dbo].[TRPX_MeasurementTask] ( ";
            SQL = SQL + " [OmniaCode] ";
            SQL = SQL + " ,[IntersectionID] ";
            SQL = SQL + " ,[ControllerId] ";
            SQL = SQL + " ,[DetectorID] ";
            SQL = SQL + " ,[DetectorMeasuresTimestamp] ";
            SQL = SQL + " ,[PermanentDataTimestamp]  ";
            SQL = SQL + " ,[TaskType] ";
            SQL = SQL + " ,[TaskStatus] ";
            SQL = SQL + " ,[Created])  ";
            SQL = SQL + " select  ";
            SQL = SQL + " 7777  ";
            SQL = SQL + " ,7777  ";
            SQL = SQL + " ,7777 ";
            SQL = SQL + " ,[DetectorId] ";
            SQL = SQL + " , [LastUpdate]  ";
            SQL = SQL + " , [LastUpdate]  ";
            SQL = SQL + " ,'DETECTORDATACHANGE' ";
            SQL = SQL + " ,0 ";
            SQL = SQL + " ,GETDATE() ";
            SQL = SQL + " from [dbo].[Detectors]  where visible=1 and deleted=0 and ";
            SQL = SQL + " DetectorId = " + pCe.getDetectorId() + " and ";
            SQL = SQL + "  LastUpdate = '" + pCe.getPermanentDataTimestamp() + "'";
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
        //    logger.info("Lines inserted iRet = " + iRet);
            stmt.close();
            if (iRet < 0) {
                gSqlCon.close();
                iRet = UNSUCCESSFUL_DATABASE_INSERT_OPERATION;
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
    public int DefineFirstUnhandledGroup() throws SQLException {
        int iRet = INT_RET_NOT_OK;
        try {
            String SQL = "exec dbo.TRPX_DefineFirstUnhandledGroup;";
        //    logger.info("SQL = " + SQL);
            java.sql.PreparedStatement stmt;
            stmt = gSqlCon.prepareStatement(SQL);
            iRet = stmt.executeUpdate();
        //    logger.info(" iRet = " + iRet);
            if(iRet==0) {
                iRet=INT_RET_OK;
            }
            stmt.close();
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
    public  int GetOmniaCode () throws SQLException{
// get omniacode to
        int iRet;
        try {
            String strRet;

            String SQL =   "select [dbo].[TRPX_GetOmniaCode]();";
            java.sql.PreparedStatement stmt;
            logger.info("SQL = " + SQL);
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

}


