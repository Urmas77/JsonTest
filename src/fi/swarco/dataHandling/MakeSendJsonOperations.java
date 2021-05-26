package fi.swarco.dataHandling;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.dataHandling.pojos.TRPXMeasurementTaskData;
import fi.swarco.omniaDataTransferServices.LogUtilities;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import java.sql.SQLException;
import static fi.swarco.CONSTANT.*;
import static fi.swarco.omniaDataTransferServices.omniaClient.OmniaClient.*;

public class MakeSendJsonOperations {
    static Logger logger = Logger.getLogger(MakeSendJsonOperations.class.getName());
    private static String jSonPermanentData = "novalue";
    private static String getJSonPermanentData() {
        return jSonPermanentData;
    }
    private static void setjSonPermanentData(String pJSonPermanentData) {
        jSonPermanentData = pJSonPermanentData;
    }
    private long currentOmniviewFirstRound= 0;
    public long getCurrentOmniviewFirstRound() {
        return currentOmniviewFirstRound;
    }
    public void setCurrentOmniviewFirstRound(long pCurrentOmniviewFirstRound) {
        currentOmniviewFirstRound=pCurrentOmniviewFirstRound;
    }
   private long currentWorkIndex = 0;
    public long getCurrentWorkIndex() {
        return currentWorkIndex;
    }
    public void setCurrentWorkIndex(long pCurrentWorkIndex) {
        currentWorkIndex = pCurrentWorkIndex;
    }
    private static int intSleep = 200;
    public static int getSleep() {
        return intSleep;
    }
    public static void setSleep(int pSleep) {
        intSleep = pSleep;
    }
   //-----------------------------------------------------------------------------------
    private static String jSonControllerStatus = "novalue";
    private static String getJSonControllerStatusData() {
        return jSonControllerStatus;
    }
    private static void setJSonControllerStatusData(String pJSonControllerStatus) {
        jSonControllerStatus = pJSonControllerStatus;
    }
    //------------------------------------------------------------------------
    private static String jSonMeasurementData = "novalue";
    private static String getJSonMeasurementData() {
        return jSonMeasurementData;
    }
    private static void setJSonMeasurementData(String pJSonMeasurementData) {
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
    public int PollOfWorks()  {
        // check is ther work is if it is transfer them to work queue
        int iloop = 1;
        int intSleep = 0;
        int iRet;
        SwarcoEnumerations.ConnectionType oConnType;
        oConnType = getSqlServerConnectionType() ;
        iRet = th.MakeConnection(oConnType);
        if (iRet != INT_RET_OK) {
            logger.info("Ei kantayhteyttä lopetetaan");
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
                 if ((th.AnyWorkMeasurementsNew1() == 0)  && (th.AnyWorkMeasurementsNew2() == 0)) {
                    Thread.sleep(getSleep());   // 5 seconds sleep
                } else{
                    iRet = th.GetNextTimestampToTransfer(0);
                    if (iRet == INT_RET_OK) {
// check that there is work
                        if (th.AnyWorkWork() == 0) {
                            iRet = th.GetFirstDebtPaymentTimestampToTransfer();
                            if (iRet == INT_RET_OK) {
                                if (th.AnyWorkWork() == 0) {   // get more work if it is possible
                                    iRet = th.HourlyUpdateTransferredLines();
                                    if (iRet == INT_RET_OK) {
                                        if (th.AnyWorkWork() == 0) {
                                            iRet = th.GetFirstDebtPaymentTimestampToTransfer();
                                            if (th.AnyWorkWork() == 0) {
                                                Thread.sleep(getSleep());
                                                return THERE_IS_NO_WORK;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        setWorkType(TT_MEASUREMENT_DATA_INSERT);
                        iRet = th.FillUpTasks();
                        if (iRet != FILL_UP_TASK_OK) {
                            logger.info("Fill UpTask error iRet = " + iRet);
                            return iRet;
                        }
                        iRet = th.DeleteNotFilledTasks();
                        if (iRet != DELETE_UNFILLABLE_TASK_OK) {
                            logger.info("DeleteNotFilledTasks error iRet = " + iRet);
                            return iRet;
                        }
                     long iMaxDetectors=getMaxDetectorsOnControllerGroups();
                        iRet =th.CreateControllerGroups3(iMaxDetectors);
                        if (iRet!=INT_RET_OK)  {
                            logger.info("Impossible to create Controller groups iRet =" + iRet);
                            return iRet;
                        }
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
    public int PollOfWorksOmniview()  {
        // check is there work is if it is transfer them to work queue
        int iloop = 1;
        int intSleep = 0;
        int iRet;
        SwarcoEnumerations.ConnectionType oConnType;
        oConnType = getSqlServerConnectionType() ;
        iRet = th.MakeConnection(oConnType);
        if (iRet != INT_RET_OK) {
            logger.info("Ei kantayhteyttä lopetetaan");
            return OMNIA_DATA_PICK_NOT_OK;
        }
        try {
            iRet = th.AnyWorkWork();
            if (iRet > 0) {
                return THERE_IS_WORK;
            }
            while (iloop == 1) {
// this is wrong here rethink 9.4 2021 klo 16:15
                iRet = th.CreateOmniviewTypeMeasurementsWorks();
                if (iRet != INT_RET_OK) {
                    return iRet;
                }
                setCurrentOmniviewFirstRound(OMNIVIEW_FIRST_ROUND_YES);
                iRet =th.AnyWorkWork();
                if (iRet ==0) {
                    iRet = th.CreateOmniviewTypeControllerStatusWorks();
                    if (iRet != INT_RET_OK) {
                        return iRet;
                    }
                }
               // setCurrentOmniviewFirstRound(OMNIVIEW_FIRST_ROUND_YES);
                // test end
               // */
            // only permanent works
                iRet =th.AnyWorkWork();
                if (iRet ==0) {
                    iRet = th.CreateNeededPermanentDataWorks();
                    if (iRet != INT_RET_OK) {
                        return iRet;
                    }
                }
                if (th.AnyWorkIntersectionOmniview() > 0) {
                    return THERE_IS_WORK;
                }
                if (th.AnyWorkControllerOmniview() > 0) {
                    return THERE_IS_WORK;
                }
                if (th.AnyWorkDetectorOmniview() > 0) {
                    return THERE_IS_WORK;
                }
                if (th.AnyWorkControllerStatusOmniview() >0) {
                    return THERE_IS_WORK;
                }
                if (th.AnyWorkMeasurementsOmniview() == 0) {
                    Thread.sleep(getSleep());   // 5 seconds sleep
                }
                iRet = th.AnyWorkWork();
                if (iRet == 0) {
                    return THERE_IS_NO_WORK;
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
    public int MakeSendOmniviewOperations() throws SQLException {
        LogUtilities mfl = new LogUtilities();
        SwarcoEnumerations.ConnectionType  oConnType;
        oConnType=getSqlServerConnectionType();
        int iRet = th.MakeConnection(oConnType);
        if (iRet != INT_RET_OK) {
            logger.info("Ei kantayhteyttä lopetetaan");
            return OMNIA_DATA_PICK_NOT_OK;
        }
        TRPXMeasurementTaskData ce = null;
        int iRound = 0;
        int iloop = 1;
        int iRet2 = 0;
        String strHelp1 = NO_VALUE;
        while (iloop == 1) {
            try {
 // RETHINK HERE ************************************** is this enough
                // OmniaView/APITran  create works here  if there are no works
                iRet =th.AnyWorkWork();
           //   test start ??????????????????????????????? RETHINK
                if (iRet==0) {
                    if (getCurrentOmniviewFirstRound() == OMNIVIEW_FIRST_ROUND_YES) {
                        setCurrentOmniviewFirstRound(OMNIVIEW_FIRST_ROUND_NO);
                    } else {
                        iRet = th.CreateOmniviewTypeMeasurementsWorks();
                    }
                    if (iRet != INT_RET_OK) {
                        return iRet;
                    }
                    iRet = th.MeasurementTaskDataList();
                    if (iRet != INT_RET_OK) {
                        return OMNIA_DATA_PICK_NOT_OK;
                    }
                }
                // there is no list on omniview side build it here
                // so get on first undone work
                iRet =th.CountOmniviewWorkTypes();
                if (iRet !=0) {
                    if (iRet>1) {
                        logger.info("Too many work types  amoumt of types (iRet) :  "+ iRet);
                        return INT_RET_NOT_OK;
                    }
                    return iRet;
                }
                String strRet = th.GetFirstUndoneOmniviewTaskWorktype();   // -Work table
                if (strRet.equals(TT_NOT_DEFINED)) {
                    logger.info("Empty task list wait 1 s ");
                    iRound = iRound + 1;
                    return OMNIA_EMPTY_WORK_LIST;   // HERE HERE
                } else if (strRet.equals(TT_TOO_MANY_WORKTYPES)) {
                    logger.info("TT_TOO_MANY_WORKTYPES error  ");
                    iRound = iRound + 1;
                    return OMNIA_EMPTY_WORK_LIST;   // HERE HERE
                } else {
                    setWorkType(strRet);
               }
               if (getWorkType().equals(TT_MEASUREMENT_DATA_INSERT)) {
                   MeasurementTaskWorkHandling ms = new MeasurementTaskWorkHandling();
                   oConnType = getSqlServerConnectionType();
                   iRet =ms.MakeConnection(oConnType);
                   if (iRet != INT_RET_OK) {
                       logger.info("Ei kantayhteyttä lopetetaan");
                       return iRet;
                   }
                       String strHelp2= th.GetCurrentWorkTimestamp();
                       if (strHelp2.equals("1970-01-11 00:00:00")) {
                          logger.info("Illegal timestamp strHelp2 = " +strHelp2 );
                          return INT_RET_NOT_OK;
                       }
                       strHelp1 = th.GetMeasurementShortSqlDataGroup(strHelp2);
                   if (strHelp1.equals(NO_VALUE)) {
                       // check here  are there any orphan lines left

                       iRet = MakeClearanceOperations(ce);  // RETHINK JIs 19.05 2021
                       logger.info("No JsonMeasurementData! 1111 ");
                       return iRet;
                   } else {
                       setJSonMeasurementData(strHelp1);
                       setJSonDataForTransfer(TT_MEASUREMENT_DATA_INSERT + getJSonMeasurementData());
                       setTaskUnderWork(ce);
                   }
               }
//***********************************************************************************
                if (getWorkType().equals(TT_CONTROLLER_STATUS_DATA_INSERT)) {
                  // RETHINK 12.4 2021
                    MeasurementTaskWorkHandling ms = new MeasurementTaskWorkHandling();
                    oConnType = getSqlServerConnectionType();
                    iRet =ms.MakeConnection(oConnType);
                    if (iRet != INT_RET_OK) {
                        logger.info("Ei kantayhteyttä lopetetaan");
                        return iRet;
                    }
                    String strHelp2= th.GetCurrentWorkTimestamp();
                    if (strHelp2.equals("1970-01-11 00:00:00")) {
                        logger.info("Illegal timestamp strHelp2 = " +strHelp2 );
                        return INT_RET_NOT_OK;
                    }
                    strHelp1 =th.GetControllerStatusSqlDataGroup(strHelp2);
                    setJSonControllerStatusData(strHelp1);
                    setJSonDataForTransfer(TT_CONTROLLER_STATUS_DATA_INSERT + getJSonControllerStatusData());
                    setTaskUnderWork(ce);
                }
                //**********************************************************
               if ((getWorkType().equals(TT_INTERSECTION_DATA_CHANGE)) || (getWorkType().equals(TT_CONTROLLER_DATA_CHANGE)) || (getWorkType().equals(TT_DETECTOR_DATA_CHANGE))) {
                   long lngWorkIndex = th.GetCurrentWorkTaskIdindex();
                   if (lngWorkIndex > 0) {
                       setCurrentWorkIndex(lngWorkIndex);
                   } else {
                       setCurrentWorkIndex(NO_WORK_INDEX);
                   }
               }
               if ((getWorkType().equals(TT_INTERSECTION_DATA_CHANGE)) || (getWorkType().equals(TT_CONTROLLER_DATA_CHANGE))) {
                   iRet= th.MeasurementTaskDataList();
                   if (iRet!= INT_RET_OK) {
                       if  (iRet== NO_TASK_LIST) {
                           logger.info(" No task list found iRet = "+ iRet);
                           return iRet;
                       }
                       return iRet;
                   }
                   ce = th.GetFirstUndoneTaskFromList();
                   strHelp1 = th.GetIntersectionControllerSqlDataForOmniview(ce.getIntersectionId(),
                              ce.getControllerId(),
                              ce.getPermanentDataTimestamp(),
                              getWorkType());
                   if (strHelp1.equals(NO_VALUE)) {
                        // RETHINK update task state and write to log
                       iRet = MakeClearanceOperations(ce);
                       logger.info("No JsonMeasurementData! 2222 ");
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
                   iRet= th.MeasurementTaskDataList();
                   if (iRet!= INT_RET_OK) {
                       if  (iRet== NO_TASK_LIST) {
                           logger.info(" No task list found iRet = "+ iRet);
                           return iRet;
                       }
                       return iRet;
                   }
                   ce = th.GetFirstUndoneTaskFromList();
                   strHelp1 = th.GetDetectorOmniviewSqlData(ce.getControllerId(),ce.getDetectorId(),ce.getPermanentDataTimestamp());
                   if (strHelp1.equals(NO_VALUE)) {
                        // RETHINK update task state and write to log
                       iRet = MakeClearanceOperations(ce);
                       logger.info("No JsonMeasurementData! 3333 ");
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
    private int  WorkTaskExecuteSelector() {
       int iRet=INT_RET_OK;
       int iCount;
       int iSomethingSelected=NO_SELECTION_DONE;
       long iPreviousMode;
       long iRunningMode = getDataTransferTaskSelectionMode();
       long iMinLines = getClientCriticalAmountOfTransferTaskLines();
       try {
           iSomethingSelected=NO_SELECTION_DONE;
           logger.info("iRunningMode = "+iRunningMode);
            if (iRunningMode == TASK_SELECTION_MODE_NOT_DEFINED) {
                logger.info("inside iRunningMode = "+iRunningMode);
                iSomethingSelected=SELECTION_DONE;
                iRet = th.TransferMeasurementTasksToWorkQueue(); // youngest
               return iRet;
           }
           if (iRunningMode == TASK_SELECTION_MODE_OLDEST) {
               logger.info("inside iRunningMode = "+iRunningMode);
               iSomethingSelected=SELECTION_DONE;
               iRet = th.TransferMeasurementTasksToWorkQueueOldest();
                   return iRet;
           }
           if (iRunningMode == TASK_SELECTION_MODE_YOUNGEST) {
               logger.info("inside iRunningMode = "+iRunningMode);
               iSomethingSelected=SELECTION_DONE;
               iRet = th.TransferMeasurementTasksToWorkQueue();
                   return iRet;
           }
           if (iRunningMode == TASK_SELECTION_MODE_YOUNGEST_WITH_LINE_LIMIT) {
               logger.info("inside iRunningMode ="+iRunningMode);
               iSomethingSelected=SELECTION_DONE;
               iCount = th.TransferMeasurementTasksCountYoungest(); // that have enough lines
              if (iCount >= iMinLines) {
                 iRet = th.TransferMeasurementTasksToWorkQueue();
                 return iRet;
              } else {
                 return TOO_FEW_LINES_TO_TRANSFER;
              }
           }
           if (iRunningMode == TASK_SELECTION_MODE_OLDEST_WITH_LINE_LIMIT) {
               logger.info("inside iRunningMode = "+iRunningMode);
               iSomethingSelected=SELECTION_DONE;
               iCount = th.TransferMeasurementTasksCountOldest();  // that have enough lines
               logger.info("iCount = "  + iCount);
              logger.info("iMinLines = "  +iMinLines);
               if (iCount >= iMinLines) {
                 iRet = th.TransferMeasurementTasksToWorkQueueOldest();
                 return iRet;
              } else {
                 return TOO_FEW_LINES_TO_TRANSFER;
              }
           }
           if (iRunningMode == TASK_SELECTION_MODE_MIXTURE_WITH_LINE_LIMIT_01) {
               logger.info("inside iRunningMode = "+iRunningMode);
               iSomethingSelected=SELECTION_DONE;
               iPreviousMode = getTaskSelectionModePrevious();
               logger.info(" iPreviousMode = "+iPreviousMode);
               if (iPreviousMode == TASK_SELECTION_MODE_YOUNGEST_WITH_LINE_LIMIT) {
                   logger.info("inside iRunningMode = "+iRunningMode + " iPreviousMode = "+iPreviousMode);
                   setTaskSelectionModePrevious(TASK_SELECTION_MODE_OLDEST_WITH_LINE_LIMIT);
                   iCount = th.TransferMeasurementTasksCountYoungest(); // that have enough lines
                   if (iCount >= iMinLines) {
                       iRet = th.TransferMeasurementTasksToWorkQueue();
                       return iRet;
                   } else {
                       return TOO_FEW_LINES_TO_TRANSFER;
                   }
               }
               if (iPreviousMode == TASK_SELECTION_MODE_OLDEST_WITH_LINE_LIMIT) {
                   logger.info("inside iRunningMode = "+iRunningMode);
                   iSomethingSelected=SELECTION_DONE;
                   setTaskSelectionModePrevious(TASK_SELECTION_MODE_YOUNGEST_WITH_LINE_LIMIT);
                   logger.info("inside iRunningMode = "+iRunningMode + " iPreviousMode = "+iPreviousMode);
                   iCount = th.TransferMeasurementTasksCountOldest();  // that have enough lines
                   logger.info("inside iRunningMode = "+iRunningMode +  " iCount = " + iCount);
                   if (iCount >= iMinLines) {
                       iRet = th.TransferMeasurementTasksToWorkQueueOldest();
                       return iRet;
                   } else {
                       return TOO_FEW_LINES_TO_TRANSFER;
                   }
               }
               // no init value
               logger.info("inside iRunningMode = "+iRunningMode + " no init value");
               setTaskSelectionModePrevious(TASK_SELECTION_MODE_YOUNGEST_WITH_LINE_LIMIT);
               return iRet;
           }
           if (iRunningMode == TASK_SELECTION_MODE_MIXTURE_01) {
               logger.info("inside iRunningMode = "+iRunningMode);
               iSomethingSelected=SELECTION_DONE;
               iPreviousMode = getTaskSelectionModePrevious();
               if (iPreviousMode == TASK_SELECTION_MODE_YOUNGEST) {
                   iRet = th.TransferMeasurementTasksToWorkQueue();
                   setTaskSelectionModePrevious(TASK_SELECTION_MODE_OLDEST);
                   return iRet;
               }
               if (iPreviousMode == TASK_SELECTION_MODE_OLDEST) {
                   iRet = th.TransferMeasurementTasksToWorkQueueOldest();
                   setTaskSelectionModePrevious(TASK_SELECTION_MODE_YOUNGEST);
                   return iRet;
               }
               // no init value
               logger.info("inside iRunningMode = "+iRunningMode + " No init mode");
               setTaskSelectionModePrevious(TASK_SELECTION_MODE_YOUNGEST);
               return NO_INT_VALUE_FOR_TO_TRANSFER;
           }
           // nothing found so
           if (iSomethingSelected==NO_SELECTION_DONE) {
               logger.info("No selection done?");
               logger.info("inside iRunningMode = "+iRunningMode + " No selection done");
               iRet = th.TransferMeasurementTasksToWorkQueue(); // youngest
               return iRet;
           }
           return  IMPOSSIBLE_SELECTOR_RESULT;
       } catch (Exception e) {
         logger.info(ExceptionUtils.getRootCauseMessage(e));
         logger.info(ExceptionUtils.getFullStackTrace(e));
         e.printStackTrace();
         return OMNIA_DATA_PICK_NOT_OK;
       }
    }
   public int MakeSendOmniaOperations() throws SQLException {
        LogUtilities mfl = new LogUtilities();
        SwarcoEnumerations.ConnectionType  oConnType;
        oConnType=getSqlServerConnectionType();
        int iRet = th.MakeConnection(oConnType);
        if (iRet != INT_RET_OK) {
            logger.info("Ei kantayhteyttä lopetetaan");
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
                        iRet2 = th.DeleteTrashTasksAfterHand();
                        iRet2 = th.DeleteTrashTasksAfterHandFromTaskList();
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
// update first group to handling
                    MeasurementTaskWorkHandling ms = new MeasurementTaskWorkHandling();
                    oConnType = getSqlServerConnectionType();
                    iRet =ms.MakeConnection(oConnType);
                    if (iRet != INT_RET_OK) {
                        logger.info("Ei kantayhteyttä lopetetaan");
                        return iRet;
                    }
                    iRet =ms.DefineFirstUnhandledGroup();
                    if (iRet!=INT_RET_OK)  {
                        logger.info("Impossible to find first unhandled group iRet =" + iRet);
                        return iRet;
                    }
// get first group on use
                    strHelp1 = th.GetMeasurementShortSqlDataGroup(ce.getDetectorMeasuresTimestamp());
                    if (strHelp1.equals(NO_VALUE)) {
                        iRet = MakeClearanceOperations(ce);
                        logger.info("No JsonMeasurementData here here 4444 prop this! ");
                        return iRet;
                    } else {
                        setJSonMeasurementData(strHelp1);
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
                    strHelp1 = th.GetIntersectionControllerSqlData(ce.getIntersectionId(),
                                                                   ce.getControllerId(),
                                                                   ce.getPermanentDataTimestamp(),
                                                                   getWorkType());
                    if (strHelp1.equals(NO_VALUE)) {
                        // RETHINK update task state and write to log
                        iRet = MakeClearanceOperations(ce);
                        logger.info("No JsonMeasurementData! 5555");
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
                    strHelp1 = th.GetDetectorSqlData(ce.getDetectorId(),ce.getPermanentDataTimestamp());
                   //logger.info("strHelp1 = " + strHelp1);
                   // logger.info("strHelp1.length()  = " + strHelp1.length());
                    if (strHelp1.equals(NO_VALUE)) {
                        // RETHINK update task state and write to log
                        iRet = MakeClearanceOperations(ce);
                        logger.info("No JsonMeasurementData! 6666 ");
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
        long doneWorkIndex = 0;
        int iRet = INT_RET_OK;
        if (getWorkType().equals(TT_MEASUREMENT_DATA_INSERT)) {
            iRet = th.UpdateTransferredState();
            if (iRet < 1) {
                logger.info("Unsuccessful tansferred state update iRet = " + iRet);
                return OMNIA_DATA_PICK_NOT_OK;
            }
            iRet = th.DeleteDoneTaskFromGroupWorkDb();
            if (iRet < 1) {
                logger.info("Unsuccessful delete from Work table iRet = " + iRet);
                return OMNIA_DATA_PICK_NOT_OK;
            }
            iRet=th.MarkControllerGroupHandled();
            if (iRet < 1) {
                logger.info("Unsuccessful Controllergroup Handled operation iRet =  " + iRet);
                logger.info("Unsuccessful delete from Work table iRet = " + iRet);
                return OMNIA_DATA_PICK_NOT_OK;
            }
            return INT_RET_OK;
        } else if ((getWorkType().equals(TT_INTERSECTION_DATA_CHANGE)) || (getWorkType().equals(TT_CONTROLLER_DATA_CHANGE)) || (getWorkType().equals(TT_DETECTOR_DATA_CHANGE))) {
             TRPXMeasurementTaskData ce = getTaskUnderWork();
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
    public int DeleteDoneTaskFromWorkDbOmniview() throws SQLException {
        TRPXMeasurementTaskData ce = getTaskUnderWork();
        long doneWorkIndex = 0;
        int iRet = INT_RET_OK;
 //       logger.info ("1 bef MarkOmniviewMeasurementsHandled(); ");
 //       logger.info (" getWorkType() = " +getWorkType());
        if (getWorkType().equals(TT_MEASUREMENT_DATA_INSERT)) {
            iRet = th.MarkOmniviewMeasurementsHandled();
            if (iRet < 1) {
                logger.info("Unsuccessful update on Measurements table ce.toString() =  " + ce.toString());
                iRet = th.MarkOmniviewMeasurementsBackToBeingTransfer();
                if (iRet < 1) {
                    logger.info("Unsuccessful update meausrements table ce.toString() =  " + ce.toString());
                    return OMNIA_DATA_PICK_NOT_OK;
                }
            }
        }
        if (getWorkType().equals(TT_CONTROLLER_STATUS_DATA_INSERT)) {
           iRet = th.MarkOmniviewControllerStatusesHandled();
           if (iRet < 1) {
              logger.info("Unsuccessful update on controllerstatus table ce.toString() =  " + ce.toString());
              logger.info("2 aft MarkOmniviewMeasurementsHandled() iRet = " + iRet);
              iRet = th.MarkOmniviewControllerStatusBackToBeingTransfer();
              if (iRet < 1) {
                 logger.info("Unsuccessful update controllerstatus table ce.toString() =  " + ce.toString());
                 return OMNIA_DATA_PICK_NOT_OK;
              }
           }
    //      logger.info("3 aft MarkOmniviewMeasurementsBackToBeingTransfer() iRet = " + iRet);
       }
       if ((getWorkType().equals(TT_CONTROLLER_STATUS_DATA_INSERT)) || (getWorkType().equals(TT_MEASUREMENT_DATA_INSERT))) {
            iRet = th.DeleteDoneTaskFromOmniView();
            if (iRet < 1) {
                logger.info("Unsuccessful delete from Work table ce.toString() =  " + ce.toString());
                logger.info("Unsuccessful delete from Work table iRet = " + iRet);
                return OMNIA_DATA_PICK_NOT_OK;
            }
      //      logger.info("4 aft DeleteDoneTaskFromOmniView iRet = " + iRet);
            return INT_RET_OK;
        }
        if ((getWorkType().equals(TT_INTERSECTION_DATA_CHANGE)) || (getWorkType().equals(TT_CONTROLLER_DATA_CHANGE)) || (getWorkType().equals(TT_DETECTOR_DATA_CHANGE))) {
            doneWorkIndex = th.GetCurrentWorkTaskIdindex();
            if (doneWorkIndex > 0) {
                iRet = th.DeleteDonePermanentdataTaskFromDb();   // RETHINK workindex
                if (iRet < 0) {   // 0 they have all ready been deleted
                    logger.info("Unsuccessful delete from worktask doneWorkIndex =  " + doneWorkIndex);
                    logger.info("Unsuccessful delete from worktask iRet = " + iRet);
                    return OMNIA_DATA_PICK_NOT_OK;
                }
            }
            logger.info("No tasks/works to deleted doneWorkIndex = " + doneWorkIndex);
            return INT_RET_NOT_OK;
        }
        return iRet;
    }
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
            strHelp1 = th.GetIntersectionControllerSqlData(ce.getIntersectionId(),
                                                           ce.getControllerId(),
                                                           ce.getDetectorMeasuresTimestamp(),
                                                           getWorkType());
            return strHelp1;
        }
        if ((getWorkType().equals(TT_DETECTOR_DATA_CHANGE))) {
            strHelp1 = th.GetDetectorSqlData(ce.getDetectorId(), ce.getDetectorMeasuresTimestamp());
            return strHelp1;
        }
        return strHelp1;
    }
}
