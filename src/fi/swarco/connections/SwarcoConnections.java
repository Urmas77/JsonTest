package fi.swarco.connections;
import java.sql.*;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.properties.JSwarcoproperties;
import fi.swarco.testPrograms.InfluxDBTestJI;
import org.apache.log4j.Logger;
import static fi.swarco.CONSTANT.*;
public class SwarcoConnections {
    static Logger logger = Logger.getLogger(SwarcoConnections.class.getName());
    static Connection curSqlCon;
    InfluxDBTestJI curInfluxDB;
    static SwarcoEnumerations.ConnectionType curSqlConnectionType = SwarcoEnumerations.ConnectionType.NOT_DEFINED;
    public Connection getSqlCon() {
        return curSqlCon;
    }
    public void setSqlCon(Connection pcurSqlCon) {
        curSqlCon = pcurSqlCon;
    }
    private JSwarcoproperties swarvop;
    protected int SeekProperties() {
        int iRet = 0;
        swarvop    = new JSwarcoproperties();
          iRet = swarvop.getSwarcoProperties();
          if (iRet != INT_RET_OK) {
             logger.info ("Ei saatu properteja");
             return iRet;
         }
        return INT_RET_OK;
    }
    public int MakeConnection(SwarcoEnumerations.ConnectionType enConType) {
        if (curSqlConnectionType.equals(enConType)) {
            if (curSqlCon != null) {
                try {
                    if (curSqlCon.isValid(0)) {
                        return INT_RET_OK;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        int iRet=INT_RET_OK;
        int maxRound = 5;
        int round = 0;
        iRet = SeekProperties();
        if (iRet != INT_RET_OK) {
            return iRet;
        }
        if (enConType == SwarcoEnumerations.ConnectionType.MYSQL_LOCAL_JATRI2) {
            iRet = MakeMySqlConnection();
            while ((round < maxRound) && (iRet != INT_RET_OK)) {
                logger.info("Round 1 inside while loop"); // debug
                round = round + 1;
                iRet = MakeMySqlConnection();
                if (iRet != INT_RET_OK) {
                    if (round == 1) {
                        logger.info("Round 1 wait 4 s iRet=" + iRet);
                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        logger.info("Round " + round + "  wait 30 s iRet=" + iRet);
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        if ((enConType == SwarcoEnumerations.ConnectionType.SQLSERVER_LOCAL_HELSINKI_OMNIVIEW) ||
            (enConType == SwarcoEnumerations.ConnectionType.SQLSERVER_LOCAL_HELSINKI) ||
            (enConType == SwarcoEnumerations.ConnectionType.SQLSERVER_LOCAL_lAHTI)) {
            iRet = MakeSqlServerConnectionCity(enConType);
            while ((round < maxRound) && (iRet != 1)) {
                logger.info("Round 1 inside while loop"); // debug
                round = round + 1;
                iRet = MakeSqlServerConnectionCity(enConType);
                if (iRet != INT_RET_OK) {
                    if (round == 1) {
                        logger.info("Round 1 wait 4 s iRet=" + iRet);
                        try {
                            Thread.sleep(4000);
                         } catch (InterruptedException e) {
                            e.printStackTrace();
                         }
                    } else {
                        logger.info("Round " + round + "  wait 30 s iRet=" + iRet);
                        try {
                            Thread.sleep(30000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (iRet == INT_RET_OK) {
                   logger.info("Saatiin SqlServer connectti");
                   return iRet;
                }
                logger.info("Ei saatu SqlServer connectia");
                if (round == maxRound) {
                    logger.info("**********************************************************************Ei connectia tapetaan prosessi");
                    System.exit(0); // prosessi starts again automatically in prosudction
                }
           }
       }
       logger.info("Saatiin SqlServer connectti");
       return iRet;
    }
    private int MakeSqlServerConnectionCity(SwarcoEnumerations.ConnectionType enConType) {
        ConWrapper cW = new ConWrapper();
        try {
            logger.info("Start 2");
            cW =swarvop.FillConnectionWrapper(enConType);
            logger.info(" enConType = " +enConType);
            if (curSqlCon == null) {   // only one RSQSErver connection/ program JIs 23.11 2020 klo 13.20
               logger.info("CurConnection is null make totally new connection!");
                //logger.info("not used Connected.swarvop.getSqlServerConnUrlWhole() = " + swarvop.getSqlServerConnUrlWhole());
                logger.info("cW.getConnUrlStart() = " + cW.getConnUrlStart());
                logger.info("cW.getDatabaseName() = " + cW.getDatabaseName());
                logger.info("cW.getDatabaseUserName() = " + cW.getDatabaseUserName());
                logger.info("cW.getDbPassword() = " + cW.getDbPassword());
//                String strHelp1 = prop.getProperty("SqlServerConnectionUrlStart") + ";";
 //               strHelp1 = strHelp1 + "database=" + getSqlServerdatabase() + ";";    // +";";
                String strHelp2 = cW.getConnUrlStart() +";" +"database=" + cW.getDatabaseName() +";";
                logger.info("strHelp2 = " + strHelp2);
                curSqlCon = DriverManager.getConnection(strHelp2, cW.getDatabaseUserName(), cW.getDbPassword());
               if (curSqlCon.isValid(0)) {
                  return INT_RET_OK;
               }
            } else if (!curSqlCon.isValid(0)) {
                logger.info("CurConnection is not valid make new one!");
                logger.info("cW.getConnUrlStart() = " + cW.getConnUrlStart());
                logger.info("cW.getDatabaseName() = " + cW.getDatabaseName());
                logger.info("cW.getDatabaseUserName() = " + cW.getDatabaseUserName());
                logger.info("cW.getDbPassword() = " + cW.getDbPassword());
//                String strHelp1 = prop.getProperty("SqlServerConnectionUrlStart") + ";";
                //               strHelp1 = strHelp1 + "database=" + getSqlServerdatabase() + ";";    // +";";
                String strHelp2 = cW.getConnUrlStart() +";" +"database=" + cW.getDatabaseName() +";";
                logger.info("strHelp2 = " + strHelp2);
                curSqlCon = DriverManager.getConnection(strHelp2, cW.getDatabaseUserName(), cW.getDbPassword());
             //  curSqlCon = DriverManager.getConnection(swarvop.getSqlServerConnUrlStart(), swarvop.getSqlServerdbuser(), swarvop.getSqlServerpassword());
                if (curSqlCon.isValid(0)) {
                   return INT_RET_OK;
                }
            } else if (curSqlCon.isValid(0)) {
                logger.info("CurConnection is valid !");
                return INT_RET_OK;
            }
            return INT_RET_NOT_OK;
        } catch (Exception e) {
            System.out.println(" catch 11");
            logger.info(e.toString());
            e.printStackTrace();
            return INT_RET_NOT_OK;
        }
    }
   private int MakeSqlServerConnection2() {
       try {
           logger.info("Start 2  555    5555 not here ");
           if (curSqlCon == null) {
               logger.info("CurConnection is null make totally new connection!");
  //              logger.info("not used Connected.swarvop.getSqlServerConnUrlWhole() = " + swarvop.getSqlServerConnUrlWhole());
  //             logger.info("swarvop.getSqlServerConnUrlStart() = " + swarvop.getSqlServerConnUrlStart());
  //              logger.info("swarvop.getSqlServerdatabase() = " + swarvop.getSqlServerdatabase());
  //              logger.info("swarvop.getSqlServerdbuser() = " + swarvop.getSqlServerdbuser());
  //              logger.info("swarvop.getSqlServerpassword() = " + swarvop.getSqlServerpassword());
               String strHelp1=swarvop.getSqlServerConnUrlStart() +";database=" + swarvop.getSqlServerdatabase() +";";
               swarvop.setSqlServerConnUrlStart(strHelp1);
    //            logger.info("with database swarvop.getSqlServerConnUrlStart() = " + swarvop.getSqlServerConnUrlStart());
               curSqlCon = DriverManager.getConnection(swarvop.getSqlServerConnUrlStart(), swarvop.getSqlServerdbuser(), swarvop.getSqlServerpassword());
                //
               if (curSqlCon.isValid(0)) {
                   return INT_RET_OK;
               }
          } else if (!curSqlCon.isValid(0)) {
               logger.info("CurConnection is not valid make new one!");
       //                 logger.info("not used swarvop.getSqlServerConnUrlWhole()) = " + swarvop.getSqlServerConnUrlWhole());
       //                 logger.info("swarvop.getSqlServerConnUrlStart() = " + swarvop.getSqlServerConnUrlStart());
      //                  logger.info("swarvop.getSqlServerdbuser() = " + swarvop.getSqlServerdbuser());
      //                  logger.info("swarvop.getSqlServerpassword() = " + swarvop.getSqlServerpassword());
               curSqlCon = DriverManager.getConnection(swarvop.getSqlServerConnUrlStart(), swarvop.getSqlServerdbuser(), swarvop.getSqlServerpassword());
               if (curSqlCon.isValid(0)) {
                   return INT_RET_OK;
               }
          } else if (curSqlCon.isValid(0)) {
              logger.info("CurConnection is valid !");
              return INT_RET_OK;
          }
          return INT_RET_NOT_OK;
       } catch (Exception e) {
          System.out.println(" catch 11");
          logger.info(e.toString());
          e.printStackTrace();
         return INT_RET_NOT_OK;
       }
   }
   private int MakeMySqlConnection() {
       ConWrapper cW = new ConWrapper();
       String strHelp1="";
       cW =swarvop.FillConnectionWrapper(SwarcoEnumerations.ConnectionType.MYSQL_LOCAL_JATRI2);
      logger.info("***moimmoi****");

       try {
           if (curSqlCon == null) {
               logger.info("CurConnection is null make totally new connection!");
               logger.info("cW.getConnUrlStart() = " + cW.getConnUrlStart());
               logger.info("cW.getDatabaseName() = " + cW.getDatabaseName());
               logger.info("cW.getDatabaseUserName() = " + cW.getDatabaseUserName());
               logger.info("cW.getDbPassword() = " + cW.getDbPassword());
               logger.info("cW.getServerTimeZone() = " + cW.getServerTimeZone());
               if (swarvop.getMySqlServerTimeZone().equals(TT_NOT_DEFINED)) {
//                   setMySqlConnUrlStartDbase(prop.getProperty("MySqlConnectionUrlStart") + prop.getProperty("MySqldatabase"));
                   strHelp1=   cW.getConnUrlStart() + cW.getDatabaseName();
                   curSqlCon = DriverManager.getConnection(strHelp1,
                                                           cW.getDatabaseUserName(),
                                                           cW.getDbPassword());
                //   curSqlCon = DriverManager.getConnection(swarvop.getMySqlConnUrlStartDbase(), swarvop.getMySqldbuser(), swarvop.getMySqlpassword());
                   if (curSqlCon.isValid(0)) {
                       return INT_RET_OK;
                   }
                   return INT_RET_NOT_OK;
               } else {
                   //String strHelp1 = swarvop.getMySqlConnUrlStartDbase() +
                   //        "?serverTimezone=" +
                   //        swarvop.getMySqlServerTimeZone();
                   strHelp1=   cW.getConnUrlStart() + cW.getDatabaseName();
                   strHelp1 = strHelp1  + "?serverTimezone=" + cW.getServerTimeZone();
                   logger.info("strHelp1 = " + strHelp1);
                   logger.info("cW.getDatabaseName() = " + cW.getDatabaseName());
                   logger.info("cW.getDatabaseUserName() = " + cW.getDatabaseUserName());
                   logger.info("cW.getDbPassword() = " + cW.getDbPassword());
                   curSqlCon = DriverManager.getConnection(strHelp1, cW.getDatabaseUserName(), cW.getDbPassword());
                   // curSqlCon = DriverManager.getConnection(strHelp1, swarvop.getMySqldbuser(), swarvop.getMySqlpassword());
                   // curSqlCon = DriverManager.getConnection(swarvop.getMySqlConnUrlStartDbase(), swarvop.getMySqldbuser(), swarvop.getMySqlpassword());
                   // String strHelp1 =   swarvop.getMySqlConnUrlStartDbase() +
                   //         "?serverTimezone="+     // Olde was ,server...
                   //         swarvop.getMySqlServerTimeZone() +
                   //         ",user=" +
                   //         swarvop.getMySqldbuser() +
                   //         ",password=" +
                   //         swarvop.getMySqlpassword();
                   //      String strHelp1 =   swarvop.getMySqlConnUrlStartDbase() + ",user=" +
                   //                             swarvop.getMySqldbuser() + ",password=" +
                   //                            swarvop.getMySqlpassword() +"?serverTimezone="+     // Olde was ,server...
                   //                            swarvop.getMySqlServerTimeZone();
                   //                      logger.info("strHelp1 = " + strHelp1);
//                       curSqlCon = DriverManager.getConnection(strHelp1);
                   if (curSqlCon.isValid(0)) {
                       return INT_RET_OK;
                   }
                   return INT_RET_NOT_OK;
               }
           } else {
               if (!curSqlCon.isValid(0)) {
                   logger.info("CurConnection is not valid make new one!");
                   //              logger.info("swarvop.getMySqlConnUrlStartDbase() = " + swarvop.getMySqlConnUrlStartDbase());
                   //     logger.info("swarvop.getMySqldbuser() = " + swarvop.getMySqldbuser());
                   //     logger.info("swarvop.getMySqlpassword() = " + swarvop.getMySqlpassword());
                   logger.info("cW.getConnUrlStart() = " + cW.getConnUrlStart());
                   logger.info("cW.getDatabaseName() = " + cW.getDatabaseName());
                   logger.info("cW.getDatabaseUserName() = " + cW.getDatabaseUserName());
                   logger.info("cW.getDbPassword() = " + cW.getDbPassword());
                   logger.info("cW.getServerTimeZone() = " + cW.getServerTimeZone());
                   if (swarvop.getMySqlServerTimeZone().equals(TT_NOT_DEFINED)) {
//                   setMySqlConnUrlStartDbase(prop.getProperty("MySqlConnectionUrlStart") + prop.getProperty("MySqldatabase"));
                       strHelp1 = cW.getConnUrlStart() + cW.getDatabaseName();
                       curSqlCon = DriverManager.getConnection(strHelp1,
                               cW.getDatabaseUserName(),
                               cW.getDbPassword());
                    } else {
                       //String strHelp1 = swarvop.getMySqlConnUrlStartDbase() +
                       //        "?serverTimezone=" +
                       //        swarvop.getMySqlServerTimeZone();
                       strHelp1 = cW.getConnUrlStart() + cW.getDatabaseName();
                       strHelp1 = strHelp1 + "?serverTimezone=" + cW.getServerTimeZone();
                       logger.info("strHelp1 = " + strHelp1);
                       logger.info("cW.getDatabaseName() = " + cW.getDatabaseName());
                       logger.info("cW.getDatabaseUserName() = " + cW.getDatabaseUserName());
                       logger.info("cW.getDbPassword() = " + cW.getDbPassword());
                       curSqlCon = DriverManager.getConnection(strHelp1, cW.getDatabaseUserName(), cW.getDbPassword());
                   }


                   //         curSqlCon = DriverManager.getConnection(swarvop.getMySqlConnUrlStartDbase(), swarvop.getMySqldbuser(), swarvop.getMySqlpassword());
                   if (curSqlCon.isValid(0)) {
                       return INT_RET_OK;
                   }
                   return INT_RET_NOT_OK;
               } else {
                   logger.info("CurConnection is valid return OK !");
                   return INT_RET_OK;
               }
           }
       } catch (Exception e) {
           System.out.println(" catch 11");
           e.printStackTrace();
           return UNSUCCESSFUL_DATABASE_CONNECTION_OPERATION;
       }
   }
    //*********************************************************************************************
    public int MakeTimeSeriesConnection() {
        int iRet;
        try {
            iRet=SeekProperties();
            if (iRet!=1) {
               return iRet;
            }
      //      logger.debug("Start 1");
      //      logger.debug("swarvop.getInfluxConnUrlStart() = "+ swarvop.getInfluxConnUrlStart());
      //      logger.debug("swarvop.getInfluxdbuser() = " + swarvop.getInfluxdbuser() );
      //      logger.debug("swarvop.getInfluxpassword() =" + swarvop.getInfluxpassword() );
           InfluxDBTestJI it = new  InfluxDBTestJI();
           it.setInfluxConnUrlStart(swarvop.getInfluxConnUrlStart());
           it.setInfluxdbuser(swarvop.getInfluxdbuser());
           it.setInfluxpassword(swarvop.getInfluxpassword());
           it.setUp1();
           curInfluxDB =it;
        } catch(Exception e) {
           logger.info(" influx catch 11");
           e.printStackTrace();
        }
        return INT_RET_OK;
    }
}



