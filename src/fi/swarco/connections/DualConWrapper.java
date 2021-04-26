package fi.swarco.connections;
        import static fi.swarco.CONSTANT.NO_VALUE;
public class DualConWrapper  {
   // This wrapper is for direct datatransfer from sqlserver/city to Mysql = two connetion programs JIs 8.1 2020
    private String  readConnUrlStart;
    private String  readDatabaseName;
    private String  readDatabaseUserName;
    private String  readDbPassword;
    private String  readHttpServerPort;
    private String  readHttpClientPort;
    private String  readServerTimeZone;
    private String  readClientUrl;
    private String  readDataTransferStatus;
    private String  writeConnUrlStart;
    private String  writeDatabaseName;
    private String  writeDatabaseUserName;
    private String  writeDbPassword;
    private String  writeHttpServerPort;
    private String  writeHttpClientPort;
    private String  writeServerTimeZone;
    private String  writeClientUrl;
    private String  writeDataTransferStatus;
    public  DualConWrapper (){}
    public  DualConWrapper (
            String  readConnUrlStart,
            String  readDatabaseName,
            String  readDatabaseUserName,
            String  readDbPassword,
            String  readHttpServerPort,
            String  readHttpClientPort,
            String  readClientUrl,
            String  readServerTimeZone,
            String  readDataTransferStatus,
            String  writeConnUrlStart,
            String  writeDatabaseName,
            String  writeDatabaseUserName,
            String  writeDbPassword,
            String  writeHttpServerPort,
            String  writeHttpClientPort,
            String  writeClientUrl,
            String  writeServerTimeZone,
            String  writeDataTransferStatus){
        super();
        this.readConnUrlStart=readConnUrlStart;
        this.readDatabaseName=readDatabaseName;
        this.readDatabaseUserName=readDatabaseUserName;
        this.readDbPassword=readDbPassword;
        this.readHttpServerPort=readHttpServerPort;
        this.readHttpClientPort=readHttpClientPort;
        this.readClientUrl=readClientUrl;
        this.readServerTimeZone=readServerTimeZone;
        this.readDataTransferStatus=readDataTransferStatus;
        this.writeConnUrlStart=writeConnUrlStart;
        this.writeDatabaseName=writeDatabaseName;
        this.writeDatabaseUserName=writeDatabaseUserName;
        this.writeDbPassword=writeDbPassword;
        this.writeHttpServerPort=writeHttpServerPort;
        this.writeHttpClientPort=writeHttpClientPort;
        this.writeClientUrl=writeClientUrl;
        this.writeServerTimeZone=writeServerTimeZone;
        this.writeDataTransferStatus=writeDataTransferStatus;
    }
    public String getReadConnUrlStart() {
        return readConnUrlStart;
    }
    public void setReadConnUrlStart(String pReadConnUrlStart) {
        this.readConnUrlStart = pReadConnUrlStart;
    }
    public String getReadDatabaseName() {
        return this.readDatabaseName;
    }
    public void setReadDatabaseName(String pReadDatabaseName) {
        this.readDatabaseName = pReadDatabaseName;
    }
    public String getReadDatabaseUserName() {
        return this.readDatabaseUserName;
    }
    public void setReadDatabaseUserName(String pReadDatabaseUserName) {
        this.readDatabaseUserName = pReadDatabaseUserName;
    }
    public String getReadDbPassword() {return this.readDbPassword;}
    public void setReadDbPassword(String pReadDbPassword) {
        this.readDbPassword = pReadDbPassword;
    }
    public void setReadHttpServerPort(String pReadHttpServerPort) {
        this.readHttpServerPort= pReadHttpServerPort;
    }
    public String getReadHttpServerPort() {return this.readHttpServerPort;}
    public void setReadHttpClientPort(String pReadHttpClientPort) {
        this.readHttpClientPort= pReadHttpClientPort;
    }
    public String getReadHttpClientPort() {
        return this.readHttpClientPort;
    }
    public void setReadClientUrl(String pReadClientUrl) {
        this.readClientUrl= pReadClientUrl;
    }
    public String getReadClientUrl() {return this.readClientUrl;}
    public void setReadServerTimeZone(String pReadServerTimeZone) {
        this.readServerTimeZone= pReadServerTimeZone;
    }
    public String getReadServerTimeZone() {return this.readServerTimeZone;}
    public void setReadDataTransferStatus(String pReadDataTransferStatus) {
        this.readDataTransferStatus= pReadDataTransferStatus;
    }
    public String getReadDataTransferStatus() {return this.readDataTransferStatus;}
//*****************************************************************************************
    public String getWriteConnUrlStart() {
        return writeConnUrlStart;
    }
    public void setWriteConnUrlStart(String pWriteConnUrlStart) {
        this.writeConnUrlStart = pWriteConnUrlStart;
    }
    public String getWriteDatabaseName() {
        return this.writeDatabaseName;
    }
    public void setWriteDatabaseName(String pWriteDatabaseName) {
        this.writeDatabaseName = pWriteDatabaseName;
    }
    public String getWriteDatabaseUserName() {
        return this.writeDatabaseUserName;
    }
    public void setWriteDatabaseUserName(String pWriteDatabaseUserName) {
        this.writeDatabaseUserName = pWriteDatabaseUserName;
    }
    public String getWriteDbPassword() {return this.writeDbPassword;}
    public void setWriteDbPassword(String pWriteDbPassword) {
        this.writeDbPassword = pWriteDbPassword;
    }
    public void setWriteHttpServerPort(String pWriteHttpServerPort) {
        this.writeHttpServerPort= pWriteHttpServerPort;
    }
    public String getWriteHttpServerPort() {return this.writeHttpServerPort;}
    public void setWriteHttpClientPort(String pWriteHttpClientPort) {
        this.writeHttpClientPort= pWriteHttpClientPort;
    }
    public String getWriteHttpClientPort() {
        return this.writeHttpClientPort;
    }
    public void setWriteClientUrl(String pWriteClientUrl) {
        this.writeClientUrl= pWriteClientUrl;
    }
    public String getWriteClientUrl() {return this.writeClientUrl;}
    public void setWriteServerTimeZone(String pWriteServerTimeZone) {
        this.writeServerTimeZone= pWriteServerTimeZone;
    }
    public String getWriteServerTimeZone() {return this.writeServerTimeZone;}
    public void setWriteDataTransferStatus(String pWriteDataTransferStatus) {
        this.writeDataTransferStatus= pWriteDataTransferStatus;
    }
    public String getWriteDataTransferStatus() {return this.writeDataTransferStatus;}
    @Override
    public String toString() {
        return "JsonWrapper [" +
                "readConnUrlStart = " + this.readConnUrlStart +
                ", readDatabaseName = " + this.readDatabaseName  +
                ", readDatabaseUserName = " + this.readDatabaseUserName  +
                ", readDbPassword = " + this.readDbPassword +
                ", readHttpServerPort = "  + this.readHttpServerPort +
                ", readHttpClientPort = "  + this.readHttpClientPort +
                ", readClientUrl = " + this.readClientUrl +
                ", readServerTimeZone = "      + this.readServerTimeZone +
                ", readDataTransferStatus = " + this.readDataTransferStatus +
                ", writeConnUrlStart = " + this.writeConnUrlStart +
                ", writeDatabaseName = " + this.writeDatabaseName  +
                ", writeDatabaseUserName = " + this.writeDatabaseUserName  +
                ", writeDbPassword = " + this.writeDbPassword +
                ", writeHttpServerPort = "  + this.writeHttpServerPort +
                ", writeHttpClientPort = "  + this.writeHttpClientPort +
                ", writeClientUrl = " + this.writeClientUrl +
                ", writeServerTimeZone = "      + this.writeServerTimeZone +
                ", writeDataTransferStatus = " + this.writeDataTransferStatus +
                " ]";
    }
    public void MakeEmptyElement() {
        readConnUrlStart=NO_VALUE;
        readDatabaseName=NO_VALUE;
        readDatabaseUserName=NO_VALUE;
        readDbPassword=NO_VALUE;
        readHttpServerPort=NO_VALUE;
        readHttpClientPort=NO_VALUE;
        readClientUrl=NO_VALUE;
        readServerTimeZone=NO_VALUE;
        readDataTransferStatus=NO_VALUE;
        writeConnUrlStart=NO_VALUE;
        writeDatabaseName=NO_VALUE;
        writeDatabaseUserName=NO_VALUE;
        writeDbPassword=NO_VALUE;
        writeHttpServerPort=NO_VALUE;
        writeHttpClientPort=NO_VALUE;
        writeClientUrl=NO_VALUE;
        writeServerTimeZone=NO_VALUE;
        writeDataTransferStatus=NO_VALUE;
    }
}
