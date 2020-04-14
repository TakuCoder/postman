package thiyagu.postman.com.postmanandroid.Database.DAO;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;

import thiyagu.postman.com.postmanandroid.Database.Authorization;
import thiyagu.postman.com.postmanandroid.Database.Data_value;
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
