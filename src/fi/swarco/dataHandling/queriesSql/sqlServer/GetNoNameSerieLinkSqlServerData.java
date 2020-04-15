package fi.swarco.dataHandling.queriesSql.sqlServer;
import org.apache.log4j.Logger;
public class GetNoNameSerieLinkSqlServerData {
    private static Logger logger = Logger.getLogger(GetNoNameSerieLinkSqlServerData.class.getName());
    private String statement="";
    public String getStatement() {
        buildStatement ();
        return statement; }
    private void  buildStatement () {
        statement = "SELECT ";
        statement = statement + "OmniaCode,";
        statement = statement + "IntersectionID,";
        statement = statement + "ControllerId,";
        statement = statement + "DetectorID,";
        statement = statement + "'novalue'";
        statement = statement + " FROM TRPX_MeasurementTask_history work ";   // REHTINK WORK
        statement = statement + "WHERE NOT EXISTS ";
        statement = statement + "(SELECT * FROM TRPX_SerieLink link  WHERE link.detectorID=work.DetectorID) and ";
        statement = statement + "work.ControllerId =?;";
        logger.info("statement = " + statement);
    }
}


