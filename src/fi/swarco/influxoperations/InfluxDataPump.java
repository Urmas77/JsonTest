package fi.swarco.influxoperations;
import fi.swarco.dataHandling.omniaClientDataHandling.SuperClientDataLevel;
import fi.swarco.dataHandling.omniaClientDataHandling.TimeSeriesClientDataLevel;
import fi.swarco.dataHandling.pojos.SuperData;
import fi.swarco.dataHandling.pojos.influx.InfluxSqlToTimeSerie;
import fi.swarco.serviceOperations.SwarcoTimeUtilities;
import org.apache.log4j.Logger;
import java.util.List;
import static fi.swarco.CONSTANT.*;
import static fi.swarco.SwarcoEnumerations.ConnectionType.INFLUX_LOCAL;
import static fi.swarco.SwarcoEnumerations.ConnectionType.SQLSERVER_LOCAL_JOMNIATEST;
public class InfluxDataPump {
    static Logger logger = Logger.getLogger(InfluxDataPump.class.getName());
    public static void main(String[] args) throws Exception {
        TimeSeriesClientDataLevel it = new TimeSeriesClientDataLevel();
       int iRet = it.MakeConnection(SQLSERVER_LOCAL_JOMNIATEST,INFLUX_LOCAL);
       if (iRet!=DATABASE_CONNECTION_OK) {
           logger.info ("unsuccessful db connets iRet = "+ iRet);
           System.exit(1);
       }
// read detector names to table
// made this for loop later
        SuperClientDataLevel su = new SuperClientDataLevel();
         iRet =su.MakeConnection(SQLSERVER_LOCAL_JOMNIATEST);
         if (iRet!=DATABASE_CONNECTION_OK) {
             logger.info ("unsuccessful db connets iRet = "+ iRet);
             System.exit(1);
         }
         iRet= su.OmniaSuperDataList();
        if (iRet!=INT_RET_OK) {
            logger.info ("error in Detectorlist searcH iRet = "+ iRet);
            System.exit(1);
        }
        List<SuperData> DetectorList= su.GetOmniaSuperDataList();
        List<InfluxSqlToTimeSerie> TimeSerieList;
        SuperData sData;
        InfluxSqlToTimeSerie serieData;
        long detectorId;
        String startTime;
        SwarcoTimeUtilities tu = new SwarcoTimeUtilities();
        logger.info("DetectorList.size()= " + DetectorList.size());
        for (int i = 0; i < DetectorList.size(); i++) {
            sData = DetectorList.get(i);
            logger.info("sData.toString() =" + sData.toString());
            if (sData.getDetectorId() != NO_DETECTOR_ID) {
                logger.info("löytyi sData.getDetectorId =" + sData.getDetectorId());
                detectorId = sData.getDetectorId();
                iRet = INT_RET_OK;
                // repeat until all lines has been transferred
                startTime = UNIX_START_TIME;
                while (!(startTime.equals(SWARCO_END_TIME))) {
                    startTime = it.GetNextUnhandledMeasurement(detectorId, startTime);
                    if (!(startTime.equals(SQL_SERVER_NULL_TIME))) {
                        startTime =tu.ToSwarcoTime(startTime);
                        logger.info("startTime = "+ startTime);
                        startTime = it.FillDetectorMeasurementsList(101, 1000, startTime);
                        if (startTime.equals(RET_NOT_OK)) {
                            logger.info("Error in FillDetectorlist functionality startTime = " + startTime);
                            System.exit(1);
                        }
                        iRet =it.WriteDetectorMeasurementsListToInflux();
                        if (iRet!=INT_RET_OK) {
                            logger.info("Influx write error iRet = " + iRet);
                            System.exit(1);
                        }
                    }
                }
            }
        }
    }
}