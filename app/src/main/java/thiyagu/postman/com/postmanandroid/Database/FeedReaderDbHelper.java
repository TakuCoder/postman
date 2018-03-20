package thiyagu.postman.com.postmanandroid.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by thiyagu on 3/20/2018.
 */

public class FeedReaderDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mydata.db";
    private static final String TABLE_NAME = "Entry";
    private static final String ID = "id";
    private static final String COLUMN_KEY = "key";

    private static final String COLUMN_VALUE = "value";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedReaderDbHelper.TABLE_NAME + " (" +
                    FeedReaderDbHelper.ID + " INTEGER PRIMARY KEY," +
                    FeedReaderDbHelper.COLUMN_KEY + " TEXT," +
                    FeedReaderDbHelper.COLUMN_VALUE + " TEXT)";



    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);


    }
}
