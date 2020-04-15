package fi.swarco.dataHandling.pojos;
import fi.swarco.omniaDataTransferServices.InfluxUtilities;
import fi.swarco.serviceOperations.SwarcoTimeUtilities;
import org.apache.log4j.Logger;
import static fi.swarco.CONSTANT.*;
public class SerieLink {
    private static Logger logger = Logger.getLogger(SerieLink.class.getName());
    private long OmniaCode=INT_EMPTY_ELEMENT;
    private String SerieName=NO_VALUE;
    private long IntersectionId=NO_INTERSECTION_ID;
    private long ControllerId=NO_CONTROLLER_ID;
    private long DetectorId=NO_DETECTOR_ID;
    public SerieLink(){}
    public SerieLink(
           long omniaCode,
            long intersectionId,
            long controllerId,
            long detectorId,
            String serieName
    ) {
        super();
        this.OmniaCode = omniaCode;
        this.IntersectionId = intersectionId;
        this.ControllerId = controllerId;
        this.DetectorId = detectorId;
        this.SerieName=serieName;
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
    public String getSerieName() {
        return SerieName;
    }
    public void setSerieName(String pSerieName) {
        this.SerieName = pSerieName;
    }
    @Override
    public String toString() {
        return "Serielink [OmniaCode = " +OmniaCode  +
                ", IntersectionId = " +  IntersectionId +
                ", ControllerId =" + ControllerId +
                ", DetectorId =" + DetectorId +
                ", SerieName =" + SerieName + "]";
    }
    public String GetSerieLinkInfluxString() {
        String strHelp1;
       // String time;
        InfluxUtilities aaa = new InfluxUtilities();
        if (SerieName.equals(NO_VALUE)) {
            return NO_TIME_SERIE_STRING;
        }
        //SwarcoTimeUtilities tu = new SwarcoTimeUtilities();
        //time ="1971-01-01T00:00:00.0000000Z";
        String strHelp2;
       // strHelp2 =tu.ToNanoSec8601Str(time);
        strHelp2 ="1576591469228722464";
        strHelp1 = "SerieName,";
        strHelp1 = strHelp1 +"OmniaCode="+ OmniaCode + ",";
        strHelp1 = strHelp1 + "IntersectionId=" +IntersectionId +",";
        strHelp1 = strHelp1 + "ControllerId="+  ControllerId +",";
        strHelp1 = strHelp1 + "DetectorId="+ DetectorId +",";
        strHelp1 = strHelp1 + "SerieName="+ SerieName + " ";
        strHelp1 = strHelp1 + "Value=1" + " ";
        strHelp1 = strHelp1 + strHelp2;
        logger.info("strHelp1 = "+ strHelp1);
        return strHelp1;
    }
    public void MakeEmptyElement() {
        OmniaCode =INT_EMPTY_ELEMENT;
        SerieName=NO_VALUE;
        IntersectionId=NO_INTERSECTION_ID;
        ControllerId=NO_CONTROLLER_ID;
        DetectorId=NO_DETECTOR_ID;
    }
}
