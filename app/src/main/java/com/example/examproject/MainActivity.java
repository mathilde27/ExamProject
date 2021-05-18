package com.example.examproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    NavController navController;
    AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
       // bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        //setting home fragment as main
      //  getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, home).commit();

        Toolbar toolbar = findViewById(R.id.my_toolbar); //will set the title = the app name when removing the String i hardcoded.
        setSupportActionBar(toolbar);

//        //new shit - should be in my plant fragment somehow
//        recyclerView = findViewById(R.id.recycler_view);
//        fab = findViewById(R.id.fab);
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, AddPlantsActivity.class); //this starts the activity add plant with the view. But this should be located in myplantfragment
//                startActivity(intent);
//            }
//        });
//
//        plantsList = new ArrayList<>();
//        databaseClass = new DatabaseClass(this);
//        fetchAllNotesFromDatabase();
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new Adapter(this, MainActivity.this, plantsList);
//        recyclerView.setAdapter(adapter);
    }

//    private void fetchAllNotesFromDatabase() {
//        Cursor cursor = databaseClass.readAllData();
//
//        if (cursor.getCount() == 0) {
//            Toast.makeText(this, "No Data to show", Toast.LENGTH_SHORT).show();
//        } else {
//            while (cursor.moveToNext()) {
//                plantsList.add(new Plant(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
//            }
//        }
//    }

    //Listener nav bar
//    private  BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            Fragment selectedFragment = null;
//
//            switch(item.getItemId()){
//                case R.id.home:
//                    selectedFragment = new Home();
//                    break;
//
//                case R.id.myplant:
//                    selectedFragment = new MyPlants();
//                    break;
//
//                case R.id.calendar:
//                    selectedFragment = new Calendar();
//                    break;
//            }
//            //Begin transaction
//            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, selectedFragment).commit();
//            return true;
//        }
//    };

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
           Intent intent = new Intent(MainActivity.this, Profile.class); //this starts the activity add plant with the view. But this should be located in myplantfragment
           startActivity(intent);
        }
        if (itemId == R.id.setting){
            Intent intent = new Intent(MainActivity.this, Setting.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}