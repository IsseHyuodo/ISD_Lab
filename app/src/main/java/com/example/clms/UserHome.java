package com.example.clms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class UserHome extends AppCompatActivity {

    Intent Catch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        Catch = getIntent();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater()
                .inflate(R.menu.my_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT)
                .show();*/
        Intent i;
        switch (item.getItemId()) {
            case R.id.my_books:
                i = new Intent(UserHome.this, MyBooks.class);
                i.putExtra("person", "User");
                i.putExtra("user", Catch.getStringExtra("user"));
                startActivity(i);
                return true;
            case R.id.search_books:
                i = new Intent(UserHome.this, Search.class);
                i.putExtra("person", "User");
                i.putExtra("user", Catch.getStringExtra("user"));
                startActivity(i);
                return true;
            case R.id.me:
                return true;
            case R.id.Log_Out:
                i = new Intent(UserHome.this, MainActivity.class);
                i.putExtra("Type", "Student/Faculty");
                i.putExtra("person", "User");
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}