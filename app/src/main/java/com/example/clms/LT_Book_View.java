package com.example.clms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LT_Book_View extends AppCompatActivity {

    AlertDialog.Builder builder;
    Button remove, update;
    TextView Title, Author, Publisher, ISBN, Pages, Subject, Copies, Rack;
    DatabaseReference ref;
    Intent in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lt_book_view);
        remove = (Button) findViewById(R.id.Remove);
        update = (Button) findViewById(R.id.Update);
        builder = new AlertDialog.Builder(this);

        in = getIntent();
        ref = FirebaseDatabase.getInstance().getReference("books").child(in.getStringExtra("ISBN"));

        Title = (TextView) findViewById(R.id.Title);
        Author = (TextView) findViewById(R.id.tvAuthor);
        Publisher = (TextView) findViewById(R.id.tvPublisher);
        ISBN = (TextView) findViewById(R.id.ISBN1);
        Pages = (TextView) findViewById(R.id.pages);
        Subject = (TextView) findViewById(R.id.Subject);
        Copies = (TextView) findViewById(R.id.copies1);
        Rack = (TextView) findViewById(R.id.rack);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                   // Toast.makeText(LT_Book_View.this, ds.getKey(), Toast.LENGTH_SHORT).show();
                    Title.setText(ds.child("Title").getValue(String.class));
                    Author.setText(ds.child("Author").getValue(String.class));
                    Publisher.setText(ds.child("Publisher").getValue(String.class));
                    ISBN.setText("ISBN: "+in.getStringExtra("ISBN"));
                    Pages.setText("Pages: " + ds.child("Pages").getValue(String.class));
                    Subject.setText("Subject: " + ds.child("Subject").getValue(String.class));
                    Copies.setText("Copies: " + String.valueOf(ds.child("Acc").getChildrenCount()));
                    Rack.setText("Rack: "+ds.child("Rack").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LT_Book_View.this, Update_Book.class);
                i.putExtra("Title", in.getStringExtra("Title"));
                i.putExtra("Author", in.getStringExtra("Author"));
                i.putExtra("Publisher", in.getStringExtra("Publisher"));
                i.putExtra("ISBN", in.getStringExtra("ISBN"));
                i.putExtra("Subject", in.getStringExtra("Subject"));
                i.putExtra("Pages", in.getStringExtra("Pages"));
                i.putExtra("Rack", in.getStringExtra("Rack"));
                i.putExtra("Copies", in.getStringExtra("Copies"));
                startActivity(i);
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setMessage("Do you want to remove this book ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ref.removeValue();
                                Toast.makeText(getApplicationContext(),"Book removed.",
                                        Toast.LENGTH_SHORT).show();finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(),"Book remove failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Remove Book");
                alert.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                // Toast.makeText(LT_Book_View.this, ds.getKey(), Toast.LENGTH_SHORT).show();
                Title.setText(ds.child("Title").getValue(String.class));
                Author.setText(ds.child("Author").getValue(String.class));
                Publisher.setText(ds.child("Publisher").getValue(String.class));
                ISBN.setText("ISBN: "+in.getStringExtra("ISBN"));
                Pages.setText("Pages: " + ds.child("Pages").getValue(String.class));
                Subject.setText("Subject: " + ds.child("Subject").getValue(String.class));
                Copies.setText("Copies: " + String.valueOf(ds.child("Acc").getChildrenCount()));
                Rack.setText("Rack: "+ds.child("Rack").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}