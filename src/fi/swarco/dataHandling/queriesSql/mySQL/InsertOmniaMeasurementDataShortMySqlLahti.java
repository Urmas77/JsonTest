package fi.swarco.dataHandling.queriesSql.mySQL;
import org.apache.log4j.Logger;
public class InsertOmniaMeasurementDataShortMySqlLahti {
    private static Logger logger = Logger.getLogger(InsertOmniaMeasurementDataShortMySql.class.getName());
    private String statement="";
    public String getStatement() {
        buildStatement ();
        return statement;
    }
    private void  buildStatement () {
        statement = "insert into OmniaMeasurementDataShortLahti (";
        statement =	statement +	"OmniaCode,";
        statement =	statement +	"IntersectionID,";
        statement =	statement +	"ControllerId,";
        statement =	statement +	"MeasurementTime,";
        statement =	statement +	"DetectorId,";
        statement =	statement +	"DetectorExternalCode,";
        statement =	statement +	"VehicleCount,";
        statement =	statement +	"MeanVehicleSpeed,";
        statement =	statement +	"OccupancyProcent,";
        statement =	statement +	"Accurancy)";
        statement =	statement +	"values (";
        statement =	statement +	"?,";
        statement =	statement +	"?,";
        statement =	statement +	"?,";
        statement =	statement +	"?,";
        statement =	statement +	"?,";
        statement =	statement +	"?,";
        statement =	statement +	"?,";
        statement =	statement +	"?,";
        statement =	statement +	"?,";
        statement =	statement +	"?);";
        // logger.info("statement = " + statement);
    }
}
