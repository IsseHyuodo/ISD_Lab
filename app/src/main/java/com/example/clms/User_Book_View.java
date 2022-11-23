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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class User_Book_View extends AppCompatActivity {

    Button issue;
    TextView Title, Author, Publisher, ISBN, Pages, Subject, Copies, Rack;
    AlertDialog.Builder builder;
    DatabaseReference ref, ref1;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");
    Calendar cal = Calendar.getInstance();
    String p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_book_view);


        Title = (TextView) findViewById(R.id.Title);
        Author = (TextView) findViewById(R.id.tvAuthor);
        Publisher = (TextView) findViewById(R.id.tvPublisher);
        ISBN = (TextView) findViewById(R.id.ISBN1);
        Pages = (TextView) findViewById(R.id.pages);
        Subject = (TextView) findViewById(R.id.Subject);
        Copies = (TextView) findViewById(R.id.copies1);
        Rack = (TextView) findViewById(R.id.rack);



        Intent in= getIntent();
        p = in.getStringExtra("user");


        ref = FirebaseDatabase.getInstance().getReference("books").child(in.getStringExtra("ISBN"));
        ref1 = FirebaseDatabase.getInstance().getReference("issuedCopies");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                Title.setText(ds.child("Title").getValue(String.class));
                Author.setText(ds.child("Author").getValue(String.class));
                Publisher.setText(ds.child("Publisher").getValue(String.class));
                ISBN.setText("ISBN: "+in.getStringExtra("ISBN"));
                Pages.setText("Pages: " + ds.child("Pages").getValue(String.class));
                Subject.setText("Subject: " + ds.child("Subject").getValue(String.class));
                Copies.setText("Copies: " + String.valueOf(ds.child("Copies").getValue(Integer.class)));
                Rack.setText("Rack: "+ds.child("Rack").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        issue = (Button) findViewById(R.id.Issue);
        builder = new AlertDialog.Builder(this);
        issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference ref2 = ref.child("Acc");
                builder.setMessage("Do you want to issue this book ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    if(snapshot.getChildrenCount() > 0)
                                    {
                                        Toast.makeText(User_Book_View.this, snapshot.child(String.valueOf(snapshot.getChildrenCount()-1)).getValue(String.class), Toast.LENGTH_SHORT).show();
                                        String acc = snapshot.child("1").getValue(String.class);
                                        DatabaseReference ref3 = ref1.child(acc);
                                        ref3.child("ISBN").setValue(in.getStringExtra("ISBN"));
                                        ref3.child("user").setValue(p);
                                        ref3.child("IDate").setValue(simpleDateFormat.format(new Date()));
                                        //ref3.child("Copies").setValue(String.valueOf(Integer.parseInt(Copies.getText().toString())-1));
                                        //Toast.makeText(User_Book_View.this,String.valueOf(Integer.parseInt(Copies.getText().toString())-1), Toast.LENGTH_SHORT).show();
                                        cal.setTime(new Date());
                                        cal.add(Calendar.DATE, 15);
                                        String output = simpleDateFormat.format(cal.getTime());
                                        ref3.child("DDate").setValue(output);
                                        finish();
                                        Toast.makeText(getApplicationContext(),"Book issued.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(),"Book issue failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Issue Book");
                alert.show();
            }
        });
    }


}