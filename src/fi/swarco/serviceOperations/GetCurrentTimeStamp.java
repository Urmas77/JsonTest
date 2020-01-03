package fi.swarco.serviceOperations;
import org.apache.log4j.Logger;
//1.1.1970 ---> 1.1.2000
//30 vuotta = 10957 p�iv�� = 10957*86400*1000 millisekuntia =  946 684 800 000 millisekuntia  
//halutaan 1.1.2000 to now millisekunteja
//now -
//ei voi testata t��ll� mainissa jos ei ole static metodi ? JIs 25.5 2016
public class GetCurrentTimeStamp {
  static Logger logger = Logger.getLogger(GetCurrentTimeStamp.class.getName());
  public static long Getfrom2000Timestamp() {
	    long lngRet=0;
	    try {
	        String w1970To2000Milliseconds="946684800000"; 
          long year2000ZeroTime = Long.parseLong(w1970To2000Milliseconds);
	        long curTimestamp= System.currentTimeMillis()-  year2000ZeroTime; 
          return curTimestamp/1000;
	    } catch (Exception e) {
          e.printStackTrace();
			return lngRet; 
      }
  }  
}
