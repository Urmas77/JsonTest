package fi.swarco.omniaDataTransferServices;

import com.sun.org.apache.regexp.internal.REUtil;
import fi.swarco.dataHandling.MakeSendJsonOperations;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static fi.swarco.CONSTANT.ASCII_CTRL_STRING_DC2;
import static fi.swarco.CONSTANT.NO_VALUE;

// Warning these function are only used for OmniaDatatransfer procedures
// "Single string solution", "own decoding for json because lack of knowled from json/http"
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
        String strHelp1 = "";
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

    public String reCreateJson(String pPseudoJson) {
        String strHelp1 = "";
        // ** --> }
        // * --> {
        // ' ---> "
        // %32  --> blankko/space
       // logger.info("pPseudoJson = " + pPseudoJson);
        strHelp1 = pPseudoJson.replace("**", "}");
       // logger.info("** strHelp1 = " + strHelp1);
        String strHelp2 = strHelp1.replace("*", "{");
      //  logger.info("* strHelp2 = " + strHelp2);
        char ch = '"';
        String charToString = Character.toString(ch);
        strHelp1 = strHelp2.replace("'", charToString);
      //  logger.info(" hipsu strHelp1 = " + strHelp1);
        strHelp2 = strHelp1.replace("%32", " ");
     //   logger.info(" from %32 to blankko strHelp2 =" + strHelp2);
        return strHelp1;
    }

    public String DecodeJson(String pJson) {
        String strHelp1 = "";
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
        String strHelp1 = "";
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
        String strHelp1 = "";
        // [ --> ""
        // ] --> ""
      //  logger.info("pJson =" + pJson);
        strHelp1 = pJson.replace("[", "");
      //  logger.info(" [ --> '' strHelp1 =" + strHelp1);
        String strHelp2 = strHelp1.replace("]", "");
     //   logger.info("]-->'' strHelp2 =" + strHelp2);
        return strHelp2;
    }
    public String StripDC2(String pJson) {
        String strHelp1 = "";
        //DC2--->""
        strHelp1 = pJson.replace(ASCII_CTRL_STRING_DC2, "");
        return strHelp1;
    }
    public String AddBrackets(String pJson) {
        String strHelp1 = "";
        //   kkkk --> [ kkkk ] ""
       // logger.info("pJson =" + pJson);
        strHelp1 = "[" +  pJson +"]";
      //  logger.info("strHelp1 =" + strHelp1);
        return strHelp1;
    }
}