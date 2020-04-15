package fi.swarco.dataHandling;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.dataHandling.omniaClientDataHandling.TimeSeriesClientDataLevel;
import fi.swarco.dataHandling.pojos.TRPXMeasurementTaskData;
import fi.swarco.dataHandling.pojos.influx.InfluxSqlToTimeSerie;
import fi.swarco.influxoperations.InfluxJavaWrite;
import fi.swarco.omniaDataTransferServices.LogUtilities;
import fi.swarco.serviceOperations.DeleteDoneTasks;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;
import static fi.swarco.CONSTANT.*;
public class MakeSendTimeSeriesOperations {
    static Logger logger = Logger.getLogger(MakeSendTimeSeriesOperations.class.getName());
    private long currentWorkIndex = 0;

    public long getCurrentWorkIndex() {
        return currentWorkIndex;
    }

    public void setCurrentWorkIndex(long pCurrentWorkIndex) {
        currentWorkIndex = pCurrentWorkIndex;
    }

    private static int intSleep = 5000;

    public static int getSleep() {
        return intSleep;
    }

    public static void setSleep(int pSleep) {
        intSleep = pSleep;
    }

    private static String jSonMeasurementData = "novalue";

    private static String getJSonMeasurementData() {
        return jSonMeasurementData;
    }

    private static void setjSonMeasurementData(String pJSonMeasurementData) {
        jSonMeasurementData = pJSonMeasurementData;
    }

    private static String jSonDataForTransfer = NO_VALUE;

    public static String getJSonDataForTransfer() {
        return jSonDataForTransfer;
    }

    public static void setJSonDataForTransfer(String pJSonDataForTransfer) {
        jSonDataForTransfer = pJSonDataForTransfer;
    }

    private static TRPXMeasurementTaskData TaskUnderWork = null;

    public static TRPXMeasurementTaskData getTaskUnderWork() {
        return TaskUnderWork;
    }

    public static void setTaskUnderWork(TRPXMeasurementTaskData pTaskUnderWork) {
        TaskUnderWork = pTaskUnderWork;
    }

    private static String workType = TT_NO_WORK;

    public static String getWorkType() {
        return workType;
    }

    public static void setWorkType(String pWorkType) {
        workType = pWorkType;
    }

    private static MeasurementTaskHandling th = new MeasurementTaskHandling();

    private int MakeClearanceOperations(TRPXMeasurementTaskData pCe) throws SQLException {
        String strHelp1 = NO_VALUE;
        int iRet;
        LogUtilities mfl = new LogUtilities();
        iRet = th.UpdateTaskFromDbForClearance(pCe);
        if (iRet < 0) {
            logger.info("database update error iRet = " + iRet);
        }
        iRet = mfl.MakeFullLogOperations(
                SwarcoEnumerations.LoggingDestinationType.OMNIA_CLIENT,
                SwarcoEnumerations.ApiMessageCodes.DATAERROR,
                "data not found to send " + pCe.toString());
        if (iRet < 0) {
            logger.info("log write error  iRet = " + iRet);
        }
        logger.info("No JsonMeasurementData! ");
        return OMNIA_DATA_PICK_NOT_OK;
    }

    private int MakeSpareOperations(TRPXMeasurementTaskData pCe) throws SQLException {
        String strHelp1;
        int iRet;
        LogUtilities mfl = new LogUtilities();
        strHelp1 = th.getPermanentSqlDataSpare(pCe.getIntersectionId(), pCe.getControllerId());
        logger.info("spare strHelp1 = " + strHelp1);
        if (strHelp1.equals(NO_VALUE)) {   // Still no data
            iRet = th.UpdateTaskFromDbForClearance(pCe);
            if (iRet < 0) {
                logger.info("database update error iRet = " + iRet);
            }
            iRet = mfl.MakeFullLogOperations(
                    SwarcoEnumerations.LoggingDestinationType.OMNIA_CLIENT,
                    SwarcoEnumerations.ApiMessageCodes.DATAERROR,
                    "Unsuccessful data send " + pCe.toString());
            if (iRet < 0) {
                logger.info("log write error  iRet = " + iRet);
            }
            logger.info("No JsonPermanentData ");
        }
        return OMNIA_DATA_PICK_NOT_OK;
    }

    public int PollOfTimeSerieWorks() {
        int iloop = 1;
        int intSleep = 0;
        int iRet;
        iRet = MeasurementTaskHandling.MakeConnection(SwarcoEnumerations.ConnectionType.SQLSERVER_LOCAL_JOMNIATEST);
        if (iRet != INT_RET_OK) {
            logger.info("Ei kantayhteytt� lopetetaan");
            return OMNIA_DATA_PICK_NOT_OK;
        }
        try {
            iRet = th.AnyWorkWork();
            if (iRet > 0) {
                return THERE_IS_WORK;
            }
            // in time series world only Measurement trigger is followed
            // Jis 15.1.2020
            while (iloop == 1) {
                if (th.AnyWorkMeasurements() == 0) {
                    Thread.sleep(getSleep());   // 5 seconds sleep
                } else {
                    iRet = th.DeleteTrashTasksBeforeHand();
                    if (iRet != DELETE_TRASH_TASK_OK) {
                        logger.info("Task Transfer error iRet = " + iRet);
                        return iRet;
                    }
                    iRet = th.TransferMeasurementTasksToWorkQueue();
                    if (iRet != TASK_TRANSFER_OK) {
                        logger.info("Task Transfer error iRet = " + iRet);
                        return iRet;
                    }
                    setWorkType(TT_MEASUREMENT_DATA_INSERT);
                    iRet = th.FillUpTasks();
                    if (iRet != FILL_UP_TASK_OK) {
                        logger.info("Fill UpTask error iRet = " + iRet);
                        return iRet;
                    }
                }
                return THERE_IS_WORK;
            }
        } catch (Exception e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            e.printStackTrace();
        }
        return iRet;
    }

    public int MakeSendOmniaTimeSeriesOperations() throws SQLException {
        LogUtilities mfl = new LogUtilities();
        int iRet = MeasurementTaskHandling.MakeConnection(SwarcoEnumerations.ConnectionType.SQLSERVER_LOCAL_JOMNIATEST);
        if (iRet != INT_RET_OK) {
            logger.info("Ei kantayhteytt� lopetetaan");
            return OMNIA_DATA_PICK_NOT_OK;
        }
        TRPXMeasurementTaskData ce = null;
        int iRound = 0;
        int iloop = 1;
        int iRet2 = 0;
        String strHelp1 = NO_VALUE;
        TimeSeriesClientDataLevel tml = new TimeSeriesClientDataLevel();
        while (iloop == 1) {
            iRet2 = th.DeleteTrashTasksAfterHandFromTaskList();
            iRet = th.MeasurementTaskDataList();
            if (iRet != INT_RET_OK) {
                if (iRet == NO_TASK_LIST) {
                    iRet2 = th.DeleteTrashTasksAfterHand();
                    logger.info("Successful task delete afterhand continue");
                    iRet2 = th.DeleteTrashTasksAfterHandFromTaskList();
                    iRet = th.MeasurementTaskDataList();
                    if (iRet != INT_RET_OK) {
                        return OMNIA_DATA_PICK_NOT_OK;
                    }
                }
            }
            ce = th.GetFirstUndoneTaskFromList();   // -Work table
            setWorkType(ce.getTaskType());    //
            setTaskUnderWork(ce);

            if (ce.getMeasurementTaskIdindex() == EMPTY_ELEMENT) {
                logger.info("Empty task list wait 1 s ");
                iRound = iRound + 1;
                return OMNIA_EMPTY_WORK_LIST;   // HERE HERE
            }
            iRet = tml.MakeConnection(SwarcoEnumerations.ConnectionType.SQLSERVER_LOCAL_JOMNIATEST, SwarcoEnumerations.ConnectionType.INFLUX_LOCAL);
            if (iRet != DATABASE_CONNECTION_OK) {
                logger.info("No Database conncection iRet =" + iRet);
                System.exit(0);
            }
            // here logic what type work logic there are only one type of task on _work   table
            List<String> tsList;
            tsList = tml.FillControllerMeasurementsStringList(ce.getControllerId(), ce.getDetectorMeasuresTimestamp());    //controllerId, timestamp
            iRet = th.CreateInfluxWriteFile(tsList);
            if (iRet != INT_RET_OK) {
                return iRet;
            }
            InfluxJavaWrite jw = new InfluxJavaWrite();
            jw.setUp1();
            iRet = jw.WriteFileToInflux();
// ********************************************************************************                                    }
            DeleteDoneTasks ms = new DeleteDoneTasks();
            ms.setTaskUnderWork(getTaskUnderWork());
            ms.setWorkType(getWorkType());
            iRet = ms.DeleteDoneTaskFromWorkDb2();
            if (iRet != INT_RET_OK) {
                logger.info("Unsuccesfull delete from tasklist iRet = " + iRet);
            }
            if (iRound >= 2) {
                try {
                    Thread.sleep(getSleep());
                    iRound = 0;
                } catch (InterruptedException e) {
                    logger.info(ExceptionUtils.getRootCauseMessage(e));
                    logger.info(ExceptionUtils.getFullStackTrace(e));
                    e.printStackTrace();
                }
            }
        }
        return INT_RET_OK;
    }
     // polling loop while end
     //   return OMNIA_DATA_PICK_OK;
    //} catch( Exception e)  {
    //    logger.info(ExceptionUtils.getRootCauseMessage(e));
    //    logger.info(ExceptionUtils.getFullStackTrace(e));
    //    e.printStackTrace();
   // }


    public int DeleteDoneTaskFromWorkDb() throws SQLException {
        TRPXMeasurementTaskData ce = getTaskUnderWork();
        long doneWorkIndex = 0;
        int iRet = INT_RET_OK;
        if (getWorkType().equals(TT_MEASUREMENT_DATA_INSERT)) {
            iRet = th.DeleteDoneTaskFromDb(ce);
            if (iRet < 0) {   // 0 they have all ready been deleted
                logger.info("Unsuccessful delete from worktask ce.toString() =  " + ce.toString());
                logger.info("Unsuccessful delete from worktask iRet = " + iRet);
                return OMNIA_DATA_PICK_NOT_OK;
            }
            iRet = th.DeleteDoneTaskFromWorkDb(ce);
            if (iRet < 1) {
                logger.info("Unsuccessful delete from Work table ce.toString() =  " + ce.toString());
                logger.info("Unsuccessful delete from Work table iRet = " + iRet);
                return OMNIA_DATA_PICK_NOT_OK;
            }
            return INT_RET_OK;
        } else {
            logger.info("No tasks/works to deleted doneWorkIndex = " + doneWorkIndex);
            return INT_RET_NOT_OK;
        }
    }
    public String MakeMessageToBeSended() throws SQLException {
        String strHelp1 = NO_VALUE;
        String strHelp2 = NO_VALUE;
        TRPXMeasurementTaskData ce = getTaskUnderWork();
        if (getWorkType().equals(TT_NOT_DEFINED)) {
            strHelp2 = getJSonMeasurementData();
            strHelp1 = strHelp1 + strHelp2;
            return strHelp1;
        }
        return strHelp1;
    }
}
