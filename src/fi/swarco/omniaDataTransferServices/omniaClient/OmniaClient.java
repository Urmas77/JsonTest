package fi.swarco.omniaDataTransferServices.omniaClient;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.omniaDataTransferServices.FileOperations;
import fi.swarco.omniaDataTransferServices.LogUtilities;
import fi.swarco.omniaDataTransferServices.MessageUtils;
import fi.swarco.omniaDataTransferServices.XORChecksumShort;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import fi.swarco.dataHandling.MakeSendJsonOperations;
import fi.swarco.properties.JSwarcoproperties;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import static fi.swarco.CONSTANT.*;
public class OmniaClient {
    static Logger logger = Logger.getLogger(OmniaClient.class.getName());
    private long Rounds = 100000000;
    private static JSwarcoproperties sw = new JSwarcoproperties();
    public static void main(String[] args) {
        try {
            logger.info("Heippa  !!!!");
            int iRett =sw.getSwarcoProperties();
            if (iRett != INT_RET_OK) {
                logger.info(" Error reading Swarco properties ! ");
                System.exit(1);
            }
            sendGetOmniaData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    // HTTP GET request
    private static void sendGetOmniaData() throws IOException {
        int intSleep =0;
        String url1;
        String url2;
        String url3 = "";
        String inputLine;
        int iRet=0;
        MakeSendJsonOperations ms = new MakeSendJsonOperations();
        MessageUtils mu = new MessageUtils();
        LogUtilities mfl = new LogUtilities();
        int responseCode;
        BufferedReader in;
        logger.info("moi Start");
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
                    iRet = ms.PollOfWorks();
                    if (iRet == THERE_IS_WORK) {
                        iRet = ms.MakeSendOmniaOperations();
                        if (iRet != OMNIA_DATA_PICK_OK) {
                            // update task and write log *********************************************
                            if  (iRet != OMNIA_EMPTY_WORK_LIST) {
                                iRet = mfl.MakeFullLogOperations(
                                        SwarcoEnumerations.LoggingDestinationType.OMNIA_CLIENT,
                                        SwarcoEnumerations.ApiMessageCodes.DATAERROR,
                                        "Unsuccessful data send ");
                                logger.info("Unsuccessful data send ");
                            }
                        } else {
                            String strHelp1 = NO_VALUE;
                            strHelp1 = ms.getJSonPermanentData();
                            String strHelp2 = NO_VALUE;
                            strHelp2 = ms.getJSonMeasurementData();
                            strHelp1 = strHelp1 + strHelp2;
                            strHelp1 = mu.DecodeJsonPercentDecimal(strHelp1);
                            strHelp1 = URLEncoder.encode(strHelp1, StandardCharsets.UTF_8.toString());
                            url1 =sw.getOmniaClientUrl();
                            logger.info("moi url1 =" + url1);
                            url1 = url1 + strHelp1;
                            bXorResult = XORChecksumShort.xor(url1);
                            url1 = url1 + "&chk=" + bXorResult;
                            logger.info("****** Length of url1.length() =" + url1.length());
                            logger.info("moi \"&chk=\" =" + bXorResult);
                            URL obj = new URL(url1);
                            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                            con.setRequestProperty("Content-Type", "application/json,charset=UTF-8");
                            con.setDoOutput(true);
                            con.setRequestMethod("GET");
                            responseCode = con.getResponseCode();
                            logger.debug("bef Response Code : " + responseCode);
                            if (responseCode != 200) {
                                in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                                iRet = mfl.MakeFullLogOperations(
                                        SwarcoEnumerations.LoggingDestinationType.OMNIA_CLIENT,
                                        SwarcoEnumerations.ApiMessageCodes.ERROR,
                                        url1);
                            } else {
                                // delete done task from db here RETHINK
                                // wrong place this must be done Imidiadly  after One tasks has been done
                                iRet= ms.DeleteDoneTaskFromWorkDb();
                                //iRet = ms.DeleteDoneTaskFromWorkDbOLD();
                                if (iRet != INT_RET_OK) {
                                    logger.info("Unsuccesfull delete from tasklist iRet = " + iRet);
                                }
                                in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                                iRet = mfl.MakeFullLogOperations(
                                        SwarcoEnumerations.LoggingDestinationType.OMNIA_CLIENT,
                                        SwarcoEnumerations.ApiMessageCodes.SUCCESSFUL,
                                        url1);
                            }
                            logger.info("jjjj after Response Code : " + responseCode);
                            StringBuffer response = new StringBuffer();
                            while ((inputLine = in.readLine()) != null) {
                                response.append(inputLine);
                                logger.info(" jjjjj inputLine = " + inputLine);
                            }
                            in.close();
                            logger.info("jjjj response.length()=  " + response.length());
                        }
                    }
                    logger.info("bef sleep");
                    logger.info(" sw.getOmniaClientSleepMs() = " + sw.getOmniaClientSleepMs());
                    intSleep = Integer.valueOf(sw.getOmniaClientSleepMs());
                    if (intSleep <=100) {
                        intSleep=200;
                    }
                    Thread.sleep(intSleep);   // 200 ms
                    logger.info("after sleep");
                }
            } catch (Exception e) {
               e.printStackTrace();
               logger.info(ExceptionUtils.getRootCauseMessage(e));
               logger.info(ExceptionUtils.getFullStackTrace(e));
            }
        }
    }
}
