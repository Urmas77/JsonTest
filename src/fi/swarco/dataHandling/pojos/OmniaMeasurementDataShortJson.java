package fi.swarco.dataHandling.pojos;
import org.apache.log4j.Logger;

import static fi.swarco.CONSTANT.*;

public class OmniaMeasurementDataShortJson {
     static Logger logger = Logger.getLogger(OmniaMeasurementDataShortJson.class.getName());
     private long OmniaCode=INT_EMPTY_ELEMENT;
     private long IntersectionId;
     private long ControllerId;
     private String MeasurementTime;
     private long DetectorId;
     private String DetectorExternalCode;
     private long VehicleCount;
     private long MeanVehicleSpeedJson;
     private long OccupancyProcentJson;
     private long AccurancyJson;
     public  OmniaMeasurementDataShortJson() {}
     public OmniaMeasurementDataShortJson(
             long omniaCode,
             long intersectionId,
             long controllerId,
             String measurementTime,
             long detectorId,
             String detectorExternalCode,
             long VehicleCount,
             long MeanVehicleSpeedJson,
             long OccupancyProcentJson,
             long AccurancyJson
     ) {
         super();
         this.OmniaCode=omniaCode;
         this.IntersectionId=intersectionId;
         this.ControllerId=controllerId;
         this.MeasurementTime=measurementTime;
         this.DetectorId=detectorId;
         this.DetectorExternalCode=detectorExternalCode;
         this.VehicleCount=VehicleCount;
         this.MeanVehicleSpeedJson=MeanVehicleSpeedJson;
         this.OccupancyProcentJson=OccupancyProcentJson;
         this.AccurancyJson=AccurancyJson;
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
     public String getMeasurementTime() {
         return MeasurementTime;
     }
     public void setMeasurementTime(String pMeasurementTime) {
         this.MeasurementTime = pMeasurementTime;
     }
     public long getDetectorId() {
         return DetectorId;
     }
     public void setDetectorId(long detectorId) {
         this.DetectorId = detectorId;
     }
     public String getDetectorExternalCode() {
         return DetectorExternalCode;
     }
     public void setDetectorExternalCode(String detectorExternalCode) {
         this.DetectorExternalCode = detectorExternalCode;
     }
     public long getVehicleCount() {
         return VehicleCount;
     }
     public void setVehicleCount(long VehicleCount) {
         this.VehicleCount = VehicleCount;
     }
     public long getMeanVehicleSpeedJson() {
         return MeanVehicleSpeedJson;
     }
     public void setMeanVehicleSpeedJson(long pMeanVehicleSpeedJson) {
//         logger.info("pMeanVehicleSpeedJson = "+ pMeanVehicleSpeedJson);
         this.MeanVehicleSpeedJson = pMeanVehicleSpeedJson;
     }
     public long getOccupancyProcentJson() {
         return OccupancyProcentJson;
     }
     public void setOccupancyProcentJson(long pOccupancyProcentJson) {
         this.OccupancyProcentJson = pOccupancyProcentJson;
     }
     public long getAccurancyJson() {
         return AccurancyJson;
     }
     public void setAccurancyJson(long AccurancyJson) {
         this.AccurancyJson = AccurancyJson;
     }
     @Override
     public String toString() {
         return "OmniaMeasurementDataShort [OmniaCode = " + OmniaCode +
                 ", IntersectionId = " + IntersectionId +
                 ", ControllerId =" + ControllerId +
                 ", MeasurementTime =" + MeasurementTime +
                 ", DetectorId = " + DetectorId +
                 ", DetectorExternalCode = " + DetectorExternalCode +
                 ", VehicleCount = " + VehicleCount +
                 ", MeanVehicleSpeedJson = " + MeanVehicleSpeedJson +
                 ", OccupancyProcentJson = " + OccupancyProcentJson +
                 ", AccurancyJson = " + AccurancyJson + "]";
     }
     public void MakeEmptyElement() {
         OmniaCode =INT_EMPTY_ELEMENT;
         IntersectionId = 0;
         ControllerId = 0;
         MeasurementTime = "1970-01-01 00:00:00";
         DetectorId = 0;
         DetectorExternalCode = NO_VALUE;
         VehicleCount = 0;
         MeanVehicleSpeedJson = 0;
         OccupancyProcentJson = 0;
         AccurancyJson = 0;
     }
     public  OmniaMeasurementDataShort MakeItemFromJsonTransferItem() {
        OmniaMeasurementDataShort ce = new OmniaMeasurementDataShort();
        ce.setOmniaCode(getOmniaCode());
        ce.setIntersectionId(getIntersectionId());
        ce.setControllerId(getControllerId());
        ce.setMeasurementTime(getMeasurementTime());
        ce.setDetectorId(getDetectorId());
        ce.setDetectorExternalCode(getDetectorExternalCode());
        ce.setVehicleCount(getVehicleCount());
        double dblHelp3;
//      receiver
//      dblHelp3 =  lngHelp1/(DOUBLE_LONG_MULTIPLIER);
    //    logger.info("getMeanVehicleSpeedJson() = " + getMeanVehicleSpeedJson());
        dblHelp3 = getMeanVehicleSpeedJson()/(DOUBLE_LONG_MULTIPLIER);
     //   logger.info("getMeanVehicleSpeedJson() dblHelp3  = " + dblHelp3);
        ce.setMeanVehicleSpeed(dblHelp3);
        dblHelp3 =getOccupancyProcentJson()/(DOUBLE_LONG_MULTIPLIER);
        ce.setOccupancyProcent(dblHelp3);
        dblHelp3 =getAccurancyJson()/(DOUBLE_LONG_MULTIPLIER);
        ce.setAccurancy(dblHelp3);
      //  logger.info("from MakeItemFromJsonTransferItem  ce.toString()" + ce.toString());
         return ce;
    }
}
