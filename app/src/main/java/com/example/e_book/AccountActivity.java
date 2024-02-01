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

public class AccountActivity extends AppCompatActivity {
    TextView tvAccountHolderName;
    EditText etEmail;
    Button btnForget, btnCheckEmail,btnHome,btnDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        init();

        btnForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnCheckEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, DownloadActivity.class);
                Toast.makeText(AccountActivity.this, "Download Activity", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, MainActivity.class);
                Toast.makeText(AccountActivity.this, "Home Activity", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        });


       // simulateLogin();
    }

    void init() {
        tvAccountHolderName = findViewById(R.id.tvAccoutHolderName);
        etEmail = findViewById(R.id.etEmail);
        btnForget = findViewById(R.id.btnForget);
        btnCheckEmail = findViewById(R.id.btnChackEmail);
        btnHome=findViewById(R.id.btnHome);
        btnDownload=findViewById(R.id.btnDownload);
    }

//    private void simulateLogin() {
//
//        String loggedInUserEmail = etEmail.getText().toString().trim();
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
//        Query getUser = reference.orderByChild("email").equalTo(loggedInUserEmail);
//        getUser.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
//                        String userName = userSnapshot.child("name").getValue(String.class);
//                        if (userName != null) {
//                            tvAccountHolderName.setText(userName);
//                            return;
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
}
