package com.example.clms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class LT_Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lt_home);
    }

    public void add(View view) {
        Intent i = new Intent(LT_Home.this, Add_Book.class);
        i.putExtra("person", "LT");
        startActivity(i);
    }

    public void search(View view) {
        Intent i = new Intent(LT_Home.this, Search.class);
        i.putExtra("person", "LT");
        startActivity(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater()
                .inflate(R.menu.lt_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent i = new Intent(LT_Home.this, MainActivity.class);
        if(item.getItemId()==R.id.Log_Out)
        {
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}