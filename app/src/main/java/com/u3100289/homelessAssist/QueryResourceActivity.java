package com.u3100289.homelessAssist;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class QueryResourceActivity extends Activity {
  public  ListView lv;
    Spinner suburbSpinner;
    ArrayAdapter<String> suburbAdapter;
    ArrayList<String> suburbs = new ArrayList<>();
    ArrayList<Resource> resources = new ArrayList<>();
    ArrayAdapter<Resource> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_resource);
        Spinner typeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        Spinner businessTypeSpinner = (Spinner) findViewById(R.id.businessTypeSpinner);
        suburbSpinner = (Spinner) findViewById(R.id.suburbSpinner);
        lv = findViewById(R.id.list);
        DatabaseHelper db = new DatabaseHelper(this, "fairCanberraDB", null, 1);
       // resources = db.getAllResources();

        suburbs.add("All");

//

         arrayAdapter = new ArrayAdapter<Resource>(
                this,
                android.R.layout.simple_list_item_1,
                resources);

        lv.setAdapter(arrayAdapter);

        // suburbs

        suburbAdapter =
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



        lv.setClickable(true);



        // On List click
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object listItem = lv.getItemAtPosition(position);
                Resource res = (Resource) lv.getItemAtPosition(position);
            Intent i = new Intent(getApplicationContext(), MapsActivity.class);
            i.putExtra("placeID", res );
            startActivity(i);
            }
        });

    db.close();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle data = getIntent().getExtras();
        User user = (User) data.getParcelable("user");
        intent.putExtra("user", user );
        startActivity(intent);
        finish();
    }


    public void updateList(String type, String suburb, String businessType, int suburbPos) {
        DatabaseHelper db = new DatabaseHelper(this, "fairCanberraDB", null, 1);
        lv = findViewById(R.id.list);

        // Reset suburb spinner, get new list view resources.

        lv.invalidateViews();
        resources = db.resourceQuery(type, suburb, businessType);
        System.out.println("resource: " + resources);
        ArrayList<Resource> suburbQuery = db.resourceQuery(type, "All", businessType);
        Spinner suburbSpinner = findViewById(R.id.suburbSpinner);
        suburbs.clear();
        suburbs.add("All");

        for (int x = 0; x < suburbQuery.size(); x++) {
            if (suburbs.contains(suburbQuery.get(x).getSuburb())) {
                continue;
            } else {
                suburbs.add(suburbQuery.get(x).getSuburb());
            }
        }
        db.close();
        suburbAdapter.notifyDataSetChanged();
       arrayAdapter.notifyDataSetChanged();
        ArrayAdapter<Resource> arrayAdapter = new ArrayAdapter<Resource>(
                this,
                android.R.layout.simple_list_item_1,
                resources);

        lv.setAdapter(arrayAdapter);

        if(suburbPos < suburbSpinner.getCount())
        { suburbSpinner.setSelection(suburbPos);}
        setSpinnerListener(suburbSpinner);


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
