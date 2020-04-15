package fi.swarco.dataHandling.oldDataHandling;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.dataHandling.pojos.XoLdPojos.DetectorMeasurements;
import fi.swarco.messageHandling.ParameterWrapper;
import org.apache.log4j.Logger;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import static fi.swarco.CONSTANT.*;
public class MakeResponseToCustomer {
    static Logger logger = Logger.getLogger(MakeResponseToCustomer.class.getName());
    private String jsonString = NO_VALUE;
    public String getJsonString() {
        return jsonString;
    }
    public void setJsonString(String pJsonString) {
        jsonString = pJsonString;
    }
    private ParameterWrapper paramWrapper = new ParameterWrapper();
    public ParameterWrapper getParameterWrapper() {
        return paramWrapper;
    }
    public void setParameterWrapper(ParameterWrapper pParameterWrapper) {
        paramWrapper = pParameterWrapper;
    }
    private DetectorMeasurementsDataLevel dmdl = new DetectorMeasurementsDataLevel();
    public int MakeSendResponseOperations() {
        int iRet = dmdl.MakeConnection(SwarcoEnumerations.ConnectionType.MYSQL_LOCAL_JATRI2);
        if (iRet != DATABASE_CONNECTION_OK) {
            logger.info("Ei kantayhteytt� lopetetaan");
            return OMNIA_DATA_PICK_NOT_OK;
        }
        String strWhere = dmdl.MakeWhereCurrentState(getParameterWrapper());
        if ((strWhere.equals(NO_VALUE))) {
            return OMNIA_DATA_PICK_NOT_OK;   // RETHINK
        }
        iRet = dmdl.DetectorMeasurementsDataListWhere(strWhere);
        if (iRet != 1) {
            logger.info("Haku epäonnistui lopetetaan");
            return OMNIA_DATA_PICK_NOT_OK;
        }
        List<DetectorMeasurements> DmUnits = Collections.synchronizedList(new LinkedList<DetectorMeasurements>());
        DmUnits = dmdl.GetDetectorMeasurementsDataList();
        String strJson = dmdl.MakeJsonString(DmUnits);
        if (strJson.equals(NO_VALUE)) {
            logger.info("ei tulosta!");
            return OMNIA_DATA_PICK_NOT_OK;
        }
        setJsonString(strJson);
        return INT_RET_OK;
    }
}
