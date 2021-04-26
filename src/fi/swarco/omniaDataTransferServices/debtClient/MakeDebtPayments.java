package fi.swarco.omniaDataTransferServices.debtClient;
import fi.swarco.dataHandling.omniaClientDataHandling.DetectorMeasurementsShortClientDataLevel;
import fi.swarco.dataHandling.omniaClientDataHandling.OmniaMeasurementShortListDataLevel;
import fi.swarco.dataHandling.pojos.OmniaMeasurementDataShort;
import fi.swarco.dataHandling.pojos.TRPXMeasurementTaskData;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import java.sql.SQLException;
import java.util.List;
import static fi.swarco.CONSTANT.*;
import static fi.swarco.CONSTANT.INT_RET_OK;
import static fi.swarco.omniaDataTransferServices.omniaClient.OmniaClient.getSqlServerConnectionType;

public class MakeDebtPayments {
    private static Logger logger = Logger.getLogger(MakeDebtPayments.class.getName());
    // 1. get oldest timestamp amount

    // 1.1 select MIN(DetectorMeasuresTimestamp) as det from TRPX_MeasurementTask_Storage
    // 1.2 transfer data to to table TRPX_MeasurementTask_Storage_work
    //   MeasurementStorageTaskWorkHandling mt = new MeasurementStorageTaskWorkHandling();

    public int MakeDebtPayments1() {
        MeasurementStorageTaskHandling mt = new MeasurementStorageTaskHandling();
        int iRet;
        try {
            // 1.2 transfer data to to table TRPX_MeasurementTask_Storage_work
            iRet = mt.TransferMeasurementTasksToWorkQueue();
            if (iRet!=INT_RET_OK) {
                logger.info("iRet = " + iRet);
                System.exit(0);
            }
            // 1.3  Fill task  correctly
            iRet = mt.FillUpTasks();
            if (iRet!=INT_RET_OK) {
                logger.info("iRet = " + iRet);
                System.exit(0);
            }
            // 2. transfer data

            iRet = mt.MeasurementTaskDataList();
            if (iRet!=INT_RET_OK) {
                logger.info("iRet = " + iRet);
                System.exit(0);
            }
            DetectorMeasurementsShortClientDataLevel dl = new DetectorMeasurementsShortClientDataLevel();
            List<OmniaMeasurementDataShort> listOfClient = dl.getListOfOmniaMeasurementDataShort();



            List<TRPXMeasurementTaskData> tl = mt.getMeasurementTaskData();
            OmniaMeasurementDataShort dtNew;
            OmniaMeasurementDataShort dtFound;
            OmniaMeasurementShortListDataLevel dlevel = new OmniaMeasurementShortListDataLevel();
// write here
            for (OmniaMeasurementDataShort item : listOfClient) {
                dtNew = new OmniaMeasurementDataShort();
                dtNew.setOmniaCode(item.getOmniaCode());
                dtNew.setIntersectionId(item.getIntersectionId());
                dtNew.setControllerId(item.getControllerId());
                dtNew.setMeasurementTime(item.getMeasurementTime());
                dtNew.setDetectorId(item.getDetectorId());
                dtNew.setDetectorExternalCode(item.getDetectorExternalCode());
                dtNew.setVehicleCount(item.getVehicleCount());
                dtNew.setMeanVehicleSpeed(item.getMeanVehicleSpeed());
                dtNew.setOccupancyProcent(item.getOccupancyProcent());
                dtNew.setAccurancy(item.getAccurancy());
  // make read before wrire
                iRet=dlevel.DoesLineAlreadyExist(dtNew);
                if  (iRet==INT_RET_FOUND) {
                    dtFound = dlevel.getFoundRec();
                    if (dtFound.getOmniaCode()!=INT_EMPTY_ELEMENT) {
                       iRet=dlevel.IsItChanged(dtNew,dtFound);
                       if (iRet==CHANGED) {
                           iRet=dlevel.MakeDeleteInsert(dtNew);
                           if (iRet!=INT_RET_OK) {
                               logger.info("iRet = " + iRet);
                               System.exit(0);
                           }
                       }
                    }
                }  else if (iRet==INT_RET_NOT_FOUND) {
                    iRet=dlevel.AddNewOmniaMeasurementDataShort(dtNew);
                    if (iRet!=INT_RET_OK) {
                        logger.info("iRet = " + iRet);
                        System.exit(0);
                    }
                }
           }

          //  iRet = mt.getMeasurementShortSqlData(1,1,)

            // 2.1 get data to sending pojos

            // 2.2 transfer data to receiving pojos
            // 2.3 write to MySql
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info("error e=", e);
            return INT_RET_NOT_OK;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            System.out.println(ExceptionUtils.getRootCauseMessage(e));
            System.out.println(ExceptionUtils.getFullStackTrace(e));
            return INT_RET_NOT_OK;
        }


        // 1.2 transfer data to to table TRPX_MeasurementTask_Storage_work
        // 1.3  Fill task  correctly

        // 2. transfer data
        // 2.1 get data to sending pojos
        // 2.2 transfer data to receiving pojos
        // 2.3 write to MySql

        // 3. delete from debt

        // 4. wait 100 ms

        // goto 1.
     return 1;
    }
}
