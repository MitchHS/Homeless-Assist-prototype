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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.LocationBias;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
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


       EditText addressEdit = findViewById(R.id.addressEdit);
      addressEdit.setKeyListener(null);



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
        Bundle data = getIntent().getExtras();
        User user = (User) data.getParcelable("user");

        // Create a new Places client instance
        PlacesClient placesClient = Places.createClient(this);

        int AUTOCOMPLETE_REQUEST_CODE = 1;
        // Set the fields to specify which types of place data to
     // return after the user has made a selection.
       List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
    // Start the autocomplete intent.
        CheckBox useAddress = findViewById(R.id.useAddressCB);

        if(useAddress.isChecked()) {
            Intent intent = new Autocomplete.IntentBuilder(
                    AutocompleteActivityMode.OVERLAY, fields)
                    .setTypeFilter(TypeFilter.ADDRESS)
                    .setInitialQuery(user.getAddress())
                    .build(this);
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
        } else
            {
               // LocationBias locationBias = "Canberra";
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.OVERLAY, fields)
                        .setTypeFilter(TypeFilter.ADDRESS)

                      //  .setLocationBias("gg")
                        .build(this);
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
            }



    }

    public String placeID;
   public int AUTOCOMPLETE_REQUEST_CODE = 1;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                placeID = place.getId();

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

        Resource res = new Resource(selection, description.getText().toString(), placeID, address.getText().toString(),
                Integer.parseInt(quantity.getText().toString()), user.getBusinessName(), user.getId());

      String resourceID =  db.insertResource(res);
      Resource dbResource = db.getResourceById(Long.parseLong(resourceID));
      ArrayList<String> placeIDS = new ArrayList<>();
      //placeIDS.add("ChIJQcvS7fZUFmsRPRI2uePX2k0");
        placeIDS.add(dbResource.getPlaceID());

        Intent i = new Intent(getApplicationContext(), MapsActivity.class);
        i.putExtra("user", user );
        // Testing
        i.putExtra("placeID", placeIDS);
        startActivity(i);
        db.close();





    }


}
