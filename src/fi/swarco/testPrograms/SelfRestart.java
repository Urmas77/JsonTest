package fi.swarco.testPrograms;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
public class SelfRestart {
    private static Logger logger = Logger.getLogger(SelfRestart.class.getName());
    public static void main(String[] args) throws IOException, InterruptedException {
        StringBuilder cmd = new StringBuilder();
        cmd.append(System.getProperty("java.home") + File.separator + "bin" + File.separator + "java ");
        for (String jvmArg : ManagementFactory.getRuntimeMXBean().getInputArguments()) {
            cmd.append(jvmArg + " ");
        }
        cmd.append("-cp ").append(ManagementFactory.getRuntimeMXBean().getClassPath()).append(" ");
        cmd.append(SelfRestart.class.getName()).append(" ");
        for (String arg : args) {
            cmd.append(arg).append(" ");
        }
        logger.info(cmd.toString());
        Thread.currentThread().sleep(10000); // 10 seconds delay before restart
        Runtime.getRuntime().exec(cmd.toString());
       logger.info("Successful restart");
        System.exit(0);
    }
    public void StartMySelf(){
        StringBuilder cmd = new StringBuilder();
        String[] jArgs = new String[100];   // define exactly RETHINK
        try {
            cmd.append(System.getProperty("java.home") + File.separator + "bin" + File.separator + "java ");
            for (String jvmArg : ManagementFactory.getRuntimeMXBean().getInputArguments()) {
                cmd.append(jvmArg + " ");
            }
            cmd.append("-cp ").append(ManagementFactory.getRuntimeMXBean().getClassPath()).append(" ");
            cmd.append(SelfRestart.class.getName()).append(" ");
            for (String arg : jArgs) {
                cmd.append(arg).append(" ");
            }
        // put trim maybe here RETHINK
            logger.info(cmd.toString());
            Thread.currentThread().sleep(10000); // 10 seconds delay before restart
            Runtime.getRuntime().exec(cmd.toString());
            logger.info("Successful restart");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(ExceptionUtils.getRootCauseMessage(e));
            logger.info(ExceptionUtils.getFullStackTrace(e));
        }
    }

}