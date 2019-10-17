package thiyagu.postman.com.postmanandroid.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.TypeConverters;

import thiyagu.postman.com.postmanandroid.Converters.DateTypeConverter;
import thiyagu.postman.com.postmanandroid.Database.DAO.BodyDAO;
import thiyagu.postman.com.postmanandroid.Database.DAO.HeaderDAO;
import thiyagu.postman.com.postmanandroid.Database.DAO.ParametersDAO;

@Database(entities = {Authorization.class,Body.class,Header.class,parameters.class},version = 1,exportSchema = false)
@TypeConverters({DateTypeConverter.class})
public abstract class RoomDatabase extends android.arch.persistence.room.RoomDatabase {

    public abstract BodyDAO getbodyDAO();
    public abstract HeaderDAO getHeaderDAO();
    public abstract ParametersDAO getParametersDAO();
}
