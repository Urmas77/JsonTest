package fi.swarco.dataHandling.queriesSql.sqlServer;
import org.apache.log4j.Logger;
public class GetPermanentSqlServerData {
    private static Logger logger = Logger.getLogger(GetPermanentSqlServerData.class.getName());
    private String statement = "";

    public String getStatement() {
        buildStatement();
        return statement;
    }

    private void buildStatement() {
        statement = " exec dbo.TRPX_GetPermanentDataSql ";
        statement = statement + "?,";
        statement = statement + "?,";
        statement = statement + "?;";
    }
}
//SQL = SQL + plngIntersectionId + ",";
//SQL = SQL + plngControllerId +  ",";
//SQL = SQL + "'" + pstrTimestamp + "')";
