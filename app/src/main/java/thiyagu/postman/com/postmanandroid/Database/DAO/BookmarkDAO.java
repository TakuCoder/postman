package thiyagu.postman.com.postmanandroid.Database.DAO;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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

    @Query("select DISTINCT date FROM history")
    public  List<String> getDate();
    //
    @Query("select * FROM history where date = :date")
    public List<Bookmarks> getBookMarkByDate(String date);
}
