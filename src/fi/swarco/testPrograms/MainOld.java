package fi.swarco.testPrograms;
import fi.swarco.properties.JSwarcoproperties;
import org.apache.log4j.Logger;

//import java.util.Date;
import java.time.*;
import java.time.format.DateTimeFormatter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;


import static fi.swarco.CONSTANT.INT_RET_NOT_OK;
import static fi.swarco.CONSTANT.INT_RET_OK;
//kokeile tätä
// they try
//         LocalDateTime localDateTime = LocalDateTime.from(new Date().toInstant());
//         to resolve the issue, please pass in Zone -
//         LocalDateTime localDateTime = LocalDateTime.from(new Date()
//         .toInstant().atZone(ZoneId.of("UTC")));




public class MainOld {
    static Logger logger = Logger.getLogger(MainOld.class.getName());
    private static String[] tFormats;
    private static int  hH(String strPara) {
        for (int i = 0; i < tFormats.length; i++) {
            logger.info("Huti tFormats[" + i + "]=" + tFormats[i]);
            try {
                final DateTimeFormatter formatter = DateTimeFormatter
                        .ofPattern(tFormats[i]);

                Instant result = Instant.from(formatter.parse(strPara));
                logger.info("**************************************************result=" + result.toString());
                return INT_RET_OK;
            }  catch ( Exception e) {
                logger.info("Huti tFormats[" + i + "]=" + tFormats[i]);
                logger.info("e.getMessage() =" + e.getMessage());
            }
        }
        return INT_RET_NOT_OK;
    }

    private static void jZoneids() {

    Set<String> allZones = ZoneId.getAvailableZoneIds();
    List<String> zoneList = new ArrayList<String>(allZones);
    Collections.sort(zoneList);

    LocalDateTime dt = LocalDateTime.now();
    for (String s : zoneList) {
        ZoneId zone = ZoneId.of(s);
        ZonedDateTime zdt = dt.atZone(zone);
        ZoneOffset offset = zdt.getOffset();
        String out = String.format("%35s %10s%n", zone, offset);
        System.out.println(out);
    }
}





    private static int  hH4(String strPara) {
            logger.info("strPara = " + strPara);
            logger.info("Huti tFormats[" + 4 + "]=" + tFormats[4]);
            try {
                final DateTimeFormatter formatter = DateTimeFormatter
                        .ofPattern(tFormats[4]);

                Instant result = Instant.from(formatter.parse(strPara));
                logger.info("**************************************************result=" + result.toString());
                return INT_RET_OK;
            }  catch ( Exception e) {
                logger.info("Huti tFormats[4]=" + tFormats[4]);
                logger.info("e.getMessage() =" + e.getMessage());
            }
        //}
        return INT_RET_NOT_OK;
    }

    private static void  kkkk(String strPara) {
        logger.info("strPara = " + strPara);
//        LocalDateTime today = LocalDateTime.parse("2019-03-27T10:15:30");


        LocalDateTime today =LocalDateTime.now();
        System.out.println(today);
//Custom pattern
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a");


         LocalDateTime dateTime = LocalDateTime.parse("2019-03-27 10:15:30 AM", formatter);  //toimii
       // System.out.println(" today.toString() = "+ today.toString());
        //LocalDateTime dateTime = LocalDateTime.parse(today.toString(), formatter);

        //"2019-08-05T11:35:17.113"

       // LocalDateTime dateTime = LocalDateTime.parse("2019-08-05 11:35:17", formatter);


        System.out.println("dateTime = " + dateTime) ;
    }


    private static void  cccc(String strPara) {
        strPara ="2019-03-27 10:15:30";
        logger.info("strPara = " + strPara);
        String pPattern = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pPattern);
        LocalDateTime dateTime = LocalDateTime.parse(strPara, formatter);
        System.out.println("dateTime = " + dateTime) ;
        //
        strPara ="2019-03-27";
        logger.info("strPara = " + strPara);
        String strPara1 =strPara + " 00:00:00";
        logger.info("strPara1 = " + strPara1);
        pPattern = "yyyy-MM-dd HH:mm:ss";
        formatter = DateTimeFormatter.ofPattern(pPattern);
        dateTime = LocalDateTime.parse(strPara1, formatter);
        System.out.println("dateTime = " + dateTime) ;
//
        strPara ="2019-03-27";
        logger.info("strPara = " + strPara);
        String strPara2 =strPara + " 23:59:59";
        logger.info("strPara2 = " + strPara2);
        pPattern = "yyyy-MM-dd HH:mm:ss";
        formatter = DateTimeFormatter.ofPattern(pPattern);
        dateTime = LocalDateTime.parse(strPara2, formatter);
        System.out.println("dateTime = " + dateTime) ;
//  27-08-2019  versiot
        strPara ="27-03-2019 13:57:06";
        logger.info("strPara = " + strPara);
        pPattern = "dd-MM-yyyy HH:mm:ss";
        formatter = DateTimeFormatter.ofPattern(pPattern);
        dateTime = LocalDateTime.parse(strPara, formatter);
        System.out.println("dateTime = " + dateTime) ;
//
        strPara ="27-03-2019";
        logger.info("strPara = " + strPara);
        strPara1 =strPara + " 00:00:00";
        logger.info("strPara1 = " + strPara1);
        pPattern = "dd-MM-yyyy HH:mm:ss";
        formatter = DateTimeFormatter.ofPattern(pPattern);
        dateTime = LocalDateTime.parse(strPara1, formatter);
        System.out.println("dateTime = " + dateTime) ;
//
        strPara ="27-03-2019";
        logger.info("strPara = " + strPara);
        strPara2 =strPara + " 23:59:59";
        logger.info("strPara2 = " + strPara2);
        pPattern = "dd-MM-yyyy HH:mm:ss";
        formatter = DateTimeFormatter.ofPattern(pPattern);
        dateTime = LocalDateTime.parse(strPara2, formatter);
        System.out.println("dateTime = " + dateTime);
        System.out.println("dateTime.toString() = " + dateTime.toString()) ;
//*******************************************************************************
        strPara ="03.02.2019 13:57:06";
        logger.info("strPara = " + strPara);
        pPattern = "dd.MM.yyyy HH:mm:ss";
        formatter = DateTimeFormatter.ofPattern(pPattern);
        dateTime = LocalDateTime.parse(strPara, formatter);
        System.out.println("dateTime = " + dateTime) ;
//
        strPara ="03.02.2019";
        logger.info("strPara = " + strPara);
        strPara1 =strPara + " 00:00:00";
        logger.info("strPara1 = " + strPara1);
        pPattern = "dd.MM.yyyy HH:mm:ss";
        formatter = DateTimeFormatter.ofPattern(pPattern);
        dateTime = LocalDateTime.parse(strPara1, formatter);
        System.out.println("dateTime = " + dateTime) ;
//
        strPara ="03.02.2019";
        logger.info("strPara = " + strPara);
        strPara1 =strPara + " 23:59:59";
        logger.info("strPara1 = " + strPara1);
        pPattern = "dd.MM.yyyy HH:mm:ss";
        formatter = DateTimeFormatter.ofPattern(pPattern);
        dateTime = LocalDateTime.parse(strPara1, formatter);
        System.out.println("dateTime = " + dateTime) ;








        //        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime dateTime = LocalDateTime.parse("2019-03-27 10:15:30", formatter);
        //"2019-08-05T11:35:17.113"
        // LocalDateTime dateTime = LocalDateTime.parse("2019-08-05 11:35:17", formatter);


    }






    private static void  hH4444(String strPara) {
        logger.info("strPara = " + strPara);

        LocalDateTime today = LocalDateTime.parse("2019-03-27T10:15:30");
        logger.info ("today = " + today);

//Custom pattern
       // String strPattern ="yyyy-MM-dd HH:mm:ss a";
        String strPattern ="yyyy-MM-dd HH:mm:ss a";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(strPattern);
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a");
        //LocalDateTime dateTime = LocalDateTime.parse("2019-03-27 10:15:30 AM", formatter);
        LocalDateTime dateTime = LocalDateTime.parse(today.toString(), formatter);
        logger.info("strPattern = " + strPattern);
        logger.info("dateTime = " + dateTime);
    }

    private static int  hHJatri(String strPara) {
        logger.info("strPara = " + strPara);
                         //1234567890123456789
        String pattern1 = "yyyy-mm-dd HH:mm:ss";
        logger.info("pattern1 = " + pattern1);
        ZoneId zone2 = ZoneId.of("Europe/Helsinki");
                try {
            final DateTimeFormatter formatter = DateTimeFormatter
                    .ofPattern(pattern1);

             logger.info("formatter.getZone() = " +formatter.getZone());
            // formatter.
            Instant result = Instant.from(formatter.parse(strPara));
            logger.info("**************************************************result=" + result.toString());
            return INT_RET_OK;
        }  catch ( Exception e) {
            logger.info("Huti pattern1=" + pattern1);
            logger.info("e.getMessage() =" + e.getMessage());
        }
        //}
        return INT_RET_NOT_OK;
    }





    public static void main(String[] args) {
        JSwarcoproperties swp = new JSwarcoproperties();
        int iRet = swp.getSwarcoProperties();
        if (iRet != INT_RET_OK) {
            logger.info("Ei saatu propertyjä!");
        }
        tFormats = swp.getTimeFormat();
        //getSwarcoProperties()
        // Handle these dates


        // DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;

//        A simpler method is to add the default timezone to the formatter object when declaring it

        //   final DateTimeFormatter formatter = DateTimeFormatter
        //           .ofPattern("yyyy-MM-dd HH:mm:ss")
        //           .withZone(ZoneId.systemDefault());
        //  Instant result = Instant.from(formatter.parse(timestamp));


        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd kk:HH:mm:ss").withZone(ZoneId.systemDefault());
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd kk:HH:mm:ss").withZone(ZoneId.systemDefault());
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // String text =date.format(formatter);
        // LocalDate parseDate=LocalDate.parse(text,formatter);


       // LocalDate date = LocalDate.now();
       // logger.info("date=" + date.toString());
       // LocalDateTime dateTime = LocalDateTime.now();
        //LocalDateTime.
        //logger.info("dateTime=" + dateTime);
        //logger.info("dateTime.toString()=" + dateTime.toString());

        //String timestamp = dateTime.toString();   //.replace("T"," ");

       String strHelp1 = "12.6.2016 15:36:00";
        String strHelp2 = "12.06.2016 15:36:00";
        String strHelp3 = "05.12.2019 15:08:00";
        String strHelp4 = "2019-12-05 15:08:00";
        String strHelp7 = "05/08/2019";  // --> startDate 2019-08-05 00:00:00
        String strHelp8 = "05.08.2019";  // --> endDate 2019-08-05 23:59:59
        String strHelp5 = "2019-08-02T11:56:11.904";
       // ZonedDateTime noww = ZonedDateTime.now();
        // logger.info ("noww = " + noww);
       // logger.info ("noww.toString() = " + noww.toString());
         //String strHelp6 = "2019-08-02T11:56:11.904";
       // String strHelp6 = "2019-08-02T11:56:11";
       // String strHelp6 = "2019-08-05 11:56:11";

        //iRet = hHJatri(strHelp6);
         // kkkk("1");
        cccc("1");
       // hH4444("1");
        //jZoneids();
 //       iRet = hH(strHelp1);
 //       iRet = hH(strHelp2);
 //       iRet = hH(strHelp3);
 //       iRet = hH(strHelp4);
 //       iRet = hH(strHelp5);

            //
//        logger.info("Moi Jatri info");
//        logger.fatal("Moi Jatri fatal");
//        logger.warn("Moi Jatri warn");
//        logger.error("MOI Jatri error");
            //   rData.setRawDataSourceId(1);   // RETHINK get real source values here
            //   rData.setRawDataLine(strWhoJsonData);
            //   rData.setRawDataStatus(1);   // RETHINK put real status value here
//         String strHelp1="";
            //    String pattern = "yyyy-MM-dd HH:mm:ss";
            //    SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(pattern);
            //    simpleDateFormat1.setTimeZone(TimeZone.getTimeZone("Europe/Helsinki"));
            //    Calendar calendar = new GregorianCalendar();
            //   logger.info( "calendar.getTime() = " + simpleDateFormat1.format(calendar.getTime()));
            //  String strHelp1 = simpleDateFormat1.format(calendar.getTime());
            //   logger.info("strHelp1=" + strHelp1);
            //    strHelp1="2019-07-16 10:55:02";
            //    logger.info("strHelp1=" + strHelp1);
//        System.out.println("Moi Jatri from sysout");
            // connect to database local sqlsevr
            // execute read sql statement
            // write your code here
//        SendFromCloud  sf = new SendFromCloud();
//        int iRet = sf.MakeSendOperations();
//        logger.info("iRet = "+ iRet);
        }
    }


