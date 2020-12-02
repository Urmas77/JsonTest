package fi.swarco.serviceOperations;
import fi.swarco.dataHandling.MeasurementTaskHandling;
import fi.swarco.dataHandling.pojos.TRPXMeasurementTaskData;
import fi.swarco.omniaDataTransferServices.omniaClient.GetOmniaData;
import org.apache.log4j.Logger;
import java.sql.SQLException;
import static fi.swarco.CONSTANT.*;
public class DeleteDoneTasks {
    private static MeasurementTaskHandling th = new MeasurementTaskHandling();
    private static TRPXMeasurementTaskData TaskUnderWork = null;
    public static TRPXMeasurementTaskData getTaskUnderWork() {
        return TaskUnderWork;
    }
    public static void setTaskUnderWork(TRPXMeasurementTaskData pTaskUnderWork) {
        TaskUnderWork = pTaskUnderWork;
        logger.info("TaskUnderWork.toString()=" + TaskUnderWork.toString());
    }
    private static String workType = TT_NO_WORK;
    public static String getWorkType() {
        return workType;
    }
    public static void setWorkType(String pWorkType) {
        workType = pWorkType;
    }
    private static Logger logger = Logger.getLogger(GetOmniaData.class.getName());
    // *******************Make own class *****/
    public int DeleteDoneTaskFromWorkDb2() throws SQLException {
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




}
