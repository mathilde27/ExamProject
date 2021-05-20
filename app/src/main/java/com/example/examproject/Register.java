package com.example.examproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    EditText editfullName, editemail, editpassword;
    Button register;
    ProgressBar progressBar;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        title = findViewById(R.id.header_title);
        title.setOnClickListener(this);

        register = findViewById(R.id.registerMe);
        register.setOnClickListener(this);

        editfullName = findViewById(R.id.user_name);
        editemail = findViewById(R.id.email);
        editpassword = findViewById(R.id.password);

        progressBar = findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.header_title:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.registerMe:
                registerUser();
                break;
        }

    }

    private void registerUser() {
        String fullname = editfullName.getText().toString().trim();
        String email = editemail.getText().toString().trim();
        String password = editpassword.getText().toString().trim();

        if(fullname.isEmpty()){
            editfullName.setError("This field is required");
            editfullName.requestFocus();
            return;
        }
        if(email.isEmpty()){
            editemail.setError("This field is required");
            editemail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editemail.setError("Valid email required");
            editemail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editpassword.setError("This field is required");
            editpassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            editpassword.setError("Minimum 6 characters required");
            editpassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User user = new User(fullname, email);

                    //return id of registered user and the sets it to the newly created user
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().
                            getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(Register.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                //redirect to login site
                            }else {
                                Toast.makeText(Register.this, "Failed to register, Try again", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }else {
                    Toast.makeText(Register.this, "Failed to register, Try again", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}