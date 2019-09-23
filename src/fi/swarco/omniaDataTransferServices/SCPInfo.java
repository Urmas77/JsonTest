package fi.swarco.omniaDataTransferServices;

import static fi.swarco.CONSTANT.NO_VALUE;

public class SCPInfo {
    private String username;
    private String IP;
    private String password;
    private int port;
    private String key1;
    private String key2;
    private String created;
    private  String updated;
    private java.sql.Timestamp createdSql;
    private java.sql.Timestamp updatedSql;
    public SCPInfo(
             String username,
             String IP,
             String password,
             int port,
             String key1,
             String key2,
             String created,
             String updated
    ) {
        super();
        this.username=username;
        this.IP=IP;
        this.password=password;
        this.port=port;
        this.key1=key1;
        this.key2=key2;
        this.created=created;
        this.updated=updated;
        this.updatedSql=java.sql.Timestamp.valueOf(updated);
        this.createdSql=java.sql.Timestamp.valueOf(created);
    }
    public SCPInfo() {
    }
    public SCPInfo(String username, String password, String IP) {
        this.username = username;
        this.password = password;
        this.IP = IP;
    }
    public void SCPInfoKey1(String username, String password, String IP, String key1) {
        this.username = username;
        this.password = password;
        this.IP = IP;
        this.key1 =key1;
    }

    public String getUsername() {
        return username;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIP() {
        return IP;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }
    public String getKey1() {
        return key1;
    }
    public void setKey1(String pKey1) {
        this.key1 = pKey1;
    }
    public String getKey2() {
        return key2;
    }
    public void setKey2(String pKey2) {
        this.key2 = pKey2;
    }
    public String getCreated() {
        if (created == null) {
            if (!(createdSql==null)) {
                created=createdSql.toString().substring(0, 19);
            }
        }
        return created;
    }
    public void setCreated(String pcreated) {
        this.created = pcreated;
        if (!(created == null )) {
            createdSql=java.sql.Timestamp.valueOf(created);
        }
    }
    public java.sql.Timestamp getCreatedSql() {
        if (createdSql == null) {
            if (!(created == null)) {
                createdSql=java.sql.Timestamp.valueOf(created);
            }
        }
        return createdSql;
    }
    public void setCreatedSql(java.sql.Timestamp pcreatedSql) {
        this.createdSql = pcreatedSql;
        if (!(createdSql == null)) {
            created=createdSql.toString().substring(0, 19);
        }
    }
    public String getUpdated() {
    if (updated == null) {
        if (!(updatedSql==null)) {
            updated=updatedSql.toString().substring(0, 19);
        }
    }
    return updated;
}
    public void setUpdated(String pupdated) {
        this.updated = pupdated;
        if (!(updated == null )) {
            updatedSql=java.sql.Timestamp.valueOf(updated);
        }
    }
    public java.sql.Timestamp getUpdatedSql() {
        if (updatedSql == null) {
            if (!(updated == null)) {
                updatedSql=java.sql.Timestamp.valueOf(updated);
            }
        }
        return updatedSql;
    }
    public void setUpdatedSql(java.sql.Timestamp pupdatedSql) {
        this.updatedSql = pupdatedSql;
        if (!(updatedSql == null)) {
            updated=updatedSql.toString().substring(0, 19);
        }
    }
    @Override
    public String toString() {
        return "UserInfo [username = " +username +
                ", IP = " +IP  +
                ", password = " + password +
                ", port = " + port +
                ", key1 = " +  key1 +
                ", key2 =" + key2 +
                ", created =" + created +
                ", createdSql =" + createdSql +
                ", updated =" + updated +
                ", updatedSql =" + updatedSql +"]";
    }
    public void MakeEmptyElement() {
        username = NO_VALUE;
        IP =NO_VALUE;
        password=NO_VALUE;
        port =Integer.valueOf(0);
        key1 =NO_VALUE;
        key2 =NO_VALUE;
        created ="1970-01-01 00:00:00";
        updated= "1970-01-01 00:00:00";
        createdSql =java.sql.Timestamp.valueOf("1970-01-01 00:00:00");
        updatedSql= java.sql.Timestamp.valueOf("1970-01-01 00:00:00");
    }
}





