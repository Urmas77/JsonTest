package fi.swarco.messageHandling;
import static fi.swarco.CONSTANT.*;
public class ReturnWrapper {
    private String retCode = RET_NOT_OK;
    private String retMessage = NO_VALUE;
    private String retString = NO_VALUE;
    public String getRetCode() {
        return retCode;
    }
    public void setRetCode(String pRetCode) {
        retCode = pRetCode;
    }
    public String getRetMessage() {
        return retMessage;
    }
    public void setRetMessage(String pRetCode) {
        retMessage = pRetCode;
    }
    public String getRetString() {
        return retString;
    }
    public void setRetString(String pRetString) {
        retString = pRetString;
    }
    @Override
    public String toString() {
        return "ReturnWrapper  [retCode = " + retCode +
                ", retMessage = " + retMessage +
                ", retString = " + retString + "]";
    }
   public void MakeEmptyElement() {
        retCode = RET_NOT_OK;
        retMessage = NO_VALUE;
        retString = NO_VALUE;
    }
}


