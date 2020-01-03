package fi.swarco.dataHandling.queriesSql.sqlServer;
import org.apache.log4j.Logger;
public class GetOldestUnusedTimeStampFromMeasurements {
    private static Logger logger = Logger.getLogger(GetOldestUnusedTimeStampFromMeasurements.class.getName());
    private String statement="";
    public String getStatement() {
        buildStatement();
        return statement; }
    private void  buildStatement () {
        statement = "select  isnull(min(dm.MeasurementTime),cast('2069-04-25T15:51:00.000' as smalldatetime)) ";
        statement = statement +"from  TRPX_OmniaOut dm ";
        statement = statement +"where dm.DetectorId=? and dm.measurementtime>=?;";
        logger.info("statement = " + statement);
    }
}
//    select isnull(min(dm.MeasurementTime),cast('2069-04-25T15:51:00.000' as smalldatetime))
//    from TRPX_OmniaOut dm where dm.DetectorId=101 and dm.MeasurementTime>'2022-03-10 23:00:00'
