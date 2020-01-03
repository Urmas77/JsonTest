package fi.swarco.serviceOperations;
import org.apache.log4j.Logger;
public class SegFileSetter {
	static Logger logger = Logger.getLogger(SegFileSetter.class.getName());	
    public long convertToTimestampPoint(String timeLine)	{
        logger.info("timeLine ="+ timeLine);
	    String strHelp1= getTimeStampFromLine(timeLine);
	    logger.info("strHelp1 ="+ strHelp1);
	    long lresult =  GetSwarcoTimeStamp.GetFromTimeStampPoint(strHelp1);
	    logger.info("lresult ="+ lresult);    
	    return lresult;
    }
    public long convertToTimestampDoublePoint(String timeLine)	{
   //     logger.info("timeLine ="+ timeLine);
	    String strHelp1= getTimeStampFromLine(timeLine);
	//    logger.info("strHelp1 ="+ strHelp1);
	    long lresult =  GetSwarcoTimeStamp.GetFromTimeStampDoublePoint(strHelp1);
	  //  logger.info("lresult ="+ lresult);    
	    return lresult;
    }
    public long convertToTimestampInfluxUnixFromTimeString(String timeLine)	{
       // logger.info("timeLine ="+ timeLine);
	    String strHelp1= timeLine;
	   // logger.info("strHelp1 ="+ strHelp1);
	    long lresult =  GetSwarcoTimeStamp.GetFromTimeStampUnixTime(strHelp1);
	   // logger.info("lresult ="+ lresult);    
	    return lresult;
    }
    public long convertToTimestampFromTimeString(String timeLine)	{
       logger.info("timeLine ="+ timeLine);
	    String strHelp1= timeLine;
	  //  logger.info("strHelp1 ="+ strHelp1);
	    long lresult =  GetSwarcoTimeStamp.GetFromTimeStampDoublePoint(strHelp1);
	    logger.info("lresult ="+ lresult);    
	    return lresult;
    }
    public String getTimeStampFromLine(String line){
//		01.09.2016 14:15:49,A1:3.97,A2:4.00,P1:0
  //     logger.info("line ="+ line);
	   int pointPlace =line.indexOf(",");
       String Timestr = line.substring(0,pointPlace);
 //      logger.info("Timestr ="+ Timestr);
	   return Timestr; 	    
    }
    
    public String GeStringFromUnixTimeString(String pStringTimestamp ){
    	String strHelp1 = GetSwarcoTimeStamp.GeStringFromUnixTimeString(pStringTimestamp);
        return strHelp1; 
    }
    
    public String getPointTimestampToString(Long pLongTimestamp ){
    	String strRes = GetSwarcoTimeStamp.GetPointTimeStringFromTimestamp(pLongTimestamp);
        logger.info("pLongTimestamp="+ pLongTimestamp);
        logger.info("strRes="+strRes);
    	return strRes; 
    }    
    public String getPointStringTimestampToString(String pStringTimestamp ){
    	long lngValue=Long.parseLong(pStringTimestamp);
    	String strRes = GetSwarcoTimeStamp.GetPointTimeStringFromTimestamp(lngValue);
        //logger.info("lngValue="+ lngValue);
        //logger.info("strRes="+strRes);
    	return strRes; 
    }    
    public String getMeasurementsFromLine(String line) {
//		01.09.2016 14:15:49,A1:3.97,A2:4.00,P1:0
//	    logger.info("line ="+ line);
	    int pointPlace =line.indexOf(",");
	    String Measurements = line.substring(pointPlace+1,line.length());    
//	    logger.info("line ="+ line + " pointPlace =" + pointPlace + " Measurements =" +Measurements);
	    return Measurements;
	}
    public String FillTimestampString(String oStamp) {
//1.482303260E12
//13 : 1.48229912E12
//12 : 1.4823026E12
//14 : 1.482302600E12 Fixed
//12 : 1.4823044E12
//14 : 1.482304400E12 Fixed
//11 : 1.482302E12
//14 : 1.482302000E12 Fixed
//and so on
    	String strHelp55="";
	    strHelp55= oStamp;
	    //strHelp55= strHelp55.replace(".","");
	    if (strHelp55.length()!=14) {
    	   // logger.info (strHelp55.length()+ " : " + strHelp55); 	
   	        if (strHelp55.length()==13) {
    	        strHelp55=strHelp55.substring(0,10) +"0" + strHelp55.substring(10,13);
              // logger.info (strHelp55.length()+ " : " + strHelp55 + " Fixed"); 	
    	    } else if (strHelp55.length()==12) {
     		    strHelp55=strHelp55.substring(0,9) +"00" + strHelp55.substring(9,12);
        	  //  logger.info (strHelp55.length()+ " : " + strHelp55 + " Fixed"); 	
            } else if (strHelp55.length()==11) {
     	        strHelp55=strHelp55.substring(0,8) +"000" + strHelp55.substring(8,11);
     	      //  logger.info (strHelp55.length()+ " : " + strHelp55 + " Fixed"); 
            } else if (strHelp55.length()==10) {
     	        strHelp55=strHelp55.substring(0,7) +"0000" + strHelp55.substring(7,10);
               // logger.info (strHelp55.length()+ " : " + strHelp55 + " Fixed"); 
            } else if (strHelp55.length()==9) {
     	        strHelp55=strHelp55.substring(0,6) +"00000" + strHelp55.substring(6,9);
               // logger.info (strHelp55.length()+ " : " + strHelp55 + " Fixed"); 
            } else if (strHelp55.length()==8) {
     	        strHelp55=strHelp55.substring(0,5) +"000000" + strHelp55.substring(5,8);
              //  logger.info (strHelp55.length()+ " : " + strHelp55 + " Fixed"); 
            } else if (strHelp55.length()==7) {
     	        strHelp55=strHelp55.substring(0,4) +"0000000" + strHelp55.substring(4,7);
              //  logger.info (strHelp55.length()+ " : " + strHelp55 + " Fixed"); 
            } else if (strHelp55.length()==6) {
     	        strHelp55=strHelp55.substring(0,3) +"00000000" + strHelp55.substring(3,6);
            //    logger.info (strHelp55.length()+ " : " + strHelp55 + " Fixed"); 
            } else {
               // logger.info (strHelp55.length()+ " : " + strHelp55 + " not Fixed ***************************************************");
    	        return "*****"; 
            }
   	    } else {
   	      // logger.info (strHelp55.length()+ " : " + strHelp55 + " Orig OK ***************************************************");
   	    }
        return strHelp55;
	}	
}
