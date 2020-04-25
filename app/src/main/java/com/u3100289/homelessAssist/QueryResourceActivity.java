package com.u3100289.homelessAssist;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class QueryResourceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_resource);
        Spinner typeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        Spinner businessTypeSpinner = (Spinner) findViewById(R.id.businessTypeSpinner);
        Spinner suburbSpinner = (Spinner) findViewById(R.id.suburbSpinner);
        ListView lv = findViewById(R.id.list);
        DatabaseHelper db = new DatabaseHelper(this, "fairCanberraDB", null, 1);
        ArrayList<Resource> allResources = db.getAllResources();
        ArrayList<String> suburbs = new ArrayList<>();
        suburbs.add("All");

//        // Init the all suburbs for
//        for (int x = 0; x < allResources.size(); x++) {
//            if (suburbs.contains(allResources.get(x).getSuburb())) {
//                continue;
//            } else {
//                suburbs.add(allResources.get(x).getSuburb());
//            }
//        }

        ArrayAdapter<Resource> arrayAdapter = new ArrayAdapter<Resource>(
                this,
                android.R.layout.simple_list_item_1,
                allResources);

        lv.setAdapter(arrayAdapter);

        // suburbs

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

        lv.setClickable(true);
        lv.setOnItemClickListener((arg0, arg1, position, arg3) -> {

            Resource res = (Resource) lv.getItemAtPosition(position);
            Intent i = new Intent(getApplicationContext(), MapsActivity.class);
            ArrayList<String> ids = new ArrayList<>();
            ids.add(res.placeID);
            i.putExtra("placeID", ids );
            startActivity(i);


        });




    }





    public void updateList(String type, String suburb, String businessType, int suburbPos) {
        DatabaseHelper db = new DatabaseHelper(this, "fairCanberraDB", null, 1);
        ListView lv = findViewById(R.id.list);


        //TODO: db query for function args (on update selection)
        ArrayList<Resource> resources = db.resourceQuery(type, suburb, businessType);
        ArrayList<Resource> suburbQuery = db.resourceQuery(type, "All", businessType);
        Spinner suburbSpinner = findViewById(R.id.suburbSpinner);
        ArrayList<String> suburbs = new ArrayList<>();
        suburbs.add("All");

        for (int x = 0; x < suburbQuery.size(); x++) {
            if (suburbs.contains(suburbQuery.get(x).getSuburb())) {
                continue;
            } else {
                suburbs.add(suburbQuery.get(x).getSuburb());
            }
        }
        db.close();
        ArrayAdapter<String> suburbAdapter =
                new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, suburbs);
        suburbAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<Resource> arrayAdapter = new ArrayAdapter<Resource>(
                this,
                android.R.layout.simple_list_item_1,
                resources);

        lv.setAdapter(arrayAdapter);
        suburbSpinner.setAdapter(suburbAdapter);
        if(suburbPos < suburbSpinner.getCount())
        { suburbSpinner.setSelection(suburbPos);}
       // setSpinnerListener(suburbSpinner);


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
                        businessType, suburbSpinner.getSelectedItemPosition());


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }



}
