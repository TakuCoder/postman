package thiyagu.postman.com.postmanandroid.Database.CollectionsDAO;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "info",indices = {@Index(value = {"_postman_id"},
        unique = true)})
public class InfoTable {

    @PrimaryKey(autoGenerate = true)
    private int uid;


    @ColumnInfo(name = "_postman_id")
    @NonNull
    private String _postman_id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "schema")
    private String schema;

    @ColumnInfo(name = "no_of_data")
    private int no_of_data;


    public int getNo_of_data() {
        return no_of_data;
    }

    public void setNo_of_data(int no_of_data) {
        this.no_of_data = no_of_data;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String get_postman_id() {
        return _postman_id;
    }

    public void set_postman_id(String _postman_id) {
        this._postman_id = _postman_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }
}
