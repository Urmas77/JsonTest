package fi.swarco.dataHandling;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
//import pumpEquations1.PointType;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import fi.swarco.SwarcoEnumerations;
import fi.swarco.dataHandling.pojos.OmniaIntersectionData;
import fi.swarco.dataHandling.pojos.OmniaMeasurementData;
import org.apache.log4j.Logger;

public class ReandJsonfromOmniaDb {
    static Logger logger = Logger.getLogger(ReandJsonfromOmniaDb.class.getName());
    private String jsonSource;
    private boolean sourceFromFile;
// Rehink not from file direct from
    public ReandJsonfromOmniaDb(String jsonSource, boolean sourceFromFile){
        this.jsonSource = jsonSource;
        this.sourceFromFile = sourceFromFile;
    }
    public static void main(String[] args){
        JsonOmniaMeasurementSql();
        JsonOmniaIntersectionSql();
    }
    /**
     * Obtain the JsonReader for the given source details.
     * @return the JsonReader instance
     * @throws FileNotFoundException
     */
    private JsonReader getJsonReader () throws FileNotFoundException{
        JsonReader reader = null;
        if (sourceFromFile){
            reader = new JsonReader(
                    new InputStreamReader(new FileInputStream(this.jsonSource)));
        }
        return reader;
    }
    //**********************************************************************
    private static void JsonOmniaMeasurementSql() {
        ReandJsonfromOmniaDb jsonParserFactory = new ReandJsonfromOmniaDb("c:/OmniaInterface2019/Javatest/Jatri_measurement.json",true);
        OmniaMeasurementListDataLevel omniaMeasurementFunctions = new OmniaMeasurementListDataLevel();
        omniaMeasurementFunctions.setRequestOrigin(SwarcoEnumerations.RequestOriginType.FROMJSONTOMYSQL);
        //ConnectionType {MYSQL_LOCAL_JATRI2,SQLSERVER_LOCAL_JOMNIATEST}
        int iRet=omniaMeasurementFunctions.MakeConnection(SwarcoEnumerations.ConnectionType.MYSQL_LOCAL_JATRI2);
   //     int iRet=omniaMeasurementFunctions.MakeConnection(SwarcoEnumerations.ConnectionType.SQLSERVER_LOCAL_JOMNIATEST);
        if (iRet!=1) {
            logger.info("Ei kantayhteytt� lopetetaan");
            System.exit(0);
        }
        try (JsonReader jsonReader = jsonParserFactory.getJsonReader()) {
            Gson myGson = new Gson();
            JsonParser jsonParser = new JsonParser();
            JsonArray userArray =  jsonParser.parse(jsonReader).getAsJsonArray();
            List<OmniaMeasurementData> OmniaMeasurementDataUnits = Collections.synchronizedList(new LinkedList<OmniaMeasurementData>());
            for (JsonElement aUser : userArray ){
                OmniaMeasurementData aOmniaMeasurement = myGson.fromJson(aUser, OmniaMeasurementData.class);
                OmniaMeasurementDataUnits.add(aOmniaMeasurement);
            }
            logger.info("OmniaMeasurementDataUnits.size()= " + OmniaMeasurementDataUnits.size());
            for (int i = 0; i < OmniaMeasurementDataUnits.size(); i++) {
                OmniaMeasurementData aOmniaMeasurementData =OmniaMeasurementDataUnits.get(i);
                logger.info("i,aOmniaMeasurementData.toString() = "+ i +"," + aOmniaMeasurementData.toString());
                iRet= OmniaMeasurementListDataLevel.AddNewOmniaMeasurementData(aOmniaMeasurementData);
                logger.info("iRet = " + iRet);
                if (iRet!=1) {
                    logger.info("Unsuccesfull OmniaMeasuremenData insert iRet = " + iRet);
                    //     System.exit(1);
                }
            }
        } catch (FileNotFoundException e) {
            logger.info(" FileNotFoundException");
            e.printStackTrace();
        } catch (IOException e) {
            logger.info(" IOException");
            e.printStackTrace();
        }
    }
    private static void JsonOmniaIntersectionSql() {
        ReandJsonfromOmniaDb jsonParserFactory = new ReandJsonfromOmniaDb("c:/OmniaInterface2019/JavaTest/Jatri_Intersection.json",true);
        OmniaIntersectionListDataLevel omniaIntersectionFunctions = new OmniaIntersectionListDataLevel();
        omniaIntersectionFunctions.setRequestOrigin(SwarcoEnumerations.RequestOriginType.FROMJSONTOMYSQL);
        int iRet=omniaIntersectionFunctions.MakeConnection(SwarcoEnumerations.ConnectionType.MYSQL_LOCAL_JATRI2);
        if (iRet!=1) {
            logger.info("Ei kantayhteytt� lopetetaan");
            System.exit(0);
        }
        try (JsonReader jsonReader = jsonParserFactory.getJsonReader()) {
            Gson myGson = new Gson();
            JsonParser jsonParser = new JsonParser();
            JsonArray userArray =  jsonParser.parse(jsonReader).getAsJsonArray();
            List<OmniaIntersectionData> OmniaIntersectionDataUnits = Collections.synchronizedList(new LinkedList<OmniaIntersectionData>());
            for (JsonElement aUser : userArray ){
                OmniaIntersectionData aOmniaIntersection = myGson.fromJson(aUser, OmniaIntersectionData.class);
                OmniaIntersectionDataUnits.add(aOmniaIntersection);
            }
            logger.debug("OmniaIntersectionDataUnits.size()= " + OmniaIntersectionDataUnits.size());
            for (int i = 0; i < OmniaIntersectionDataUnits.size(); i++) {
                OmniaIntersectionData aOmniaIntersectionData =OmniaIntersectionDataUnits.get(i);
                logger.info("i,aOmniaIntersectionData.toString() = "+ i +"," + aOmniaIntersectionData.toString());
                OmniaIntersectionListDataLevel oi = new OmniaIntersectionListDataLevel();
                iRet= oi.AddNewOmniaIntersectionData(aOmniaIntersectionData);
                logger.info("iRet = " + iRet);
                if (iRet!=1) {
                    logger.info("Unsuccesfull OmniaIntersectionData insert iRet = " + iRet);
                    //     System.exit(1);
                }
            }
        } catch (FileNotFoundException e) {
            logger.info(" FileNotFoundException");
            e.printStackTrace();
        } catch (IOException e) {
            logger.info(" IOException");
            e.printStackTrace();
        }
    }
    private static void JsonGetIntersectionSqlFromOmniaDataBase() {
    }
}



