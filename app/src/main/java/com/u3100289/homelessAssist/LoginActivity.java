package com.u3100289.homelessAssist;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHelper db = new DatabaseHelper(this, "fairCanberraDB", null, 1);
//        Resource res = new Resource("0", "Food", "Coffee", "testPlace", "Test address", 1, "admin");
//        String testId = db.insertResource(res);
//        User admin = new User("admin@example.com", "password", 1, "adminName", "adminLast", "13",
//                "FakeStreet", "FakeSub", "2913" );

      boolean test =  db.userLogin("admin@example.com", "password");
        Context context = getApplicationContext();
            CharSequence text = "We did it reddit" + test;
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

//        String id = db.insertUser(admin);
//
//        if(id!=null){
//            Context context = getApplicationContext();
//            CharSequence text = "We did it reddit" + id;
//            int duration = Toast.LENGTH_SHORT;
//
//            Toast toast = Toast.makeText(context, text, duration);
//            toast.show();
//        }



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
        boolean exists = db.userExists(email.getText().toString());

        if(exists && login)
        {
            User user = db.getUser(email.getText().toString().toLowerCase(), password.getText().toString());
            Context context = getApplicationContext();
            CharSequence text = "user login : " + user.getEmail();
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
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
