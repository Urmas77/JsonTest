package fi.swarco.dataHandling.queriesSql.mySQL;
import org.apache.log4j.Logger;
public class SelectOmniaControllerStatusDataMySqlWhere {
    private static Logger logger = Logger.getLogger(SelectMeasurementDataShortMySqlWhere.class.getName());
    private String statement="";
    public String getStatement() {
        buildStatement ();
        return statement;
    }
    private void  buildStatement () {
        statement = "SELECT ";
        statement =	statement + "OmniaCode,";
        statement =	statement +	"IntersectionID ,";
        statement =	statement +	"ControllerId ,";
        statement =	statement + "MeasurementTime ,";
        statement =	statement +	"IntersectionCode ,";
        statement =	statement + "ControllerProgramNumber ,";
        statement =	statement +	"ControllerProgramDescription,";
        statement =	statement + "ControllerStatus";
        statement =	statement + " from OmniaControllerStatusData ";
        statement =	statement + " where omniacode =  ? and " ;
        statement =	statement + " IntersectionID  =  ? and  ";
        statement =	statement + " ControllerId  =  ? and  ";
        statement =	statement + " MeasurementTime= ? ";
        statement =	statement + ";";
      //  logger.info("  statement = " + statement);
    }
}
