package thiyagu.postman.com.postmanandroid;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import thiyagu.postman.com.postmanandroid.Database.FeedReaderDbHelper;

@Module
public class MyDatabaseModule {
    private Context context;

    public MyDatabaseModule(Context context) {
        this.context = context;

    }

    @Provides
    @MyApplicationScope
    FeedReaderDbHelper databaseReference() {

        return new FeedReaderDbHelper(context);
    }

}
