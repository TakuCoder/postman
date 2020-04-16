package thiyagu.postman.com.postmanandroid.Database.Databases;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.TypeConverters;

import thiyagu.postman.com.postmanandroid.Converters.DateTypeConverter;
import thiyagu.postman.com.postmanandroid.Database.Authorization;
import thiyagu.postman.com.postmanandroid.Database.Body;
import thiyagu.postman.com.postmanandroid.Database.Bookmarks;
import thiyagu.postman.com.postmanandroid.Database.DAO.BodyDAO;
import thiyagu.postman.com.postmanandroid.Database.DAO.BookmarkDAO;
import thiyagu.postman.com.postmanandroid.Database.DAO.HeaderDAO;
import thiyagu.postman.com.postmanandroid.Database.DAO.HistoryDAO;
import thiyagu.postman.com.postmanandroid.Database.DAO.ParametersDAO;
import thiyagu.postman.com.postmanandroid.Database.Header;
import thiyagu.postman.com.postmanandroid.Database.History;
import thiyagu.postman.com.postmanandroid.Database.parameters;

@Database(entities = {Authorization.class, Body.class, Header.class, parameters.class, History.class, Bookmarks.class},version = 1,exportSchema = false)
@TypeConverters({DateTypeConverter.class})
public abstract class TelleriumDataDatabase extends android.arch.persistence.room.RoomDatabase {

    public abstract BodyDAO getbodyDAO();
    public abstract HeaderDAO getHeaderDAO();
    public abstract ParametersDAO getParametersDAO();
    public abstract HistoryDAO getHistoryDAO();
    public abstract BookmarkDAO getBookmarkDAO();
}
