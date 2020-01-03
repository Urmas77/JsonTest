package fi.swarco.dataHandling.pojos;
import org.apache.log4j.Logger;

import static fi.swarco.CONSTANT.*;

public class SuperData {
    private static Logger logger = Logger.getLogger(SuperData.class.getName());
    private long OmniaCode=INT_EMPTY_ELEMENT;
    private String OmniaName=NO_VALUE;
    private long IntersectionId=NO_INTERSECTION_ID;
    private long ControllerId=NO_CONTROLLER_ID;
    private long DetectorId=NO_DETECTOR_ID;
    public SuperData(){}
    public SuperData(
            long omniaCode,
            long intersectionId,
            long controllerId,
            long detectorId
    ) {
        super();
        this.OmniaCode = omniaCode;
        this.IntersectionId = intersectionId;
        this.ControllerId = controllerId;
        this.DetectorId = detectorId;
    }
    public long getOmniaCode() {
        return OmniaCode;
    }
    public void setOmniaCode(long pOmniaCode) {
        this.OmniaCode = pOmniaCode;
    }
    public long getIntersectionId() {
        return IntersectionId;
    }
    public void setIntersectionId(long pIntersectionId) {
        this.IntersectionId = pIntersectionId;
    }
    public long getControllerId() {
        return ControllerId;
    }
    public void setControllerId(long pControllerId) {
        this.ControllerId = pControllerId;
    }
        public long getDetectorId() {
            return DetectorId;
        }
        public void setDetectorId(long pDetectorId) {
            this.DetectorId = pDetectorId;
        }
    @Override
    public String toString() {
        return "OmniaIntersectionDataClient [OmniaCode = " +OmniaCode  +
                ", IntersectionId = " +  IntersectionId +
                ", ControllerId =" + ControllerId +
                ", DetectorId =" + DetectorId + "]";
    }
    public void MakeEmptyElement() {
        OmniaCode =INT_EMPTY_ELEMENT;
        OmniaName=NO_VALUE;
        IntersectionId=NO_INTERSECTION_ID;
        ControllerId=NO_CONTROLLER_ID;
        DetectorId=NO_DETECTOR_ID;
    }
}
