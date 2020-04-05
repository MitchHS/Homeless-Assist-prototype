package com.u3100289.homelessAssist;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

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
    public static final String USER_COLUMN_TYPE = "userType";
    public static final String USER_COLUMN_NAME = "name";
    public static final String USER_COLUMN_LASTNAME = "lastName";
    public static final String USER_COLUMN_STREET_NUMBER = "streetNumber";
    public static final String USER_COLUMN_STREET_NAME = "streetName";
    public static final String USER_COLUMN_SUBURB = "suburb";
    public static final String USER_COLUMN_POSTCODE = "postcode";

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + USER_TABLE_NAME + "(" +
                USER_COLUMN_ID + "integer primary key, " +
                USER_COLUMN_EMAIL + " text, " +
                USER_COLUMN_PASSWORD + " text, " +
                USER_COLUMN_TYPE + " integer, " +
                USER_COLUMN_NAME + " text, " +
                USER_COLUMN_LASTNAME + " text," +
                USER_COLUMN_STREET_NUMBER + " integer, " +
                USER_COLUMN_STREET_NAME + " text, " +
                USER_COLUMN_SUBURB + " text, " +
                USER_COLUMN_POSTCODE + " integer)"
        );


        db.execSQL("create table " + RESOURCE_TABLE_NAME + "(" +
                RESOURCE_COLUMN_ID + " integer primary key, " +
                RESOURCE_COLUMN_TYPE + " text, " +
                RESOURCE_COLUMN_DESCRIPTION + " text, " +
                RESOURCE_COLUMN_PLACEID + " text, " +
                RESOURCE_COLUMN_ADDRESS + " text, " +
                RESOURCE_COLUMN_QUANTITY + " integer, " +
                RESOURCE_COLUMN_USERID + " integer, " +
                " FOREIGN KEY ("+RESOURCE_COLUMN_USERID+") REFERENCES "+USER_TABLE_NAME+"("+USER_COLUMN_ID+")) ");

//                COLUMN_URI + " integer, " +
//                COLUMN_CONTENT + " text)" );
                //+ TASK_CAT + " integer,"
                //        + " FOREIGN KEY ("+TASK_CAT+") REFERENCES "+CAT_TABLE+"("+CAT_ID+"));";


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists " + USER_TABLE_NAME);
        db.execSQL("drop table if exists " + RESOURCE_TABLE_NAME);
        onCreate(db);


    }

    public ArrayList<Resource> getAllResources()
    {
        ArrayList<Resource> resourceList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + RESOURCE_TABLE_NAME, null);
        res.moveToFirst();

        while(res.isAfterLast() == false)
        {
            Resource aResource = new Resource(
                    res.getString(res.getColumnIndex(RESOURCE_COLUMN_ID)),
                    res.getString(res.getColumnIndex(RESOURCE_COLUMN_TYPE)),
                    res.getString(res.getColumnIndex(RESOURCE_COLUMN_DESCRIPTION)),
                    res.getString(res.getColumnIndex(RESOURCE_COLUMN_PLACEID)),
                    res.getString(res.getColumnIndex(RESOURCE_COLUMN_ADDRESS)),
                    res.getInt(res.getColumnIndex((RESOURCE_COLUMN_QUANTITY))),
                    res.getString(res.getColumnIndex(RESOURCE_COLUMN_USERID)));
            resourceList.add(aResource);
            res.moveToNext();

        }
        return resourceList;
    }

    public String insertUser(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COLUMN_EMAIL, user.getEmail());
        contentValues.put(USER_COLUMN_PASSWORD, user.getPassword());
        contentValues.put(USER_COLUMN_TYPE, user.getUserType());
        contentValues.put(USER_COLUMN_NAME, user.getName());
        contentValues.put(USER_COLUMN_LASTNAME, user.getLastName());
        contentValues.put(USER_COLUMN_STREET_NUMBER, user.getStreetNumber());
        contentValues.put(USER_COLUMN_STREET_NAME, user.getStreetName());
        contentValues.put(USER_COLUMN_SUBURB, user.getSuburb());
        contentValues.put(USER_COLUMN_POSTCODE, user.getPostcode());

        long id = db.insert(USER_TABLE_NAME, null, contentValues);
        return Long.toString(id);

    }

    public String insertResource (Resource resource)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COLUMN_TYPE, resource.getType());
        contentValues.put(RESOURCE_COLUMN_DESCRIPTION, resource.getDescription());
        contentValues.put(RESOURCE_COLUMN_PLACEID, resource.placeID);
        contentValues.put(RESOURCE_COLUMN_ADDRESS, resource.getAddress());
        contentValues.put(RESOURCE_COLUMN_QUANTITY, resource.getQuanity());
        contentValues.put(RESOURCE_COLUMN_QUANTITY, resource.getUserID());

        long id = db.insert(RESOURCE_TABLE_NAME, null, contentValues);
        return Long.toString(id);

    }

    /*


    public String insertEvent(Images event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, event.getTitle());
        contentValues.put(COLUMN_URI, event.getUri());
        contentValues.put(COLUMN_CONTENT, event.getContent());
        long id = db.insert(TABLE_NAME, null, contentValues);
        return Long.toString(id);

    }
     */





}
