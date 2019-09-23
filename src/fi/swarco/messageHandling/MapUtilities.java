package fi.swarco.messageHandling;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class MapUtilities {
    static Logger logger = Logger.getLogger(MapUtilities.class.getName());
    // remember table start from 0
    public  Map<String, String> queryToMap(String query){
        Map<String, String> result = new HashMap<String, String>();
        for (String param : query.split("&")) {
            String[] pair = param.split("=");
            if (pair.length>1) {
                result.put(pair[0], pair[1]);
            } else {
                result.put(pair[0], "");
            }
        }
        return result;
    }
    public Map<String, String> SeqDataToMap(String line){
        Map<String, String> result = new HashMap<String, String>();
        for (String param : line.split(",")) {
            String[] pair = param.split("=");
            if (pair.length>1) {
                result.put(pair[0], pair[1]);
            } else {
                result.put(pair[0], "");
            }
        }
        return result;}
}



