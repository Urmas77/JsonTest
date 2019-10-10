package fi.swarco.omniaDataTransferServices.omniaCloudHTTPServer;
        import java.io.IOException;
        import java.io.OutputStream;
        import java.net.InetSocketAddress;
        import java.sql.SQLException;

        import com.sun.net.httpserver.HttpExchange;
        import com.sun.net.httpserver.HttpHandler;
        import com.sun.net.httpserver.HttpServer;
        import fi.swarco.dataHandling.MakeReceiveJsonOperations;
        import fi.swarco.messageHandling.MapHandle;
        import org.apache.commons.lang.exception.ExceptionUtils;
        import org.apache.log4j.Logger;
        import static fi.swarco.CONSTANT.MESSAGE_NOT_RECEIVED_OK;
        import static fi.swarco.CONSTANT.MESSAGE_RECEIVED_OK;
public class OmniaCloudHTTPServer {
    private static Logger logger = Logger.getLogger(OmniaCloudHTTPServer.class.getName());
    public static void main(String[] args) throws Exception {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8888), 0);
            server.createContext("/", new MyHandler3.MyHandlerOmniaJson());
            server.setExecutor(null); // creates a default executor
            logger.info("Entering application.");
            server.start();
        } catch (Exception e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            logger.info("e.getMessage() =" + e.getMessage());
        }
    }
    // Only one path/method combination is allowed

    //	accessToken	String	9F3F1A11133A6377B7B7168BBFCEA		256 bit authorization key provided by Swarco
//        cityName 	String
//        permanentData				permanentdata of Intersection
//        measurementData				current data traffic data
//        measurementTime 				time in seconds measurementt ime
//        messageTimestamp	String			UNIX / Epoch timestamp of the request
//        checksum
    static class MyHandler3 implements HttpHandler {
        @Override
        // Timestamp request handler
        public void handle(HttpExchange t) throws IOException {
            logger.info("MyHandler3* start");
            String method= t.getRequestMethod();
            String path =   t.getRequestURI().getPath();
            logger.info("MyHandler3*****path = "+ path );
            String query =   t.getRequestURI().getQuery();
            logger.info("MyHandler3*****query = "+ query );
            String strResponse;
            int iRet=0;
            long lRetTimestamp=0;
            byte bXorResult;
            String cityName;
            MapHandle jMap = new MapHandle();
            //XORChecksumShort jXor = new XORChecksumShort();
            //GetCurrentTimeStamp jTime = new  GetCurrentTimeStamp();
            jMap.setPath(path);
            jMap.setMethod(method);
            jMap.setQuery(query);
            jMap.MapQueryString();
            //  iRet=jMap.MeasurementDataCheckSumCheck();
            if  (iRet!=1) {
                logger.info("MyHandler3 from OmniaCloudHTTPServer.MyHandler2 wrong checksum ");
            } else {
                iRet=jMap.DefineCityName();
                if (iRet==1) {
                    cityName=jMap.getCityName();
                    logger.info("from OmniaCloudHTTPServer.MyHandler2 cityName ="+ cityName);
                    System.out.println("from OmniaCloudHTTPServer.MyHandler2 cityName ="+ cityName);
                }
            }
        }
        static class MyHandlerOmniaJson  implements HttpHandler {
            @Override
            public void handle(HttpExchange t) throws IOException {
                int messageReceived = MESSAGE_NOT_RECEIVED_OK;
                int iRet =0;
                MakeReceiveJsonOperations hd = new MakeReceiveJsonOperations();
                //  MESSAGE_RECEIVED_OK;
                String method = t.getRequestMethod();
                String path   = t.getRequestURI().getPath();
                String uquery = t.getRequestURI().getQuery();
                logger.info(" t.getRequestURI().getPath() path = " + path);
                logger.info("uquery                            =" + uquery);
                logger.info("method =" + method);
                String response1 = "Hello, world!";
                String response2 = "Hello, wrong world!";
                OutputStream os = t.getResponseBody();
                logger.info ("os.toString() = " + os.toString());
                if ((path.equals("/"))&& ("GET".equals(method))){
                    t.sendResponseHeaders(200, response1.length());
                    os.write(response1.getBytes());
               //     logger.info("uquery                            =" + uquery);
                    logger.info ("response1.length() = "+response1.length());
                    logger.info ("response1.getBytes() = "+response1.getBytes());
                    os.close();
                    messageReceived=MESSAGE_RECEIVED_OK;
                }else {
                    t.sendResponseHeaders(404, response2.length());
                    os.write(response2.getBytes());
                    logger.info ("response2.length() = "+response2.length());
                    logger.info ("response2.getBytes() = "+response2.getBytes());
                    os.close();
                }
                if  (messageReceived==MESSAGE_RECEIVED_OK) {
                    // RETHINK JIs 05.10 2019
                    // MakeReceiveJsonOperations hd = new MakeReceiveJsonOperations();
                    hd.setPseudoJsonData(uquery);
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
}