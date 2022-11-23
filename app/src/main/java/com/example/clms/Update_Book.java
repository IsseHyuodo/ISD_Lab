package com.example.clms;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Update_Book extends AppCompatActivity {

    AutoCompleteTextView textIn;
    Button buttonAdd, Update;
    LinearLayout container;
    Spinner spinner;
    ArrayAdapter<CharSequence> arr_adapter;

    DatabaseReference ref ;
    private static final String[] NUMBER = new String[] {
            "One", "Two", "Three", "Four", "Five",
            "Six", "Seven", "Eight", "Nine", "Ten"
    };
    ArrayAdapter<String> adapter;
    EditText Title, Author, Publisher, ISBN, Pages, Rack;
    ArrayList<String> acc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);

        ref = FirebaseDatabase.getInstance().getReference("books");

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, NUMBER);

        textIn = (AutoCompleteTextView)findViewById(R.id.textin);
        textIn.setAdapter(adapter);

        buttonAdd = (Button)findViewById(R.id.add);
        Update = (Button)findViewById(R.id.add1);
        container = (LinearLayout) findViewById(R.id.container);

        spinner = (Spinner) findViewById(R.id.spinner2);

        arr_adapter = ArrayAdapter.createFromResource(this,
                R.array.list1, android.R.layout.simple_spinner_item);
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arr_adapter);

        Intent Catch = getIntent();
        SetupValues(Catch);


        buttonAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater =
                        (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.row, null);
                AutoCompleteTextView textOut = (AutoCompleteTextView)addView.findViewById(R.id.textout);
                textOut.setAdapter(adapter);
                textOut.setText(textIn.getText().toString());
                Button buttonRemove = (Button)addView.findViewById(R.id.row_remove);

                final View.OnClickListener thisListener = new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        ((LinearLayout)addView.getParent()).removeView(addView);

                        listAllAddView();
                    }
                };

                buttonRemove.setOnClickListener(thisListener);
                container.addView(addView);
                listAllAddView();
            }
        });

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref.child(Catch.getStringExtra("ISBN")).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getRef().child("Title").setValue(Title.getText().toString());
                        snapshot.getRef().child("Author").setValue(Author.getText().toString());
                        snapshot.getRef().child("Publisher").setValue(Publisher.getText().toString());
                        snapshot.getRef().child("ISBN").setValue(ISBN.getText().toString());
                        snapshot.getRef().child("Pages").setValue(Pages.getText().toString());
                        snapshot.getRef().child("Rack").setValue(Rack.getText().toString());
                        snapshot.getRef().child("Subject").setValue(spinner.getSelectedItem().toString());
                        snapshot.getRef().child("Acc").setValue(getAcc());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Toast.makeText(Update_Book.this, "Book is updated.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private ArrayList<String> getAcc() {
        int childCount = container.getChildCount();
        ArrayList<String> arr = new ArrayList<>();
        for(int i=0; i<childCount; i++){
            View thisChild = container.getChildAt(i);
            AutoCompleteTextView childTextView = (AutoCompleteTextView) thisChild.findViewById(R.id.textout);
            String childTextViewValue = childTextView.getText().toString();
            //Toast.makeText(this, childTextViewValue, Toast.LENGTH_SHORT).show();
            arr.add(childTextViewValue);
        }
        return arr;
    }

    private void SetupValues(Intent aCatch) {


        Title = (EditText) findViewById(R.id.editTitle1);
        Author = (EditText) findViewById(R.id.editAuthor1);
        Publisher = (EditText) findViewById(R.id.editpublish1);
        ISBN = (EditText) findViewById(R.id.editISBN1);
        Pages = (EditText) findViewById(R.id.editpages1);
        Rack = (EditText) findViewById(R.id.editRack1);

        Title.setText(aCatch.getStringExtra("Title"));
        Author.setText(aCatch.getStringExtra("Author"));
        Publisher.setText(aCatch.getStringExtra("Publisher"));
        ISBN.setText(aCatch.getStringExtra("ISBN"));
        Pages.setText(aCatch.getStringExtra("Pages"));
        Rack.setText(aCatch.getStringExtra("Rack"));
        spinner.setSelection(arr_adapter.getPosition(aCatch.getStringExtra("Subject")));

        Query q = ref.orderByChild("ISBN").equalTo(aCatch.getStringExtra("ISBN"));
        acc = new ArrayList<String>();
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Toast.makeText(Update_Book.this, snapshot.getKey(), Toast.LENGTH_SHORT).show();
                for(DataSnapshot ds: snapshot.getChildren())
                {
                    //Toast.makeText(Update_Book.this, ds.getKey(), Toast.LENGTH_SHORT).show();
                    for(DataSnapshot ds1: ds.child("Acc").getChildren())
                    {
                        //Toast.makeText(Update_Book.this, ds1.getValue(String.class), Toast.LENGTH_SHORT).show();
                        acc.add(ds1.getValue(String.class));
                    }
                }

                for(int i=0; i<acc.size(); i++)
                {
                    Adding(acc.get(i));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Update_Book.this, "Database Error", Toast.LENGTH_SHORT).show();
            }
        });

       // acc = new ArrayList<String>();

    }

    private void listAllAddView(){

        int childCount = container.getChildCount();
        for(int i=0; i<childCount; i++){
            View thisChild = container.getChildAt(i);
            AutoCompleteTextView childTextView = (AutoCompleteTextView) thisChild.findViewById(R.id.textout);
            String childTextViewValue = childTextView.getText().toString();
            //Toast.makeText(this, childTextViewValue, Toast.LENGTH_SHORT).show();

        }
    }

    private  void Adding(String str)
    {
        LayoutInflater layoutInflater =
                (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addView = layoutInflater.inflate(R.layout.row, null);
        AutoCompleteTextView textOut = (AutoCompleteTextView)addView.findViewById(R.id.textout);
        textOut.setAdapter(adapter);
        textOut.setText(str);
        Button buttonRemove = (Button)addView.findViewById(R.id.row_remove);

        final View.OnClickListener thisListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((LinearLayout)addView.getParent()).removeView(addView);

                listAllAddView();
            }
        };

        buttonRemove.setOnClickListener(thisListener);
        container.addView(addView);
        listAllAddView();
    }

}