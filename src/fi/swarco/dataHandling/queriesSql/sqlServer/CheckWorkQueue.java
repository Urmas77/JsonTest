package fi.swarco.dataHandling.queriesSql.sqlServer;
import org.apache.log4j.Logger;
public class CheckWorkQueue {
    private static Logger logger = Logger.getLogger(GetControllerSqlServerData.class.getName());
    private String statement="";
    public String getStatement() {
        buildStatement ();
        return statement;
    }
    private void  buildStatement () {
        statement =  " exec dbo.TRPX_CheckWorkQueue2;  ";
    }
}
