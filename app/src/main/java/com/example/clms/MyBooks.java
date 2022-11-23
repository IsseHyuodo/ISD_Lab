package com.example.clms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

public class MyBooks extends AppCompatActivity {

    RecyclerView.Adapter adapter;
    ArrayList<String> iD= new ArrayList<>();
    ArrayList<String> dD= new ArrayList<>();
    ArrayList<String> acc= new ArrayList<>();

    FirebaseDatabase rootNode;
    boolean b =false;
    DatabaseReference ref;
    private List<MyBook> myBooks=new ArrayList<>();
    RecyclerView recyclerView;
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
    String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_books);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        showBook();


    }

    private void showBook() {

        /*MyBook book = new MyBook(5,"23-6789-4","ABC", "ECE", "Henry", "XYZ", 345, "A/45");
        myBooks.add(book);
        myBooks.add(book);
        myBooks.add(book);
        myBooks.add(book);
        myBooks.add(book);
        myBooks.add(book);
        myBooks.add(book);
        myBooks.add(book);
        myBooks.add(book);
        myBooks.add(book);*/



        Intent c = getIntent();
        user = c.getStringExtra("user");
        ref = FirebaseDatabase.getInstance().getReference("issuedCopies");
        Query q = ref.orderByChild("user").equalTo(user);

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren())
                {
                    //Toast.makeText(MyBooks.this, ds.getKey(), Toast.LENGTH_SHORT).show();
                    DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("books");
                    Query q1 = ref1.orderByChild("ISBN").equalTo(ds.child("ISBN").getValue(String.class));
                    q1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snap) {
                            if(snap.exists())
                            {
                                for(DataSnapshot ds1: snap.getChildren())
                                {

                                    MyBook book = new MyBook((ds1.child("Acc").getChildrenCount()),
                                            ds1.child("ISBN").getValue(String.class),
                                            ds1.child("Title").getValue(String.class),
                                            ds1.child("Subject").getValue(String.class),
                                            ds1.child("Author").getValue(String.class),
                                            ds1.child("Publisher").getValue(String.class),
                                            Integer.parseInt(ds1.child("Pages").getValue(String.class)),
                                            ds1.child("Rack").getValue(String.class))    ;

                                    myBooks.add(book);
                                    iD.add(ds.child("IDate").getValue(String.class));
                                    acc.add(ds.getKey());
                                    dD.add(ds.child("DDate").getValue(String.class));
                                    recyclerView.setLayoutManager(new LinearLayoutManager(getParent()));
                                    //Toast.makeText(MyBooks.this, "ToAdapter", Toast.LENGTH_SHORT).show();
                                    adapter=new Adapter(myBooks,iD,dD,acc);
                                    recyclerView.setHasFixedSize(true);
                                    recyclerView.setAdapter(adapter);

                                    //Toast.makeText(MyBooks.this, String.valueOf(myBooks.size()), Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(MyBooks.this, "No Data Found", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                    //Toast.makeText(MyBooks.this, String.valueOf(iD.size()), Toast.LENGTH_SHORT).show();
                }

                /*recyclerView.setLayoutManager(new LinearLayoutManager(getParent()));
                Toast.makeText(MyBooks.this, "ToAdapter", Toast.LENGTH_SHORT).show();
                adapter=new Adapter(myBooks);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
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
                return true;
            case R.id.search_books:
                i = new Intent(MyBooks.this, Search.class);
                i.putExtra("person", "User");
                i.putExtra("user", user);
                startActivity(i);
                return true;
            case R.id.me:
                i = new Intent(MyBooks.this, UserHome.class);
                i.putExtra("person", "User");
                i.putExtra("user", user);
                startActivity(i);
                return true;
            case R.id.Log_Out:
                i = new Intent(MyBooks.this, MainActivity.class);
                i.putExtra("Type", "Student/Faculty");
                i.putExtra("person", "User");
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}