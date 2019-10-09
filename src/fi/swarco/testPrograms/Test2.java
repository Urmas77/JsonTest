package fi.swarco.testPrograms;
import fi.swarco.dataHandling.pojos.OmniaMeasurementDataShortJson;
import fi.swarco.omniaDataTransferServices.SwarcoTimeUtilities;
import fi.swarco.properties.JSwarcoproperties;
import org.apache.log4j.Logger;

import static fi.swarco.CONSTANT.DOUBLE_LONG_MULTIPLIER;
import static fi.swarco.CONSTANT.INT_RET_OK;

public class Test2 {
    static Logger logger = Logger.getLogger(Test2.class.getName());
    public static void main (String[] args) throws java.lang.Exception
    {
        JSwarcoproperties swp = new JSwarcoproperties();
        OmniaMeasurementDataShortJson ce = new OmniaMeasurementDataShortJson();
        ce.setOmniaCode(2);
        ce.setIntersectionId(3);
        ce.setControllerId(4);
        ce.setMeasurementTime("2016-12-21 12:10:42.610");
        ce.setDetectorId(5);
        ce.setDetectorExternalCode("EXTJI1")
         ce.setVehicleCount(555);


        double bill = 555.99;
        long myBill = (long) bill;

        logger.info("bill = " + bill);
        logger.info("myBill = " + myBill);


        //String strHelp1 = 555.99
        // ((long lngVal1 = 555.99 * DOUBLE_LONG_MULTIPLIER;




        ce.setMeanVehicleSpeedJson(111);
        ce.setOccupancyProcentJson(222);
        ce.setAccurancyJson(0);
        logger.info ("ce.toString" + ce.toString());



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
