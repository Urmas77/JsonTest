package fi.swarco.omniaDataTransferServices.omniaClient;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.dataHandling.MeasurementTaskWorkHandling;
import fi.swarco.dataHandling.pojos.TRPXMeasurementTaskWorkData;
import org.apache.log4j.Logger;
import java.sql.SQLException;
import static fi.swarco.CONSTANT.*;
import static fi.swarco.CONSTANT.INT_RET_OK;
import static fi.swarco.omniaDataTransferServices.omniaClient.OmniaClient.getSqlServerConnectionType;

public class StartOmniaClient {
    private static Logger logger = Logger.getLogger(StartOmniaClient.class.getName());
    public  StartOmniaClient() {}
    public int ClearWorkTable() throws SQLException {
        StartWrapper stw = new StartWrapper();
        stw.MakeEmptyElement();
        MeasurementTaskWorkHandling mth =new MeasurementTaskWorkHandling();
        SwarcoEnumerations.ConnectionType  oConnType;
        oConnType=getSqlServerConnectionType();
        int iRet = mth.MakeConnection(oConnType);
        if (iRet != INT_RET_OK) {
            logger.info("Ei kantayhteytt� lopetetaan");
            return OMNIA_DATA_PICK_NOT_OK;
        }
//  Clean up Tasks
        iRet = mth.ExtraCleanUp();
        if (iRet==EXTRACLEANUP_DELETE_TASK_ERROR) {
            logger.info("ExtraCleanUpTask Error iRet = " + iRet);
        }
//  Are there tasks
        String strTaskType = mth.GetWorkTaskType();
        if (!(strTaskType.equals(TT_NOT_DEFINED))) {
// get first task data
            TRPXMeasurementTaskWorkData thh = mth.GetFirstWorkTask();
            if (thh.getWorkIdIndex() != NO_IDENTITY) {
// something to delete
                iRet = mth.DeleteTasksFromWorkDb();
                if (iRet == UNSUCCESSFUL_DATABASE_DELETE_OPERATION) {
                    logger.info("unsuccessfull delete iRet = " + iRet);
                } else {
// create tasks against using timestamp and worktype
                    iRet = ReCreateTasks(thh,strTaskType);
                    if (iRet<0) {
                        logger.info("Unsuccesfull Task creation operation strTaskType = " +strTaskType);
                        logger.info("Unsuccesfull Task creation operation thh.toString() = " +thh.toString());
                    }
                }
                return iRet;
            }
        }
        return iRet;
    }
    private int ReCreateTasks(TRPXMeasurementTaskWorkData pThh,String pTaskType) throws SQLException {
        MeasurementTaskWorkHandling mth = new MeasurementTaskWorkHandling();
        SwarcoEnumerations.ConnectionType  oConnType;
        oConnType=getSqlServerConnectionType();
        int iRet = mth.MakeConnection(oConnType);
        if (iRet != INT_RET_OK) {
           logger.info("Ei kantayhteytt� lopetetaan");
           return OMNIA_DATA_PICK_NOT_OK;
        }
        if (pTaskType.equals(TT_MEASUREMENT_DATA_INSERT)) {
            iRet = mth.ReCreateMeasurementsTasks(pThh);
            if (iRet<0) {
                logger.info("Unsuccesfull Measurements Task creation operation iret = "+ iRet);
                return iRet;
            }
            return INT_RET_OK;
       } else if (pTaskType.equals(TT_INTERSECTION_DATA_CHANGE)) {
            iRet = mth.ReCreateIntersectionTask(pThh);
            if (iRet<0) {
                logger.info("Unsuccesfull Intersection Task creation operation iret = "+ iRet);
                return iRet;
            }
           return INT_RET_OK;
       } else if (pTaskType.equals(TT_CONTROLLER_DATA_CHANGE)) {
            iRet = mth.ReCreateControllerTask(pThh);
            if (iRet<0) {
                logger.info("Unsuccesfull Controller Task creation operation iret = "+ iRet);
                return iRet;
            }
           return INT_RET_OK;
       } else if (pTaskType.equals(TT_DETECTOR_DATA_CHANGE)) {
            iRet = mth.ReCreateDetectorTask(pThh);
            if (iRet<0) {
                logger.info("Unsuccesfull Detector Task creation operation iret = "+ iRet);
                return iRet;
            }
            return INT_RET_OK;
       }
       return INT_RET_NOT_OK;
   }
}
