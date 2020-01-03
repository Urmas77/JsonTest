package fi.swarco.dataHandling;
import fi.swarco.dataHandling.omniaClientDataHandling.OmniaMeasurementShortListDataLevel;
import fi.swarco.dataHandling.omniaServerDataHandling.IntersectionControllerListDatalevel;
import fi.swarco.dataHandling.omniaServerDataHandling.DetectorServerListDataLevel;
import fi.swarco.dataHandling.pojos.RawData;
import fi.swarco.omniaDataTransferServices.FileOperations;
import fi.swarco.omniaDataTransferServices.MessageUtils;
import fi.swarco.serviceOperations.SwarcoTimeUtilities;
import org.apache.log4j.Logger;
import java.sql.SQLException;
import static fi.swarco.CONSTANT.*;
import static fi.swarco.SwarcoEnumerations.ConnectionType.MYSQL_LOCAL_JATRI2;
public class MakeReceiveJsonOperations {
    static Logger logger = Logger.getLogger(MakeReceiveJsonOperations.class.getName());
    private static String pseudoJsonData = NO_VALUE;
    public static String getPseudoJSonData() {
       // logger.info("get jjjjjj  pseudoJsonData = " + pseudoJsonData);
        return pseudoJsonData;
    }
    public static void setPseudoJsonData(String pPseudoJsonData) {
        pseudoJsonData = pPseudoJsonData;
     //   logger.info("set jjjjjj  pseudoJsonData = " + pseudoJsonData);
    }
    private static String fullJsonData = NO_VALUE;
    //public static String getFullJsonData() {
    //    return fullJsonData;
   // }
   // public static void setpFullJsonData(String pFullJsonData) { fullJsonData = pFullJsonData; }
    private static String permanentJsonData = NO_VALUE;
    public static String getPermanentJsonData() {
        return permanentJsonData;
    }
    public static void setPermanentJsonData(String pPermanentJsonData) {
        permanentJsonData = pPermanentJsonData;
 //       logger.info("jjjjjj   permanentJsonData = " + permanentJsonData);
    }
    private static String measurementsJsonData = NO_VALUE;
    public static String getMeasurementsJsonData() {
        return measurementsJsonData;
    }
    public static void setMeasurementsJsonData(String pMeasurementsJsonData) { measurementsJsonData = pMeasurementsJsonData;}
    private static String messageType = NO_VALUE;
    public static String getMessageType() { return messageType;}
    public static void setMessageType(String pMessageType) { messageType = pMessageType;}
// get whole string
// made it back to json
// divide permanent and measurement data
// do database inserts
// write to logs and sequential files
    public MakeReceiveJsonOperations(){}
    public static int MakeReceiveOmniaOperations()  throws SQLException {
        int iRet=0;
        FileOperations fo = new  FileOperations();
// get data to be handled
        String strWholeRawData = getPseudoJSonData();
        MessageUtils mu = new MessageUtils();
        String strWhoJsonData =mu.reCreateJsonDecimal(strWholeRawData);
// fin d out what kind of like message it is
        setMessageType(mu.GetJsonMessageType(strWhoJsonData));
        if (getMessageType().equals(NO_VALUE)) {
            logger.info("Illegal messageType");
            return INT_RET_NOT_OK;
        }
        if (getMessageType().equals(TT_MEASUREMENT_DATA_INSERT)) {
            iRet = MakeLogOperations("OK##" + strWhoJsonData);
            OmniaMeasurementShortListDataLevel mm = new OmniaMeasurementShortListDataLevel();
            iRet = mm.MakeConnection(MYSQL_LOCAL_JATRI2);
            if (iRet != DATABASE_CONNECTION_OK) {
                logger.info("No Database conncection iRet =" + iRet);
                MakeLogFileOperations("error##" + strWhoJsonData);
               // MakeLogFileOperations("error##" + getMeasurementsJsonData());
            } else {  // do db operations
                String strHelp1 = mu.CutJsonMessage(strWhoJsonData);
                iRet = mm.JsonOmniaMeasurementShortSql(strHelp1);
                if (iRet != INT_RET_OK) {
                    if (iRet == NOT_CHANGED) {
                        MakeLogFileOperations("ok##" + strWhoJsonData);
                    } else {
                        logger.info("Unsuccesful insert iRet=" + iRet);
                        MakeLogFileOperations("error##" + strWhoJsonData);
                    }
                } else {
                    MakeLogFileOperations("ok##" + strWhoJsonData);
                }
                return iRet;
            }
        }
        if ((getMessageType().equals(TT_INTERSECTION_DATA_CHANGE))||(getMessageType().equals(TT_CONTROLLER_DATA_CHANGE))) {
            // kirjoita kama lokiin
            String strHelp1 = mu.CutJsonMessage(strWhoJsonData);
            if (strHelp1.equals(NO_VALUE)) {
                logger.info("Someting wrong in split");
                iRet = MakeLogOperations("error##" + strWhoJsonData);
                return INT_RET_NOT_OK;
            }
            iRet = MakeLogOperations("OK##" + strWhoJsonData);
            IntersectionControllerListDatalevel mm = new IntersectionControllerListDatalevel();
            iRet = mm.MakeConnection(MYSQL_LOCAL_JATRI2);
            if (iRet != DATABASE_CONNECTION_OK) {
                logger.info("No Database conncection iRet =" + iRet);
                MakeLogFileOperations("error##" + strWhoJsonData);
            } else {  // do db operations
                iRet = mm.JsonOmniaIntersectionControllerSql(strHelp1);
                if (iRet != INT_RET_OK) {
                    logger.info("Unsuccesful insert iRet=" + iRet);
                    MakeLogFileOperations("error##" + strHelp1);
                } else {
                    MakeLogFileOperations("ok##" + strHelp1);
                }
            }
        }
        if (getMessageType().equals(TT_DETECTOR_DATA_CHANGE)) {
            String strHelp1 = mu.CutJsonMessage(strWhoJsonData);
            if (strHelp1.equals(NO_VALUE)) {
                logger.info("Someting wrong in split");
                iRet = MakeLogOperations("error##" + strWhoJsonData);
                return INT_RET_NOT_OK;
            }
            iRet = MakeLogOperations("OK##" + strWhoJsonData);
            DetectorServerListDataLevel mm = new DetectorServerListDataLevel();
            iRet = mm.MakeConnection(MYSQL_LOCAL_JATRI2);
            if (iRet != DATABASE_CONNECTION_OK) {
                logger.info("No Database conncection iRet =" + iRet);
                MakeLogFileOperations("error##" + strWhoJsonData);
            } else {  // do db operations
                iRet = mm.JsonDetectorSql(strHelp1);
                if (iRet==INT_RET_OK)   {
                    MakeLogFileOperations("ok##" + strHelp1);
                } else if  (iRet==NOT_CHANGED) {
                    MakeLogFileOperations("ok##" + "Not Changed " +strHelp1);
                } else {
                    logger.info("Unsuccesful insert iRet=" + iRet);
                    MakeLogFileOperations("error##" + iRet + " " + strHelp1);
                }
            }
        }
        return iRet;
    }
    private static int MakeLogOperations(String pWdata) throws SQLException {
        int iRet=0;
        FileOperations fo = new  FileOperations();
  //      logger.info("Unsuccesful insert iRet=" + iRet);
        iRet = fo.initFileOperations();
        iRet = fo.addOmniaCloudJsonLine(pWdata, "OwmMachineCloudServer");
        if (iRet == UNSUCCESSFUL_FILE_OPERATION) {
            logger.info("Unsuccessful file Operations iRet =" + iRet);
        }
        RawDataDataListLevel rd = new RawDataDataListLevel();
        iRet = rd.MakeConnection(MYSQL_LOCAL_JATRI2);
        if (iRet != DATABASE_CONNECTION_OK) {
           logger.info("No Database conncetion iRet =" + iRet);
        } else {
//           logger.info("Rawdata start");
           RawData rData = new RawData();
           rData.MakeEmptyElement();
           rData.setRawDataSourceId(1);   // RETHINK get real source values here
           rData.setRawDataLine(pWdata);
           rData.setRawDataStatus(1);   // RETHINK put real status value here
           String strHelp1 = "";
           SwarcoTimeUtilities swt = new SwarcoTimeUtilities();
           strHelp1 = swt.GetNow();
  //         logger.info("strHelp1=" + strHelp1);
           rData.setTimestamp(strHelp1);
   //        logger.info(" bef AddNewRawData");
           iRet = rd.AddNewRawData(rData);
           if (iRet != 1) {
              logger.info("Unsuccessful RawData Db operation iRet=" + iRet);
           }
        }
        return iRet;
    }
    private static void MakeLogFileOperations(String pWdata){
        FileOperations fo = new  FileOperations();
        int iRet = fo.initFileOperations();
        iRet = fo.addOmniaCloudJsonLine(pWdata, "OwmMachineCloudServer");
        if (iRet == UNSUCCESSFUL_FILE_OPERATION) {
            logger.info("Unsuccessful file Operations iRet =" + iRet);
        }
    }
}
