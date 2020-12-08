package fi.swarco.connections;
import static fi.swarco.CONSTANT.NO_VALUE;
public class ConWrapper  {
    private String  connUrlStart;
    private String   databaseName;
    private String   databaseUserName;
    private String   dbPassword;
    private String httpServerPort;
    private String httpClientPort;
    private String serverTimeZone;
    private String  clientUrl;

    public  ConWrapper (){}
    public  ConWrapper (
            String  connUrlStart,
            String  databaseName,
            String  databaseUserName,
            String dbPassword,
            String httpServerPort,
            String httpClientPort,
            String  clientUrl,
            String serverTimeZone){
        super();
        this.connUrlStart=connUrlStart;
        this.databaseName=databaseName;
        this.databaseUserName=databaseUserName;
        this.dbPassword=dbPassword;
        this.httpServerPort=httpServerPort;
        this.httpClientPort=httpClientPort;
        this.clientUrl=clientUrl;
        this.serverTimeZone=serverTimeZone;
    }
    public String getConnUrlStart() {
        return connUrlStart;
    }
    public void setConnUrlStart(String pConnUrlStart) {
        this.connUrlStart = pConnUrlStart;
    }
    public String getDatabaseName() {
        return this.databaseName;
    }
    public void setDatabaseName(String pDatabaseName) {
        this.databaseName = pDatabaseName;
    }
    public String getDatabaseUserName() {
        return this.databaseUserName;
    }
    public void setDatabaseUserName(String pDatabaseUserName) {
        this.databaseUserName = pDatabaseUserName;
    }
    public String getDbPassword() {return this.dbPassword;}
    public void setDbPassword(String pDbPassword) {
        this.dbPassword = pDbPassword;
    }
    public void setHttpServerPort(String pHttpServerPort) {
        this.httpServerPort= pHttpServerPort;
    }
    public String getHttpServerPort() {return this.httpServerPort;}
    public void setHttpClientPort(String pHttpClientPort) {
        this.httpClientPort= pHttpClientPort;
    }
    public String getHttpClientPort() {
        return this.httpClientPort;
    }
    public void setClientUrl(String pClientUrl) {
        this.clientUrl= pClientUrl;
    }
    public String getClientUrl() {return this.clientUrl;}
    public void setServerTimeZone(String pServerTimeZone) {
        this.serverTimeZone= pServerTimeZone;
    }
    public String getServerTimeZone() {return this.serverTimeZone;}
    @Override
    public String toString() {
        return "JsonWrapper [connUrlStart = " + this.connUrlStart +
                ", databaseName = " + this.databaseName  +
                ", databaseUserName = " + this.databaseUserName  +
                ", dbPassword = " + this.dbPassword +
                ", httpServerPort = "  + this.httpServerPort +
                ", httpClientPort = "  + this.httpClientPort +
                ", ClientUrl = " + this.clientUrl +
                ", serverTimeZone = "      + this.serverTimeZone +
                " ]";
    }
    public void MakeEmptyElement() {
        connUrlStart=NO_VALUE;
        databaseName=NO_VALUE;
        databaseUserName=NO_VALUE;
        dbPassword=NO_VALUE;
        httpServerPort=NO_VALUE;
        httpClientPort=NO_VALUE;
        clientUrl=NO_VALUE;
        serverTimeZone=NO_VALUE;
    }
}
