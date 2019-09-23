package fi.swarco.dataHandling.pojos;
import static fi.swarco.CONSTANT.NO_IDENTITY;
import static fi.swarco.CONSTANT.NO_VALUE;
public class RawData {
    private long rawdataid;
    private long rawdatasourceid;
    private String rawdatatimestamp;
    private long rawdatastatus;
    private String rawdatastatusstring;
    private String rawdataline;
    private String timestamp;
    private String timecreated;
    private java.sql.Timestamp rawdatatimestampSql;
    private java.sql.Timestamp timestampSql;
    private java.sql.Timestamp timecreatedSql;
    public  RawData(){}
    public RawData(
            long rawdataid,
            long rawdatasourceid,
            String rawdatatimestamp,
            long rawdatastatus,
            String rawdatastatusstring,
            String rawdataline,
            String timestamp,
            String timecreated
    ) {
        super();
        this.rawdataid=rawdataid;
        this.rawdatasourceid=rawdatasourceid;
        this.rawdatatimestamp=rawdatatimestamp;
        this.rawdatastatus=rawdatastatus;
        this.rawdatastatusstring=rawdatastatusstring;
        this.rawdataline=rawdataline;
        this.timestamp=timestamp;
        this.timecreated=timecreated;
        this.rawdatatimestampSql=java.sql.Timestamp.valueOf(rawdatatimestamp);
        this.timestampSql=java.sql.Timestamp.valueOf(timestamp);
        this.timecreatedSql=java.sql.Timestamp.valueOf(timecreated);
    }
    public long getRawDataId() {
        return rawdataid;
    }
    public void setRawDataId(long prawdataid) {
        this.rawdataid = prawdataid;
    }
    public long getRawDataSourceId() {
        return rawdatasourceid;
    }
    public void setRawDataSourceId(long prawdatasourceid) {
        this.rawdatasourceid = prawdatasourceid;
    }
    public String getRawDataTimestamp() {
        if (rawdatatimestamp == null) {
            if (!(rawdatatimestampSql==null)) {
                rawdatatimestamp=rawdatatimestampSql.toString().substring(0, 19);
            }
        }
        return rawdatatimestamp;
    }
    public void setRawDataTimestamp(String prawdatatimestamp) {
        this.rawdatatimestamp = prawdatatimestamp;
        if (!(rawdatatimestamp == null )) {
            rawdatatimestampSql=java.sql.Timestamp.valueOf(rawdatatimestamp);
        }
    }
    public long getRawDataStatus() {
        return rawdatastatus;
    }
    public void setRawDataStatus(long prawdatastatus) {
        this.rawdatastatus = prawdatastatus;
    }
    public String getRawDataStatusString() {
        return rawdatastatusstring;
    }
    public void setRawDataStatusString(String prawdatastatusstring) {
        this.rawdatastatusstring = prawdatastatusstring;
    }
    public String getRawDataLine() {
        return rawdataline;
    }
    public void setRawDataLine(String prawdataline) {
        this.rawdataline = prawdataline;
    }
    public String getTimestamp() {
        if (timestamp == null) {
            if (!(timestampSql==null)) {
                timestamp=timestampSql.toString().substring(0, 19);
            }
        }
        return timestamp;
    }
    public void setTimestamp(String ptimestamp) {
        this.timestamp = ptimestamp;
        if (!(timestamp == null )) {
            timestampSql=java.sql.Timestamp.valueOf(timestamp);
        }
    }
    public String getTimeCreated() {
        if (timecreated == null) {
            if (!(timecreatedSql==null)) {
                timecreated=timecreatedSql.toString().substring(0, 19);
            }
        }
        return timecreated;
    }
    public void setTimeCreated(String ptimecreated) {
        this.timecreated = ptimecreated;
        if (!(timecreated == null )) {
            timecreatedSql=java.sql.Timestamp.valueOf(timecreated);
        }
    }
    public java.sql.Timestamp getRawDataTimestampSql() {
        if (rawdatatimestampSql == null) {
            if (!(rawdatatimestamp == null)) {
                rawdatatimestampSql=java.sql.Timestamp.valueOf(rawdatatimestamp);
            }
        }
        return rawdatatimestampSql;
    }
    public void setRawDataTimestampSql(java.sql.Timestamp prawdatatimestampSql) {
        this.rawdatatimestampSql = prawdatatimestampSql;
        if (!(rawdatatimestampSql == null)) {
            rawdatatimestamp=rawdatatimestampSql.toString().substring(0, 19);
        }
    }
    public java.sql.Timestamp getTimestampSql() {
        if (timestampSql == null) {
            if (!(timestamp == null)) {
                timestampSql=java.sql.Timestamp.valueOf(timestamp);
            }
        }
        return timestampSql;
    }
    public void setTimestampSql(java.sql.Timestamp ptimestampSql) {
        this.timestampSql = ptimestampSql;
        if (!(timestampSql == null)) {
            timestamp=timestampSql.toString().substring(0, 19);
        }
    }
    public java.sql.Timestamp getTimeCreatedSql() {
        if (timecreatedSql == null) {
            if (!(timecreated == null)) {
                timecreatedSql=java.sql.Timestamp.valueOf(timecreated);
            }
        }
        return timecreatedSql;
    }
    public void setTimeCreatedSql(java.sql.Timestamp ptimecreatedSql) {
        this.timecreatedSql = ptimecreatedSql;
        if (!(timecreatedSql == null)) {
            timecreated=timecreatedSql.toString().substring(0, 19);
        }
    }
    @Override
    public String toString() {
        return "RawData [rawdataid = " +rawdataid +
                ", rawdatasourceid = " +rawdatasourceid  +
                ", rawdatatimestamp = " + rawdatatimestamp +
                ", rawdatastatus = " + rawdatastatus +
                ", rawdatastatusstring = " + rawdatastatusstring +
                ", rawdataline = " +  rawdataline +
                ", timestamp =" + timestamp +
                ", timecreated =" + timecreated +
                ", rawdatatimestampSql =" + rawdatatimestampSql +
                ", timestampSql =" + timestampSql +
                ", timecreatedSql =" + timecreatedSql +"]";
    }
    public void MakeEmptyElement() {
        rawdataid=Long.valueOf(NO_IDENTITY);
        rawdatasourceid=Long.valueOf(0);
        rawdatatimestamp="1970-01-01 00:00:00";
        rawdatastatus=Long.valueOf(0);
        rawdatastatusstring="NOT_DEFINED";
        rawdataline=NO_VALUE;
        timestamp="1970-01-01 00:00:00";
        timecreated="1970-01-01 00:00:00";
        rawdatatimestampSql=java.sql.Timestamp.valueOf("1970-01-01 00:00:00");
        timestampSql=java.sql.Timestamp.valueOf("1970-01-01 00:00:00");
        timecreatedSql=java.sql.Timestamp.valueOf("1970-01-01 00:00:00");
    }
}