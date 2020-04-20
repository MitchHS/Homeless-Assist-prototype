package com.u3100289.homelessAssist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResourceRegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
// AIzaSyB-xVQy82Wj0-WneALqfcL0C4PKSYolJsI



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_register);

        Bundle data = getIntent().getExtras();
        User user = (User) data.getParcelable("user");

        DatabaseHelper db = new DatabaseHelper(this, "fairCanberraDB", null, 1);

        Spinner spinner = (Spinner) findViewById(R.id.categorySpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.resources, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        ArrayList<Resource> test = db.getAllResources();
      //  String text = test.get(0).getDescription();
       // String text =  spinner.getSelectedItem().toString();
        int duration = Toast.LENGTH_SHORT;
        Context context = this.getApplicationContext();
       // String text = user.getBusinessName();
//        Toast toast = Toast.makeText(context, text, duration);
//        toast.show();


    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public void addressClick(View v)
    {
        // Initialize the SDK
        Places.initialize(getApplicationContext(), "AIzaSyB-xVQy82Wj0-WneALqfcL0C4PKSYolJsI");

        // Create a new Places client instance
        PlacesClient placesClient = Places.createClient(this);

        int AUTOCOMPLETE_REQUEST_CODE = 1;
// Set the fields to specify which types of place data to
// return after the user has made a selection.
       List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS);
// Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY, fields)
                .build(this);

        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);

    }
   public int AUTOCOMPLETE_REQUEST_CODE = 1;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);

                EditText edit = findViewById(R.id.addressEdit);
                edit.setText(place.getAddress());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }




    public void submitClick(View v)
    {
        DatabaseHelper db = new DatabaseHelper(this, "fairCanberraDB", null, 1);
        Spinner spinner = (Spinner) findViewById(R.id.categorySpinner);

        String selection = spinner.getSelectedItem().toString();
        EditText description = findViewById(R.id.descEdit);
        EditText address = findViewById(R.id.addressEdit);
        EditText quantity = findViewById(R.id.quantityEdit);
        Bundle data = getIntent().getExtras();
        User user = (User) data.getParcelable("user");

        Resource res = new Resource(selection, description.getText().toString(), "placeID", address.getText().toString(),
                Integer.parseInt(quantity.getText().toString()), user.getBusinessName(), user.getId());


        ArrayList<Resource> test = db.getAllResources();
        // String text = test.get(1).getDescription();
       //String text = res.toString();



//
//        int duration = Toast.LENGTH_SHORT;
//        Context context = this.getApplicationContext();
//        // String text = user.getBusinessName();
//        Toast toast = Toast.makeText(context, text, duration);
//        toast.show();






    }


}
