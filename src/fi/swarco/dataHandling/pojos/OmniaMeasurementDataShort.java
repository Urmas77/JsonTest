package fi.swarco.dataHandling.pojos;
import org.apache.log4j.Logger;
public class OmniaMeasurementDataShort {
    static Logger logger = Logger.getLogger(OmniaMeasurementData.class.getName());
    private long OmniaCode;
    private long IntersectionId;
    private long ControllerId;
    private String MeasurementTime;
    private long DetectorId;
    private String DetectorExternalCode;
    private long VehicleCount;
    private double MeanVehicleSpeed;
    private double OccupancyProcent;
    private double Accurancy;
    public  OmniaMeasurementDataShort() {}
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
        this.OmniaCode=omniaCode;
        this.IntersectionId=intersectionId;
        this.ControllerId=controllerId;
        this.MeasurementTime=measurementTime;
        this.DetectorId=detectorId;
        this.DetectorExternalCode=detectorExternalCode;
        this.VehicleCount=VehicleCount;
        this.MeanVehicleSpeed=MeanVehicleSpeed;
        this.OccupancyProcent=OccupancyProcent;
        this.Accurancy=Accurancy;
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
        this.MeasurementTime = pMeasurementTime;
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
        OmniaCode = Long.valueOf(0);
        IntersectionId = Long.valueOf(0);
        ControllerId = Long.valueOf(0);
        MeasurementTime = "1970-01-01 00:00:00";
        DetectorId = Long.valueOf(0);
        DetectorExternalCode = "novalue";
        VehicleCount = Long.valueOf(0);
        MeanVehicleSpeed = Double.valueOf(0.0);
        OccupancyProcent = Double.valueOf(0.0);
        Accurancy = Double.valueOf(0.0);
    }
}
