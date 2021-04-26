package fi.swarco.dataHandling.queriesSql.sqlServer;
        import org.apache.log4j.Logger;
public class JiMeasurementStorageTaskSelectSqlServer {
    private static Logger logger = Logger.getLogger(JiMeasurementStorageTaskSelectSqlServer.class.getName());
    private String statement = "";
    public String getStatement() {
        buildStatement();
        return statement;
    }
    private void buildStatement() {
        statement = "SELECT ";
        statement = statement + "MeasurementTask_idindex,";
        statement = statement + "OmniaCode,";
        statement = statement + "IntersectionID,";
        statement = statement + "ControllerID,";
        statement = statement + "DetectorID,";
        statement = statement + "DetectorMeasuresTimestamp,";
        statement = statement + "PermanentDataTimestamp,";
        statement = statement + "TaskType ,";
        statement = statement + "TaskStatus ,";
        statement = statement + "Created";
        statement = statement + " from TRPX_MeasurementTask_Storage_Work ";
        statement = statement + "where ";
        statement = statement + "MeasurementTask_idindex=(select min(MeasurementTask_idindex) from  TRPX_MeasurementTask_Storage_Work where  OmniaCode = (select dbo.TRPX_getOmniaCode()) and TaskStatus=1) ";
        statement = statement + " order by DetectorMeasuresTimestamp;";
        //    logger.info("statement = " + statement);
    }
}


