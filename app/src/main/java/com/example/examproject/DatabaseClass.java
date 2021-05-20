package com.example.examproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseClass extends SQLiteOpenHelper {

    Context context;
    private static final String DatabaseName="MyPlants";
    private static final int DatabaseVersion=1;

    private static final String TableName="myplants";
    private static final String ColumnId="id";
    private static final String ColumnName="name";
    private static final String ColumnDescription="description";

    public DatabaseClass(@Nullable Context context) {
        super(context, DatabaseName, null, DatabaseVersion);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TableName + " (" + ColumnId  + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ColumnName + " TEXT, " +
                ColumnDescription + " TEXT);";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TableName);
        onCreate(db);
    }
    void addPlants(String name, String description) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(ColumnName, name);
        cv.put(ColumnDescription, description);

        long resultValue = db.insert(TableName, null, cv);

        if (resultValue == -1) {
            Toast.makeText(context, "Oops, couldn't add data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Plant Added Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TableName;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = null;

        if(database != null){
            cursor = database.rawQuery(query, null);
        }
        return cursor;
    }
    void updatePlant(String name, String description, String id){
        SQLiteDatabase database = this.getWritableDatabase();

        //to put data to it.
        ContentValues cv = new ContentValues();
        cv.put(ColumnName, name);
        cv.put(ColumnDescription, description);

        //store query result and update
        long result = database.update(TableName, cv, "id=?", new String[]{id});
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
        }
    }
    public void deletePlant(String id) {
        SQLiteDatabase database = this.getWritableDatabase();

        long result = database.delete(TableName, "id=?", new String[]{id});
        if (result == -1) {
            Toast.makeText(context, "Oops, item Not Deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Item Deleted Successfully!", Toast.LENGTH_SHORT).show();
        }
    }
}
