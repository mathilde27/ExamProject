package com.example.examproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    NavController navController;
    AppBarConfiguration appBarConfiguration;

    private TextView register;
    private EditText editEmail, editPassword;
    private Button signIn;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = findViewById(R.id.register);
        register.setOnClickListener(this);

        signIn = findViewById(R.id.signIn);
        signIn.setOnClickListener(this);

        editEmail = findViewById(R.id.email);
        editPassword = findViewById(R.id.password);

        progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                startActivity(new Intent(this, Register.class));
                break;

            case R.id.signIn:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if(email.isEmpty()){
            editEmail.setError("Email required");
            editEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.setError("Not a valid email");
            editEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editPassword.setError("Password required");
            editPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this, Home.class));
                }else {
                    Toast.makeText(MainActivity.this, "Failed to login, Try again", Toast.LENGTH_LONG).show();
                   progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

//        bottomNavigationView = findViewById(R.id.bottom_navigation);
//        //Set Home as the selected
//        bottomNavigationView.setSelectedItemId(R.id.home);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.home:
//                        return true;
//                    case R.id.myplant:
//                        startActivity(new Intent(getApplicationContext()
//                        , MyPlants.class));
//                        overridePendingTransition(0,0);
//                        return true;
//                    case R.id.calendar:
//                        startActivity(new Intent(getApplicationContext()
//                                , Calendar.class));
//                        overridePendingTransition(0,0);
//                        return true;
//                }
//
//                return false;
//            }
//        });
//
//        Toolbar toolbar = findViewById(R.id.my_toolbar); //will set the title = the app name when removing the String i hardcoded.
//        setSupportActionBar(toolbar);
//    }
//
//    //implements the menu
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) { // sets the top menu
//        getMenuInflater().inflate(R.menu.top_menu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//    //What to do when items in setting is clicked. Should obviously not do this but take the user to a new page
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int itemId =item.getItemId();
//
//       if (itemId == R.id.profile){
//           Intent intent = new Intent(MainActivity.this, Profile.class);
//           startActivity(intent);
//        }
//        if (itemId == R.id.setting){
//            Intent intent = new Intent(MainActivity.this, Setting.class);
//            startActivity(intent);
//        }
//        return super.onOptionsItemSelected(item);
//    }

}