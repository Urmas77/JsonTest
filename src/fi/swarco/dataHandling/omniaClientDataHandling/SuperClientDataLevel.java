package fi.swarco.dataHandling.omniaClientDataHandling;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.connections.SwarcoConnections;
import fi.swarco.dataHandling.pojos.SuperData;
import fi.swarco.dataHandling.queriesSql.sqlServer.SuperDataSqlServerSelect;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import static fi.swarco.CONSTANT.DATABASE_CONNECTION_OK;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import static fi.swarco.CONSTANT.*;
    public class SuperClientDataLevel {
        private static Logger logger = Logger.getLogger(SuperClientDataLevel.class.getName());
        List<SuperData> SuperDataUnits = Collections.synchronizedList(new LinkedList<SuperData>());  // ????
        static Connection gSqlCon;
        private SwarcoEnumerations.ConnectionType SqlConnectionType=SwarcoEnumerations.ConnectionType.NOT_DEFINED;
        SwarcoEnumerations.RequestOriginType requestOrigin= SwarcoEnumerations.RequestOriginType.NORMALROAD;
        private SuperData foundRec = new SuperData();
        public SuperData getFoundRec() { return foundRec;}
        public  void setFoundRec(SuperData pFoundRec) {foundRec=pFoundRec;}
        public void setRequestOrigin(SwarcoEnumerations.RequestOriginType prequestOrigin) {
            requestOrigin=prequestOrigin;
        }
        public  int MakeConnection(SwarcoEnumerations.ConnectionType pSqlCon) {
            SwarcoConnections vg = new SwarcoConnections();
            logger.info("pSqlCon = "+ pSqlCon);
            int iRet = vg.MakeConnection(pSqlCon);
            if (iRet!=1) {
                return iRet;
            }
            SqlConnectionType=pSqlCon;
            logger.info("SqlConnectionType = " + SqlConnectionType);
            gSqlCon = vg.getSqlCon();
            return DATABASE_CONNECTION_OK;
        }
        public int OmniaSuperDataList () throws SQLException {
            SuperDataUnits.clear();
            String SQL="";
            java.sql.PreparedStatement stmt;
            logger.info(" SqlConnectionType =" + SqlConnectionType);
            logger.info("Start ");
            SuperDataSqlServerSelect st = new SuperDataSqlServerSelect();
            SQL =st.getStatement() ;
            logger.info("SqlConnectionTypeyyyy= "+ SqlConnectionType);
            logger.info("SQL = " +SQL);
            SuperData ce;
            try {
                stmt = gSqlCon.prepareStatement(SQL);
                ResultSet rs;
                rs = stmt.executeQuery();
                logger.info(" rs.getFetchSize() = " + rs.getFetchSize());
                while (rs.next()) {
                    ce= new SuperData();
                    ce.setOmniaCode(rs.getLong(1));
                    ce.setIntersectionId(rs.getLong(4));
                    ce.setControllerId(rs.getLong(9));
                    ce.setDetectorId(rs.getLong(10));
                    SuperDataUnits.add(ce);
                }
                stmt.close();
                rs.close();
                if (SuperDataUnits.isEmpty()==true) {
                    ce= new SuperData();
                    ce.MakeEmptyElement();
                    SuperDataUnits.add(ce);
                    return -1;
                }
                logger.info("bef ret iRet OK");
                return INT_RET_OK;
            } catch (SQLException e) {
                e.printStackTrace();
                logger.info(ExceptionUtils.getRootCauseMessage(e));
                logger.info(ExceptionUtils.getFullStackTrace(e));
                ce= new SuperData();
                ce.MakeEmptyElement();
                SuperDataUnits.add(ce);
                gSqlCon.close();
                return -1;
            }
        }
        public SuperData GetSuperUsingDetectorId(int pDetectorId) {
            logger.debug("pDetectorId =" + pDetectorId);
            SuperData ff = new SuperData();
            logger.info("SuperDataUnits.size()= " +SuperDataUnits.size());
            for (int i = 0; i < SuperDataUnits.size(); i++) {
                ff=SuperDataUnits.get(i);
                logger.debug("ff.toString() =" + ff.toString());
                if (ff.getDetectorId()==(pDetectorId)) {
                    logger.debug("löytyi ff.getDetectorId =" + ff.getDetectorId());
                    logger.debug("löytyi");
                    return ff;
                }
            }
            ff= new SuperData();
            ff.MakeEmptyElement();
            return ff;
        }
        public  List<SuperData> GetOmniaSuperDataList()  {
            SuperData cc = new SuperData();
            if (SuperDataUnits.isEmpty()==true) {
                cc= new SuperData();
                cc.MakeEmptyElement();
                SuperDataUnits.add(cc);
            }
            return SuperDataUnits;
        }
    }








