package com.u3100289.homelessAssist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
        Bundle data = getIntent().getExtras();
        Resource res = (Resource) data.getParcelable("resource");
        DatabaseHelper db = new DatabaseHelper(this, "fairCanberraDB", null, 1);
        EditText quantity = findViewById(R.id.quantityEdit);
        Resource resource = db.getResourceById(Long.parseLong(res.getId()));


        System.out.println("ORGIG res" + resource);
        resource.quanity ++;
        db.updateResource(resource);
        Resource newRes = db.getResourceById(Long.parseLong(res.getId()));
        System.out.println("NEW RES" + newRes);

        quantity.setText(String.valueOf(newRes.getQuantity()));


    }

    public void onDecrementClick (View v) {
        Bundle data = getIntent().getExtras();
        Resource res = (Resource) data.getParcelable("resource");
        DatabaseHelper db = new DatabaseHelper(this, "fairCanberraDB", null, 1);
        EditText quantity = findViewById(R.id.quantityEdit);

        Resource resource = db.getResourceById(Long.parseLong(res.getId()));
        if (resource.getQuantity() == 1) {
            new AlertDialog.Builder(this)
                    .setTitle("Confirmation")
                    .setMessage("Do you really want to delete this resource?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {

                                if(whichButton == -1) {
                                    Toast.makeText(getApplicationContext(), "Resource Deleted", Toast.LENGTH_SHORT).show();
                                    db.deleteResource(resource);
                                    quantity.setText("0");
                                    Bundle data = getIntent().getExtras();
                                    User user = (User) data.getParcelable("user");
                                    Intent i = new Intent(getApplicationContext(), ViewUserResourcesActivity.class);
                                    i.putExtra("user", user );
                                    startActivity(i);
                                }
                        }
                    })
                    .setNegativeButton(android.R.string.no, null).show();
        } else {

            resource.quanity--;
            db.updateResource(resource);
            Resource newRes = db.getResourceById(Long.parseLong(res.getId()));
            System.out.println("NEW RES" + newRes);
            quantity.setEnabled(true);
            quantity.setText(String.valueOf(newRes.getQuantity()));

        }
    }

    public void onDeleteClick (View v)
    {
        Bundle data = getIntent().getExtras();
        Resource res = (Resource) data.getParcelable("resource");
        DatabaseHelper db = new DatabaseHelper(this, "fairCanberraDB", null, 1);

        new AlertDialog.Builder(this)
                .setTitle("Title")
                .setMessage("Do you really want to delete this resource?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        if(whichButton == -1) {
                            Toast.makeText(getApplicationContext(), "Resource Deleted", Toast.LENGTH_SHORT).show();
                            db.deleteResource(res);

                            Bundle data = getIntent().getExtras();
                            User user = (User) data.getParcelable("user");
                            Intent i = new Intent(getApplicationContext(), ViewUserResourcesActivity.class);
                            i.putExtra("user", user );
                            startActivity(i);
                        }
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    @Override
    public void onBackPressed() {
        Bundle data = getIntent().getExtras();
        User user = (User) data.getParcelable("user");
        Intent i = new Intent(getApplicationContext(), ViewUserResourcesActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getIntent().removeExtra("onLogin");
        i.putExtra("user", user );
        startActivity(i);
    }
}
