package fi.swarco.testPrograms;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.connections.SwarcoConnections;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.sql.Connection;
import static fi.swarco.CONSTANT.INT_RET_OK;

public class T5 {
    private static Connection gSqlCon;
    private static Logger logger = Logger.getLogger(T5.class.getName());
    private static SwarcoEnumerations.ConnectionType SqlConnectionType = SwarcoEnumerations.ConnectionType.NOT_DEFINED;
    SwarcoEnumerations.RequestOriginType requestOrigin = SwarcoEnumerations.RequestOriginType.NORMALROAD;
    public void setRequestOrigin(SwarcoEnumerations.RequestOriginType prequestOrigin) {
        requestOrigin = prequestOrigin;
    }
    public static int MakeConnection(SwarcoEnumerations.ConnectionType pSqlCon) {
        SwarcoConnections vg = new SwarcoConnections();
        logger.info("pSqlCon = " + pSqlCon);
        int iRet = vg.MakeConnection(pSqlCon);
        if (iRet != INT_RET_OK) {
            return iRet;
        }
        SqlConnectionType = pSqlCon;
        logger.info("SqlConnectionType = " + SqlConnectionType);
        gSqlCon = vg.getSqlCon();
        return INT_RET_OK;
    }
    public static void main(String[] args) throws IOException, InterruptedException{
        try {


            logger.info("Heippa");
            int iRet = MakeConnection(SwarcoEnumerations.ConnectionType.SQLSERVER_LOCAL_JOMNIATEST);
            if (iRet != INT_RET_OK) {
                logger.info("Ei kantayhteytt� lopetetaan");
                System.exit(0);
            }
            logger.info("KantaYhteys OK! jippii");

            gSqlCon.close();
            logger.info("KantaYhteys katkaistu yritetään uutta ");

            Thread.currentThread().sleep(5000); // 10 seconds delay before restart
            if (!(gSqlCon.isValid(0))) {
                logger.info("KantaYhteys ei ollu validi yritetään uutta !");
                iRet = MakeConnection(SwarcoEnumerations.ConnectionType.SQLSERVER_LOCAL_JOMNIATEST);
                if (iRet != INT_RET_OK) {
                    logger.info("Ei kantayhteytt� lopetetaan");
                    System.exit(0);
                }
                logger.info("Saatiin kantayhteys jatketaa");
            }
            logger.info("loppus muuten vaan");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
        }
    }
}
