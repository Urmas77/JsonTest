package fi.swarco.dataHandling.queriesSql.sqlServer;
import org.apache.log4j.Logger;
public class InsertRawDataSqlServer {
    private static Logger logger = Logger.getLogger(InsertRawDataSqlServer.class.getName());
    private String statement="";
    public String getStatement() {
        buildStatement ();
        return statement; }

    private void  buildStatement () {    // REthink old was rawdata 2
        statement = "INSERT INTO TRPX_RawData";
        statement = statement + "(RawDataSourceId,";
        statement = statement + "RawDataTimestamp,";
        statement = statement + "RawDataStatus,";
        statement = statement + "RawDataStatusString,";
        statement = statement + "RawDataLine,";
        statement = statement + "Timestamp,";
        statement = statement + "TimeCreated";
        statement = statement + ") ";
        statement = statement + "VALUES (";
        statement = statement + "?,";
        statement = statement + "getdate(),";
        statement = statement + "?,";
        statement = statement + "?,";
        statement = statement + "?,";
        statement = statement + "?,";
        statement = statement + "getdate())";
        logger.info("statement = " + statement);
    }
}
