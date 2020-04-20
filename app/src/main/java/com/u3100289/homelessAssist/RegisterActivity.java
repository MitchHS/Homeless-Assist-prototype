package com.u3100289.homelessAssist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AddressComponent;
import com.google.android.libraries.places.api.model.AddressComponents;
import com.google.android.libraries.places.api.model.Place;
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


    }



    public void permAddressClick(View v) {
        //code to check if this checkbox is checked!
        Context context = getApplicationContext();
        CheckBox noAddress = findViewById(R.id.permAddress);
        CheckBox isBusiness = findViewById(R.id.busiBox);
        if(noAddress.isChecked()){
//            setContentView(R.layout.content_register2);
            EditText streetName = findViewById(R.id.streetName);
            streetName.setVisibility(View.GONE);

            EditText suburb = findViewById(R.id.streetNum);
            suburb.setVisibility(v.GONE);

            TextView noAddressTv = findViewById(R.id.permAddressTv);
            noAddressTv.setVisibility(v.VISIBLE);
            noAddressTv.setTextColor(Color.RED);

            TextView addressTv = findViewById(R.id.addressTv);
            addressTv.setVisibility(v.GONE);

            EditText businessName = findViewById(R.id.businessName);
            businessName.setVisibility(View.GONE);


            if(isBusiness.isChecked() && noAddress.isChecked())
            {
                isBusiness.toggle();
                String text = "Businesses must register a permanent address";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            }
        }

        if(!noAddress.isChecked()){
//            setContentView(R.layout.content_register2);
            EditText streetNo = findViewById(R.id.streetNum);
            streetNo.setVisibility(View.VISIBLE);
            EditText streetName = findViewById(R.id.streetName);
            streetName.setVisibility(View.VISIBLE);
            EditText suburb = findViewById(R.id.suburb);
            suburb.setVisibility(v.VISIBLE);
            TextView noAddressTv = findViewById(R.id.permAddressTv);
            noAddressTv.setVisibility(v.GONE);

            TextView addressTv = findViewById(R.id.addressTv);
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
