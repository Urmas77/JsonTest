package fi.swarco.omniaDataTransferServices;
import org.apache.log4j.Logger;
public class InfluxUtilities {
   private static Logger logger = Logger.getLogger(InfluxUtilities.class.getName());
   public String FilterInfluxFields(String pStrInField) {
        String strHelp1 = pStrInField.replace(",", "#");
        strHelp1 = strHelp1.replace(" ","&");
        return strHelp1;
    }
}