//OmniaMeasurementDataShortMySqlSelectWhere
package fi.swarco.dataHandling.queriesSql.mySQL;
import org.apache.log4j.Logger;
public class SelectMeasurementDataShortMySqlWhere {
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
        statement =	statement +	"DetectorId ,";
        statement =	statement + "DetectorExternalCode ,";
        statement =	statement +	"VehicleCount,";
        statement =	statement + "MeanVehicleSpeed,";
        statement =	statement +	"OccupancyProcent,";
        statement =	statement +	"Accurancy";
        statement =	statement + " from OmniaMeasurementDataShort ";
        statement =	statement + " where omniacode =  ? and " ;
        statement =	statement + " intersectionId  =  ? and  ";
        statement =	statement + " ControllerId  =  ? and  ";
        statement =	statement + " DetectorId  =  ? and  ";
        statement =	statement + " MeasurementTime= ? ";
        statement =	statement + ";";
        logger.info("statement = " + statement);
    }
}

