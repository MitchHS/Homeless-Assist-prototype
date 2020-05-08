package com.u3100289.homelessAssist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;;
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
        


    }
}
