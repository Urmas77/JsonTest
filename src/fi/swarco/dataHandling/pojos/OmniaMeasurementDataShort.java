package fi.swarco.dataHandling.pojos;
import fi.swarco.omniaDataTransferServices.InfluxUtilities;
import fi.swarco.serviceOperations.SwarcoTimeUtilities;
import org.apache.log4j.Logger;

import static fi.swarco.CONSTANT.*;

public class OmniaMeasurementDataShort {
    static Logger logger = Logger.getLogger(OmniaMeasurementDataShort.class.getName());
    private long OmniaCode=INT_EMPTY_ELEMENT;
    private long IntersectionId;
    private long ControllerId;
    private String MeasurementTime;
    private long DetectorId;
    private String DetectorExternalCode;
    private long VehicleCount;
    private double MeanVehicleSpeed;
    private double OccupancyProcent;
    private double Accurancy;
    public OmniaMeasurementDataShort() {
    }
    public OmniaMeasurementDataShort(
            long omniaCode,
            long intersectionId,
            long controllerId,
            String measurementTime,
            long detectorId,
            String detectorExternalCode,
            long VehicleCount,
            double MeanVehicleSpeed,
            double OccupancyProcent,
            double Accurancy
    ) {
        super();
        this.OmniaCode = omniaCode;
        this.IntersectionId = intersectionId;
        this.ControllerId = controllerId;
        this.MeasurementTime = measurementTime;
        this.DetectorId = detectorId;
        this.DetectorExternalCode = detectorExternalCode;
        this.VehicleCount = VehicleCount;
        this.MeanVehicleSpeed = MeanVehicleSpeed;
        this.OccupancyProcent = OccupancyProcent;
        this.Accurancy = Accurancy;
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
    public String getMeasurementTime() {
        return MeasurementTime;
    }
    public void setMeasurementTime(String pMeasurementTime) {
        this.MeasurementTime = pMeasurementTime.substring(0,19);
    }
    public long getDetectorId() {
        return DetectorId;
    }
    public void setDetectorId(long detectorId) {
        this.DetectorId = detectorId;
    }
    public String getDetectorExternalCode() {
        return DetectorExternalCode;
    }
    public void setDetectorExternalCode(String detectorExternalCode) {
        this.DetectorExternalCode = detectorExternalCode;
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
        return "OmniaMeasurementDataShort [OmniaCode = " + OmniaCode +
                ", IntersectionId = " + IntersectionId +
                ", ControllerId =" + ControllerId +
                ", MeasurementTime =" + MeasurementTime +
                ", DetectorId = " + DetectorId +
                ", DetectorExternalCode = " + DetectorExternalCode +
                ", VehicleCount = " + VehicleCount +
                ", MeanVehicleSpeed = " + MeanVehicleSpeed +
                ", OccupancyProcent = " + OccupancyProcent +
                ", Accurancy = " + Accurancy + "]";
    }

    public void MakeEmptyElement() {
        OmniaCode = INT_EMPTY_ELEMENT;
        IntersectionId = 0;
        ControllerId = 0;
        MeasurementTime = "1970-01-01 00:00:00";
        DetectorId = 0;
        DetectorExternalCode = NO_VALUE;
        VehicleCount = 0;
        MeanVehicleSpeed = 0.0;
        OccupancyProcent = 0.0;
        Accurancy = 0.0;
    }
    public String GetTimeSeriesString(String pSeriesName) {
        String strHelp1;
        String time;
        InfluxUtilities aaa = new InfluxUtilities();
        if (pSeriesName.equals(NO_VALUE)) {
            return NO_TIME_SERIE_STRING;
        }
        SwarcoTimeUtilities tu = new SwarcoTimeUtilities();
        String strHelp2;
        strHelp2 =tu.ToNanoSec8601Str(MeasurementTime);
        strHelp1 = pSeriesName +",";
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
        logger.info("strHelp1 = "+ strHelp1);
        return strHelp1;
    }
   public OmniaMeasurementDataShortJson SetJsonTransferItem() {
        OmniaMeasurementDataShortJson ce = new OmniaMeasurementDataShortJson();
        ce.setOmniaCode(getOmniaCode());
        ce.setIntersectionId(getIntersectionId());
        ce.setControllerId(getControllerId());
        ce.setMeasurementTime(getMeasurementTime());
        ce.setDetectorId(getDetectorId());
        ce.setDetectorExternalCode(getDetectorExternalCode());
        ce.setVehicleCount(getVehicleCount());
        double dblHelp1;
        double dblHelp2;
        long lngHelp1;
        // sender
        dblHelp1 = getMeanVehicleSpeed();
        dblHelp2 = dblHelp1 * DOUBLE_LONG_MULTIPLIER;
        lngHelp1 = (long) dblHelp2;
        ce.setMeanVehicleSpeedJson(lngHelp1);
        dblHelp1 = getOccupancyProcent();
        dblHelp2 = dblHelp1 * DOUBLE_LONG_MULTIPLIER;
        lngHelp1 = (long) dblHelp2;
        ce.setOccupancyProcentJson(lngHelp1);
        dblHelp1 = getAccurancy();
        dblHelp2 = dblHelp1 * DOUBLE_LONG_MULTIPLIER;
        lngHelp1 = (long) dblHelp2;
        ce.setAccurancyJson(lngHelp1);
        return ce;
    }
}



