package thiyagu.postman.com.postmanandroid.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "body")
public class Body {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "key")
    public String key;

    @ColumnInfo(name = "bodytype")
    public String bodytype;

    @ColumnInfo(name = "flag")
    public String flag;


    @ColumnInfo(name ="value")
    public String value;


    @ColumnInfo(name ="rawvalue")
    public String rawvalue;
    @ColumnInfo(name ="referenceid")
    public String referenceId;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getBodytype() {
        return bodytype;
    }

    public void setBodytype(String bodytype) {
        this.bodytype = bodytype;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRawvalue() {
        return rawvalue;
    }

    public void setRawvalue(String rawvalue) {
        this.rawvalue = rawvalue;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }
}
