package fi.swarco.dataHandling;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.dataHandling.pojos.TRPXMeasurementTaskData;
import fi.swarco.omniaDataTransferServices.LogUtilities;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import java.sql.SQLException;
import static fi.swarco.CONSTANT.*;
public class MakeSendJsonOperations {
    static Logger logger = Logger.getLogger(MakeSendJsonOperations.class.getName());
    private static String jSonPermanentData = "novalue";
    private static String getJSonPermanentData() {
        return jSonPermanentData;
    }
    private static void setjSonPermanentData(String pJSonPermanentData) {
        jSonPermanentData = pJSonPermanentData;
    }
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
    public int PollOfWorks()  {
        // check is ther work is if it is transfer them to work queue
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
            while (iloop == 1) {
                if (th.AnyWorkIntersection() > 0) {
                    iRet = th.TransferIntersectionTasksToWorkQueue();
                    if (iRet != TASK_TRANSFER_OK) {
                        logger.info("Task Transfer error iRet = " + iRet);
                        return iRet;
                    }
                    setWorkType(TT_INTERSECTION_DATA_CHANGE);
                    // update work from super
                    iRet = th.FillUpIntersectionTasks();
                    if (iRet != FILL_UP_TASK_OK) {
                        logger.info("Fill UpTask error iRet = " + iRet);
                        return iRet;
                    }
                    iRet = th.DeleteNotFilledIntersectionTasks();
                    if (iRet != DELETE_UNFILLABLE_TASK_OK) {
                        logger.info("Fill UpTask error iRet = " + iRet);
                        return iRet;
                    }
                    return THERE_IS_WORK;
                }
                if (th.AnyWorkController() > 0) {
                    iRet = th.TransferControllerTasksToWorkQueue();
                    if (iRet != TASK_TRANSFER_OK) {
                        logger.info("Task Transfer error iRet = " + iRet);
                        return iRet;
                    }
                    setWorkType(TT_CONTROLLER_DATA_CHANGE);
                    iRet = th.FillUpControllerTasks();
                    if (iRet != FILL_UP_TASK_OK) {
                        logger.info("Fill UpTask error iRet = " + iRet);
                        return iRet;
                    }
                    iRet = th.DeleteNotFilledControllerTasks();
                    if (iRet != DELETE_UNFILLABLE_TASK_OK) {
                        logger.info("Fill UpTask error iRet = " + iRet);
                        return iRet;
                    }
                    return THERE_IS_WORK;
                }
                if (th.AnyWorkDetector() > 0) {
                    iRet = th.TransferDetectorTasksToWorkQueue();
                    if (iRet != TASK_TRANSFER_OK) {
                        logger.info("Task Transfer error iRet = " + iRet);
                        return iRet;
                    }
                    setWorkType(TT_DETECTOR_DATA_CHANGE);
                    // update work from super
                    iRet = th.FillUpDetectorTasks();
                    if (iRet != FILL_UP_TASK_OK) {
                        logger.info("Fill UpTask error iRet = " + iRet);
                        return iRet;
                    }
                    iRet = th.DeleteNotFilledDetectorTasks();
                    if (iRet != DELETE_UNFILLABLE_TASK_OK) {
                        logger.info("Fill UpTask error iRet = " + iRet);
                        return iRet;
                    }
                    return THERE_IS_WORK;
                }
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
    public int MakeSendOmniaOperations() throws SQLException {
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
        while (iloop == 1) {
            try {
                // do work   "old way"
                iRet2 = th.DeleteTrashTasksAfterHandFromTaskList();
                iRet = th.MeasurementTaskDataList();
                if (iRet != INT_RET_OK) {
                    if (iRet == NO_TASK_LIST) {
   //                     logger.info("Other error iRet = " + iRet);
                        iRet2 = th.DeleteTrashTasksAfterHand();
                        logger.info("Successful task delete afterhand continue");
//   Delete lines also from Task list RETHINK JIs 18.11 2019
                        iRet2 = th.DeleteTrashTasksAfterHandFromTaskList();
// do  not Handle iRet2 codes   RETHINK JIs 18.11 2019
                        iRet = th.MeasurementTaskDataList();
                        if (iRet != INT_RET_OK) {
                            return OMNIA_DATA_PICK_NOT_OK;
                        }
                    }
                }
                ce = th.GetFirstUndoneTaskFromList();   // -Work table
                setWorkType(ce.getTaskType());    //
                if (ce.getMeasurementTaskIdindex() == EMPTY_ELEMENT) {
                    logger.info("Empty task list wait 1 s ");
                    iRound = iRound + 1;
                    return OMNIA_EMPTY_WORK_LIST;   // HERE HERE
                }
// here logic what type work logic there are only one type of task on _work   table
                if (getWorkType().equals(TT_MEASUREMENT_DATA_INSERT)) {
                    strHelp1 = th.getMeasurementShortSqlData(ce.getIntersectionId(), ce.getControllerId(), ce.getDetectorMeasuresTimestamp());
                    logger.info("strHelp1 = " + strHelp1);
                //    logger.info("strHelp1.length()  = " + strHelp1.length());
                    if (strHelp1.equals(NO_VALUE)) {
                        iRet = MakeClearanceOperations(ce);
                        logger.info("No JsonMeasurementData! ");
                        return iRet;
                    } else {
                        setjSonMeasurementData(strHelp1);
                        setJSonDataForTransfer(TT_MEASUREMENT_DATA_INSERT + getJSonMeasurementData());
                        setTaskUnderWork(ce);
                    }
                }
                if ((getWorkType().equals(TT_INTERSECTION_DATA_CHANGE)) || (getWorkType().equals(TT_CONTROLLER_DATA_CHANGE)) || (getWorkType().equals(TT_DETECTOR_DATA_CHANGE))) {
                    long lngWorkIndex = th.GetCurrentWorkTaskIdindex();
                    if (lngWorkIndex > 0) {
                        setCurrentWorkIndex(lngWorkIndex);
                    } else {
                        setCurrentWorkIndex(NO_WORK_INDEX);
                    }
                }
                if ((getWorkType().equals(TT_INTERSECTION_DATA_CHANGE)) || (getWorkType().equals(TT_CONTROLLER_DATA_CHANGE))) {
                    strHelp1 = th.getIntersectionControllerSqlData(ce.getIntersectionId(),
                                                                   ce.getControllerId(),
                                                                   ce.getPermanentDataTimestamp(),
                                                                   getWorkType());
                    logger.info("strHelp1 = " + strHelp1);
                    logger.info("strHelp1.length()  = " + strHelp1.length());
                    if (strHelp1.equals(NO_VALUE)) {
                        // RETHINK update task state and write to log
                        iRet = MakeClearanceOperations(ce);
                        logger.info("No JsonMeasurementData! ");
                        return iRet;
                    } else {
                        if (getWorkType().equals(TT_INTERSECTION_DATA_CHANGE)) {
                            setJSonDataForTransfer(TT_INTERSECTION_DATA_CHANGE + strHelp1);
                        } else {
                            setJSonDataForTransfer(TT_CONTROLLER_DATA_CHANGE+ strHelp1);
                        }
                        setTaskUnderWork(ce);
                    }
                }
                if ((getWorkType().equals(TT_DETECTOR_DATA_CHANGE))) {
                    strHelp1 = th.getDetectorSqlData(ce.getDetectorId(), ce.getPermanentDataTimestamp());
                    logger.info("strHelp1 = " + strHelp1);
                    logger.info("strHelp1.length()  = " + strHelp1.length());
                    if (strHelp1.equals(NO_VALUE)) {
                        // RETHINK update task state and write to log
                        iRet = MakeClearanceOperations(ce);
                        logger.info("No JsonMeasurementData! ");
                        return iRet;
                    } else {
                        setJSonDataForTransfer(TT_DETECTOR_DATA_CHANGE + strHelp1);
                        setTaskUnderWork(ce);
                    }
                }
                if (iRound >= 2) {
                    Thread.sleep(getSleep());
                    iRound = 0;
                }
            } catch (Exception e) {
                logger.info(ExceptionUtils.getRootCauseMessage(e));
                logger.info(ExceptionUtils.getFullStackTrace(e));
                e.printStackTrace();
                return OMNIA_DATA_PICK_NOT_OK;
            }
            return OMNIA_DATA_PICK_OK;
        } // polling loop while end
        return OMNIA_DATA_PICK_OK;
    }


    // *******************Make own class *****/
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
        } else if ((getWorkType().equals(TT_INTERSECTION_DATA_CHANGE)) || (getWorkType().equals(TT_CONTROLLER_DATA_CHANGE)) || (getWorkType().equals(TT_DETECTOR_DATA_CHANGE))) {
            doneWorkIndex = th.GetCurrentWorkTaskIdindex();
            if (doneWorkIndex > 0) {
                iRet = th.DeleteDoneTaskWorkUsingIdentityFromDb(doneWorkIndex);
                if (iRet < 0) {   // 0 they have all ready been deleted
                    logger.info("Unsuccessful delete from worktask doneWorkIndex =  " + doneWorkIndex);
                    logger.info("Unsuccessful delete from worktask ce.toString() =  " + ce.toString());
                    logger.info("Unsuccessful delete from worktask iRet = " + iRet);
                    return OMNIA_DATA_PICK_NOT_OK;
                }
                iRet = th.DeleteDoneTaskUsingIdentityFromDb(doneWorkIndex);
                if (iRet < 1) {
                    logger.info("Unsuccessful delete from Work table doneWorkIndex =  " + doneWorkIndex);
                    logger.info("Unsuccessful delete from Work table ce.toString() =  " + ce.toString());
                    logger.info("Unsuccessful delete from Work table iRet = " + iRet);
                    return OMNIA_DATA_PICK_NOT_OK;
                }
            } else {
                logger.info("No tasks/works to deleted doneWorkIndex = " + doneWorkIndex);
                return INT_RET_NOT_OK;
            }
        }
        return iRet;
    }
    //*******************************************************


    public String MakeMessageToBeSended() throws SQLException {
        String strHelp1 = NO_VALUE;
        String strHelp2 = NO_VALUE;
        TRPXMeasurementTaskData ce = getTaskUnderWork();
        if (getWorkType().equals(TT_NOT_DEFINED)) {
            strHelp1 = getJSonPermanentData();
            strHelp2 = getJSonMeasurementData();
            strHelp1 = strHelp1 + strHelp2;
            return strHelp1;
        }
        if ((getWorkType().equals(TT_INTERSECTION_DATA_CHANGE)) ||
           (getWorkType().equals(TT_CONTROLLER_DATA_CHANGE))) {
            strHelp1 = th.getIntersectionControllerSqlData(ce.getIntersectionId(),
                                                           ce.getControllerId(),
                                                           ce.getDetectorMeasuresTimestamp(),
                                                           getWorkType());
            return strHelp1;
        }
        if ((getWorkType().equals(TT_DETECTOR_DATA_CHANGE))) {
            strHelp1 = th.getDetectorSqlData(ce.getDetectorId(), ce.getDetectorMeasuresTimestamp());
            return strHelp1;
        }
        return strHelp1;
    }
}
