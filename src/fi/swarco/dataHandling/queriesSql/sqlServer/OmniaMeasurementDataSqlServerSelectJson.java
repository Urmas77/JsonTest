package fi.swarco.dataHandling.queriesSql.sqlServer;
import org.apache.log4j.Logger;
public class OmniaMeasurementDataSqlServerSelectJson {
    static Logger logger = Logger.getLogger(OmniaMeasurementDataSqlServerSelectJson.class.getName());
    String statement="";
    public String getStatement() {
        buildStatement ();
        return statement;
    }
    private void  buildStatement () {
        statement = "SELECT ";
        statement =	statement + "OmniaCode,";
        statement =	statement +	"OmniaName,";
        statement =	statement +	"OmniaPublicationStatus,";
        statement =	statement +	"IntersectionID ,";
        statement =	statement +	"ControllerId ,";
        statement =	statement + "MeasurementTime ,";
        statement =	statement +	"OO.DetectorId ,";
        statement =	statement +	"DetectorTypeId ,";
        statement =	statement + "DetectorExternalCode ,";
        statement =	statement +	"DetectorMaintenanceCode ,";
        statement =	statement +	"DetectorUnitId,";
        statement =	statement + "DetectorDataPreviousUpdate,";
        statement =	statement +	"DetectorDescription,";
        statement =	statement +	"MeasurementVehicleCount,";
        statement =	statement + "MeasurementMeanVehicleSpeed,";
        statement =	statement +	"MeasurementOccupancyProcent,";
        statement =	statement +	"MeasurementAccurancy";
        statement =	statement + " from TRPX_OmniaOut OO , TRPX_JI_MeasurementTask_work mt ";   // Not tested ever used
        statement =	statement + " where OO.MeasurementTime=mt.DetectorMeasuresTimestamp ";
        statement =	statement + " and mt.TaskStatus=1 ";
        statement =	statement + " and OO.DetectorId=mt.DetectorId ";
        statement =	statement + " for json auto; ";
        }
}





