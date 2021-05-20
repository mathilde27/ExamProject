package com.example.examproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPlantsActivity extends AppCompatActivity {

    EditText name, description;
    Button addPlant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plants);

        name = findViewById(R.id.plant_name);
        description = findViewById(R.id.plant_desc);
        addPlant = findViewById(R.id.addPlant);

        addPlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(name.getText().toString()) && !TextUtils.isEmpty(description.getText().toString())) {
                    DatabaseClass db = new DatabaseClass(AddPlantsActivity.this);
                    db.addPlants(name.getText().toString(), description.getText().toString());

                    Intent intent = new Intent(AddPlantsActivity.this, MyPlants.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(AddPlantsActivity.this, "Please fill in both fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}