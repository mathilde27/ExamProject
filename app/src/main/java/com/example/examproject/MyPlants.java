package com.example.examproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MyPlants extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton fab;
    Adapter adapter;
    List<Plant> plantsList;
    DatabaseClass databaseClass;
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_plants);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        //Set Home as the selected
        bottomNavigationView.setSelectedItemId(R.id.myplant);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.myplant:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                , MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.calendar:
                        startActivity(new Intent(getApplicationContext()
                                , Calendar.class));
                        overridePendingTransition(0, 0);
                        return true;
                }

                return false;
            }
        });
        //new shit - should be in my plant fragment somehow
        recyclerView = findViewById(R.id.recycler_view);
        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPlants.this, AddPlantsActivity.class); //this starts the activity add plant with the view. But this should be located in myplantfragment
                startActivity(intent);
            }
        });

        plantsList = new ArrayList<>();
        databaseClass = new DatabaseClass(this);
        fetchAllNotesFromDatabase();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this, MyPlants.this, plantsList);
        recyclerView.setAdapter(adapter);
    }

    private void fetchAllNotesFromDatabase() {
        Cursor cursor = databaseClass.readAllData();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data to show", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                plantsList.add(new Plant(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
            }
        }
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
            Intent intent = new Intent(MyPlants.this, Profile.class); //this starts the activity add plant with the view. But this should be located in myplantfragment
            startActivity(intent);
        }
        if (itemId == R.id.setting){
            Intent intent = new Intent(MyPlants.this, Setting.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}