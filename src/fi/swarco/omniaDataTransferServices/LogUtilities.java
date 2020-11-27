package fi.swarco.omniaDataTransferServices;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.dataHandling.pojos.RawData;
import fi.swarco.dataHandling.RawDataDataListLevel;
import fi.swarco.serviceOperations.SwarcoTimeUtilities;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import java.sql.SQLException;

import static fi.swarco.CONSTANT.*;
import static fi.swarco.SwarcoEnumerations.ConnectionType.*;
import static fi.swarco.SwarcoEnumerations.ConnectionType.NOT_DEFINED;
import static fi.swarco.SwarcoEnumerations.LoggingDestinationType.*;
import static fi.swarco.omniaDataTransferServices.omniaClient.OmniaClient.getSqlServerConnectionType;

// class takes care of writing logging strings to file system and daatabses
// Destination classes given//
// version 1 destination DBs and files are taken from parameters
// message type  OK // NOT_OK, ERROR // REQUEST //RESPONSE  parameter are supported
// Timestamp is added automatically
public class LogUtilities {
    private static Logger logger = Logger.getLogger(LogUtilities.class.getName());
    //LoggingDestinationType {API_CLIENT,OMNIA_CLIENT,OMNIA_HTTPSERVER,CLOUD_HTTPSERVER}
    //public enum ApiMessageCodes {SUCCESSFUL,UNSUCCESSFUL,ERROR,REQUEST,RESPONSE}
    public int MakeFullLogOperations(SwarcoEnumerations.LoggingDestinationType pDestination,
                                     SwarcoEnumerations.ApiMessageCodes pSuccessCode,
                                     String pLogline) {
        int iRet;
        FileOperations fo = new FileOperations();
        iRet = fo.initFileOperations();
        if (iRet == UNSUCCESSFUL_FILE_OPERATION) {
            logger.info("Unsuccessful file Operations iRet =" + iRet);
            return iRet;
        }
        SwarcoTimeUtilities tt = new SwarcoTimeUtilities();
        String strTime = tt.GetNow();
//        logger.info("strTime=" + strTime);
        String destinationFileName = "";
        SwarcoEnumerations.ConnectionType destinationDatabaseName = NOT_DEFINED;
        if (pDestination.equals(API_CLIENT)) {
            destinationFileName = "OwmMachineCustomerClient";
            destinationDatabaseName = MYSQL_LOCAL_JATRI2;
        } else if (pDestination.equals(CLOUD_HTTPSERVER)) {
            destinationFileName = "OwmMachinePickCloudServer";
            destinationDatabaseName = MYSQL_LOCAL_JATRI2;
        } else if (pDestination.equals(OMNIA_CLIENT)){
            destinationFileName = "OwmMachineClient";
            destinationDatabaseName = SQLSERVER_LOCAL_JOMNIATEST;
        }
        iRet = fo.addOmniaClientJsonLine(pSuccessCode + strTime + "##" + pLogline, destinationFileName);
        if (iRet != INT_RET_OK) {
            logger.info("Unsuccessful log file write iRet =" + iRet + "  " + pSuccessCode + strTime + "##" + pLogline + destinationFileName);
        }
        RawDataDataListLevel rd = new RawDataDataListLevel();
        SwarcoEnumerations.ConnectionType oConnType;
        oConnType = getSqlServerConnectionType() ;
        iRet = rd.MakeConnection(oConnType);
        if (iRet == DATABASE_CONNECTION_OK) {
            RawData rData = new RawData();
            rData.MakeEmptyElement();
            rData.setRawDataSourceId(1);   // RETHINK get real source values here
            rData.setRawDataLine(pLogline);
            rData.setRawDataStatus(1);   // RETHINK put real status value here see bellow this maybe not neede field
            rData.setRawDataStatusString(pSuccessCode.toString());   //
            rData.setTimestamp(strTime);
  //          logger.info("bef AddNewRawData");
            try {
            iRet = rd.AddNewRawData(rData);
            } catch (SQLException e) {
                logger.info(ExceptionUtils.getRootCauseMessage(e));
                logger.info(ExceptionUtils.getFullStackTrace(e));
                e.printStackTrace();
            }
            if (iRet != INT_RET_OK) {
                logger.info("Unsuccessful RawData Db operation iRet=" + iRet);
            }
        }
        return INT_RET_OK;
    }
    public int MakeOnlyLogFileOperations(SwarcoEnumerations.LoggingDestinationType pDestination,
                                         SwarcoEnumerations.ApiMessageCodes pSuccessCode,
                                         String pLogline) {
        int iRet;
        FileOperations fo = new FileOperations();
        iRet = fo.initFileOperations();
        if (iRet == UNSUCCESSFUL_FILE_OPERATION) {
            logger.info("Unsuccessful file Operations iRet =" + iRet);
            return iRet;
        }
        SwarcoTimeUtilities tt = new SwarcoTimeUtilities();
        String strTime = tt.GetNow();
        logger.info("strTime=" + strTime);
        String destinationFileName = "";
        if (pDestination.equals(API_CLIENT)) {
            destinationFileName = "OwmMachineCustomerClient";
        } else if (pDestination.equals(CLOUD_HTTPSERVER)) {
            destinationFileName = "OwmMachinePickCloudServer";
        }
        iRet = fo.addOmniaClientJsonLine(pSuccessCode + strTime + "##" + pLogline, destinationFileName);
        if (iRet != INT_RET_OK) {
            logger.info("Unsuccessful log file write iRet =" + iRet + "  " + pSuccessCode + strTime + "##" + pLogline + destinationFileName);
        }
        return iRet;
    }
    public int MakeOnlyLogDatabaseOperations(SwarcoEnumerations.LoggingDestinationType pDestination,
                                             SwarcoEnumerations.ApiMessageCodes pSuccessCode,
                                              String pLogline) {
        int iRet;
  // RETHINK db connection MYSql ???
        SwarcoTimeUtilities tt = new SwarcoTimeUtilities();
        String strTime = tt.GetNow();
        logger.info("strTime=" + strTime);
        SwarcoEnumerations.ConnectionType destinationDatabaseName = NOT_DEFINED;
        if (pDestination.equals(API_CLIENT)) {
            destinationDatabaseName = MYSQL_LOCAL_JATRI2;
        } else if (pDestination.equals(CLOUD_HTTPSERVER)) {
            destinationDatabaseName = MYSQL_LOCAL_JATRI2;
        }
        RawDataDataListLevel rd = new RawDataDataListLevel();
        iRet = rd.MakeConnection(destinationDatabaseName);
        if (iRet != DATABASE_CONNECTION_OK) {
            logger.info("No Database conncetion iRet =" + iRet);
        } else {
            logger.info("Rawdata start");
            RawData rData = new RawData();
            rData.MakeEmptyElement();
            rData.setRawDataSourceId(1);   // RETHINK get real source values here
            rData.setRawDataLine(pLogline);
            rData.setRawDataStatus(1);   // RETHINK put real status value here see bellow this maybe not neede field
            rData.setRawDataStatusString(pSuccessCode.toString());   //
            rData.setTimestamp(strTime);
            logger.info("bef AddNewRawData");
            try {
                iRet = rd.AddNewRawData(rData);
            } catch (SQLException e) {
                logger.info(ExceptionUtils.getRootCauseMessage(e));
                logger.info(ExceptionUtils.getFullStackTrace(e));
                e.printStackTrace();
            }
            if (iRet != INT_RET_OK) {
                logger.info("Unsuccessful RawData Db operation iRet=" + iRet);
            }
        }
        return iRet;
    }
}



