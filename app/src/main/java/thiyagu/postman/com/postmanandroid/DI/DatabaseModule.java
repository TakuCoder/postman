package thiyagu.postman.com.postmanandroid.DI;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import thiyagu.postman.com.postmanandroid.Database.CollectionsDAO.InfoDAO;
import thiyagu.postman.com.postmanandroid.Database.CollectionsDAO.ItemDAO;
import thiyagu.postman.com.postmanandroid.Database.Databases.CollectionDatabase;

@Module
public class DatabaseModule {


    @ApplicationContext
    private final Context mContext;



    public DatabaseModule(@ApplicationContext Context context) {

        this.mContext = context;

    }

    @Singleton
    @Provides
    CollectionDatabase provideDatabase() {


        return Room.databaseBuilder(mContext, CollectionDatabase.class, "collection_db")
                .allowMainThreadQueries()   //Allows room to do operation on main thread
                .build();
    }


//    @Singleton
//    @Provides
//    TelleriumDataDatabase provideTelleriumDatabase() {
//
//
//        return Room.databaseBuilder(mContext, TelleriumDataDatabase.class, "data_db")
//                .allowMainThreadQueries()   //Allows room to do operation on main thread
//                .build();
//    }

    @Singleton
    @Provides
    InfoDAO provideInfoDAO(CollectionDatabase collectionDatabase) {
        return collectionDatabase.getInfoDAO();

    }



    @Singleton
    @Provides
    ItemDAO provideItemDAO(CollectionDatabase collectionDatabase) {
        return collectionDatabase.getItemDAO();

    }

}
