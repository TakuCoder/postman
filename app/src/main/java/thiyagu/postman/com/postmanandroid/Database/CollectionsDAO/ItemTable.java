package thiyagu.postman.com.postmanandroid.Database.CollectionsDAO;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "item")
public class ItemTable {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "_postman_id")
    @NonNull
    private String _postman_id;

    @ColumnInfo(name = "name")
    @NonNull
    private String name;

    @ColumnInfo(name = "request")
    private String request;

    @ColumnInfo(name = "response")
    private String response;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }


    public void set_postman_id(@NonNull String _postman_id) {
        this._postman_id = _postman_id;
    }

    @NonNull
    public String get_postman_id() {
        return _postman_id;
    }
}
