package fi.swarco.dataHandling;
import fi.swarco.dataHandling.pojos.RawData;
import fi.swarco.omniaDataTransferServices.FileOperations;
import fi.swarco.omniaDataTransferServices.JsonWrapper;
import fi.swarco.omniaDataTransferServices.MessageUtils;
import fi.swarco.omniaDataTransferServices.SwarcoTimeUtilities;
import org.apache.log4j.Logger;
import static fi.swarco.CONSTANT.*;
import static fi.swarco.SwarcoEnumerations.ConnectionType.MYSQL_LOCAL_JATRI2;
public class MakeReceiveJsonOperations {
    static Logger logger = Logger.getLogger(MakeReceiveJsonOperations.class.getName());
    private static String pseudoJsonData = NO_VALUE;
    public static String getPseudoJSonData() {
        logger.info("get jjjjjj  pseudoJsonData = " + pseudoJsonData);
        return pseudoJsonData;
    }
    public static void setPseudoJsonData(String pPseudoJsonData) {
        pseudoJsonData = pPseudoJsonData;
        logger.info("set jjjjjj  pseudoJsonData = " + pseudoJsonData);
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
        logger.info("jjjjjj   permanentJsonData = " + permanentJsonData);
    }
    private static String measurementsJsonData = NO_VALUE;
    public static String getMeasurementsJsonData() {
        return measurementsJsonData;
    }
    public static void setMeasurementsJsonData(String pMeasurementsJsonData) { measurementsJsonData = pMeasurementsJsonData;
        logger.info("jjjjjj   measurementsJsonData = " + measurementsJsonData);
    }
// get whole string
// made it back to json
// divide permanent and measurement data
// do database inserts
// write to logs and sequential files
    public MakeReceiveJsonOperations(){}
    public static int MakeReceiveOmniaOperations() {
        int iRet=0;
        FileOperations fo = new  FileOperations();
        JsonWrapper jsw = new JsonWrapper();
        jsw.MakeEmptyElement();
// get data to be handled
        String strWholeRawData = getPseudoJSonData();
        MessageUtils mu = new MessageUtils();
        String strWhoJsonData =mu.reCreateJsonDecimal(strWholeRawData);
       // setpFullJsonData(strWhoJsonData);
       logger.info("strWhoJsonData = " +strWhoJsonData);
        jsw = mu.SplitJson(strWhoJsonData);
        if (!(jsw.getPermanentData().equals(NO_VALUE))) {
            setPermanentJsonData(jsw.getPermanentData());
            setMeasurementsJsonData(jsw.getMeasurementsData());
        }   else {
            // RETHINK add also Rawdata write here JIs 13.09 2019
            logger.info("Unsuccesful insert iRet=" + iRet);
            iRet = fo.addOmniaCloudJsonLine("error##" + getMeasurementsJsonData(),"OwmMachineCloudServer");
            if (iRet == UNSUCCESSFUL_FILE_OPERATION ) {
                logger.info("Unsuccessful file Operations iRet =" + iRet);
            }
            return INT_RET_NOT_OK;
        }
        logger.info("getPermanentJsonData() =  " + getPermanentJsonData());
        logger.info("getMeasurementsJsonData() =  " + getMeasurementsJsonData());

        iRet=fo.initFileOperations();
        if (iRet == UNSUCCESSFUL_FILE_OPERATION ) {
            logger.info("Unsuccessful file Operations iRet =" + iRet);
        }
// database operations
        RawDataDataListLevel  rd = new RawDataDataListLevel();
        OmniaIntersectionListDataLevel kk = new OmniaIntersectionListDataLevel();
        OmniaMeasurementListDataLevel mm = new OmniaMeasurementListDataLevel();
        iRet = rd.MakeConnection(MYSQL_LOCAL_JATRI2);
        if (iRet != DATABASE_CONNECTION_OK) {
            logger.info("No Database conncetion iRet =" + iRet);
        } else {
            logger.info("Rawdata start");
            RawData rData = new RawData();
            rData.MakeEmptyElement();
            rData.setRawDataSourceId(1);   // RETHINK get real source values here
            rData.setRawDataLine(strWhoJsonData);
            rData.setRawDataStatus(1);   // RETHINK put real status value here
            String strHelp1="";
            SwarcoTimeUtilities swt = new SwarcoTimeUtilities();
            strHelp1 = swt.GetNow();
            logger.info("strHelp1=" + strHelp1);
//            strHelp1="2019-07-16 10:55:02";
//            logger.info("strHelp1=" + strHelp1);
            rData.setTimestamp(strHelp1);
            logger.info( " bef AddNewRawData");
            iRet = rd.AddNewRawData(rData);
            if (iRet!=1) {
                logger.info("Unsuccessful RawData Db operation iRet="+ iRet);
            }
        }
        iRet = kk.MakeConnection(MYSQL_LOCAL_JATRI2);
        if (iRet != DATABASE_CONNECTION_OK)  {
            logger.info("No Database conncetion iRet =" + iRet);
            //   System.exit(1);
        } else {  // do db operations
            iRet=  kk.JsonOmniaIntersectionSql(getPermanentJsonData());
            if  (iRet!=1) {
                logger.info("Unsuccessful insert iRet="+ iRet);
                iRet = fo.addOmniaCloudJsonLine("error##" + getPermanentJsonData(),"OwmMachineCloudServer");
           } else
                iRet=  mm.MakeConnection(MYSQL_LOCAL_JATRI2);
            if (iRet != DATABASE_CONNECTION_OK)  {
                logger.info("No Database conncetion iRet =" + iRet);
                logger.info("Unsuccessful insert iRet="+ iRet);
                iRet = fo.addOmniaCloudJsonLine("error##" + getMeasurementsJsonData(),"OwmMachineCloudServer");
                if (iRet == UNSUCCESSFUL_FILE_OPERATION ) {
                    logger.info("Unsuccessful file Operations iRet =" + iRet);
                }
                //   System.exit(1);
            } else {  // do db operations
                iRet = mm.JsonOmniaMeasurementSql(getMeasurementsJsonData());
                if  (iRet!=1) {
                    logger.info("Unsuccesful insert iRet=" + iRet);
                    iRet = fo.addOmniaCloudJsonLine("error##" + getMeasurementsJsonData(),"OwmMachineCloudServer");
                    if (iRet == UNSUCCESSFUL_FILE_OPERATION ) {
                        logger.info("Unsuccessful file Operations iRet =" + iRet);
                    }
                } else {
                    iRet = fo.addOmniaCloudJsonLine("ok##" + getMeasurementsJsonData(),"OwmMachineCloudServer");
                    if (iRet == UNSUCCESSFUL_FILE_OPERATION ) {
                        logger.info("Unsuccessful file Operations iRet =" + iRet);
                    }
                }
            }
        }
        return iRet;
    }
}
