package com.u3100289.homelessAssist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;;
import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.text.FieldPosition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.R.layout.simple_list_item_1;

public class EventActivity extends Dialog implements
        android.view.View.OnClickListener  {
    public Activity thisActivity;

    public EventActivity(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        DatabaseHelper db = new DatabaseHelper(getContext(), "fairCanberraDB", null, 1);
        ListView lv = findViewById(R.id.eventList);
        ArrayList<Event> eventArray = db.getAllEvents();
        System.out.println("DEBUG" + eventArray.size());
        ArrayAdapter<Event> arrayAdapter;
        if(eventArray.isEmpty())
        {
                String pattern = "dd-MM-yyyy HH:mm:ss";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

                Date date = null;
                try {
                    date = simpleDateFormat.parse("08-09-2020 17:00:00");
                    System.out.println(date.toString());

                } catch (ParseException e) {
                    System.out.println("ERROR: " + e);
                }

                String id =  db.insertEvent(new Event("Weekly BBQ", date.toString(), "Weekly BBQ serving free food to vulnerable residents of Canberra", 73,
                      "Petrie Plaza", "Civic", 2601  ));

                eventArray = db.getAllEvents();
                arrayAdapter = new ArrayAdapter<Event>(
                    getContext(),
                    android.R.layout.simple_list_item_1,
                    eventArray);
            lv.setAdapter(arrayAdapter);

            db.insertUser(new User("2", "private@example.com", "password", 1, "john", "doe", "0416211080", "63", "mcgovern street",  "casey", "2913", "private user" ));
            db.insertUser(new User("3", "hyatt@example.com", "password", 2, "michael", "james", "0416211080", "120", "commonwealth avenue",  "yarralumla", "2600", "Hyatt Hotel" ));
            db.insertResource(new Resource("1", "Food", "Example Food", "ChIJQWzEXWFNFmsR9GDCpxQ808g", "Braddon", 10, "Grease Monkey", "1"));
            db.insertResource(new Resource("2", "Food", "Another food Example", "ChIJJUsFufZUFmsR5Q-1f56uTEk", "Forde", 1, "Grease Moneky", "1"));
            db.insertResource(new Resource("3", "Beverages", "Example Beverage", "EipDYW5iZXJyYSBBdmUsIEdyaWZmaXRoIEFDVCAyNjAzLCBBdXN0cmFsaWEiLiosChQKEgnb5Gv-OEwWaxE6Ec8hc7G3sxIUChIJM1ngD_JMFmsREFdpp27qAAU", "Griffith", 1, "Grease Monkey", "1"));
            db.insertResource(new Resource("4", "Beverages", "Example Beverages 2", "ChIJQWzEXWFNFmsR9GDCpxQ808g", "Braddon", 1, "Grease Monkey", "1"));
            db.insertResource(new Resource("5", "Clothing", "Example clothing", "ChIJHR0kf0qrF2sRORKQ71Ls574", "Casey", 1, "private user", "2"));
            db.insertResource(new Resource("6", "Clothing", "Example Clothing 2", "EiREYXZpZCBTdCwgVHVybmVyIEFDVCAyNjEyLCBBdXN0cmFsaWEiLiosChQKEgn_Ad_OoFIWaxGb-kHZEXYpLRIUChIJK_j30aFSFmsRMFZpp27qAAU", "Turner", 1,"private user", "2" ));
            db.insertResource(new Resource("7", "Utilities", "Example Utilities", "ChIJbfET155UFmsRyyGqn9P6qVo", "Gungahlin", 1, "private user", "2"));
            db.insertResource(new Resource( "8", "Housing", "Temp accomodation exame", "ChIJ7RovVSJNFmsRjOdxPHN_5sI", "Yarralumla", 1, "Hyatt Hotel", "3"));






        } else {

            arrayAdapter = new ArrayAdapter<Event>(
                    getContext(),
                    android.R.layout.simple_list_item_1,
                    eventArray);

            lv.setAdapter(arrayAdapter);
        }

        Button close = findViewById(R.id.closeButto);
        close.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        this.dismiss();

    }
}
