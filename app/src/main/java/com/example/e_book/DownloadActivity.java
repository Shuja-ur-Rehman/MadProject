package com.example.e_book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DownloadActivity extends AppCompatActivity {

    Button btnHome,btnDownload,btnAccount;

    RecyclerView rvDownloadBook;
    BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        init();
        rvDownloadBook.setLayoutManager(new LinearLayoutManager(this));
        Query query = FirebaseDatabase.getInstance().getReference().child("Book");
        FirebaseRecyclerOptions<Book> options =
                new FirebaseRecyclerOptions.Builder<Book>()
                        .setQuery(query, Book.class)
                        .build();
        adapter=new BookAdapter(options,this);
        rvDownloadBook.setAdapter(adapter);
        FirebaseDatabase.getInstance()
                .getReference().child("Book")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String result="";
                        for(DataSnapshot sShot:snapshot.getChildren())
                        {
                            result=result+"BookName: "+sShot.child("bookname").getValue(String.class)
                                    +"\nDiscription: "+sShot.child("discription").getValue(String.class)
                                    +"\nTimeStamp: "+sShot.child("timestamp").getValue(String.class)
                                    +"\nAuthor: "+sShot.child("author").getValue(String.class)
                                    +"\n\n";
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DownloadActivity.this, MainActivity.class);
                Toast.makeText(DownloadActivity.this, "Home Activity ", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        });



    }

    void init()
    {
        btnHome=findViewById(R.id.btnHome);
        btnDownload=findViewById(R.id.btnDownload);
        btnAccount=findViewById(R.id.btnAccount);
        rvDownloadBook  = findViewById(R.id.rvDownloadBook);


    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}