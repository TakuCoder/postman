package thiyagu.postman.com.postmanandroid.Database.CollectionsDAO;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

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
