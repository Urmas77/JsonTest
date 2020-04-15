package fi.swarco.dataHandling.pojos.influx;
import fi.swarco.omniaDataTransferServices.InfluxUtilities;
import fi.swarco.serviceOperations.SwarcoTimeUtilities;
import org.apache.log4j.Logger;

import static fi.swarco.CONSTANT.*;

public class InfluxSqlToTimeSerie {
    private static Logger logger = Logger.getLogger(fi.swarco.dataHandling.pojos.influx.InfluxSqlToTimeSerie.class.getName());
    private String SerieName=NO_VALUE;
    private long OmniaCode=INT_EMPTY_ELEMENT;
    private long IntersectionId=Long.valueOf(0);
    private long ControllerId=Long.valueOf(0);
    private long DetectorId=Long.valueOf(0);
    private String DetectorExternalCode=NO_VALUE;
    private String MeasurementTime;
    private long VehicleCount;
    private double MeanVehicleSpeed;
    private double OccupancyProcent;
    private double Accurancy;
    public InfluxSqlToTimeSerie(){}
    public InfluxSqlToTimeSerie(
            String serieName,
            long omniaCode,
            long intersectionId,
            long controllerId,
            long detectorId,
            String detectorExternalCode,
            String measurementTime,
            long VehicleCount,
            double MeanVehicleSpeed,
            double OccupancyProcent,
            double Accurancy
    ) {
        super();
        this.SerieName=serieName;
        this.OmniaCode = omniaCode;
        this.IntersectionId = intersectionId;
        this.ControllerId = controllerId;
        this.DetectorId = detectorId;
        this.DetectorExternalCode=detectorExternalCode;
        this.MeasurementTime=measurementTime;
        this.VehicleCount = VehicleCount;
        this.MeanVehicleSpeed = MeanVehicleSpeed;
        this.OccupancyProcent = OccupancyProcent;
        this.Accurancy = Accurancy;
    }
    public String getSerieName() {
        return SerieName;
    }
    public void setSerieName(String pSerieName) {
        this.SerieName = pSerieName;
    }
    public long getOmniaCode() {
        return OmniaCode;
    }
    public void setOmniaCode(long omniaCode) {
        this.OmniaCode = omniaCode;
    }
    public long getIntersectionId() {
        return IntersectionId;
    }
    public void setIntersectionId(long intersectionId) {
        this.IntersectionId = intersectionId;
    }
    public long getControllerId() {
        return ControllerId;
    }
    public void setControllerId(long controllerId) {
        this.ControllerId = controllerId;
    }
    public long getDetectorId() {
        return DetectorId;
    }
    public void setDetectorId(long DetectorId) {
        this.DetectorId = DetectorId;
    }
    public String getDetectorExternalCode() {
        return DetectorExternalCode;
    }
    public void setDetectorExternalCode(String pDetectorExternalCode) {
        this.DetectorExternalCode = pDetectorExternalCode;
    }
    public String getMeasurementTime() {
        return MeasurementTime;
    }
    public void setMeasurementTime(String pMeasurementTime) {
        this.MeasurementTime = pMeasurementTime;
    }
    public long getVehicleCount() {
        return VehicleCount;
    }
    public void setVehicleCount(long VehicleCount) {
        this.VehicleCount = VehicleCount;
    }
    public double getMeanVehicleSpeed() {
        return MeanVehicleSpeed;
    }
    public void setMeanVehicleSpeed(double MeanVehicleSpeed) {
        this.MeanVehicleSpeed = MeanVehicleSpeed;
    }
    public double getOccupancyProcent() {
        return OccupancyProcent;
    }
    public void setOccupancyProcent(double OccupancyProcent) {
        this.OccupancyProcent = OccupancyProcent;
    }
    public double getAccurancy() {
        return Accurancy;
    }
    public void setAccurancy(double Accurancy) {
        this.Accurancy = Accurancy;
    }
    @Override
    public String toString() {
        return "RETHINK InfluxSqlToTimeSerie [SerieName =" + SerieName +
                ",OmniaCode = " +OmniaCode  +
                ", IntersectionId = " +  IntersectionId +
                ", ControllerId =" + ControllerId +
                ", DetectorId =" + DetectorId +
                ", DetectorExternalCode =" + DetectorExternalCode +
                ", VehicleCount = " + VehicleCount +
                ", MeanVehicleSpeed = " + MeanVehicleSpeed +
                ", OccupancyProcent = " + OccupancyProcent +
                ", Accurancy = " + Accurancy +
                "MeasurementTime=" + MeasurementTime + "]";
    }
    public String GetTimeSerieString() {
        String strHelp1;
        String time;
        InfluxUtilities aaa = new InfluxUtilities();
        if (SerieName.equals(NO_VALUE)) {
            return NO_TIME_SERIE_STRING;
        }
        SwarcoTimeUtilities tu = new SwarcoTimeUtilities();
        String strHelp2;
         strHelp2 =tu.ToNanoSec8601Str(MeasurementTime);
        strHelp1 = SerieName +",";
        strHelp1 = strHelp1 +"OmniaCode="+ OmniaCode + ",";
        strHelp1 = strHelp1 + "IntersectionId=" +IntersectionId +",";
        strHelp1 = strHelp1 + "ControllerId="+  ControllerId +",";
        strHelp1 = strHelp1 + "DetectorId="+ DetectorId +",";
        strHelp1 = strHelp1 + "DetectorExternalCode="+ aaa.FilterInfluxFields(DetectorExternalCode) +" ";
        strHelp1 = strHelp1 + "VehicleCount="+ VehicleCount+",";
        strHelp1 = strHelp1 + "MeanVehicleSpeed="+ MeanVehicleSpeed +",";
        strHelp1 = strHelp1 +  "OccupancyProcent="+OccupancyProcent +",";
        strHelp1 = strHelp1 +  "Accurancy="+Accurancy+" ";
        strHelp1 = strHelp1 +   strHelp2;
    //    logger.info("strHelp1 = "+ strHelp1);
        return strHelp1;
    }
    public void MakeEmptyElement() {
        SerieName=NO_VALUE;
        OmniaCode =INT_EMPTY_ELEMENT;
        IntersectionId=Long.valueOf(0);
        ControllerId=Long.valueOf(0);
        DetectorId=Long.valueOf(0);
        DetectorExternalCode=NO_VALUE;
        MeasurementTime = "1970-01-01T00:00:00.0000000Z";
        VehicleCount = 0;
        MeanVehicleSpeed = 0.0;
        OccupancyProcent = 0.0;
        Accurancy = 0.0;
    }
}

