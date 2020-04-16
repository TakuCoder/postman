package thiyagu.postman.com.postmanandroid.Database.CollectionsDAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import thiyagu.postman.com.postmanandroid.Database.CollectionsDAO.ItemTable;
import java.util.List;

@Dao
public interface ItemDAO {

    @Insert
    public void Insert(ItemTable... itemTables);


    @Update
    public void update(ItemTable... body);

    @Delete
    public void delete(ItemTable body);

    @Query("SELECT * FROM item")
    public List<ItemTable> getItem();

}