package fi.swarco.testPrograms;
import fi.swarco.omniaDataTransferServices.MessageUtils;
import org.apache.log4j.Logger;
import static fi.swarco.CONSTANT.*;
public class Test3 {
    static Logger logger = Logger.getLogger(Test3.class.getName());
    public static void main (String[] args) throws java.lang.Exception
    {
    String strHelp1 = NO_VALUE;
    String strHelp2 = NO_VALUE;
    MessageUtils mu = new MessageUtils();
    strHelp1= TT_CONTROLLER_DATA_CHANGE+"uuuuuuuuuuuuuuuuXXXXXXXXXXXXXXXXXXXXXXXXXX"+TT_CONTROLLER_DATA_CHANGE;
    logger.info("orig strHelp1 =" + strHelp1);
    strHelp2=mu.GetJsonMessageType(strHelp1);
    logger.info("messagetype  strHelp2 =" + strHelp2);
    strHelp2 =mu.CutJsonMessage(strHelp1);
    logger.info("message  strHelp2 =" + strHelp2);
    logger.info("************************************************************");
    strHelp1= "TT_CONTROLLER_DATAccc_CHANGE"+"uuuuuuuuuuuuuuuuXXXXXXXXXXXXXXXXXXXXXXXXXX"+TT_CONTROLLER_DATA_CHANGE;
    logger.info("orig strHelp1 =" + strHelp1);
    strHelp2=mu.GetJsonMessageType(strHelp1);
    logger.info("messagetype  strHelp2 =" + strHelp2);
    strHelp2 =mu.CutJsonMessage(strHelp1);
    logger.info("message  strHelp2 =" + strHelp2);
    logger.info("************************************************************");
    strHelp1= TT_INTERSECTION_DATA_CHANGE+"uuuuuuuuuuuuuuuuXXXXXXXXXXXXXXXXXXXXXXXXXX"+TT_CONTROLLER_DATA_CHANGE;
    logger.info("orig strHelp1 =" + strHelp1);
    strHelp2=mu.GetJsonMessageType(strHelp1);
    logger.info("messagetype  strHelp2 =" + strHelp2);
    strHelp2 =mu.CutJsonMessage(strHelp1);
    logger.info("message  strHelp2 =" + strHelp2);
    logger.info("************************************************************");
    strHelp1= TT_DETECTOR_DATA_CHANGE+"uuuuuuuuuuuuuuuuXXXXXXXXXXXXXXXXXXXXXXXXXX"+TT_CONTROLLER_DATA_CHANGE;
    logger.info("orig strHelp1 =" + strHelp1);
    strHelp2=mu.GetJsonMessageType(strHelp1);
    logger.info("messagetype  strHelp2 =" + strHelp2);
    strHelp2 =mu.CutJsonMessage(strHelp1);
    logger.info("message  strHelp2 =" + strHelp2);
    logger.info("************************************************************");
    strHelp1= TT_MEASUREMENT_DATA_INSERT+"uuuuuuuuuuuuuuuuXXXXXXXXXXXXXXXXXXXXXXXXXX"+TT_CONTROLLER_DATA_CHANGE;
    logger.info("orig strHelp1 =" + strHelp1);
    strHelp2=mu.GetJsonMessageType(strHelp1);
    logger.info("messagetype  strHelp2 =" + strHelp2);
    strHelp2 =mu.CutJsonMessage(strHelp1);
    logger.info("message  strHelp2 =" + strHelp2);
    logger.info("************************************************************");
    strHelp1= TT_NOT_DEFINED+"uuuuuuuuuuuuuuuuXXXXXXXXXXXXXXXXXXXXXXXXXX"+TT_CONTROLLER_DATA_CHANGE;
    logger.info("orig strHelp1 =" + strHelp1);
    strHelp2=mu.GetJsonMessageType(strHelp1);
    logger.info("messagetype  strHelp2 =" + strHelp2);
    strHelp2 =mu.CutJsonMessage(strHelp1);
    logger.info("message  strHelp2 =" + strHelp2);
    logger.info("************************************************************");
    strHelp1= "uuuuuuuuuuuuuuuuXXXXXXXXXXXXXXXXXXXXXXXXXX"+TT_CONTROLLER_DATA_CHANGE;
    logger.info("orig strHelp1 =" + strHelp1);
    strHelp2=mu.GetJsonMessageType(strHelp1);
    logger.info("messagetype  strHelp2 =" + strHelp2);
    strHelp2 =mu.CutJsonMessage(strHelp1);
    logger.info("message  strHelp2 =" + strHelp2);
    logger.info("************************************************************");
    strHelp1= "uuuuuuuuuuuuuuuuXXXXXXXXXXXXXXXXXXXXXXXXXX";
    logger.info("orig strHelp1 =" + strHelp1);
    strHelp2=mu.GetJsonMessageType(strHelp1);
    logger.info("messagetype  strHelp2 =" + strHelp2);
    strHelp2 =mu.CutJsonMessage(strHelp1);
    logger.info("message  strHelp2 =" + strHelp2);
    }
}



