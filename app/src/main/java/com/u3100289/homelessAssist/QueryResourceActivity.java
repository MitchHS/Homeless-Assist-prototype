package com.u3100289.homelessAssist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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

        DatabaseHelper db = new DatabaseHelper(this, "fairCanberraDB", null, 1);
        ArrayList<Resource> allResources = db.getAllResources();
        ArrayList<String> suburbs = new ArrayList<>();
        suburbs.add("All");
        // Init the all suburbs for 
        for(int x = 0; x < allResources.size(); x++)
        {
            suburbs.add(allResources.get(x).getSuburb());
        }


        // suburbs
        System.out.println("RESOURCES " + allResources.toString());
        ArrayAdapter<String> adapter2 =
                new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, suburbs);
        adapter2.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.resourceQuery, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.businessType, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        typeSpinner.setAdapter(adapter);
        businessTypeSpinner.setAdapter(adapter3);
        suburbSpinner.setAdapter(adapter2);

    }
}
