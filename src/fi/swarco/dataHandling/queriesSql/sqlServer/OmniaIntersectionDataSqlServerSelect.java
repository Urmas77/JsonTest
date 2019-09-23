package fi.swarco.dataHandling.queriesSql.sqlServer;
import org.apache.log4j.Logger;
public class OmniaIntersectionDataSqlServerSelect {
    private static Logger logger = Logger.getLogger(OmniaMeasurementDataSqlServerSelect.class.getName());
    private String statement="";
    public String getStatement() {
        buildStatement ();
        return statement;
    }
    private void  buildStatement () {
        statement = "SELECT ";
        statement =	statement + "distinct OmniaCode,";
        statement =	statement +	"OmniaName,";
        statement =	statement +	"OmniaPublicationStatus,";
        statement =	statement +	"IntersectionID ,";
        statement =	statement +	"IntersectionDescription,";
        statement =	statement +	"IntersectionAreaId,";
        statement =	statement + "IntersectionExternalCode,";
        statement =	statement +	"InterSectionDataPreviousUpdate,";
        statement =	statement +	"ControllerId,";
        statement =	statement + "ControllerDescription,";
        statement =	statement +	"ControllerExternalCode,";
        statement =	statement +	"ControllerDataPreviousUpdate";
        statement =	statement + " from TRPX_OmniaOut OO , TRPX_MeasurementTask_Work mt ";
        statement =	statement + " where OO.MeasurementTime=mt.DetectorMeasuresTimestamp ";
        statement =	statement + " where OO.MeasurementTime=mt.DetectorMeasuresTimestamp ";
        statement =	statement + " and mt.TaskStatus=1 ";
        statement =	statement + " and OO.DetectorId=mt.DetectorId; ";
        logger.info("statement = " + statement);
    }
}
