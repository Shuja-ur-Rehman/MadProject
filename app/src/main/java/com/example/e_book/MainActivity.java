package com.example.e_book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements BookAdapter.ShowData{

    EditText etSearch;
    Button btnAdd,btnHome,btnDownload,btnAccount,btnSearch;

    RecyclerView rvBook;
    BookAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        rvBook.setLayoutManager(new LinearLayoutManager(this));
        Query query = FirebaseDatabase.getInstance().getReference().child("Book");
        FirebaseRecyclerOptions<Book> options =
                new FirebaseRecyclerOptions.Builder<Book>()
                        .setQuery(query, Book.class)
                        .build();
        adapter=new BookAdapter(options,this);
        rvBook.setAdapter(adapter);
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
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = etSearch.getText().toString().toString();

                Query query = FirebaseDatabase.getInstance().getReference().child("Book").orderByChild("bookname").startAt(searchText).endAt(searchText + "\uf8ff");

                FirebaseRecyclerOptions<Book> options =
                        new FirebaseRecyclerOptions.Builder<Book>()
                                .setQuery(query, Book.class)
                                .build();

                adapter.updateOptions(options);
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v= LayoutInflater.from(MainActivity.this).inflate(R.layout.addbook,null);

                TextView tvTimeStamp=v.findViewById(R.id.tvTimeStamp);
                EditText etBookName=v.findViewById(R.id.etBookName);
                EditText etDiscription=v.findViewById(R.id.etDiscription);
                EditText etAuthorName=v.findViewById(R.id.etAuthorName);

                SimpleDateFormat formater=new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                Date date=new Date();
                tvTimeStamp.setText(formater.format(date));

                AlertDialog.Builder addItemDilog=new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Creating new Book")
                        .setView(v)
                        .setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String bookname= Objects.requireNonNull(etBookName.getText().toString().trim());
                                String discription=Objects.requireNonNull(etDiscription.getText().toString().trim());
                                String authorname=Objects.requireNonNull(etAuthorName.getText().toString().trim());

                                HashMap<String,Object> data=new HashMap<>();
                                data.put("bookname",bookname);
                                data.put("discription",discription);
                                data.put("authorname",authorname);
                                data.put("timestamp",tvTimeStamp.getText().toString());

                                FirebaseDatabase .getInstance().getReference().child("Book")
                                        .push()
                                        .setValue(data)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(MainActivity.this, "Book created", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                addItemDilog.create();
                addItemDilog.show();
            }

        });

//        rvBook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                View view= LayoutInflater.from(MainActivity.this).inflate(R.layout.downloadbook,null);
//                TextView tvDownload=view.findViewById(R.id.tvDownload);
//               TextView tvBook=view.findViewById(R.id.tvBook);
//                TextView tvDiscription=view.findViewById(R.id.tvDiscription);
//                TextView tvAuthorName=view.findViewById(R.id.tvAuthorName);
//                AlertDialog.Builder DownloadDilog=new AlertDialog.Builder(MainActivity.this)
//                        .setTitle("Creating new Book")
//                        .setView(view)
//                        .setPositiveButton("Download", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                String bookname= Objects.requireNonNull(tvBook.getText().toString().trim());
//                                String discription=Objects.requireNonNull(tvDiscription.getText().toString().trim());
//                                String authorname=Objects.requireNonNull(tvAuthorName.getText().toString().trim());
//                                HashMap<String,Object> data=new HashMap<>();
//                                data.put("bookname",bookname);
//                                data.put("discription",discription);
//                                data.put("authorname",authorname);
//                                FirebaseDatabase .getInstance().getReference().child("Book")
//                                        .push()
//                                        .setValue(data)
//                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void unused) {
//                                                Toast.makeText(MainActivity.this, "Book Downloaded", Toast.LENGTH_SHORT).show();
//                                            }
//                                        })
//                                        .addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception e) {
//                                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                                            }
//                                        });
//
//                            }
//                        })
//                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        });
//                DownloadDilog.create();
//                DownloadDilog.show();
//
//            }
//        });

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DownloadActivity.class);
                Toast.makeText(MainActivity.this, "Download Activity", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();

            }
        });
        btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AccountActivity.class);
                Toast.makeText(MainActivity.this, "Account Activity", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        });


    }
    void init()
    {
        etSearch=findViewById(R.id.etSearch);
        btnAdd=findViewById(R.id.btnAdd);
        btnHome=findViewById(R.id.btnHome);
        btnDownload=findViewById(R.id.btnDownload);

        btnAccount=findViewById(R.id.btnAccount);
        rvBook  = findViewById(R.id.rvBook);
        btnSearch=findViewById(R.id.btnSearch);


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


   @Override
  public void ShowBook(Book data) {
//        Toast.makeText(this, ""+ data.getAuthorname(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, ""+ data.getBookname(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, ""+ data.getDiscription(), Toast.LENGTH_SHORT).show();
    }
}