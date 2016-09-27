package com.example.sanoop.autocomplete.Database;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final String TAG = "DatabaseHandler.java";

    private static final int DATABASE_VERSION = 4;

    protected static final String DATABASE_NAME = "SampleDatabse";

    public String tableName = "sample";
    public String columnId = "id";
    public String coulumnName = "name";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "";

        sql += "CREATE TABLE " + tableName;
        sql += " ( ";
        sql += columnId + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += coulumnName + " TEXT ";
        sql += " ) ";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF EXISTS " + tableName;
        db.execSQL(sql);

        onCreate(db);
    }

    public boolean create(TextName textName) {

        boolean createSuccessful = false;

        if(!checkIfExists(textName.objectName)){

            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(coulumnName, textName.objectName);
            createSuccessful = db.insert(tableName, null, values) > 0;

            db.close();

            if(createSuccessful){
                Log.e(TAG, textName.objectName + " created.");
            }
        }

        return createSuccessful;
    }

    public boolean checkIfExists(String objectName){

        boolean recordExists = false;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + columnId + " FROM " + tableName + " WHERE " + coulumnName + " = '" + objectName + "'", null);

        if(cursor!=null) {

            if(cursor.getCount()>0) {
                recordExists = true;
            }
        }

        cursor.close();
        db.close();

        return recordExists;
    }

    public List<TextName> read(String searchTerm) {

        List<TextName> recordsList = new ArrayList<TextName>();

        String sql = "";
        sql += "SELECT * FROM " + tableName;
        sql += " WHERE " + coulumnName + " LIKE '%" + searchTerm + "%'";
        sql += " ORDER BY " + columnId + " DESC";
        sql += " LIMIT 0,5";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {

                String objectName = cursor.getString(cursor.getColumnIndex(coulumnName));
                TextName textName = new TextName(objectName);

                recordsList.add(textName);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }

}