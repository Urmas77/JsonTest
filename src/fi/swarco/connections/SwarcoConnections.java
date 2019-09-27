package fi.swarco.connections;
import java.sql.*;
import fi.swarco.SwarcoEnumerations;
import fi.swarco.properties.JSwarcoproperties;
import org.apache.log4j.Logger;

import static fi.swarco.CONSTANT.*;

public class SwarcoConnections {
    static Logger logger = Logger.getLogger(SwarcoConnections.class.getName());
    static Connection curSqlCon;
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
         logger.info("inside seekproperties");
        swarvop    = new JSwarcoproperties();
          iRet = swarvop.getSwarcoProperties();
          if (iRet != INT_RET_OK) {
             logger.info ("Ei saatu properteja");
             return iRet;
         }
        return INT_RET_OK;
    }
    public int MakeConnection(SwarcoEnumerations.ConnectionType enConType) {
        //	logger.info("strConType = "+ strConType);
        //	logger.info("curSqlConnectionType = "+ curSqlConnectionType);
        if (curSqlConnectionType.equals(enConType)) {
            if (curSqlCon != null) {
                try {
                    if (curSqlCon.isValid(0)) {
                        logger.info("sqlconnect on validi curSqlConnectionType =" + curSqlConnectionType);
                        logger.info("curSqlCon.getMetaData() = " + curSqlCon.getMetaData());
                        return INT_RET_OK;
                    }
                } catch (SQLException e) {
                    //	    logger.info("sqlconnect ei ollut validicurSqlConnectionType =" + curSqlConnectionType);
                    e.printStackTrace();
                }
            }
        }
        int iRet = 1;
        int maxRound = 3;
        int round = 0;
        iRet = SeekProperties();
        if (iRet != INT_RET_OK) {
            return iRet;
        }
        if (enConType==SwarcoEnumerations.ConnectionType.SQLSERVER_LOCAL_JOMNIATEST) {
            iRet = MakeSqlServerConnection();
            while ((round < maxRound) && (iRet != 1)) {
                logger.info("Round 1 inside while loop"); // debug
                round = round + 1;
                iRet = MakeSqlServerConnection();
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
            }
            if (iRet != INT_RET_OK) {
                logger.info("Ei saatu SqlServer connectia");
                return iRet;
            }
            logger.info("Saatiin SqlServer connectti");
        } else if (enConType==SwarcoEnumerations.ConnectionType.MYSQL_LOCAL_JATRI2) {
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
                           // Thread.sleep(30000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            if (iRet !=INT_RET_OK) {
                logger.info("Ei saatu MySql connectia");
                return iRet;
            }
            logger.info("Saatiin MySql connectti");
        }
        return iRet;
    }
    private int MakeSqlServerConnection() {
        try {
            logger.info("Start 2");
            if (curSqlCon == null) {
                logger.info("CurConnection is null make totally new connection!");
                logger.info("not used Connected.swarvop.getSqlServerConnUrlWhole() = " + swarvop.getSqlServerConnUrlWhole());
               logger.info("swarvop.getSqlServerConnUrlStart() = " + swarvop.getSqlServerConnUrlStart());
                logger.info("swarvop.getSqlServerdatabase() = " + swarvop.getSqlServerdatabase());
                logger.info("swarvop.getSqlServerdbuser() = " + swarvop.getSqlServerdbuser());
                logger.info("swarvop.getSqlServerpassword() = " + swarvop.getSqlServerpassword());
                String strHelp1=swarvop.getSqlServerConnUrlStart() +";database=" + swarvop.getSqlServerdatabase() +";";
                 swarvop.setSqlServerConnUrlStart(strHelp1);
                logger.info("with database swarvop.getSqlServerConnUrlStart() = " + swarvop.getSqlServerConnUrlStart());
                curSqlCon = DriverManager.getConnection(swarvop.getSqlServerConnUrlStart(), swarvop.getSqlServerdbuser(), swarvop.getSqlServerpassword());
                //
                if (curSqlCon.isValid(0)) {
                    return INT_RET_OK;
                }
             } else if (!curSqlCon.isValid(0)) {
                        logger.info("CurConnection is not valid make new one!");
                        logger.info("not used swarvop.getSqlServerConnUrlWhole()) = " + swarvop.getSqlServerConnUrlWhole());
                        logger.info("swarvop.getSqlServerConnUrlStart() = " + swarvop.getSqlServerConnUrlStart());
                        logger.info("swarvop.getSqlServerdbuser() = " + swarvop.getSqlServerdbuser());
                        logger.info("swarvop.getSqlServerpassword() = " + swarvop.getSqlServerpassword());
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
        try {
            if (curSqlCon == null) {
                logger.info("CurConnection is null make totally new connection!");
                logger.info("swarvop.getMySqlConnUrlStartDbase() = " + swarvop.getMySqlConnUrlStartDbase());
                logger.info("swarvop.getMySqldbuser() = " + swarvop.getMySqldbuser());
                logger.info("swarvop.getMySqlpassword() = " + swarvop.getMySqlpassword());
                logger.info("swarvop.getMySqlServerTimeZone() = " + swarvop.getMySqlServerTimeZone());
                if (swarvop.getMySqlServerTimeZone().equals("NOTDEFINED")) {
                    logger.info(" timezone not defined");
                    curSqlCon = DriverManager.getConnection(swarvop.getMySqlConnUrlStartDbase(), swarvop.getMySqldbuser(), swarvop.getMySqlpassword());
                    if (curSqlCon.isValid(0)) {
                        return INT_RET_OK;
                    }
                    return INT_RET_NOT_OK;
                } else {
                    String strHelp1 =   swarvop.getMySqlConnUrlStartDbase() +
                             "?serverTimezone=" +
                                swarvop.getMySqlServerTimeZone();
                    logger.info("strHelp1 = " + strHelp1);
                    logger.info("swarvop.getMySqldbuser() = " + swarvop.getMySqldbuser());
                    logger.info("swarvop.getMySqlpassword() = " + swarvop.getMySqlpassword());
                    curSqlCon = DriverManager.getConnection(strHelp1, swarvop.getMySqldbuser(), swarvop.getMySqlpassword());
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
                    logger.info("swarvop.getMySqlConnUrlStartDbase() = " + swarvop.getMySqlConnUrlStartDbase());
                    logger.info("swarvop.getMySqldbuser() = " + swarvop.getMySqldbuser());
                    logger.info("swarvop.getMySqlpassword() = " + swarvop.getMySqlpassword());
                    curSqlCon = DriverManager.getConnection(swarvop.getMySqlConnUrlStartDbase(),swarvop.getMySqldbuser(),swarvop.getMySqlpassword());
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
}
