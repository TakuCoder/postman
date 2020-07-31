package thiyagu.postman.com.postmanandroid.Database.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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

    @Query("SELECT * FROM parameters where flag = 'false'")
    public List<parameters> getParamFlagBased();

    @Query("DELETE FROM parameters where tag= :tag")
    public void deleteParam(String tag);


    @Query("UPDATE parameters set flag =:flag where tag= :tag")
    public void updateParam(String flag,String tag);

    @Query("DELETE FROM parameters")
    public void nukeParams();

}
