package fi.swarco.dataHandling.pojos;
import static fi.swarco.CONSTANT.NO_VALUE;
public class DetectorPermanentData {
    private long omniaCode;
    private long intersectionId;
    private long controllerId;
    private long detectorId;
    private String omniaPublicName;
    private String controllerPublicName;
    private String intersectionPublicName;
    private String detectorPublicName;
    private int intValue;
    private double dblValue;
    private String strValue;
    private double latitude;
    private double longitude;
    private double directiondegrees;
    private String direction;
    private String realCource;
    private String comment1;
    private String comment2;
    private String created;
    public  DetectorPermanentData() {}
    public DetectorPermanentData(
            long omniaCode,
            long intersectionId,
            long controllerId,
            long detectorId,
            String omniaPublicName,
            String controllerPublicName,
            String intersectionPublicName,
            String detectorPublicName,
            int intValue,
            double dblValue,
            String strValue,
            double latitude,
            double longitude,
            double directiondegrees,
            String direction,
            String realCource,
            String comment1,
            String comment2,
            String created
    ) {
        super();
        this.omniaCode=omniaCode;
        this.intersectionId=intersectionId;
        this.controllerId=controllerId;
        this.detectorId=detectorId;
        this.omniaPublicName=omniaPublicName;
        this.controllerPublicName=controllerPublicName;
        this.intersectionPublicName=intersectionPublicName;
        this.detectorPublicName=detectorPublicName;
        this.intValue=intValue;
        this.dblValue=dblValue;
        this.strValue=strValue;
        this.latitude=latitude;
        this.longitude=longitude;
        this.directiondegrees=directiondegrees;
        this.direction=direction;
        this.realCource=realCource;
        this.comment1=comment1;
        this.comment2=comment2;
        this.created=created;
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
        return controllerId;
    }
    public void setControllerId(long controllerId) {
        this.controllerId = controllerId;
    }
    public long getDetectorId() {
        return detectorId;
    }
    public void setDetectorId(long pDetectorId) {
        this.detectorId = pDetectorId;
    }
    public String getOmniaPublicName() {
        return omniaPublicName;
    }
    public void setOmniaPublicName(String pOmniaPublicName) {
        this.omniaPublicName = pOmniaPublicName;
    }
    public String getControllerPublicName() {
        return controllerPublicName;
    }
    public void setControllerPublicName(String pControllerPublicName) {
        this.controllerPublicName = pControllerPublicName;
    }
    public String getIntersectionPublicName() {
        return intersectionPublicName;
    }
    public void setIntersectionPublicName(String pIntersectionPublicName) {
        this.intersectionPublicName = pIntersectionPublicName;
    }
    public String getDetectorPublicName() {
        return detectorPublicName;
    }
    public void setDetectorPublicName(String pDetectorPublicName) {
        this.detectorPublicName = pDetectorPublicName;
    }
    public int  getIntValue() {
        return intValue;
    }
    public void setIntValue(int pIntValue) {
        this.intValue = pIntValue;
    }
    public double  getDblValue() {
        return dblValue;
    }
    public void setDblValue(double pDblValue) {
        this.dblValue = pDblValue;
    }
    public String  getStrValue() {
        return strValue;
    }
    public void setStrValue(String pStrValue) {
        this.strValue = pStrValue;
    }
    public double  getLatitude() {
        return latitude;
    }
    public void setLatitude(double pLatitudeValue) {
        this.latitude = pLatitudeValue;
    }
    public double  getLongitude() {
        return longitude;
    }
    public void setLongitude(double pLongitudeValue) {
        this.longitude = pLongitudeValue;
    }
    public double  getDirectionDegrees() {
        return directiondegrees;
    }
    public void setDirectionDegrees(double pDirectionDegrees) {
        this.directiondegrees = pDirectionDegrees;
    }
    public String  getDirection() {
        return direction;
    }
    public void setDirection(String pDirection) {
        this.direction = pDirection;
    }
    public String  getRealCource() {
        return realCource;
    }
    public void setRealCource(String pRealCource) {
        this.realCource = pRealCource;
    }
    public String  getComment1() {
        return comment1;
    }
    public void setComment1(String pComment1) {
        this.comment1 = pComment1;
    }
    public String  getComment2() {
        return comment2;
    }
    public void setComment2(String pComment2) {
        this.comment2 = pComment2;
    }
    public String getCreated() {
        return created;
    }
    public void setCreated(String pCreated) {
        this.created = pCreated;
    }
    @Override
    public String toString() {
        return "DetectorPermanentData [omniaCode = " + omniaCode +
                ", intersectionId = " + intersectionId +
                ", controllerId =" + controllerId +
                ", detectorId =" + detectorId +
                ",omniaPublicName  =" + omniaPublicName +
                ",controllerPublicName =" + controllerPublicName +
                ",intersectionPublicName  =" + intersectionPublicName +
                ",detectorPublicName  =" + detectorPublicName +
                ",intValue  =" + intValue +
                ",dblValue  =" + dblValue +
                ",strValue  =" + strValue +
                ",latitude  =" + latitude +
                ",longitude  =" + longitude +
                ",directiondegrees =" + directiondegrees +
                ",direction  =" + direction +
                ",realCource  =" + realCource +
                ",comment1  =" + comment1 +
                ",comment2  =" + comment2 +
                ",created  =" + created + "]";
    }
    public void MakeEmptyElement() {
        omniaCode =Long.valueOf(0);
        intersectionId=Long.valueOf(0);
        controllerId=Long.valueOf(0);
        detectorId=Long.valueOf(0);
        omniaPublicName=NO_VALUE;
        controllerPublicName=NO_VALUE;
        intersectionPublicName=NO_VALUE;
        detectorPublicName=NO_VALUE;
        intValue=Integer.valueOf(0);
        dblValue=Double.valueOf(0.0);
        strValue=NO_VALUE;
        latitude=Double.valueOf(0.0);
        longitude=Double.valueOf(0.0);
        directiondegrees=Double.valueOf(0.0);
        direction=NO_VALUE;
        realCource=NO_VALUE;
        comment1=NO_VALUE;
        comment2=NO_VALUE;
        created="1970-01-01 00:00:00";
    }
}

