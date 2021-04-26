package fi.swarco.dataHandling.pojos;
import org.apache.log4j.Logger;
import static fi.swarco.CONSTANT.*;
public class OmniaControllerStatusDataJson {
    static Logger logger = Logger.getLogger(OmniaControllerStatusDataJson .class.getName());
    private long omniaCode=INT_EMPTY_ELEMENT;
    private long intersectionId;
    private long controllerId;
    private String intersectionCode;
    private long controllerProgramNumber;
    private String controllerProgramDescription;
    private String controllerStatus;
    private String measurementTime;
    private String transferTime;
    private long transferState;
    public  OmniaControllerStatusDataJson() {}
    public OmniaControllerStatusDataJson(
            long omniaCode,
            long intersectionId,
            long controllerId,
            String measurementTime,
            String intersectionCode,
            long controllerProgramNumber,
            String controllerProgramDescription,
            String controllerStatus,
            String transferTime,
            long transferState
    ) {
        super();
        this.omniaCode = omniaCode;
        this.intersectionId = intersectionId;
        this.controllerId = controllerId;
        this.measurementTime = measurementTime;
        this.intersectionCode =intersectionCode;
        this.controllerProgramNumber=controllerProgramNumber;
        this.controllerProgramDescription=controllerProgramDescription;
        this.controllerStatus=controllerStatus;
        this.transferTime=transferTime;
        this.transferState=transferState;
    }
    public long getOmniaCode() {
        return this.omniaCode;
    }
    public void setOmniaCode(long pOmniaCode) {
        this.omniaCode = pOmniaCode;
    }
    public long getIntersectionId() {
        return this.intersectionId;
    }
    public void setIntersectionId(long pIntersectionId) {
        this.intersectionId = pIntersectionId;
    }
    public long getControllerId() {
        return this.controllerId;
    }
    public void setControllerId(long pControllerId) {
        this.controllerId = pControllerId;
    }
    public String getMeasurementTime() {
        return this.measurementTime;
    }
    public void setMeasurementTime(String pMeasurementTime) {
  //      logger.info (" this.measurementTime = "+ this.measurementTime);
        this.measurementTime = pMeasurementTime;
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
    public String getTransferTime() {
        return this.transferTime;
    }
    public void setTransferTime(String pTransferTime) {
        this.transferTime = pTransferTime;
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
                ", transferState = " + transferState +  "]";
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
    }
    public  OmniaControllerStatusData MakeItemFromJsonTransferItem() {
        OmniaControllerStatusData ce = new OmniaControllerStatusData();
        ce.setOmniaCode(getOmniaCode());
        ce.setIntersectionId(getIntersectionId());
        ce.setControllerId(getControllerId());
        ce.setMeasurementTime(getMeasurementTime());
        ce.setIntersectionCode(getIntersectionCode());
        ce.setControllerProgramNumber(getControllerProgramNumber());
        ce.setControllerProgramDescription(getControllerProgramDescription());
        ce.setControllerStatus(getControllerStatus());
        ce.setTransferTime(getTransferTime());
        ce.setTransferState(getTransferState());
        return ce;
   }
}