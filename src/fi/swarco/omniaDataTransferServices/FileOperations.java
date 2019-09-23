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
    public int addOmniaClientJsonLine(String pstrJson,String pJsonFileName ) {
        String fileName=swarvop.getFilePathStringOmniaClient() + pJsonFileName +".txt";
        String wtext;
        File myFile = new File(fileName);
        try {
            logger.info("fileName = " + fileName);
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
}
