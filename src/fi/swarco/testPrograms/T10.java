package fi.swarco.testPrograms;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.connections.ConWrapper;
import fi.swarco.connections.SwarcoConnections;
import fi.swarco.properties.JSwarcoproperties;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import java.sql.Connection;
import static fi.swarco.CONSTANT.INT_RET_OK;
import static fi.swarco.CONSTANT.NO_VALUE;
public class T10 {
    private static Connection gSqlCon;
    private static JSwarcoproperties swarvop;
    private static Logger logger = Logger.getLogger(T5.class.getName());
    private static SwarcoEnumerations.ConnectionType SqlConnectionType = SwarcoEnumerations.ConnectionType.NOT_DEFINED;
    private SwarcoEnumerations.RequestOriginType requestOrigin = SwarcoEnumerations.RequestOriginType.NORMALROAD;
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
    public static void main(String[] args){
        int iRet=INT_RET_OK;
        swarvop    = new JSwarcoproperties();
        iRet = swarvop.getSwarcoProperties();
        if (iRet != INT_RET_OK) {
            logger.info ("Ei saatu properteja");
            System.exit(0);
        }
        String strHelp1 =NO_VALUE;
        if (args.length>0) {
            strHelp1 = args[0];
        }
        String strHelp2 ="";
        System.out.println("args.length =" + args.length);
        for (String s: args) {

            System.out.println(s);
        }
        // strHelp1= "e://log/example.log";
        if (args.length==0) {
            System.out.println("Ei argumentteja ");
        } else if  (args.length==1) {
            strHelp1 = args[0];
            System.out.println("strHelp1 = "+ strHelp1);
        } else {
            System.out.println("Liikaa argumentteja");
            for (String s: args) {
                System.out.println(s);
            }
        }
        try {
            logger.info("Heippa");
            ConWrapper cW = new ConWrapper();
            ConWrapper cW1 = new ConWrapper();
            cW.MakeEmptyElement();
            cW1.MakeEmptyElement();
            if (strHelp1.equals(NO_VALUE)) {
                iRet = MakeConnection(SwarcoEnumerations.ConnectionType.SQLSERVER_LOCAL_JOMNIATEST);
                cW =swarvop.FillConnectionWrapper(SwarcoEnumerations.ConnectionType.SQLSERVER_LOCAL_JOMNIATEST);
                logger.info("cW.getHttpServerPort() = " + cW.getHttpServerPort());
                cW1 =swarvop.FillServerWrapper("999");
                logger.info("cW1.getHttpServerPort() = " + cW1.getHttpServerPort());
            } else if (strHelp1.equals("lahti")) {
                iRet = MakeConnection(SwarcoEnumerations.ConnectionType.SQLSERVER_LOCAL_lAHTI);
                cW =swarvop.FillConnectionWrapper(SwarcoEnumerations.ConnectionType.SQLSERVER_LOCAL_lAHTI);
                logger.info("cW.getHttpServerPort() = " + cW.getHttpServerPort());
                cW1=swarvop.FillServerWrapper("lahti");
                logger.info("cW1.getHttpServerPort() = " + cW1.getHttpServerPort());
            } else if (strHelp1.equals("helsinki")) {
                iRet = MakeConnection(SwarcoEnumerations.ConnectionType.SQLSERVER_LOCAL_HELSINKI);
                cW =swarvop.FillConnectionWrapper(SwarcoEnumerations.ConnectionType.SQLSERVER_LOCAL_HELSINKI);
                logger.info("cW.getHttpServerPort() = " + cW.getHttpServerPort());
                cW1=swarvop.FillServerWrapper("helsinki");
                logger.info("cW1.getHttpServerPort() = " + cW1.getHttpServerPort());
            } else if (strHelp1.equals("helsinkiomniview")) {
                iRet = MakeConnection(SwarcoEnumerations.ConnectionType.SQLSERVER_LOCAL_HELSINKI_OMNIVIEW);
                cW =swarvop.FillConnectionWrapper(SwarcoEnumerations.ConnectionType.SQLSERVER_LOCAL_HELSINKI_OMNIVIEW);
                logger.info("cW.getHttpServerPort() = " + cW.getHttpServerPort());
                cW1=swarvop.FillServerWrapper("helsinkiomniview");
                logger.info("cW1.getHttpServerPort() = " + cW1.getHttpServerPort());
            } else {
                logger.info("Sqlserver kantayhteyden tyyppiä ei ole määritelty strHelp1 = " + strHelp1 + " !");
                System.exit(0);
            }
            if (iRet != INT_RET_OK) {
                logger.info("Ei kantayhteytta lopetetaan");
                System.exit(0);
            }
            logger.info("KantaYhteys OK! jippii");
            gSqlCon.close();
            logger.info("KantaYhteys katkaistu yritetään uutta ");
            Thread.sleep(5000); // 10 seconds delay before restart
            if (!(gSqlCon.isValid(0))) {
                logger.info("KantaYhteys ei ollu validi yritetään uutta !");
                if (strHelp1.equals(NO_VALUE)) {
                    iRet = MakeConnection(SwarcoEnumerations.ConnectionType.SQLSERVER_LOCAL_JOMNIATEST);
                } else if (strHelp1.equals("lahti")) {
                    iRet = MakeConnection(SwarcoEnumerations.ConnectionType.SQLSERVER_LOCAL_lAHTI);
                } else if (strHelp1.equals("helsinki")) {
                    iRet = MakeConnection(SwarcoEnumerations.ConnectionType.SQLSERVER_LOCAL_HELSINKI);
                } else if (strHelp1.equals("helsinkiomniview")) {
                    iRet = MakeConnection(SwarcoEnumerations.ConnectionType.SQLSERVER_LOCAL_HELSINKI_OMNIVIEW);
                } else {
                    logger.info("Sqlserver kantayhteyden tyyppiä ei ole määritelty strHelp1 = " + strHelp1 + " !");
                    System.exit(0);
                }
                if (iRet != INT_RET_OK) {
                    logger.info("Ei kantayhteytt� lopetetaan");
                    System.exit(0);
                }
                logger.info("Saatiin kantayhteys jatketaan");
            }
            logger.info("loppus muuten vaan");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
        }
    }
}
