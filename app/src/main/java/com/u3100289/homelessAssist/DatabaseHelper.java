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
    public static final String RESOURCE_COLUMN_SUBURB = "suburb";
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
    public static final String USER_COLUMN_CONTACTNO = "phone";
    public static final String USER_COLUMN_BUSINESS_NAME = "businessName";

    // Events
    public static final String EVENT_TABLE_NAME = "events";
    public static final String EVENT_ID = "id";
    public static final String EVENT_TITLE = "title";
    public static final String EVENT_DATE = "date";
    public static final String EVENT_DESCRIPTION = "description";
    public static final String EVENT_COLUMN_STREET_NUMBER = "streetNumber";
    public static final String EVENT_COLUMN_STREET_NAME = "streetName";
    public static final String EVENT_COLUMN_SUBURB = "suburb";
    public static final String EVENT_COLUMN_POSTCODE = "postcode";



    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + USER_TABLE_NAME + "(" +
                USER_COLUMN_ID + " integer primary key AUTOINCREMENT NOT NULL, " +
                USER_COLUMN_EMAIL + " text, " +
                USER_COLUMN_PASSWORD + " text, " +
                USER_COLUMN_TYPE + " integer, " +
                USER_COLUMN_NAME + " text, " +
                USER_COLUMN_LASTNAME + " text," +
                USER_COLUMN_CONTACTNO + " text, " +
                USER_COLUMN_STREET_NUMBER + " integer, " + // nullable
                USER_COLUMN_STREET_NAME + " text, " +// nullable
                USER_COLUMN_SUBURB + " text, " +
                USER_COLUMN_POSTCODE + " integer, " +
                USER_COLUMN_BUSINESS_NAME + " text )");



        db.execSQL("create table " + RESOURCE_TABLE_NAME + "(" +
                RESOURCE_COLUMN_ID +  " integer primary key AUTOINCREMENT NOT NULL, " +
                RESOURCE_COLUMN_TYPE + " text, " + // Dropdown box of types in the app
                RESOURCE_COLUMN_DESCRIPTION + " text, " + // User description of the resource
                RESOURCE_COLUMN_PLACEID + " text, " + // Google API Place id for street Address
                RESOURCE_COLUMN_SUBURB + " text, " + // place.getAddress()
                RESOURCE_COLUMN_QUANTITY + " integer, " + // Available quanity
                USER_COLUMN_BUSINESS_NAME + " integer, " +
                RESOURCE_COLUMN_USERID + " integer, " +
                " FOREIGN KEY ("+RESOURCE_COLUMN_USERID+") REFERENCES "+USER_TABLE_NAME+"("+USER_COLUMN_ID+")) "); // FK of user

        db.execSQL("create table " + EVENT_TABLE_NAME + "(" +
                EVENT_ID +  " integer primary key AUTOINCREMENT NOT NULL, " +
                EVENT_TITLE + " text, " +
                EVENT_DATE + " text, " +
                EVENT_DESCRIPTION + " text, " +
                EVENT_COLUMN_STREET_NUMBER + " integer, " +
                EVENT_COLUMN_STREET_NAME + " text, " +
                EVENT_COLUMN_SUBURB + " text, " +
                EVENT_COLUMN_POSTCODE + " integer )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists " + USER_TABLE_NAME);
        db.execSQL("drop table if exists " + RESOURCE_TABLE_NAME);
        db.execSQL("drop table if exists " + EVENT_TABLE_NAME);
        onCreate(db);


    }

    public ArrayList<Resource> getAllResources(Integer userID)
    {
        ArrayList<Resource> resourceList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + RESOURCE_TABLE_NAME + " where " + RESOURCE_COLUMN_USERID + "=" + userID;
        Cursor res = db.rawQuery(query, null);

        res.moveToFirst();

        while(res.isAfterLast() == false)
        {
            Resource aResource = new Resource(
                    res.getString(res.getColumnIndex(RESOURCE_COLUMN_ID)),
                    res.getString(res.getColumnIndex(RESOURCE_COLUMN_TYPE)),
                    res.getString(res.getColumnIndex(RESOURCE_COLUMN_DESCRIPTION)),
                    res.getString(res.getColumnIndex(RESOURCE_COLUMN_PLACEID)),
                    res.getString(res.getColumnIndex(RESOURCE_COLUMN_SUBURB)),
                    res.getInt(res.getColumnIndex((RESOURCE_COLUMN_QUANTITY))),
                    res.getString(res.getColumnIndex(USER_COLUMN_BUSINESS_NAME)),
                    res.getString(res.getColumnIndex(RESOURCE_COLUMN_USERID)));
            resourceList.add(aResource);
            res.moveToNext();

        }
        return resourceList;
    }

    public ArrayList<Event> getAllEvents()
    {
        ArrayList<Event> eventList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + EVENT_TABLE_NAME;
        Cursor res = db.rawQuery(query, null);

        res.moveToFirst();

        while(res.isAfterLast() == false)
        {
            Event aEvent = new Event(
                    res.getString(res.getColumnIndex(EVENT_ID)),
                    res.getString(res.getColumnIndex(EVENT_TITLE)),
                    res.getString(res.getColumnIndex(EVENT_DATE)),
                    res.getString(res.getColumnIndex(EVENT_DESCRIPTION)),
                    res.getInt(res.getColumnIndex(EVENT_COLUMN_STREET_NUMBER)),
                    res.getString(res.getColumnIndex((EVENT_COLUMN_STREET_NAME))),
                    res.getString(res.getColumnIndex(EVENT_COLUMN_SUBURB)),
                    res.getInt(res.getColumnIndex(EVENT_COLUMN_POSTCODE)));
            eventList.add(aEvent);
            res.moveToNext();

        }
        return eventList;
    }



    public boolean userExists(String email)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cs = db.query(USER_TABLE_NAME, new String[] {USER_COLUMN_EMAIL}, USER_COLUMN_EMAIL + "=" + "'" + email + "'", null, null, null, null);

        if(cs.moveToFirst()){return true;}
        return false;

    }

    public ArrayList<Resource> resourceQuery(String type, String suburb, String businessType)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Resource> resourceList = new ArrayList<>();
        ArrayList<String> queryString = new ArrayList<>();
        String typeA = RESOURCE_COLUMN_TYPE + "=" + "'" + type + "'" +
            "AND ";
        String suburA = RESOURCE_COLUMN_SUBURB + "=" + "'"+ suburb + "'" + "AND ";
        String busiType = USER_COLUMN_BUSINESS_NAME + "=" + "'" + businessType + "'" + "AND ";
        queryString.add(typeA);
        queryString.add(suburA);
        queryString.add(busiType);

        if(!businessType.contains("private user") && !businessType.contains("All"))
        {
            int x =  queryString.indexOf(busiType);
            queryString.remove(x);
            String newQuery = USER_COLUMN_BUSINESS_NAME + "!=" + "'" + "private user" + "'" + "AND ";
            queryString.add(newQuery);
        }


        if(type.contains("All"))
        {

          int x =  queryString.indexOf(typeA);
            queryString.remove(x);
        }

        if(suburb.contains("All"))
        {

          int x = queryString.indexOf(suburA);
          queryString.remove(x);
        }

        if(businessType.contains("All"))
        {

            int x = queryString.indexOf(busiType);
            if(x > -1){ queryString.remove(x);}

        }

        String query = "";

        for(int x = 0; x < queryString.size(); x ++)
        {
            String tmp = queryString.get(x);
            if(x == queryString.size() -1 || queryString.size() == 1)
            {
               tmp = tmp.replace("AND", "");
            }
            query = query + tmp;
        }

        if(query.isEmpty()){
            Cursor res = db.query(RESOURCE_TABLE_NAME, new String[]{RESOURCE_COLUMN_ID, RESOURCE_COLUMN_TYPE, RESOURCE_COLUMN_DESCRIPTION, RESOURCE_COLUMN_PLACEID,
                            RESOURCE_COLUMN_SUBURB, RESOURCE_COLUMN_QUANTITY, USER_COLUMN_BUSINESS_NAME, RESOURCE_COLUMN_USERID}, null, null,
                    null, null, null );

            res.moveToFirst();


            while(res.isAfterLast() == false && res.getCount() > 0) {
                Resource aResource = new Resource(
                        res.getString(res.getColumnIndex(RESOURCE_COLUMN_ID)),
                        res.getString(res.getColumnIndex(RESOURCE_COLUMN_TYPE)),
                        res.getString(res.getColumnIndex(RESOURCE_COLUMN_DESCRIPTION)),
                        res.getString(res.getColumnIndex(RESOURCE_COLUMN_PLACEID)),
                        res.getString(res.getColumnIndex(RESOURCE_COLUMN_SUBURB)),
                        res.getInt(res.getColumnIndex((RESOURCE_COLUMN_QUANTITY))),
                        res.getString(res.getColumnIndex(USER_COLUMN_BUSINESS_NAME)),
                        res.getString(res.getColumnIndex(RESOURCE_COLUMN_USERID)));
                resourceList.add(aResource);
                res.moveToNext();

            }
            db.close();
            return resourceList;
        } else {
            Cursor res = db.query(RESOURCE_TABLE_NAME, new String[]{RESOURCE_COLUMN_ID, RESOURCE_COLUMN_TYPE, RESOURCE_COLUMN_DESCRIPTION, RESOURCE_COLUMN_PLACEID,
                            RESOURCE_COLUMN_SUBURB, RESOURCE_COLUMN_QUANTITY, USER_COLUMN_BUSINESS_NAME, RESOURCE_COLUMN_USERID}, query, null,
                    null, null, null );

            res.moveToFirst();


            while(res.isAfterLast() == false && res.getCount() > 0) {
                Resource aResource = new Resource(
                        res.getString(res.getColumnIndex(RESOURCE_COLUMN_ID)),
                        res.getString(res.getColumnIndex(RESOURCE_COLUMN_TYPE)),
                        res.getString(res.getColumnIndex(RESOURCE_COLUMN_DESCRIPTION)),
                        res.getString(res.getColumnIndex(RESOURCE_COLUMN_PLACEID)),
                        res.getString(res.getColumnIndex(RESOURCE_COLUMN_SUBURB)),
                        res.getInt(res.getColumnIndex((RESOURCE_COLUMN_QUANTITY))),
                        res.getString(res.getColumnIndex(USER_COLUMN_BUSINESS_NAME)),
                        res.getString(res.getColumnIndex(RESOURCE_COLUMN_USERID)));
                resourceList.add(aResource);
                res.moveToNext();

            }
            db.close();
            return resourceList;
        }

    }



    public User getUser (String email, String password) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cs = db.query(USER_TABLE_NAME, new String[]{USER_COLUMN_ID, USER_COLUMN_EMAIL, USER_COLUMN_PASSWORD, USER_COLUMN_TYPE, USER_COLUMN_NAME, USER_COLUMN_LASTNAME,
        USER_COLUMN_STREET_NUMBER, USER_COLUMN_STREET_NAME, USER_COLUMN_SUBURB, USER_COLUMN_POSTCODE, USER_COLUMN_CONTACTNO, USER_COLUMN_BUSINESS_NAME}, USER_COLUMN_EMAIL +
                "=" + "'" + email + "'" + "AND " + USER_COLUMN_PASSWORD + "=" + "'" +
                password + "'", null, null, null, null);

        cs.moveToFirst();

        String id = cs.getString(cs.getColumnIndex(USER_COLUMN_ID));
        String emailTmp = cs.getString(cs.getColumnIndex(USER_COLUMN_EMAIL));
        String passwordTmp = cs.getString(cs.getColumnIndex(USER_COLUMN_PASSWORD));
        String nme = cs.getString(cs.getColumnIndex(USER_COLUMN_NAME));
            String lastName = cs.getString(cs.getColumnIndex(USER_COLUMN_LASTNAME));
            String streetNo = cs.getString(cs.getColumnIndex(USER_COLUMN_STREET_NUMBER));
            String streetName = cs.getString(cs.getColumnIndex(USER_COLUMN_STREET_NAME));
            String postcode = cs.getString(cs.getColumnIndex(USER_COLUMN_POSTCODE));
            String suburb = cs.getString(cs.getColumnIndex(USER_COLUMN_SUBURB));
            int type = cs.getInt(cs.getColumnIndex(USER_COLUMN_TYPE));
            String contact = cs.getString(cs.getColumnIndex(USER_COLUMN_CONTACTNO));
            String businessName = cs.getString(cs.getColumnIndex(USER_COLUMN_BUSINESS_NAME));
            db.close();
            User user = new User(id, emailTmp, passwordTmp, type, nme, lastName, contact, streetNo, streetName, suburb, postcode, businessName);
            return user;

    }

    public Resource getResourceById(Long id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.query(RESOURCE_TABLE_NAME, new String[]{RESOURCE_COLUMN_ID, RESOURCE_COLUMN_TYPE, RESOURCE_COLUMN_DESCRIPTION, RESOURCE_COLUMN_PLACEID,
                        RESOURCE_COLUMN_SUBURB, RESOURCE_COLUMN_QUANTITY, USER_COLUMN_BUSINESS_NAME, RESOURCE_COLUMN_USERID}, RESOURCE_COLUMN_ID + "=" + "'" + id + "'", null,
                null, null, null );

        res.moveToFirst();
        Resource aResource = new Resource(
                res.getString(res.getColumnIndex(RESOURCE_COLUMN_ID)),
                res.getString(res.getColumnIndex(RESOURCE_COLUMN_TYPE)),
                res.getString(res.getColumnIndex(RESOURCE_COLUMN_DESCRIPTION)),
                res.getString(res.getColumnIndex(RESOURCE_COLUMN_PLACEID)),
                res.getString(res.getColumnIndex(RESOURCE_COLUMN_SUBURB)),
                res.getInt(res.getColumnIndex((RESOURCE_COLUMN_QUANTITY))),
                res.getString(res.getColumnIndex(USER_COLUMN_BUSINESS_NAME)),
                res.getString(res.getColumnIndex(RESOURCE_COLUMN_USERID)));

        return aResource;
    }


    public boolean userLogin(String email, String password )
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cs = db.query(USER_TABLE_NAME, new String[] {USER_COLUMN_EMAIL, USER_COLUMN_PASSWORD}, USER_COLUMN_EMAIL + "=" + "'" + email + "'" + "AND " + USER_COLUMN_PASSWORD + "=" + "'" +
                password + "'", null, null, null, null);

        if(cs.moveToFirst()){ return true;}
        return false;

    }

    public String insertEvent(Event event)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EVENT_TITLE, event.title);
        contentValues.put(EVENT_DATE, event.date);
        contentValues.put(EVENT_DESCRIPTION, event.description);
        contentValues.put(EVENT_COLUMN_STREET_NUMBER, event.streetNo);
        contentValues.put(EVENT_COLUMN_STREET_NAME, event.streetName);
        contentValues.put(EVENT_COLUMN_SUBURB, event.suburb);
        contentValues.put(EVENT_COLUMN_POSTCODE, event.postcode);

        long id = db.insert(EVENT_TABLE_NAME, null, contentValues);
        return Long.toString(id);


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
        contentValues.put(USER_COLUMN_CONTACTNO, user.getContactNo());
        if(user.getStreetName() == null && user.getStreetNumber() == null)
        {

        }else {
            contentValues.put(USER_COLUMN_STREET_NUMBER, user.getStreetNumber());
            contentValues.put(USER_COLUMN_STREET_NAME, user.getStreetName());
        }

        contentValues.put(USER_COLUMN_SUBURB, user.getSuburb());
        contentValues.put(USER_COLUMN_POSTCODE, user.getPostcode());

        if(user.getBusinessName() == null)
        {
            // Default to "private" businessName
        } else {
            // Get user businessName
            contentValues.put(USER_COLUMN_BUSINESS_NAME, user.getBusinessName());
        }

        long id = db.insert(USER_TABLE_NAME, null, contentValues);
        return Long.toString(id);

    }

    public String insertResource (Resource resource)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RESOURCE_COLUMN_TYPE, resource.getType());
        contentValues.put(RESOURCE_COLUMN_DESCRIPTION, resource.getDescription());
        contentValues.put(RESOURCE_COLUMN_PLACEID, resource.getPlaceID());
        contentValues.put(RESOURCE_COLUMN_SUBURB, resource.getSuburb());
        contentValues.put(RESOURCE_COLUMN_QUANTITY, resource.getQuantity());
        contentValues.put(USER_COLUMN_BUSINESS_NAME, resource.getBusinessName());
        contentValues.put(RESOURCE_COLUMN_USERID, resource.getUserID());

        long id = db.insert(RESOURCE_TABLE_NAME, null, contentValues);
        return Long.toString(id);
    }

    public void updateResource (Resource resource)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RESOURCE_COLUMN_TYPE, resource.getType());
        contentValues.put(RESOURCE_COLUMN_DESCRIPTION, resource.getDescription());
        contentValues.put(RESOURCE_COLUMN_PLACEID, resource.getPlaceID());
        contentValues.put(RESOURCE_COLUMN_SUBURB, resource.getSuburb());
        contentValues.put(RESOURCE_COLUMN_QUANTITY, resource.getQuantity());
        contentValues.put(USER_COLUMN_BUSINESS_NAME, resource.getBusinessName());
        contentValues.put(RESOURCE_COLUMN_USERID, resource.getUserID());
        db.update(RESOURCE_TABLE_NAME, contentValues, RESOURCE_COLUMN_ID +"="+"'" +resource.getId()+"'", null  );


    }

    public void deleteResource(Resource resource)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(RESOURCE_TABLE_NAME,RESOURCE_COLUMN_ID +"="+"'" +resource.getId()+"'", null );
    }



}
