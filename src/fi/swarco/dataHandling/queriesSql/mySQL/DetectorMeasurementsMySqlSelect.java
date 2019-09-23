package fi.swarco.dataHandling.queriesSql.mySQL;
import org.apache.log4j.Logger;
public class DetectorMeasurementsMySqlSelect {
    static Logger logger = Logger.getLogger(DetectorMeasurementsMySqlSelect.class.getName());
    String statement="";
    public String getStatement() {
        buildStatement ();
        return statement;
    }

    // ifnull(OmniaName,'novalue'),";
    private void  buildStatement () {
        statement = "SELECT ";
        statement =	statement + "OmniaCode,";
        statement =	statement + "ifnull(OmniaName,'novalue'),";
        statement =	statement + "IntersectionID,";
        statement =	statement + "ifnull(IntersectionDescription,'novalue'),";
        statement =	statement + "ControllerId,";
        statement =	statement + "ifnull(ControllerExternalCode,'novalue'),";
        statement =	statement + "DetectorId,";
        statement =	statement + "ifnull(DetectorExternalCode,'novalue'),";
        statement =	statement + "ifnull(MeasurementTime,'novalue'),";
        statement =	statement + "MeasurementVehicleCount,";
        statement =	statement + "MeasurementMeanVehicleSpeed,";
        statement =	statement + "MeasurementOccupancyProcent,";
        statement =	statement + "MeasurementAccurancy ";
        statement =	statement + " from DetectorMeasurements ";
        logger.info("statement = " + statement);
    }
}



