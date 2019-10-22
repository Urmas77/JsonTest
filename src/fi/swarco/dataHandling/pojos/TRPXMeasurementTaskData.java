package fi.swarco.dataHandling.pojos;
import fi.swarco.SwarcoEnumerations;

import static fi.swarco.CONSTANT.*;

public class TRPXMeasurementTaskData {
    private long measurementTaskIdindex=NO_IDENTITY;
    private long omniaCode=INT_EMPTY_ELEMENT;
    private long intersectionId;
    private long controllerId;
    private long detectorId;
    private String detectorMeasuresTimestamp;
    private java.sql.Timestamp detectorMeasuresTimestampSql;
    private String permanentDataTimestamp;
    private java.sql.Timestamp permanentDataTimestampSql;
    private String taskType=TT_NOT_DEFINED;
    private long taskStatus;
    private String created;
    private java.sql.Timestamp createdSql;
    public TRPXMeasurementTaskData(){}

    public TRPXMeasurementTaskData(
            long measurementTaskIdindex,
            long omniaCode,
            long intersectionId,
            long controllerId,
            long detectorId,
            String detectorMeasuresTimestamp,
            String permanentDataTimestamp,
            String  taskType,
            long taskStatus,
            String created
    ){
        super();
        this.measurementTaskIdindex=measurementTaskIdindex;
        this.omniaCode=omniaCode;
        this.intersectionId=intersectionId;
        this.controllerId=controllerId;
        this.detectorId=detectorId;
        this.detectorMeasuresTimestamp=detectorMeasuresTimestamp;
        this.permanentDataTimestamp=  permanentDataTimestamp;
        this.taskType=taskType;
        this.taskStatus=taskStatus;
        this.created=created;
        this.detectorMeasuresTimestampSql=java.sql.Timestamp.valueOf(detectorMeasuresTimestamp);
        this.permanentDataTimestampSql=java.sql.Timestamp.valueOf(permanentDataTimestamp);
        this.createdSql=java.sql.Timestamp.valueOf(created);
    }
    public long getMeasurementTaskIdindex() {
        return measurementTaskIdindex;
    }
    public void setMeasurementTaskIdindex(long pMeasurementTaskIdindex) {
        this.measurementTaskIdindex = pMeasurementTaskIdindex;
    }
    public long getOmniaCode() {
        return omniaCode;
    }
    public void setOmniaCode(long pOmniaCode) {
        this.omniaCode = pOmniaCode;
    }
    public long getIntersectionId() {
        return intersectionId;
    }
    public void setIntersectionId(long pIntersectionId) {
        this.intersectionId = pIntersectionId;
    }
    public long getControllerId() {
        return  controllerId;
    }
    public void setControllerId(long pControllerId) {
        this.controllerId =  pControllerId;
    }
    public long getDetectorId() {
        return detectorId;
    }
    public void setDetectorId(long pDetectorId) {
        this.detectorId = pDetectorId;
    }




    public java.sql.Timestamp getDetectorMeasuresTimestampSql() {
        if (detectorMeasuresTimestampSql == null) {
            if (!(detectorMeasuresTimestamp == null)) {
                detectorMeasuresTimestampSql=java.sql.Timestamp.valueOf(detectorMeasuresTimestamp);
            }
        }
        return detectorMeasuresTimestampSql;
    }
    public void setDetectorMeasuresTimestampSql(java.sql.Timestamp pDetectorMeasuresTimestampSql) {
        this.detectorMeasuresTimestampSql = pDetectorMeasuresTimestampSql;
        if (!(this.detectorMeasuresTimestampSql == null)) {
            this.detectorMeasuresTimestamp=this.detectorMeasuresTimestampSql.toString().substring(0, 19);
        }
    }
    public String getDetectorMeasuresTimestamp() {
        if (detectorMeasuresTimestamp == null) {
            if (!(detectorMeasuresTimestampSql==null)) {
                detectorMeasuresTimestamp=detectorMeasuresTimestampSql.toString().substring(0, 19);
            }
        }
    	return detectorMeasuresTimestamp;
    }
    public void setDetectorMeasuresTimestamp(String pDetectorMeasuresTimestamp) {
        this.detectorMeasuresTimestamp = pDetectorMeasuresTimestamp;
        if (!(detectorMeasuresTimestamp == null )) {
            detectorMeasuresTimestampSql=java.sql.Timestamp.valueOf(detectorMeasuresTimestamp);
        }
    }
    public java.sql.Timestamp getPermanentDataTimestampSql() {
        if (permanentDataTimestampSql == null) {
            if (!(permanentDataTimestamp == null)) {
                permanentDataTimestampSql=java.sql.Timestamp.valueOf(permanentDataTimestamp);
            }
        }
        return permanentDataTimestampSql;
    }
    public void setPermanentDataTimestampSql(java.sql.Timestamp pPermanentDataTimestampSql) {
        this.detectorMeasuresTimestampSql = pPermanentDataTimestampSql;
        if (!(this.permanentDataTimestampSql == null)) {
            this.permanentDataTimestamp=this.permanentDataTimestampSql.toString().substring(0, 19);
        }
    }
    public String getPermanentDataTimestamp() {
        if (permanentDataTimestamp == null) {
            if (!(permanentDataTimestampSql==null)) {
                permanentDataTimestamp=permanentDataTimestampSql.toString().substring(0, 19);
            }
        }
        return permanentDataTimestamp;
    }
    public void setPermanentDataTimestamp(String pPermanentDataTimestamp) {
        this.permanentDataTimestamp = pPermanentDataTimestamp;
        if (!(permanentDataTimestamp == null )) {
            permanentDataTimestampSql=java.sql.Timestamp.valueOf(permanentDataTimestamp);
        }
    }
    public String getTaskType() {
        return taskType;
    }
    public void setTaskType(String pTaskType) {
        this.taskType = pTaskType;
    }
    public long getTaskStatus() {
        return taskStatus;
    }
    public void setTaskStatus(long pTaskStatus) {
        this.taskStatus = pTaskStatus;
    }
    public String getCreated() {
        if (this.created == null) {
            if (!(this.createdSql==null)) {
                this.created=this.createdSql.toString().substring(0, 19);
            }
        }
        return created;
    }
    public void setCreated(String pCreated) {
        this.created = pCreated;
        if (!(this.created == null )) {
            this.createdSql=java.sql.Timestamp.valueOf(this.created);
        }
    }
    public java.sql.Timestamp getCreatedSql() {
        if (this.createdSql == null) {
            if (!(this.created == null)) {
                this.createdSql=java.sql.Timestamp.valueOf(this.created);
            }
        }
        return this.createdSql;
    }
    public void setCreatedSql(java.sql.Timestamp pCreatedSql) {
        this.createdSql = pCreatedSql;
        if (!(this.createdSql == null)) {
            this.created=this.createdSql.toString().substring(0, 19);
        }
    }
    @Override
    public String toString() {
        return "JiMeasurementTaskData [measurementTaskIdindex = " +measurementTaskIdindex +
                ", omniaCode = " + omniaCode  +
                ", intersectionId = " + intersectionId  +
                ", controllerId = " + controllerId  +
                ", detectorId = " +detectorId  +
                ", detectorMeasuresTimestamp = " + detectorMeasuresTimestamp +
                ", detectorMeasuresTimestampSql = " + detectorMeasuresTimestampSql +
                ", permanentDataTimestamp = " + permanentDataTimestamp +
                ", permanentDataTimestampSql = " + permanentDataTimestampSql +
                ", taskType = " +  taskType +
                ", taskStatus = " +  taskStatus +
                ", created =" + created +
                ", createdSql =" + createdSql +"]";
    }
    public void MakeEmptyElement() {
        measurementTaskIdindex=EMPTY_ELEMENT;
        omniaCode=INT_EMPTY_ELEMENT;
        intersectionId=Long.valueOf(0);
        controllerId=Long.valueOf(0);
        detectorId=Long.valueOf(0);
        detectorMeasuresTimestamp="1970-01-01 00:00:00";
        detectorMeasuresTimestampSql=java.sql.Timestamp.valueOf("1970-01-01 00:00:00");
        permanentDataTimestamp ="1970-01-01 00:00:00";
        permanentDataTimestampSql =java.sql.Timestamp.valueOf("1970-01-01 00:00:00");
        taskType = TT_NOT_DEFINED;
        taskStatus=Long.valueOf(0);
        created="1970-01-01 00:00:00";
        createdSql =java.sql.Timestamp.valueOf("1970-01-01 00:00:00");
    }
}



