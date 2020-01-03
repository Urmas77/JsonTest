package fi.swarco.serviceOperations;
import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.time.*;

import static fi.swarco.CONSTANT.INT_RET_NOT_OK;
import static fi.swarco.CONSTANT.NO_VALUE;
public class SwarcoTimeUtilities {
    static Logger logger = Logger.getLogger(SwarcoTimeUtilities.class.getName());
    public String GetNow() {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(pattern);
        simpleDateFormat1.setTimeZone(TimeZone.getTimeZone("Europe/Helsinki"));
        Calendar calendar = new GregorianCalendar();
   //     logger.info("calendar.getTime() = " + simpleDateFormat1.format(calendar.getTime()));
        // logger.info("strTime=" + strTime);
        return simpleDateFormat1.format(calendar.getTime());
    }
    public String ToSwarcoTime (String pTime) {
       // String pattern = "yyyy-MM-dd HH:mm:ss";
        //yyyy-MM-ddTHH:mm:ss.mmm -->  "yyyy-MM-dd HH:mm:ss"
        //2016-12-21T12:10:42.610 ---> 2016-12-21 12:10:42.610
        String strHelp1=pTime.replace("T"," ");
//        logger.info("strHelp1=" + strHelp1);
        int index1= strHelp1.indexOf(".");
     //   logger.info("index1=" + index1);
     //   logger.info("strHelp1=" + strHelp1 + " strHelp1.length() = " + strHelp1.length() +"index1=" + index1 );
       if ((index1==-1) && (strHelp1.length()==19)) {
           return strHelp1;   // RTETHINK lets hope that format is OK
       //    System.exit(1);
       } else if ((index1==-1) && (!(strHelp1.length()==19))) {
           System.exit(1);
       }
        strHelp1 = strHelp1.substring(0,index1);
  //      logger.info("pTime=" + pTime);
  //      logger.info("strHelp1=" + strHelp1   +  " strHelp1.length() = " + strHelp1.length());

        return strHelp1;
    }
    public String FromSwarcoTimeToInfluxMills (String pTime) {
       //"yyyy-MM-dd HH:mm:ss"  -->yyyy-MM-ddTHH:mm:ss.mmm-0000"
        //2016-12-21T12:10:42.610 ---> 2016-12-21 12:10:42.610
        String strHelp1;
        logger.info("pTime = " +pTime);
        strHelp1=pTime.replace(" ","T");
        int index1= strHelp1.indexOf(".");
        if ((index1==-1) && (!(pTime.length()==19))) {
            System.exit(1);
        }
        strHelp1=strHelp1 + ".000-0000";
        logger.info("strHelp1  = " +strHelp1);
        return strHelp1;
    }
    public static int ToSec8601(String timestamp){
        if(timestamp == null) return INT_RET_NOT_OK;
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
    public  BigInteger ToNanoSec8601(String timestamp){
        if(timestamp == null) return BigInteger.ZERO;
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS-0000");
        Date dt;
        long lngEpoch=0L;
        try {
            dt = sdf.parse(timestamp);
            lngEpoch = dt.getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        BigInteger bigEpoch;
        bigEpoch = BigInteger.valueOf(1000*lngEpoch);
        return bigEpoch;
    }
    public String EatPointFromTime(String pTimestamp) {
        int index1;
        String strHelp1=NO_VALUE;
        if(pTimestamp == null) return NO_VALUE;
        index1= pTimestamp.indexOf(".");
        if ((index1==-1) && (pTimestamp.length()==19)) {
            return pTimestamp;   // RTETHINK lets hope that format is OK
            //    System.exit(1);
        } else if ((index1==-1) && (!(pTimestamp.length()==19))) {
            System.exit(1);
        }
        strHelp1 = pTimestamp.substring(0,index1);
        return strHelp1;
    }
    public String ToNanoSec8601Str(String pTimestamp){
        String strHelp1,strHelp2,strHelp3;
        if(pTimestamp == null) return NO_VALUE;
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS-0000");
        Date dt;
        long lngEpoch=0L;
        try {
            logger.info("pTimestamp = "+ pTimestamp);
            strHelp3 =EatPointFromTime(pTimestamp);
            strHelp2 =FromSwarcoTimeToInfluxMills(strHelp3);
            logger.info("strHelp2 = "+ strHelp2);
            dt = sdf.parse(strHelp2);
            lngEpoch = dt.getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        BigInteger biEpoch =BigInteger.valueOf(lngEpoch*1000000);
        strHelp1=biEpoch.toString();
        return strHelp1;
    }




}
