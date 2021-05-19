package com.example.examproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateMyPlants extends AppCompatActivity {

    EditText name, description;
    Button update;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_my_plants);

        name = findViewById(R.id.plant_name);
        description = findViewById(R.id.plant_desc);
        update = findViewById(R.id.updatePlant);

        Intent intent = getIntent();
        name.setText(intent.getStringExtra("name"));
        description.setText(intent.getStringExtra("description"));
        id=intent.getStringExtra("id");

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(name.getText().toString()) && !TextUtils.isEmpty(description.getText().toString()))
                {

                    DatabaseClass db = new DatabaseClass(UpdateMyPlants.this);
                    db.updatePlant(name.getText().toString(),description.getText().toString(),id);

                    //go to my plants afterwards
                    Intent i=new Intent(UpdateMyPlants.this, MyPlants.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Toast.makeText(UpdateMyPlants.this, "Both Fields Required", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}