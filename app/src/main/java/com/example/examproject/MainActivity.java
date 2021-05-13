package com.example.examproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    NavController navController;
    AppBarConfiguration appBarConfiguration;
    HomeFragment home = new HomeFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.front_page);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        //setting home fragment as main
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, home).commit();

        Toolbar toolbar = findViewById(R.id.my_toolbar); //will set the title = the app name when removing the String i hardcoded.
        setSupportActionBar(toolbar);
    }

    //Listener nav bar
    private  BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch(item.getItemId()){
                case R.id.home:
                    selectedFragment = new HomeFragment();
                    break;

                case R.id.myplant:
                    selectedFragment = new MyPlantsFragment();
                    break;

                case R.id.calendar:
                    selectedFragment = new CalendarFragment();
                    break;
            }
            //Begin transaction
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, selectedFragment).commit();
            return true;
        }
    };

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
            Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
           getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new ProfileFragment()).commit();
        }
        if (itemId == R.id.setting){
            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new SettingFragment()).commit();
        }
        return super.onOptionsItemSelected(item);
    }

}