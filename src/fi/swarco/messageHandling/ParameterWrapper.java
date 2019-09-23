package fi.swarco.messageHandling;
import org.apache.log4j.Logger;
import static fi.swarco.CONSTANT.*;
public class ParameterWrapper {
    private static Logger logger = Logger.getLogger(ParameterWrapper.class.getName());
    private int omniaCode=NO_OMNIA;
    private String omniaName=NO_VALUE;
    private int intersectionId=NO_INTERSECTION_ID;
    private String intersectionDescription=NO_VALUE;
    private int controllerId=NO_CONTROLLER_ID;
    private String controllerExternalCode=NO_VALUE;
    private int detectorId=NO_DETECTOR_ID;
    private String detectorExternalCode=NO_VALUE;
    private String startTime=UNIX_START_TIME;
    private String endTime=UNIX_START_TIME;
    private int omniaCodeCounter=0;
    private int omniaNameCounter=0;
    private int intersectionIdCounter=0;
    private int intersectionDescriptionCounter=0;
    private int controllerIdCounter=0;
    private int controllerExternalCodeCounter=0;
    private int detectorIdCounter =0;
    private int detectorExternalCodeCounter=0;
    private int startTimeCounter=0;
    private int endTimeCounter=0;
// parameter names on upper case
    private String strOmniaCode="OMNIA_CODE";
    private String strOmniaName="OMNIA_NAME";
    private String strIntersectionId="INTERSECTION_ID";
    private String strIntersectionDescription="INTERSECTION_DESCRIPTION";
    private String strControllerId="CONTROLLER_ID";
    private String strControllerExternalCode="CONTROLLER_EXTERNAL_CODE";
    private String strDetectorId ="DETECTOR_ID";
    private String strDetectorExternalCode="DETECTOR_EXTERNAL_CODE";
    private String strStartTime="START_TIME";
    private String strEndTime="END_TIME";
// Extra fields from checkings
    private String extraValues =NO_VALUE;
public  ParameterWrapper (){}
    public  ParameterWrapper (
             int omniaCode,
             String omniaName,
             int intersectionId,
             String intersectionDescription,
             int controllerId,
             String controllerExternalCode,
             int detectorId,
            String detectorExternalCode,
            String startTime,
            String endTime,
            int omniaCodeCounter,
            int omniaNameCounter,
            int intersectionIdCounter,
            int intersectionDescriptionCounter,
            int controllerIdCounter,
            int controllerExternalCodeCounter,
            int detectorIdCounter,
            int detectorExternalCodeCounter,
            int startTimeCounter,
            int endTimeCounter
    ) {
        super();
        this.omniaCode = omniaCode;
        this.omniaName = omniaName;
        this.intersectionId = intersectionId;
        this.intersectionDescription = intersectionDescription;
        this.controllerId = controllerId;
        this.controllerExternalCode = controllerExternalCode;
        this.detectorId = detectorId;
        this.detectorExternalCode = detectorExternalCode;
        this.startTime = startTime;
        this.endTime = endTime;
        this.omniaCodeCounter = omniaCodeCounter;
        this.omniaNameCounter = omniaNameCounter;
        this.intersectionIdCounter = intersectionIdCounter;
        this.intersectionDescriptionCounter = intersectionDescriptionCounter;
        this.controllerIdCounter = controllerIdCounter;
        this.controllerExternalCodeCounter = controllerExternalCodeCounter;
        this.detectorIdCounter = detectorIdCounter;
        this.detectorExternalCodeCounter = detectorExternalCodeCounter;
        this.startTimeCounter = startTimeCounter;
        this.endTimeCounter = endTimeCounter;
    }
    public int getOmniaCode() {
        return omniaCode;
    }
    public void setOmniaCode(int pOmniaCode) {
        this.omniaCode = pOmniaCode;
    }
    public String getOmniaName() {
        return omniaName;
    }
    public void setOmniaName(String pOmniaName) {
        this.omniaName = pOmniaName;
    }
    public int getIntersectionId() {
        return intersectionId;
    }
    public void setIntersectionId(int pIntersectionId) {
        this.intersectionId = pIntersectionId;
    }
    public String getIntersectionDescription() {
        return intersectionDescription;
    }
    public void setIntersectionDescription(String pIntersectionDescription) {
        this.intersectionDescription = pIntersectionDescription;
    }
    public int getControllerId() {
        return controllerId;
    }
    public void setControllerId(int pControllerId) {
        this.controllerId = pControllerId;
    }
    public String getControllerExternalCode() {
        return controllerExternalCode;
    }
    public void setControllerExternalCode(String pControllerExternalCode) {
        this.controllerExternalCode = pControllerExternalCode;
    }
    public int getDetectorId() {
        return detectorId;
    }
    public void setDetectorId(int pDetectorId) {
        this.detectorId = pDetectorId;
    }
    public String getDetectorExternalCode() {
        return detectorExternalCode;
    }
    public void setDetectorExternalCode(String pDetectorExternalCode) {
        this.detectorExternalCode = pDetectorExternalCode;
    }
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String pStartTime) {
        this.startTime = pStartTime;
    }
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String pEndTime) {
        this.endTime = pEndTime;
    }
    public int getOmniaCodeCounter() {
        return omniaCodeCounter;
    }
    public void setOmniaCodeCounter(int pOmniaCodeCounter) {
        this.omniaCodeCounter = pOmniaCodeCounter;
    }
    public int getOmniaNameCounter() {
        return omniaNameCounter;
    }
    public void setOmniaNameCounter(int pOmniaNameCounter) {
        this.omniaNameCounter = pOmniaNameCounter;
    }
    public int getIntersectionIdCounter() {
        return intersectionIdCounter;
    }
    public void setIntersectionIdCounter(int pIntersectionIdCounter) {
        this.intersectionIdCounter = pIntersectionIdCounter;
    }
    public int getIntersectionDescriptionCounter() {
        return intersectionDescriptionCounter;
    }
    public void setIntersectionDescriptionCounter(int pIntersectionDescriptionCounter) {
        this.intersectionDescriptionCounter = pIntersectionDescriptionCounter;
    }
    public int getControllerIdCounter() {
        return controllerIdCounter;
    }
    public void setControllerIdCounter(int pControllerIdCounter) {
        this.controllerIdCounter = pControllerIdCounter;
    }
    public int getControllerExternalCodeCounter() {
        return controllerExternalCodeCounter;
    }
    public void setControllerExternalCodeCounter(int pControllerExternalCodeCounter) {
        this.controllerExternalCodeCounter = pControllerExternalCodeCounter;
    }
    public int getDetectorIdCounter() {
        return detectorIdCounter;
    }
    public void setDetectorIdCounter(int pDetectorIdCounter) {
        this.detectorIdCounter = pDetectorIdCounter;
    }
    public int getDetectorExternalCodeCounter() {
        return detectorExternalCodeCounter;
    }
    public void setDetectorExternalCodeCounter(int pDetectorExternalCodeCounter) {
        this.detectorExternalCodeCounter = pDetectorExternalCodeCounter;
    }
    public int getStartTimeCounter() {
        return startTimeCounter;
    }
    public void setStartTimeCounter(int pStartTimeCounter) {
        this.startTimeCounter = pStartTimeCounter;
    }
    public int getEndTimeCounter() {
        return endTimeCounter;
    }
    public void setEndTimeCounter(int pEndTimeCounter) {
        this.endTimeCounter = pEndTimeCounter;
    }
    public String  getStrOmniaCode() {
        return strOmniaCode;
    }
    public String getStrOmniaName() {
        return strOmniaName;
    }
    public String getStrIntersectionId() {
        return strIntersectionId;
    }
    public String getStrIntersectionDescription() {
        return strIntersectionDescription;
    }
    public String getStrControllerId() {
        return strControllerId;
    }
    public String getStrControllerExternalCode() {
        return strControllerExternalCode;
    }
    public String getStrDetectorId() {
        return strDetectorId;
    }
    public String getStrDetectorExternalCode() {
        return strDetectorExternalCode;
    }
    public String getStrStartTime() {
        return strStartTime;
    }
    public String getStrEndTime() {
        return strEndTime;
    }
// for extra values return
    public String getExtraValues() {
        return extraValues;
    }
    public void setExtraValues(String pExtraValues) {
        this.extraValues = pExtraValues;
    }
    public int StoreFieldToWrapper(String pFieldName,String pFieldValue) {
        String strHelp1 = pFieldName.toUpperCase();
        String strExtra = NO_VALUE;
        if (strHelp1.equalsIgnoreCase(strOmniaCode)) {
            if (getOmniaCodeCounter() == 0) {
                setOmniaCode(new Integer(pFieldValue));
                setOmniaCodeCounter(1);
            } else {
                setOmniaCodeCounter(getOmniaCodeCounter() + 1);
                strExtra = strExtra + "," + pFieldValue;
            }
        } else if (strHelp1.equalsIgnoreCase(strOmniaName)) {
            if (getOmniaNameCounter() == 0) {
                setOmniaName(pFieldValue);
                setOmniaNameCounter(1);
            } else {
                setOmniaNameCounter(getOmniaNameCounter() + 1);
                strExtra = strExtra + "," + pFieldValue;
            }
        } else if (strHelp1.equalsIgnoreCase(strIntersectionId)) {
            if (getIntersectionIdCounter() == 0) {
                setIntersectionId(new Integer(pFieldValue));
                setIntersectionIdCounter(1);
            } else {
                setIntersectionIdCounter(getIntersectionIdCounter() + 1);
                strExtra = strExtra + "," + pFieldValue;
            }
        } else if (strHelp1.equalsIgnoreCase(strIntersectionDescription)) {
            if (getIntersectionDescriptionCounter() == 0) {
                setIntersectionDescription(pFieldValue);
                setIntersectionDescriptionCounter(1);
            } else {
                setIntersectionDescriptionCounter(getIntersectionDescriptionCounter() + 1);
                strExtra = strExtra + "," + pFieldValue;
            }
        } else if (strHelp1.equalsIgnoreCase(strControllerId)) {
            if (getControllerIdCounter() == 0) {
                setControllerId(new Integer(pFieldValue));
                setControllerIdCounter(1);
            } else {
                setControllerIdCounter(getControllerIdCounter() + 1);
                strExtra = strExtra + "," + pFieldValue;
            }
        } else if (strHelp1.equalsIgnoreCase(strControllerExternalCode)) {
            if (getControllerExternalCodeCounter() == 0) {
                setControllerExternalCode(pFieldValue);
                setControllerExternalCodeCounter(1);
            } else {
                setControllerExternalCodeCounter(getControllerExternalCodeCounter() + 1);
                strExtra = strExtra + "," + pFieldValue;
            }
        } else if (strHelp1.equalsIgnoreCase(strDetectorId)) {
            if (getDetectorIdCounter() == 0) {
                setDetectorId(new Integer(pFieldValue));
                setDetectorIdCounter(1);
            } else {
                setDetectorIdCounter(getDetectorIdCounter() + 1);
                strExtra = strExtra + "," + pFieldValue;
            }
        } else if (strHelp1.equalsIgnoreCase(strDetectorExternalCode)) {
            if (getDetectorExternalCodeCounter() == 0) {
                setDetectorExternalCode(pFieldValue);
                setDetectorExternalCodeCounter(1);
            } else {
                setDetectorExternalCodeCounter(getDetectorExternalCodeCounter() + 1);
                strExtra = strExtra + "," + pFieldValue;
            }
        } else if (strHelp1.equalsIgnoreCase(strStartTime)) {
            if (getStartTimeCounter() == 0) {
                setStartTime(pFieldValue);
                setStartTimeCounter(1);
            } else {
                setStartTimeCounter(getStartTimeCounter() + 1);
                strExtra = strExtra + "," + pFieldValue;
            }
        } else if (strHelp1.equalsIgnoreCase(strEndTime)) {
            if (getEndTimeCounter() == 0) {
                setEndTime(pFieldValue);
                setEndTimeCounter(1);
            } else {
                setEndTimeCounter(getEndTimeCounter() + 1);
                strExtra = strExtra + "," + pFieldValue;
            }
        } else {
            strExtra = strExtra + "," + pFieldValue;
        }
        setExtraValues(strExtra);
        return INT_RET_OK;
    }
    public ReturnWrapper   CheckStoreResult(){
    // is there extra values
    // if there is no extra values --> counters are ok
    // are counters OK = Maxvalue 1
        String strHelp1="";
        if (!(getExtraValues().equalsIgnoreCase(NO_VALUE))) {
            logger.info("StoreResultsToString =" + StoreResultsToString());
            strHelp1=StoreResultsToString();
        }
// check counter values
        ReturnWrapper rw = CheckCounterValues();
        logger.info("rw.toString() = " +rw.toString());
        rw.setRetMessage(strHelp1);
        return rw;
    }
    private ReturnWrapper   CheckCounterValues(){
        // is there extra values
        // if there is no extra values --> counters are ok
        // are counters OK = Maxvalue 1
// check counter values
        ReturnWrapper rw =new ReturnWrapper();
        rw.MakeEmptyElement();
        rw.setRetCode(RET_OK);
        if (getOmniaCodeCounter() > 1) {
            logger.info("Too many OmniaCodes getOmniaCodeCounter() = "+ getOmniaCodeCounter());
            rw.setRetMessage("Too many OmniaCodes getOmniaCodeCounter() = "+ getOmniaCodeCounter());
            rw.setRetString("Too many OmniaCodes getOmniaCodeCounter() = "+ getOmniaCodeCounter());
            rw.setRetCode(RET_NOT_OK);
        }
        if (getOmniaNameCounter() >1 ) {
            logger.info("Too many OmniaNames getOmniaNameCounter() = "+ getOmniaNameCounter());
            rw.setRetMessage("Too many OmniaNames getOmniaNameCounter() = "+ getOmniaNameCounter());
            rw.setRetString(rw.getRetString() + ","+"Too many OmniaNames getOmniaNameCounter() = "+ getOmniaNameCounter());
            rw.setRetCode(RET_NOT_OK);
        }
        if (getIntersectionIdCounter() > 1) {
            logger.info("Too many IntersectionIds getIntersectionIdCounter() = "+ getIntersectionIdCounter());
            rw.setRetMessage("Too many IntersectionIds getIntersectionIdCounter() = "+ getIntersectionIdCounter());
            rw.setRetString(rw.getRetString() + ","+"Too many IntersectionIds getIntersectionIdCounter() = "+ getIntersectionIdCounter());
            rw.setRetCode(RET_NOT_OK);
        }
        if (getIntersectionDescriptionCounter() > 1) {
            logger.info("Too many IntersectionDescriptions getIntersectionDescriptionCounter() = "+ getIntersectionDescriptionCounter());
            rw.setRetMessage("Too many IntersectionDescriptions getIntersectionDescriptionCounter() = "+ getIntersectionDescriptionCounter());
            rw.setRetString(rw.getRetString() + ","+"Too many IntersectionDescriptions getIntersectionDescriptionCounter() = "+ getIntersectionDescriptionCounter());
            rw.setRetCode(RET_NOT_OK);
        }
        if (getControllerIdCounter() >1) {
            logger.info("Too many ControllerIds getControllerIdCounter() = "+ getControllerIdCounter());
            rw.setRetMessage("Too many ControllerIds getControllerIdCounter() = "+ getControllerIdCounter());
            rw.setRetString(rw.getRetString() + ","+"Too many ControllerIds getControllerIdCounter() = "+ getControllerIdCounter());
            rw.setRetCode(RET_NOT_OK);
        }
        if (getControllerExternalCodeCounter() > 1) {
            logger.info("Too many ControllerExternalCodes getControllerExternalCodeCounter() = "+ getControllerExternalCodeCounter());
            rw.setRetMessage("Too many ControllerExternalCodes getControllerExternalCodeCounter() = "+ getControllerExternalCodeCounter());
            rw.setRetString(rw.getRetString() + ","+"Too many ControllerExternalCodes getControllerExternalCodeCounter() = "+ getControllerExternalCodeCounter());
            rw.setRetCode(RET_NOT_OK);
        }
        if (getDetectorIdCounter() >1) {
            logger.info("Too many DetectorIds getDetectorIdCounter() = "+ getDetectorIdCounter());
            rw.setRetMessage("Too many DetectorIds getDetectorIdCounter() = "+ getDetectorIdCounter());
            rw.setRetString(rw.getRetString() + ","+"Too many DetectorIds getDetectorIdCounter() = "+ getDetectorIdCounter());
            rw.setRetCode(RET_NOT_OK);
        }
        if (getDetectorExternalCodeCounter() >1 ) {
            logger.info("Too many DetectorExternalCodes getDetectorPublicNameCounter() = "+ getDetectorExternalCodeCounter());
            rw.setRetMessage("Too many DetectorExternalCodes getDetectorExternalCodeCounter() = "+ getDetectorExternalCodeCounter());
            rw.setRetString(rw.getRetString() + ","+"Too many DetectorPublicName getDetectorPublicNameCounter() = "+ getDetectorExternalCodeCounter());
            rw.setRetCode(RET_NOT_OK);
        }
        if (getStartTimeCounter() > 1) {
            logger.info("Too many StartTimes getStartTimeCounter() = "+ getStartTimeCounter());
            rw.setRetMessage("Too many StartTimes getStartTimeCounter() = "+ getStartTimeCounter());
            rw.setRetString(rw.getRetString() + ","+"Too many StartTimes getStartTimeCounter() = "+ getStartTimeCounter());
            rw.setRetCode(RET_NOT_OK);
        }
        if (getEndTimeCounter() > 1) {
            logger.info("Too many EndTimes getEndTimeCounter() = "+ getEndTimeCounter());
            rw.setRetMessage("Too many EndTimes getEndTimeCounter() = "+ getEndTimeCounter());
            rw.setRetString(rw.getRetString() + ","+"Too many EndTimes getEndTimeCounter() = "+ getEndTimeCounter());
            rw.setRetCode(RET_NOT_OK);
        }
        return rw;
    }
    @Override
    public String toString() {
        return "ParameterWrapper  [omniaCode = " + omniaCode +
                ", omniaName = " + omniaName  +
                ", intersectionId = " + intersectionId  +
                ", intersectionDescription = " + intersectionDescription  +
                ", controllerId = " + controllerId  +
                ", controllerExternalCode = " + controllerExternalCode  +
                ", detectorId = " + detectorId  +
                ", detectorExternalCode = " + detectorExternalCode  +
                ", startTime = " + startTime  +
                ", endTime = " + endTime  +
                ",omniaCodeCounter = " +omniaCodeCounter +
                ",omniaNameCounter = " +omniaNameCounter +
                ",intersectionIdCounter = " +intersectionIdCounter +
                ",intersectionDescriptionCounter = " +intersectionDescriptionCounter +
                ",controllerIdCounter = " +controllerIdCounter +
                ",controllerExternalCodeCounter = " +controllerExternalCodeCounter +
                ",detectorIdCounter = " +detectorIdCounter +
                ",detectorExternalCodeCounter = " +detectorExternalCodeCounter +
                ",startTimeCounter = " +startTimeCounter +
                ",endTimeCounter = " +endTimeCounter +
                ",strOmniaCode = " +strOmniaCode +
                ",strOmniaName = " +strOmniaName +
                ",strIntersectionId = " +strIntersectionId +
                ",strIntersectionDescription = " +strIntersectionDescription +
                ",strControllerId = " +strControllerId +
                ",strControllerExternalCode = " +strControllerExternalCode +
                ",strDetectorId = " +strDetectorId +
                ",strDetectorExternalCode = " +strDetectorExternalCode +
                ",strStartTime = " +strStartTime +
                ",strEndTime = " +strEndTime +"]";
    }
    public String StoreResultsToString() {
        return "StroResults [extraValues = " +  extraValues +
                ",omniaCodeCounter = " +omniaCodeCounter +
                ",omniaNameCounter = " +omniaNameCounter +
                ",intersectionIdCounter = " +intersectionIdCounter +
                ",intersectionDescriptionCounter = " +intersectionDescriptionCounter +
                ",controllerIdCounter = " +controllerIdCounter +
                ",controllerExternalCodeCounter = " +controllerExternalCodeCounter +
                ",detectorIdCounter = " +detectorIdCounter +
                ",detectorExternalCodeCounter = " +detectorExternalCodeCounter +
                ",startTimeCounter = " +startTimeCounter +
                ",endTimeCounter = " +endTimeCounter +"]";
    }
    public void MakeEmptyElement() {
        omniaCode=NO_OMNIA;
        omniaName=NO_VALUE;
        intersectionId=NO_INTERSECTION_ID;
        intersectionDescription=NO_VALUE;
        controllerId=NO_CONTROLLER_ID;
        controllerExternalCode=NO_VALUE;
        detectorId=NO_DETECTOR_ID;
        detectorExternalCode=NO_VALUE;
        startTime=UNIX_START_TIME;
        endTime=UNIX_START_TIME;
        omniaCodeCounter=0;
        omniaNameCounter=0;
        intersectionIdCounter=0;
        intersectionDescriptionCounter=0;
        controllerIdCounter=0;
        controllerExternalCodeCounter=0;
        detectorIdCounter =0;
        detectorExternalCodeCounter=0;
        startTimeCounter=0;
        endTimeCounter=0;
        strOmniaCode="OMNIA_CODE";
        strOmniaName="OMNIA_NAME";
        strIntersectionId="INTERSECTION_ID";
        strIntersectionDescription="INTERSECTION_DESCRIPTION";
        strControllerId="CONTROLLER_ID";
        strControllerExternalCode="CONTROLLER_EXTERNAL_CODE";
        strDetectorId ="DETECTOR_ID";
        strDetectorExternalCode="DETECTOR_EXTERNAL_CODE";
        strStartTime="START_TIME";
        strEndTime="END_TIME";
    }
}