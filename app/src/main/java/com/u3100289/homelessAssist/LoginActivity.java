package com.u3100289.homelessAssist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHelper db = new DatabaseHelper(this, "fairCanberraDB", null, 1);
        ArrayList<Event> ev = db.getAllEvents();
        if(ev.isEmpty()){  db.insertUser(new User("1", "admin@example.com", "password", 2, "mitchell", "hunt", "0416211080",
                "19", "lonsdale street",  "braddon", "2616", "Grease Monkey" ));}
        db.close();

    }

    public void toRegister(View v) {
        Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(i);
    }


    public void loginClick(View view) {
        DatabaseHelper db = new DatabaseHelper(this, "fairCanberraDB", null, 1);
        EditText email = findViewById(R.id.emailText);
        EditText password = findViewById(R.id.passwordText);


        boolean login = db.userLogin(email.getText().toString().toLowerCase(), password.getText().toString());
        boolean exists = db.userExists(email.getText().toString().toLowerCase());

        if(exists && login)
        {
            User user = db.getUser(email.getText().toString().toLowerCase(), password.getText().toString());
            Context context = getApplicationContext();
            CharSequence text = "user login : " + user.getEmail();



            Intent i = new Intent(getApplicationContext(), MainMenuActivity.class);
            i.putExtra("user", user );
            i.putExtra("onLogin", 1);
            db.close();
            startActivity(i);

        }

        if(!exists && !login)
        {
            TextView error = findViewById(R.id.errorTV);
            error.clearComposingText();
            error.setText("Email or password incorrect");
            error.setVisibility(View.VISIBLE);
        }

        if(!exists)
        {
            TextView error = findViewById(R.id.errorTV);
            error.clearComposingText();
            error.setText("Email not registered");
            error.setVisibility(View.VISIBLE);
        }

        if(!login && exists == true)
        {
            TextView error = findViewById(R.id.errorTV);
            error.clearComposingText();
            error.setText("Incorrect password");
            error.setVisibility(View.VISIBLE);
        }



    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
