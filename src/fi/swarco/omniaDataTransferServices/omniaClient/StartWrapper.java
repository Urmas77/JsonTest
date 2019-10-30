package fi.swarco.omniaDataTransferServices.omniaClient;
import org.apache.log4j.Logger;
import static fi.swarco.CONSTANT.*;
public class StartWrapper {
    private static Logger logger = Logger.getLogger(StartWrapper.class.getName());
    private int omniaCode=NO_OMNIA;
    private int intersectionId=NO_INTERSECTION_ID;
    private int controllerId=NO_CONTROLLER_ID;
    private int detectorId=NO_DETECTOR_ID;
    private String detectorMeasuresTimestamp="1970-01-01 00:00:00";
    private String permanentDataTimestamp="1970-01-01 00:00:00";
    private String  taskType=TT_NOT_DEFINED;
    public  StartWrapper (){}
    public StartWrapper (
            int omniaCode,
            int intersectionId,
            int controllerId,
            int detectorId,
            String detectorMeasuresTimestamp,
            String permanentDataTimestamp,
            String  taskType
    ) {
        super();
        this.omniaCode = omniaCode;
        this.intersectionId = intersectionId;
        this.controllerId = controllerId;
        this.detectorId = detectorId;
        this.detectorMeasuresTimestamp=detectorMeasuresTimestamp;
        this.permanentDataTimestamp=permanentDataTimestamp;
        this.taskType=taskType;
    }
    public int getOmniaCode() {
        return omniaCode;
    }
    public void setOmniaCode(int pOmniaCode) {
        this.omniaCode = pOmniaCode;
    }
    public int getIntersectionId() {
        return intersectionId;
    }
    public void setIntersectionId(int pIntersectionId) {
        this.intersectionId = pIntersectionId;
    }
    public int getControllerId() {
        return controllerId;
    }
    public void setControllerId(int pControllerId) {
        this.controllerId = pControllerId;
    }
    public int getDetectorId() {
        return detectorId;
    }
    public void setDetectorId(int pDetectorId) {
        this.detectorId = pDetectorId;
    }
    public String getDetectorMeasuresTimestamp() {
        return detectorMeasuresTimestamp;
    }
    public void setDetectorMeasuresTimestamp(String pDetectorMeasuresTimestamp) {
        this.detectorMeasuresTimestamp = pDetectorMeasuresTimestamp;
    }
    public String getPermanentDataTimestamp() {
        return permanentDataTimestamp;
    }
    public void setPermanentDataTimestamp(String pPermanentDataTimestamp) {
        this.permanentDataTimestamp = pPermanentDataTimestamp;
    }
    public String getTaskType() {
        return taskType;
    }
    public void setTaskType(String pTaskType) {
        this.taskType = pTaskType;
    }
    @Override
    public String toString() {
        return "ParameterWrapper  [omniaCode = " + omniaCode +
                ", intersectionId = " + intersectionId  +
                ", controllerId = " + controllerId  +
                ", detectorId = " + detectorId  +
                ", detectorMeasuresTimestamp = " + detectorMeasuresTimestamp  +
                ", permanentDataTimestamp = " + permanentDataTimestamp  +
                ", taskType = " + taskType +"]";
    }

    public void MakeEmptyElement() {
        omniaCode=NO_OMNIA;
        intersectionId=NO_INTERSECTION_ID;
        controllerId=NO_CONTROLLER_ID;
        detectorId=NO_DETECTOR_ID;
        detectorMeasuresTimestamp="1970-01-01 00:00:00";
        permanentDataTimestamp="1970-01-01 00:00:00";
        taskType = TT_NOT_DEFINED;
    }
}