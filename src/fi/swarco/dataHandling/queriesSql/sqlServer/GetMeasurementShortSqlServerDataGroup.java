package fi.swarco.dataHandling.queriesSql.sqlServer;
import org.apache.log4j.Logger;
public class GetMeasurementShortSqlServerDataGroup  {
    private static Logger logger = Logger.getLogger(GetMeasurementShortSqlServerDataGroup.class.getName());
    private String statement="";
    public String getStatement() {
        buildStatement ();
        return statement;
    }
    private void  buildStatement () {
        statement =  " exec dbo.TRPX_GetMeasurementDataShortSqlGroup  ";
        statement =	statement +  "?;";
    }
}
