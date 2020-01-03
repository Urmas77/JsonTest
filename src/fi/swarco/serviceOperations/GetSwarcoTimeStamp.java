package fi.swarco.serviceOperations;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
    public class GetSwarcoTimeStamp {
        static Logger logger = Logger.getLogger(GetSwarcoTimeStamp.class.getName());
	  //01.09.2016 14:15:49
        
	    public static long GetFromTimeStampDoublePoint(String finTimeStr) {
	        long curTimestamp=0;
	        try {
		 	    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
		 	    String dateInString = finTimeStr;
		  	    Date date = sdf.parse(dateInString);
		    //    long unixTime = (long) date.getTime();
		 //       logger.info(" date.toString() = " + date.toString());  
		 //       logger.info(" unixTime = " + unixTime);    
		 // 	    logger.info("dateInString=" +dateInString);
		 // 	    logger.info("Date - Time in milliseconds : " + date.getTime());
		  	    Calendar calendar = Calendar.getInstance();
		  	    calendar.setTime(date);
		 // 	    System.out.println("*****Calender - Time in milliseconds : " + calendar.getTimeInMillis());
		  	    curTimestamp=  calendar.getTimeInMillis();
		  //	    logger.info("calendar.getTime() = " +calendar.getTime());  
		  	    curTimestamp = curTimestamp*1000000l;
  		  //	    logger.info("curTimestamp = curTimestamp*100000000l : curTimestamp =" +curTimestamp);		
	            
		  	    //String w1970To2000Milliseconds="946684800000"; 
	            //long year2000ZeroTime = Long.parseLong(w1970To2000Milliseconds)*1000;
	            //logger.info(" year2000ZeroTime = " + year2000ZeroTime); 
	            //curTimestamp = curTimestamp + year2000ZeroTime; 
	            //logger.info("year 2000 fix curTimestamp = " + curTimestamp ); 
	          //  long year2000curTimestamp = 946684800000000000l; 
	          //  curTimestamp = year2000curTimestamp    +curTimestamp;
	            // + 2 HOUR IN NANOS 
	            long twoHourNanoSeconds = 7200l*1000000000l; 
	        //    logger.info("twoHourNanoSeconds = " +twoHourNanoSeconds);
	           
	            //              1488323398000
	           // curTimestamp =1488322858000000000l; 
	          //  logger.info(" curTimestamp = " + curTimestamp);
	          //  curTimestamp =1488322858000000000l + twoHourNanoSeconds ;
	            curTimestamp = curTimestamp+ twoHourNanoSeconds ;
	          //  logger.info(" curTimestamp + twohoursnanoes = " +curTimestamp);               
	            return curTimestamp;
		    } catch (Exception e) {
	            e.printStackTrace();
	        }
		    return curTimestamp ;
        }
	    public static String GeStringFromUnixTimeString(String finTimeStr) {
	        long lngHelp1 =	GetFromTimeStampUnixTime(finTimeStr);
	        String strHelp1 = Long.toString(lngHelp1); 
	         return strHelp1; 
	    }
	    
	    public static long GetFromTimeStampUnixTime(String finTimeStr) {
	        String strHelp1 ="";
	        //logger.info("******finTimeStr = " + finTimeStr);
	        strHelp1= finTimeStr.replace("Z",".000-0000");  
	        logger.info("*******strHelp1=" + strHelp1);
		 	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZ");
		 	Date dt;
			long epoch=0L;
			try {
				dt = sdf.parse(strHelp1);
				epoch = dt.getTime();
			//	logger.info("*******epoch = " + epoch);
			//	logger.info("*******(int)(epoch/1000) = " +(int)(epoch/1000)); 
      
			} catch (ParseException e) {
			   e.printStackTrace();
			}
			return epoch;
	    }	 	    
		public static long jjjtsToSec8601(String timestamp){
			  if(timestamp == null) return 0L; 
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		  Date dt;
		    long epoch=0L;
			try {
				dt = sdf.parse(timestamp);
				epoch = dt.getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		    return epoch;
	}
	    
	    
	    
	    public static long GetFromTimeStampPoint(String finTimeStr) {
	        long curTimestamp=0;
	        try {
		 	   SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.yyyy hh.mm.ss");
		 	    String dateInString = finTimeStr;
		 	    Date date = sdf1.parse(dateInString);
		  	    System.out.println(dateInString);
		  	    System.out.println("Date - Time in milliseconds : " + date.getTime());
		  	    Calendar calendar = Calendar.getInstance();
		  	    calendar.setTime(date);
		  	    curTimestamp=  calendar.getTimeInMillis();
		  	    System.out.println("Calender - Time in milliseconds : " + calendar.getTimeInMillis());
	            //curTimestamp=  calendar.getTimeInMillis()-  year2000ZeroTime;
	            //curTimestamp = curTimestamp/1000;
	            return curTimestamp;
		    } catch (Exception e) {
	            e.printStackTrace();
	        }
		    return curTimestamp ;
        }  
	    public static String GetPointTimeStringFromTimestamp(Long pLongTime) {
	        String retString="";
	        try {
		 	   SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.yyyy hh.mm.ss");
		  	    Calendar calendar = Calendar.getInstance();
		  	    calendar.setTimeInMillis(pLongTime);
		  	    Date date=calendar.getTime();
		  	   // curTimestamp=  calendar.getTimeInMillis();
		  	    retString=sdf1.format(date);
		  	    
		  	    //retString =date.toString();
		  	    logger.info("retString= " + retString);
	            return retString;
		    } catch (Exception e) {
	            e.printStackTrace();
	        }
		    return retString ;
        }  
    }
