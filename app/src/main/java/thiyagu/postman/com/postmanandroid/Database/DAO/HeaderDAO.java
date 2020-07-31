package thiyagu.postman.com.postmanandroid.Database.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

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

    @Query("DELETE FROM header")
    public void nukeHeader();


    @Query("SELECT * FROM header where flag='false'")
    public List<Header> getHeadersFlagBased();

}