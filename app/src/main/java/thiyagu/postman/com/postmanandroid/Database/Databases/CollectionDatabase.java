package thiyagu.postman.com.postmanandroid.Database.Databases;

import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import thiyagu.postman.com.postmanandroid.Converters.DateTypeConverter;
import thiyagu.postman.com.postmanandroid.Database.CollectionsDAO.InfoDAO;
import thiyagu.postman.com.postmanandroid.Database.CollectionsDAO.InfoTable;
import thiyagu.postman.com.postmanandroid.Database.CollectionsDAO.ItemDAO;
import thiyagu.postman.com.postmanandroid.Database.CollectionsDAO.ItemTable;

@Database( entities= {InfoTable.class, ItemTable.class},version = 1,exportSchema = false)
@TypeConverters({DateTypeConverter.class})
public abstract class CollectionDatabase extends RoomDatabase {

    public abstract InfoDAO getInfoDAO();
    public abstract ItemDAO getItemDAO();

}
