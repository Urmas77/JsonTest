package fi.swarco.controlandalarms;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.connections.SwarcoConnections;
import fi.swarco.dataHandling.queriesSql.sqlServer.CheckWorkQueue;
import org.apache.log4j.Logger;
import java.sql.SQLException;
import static fi.swarco.CONSTANT.*;
import java.sql.*;
public class AlarmHandling {
    private static Connection gSqlCon;
    private static SwarcoEnumerations.ConnectionType SqlConnectionType = SwarcoEnumerations.ConnectionType.NOT_DEFINED;
    private static Logger logger = Logger.getLogger(AlarmHandling.class.getName());
    public static int MakeConnection(SwarcoEnumerations.ConnectionType pSqlCon)  {
        SwarcoConnections vg = new SwarcoConnections();
        int iRet = vg.MakeConnection(pSqlCon);
        if (iRet != INT_RET_OK) {
            return iRet;
        }
        SqlConnectionType = pSqlCon;
        gSqlCon = vg.getSqlCon();
        return INT_RET_OK;
    }
    public int SendAlarm() throws SQLException{
        // send alarm email if it is needed JIs 10.3 20202
        String SQL ="";
        String strRet="";
        int iRet;
        CheckWorkQueue em = new CheckWorkQueue();
        java.sql.PreparedStatement stmt;
        SQL= em.getStatement();
        ResultSet rs;
        stmt=gSqlCon.prepareStatement(SQL);
        rs = stmt.executeQuery();
        logger.info(" rs.getFetchSize() = " + rs.getFetchSize());
        while (rs.next()) {
            strRet = rs.getString(1);
            logger.info("strRet= " + strRet);
        }
        stmt.close();
        rs.close();
        return INT_RET_OK;
    }
}
