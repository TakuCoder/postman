package thiyagu.postman.com.postmanandroid.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.TypeConverters;

import thiyagu.postman.com.postmanandroid.Converters.DateTypeConverter;
import thiyagu.postman.com.postmanandroid.Database.DAO.BodyDAO;
import thiyagu.postman.com.postmanandroid.Database.DAO.BookmarkDAO;
import thiyagu.postman.com.postmanandroid.Database.DAO.HeaderDAO;
import thiyagu.postman.com.postmanandroid.Database.DAO.HistoryDAO;
import thiyagu.postman.com.postmanandroid.Database.DAO.ParametersDAO;

@Database(entities = {Authorization.class,Body.class,Header.class,parameters.class,History.class,Bookmarks.class},version = 1,exportSchema = false)
@TypeConverters({DateTypeConverter.class})
public abstract class RoomDatabase extends android.arch.persistence.room.RoomDatabase {

    public abstract BodyDAO getbodyDAO();
    public abstract HeaderDAO getHeaderDAO();
    public abstract ParametersDAO getParametersDAO();
    public abstract HistoryDAO getHistoryDAO();
    public abstract BookmarkDAO getBookmarkDAO();
}
