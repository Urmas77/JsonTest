package fi.swarco.dataHandling.queriesSql.sqlServer;
import org.apache.log4j.Logger;
public class GetVisibleDetectorsSqlServerData {
    private static Logger logger = Logger.getLogger(GetVisibleDetectorsSqlServerData.class.getName());
    private String statement = "";
    public String getStatement() {
        buildStatement();
        return statement;
    }
    private void buildStatement() {
        statement = "select  ";
        statement = statement + "OmniaCode,";
        statement = statement + "OmniaName,";
        statement = statement +"OmniaPublicationStatus,";
        statement = statement +"IntersectionId,";
        statement = statement +"ControllerId,";
        statement = statement +"DetectorId,";
        statement = statement + "DetectorTypeId,";
        statement = statement +"DetectorProcressId,";
        statement = statement +"DetectorMaintenanceCode,";
        statement = statement +"DetectorMeasurementStationId,";
        statement = statement +"DetectorExternalCode,";
        statement = statement +"DetectorSubSystemId,";
        statement = statement +"DetectionUnitId,";
        statement = statement +"DetectorVisible,";
        statement = statement +"DetectorDeleted,";
        statement = statement +"DetectorDataPreviousUpdate,";
        statement = statement +"DetectorGuid,";
        statement = statement +"DetectorDescription,";
        statement = statement +"DetectorAreaId,";
        statement = statement +"Created,";
        statement = statement +"DetectorObjectPriorityId, ";
        statement = statement +"DetectorParkingHouseId";
        statement = statement +" from TRPX_OmniaDetectorOut";
        logger.info("statement =" +statement);

    }




}
