package fi.swarco.testPrograms;
import fi.swarco.omniaDataTransferServices.SwarcoTimeUtilities;
import fi.swarco.properties.JSwarcoproperties;
import org.apache.log4j.Logger;
import static fi.swarco.CONSTANT.INT_RET_OK;

public class Test2 {
    static Logger logger = Logger.getLogger(Test2.class.getName());
    public static void main (String[] args) throws java.lang.Exception
    {
        JSwarcoproperties swp = new JSwarcoproperties();
        int iRet = swp.getSwarcoProperties();
        if (iRet != INT_RET_OK) {
            logger.info("Ei saatu propertyjä!");
        }
        SwarcoTimeUtilities sw = new SwarcoTimeUtilities();
        //yyyy-MM-ddTHH:mm:ss.mmm -->  "yyyy-MM-dd HH:mm:ss"
        //2016-12-21T12:10:42.610 ---> 2016-12-21 12:10:42.610

        String strHelp1 = sw.ToSwarcoTime("2016-12-21T12:10:42.610");
        logger.info("strHelp1 =" + strHelp1);

    }
}
