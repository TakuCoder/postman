package thiyagu.postman.com.postmanandroid.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "authorization")
public class Authorization {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "key")
    public String key;

    @ColumnInfo(name ="value")
    public String value;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }
}
