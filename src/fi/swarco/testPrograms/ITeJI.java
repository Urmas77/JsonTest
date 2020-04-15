package fi.swarco.testPrograms;
import fi.swarco.serviceOperations.RandomString;
import org.apache.log4j.Logger;
import java.security.SecureRandom;
import static fi.swarco.CONSTANT.NO_VALUE;
//import org.influxdb.InfluxDBTestJI;
public class ITeJI {
    static Logger logger = Logger.getLogger(ITeJI.class.getName());
    public static void main(String[] args) throws Exception {
       /* InfluxDBTestJI it= new  InfluxDBTestJI();
        logger.info("ITeJI end ires= ");
        logger.info("ITeJI ITeJI.class.getName() = "+ ITeJI.class.getName());
        logger.info("start jatri jatri"); */
 // 1. create new serie name
 // 2. check is it on use
 // 3. if not use else goto 1
      /*  it.setUp1();
        it.WriteSerieName("YOENJIF4W1X08JFT");
       int iRet=  it.IsSerieNameUsed("YOENJIF4W1X08JFT");
        iRet=  it.IsSerieNameUsed("YOENJIF4W1XaaaFT");
        //YOENJIF4W1X08JFT
        logger.info("after jatri jatri"); */
        String strHelp1;
        RandomString rd = new RandomString(16,new SecureRandom());
         strHelp1 = rd.nextString();
        for (int i = 1; i < 2200; i++) {
           // logger.info("i =" + i +" # " + rd.nextString());
            strHelp1 =rd.nextString();
            if (strHelp1.startsWith("0")) {
             //   logger.info("bef strHelp1 = "+strHelp1);
                strHelp1 = "A" + strHelp1.substring(1,strHelp1.length());
             //   logger.info("aft strHelp1 = "+strHelp1);
            } else if (strHelp1.startsWith("1")) {
               // logger.info("bef strHelp1 = "+strHelp1);
                strHelp1 = "B" + strHelp1.substring(1,strHelp1.length());
              //logger.info("aft strHelp1 = "+strHelp1);
            } else if (strHelp1.startsWith("2")) {
              //logger.info("bef strHelp1 = "+strHelp1);
                strHelp1 = "C" + strHelp1.substring(1,strHelp1.length());
              //logger.info("aft strHelp1 = "+strHelp1);
            } else if  (strHelp1.startsWith("3")) {
             // logger.info("bef strHelp1 = "+strHelp1);
                strHelp1 = "D" + strHelp1.substring(1,strHelp1.length());
             // logger.info("aft strHelp1 = "+strHelp1);
            } else if  (strHelp1.startsWith("4")) {
             // logger.info("bef strHelp1 = "+strHelp1);
                strHelp1 = "E" + strHelp1.substring(1,strHelp1.length());
            //  logger.info("aft strHelp1 = "+strHelp1);
            } else if  (strHelp1.startsWith("5")) {
              //logger.info("bef strHelp1 = "+strHelp1);
                strHelp1 = "F" + strHelp1.substring(1,strHelp1.length());
             // logger.info("aft strHelp1 = "+strHelp1);
            } else if  (strHelp1.startsWith("6")) {
               //Logger.info("bef strHelp1 = "+strHelp1);
                strHelp1 = "G" + strHelp1.substring(1,strHelp1.length());
              //  logger.info("aft strHelp1 = "+strHelp1);
            } else if  (strHelp1.startsWith("7")) {
              //  logger.info("bef strHelp1 = "+strHelp1);
                strHelp1 = "H" + strHelp1.substring(1,strHelp1.length());
              //  logger.info("aft strHelp1 = "+strHelp1);
            } else if  (strHelp1.startsWith("8")) {
             //   logger.info("bef strHelp1 = "+strHelp1);
                strHelp1 = "I" + strHelp1.substring(1,strHelp1.length());
             //   logger.info("aft strHelp1 = "+strHelp1);
            } else if  (strHelp1.startsWith("9")) {
             //   logger.info("bef strHelp1 = "+strHelp1);
                strHelp1 = "K" + strHelp1.substring(1,strHelp1.length());
            //    logger.info("aft strHelp1 = "+strHelp1);
            }
            logger.info("insert into TRPX_SerieName (SerieCounter,SerieName) values ("+i+",'"+strHelp1+"');");
        }
        //  logger.info("strHelp1 = " +strHelp1);
       // rd.RandomString(16,new SecureRandom());
       // new SecureRandom(), easy);
        //
        //it.testQuery();
       // 'Example1,omniacode=2,intersectionid=5555555,controllerid=66666666,detectorid=12345,detectorexternalcode='DET1341'vehiclecount =567,meanvehiclespeed = 36.89,occupancyprocent=11.33,accurancy 99.1,measurementtime =1489096808000i': missing field key
        //it.jisWriteMultipleStringDataLines();
        // it.WriteToSwarcoExampleTimeSerie3();
         //it.jatriWrite();
        //int iRet =it.jisWriteSerialLine("6789","567891234","bbbz1","777" );
       // it.jisWriteMultipleStringDataLines();
    }
    public  String  GetNewSerieName() {
        RandomString rd = new RandomString(16,new SecureRandom());
        String strHelp1 = rd.nextString();
   //     it.setUp1();
// read from name serie
        return NO_VALUE;
    }
}
