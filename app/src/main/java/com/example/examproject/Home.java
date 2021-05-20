package com.example.examproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class Home extends AppCompatActivity {

    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    NavController navController;
    AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        //Set Home as the selected
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        return true;
                    case R.id.myplant:
                        startActivity(new Intent(getApplicationContext()
                                , MyPlants.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.calendar:
                        startActivity(new Intent(getApplicationContext()
                                , Calendar.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        Toolbar toolbar = findViewById(R.id.my_toolbar); //will set the title = the app name when removing the String i hardcoded.
        setSupportActionBar(toolbar);
    }
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
            Intent intent = new Intent(Home.this, Profile.class);
            startActivity(intent);
        }
        if (itemId == R.id.setting){
            Intent intent = new Intent(Home.this, Setting.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}