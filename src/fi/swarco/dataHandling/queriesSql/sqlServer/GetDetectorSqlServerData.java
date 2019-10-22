package fi.swarco.dataHandling.queriesSql.sqlServer;
import org.apache.log4j.Logger;
public class GetDetectorSqlServerData {
    private static Logger logger = Logger.getLogger(GetDetectorSqlServerData.class.getName());
    private String statement="";
    public String getStatement() {
        buildStatement ();
        return statement;
    }
    private void  buildStatement () {
        statement =  " exec dbo.TRPX_GetDetectorDataSql  ";
        statement =	statement +  "?,";
        statement =	statement  + "?;";
    }
}
