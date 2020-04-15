package fi.swarco.dataHandling.queriesSql.sqlServer;
import org.apache.log4j.Logger;
public class InsertSeriesLinkDataSqlServer {
    private static Logger logger = Logger.getLogger(InsertSeriesLinkDataSqlServer.class.getName());
    private String statement="";
    public String getStatement() {
        buildStatement ();
        return statement; }
    private void  buildStatement () {
        statement = "INSERT INTO TRPX_SerieLink";
        statement = statement + "(OmniaCode,";
        statement = statement + "IntersectionID,";
        statement = statement + "ControllerId,";
        statement = statement + "DetectorID,";
        statement = statement + "SerieName,";
        statement = statement + "Created";
        statement = statement + ") ";
        statement = statement + "VALUES (";
        statement = statement + "?,";
        statement = statement + "?,";
        statement = statement + "?,";
        statement = statement + "?,";
        statement = statement + "?,";
        statement = statement + "getdate())";
        logger.info("statement = " + statement);
    }
}

