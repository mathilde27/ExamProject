package com.example.examproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;

import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class Calendar extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    //MainActivity main = new MainActivity();
    Toolbar toolbar;
    AppBarConfiguration appBarConfiguration;
    EditText name, careDesc;
    Button addToCalendar;
    Spinner spinner;
    String repetition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

        name = findViewById(R.id.plantname);
        careDesc = findViewById(R.id.caredesc);
        spinner = findViewById(R.id.spinner);

        ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this,R.array.repetition, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        addToCalendar = findViewById(R.id.add_to_calendar);

        addToCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!name.getText().toString().isEmpty() && !careDesc.getText().toString().isEmpty()){

                    Intent intent = new Intent(Intent.ACTION_INSERT);
                    intent.setData(CalendarContract.Events.CONTENT_URI);
                    intent.putExtra(CalendarContract.Events.TITLE, name.getText().toString());
                    intent.putExtra(CalendarContract.Events.DESCRIPTION, careDesc.getText().toString());
                    intent.putExtra(CalendarContract.Events.RRULE, repetition);
                    intent.putExtra(CalendarContract.Reminders.HAS_ALARM, true); // not doing anything right now 

                    if(intent.resolveActivity(getPackageManager()) != null){
                        startActivity(intent);
                    }else {
                        Toast.makeText(Calendar.this, "There is no app that support this action", Toast.LENGTH_SHORT).show();
                    }

                    } else {
                Toast.makeText(Calendar.this, "All the fields are required",
                        Toast.LENGTH_SHORT).show();
            }
            }
        });

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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position == 0){
            repetition = "FREQ=DAILY;";
        }
        if(position == 1){
            repetition = "FREQ=WEEKLY;";
        }
        if(position == 2){
            repetition = "FREQ=MONTHLY;";
        }
        if(position == 3){
            repetition = null;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}