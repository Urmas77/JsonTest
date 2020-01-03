package fi.swarco.serviceOperations;
import org.apache.log4j.Logger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
public class ShowHumanTime {
	static Logger logger = Logger.getLogger(ShowHumanTime.class.getName());
	public String getHumanGMTZeroFromUnixTimestamp(long plTimestamp){ 
	   SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
	   jdf.setTimeZone(TimeZone.getTimeZone("GMT-0"));
	   Date date = new Date(plTimestamp); 
	   String java_date = jdf.format(date);
	   logger.info("plTimestamp =" +plTimestamp);
	   logger.info("java_date =" +java_date);
       return java_date;	   
	}
}
