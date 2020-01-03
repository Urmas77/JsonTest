package fi.swarco.dataHandling.queriesSql.sqlServer;
import org.apache.log4j.Logger;
public class GetTimeSerieSqlServerData {
    private static Logger logger = Logger.getLogger(GetTimeSerieSqlServerData.class.getName());
    private String statement="";
    public String getStatement(int pIntCount) {
        buildStatement (pIntCount);
        return statement; }
    private void  buildStatement (int pIntCount) {    // REthink old was rawdata 2
        if (pIntCount<=1) {
            statement = "select ";
        } else {
            statement = "select top " + pIntCount + " ";
        }
        statement = statement + "sl.seriename,";
        statement = statement + "dm.omniacode,";
        statement = statement + "dm.intersectionid,";
        statement = statement + "dm.controllerid,";
        statement = statement + "dm.detectorid,";
        statement = statement + "dm.detectorexternalcode,";
        statement = statement + "dm.measurementtime,";
        statement = statement + "dm.measurementvehiclecount as vehiclecount,";
        statement = statement +"dbo.TRPX_GetSpeed(dm.MeasurementMeanVehicleSpeed) as speed,";
        statement = statement +"dbo.TRPX_GetOccupancy(dm.MeasurementOccupancyProcent)   as occupancy,";
        statement = statement +"dbo.TRPX_GetAccuracy(dm.MeasurementAccurancy) as accurancy ";
        statement = statement +"from  TRPX_OmniaOut dm ";
        statement = statement +"join TRPX_SerieLink sl on sl.detectorid=dm.detectorid ";
        statement = statement +"where dm.DetectorId=? and dm.measurementtime>=?;";
        logger.info("statement = " + statement);
    }
}
