package fi.swarco.testPrograms;
import fi.swarco.omniaDataTransferServices.FileOperations;
import fi.swarco.properties.JSwarcoproperties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import static fi.swarco.CONSTANT.INT_RET_OK;
    public class T6 {
        static Logger logger = Logger.getLogger(fi.swarco.testPrograms.T6.class.getName());
        public static void main (String[] args)
        {
            JSwarcoproperties swp = new JSwarcoproperties();
            String strTime = java.time.LocalTime.now().toString();
            System.out.println("strTime = "+ strTime);
            strTime=strTime.substring(0,5);
            System.out.println("strTime = "+ strTime);
        }
    }

