package fi.swarco.influxoperations;
import fi.swarco.omniaDataTransferServices.FileOperations;
import fi.swarco.properties.JSwarcoproperties;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import static fi.swarco.CONSTANT.INT_RET_OK;
import static fi.swarco.CONSTANT.NO_VALUE;
public class InfluxJavaWrite {
    static Logger logger = Logger.getLogger(InfluxJavaWrite.class.getName());
    FileOperations  fo = new FileOperations();
    JSwarcoproperties sw = new JSwarcoproperties();
    String strInfluxFileName=NO_VALUE;
    String InfluxDbName1 =NO_VALUE;
    String InfluxDbUser=NO_VALUE;
    String InfluxPassWord=NO_VALUE;
    String InfluxConnectionUrlStart=NO_VALUE;
    public void setUp1()  {
        int iRet =fo.initFileOperations();
        iRet =sw.getSwarcoProperties();
        strInfluxFileName="@"+sw.getFilePathStringOmniaInflux() +sw.getFileNameInflux1()+".txt";  // RETHINK
        logger.info("strInfluxFileName= "+strInfluxFileName);
        InfluxConnectionUrlStart=sw.getInfluxConnUrlStart();
        InfluxDbName1 =sw.getInfluxDbName1();
        InfluxDbUser=sw.getInfluxdbuser();
        InfluxPassWord=sw.getInfluxpassword();
    }
    public int WriteFileToInflux(){
        int iRet=INT_RET_OK;
        String strDbPart = InfluxConnectionUrlStart + "/write?db=" + InfluxDbName1;
        String[] command = {"curl", "-i", "-XPOST",strDbPart,"--data-binary",strInfluxFileName};
        // String[] command = {"curl", "-k", "-v", "-u","admin2:0xdRv63RKq2MtA326BNGQAI6yA1QNGO09enamGxI",
        //         "-d", "{\"username\":\"test\",\"token_code\":\"246212\"}","-H", "Content-Type: application/json", "https://192.168.101.59/api/v1/auth/"};
        //String[] command = {"curl", "-i", "-XPOST", "http://192.168.111.121:8086/write?db=Jatri62", "--data-binary", strInfluxFileName};
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.redirectErrorStream(true);
        String curlResult = "";
        String line = "";
        try {
            Process process = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while (true) {
                line = r.readLine();
                if (line == null) {
                    break;
                }
                curlResult = curlResult + line;
                logger.info("curlResult ="+curlResult);
                return INT_RET_OK;  // RETHINK
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
        }
        return INT_RET_OK;
    }
    // This is for test purposes
    public int WriteFileToInflux111(){
        int iRet=INT_RET_OK;
        //String strDbPart = InfluxConnectionUrlStart + "/write?db=" + InfluxDbName1;
       // String[] command = {"curl", "-i", "-XPOST",strDbPart,"--data-binary",strInfluxFileName};
        // String[] command = {"curl", "-k", "-v", "-u","admin2:0xdRv63RKq2MtA326BNGQAI6yA1QNGO09enamGxI",
        //         "-d", "{\"username\":\"test\",\"token_code\":\"246212\"}","-H", "Content-Type: application/json", "https://192.168.101.59/api/v1/auth/"};
        String[] command = {"curl", "-i", "-XPOST", "http://192.168.111.121:8086/write?db=Jatri62", "--data-binary", strInfluxFileName};
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.redirectErrorStream(true);
        String curlResult = "";
        String line = "";
        try {
            Process process = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while (true) {
                line = r.readLine();
                if (line == null) {
                    break;
                }
                curlResult = curlResult + line;
                logger.info("curlResult ="+curlResult);
                return INT_RET_OK;  // RETHINK
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
        }
        return INT_RET_OK;
    }



}



