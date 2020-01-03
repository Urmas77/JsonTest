package fi.swarco.testPrograms;
import fi.swarco.dataHandling.pojos.OmniaMeasurementDataShort;
import fi.swarco.dataHandling.pojos.OmniaMeasurementDataShortJson;
import fi.swarco.properties.JSwarcoproperties;
import org.apache.log4j.Logger;

import static fi.swarco.CONSTANT.DOUBLE_LONG_MULTIPLIER;

public class t3 {
    static Logger logger = Logger.getLogger(t3.class.getName());
    public static void main (String[] args) throws java.lang.Exception
    {
        JSwarcoproperties swp = new JSwarcoproperties();

        double dblHelp1=0.0;
        double dblHelp2=0.0;
        double dblHelp3=0.0;
        long lngHelp1=0;

        dblHelp1 = 7.01;
        logger.info("dblHelp1 = " + dblHelp1);
        dblHelp2 = dblHelp1*DOUBLE_LONG_MULTIPLIER;
        logger.info("dblHelp1*DOUBLE_LONG_MULTIPLIER = " + dblHelp2);
        lngHelp1 = (long) dblHelp2;
        logger.info("lngHelp1 = " + lngHelp1);
        dblHelp3 =  lngHelp1/DOUBLE_LONG_MULTIPLIER;
        logger.info("end = " + dblHelp3 + " midle :" + dblHelp2 +   " start :" + dblHelp1);
        logger.info("***************************************************************");
        dblHelp1 = -7.01;
        logger.info("dblHelp1 = " + dblHelp1);
        dblHelp2 = dblHelp1*DOUBLE_LONG_MULTIPLIER;
        logger.info("dblHelp1*DOUBLE_LONG_MULTIPLIER = " + dblHelp2);
        lngHelp1 = (long) dblHelp2;
        logger.info("lngHelp1 = " + lngHelp1);
        dblHelp3 =  lngHelp1/DOUBLE_LONG_MULTIPLIER;
        logger.info("end = " + dblHelp3 + " midle :" + dblHelp2 +   " start :" + dblHelp1);
        logger.info("***************************************************************");
        dblHelp1 = -1;
        logger.info("dblHelp1 = " + dblHelp1);
        dblHelp2 = dblHelp1*DOUBLE_LONG_MULTIPLIER;
        logger.info("dblHelp1*DOUBLE_LONG_MULTIPLIER = " + dblHelp2);
        lngHelp1 = (long) dblHelp2;
        logger.info("lngHelp1 = " + lngHelp1);
        dblHelp3 =  lngHelp1/DOUBLE_LONG_MULTIPLIER;
        logger.info("end = " + dblHelp3 + " midle :" + dblHelp2 +   " start :" + dblHelp1);
        logger.info("***************************************************************");
        dblHelp1 = 100;
        logger.info("dblHelp1 = " + dblHelp1);
        dblHelp2 = dblHelp1*DOUBLE_LONG_MULTIPLIER;
        logger.info("dblHelp1*DOUBLE_LONG_MULTIPLIER = " + dblHelp2);
        lngHelp1 = (long) dblHelp2;
        logger.info("lngHelp1 = " + lngHelp1);
        dblHelp3 =  lngHelp1/DOUBLE_LONG_MULTIPLIER;
        logger.info("end = " + dblHelp3 + " midle :" + dblHelp2 +   " start :" + dblHelp1);
        logger.info("***************************************************************");
        dblHelp1 = 0;
        logger.info("dblHelp1 = " + dblHelp1);
        dblHelp2 = dblHelp1*DOUBLE_LONG_MULTIPLIER;
        logger.info("dblHelp1*DOUBLE_LONG_MULTIPLIER = " + dblHelp2);
        lngHelp1 = (long) dblHelp2;
        logger.info("lngHelp1 = " + lngHelp1);
        dblHelp3 =  lngHelp1/DOUBLE_LONG_MULTIPLIER;
        logger.info("end : " + dblHelp3 + " midle :" + dblHelp2 +   " start :" + dblHelp1);
        logger.info("***************************************************************");
        dblHelp1 = 0.18;
        logger.info("dblHelp1 = " + dblHelp1);
        dblHelp2 = dblHelp1*DOUBLE_LONG_MULTIPLIER;
        logger.info("dblHelp1*DOUBLE_LONG_MULTIPLIER = " + dblHelp2);
        lngHelp1 = (long) dblHelp2;
        logger.info("lngHelp1 = " + lngHelp1);
        dblHelp3 =  lngHelp1/DOUBLE_LONG_MULTIPLIER;
        logger.info("end = " + dblHelp3 + " midle :" + dblHelp2 +   " start :" + dblHelp1);
        logger.info("***************************************************************");
        dblHelp1 = 0.00078;
        logger.info("dblHelp1 = " + dblHelp1);
        dblHelp2 = dblHelp1*DOUBLE_LONG_MULTIPLIER;
        logger.info("dblHelp1*DOUBLE_LONG_MULTIPLIER = " + dblHelp2);
        lngHelp1 = (long) dblHelp2;
        logger.info("lngHelp1 = " + lngHelp1);
        dblHelp3 =  lngHelp1/(DOUBLE_LONG_MULTIPLIER);
        logger.info("end = " + dblHelp3 + " midle :" + dblHelp2 +   " start :" + dblHelp1);
        logger.info("***************************************************************");
        OmniaMeasurementDataShortJson ceJson = new OmniaMeasurementDataShortJson();
        OmniaMeasurementDataShort ce = new OmniaMeasurementDataShort();
        ce.setOmniaCode(2);
        ce.setIntersectionId(3);
        ce.setControllerId(4);
        ce.setMeasurementTime("2016-12-21 12:10:42.610");
        ce.setDetectorId(5);
        ce.setDetectorExternalCode("EXTJI1");
        ce.setVehicleCount(555);
        ce.setMeanVehicleSpeed(-1111.111);
        ce.setOccupancyProcent(22.2);
        ce.setAccurancy(0.5);
        logger.info ("Start ce.toString" + ce.toString());
        ceJson=ce.SetJsonTransferItem();
        logger.info ("ceJson.toString" + ceJson.toString());
        ce=ceJson.MakeItemFromJsonTransferItem();
        logger.info ("end ce.toString" + ce.toString());
        logger.info("***************************************************************");
        logger.info(" DOUBLE_LONG_MULTIPLIER  =" +  DOUBLE_LONG_MULTIPLIER);
        ce.setMeanVehicleSpeed(-0.111);
        ce.setOccupancyProcent(4444444422.2);
        ce.setAccurancy(0.000075);
        logger.info ("Start ce.toString" + ce.toString());
        ceJson=ce.SetJsonTransferItem();
        logger.info ("ceJson.toString" + ceJson.toString());
        ce=ceJson.MakeItemFromJsonTransferItem();
        logger.info ("end ce.toString" + ce.toString());
        logger.info("***************************************************************");



        ce.setMeanVehicleSpeed(12.11);
        ce.setOccupancyProcent(33.2);
        ce.setAccurancy(99);
        logger.info ("Start ce.toString" + ce.toString());
        ceJson=ce.SetJsonTransferItem();
        logger.info ("ceJson.toString" + ceJson.toString());
        ce=ceJson.MakeItemFromJsonTransferItem();
        logger.info ("end ce.toString" + ce.toString());
        logger.info("***************************************************************");


        ce.setMeanVehicleSpeed(0);
        ce.setOccupancyProcent(100);
        ce.setAccurancy(56);
        logger.info ("Start ce.toString" + ce.toString());
        ceJson=ce.SetJsonTransferItem();
        logger.info ("ceJson.toString" + ceJson.toString());
        ce=ceJson.MakeItemFromJsonTransferItem();
        logger.info ("end ce.toString" + ce.toString());
        logger.info("***************************************************************");


        ce.setMeanVehicleSpeed(-.1000011);
        ce.setOccupancyProcent(1522.2);
        ce.setAccurancy(333);
        logger.info ("Start ce.toString" + ce.toString());
        ceJson=ce.SetJsonTransferItem();
        logger.info ("ceJson.toString" + ceJson.toString());
        ce=ceJson.MakeItemFromJsonTransferItem();
        logger.info ("end ce.toString" + ce.toString());
        logger.info("***************************************************************");


        ce.setMeanVehicleSpeed(-.1000011);
        ce.setOccupancyProcent(.1000011);
        ce.setAccurancy(.1000019);
        logger.info ("Start ce.toString" + ce.toString());
        ceJson=ce.SetJsonTransferItem();
        logger.info ("ceJson.toString" + ceJson.toString());
        ce=ceJson.MakeItemFromJsonTransferItem();
        logger.info ("end ce.toString" + ce.toString());
        logger.info("***************************************************************");

        ce.setMeanVehicleSpeed(.1000019);
        ce.setOccupancyProcent(.100019);
        ce.setAccurancy(.10019);
        logger.info ("Start ce.toString" + ce.toString());
        ceJson=ce.SetJsonTransferItem();
        logger.info ("ceJson.toString" + ceJson.toString());
        ce=ceJson.MakeItemFromJsonTransferItem();
        logger.info ("end ce.toString" + ce.toString());
        logger.info("***************************************************************");









      /*  int iRet = swp.getSwarcoProperties();
        if (iRet != INT_RET_OK) {
            logger.info("Ei saatu propertyjä!");
        }
        SwarcoTimeUtilities sw = new SwarcoTimeUtilities();
        //yyyy-MM-ddTHH:mm:ss.mmm -->  "yyyy-MM-dd HH:mm:ss"
        //2016-12-21T12:10:42.610 ---> 2016-12-21 12:10:42.610

        String strHelp1 = sw.ToSwarcoTime("2016-12-21T12:10:42.610");
        logger.info("strHelp1 =" + strHelp1); */

    }
}
