package thiyagu.postman.com.postmanandroid.Database.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import thiyagu.postman.com.postmanandroid.Database.Authorization;

@Dao
public interface AuthorizationDAO {


    @Insert
    public void insert(Authorization... body);

    @Update
    public void update(Authorization... body);

    @Delete
    public void delete(Authorization body);

    @Query("SELECT * FROM authorization")
    public List<Authorization> getBody();
}
