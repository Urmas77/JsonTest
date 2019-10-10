package fi.swarco.omniaDataTransferServices.omniaDataPickHTTPServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import fi.swarco.dataHandling.oldDataHandling.MakeReceiveClientRequestOperations;
import fi.swarco.dataHandling.MakeReceiveJsonOperations;
import fi.swarco.messageHandling.MapHandle;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import static fi.swarco.CONSTANT.*;

public class OmniaDataPickHTTPServer {
    static Logger logger = Logger.getLogger(OmniaDataPickHTTPServer.class.getName());

    public static void main(String[] args) throws Exception {
        try{
        HttpServer server = HttpServer.create(new InetSocketAddress(8888), 0);
        // server.createContext("/", new MyHandler3());
        //server.createContext("/", new MyHandler());
        //server.createContext("/", new MyHandler4.MyHandlerGetCloudRequest());
        server.createContext("/", new MyHandlerGetCloudRequest());
        //    server.createContext("/", new MyHandler2());
        server.setExecutor(null); // creates a default executor
        logger.info("Entering application.");
        server.start();
    } catch (Exception e) {
           // logger.info("Huti pattern1=" + pattern1);
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            logger.info("e.getMessage() =" + e.getMessage());
        }
        }

    // Only one path/method combination is allowed
    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String method = t.getRequestMethod();
            String path = t.getRequestURI().getPath();
            String uquery = t.getRequestURI().getQuery();
            logger.info(" t.getRequestURI().getPath() path = " + path);
            logger.info("uquery                            =" + uquery);
            logger.info("method =" + method);
            System.out.println(" t.getRequestURI().getPath() path = " + path);
            System.out.println("uquery                            =" + uquery);
            System.out.println("method =" + method);
            String response1 = "Hello, world!";
            String response2 = "Hello, wrong world!";
            OutputStream os = t.getResponseBody();
            //if ((path.equals("/"))&& ("POST".equals(method))){
            if ((path.equals("/")) && ("GET".equals(method))) {
                t.sendResponseHeaders(200, response1.length());
                os.write(response1.getBytes());
                logger.info("uquery                            =" + uquery);
                logger.info("response1.length() = " + response1.length());
                //logger.info("response1.getBytes() = " + response1.getBytes());
                logger.info("response1 = " + response1);
            } else {
                t.sendResponseHeaders(404, response2.length());
                os.write(response2.getBytes());
                logger.info("response2.length() = " + response2.length());
                logger.info("response2.getBytes() = " + response2.getBytes());
            }
            os.close();
        }
    }

    static class MyHandler2 implements HttpHandler {
        @Override
        // Timestamp request handler
        public void handle(HttpExchange t) throws IOException {
            String method = t.getRequestMethod();
            String path = t.getRequestURI().getPath();
            logger.info("*****path = " + path);
            String query = t.getRequestURI().getQuery();
            String strResponse;
            int iRet = 0;
            long lRetTimestamp = 0;
            byte bXorResult;
            String cityName;
            MapHandle jMap = new MapHandle();
            //XORChecksumShort jXor = new XORChecksumShort();
            //GetCurrentTimeStamp jTime = new  GetCurrentTimeStamp();
            jMap.setPath(path);
            jMap.setMethod(method);
            jMap.setQuery(query);
            jMap.MapQueryString();
            iRet = jMap.DefineCityName();
            if (iRet == 1) {
                cityName = jMap.getCityName();
                System.out.println("from OmniaCloudHTTPServer.MyHandler2 cityName =" + cityName);
            }
        }
    }
    static class MyHandlerGetCloudRequest implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            int messageReceived = MESSAGE_NOT_RECEIVED_OK;
            int iRet = 0;
            //  MESSAGE_RECEIVED_OK;
            String method = t.getRequestMethod();
            String path = t.getRequestURI().getPath();
            String uquery = t.getRequestURI().getQuery();
            logger.info(" t.getRequestURI().getPath() path = " + path);
            logger.info("uquery                            =" + uquery);
            logger.info("method =" + method);
            String response1 = "Hello, world!";
            String response2 = "Hello, wrong world!";
            OutputStream os = t.getResponseBody();
            logger.info("os.toString() = " + os.toString());
            if ((path.equals("/")) && ("GET".equals(method))) {
                logger.info("*** here ***");
                MakeReceiveClientRequestOperations hd = new MakeReceiveClientRequestOperations();
                hd.setRequestDataData(uquery);
                iRet =hd.MakeClientRequestOperations();
                logger.info("bef hd.getReponseMessage() iRet= "+ iRet);
                logger.info("bef hd.getReponseMessage() = "+ hd.getReponseMessage());
                response1= hd.getReponseMessage();
                 response1 = URLEncoder.encode(response1, StandardCharsets.UTF_8.toString());
                if (iRet==INT_RET_OK) {
                    t.sendResponseHeaders(200, response1.length());
                    logger.info("uquery                            =" + uquery);
                    logger.info("response1.length() = " + response1.length());
                    logger.info("response1 = " + response1);
                // old flush
                    os.write(response1.getBytes());
                    os.close();
                    os.flush();
                    messageReceived = MESSAGE_RECEIVED_OK;
                }  else {
                    t.sendResponseHeaders(404, response2.length());
                   // os.flush();
                    os.write(response2.getBytes());
                    logger.info("response2.length() = " + response2.length());
                    logger.info("response2.getBytes() = " + response2.getBytes());
                    os.close();
                    os.flush();
                }
            } else {
                t.sendResponseHeaders(404, response2.length());
                os.flush();
                os.write(response2.getBytes());
                logger.info("response2.length() = " + response2.length());
                logger.info("response2.getBytes() = " + response2.getBytes());
                os.close();
            }
            if (messageReceived == MESSAGE_RECEIVED_OK) {
            }
        }
    }
    static class MyHandlerOmniaJson implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            int messageReceived = MESSAGE_NOT_RECEIVED_OK;
            int iRet = 0;
            //  MESSAGE_RECEIVED_OK;
            String method = t.getRequestMethod();
            String path = t.getRequestURI().getPath();
            String uquery = t.getRequestURI().getQuery();
            logger.info(" t.getRequestURI().getPath() path = " + path);
            logger.info("uquery                            =" + uquery);
            logger.info("method =" + method);
            System.out.println(" t.getRequestURI().getPath() path = " + path);
            System.out.println("uquery                            =" + uquery);
            System.out.println("method =" + method);
            String response1 = "Hello, world!";
            String response2 = "Hello, wrong world!";
            OutputStream os = t.getResponseBody();
            logger.info("os.toString() = " + os.toString());
            if ((path.equals("/")) && ("GET".equals(method))) {
                t.sendResponseHeaders(200, response1.length());
                os.flush();
                os.write(response1.getBytes());
                logger.info("uquery                            =" + uquery);
                logger.info("response1.length() = " + response1.length());
                logger.info("response1.getBytes() = " + response1.getBytes());
                os.close();
                messageReceived = MESSAGE_RECEIVED_OK;
            } else {
                t.sendResponseHeaders(404, response2.length());
                os.flush();
                os.write(response2.getBytes());
                logger.info("response2.length() = " + response2.length());
                logger.info("response2.getBytes() = " + response2.getBytes());
                os.close();
            }
            if (messageReceived == MESSAGE_RECEIVED_OK) {
                MakeReceiveJsonOperations hd = new MakeReceiveJsonOperations();
                MakeReceiveJsonOperations.setPseudoJsonData(uquery);
                try {
                    iRet = MakeReceiveJsonOperations.MakeReceiveOmniaOperations();
                } catch (SQLException e) {
                    logger.info(ExceptionUtils.getRootCauseMessage(e));
                    logger.info(ExceptionUtils.getFullStackTrace(e));
                    e.printStackTrace();
                }
                logger.info ("iRet = " + iRet);
            }
        }
    }
}