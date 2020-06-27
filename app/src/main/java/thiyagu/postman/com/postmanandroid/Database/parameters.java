package thiyagu.postman.com.postmanandroid.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "parameters")
public class parameters {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "key")
    public String key;

    @ColumnInfo(name = "flag")
    public String flag;


    @ColumnInfo(name ="value")
    public String value;


    @ColumnInfo(name ="tag")
    public String tag;



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

    public String getReferenceId() {
        return referenceId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }
}
