package thiyagu.postman.com.postmanandroid.Database.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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

    @Query("SELECT * FROM body where flag ='true'")
    public List<Body> getBodyFlagged();

}
