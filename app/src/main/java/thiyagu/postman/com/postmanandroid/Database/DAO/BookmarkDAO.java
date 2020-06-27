package thiyagu.postman.com.postmanandroid.Database.DAO;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import thiyagu.postman.com.postmanandroid.Database.Bookmarks;

@Dao
public interface BookmarkDAO {

    @Insert
    public void insert(Bookmarks... bookmarks);

    @Update
    public void update(Bookmarks... bookmarks);

    @Delete
    public void delete(Bookmarks bookmarks);

    @Query("SELECT * FROM bookmark")
    public List<Bookmarks> getBookMark();

    @Query("select DISTINCT date FROM bookmark")
    public  List<String> getDate();
    //
    @Query("select * FROM bookmark where date = :date")
    public List<Bookmarks> getBookMarkByDate(String date);
}
