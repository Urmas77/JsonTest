package fi.swarco.dataHandling.queriesSql.sqlServer;
import org.apache.log4j.Logger;
public class SerieLinkSqlServerSelect {
    private static Logger logger = Logger.getLogger(SerieLinkSqlServerSelect.class.getName());
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
        statement =	statement +	"DetectorId,";
        statement =	statement +	"SerieName";
        statement =	statement + " from TRPX_SerieLink;";
        logger.info("statement = " + statement);
    }
}
