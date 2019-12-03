// InsertIntersectionTasks {
package fi.swarco.dataHandling.queriesSql.sqlServer;
import org.apache.log4j.Logger;
public class InsertIntersectionTasks {
    private static Logger logger = Logger.getLogger(InsertIntersectionTasks.class.getName());
    private String statement="";
    public String getStatement() {
        buildStatement ();
        return statement; }
    private void  buildStatement () {    // REthink old was rawdata 2
        statement = "INSERT INTO TRPX_MeasurementTask(";
        statement = statement + "OmniaCode,IntersectionID,ControllerId,DetectorID,";
        statement = statement + "DetectorMeasuresTimestamp ,PermanentDataTimestamp ,TaskType,TaskStatus,Created)";
        statement = statement + " SELECT ";
        statement = statement + "7777,IntersectionId,7777,7777,";
        statement = statement + "LastUpdate,LastUpdate,'INTERSECTIONDATACHANGE',0 ,getdate()";
        statement = statement + " FROM Intersections where deleted=0 and visible=1";
        logger.info("statement = " + statement);
    }
}

