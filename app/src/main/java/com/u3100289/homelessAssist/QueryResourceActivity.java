package com.u3100289.homelessAssist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.u3100289.homelessAssist.R;

import java.util.ArrayList;

public class QueryResourceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_resource);
        Spinner typeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        Spinner businessTypeSpinner = (Spinner) findViewById(R.id.businessTypeSpinner);
        Spinner suburbSpinner = (Spinner) findViewById(R.id.suburbSpinner);
        ListView lv = findViewById(R.id.listView);
        DatabaseHelper db = new DatabaseHelper(this, "fairCanberraDB", null, 1);
        ArrayList<Resource> allResources = db.getAllResources();
        ArrayList<String> suburbs = new ArrayList<>();
        suburbs.add("All");

        // Init the all suburbs for 
        for (int x = 0; x < allResources.size(); x++) {
            if (suburbs.contains(allResources.get(x).getSuburb())) {
                continue;
            } else {
                suburbs.add(allResources.get(x).getSuburb());
            }
        }

        ArrayAdapter<Resource> arrayAdapter = new ArrayAdapter<Resource>(
                this,
                android.R.layout.simple_list_item_1,
                allResources);

        lv.setAdapter(arrayAdapter);

        // suburbs
        System.out.println("RESOURCES " + allResources.toString());
        ArrayAdapter<String> suburbAdapter =
                new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, suburbs);
        suburbAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        ArrayAdapter<CharSequence> resourceAdapter = ArrayAdapter.createFromResource(this,
                R.array.resourceQuery, android.R.layout.simple_spinner_item);

        resourceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> businessAdapater = ArrayAdapter.createFromResource(this,
                R.array.businessType, android.R.layout.simple_spinner_item);

        resourceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the resourceAdapter to the spinner
        typeSpinner.setAdapter(resourceAdapter);
        businessTypeSpinner.setAdapter(businessAdapater);
        suburbSpinner.setAdapter(suburbAdapter);

        // Registering listeners for spinner updates
        setSpinnerListener(typeSpinner);
        setSpinnerListener(businessTypeSpinner);
        setSpinnerListener(suburbSpinner);

       // db.resourceQuery("Food", "Forde", "private user");


    }

    public void updateList(String type, String suburb, String businessType) {
        DatabaseHelper db = new DatabaseHelper(this, "fairCanberraDB", null, 1);
        ListView lv = findViewById(R.id.listView);


        //TODO: db query for function args (on update selection)
        ArrayList<Resource> resources = db.resourceQuery(type, suburb, businessType);
        ArrayList<String> suburbs = new ArrayList<>();
        suburbs.add("All");

        for (int x = 0; x < resources.size(); x++) {
            if (suburbs.contains(resources.get(x).getSuburb())) {
                continue;
            } else {
                suburbs.add(resources.get(x).getSuburb());
            }
        }



        db.close();
        ArrayAdapter<Resource> arrayAdapter = new ArrayAdapter<Resource>(
                this,
                android.R.layout.simple_list_item_1,
                resources);

        lv.setAdapter(arrayAdapter);
    }

    // Register listener for a spinner
    public void setSpinnerListener(Spinner spinner)
    {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Spinner typeSpinner = (Spinner) findViewById(R.id.typeSpinner);
                Spinner businessTypeSpinner = (Spinner) findViewById(R.id.businessTypeSpinner);
                Spinner suburbSpinner = (Spinner) findViewById(R.id.suburbSpinner);
                String businessType = businessTypeSpinner.getSelectedItem().toString();
                if(businessType.contains("Private"))
                {
                    businessType = "private user";
                }

                updateList(typeSpinner.getSelectedItem().toString(), suburbSpinner.getSelectedItem().toString(),
                        businessType);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }





}
