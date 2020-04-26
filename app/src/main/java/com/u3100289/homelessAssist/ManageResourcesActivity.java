package com.u3100289.homelessAssist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Arrays;
import java.util.List;

public class ManageResourcesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_resources);
        Bundle data = getIntent().getExtras();
        Resource res = (Resource) data.getParcelable("resource");

        EditText type = findViewById(R.id.typeEdit);
        type.setKeyListener(null);

        EditText address = findViewById(R.id.addressEdit);
        address.setKeyListener(null);
        EditText description = findViewById(R.id.descEdit);
        description.setKeyListener(null);
        EditText quantity = findViewById(R.id.quantityEdit);
        quantity.setKeyListener(null);
        Places.initialize(getApplicationContext(), "AIzaSyB-xVQy82Wj0-WneALqfcL0C4PKSYolJsI");
        PlacesClient placesClient = Places.createClient(this);
        type.setText(res.getType());
        String placeId = res.getPlaceID();
        description.setText(res.getDescription());

        quantity.setText(String.valueOf(res.getQuantity()));
        // Specify the fields to return.
        List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS, Place.Field.ADDRESS_COMPONENTS);

        // Construct a request object, passing the place ID and fields array.
        FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId, placeFields);

        placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
            Place place = response.getPlace();
            address.setText(place.getAddress());
        });
    }



    public void onIncrementClick (View v)
    {

    }

    public void onDecrementClick (View v)
    {

    }

    public void onDeleteClick (View v)
    {

    }

}
