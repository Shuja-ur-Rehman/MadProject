package com.example.e_book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LogInActivity extends AppCompatActivity {

    EditText etemail,etpasword;

    TextView btnSignIn;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        init();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LogInActivity.this , SingupActivity.class));
                Toast.makeText(LogInActivity.this, "start clicked listener", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
       btnLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (!validEmail() | !validPassword())
               {
                   Toast.makeText(LogInActivity.this, "Invalid email or Password", Toast.LENGTH_SHORT).show();
               }
               else
               {
                   CheckUser();
                   Toast.makeText(LogInActivity.this, "Successfully LogIn", Toast.LENGTH_SHORT).show();
               }
           }
       });


    }

    public boolean validEmail()
    {
        String val=etemail.getText().toString().trim();
        if (val.isEmpty())
        {
            etemail.setError("Email cannot be empty");
            return false;
        }
        else
        {
            etemail.setError(null);
            return true;
        }
    }

    public boolean validPassword()
    {
        String val=etpasword.getText().toString().trim();
        if (val.isEmpty())
        {
            etpasword.setError("Password cannot be empty");
            return false;
        }
        else
        {
            etpasword.setError(null);
            return true;
        }
    }
    public void CheckUser()
    {
        String email = etemail.getText().toString().trim();
        String password = etpasword.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        Query checkUser = reference.orderByChild("email").equalTo(email);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        String DBPassword = userSnapshot.child("password").getValue(String.class);
                        if (DBPassword != null && DBPassword.equals(password)) {
                            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            return;
                        }
                    }
                    etpasword.setError("Invalid Password");
                    etpasword.requestFocus();
                } else {

                    etemail.setError("Email does not exist");
                    etemail.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void init(){
        btnLogin=findViewById(R.id.btnLogin);
        etemail=findViewById(R.id.etEmail);
        etpasword=findViewById(R.id.etPassword);
        btnSignIn= findViewById(R.id.btnSignIn);
    }

}