package com.u3100289.homelessAssist;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




    }

    // When map is available
    @Override
    public void onMapReady(GoogleMap googleMap) {
        AtomicReference<LatLng> placeLatLng  = null;

        // Initialize the SDK
        Places.initialize(getApplicationContext(), "AIzaSyB-xVQy82Wj0-WneALqfcL0C4PKSYolJsI");
        Bundle data = getIntent().getExtras();
        User user = (User) data.getParcelable("user");
       // ArrayList<String> placeIDS = data.getStringArrayList("placeID");
        Resource res = (Resource) data.getParcelable("placeID");
        // Log Tag
        String TAG = "Google Maps";

        // Create a new Places client instance
        PlacesClient placesClient = Places.createClient(this);
        LatLngBounds.Builder latBuilder = new LatLngBounds.Builder();
        LatLng tmp ;
        Thread thread = new Thread() {
            public void run() {


//                for (int x = 0; x < placeIDS.size(); x++) {
                    // Define a Place ID.
                    String placeId = res.getPlaceID();

                    // Specify the fields to return.
                    List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS, Place.Field.ADDRESS_COMPONENTS);

                    // Construct a request object, passing the place ID and fields array.
                    FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId, placeFields);

                    placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
                        Place place = response.getPlace();

                        mMap = googleMap;


                        // Add marker
                        mMap.addMarker(new MarkerOptions()
                                .position(place.getLatLng()).title(res.getType())
                                .snippet("Address: " + place.getAddress() + "\nDescription: " + res.getDescription() + "\nQuantity: " + res.getQuantity())
                        );
                       latBuilder.include(place.getLatLng());

                      //  LatLngBounds bounds =  latBuilder.build();
//                        int padding = 0; // offset from edges of the map in pixels
//                        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
//                        googleMap.moveCamera(cu);

                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 10.0f));

                        Log.i(TAG, "Place found: " + place.getLatLng().toString());
                        Log.i(TAG, "Address comp: " + place.getAddressComponents());
                    }).addOnFailureListener((exception) -> {
                        if (exception instanceof ApiException) {
                            ApiException apiException = (ApiException) exception;
                            int statusCode = apiException.getStatusCode();
                            // Handle error with given status code.
                            Log.e(TAG, "Place not found: " + exception.getMessage());
                        }
                    });

                }
           // }
        };



        try {
            thread.start();
            thread.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "END OF THREAD");
    }


}







