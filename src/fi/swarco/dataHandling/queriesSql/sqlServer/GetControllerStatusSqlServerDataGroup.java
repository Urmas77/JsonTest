package fi.swarco.dataHandling.queriesSql.sqlServer;
import org.apache.log4j.Logger;
public class GetControllerStatusSqlServerDataGroup {
    private static Logger logger = Logger.getLogger(GetControllerStatusSqlServerDataGroup.class.getName());
    private String statement="";
    public String getStatement() {
        buildStatement ();
        return statement;
    }
    private void  buildStatement () {
        statement =  " exec TRPX_GetControllerStatusDataSqlGroup ";
        statement =	statement +  "?;";
    }
}
