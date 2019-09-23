package fi.swarco.dataHandling.pojos;
import org.apache.log4j.Logger;
import static fi.swarco.CONSTANT.NO_VALUE;
public class SuperData {
    private static Logger logger = Logger.getLogger(SuperData.class.getName());
    private long OmniaCode=Long.valueOf(0);
    private String OmniaName=NO_VALUE;
    private long IntersectionId=Long.valueOf(0);
    private long ControllerId=Long.valueOf(0);
    private long DetectorId=Long.valueOf(0);
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
        public long getDetectorId() {
            return DetectorId;
        }
        public void setDetectorId(long controllerId) {
            this.ControllerId = DetectorId;
        }
    @Override
    public String toString() {
        return "OmniaIntersectionDataClient [OmniaCode = " +OmniaCode  +
                ", IntersectionId = " +  IntersectionId +
                ", ControllerId =" + ControllerId +
                ", DetectorId =" + DetectorId + "]";
    }
    public void MakeEmptyElement() {
        OmniaCode =Long.valueOf(0);
        OmniaName=NO_VALUE;
        IntersectionId=Long.valueOf(0);
        ControllerId=Long.valueOf(0);
        DetectorId=Long.valueOf(0);
    }
}
