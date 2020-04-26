package com.u3100289.homelessAssist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewUserResourcesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_resources);
        DatabaseHelper db = new DatabaseHelper(this, "fairCanberraDB", null, 1);

        Bundle data = getIntent().getExtras();
        User user = (User) data.getParcelable("user");
        ArrayList<Resource> resources = db.getAllResources(Integer.parseInt(user.getId()));

        ListView lv = findViewById(R.id.userResourceLv);

       ArrayAdapter arrayAdapter = new ArrayAdapter<Resource>(
                this,
                android.R.layout.simple_list_item_1,
                resources);

        lv.setAdapter(arrayAdapter);

        lv.setClickable(true);




        // On List click
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object listItem = lv.getItemAtPosition(position);
                Resource res = (Resource) lv.getItemAtPosition(position);
                Intent i = new Intent(getApplicationContext(), ManageResourcesActivity.class);
                i.putExtra("resource", res );
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle data = getIntent().getExtras();
        User user = (User) data.getParcelable("user");
        intent.putExtra("user", user );
        startActivity(intent);
        finish();
    }
}
