package com.example.e_book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;

public class SingupActivity extends AppCompatActivity {


    EditText etName,etEmail,etPass,etConfirmPassword;
    TextView tvLogIn;
    Button btnRegister;

    FirebaseDatabase database;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);


        init();
        btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPass.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();
                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(SingupActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(SingupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {

                    database=FirebaseDatabase.getInstance();
                    reference=database.getReference("Users");
                    SignupHelper helper=new SignupHelper(name,email,password,confirmPassword);
                    reference.child(name).setValue(helper);
                    Toast.makeText(SingupActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SingupActivity.this, LogInActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        tvLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingupActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    void init()
    {
        etName=findViewById(R.id.etName);
        etEmail=findViewById(R.id.etEmail);
        etPass=findViewById(R.id.etPassword);
        etConfirmPassword=findViewById(R.id.etConfirmPassword);
        tvLogIn=findViewById(R.id.tvLogIn);
        btnRegister = findViewById(R.id.btnRegister);
    }

}