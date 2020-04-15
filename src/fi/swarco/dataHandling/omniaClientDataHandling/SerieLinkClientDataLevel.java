package fi.swarco.dataHandling.omniaClientDataHandling;
        import fi.swarco.CONSTANT;
        import fi.swarco.SwarcoEnumerations;
        import fi.swarco.connections.SwarcoConnections;
        import fi.swarco.dataHandling.pojos.SerieLink;
        import fi.swarco.dataHandling.pojos.influx.InfluxSeriesName;
        import fi.swarco.dataHandling.queriesSql.sqlServer.GetNoNameSerieLinkSqlServerData;
        import fi.swarco.dataHandling.queriesSql.sqlServer.InsertSeriesLinkDataSqlServer;
        import fi.swarco.dataHandling.queriesSql.sqlServer.SerieLinkSqlServerSelect;
        import fi.swarco.influxoperations.InfluxDBOwn;
        import fi.swarco.properties.JSwarcoproperties;
        import fi.swarco.serviceOperations.RandomString;
        import org.apache.commons.lang.exception.ExceptionUtils;
        import org.apache.log4j.Logger;
        import org.influxdb.dto.QueryResult;
        import java.io.IOException;
        import java.security.SecureRandom;
        import java.sql.Connection;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import static fi.swarco.CONSTANT.DATABASE_CONNECTION_OK;
        import java.util.Collections;
        import java.util.LinkedList;
        import java.util.List;
        import static fi.swarco.CONSTANT.*;
        import static fi.swarco.SwarcoEnumerations.ConnectionType.INFLUX_LOCAL;
        import static fi.swarco.SwarcoEnumerations.ConnectionType.SQLSERVER_LOCAL_JOMNIATEST;
    public class SerieLinkClientDataLevel {
    private static Logger logger = Logger.getLogger(SerieLinkClientDataLevel.class.getName());
    List<SerieLink> DataUnits = Collections.synchronizedList(new LinkedList<SerieLink>());  // ????
    public List<SerieLink> getSerieLinkUnits() {
        return DataUnits;
    }
    public void setSerieLinkUnits(List<SerieLink> pSerieLinkUnits) {
        this.DataUnits = pSerieLinkUnits;
    }
    static Connection gSqlCon;
    private SwarcoEnumerations.ConnectionType SqlConnectionType=SwarcoEnumerations.ConnectionType.NOT_DEFINED;
    SwarcoEnumerations.RequestOriginType requestOrigin= SwarcoEnumerations.RequestOriginType.NORMALROAD;
    private SerieLink foundRec = new SerieLink();
    public SerieLink getFoundRec() { return foundRec;}
    public  void setFoundRec(SerieLink pFoundRec) {foundRec=pFoundRec;}
    public void setRequestOrigin(SwarcoEnumerations.RequestOriginType prequestOrigin) {
        requestOrigin=prequestOrigin;
    }
    int iRet;
    InfluxDBOwn ts = new InfluxDBOwn();
    public  int MakeConnection(SwarcoEnumerations.ConnectionType pSqlCon) {
        SwarcoConnections vg = new SwarcoConnections();
        logger.info("pSqlCon = "+ pSqlCon);
        iRet = vg.MakeConnection(pSqlCon);
        if (iRet!=INT_RET_OK) {
            return iRet;
        }
        SqlConnectionType=pSqlCon;
        logger.info("SqlConnectionType = " + SqlConnectionType);
        gSqlCon = vg.getSqlCon();
        return DATABASE_CONNECTION_OK;
    }
    JSwarcoproperties swarcop;
    public  int MakeConnection2(SwarcoEnumerations.ConnectionType pSqlCon1,SwarcoEnumerations.ConnectionType pSqlCon2) {
        swarcop = new JSwarcoproperties();
        SwarcoConnections vg = new SwarcoConnections();
        try {
            iRet = swarcop.getSwarcoProperties();
            if (iRet != 1) {
                logger.info ("Ei saatu properteja");
                return iRet;
            }
            if ((pSqlCon1.equals(INFLUX_LOCAL) || (pSqlCon2.equals(INFLUX_LOCAL)))) {
                ts.setInfluxConnUrlStart(swarcop.getInfluxConnUrlStart());
                ts.setInfluxdbuser(swarcop.getInfluxdbuser());
                ts.setInfluxpassword(swarcop.getInfluxdbuser());
                ts.setUp1();
            }
            if ((pSqlCon1.equals(SQLSERVER_LOCAL_JOMNIATEST) || (pSqlCon2.equals(SQLSERVER_LOCAL_JOMNIATEST)))) {
                int iRet = vg.MakeConnection(SQLSERVER_LOCAL_JOMNIATEST);
                if (iRet!=INT_RET_OK) {
                    return iRet;
                }
                SqlConnectionType=SQLSERVER_LOCAL_JOMNIATEST;
                logger.info("SqlConnectionType = " + SqlConnectionType);
                gSqlCon = vg.getSqlCon();
                return DATABASE_CONNECTION_OK;
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        return INT_RET_OK;
    }
    public int OmniaSerieLinkList () throws SQLException {
        DataUnits.clear();
        String SQL="";
        java.sql.PreparedStatement stmt;
        logger.info(" SqlConnectionType =" + SqlConnectionType);
        logger.info("Start ");
        SerieLinkSqlServerSelect st = new SerieLinkSqlServerSelect();
        SQL =st.getStatement() ;
        logger.info("SqlConnectionTypeyyyy= "+ SqlConnectionType);
        logger.info("SQL = " +SQL);
        SerieLink ce;
        try {
            stmt = gSqlCon.prepareStatement(SQL);
            ResultSet rs;
            rs = stmt.executeQuery();
            logger.info(" rs.getFetchSize() = " + rs.getFetchSize());
            while (rs.next()) {
                ce= new SerieLink();
                ce.setOmniaCode(rs.getLong(1));
                ce.setIntersectionId(rs.getLong(2));
                ce.setControllerId(rs.getLong(3));
                ce.setDetectorId(rs.getLong(4));
                ce.setSerieName(rs.getString(5));
                DataUnits.add(ce);
               logger.info("ce.toString() = "+ ce.toString());
                logger.info("ce.GetSerieLinkInfluxString() = "+ ce.GetSerieLinkInfluxString());
           }
            stmt.close();
            rs.close();
            if (DataUnits.isEmpty()==true) {
                ce= new SerieLink();
                ce.MakeEmptyElement();
                DataUnits.add(ce);
                return INT_RET_NOT_OK;
            }
            logger.info("bef ret iRet OK");
            return INT_RET_OK;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            ce= new SerieLink();
            ce.MakeEmptyElement();
            DataUnits.add(ce);
            gSqlCon.close();
            return INT_RET_NOT_OK;
        }
    }

    public List<SerieLink> GetNoSeriesLinkData(long pControllerId) throws SQLException {
        DataUnits.clear();
        String SQL;
        java.sql.PreparedStatement stmt;
        logger.info("Start ");
        GetNoNameSerieLinkSqlServerData st = new GetNoNameSerieLinkSqlServerData();
        SQL = st.getStatement();
        SerieLink ce;
        try {
            stmt = gSqlCon.prepareStatement(SQL);
            ResultSet rs;
            logger.info(" 1: pControllerId =" + pControllerId);
            stmt.setLong(1,pControllerId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                ce = new SerieLink();
                ce.setOmniaCode(rs.getLong(1));
                ce.setIntersectionId(rs.getLong(2));
                ce.setControllerId(rs.getLong(3));
                ce.setDetectorId(rs.getLong(4));
                ce.setSerieName(rs.getString(5));
                DataUnits.add(ce);
            }
            stmt.close();
            rs.close();
            if (DataUnits.isEmpty() == true) {
                ce = new SerieLink();
                ce.MakeEmptyElement();
                DataUnits.add(ce);
                return DataUnits ;
            }
            return DataUnits;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
            ce = new SerieLink();
            ce.MakeEmptyElement();
            DataUnits.add(ce);
            gSqlCon.close();
            return DataUnits;
        }
    }
    public int CreateNeededSerieNames() {
        SerieLink ce;
        for (int i = 0; i <DataUnits.size(); i++) {
            ce = DataUnits.get(i);
            iRet =AddNewSerieName(ce);
            if (iRet!=INT_RET_OK) {
                return SERIENAMES_CREATED_NOT_OK;
            }
        }
        return SERIENAMES_CREATED_OK;
    }
    public int AddNewSerieName(SerieLink pCe) {
        iRet = SERIENAME_IS_ON_USE;
        int iCount = 0;
        String serieNameCandidate = "";
        while (iRet == SERIENAME_IS_ON_USE) {
            serieNameCandidate = createNewSeriesName();
            iRet = IsSerieNameInUse(serieNameCandidate);
            iCount = iCount + 1;
            if (iCount > 5) {
                iRet = SERIENAME_ERROR;
                logger.info("Impossible to define new SerieName? iRet= " + iRet);
                return iRet;
            }
            pCe.setSerieName(serieNameCandidate);
            InfluxSeriesName Sn = new InfluxSeriesName();
            Sn.fromTimeSerieLinkToSerieName(pCe);
            iRet = AddNewSerieLink(pCe);
            if (iRet != INT_RET_OK) {
                logger.info("Unsuccessful SerieLink insert iRet =" + iRet);
                return iRet;
            }
            iRet = AddNewSerieNameToInflux(Sn);
            return iRet;
        }
        return iRet;
    }
    public SerieLink GetSerieLinkUsingDetectorId(int pDetectorId) {
        logger.info("pDetectorId =" + pDetectorId);
        SerieLink ff = new SerieLink();
        logger.info("DataUnits.size()= " +DataUnits.size());
        for (int i = 0; i < DataUnits.size(); i++) {
            ff=DataUnits.get(i);
            logger.info("ff.toString() =" + ff.toString());
            if (ff.getDetectorId()==(pDetectorId)) {
                logger.info("löytyi ff.getDetectorId =" + ff.getDetectorId());
                logger.info("löytyi");
                return ff;
            }
        }
        ff= new SerieLink();
        ff.MakeEmptyElement();
        return ff;
    }
    public int IsSerieNameInUse(String pSerieName) {
        int amountOfResultLines;
        int iRet = INT_RET_NOT_OK;
        logger.info("pSerieName =" + pSerieName);
        QueryResult retQuery = ts.readInfluxSeriesDataLine(swarcop.getInfluxDbName1(), pSerieName);
        while (!retQuery.getResults().isEmpty()) {
            for (QueryResult.Result result : retQuery.getResults()) {
                if (result.getSeries()==null) {
                   return SERIENAME_IS_FREE;
                } else
                   return SERIENAME_IS_ON_USE;
                }
               return SERIENAME_ERROR;
        }
        return iRet;
    }
    public String createNewSeriesName() {
        String strHelp1;
        RandomString rd = new RandomString(16, new SecureRandom());
        strHelp1 = rd.nextString();
        if (strHelp1.startsWith("0")) {
            strHelp1 = "A" + strHelp1.substring(1, strHelp1.length());
        } else if (strHelp1.startsWith("1")) {
            strHelp1 = "B" + strHelp1.substring(1, strHelp1.length());
        } else if (strHelp1.startsWith("2")) {
            strHelp1 = "C" + strHelp1.substring(1, strHelp1.length());
        } else if (strHelp1.startsWith("3")) {
            strHelp1 = "D" + strHelp1.substring(1, strHelp1.length());
        } else if (strHelp1.startsWith("4")) {
            strHelp1 = "E" + strHelp1.substring(1, strHelp1.length());
        } else if (strHelp1.startsWith("5")) {
            strHelp1 = "F" + strHelp1.substring(1, strHelp1.length());
        } else if (strHelp1.startsWith("6")) {
            strHelp1 = "G" + strHelp1.substring(1, strHelp1.length());
        } else if (strHelp1.startsWith("7")) {
            strHelp1 = "H" + strHelp1.substring(1, strHelp1.length());
        } else if (strHelp1.startsWith("8")) {
            strHelp1 = "I" + strHelp1.substring(1, strHelp1.length());
        } else if (strHelp1.startsWith("9")) {
            strHelp1 = "K" + strHelp1.substring(1, strHelp1.length());
        }
        return strHelp1;
    }
    public String  GetNewSeriesName() {
       String  seriesName=NO_VALUE;
       int iRet= SERIENAME_IS_ON_USE;
       while (iRet==SERIENAME_IS_ON_USE) {
           seriesName = createNewSeriesName();
           iRet = IsSerieNameInUse(seriesName);
       }
       if (iRet==SERIENAME_ERROR) {
           return NO_VALUE;
       }
       return seriesName;
    }
    public int AddNewSerieNameToInflux(InfluxSeriesName pSn ) {
        logger.info(" pSn.GetTimeSerieString() =" +  pSn.GetTimeSerieString());
        int iRet =ts.WriteSingleSerialLine2(swarcop.getInfluxDbName1(),pSn.GetTimeSerieString());
        return iRet;
    }
    public int AddNewSerieLink(SerieLink pSn) {
        int iRet;
        String SQL="";
        java.sql.PreparedStatement stmt=null;
        try {
            InsertSeriesLinkDataSqlServer   st = new InsertSeriesLinkDataSqlServer();
            SQL =st.getStatement();
            stmt = gSqlCon.prepareStatement(SQL);
            int pos=0;
            pos=1;
            stmt.setLong(pos,pSn.getOmniaCode());
            pos=pos+1;
            stmt.setLong(pos,pSn.getIntersectionId());
            pos=pos+1;
            stmt.setLong(pos,pSn.getControllerId());
            pos=pos+1;
            stmt.setLong(pos,pSn.getDetectorId());
            pos=pos+1;
            stmt.setString(pos,pSn.getSerieName());
            iRet = stmt.executeUpdate();
            stmt.close();
            if (iRet!=1) {
                iRet= CONSTANT.UNSUCCESSFUL_DATABASE_INSERT_OPERATION;
                return iRet;
            }
            return INT_RET_OK;
        } catch(Exception e) {
            e.printStackTrace();
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
         // RETHINK this should be work
            //   stmt.close();
         //   gSqlCon.close();
            return UNSUCCESSFUL_DATABASE_OPERATION;
        }
    }
    public  List<SerieLink> GetOmniaSerieLinkList()  {
        SerieLink cc = new SerieLink();
        if (DataUnits.isEmpty()==true) {
            cc= new SerieLink();
            cc.MakeEmptyElement();
            DataUnits.add(cc);
        }
        return DataUnits;
    }
    public int WriteSerieLinkDataToInflux(){
        InfluxDBOwn it  = new InfluxDBOwn();
        int iRet=INT_RET_OK;
        SerieLink ce;
        logger.info("DataUnits.size()= " + DataUnits.size());
        for (int i = 0; i < DataUnits.size(); i++) {
            ce=DataUnits.get(i);
            logger.info("ce.GetSerieLinkInfluxString() = " + ce.GetSerieLinkInfluxString());
            iRet=ts.WriteSwarcoLineFromString("swarco_test",ce.GetSerieLinkInfluxString());
            if  (iRet!=INT_RET_OK) {
                logger.info("unsuccessful influx write operation iRet = " + iRet);
                return iRet;
            }
        }
        return INT_RET_OK;
    }
}
