package com.example.e_book;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;


public class BookAdapter extends FirebaseRecyclerAdapter<Book,BookAdapter.BookViewHolder> {

Context parent;
    public BookAdapter(@NonNull FirebaseRecyclerOptions<Book> options, Context context) {
        super(options);
        parent=context;
    }

    public interface  ShowData{
        void ShowBook(Book data);
    }
    ShowData clicked;
    @Override
    protected void onBindViewHolder(@NonNull BookViewHolder holder, int position, @NonNull Book model) {

        holder.tvBookName.setText(model.getBookname());
        holder.tvDecription.setText(model.getDiscription());
        holder.tvAuthorName.setText(model.getAuthorname());
        holder.tvTS.setText(model.getTimestamp());
        clicked = (ShowData) parent;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), ""+ model.getAuthorname() , Toast.LENGTH_SHORT).show();

                AlertDialog.Builder popup = new AlertDialog.Builder(parent);
                View popupView = View.inflate(parent ,R.layout.downloadbook, null);
                popup.setView(popupView);

                TextView textView = popupView.findViewById(R.id.tvBook); // Replace with the actual id of your TextView
                textView.setText(model.getBookname());
                TextView tvDiscription = popupView.findViewById(R.id.tvDiscription); // Replace with the actual id of your TextView
                tvDiscription.setText(model.getDiscription());
                TextView tvAuthorName = popupView.findViewById(R.id.tvAuthorName); // Replace with the actual id of your TextView
                tvAuthorName.setText(model.getAuthorname());
                Button downloadOption = popupView.findViewById(R.id.downloadOption);
                downloadOption.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(parent,"book is downlaod" , Toast.LENGTH_SHORT).show();
                    }
                });
                popup.show();

//clicked.ShowBook(model);
            }
        });
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder confirmationDialog=new AlertDialog.Builder(parent)
                        .setTitle("Confirmation").setMessage("Do yo want to delete this Book?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getRef(position).removeValue()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(parent, "Delete", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(parent, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                confirmationDialog.create();
                confirmationDialog.show();
                Toast.makeText(parent, "Item Deleted", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.bookdesign,parent,false);

        return new BookViewHolder(v);
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {

        TextView tvBookName,tvDecription,tvAuthorName,tvTS;
        ImageView ivDelete;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBookName=itemView.findViewById(R.id.tvBookName);
            tvDecription=itemView.findViewById(R.id.tvDecription);
            tvAuthorName=itemView.findViewById(R.id.tvAuthorName);
            tvTS=itemView.findViewById(R.id.tvTS);
            ivDelete=itemView.findViewById(R.id.ivDelete);

        }
    }

}
