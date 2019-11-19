package fi.swarco.dataHandling.queriesSql.mySQL;
import org.apache.log4j.Logger;
public class InsertRawDataMySql {
    private static Logger logger = Logger.getLogger(InsertRawDataMySql.class.getName());
    private String statement="";
    public String getStatement() {
        buildStatement ();
        return statement; }

    private void  buildStatement () {    // REthink old was rawdata 2
        statement = "INSERT INTO RawData ";
        statement = statement + "(RawDataSourceId,";
        statement = statement + "RawDataTimestamp,";
        statement = statement + "RawDataStatus,";
        statement = statement + "RawDataStatusString,";
        statement = statement + "RawDataLine,";
        statement = statement + "Timestamp,";
        statement = statement + "TimeCreated";
        statement = statement +") ";
        statement = statement + "VALUES (";
        statement = statement +  "?,";
        statement = statement + "now(),";
        statement = statement +  "?,";
        statement = statement +  "?,";
        statement = statement +  "?,";
        statement = statement +  "?,";
        statement = statement + "now())";
      //  logger.info("statement = " + statement);
    }
}