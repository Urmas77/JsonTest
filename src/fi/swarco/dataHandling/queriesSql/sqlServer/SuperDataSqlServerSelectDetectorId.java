package fi.swarco.dataHandling.queriesSql.sqlServer;
import org.apache.log4j.Logger;
public class SuperDataSqlServerSelectDetectorId {
    private static Logger logger = Logger.getLogger(SuperDataSqlServerSelectDetectorId.class.getName());
    private String statement="";
    public String getStatement() {
        buildStatement ();
        return statement;
    }
    private void  buildStatement () {
        statement = "SELECT ";
        statement =	statement + "distinct OmniaCode,";
        statement =	statement +	"IntersectionID ,";
        statement =	statement +	"ControllerId,";
        statement =	statement +	"DetectorId";
        statement =	statement + " from TRPX_Super ";
        statement =	statement + "where DetectorId >? ";
        statement =	statement + "order by DetectorId;";
        logger.info("statement = " + statement);
    }
}


