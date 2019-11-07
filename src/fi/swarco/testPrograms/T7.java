package fi.swarco.testPrograms;
import org.apache.commons.lang.exception.ExceptionUtils;
import java.io.File;
import static fi.swarco.CONSTANT.*;
public class T7 {
    public static void main (String[] args)
    {
        int iRet =0;
        String strHelp1 ; // = NO_VALUE;


        System.out.println("args.length =" + args.length);

       // strHelp1= "e://log/example.log";
         if (args.length>0) {
             strHelp1 = args[0];
             System.out.println("strHelp1 =" + strHelp1);
             iRet = closeAndDeleteFile(strHelp1);
         }
        System.out.println("iRey = "+ iRet);
    }
    private static int closeAndDeleteFile(String pPathAndFileName ) {
        File myFile = new File(pPathAndFileName);
        String strHelp1; // = NO_VALUE;
       // try {
            System.out.println("fo pPathAndFileName = " + pPathAndFileName);
            strHelp1 = pPathAndFileName;
            System.out.println(" fofofo strHelp1 =" + strHelp1);
            if (myFile.exists()) {
               if (myFile.delete()) {
                   System.out.println("File is deleted myFile.getAbsolutePath() = " + myFile.getAbsolutePath());
               } else {
                   System.out.println("Unsuccesfull File delete operation myFile.getAbsolutePath() = " + myFile.getAbsolutePath());
               }
            } else {
               System.out.println("File not found myFile.getAbsolutePath() = " + myFile.getAbsolutePath());
            }
            return INT_RET_OK;
       // } catch (Exception ex) {
       //     System.out.println("Error deleting file  '" + pPathAndFileName + "'");
       //     System.out.println(ExceptionUtils.getRootCauseMessage(ex));
       //     System.out.println(ExceptionUtils.getFullStackTrace(ex));
       //     ex.printStackTrace();
       //     return INT_RET_NOT_OK;
       // }
    }
}
