package fi.swarco.messageHandling;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Iterator;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import static fi.swarco.CONSTANT.*;
public class MapHandle {
    static Logger logger = Logger.getLogger(MapHandle.class.getName());
    public int MMapHandle(String pPath, String pMethod, String pQuery, String PCityName) {
        // This full constructor
        System.out.println("From MapHandle.constructor pPath=" + pPath);
        System.out.println("From MapHandle.constructor pMethod=" + pMethod);
        System.out.println("From MapHandle.constructor pQuery=" + pQuery);
        System.out.println("From MapHandle.constructor PCityName=" + PCityName);
        setPath(pPath);
        setMethod(pMethod);
        setQuery(pQuery);
        setCityName(PCityName);
        MapQueryString();
        return 1;
    }
    // Omnia depending data transfer path
    // Omnia DbName
    // checksum
    private String legalPath;
    private String currentMethod;
    private String currentQuery;
    private String currentCityName;
    private String currentDataCheksum;
    private String currentQueryName;
    private ParameterWrapper currentParameterWrapper = new ParameterWrapper();
    public ParameterWrapper getParameterWrapper() {
        return currentParameterWrapper;
    }
    public void setParameterWrapper(ParameterWrapper pParameterWrapper) {
        currentParameterWrapper = pParameterWrapper;
    }
    // Rethink in future you must check info inside single omnia data
// what to do if new intersection has been added to exsisting omnia
// Alarm to log. Because new stable data should be added manually
// RETHINK first try: Check only Omnia codes

    private int[] currentOmniaCodes = {1,2};
    private String[] currentOmniaNames = {"TOIMISTON TESTIJÄRJESTELMÄ","OMNIA LAHTI TOIMISTO"};
    private int[] currentIntersectionCodes = {200013};
    private String[] currentIntersectionPublicNames = {"Eka risteys"};
    private int[] currentControllerCodes = {100013};
    private String[] currentControllerPublicNames = {"Toimiston testijärjestelmä"};
    private int[] currentDetectorCodes = {164, 165, 166};
    private String[] currentDetectorPublicNames = {"164 pohjoiseen", "165 itään", "166 länteen"};
    private String[] currentLegalQueryNames = {"CURRENTSTATE","HISTORYSTATE"};
    public String getPath() {
        return legalPath;
    }
    public void setPath(String pPath) {
        legalPath = pPath;
    }
    public String getMethod() {
        return currentMethod;
    }
    public void setMethod(String pMethod) {
        currentMethod = pMethod;
    }
    public String getQuery() {
        return currentQuery;
    }
    public void setQuery(String pQuery) {
        currentQuery = pQuery;
    }
    public String getQueryName() {
        return currentQueryName;
    }
    public void setQueryName(String pQueryName) {
        currentQueryName = pQueryName;
    }
    public String getCityName() {
        return currentCityName;
    }
    public void setCityName(String pCurrentCityName) {
        currentCityName = pCurrentCityName;
    }
    public String getDataCheksum() {
        return currentDataCheksum;
    }
    public void setDataCheksum(String pDataCheksum) {
        currentDataCheksum = pDataCheksum;
    }
    private Map<String, String> currentMap = new HashMap<String, String>();
    public int findOmniaCode(String pOmniaName){
    // two separate array version    RETHINK do map
        int  intI =0;
        for (String strHelp1  : currentOmniaNames) {
            if (strHelp1.equalsIgnoreCase(pOmniaName)) {
               return intI;
            }
            intI=intI+1;
        }
        return VALUE_NOT_FOUND;
    }
    public int isQueryNameLegal() {
       if (ArrayUtils.contains( currentLegalQueryNames, getQueryName().toUpperCase() ) ) {
            logger.info("Queryname is  ok =" + getQueryName() );
            return INT_RET_OK;
        }
        return INT_RET_NOT_OK ;
    }
    public int MapQueryString() {
        int iCount;
        logger.info("From MapHandle.MapQueryString currenQuery= " + currentQuery);
        String strHelp1=currentQuery.substring(0,currentQuery.indexOf(","));
        currentQuery=currentQuery.substring(currentQuery.indexOf(",")+1);
        setQueryName(strHelp1);
        int iRet = isQueryNameLegal();
        if (iRet!=INT_RET_OK){
            return iRet;
        }
        MapUtilities mu = new MapUtilities();
        currentMap = mu.queryToMap(currentQuery);
        iCount=0;
        Iterator<Entry<String, String>> entries = currentMap.entrySet().iterator();
        while (entries.hasNext()) {
            Entry<String, String> entry = entries.next();
            logger.info("currenQuery Key = " + entry.getKey() + ", Value = " + entry.getValue() + " iCount = " + iCount);
            iCount = iCount+1;
        }
        return INT_RET_OK;
    }
    public int DefineQueryParameters() {
        int intFirst=0;
        int iCounter =0;
        currentParameterWrapper.MakeEmptyElement();
        Iterator<Entry<String, String>> entries = currentMap.entrySet().iterator();
        while (entries.hasNext()) {
            Entry<String, String> entry = entries.next();
            logger.info("From MapHandle.defineDeviceNumber Key = " + entry.getKey() + ", Value = " + entry.getValue());
            logger.info("currentParameterWrapper.getStrOmniaCode() = " + currentParameterWrapper.getStrOmniaCode());
            if (entry.getKey().equalsIgnoreCase(currentParameterWrapper.getStrOmniaCode())) {
                iCounter = currentParameterWrapper.getOmniaCodeCounter() + 1;
                currentParameterWrapper.setOmniaCodeCounter(iCounter);
                if (iCounter > 1) {
                    logger.info("too many OmniaCodes on messages !");
                    return TOO_MANY_SIMILAR_PARAMETERS;
                }
                currentParameterWrapper.setOmniaCode(Integer.valueOf(entry.getValue()));
            } else if (entry.getKey().equalsIgnoreCase(currentParameterWrapper.getStrOmniaName())) {
                iCounter = currentParameterWrapper.getOmniaNameCounter() + 1;
                currentParameterWrapper.setOmniaNameCounter(iCounter);
                if (iCounter > 1) {
                    logger.info("too many OmniaNames on messages !");
                    return TOO_MANY_SIMILAR_PARAMETERS;
                }
                currentParameterWrapper.setOmniaName(String.valueOf(entry.getValue()));
            } else if (entry.getKey().equalsIgnoreCase(currentParameterWrapper.getStrIntersectionId())) {
                iCounter = currentParameterWrapper.getIntersectionIdCounter() + 1;
                currentParameterWrapper.setIntersectionIdCounter(iCounter);
                if (iCounter > 1) {
                    logger.info("too many IntersectionId codes in message!");
                    return TOO_MANY_SIMILAR_PARAMETERS;
                }
                currentParameterWrapper.setIntersectionId(Integer.valueOf(entry.getValue()));
            } else if (entry.getKey().equalsIgnoreCase(currentParameterWrapper.getStrIntersectionDescription())) {
                iCounter = currentParameterWrapper.getIntersectionDescriptionCounter() + 1;
                currentParameterWrapper.setIntersectionDescriptionCounter(iCounter);
                if (iCounter > 1) {
                    logger.info("too many IntersectionDescriptions in message!");
                    return TOO_MANY_SIMILAR_PARAMETERS;
                }
                currentParameterWrapper.setIntersectionDescription(String.valueOf(entry.getValue()));
            } else if (entry.getKey().equalsIgnoreCase(currentParameterWrapper.getStrControllerId())) {
                iCounter = currentParameterWrapper.getIntersectionIdCounter() + 1;
                currentParameterWrapper.setControllerIdCounter(iCounter);
                if (iCounter > 1) {
                    logger.info("too many ControllerId codes in message!");
                    return TOO_MANY_SIMILAR_PARAMETERS;
                }
                currentParameterWrapper.setControllerId(Integer.valueOf(entry.getValue()));
            } else if (entry.getKey().equalsIgnoreCase(currentParameterWrapper.getStrControllerExternalCode())) {
                iCounter = currentParameterWrapper.getControllerExternalCodeCounter() + 1;
                currentParameterWrapper.setControllerExternalCodeCounter(iCounter);
                if (iCounter > 1) {
                    logger.info("too many ControllerExternalCodes in message!");
                    return TOO_MANY_SIMILAR_PARAMETERS;
                }
                currentParameterWrapper.setControllerExternalCode(String.valueOf(entry.getValue()));
            } else if (entry.getKey().equalsIgnoreCase(currentParameterWrapper.getStrDetectorId())) {
                iCounter = currentParameterWrapper.getDetectorIdCounter() + 1;
                currentParameterWrapper.setDetectorIdCounter(iCounter);
                if (iCounter > 1) {
                    logger.info("too many DetectorId codes in message!");
                    return TOO_MANY_SIMILAR_PARAMETERS;
                }
                currentParameterWrapper.setDetectorId(Integer.valueOf(entry.getValue()));
            } else if (entry.getKey().equalsIgnoreCase(currentParameterWrapper.getStrDetectorExternalCode())) {
                iCounter = currentParameterWrapper.getDetectorExternalCodeCounter() + 1;
                currentParameterWrapper.setDetectorExternalCodeCounter(iCounter);
                if (iCounter > 1) {
                    logger.info("too many DetectorExternalCodes in message!");
                    return TOO_MANY_SIMILAR_PARAMETERS;
                }
                currentParameterWrapper.setDetectorExternalCode(String.valueOf(entry.getValue()));
            } else if (entry.getKey().equalsIgnoreCase(currentParameterWrapper.getStrStartTime())) {
                iCounter = currentParameterWrapper.getStartTimeCounter() + 1;
                currentParameterWrapper.setStartTimeCounter(iCounter);
                if (iCounter > 1) {
                    logger.info("too many StartTime codes in message!");
                    return TOO_MANY_SIMILAR_PARAMETERS;
                }
                currentParameterWrapper.setStartTime(String.valueOf(entry.getValue()));
            } else if (entry.getKey().equalsIgnoreCase(currentParameterWrapper.getStrEndTime())) {
                iCounter = currentParameterWrapper.getEndTimeCounter() + 1;
                currentParameterWrapper.setEndTimeCounter(iCounter);
                if (iCounter > 1) {
                    logger.info("too many EndTime codes in message!");
                    return TOO_MANY_SIMILAR_PARAMETERS;
                }
                currentParameterWrapper.setEndTime(String.valueOf(entry.getValue()));
            }
            return INT_RET_OK;
        }
        return  INT_RET_OK;
    }
        //   if (ArrayUtils.contains( currentCityNames, getCityName() ) ) {
    public int CheckParameterSetLegacy() {
        ReturnWrapper rw = new  ReturnWrapper();
        rw.MakeEmptyElement();
        HtppRequestWrapper rqw = new HtppRequestWrapper();
        if (getQueryName().equalsIgnoreCase("CURRENTSTATE")) {
            rw = rqw.CurrentState(currentParameterWrapper);
        } else if  (getQueryName().equalsIgnoreCase("HISTORYSTATE")) {
            rw = rqw.HistoryState(currentParameterWrapper);
        }
        if (rw.getRetCode().equals(RET_NOT_OK)) {
            logger.info("rw.getRetMessage() = " + rw.getRetMessage() );
            logger.info("rw.getRetCode() = " + rw.getRetCode() );
            logger.info("rw.getRetString() = " + rw.getRetString() );
            return INT_RET_NOT_OK;
        }
        return INT_RET_OK;
    }
    public int HandleQyery() {
        int iRet= MapQueryString();
        if (iRet!=INT_RET_OK) {
            return iRet;
        }
        iRet= DefineQueryParameters();
        if (iRet!=INT_RET_OK) {
            return iRet;
        }
       iRet=CheckParameterSetLegacy();
        if (iRet!=INT_RET_OK) {
            return iRet;
        }
        return iRet;
    }
    public int DefineCityName() {
// there should be only one device number in Query
        int intFirst=0; // only one device number is allowed
        Iterator<Entry<String, String>> entries = currentMap.entrySet().iterator();
        while (entries.hasNext()) {
            Entry<String, String> entry = entries.next();
            //System.out.println("From MapHandle.defineDeviceNumber Key = " + entry.getKey() + ", Value = " + entry.getValue());
            if (entry.getKey().equals("city")) {
                setCityName(entry.getValue());
                intFirst=intFirst+1;
                logger.info("From MapHandle.defineCityName Key = " + entry.getKey() + ", Value = " + entry.getValue());
                if (intFirst>1) {
                    return -9999;
                }
            }
            //entries.remove();
        }
        if (intFirst==0) {
            return -9998;
        }
     //   if (ArrayUtils.contains( currentCityNames, getCityName() ) ) {
     //       logger.info("currentCityName ok =" + getCityName());
     //       return 1;
     //   }
        return -9997;
    }
}