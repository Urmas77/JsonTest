package fi.swarco;
public class CONSTANT {
private CONSTANT(){}
public static final String NO_VALUE ="novalue";
public static final String ERROR_VALUE ="errorvalue";

public static final int NO_IDENTITY =-1;
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
public static final int    TOO_MANY_SIMILAR_PARAMETERS=-3333;
public static final int NO_TASK_LIST =1234567;
public static final int UNSUCCESSFUL_FILE_OPERATION =-9998;
public static final int UNSUCCESSFUL_DATABASE_OPERATION =-9997;
public static final int UNSUCCESSFUL_DATABASE_INSERT_OPERATION=-9996;
public static final int UNSUCCESSFUL_DATABASE_FETCH_OPERATION=-9994;
public static final int UNSUCCESSFUL_DATABASE_CONNECTION_OPERATION=-9995;
public static final int UNSUCCESSFUL_DATABASE_DELETE_OPERATION=-9993;
public static final int UNSUCCESSFUL_DATABASE_UPDATE_OPERATION=-9992;
public static final int UNSUCCESSFUL_JSON_OPERATION = -8999;
public static final int EMPTY_ELEMENT =-99999;
public static final int LINE_NOT_OK = -2222;
public static final int  LINE_OK = 2222;
public static final int INT_RET_FOUND =777;
public static final int INT_RET_NOT_FOUND=-777;
public static final int NOT_CHANGED =888;
public static final int CHANGED=-888;

public static final byte ASCII_CONTROL_DC2	= 0x12;
public static final String ASCII_CTRL_STRING_DC2		= "DC2".intern();

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
public static final int  OMNIA_EMPTY_WORK_LIST = -4999;
public static final String QUERY_CURRENTSTATE="CURRENTSTATE";
public static final String QUERY_HISTORYSTATE="HISTORYSTATE";
// Omnia inteface  publication statuses Jis 10.6 2019
//     OFF :   No data send
//     SWARCO  : Data is sent  Swarco cloud  for swarco and "test use"
//     ON      : Data can be send to Swarco cloud  for Public use
public static final int PUBLICATION_STATUS_OFF =-1;
public static final int PUBLICATION_STATUS_SWARCO =1;
public static final int PUBLICATION_STATUS_ON =2;
//
public static final int NEW_DATATRANSFER_TASK =0;
public static final int READY_DATATRANSFER_TASK =-999;
public static final int DATABASE_CONNECTION_OK =111;
//
public static final int  FILE_NOT_EXIST = -9998;
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
public static final String  TT_NOT_DEFINED="NOTDEFINED";
public static final String  TT_NO_WORK="NO_WORK";
public static final long    NO_WORK_INDEX=-5678;
//
public static final double  DOUBLE_LONG_MULTIPLIER = 10000000.0;
}
