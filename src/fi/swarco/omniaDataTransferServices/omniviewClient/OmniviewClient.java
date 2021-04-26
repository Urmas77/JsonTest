package fi.swarco.omniaDataTransferServices.omniviewClient;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.connections.ConWrapper;
import fi.swarco.omniaDataTransferServices.omniaClient.StartOmniaClient;
import fi.swarco.properties.JSwarcoproperties;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import static fi.swarco.CONSTANT.*;
import static fi.swarco.omniaDataTransferServices.omniaClient.GetOmniaData.sendGetOmniaData;
import static fi.swarco.omniaDataTransferServices.omniviewClient.GetOmniviewData.sendGetOmniviewData;

public class OmniviewClient {
// special client for Helsiki Omniview data transfer
    public static SwarcoEnumerations.ConnectionType getSqlServerConnectionType() { return GSqlServerConnectionType;};
    public static SwarcoEnumerations.ConnectionType GSqlServerConnectionType=SwarcoEnumerations.ConnectionType.NOT_DEFINED;
    public static String GHTTPOmniaClientPort=NO_VALUE;
    public static int GMaxClientMaxControllersInMessage=INT_RET_NOT_OK;
    private long Rounds = 100000000;
    private static Logger logger = Logger.getLogger(StartOmniaClient.class.getName());
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
        String strHelp1 =sw.getClientMaxControllersInMessage();
        GMaxClientMaxControllersInMessage =Integer.parseInt(strHelp1.trim());
        System.out.println(" strHelp1.trim() = " + strHelp1.trim());
        if (GMaxClientMaxControllersInMessage==INT_RET_NOT_OK ) {
            System.out.println(" Error GMaxClientMaxControllersInMessage is not set ! " + GMaxClientMaxControllersInMessage);
            System.exit(1);
        }
        System.out.println("Heippa  !!!!");
        if (args.length > 0) {
            System.out.println("args.length =" + args.length);
            strHelp22 = args[0];
        }
        ConWrapper cW;
        cW = sw.FillServerWrapper(strHelp22);
        GHTTPOmniaClientPort=cW.getHttpClientPort();
        if (strHelp22.equals("lahti")) {
            GSqlServerConnectionType=SwarcoEnumerations.ConnectionType.SQLSERVER_LOCAL_lAHTI;
            System.out.println("Only parametervalue 'helsinkiomniview' is allowed!");
            System.exit(1);
        } else if (strHelp22.equals("helsinki")) {
            GSqlServerConnectionType=SwarcoEnumerations.ConnectionType.SQLSERVER_LOCAL_HELSINKI;
            System.out.println(" Only parametervalue 'helsinkiomniview' is allowed!");
            System.exit(1);
        } else if (strHelp22.equals("helsinkiomniview")) {
            GSqlServerConnectionType=SwarcoEnumerations.ConnectionType.SQLSERVER_LOCAL_HELSINKI_OMNIVIEW;
        }
        System.out.println("Heippa  2 !!!!");
       // logger.info(" getSqlServerConnectionType() = ", getSqlServerConnectionType());
        StartOmniviewClient start = new StartOmniviewClient();
        try {
            iRett = start.ClearReCreateWorkTable();
            if (iRett != INT_RET_OK) {
               System.out.println(" Error clearing WorkTable iRett =  " + iRett);
               System.exit(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(ExceptionUtils.getRootCauseMessage(e));
            System.out.println(ExceptionUtils.getFullStackTrace(e));
        }
        System.out.println(" loppui tänne lisää koodia  ");
           sendGetOmniviewData();
    }
}
