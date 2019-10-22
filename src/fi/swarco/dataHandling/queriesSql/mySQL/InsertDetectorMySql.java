// InsertDetectorMySql
package fi.swarco.dataHandling.queriesSql.mySQL;
import org.apache.log4j.Logger;
public class InsertDetectorMySql {
    private static Logger logger = Logger.getLogger(InsertDetectorMySql.class.getName());
    private String statement = "";
    public String getStatement() {
        buildStatement();
        return statement;
    }
    private void buildStatement() {
        statement = "insert into DetectorData (";
        statement = statement + "OmniaCode,";
        statement = statement + "OmniaName,";
        statement = statement + "OmniaPublicationStatus,";
        statement = statement + "IntersectionId,";
        statement = statement + "ControllerId,";
        statement = statement + "DetectorId,";
        statement = statement + "DetectorTypeId,";
        statement = statement + "DetectorProgressId,";
        statement = statement + "DetectorMaintenanceCode,";
        statement = statement + "DetectorMeasurementStationId,";
        statement = statement + "DetectorExternalCode,";
        statement = statement + "DetectorSubSystemId,";
        statement = statement + "DetectorUnitId,";
        statement = statement + "DetectorVisible,";
        statement = statement + "DetectorDeleted,";
        statement = statement + "DetectorDataPreviousUpdate,";
        statement = statement + "DetectorGuid,";
        statement = statement + "DetectorDescription,";
        statement = statement + "DetectorAreaId,";
        statement = statement + "Created,";
        statement = statement + "DetectorObjectPriorityId,";
        statement = statement + "DetectorParkingHouseId)";
        statement = statement + "values ( ";
        statement = statement + "?,";
        statement = statement + "?,";
        statement = statement + "?,";
        statement = statement + "?,";
        statement = statement + "?,";
        statement = statement + "?,";
        statement = statement + "?,";
        statement = statement + "?,";
        statement = statement + "?,";
        statement = statement + "?,";
        statement = statement + "?,";
        statement = statement + "?,";
        statement = statement + "?,";
        statement = statement + "?,";
        statement = statement + "?,";
        statement = statement + "?,";
        statement = statement + "?,";
        statement = statement + "?,";
        statement = statement + "?,";
        statement = statement + "?,";
        statement = statement + "?,";
        statement = statement + "?);";
        //      logger.info("statement = " + statement);
    }
}
