package thiyagu.postman.com.postmanandroid.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by thiyagu on 3/20/2018.
 */

public class FeedReaderDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mydata.db";
    private static final String TABLE_NAME = "Entry";
    private static final String ID = "id";
    private static final String COLUMN_KEY = "key";
    private static final String COLUMN_FLAG = "flag";
    private static final String COLUMN_VALUE = "value";

    private static final String SQL_CREATE_ENTRIES =
                    "CREATE TABLE " + FeedReaderDbHelper.TABLE_NAME + " (" +
                    FeedReaderDbHelper.ID + " INTEGER PRIMARY KEY," +
                    FeedReaderDbHelper.COLUMN_KEY + " TEXT," +
                    FeedReaderDbHelper.COLUMN_FLAG + " TEXT," +
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

    public void addEntry(DataPojoClass dataPojoClass)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_KEY,dataPojoClass.getKey());
        values.put(COLUMN_VALUE,dataPojoClass.getValue());
        values.put(COLUMN_FLAG,dataPojoClass.getFlag());
        db.insert(TABLE_NAME,null,values);
        PrintAllData();
        db.close();


    }
 public ArrayList<String> getAllCotacts() {
    ArrayList<String> array_list = new ArrayList<String>();

    //hp = new HashMap();
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor res =  db.rawQuery( "select * from "+TABLE_NAME, null );
    res.moveToFirst();

    while(res.isAfterLast() == false){
        //array_list.add(res.getString(res.getColumnIndex(COLUMN_KEY)) +"@@"+res.getString(res.getColumnIndex(COLUMN_VALUE))+"@@"+res.getString(res.getColumnIndex(COLUMN_FLAG)));

        array_list.add(res.getString(res.getColumnIndex(ID))+"@@"+res.getString(res.getColumnIndex(COLUMN_KEY)) +"@@"+res.getString(res.getColumnIndex(COLUMN_VALUE)));
        res.moveToNext();
    }
    return array_list;
}

    public void PrintAllData() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(ID)));
            res.moveToNext();
        }
        Log.v("thisisallcontent",array_list.toString());
    }
public void DeleteOldRecords()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);


    }

    public void DeleteSingleRec(int id)
    {
        PrintAllData();
        Log.v("stausssss","beforeeeeeeeeeeeeeeeeeeee");
        SQLiteDatabase db = this.getReadableDatabase();

        db.execSQL("delete from "+ TABLE_NAME +" where id = "+id);
        PrintAllData();
        Log.v("stausssss","Afterrrrrrrrrrrrrrrrrrrrrr");





    }
}
