package com.example.clms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class AL_Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_al_home);
    }

    public void Return(View view) {
        Intent i = new Intent(AL_Home.this, Return_Book.class);
        i.putExtra("person", "LT");
        startActivity(i);
    }

    public void Register(View view) {
        Intent i = new Intent(AL_Home.this, Registeration.class);
        i.putExtra("person", "LT");
        startActivity(i);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater()
                .inflate(R.menu.lt_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent i = new Intent(AL_Home.this, MainActivity.class);
        if(item.getItemId()==R.id.Log_Out)
        {
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}