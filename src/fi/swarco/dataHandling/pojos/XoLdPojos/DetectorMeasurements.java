package fi.swarco.dataHandling.pojos.XoLdPojos;
import static fi.swarco.CONSTANT.NO_IDENTITY;
 import static fi.swarco.CONSTANT.NO_VALUE;
public class DetectorMeasurements {
    private long identity;
    private long omniaCode;
    private String omniaName; // New
    private long intersectionId;
    private String intersectionDescription; // New
    private long controllerId;
    private String controllerExternalCode; // New
    private long detectorId;
    private String detectorExternalCode; // New
    private String measurementTime;
    private java.sql.Timestamp measurementTimeSql;
    private long measurementVehicleCount;
    private double measurementMeanVehicleSpeed;
    private double measurementOccupancyProcent;
    private double measurementAccurancy;
    public DetectorMeasurements() {}
    public DetectorMeasurements(
            long identity,
            long omniaCode,
            String omniaName,
            long intersectionId,
            String intersectionDescription,
            long controllerId,
            String controllerExternalCode,
            long detectorId,
            String detectorExternalCode,
            String measurementTime,
            long measurementVehicleCount,
            double measurementMeanVehicleSpeed,
            double measurementOccupancyProcent,
            double measurementAccurancy
    ) {
        super();
        this.identity=identity;
        this.omniaCode=omniaCode;
        this.omniaName=omniaName;
        this.intersectionId=intersectionId;
        this.intersectionDescription=intersectionDescription;
        this.controllerId=controllerId;
        this.controllerExternalCode=controllerExternalCode;
        this.detectorId=detectorId;
        this.detectorExternalCode=detectorExternalCode;
        this.measurementTime=measurementTime;
        this.measurementVehicleCount=measurementVehicleCount;
        this.measurementMeanVehicleSpeed=measurementMeanVehicleSpeed;
        this.measurementOccupancyProcent=measurementOccupancyProcent;
        this.measurementAccurancy=measurementAccurancy;
        measurementTimeSql = java.sql.Timestamp.valueOf(measurementTime);
    }
    public long getIdentity() {
        return identity;
    }
    public void setIdentity(long identity) {
        this.identity = identity;
    }
    public long getOmniaCode() {
        return omniaCode;
    }
    public void setOmniaCode(long pOmniaCode) {
        this.omniaCode = pOmniaCode;
    }
    public String getOmniaName() {
        return omniaName;
    }
    public void setOmniaName(String pOmniaName) {
        this.omniaName = pOmniaName;
    }
    public long getIntersectionId() {
        return intersectionId;
    }
    public void setIntersectionId(long pIntersectionId) {
        this.intersectionId = pIntersectionId;
    }
    public String getIntersectionDescription() {
        return intersectionDescription;
    }
    public void setIntersectionDescription(String pIntersectionDescription) {
        this.intersectionDescription = pIntersectionDescription;
    }
    public long getControllerId() {
        return controllerId;
    }
    public void setControllerId(long controllerId) {
        this.controllerId = controllerId;
    }
    public String getControllerExternalCode() {
        return controllerExternalCode;
    }
    public void setControllerExternalCode(String pControllerExternalCode) {
        this.controllerExternalCode = pControllerExternalCode;
    }
    public long getDetectorId() {
        return detectorId;
    }
    public void setDetectorId(long pDetectorId) {
        this.detectorId = pDetectorId;
    }
    public String getDetectorExternalCode() {
        return detectorExternalCode;
    }
    public void setDetectorExternalCode(String pDetectorExternalCode) {
        this.detectorExternalCode = pDetectorExternalCode;
    }
    public String getMeasurementTime() {
        if (measurementTime == null) {
            if (!(measurementTimeSql==null)) {
                measurementTime=measurementTimeSql.toString().substring(0, 19);
            }
        }
        return measurementTime;
    }
    public void setMeasurementTime(String pMeasurementTime) {
        this.measurementTime = pMeasurementTime;
        if (!(measurementTime == null )) {
            measurementTimeSql=java.sql.Timestamp.valueOf(measurementTime);
        }
    }
    public java.sql.Timestamp getMeasurementTimeSql() {
        if (measurementTimeSql == null) {
            if (!(measurementTime == null)) {
                measurementTimeSql=java.sql.Timestamp.valueOf(measurementTime);
            }
        }
        return measurementTimeSql;
    }
    public void setMeasurementTimeSql(java.sql.Timestamp pMeasurementTimeSql) {
        this.measurementTimeSql = pMeasurementTimeSql;
        if (!(measurementTimeSql == null)) {
            measurementTime=measurementTimeSql.toString().substring(0, 19);
        }
    }
    public long getMeasurementVehicleCount() {
        return measurementVehicleCount;
    }
    public void setMeasurementVehicleCount(long measurementVehicleCount) {
        this.measurementVehicleCount = measurementVehicleCount;
    }
    public double getMeasurementMeanVehicleSpeed() {
        return measurementMeanVehicleSpeed;
    }
    public void setMeasurementMeanVehicleSpeed(double measurementMeanVehicleSpeed) {
        this.measurementMeanVehicleSpeed = measurementMeanVehicleSpeed;
    }
    public double getMeasurementOccupancyProcent() {
        return measurementOccupancyProcent;
    }
    public void setMeasurementOccupancyProcent(double measurementOccupancyProcent) {
        this.measurementOccupancyProcent = measurementOccupancyProcent;
    }
    public double getMeasurementAccurancy() {
        return measurementAccurancy;
    }
    public void setMeasurementAccurancy(double measurementAccurancy) {
        this.measurementAccurancy = measurementAccurancy;
    }
    @Override
    public String toString() {
        return "DetectorMeasurements [identity = " + identity +
                ",omniaCode = " + omniaCode +
                ",omniaName = " + omniaName +
                ", intersectionId = " + intersectionId +
                ", intersectionDescription = " + intersectionDescription +
                ", controllerId =" + controllerId +
                ", controllerExternalCode =" + controllerExternalCode +
                ", detectorId =" + detectorId +
                ", detectorExternalCode =" + detectorExternalCode +
                ",measurementTime  =" + measurementTime +
                ",measurementVehicleCount = " + measurementVehicleCount +
                ",measurementMeanVehicleSpeed = " + measurementMeanVehicleSpeed +
                ",measurementOccupancyProcent = " + measurementOccupancyProcent +
                ",measurementAccurancy = " + measurementAccurancy +
                ",measurementTimeSql = " + measurementTimeSql + "]";
    }
    public void MakeEmptyElement() {
        identity=Long.valueOf(NO_IDENTITY);
        omniaCode =Long.valueOf(0);
        omniaName =NO_VALUE;
        intersectionId=Long.valueOf(0);
        intersectionDescription=NO_VALUE;
        controllerId=Long.valueOf(0);
        controllerExternalCode=NO_VALUE;
        detectorId=Long.valueOf(0);
        detectorExternalCode=NO_VALUE;
        measurementTime="1970-01-01 00:00:00";
        measurementVehicleCount=Long.valueOf(0);
        measurementMeanVehicleSpeed= Double.valueOf(0.0);
        measurementOccupancyProcent= Double.valueOf(0.0);
        measurementAccurancy= Double.valueOf(0.0);
        measurementTimeSql=java.sql.Timestamp.valueOf("1970-01-01 00:00:00");
    }
}
