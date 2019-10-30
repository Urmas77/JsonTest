package fi.swarco.dataHandling.queriesSql.sqlServer;
import org.apache.log4j.Logger;
public class JiMeasurementTaskWorkSelectSqlServer {
    private static Logger logger = Logger.getLogger(JiMeasurementTaskWorkSelectSqlServer.class.getName());
    private String statement = "";
    public String getStatement() {
        buildStatement();
        return statement;
    }
    private void buildStatement() {
        statement = "SELECT ";
        statement = statement + "work_idindex,";
        statement = statement + "MeasurementTask_idindex,";
        statement = statement + "OmniaCode,";
        statement = statement + "IntersectionID,";
        statement = statement + "ControllerID,";
        statement = statement + "DetectorID,";
        statement = statement + "DetectorMeasuresTimestamp,";
        statement = statement + "PermanentDataTimestamp,";
        statement = statement + "TaskType ,";
        statement = statement + "TaskStatus ,";
        statement = statement + "Created,";
        statement = statement + "WorkCreated";
        statement = statement + " from TRPX_MeasurementTask_Work ";
        logger.info("statement = " + statement);
    }
}


