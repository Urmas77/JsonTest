package fi.swarco;
public class SwarcoEnumerations {
public SwarcoEnumerations(){}
    //public enum CurrentPlan {ISOLATE,LocalCO,CENTRAL,UTC, MANUAL,PRIO}
    public enum RequestOriginType {FROMJSONTOMYSQL,NORMALROAD}
    public enum LoggingDestinationType {API_CLIENT,OMNIA_CLIENT,OMNIA_HTTPSERVER,CLOUD_HTTPSERVER,NOT_DEFINED}
    public enum ApiMessageCodes {SUCCESSFUL,UNSUCCESSFUL,ERROR,REQUEST,RESPONSE,DATAERROR,NOT_DEFINED}
    public enum ConnectionType {MYSQL_LOCAL_JATRI2,SQLSERVER_LOCAL_JOMNIATEST,INFLUX_LOCAL,SQLSERVER_LOCAL_lAHTI,
                                SQLSERVER_LOCAL_HELSINKI,SQLSERVER_LOCAL_HELSINKI_OMNIVIEW,NOT_DEFINED}
    public enum TRPXTaskTypes {MEASUREMENT_DATA_INSERT,CONTROLLER_INTERSECTION_DATA_CHANGE,DETECTOR_DATA_CHANGE,LOAD_CURRENT_CONTROLLER_INTERSECTION_DATA,LOAD_CURRENT_DETECTORS_DATA,NOT_DEFINED}
   // MEASUREMENT_DATA_INSERT,    // measuremets insert
   //  CONTROLLER_INTERSECTION_DATA_CHANGE, //single line of controller or intersection data is changed
   // DETECTOR_DATA_CHANGE,                // single line in detector data is changed
   // LOAD_CURRENT_CONTROLLER_INTERSECTION_DATA, // load new set of current controller or intersection data
   // LOAD_CURRENT_DETECTORS_DATA
   //         NOT_DEFINED
}
