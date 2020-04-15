package fi.swarco.omniaInfluxClient;
import java.io.*;
import java.sql.SQLException;
import java.util.List;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.dataHandling.MakeSendTimeSeriesOperations;
import fi.swarco.dataHandling.omniaClientDataHandling.SerieLinkClientDataLevel;
import fi.swarco.dataHandling.omniaClientDataHandling.TimeSeriesClientDataLevel;
import fi.swarco.dataHandling.pojos.SerieLink;
import fi.swarco.omniaDataTransferServices.FileOperations;
import fi.swarco.omniaDataTransferServices.LogUtilities;
import fi.swarco.omniaDataTransferServices.omniaClient.DeleteLog;
import fi.swarco.omniaDataTransferServices.omniaClient.StartOmniaClient;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import static fi.swarco.CONSTANT.*;
public class OmniaInfluxClient {
    private long Rounds = 100000000;
    private static Logger logger = Logger.getLogger(TimeSeriesClientDataLevel.class.getName());
    public static void main(String[] args) {
        int iRet,iRett;
        FileOperations fo =new FileOperations();
        LogUtilities mfl = new LogUtilities();
        System.out.println("Heippa  !!!!");
        if (args.length > 0) {
            System.out.println("args.length =" + args.length);
            String strHelp22 = args[0];
            System.out.println("strHelp22 =" + strHelp22);
            DeleteLog dd = new DeleteLog();
            iRett = DeleteLog.CloseAndDeleteFile(strHelp22);
        }
        System.out.println("Heippa  2 !!!!");
        StartOmniaClient start = new StartOmniaClient();
        try {
            iRett = start.ClearWorkTable();
            if (iRett != INT_RET_OK) {
                if (iRett == EXTRACLEANUP_TASK_OK) {
                    System.out.println("  EXTRACLEANUP_TASK_OK iRett = " + iRett);
                } else {
                    System.out.println(" Error clearing WorkTable iRett =  " + iRett);
                    System.exit(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(ExceptionUtils.getRootCauseMessage(e));
            System.out.println(ExceptionUtils.getFullStackTrace(e));
        }

        System.out.println("Heippa  2 !!!!");
       // InfluxJavaWrite  jw = new InfluxJavaWrite();
       // jw.setUp1();
       // iRet =jw.WriteFileToInflux111();
       // System.exit(1);
       // find tasks list
        MakeSendTimeSeriesOperations ms = new MakeSendTimeSeriesOperations();
       int iLoop =1;
        while (iLoop == 1) {
            try {
                for (long i = 1; i < 2; i++) {
                   iRet =ms.PollOfTimeSerieWorks();
                   if (iRet == THERE_IS_WORK) {
                      iRet = ms.MakeSendOmniaTimeSeriesOperations();
                        if (iRet != OMNIA_DATA_PICK_OK) {
                            // update task and write log *********************************************
                            if (iRet != OMNIA_EMPTY_WORK_LIST) {
                                if (iRet != INT_RET_OK) {
                                    if (iRet != OMNIA_DATA_PICK_NOT_OK) {
                                        logger.info("Unsuccessful data send1 iRet = " + iRet);
                                        iRet = mfl.MakeFullLogOperations(
                                                SwarcoEnumerations.LoggingDestinationType.OMNIA_CLIENT,
                                                SwarcoEnumerations.ApiMessageCodes.DATAERROR,
                                                "Unsuccessful data send ");
                                        logger.info("Unsuccessful data send iRet = " + iRet);
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(ExceptionUtils.getRootCauseMessage(e));
                System.out.println(ExceptionUtils.getFullStackTrace(e));
            }
        }
// check that all timeseries exists in Influx side
// if not exists create needed time series
// do until all series are ready
// create influx file and it lines
// write using curl


// handle response
            SerieLinkClientDataLevel ts = new SerieLinkClientDataLevel();
            iRet = ts.MakeConnection2(SwarcoEnumerations.ConnectionType.SQLSERVER_LOCAL_JOMNIATEST, SwarcoEnumerations.ConnectionType.INFLUX_LOCAL);
            if (iRet != DATABASE_CONNECTION_OK) {
                logger.info("No Database conncection iRet =" + iRet);
                System.exit(0);
            }
            List<SerieLink> sList=null;
            try{
                sList = ts.GetNoSeriesLinkData(100005);
            } catch (SQLException e) {
                e.printStackTrace();
                logger.info(ExceptionUtils.getRootCauseMessage(e));
                logger.info(ExceptionUtils.getFullStackTrace(e));
            }
            if (!(sList.isEmpty())) {
               if (!(sList.get(0).getOmniaCode() == INT_EMPTY_ELEMENT)) {
// go thru list and create new serienames
                  iRet = ts.CreateNeededSerieNames();
                  if (iRet != SERIENAMES_CREATED_OK) {
                     logger.info("Error on creating new serie names" + iRet);
                     System.exit(0);
                  }
               }
            }




            fo.initFileOperations();
            // NEW_LINE_LINUX
            String strInfluxFileName = fo.getFullInfluxFileNameOld("jatri1");
            iRet = fo.closeAndDeleteNormalFile(strInfluxFileName);
            strInfluxFileName = "@" + strInfluxFileName;
            iRet = fo.addOmniaInfluxLine("cpu_load_short,host=server111 value=0.12345 1422568543702900257", "jatri1");
            iRet = fo.addOmniaInfluxLine(NEW_LINE_LINUX, "jatri1");
            iRet = fo.addOmniaInfluxLine("cpu_load_short,host=server02 value=0.678999 1422568543702900266", "jatri1");

            //


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

            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));

        }

    }
}