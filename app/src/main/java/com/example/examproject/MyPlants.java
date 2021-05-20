package com.example.examproject;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

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
    CoordinatorLayout layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_plants);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        layout = findViewById(R.id.layout_myplants);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.myplant);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.myplant:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                , Home.class));
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
        recyclerView = findViewById(R.id.recycler_view);
        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPlants.this, AddPlantsActivity.class);
                startActivity(intent);
            }
        });

        plantsList = new ArrayList<>();
        databaseClass = new DatabaseClass(this);
        fetchAllNotesFromDatabase();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this, MyPlants.this, plantsList);
        recyclerView.setAdapter(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);
    }

    private void fetchAllNotesFromDatabase() {
        Cursor cursor = databaseClass.readAllData();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No plants yet, add now!", Toast.LENGTH_LONG).show();
        } else {
            while (cursor.moveToNext()) {
                plantsList.add(new Plant(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId =item.getItemId();

        if (itemId == R.id.profile){
            Intent intent = new Intent(MyPlants.this, Profile.class);
            startActivity(intent);
        }
        if (itemId == R.id.setting){
            Intent intent = new Intent(MyPlants.this, Setting.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            Plant plant = adapter.getPlantsList().get(position);
            adapter.removePlant(viewHolder.getAdapterPosition());
            Snackbar snackbar = Snackbar.make(layout, "Plant Deleted", Snackbar.LENGTH_LONG)
                    .setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            adapter.restorePlant(plant, position);
                            recyclerView.scrollToPosition(position);
                        }
                    }).addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                        @Override
                        public void onDismissed(Snackbar transientBottomBar, int event) {
                            super.onDismissed(transientBottomBar, event);

                            if (!(event == DISMISS_EVENT_ACTION)) {
                                DatabaseClass db = new DatabaseClass(MyPlants.this);
                                db.deletePlant(plant.getId());
                            }
                        }
                    });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    };
}