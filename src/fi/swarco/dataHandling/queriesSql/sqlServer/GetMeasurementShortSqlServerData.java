// GetMeasurementShortSqlServerData
package fi.swarco.dataHandling.queriesSql.sqlServer;
import org.apache.log4j.Logger;
public class GetMeasurementShortSqlServerData {
    private static Logger logger = Logger.getLogger(GetMeasurementShortSqlServerData.class.getName());
    private String statement="";
    public String getStatement() {
        buildStatement ();
        return statement;
    }
    private void  buildStatement () {
        statement =  " exec dbo.TRPX_GetMeasurementDataShortSql  ";
        statement =	statement +  "?,";
        statement =	statement +  "?,";
        statement =	statement  + "?;";
    }
}
