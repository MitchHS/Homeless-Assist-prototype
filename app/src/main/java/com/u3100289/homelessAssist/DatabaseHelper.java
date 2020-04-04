package com.u3100289.homelessAssist;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Resource table
    public static final String RESOURCE_TABLE_NAME = "resources";
    public static final String RESOURCE_COLUMN_ID = "id";
    public static final String RESOURCE_COLUMN_TYPE = "type";
    public static final String RESOURCE_COLUMN_DESCRIPTION = "description";
    public static final String RESOURCE_COLUMN_PLACEID = "placeId";
    public static final String RESOURCE_COLUMN_ADDRESS = "address";
    public static final String RESOURCE_COLUMN_QUANTITY = "quantity";
    public static final String RESOURCE_COLUMN_USERID = "userId"; // FK

    // User Table
    public static final String USER_TABLE_NAME = "users";
    public static final String USER_COLUMN_ID = "userId";
    public static final String USER_COLUMN_EMAIL = "email";
    public static final String USER_COLUMN_PASSWORD = "password";
    public static final String USER_COLUMN_NAME = "name";
    public static final String USER_COLUMN_STREET_NUMBER = "streetNumber";
    public static final String USER_COLUMN_STREET_NAME = "streetName";
    public static final String USER_COLUMN_SUBURB = "suburb";
    public static final String USER_COLUMN_POSTCODE = "postcode";

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("create table " + TABLE_NAME + "(" +
//                COLUMN_ID + " integer primary key, " +
//                COLUMN_NAME + " text, " +
//                COLUMN_URI + " integer, " +
//                COLUMN_CONTENT + " text)" );


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

//        db.execSQL("drop table if exists " + TABLE_NAME);
//        onCreate(db);


    }





}
