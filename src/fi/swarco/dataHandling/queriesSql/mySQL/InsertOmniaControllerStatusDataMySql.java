package fi.swarco.dataHandling.queriesSql.mySQL;
import org.apache.log4j.Logger;
public class InsertOmniaControllerStatusDataMySql {
    private static Logger logger = Logger.getLogger(InsertOmniaControllerStatusDataMySql.class.getName());
    private String statement="";
    public String getStatement() {
        buildStatement ();
        return statement;
    }
    private void  buildStatement () {
        statement = "insert into OmniaControllerStatusData (";
        statement =	statement +	"OmniaCode,";
        statement =	statement +	"IntersectionID,";
        statement =	statement +	"ControllerId,";
        statement =	statement +	"MeasurementTime,";
        statement =	statement +	"IntersectionCode,";
        statement =	statement +	"ControllerProgramNumber,";
        statement =	statement +	"ControllerProgramDescription,";
        statement =	statement +	"ControllerStatus)";
        statement =	statement +	" values (";
        statement =	statement +	"?,";
        statement =	statement +	"?,";
        statement =	statement +	"?,";
        statement =	statement +	"?,";
        statement =	statement +	"?,";
        statement =	statement +	"?,";
        statement =	statement +	"?,";
        statement =	statement +	"?);";
  //      logger.info("statement = " + statement);
    }
}
