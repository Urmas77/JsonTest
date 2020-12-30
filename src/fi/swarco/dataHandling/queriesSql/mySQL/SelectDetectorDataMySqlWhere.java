// SelectOmniaDetectorMySqlData
package fi.swarco.dataHandling.queriesSql.mySQL;
import org.apache.log4j.Logger;
public class SelectDetectorDataMySqlWhere {
    private static Logger logger = Logger.getLogger(fi.swarco.dataHandling.queriesSql.mySQL.SelectDetectorDataMySqlWhere.class.getName());
    private String statement="";
    public String getStatement() {
        buildStatement ();
        return statement; }
    private void  buildStatement () {
        statement = "select ";
        statement = statement + "OmniaCode,";
        statement = statement + "OmniaName, ";
        statement = statement + "OmniaPublicationStatus, ";
        statement = statement + "IntersectionId, ";
        statement = statement + "ControllerId, ";
        statement = statement + "DetectorId, ";
        statement = statement + "DetectorTypeId, ";
        statement = statement + "DetectorProgressId, ";
        statement = statement + "DetectorMaintenanceCode, ";
        statement = statement + "DetectorMeasurementStationId, ";
        statement = statement + "DetectorExternalCode, ";
        statement = statement + "DetectorSubSystemId, ";
        statement = statement + "DetectorUnitId, ";
        statement = statement + "DetectorVisible, ";
        statement = statement + "DetectorDeleted, ";
        statement = statement + "DetectorDataPreviousUpdate, ";
        statement = statement + "DetectorGuid, ";
        statement = statement + "DetectorDescription, ";
        statement = statement + "DetectorAreaId, ";
        statement = statement + "Created, ";
        statement = statement + "DetectorObjectPriorityId, ";
        statement = statement + "DetectorParkingHouseId ";
        statement = statement + " from DetectorData ";
        statement = statement + "  where OmniaCode = ? and ";
        statement = statement + "  DetectorId = ? ; ";
       logger.info("statement = " + statement);
    }
}

