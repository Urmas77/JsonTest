package fi.swarco.dataHandling.queriesSql.mySQL;
import org.apache.log4j.Logger;
public class InsertIntersectionDataMySql {
    private static Logger logger = Logger.getLogger(InsertMeasurementDataMySql.class.getName());
    private String statement = "";
    public String getStatement() {
        buildStatement();
        return statement;
    }
    private void buildStatement() {
        statement = "insert into OmniaIntersectionData (";
        statement = statement + "OmniaCode,";
        statement = statement + "OmniaName,";
        statement = statement + "OmniaPublicationStatus ,";
        statement = statement + "IntersectionID ,";
        statement = statement + "IntersectionDescription ,";
        statement = statement + "IntersectionAreaId ,";
        statement = statement + "IntersectionExternalCode ,";
        statement = statement + "InterSectionDataPreviousUpdate,";
        statement = statement + "ControllerId,";
        statement = statement + "ControllerDescription ,";
        statement = statement + "ControllerExternalCode ,";
        statement = statement + "ControllerDataPreviousUpdate)";
        statement = statement + "values ( ";
        statement = statement + "?,";
        statement = statement + "?,";
        statement = statement + "?,";
        statement = statement + "?,";
        statement = statement + "?,";
        statement = statement + "?,";
        statement = statement + "?,";
        statement = statement + "?,";
        statement = statement + "?,";
        statement = statement + "?,";
        statement = statement + "?,";
        statement = statement + "?);";
  //      logger.info("statement = " + statement);
    }
}