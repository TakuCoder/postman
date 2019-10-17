package thiyagu.postman.com.postmanandroid.Database.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import thiyagu.postman.com.postmanandroid.Database.Authorization;
import thiyagu.postman.com.postmanandroid.Database.Header;

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
