package com.hledya.sqldemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

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

    public List<CustomerModel> getEveryone(){
        List<CustomerModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM CUSTOMER_TABLE";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()){
            // loop through the cursor (result set) and create new customer objects. Put them into the return list.
            do{
                int customerID = cursor.getInt(0);
                String customerName = cursor.getString(1);
                int customerAge = cursor.getInt(2);
                boolean customerActive = cursor.getInt(3) == 1 ? true: false;
                CustomerModel newCustomer = new CustomerModel(customerID, customerName, customerAge,customerActive);
                returnList.add(newCustomer);
            } while(cursor.moveToNext());
        } else{

        }
        // close both the cursor and the db when done
        cursor.close();
        db.close();
        return returnList;
    }

    public boolean deleteOne(CustomerModel customerModel){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString ="Delete from CUSTOMER_TABLE where ID="+customerModel.getId();

        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToNext()){
            return true;
        }else{
            return false;
        }
    }

}
