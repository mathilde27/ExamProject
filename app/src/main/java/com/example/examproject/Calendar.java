package com.example.examproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class Calendar extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    MainActivity main = new MainActivity();
    Toolbar toolbar;
    AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        //Set Home as the selected
        bottomNavigationView.setSelectedItemId(R.id.calendar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.calendar:
                        return true;
                    case R.id.myplant:
                        startActivity(new Intent(getApplicationContext()
                                , MyPlants.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                , MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.my_toolbar); //will set the title = the app name when removing the String i hardcoded.
        setSupportActionBar(toolbar);
    }
    //implements the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // sets the top menu
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    //What to do when items in setting is clicked. Should obviously not do this but take the user to a new page
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId =item.getItemId();

        if (itemId == R.id.profile){
            Intent intent = new Intent(Calendar.this, Profile.class); //this starts the activity add plant with the view. But this should be located in myplantfragment
            startActivity(intent);
        }
        if (itemId == R.id.setting){
            Intent intent = new Intent(Calendar.this, Setting.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


}