package fi.swarco;
public class CONSTANT {
private CONSTANT(){}
public static final String VALUE_ON ="ON";
public static final String VALUE_OFF ="OFF";
public static final String NO_VALUE ="novalue";
public static final String ERROR_VALUE ="errorvalue";
public static final String JOB_DONE ="JOB_DONE";
public static final String  NO_TIME_SERIE_STRING ="NO_TIME_SERIE_STRING";
   public static final int NO_INT_VALUE =-9999999;
   public static int SERIENAMES_CREATED_OK=45987;
public static int SERIENAMES_CREATED_NOT_OK=-45987;
public static final int NO_IDENTITY =-1;
public static final int INT_FIELD_NOT_IN_USE =-9999999;
public static final double DOUBLE_FIELD_NOT_IN_USE =-9999999.99;
public static final int INT_EMPTY_ELEMENT =-99999;  //EMPTY_ELEMENT
public static final int NO_OMNIA = -444;
public static final int NO_INTERSECTION_ID = -445;
public static final int NO_CONTROLLER_ID = -446;
public static final int NO_DETECTOR_ID = -447;
public static final int VALUE_NOT_FOUND=-551;
public static final String RET_OK ="OK";
public static final String RET_NOT_OK ="Not OK";
public static final int INT_RET_OK = 1;
public static final int INT_RET_NOT_OK = -111;
public static final String UNIX_START_TIME ="1970-01-01 00:00:00";
public static final String SWARCO_END_TIME="9999-01-01 00:00:00";
//2069-04-25T15:51:00.000
public static final String  SQL_SERVER_NULL_TIME ="2069-04-25 15:51:00.0";  // RETHINK
public static final String  SERIENAME_TIMESTAMP ="1576591469228722464";
public static final int    TOO_MANY_SIMILAR_PARAMETERS=-3333;
public static final int NO_TASK_LIST =1234567;
public static final int UNSUCCESSFUL_FILE_OPERATION =-9998;
public static final int UNSUCCESSFUL_DATABASE_OPERATION =-9997;
public static final int UNSUCCESSFUL_DATABASE_INSERT_OPERATION=-9996;
public static final int UNSUCCESSFUL_DATABASE_FETCH_OPERATION=-9994;
public static final int UNSUCCESSFUL_DATABASE_CONNECTION_OPERATION=-9995;
public static final int UNSUCCESSFUL_DATABASE_DELETE_OPERATION=-9993;
public static final int UNSUCCESSFUL_DATABASE_UPDATE_OPERATION=-9992;
public static final int UNSUCCESSFUL_DATABASE_DATA_TRANSFER_OPERATION=-9991;
public static final int UNSUCCESSFUL_JSON_OPERATION = -8999;
public static final int EMPTY_ELEMENT =-99999;
public static final int LINE_NOT_OK = -2222;
public static final int  LINE_OK = 2222;
public static final int INT_RET_FOUND =777;
public static final int INT_RET_NOT_FOUND=-777;
public static final int NOT_CHANGED =888;
public static final int CHANGED=-888;
public static final byte ASCII_CONTROL_DC2	= 0x12;
public static final String ASCII_CTRL_STRING_DC2		= "DC2";
public final static char CR  = (char) 0x0D;
public final static char LF  = (char) 0x0A;
public final static String NEW_LINE_LINUX = "\n";
public static final int MEASUREMENT_TASK_STATUS_CREATED =0;
public static final int MEASUREMENT_TASK_STATUS_OK =1;    // old value was 0
public static final int MEASUREMENT_TASK_STATUS_CLEARING =-9999;
public static final int FILL_UP_TASK_ERROR =-5999;
public static final int FILL_UP_TASK_OK =5999;
public static final int TASK_TRANSFER_ERROR =-5998;
public static final int TASK_TRANSFER_OK =5998;

public static final int THERE_IS_WORK =5997;
public static final int THERE_IS_NO_WORK =-5997;
public static final int  DELETE_UNFILLABLE_TASK_OK=5996;
public static final int  DELETE_UNFILLABLE_TASK_ERROR=-5996;
public static final int  DELETE_TRASH_TASK_OK =5995;
public static final int  DELETE_TRASH_TASK_ERROR =-5995;
public static final int  EXTRACLEANUP_TASK_OK=5994;
public static final int  EXTRACLEANUP_DELETE_TASK_ERROR=-5994;
public static final int DELETE_TRASH_TASKTASK_ERROR=-5992;
public static final int DELETE_TRASH_TASKTASK_OK=5992;
public static final int TASK_TRANSFERSTATE_UPDATE_ERROR =-5991;
public static final int TASK_TRANSFERSTATE_UPDATE_OK =5991;

public static final int  OMNIA_EMPTY_WORK_LIST = -4999;
public static final String QUERY_CURRENTSTATE="CURRENTSTATE";
public static final String QUERY_HISTORYSTATE="HISTORYSTATE";
public static final int PUBLICATION_STATUS_OFF =-1;
public static final int PUBLICATION_STATUS_SWARCO =1;
public static final int PUBLICATION_STATUS_ON =2;
public static final int SERIENAME_ERROR = -5678;
public static final int SERIENAME_IS_ON_USE = 5678;
public static final int SERIENAME_IS_FREE = 5679;
//
public static final int NEW_DATATRANSFER_TASK =0;
public static final int READY_DATATRANSFER_TASK =-999;
public static final int DATABASE_CONNECTION_OK =111;
//
public static final int    OMNIVIEW_FIRST_ROUND_YES =878787;
public static final int    OMNIVIEW_FIRST_ROUND_NO =-878787;
//
public static final int FILE_NOT_EXIST = -9998;
public static final int OMNIA_DATA_PICK_OK =2222;
public static final int OMNIA_DATA_PICK_NOT_OK =-777;
public static final int CLOUD_DATA_PICK_NOT_OK=-778;
public static final int CLOUD_DATA_LIST_IS_EMPTY=-779;
public static final int CLOUD_DATA_LIST_CREATION_ERROR=-780;
public static final int MESSAGE_RECEIVED_OK=1234;
public static final int MESSAGE_NOT_RECEIVED_OK=-4321;
//  transfer task types  JIs 02.10 2019
public static final String   TT_MEASUREMENT_DATA_INSERT="MEASUREMENTDATAINSERT";
public static final String  TT_CONTROLLER_DATA_CHANGE="CONTROLLERDATACHANGE";
public static final String  TT_INTERSECTION_DATA_CHANGE="INTERSECTIONDATACHANGE";
public static final String  TT_DETECTOR_DATA_CHANGE="DETECTORDATACHANGE";                    //  1234567890123456789012345678901234567890
public static final String  TT_LOAD_CURRENT_CONTROLLER_INTERSECTION_DATA="LOADCURRENTCONTROLLERINTERSECTIONDATA";
public static final String  TT_LOAD_CURRENT_DETECTORS_DATA="LOADCURRENTDETECTORSDATA";
public static final String  TT_CONTROLLER_STATUS_DATA_INSERT="CONTROLLERSTATUSDATAINSERT";
public static final String  TT_NOT_DEFINED="NOTDEFINED";
public static final String  TT_NO_WORK="NO_WORK";

public static final long TASK_SELECTION_MODE_YOUNGEST=0;
public static final long TASK_SELECTION_MODE_OLDEST=1;
public static final long TASK_SELECTION_MODE_YOUNGEST_WITH_LINE_LIMIT=2;
public static final long TASK_SELECTION_MODE_OLDEST_WITH_LINE_LIMIT=3;
public static final long TASK_SELECTION_MODE_MIXTURE_01=4;
public static final long TASK_SELECTION_MODE_MIXTURE_WITH_LINE_LIMIT_01=5;
//
public static final long TASK_SELECTION_MODE_NOT_DEFINED=-9999999;
public static final int  TOO_FEW_LINES_TO_TRANSFER=-60001;
public static final int  NO_INT_VALUE_FOR_TO_TRANSFER=-60002;
public static final int  IMPOSSIBLE_SELECTOR_RESULT=-60003;
public static final int NO_SELECTION_DONE=-543;
public static final int SELECTION_DONE=543;
   // youngest,  oldest , youngest with line limit ..... JIs 27.04 2021
public static final String  TT_TOO_MANY_WORKTYPES="TT_TOO_MANY_WORKTYPES";
public static final long    NO_WORK_INDEX=-5678;
public static final long    TWO_HOURS_MS=7200000;
public static final long    THREE_HOURS_MS=10800000;
public static final long    TWENTYSEVEN_HOURS_MS=97200000;
public static final double  DOUBLE_LONG_MULTIPLIER = 10000000.0;
}
