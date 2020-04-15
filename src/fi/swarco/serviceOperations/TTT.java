package fi.swarco.serviceOperations;
import fi.swarco.omniaDataTransferServices.InfluxUtilities;
import org.apache.log4j.Logger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.influxdb.impl.TimeUtil;
//import java.util.Calendar;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
//1.1.1970 ---> 1.1.2000
//30 vuotta = 10957 p�iv�� = 10957*86400*1000 millisekuntia =  946 684 800 000 millisekuntia  
//halutaan 1.1.2000 to now millisekunteja
//now -
//ei voi testata t��ll� mainissa jos ei ole static metodi ? JIs 25.5 2016
public class TTT {
	static Logger logger = Logger.getLogger(TTT.class.getName());
	 public static String jjjGeStringFromUnixTimeString(String finTimeStr) {
	        long lngHelp1 =	jjjGetFromTimeStampUnixTime(finTimeStr);
	        String strHelp1 = Long.toString(lngHelp1); 
	         return strHelp1; 
	    }
	    public static Integer jjjGetFromTimeStampUnixTime(String finTimeStr) {
	        String strHelp1 ="";
	        if(finTimeStr == null) return null;
	        logger.info("******finTimeStr = " + finTimeStr);
	        strHelp1= finTimeStr.replace("Z",".000-0000");
	        logger.info("*******strHelp1 = " + strHelp1);
		 	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZ");
		 	Date dt;
			long epoch=0L;
			try {
				dt = sdf.parse(finTimeStr);
				//dt = sdf.parse(strHelp1);
				epoch = dt.getTime();
			} catch (ParseException e) {
			   e.printStackTrace();
			}
			return (int) epoch/1000;
	    }	 	    
	public static Integer aatsToSec8601(String timestamp){
		 if(timestamp == null) return null;
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		    Date dt;
		    long epoch=0L;
			try {
				dt = sdf.parse(timestamp);
				epoch = dt.getTime();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return (int)(epoch/1000);
	}
	public static long aajjjtsToNanoSec8601(String timestamp){
		  if(timestamp == null) return 0L; 
	    //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS-0000");
		  Date dt;
		    long epoch=0L;
			try {
				dt = sdf.parse(timestamp);
				epoch = dt.getTime();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return epoch;
	}
	public static void main(String[] args) throws InterruptedException {
    /*    String strHelpLongDate20="1481371037000000000"; 
        String strHelpTextDate20="2016-12-10T11:57:17Z"; 
        String strHelpLongDate21="1481371097000000000"; 
        String strHelpTextDate21="2016-12-10T11:58:17Z";
        String strHelpLongDate22="1481371157000000000"; 
        String strHelpTextDate22="2016-12-10T11:59:17Z";
//****************************************
        String strHelpLongDate23="1481371157000000000"; 
        String strHelpTextDate23="2016-12-10T11:59:17Z";
        String strHelpLongDate24="1481371217000000000"; 
        String strHelpTextDate24="";
        String strHelpLongDate25="1481371277000000000"; 
        String strHelpTextDate25="";
        String strHelpLongDate26="1481371337000000000"; 
        String strHelpTextDate26="";
        String strHelpLongDate27="1481371397000000000"; 
        String strHelpTextDate27="";
        String strHelpLongDate28="1481371457000000000"; 
        String strHelpTextDate28="";
        String strHelpLongDate29="1481371517000000000"; 
        String strHelpTextDate29="";
        String strHelpLongDate30="1481371577000000000"; 
        String strHelpTextDate30=""; */		
		// String curTime="";  
	//	GetFromTimeStampUnixTime()
	 /*   curTime=GetViimaTimeStamp.GeStringFromUnixTimeString(strHelpTextDate20);
	    logger.info(" **********strHelpTextDate20  " + strHelpTextDate20 + " CurTime  " + curTime );
		curTime=GetViimaTimeStamp.GeStringFromUnixTimeString(strHelpTextDate21);
		logger.info(" **********strHelpTextDate21  " + strHelpTextDate21 + " CurTime  " + curTime );
		curTime=GetViimaTimeStamp.GeStringFromUnixTimeString(strHelpTextDate22);
		logger.info(" **********strHelpTextDate22  " + strHelpTextDate22 + " CurTime  " + curTime );
		curTime=GetViimaTimeStamp.GeStringFromUnixTimeString(strHelpTextDate23);
		logger.info(" **********strHelpTextDate23  " + strHelpTextDate23 + " CurTime  " + curTime ); */
//		
	  /*  logger.info("strHelpTextDate20 = "+strHelpTextDate20 + 
	    		    " GetFromTimeStampUnixTime(strHelpTextDate20)= " + GetViimaTimeStamp.GetFromTimeStampUnixTime(strHelpTextDate20) +
	                " strHelpLongDate20 = " +strHelpLongDate20);                              	
	    logger.info("strHelpTextDate21 = "+strHelpTextDate21 + 
    		    " GetFromTimeStampUnixTime(strHelpTextDate21)= " + GetViimaTimeStamp.GetFromTimeStampUnixTime(strHelpTextDate21) +
                " strHelpLongDate21 = " +strHelpLongDate21);                              	
	    logger.info("strHelpTextDate22 = "+strHelpTextDate22 + 
    		    " GetFromTimeStampUnixTime(strHelpTextDate22)= " + GetViimaTimeStamp.GetFromTimeStampUnixTime(strHelpTextDate22) +
                " strHelpLongDate22 = " +strHelpLongDate22);                              	
	    logger.info("strHelpTextDate23 = "+strHelpTextDate23 + 
    		    " GetFromTimeStampUnixTime(strHelpTextDate23)= " + GetViimaTimeStamp.GetFromTimeStampUnixTime(strHelpTextDate23) +
                " strHelpLongDate23 = " +strHelpLongDate23);                              	
		//curTime=GetViimaTimeStamp.GeStringFromUnixTimeString(strHelpTextDate24);
		/* logger.info(" **********strHelpTextDate24  " + strHelpTextDate24 + " CurTime  " + curTime );
		curTime=GetViimaTimeStamp.GeStringFromUnixTimeString(strHelpTextDate25);
		logger.info(" **********strHelpTextDate25  " + strHelpTextDate25 + " CurTime  " + curTime );
		curTime=GetViimaTimeStamp.GeStringFromUnixTimeString(strHelpTextDate26);
	    logger.info(" **********strHelpTextDate26  " + strHelpTextDate26 + " CurTime  " + curTime );
		curTime=GetViimaTimeStamp.GeStringFromUnixTimeString(strHelpTextDate27);
	    logger.info(" **********strHelpTextDate27  " + strHelpTextDate27 + " CurTime  " + curTime );
		curTime=GetViimaTimeStamp.GeStringFromUnixTimeString(strHelpTextDate28);
		logger.info(" **********strHelpTextDate28  " + strHelpTextDate28 + " CurTime  " + curTime );
		curTime=GetViimaTimeStamp.GeStringFromUnixTimeString(strHelpTextDate29);
	    logger.info(" **********strHelpTextDate29  " + strHelpTextDate29 + " CurTime  " + curTime );
		curTime=GetViimaTimeStamp.GeStringFromUnixTimeString(strHelpTextDate30);
	    logger.info(" **********strHelpTextDate30  " + strHelpTextDate30 + " CurTime  " + curTime ); */
	/*	String strHelpTextDate31="2016-01-01T00:00:00.000-0000";
		logger.info(" strHelpTextDate31= "+strHelpTextDate31 + " lngdate= " +tsToSec8601(strHelpTextDate31));
		String strHelpTextDate32="2016-01-01T11:58:59.000-0000";
		logger.info(" strHelpTextDate32= "+strHelpTextDate32 + " lngdate= " +tsToSec8601(strHelpTextDate32));
		String strHelpTextDate33="2016-01-01T11:59:59.000-0000";
		logger.info(" strHelpTextDate33= "+strHelpTextDate33 + " lngdate= " +tsToSec8601(strHelpTextDate33));
		String strHelpTextDate34="2016-01-01T12:00:00.000-0000";
		logger.info(" strHelpTextDate34= "+strHelpTextDate34 + " lngdate= " +tsToSec8601(strHelpTextDate34));
		String strHelpTextDate35="2016-01-01T12:01:00.000-0000";
		logger.info(" strHelpTextDate35= "+strHelpTextDate35 + " lngdate= " +tsToSec8601(strHelpTextDate35));
		String strHelpTextDate36="2016-12-10T11:59:17.000-0000";
		logger.info(" strHelpTextDate36= "+strHelpTextDate36 + " lngdate= " +tsToSec8601(strHelpTextDate36));
		String strHelpTextDate37="2016-12-10T12:00:17.000-0000";
		logger.info(" strHelpTextDate37= "+strHelpTextDate37 + " lngdate= " +tsToSec8601(strHelpTextDate37));
		String strHelpTextDate38="2016-12-10T11:59:59.000-0000";
		logger.info(" strHelpTextDate38= "+strHelpTextDate38 + " lngdate= " +tsToSec8601(strHelpTextDate38));
		String strHelpTextDate39="2016-12-10T12:00:00.000-0000";
		logger.info(" strHelpTextDate39= "+strHelpTextDate39 + " lngdate= " +tsToSec8601(strHelpTextDate39)); */
	//	String strHelpTextDate40="2016-12-10T12:00:01.000-0000";
		//logger.info(" strHelpTextDate40= "+strHelpTextDate40 + " lngdate= " +tsToSec8601(strHelpTextDate40)); 
//*************************************************************************************************************
/*		String strHelpTextDate53="2016-12-10T23:59:59Z";
		strHelpTextDate40="2016-12-10T23:59:59.000-0000";
		logger.info("GetViimaTimeStamp.GeStringFromUnixTimeString(" + strHelpTextDate53 +")= " + GetViimaTimeStamp.GeStringFromUnixTimeString(strHelpTextDate53));
		logger.info("tsToSec8601("+  strHelpTextDate40 + ")= " + tsToSec8601(strHelpTextDate40));
		String strHelpTextDate54="2016-12-11T00:00:00Z";
		strHelpTextDate40="2016-12-11T00:00:00.000-0000";
		logger.info("GetViimaTimeStamp.GeStringFromUnixTimeString(" + strHelpTextDate54 +")= " + GetViimaTimeStamp.GeStringFromUnixTimeString(strHelpTextDate54));
		logger.info("tsToSec8601("+  strHelpTextDate40 + ")= " + tsToSec8601(strHelpTextDate40));
		String strHelpTextDate55="2016-12-11T00:00:01Z";
		strHelpTextDate40="2016-12-11T00:00:01.000-0000";
		logger.info("GetViimaTimeStamp.GeStringFromUnixTimeString(" + strHelpTextDate55 +")= " + GetViimaTimeStamp.GeStringFromUnixTimeString(strHelpTextDate55));
		logger.info("tsToSec8601("+  strHelpTextDate40 + ")= " + tsToSec8601(strHelpTextDate40));
		String strHelpTextDate56="2016-12-11T12:00:02Z";
		strHelpTextDate40="2016-12-11T12:00:02.000-0000";
		logger.info("GetViimaTimeStamp.GeStringFromUnixTimeString(" + strHelpTextDate56 +")= " + GetViimaTimeStamp.GeStringFromUnixTimeString(strHelpTextDate56));
		logger.info("tsToSec8601("+  strHelpTextDate40 + ")= " + tsToSec8601(strHelpTextDate40)); */
//  **********************************************************************************************************************************
/*		String strHelpTextDate56="2016-12-11T11:59:59Z";
		strHelpTextDate40="2016-12-11T11:59:59.000-0000";
		logger.info("GetViimaTimeStamp.GeStringFromUnixTimeString(" + strHelpTextDate56 +")= " + GetViimaTimeStamp.GeStringFromUnixTimeString(strHelpTextDate56));
		logger.info("tsToSec8601("+  strHelpTextDate40 + ")= " + tsToSec8601(strHelpTextDate40));
		strHelpTextDate56="2016-12-11T12:00:00Z";
		strHelpTextDate40="2016-12-11T12:00:00.000-0000";
		logger.info("GetViimaTimeStamp.GeStringFromUnixTimeString(" + strHelpTextDate56 +")= " + GetViimaTimeStamp.GeStringFromUnixTimeString(strHelpTextDate56));
		logger.info("tsToSec8601("+  strHelpTextDate40 + ")= " + tsToSec8601(strHelpTextDate40));
		strHelpTextDate56="2016-12-11T12:00:01Z";
		strHelpTextDate40="2016-12-11T12:00:01.000-0000";
		logger.info("GetViimaTimeStamp.GeStringFromUnixTimeString(" + strHelpTextDate56 +")= " + GetViimaTimeStamp.GeStringFromUnixTimeString(strHelpTextDate56));
		logger.info("tsToSec8601("+  strHelpTextDate40 + ")= " + tsToSec8601(strHelpTextDate40)); */
 //************************************************************************************************************************************************************************
	/*	strHelpTextDate56="2016-12-11T11:59:59Z";
		strHelpTextDate40="2016-12-11T11:59:59.000-0000";
		logger.info("jjjGeStringFromUnixTimeString(" + strHelpTextDate56 +")= " + jjjGeStringFromUnixTimeString(strHelpTextDate56));
		logger.info("tsToSec8601("+  strHelpTextDate40 + ")= " + tsToSec8601(strHelpTextDate40));
		strHelpTextDate56="2016-12-11T12:00:00Z";
		strHelpTextDate40="2016-12-11T12:00:00.000-0000";
		logger.info("jjjGeStringFromUnixTimeString(" + strHelpTextDate56 +")= " + jjjGeStringFromUnixTimeString(strHelpTextDate56));
		logger.info("tsToSec8601("+  strHelpTextDate40 + ")= " + tsToSec8601(strHelpTextDate40));
		strHelpTextDate56="2016-12-11T12:00:01Z";
		strHelpTextDate40="2016-12-11T12:00:01.000-0000";
		logger.info("jjjGeStringFromUnixTimeString(" + strHelpTextDate56 +")= " + jjjGeStringFromUnixTimeString(strHelpTextDate56));
		logger.info("tsToSec8601("+  strHelpTextDate40 + ")= " + tsToSec8601(strHelpTextDate40)); */
 //************************************************************************************************************************************************************************
/*		strHelpTextDate56="2016-12-11T11:59:59Z";
		strHelpTextDate40="2016-12-11T11:59:59.000-0000";
		logger.info("jjjGetFromTimeStampUnixTime(" + strHelpTextDate56 +")= " + jjjGetFromTimeStampUnixTime(strHelpTextDate56));
		logger.info("tsToSec8601("+  strHelpTextDate40 + ")= " + tsToSec8601(strHelpTextDate40));
		strHelpTextDate56="2016-12-11T12:00:00Z";
		strHelpTextDate40="2016-12-11T12:00:00.000-0000";
		logger.info("jjjGetFromTimeStampUnixTime(" + strHelpTextDate56 +")= " + jjjGetFromTimeStampUnixTime(strHelpTextDate56));
		logger.info("tsToSec8601("+  strHelpTextDate40 + ")= " + tsToSec8601(strHelpTextDate40));
		strHelpTextDate56="2016-12-11T12:00:01Z";
		strHelpTextDate40="2016-12-11T12:00:01.000-0000";
		logger.info("jjjGetFromTimeStampUnixTime(" + strHelpTextDate56 +")= " + jjjGetFromTimeStampUnixTime(strHelpTextDate56));
		logger.info("tsToSec8601("+  strHelpTextDate40 + ")= " + tsToSec8601(strHelpTextDate40)); */
//******************************************************
	//	2016-04-21 11:30:00
	//	2016-04-21 13:15:00
		SwarcoTimeUtilities tu = new SwarcoTimeUtilities();
		String strHelp1="2016-04-21 11:30:00";
		String strHelp2="2016-04-21 13:15:00";
		logger.info("1. strHelp1 = " + strHelp1);
	//	strHelp1 =tu.FromSwarcoTimeToInfluxNanos(strHelp1);
	//	logger.info("tu.ToSec8601("+  strHelp1 + ")= " + tu.ToSec8601(strHelp1));
	//	logger.info("tu.ToNanoSec8601("+  strHelp1 + ")= " + tu.ToNanoSec8601(strHelp1));
		logger.info("***************tu.ToNanoSec8601Str("+  strHelp1 + ")= " + tu.ToNanoSec8601Str(strHelp1));
		String strHelp3=tu.ToNanoSec8601Str(strHelp1);
		logger.info("******strHelp3 ="+ strHelp3);
		logger.info("*************************************************");
	//	String strHelp56=org.influxdb.impl.TimeUtil.toInfluxDBTimeFormat(Long.valueOf(strHelp3));
	//	logger.info("strHelp56 = "+ strHelp56);
// this is right class
		ZoneOffset zoneOffSet= ZoneOffset.of("+02:00");
		OffsetDateTime date = OffsetDateTime.now(zoneOffSet);
        logger.info ("date.toString() = " +date.toString());
		logger.info ("date.toEpochSecond() = " + date.toEpochSecond());
		logger.info ("date.getNano() = " + date.getNano());
		Thread.sleep(3000);
		date = OffsetDateTime.now(zoneOffSet);
		logger.info ("date.toEpochSecond() = " + date.toEpochSecond());
		logger.info ("3 date.getNano() = " + date.getNano());
		Thread.sleep(5000);
		date = OffsetDateTime.now(zoneOffSet);
		logger.info ("date.toEpochSecond() = " + date.toEpochSecond());
		logger.info ("5 date.getNano() = " + date.getNano());
         strHelp1 = String.valueOf(date.toEpochSecond()) + String.valueOf(date.getNano());
		logger.info("strhelp1 = " + strHelp1);
		InfluxUtilities aaa = new InfluxUtilities();
        logger.info("aaa.FilterInfluxFields(uuu,kkkk,cccc) = " + aaa.FilterInfluxFields("uuu,kkkk,cccc"));
		//Instant ins = Instant.now();
		//ins.atZone(ZoneId.of("Europe/Helsinki"));
//		logger.info("ins.getNano()= " +ins.getNano());
		//ins.atZone(ZoneId.of("Europe/Helsinki"));
		//String strHelp4 =ins.toString();
/*		logger.info("strHelp4=" + strHelp4);
        ins.plusNanos(60*60*1000*1000);
        strHelp4 =ins.toString();
         logger.info("strHelp4=" + strHelp4); */
		//
		//		logger.info("2. strHelp1 = " + strHelp1);
//		jjjtsToNanoSec8601(strHelp1);
//        logger.info(1. strHelp1 = " + strHelp1);
//		strHelp1=strHelp1+".000-0000";
//		logger.info(1. strHelp1 = " + strHelp1);
	/*	String strHelpTextDate40="2016-12-10T12:00:01.000-0000";
		String strHelpTextDate56="2016-12-11T11:59:59Z";
		strHelpTextDate56=strHelpTextDate56.replace("Z",".000-0000");
		strHelpTextDate40="2016-12-11T11:59:59.000-0000";
		logger.info("jjjGetFromTimeStampUnixTime(" + strHelpTextDate56 +")= " + jjjGetFromTimeStampUnixTime(strHelpTextDate56));
		logger.info("tsToSec8601("+  strHelpTextDate40 + ")= " + tsToSec8601(strHelpTextDate40));
		strHelpTextDate56="2016-12-11T12:00:00Z";
		strHelpTextDate56=strHelpTextDate56.replace("Z",".000-0000");
		strHelpTextDate40="2016-12-11T12:00:00.000-0000";
		logger.info("jjjGetFromTimeStampUnixTime(" + strHelpTextDate56 +")= " + jjjGetFromTimeStampUnixTime(strHelpTextDate56));
		logger.info("tsToSec8601("+  strHelpTextDate40 + ")= " + tsToSec8601(strHelpTextDate40));
		strHelpTextDate56="2016-12-11T12:00:01Z";
		strHelpTextDate56=strHelpTextDate56.replace("Z",".000-0000");
		strHelpTextDate40="2016-12-11T12:00:01.000-0000";                       
		logger.info("jjjGetFromTimeStampUnixTime(" + strHelpTextDate56 +")= " + jjjGetFromTimeStampUnixTime(strHelpTextDate56));
		logger.info("tsToSec8601("+  strHelpTextDate40 + ")= " + tsToSec8601(strHelpTextDate40));
*/
	}
}
