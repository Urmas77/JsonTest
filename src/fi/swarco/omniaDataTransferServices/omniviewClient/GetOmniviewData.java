package fi.swarco.omniaDataTransferServices.omniviewClient;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.connections.ConWrapper;
import fi.swarco.controlandalarms.AlarmHandling;
import fi.swarco.dataHandling.MakeSendJsonOperations;
import fi.swarco.dataHandling.MeasurementTaskHandling;
import fi.swarco.dataHandling.MeasurementTaskWorkHandling;
import fi.swarco.omniaDataTransferServices.FileOperations;
import fi.swarco.omniaDataTransferServices.LogUtilities;
import fi.swarco.omniaDataTransferServices.MessageUtils;
import fi.swarco.omniaDataTransferServices.omniaClient.GetDetectorData;
import fi.swarco.omniaDataTransferServices.omniaClient.GetOmniaData;
import fi.swarco.properties.JSwarcoproperties;
import fi.swarco.serviceOperations.XORChecksumShort;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import static fi.swarco.CONSTANT.*;
import static fi.swarco.CONSTANT.INT_RET_OK;
import static fi.swarco.omniaDataTransferServices.omniviewClient.OmniviewClient.getSqlServerConnectionType;
public class GetOmniviewData {
    private static Logger logger = Logger.getLogger(GetOmniaData.class.getName());
    private static boolean TodayDone = false; // true if no need to tranfer detectordata today
    private static boolean HourDone = false; // true if no need to run check queries
    private static MeasurementTaskHandling th = new MeasurementTaskHandling();
    public static void sendGetOmniviewData()  {
        int intSleep;
        String strHelp1;
        String strJobTime;
        String strTime;
        String strMinute;
        String url1;
        String inputLine;
        int iRet;
        StringBuffer response;
        SwarcoEnumerations.ConnectionType  oConnType;
        MakeSendJsonOperations ms = new MakeSendJsonOperations();
        AlarmHandling ah = new AlarmHandling();
        MessageUtils mu = new MessageUtils();
        LogUtilities mfl = new LogUtilities();
        MeasurementTaskWorkHandling mth = new MeasurementTaskWorkHandling();
        int responseCode;
        BufferedReader in;
        JSwarcoproperties sw = new JSwarcoproperties();
        iRet =sw.getSwarcoProperties();
        if (iRet != INT_RET_OK) {
            System.out.println(" Error reading Swarco properties ! ");
            System.exit(1);
        }
             sw.getClientMaxControllersInMessage();

        FileOperations fo = new  FileOperations();
        iRet=fo.initFileOperations();
        if (iRet == UNSUCCESSFUL_FILE_OPERATION ) {
            logger.info("Unsuccessful file Operations iRet =" + iRet);
        }
        int iloop=1;
        byte bXorResult;
        ms.setSleep(Integer.valueOf(sw.getOmniaClientWorkWaitSleep()));
        while (iloop == 1) {
            try {
                for (long i = 1; i < 2; i++) {
                    iRet = ms.PollOfWorksOmniview();
// RETHINK does above really belongs here
                    if (iRet == THERE_IS_WORK) {
                        iRet = ms.MakeSendOmniviewOperations();
                        if (iRet != OMNIA_DATA_PICK_OK) {
                            // update task and write log *********************************************
                            if (iRet != OMNIA_EMPTY_WORK_LIST) {
                                if (iRet != INT_RET_OK) {
                                    if (iRet != OMNIA_DATA_PICK_NOT_OK) {
                                        logger.info("Unsuccessful data send1 iRet = " + iRet);
                                        iRet = mfl.MakeFullLogOperations(
                                                SwarcoEnumerations.LoggingDestinationType.OMNIVIEW_CLIENT,
                                                SwarcoEnumerations.ApiMessageCodes.DATAERROR,
                                                "Unsuccessful data send ");
                                        logger.info("Unsuccessful data send iRet = " + iRet);
                                    }
                                }
                            }
                        } else {
                            // if no value do not send RETHINK
                            strHelp1 = ms.getJSonDataForTransfer();
                            logger.info("moi2 lähtee jotakin  strHelp1.length() = "+  strHelp1.length());
                            logger.info("moi2 lähtee jotakin sk  strHelp1 = "+  strHelp1);
                            if (strHelp1.equals(NO_VALUE)) {
                                iRet = mfl.MakeFullLogOperations(
                                        SwarcoEnumerations.LoggingDestinationType.OMNIVIEW_CLIENT,
                                        SwarcoEnumerations.ApiMessageCodes.DATAERROR,
                                        "no data to send  strHelp1 = " + strHelp1);
                                logger.info("no data to send  strHelp1 = " + strHelp1);
                            } else {
                                strHelp1 = mu.DecodeJsonPercentDecimal(strHelp1);
                                //  strHelp1="OK";   //RETHINK
                                strHelp1 = URLEncoder.encode(strHelp1, StandardCharsets.UTF_8.toString());
                                //url1 = sw.getOmniaClientUrl();
                                oConnType=getSqlServerConnectionType();
                                ConWrapper cW = new ConWrapper();
                                cW= sw.FillConnectionWrapper(oConnType);
                                url1 = cW.getClientUrl();
                                //url1 ="http://localhost:" +GHTTPOmniaClientPort +  "/?";
                           //     logger.info("moi2 url1 =" + url1);
                                url1 = url1 + strHelp1;
                                //                       logger.info("moi2 url1 =" + url1);
                                bXorResult = XORChecksumShort.xor(url1);
                                url1 = url1 + "&chk=" + bXorResult;
                                logger.info("****** Length of url1.length() =" + url1.length());
                                URL obj = new URL(url1);
                                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                                con.setRequestProperty("Content-Type", "application/json,charset=UTF-8");
                                con.setDoOutput(true);
                                con.setRequestMethod("GET");
                                responseCode = con.getResponseCode();
                                //       logger.info("bef Response Code : " + responseCode);
                                if (responseCode != 200) {
                                    in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                                    iRet = mfl.MakeFullLogOperations(
                                            SwarcoEnumerations.LoggingDestinationType.OMNIVIEW_CLIENT,
                                            SwarcoEnumerations.ApiMessageCodes.ERROR,
                                            url1);
                                } else {

                                   iRet = ms.DeleteDoneTaskFromWorkDbOmniview();
                                    if (iRet != INT_RET_OK) {
                                        logger.info("Unsuccesfull delete from tasklist iRet = " + iRet);
                                    }
                                    in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                                    iRet = mfl.MakeFullLogOperations(
                                            SwarcoEnumerations.LoggingDestinationType.OMNIVIEW_CLIENT,
                                            SwarcoEnumerations.ApiMessageCodes.SUCCESSFUL,
                                            url1);
                                }
                                logger.info("jjjj after Response Code : " + responseCode);
                                response = new StringBuffer();
                                while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                                    logger.info(" jjjjj inputLine = " + inputLine);
                                }
                                in.close();
                              //  logger.info("jjjj response.length()=  " + response.length());
                            }
                        }
                    }
                    //       logger.info("bef sleep");
                    intSleep = Integer.valueOf(sw.getOmniaClientSleepMs());
                    if (intSleep <=100) {
                        intSleep=101;
                    }
                    Thread.sleep(intSleep);   // 200 ms
                    strJobTime=sw.getOmniaClientDetectorDataTime();
                    strTime = java.time.LocalTime.now().toString();
                    strTime= strTime.substring(0,5);
                    strMinute=strTime.substring(4);
                    if (strJobTime.equals(strTime)) {
                        if (!(TodayDone)) {
//     getOmniaClientDetectorDataTime() if time >   getOmniaClientDetectorDataTime()
                            //dootrick
                            //  if ok
                            GetDetectorData dd = new  GetDetectorData();
                            oConnType=getSqlServerConnectionType();
                            iRet= dd.MakeConnection(oConnType);
                            if (iRet == DATABASE_CONNECTION_OK) {
                                iRet =dd.AddDetectorDataLines();
                                iRet =dd.AddIntersectionControllerDataLines();
                            } else {
                                logger.info("No database connection! ");
                            }
                            TodayDone=true;
                        }
                    }
                    // hour job if here
                    if (HourDone==false) {
                        GetDetectorData dd = new  GetDetectorData();
                        oConnType=getSqlServerConnectionType();
                        iRet= dd.MakeConnection(oConnType);
                        if (iRet == INT_RET_OK) {
                       // alarms are not in use in Omnivue
                            //     iRet = ah.SendAlarm();
                            iRet = mth.MakeStartOmniviewClientDbOperations();
                            if (iRet < 0) {
                                logger.info(" MakeStartOmniviewClientDbOperations ");
                            }
                            HourDone=true;
                        }
                    }
                    // hour job functionality enf
                    if (strMinute.equals("00")) {
                        HourDone=false;
                    }

                    if (strTime.equals("00:00")) {
                        TodayDone=false;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.info(ExceptionUtils.getRootCauseMessage(e));
                logger.info(ExceptionUtils.getFullStackTrace(e));
            }
        }
    }
}

