package com.u3100289.homelessAssist;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }



    public void permAddressClick(View v) {
        //code to check if this checkbox is checked!
        CheckBox noAddress = findViewById(R.id.permAddress);
        if(noAddress.isChecked()){
//            setContentView(R.layout.content_register2);
            EditText streetName = findViewById(R.id.streetName);
            streetName.setVisibility(View.GONE);

            EditText suburb = findViewById(R.id.suburb);
            suburb.setVisibility(v.GONE);

            TextView noAddressTv = findViewById(R.id.permAddressTv);
            noAddressTv.setVisibility(v.VISIBLE);
            noAddressTv.setTextColor(Color.RED);

            TextView addressTv = findViewById(R.id.addressTv);
            addressTv.setVisibility(v.GONE);
        }

        if(!noAddress.isChecked()){
//            setContentView(R.layout.content_register2);
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

    public void registerClick(View v)
    {
        
    }

}
