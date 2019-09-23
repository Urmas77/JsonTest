package fi.swarco.messageHandling;
import fi.swarco.CONSTANT;
import static fi.swarco.CONSTANT.*;
public class HtppRequestWrapper {
    private int omniaCode;
    private String omniaPublicName;
    private int intersectionId;
    private int controllerId;
    private int detectorId;
    private String startTime;
    private String endTime;
    private ReturnWrapper CheckParameters(ParameterWrapper pw) {
//  You can give only OmniaPublicname or OmniaCode as query parameter
        // one of them is mandatory
//        If start or entTíme are given check time sysntax
        // only one can be on use at time
        ReturnWrapper rt = new ReturnWrapper();
        rt.MakeEmptyElement();
        rt.setRetString("CheckParameters");
        rt.setRetCode(RET_NOT_OK);
        if ((pw.getOmniaCode()==NO_OMNIA) &&  (pw.getOmniaName().equals(NO_VALUE))) {
            rt.setRetMessage("Omnia not defined!");
            return rt;
        }
        if ((pw.getOmniaCode()!=NO_OMNIA) && (!(pw.getOmniaName().equals(NO_VALUE)))) {
            rt.setRetMessage("Omnia defined twice!");
            return rt;
        }
// find OmniaCode agains known omnia publicName
        if ((pw.getOmniaCode()==NO_OMNIA) && (!(pw.getOmniaName().equals(NO_VALUE)))) {
           MapHandle mh = new MapHandle();
            int intHelp1 = mh.findOmniaCode(pw.getOmniaName());
           pw.setOmniaCode(intHelp1);
        }
        // assumption other ID are unique inside on omnia space
        // only one these pairs can be on use at time
        if  (!(pw.getEndTime().equals(CONSTANT.UNIX_START_TIME))) {
            rt.setRetMessage("Only StartTime is allowed !");
            return rt;
        }
        String strHelp1="CheckParameters";
        rt.setRetString(strHelp1);
        rt.setRetCode(RET_OK);
        rt.setRetMessage("CurrentState parameter list logically OK!");
        return rt;
    }
    public ReturnWrapper CurrentState(ParameterWrapper pw) {
        ReturnWrapper rt = new ReturnWrapper();
        rt.MakeEmptyElement();
         rt=CheckParameters(pw);
         if (rt.getRetCode().equals(RET_NOT_OK)){
            return rt;
        }
//  You can give only Omnia_name or Omnia_Code as query parameter
        // one of them is mandatory
//        If start or entTíme are given check time sysntax
 // assumption other ID are unique inside on omnia space
 // only one these pairs can be on use at time
        if  (!(pw.getEndTime().equals(CONSTANT.UNIX_START_TIME))) {
            rt.setRetMessage("Only StartTime is allowed !");
            rt.setRetString("Only StartTime is allowed !");
            return rt;
        }
        // "everything" is checked make store operations
        String strHelp1 = NO_VALUE;
        if (pw.getOmniaCode()!=NO_OMNIA){
            strHelp1= "OMNIA_CODE=" + pw.getOmniaCode();
        }
        if (!(pw.getOmniaName().equals(NO_VALUE))){
            strHelp1= "OMNIA_NAME=" + pw.getOmniaName();
        }
        if (pw.getIntersectionId()!=NO_INTERSECTION_ID){
            strHelp1= strHelp1 + ",INTERSECTION_ID=" + pw.getIntersectionId();
            //  strHelp1= strHelp1 + ",IntersectionId=" + pw.getIntersectionId();
        }
        if (pw.getControllerId()!=NO_CONTROLLER_ID){
            strHelp1= strHelp1 + ",CONTROLLER_ID=" + pw.getControllerId();
            //           strHelp1= strHelp1 + ",ControllerId=" + pw.getControllerId();
        }
        if (pw.getDetectorId()!=NO_DETECTOR_ID){
            strHelp1= strHelp1 + ",DETECTOR_ID=" + pw.getDetectorId();
           // strHelp1= strHelp1 + ",DetectorId=" + pw.getDetectorId();
        }
       // RETHINK check start time syntax
        if (!(pw.getStartTime().equals(UNIX_START_TIME))){
            strHelp1= ",START_TIME=" + pw.getStartTime();
            //strHelp1= ",StartTime=" + pw.getStartTime();
        }
        strHelp1="CurrentState,"+strHelp1;
        rt.setRetString(strHelp1);
        rt.setRetCode(RET_OK);
        rt.setRetMessage("Histor" + "CurrentState parameter list logically OK!");
        return rt;
    }
    public ReturnWrapper HistoryState(ParameterWrapper pw) {
        ReturnWrapper rt = new ReturnWrapper();
        rt.MakeEmptyElement();
//  You can give only OmniaPublicname as query parameter
        // one of them is mandatory
        // only one can be on use at time
        // start time is mandatory
        // if end time is not given endTIme  currentTime
        if ((pw.getOmniaCode()==NO_OMNIA) &&  (pw.getOmniaName().equals(NO_VALUE))) {
            rt.setRetMessage("Omnia not defined!");
            return rt;
        }
        if ((pw.getOmniaCode()!=NO_OMNIA) && (!(pw.getOmniaName().equals(NO_VALUE)))) {
            rt.setRetMessage("Omnia defined twice!");
            rt.setRetString("Omnia defined twice!");
            return rt;
        }
// assumption other ID are unique inside on omnia space
// "everything" is checked make store operations
        String strHelp1 = NO_VALUE;
        if (pw.getOmniaCode()!=NO_OMNIA){
            strHelp1= "OMNIA_CODE=" + pw.getOmniaCode();
            //strHelp1= "OmniaCode=" + pw.getOmniaCode();
        }
        if (!(pw.getOmniaName().equals(NO_VALUE))){
            strHelp1= "OMNIA_NAME=" + pw.getOmniaName();
            // strHelp1= "OmniaName=" + pw.getOmniaName();
        }
        if (pw.getIntersectionId()!=NO_INTERSECTION_ID){
            strHelp1= strHelp1 + ",INTERSECTION_ID=" + pw.getIntersectionId();
           // strHelp1= strHelp1 + ",IntersectionId=" + pw.getIntersectionId();
        }
        if (pw.getControllerId()!=NO_CONTROLLER_ID){
            strHelp1= strHelp1 + ",CONTROLLER_ID=" + pw.getControllerId();
            // strHelp1= strHelp1 + ",ControllerId=" + pw.getControllerId();
        }
        if (pw.getDetectorId()!=NO_DETECTOR_ID){
            strHelp1= strHelp1 + ",DETECTOR_ID=" + pw.getDetectorId();
            // strHelp1= strHelp1 + ",DetectorId=" + pw.getDetectorId();
        }
        // RETHINK check start time syntax
        if (!(pw.getStartTime().equals(UNIX_START_TIME))){
            strHelp1= strHelp1 +",START_TIME=" + pw.getStartTime();
            //strHelp1= strHelp1 +",StartTime=" + pw.getStartTime();
        }
        // RETHINK check end time syntax
        if (!(pw.getEndTime().equals(UNIX_START_TIME))){
            strHelp1= strHelp1 + ",END_TIME=" + pw.getStartTime();
            // strHelp1= strHelp1 + ",EndTime=" + pw.getStartTime();
        }
// RETHINK check also "order" of time strings
        strHelp1="HistoryState,"+strHelp1;
        rt.setRetString(strHelp1);
        rt.setRetCode(RET_OK);
        rt.setRetMessage("HistoryState parameter list logically OK!");
        rt.setRetString(strHelp1);
        return rt;
    }
}
