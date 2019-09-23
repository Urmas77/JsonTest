package fi.swarco.omniaDataTransferServices;
import static fi.swarco.CONSTANT.NO_VALUE;
public class JsonWrapper {
private String  permanentData;
private String  measurementsData;
private String fullData;
public  JsonWrapper(){}
    public  JsonWrapper(
     String  permanentData,
     String  measurementsData,
     String fullData){
        super();
        this.permanentData=permanentData;
        this.measurementsData=measurementsData;
        this.fullData=fullData;
    }
    public String getPermanentData() {
      return permanentData;
    }
    public void setPermanentData(String pPermanentData) {
        this.permanentData = pPermanentData;
    }
    public String getMeasurementsData() {
        return this.measurementsData;
    }
    public void setMeasurementsData(String pMeasurementsData) {
        this.measurementsData = pMeasurementsData;
    }
    public String getFullData() {
        return this.fullData;
    }
    public void setFullData(String pFullData) {
        this.fullData = pFullData;
    }
    @Override
    public String toString() {
        return "JsonWrapper  [permanentData = " + permanentData +
                ", measurementsData = " + measurementsData  +
                ", fullData = " + "]";
    }
    public void MakeEmptyElement() {
        permanentData=NO_VALUE;
        measurementsData=NO_VALUE;
        fullData=NO_VALUE;
    }
}
