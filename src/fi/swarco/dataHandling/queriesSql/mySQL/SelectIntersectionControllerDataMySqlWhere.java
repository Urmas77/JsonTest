package fi.swarco.dataHandling.queriesSql.mySQL;
import org.apache.log4j.Logger;
public class SelectIntersectionControllerDataMySqlWhere {
    private static Logger logger = Logger.getLogger(SelectIntersectionControllerDataMySqlWhere.class.getName());
    private String statement="";
    public String getStatement() {
        buildStatement ();
        return statement; }
    private void  buildStatement () {
        statement = "select ";
 //       statement = "select  * ";
        statement = statement + "OmniaCode,";
        statement = statement + "OmniaName, ";
        statement = statement + "OmniaPublicationStatus, ";
        statement = statement + "IntersectionId, ";
        statement = statement + "IntersectionDescription, ";
        statement = statement + "IntersectionAreaId, ";
        statement = statement + "IntersectionMaintenanceAreaId, ";
        statement = statement + "IntersectionExternalCode, ";
        statement = statement + "IntersectionSubSystemId, ";
        statement = statement + "IntersectionVisible, ";
        statement = statement + "IntersectionDeleted, ";
        statement = statement + "InterSectionDataPreviousUpdate, ";
        statement = statement + "IntersectionGuid, ";
        statement = statement + "IntersectionProgressId, ";
        statement = statement + "ControllerId, ";
        statement = statement + "ControllerDescription, ";
        statement = statement + "ControllerTypeId, ";
        statement = statement + "ControllerRoadsideUnitId, ";
        statement = statement + "ControllerExternalCode, ";
        statement = statement + "ControllerSubSystemId, ";
        statement = statement + "ControllerObjectPriorityId, ";
        statement = statement + "ControllerVisible, ";
        statement = statement + "ControllerDeleted, ";
        statement = statement + "ControllerDataPreviousUpdate, ";
        statement = statement + "ControllerGuid,";
        statement = statement + "Created";
        statement = statement + " from IntersectionControllerData ";
        statement = statement + "  where OmniaCode = ? and ";
        statement = statement + "  IntersectionId = ? and ";
        statement = statement + "  ControllerId = ? ; ";
        logger.info("statement = " + statement);
    }
}

