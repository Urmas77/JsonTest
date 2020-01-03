package fi.swarco.serviceOperations;
import org.apache.log4j.Logger;

public class KKKK {
	static Logger logger = Logger.getLogger(KKKK.class.getName());
	public static void main(String[] args) {
		String strHelp1="";
/*
		
		//14: 
			1.482303260E12 
			1.482303320E12 
			1.482303380E12 
			1.482303440E12 
//13:
			1.48130746E12
			1.48130752E12
			1.48130758E12
			1.48229906E12
			1.48229912E12
//12:
			1.4823026E12
			1.4823044E12
//11:
			1.482302E12
			1.482305E12
			1.481308E12
			1.482299E12 */ 
		SegFileSetter hhh = new SegFileSetter();
        strHelp1 ="1.482303260E12";     
		logger.info("FillTimestampString("+ strHelp1 +  ")= " + hhh.FillTimestampString(strHelp1));
		//14: 
		strHelp1 ="1.482303260E12"; 
		logger.info("FillTimestampString("+ strHelp1 +  ")= " + hhh.FillTimestampString(strHelp1));
		strHelp1 ="1.482303320E12"; 
		logger.info("FillTimestampString("+ strHelp1 +  ")= " + hhh.FillTimestampString(strHelp1));
		strHelp1 ="1.482303380E12"; 
		logger.info("FillTimestampString("+ strHelp1 +  ")= " + hhh.FillTimestampString(strHelp1));
		strHelp1 ="1.482303440E12"; 
		logger.info("FillTimestampString("+ strHelp1 +  ")= " + hhh.FillTimestampString(strHelp1));
		//13:
		strHelp1 ="1.48130746E12";
		logger.info("FillTimestampString("+ strHelp1 +  ")= " + hhh.FillTimestampString(strHelp1));
		strHelp1 ="1.48130752E12";
		logger.info("FillTimestampString("+ strHelp1 +  ")= " + hhh.FillTimestampString(strHelp1));
		strHelp1 ="1.48130758E12";
		logger.info("FillTimestampString("+ strHelp1 +  ")= " + hhh.FillTimestampString(strHelp1));
		strHelp1 ="1.48229906E12";
		logger.info("FillTimestampString("+ strHelp1 +  ")= " + hhh.FillTimestampString(strHelp1));
		strHelp1 ="1.48229912E12";
		logger.info("FillTimestampString("+ strHelp1 +  ")= " + hhh.FillTimestampString(strHelp1));
//12:
		strHelp1 ="1.4823026E12";
		logger.info("FillTimestampString("+ strHelp1 +  ")= " + hhh.FillTimestampString(strHelp1));
		strHelp1 ="1.4823044E12";
		logger.info("FillTimestampString("+ strHelp1 +  ")= " + hhh.FillTimestampString(strHelp1));
//11:              01234567890
		strHelp1 ="1.482302E12";
		logger.info("FillTimestampString("+ strHelp1 +  ")= " + hhh.FillTimestampString(strHelp1));
		strHelp1 ="1.482305E12";
		logger.info("FillTimestampString("+ strHelp1 +  ")= " + hhh.FillTimestampString(strHelp1));
		strHelp1 ="1.481308E12";
		logger.info("FillTimestampString("+ strHelp1 +  ")= " + hhh.FillTimestampString(strHelp1));
		strHelp1 ="1.482299E12"; 
		logger.info("FillTimestampString("+ strHelp1 +  ")= " + hhh.FillTimestampString(strHelp1));
// 10		       01234567890 
		strHelp1 ="1.48229E12";
		logger.info("FillTimestampString("+ strHelp1 +  ")= " + hhh.FillTimestampString(strHelp1));
// 9:
		strHelp1 ="1.4822E12";
		logger.info("FillTimestampString("+ strHelp1 +  ")= " + hhh.FillTimestampString(strHelp1));
// 8: 
		strHelp1 ="1.482E12";
		logger.info("FillTimestampString("+ strHelp1 +  ")= " + hhh.FillTimestampString(strHelp1));
// 7:
		strHelp1 ="1.48E12";
		logger.info("FillTimestampString("+ strHelp1 +  ")= " + hhh.FillTimestampString(strHelp1));
// 6: 
		strHelp1 ="1.4E12";
		logger.info("FillTimestampString("+ strHelp1 +  ")= " + hhh.FillTimestampString(strHelp1));
// 5:
		strHelp1 ="1.E12";
		logger.info("FillTimestampString("+ strHelp1 +  ")= " + hhh.FillTimestampString(strHelp1));
	}		
		
		
		


    public static String FillTimestampStringkkkkk(String oStamp) {
	    String strHelp55="";
	    strHelp55= oStamp;
   	    if (strHelp55.length()!=14) {
    	    logger.info (strHelp55.length()+ " : " + strHelp55); 	
   	        if (strHelp55.length()==13) {
    	        strHelp55=strHelp55.substring(0,10) +"0" + strHelp55.substring(10,13);
                logger.info (strHelp55.length()+ " : " + strHelp55 + " Fixed"); 	
    	    } else if (strHelp55.length()==12) {
     		    strHelp55=strHelp55.substring(0,9) +"00" + strHelp55.substring(9,12);
        	    logger.info (strHelp55.length()+ " : " + strHelp55 + " Fixed"); 	
            } else if (strHelp55.length()==11) {
     	        strHelp55=strHelp55.substring(0,8) +"000" + strHelp55.substring(8,11);
                logger.info (strHelp55.length()+ " : " + strHelp55 + " Fixed"); 	
            } else {
                logger.info (strHelp55.length()+ " : " + strHelp55 + " not Fixed ***************************************************");
    	        return "*****"; 
            }
   	    } else {
   	        logger.info (strHelp55.length()+ " : " + strHelp55 + " Orig OK ***************************************************");
   	    }
        return strHelp55;
	
	}	
	
}	

