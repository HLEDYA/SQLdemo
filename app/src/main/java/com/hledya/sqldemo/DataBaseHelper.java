package com.hledya.sqldemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(@Nullable Context context) {
        super(context, "customer.db", null, 1);
    }

    // this is called first time a db is accessed. There should be code in here to create a new db.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE  CUSTOMER_TABLE "+
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, AGE INT, ACTIVE BOOL)";
        db.execSQL(createTableStatement);
    }

    // this is called if db version is changes.
    // It prevents previous user apps from breaking when you change the db design.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(CustomerModel customerModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("NAME", customerModel.getName());
        cv.put("AGE", customerModel.getAge());
        cv.put("ACTIVE", customerModel.isActive());

        long insert = db.insert("CUSTOMER_TABLE", null, cv);
        if (insert == -1)
            return false;

        return true;
    }

}
