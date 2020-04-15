package fi.swarco.influxoperations;
import fi.swarco.dataHandling.omniaClientDataHandling.SuperClientDataLevel;
import fi.swarco.dataHandling.omniaClientDataHandling.TimeSeriesClientDataLevel;
import fi.swarco.dataHandling.pojos.SuperData;
import fi.swarco.dataHandling.pojos.influx.InfluxSqlToTimeSerie;
import fi.swarco.serviceOperations.SwarcoTimeUtilities;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import java.util.List;
import static fi.swarco.CONSTANT.*;
import static fi.swarco.SwarcoEnumerations.ConnectionType.INFLUX_LOCAL;
import static fi.swarco.SwarcoEnumerations.ConnectionType.SQLSERVER_LOCAL_JOMNIATEST;
public class InfluxDataPump {
    static Logger logger = Logger.getLogger(InfluxDataPump.class.getName());
    public static void main(String[] args) {
        TimeSeriesClientDataLevel it = new TimeSeriesClientDataLevel();
        try {
            int iRet = it.MakeConnection(SQLSERVER_LOCAL_JOMNIATEST, INFLUX_LOCAL);
            if (iRet != DATABASE_CONNECTION_OK) {
                logger.info("unsuccessful db connets iRet = " + iRet);
                System.exit(1);
            }
// read detector names to table
// made this for loop later
            SuperClientDataLevel su = new SuperClientDataLevel();
            iRet = su.MakeConnection(SQLSERVER_LOCAL_JOMNIATEST);
            if (iRet != DATABASE_CONNECTION_OK) {
                logger.info("unsuccessful db connets iRet = " + iRet);
                System.exit(1);
            }                                             //463 JIs 14.01.2020 klo 13:45
            iRet = su.MakeOmniaSuperDataListGEDetectorId(1129);    //1181 was latest
            //iRet = su.OmniaSuperDataList();
            if (iRet != INT_RET_OK) {
                logger.info("error in Detectorlist searcH iRet = " + iRet);
                System.exit(1);
            }
            List<SuperData> DetectorList = su.GetOmniaSuperDataList();
            List<InfluxSqlToTimeSerie> TimeSerieList;
            SuperData sData;
            InfluxSqlToTimeSerie serieData;
            long detectorId;
            String startTime;
            String prevStartTime;
            SwarcoTimeUtilities tu = new SwarcoTimeUtilities();
            //logger.info("DetectorList.size()= " + DetectorList.size());
         //   for (int i = 0; i < 3; i++) {
            for (int i = 0; i < DetectorList.size(); i++) {
                sData = DetectorList.get(i);
                logger.info("sData.toString() =" + sData.toString());
                if (sData.getDetectorId() != NO_DETECTOR_ID) {
                    logger.info("löytyi sData.getDetectorId =" + sData.getDetectorId());
                    detectorId = sData.getDetectorId();
                    iRet = INT_RET_OK;
                    // repeat until all lines has been transferred
                    startTime = UNIX_START_TIME;
                    prevStartTime = UNIX_START_TIME;
                    Boolean bInLoop = true;
                    while (bInLoop) {
                        logger.info("*******before GN Current detectorId = " + detectorId);
                        logger.info("*******before GN Current startTime = " + startTime);
                        logger.info("*******before GN Current prevStartTime = " + prevStartTime);
                        prevStartTime = startTime;
                        startTime = it.GetNextUnhandledMeasurement(detectorId, startTime);
                        if (prevStartTime.equals(startTime)) {
                            logger.info("startTime JOB_DONE");
                            startTime = JOB_DONE;
                        }
                        logger.info("*****after Current detectorId = " + detectorId);
                        logger.info("*****after GN Current startTime = " + startTime);
                        logger.info("*****after GN Current prevStartTime = " + prevStartTime);
                        //   logger.info(" JOB_DONE = " + JOB_DONE);
                        logger.info(" SQL_SERVER_NULL_TIME = " + SQL_SERVER_NULL_TIME);
                        if (!(startTime.equals(SQL_SERVER_NULL_TIME))) {
                            if (!(startTime.equals(JOB_DONE))) {
                                startTime = tu.ToSwarcoTime(startTime);
                                if (!(startTime.equals(SQL_SERVER_NULL_TIME))) {
                                    logger.info("inside id detectorId = " + detectorId);
                                    logger.info("inside id startTime = " + startTime);
                                    //  startTime = it.FillDetectorMeasurementsList(detectorId, 50000, startTime);
                                    startTime = it.FillDetectorMeasurementsStringList(detectorId, 50000, startTime);
                                    if (startTime.equals(RET_NOT_OK)) {
                                        logger.info("Error in FillDetectorlist functionality startTime = " + startTime);
                                        System.exit(1);
                                    }
                                    //  iRet = it.WriteDetectorMeasurementsListToInflux();
                                    //iRet = it.WriteDetectorMeasurementsStringListToInflux();
                                    iRet = it.WriteDetectorMeasurementsStringListToInflux2();
                                    if (iRet != INT_RET_OK) {
                                       logger.info("Influx write error iRet = " + iRet);
                                       System.exit(1);
                                    }
                                } else {
                                    logger.info("outloop1");
                                    bInLoop = false;
                                }
                            } else {
                                logger.info("outloop2  detectorId ="+  detectorId);
                                bInLoop = false;
                            }
                        } else {
                            logger.info("outloop3  detectorId ="+  detectorId);
                            bInLoop = false;
                        }
                        logger.info("inloop1  detectorId ="+  detectorId);
                    }   // RETHINK put loop ends here JIs 08.01.2020
                }
            }
        } catch (Exception e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            e.printStackTrace();
        }
    }
}