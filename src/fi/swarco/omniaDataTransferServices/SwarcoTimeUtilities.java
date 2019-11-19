package fi.swarco.omniaDataTransferServices;
import org.apache.log4j.Logger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
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


}
