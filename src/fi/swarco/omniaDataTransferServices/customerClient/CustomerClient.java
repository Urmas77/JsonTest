package fi.swarco.omniaDataTransferServices.customerClient;
import fi.swarco.messageHandling.HtppRequestWrapper;
import fi.swarco.messageHandling.ParameterWrapper;
import fi.swarco.messageHandling.ReturnWrapper;
import fi.swarco.omniaDataTransferServices.MessageUtils;
import fi.swarco.serviceOperations.XORChecksumShort;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import static fi.swarco.CONSTANT.*;
public class CustomerClient {
    static Logger logger = Logger.getLogger(CustomerClient.class.getName());
    private long Rounds = 100000000;
    public static void main(String[] args) {
        try {
            logger.info("Heippa");
            sendCloudRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    // HTTP GET request
    private static void sendCloudRequest() throws IOException {
        String url1;
        String url2;
        String url3;
        String inputLine;
        int responseCode;
        BufferedReader in;
        logger.info("moi Start");
        try {
            byte bXorResult;
            for (long i = 1; i < 2; i++) {
                logger.info("i=" + i);
                url1 = "http://localhost:8888/?";   //?
                ParameterWrapper pw = new ParameterWrapper();
                pw.MakeEmptyElement();
                pw.setOmniaCode(2);
//                pw.setIntersectionId(200013);
//                pw.setControllerId(100013);
//                pw.setDetectorId(164);
//                pw.setDetectorId(165);
//                pw.setDetectorId(166);
              //  pw.setStartTime("2019-12-05 00:00:00");
              //  pw.setEndTime("2019-12-05 15:11:00");
                HtppRequestWrapper ht = new HtppRequestWrapper();
                ReturnWrapper ret =ht.CurrentState(pw);
              // ReturnWrapper ret = ht.HistoryState(pw);
                if (!ret.getRetCode().equals(RET_OK)) {
                    logger.info("ret.toString() = " + ret.toString());
                    System.exit(0);
                }
                logger.info("ret.toString() = " + ret.toString());
                url3 = ret.getRetString();
                logger.info("moi url1 =" + url1);
                logger.info("moi url3 =" + url3);
                logger.info ("Request before encode checksum missing  url1 + url3 =" + url1 +url3);
                url3 = URLEncoder.encode(url3, StandardCharsets.UTF_8.toString());
                url1 = url1 + url3;
                bXorResult = XORChecksumShort.xor(url1);
                url1 = url1 + "&chk=" + bXorResult;
                logger.info("moi ur11= url1 + url3 ## ur11 = " + url1);
                logger.info("moi ur11= " + url1);
                URL obj = new URL(url1);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                con.setDoOutput(true);
                con.setRequestProperty("Content-Type", "application/json,charset=UTF-8");
                responseCode = con.getResponseCode();
                logger.info("bef Response Code : " + responseCode);
                if (responseCode != 200) {
                    in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                } else {
                    in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                }
                logger.info("jjjj after Response Code : " + responseCode);
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                    logger.info(" jjjjj inputLine = " + inputLine);
                    inputLine = URLDecoder.decode(inputLine, StandardCharsets.UTF_8.toString());
                    MessageUtils mu = new MessageUtils();
                    inputLine =  mu.reCreateJsonDecimal(inputLine);
                    logger.info(" jjjjj decoded inputLine = " + inputLine);
                }
                in.close();
                logger.info("jjjj response.length()=  " + response.length());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            logger.info("e.printStackTrace()=" + e.getMessage());
            System.exit(0);
        }
    }
}
