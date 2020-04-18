package com.u3100289.homelessAssist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class ResourceRegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_register);

        Bundle data = getIntent().getExtras();
        User user = (User) data.getParcelable("user");

        DatabaseHelper db = new DatabaseHelper(this, "fairCanberraDB", null, 1);


        ArrayList<Resource> test = db.getAllResources();
        String text = test.get(0).getDescription();

        int duration = Toast.LENGTH_SHORT;
        Context context = this.getApplicationContext();
       // String text = user.getBusinessName();
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        Spinner spinner = (Spinner) findViewById(R.id.categorySpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.resources, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);




    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }


}
