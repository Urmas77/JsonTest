package fi.swarco.dataHandling.queriesSql.sqlServer;
import org.apache.log4j.Logger;
public class GetIntersectionSqlServerData {
    private static Logger logger = Logger.getLogger(GetIntersectionSqlServerData.class.getName());
    private String statement="";
    public String getStatement() {
        buildStatement ();
        return statement;
    }
    private void  buildStatement () {
        statement =  " exec dbo.TRPX_GetIntersectionDataSql  ";
        statement =	statement +  "?,";
        statement =	statement +  "?,";
        statement =	statement  + "?;";
    }
}
