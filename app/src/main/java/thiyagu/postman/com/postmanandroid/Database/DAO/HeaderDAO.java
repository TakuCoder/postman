package thiyagu.postman.com.postmanandroid.Database.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import thiyagu.postman.com.postmanandroid.Database.Body;
import thiyagu.postman.com.postmanandroid.Database.Header;

@Dao
public interface HeaderDAO {


    @Insert
    public void insert(Header... headers);

    @Update
    public void update(Header... headers);

    @Delete
    public void delete(Header headers);

    @Query("SELECT * FROM header")
    public List<Header> getHeaders();

    @Query("DELETE from header where tag =:tag")
    public void DeleteHeader(String tag);


    @Query("UPDATE header set flag=:flag where tag=:tag")
    public  void updateHeader(String flag,String tag);

}