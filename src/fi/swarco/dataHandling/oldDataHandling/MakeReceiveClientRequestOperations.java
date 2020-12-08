package fi.swarco.dataHandling.oldDataHandling;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.messageHandling.CustomerRequestMapHandle;
import fi.swarco.messageHandling.HtppRequestWrapper;
import fi.swarco.messageHandling.ParameterWrapper;
import fi.swarco.omniaDataTransferServices.LogUtilities;
import fi.swarco.omniaDataTransferServices.MessageUtils;
import org.apache.log4j.Logger;
import java.util.Map;
import static fi.swarco.CONSTANT.*;
import static fi.swarco.SwarcoEnumerations.ConnectionType.MYSQL_LOCAL_JATRI2;
public class MakeReceiveClientRequestOperations {
    static Logger logger = Logger.getLogger(MakeReceiveClientRequestOperations.class.getName());
    private static String requestData = NO_VALUE;
    public  String getRequestData() {
        return requestData;
    }
    public  void setRequestDataData(String pRequestData ) {
        requestData = pRequestData;
    }
    private static String currentQueryName=NO_VALUE;
    public static String getCurrentQueryName() {
        return currentQueryName;
    }
    public static void setCurrentQueryName(String pCurrentQueryName ) {
        currentQueryName = pCurrentQueryName;
    }
    private static String currentParameterData=NO_VALUE;
    public static String getCurrentParameterData() {
        return currentParameterData;
    }
    public static void setCurrentParameterData(String pCurrentParameterData ) {
        currentParameterData = pCurrentParameterData;
    }
    private String responseMessage=NO_VALUE;
    public  String getReponseMessage() {
        return responseMessage;
    }
    public  void setReponseMessage(String pResponseMessage ) {
        responseMessage = pResponseMessage;
    }
    public int MakeClientRequestOperations() {
        int iRet=INT_RET_OK;
        HtppRequestWrapper custRequest = new HtppRequestWrapper();
// find command
        CustomerRequestMapHandle cmH = new CustomerRequestMapHandle();
        cmH.setQuery(getRequestData());
        logger.info("getRequestData() ="+ getRequestData());

        iRet=cmH.findQueryNameAndQueryParameters();
        if (iRet!=INT_RET_OK) {
            logger.info("queryParameters error iRet= " + iRet);
            return iRet;
        }
        Map<String,String> queryMap =cmH.MapQueryParametersString();
        ParameterWrapper pmW =  cmH.DefineAndCheckQueryParameters(queryMap);
        if (pmW.getOmniaCode()==NO_OMNIA) {
            logger.info("Empty parameterlist!");
            return INT_RET_OK;   // should be EMPTY_PARAMETER_WRAPPER
        }
        DetectorMeasurementsDataLevel dmD = new DetectorMeasurementsDataLevel();
        iRet =dmD.MakeConnection(MYSQL_LOCAL_JATRI2);
        if (iRet!=DATABASE_CONNECTION_OK) {
            logger.info("No database connection!");
            return INT_RET_OK;
        }
        String strWhere =dmD.MakeWhereCurrentState(pmW);
        if (strWhere.equals(NO_VALUE)) {
            logger.info("Unable to build Where conditions!");
            return INT_RET_OK;
        }
        iRet=dmD.DetectorMeasurementsDataListWhere(strWhere);
        if (iRet!=INT_RET_OK) {
            logger.info("Unsuccessful detectorMeasurementList search!");
            return INT_RET_NOT_OK;
        }
        String strHelp1 = dmD.MakeJsonString(dmD.GetDetectorMeasurementsDataList());
        if (strHelp1.equals(NO_VALUE)) {
            logger.info("Unsuccessful Json String creation process!");
            setReponseMessage(NO_VALUE);
            return INT_RET_NOT_OK;
        }
        // RETHINK should spaces take away here
        // yes
        MessageUtils mu = new MessageUtils();
        strHelp1= mu.DecodeJsonPercentDecimal(strHelp1);
        setReponseMessage(strHelp1);
        // both file io and rawData to db
        LogUtilities mfl = new LogUtilities();
                iRet= mfl.MakeFullLogOperations(
                SwarcoEnumerations.LoggingDestinationType.CLOUD_HTTPSERVER,
                SwarcoEnumerations.ApiMessageCodes.RESPONSE,
                getReponseMessage());
        logger.info("at end iRet = "+ iRet);
        return iRet;
    }
}


