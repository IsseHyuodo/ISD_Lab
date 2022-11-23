package com.example.clms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Return_Book extends AppCompatActivity {

    Button returnB, search;
    TextView  name, author, isbn, issuer, id, iDate, dDate, fine;
    EditText Acc;
    CheckBox cb;
    LinearLayout details;
    AlertDialog.Builder builder;
    DatabaseReference ref, ref1, ref2;
    SimpleDateFormat sdf  = new SimpleDateFormat("dd/MM/yy" );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_book);

        search = (Button) findViewById(R.id.search);
        returnB = (Button) findViewById(R.id.return_book);
        Acc = (EditText) findViewById(R.id.searchBar);
        name = (TextView) findViewById(R.id.BookName);
        author = (TextView) findViewById(R.id.bookAuthor3);
        isbn = (TextView) findViewById(R.id.ISBN3);
        issuer = (TextView) findViewById(R.id.issue_name);
        id = (TextView) findViewById(R.id.issue_id);
        iDate = (TextView) findViewById(R.id.IDate);
        dDate = (TextView) findViewById(R.id.DDate);
        cb = (CheckBox) findViewById(R.id.checkBox);
        details = (LinearLayout) findViewById(R.id.details);

        builder = new AlertDialog.Builder(this);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(Acc.getText()))
                    details.setVisibility(View.INVISIBLE);
                else
                {

                    ref = FirebaseDatabase.getInstance().getReference("issuedCopies").child(Acc.getText().toString());

                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            iDate.setText("Issued on: \n" + snapshot.child("IDate").getValue(String.class));
                            dDate.setText("Due on:\n"+snapshot.child("DDate").getValue(String.class));
                            //Toast.makeText(Return_Book.this, snapshot.child("DDate").getValue(String.class), Toast.LENGTH_SHORT).show();
                            ref1 = FirebaseDatabase.getInstance().getReference("books").child(snapshot.child("ISBN").getValue(String.class));
                            ref1.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot ds) {
                                    name.setText(ds.child("Title").getValue(String.class));
                                    author.setText(ds.child("Author").getValue(String.class));
                                    isbn.setText(ds.child("ISBN").getValue(String.class));
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            ref2 = FirebaseDatabase.getInstance().getReference("users").child(Objects.requireNonNull(snapshot.child("user").getValue(String.class)));
                            ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot DS) {
                                    issuer.setText(DS.child("Name").getValue(String.class));
                                    id.setText(DS.getKey());
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            details.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }
        });

        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cb.isChecked())
                {
                    returnB.setEnabled(true);
                }
                else
                {
                    returnB.setEnabled(false);
                }
            }
        });
        returnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setMessage("Does the user want to return the book ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ref.removeValue();

                                Toast.makeText(getApplicationContext(),"Book returned.",
                                        Toast.LENGTH_SHORT).show();finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(),"Book return failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Return Book");
                alert.show();
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater()
                .inflate(R.menu.lt_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent i = new Intent(Return_Book.this, MainActivity.class);
        if(item.getItemId()==R.id.Log_Out)
        {
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}