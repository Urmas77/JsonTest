 package fi.swarco.dataHandling.pojos;
 import fi.swarco.dataHandling.pojos.OmniaIntersectionData;
 import org.apache.log4j.Logger;

 import static fi.swarco.CONSTANT.*;

 public class OmniaIntersectionControllerData {
     private static Logger logger = Logger.getLogger(OmniaIntersectionControllerData.class.getName());
     private long omniaCode=INT_EMPTY_ELEMENT;
     private String omniaName=NO_VALUE;
     private long omniaPublicationStatus=Long.valueOf(0);
     private long intersectionId=Long.valueOf(0);
     private String intesectionDescription=NO_VALUE;
     private long intersectionAreaId=Long.valueOf(0);
     private long  intersectionMaintenanceAreaId =Long.valueOf(0);
     private String intersectionExternalCode=NO_VALUE;
     private long  intersectionSubSystemId=Long.valueOf(0);
     private long  intersectionVisible=Long.valueOf(0);
     private long  intersectionDeleted=Long.valueOf(0);
     private String intersectionDataPreviousUpdate="1970-01-01 00:00:00";
     private String  intersectionGuid=NO_VALUE;
     private long  intersectionProgressId=Long.valueOf(0);
     private long controllerId=Long.valueOf(0);
     private String controllerDescription=NO_VALUE;
     private long controllerTypeId=Long.valueOf(0);
     private long controllerRoadsideUnitId=Long.valueOf(0);
     private String controllerExternalCode=NO_VALUE;
     private long controllerSubSystemId=Long.valueOf(0);
     private long controllerObjectPriorityId=Long.valueOf(0);
     private long controllerVisible=Long.valueOf(0);
     private long controllerDeleted=Long.valueOf(0);
     private String controllerDataPreviousUpdate="1970-01-01 00:00:00";
     private String controllerGuid=NO_VALUE;
     private java.sql.Timestamp controllerDataPreviousUpdateSql=java.sql.Timestamp.valueOf("1970-01-01 00:00:00");
     private java.sql.Timestamp intersectionDataPreviousUpdateSql=java.sql.Timestamp.valueOf("1970-01-01 00:00:00");
     public  OmniaIntersectionControllerData(){}
     public OmniaIntersectionControllerData(
             long omniaCode,
             String omniaName,
             long omniaPublicationStatus,
             long intersectionId,
             String intesectionDescription,
             long intersectionAreaId,
             long  intersectionMaintenanceAreaId,
             String intersectionExternalCode,
             long  intersectionSubSystemId,
             long  intersectionVisible,
             long  intersectionDeleted,
             String intersectionDataPreviousUpdate,
             String  intersectionGuid,
             long  intersectionProgressId,
             long controllerId,
             String controllerDescription,
             long controllerTypeId,
             long controllerRoadsideUnitId,
             String controllerExternalCode,
             long controllerSubSystemId,
             long controllerObjectPriorityId,
             long controllerVisible,
             long controllerDeleted,
             String controllerDataPreviousUpdate,
             String controllerGuid
     ) {
         super();
         this.omniaCode = omniaCode;
         this.omniaName = omniaName;
         this.omniaPublicationStatus = omniaPublicationStatus;
         this.intersectionId = intersectionId;
         this.intesectionDescription = intesectionDescription;
         this.intersectionAreaId = intersectionAreaId;
         this.intersectionMaintenanceAreaId=intersectionMaintenanceAreaId;
         this.intersectionExternalCode=intersectionExternalCode;
         this.intersectionSubSystemId=intersectionSubSystemId;
         this.intersectionVisible=intersectionVisible;
         this.intersectionDeleted=intersectionDeleted;
         this.intersectionDataPreviousUpdate=intersectionDataPreviousUpdate;
         this.intersectionGuid=intersectionGuid;
         this.intersectionProgressId=intersectionProgressId;
         this.controllerId=controllerId;
         this.controllerDescription=controllerDescription;
         this.controllerTypeId=controllerTypeId;
         this.controllerRoadsideUnitId=controllerRoadsideUnitId;
         this.controllerExternalCode=controllerExternalCode;
         this.controllerSubSystemId=controllerSubSystemId;
         this.controllerObjectPriorityId=controllerObjectPriorityId;
         this.controllerVisible=controllerVisible;
         this.controllerDeleted=controllerDeleted;
         this.controllerDataPreviousUpdate=controllerDataPreviousUpdate;
         this.controllerGuid=controllerGuid;
         this.controllerDataPreviousUpdateSql = java.sql.Timestamp.valueOf(controllerDataPreviousUpdate);
         this.intersectionDataPreviousUpdateSql = java.sql.Timestamp.valueOf(intersectionDataPreviousUpdate);
     }
     public long getOmniaCode() {
         return omniaCode;
     }
     public void setOmniaCode(long pOmniaCode) {
         this.omniaCode = pOmniaCode;
     }
     public String getOmniaName() {
         if (this.omniaName == null) {
             this.omniaName = NO_VALUE;
         }
         return this.omniaName;
     }
     public void setOmniaName(String pOmniaName) {
         this.omniaName = pOmniaName;
     }
     public long getOmniaPublicationStatus() {
         return omniaPublicationStatus;
     }
     public void setOmniaPublicationStatus(long pOmniaPublicationStatus) {
         this.omniaPublicationStatus = pOmniaPublicationStatus;
     }
     public long getIntersectionId() {
         return intersectionId;
     }
     public void setIntersectionId(long pIntersectionId) {
         this.intersectionId = pIntersectionId;
     }
     public String getIntesectionDescription() {
         if (intesectionDescription == null) {
             this.intesectionDescription = NO_VALUE;
         }
         return intesectionDescription;
     }
     public void setIntesectionDescription(String pIntesectionDescription) {
         if (pIntesectionDescription == null) {
             this.intesectionDescription = NO_VALUE;
         } else {
             this.intesectionDescription = pIntesectionDescription;
         }
     }
     public long getIntersectionAreaId() {
         return intersectionAreaId;
     }
     public void setIntersectionAreaId(long pIntersectionAreaId) {
         this.intersectionAreaId = pIntersectionAreaId;
     }
     public long getIntersectionMaintenanceAreaId() {
         return intersectionMaintenanceAreaId;
     }
     public void setIntersectionMaintenanceAreaId(long pIntersectionMaintenanceAreaId) {
         this.intersectionMaintenanceAreaId = pIntersectionMaintenanceAreaId;
     }
     public String getIntersectionExternalCode() {
         if (this.intersectionExternalCode == null) {
             this.intersectionExternalCode = NO_VALUE;
         }
         return this.intersectionExternalCode;
     }
     public void setIntersectionExternalCode(String pIntersectionExternalCode) {
         this.intersectionExternalCode = pIntersectionExternalCode;
     }
     public long getIntersectionSubSystemId() {
         return intersectionSubSystemId;
     }
     public void setIntersectionSubSystemId(long pIntersectionSubSystemId) {
         this.intersectionSubSystemId = pIntersectionSubSystemId;
     }
     public long getIntersectionVisible() {
         return intersectionVisible;
     }
     public void setIntersectionVisible(long pIntersectionVisible) {
         this.intersectionVisible = pIntersectionVisible;
     }
     public long getIntersectionDeleted() {
         return intersectionDeleted;
     }
     public void setIntersectionDeleted(long pIntersectionDeleted) {
         this.intersectionDeleted = pIntersectionDeleted;
     }
     public String getIntersectionDataPreviousUpdate() {
         if (intersectionDataPreviousUpdate == null) {
             if (!(intersectionDataPreviousUpdateSql==null)) {
                 intersectionDataPreviousUpdate=intersectionDataPreviousUpdateSql.toString().substring(0, 19);
             }
         }
         return intersectionDataPreviousUpdate;
     }
     public void setIntersectionDataPreviousUpdate(String pIntersectionDataPreviousUpdate) {
         this.intersectionDataPreviousUpdate = pIntersectionDataPreviousUpdate;
         if (!(intersectionDataPreviousUpdate == null )) {
             intersectionDataPreviousUpdateSql=java.sql.Timestamp.valueOf(intersectionDataPreviousUpdate);
         }
     }
     public java.sql.Timestamp getInterSectionDataPreviousUpdate() {
         if (intersectionDataPreviousUpdateSql == null) {
             if (!(intersectionDataPreviousUpdate == null)) {
                 intersectionDataPreviousUpdateSql=java.sql.Timestamp.valueOf(intersectionDataPreviousUpdate);
             }
         }
         return intersectionDataPreviousUpdateSql;
     }
     public void setInterSectionDataPreviousUpdateSql(java.sql.Timestamp pIntersectionDataPreviousUpdateSql) {
         this.intersectionDataPreviousUpdateSql = pIntersectionDataPreviousUpdateSql;
         if (!(intersectionDataPreviousUpdateSql == null)) {
             intersectionDataPreviousUpdate=intersectionDataPreviousUpdateSql.toString().substring(0, 19);
         }
     }
     public String getIntersectionGuid() {
         return intersectionGuid;
     }
     public void setIntersectionGuid(String pIntersectionGuid) {
         this.intersectionGuid = pIntersectionGuid;
     }
     public long getIntersectionProgressId() {
         return intersectionProgressId;
     }
     public void setIntersectionProgressId(long pIntersectionProgressId) {
         this.intersectionProgressId = pIntersectionProgressId;
     }
     public long getControllerId() {
         return controllerId;
     }
     public void setControllerId(long controllerId) {
         this.controllerId = controllerId;
     }
     public String getControllerDescription() {
         if (this.controllerDescription == null) {
             this.controllerDescription = NO_VALUE;
         }
         return controllerDescription;
     }
     public void setControllerDescription(String pControllerDescription) {
         this.controllerDescription =  pControllerDescription;
     }
     public long getControllerTypeId() {
         return controllerTypeId;
     }
     public void setControllerTypeId(long pControllerTypeId) {
         this.controllerTypeId = pControllerTypeId;
     }
     public long getControllerRoadsideUnitId() {
         return controllerRoadsideUnitId;
     }
     public void setControllerRoadsideUnitId(long pControllerRoadsideUnitId) {
         this.controllerRoadsideUnitId = pControllerRoadsideUnitId;
     }
     public String getControllerExternalCode() {
         if (this.controllerExternalCode == null) {
             this.controllerExternalCode = NO_VALUE;
         }
         return controllerExternalCode;
     }
     public void setControllerExternalCode(String pControllerExternalCode) {
         this.controllerExternalCode = pControllerExternalCode;
     }
     public long getControllerSubSystemId() {
         return controllerSubSystemId;
     }
     public void setControllerSubSystemId(long pControllerSubSystemId) {
         this.controllerSubSystemId = pControllerSubSystemId;
     }
     public long getControllerObjectPriorityId() {
         return controllerObjectPriorityId;
     }
     public void setControllerObjectPriorityId(long pControllerObjectPriorityId) {
         this.controllerObjectPriorityId = pControllerObjectPriorityId;
     }
     public long getControllerVisible() {
         return controllerVisible;
     }
     public void setControllerVisible(long pControllerVisible) {
         this.controllerVisible = pControllerVisible;
     }
     public long getControllerDeleted() {
         return controllerDeleted;
     }
     public void setControllerDeleted(long pControllerDeleted) {
         this.controllerDeleted = pControllerDeleted;
     }
     public String getControllerDataPreviousUpdate() {
         if (controllerDataPreviousUpdate == null) {
             if (!(controllerDataPreviousUpdateSql==null)) {
                 controllerDataPreviousUpdate=intersectionDataPreviousUpdateSql.toString().substring(0, 19);
             }
         }
         return controllerDataPreviousUpdate;
     }
     public void setControllerDataPreviousUpdate(String pControllerDataPreviousUpdate) {
         this.controllerDataPreviousUpdate= pControllerDataPreviousUpdate;
         if (!(controllerDataPreviousUpdate == null )) {
             controllerDataPreviousUpdateSql=java.sql.Timestamp.valueOf(controllerDataPreviousUpdate);
         }
     }
     public java.sql.Timestamp getControllerDataPreviousUpdateSql() {
         if (controllerDataPreviousUpdateSql == null) {
             if (!(controllerDataPreviousUpdate == null)) {
                 controllerDataPreviousUpdateSql=java.sql.Timestamp.valueOf(controllerDataPreviousUpdate);
             }
         }
         return controllerDataPreviousUpdateSql;
     }
     public void setControllerDataPreviousUpdateSql(java.sql.Timestamp pControllerDataPreviousUpdateSql) {
         this.controllerDataPreviousUpdateSql = pControllerDataPreviousUpdateSql;
         if (!(intersectionDataPreviousUpdateSql == null)) {
             intersectionDataPreviousUpdate=intersectionDataPreviousUpdateSql.toString().substring(0, 19);
         }
     }
     public String getControllerGuid() {
         return controllerGuid;
     }
     public void setControllerGuid(String pControllerGuid) {
         this.controllerGuid = pControllerGuid;
     }
     @Override
     public String toString() {
         return "OmniaIntersectionControllerData [omniaCode = " +omniaCode  +
                 ", omniaName = " + omniaName +
                 ", omniaPublicationStatus = " + omniaPublicationStatus +
                 ", intersectionId = " +  intersectionId +
                 ", intesectionDescription =" + intesectionDescription +
                 ", intersectionAreaId =" + intersectionAreaId +
                 ", intersectionMaintenanceAreaId =" + intersectionMaintenanceAreaId +
                 ", intersectionExternalCode =" + intersectionExternalCode +
                 ", intersectionSubSystemId =" + intersectionSubSystemId +
                 ", intersectionVisible =" + intersectionVisible +
                 ", intersectionDeleted =" + intersectionDeleted +
                 ", intersectionExternalCode =" + intersectionExternalCode +
                 ", intersectionExternalCode =" + intersectionExternalCode +
                 ", intersectionDataPreviousUpdate =" + intersectionDataPreviousUpdate +
                 ", intersectionGuid =" + intersectionGuid +
                 ", intersectionProgressId =" + intersectionProgressId +
                 ", controllerId =" + controllerId +
                 ", controllerDescription =" + controllerDescription +
                 ", controllerTypeId =" + controllerTypeId +
                 ", controllerRoadsideUnitId =" + controllerRoadsideUnitId +
                 ", controllerExternalCode =" + controllerExternalCode +
                 ", controllerSubSystemId =" + controllerSubSystemId +
                 ", controllerObjectPriorityId =" + controllerObjectPriorityId +
                 ", controllerVisible =" + controllerVisible +
                 ", controllerDeleted =" + controllerDeleted +
                 ", controllerDataPreviousUpdate =" + controllerDataPreviousUpdate +
                 ", controllerGuid =" + controllerGuid +
                 ", controllerDataPreviousUpdateSql =" + controllerDataPreviousUpdateSql +
                 ", intersectionDataPreviousUpdateSql =" + intersectionDataPreviousUpdateSql +"]";
     }
     public void MakeEmptyElement() {
         omniaCode =INT_EMPTY_ELEMENT;
         omniaName=NO_VALUE;
         omniaPublicationStatus =Long.valueOf(0);
         intersectionId=Long.valueOf(0);
         intesectionDescription=NO_VALUE;
         intersectionAreaId=Long.valueOf(0);
         intersectionMaintenanceAreaId =Long.valueOf(0);
         intersectionExternalCode=NO_VALUE;
         intersectionSubSystemId=Long.valueOf(0);
         intersectionVisible=Long.valueOf(0);
         intersectionDeleted=Long.valueOf(0);
         intersectionDataPreviousUpdate="1970-01-01 00:00:00";
         intersectionGuid=NO_VALUE;
         intersectionProgressId=Long.valueOf(0);
         controllerId=Long.valueOf(0);
         controllerDescription=NO_VALUE;
         controllerTypeId=Long.valueOf(0);
         controllerRoadsideUnitId=Long.valueOf(0);
         controllerExternalCode=NO_VALUE;
         controllerSubSystemId=Long.valueOf(0);
         controllerObjectPriorityId=Long.valueOf(0);
         controllerVisible=Long.valueOf(0);
         controllerDeleted=Long.valueOf(0);
         controllerDataPreviousUpdate="1970-01-01 00:00:00";
         controllerGuid=NO_VALUE;
         controllerDataPreviousUpdateSql=java.sql.Timestamp.valueOf("1970-01-01 00:00:00");
         intersectionDataPreviousUpdateSql=java.sql.Timestamp.valueOf("1970-01-01 00:00:00");
     }
 }











