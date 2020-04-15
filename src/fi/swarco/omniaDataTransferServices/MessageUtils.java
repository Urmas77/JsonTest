package fi.swarco.omniaDataTransferServices;
import org.apache.log4j.Logger;
import static fi.swarco.CONSTANT.*;
// Warning these function are only used for OmniaDatatransfer procedures
// "Single string solution", "own decoding for json because lack of knowledge from json/http"
// JIs 26.06 2019
public class MessageUtils {
    static Logger logger = Logger.getLogger(MessageUtils.class.getName());
    public MessageUtils() {
    }
    // } --> %125
    // { --> %123
    // " ---> %34
    // ' ' = %32
    public String reCreateJsonDecimal(String pPseudoJson) {
        String strHelp1; // = "";
        // %125 -->    }
        // %123  -->   {
        // %34  --->  "
        // %32   --->' '
      //  logger.info("pPseudoJson = " + pPseudoJson);
        strHelp1 = pPseudoJson.replace("%125", "}");
      //  logger.info("%125 --> } strHelp1 = " + strHelp1);
        String strHelp2 = strHelp1.replace("%123", "{");
       // logger.info("%123 --> { strHelp2 = " + strHelp2);
        char ch = '"';
        String charToString = Character.toString(ch);
        strHelp1 = strHelp2.replace("%34", charToString);
       // logger.info("%34 --> hipsu strHelp1 = " + strHelp1);
        strHelp2 = strHelp1.replace("%32", " ");
       // logger.info(" from %32 --> blankko strHelp2 =" + strHelp2);
        // },{   --->  }{   RETHINK JIs 16.07 2019
        strHelp1 = strHelp2.replace("},{", "}{");
       // logger.info(" from },{ --> }{ strHelp1 =" + strHelp1);
        return strHelp1;
    }
    // Not tested RETHINK
    private String numberToLetter(int pNumber) {
        String[] letters = {"A", "B","C","D","E","F","G","H","I","K"};
        if (pNumber<0) {
            return "S";
        }
        if (pNumber>9) {
            return "M";
        }
        return letters[pNumber];
    }
    public String DecodeJson(String pJson) {
        String strHelp1;
        // } --> **
        // { --> *
        // " ---> '
        // ' ' = %32
       // logger.info("pJson =" + pJson);
        strHelp1 = pJson.replace("}", "**");
     //   logger.info(" -->** strHelp1 =" + strHelp1);
        String strHelp2 = strHelp1.replace("{", "*");
       // logger.info("{-->* strHelp2 =" + strHelp2);
        char ch = '"';
        String charToString = Character.toString(ch);
        strHelp1 = strHelp2.replace(charToString, "'");
      //  logger.info(" to ykshipisu strHelp1 =" + strHelp1);
        strHelp2 = strHelp1.replace(" ", "%32");
     //   logger.info(" blanko to %32 strHelp1 =" + strHelp1);
        return strHelp2;
    }
    public String DecodeJsonPercentDecimal(String pJson) {
        String strHelp1;
        // } --> %125
        // { --> %123
        // " ---> %34
        // ' ' = %32
     //   logger.info("pJson =" + pJson);
        strHelp1 = pJson.replace("}", "%125");
    //    logger.info(" } --> %125 strHelp1 =" + strHelp1);
        String strHelp2 = strHelp1.replace("{", "%123");
    //    logger.info("{-->%123  strHelp2 =" + strHelp2);
        char ch = '"';
        String charToString = Character.toString(ch);
        strHelp1 = strHelp2.replace(charToString, "%34");
     //   logger.info("  ykshipisu  %34 strHelp1 =" + strHelp1);
        strHelp2 = strHelp1.replace(" ", "%32");
    //    logger.info(" blanko to %32 strHelp2 =" + strHelp2);
        return strHelp2;
    }
    public String CutJsonMessage(String pJson) {
        String strHelp1;
        if (pJson.indexOf(TT_MEASUREMENT_DATA_INSERT) == 0) {
            strHelp1 = pJson.substring(TT_MEASUREMENT_DATA_INSERT.length());
            return strHelp1;
        }
        if (pJson.indexOf(TT_INTERSECTION_DATA_CHANGE) == 0) {
            strHelp1 = pJson.substring(TT_INTERSECTION_DATA_CHANGE.length());
            return strHelp1;
        }
        if (pJson.indexOf(TT_CONTROLLER_DATA_CHANGE) == 0) {
            strHelp1 = pJson.substring(TT_CONTROLLER_DATA_CHANGE.length());
            return strHelp1;
        }
        if (pJson.indexOf(TT_DETECTOR_DATA_CHANGE) == 0) {
            strHelp1 = pJson.substring(TT_DETECTOR_DATA_CHANGE.length());
            return strHelp1;
        }
        if (pJson.indexOf(TT_NOT_DEFINED) == 0)    {
            strHelp1 = pJson.substring(TT_NOT_DEFINED.length());
            return strHelp1;
        }
        return pJson ;   // current situation nothing to cut RETHINK JIs 15.10 2019
    }
    public String GetJsonMessageType(String pJson) {
//        public static final String  TT_MEASUREMENT_DATA_INSERT="MEASUREMENTDATAINSERT";
//        public static final String  TT_CONTROLLER_DATA_CHANGE="CONTROLLERDATACHANGE";
//        public static final String  TT_DETECTOR_DATA_CHANGE="DETECTORDATACHANGE";                    //  1234567890123456789012345678901234567890
//        public static final String  TT_NOT_DEFINED="NOTDEFINED";
        if (pJson.indexOf(TT_MEASUREMENT_DATA_INSERT) == 0) {
            return TT_MEASUREMENT_DATA_INSERT;
        }
        if (pJson.indexOf(TT_INTERSECTION_DATA_CHANGE) == 0) {
            return TT_INTERSECTION_DATA_CHANGE;
        }
        if (pJson.indexOf(TT_CONTROLLER_DATA_CHANGE) == 0) {
            return TT_CONTROLLER_DATA_CHANGE;
        }
        if (pJson.indexOf(TT_DETECTOR_DATA_CHANGE) == 0) {
           return TT_DETECTOR_DATA_CHANGE;
        }
        if (pJson.indexOf(TT_NOT_DEFINED) == 0)    {
        return TT_NOT_DEFINED ;
        }
        return TT_NOT_DEFINED ;
    }

    public JsonWrapper SplitJson(String pJson) {
// separate permanent and measurement data
        JsonWrapper jsw = new JsonWrapper();
    //    logger.info("pJson = " + pJson);
        jsw.setFullData(pJson);
    //    logger.info("after setfulldatapJson = " + pJson);
        //RETHINK Check isep =-1
        int iSep = pJson.indexOf("[");   //   pJson.indexOf("]")  this was old RETHINK
        if (iSep == -1) {
      //      logger.info("Unsuccessfull split iSep = " + iSep);
            iSep = 0;
            jsw.MakeEmptyElement();
        } else {
            String strPermanent = pJson.substring(0, iSep);
            jsw.setPermanentData(strPermanent);
        //    logger.info(" strPermanent = " + strPermanent);
            String strMeasurements = pJson.substring(iSep + 1);
            jsw.setMeasurementsData(strMeasurements);
        //    logger.info(" strMeasurements = " + strMeasurements);
        //    logger.info("succesfull split");
        }
        return jsw;
    }
    public String StripFileStartEnd(String pJson) {
        String strHelp1;
        // [ --> ""
        // ] --> ""
      //  logger.info("pJson =" + pJson);
        strHelp1 = pJson.replace("[", "");
      //  logger.info(" [ --> '' strHelp1 =" + strHelp1);
        strHelp1 = strHelp1.replace("]", "");
     //   logger.info("]-->'' strHelp2 =" + strHelp2);
        return strHelp1;
    }


    public String ThrowChecksumAway(String pJson) {
        String strHelp1;
        int iSep = pJson.indexOf("}&chk");
        if (iSep == -1) {
            return pJson;
        }  else {
            strHelp1 = pJson.substring(0,pJson.indexOf("}&chk")+1);
            return strHelp1;
        }
    }
    public String StripDC2(String pJson) {
        String strHelp1;
        //DC2--->""
        strHelp1 = pJson.replace(ASCII_CTRL_STRING_DC2, "");
        return strHelp1;
    }
    public String AddBrackets(String pJson) {
        String strHelp1;
        //   kkkk --> [ kkkk ] ""
       // logger.info("pJson =" + pJson);
        strHelp1 = "[" +  pJson +"]";
      //  logger.info("strHelp1 =" + strHelp1);
        return strHelp1;
    }
}