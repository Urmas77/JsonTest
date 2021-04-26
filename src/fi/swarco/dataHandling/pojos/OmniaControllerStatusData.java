package fi.swarco.dataHandling.pojos;
import org.apache.log4j.Logger;
import static fi.swarco.CONSTANT.*;
public class OmniaControllerStatusData {
    static Logger logger = Logger.getLogger(OmniaControllerStatusData.class.getName());
    private long omniaCode=INT_EMPTY_ELEMENT;
    private long intersectionId;
    private long controllerId;
    private String intersectionCode;
    private long controllerProgramNumber;
    private String controllerProgramDescription;
    private String controllerStatus;
    private String measurementTime;
    private java.sql.Timestamp measurementTimeSql;
    private String transferTime;
    private java.sql.Timestamp transferTimeSql;
    private long transferState;
    public OmniaControllerStatusData() {
    }

    public OmniaControllerStatusData(
            long omniaCode,
            long intersectionId,
            long controllerId,
            String intersectionCode,
            long controllerProgramNumber,
            String controllerProgramDescription,
            String controllerStatus,
            String measurementTime,
            String transferTime,
            long transferState
    ) {
        super();
        this.omniaCode = omniaCode;
        this.intersectionId = intersectionId;
        this.controllerId = controllerId;
        this.intersectionCode=intersectionCode;
        this.controllerProgramNumber=controllerProgramNumber;
        this.controllerProgramDescription=controllerProgramDescription;
        this.controllerStatus=controllerStatus;
        this.measurementTime = measurementTime;
        this.transferTime=transferTime;
        this.transferState=transferState;
        this.measurementTimeSql=java.sql.Timestamp.valueOf(measurementTime);
        this.transferTimeSql=java.sql.Timestamp.valueOf(transferTime);
    }
    public long getOmniaCode() {
        return omniaCode;
    }
    public void setOmniaCode(long omniaCode) {
        this.omniaCode = omniaCode;
    }
    public long getIntersectionId() {
        return intersectionId;
    }
    public void setIntersectionId(long intersectionId) {
        this.intersectionId = intersectionId;
    }
    public long getControllerId() {
        return controllerId;
    }
    public void setControllerId(long controllerId) {
        this.controllerId = controllerId;
    }
    public String getIntersectionCode() {
        return intersectionCode;
    }
    public void setIntersectionCode(String pIntersectionCode) {
        this.intersectionCode = pIntersectionCode;
    }
    public long getControllerProgramNumber() {
        return controllerProgramNumber;
    }
    public void setControllerProgramNumber(long pControllerProgramNumber) {
        this.controllerProgramNumber = pControllerProgramNumber;
    }
    public String getControllerProgramDescription() {
        return controllerProgramDescription;
    }
    public void setControllerProgramDescription(String pControllerProgramDescription) {
        this.controllerProgramDescription = pControllerProgramDescription;
    }
    public String getControllerStatus() {
        return controllerStatus;
    }
    public void setControllerStatus(String pControllerStatus) {
        this.controllerStatus = pControllerStatus;
    }
    public String getMeasurementTime() {
        if (this.measurementTime== null) {
            if (!(this.measurementTimeSql == null)) {
                this.measurementTime = this.measurementTimeSql.toString().substring(0, 19);
            }
        }
        return this.measurementTime;
    }
    public void setMeasurementTime(String pMeasurementTime) {
        this.measurementTime = pMeasurementTime.substring(0, 19);
        if (!(this.measurementTime== null)) {
            this.measurementTimeSql= java.sql.Timestamp.valueOf(this.measurementTime);
            }
    }
    public java.sql.Timestamp getMeasurementTimeSql() {
    if (this.measurementTimeSql== null) {
        if (!(this.measurementTime == null)) {
            this.measurementTimeSql = java.sql.Timestamp.valueOf(this.measurementTime);
        }
    }
    return this.measurementTimeSql;
    }
    public void setMeasurementTimeSql(java.sql.Timestamp pMeasurementTimeSql) {
        this.measurementTimeSql = pMeasurementTimeSql;
        if (!(this.measurementTimeSql== null)) {
            this.measurementTime= this.measurementTimeSql.toString().substring(0, 19);
        }
    }
    public String getTransferTime() {
        if (this.transferTime== null) {
            if (!(this.transferTimeSql == null)) {
                this.transferTime = this.transferTimeSql.toString().substring(0, 19);
            }
        }
        return this.transferTime;
    }
    public void setTransferTime(String pTransferTime) {
        this.transferTime = pTransferTime.substring(0, 19);
        if (!(this.transferTime== null)) {
            this.transferTimeSql= java.sql.Timestamp.valueOf(this.transferTime);
        }
    }
    public java.sql.Timestamp getTransferTimeSql() {
        if (this.transferTimeSql== null) {
            if (!(this.transferTime == null)) {
                this.transferTimeSql = java.sql.Timestamp.valueOf(this.transferTime);
            }
        }
        return this.transferTimeSql;
    }
    public void setTransferTimeSql(java.sql.Timestamp pTransferTimeSql) {
        this.transferTimeSql = pTransferTimeSql;
        if (!(this.transferTimeSql== null)) {
            this.transferTime= this.transferTimeSql.toString().substring(0, 19);
        }
    }
    public long getTransferState() {
        return transferState;
    }
    public void setTransferState(long pTransferState) {
        this.transferState = pTransferState;
    }
    @Override
    public String toString() {
        return "OmniaMeasurementDataShort [omniaCode = " + omniaCode +
                ", intersectionId = " + intersectionId +
                ", controllerId =" + controllerId +
                ", intersectionCode =" + intersectionCode +
                ", controllerProgramNumber =" + controllerProgramNumber +
                ", controllerProgramDescription =" + controllerProgramDescription +
                ", controllerStatus =" + controllerStatus +
                ", measurementTime =" + measurementTime +
                ", transferTime =" + transferTime +
                ", transferState = " + transferState +
                ", measurementTimeSql =" + measurementTimeSql +
                ", transferTimeSql =" + transferTimeSql + "]";
    }

    public void MakeEmptyElement() {
        omniaCode=INT_EMPTY_ELEMENT;
        intersectionId=0;
        controllerId=0;
        intersectionCode=NO_VALUE;
        controllerProgramNumber=0;
        controllerProgramDescription=NO_VALUE;
        controllerStatus=NO_VALUE;
        measurementTime="1970-01-01 00:00:00";
        transferTime="1970-01-01 00:00:00";
        transferState=0;
        measurementTimeSql=java.sql.Timestamp.valueOf("1970-01-01 00:00:00");
        transferTimeSql=java.sql.Timestamp.valueOf("1970-01-01 00:00:00");
    }
    public OmniaControllerStatusDataJson SetJsonTransferItem() {
       OmniaControllerStatusDataJson ce = new  OmniaControllerStatusDataJson();
       ce.setOmniaCode(getOmniaCode());
       ce.setIntersectionId(getIntersectionId());
       ce.setControllerId(getControllerId());
       ce.setMeasurementTime(getMeasurementTime());
       ce.setIntersectionCode(getIntersectionCode());
       ce.setControllerProgramNumber(getControllerProgramNumber());
       ce.setControllerProgramDescription(getControllerProgramDescription());
       ce.setControllerStatus(getControllerStatus());
       ce.setMeasurementTime(getMeasurementTime());
       ce.setTransferTime(getTransferTime());
       ce.setTransferState(getTransferState());
       return ce;
    }
}
