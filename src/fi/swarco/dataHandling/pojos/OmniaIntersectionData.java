package fi.swarco.dataHandling.pojos;
import org.apache.log4j.Logger;

import static fi.swarco.CONSTANT.NO_IDENTITY;
import static fi.swarco.CONSTANT.NO_VALUE;
public class OmniaIntersectionData {
    private static Logger logger = Logger.getLogger(OmniaIntersectionData.class.getName());
    private long identity=Long.valueOf(0);
    private long OmniaCode=Long.valueOf(0);
    private String OmniaName=NO_VALUE;
    private long OmniaPublicationStatus=Long.valueOf(0);
    private long IntersectionId=Long.valueOf(0);
    private String IntesectionDescription=NO_VALUE;
    private long IntersectionAreaId=Long.valueOf(0);
    private String IntersectionExternalCode=NO_VALUE;
    private String IntersectionDataPreviousUpdate="1970-01-01 00:00:00";
    private long ControllerId=Long.valueOf(0);
    private String ControllerDescription=NO_VALUE;
    private String ControllerExternalCode=NO_VALUE;
    private String ControllerDataPreviousUpdate="1970-01-01 00:00:00";
    private java.sql.Timestamp controllerDataPreviousUpdateSql=java.sql.Timestamp.valueOf("1970-01-01 00:00:00");
    private java.sql.Timestamp intersectionDataPreviousUpdateSql=java.sql.Timestamp.valueOf("1970-01-01 00:00:00");
    public  OmniaIntersectionData(){}
    public OmniaIntersectionData(
        long identity,
        long omniaCode,
        String omniaName,
        long omniaPublicationStatus,
        long intersectionId,
        String intesectionDescription,
        long intersectionAreaId,
        String intersectionExternalCode,
        String intersectionDataPreviousUpdate,
        long controllerId,
        String controllerDescription,
        String controllerExternalCode,
        String controllerDataPreviousUpdate
        ) {
        super();
        this.identity = identity;
        this.OmniaCode = omniaCode;
        this.OmniaName = omniaName;
        this.OmniaPublicationStatus = omniaPublicationStatus;
        this.IntersectionId = intersectionId;
        this.IntesectionDescription = intesectionDescription;
        this.IntersectionAreaId = intersectionAreaId;
        this.IntersectionExternalCode = intersectionExternalCode;
        this.IntersectionDataPreviousUpdate = intersectionDataPreviousUpdate;
        this.ControllerId = controllerId;
        this.ControllerDescription = controllerDescription;
        this.ControllerExternalCode = controllerExternalCode;
        this.ControllerDataPreviousUpdate = controllerDataPreviousUpdate;
        this.controllerDataPreviousUpdateSql = java.sql.Timestamp.valueOf(controllerDataPreviousUpdate);
        this.intersectionDataPreviousUpdateSql = java.sql.Timestamp.valueOf(intersectionDataPreviousUpdate);
    }
    public long getIdentity() {
        return identity;
    }
   public void setIdentity(long identity) {
        this.identity = identity;
    }
    public long getOmniaCode() {
        return OmniaCode;
    }
    public void setOmniaCode(long omniaCode) {
        this.OmniaCode = omniaCode;
    }
    public String getOmniaName() {
        if (this.OmniaName == null) {
            this.OmniaName = NO_VALUE;
        }
        return this.OmniaName;
    }
    public void setOmniaName(String omniaName) {
        this.OmniaName = omniaName;
    }
    public long getOmniaPublicationStatus() {
        return OmniaPublicationStatus;
    }
    public void setOmniaPublicationStatus(long omniaPublicationStatus) {
        this.OmniaPublicationStatus = omniaPublicationStatus;
    }
    public long getIntersectionId() {
       return IntersectionId;
    }
    public void setIntersectionId(long intersectionId) {
       this.IntersectionId = intersectionId;
    }
    public String getIntesectionDescription() {
      //  logger.info ("from get start IntesectionDescription ="+ IntesectionDescription);
        if (IntesectionDescription == null) {
            this.IntesectionDescription = NO_VALUE;
        }
      //  logger.info ("from get end IntesectionDescription ="+ IntesectionDescription);
        return IntesectionDescription;
    }
    public void setIntesectionDescription(String pIntesectionDescription) {
      //  logger.info ("pIntesectionDescription ="+ pIntesectionDescription);
        if (pIntesectionDescription == null) {
            this.IntesectionDescription = NO_VALUE;
        } else {
            this.IntesectionDescription = pIntesectionDescription;
       }
    }
    public long getIntersectionAreaId() {
        return IntersectionAreaId;
    }
    public void setIntersectionAreaId(long intersectionAreaId) {
        this.IntersectionAreaId = intersectionAreaId;
    }
    public String getIntersectionExternalCode() {
        if (this.IntersectionExternalCode == null) {
            this.IntersectionExternalCode = NO_VALUE;
        }
        return this.IntersectionExternalCode;
    }
    public void setIntersectionExternalCode(String intersectionExternalCode) {
        this.IntersectionExternalCode = intersectionExternalCode;
    }
    public String getIntersectionDataPreviousUpdate() {
        if (IntersectionDataPreviousUpdate == null) {
            if (!(intersectionDataPreviousUpdateSql==null)) {
                IntersectionDataPreviousUpdate=intersectionDataPreviousUpdateSql.toString().substring(0, 19);
            }
        }
        return IntersectionDataPreviousUpdate;
    }
    public void setIntersectionDataPreviousUpdate(String pIntersectionDataPreviousUpdate) {
        this.IntersectionDataPreviousUpdate = pIntersectionDataPreviousUpdate;
        if (!(IntersectionDataPreviousUpdate == null )) {
            intersectionDataPreviousUpdateSql=java.sql.Timestamp.valueOf(IntersectionDataPreviousUpdate);
        }
    }
    public java.sql.Timestamp getUnterSectionDataPreviousUpdate() {
        if (intersectionDataPreviousUpdateSql == null) {
           if (!(IntersectionDataPreviousUpdate == null)) {
               intersectionDataPreviousUpdateSql=java.sql.Timestamp.valueOf(IntersectionDataPreviousUpdate);
           }
        }
        return intersectionDataPreviousUpdateSql;
    }
    public void setInterSectionDataPreviousUpdateSql(java.sql.Timestamp pIntersectionDataPreviousUpdateSql) {
        this.intersectionDataPreviousUpdateSql = pIntersectionDataPreviousUpdateSql;
        if (!(intersectionDataPreviousUpdateSql == null)) {
            IntersectionDataPreviousUpdate=intersectionDataPreviousUpdateSql.toString().substring(0, 19);
        }
    }
    public long getControllerId() {
        return ControllerId;
    }
    public void setControllerId(long controllerId) {
        this.ControllerId = controllerId;
    }
    public String getControllerDescription() {
        if (this.ControllerDescription == null) {
            this.ControllerDescription = NO_VALUE;
        }
        return ControllerDescription;
    }
    public void setControllerDescription(String controllerDescription) {
        this.ControllerDescription = controllerDescription;
    }
    public String getControllerExternalCode() {
        if (this.ControllerExternalCode == null) {
            this.ControllerExternalCode = NO_VALUE;
        }
        return ControllerExternalCode;
    }
    public void setControllerExternalCode(String controllerExternalCode) {
        this.ControllerExternalCode = controllerExternalCode;
    }
    public String getControllerDataPreviousUpdate() {
        if (ControllerDataPreviousUpdate == null) {
            if (!(controllerDataPreviousUpdateSql==null)) {
                ControllerDataPreviousUpdate=intersectionDataPreviousUpdateSql.toString().substring(0, 19);
            }
         }
         return ControllerDataPreviousUpdate;
    }
    public void setControllerDataPreviousUpdate(String pControllerDataPreviousUpdate) {
        this.ControllerDataPreviousUpdate= pControllerDataPreviousUpdate;
        if (!(ControllerDataPreviousUpdate == null )) {
            controllerDataPreviousUpdateSql=java.sql.Timestamp.valueOf(ControllerDataPreviousUpdate);
        }
    }
    public java.sql.Timestamp getControllerDataPreviousUpdateSql() {
        if (controllerDataPreviousUpdateSql == null) {
            if (!(ControllerDataPreviousUpdate == null)) {
                controllerDataPreviousUpdateSql=java.sql.Timestamp.valueOf(ControllerDataPreviousUpdate);
           }
        }
        return controllerDataPreviousUpdateSql;
    }
    public void setControllerDataPreviousUpdateSql(java.sql.Timestamp pControllerDataPreviousUpdateSql) {
        this.controllerDataPreviousUpdateSql = pControllerDataPreviousUpdateSql;
        if (!(intersectionDataPreviousUpdateSql == null)) {
            IntersectionDataPreviousUpdate=intersectionDataPreviousUpdateSql.toString().substring(0, 19);
        }
    }
    @Override
    public String toString() {
        return "OmniaIntersectionData [identity = " +identity +
              ", OmniaCode = " +OmniaCode  +
              ", OmniaName = " + OmniaName +
              ", OmniaPublicationStatus = " + OmniaPublicationStatus +
              ", IntersectionId = " +  IntersectionId +
              ", IntesectionDescription =" + IntesectionDescription +
              ", IntersectionAreaId =" + IntersectionAreaId +
              ", IntersectionExternalCode =" + IntersectionExternalCode +
              ", IntersectionDataPreviousUpdate =" + IntersectionDataPreviousUpdate +
              ", ControllerId =" + ControllerId +
              ", ControllerDescription =" + ControllerDescription +
              ", ControllerExternalCode =" + ControllerExternalCode +
              ", ControllerDataPreviousUpdate =" + ControllerDataPreviousUpdate +
              ", controllerDataPreviousUpdateSql =" + controllerDataPreviousUpdateSql +
              ", intersectionDataPreviousUpdateSql =" + intersectionDataPreviousUpdateSql +"]";
    }
    public void MakeEmptyElement() {
        identity=Long.valueOf(NO_IDENTITY);
        OmniaCode =Long.valueOf(0);
        OmniaName=NO_VALUE;
        OmniaPublicationStatus =Long.valueOf(0);
        IntersectionId=Long.valueOf(0);
        IntesectionDescription=NO_VALUE;
        IntersectionAreaId=Long.valueOf(0);
        IntersectionExternalCode="novalue";
        IntersectionDataPreviousUpdate="1970-01-01 00:00:00";
        ControllerId=Long.valueOf(0);
        ControllerDescription=NO_VALUE;
        ControllerExternalCode=NO_VALUE;
        ControllerDataPreviousUpdate="1970-01-01 00:00:00";
        controllerDataPreviousUpdateSql=java.sql.Timestamp.valueOf("1970-01-01 00:00:00");
        intersectionDataPreviousUpdateSql=java.sql.Timestamp.valueOf("1970-01-01 00:00:00");
    }
}










