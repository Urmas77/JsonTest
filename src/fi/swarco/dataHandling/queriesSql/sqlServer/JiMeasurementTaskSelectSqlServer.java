package fi.swarco.dataHandling.queriesSql.sqlServer;
import org.apache.log4j.Logger;
public class JiMeasurementTaskSelectSqlServer {
    private static Logger logger = Logger.getLogger(JiMeasurementTaskSelectSqlServer.class.getName());
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
       statement = statement + "TaskType ,";
        statement = statement + "TaskStatus ,";
        statement = statement + "Created";
        statement = statement + " from TRPX_MeasurementTask_Work ";
        statement = statement + "where ";
        statement = statement + "MeasurementTask_idindex=(select min(MeasurementTask_idindex) from  TRPX_MeasurementTask_Work where  OmniaCode = (select dbo.TRPX_getOmniaCode()) and TaskStatus=1) ";
        statement = statement + " order by DetectorMeasuresTimestamp;";
        logger.info("statement = " + statement);
    }
}
// OLd
//        statement = statement + " from TRPX_MeasurementTask ";
//                statement = statement + "where ";
//                statement = statement + "MeasurementTask_idindex=(select min(MeasurementTask_idindex) from  TRPX_MeasurementTask where  OmniaCode = (select dbo.TRPX_getOmniaCode()) and TaskStatus=0) ";
//                statement = statement + " order by DetectorMeasuresTimestamp;";
//OLDOLD
// statement = statement + "OmniaCode = (select dbo.JI_getOmniaCode()) and ";
// statement = statement + "IntersectionID = (select min(IntersectionID) from JI_MeasurementTask where DetectorMeasuresTimestamp = [dbo].[JI_GetMinDetectorMeasuresTimestamp]()) and ";
// statement = statement + "ControllerID  = (select min(ControllerID) from JI_MeasurementTask where DetectorMeasuresTimestamp = [dbo].[JI_GetMinDetectorMeasuresTimestamp]()) and ";
// statement = statement + "DetectorMeasuresTimestamp = [dbo].[JI_GetMinDetectorMeasuresTimestamp]() and ";
// statement = statement + " TaskStatus = 0 ";


