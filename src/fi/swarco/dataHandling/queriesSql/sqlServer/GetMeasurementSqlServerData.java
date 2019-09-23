package fi.swarco.dataHandling.queriesSql.sqlServer;
import org.apache.log4j.Logger;
public class GetMeasurementSqlServerData {
    private static Logger logger = Logger.getLogger(GetMeasurementSqlServerData.class.getName());
    private String statement="";
    public String getStatement() {
        buildStatement ();
        return statement;
    }
    private void  buildStatement () {
        statement =  " exec dbo.TRPX_GetMeasurementDataSql  ";
        statement =	statement +  "?,";
        statement =	statement +  "?,";
        statement =	statement  + "?;";
    }
}
  //SQL = SQL + plngIntersectionId + ",";
  //SQL = SQL + plngControllerId +  ",";
  //SQL = SQL + "'" + pstrTimestamp + "')";
