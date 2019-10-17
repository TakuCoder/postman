package thiyagu.postman.com.postmanandroid.Database.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import thiyagu.postman.com.postmanandroid.Database.Body;

@Dao
public interface BodyDAO {


    @Insert
    public void insert(Body... body);

    @Update
    public void update(Body... body);

    @Delete
    public void delete(Body body);

    @Query("SELECT * FROM body")
    public List<Body> getBody();

    @Query("update body set flag=:flag where referenceid=:tag")
    public void updateBody(String flag,String tag);
}
