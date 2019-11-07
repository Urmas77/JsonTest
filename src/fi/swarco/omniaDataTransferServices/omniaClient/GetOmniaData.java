package fi.swarco.omniaDataTransferServices.omniaClient;

import fi.swarco.SwarcoEnumerations;
import fi.swarco.dataHandling.MakeSendJsonOperations;
import fi.swarco.omniaDataTransferServices.FileOperations;
import fi.swarco.omniaDataTransferServices.LogUtilities;
import fi.swarco.omniaDataTransferServices.MessageUtils;
import fi.swarco.properties.JSwarcoproperties;
import fi.swarco.omniaDataTransferServices.XORChecksumShort;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static fi.swarco.CONSTANT.*;
import static fi.swarco.CONSTANT.INT_RET_OK;
public class GetOmniaData {
    private static Logger logger = Logger.getLogger(GetOmniaData.class.getName());
    public static void sendGetOmniaData()  {
        int intSleep;
        String url1;
        String inputLine;
        int iRet;
        MakeSendJsonOperations ms = new MakeSendJsonOperations();
        MessageUtils mu = new MessageUtils();
        LogUtilities mfl = new LogUtilities();
        int responseCode;
        BufferedReader in;
        logger.info("moi Start");
        JSwarcoproperties sw = new JSwarcoproperties();
        iRet =sw.getSwarcoProperties();
        if (iRet != INT_RET_OK) {
            System.out.println(" Error reading Swarco properties ! ");
            System.exit(1);
        }

        FileOperations fo = new  FileOperations();
        iRet=fo.initFileOperations();
        if (iRet == UNSUCCESSFUL_FILE_OPERATION ) {
            logger.info("Unsuccessful file Operations iRet =" + iRet);
        }
        int iloop=1;
        byte bXorResult;
        MakeSendJsonOperations.setSleep(Integer.valueOf(sw.getOmniaClientWorkWaitSleep()));
        while (iloop == 1) {
            try {
                for (long i = 1; i < 2; i++) {
                    iRet = ms.PollOfWorks();
                    if (iRet == THERE_IS_WORK) {
                        iRet = ms.MakeSendOmniaOperations();
                        if (iRet != OMNIA_DATA_PICK_OK) {
                            // update task and write log *********************************************
                            if (iRet != OMNIA_EMPTY_WORK_LIST) {
                                iRet = mfl.MakeFullLogOperations(
                                        SwarcoEnumerations.LoggingDestinationType.OMNIA_CLIENT,
                                        SwarcoEnumerations.ApiMessageCodes.DATAERROR,
                                        "Unsuccessful data send ");
                                logger.info("Unsuccessful data send ");
                            }
                        } else {
                            // if no value do not send RETHINK
                            String strHelp1 = MakeSendJsonOperations.getJSonDataForTransfer();
                            if (strHelp1.equals(NO_VALUE)) {
                                iRet = mfl.MakeFullLogOperations(
                                        SwarcoEnumerations.LoggingDestinationType.OMNIA_CLIENT,
                                        SwarcoEnumerations.ApiMessageCodes.DATAERROR,
                                        "no data to send  strHelp1 = " + strHelp1);
                                logger.info("no data to send  strHelp1 = " + strHelp1);
                            } else {
                                strHelp1 = mu.DecodeJsonPercentDecimal(strHelp1);
                                strHelp1 = URLEncoder.encode(strHelp1, StandardCharsets.UTF_8.toString());
                                url1 = sw.getOmniaClientUrl();
                 //               logger.info("moi url1 =" + url1);
                                url1 = url1 + strHelp1;
                                bXorResult = XORChecksumShort.xor(url1);
                                url1 = url1 + "&chk=" + bXorResult;
                                logger.info("****** Length of url1.length() =" + url1.length());
               //                 logger.info("moi \"&chk=\" =" + bXorResult);
                                URL obj = new URL(url1);
                                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                                con.setRequestProperty("Content-Type", "application/json,charset=UTF-8");
                                con.setDoOutput(true);
                                con.setRequestMethod("GET");
                                responseCode = con.getResponseCode();
                                logger.info("bef Response Code : " + responseCode);
                                if (responseCode != 200) {
                                    in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                                    iRet = mfl.MakeFullLogOperations(
                                            SwarcoEnumerations.LoggingDestinationType.OMNIA_CLIENT,
                                            SwarcoEnumerations.ApiMessageCodes.ERROR,
                                            url1);
                                } else {
                                    // delete done task from db here RETHINK
                                    // wrong place this must be done Imidiadly  after One tasks has been done
                                    iRet = ms.DeleteDoneTaskFromWorkDb();
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
