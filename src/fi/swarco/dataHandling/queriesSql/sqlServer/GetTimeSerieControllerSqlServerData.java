package fi.swarco.dataHandling.queriesSql.sqlServer;
import org.apache.log4j.Logger;
public class GetTimeSerieControllerSqlServerData {
    private static Logger logger = Logger.getLogger(GetTimeSerieControllerSqlServerData.class.getName());
    private String statement="";
    public String getStatement() {
        buildStatement ();
        return statement; }
    private void  buildStatement () {    // REthink old was rawdata 2
        statement = "select ";
        statement = statement + "sl.seriename,";
        statement = statement + "dm.omniacode,";
        statement = statement + "dm.intersectionid,";
        statement = statement + "dm.controllerid,";
        statement = statement + "dm.detectorid,";
        statement = statement + "dm.detectorexternalcode,";
        statement = statement + "dm.measurementtime,";
        statement = statement + "dm.measurementvehiclecount as vehiclecount,";
        statement = statement +"dbo.TRPX_GetSpeed(dm.MeasurementMeanVehicleSpeed) as speed,";
        statement = statement +"dbo.TRPX_GetOccupancy(dm.MeasurementOccupancyProcent) as occupancy,";
        statement = statement +"dbo.TRPX_GetAccuracy(dm.MeasurementAccurancy) as accurancy ";
        statement = statement +"from  TRPX_OmniaOut dm ";
        statement = statement +"join TRPX_SerieLink sl ";
        statement = statement + "on dm.controllerid =sl.controllerid and sl.detectorid=dm.detectorid ";
        statement = statement +"where dm.ControllerId=? and dm.measurementtime=?;";
        logger.info("statement = " + statement);
    }
}
