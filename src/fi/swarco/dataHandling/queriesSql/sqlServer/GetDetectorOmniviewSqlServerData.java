package fi.swarco.dataHandling.queriesSql.sqlServer;

import org.apache.log4j.Logger;

public class GetDetectorOmniviewSqlServerData {
    private static Logger logger = Logger.getLogger(GetDetectorSqlServerData.class.getName());
    private String statement="";
    public String getStatement() {
        buildStatement ();
        return statement;
    }
    private void  buildStatement () {
        statement =  " exec dbo.TRPX_GetDetectorOmniviewDataSql  ";
        statement =	statement +  "?,";
        statement =	statement  + "?,";
        statement =	statement  + "?;";
    }
}