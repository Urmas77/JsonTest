package fi.swarco.dataHandling.pojos;
import org.apache.log4j.Logger;
import static fi.swarco.CONSTANT.NO_IDENTITY;
import static fi.swarco.CONSTANT.NO_VALUE;
public class OmniaDetector {
    static Logger logger = Logger.getLogger(fi.swarco.dataHandling.pojos.OmniaDetector.class.getName());
    private long omniaCode=Long.valueOf(0);
    private String omniaName=NO_VALUE;
    private long omniaPublicationStatus=Long.valueOf(0);
    private long intersectionId=Long.valueOf(0);
    private long controllerId=Long.valueOf(0);
    private long detectorId=Long.valueOf(0);
    private long detectorTypeId=Long.valueOf(0);
    private long detectorProgressId=Long.valueOf(0);
    private String detectorMaintenanceCode=NO_VALUE;
    private long detectorMeasurementStationId=Long.valueOf(0);
    private String detectorExternalCode=NO_VALUE;
    private long  detectorSubSystemId=Long.valueOf(0);
    private long detectionUnitId=Long.valueOf(0);
    private long detectorVisible=Long.valueOf(0);
    private long detectorDeleted=Long.valueOf(0);
    private String detectorDataPreviousUpdate="1970-01-01 00:00:00";
    private java.sql.Timestamp detectorDataPreviousUpdateSql=java.sql.Timestamp.valueOf("1970-01-01 00:00:00");
    private String  detectorGuid=NO_VALUE;
    private String detectorDescription=NO_VALUE;
    private long detectorAreaId=Long.valueOf(0);
    private String created="1970-01-01 00:00:00";
    private java.sql.Timestamp createdSql=java.sql.Timestamp.valueOf("1970-01-01 00:00:00");
    private long detectorObjectPriorityId=Long.valueOf(0);
    private long detectorParkingHouseId=Long.valueOf(0);
    public  OmniaDetector() {}
    public OmniaDetector(
            long omniaCode,
            String omniaName,
            long omniaPublicationStatus,
            long intersectionId,
            long controllerId,
            long detectorId,
            long detectorTypeId,
            long detectorProgressId,
            String detectorMaintenanceCode,
            long detectorMeasurementStationId,
            String detectorExternalCode,
            long  detectorSubSystemId,
            long detectionUnitId,
            long detectorVisible,
            long detectorDeleted,
            String detectorDataPreviousUpdate,
            String  detectorGuid,
            String detectorDescription,
            long detectorAreaId,
            String created,
            long detectorObjectPriorityId,
            long detectorParkingHouseId
    ) {
        super();
        this.omniaCode = omniaCode;
        this.omniaName = omniaName;
        this.omniaPublicationStatus = omniaPublicationStatus;
        this.intersectionId = intersectionId;
        this.controllerId = controllerId;
        this.detectorId = detectorId;
        this.detectorTypeId = detectorTypeId;
        this.detectorProgressId = detectorProgressId;
        this.detectorMaintenanceCode = detectorMaintenanceCode;
        this.detectorMeasurementStationId = detectorMeasurementStationId;
        this.detectorExternalCode = detectorExternalCode;
        this.detectorSubSystemId = detectorSubSystemId;
        this.detectionUnitId = detectionUnitId;
        this.detectorVisible = detectorVisible;
        this.detectorDeleted = detectorDeleted;
        this.detectorDataPreviousUpdate = detectorDataPreviousUpdate;
        this.detectorGuid = detectorGuid;
        this.detectorDescription = detectorDescription;
        this.detectorAreaId = detectorAreaId;
        this.detectorObjectPriorityId = detectorObjectPriorityId;
        this.detectorParkingHouseId = detectorParkingHouseId;
        createdSql = java.sql.Timestamp.valueOf(created);
        detectorDataPreviousUpdateSql = java.sql.Timestamp.valueOf(detectorDataPreviousUpdate);
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
    public long getOmniaPublicationStatus() {
        return omniaPublicationStatus;
    }
    public void setOmniaPublicationStatus(long pOmniaPublicationStatus) {
        this.omniaPublicationStatus = omniaPublicationStatus;
    }
    public long getIntersectionId() {
        return intersectionId;
    }
    public void setIntersectionId(long pIntersectionId) {
        this.intersectionId = pIntersectionId;
    }
    public long getControllerId() {
        return controllerId;
    }
    public void setControllerId(long pControllerId) {
        this.controllerId = controllerId;
    }
    public long getDetectorId() {
        return detectorId;
    }
    public void setDetectorId(long pDetectorId) {
        this.detectorId = detectorId;
    }
    public long getDetectorTypeId() {
        return detectorTypeId;
    }
    public void setDetectorTypeId(long pDetectorTypeId) {
        this.detectorTypeId = pDetectorTypeId;
    }
    public long getDetectorProgressId() {
        return detectorProgressId;
    }
    public void setDetectorProgressId(long pDetectorProgressId) {
        this.detectorProgressId = pDetectorProgressId;
    }
    public String getDetectorMaintenanceCode() {
        return detectorMaintenanceCode;
    }
    public void setDetectorMaintenanceCode(String pDetectorMaintenanceCode) {
        this.detectorMaintenanceCode = pDetectorMaintenanceCode;
    }
    public long getDetectorMeasurementStationId() {
        return detectorMeasurementStationId;
    }
    public void setDetectorMeasurementStationId(long pDetectorMeasurementStationId) {
        this.detectorMeasurementStationId = pDetectorMeasurementStationId;
    }
    public String getDetectorExternalCode() {
        return detectorExternalCode;
    }
    public void setDetectorExternalCode(String pDetectorExternalCode) {
        this.detectorExternalCode = pDetectorExternalCode;
    }
    public long getDetectorSubSystemId() {
        return detectorSubSystemId;
    }
    public void setDetectorSubSystemId(long pDetectorSubSystemId) {
        //  logger.info("pDetectorTypeId =" + pDetectorTypeId);
        this.detectorSubSystemId = pDetectorSubSystemId;
    }
    public long getDetectorUnitId() {
        return detectionUnitId;
    }
    public void setDetectorUnitId(long pDetectionUnitId) {
        this.detectionUnitId = pDetectionUnitId;
    }
    public long getDetectorVisible() {
        return detectorVisible;
    }
    public void setDetectorVisible(long pDetectorVisible) {
        this.detectorVisible = pDetectorVisible;
    }
    public long getDetectorDeleted() {
        return detectorDeleted;
    }
    public void setDetectorDeleted(long pDetectorDeleted) {
        this.detectorDeleted = pDetectorDeleted;
    }
    public String getDetectorDataPreviousUpdate() {
        if (detectorDataPreviousUpdate == null) {
            if (!(detectorDataPreviousUpdateSql==null)) {
                detectorDataPreviousUpdate=detectorDataPreviousUpdateSql.toString().substring(0, 19);
            }
        }
        return detectorDataPreviousUpdate;
    }
    public void setDetectorDataPreviousUpdate(String pDetectorDataPreviousUpdate) {
        this.detectorDataPreviousUpdate = pDetectorDataPreviousUpdate;
        if (!(detectorDataPreviousUpdate == null )) {
            detectorDataPreviousUpdateSql=java.sql.Timestamp.valueOf(detectorDataPreviousUpdate);
        }
    }
    public java.sql.Timestamp getDetectorDataPreviousUpdateSql() {
        if (detectorDataPreviousUpdateSql == null) {
            if (!(detectorDataPreviousUpdate == null)) {
                detectorDataPreviousUpdateSql=java.sql.Timestamp.valueOf(detectorDataPreviousUpdate);
            }
        }
        return detectorDataPreviousUpdateSql;
    }
    public void setDetectorDataPreviousUpdatesql(java.sql.Timestamp pDetectorDataPreviousUpdateSql) {
        this.detectorDataPreviousUpdateSql = pDetectorDataPreviousUpdateSql;
        if (!(detectorDataPreviousUpdate == null)) {
            detectorDataPreviousUpdate=detectorDataPreviousUpdateSql.toString().substring(0, 19);
        }
    }
    public String getDetectorGuid() {
        return detectorGuid;
    }
    public void setDetectorGuid(String pDetectorGuid) {
        this.detectorGuid = pDetectorGuid;
    }
    public String getDetectorDescription() {
        return detectorDescription;
    }
    public void setDetectorDescription(String pDetectorDescription) {
        this.detectorDescription = pDetectorDescription;
    }
    public long getDetectorAreaId() {
        return detectorAreaId;
    }
    public void setDetectorAreaId(long pDetectorAreaId) {
        this.detectorAreaId = pDetectorAreaId;
    }
    public long getDetectorObjectPriorityId() {
        return detectorObjectPriorityId;
    }
    public void setDetectorObjectPriorityId(long pDetectorObjectPriorityId) {
        this.detectorObjectPriorityId = pDetectorObjectPriorityId;
    }
    public long getDetectorParkingHouseId() {
        return detectorParkingHouseId;
    }
    public void setDetectorParkingHouseId(long pDetectorParkingHouseId) {
        this.detectorParkingHouseId = pDetectorParkingHouseId;
    }
    public String getCreated() {
        if (created == null) {
            if (!(createdSql==null)) {
                created=createdSql.toString().substring(0, 19);
            }
        }
        return created;
    }
    public void setCreated(String pCreated) {
        this.created = pCreated;
        if (!(created == null )) {
            createdSql=java.sql.Timestamp.valueOf(created);
        }
    }
    public java.sql.Timestamp getCreatedSql() {
        if (createdSql == null) {
            if (!(created == null)) {
                createdSql=java.sql.Timestamp.valueOf(created);
            }
        }
        return createdSql;
    }
    public void setCreatedSql(java.sql.Timestamp pMeasurementTimeSql) {
        this.createdSql = pMeasurementTimeSql;
        if (!(createdSql == null)) {
            created=createdSql.toString().substring(0, 19);
        }
    }
    @Override
    public String toString() {
        return 	"OmniaMeasurementData [omniaCode = " +omniaCode  +
                ", omniaName = " + omniaName +
                ", omniaPublicationStatus = " +omniaPublicationStatus +
                ", intersectionId = " + intersectionId +
                ", controllerId =" + controllerId +
                ", detectorId =" + detectorId +
                ", detectorTypeId =  " + detectorTypeId +
                ", detectorProgressId = " + detectorProgressId +
                ", detectorMaintenanceCode = " + detectorMaintenanceCode +
                ", detectorMeasurementStationId = " + detectorMeasurementStationId +
                ", detectorExternalCode = " + detectorExternalCode +
                ", detectorSubSystemId = " + detectorSubSystemId +
                ", detectionUnitId = " + detectionUnitId +
                ", detectorVisible = " + detectorVisible +
                ", detectorDeleted = " + detectorDeleted +
                ", detectorDataPreviousUpdate = " + detectorDataPreviousUpdate +
                ", DetectorDataPreviousUpdateSql = " + detectorDataPreviousUpdateSql +
                ", detectorGuid = " + detectorGuid +
                ", DetectorDescription = " + detectorDescription +
                ", detectorAreaId = " + detectorAreaId +
                ", detectorObjectPriorityId = " + detectorObjectPriorityId +
                ", detectorParkingHouseId = " + detectorParkingHouseId +
                ", created = " + created +
                ", createdSql = " + createdSql +"]";

    }
    public void MakeEmptyElement() {
        omniaCode =  Long.valueOf(0);
        omniaName =NO_VALUE;
        omniaPublicationStatus =Long.valueOf(0);
        intersectionId =Long.valueOf(0);
        controllerId =Long.valueOf(0);
        detectorId =Long.valueOf(0);
        detectorTypeId =Long.valueOf(0);
        detectorProgressId =Long.valueOf(0);
        detectorMaintenanceCode =NO_VALUE;
        detectorMeasurementStationId =Long.valueOf(0);
        detectorExternalCode =NO_VALUE;
        detectorSubSystemId = Long.valueOf(0);
        detectionUnitId =Long.valueOf(0);
        detectorVisible =Long.valueOf(0);
        detectorDeleted =Long.valueOf(0);
        detectorDataPreviousUpdate ="1970-01-01 00:00:00";
        detectorDataPreviousUpdateSql =java.sql.Timestamp.valueOf("1970-01-01 00:00:00");
        detectorGuid =NO_VALUE;
        detectorDescription =NO_VALUE;
        detectorAreaId =Long.valueOf(0);
        detectorObjectPriorityId =Long.valueOf(0);
        detectorParkingHouseId =Long.valueOf(0);
        created ="1970-01-01 00:00:00";
        createdSql =java.sql.Timestamp.valueOf("1970-01-01 00:00:00");
    }
}
