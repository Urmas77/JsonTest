package fi.swarco.omniaDataTransferServices.omniaCloudHTTPServerLahti;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import fi.swarco.connections.ConWrapper;
import fi.swarco.dataHandling.MakeReceiveJsonOperations;
import fi.swarco.properties.JSwarcoproperties;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.SQLException;

import static fi.swarco.CONSTANT.*;
import static fi.swarco.CONSTANT.MESSAGE_RECEIVED_OK;
        import fi.swarco.SwarcoEnumerations;
        import fi.swarco.connections.ConWrapper;
        import fi.swarco.dataHandling.MakeReceiveJsonOperations;
        import fi.swarco.properties.JSwarcoproperties;
        import org.apache.commons.lang.exception.ExceptionUtils;
        import org.apache.log4j.Logger;
        import static fi.swarco.CONSTANT.MESSAGE_NOT_RECEIVED_OK;
        import static fi.swarco.CONSTANT.MESSAGE_RECEIVED_OK;
        import static fi.swarco.CONSTANT.*;
public class OmniaCloudHTTPServerLahti {
    public static  void setOmniaServerName(String pOmniaServerName ) {GOmniaHttpServerName = pOmniaServerName;};
    public static String getOmniaServerName() { return GOmniaHttpServerName;};
    public static  String GOmniaHttpServerName =NO_VALUE;
    private static String strOmniaMysqlFromReadToWrite="OFF";
    public static String getOmniaMysqlFromReadToWrite() { return strOmniaMysqlFromReadToWrite;};
    private static void setOmniaMysqlFromReadToWrite(String pOmniaMysqlFromReadToWrite)
    { strOmniaMysqlFromReadToWrite= pOmniaMysqlFromReadToWrite;};

    private static Logger logger = Logger.getLogger(fi.swarco.omniaDataTransferServices.omniaCloudHTTPServerLahti.OmniaCloudHTTPServerLahti.class.getName());
    private static JSwarcoproperties swarvop;
    public static void main(String[] args) {
        int iRet =INT_RET_OK;
        String strServer=NO_VALUE;
        String strServerPort=NO_VALUE;
        ConWrapper cW1;
        swarvop    = new JSwarcoproperties();
        iRet = swarvop.getSwarcoProperties();
        if (iRet != INT_RET_OK) {
            logger.info ("Ei saatu properteja");
            System.exit(0);
        }
        //   setOmniaMysqlFromReadToWrite(swarvop.getOmniaMysqlFromReadToWrite());
        //   logger.info(" getOmniaMysqlFromReadToWrite() = " +getOmniaMysqlFromReadToWrite());

        if (args.length==0) {
            System.out.println("Ei argumentteja ");
        } else if  (args.length==1) {
            strServer = args[0];
            setOmniaServerName(strServer);
            logger.info("strServer = " + strServer);
        }
        cW1 =swarvop.FillServerWrapper(strServer);
        logger.info("cW1.getHttpServerPort() = " + cW1.getHttpServerPort());
        strServerPort=cW1.getHttpServerPort();
        if (strServerPort.equals(NO_VALUE)) {
            logger.info ("Ei saatu porttia");
            System.exit(0);
        }
        //   setOmniaMysqlFromReadToWrite(swarvop.getOmniaMysqlFromReadToWrite());
        //   logger.info(" getOmniaMysqlFromReadToWrite() = " +getOmniaMysqlFromReadToWrite());
        setOmniaMysqlFromReadToWrite(cW1.getDataTransferStatus());
        logger.info(" getOmniaMysqlFromReadToWrite() = " +getOmniaMysqlFromReadToWrite());
        try {
            int intPort = Integer.parseInt(strServerPort);
            HttpServer server = HttpServer.create(new InetSocketAddress(intPort), 0);
//            HttpServer server = HttpServer.create(new InetSocketAddress(8888), 0);
            server.createContext("/", new fi.swarco.omniaDataTransferServices.omniaCloudHTTPServerLahti.OmniaCloudHTTPServerLahti.MyHandlerOmniaJson());
        //    server.createContext("/", new fi.swarco.omniaDataTransferServices.omniaCloudHTTPServer.OmniaCloudHTTPServer.MyHandlerOmniaJson());
            server.setExecutor(null); // creates a default executor
            logger.info("Entering application. server.toString() =" + server.toString());
            server.start();
        } catch (Exception e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            logger.info("e.getMessage() =" + e.getMessage());
        }
    }
    static class MyHandlerOmniaJson implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            int messageReceived = MESSAGE_NOT_RECEIVED_OK;
            int iRet = 0;
            MakeReceiveJsonOperations hd = new MakeReceiveJsonOperations();
            String method = t.getRequestMethod();
            String path = t.getRequestURI().getPath();
            String uquery = t.getRequestURI().getQuery();
            //       logger.info(" t.getRequestURI().getPath() path = " + path);
            //        logger.info("uquery                            =" + uquery);
            logger.info("method =" + method);
            String response1 = "Hello, world!";
            String response2 = "Hello, wrong world!";
            OutputStream os = t.getResponseBody();
//           logger.info ("os.toString() = " + os.toString());
            if ((path.equals("/")) && ("GET".equals(method))) {
                t.sendResponseHeaders(200, response1.length());
                os.write(response1.getBytes());
                logger.info("response1.length() = " + response1.length());
                //              logger.info ("response1.getBytes() = "+response1.getBytes());
                os.close();
                messageReceived = MESSAGE_RECEIVED_OK;
            } else {
                t.sendResponseHeaders(404, response2.length());
                os.write(response2.getBytes());
                logger.info("response2.length() = " + response2.length());
                logger.info("response2.getBytes() = " + response2.getBytes());
                os.close();
            }
            if (messageReceived == MESSAGE_RECEIVED_OK) {
                hd.setPseudoJsonData(uquery);
                //   MakeReceiveJsonOperations.setPseudoJsonData(uquery);
                try {
                    iRet = hd.MakeReceiveOmniaOperations();
                } catch (SQLException e) {
                    logger.info(ExceptionUtils.getRootCauseMessage(e));
                    logger.info(ExceptionUtils.getFullStackTrace(e));
                    e.printStackTrace();
                }
            }
        }
    }
}
