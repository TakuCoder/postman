package thiyagu.postman.com.postmanandroid.Database.DAO;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import thiyagu.postman.com.postmanandroid.Database.History;

@Dao
public interface HistoryDAO {

    @Insert
    public void insert(History... history);

    @Update
    public void update(History... body);

    @Delete
    public void delete(History body);

    @Query("SELECT * FROM history")
    public List<History> getHistory();

    @Query("select DISTINCT date FROM history")
    public  List<String> getDate();
//
    @Query("select * FROM history where date = :date")
    public List<History> getHistoryByDate(String date);


}
