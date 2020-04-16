package thiyagu.postman.com.postmanandroid.Database.CollectionsDAO;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import thiyagu.postman.com.postmanandroid.Database.Body;

@Dao
public interface InfoDAO {

    @Insert
    public void Insert(InfoTable... infoTables);


    @Update
    public void update(InfoTable... body);

    @Delete
    public void delete(InfoTable body);

    @Query("SELECT * FROM info")
    public List<InfoTable> getInfo();

//    @Query("update body set flag=:flag where referenceid=:tag")
//    public void updateBody(String flag,String tag);
//
//    @Query("SELECT * FROM body where flag ='true'")
//    public List<Body> getBodyFlagged();

}
