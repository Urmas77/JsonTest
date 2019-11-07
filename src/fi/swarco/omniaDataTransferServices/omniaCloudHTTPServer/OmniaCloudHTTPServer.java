package fi.swarco.omniaDataTransferServices.omniaCloudHTTPServer;
        import java.io.IOException;
        import java.io.OutputStream;
        import java.net.InetSocketAddress;
        import java.sql.SQLException;
        import com.sun.net.httpserver.HttpExchange;
        import com.sun.net.httpserver.HttpHandler;
        import com.sun.net.httpserver.HttpServer;
        import fi.swarco.dataHandling.MakeReceiveJsonOperations;
        import org.apache.commons.lang.exception.ExceptionUtils;
        import org.apache.log4j.Logger;
        import static fi.swarco.CONSTANT.MESSAGE_NOT_RECEIVED_OK;
        import static fi.swarco.CONSTANT.MESSAGE_RECEIVED_OK;
public class OmniaCloudHTTPServer {
    private static Logger logger = Logger.getLogger(OmniaCloudHTTPServer.class.getName());
    public static void main(String[] args)  {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8888), 0);
            server.createContext("/", new MyHandlerOmniaJson());
            server.setExecutor(null); // creates a default executor
            logger.info("Entering application.");
            server.start();
        } catch (Exception e) {
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            logger.info("e.getMessage() =" + e.getMessage());
        }
    }
    static class MyHandlerOmniaJson  implements HttpHandler {
       @Override
       public void handle(HttpExchange t) throws IOException {
           int messageReceived = MESSAGE_NOT_RECEIVED_OK;
           int iRet =0;
           MakeReceiveJsonOperations hd = new MakeReceiveJsonOperations();
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
               logger.info ("response1.length() = "+response1.length());
               logger.info ("response1.getBytes() = "+response1.getBytes());
               os.close();
               messageReceived=MESSAGE_RECEIVED_OK;
           } else {
               t.sendResponseHeaders(404, response2.length());
               os.write(response2.getBytes());
               logger.info ("response2.length() = "+response2.length());
               logger.info ("response2.getBytes() = "+response2.getBytes());
               os.close();
           }
           if  (messageReceived==MESSAGE_RECEIVED_OK) {
               MakeReceiveJsonOperations.setPseudoJsonData(uquery);
               try {
                  iRet = MakeReceiveJsonOperations.MakeReceiveOmniaOperations();
               } catch (SQLException e) {
                  logger.info(ExceptionUtils.getRootCauseMessage(e));
                  logger.info(ExceptionUtils.getFullStackTrace(e));
                  e.printStackTrace();
               }
           }
       }
    }
}
