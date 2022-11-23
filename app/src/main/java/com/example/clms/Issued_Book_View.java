package com.example.clms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Issued_Book_View extends AppCompatActivity {
    Button issue;
    TextView Title, Author, Publisher, ISBN, Pages, Subject, Acc, Id,Dd;
    AlertDialog.Builder builder;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issued_book_view);
        Title = (TextView) findViewById(R.id.Title1);
        Author = (TextView) findViewById(R.id.tvAuthor1);
        Publisher = (TextView) findViewById(R.id.tvPublisher1);
        ISBN = (TextView) findViewById(R.id.ISBN2);
        Pages = (TextView) findViewById(R.id.pages1);
        Subject = (TextView) findViewById(R.id.Subject1);
        Acc = (TextView) findViewById(R.id.Acc);
        Id = (TextView) findViewById(R.id.issueDate);
        Dd = (TextView) findViewById(R.id.dueDate);

        Intent in= getIntent();
        ref = FirebaseDatabase.getInstance().getReference("books").child(in.getStringExtra("ISBN"));

        Id.setText("Issued on: " + in.getStringExtra("IDate"));
        Dd.setText("Due on: " + in.getStringExtra("DDate"));
        Acc.setText("Accession: " + in.getStringExtra("Acc"));
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                Title.setText(ds.child("Title").getValue(String.class));
                Author.setText(ds.child("Author").getValue(String.class));
                Publisher.setText(ds.child("Publisher").getValue(String.class));
                ISBN.setText("ISBN: "+in.getStringExtra("ISBN"));
                Pages.setText("Pages: " + ds.child("Pages").getValue(String.class));
                Subject.setText("Subject: " + ds.child("Subject").getValue(String.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}