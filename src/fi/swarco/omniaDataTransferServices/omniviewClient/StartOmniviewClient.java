package fi.swarco.omniaDataTransferServices.omniviewClient;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.dataHandling.MeasurementTaskWorkHandling;
import fi.swarco.dataHandling.pojos.TRPXMeasurementTaskWorkData;
import fi.swarco.omniaDataTransferServices.omniaClient.StartOmniaClient;
import fi.swarco.omniaDataTransferServices.omniaClient.StartWrapper;
import org.apache.log4j.Logger;
import java.sql.SQLException;
import static fi.swarco.CONSTANT.*;
import static fi.swarco.CONSTANT.INT_RET_OK;
import static fi.swarco.omniaDataTransferServices.omniviewClient.OmniviewClient.getSqlServerConnectionType;
public class StartOmniviewClient {
    private static Logger logger = Logger.getLogger(StartOmniaClient.class.getName());
    public StartOmniviewClient() {
        StartWrapper stw = new StartWrapper();
        stw.MakeEmptyElement();
        SwarcoEnumerations.ConnectionType  oConnType;
        oConnType=getSqlServerConnectionType();
        MeasurementTaskWorkHandling mth =new MeasurementTaskWorkHandling();
        int iRet = mth.MakeConnection(oConnType);
        if (iRet != INT_RET_OK) {
            logger.info("Ei kantayhteytt� lopetetaan");
            System.exit(1);
        }
    }
    public int ClearReCreateWorkTable() throws SQLException {
        StartWrapper stw = new StartWrapper();
        stw.MakeEmptyElement();
        MeasurementTaskWorkHandling mth = new MeasurementTaskWorkHandling();
        SwarcoEnumerations.ConnectionType oConnType;
        oConnType = getSqlServerConnectionType();
        int iRet = mth.MakeConnection(oConnType);
        if (iRet != INT_RET_OK) {
            logger.info("Ei kantayhteytt� lopetetaan");
            return OMNIA_DATA_PICK_NOT_OK;
        }
        logger.info("Moi Jatri**************************");
        iRet = mth.MakeStartOmniviewClientDbOperations();
        if (iRet < 0) {
            logger.info(" MakeStartOmniviewClientDbOperations ");
        }
        return iRet;
    }
}