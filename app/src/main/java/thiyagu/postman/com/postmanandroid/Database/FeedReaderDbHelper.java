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
    private static final String TABLE_NAME_PARAM = "param";
    private static final String TABLE_NAME_BODY = "body";
    private static final String TABLE_NAME_RESPONSE = "response";
    private static final String COLUMN_RESPONSE = "columnresponse";
    private static final String TABLE_NAME_HEADER = "header";
    private static final String ID = "id";
    private static final String COLUMN_KEY = "key";
    private static final String COLUMN_FLAG = "flag";
    private static final String COLUMN_VALUE = "value";

    private static final String SQL_CREATE_TABLE_PARAM =
            "CREATE TABLE " + FeedReaderDbHelper.TABLE_NAME_PARAM + " (" +
                    FeedReaderDbHelper.ID + " INTEGER PRIMARY KEY," +
                    FeedReaderDbHelper.COLUMN_KEY + " TEXT," +
                    FeedReaderDbHelper.COLUMN_FLAG + " TEXT," +
                    FeedReaderDbHelper.COLUMN_VALUE + " TEXT)";


    private static final String SQL_CREATE_TABLE_RESPONSE =
            "CREATE TABLE " + FeedReaderDbHelper.TABLE_NAME_RESPONSE + " (" +
                    FeedReaderDbHelper.ID + " INTEGER PRIMARY KEY," +
                    FeedReaderDbHelper.COLUMN_RESPONSE + " TEXT)";


    private static final String SQL_CREATE_TABLE_BODY =
            "CREATE TABLE " + FeedReaderDbHelper.TABLE_NAME_BODY + " (" +
                    FeedReaderDbHelper.ID + " INTEGER PRIMARY KEY," +
                    FeedReaderDbHelper.COLUMN_KEY + " TEXT," +
                    FeedReaderDbHelper.COLUMN_FLAG + " TEXT," +
                    FeedReaderDbHelper.COLUMN_VALUE + " TEXT)";

    private static final String SQL_CREATE_TABLE_HEADER =
            "CREATE TABLE " + FeedReaderDbHelper.TABLE_NAME_HEADER + " (" +
                    FeedReaderDbHelper.ID + " INTEGER PRIMARY KEY," +
                    FeedReaderDbHelper.COLUMN_KEY + " TEXT," +
                    FeedReaderDbHelper.COLUMN_FLAG + " TEXT," +
                    FeedReaderDbHelper.COLUMN_VALUE + " TEXT)";


    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_PARAM);
        db.execSQL(SQL_CREATE_TABLE_BODY);
        db.execSQL(SQL_CREATE_TABLE_HEADER);
        db.execSQL(SQL_CREATE_TABLE_RESPONSE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PARAM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_BODY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_HEADER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_RESPONSE);
        onCreate(db);


    }

    public void addEntryParam(DataPojoClass dataPojoClass) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_KEY, dataPojoClass.getKey());
        values.put(COLUMN_VALUE, dataPojoClass.getValue());
        values.put(COLUMN_FLAG, dataPojoClass.getFlag());
        db.insert(TABLE_NAME_PARAM, null, values);
        PrintAllParam();
        db.close();


    }

    public void addEntryBody(DataPojoClass dataPojoClass) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_KEY, dataPojoClass.getKey());
        values.put(COLUMN_VALUE, dataPojoClass.getValue());
        values.put(COLUMN_FLAG, dataPojoClass.getFlag());
        db.insert(TABLE_NAME_BODY, null, values);
        PrintAllBody();
        db.close();


    }

    public void addEntryHeader(DataPojoClass dataPojoClass) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_KEY, dataPojoClass.getKey());
        values.put(COLUMN_VALUE, dataPojoClass.getValue());
        values.put(COLUMN_FLAG, dataPojoClass.getFlag());
        db.insert(TABLE_NAME_HEADER, null, values);
        PrintAllParamHeader();
        db.close();


    }


    public void addResponse(ResponsePojoClass responsePojoClass) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RESPONSE, responsePojoClass.getResponse());
        db.insert(TABLE_NAME_RESPONSE, null, values);
        db.close();
        PrintResponse();

    }


    public ArrayList<String> getAllParam() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_PARAM, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            //array_list.add(res.getString(res.getColumnIndex(COLUMN_KEY)) +"@@"+res.getString(res.getColumnIndex(COLUMN_VALUE))+"@@"+res.getString(res.getColumnIndex(COLUMN_FLAG)));

            array_list.add(res.getString(res.getColumnIndex(ID)) + "@@" + res.getString(res.getColumnIndex(COLUMN_KEY)) + "@@" + res.getString(res.getColumnIndex(COLUMN_VALUE)));
            res.moveToNext();
        }
        db.close();
        return array_list;

    }


    public ArrayList<String> getAllBody() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_BODY, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            //array_list.add(res.getString(res.getColumnIndex(COLUMN_KEY)) +"@@"+res.getString(res.getColumnIndex(COLUMN_VALUE))+"@@"+res.getString(res.getColumnIndex(COLUMN_FLAG)));

            array_list.add(res.getString(res.getColumnIndex(ID)) + "@@" + res.getString(res.getColumnIndex(COLUMN_KEY)) + "@@" + res.getString(res.getColumnIndex(COLUMN_VALUE)));
            res.moveToNext();
        }
        db.close();
        return array_list;
    }

    public ArrayList<String> getAllHeader() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_HEADER, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            //array_list.add(res.getString(res.getColumnIndex(COLUMN_KEY)) +"@@"+res.getString(res.getColumnIndex(COLUMN_VALUE))+"@@"+res.getString(res.getColumnIndex(COLUMN_FLAG)));

            array_list.add(res.getString(res.getColumnIndex(ID)) + "@@" + res.getString(res.getColumnIndex(COLUMN_KEY)) + "@@" + res.getString(res.getColumnIndex(COLUMN_VALUE)));
            res.moveToNext();
        }
        db.close();
        return array_list;
    }


    public void PrintAllParam() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_PARAM, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(ID)));
            res.moveToNext();
        }
        db.close();
        Log.v("thisisallcontent", array_list.toString());
    }

    public void PrintResponse() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_RESPONSE, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(COLUMN_RESPONSE)));
            res.moveToNext();
        }
        db.close();
        Log.v("thisisallcontent", array_list.toString());
    }

    public void PrintAllBody() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_BODY, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(ID)));
            res.moveToNext();
        }
        db.close();
        Log.v("thisisallcontent", array_list.toString());
    }


    public void PrintAllParamHeader() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_HEADER, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(ID)));
            res.moveToNext();
        }
        db.close();
        Log.v("thisisallcontent", array_list.toString());
    }

    public void DeleteOldRecords() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + TABLE_NAME_PARAM);

        db.close();
    }

    public void DeleteSingleRecParam(int id) {
        PrintAllParam();

        SQLiteDatabase db = this.getReadableDatabase();

        db.execSQL("delete from " + TABLE_NAME_PARAM + " where id = " + id);
        PrintAllParam();
        db.close();

    }

    public void DeleteSingleRecBody(int id) {
        PrintAllParam();

        SQLiteDatabase db = this.getReadableDatabase();

        db.execSQL("delete from " + TABLE_NAME_BODY + " where id = " + id);
        PrintAllParam();
        db.close();

    }

    public void DeleteSingleRecHeader(int id) {
        PrintAllParam();

        SQLiteDatabase db = this.getReadableDatabase();

        db.execSQL("delete from " + TABLE_NAME_HEADER + " where id = " + id);
        PrintAllParam();

        db.close();
    }
}
