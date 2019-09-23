package fi.swarco.messageHandling;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import static fi.swarco.CONSTANT.*;
public class CustomerRequestMapHandle {
    static Logger logger = Logger.getLogger(CustomerRequestMapHandle.class.getName());
    private String legalPath;
    private String currentMethod;
    private String currentQuery;
    Map<String, String> currentMap = new HashMap<String, String>();
    public String getPath() {return legalPath;}

    public void setPath(String pPath) { legalPath=pPath;}

    public String getMethod() {return currentMethod;}

    public void setMethod(String pMethod) { currentMethod=pMethod;}

    public String getQuery() {return currentQuery;}

    public void setQuery(String pQuery) { currentQuery=pQuery;}

    private String queryName;
    public String getQueryName() {return queryName;}

    public void setQueryName(String pQueryName) { queryName=pQueryName;}

    private String queryParameters;
    public String getQueryParameters() {return queryParameters;}

    public void setQueryParameters(String pQueryParameters ) { queryParameters=pQueryParameters;}

    // RETHINK constant base solution?
    public int findQueryNameAndQueryParameters() {
        String strHelp1 = getQuery();
        String strHelp2=strHelp1.toUpperCase().substring(0,strHelp1.indexOf(","));
        if (strHelp2.equals(QUERY_CURRENTSTATE)) {
            setQueryName(QUERY_CURRENTSTATE);
        }
        if (strHelp2.equals(QUERY_HISTORYSTATE)) {
            setQueryName(QUERY_HISTORYSTATE);
        }
        strHelp1 =strHelp1.substring(strHelp1.indexOf(",")+1);
// RETHINK take check sum away temporary
        int iPos =  strHelp1.indexOf("&");
        strHelp1 =strHelp1.substring(0,iPos);
        setQueryParameters(strHelp1);
        return INT_RET_OK;
    }
    public Map<String, String> MapQueryParametersString() {
        int iCount;
        String strHelp1 = getQueryParameters();
        logger.info("strHelp1 = " + strHelp1);
        MapUtilities mu = new MapUtilities();
        currentMap = mu.SeqDataToMap(strHelp1);
        iCount=0;
        Iterator<Map.Entry<String, String>> entries = currentMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            logger.info("currenQuery Key = " + entry.getKey() + ", Value = " + entry.getValue() + " iCount = " + iCount);
            iCount = iCount+1;
        }
        return currentMap; // RETHINK ????????
        // return INT_RET_OK;
    }
    public  ParameterWrapper DefineAndCheckQueryParameters(Map<String, String> pQp) {
        ParameterWrapper retWrap = new ParameterWrapper();
        retWrap.MakeEmptyElement();
        int iCount=0;
        Iterator<Map.Entry<String, String>> entries = pQp.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            logger.info("currenQuery Key = " + entry.getKey() + ", Value = " + entry.getValue() + " iCount = " + iCount);
            iCount = iCount+1;
            retWrap.StoreFieldToWrapper(entry.getKey(),entry.getValue());
        }
        ReturnWrapper  returnW = retWrap.CheckStoreResult();
        if  (returnW.getRetCode().equalsIgnoreCase(RET_NOT_OK)) {
            retWrap.MakeEmptyElement();
        }
        return retWrap;
    }
}
