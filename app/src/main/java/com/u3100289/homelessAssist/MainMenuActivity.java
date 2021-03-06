package com.u3100289.homelessAssist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Bundle data = getIntent().getExtras();
        User user = (User) data.getParcelable("user");
        TextView title = findViewById(R.id.welcomeTv);

        int x = data.getInt("onLogin");
        System.out.println("X"  + x);

        if(x > 0){
            EventActivity ea = new EventActivity(MainMenuActivity.this);
            ea.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            ea.show();
        }
        title.setText("Welcome " + user.getName() + "!");

    }

    public void viewEventClick(View v){

        EventActivity ea = new EventActivity(MainMenuActivity.this);
        ea.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        ea.show();
    }


    public void onManageClick (View v)
    {
        Bundle data = getIntent().getExtras();
        User user = (User) data.getParcelable("user");
        Intent i = new Intent(getApplicationContext(), ViewUserResourcesActivity.class);
        i.putExtra("user", user );
        startActivity(i);
    }

    public void onFindClick(View v)
    {
        Bundle data = getIntent().getExtras();
        User user = (User) data.getParcelable("user");
        Intent i = new Intent(getApplicationContext(), QueryResourceActivity.class);
        i.putExtra("user", user );
        startActivity(i);
    }

    public void onProviderClick (View v)
    {
        Bundle data = getIntent().getExtras();
        User user = (User) data.getParcelable("user");
        Intent i = new Intent(getApplicationContext(), ResourceRegisterActivity.class);
        i.putExtra("user", user );
        startActivity(i);
    }
}
