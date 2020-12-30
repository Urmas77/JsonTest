package fi.swarco.omniaDataTransferServices.debtClient;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.connections.ConWrapper;
import fi.swarco.properties.JSwarcoproperties;
import org.apache.commons.lang.exception.ExceptionUtils;
import java.sql.SQLException;
import static fi.swarco.CONSTANT.*;
import org.apache.log4j.Logger;
public class DebtClient {
    private static Logger logger = Logger.getLogger(DebtClient.class.getName());
    public static void main(String[] args) {
        int iRett;
        int iRet=INT_RET_OK;
        String strHelp22=NO_VALUE;
        JSwarcoproperties sw = new JSwarcoproperties();
        iRet =sw.getSwarcoProperties();
        if (iRet != INT_RET_OK) {
            System.out.println(" Error reading Swarco properties ! ");
            System.exit(1);
        }
        System.out.println("Heippa  !!!!");
        if (args.length > 0) {
            System.out.println("args.length =" + args.length);
            strHelp22 = args[0];
        }
        ConWrapper cW;
        cW = sw.FillServerWrapper(strHelp22);
   //     GHTTPOmniaClientPort=cW.getHttpClientPort();
        if (strHelp22.equals("lahti")) {
    //        GSqlServerConnectionType=SwarcoEnumerations.ConnectionType.SQLSERVER_LOCAL_lAHTI;
        } else if (strHelp22.equals("helsinki")) {
     //       GSqlServerConnectionType=SwarcoEnumerations.ConnectionType.SQLSERVER_LOCAL_HELSINKI;
        } else if (strHelp22.equals("helsinkiomniview")) {
       //     GSqlServerConnectionType=SwarcoEnumerations.ConnectionType.SQLSERVER_LOCAL_HELSINKI_OMNIVIEW;
        }
        System.out.println("Heippa  2 !!!!");
        // StartOmniaClient start = new StartOmniaClient();
        try {
            System.out.println("Heippa  2 !!!!");
            // may be something here
         //    StartOmniaClient start = new StartOmniaClient();
        /*    if (iRett != INT_RET_OK) {
                if (iRett == EXTRACLEANUP_TASK_OK) {
                    System.out.println("  EXTRACLEANUP_TASK_OK iRett = " + iRett);
                } else {
                    System.out.println(" Error clearing WorkTable iRett =  " + iRett);
                    System.exit(1);
                }
            } */
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            System.out.println(ExceptionUtils.getRootCauseMessage(e));
            System.out.println(ExceptionUtils.getFullStackTrace(e));
        }
//        sendGetOmniaData();
    }
}


