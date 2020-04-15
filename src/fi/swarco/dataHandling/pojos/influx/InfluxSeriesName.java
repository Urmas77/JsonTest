package fi.swarco.dataHandling.pojos.influx;
import fi.swarco.dataHandling.pojos.SerieLink;
import fi.swarco.omniaDataTransferServices.InfluxUtilities;
import fi.swarco.serviceOperations.SwarcoTimeUtilities;
import org.apache.log4j.Logger;
import static fi.swarco.CONSTANT.*;
public class InfluxSeriesName {
    private static Logger logger = Logger.getLogger(fi.swarco.dataHandling.pojos.influx.InfluxSqlToTimeSerie.class.getName());
    private String SerieName=NO_VALUE;
    private long OmniaCode=INT_EMPTY_ELEMENT;
    private long IntersectionId=Long.valueOf(0);
    private long ControllerId=Long.valueOf(0);
    private long DetectorId=Long.valueOf(0);
    private long Value=Long.valueOf(0);;
    public InfluxSeriesName(){}
    public InfluxSeriesName(
            String serieName,
            long omniaCode,
            long intersectionId,
            long controllerId,
            long detectorId,
            long value
    ) {
        super();
        this.SerieName=serieName;
        this.OmniaCode = omniaCode;
        this.IntersectionId = intersectionId;
        this.ControllerId = controllerId;
        this.DetectorId = detectorId;
        this.Value=value;
    }
    public String getSerieName() {
        return SerieName;
    }
    public void setSerieName(String pSerieName) {
        this.SerieName = pSerieName;
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
    public void setDetectorId(long DetectorId) {
        this.DetectorId = DetectorId;
    }
    public long getValue() {
        return Value;
    }
    public void setValue(long pValue) {
        this.Value = pValue;
    }
    public void fromTimeSerieLinkToSerieName(SerieLink pSl){
        this.SerieName=pSl.getSerieName();
        this.OmniaCode = pSl.getOmniaCode();
        this.IntersectionId = pSl.getIntersectionId();
        this.ControllerId = pSl.getControllerId();
        this.DetectorId= pSl.getDetectorId();
        this.Value=1;
    }


    @Override
    public String toString() {
        return "RETHINK InfluxSerieName [SerieName =" + SerieName +
                ",OmniaCode = " +OmniaCode  +
                ", IntersectionId = " +  IntersectionId +
                ", ControllerId =" + ControllerId +
                ", DetectorId =" + DetectorId +
                ", Value =" + Value + "]";
    }
    public String GetTimeSerieString() {
        String strHelp1;
        String time;
        InfluxUtilities aaa = new InfluxUtilities();
        if (SerieName.equals(NO_VALUE)) {
            return NO_TIME_SERIE_STRING;
        }
        SwarcoTimeUtilities tu = new SwarcoTimeUtilities();
        String strHelp2;
        strHelp2 =SERIENAME_TIMESTAMP;
        strHelp1 = SerieName +",";
        strHelp1 = strHelp1 +"OmniaCode="+ OmniaCode + ",";
        strHelp1 = strHelp1 + "IntersectionId=" +IntersectionId +",";
        strHelp1 = strHelp1 + "ControllerId="+  ControllerId +",";
        strHelp1 = strHelp1 + "DetectorId="+ DetectorId +" ";
        strHelp1 = strHelp1 + "Value="+ Value +" ";
        strHelp1 = strHelp1 +   strHelp2;
        logger.info("strHelp1 = "+ strHelp1);
        return strHelp1;
    }
    public void MakeEmptyElement() {
        SerieName=NO_VALUE;
        OmniaCode =INT_EMPTY_ELEMENT;
        IntersectionId=0;
        ControllerId=0;
        DetectorId=0;
        Value = 0;
    }
}
