package com.u3100289.homelessAssist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AddressComponent;
import com.google.android.libraries.places.api.model.AddressComponents;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        EditText streetNum = findViewById(R.id.streetNum);
        EditText streetName = findViewById(R.id.streetName);
        EditText suburb = findViewById(R.id.suburb);
        EditText postcode = findViewById(R.id.postcode);

        streetName.setKeyListener(null);
        streetNum.setKeyListener(null);
        suburb.setKeyListener(null);
        postcode.setKeyListener(null);


    }

    public void addressClick(View v)
    {
        // Initialize the SDK
        Places.initialize(getApplicationContext(), "AIzaSyB-xVQy82Wj0-WneALqfcL0C4PKSYolJsI");
        Bundle data = getIntent().getExtras();

        // Create a new Places client instance
        PlacesClient placesClient = Places.createClient(this);

        int AUTOCOMPLETE_REQUEST_CODE = 1;
        // Set the fields to specify which types of place data to
        // return after the user has made a selection.
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.ADDRESS_COMPONENTS);
        // Start the autocomplete intent.
        RectangularBounds bounds = RectangularBounds.newInstance(
                new LatLng(-35.585757, 149.227334),
                new LatLng(-35.127816, 149.123940));
        {
            // LocationBias locationBias = "Canberra";
            Intent intent = new Autocomplete.IntentBuilder(
                    AutocompleteActivityMode.OVERLAY, fields)
                    .setLocationBias(bounds)
                    .setCountry("AU")
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
                CheckBox noPerm = findViewById(R.id.permAddress);
                if(!noPerm.isChecked()) {
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    placeID = place.getId();
                    AddressComponents addressComp = place.getAddressComponents();
                    List<AddressComponent> list = addressComp.asList();

                    EditText streetNum = findViewById(R.id.streetNum);
                    EditText streetName = findViewById(R.id.streetName);
                    EditText suburb = findViewById(R.id.suburb);
                    EditText postcode = findViewById(R.id.postcode);

                    for(int x = 0; x < list.size(); x ++)
                    {
                        switch(list.get(x).getTypes().toString())
                        {
                            case "[locality, political]":
                                suburb.setText(list.get(x).getName());
                                break;

                            case "[postal_code]":
                                postcode.setText(list.get(x).getName());
                                break;

                            case "[street_number]":
                                    streetNum.setText(list.get(x).getName());
                                    break;

                            case "[route]":
                                streetName.setText(list.get(x).getName());
                                break;

                            default:
                                System.out.println("Not found" + list.get(x).getTypes().toString());

                        }
                    }

                } else {
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    placeID = place.getId();
                    AddressComponents addressComp = place.getAddressComponents();
                    List<AddressComponent> list = addressComp.asList();
                    EditText streetNum = findViewById(R.id.streetNum);
                    EditText streetName = findViewById(R.id.streetName);
                    EditText suburb = findViewById(R.id.suburb);
                    EditText postcode = findViewById(R.id.postcode);
                    System.out.println("LIST " + (list.toString()));
                    System.out.println("TYPE" + list.get(0).getTypes());
                    for(int x = 0; x < list.size(); x ++) {
                        switch (list.get(x).getTypes().toString()) {
                            case "[locality, political]":
                                suburb.setText(list.get(x).getName());
                                break;

                            case "[postal_code]":
                                postcode.setText(list.get(x).getName());
                                break;

                            case "[street_number]":
                                streetNum.setText(list.get(x).getName());
                                break;

                            case "[route]":
                                streetName.setText(list.get(x).getName());
                                break;

                            default:
                                System.out.println("Not found" + list.get(x).getTypes().toString());

                        }
                    }
//
                }

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }



    public void permAddressClick(View v) {
        //code to check if this checkbox is checked!
        Context context = getApplicationContext();
        CheckBox noAddress = findViewById(R.id.permAddress);
        CheckBox isBusiness = findViewById(R.id.busiBox);
        EditText streetName = findViewById(R.id.streetName);
        EditText streetNum = findViewById(R.id.streetNum);
        EditText suburb = findViewById(R.id.suburb);
        EditText postcode = findViewById(R.id.postcode);
        TextView noAddressTv = findViewById(R.id.permAddressTv);
        TextView addressTv = findViewById(R.id.addressTv);
        EditText businessName = findViewById(R.id.businessName);
        if(noAddress.isChecked()){
//            setContentView(R.layout.content_register2);
            streetName.setVisibility(View.GONE);
            streetNum.setVisibility(v.GONE);

            noAddressTv.setVisibility(v.VISIBLE);
            noAddressTv.setTextColor(Color.RED);
            addressTv.setVisibility(v.GONE);
            businessName.setVisibility(View.GONE);

            if(isBusiness.isChecked() && noAddress.isChecked())
            {
                isBusiness.toggle();
                String text = "Businesses must register a permanent address";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

            if(!isBusiness.isChecked())
            {
                streetName.clearComposingText();
                streetNum.clearComposingText();
                suburb.clearComposingText();
                postcode.clearComposingText();
            }
        }

        if(!noAddress.isChecked()){
            EditText streetNo = findViewById(R.id.streetNum);
            streetNo.setVisibility(View.VISIBLE);
            streetNo.clearComposingText();
            streetName.setVisibility(View.VISIBLE);
            streetName.clearComposingText();
            suburb.setVisibility(v.VISIBLE);
            suburb.clearComposingText();
            noAddressTv.setVisibility(v.GONE);
            addressTv.setVisibility(v.VISIBLE);
        }


    }

    public void isBusinessClick(View v){
        Context context = getApplicationContext();
        CheckBox noAddress = findViewById(R.id.permAddress);
        CheckBox isBusiness = findViewById(R.id.busiBox);
        EditText businessName = findViewById(R.id.businessName);
        businessName.setVisibility(View.VISIBLE);

        if(!isBusiness.isChecked())
        {
            businessName.setVisibility(View.GONE);
        }

        if(noAddress.isChecked() && isBusiness.isChecked())
        {
            EditText streetNo = findViewById(R.id.streetNum);
            streetNo.setVisibility(View.VISIBLE);
            EditText streetName = findViewById(R.id.streetName);
            streetName.setVisibility(View.VISIBLE);
            EditText suburb = findViewById(R.id.suburb);
            suburb.setVisibility(v.VISIBLE);
            TextView noAddressTv = findViewById(R.id.permAddressTv);
            noAddressTv.setVisibility(v.GONE);

            businessName.setVisibility(View.VISIBLE);

            TextView addressTv = findViewById(R.id.addressTv);
            addressTv.setVisibility(v.VISIBLE);
            noAddress.toggle();
            String text = "Businesses must register a permanent address";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    public void registerClick(View v)
    {
        EditText firstName = findViewById(R.id.firstName);
        EditText lastName = findViewById(R.id.lastName);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        EditText confirmPass = findViewById(R.id.confirmPassword);
        EditText streetNum = findViewById(R.id.streetNum);
        EditText streetName = findViewById(R.id.streetName);
        EditText suburb = findViewById(R.id.suburb);
        EditText postcode = findViewById(R.id.postcode);
        EditText businessName = findViewById(R.id.businessName);
        EditText contactNo = findViewById(R.id.contactNo);
        Context context = getApplicationContext();
        CheckBox isBusiness = findViewById(R.id.busiBox);

        boolean isComplete = true;
        boolean isEmailChecked = false;
        ArrayList<EditText> incompleteFields = new ArrayList<>();
        ArrayList<String> fields = new ArrayList<>();
        DatabaseHelper db = new DatabaseHelper(this, "fairCanberraDB", null, 1);


        if(firstName.getText().toString().trim().length() <= 0) {
            isComplete = false;
            incompleteFields.add(firstName);
            fields.add(" first name,");
        }

        if(contactNo.getText().toString().trim().length() <= 0 || !isInteger(contactNo.getText().toString())){
            isComplete = false;
            incompleteFields.add(contactNo);
            fields.add(" contact number,");
        }

        if(lastName.getText().toString().trim().length() <= 0) {
            isComplete = false;
            incompleteFields.add(lastName);
            fields.add(" last name,");
        }

        if(email.getText().toString().trim().length() <= 0) {
            isComplete = false;
            incompleteFields.add(email);
            fields.add(" email,");
            isEmailChecked = true;
        }

        // If email passed empty check and does not contain an @. // Need to add further for .com / .net / .au or other valid emails etc.
        if(!isEmailChecked && !email.getText().toString().contains("@"))
        {
            fields.add(" email: invalid address,");
            isComplete = false;
            incompleteFields.add(email);
        }

        if(password.getText().toString().trim().length() <= 0) {
            isComplete = false;
            incompleteFields.add(password);
            fields.add(" password,");
        }

        if(confirmPass.getText().toString().trim().length() <= 0) {
            isComplete = false;
            incompleteFields.add(confirmPass);
            fields.add(" confirm password,");
        }

        if((confirmPass.getText().toString().length() != 0 && password.getText().toString().trim().length() != 0 &&
                !password.getText().toString().equals(confirmPass.getText().toString())))
        {
            isComplete = false;
            fields.add(" password confirm missmatch");
            incompleteFields.add(confirmPass);
        }

        if(lastName.getText().toString().trim().length() <= 0) {
            isComplete = false;
            incompleteFields.add(lastName);
            fields.add(" last name,");
        }

        // Perm address check
        CheckBox noAddress = findViewById(R.id.permAddress);
        if(noAddress.isChecked()) {
            if(suburb.getText().toString().trim().length() <= 0) {
                isComplete = false;
                incompleteFields.add(suburb);
                fields.add(" suburb,");
            }

            if(postcode.getText().toString().trim().length() <= 0) {
                isComplete = false;
                incompleteFields.add(postcode);
                fields.add(" postcode,");
            }

        } else {

            if(isBusiness.isChecked())
            {
                if(businessName.getText().toString().length() <= 0)
                {
                    isComplete = false;
                    incompleteFields.add(businessName);
                    fields.add(" business name,");
                }
            }
            if (streetNum.getText().toString().trim().length() <= 0 || !isInteger(streetNum.getText().toString())) {

                isComplete = false;
                incompleteFields.add(streetNum);
                fields.add(" street no,");
            }

            if (streetName.getText().toString().trim().length() <= 0) {
                isComplete = false;
                incompleteFields.add(streetName);
                fields.add(" street name,");
            }

            if(suburb.getText().toString().trim().length() <= 0) {
                isComplete = false;
                incompleteFields.add(suburb);
                fields.add(" suburb,");
            }

            if(postcode.getText().toString().trim().length() <= 0 || !isInteger(postcode.getText().toString())) {
                isComplete = false;
                incompleteFields.add(postcode);
                fields.add(" postcode,");
            }


        }




        // Toast & Change text colour
        if(!isComplete)
        {
            String text = "Please complete the fields:";

            for(int x = 0; x < fields.size(); x++)
            {
                if(x == fields.size()-1)
                {
                    String tmp = fields.get(x);
                    tmp = tmp.substring(0, tmp.length() - 1);
                    text+= tmp;
                    break;
                }
                text+= fields.get(x);
            }

            for(int x = 0; x < incompleteFields.size(); x++)
            {
                incompleteFields.get(x).setHintTextColor(Color.RED);
            }
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } else {

           if(!db.userExists(email.getText().toString().toLowerCase())){ // if no address for perm register user with 2nd constructor.
            if(noAddress.isChecked())
            {

                User newUser = new User(email.getText().toString(), password.getText().toString(), 1, firstName.getText().toString(), lastName.getText().toString(), contactNo.getText().toString(), null, null,
                        suburb.getText().toString(), postcode.getText().toString(), null);
                db.insertUser(newUser);
            } else if(isBusiness.isChecked()) {
                // Business type 2.

                User newUser = new User(email.getText().toString(), password.getText().toString(), 2, firstName.getText().toString(), lastName.getText().toString(), contactNo.getText().toString(),
                        streetNum.getText().toString(), streetName.getText().toString(), suburb.getText().toString(), postcode.getText().toString(), businessName.getText().toString());
                db.insertUser(newUser);

            } else {
                User newUser = new User(email.getText().toString(), password.getText().toString(), 1, firstName.getText().toString(), lastName.getText().toString(), contactNo.getText().toString(),
                        streetNum.getText().toString(), streetName.getText().toString(), suburb.getText().toString(), postcode.getText().toString(), null);
                db.insertUser(newUser);
            }
               Intent i = new Intent(getApplicationContext(), LoginActivity.class);
               startActivity(i);
           } else {
               String text = "User already exists";
               int duration = Toast.LENGTH_LONG;
               Toast toast = Toast.makeText(context, text, duration);
               toast.show();
           }





            }

    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }



}
