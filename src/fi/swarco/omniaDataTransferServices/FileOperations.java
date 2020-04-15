package fi.swarco.omniaDataTransferServices;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import fi.swarco.properties.JSwarcoproperties;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import static fi.swarco.CONSTANT.*;
public class FileOperations {
    private static Logger logger = Logger.getLogger(FileOperations.class.getName());
    private JSwarcoproperties swarvop;
    protected int SeekProperties() {
        int iRet = 0;
     //   logger.info("inside seekproperties");
        swarvop    = new JSwarcoproperties();
        iRet = swarvop.getSwarcoProperties();
        if (iRet != 1) {
            logger.info ("Ei saatu properteja");
            return iRet;
        }
        return INT_RET_OK;
    }
    public int initFileOperations(){
        return SeekProperties();
    }
    public int closeAndDeleteFile(String pPathAndFileName ) {
        File myFile = new File(pPathAndFileName);
        String strHelp1 = NO_VALUE;
        int index =-1;
        try {
            logger.info("pPathAndFileName = " + pPathAndFileName);
            System.out.println("fo pPathAndFileName = " + pPathAndFileName);
            index = pPathAndFileName.indexOf(".log");
            System.out.println("fo index = " +index);
            if (index >0) {
                strHelp1 = pPathAndFileName.substring(0, index) + ".ttt";
                System.out.println(" fofofo strHelp1 ="+ strHelp1);
                File myFileDest = new File(strHelp1);
                boolean success =myFile.renameTo(myFileDest);
                if (success) {
                    System.out.println(" fo succes myFile.getAbsolutePath() = " + myFile.getAbsolutePath());
                } else {
                    System.out.println(" fo not success myFile.getAbsolutePath() = " + myFile.getAbsolutePath());
                }
            }
            myFile= new File(strHelp1);
            if (myFile.exists() ) {
                if (myFile.delete()) {
                    System.out.println("File is deleted myFile.getAbsolutePath() = " + myFile.getAbsolutePath());
                    logger.info("File is deleted myFile.getAbsolutePath() = " + myFile.getAbsolutePath());
                } else {
                    System.out.println("Unsuccesfull File delete operation myFile.getAbsolutePath() = " + myFile.getAbsolutePath());
                    logger.info("Unsuccesfull File delete operation myFile.getAbsolutePath() = " + myFile.getAbsolutePath());
                }
            } else {
                System.out.println("File not found myFile.getAbsolutePath() = " + myFile.getAbsolutePath());
                logger.info("File not found myFile.getAbsolutePath() = " + myFile.getAbsolutePath());
            }
            return INT_RET_OK;
        }  catch(Exception ex) {
            logger.info("Error deleting file  '" + pPathAndFileName + "'");
            logger.info(ExceptionUtils.getRootCauseMessage(ex));
            logger.info(ExceptionUtils.getFullStackTrace(ex));
            ex.printStackTrace();
            return INT_RET_NOT_OK;
        }
    }
    public int closeAndDeleteNormalFile(String pPathAndFileName ) {
        File myFile = new File(pPathAndFileName);
        String strHelp1 = NO_VALUE;
        try {
            logger.info("pPathAndFileName = " + pPathAndFileName);
            System.out.println("fo pPathAndFileName = " + pPathAndFileName);
            myFile= new File(pPathAndFileName);
            if (myFile.exists() ) {
                if (myFile.delete()) {
                    System.out.println("File is deleted myFile.getAbsolutePath() = " + myFile.getAbsolutePath());
                    logger.info("File is deleted myFile.getAbsolutePath() = " + myFile.getAbsolutePath());
                } else {
                    System.out.println("Unsuccesfull File delete operation myFile.getAbsolutePath() = " + myFile.getAbsolutePath());
                    logger.info("Unsuccesfull File delete operation myFile.getAbsolutePath() = " + myFile.getAbsolutePath());
                }
            } else {
                System.out.println("File not found myFile.getAbsolutePath() = " + myFile.getAbsolutePath());
                logger.info("File not found myFile.getAbsolutePath() = " + myFile.getAbsolutePath());
            }
            return INT_RET_OK;
        }  catch(Exception ex) {
            logger.info("Error deleting file  '" + pPathAndFileName + "'");
            logger.info(ExceptionUtils.getRootCauseMessage(ex));
            logger.info(ExceptionUtils.getFullStackTrace(ex));
            ex.printStackTrace();
            return INT_RET_NOT_OK;
        }
    }
    public int addOmniaClientJsonLine(String pstrJson,String pJsonFileName ) {
        String fileName=swarvop.getFilePathStringOmniaClient() + pJsonFileName +".txt";
        String wtext;
        File myFile = new File(fileName);
        try {
 //           logger.info("fileName = " + fileName);
            if (!myFile.exists() && !myFile.isDirectory()) {
                myFile.createNewFile();
                logger.info("File does not exists create new filename ="+ fileName);
                if (!myFile.exists()) {
                    return FILE_NOT_EXIST;
                }
            }
           // logger.info("File exists filename ="+ fileName);
            if (myFile.canWrite()) {
                FileWriter fileWriter = new FileWriter(fileName,true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                wtext =pstrJson;
       //         logger.info("File can write  writestr= "+ wtext);
                // Note that write() does not automatically
                // append a newline character.
                bufferedWriter.write(wtext);
                bufferedWriter.newLine();
                // Always close files.
                bufferedWriter.close();
            }
            return INT_RET_OK;
        }  catch(IOException ex) {
            logger.info("Error writing to file '" + fileName + "'");
            logger.info(ExceptionUtils.getRootCauseMessage(ex));
            logger.info(ExceptionUtils.getFullStackTrace(ex));
            ex.printStackTrace();
            return INT_RET_NOT_OK;
        }
    }
    public int addOmniaCloudJsonLine(String pstrJson,String pJsonFileName ) {
        String fileName=swarvop.getFilePathStringOmniaCloud() + pJsonFileName +".txt";
        String wtext;
        File myFile = new File(fileName);
        try {
         //   logger.info("fileName = " + fileName);
            if (!myFile.exists() && !myFile.isDirectory()) {
                myFile.createNewFile();
                logger.info("File does not exists create new filename ="+ fileName);
                if (!myFile.exists()) {
                    return FILE_NOT_EXIST;
                }
            }
           // logger.info("File exists filename ="+ fileName);
            if (myFile.canWrite()) {
                FileWriter fileWriter = new FileWriter(fileName,true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                wtext =pstrJson;
           //     logger.info("File can write  writestr= "+ wtext);
                // Note that write() does not automatically
                // append a newline character.
                bufferedWriter.write(wtext);
                bufferedWriter.newLine();
                // Always close files.
                bufferedWriter.close();
            }
            return INT_RET_OK;
        }  catch(IOException ex) {
            logger.info("Error writing to file '" + fileName + "'");
            logger.info(ExceptionUtils.getRootCauseMessage(ex));
            logger.info(ExceptionUtils.getFullStackTrace(ex));
            ex.printStackTrace();
            return INT_RET_NOT_OK;
        }
    }
    public String getFullInfluxFileNameOld(String pInfluxFileName) {
        return swarvop.getFilePathStringOmniaInflux() + pInfluxFileName +".txt";
    }
    public String getFullInfluxFileName() {
        return swarvop.getFilePathStringOmniaInflux() +swarvop.getFileNameInflux1() +".txt";
    }
    public int addOmniaInfluxLine(String pstrLine,String pInfluxFileName ) {
        String fileName= pInfluxFileName; // this includes full path
        String wtext;
        File myFile = new File(fileName);
        try {
            //   logger.info("fileName = " + fileName);
            if (!myFile.exists() && !myFile.isDirectory()) {
                myFile.createNewFile();
                logger.info("File does not exists create new filename ="+ fileName);
                if (!myFile.exists()) {
                    return FILE_NOT_EXIST;
                }
            }
            // logger.info("File exists filename ="+ fileName);
            if (myFile.canWrite()) {
                FileWriter fileWriter = new FileWriter(fileName,true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                wtext =pstrLine;
                wtext = wtext +NEW_LINE_LINUX;
                logger.info("File can write  writestr= "+ wtext);
                // Note that write() does not automatically
                // append a newline character.
                bufferedWriter.write(wtext);
               // bufferedWriter.newLine();
                // Always close files.
                bufferedWriter.close();
            }
            return INT_RET_OK;
        }  catch(IOException ex) {
            logger.info("Error writing to file '" + fileName + "'");
            logger.info(ExceptionUtils.getRootCauseMessage(ex));
            logger.info(ExceptionUtils.getFullStackTrace(ex));
            ex.printStackTrace();
            return INT_RET_NOT_OK;
        }
    }



}
