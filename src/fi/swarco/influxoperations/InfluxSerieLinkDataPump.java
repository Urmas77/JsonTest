package fi.swarco.influxoperations;
import fi.swarco.dataHandling.omniaClientDataHandling.SerieLinkClientDataLevel;
import org.apache.log4j.Logger;
import java.util.List;
import static fi.swarco.CONSTANT.*;
import static fi.swarco.SwarcoEnumerations.ConnectionType.INFLUX_LOCAL;
import static fi.swarco.SwarcoEnumerations.ConnectionType.SQLSERVER_LOCAL_JOMNIATEST;
public class  InfluxSerieLinkDataPump {
    static Logger logger = Logger.getLogger(InfluxSerieLinkDataPump.class.getName());
    public static void main(String[] args) throws Exception {
        SerieLinkClientDataLevel it = new SerieLinkClientDataLevel();
        int iRet = it.MakeConnection2(SQLSERVER_LOCAL_JOMNIATEST,INFLUX_LOCAL);
        if (iRet!=DATABASE_CONNECTION_OK) {
            logger.info ("unsuccessful db connets iRet = "+ iRet);
            System.exit(1);
        }
        iRet= it.OmniaSerieLinkList();
        if (iRet!=INT_RET_OK) {
            logger.info ("error in Detectorlist searcH iRet = "+ iRet);
            System.exit(1);
        }
        iRet= it.WriteSerieLinkDataToInflux();
        if (iRet!=INT_RET_OK) {
            logger.info ("error in Detectorlist searcH iRet = "+ iRet);
            System.exit(1);
        }
    }
}
