package fi.swarco.dataHandling.queriesSql.sqlServer;
import org.apache.log4j.Logger;
public class GetIntersectionControllerSqlServerData {
    private static Logger logger = Logger.getLogger(GetIntersectionControllerSqlServerData.class.getName());
    private String statement="";
    public String getStatement() {
        buildStatement ();
        return statement;
    }
    private void  buildStatement () {
        statement =  " exec dbo.TRPX_GetIntersectionControllerDataSql  ";
        statement =	statement +  "?,";
        statement =	statement +  "?,";
        statement =	statement  + "?;";
    }
}
