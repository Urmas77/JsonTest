package fi.swarco.testPrograms;
import fi.swarco.dataHandling.pojos.OmniaMeasurementDataShort;
import fi.swarco.dataHandling.pojos.OmniaMeasurementDataShortJson;
import fi.swarco.omniaDataTransferServices.FileOperations;
import fi.swarco.properties.JSwarcoproperties;
import org.apache.log4j.Logger;
import static fi.swarco.CONSTANT.DOUBLE_LONG_MULTIPLIER;
import static fi.swarco.CONSTANT.INT_RET_OK;
    public class T6 {
        static Logger logger = Logger.getLogger(fi.swarco.testPrograms.T6.class.getName());
        public static void main (String[] args) throws java.lang.Exception
        {
            JSwarcoproperties swp = new JSwarcoproperties();
          //  logger.info("***************************************************************");
            int iRet = swp.getLog4JProperties();
            if (iRet != INT_RET_OK) {
                System.out.println("Ei saatu propertyjä!");
            }
            String strHelp1 = swp.getLog4JPathAndFileName();
        //    strHelp1= "e://log//apu.log";
            System.out.println("strHelp1 =" + strHelp1);
            FileOperations fo = new FileOperations();
            iRet = fo.closeAndDeleteFile(strHelp1);
            System.out.println("iRey = "+ iRet);
        }
    }

