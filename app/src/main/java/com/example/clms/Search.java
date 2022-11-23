package com.example.clms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Search extends AppCompatActivity {
    Spinner spinner;
    EditText name;
    Button search;

    private List<MyBook> myBooks = new ArrayList<>();
    RecyclerView recyclerView;
    String person, p, type;

    FirebaseDatabase rootNode;
    DatabaseReference ref;
    Query q;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        spinner = (Spinner) findViewById(R.id.spinner);
        search = (Button) findViewById(R.id.button);

        name = (EditText) findViewById(R.id.Name);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Type, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Intent Catch = getIntent();
        person = Catch.getStringExtra("person");
        type = Catch.getStringExtra("Type");
        p = Catch.getStringExtra("user");

        rootNode = FirebaseDatabase.getInstance();
        ref = rootNode.getReference("books");

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // myBooks=new ArrayList<>();

                String type = spinner.getSelectedItem().toString();
                String str = name.getText().toString();
                rootNode = FirebaseDatabase.getInstance();
                ref = rootNode.getReference("books");
                 q = ref.orderByChild(type).equalTo(str);
                // Toast.makeText(Search.this, type + " "+ str, Toast.LENGTH_SHORT).show();

                recyclerView = (RecyclerView) findViewById(R.id.recycler1);
                showBook(true);


            }
        });
    }

    private void showBook(boolean c) {

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myBooks.clear();
                if(snapshot.exists())
                {
                    for(DataSnapshot ds: snapshot.getChildren())
                    {
                        MyBook book = new MyBook((ds.child("Acc").getChildrenCount()),
                                                 ds.child("ISBN").getValue(String.class),
                                                    ds.child("Title").getValue(String.class),
                                                    ds.child("Subject").getValue(String.class),
                                                    ds.child("Author").getValue(String.class),
                                                    ds.child("Publisher").getValue(String.class),
                                                    Integer.parseInt(ds.child("Pages").getValue(String.class)),
                                                    ds.child("Rack").getValue(String.class));

                        myBooks.add(book);
                        //Toast.makeText(Search.this, String.valueOf(myBooks.size()), Toast.LENGTH_SHORT).show();
                    }
                }else if(c){
                    Toast.makeText(Search.this, "No Data Found", Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(Search.this, p, Toast.LENGTH_SHORT).show();
                RecyclerView.Adapter newadapter = new Search_Adapter(myBooks, person, p);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(newadapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getParent()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Search.this, "Database error", Toast.LENGTH_SHORT).show();
            }
        });
        /*MyBook book = new MyBook( 5,"23-6789-4","ABC", "Magazine", "Henry", "XYZ", 345, "A/45");
        myBooks.add(book);
        myBooks.add(book);
        myBooks.add(book);

        MyBook book1 = new MyBook(0,"23-6789-4","ABCD", "CSE", "Bob","XYZ", 345, "A/45");
        myBooks.add(book1);
        myBooks.add(book1);
        myBooks.add(book1);*/

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        if(person.equalsIgnoreCase("User"))
            getMenuInflater()
                    .inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT)
        //.show();
        Intent i;
        switch (item.getItemId()) {
            case R.id.my_books:
                i = new Intent(Search.this, MyBooks.class);
                i.putExtra("person", "User");
                i.putExtra("user", p);
                startActivity(i);
                return true;
            case R.id.search_books:
                return true;
            case R.id.me:
                i = new Intent(Search.this, UserHome.class);
                i.putExtra("person", "User");
                i.putExtra("user", p);
                startActivity(i);
                return true;
            case R.id.Log_Out:
                i = new Intent(Search.this, MainActivity.class);
                //i.putExtra("Type", "Student/Faculty");
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        String type = spinner.getSelectedItem().toString();
        String str = name.getText().toString();
        rootNode = FirebaseDatabase.getInstance();
        ref = rootNode.getReference("books");
        q = ref.orderByChild(type).equalTo(str);
        // Toast.makeText(Search.this, type + " "+ str, Toast.LENGTH_SHORT).show();

        recyclerView = (RecyclerView) findViewById(R.id.recycler1);
        showBook(false);

    }
}