package fi.swarco.dataHandling.pojos;
import org.apache.log4j.Logger;

import static fi.swarco.CONSTANT.NO_IDENTITY;
public class OmniaMeasurementData {
    static Logger logger = Logger.getLogger(OmniaMeasurementData.class.getName());
    private long identity;
    private long OmniaCode;
    private String OmniaName;
    private long OmniaPublicationStatus;
    private long IntersectionId;
    private long ControllerId;
    private java.sql.Timestamp measurementTimeSql;
    private String MeasurementTime;
    private long DetectorId;
    private long DetectorTypeId;
    private String DetectorExternalCode;
    private String DetectorMaintenanceCode;
    private long DetectorUnitId;
    private String DetectorDataPreviousUpdate;
    private java.sql.Timestamp detectorDataPreviousUpdateSql;
     private String DetectorDescription;
    private long MeasurementVehicleCount;
    private double MeasurementMeanVehicleSpeed;
    private double MeasurementOccupancyProcent;
    private double MeasurementAccurancy;
    public  OmniaMeasurementData() {}
    public OmniaMeasurementData(
            long identity,
            long omniaCode,
            String omniaName,
            long omniaPublicationStatus,
            long intersectionId,
            long controllerId,
            String measurementTime,
            long detectorId,
            long detectorTypeId,
            String detectorExternalCode,
            String detectorMaintenanceCode,
            long detectorUnitId,
            String detectorDataPreviousUpdate,
            String detectorDescription,
            long measurementVehicleCount,
            double measurementMeanVehicleSpeed,
            double measurementOccupancyProcent,
            double measurementAccurancy
    ) {
        super();
        this.identity=identity;
        this.OmniaCode=omniaCode;
        this.OmniaName=omniaName;
        this.OmniaPublicationStatus=omniaPublicationStatus;
        this.IntersectionId=intersectionId;
        this.ControllerId=controllerId;
        this.MeasurementTime=measurementTime;
        this.DetectorId=detectorId;
        this.DetectorTypeId=detectorTypeId;
        this.DetectorExternalCode=detectorExternalCode;
        this.DetectorMaintenanceCode=detectorMaintenanceCode;
        this.DetectorUnitId=detectorUnitId;
        this.DetectorDataPreviousUpdate=detectorDataPreviousUpdate;
        this.DetectorDescription=detectorDescription;
        this.MeasurementVehicleCount=measurementVehicleCount;
        this.MeasurementMeanVehicleSpeed=measurementMeanVehicleSpeed;
        this.MeasurementOccupancyProcent=measurementOccupancyProcent;
        this.MeasurementAccurancy=measurementAccurancy;
        measurementTimeSql = java.sql.Timestamp.valueOf(measurementTime);
        detectorDataPreviousUpdateSql = java.sql.Timestamp.valueOf(detectorDataPreviousUpdate);
    }
    public long getIdentity() {
        return identity;
    }
    public void setIdentity(long identity) {
        this.identity = identity;
    }
    public long getOmniaCode() {
        return OmniaCode;
    }
    public void setOmniaCode(long omniaCode) {
        this.OmniaCode = omniaCode;
    }
    public String getOmniaName() {
        return OmniaName;
    }
    public void setOmniaName(String omniaName) {
        this.OmniaName = omniaName;
    }
    public long getOmniaPublicationStatus() {
        return OmniaPublicationStatus;
    }
    public void setOmniaPublicationStatus(long omniaPublicationStatus) {
        this.OmniaPublicationStatus = omniaPublicationStatus;
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
        if (MeasurementTime == null) {
            if (!(measurementTimeSql==null)) {
                MeasurementTime=measurementTimeSql.toString().substring(0, 19);
            }
        }
        return MeasurementTime;
    }
    public void setMeasurementTime(String pMeasurementTime) {
        this.MeasurementTime = pMeasurementTime;
        if (!(MeasurementTime == null )) {
            measurementTimeSql=java.sql.Timestamp.valueOf(MeasurementTime);
        }
    }
    public java.sql.Timestamp getMeasurementTimeSql() {
        if (measurementTimeSql == null) {
            if (!(MeasurementTime == null)) {
                measurementTimeSql=java.sql.Timestamp.valueOf(MeasurementTime);
            }
        }
        return measurementTimeSql;
    }
    public void setMeasurementTimeSql(java.sql.Timestamp pMeasurementTimeSql) {
        this.measurementTimeSql = pMeasurementTimeSql;
        if (!(measurementTimeSql == null)) {
            MeasurementTime=measurementTimeSql.toString().substring(0, 19);
        }
    }
    public long getDetectorId() {
        return DetectorId;
    }
    public void setDetectorId(long detectorId) {
        this.DetectorId = detectorId;
    }
    public long getDetectorTypeId() {
        return DetectorTypeId;
    }
    public void setDetectorTypeId(long pDetectorTypeId) {
      //  logger.info("pDetectorTypeId =" + pDetectorTypeId);
        this.DetectorTypeId = pDetectorTypeId;
    }
    public String getDetectorExternalCode() {
        return DetectorExternalCode;
    }
    public void setDetectorExternalCode(String detectorExternalCode) {
        this.DetectorExternalCode = detectorExternalCode;
    }
    public String getDetectorMaintenanceCode() {
        return DetectorMaintenanceCode;
    }
    public void setDetectorMaintenanceCode(String detectorMaintenanceCode) {
        this.DetectorMaintenanceCode = detectorMaintenanceCode;
    }
    public long getDetectorUnitId() {
        return DetectorUnitId;
    }
    public void setDetectorUnitId(long detectorUnitId) {
        this.DetectorUnitId = detectorUnitId;
    }
    public String getDetectorDataPreviousUpdate() {
        if (DetectorDataPreviousUpdate == null) {
            if (!(detectorDataPreviousUpdateSql==null)) {
                DetectorDataPreviousUpdate=detectorDataPreviousUpdateSql.toString().substring(0, 19);
            }
        }
        return DetectorDataPreviousUpdate;
    }
    public void setDetectorDataPreviousUpdate(String pDetectorDataPreviousUpdate) {
        this.DetectorDataPreviousUpdate = pDetectorDataPreviousUpdate;
        //logger.info("DetectorDataPreviousUpdate = "+ DetectorDataPreviousUpdate);
        if (!(DetectorDataPreviousUpdate == null )) {
            detectorDataPreviousUpdateSql=java.sql.Timestamp.valueOf(DetectorDataPreviousUpdate);
        }
    }
    public java.sql.Timestamp getDetectorDataPreviousUpdateSql() {
        if (detectorDataPreviousUpdateSql == null) {
            if (!(DetectorDataPreviousUpdate == null)) {
                detectorDataPreviousUpdateSql=java.sql.Timestamp.valueOf(DetectorDataPreviousUpdate);
            }
        }
        return detectorDataPreviousUpdateSql;
    }
    public void setDetectorDataPreviousUpdatesql(java.sql.Timestamp pDetectorDataPreviousUpdateSql) {
        this.detectorDataPreviousUpdateSql = pDetectorDataPreviousUpdateSql;
        if (!(DetectorDataPreviousUpdate == null)) {
            DetectorDataPreviousUpdate=detectorDataPreviousUpdateSql.toString().substring(0, 19);
        }
    }
    public String getDetectorDescription() {
        return DetectorDescription;
    }
    public void setDetectorDescription(String detectorDescription) {
        this.DetectorDescription = detectorDescription;
    }
    public long getMeasurementVehicleCount() {
        return MeasurementVehicleCount;
    }
    public void setMeasurementVehicleCount(long measurementVehicleCount) {
        this.MeasurementVehicleCount = measurementVehicleCount;
    }
    public double getMeasurementMeanVehicleSpeed() {
        return MeasurementMeanVehicleSpeed;
    }
    public void setMeasurementMeanVehicleSpeed(double measurementMeanVehicleSpeed) {
        this.MeasurementMeanVehicleSpeed = measurementMeanVehicleSpeed;
    }
    public double getMeasurementOccupancyProcent() {
        return MeasurementOccupancyProcent;
    }
    public void setMeasurementOccupancyProcent(double measurementOccupancyProcent) {
        this.MeasurementOccupancyProcent = measurementOccupancyProcent;
    }
    public double getMeasurementAccurancy() {
        return MeasurementAccurancy;
    }
    public void setMeasurementAccurancy(double measurementAccurancy) {
        this.MeasurementAccurancy = measurementAccurancy;
    }
    @Override
    public String toString() {
        return 	"OmniaMeasurementData [identity = " +identity
                + ", OmniaCode = " +OmniaCode  +
                ", OmniaName = " + OmniaName +
                ", OmniaPublicationStatus = " +OmniaPublicationStatus +
                ", IntersectionId = " + IntersectionId +
                ", ControllerId =" + ControllerId +
                ", MeasurementTime =" + MeasurementTime +
                ", DetectorId = " +  DetectorId +
                ", DetectorTypeId =  " + DetectorTypeId +
                ", DetectorExternalCode = " + DetectorExternalCode +
                ", DetectorMaintenanceCode = " + DetectorMaintenanceCode +
                ", DetectorUnitId = " + DetectorUnitId +
                ", DetectorDataPreviousUpdate = " + DetectorDataPreviousUpdate +
                ", DetectorDescription = " + DetectorDescription +
                ", MeasurementVehicleCount = " + MeasurementVehicleCount +
                ", MeasurementMeanVehicleSpeed = " + MeasurementMeanVehicleSpeed +
                ", MeasurementOccupancyProcent = " + MeasurementOccupancyProcent +
                ", MeasurementAccurancy = " + MeasurementAccurancy +
                ", MeasurementTimeSql = " + measurementTimeSql +
                ", DetectorDataPreviousUpdateSql = " + detectorDataPreviousUpdateSql +"]";
    }
    public void MakeEmptyElement() {
        identity=Long.valueOf(NO_IDENTITY);
        OmniaCode =Long.valueOf(0);
        OmniaName="novalue";
        OmniaPublicationStatus =Long.valueOf(0);
        IntersectionId=Long.valueOf(0);
        ControllerId=Long.valueOf(0);
        MeasurementTime="1970-01-01 00:00:00";
        DetectorId=Long.valueOf(0);
        DetectorTypeId=Long.valueOf(0);
        DetectorExternalCode="novalue";
        DetectorMaintenanceCode="novalue";
        DetectorUnitId=Long.valueOf(0);
        DetectorDataPreviousUpdate="1970-01-01 00:00:00";
        DetectorDescription="novalue";
        MeasurementVehicleCount=Long.valueOf(0);
        MeasurementMeanVehicleSpeed= Double.valueOf(0.0);
        MeasurementOccupancyProcent= Double.valueOf(0.0);
        MeasurementAccurancy= Double.valueOf(0.0);
        measurementTimeSql=java.sql.Timestamp.valueOf("1970-01-01 00:00:00");
        detectorDataPreviousUpdateSql=java.sql.Timestamp.valueOf("1970-01-01 00:00:00");
    }
}
