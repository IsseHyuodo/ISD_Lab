package com.example.clms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registeration extends AppCompatActivity {
    private EditText name;
    private EditText email;
    private EditText number;
    private EditText password;
    private EditText confirm_password;
    private CheckBox checkbox;
    private Spinner usertype;
    private RadioGroup rg;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        number = (EditText) findViewById(R.id.number);
        password = (EditText) findViewById(R.id.password);
        confirm_password = (EditText) findViewById(R.id.confirm_password);
        usertype = (Spinner) findViewById(R.id.spinner);
        rg = (RadioGroup) findViewById(R.id.gender);
        checkbox = (CheckBox) findViewById(R.id.checkBox);
        register = (Button) findViewById(R.id.register_button);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.usertype, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        usertype.setAdapter(adapter);
        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkbox.isChecked() == true) {
                    register.setEnabled(true);
                } else {
                    register.setEnabled(false);
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register(view);
            }
        });


    }

    public void register(View view) {
        int radiobuttoid = rg.getCheckedRadioButtonId();
        RadioButton selectedans = (RadioButton) findViewById(radiobuttoid);
        String gender1 = selectedans.getText().toString();
        String name1 = name.getText().toString();
        String email1 = email.getText().toString();
        String usertype1 = usertype.getSelectedItem().toString();
        String number1 = number.getText().toString();
        String password1 = password.getText().toString();
        String confirm_password1 = confirm_password.getText().toString();

        if (TextUtils.isEmpty(name1) || TextUtils.isEmpty(email1) || TextUtils.isEmpty(usertype1) || TextUtils.isEmpty(number1) || TextUtils.isEmpty(gender1) || TextUtils.isEmpty(number1) || TextUtils.isEmpty(password1) || TextUtils.isEmpty(confirm_password1)) {
            Toast.makeText(this, "Enter the Details Correctly", Toast.LENGTH_SHORT).show();
        } else if (!TextUtils.equals(password1, confirm_password1)) {
            Toast.makeText(this, "Re-enter the password", Toast.LENGTH_SHORT).show();

        } else {
            DatabaseReference ref= FirebaseDatabase.getInstance().getReference("users");
            String[] arr = email1.split("@");
            DatabaseReference ref2 = ref.child(arr[0]);
            //Toast.makeText(this, arr[0], Toast.LENGTH_SHORT).show();
            ref2.child("Name").setValue(name1);
            ref2.child("Email").setValue(email1);
            ref2.child("Type").setValue(usertype1);
            ref2.child("Phone").setValue(number1);
            ref2.child("Password").setValue(password1);
            ref2.child("Gender").setValue(gender1);
            Toast.makeText(this, "Register Successfully", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Registeration.this, AL_Home.class);
            startActivity(i);
        }


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater()
                .inflate(R.menu.lt_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent i = new Intent(Registeration.this, MainActivity.class);
        if(item.getItemId()==R.id.Log_Out)
        {
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}