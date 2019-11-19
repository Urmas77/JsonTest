package fi.swarco.omniaDataTransferServices.omniaClient;
import java.io.*;
import org.apache.commons.lang.exception.ExceptionUtils;
import java.sql.SQLException;
import static fi.swarco.CONSTANT.*;
import static fi.swarco.omniaDataTransferServices.omniaClient.GetOmniaData.sendGetOmniaData;
public class OmniaClient {
    private long Rounds = 100000000;
    public static void main(String[] args) {
        int iRett;
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
        sendGetOmniaData();
    }
}
