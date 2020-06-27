package thiyagu.postman.com.postmanandroid.Database.CollectionsDAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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


    @Query("SELECT * from item where _postman_id = :postman_id AND name = :name")
    public List<ItemTable> getItemByPostIdandName(String postman_id,String name);


    @Query("SELECT * from item where _postman_id = :postman_id")
    public List<ItemTable> getItemByPostId(String postman_id);
}
