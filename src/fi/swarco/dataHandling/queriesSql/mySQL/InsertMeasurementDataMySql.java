package fi.swarco.dataHandling.queriesSql.mySQL;
import org.apache.log4j.Logger;
public class InsertMeasurementDataMySql {
    private static Logger logger = Logger.getLogger(InsertMeasurementDataMySql.class.getName());
    private String statement="";
    public String getStatement() {
        buildStatement ();
        return statement;
    }
    private void  buildStatement () {
        statement = "insert into OmniaMeasurementData (";
        statement =	statement +	"OmniaCode,";
        statement =	statement +	"OmniaName,";
        statement =	statement +	"OmniaPublicationStatus,";
        statement =	statement +	"IntersectionID,";
        statement =	statement +	"ControllerId,";
        statement =	statement +	"MeasurementTime,";
        statement =	statement +	"DetectorId,";
        statement =	statement +	"DetectorTypeId,";
        statement =	statement +	"DetectorExternalCode,";
        statement =	statement +	"DetectorMaintenanceCode,";
        statement =	statement +	"DetectorUnitId,";
        statement =	statement +	"DetectorDataPreviousUpdate,";
        statement =	statement +	"DetectorDescription,";
        statement =	statement +	"MeasurementVehicleCount,";
        statement =	statement +	"MeasurementMeanVehicleSpeed,";
        statement =	statement +	"MeasurementOccupancyProcent,";
        statement =	statement +	"MeasurementAccurancy)";
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
        statement =	statement +	"?,";
        statement =	statement +	"?,";
        statement =	statement +	"?,";
        statement =	statement +	"?,";
        statement =	statement +	"?,";
        statement =	statement +	"?,";
        statement =	statement +	"?,";
        statement =	statement +	"?);";
    //    logger.info("statement = " + statement);
    }
}
