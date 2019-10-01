package fi.swarco.dataHandling;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.dataHandling.pojos.TRPXMeasurementTaskData;
import fi.swarco.omniaDataTransferServices.LogUtilities;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import static fi.swarco.CONSTANT.*;
public class MakeSendJsonOperations {
    static Logger logger = Logger.getLogger(MakeSendJsonOperations.class.getName());
    private static String jSonPermanentData = "novalue";
    public static String getJSonPermanentData() {
        return jSonPermanentData;
    }
    public static void setjSonPermanentData(String pJSonPermanentData) {
        jSonPermanentData = pJSonPermanentData;
    }
    private static String jSonMeasurementData = "novalue";
    public static String getJSonMeasurementData() {
        return jSonMeasurementData;
    }
    public static void setjSonMeasurementData(String pJSonMeasurementData) {
        jSonMeasurementData = pJSonMeasurementData;
    }
    private static TRPXMeasurementTaskData TaskUnderWork = null;
    public static TRPXMeasurementTaskData getTaskUnderWork() {
        return TaskUnderWork;
    }
    public static void setTaskUnderWork(TRPXMeasurementTaskData pTaskUnderWork) {
        TaskUnderWork = pTaskUnderWork;
    }
    private static MeasurementTaskHandling th = new MeasurementTaskHandling();
    private int MakeClearanceOperations(TRPXMeasurementTaskData pCe) {
        String strHelp1 = NO_VALUE;
        int iRet = INT_RET_NOT_OK;
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
    private int MakeSpareOperations(TRPXMeasurementTaskData pCe) {
        String strHelp1 = NO_VALUE;
        int iRet = INT_RET_NOT_OK;
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
    public int PollOfWorks() {
        // check is ther work is if it is transfer them to work queue
        int iloop = 1;
        int iRet = INT_RET_NOT_OK;
        iRet = MeasurementTaskHandling.MakeConnection(SwarcoEnumerations.ConnectionType.SQLSERVER_LOCAL_JOMNIATEST);
        if (iRet != INT_RET_OK) {
            logger.info("Ei kantayhteytt� lopetetaan");
            return OMNIA_DATA_PICK_NOT_OK;
        }
        try {
// check here is there still work left
            iRet =th.AnyWorkWork();
            if (iRet>0) {
                return THERE_IS_WORK;
            }
            while (iloop == 1) {
                if (th.AnyWork() == 0) {
                    Thread.sleep(5000);   // 5 seconds sleep
                } else {
                    // transfer tasks to work table
                   iRet = th.TransferTasksToWorkQueue();
                   if (iRet != TASK_TRANSFER_OK) {
                       logger.info("Task Transfer error iRet = " + iRet);
                       return iRet;
                   }
                    // update work from super
                    iRet = th.FillUpTasks();
                    if (iRet != FILL_UP_TASK_OK) {
                        logger.info("Fill UpTask error iRet = " + iRet);
                        return iRet;
                    }
                    // because Intersection or controller is not visible
                    iRet = th.DeleteNotFilledTasks();
                    if (iRet != DELETE_UNFILLABLE_TASK_OK) {
                        logger.info("Fill UpTask error iRet = " + iRet);
                        return iRet;
                    }
                    // delete tasks from task table
                    iRet=th.DeleteTasksFromDb();
                    if (iRet < 0) {
                        logger.info("Task Delete error iRet = " + iRet);
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
    public int MakeSendOmniaOperations() {
        LogUtilities mfl = new LogUtilities();
        int iRet = MeasurementTaskHandling.MakeConnection(SwarcoEnumerations.ConnectionType.SQLSERVER_LOCAL_JOMNIATEST);
        if (iRet != INT_RET_OK) {
            logger.info("Ei kantayhteytt� lopetetaan");
            return OMNIA_DATA_PICK_NOT_OK;
        }
        TRPXMeasurementTaskData ce = null;
        int iRound = 0;
        int iloop = 1;
        String strHelp1 = NO_VALUE;
        while (iloop == 1) {
            try {
                // do work   "old way"
                iRet = th.MeasurementTaskDataList();
                if (iRet != INT_RET_OK) {
                    if (iRet != NO_TASK_LIST) {
                        logger.info("Other error iRet = " + iRet);
                        return OMNIA_DATA_PICK_NOT_OK;
                    }
                }
                ce = th.GetFirstUndoneTaskFromList();
                if (ce.getMeasurementTaskIdindex() == EMPTY_ELEMENT) {
                    logger.info("Empty task list wait 1 s ");
                    iRound = iRound + 1;
                    return OMNIA_EMPTY_WORK_LIST;   // HERE HERE
                }
                strHelp1 = th.getPermanentSqlData(ce.getIntersectionId(), ce.getControllerId(), ce.getDetectorMeasuresTimestamp());
                logger.info("strHelp1 = " + strHelp1);
                logger.info("strHelp1.lenght()  = " + strHelp1.length());
                if (strHelp1.equals(NO_VALUE)) {
                    iRet = MakeSpareOperations(ce);
                    if (iRet < 0) {
                        logger.info("log write error  iRet = " + iRet);
                    }
                    logger.info("No JsonPermanentData ");
                    return OMNIA_DATA_PICK_NOT_OK;
                }
                setjSonPermanentData(strHelp1);
                //strHelp1 = th.getMeasurementSqlData(ce.getIntersectionId(), ce.getControllerId(), ce.getDetectorMeasuresTimestamp());
                strHelp1 = th.getMeasurementShortSqlData(ce.getIntersectionId(), ce.getControllerId(), ce.getDetectorMeasuresTimestamp());
                logger.info("strHelp1 = " + strHelp1);
                logger.info("strHelp1.length()  = " + strHelp1.length());
                if (strHelp1.equals(NO_VALUE)) {
                    // REHINK update task state and write to log
                    iRet = MakeClearanceOperations(ce);
                    logger.info("No JsonMeasurementData! ");
                    return iRet;
                } else {
                    setjSonMeasurementData(strHelp1);
                    setTaskUnderWork(ce);
                }
                if (iRound  >= 2) {
                    Thread.sleep(5000);
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
   public int   DeleteDoneTaskFromWorkDb() {
       TRPXMeasurementTaskData ce = getTaskUnderWork();
       int iRet = th.DeleteDoneTaskFromWorkDb(ce);
       if (iRet < 1) {
          logger.info("Unsuccesfull delete from tasklist ce.toString() =  " + ce.toString());
          logger.info("Unsuccesfull delete from tasklist iRet = " + iRet);
          return OMNIA_DATA_PICK_NOT_OK;
      }
      return INT_RET_OK;
   }
}

