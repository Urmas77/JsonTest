package fi.swarco.dataHandling.queriesSql.mySQL;
import org.apache.log4j.Logger;
public class SelectIntersectionDataMySqlWhere {
    private static Logger logger = Logger.getLogger(SelectIntersectionDataMySqlWhere.class.getName());
    private String statement="";
    public String getStatement() {
        buildStatement ();
        return statement; }

    private void  buildStatement () {
        statement = "select ";
        statement = statement + "OmniaCode,";
        statement = statement + "ifnull(OmniaName,'novalue'),";
        statement = statement + "OmniaPublicationStatus ,";
        statement = statement + "IntersectionID ,";
        statement = statement + "ifnull(IntersectionDescription,'novalue') ,";
        statement = statement + "IntersectionAreaId ,";
        statement = statement + "ifnull(IntersectionExternalCode,'novalue') ,";
        statement = statement + "ifnull(InterSectionDataPreviousUpdate,'novalue'),";
        statement = statement + "ControllerId,";
        statement = statement + "ifnull(ControllerDescription ,'novalue'),";
        statement = statement + "ifnull(ControllerExternalCode,'novalue') ,";
        statement = statement + "ifnull(ControllerDataPreviousUpdate,'novalue')";
        statement = statement + " from OmniaIntersectionData ";
        statement = statement + " where OmniaCode= ? and ";
        statement = statement + "IntersectionID = ? and ";
        statement = statement + "ControllerId = ?; ";
        logger.info("statement = " + statement);
    }
}