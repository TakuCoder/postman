package thiyagu.postman.com.postmanandroid.Database.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import thiyagu.postman.com.postmanandroid.Database.parameters;

@Dao
public interface ParametersDAO {


    @Insert
    public void insert(parameters... body);

    @Update
    public void update(parameters... body);

    @Delete
    public void delete(parameters body);

    @Query("SELECT * FROM parameters")
    public List<parameters> getParam();

    @Query("DELETE FROM parameters where tag= :tag")
    public void deleteParam(String tag);


    @Query("UPDATE parameters set flag =:flag where tag= :tag")
    public void updateParam(String flag,String tag);
}
