package com.example.bankingsystemandroid;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Vinay on 8/13/2019.
 */
public class LoginTimeDetailsManager extends SQLiteOpenHelper {
    //database name and version
    private static final String DATABASE_NAME = "LoginDetailsTable";
    private static final int DATABASE_VERSION = 1;

    // table name and table creator string (SQL statement to create the table)
    // should be set from within main activity
    private static String tableName;
    private static String tableCreatorString;
    //
    // no-argument constructor
    public LoginTimeDetailsManager(Context context)
    {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }
    // Called when the database is created for the first time.
    // This is where the creation of tables and the initial population
    // of the tables should happen.
    @Override
    public void onCreate(SQLiteDatabase db) {
        //create the table
        db.execSQL(tableCreatorString);
    }

    // Called when the database needs to be upgraded.
    // The implementation should use this method to drop tables,
    // add tables, or do anything else it needs to upgrade
    // to the new schema version.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop table if existed
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        //recreate the table
        onCreate(db);
    }
    //
    // initialize table names and CREATE TABLE statement
    // called by activity to create a table in the database
    // The following arguments should be passed:
    // tableName - a String variable which holds the table name
    // tableCreatorString - a String variable which holds the CREATE Table statement
    //
    public void dbInitialize(String tableName, String tableCreatorString)
    {
        this.tableName=tableName;
        this.tableCreatorString=tableCreatorString;
    }

    // CRUD Operations
    //This method is called by the activity to add a row in the table
    // The following arguments should be passed:
    // values - a ContentValues object that holds row values

    public boolean addRecord  (ContentValues values) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        // Insert the row
        long nr= db.insert(tableName, null, values);
        db.close(); //close database connection
        return nr> -1;
    }
    //This method returns a flower object which holds a row with the given flower code
    // The following argument should be passed:
    // code - an Object which holds the primary key value
    // fieldName - the  name of the primary key field

    public ArrayList<String> ShowLoginDetails() throws Exception {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + tableName , null);
        LoginTimeDetails f = new LoginTimeDetails(); //create a new flower object
        ArrayList<String> timedata=new ArrayList();
        if (cursor != null) {
            // move cursor to first row
            if (cursor.moveToFirst()) {
                do {
                    // Get version from Cursor
                    String time = cursor.getString(cursor.getColumnIndex("loginTime"));

                    // add the bookName into the bookTitles ArrayList
                    timedata.add(time);
                    // move to next row
                } while (cursor.moveToNext());
            }


           /* if (cursor.moveToFirst()) { //if row exists
                cursor.moveToFirst(); //move to first row
                //initialize the instance variables of flower object
                f.setId(cursor.getString(0));
                f.setLoginTime(cursor.getString(1));
                cursor.close();*/

        } else
            f = null;


        db.close();
        return timedata;

    }
    //
    // The following argument should be passed:
    // code - an Object which holds the primary key value
    // fieldName - the  name of the primary key field
    // values - a ContentValues object that holds row values
    //


}

