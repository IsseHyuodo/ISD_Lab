package com.example.clms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void UserClick(View view) {
        Intent intent = new Intent(MainActivity.this , SignIn.class);
        intent.putExtra( "Type","Student/Faculty");
        startActivity(intent);
        //Toast.makeText(this, "Signed In Successfully", Toast.LENGTH_SHORT).show();
    }
    public void AlClick(View view) {
        Intent intent = new Intent(MainActivity.this , SignIn.class);
        intent.putExtra( "Type","Assistant Librarian");
        startActivity(intent);
        //Toast.makeText(this, "Signed In Successfully", Toast.LENGTH_SHORT).show();
    }
    public void LtClick(View view) {
        Intent intent = new Intent(MainActivity.this , SignIn.class);
        intent.putExtra( "Type","Library Trainee");
        intent.putExtra("person", "Library Trainee");
        startActivity(intent);
        //Toast.makeText(this, "Signed In Successfully", Toast.LENGTH_SHORT).show();
    }


}